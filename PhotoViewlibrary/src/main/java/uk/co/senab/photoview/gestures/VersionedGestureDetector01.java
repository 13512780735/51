package uk.co.senab.photoview.gestures;

/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

import android.content.Context;
import android.os.Build;
import android.view.GestureDetector;

public final class VersionedGestureDetector01 {

    public static CupcakeGestureDetector01 newInstance(Context context,
                                                       OnGestureListener01 listener) {
        final int sdkVersion = Build.VERSION.SDK_INT;
        CupcakeGestureDetector01 detector;

        if (sdkVersion < Build.VERSION_CODES.ECLAIR) {
            detector = new CupcakeGestureDetector01(context);
        } else if (sdkVersion < Build.VERSION_CODES.FROYO) {
            detector = new EclairGestureDetector01(context);
        } else {
            detector = new FroyoGestureDetector01(context);
        }

        detector.setOnGestureListener(listener);

        return detector;
    }

}