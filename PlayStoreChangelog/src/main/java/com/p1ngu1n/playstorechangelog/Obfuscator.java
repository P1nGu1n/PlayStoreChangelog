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

/**
 * This helps with the obfuscation introduced in Google Play v6.2.10
 */
public class Obfuscator {
    public static final int V6_2_10 = 80621000;
    public static final int V6_3_13_B = 80631300;
    public static final int V6_7_07_E = 80670700;
    public static final int V6_8_20_F = 80682000;

    // com.google.android.finsky.layout.DetailsTextBlock > method bind(CharSequence, CharSequence, int)
    public String detailsTextBlockBind;
    // com.google.android.finsky.activities.MainActivity > method handleIntent()
    public String mainActHandleIntent;
    // com.google.android.finsky.activities.MainActivity > field mNavigationManager (NavigationManager)
    public String mainActNavManager;
    // com.google.android.finsky.navigationmanager.NavigationManager > class
    public String navManager;
    // com.google.android.finsky.navigationmanager.NavigationManager > method isBackStackEmpty()
    public String navManagerIsBackStackEmpty;
    // com.google.android.finsky.navigationmanager.NavigationManager > method goToAggregatedHome(DfeToc)
    public String navManagerGoToAggregatedHome;
    // com.google.android.finsky.navigationmanager.NavigationManager > method goToMyDownloads(DfteToc, bool)
    public String navManagerGoToMyDownloads;
    // com.google.android.finsky.b.n > class (some kind of analytics/logging)
    public String analytics;

    // com.google.android.finsky.activities.myapps.MyAppsTabbedFragment > class
    public String myAppsTabbedFragment;
    // com.google.android.finsky.activities.myapps.MyAppsInstalledTab > class
    public String myAppsInstalledTab;
    // com.google.android.finsky.activities.myapps.MyAppsTabbedAdapter > class
    public String myAppsTabbedAdapter;
    // com.google.android.finsky.activities.myapps.MyAppsTabbedAdapter > method instantiateItem(ViewGroup, int)
    public String myAppsTabbedAdapterInstItem;
    // com.google.android.finsky.activities.myapps.MyAppsTabbedAdapter > field mFragment (MyAppsTabbedFragment)
    public String myAppsTabbedAdapterFragment;
    // com.google.android.finsky.activities.myapps.MyAppsTab > method loadData()
    public String myAppsTabLoadData;
    // android.support.v4.app.Fragment > method onStart()
    public String fragmentOnStart;

    public Obfuscator(int version) {
        // init general names
        if (version >= V6_2_10) {
            detailsTextBlockBind = "a";
            mainActHandleIntent = "B";
            mainActNavManager = "o";
            navManager = "com.google.android.finsky.navigationmanager.b";
            navManagerIsBackStackEmpty = "e";
            navManagerGoToAggregatedHome = "a";
            navManagerGoToMyDownloads = "a";

            if (version >= V6_3_13_B) {
                mainActNavManager = "q";
            }
            if (version >= V6_7_07_E) {
                mainActHandleIntent = "J";
            }
            if (version >= V6_8_20_F) {
                mainActHandleIntent = "M";
                analytics = "com.google.android.finsky.b.n";
            }
        } else {
            detailsTextBlockBind = "bind";
            mainActHandleIntent = "handleIntent";
            mainActNavManager = "mNavigationManager";
            navManager = "com.google.android.finsky.navigationmanager.NavigationManager";
            navManagerIsBackStackEmpty = "isBackStackEmpty";
            navManagerGoToAggregatedHome = "goToAggregatedHome";
            navManagerGoToMyDownloads = "goToMyDownloads";
        }

        // init auto-refresh names
        if (version >= V6_7_07_E) {
            myAppsTabbedFragment = "com.google.android.finsky.activities.myapps.ag";
            myAppsInstalledTab = "com.google.android.finsky.activities.myapps.p";
            myAppsTabbedAdapter = "com.google.android.finsky.activities.myapps.ae";
            myAppsTabbedAdapterInstItem = "a";
            myAppsTabbedAdapterFragment = "l";
            myAppsTabLoadData = "k"; // com.google.android.finsky.activities.myapps.ac
            fragmentOnStart = "g_";
            if (version >= V6_8_20_F) {
                myAppsTabLoadData = "j";
            }
        } else if (version >= V6_3_13_B) {
            myAppsTabbedFragment = "com.google.android.finsky.activities.myapps.ae";
            myAppsInstalledTab = "com.google.android.finsky.activities.myapps.n";
            myAppsTabbedAdapter = "com.google.android.finsky.activities.myapps.ac";
            myAppsTabbedAdapterInstItem = "a";
            myAppsTabbedAdapterFragment = "k";
            myAppsTabLoadData = "k"; // com.google.android.finsky.activities.myapps.aa
            fragmentOnStart = "g_";
        } else if (version >= V6_2_10) {
            myAppsTabbedFragment = "com.google.android.finsky.activities.myapps.ac";
            myAppsInstalledTab = "com.google.android.finsky.activities.myapps.l";
            myAppsTabbedAdapter = "com.google.android.finsky.activities.myapps.aa";
            myAppsTabbedAdapterInstItem = "a";
            myAppsTabbedAdapterFragment = "j";
            myAppsTabLoadData = "k"; // com.google.android.finsky.activities.myapps.y
            fragmentOnStart = "g_";
        } else {
            myAppsTabbedFragment = "com.google.android.finsky.activities.myapps.MyAppsTabbedFragment";
            myAppsInstalledTab = "com.google.android.finsky.activities.myapps.MyAppsInstalledTab";
            myAppsTabbedAdapter = "com.google.android.finsky.activities.myapps.MyAppsTabbedAdapter";
            myAppsTabbedAdapterInstItem = "instantiateItem";
            myAppsTabbedAdapterFragment = "mFragment";
            myAppsTabLoadData = "loadData";
            fragmentOnStart = "onStart";
        }
    }
}
