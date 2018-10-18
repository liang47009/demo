package com.yunfeng;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionRequest {
    private Activity activity;
    private String[] permissions;
    private int requestCode;
    private Callback callback;
    private int no;

    public PermissionRequest(Activity activity) {
        this.activity = activity;
    }

    public void startRequest(String[] permissions, int requestCode, Callback callback) {
        this.permissions = permissions;
        this.requestCode = requestCode;
        this.callback = callback;
        requesting();
    }

    private boolean checkPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this.activity, permission) != 0) {
                return false;
            }
        }
        return true;
    }

    private List<String> getDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            if ((ContextCompat.checkSelfPermission(this.activity, permission) != 0) || (ActivityCompat.shouldShowRequestPermissionRationale(this.activity, permission))) {
                needRequestPermissionList.add(permission);
            }
        }
        return needRequestPermissionList;
    }

    private void requesting() {
        if (checkPermissions(this.permissions)) {
            this.callback.onCallback(true);
        } else {
            List<String> needPermissions = getDeniedPermissions(this.permissions);
            ActivityCompat.requestPermissions(this.activity, (String[]) needPermissions.toArray(new String[needPermissions.size()]), this.requestCode);
        }
    }

    private boolean verifyPermissions(int[] grantResults) {
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != 0) {
                this.no = i;
                return false;
            }
        }
        return true;
    }

    public void onResponse(int requestCode, String[] permissions, int[] grantResults) {
        if (this.requestCode == requestCode) {
            if (verifyPermissions(grantResults)) {
                this.callback.onCallback(true);
            } else {
                openSettings(permissions[this.no]);
            }
        }
    }

    private void openSettings(String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this.activity, permission)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this.activity, android.R.style.Theme_Material_Light_Dialog_Alert);
            }
            String message = Res.getString("permission_text" + getStringName(permission));
            builder.setMessage(message);
            builder.setPositiveButton(Res.getString("permission_ok"), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    List<String> needPermissions = PermissionRequest.this.getDeniedPermissions(PermissionRequest.this.permissions);
                    ActivityCompat.requestPermissions(PermissionRequest.this.activity, (String[]) needPermissions.toArray(new String[needPermissions.size()]), PermissionRequest.this.requestCode);
                }
            });
            builder.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
            if (Build.VERSION.SDK_INT >= 21) {
                builder = new AlertDialog.Builder(this.activity, android.R.style.Theme_Material_Light_Dialog_Alert);
            }
            String message = String.format(Res.getString("permission_text" + getStringName(permission)), getApplicationName());
            builder.setPositiveButton(Res.getString("permission_setting"), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    PermissionRequest.this.callback.onCallback(false);
                    try {
                        Intent intent = new Intent();
                        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        intent.setData(Uri.parse("package:" + PermissionRequest.this.activity.getPackageName()));
                        PermissionRequest.this.activity.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            builder.setNegativeButton(Res.getString("permission_cancel"), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    PermissionRequest.this.callback.onCallback(false);
                }
            });
            builder.setMessage(message);
            builder.show();
        }
    }

    private String getStringName(String permission) {
        String name = "";
        switch (permission) {
            case "android.permission.WRITE_EXTERNAL_STORAGE":
                name = "_storage";
                break;
            case "android.permission.GET_ACCOUNTS":
                name = "_contacts";
                break;
            case "android.permission.READ_PHONE_STATE":
                name = "_phone";
                break;
            case "android.permission.RECORD_AUDIO":
                name = "_record";
                break;
        }
        return name;
    }

    private String getApplicationName() {
        try {
            PackageManager packageManager = this.activity.getApplicationContext().getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(this.activity.getPackageName(), 0);
            return (String) packageManager.getApplicationLabel(applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public interface Callback {
        void onCallback(boolean paramBoolean);
    }
}
