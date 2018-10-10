package com.yunfeng;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.widget.ListView;
import android.widget.Toast;

import com.yunfeng.demo.R;

/**
 * main
 * Created by xll on 2018/9/30.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ActivityListAdapter adapter = new ActivityListAdapter(this.getApplicationContext());
        ListView list = findViewById(R.id.activitys_list);

        adapter.addActivity(com.yunfeng.demo.MainActivity.class);
        adapter.addActivity(com.yunfeng.ddd.MainActivity.class);
        adapter.addActivity(com.yunfeng.aop.MainActivity.class);
        adapter.addActivity(com.yunfeng.mvp.MainActivity.class);
        adapter.addActivity(com.yunfeng.mvvm.MainActivity.class);
        adapter.addActivity(com.yunfeng.nativecall.MainActivity.class);
        adapter.addActivity(com.yunfeng.nativefork.MainActivity.class);
        adapter.addActivity(com.yunfeng.retrofit2.MainActivity.class);
        adapter.addActivity(com.yunfeng.mvc.BookActivity.class);
        adapter.addActivity(com.yunfeng.sensor.MainActivity.class);
        adapter.addActivity(com.yunfeng.gui.GuiMainActivity.class);
        adapter.addActivity(com.yunfeng.guinative.GuiNativeMainActivity.class);
        adapter.addActivity(com.yunfeng.opensles.MainActivity.class);
        adapter.addActivity(com.yunfeng.vulkan.VulkanActivity.class);

        list.setAdapter(adapter);
        LayoutInflaterCompat.setFactory2(this.getLayoutInflater(), new SkinFactoryTwo());

        if (Build.VERSION.SDK_INT >= 23) {
//            if (Settings.canDrawOverlays(MainActivity.this)) {
            Intent intent = new Intent(MainActivity.this, FloatService.class);
//            Toast.makeText(MainActivity.this, "已开启Toucher", Toast.LENGTH_SHORT).show();
            startService(intent);
//            } else {
//                //若没有权限，提示获取.
//                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
//                Toast.makeText(MainActivity.this, "需要取得权限以使用悬浮窗", Toast.LENGTH_SHORT).show();
//                startActivity(intent);
//            }
        } else { //SDK在23以下，不用管.
            Intent intent = new Intent(MainActivity.this, FloatService.class);
            startService(intent);
        }

    }

}
