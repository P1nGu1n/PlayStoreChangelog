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

    // com.google.android.finsky.layout.DetailsTextBlock > method bind(CharSequence, CharSequence, int)
    public String detailsTextBlockBind;

    public Obfuscator(int version) {
        // init general names
        if (version >= V6_2_10) {
            detailsTextBlockBind = "a";
        } else {
            detailsTextBlockBind = "bind";
        }
    }
}
