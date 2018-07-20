package com.yunfeng.demo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.yunfeng.demo.ui.SectionsPagerAdapter;
import com.yunfeng.demo.utils.Constants;
import com.yunfeng.demo.utils.GPSUtil;
import com.yunfeng.demo.utils.PermissionHelper;
import com.yunfeng.demo.utils.PermissionListener;

public class MainActivity extends AppCompatActivity {
    private String location = "0,0";

    //    private TextView mTextView;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    ImageView layout1;
    ImageView layout2;
    ImageView layout3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        // 初始化三个按钮
        layout1 = findViewById(R.id.id_tab_1);
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });
        layout2 = findViewById(R.id.id_tab_2);
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
            }
        });
        layout3 = findViewById(R.id.id_tab_3);
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(2);
            }
        });
        setCurrentItemPic(mViewPager.getCurrentItem());
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                setCurrentItemPic(position);
            }
        });
//        mTextView = findViewById(R.id.location_text);
        if (!PermissionHelper.hasPermission(this)) {
            PermissionHelper.requestPermissions(this, new PermissionListener() {
                @Override
                public void callBack(String[] permission, int[] granted) {
                    for (int i = 0; i < permission.length; i++) {
                        if (permission[i].equalsIgnoreCase(Manifest.permission.ACCESS_FINE_LOCATION) || permission[i].equalsIgnoreCase(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                            if (granted[i] == PackageManager.PERMISSION_GRANTED) {
                                location = GPSUtil.getLngAndLat(MainActivity.this);
                                MainActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
//                                        mTextView.setText(location);
                                    }
                                });
                            }
                        }
                    }
                }
            });
        } else {
            location = GPSUtil.getLngAndLat(MainActivity.this);
//            mTextView.setText(location);
        }
//120.627138,31.310937
    }

    private void setCurrentItemPic(int position) {
        switch (position) {
            case 0:
                resetImg();
                layout1.setImageResource(R.drawable.a6m);
                break;
            case 1:
                resetImg();
                layout2.setImageResource(R.drawable.a6q);
                break;
            case 2:
                resetImg();
                layout3.setImageResource(R.drawable.a6o);
            default:
                break;
        }
    }

    private void resetImg() {
        layout1.setImageResource(R.drawable.a6l);
        layout2.setImageResource(R.drawable.a6p);
        layout3.setImageResource(R.drawable.a6n);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    public void login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, Constants.REQ_ACTIVITY_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQ_ACTIVITY_LOGIN) {
            Log.d("Main", requestCode + "");
        }
    }

    public void toGaoDe(View view) {
        try {//             120.627138,31.310937
            //androidamap://viewMap?sourceApplication=appname&poiname=abc&lat=36.2&lon=116.1&dev=0
            String[] locations = location.split(",");
            double[] gps = GPSUtil.gps84_To_Gcj02(Double.valueOf(locations[1]), Double.valueOf(locations[0]));
            GPSUtil.geocoder(this, gps[1], gps[0]);
            String url = String.format("androidamap://viewReGeo?sourceApplication=appname&lat=%s&lon=%s&dev=0", gps[0], gps[1]);
            Intent intent = Intent.getIntent(url);
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            //"androidamap://route?sourceApplication=softname&sname=我的位置&dlat=" + LATITUDE_B + "&dlon=" + LONGTITUDE_B + "&dname=" + "东郡华城广场|A座" + "&dev=0&m=0&t=1"
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toBaiDu(View view) {
        try {//120.627138,31.310937
            //"baidumap://map/geocoder?src=openApiDemo&address=北京市海淀区上地信息路9号奎科科技大厦"
//            double[] location = Constants.gaoDeToBaidu(120.627138, 31.310937);
            String[] locations = location.split(",");
            double[] gps = GPSUtil.gps84_To_bd09(Double.valueOf(locations[1]), Double.valueOf(locations[0]));
            String url = String.format("baidumap://map/geocoder?location=%s,%s", gps[0], gps[1]);
            Intent intent = Intent.getIntent(url);
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void amClicked(View view) {
        Toast.makeText(this, "amClicked", Toast.LENGTH_LONG).show();
    }
}
