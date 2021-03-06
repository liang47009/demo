package com.yunfeng;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.yunfeng.demo.R;
import com.yunfeng.floatwindow.FloatView;

import java.util.ArrayList;
import java.util.List;

/**
 * main
 * Created by xll on 2018/9/30.
 */
public class MainActivity extends AppCompatActivity {

    private FloatView floatView;
    private PermissionRequest permissionRequest;

    private List<Class<? extends Activity>> activitys = new ArrayList<>();

    private String[] permissions = {Manifest.permission.INTERNET, Manifest.permission.GET_ACCOUNTS, Manifest.permission.READ_CONTACTS,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SYSTEM_ALERT_WINDOW,};

    private int PERMISSION_REQUESE_CODE = 1100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyContext.getInstance().setContext(this);
        initData();
        setContentView(R.layout.activity_list);

        permissionRequest = new PermissionRequest(this);
        permissionRequest.startRequest(permissions, PERMISSION_REQUESE_CODE, new PermissionRequest.Callback() {
            @Override
            public void onCallback(boolean paramBoolean) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ActivityListAdapter adapter = new ActivityListAdapter(MainActivity.this.getApplicationContext());
                        ListView listView = findViewById(R.id.activitys_list);
                        adapter.setData(activitys);
                        listView.setAdapter(adapter);

                        //通过findViewById拿到RecyclerView实例
                        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
                        //设置RecyclerView管理器
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
                        mRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));
                        //初始化适配器
                        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter();
                        mAdapter.setData(activitys);
                        //设置添加或删除item时的动画，这里使用默认动画
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        //设置适配器
                        mRecyclerView.setAdapter(mAdapter);

                        LayoutInflater.Factory2 factory2 = MainActivity.this.getLayoutInflater().getFactory2();
                        if (null == factory2) {
                            MainActivity.this.getLayoutInflater().setFactory2(new SkinFactoryTwo());
                        }
                        showFloatView();
                    }
                });
            }
        });

    }

    private void initData() {
        activitys.add(com.yunfeng.demo.MainActivity.class);
        activitys.add(com.yunfeng.ddd.MainActivity.class);
        activitys.add(com.yunfeng.aop.MainActivity.class);
        activitys.add(com.yunfeng.mvp.MainActivity.class);
        activitys.add(com.yunfeng.mvvm.MainActivity.class);
        activitys.add(com.yunfeng.nativecall.MainActivity.class);
        activitys.add(com.yunfeng.nativefork.MainActivity.class);
        activitys.add(com.yunfeng.retrofit2.MainActivity.class);
        activitys.add(com.yunfeng.mvc.BookActivity.class);
        activitys.add(com.yunfeng.sensor.MainActivity.class);
        activitys.add(com.yunfeng.gui.GuiMainActivity.class);
        activitys.add(com.yunfeng.guinative.GuiNativeMainActivity.class);
        activitys.add(com.yunfeng.opensles.MainActivity.class);
        activitys.add(com.yunfeng.vulkan.VulkanActivity.class);
        activitys.add(com.yunfeng.floatwindow.FloatActivity.class);
        activitys.add(com.yunfeng.fmod.FmodActivity.class);
        activitys.add(com.yunfeng.twowaysample.ui.MainActivity.class);
        activitys.add(com.yunfeng.rxcpp.MainActivity.class);
    }

    @Override
    protected void onDestroy() {
        destroyFloatView();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionRequest.onResponse(requestCode, permissions, grantResults);
    }

    @Override
    public AppCompatDelegate getDelegate() {
        return super.getDelegate();
    }

    public void showFloatView() {
        if (floatView == null) {
            floatView = new FloatView(this);
        }
        floatView.setVisibility(View.VISIBLE);
    }

    public void dismissFloatView() {
        if (floatView != null) {
            floatView.setVisibility(View.GONE);
        }
    }

    public void destroyFloatView() {
        if (floatView != null) {
            floatView.destroy();
            floatView = null;
        }
    }
}
