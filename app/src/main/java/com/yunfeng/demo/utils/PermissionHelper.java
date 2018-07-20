/*
 * Copyright 2017 Google Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yunfeng.demo.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Helper to ask camera permission.
 */
public class PermissionHelper {
    private static final String[] APP_PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int REQ_PERMISSION_CODE = 0;

    private static PermissionListener mPermissionListener;

    /**
     * Check to see we have the necessary permissions for this app.
     */
    public static boolean hasPermission(Activity activity) {
        for (String permission : APP_PERMISSIONS) {
            boolean b = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
            if (!b) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check to see we have the necessary permissions for this app, and ask for them if we don't.
     */
    public static void requestPermissions(Activity activity, PermissionListener permissionListener) {
        mPermissionListener = permissionListener;
        ActivityCompat.requestPermissions(activity, APP_PERMISSIONS, REQ_PERMISSION_CODE);
    }

    /**
     * Check to see if we need to show the rationale for this permission.
     */
    public static boolean shouldShowRequestPermissionRationale(Activity activity) {
        for (String permision : APP_PERMISSIONS) {
            boolean b = ActivityCompat.shouldShowRequestPermissionRationale(activity, permision);
            if (b) {
                return true;
            }
        }
        return false;
    }

    /**
     * Launch Application Setting to grant permission.
     */
    public static void launchPermissionSettings(Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        activity.startActivity(intent);
    }

    public static void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQ_PERMISSION_CODE) {
            if (mPermissionListener != null) {
                mPermissionListener.callBack(permissions, grantResults);
            }
        }
    }
}
