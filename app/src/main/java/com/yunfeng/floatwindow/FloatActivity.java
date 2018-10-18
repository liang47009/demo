package com.yunfeng.floatwindow;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.yunfeng.MyContext;

/**
 * float
 * Created by xll on 2018/10/10.
 */
public class FloatActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(this, FloatService.class);
                Toast.makeText(this, "已开启Toucher", Toast.LENGTH_SHORT).show();
                startService(intent);
            } else {
                //若没有权限，提示获取.
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                Toast.makeText(this, "需要取得权限以使用悬浮窗", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        } else { //SDK在23以下，不用管.
            Intent intent = new Intent(this, FloatService.class);
            startService(intent);
        }

    }

}
