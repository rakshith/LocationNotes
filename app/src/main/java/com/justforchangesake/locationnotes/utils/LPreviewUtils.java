/*
 * ******************************************************************************
 *   Copyright 2014 Google Inc. All rights reserved.
 *   Copyright (c) 2013-2014 Gabriele Mariotti.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  *****************************************************************************
 */

package com.justforchangesake.locationnotes.utils;

import android.support.v7.app.ActionBarActivity;

/**
 * An abstract interface to use SDK-L and SDK-19
 */
public class LPreviewUtils {

    private LPreviewUtils() {
    }

    public static LPreviewUtilsBase getInstance(ActionBarActivity activity) {
            return new LPreviewUtilsBase(activity);
    }
}
