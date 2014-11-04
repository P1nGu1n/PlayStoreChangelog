/*
 * Copyright (C) 2014  P1nGu1n
 *
 * This file is part of Play Store Changelog.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.p1ngu1n.playstorechangelog;

import android.os.Bundle;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * The class to be loaded by Xposed.
 */
public class PlayStoreChangelog implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!loadPackageParam.packageName.equals("com.android.vending"))
            return;

        /*
         * DetailsTextBlock is the block containing the "What's New" heading and the changelog.
         * The maximum number of lines of the changelog gets set to the number passed as a parameter to the 'bind' method.
         * This mod changes this parameter to the maximum integer value, so the changelog will always be fully shown.
         */
        Class<?> detailsTextBlockClass = XposedHelpers.findClass("com.google.android.finsky.layout.DetailsTextBlock", loadPackageParam.classLoader);
        XposedHelpers.findAndHookMethod(detailsTextBlockClass, "bind", CharSequence.class, CharSequence.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                // 0 = What's new text, 1 = changelog, 2 = maximum changelog lines
                param.args[2] = Integer.MAX_VALUE;
            }
        });

        /*
         * Hook MainActivity.onCreate() to go to the 'My Apps' fragment right after the activity is initialized.
         */
        Class<?> mainActivityClass = XposedHelpers.findClass("com.google.android.finsky.activities.MainActivity", loadPackageParam.classLoader);
        XposedHelpers.findAndHookMethod(mainActivityClass, "onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                // Call this.mNavigationManager.goToMyDownloads(FinskyApp.get().getToc())
                Class<?> finskyAppClass = XposedHelpers.findClass("com.google.android.finsky.FinskyApp", loadPackageParam.classLoader);
                Object finskyAppInstance = XposedHelpers.callStaticMethod(finskyAppClass, "get");
                Object dfeTocObj = XposedHelpers.callMethod(finskyAppInstance, "getToc");

                Object mNavigationManager = XposedHelpers.getObjectField(param.thisObject, "mNavigationManager");
                XposedHelpers.callMethod(mNavigationManager, "goToMyDownloads", dfeTocObj);
            }
        });
    }
}
