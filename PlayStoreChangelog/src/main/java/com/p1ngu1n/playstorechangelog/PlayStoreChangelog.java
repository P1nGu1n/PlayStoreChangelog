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

import android.content.Context;
import android.content.pm.PackageInfo;
import android.view.ViewGroup;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * The class to be loaded by Xposed.
 */
public class PlayStoreChangelog implements IXposedHookLoadPackage {
    private static final String LOG_TAG = "PSC: ";
    // Preferences and their default values
    private XSharedPreferences prefs;
    public boolean showFullChangelog = true;
    public boolean debugging = false;

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!loadPackageParam.packageName.equals("com.android.vending"))
            return;

        prefs = new XSharedPreferences(BuildConfig.APPLICATION_ID);
        refreshPreferences();

        Object activityThread = XposedHelpers.callStaticMethod(XposedHelpers.findClass("android.app.ActivityThread", null), "currentActivityThread");
        Context context = (Context) XposedHelpers.callMethod(activityThread, "getSystemContext");
        PackageInfo piPlayStore = context.getPackageManager().getPackageInfo(loadPackageParam.packageName, 0);
        final int playStoreVersion = piPlayStore.versionCode;

        if (debugging) {
            XposedBridge.log(LOG_TAG + "Play Store Version: " + piPlayStore.versionName + " (" + piPlayStore.versionCode + ")");
            XposedBridge.log(LOG_TAG + "Module version: " + BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")");
        }

        final Obfuscator obfsc = new Obfuscator(playStoreVersion);

        /*
         * DetailsTextBlock is the block containing the "What's New" heading and the changelog.
         * The maximum number of lines of the changelog gets set to the number passed as a parameter to the 'bind' method.
         * This mod changes this parameter to the maximum integer value, so the changelog will always be fully shown.
         */
        Class<?> detailsTextBlockClass = XposedHelpers.findClass("com.google.android.finsky.layout.DetailsTextBlock", loadPackageParam.classLoader);
        XposedHelpers.findAndHookMethod(detailsTextBlockClass, obfsc.detailsTextBlockBind, CharSequence.class, CharSequence.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                refreshPreferences();
                if (showFullChangelog) {
                    // 0 = What's new text, 1 = changelog, 2 = maximum changelog lines
                    param.args[2] = Integer.MAX_VALUE;
                }
            }
        });
    }

    /**
     * Refresh the preferences.
     */
    private void refreshPreferences() {
        prefs.reload();
        showFullChangelog = prefs.getBoolean("pref_full_changelog", showFullChangelog);
        debugging = prefs.getBoolean("pref_debug", debugging);
    }
}
