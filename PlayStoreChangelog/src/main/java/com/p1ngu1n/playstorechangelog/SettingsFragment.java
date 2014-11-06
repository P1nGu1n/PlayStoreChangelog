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

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

/**
 * Fragment container the preferences.
 */
public class SettingsFragment extends PreferenceFragment {

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesMode(Context.MODE_WORLD_READABLE);
        addPreferencesFromResource(R.xml.settings);

        // Set the version number in the about screen
        findPreference("pref_about").setTitle(getString(R.string.pref_about_title, BuildConfig.VERSION_NAME));
        // Set change listener to the 'show in launcher' preference
        findPreference("pref_launcher").setOnPreferenceChangeListener(changeListenerLauncher);
    }

    /**
     * Hides or shows the icon in the launcher when the preference changed.
     */
    private final Preference.OnPreferenceChangeListener changeListenerLauncher = new Preference.OnPreferenceChangeListener() {
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            int componentState = ((Boolean) newValue ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED);

            Activity activity = getActivity();
            ComponentName alias = new ComponentName(activity, "com.p1ngu1n.playstorechangelog.SettingsActivity-Alias");
            activity.getPackageManager().setComponentEnabledSetting(alias, componentState, PackageManager.DONT_KILL_APP);
            return true;
        }
    };
}
