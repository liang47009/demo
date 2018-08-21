package com.yunfeng.ddd;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.yunfeng.ddd.struct.FunctionManager;
import com.yunfeng.ddd.struct.FunctionNoParamNoResult;
import com.yunfeng.ddd.ui.EmptyFragment;
import com.yunfeng.demo.R;

/**
 * main
 * Created by xll on 2018/8/21.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ddd);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        EmptyFragment ef = new EmptyFragment();
        ft.replace(R.id.fl_content, ef);
        ft.commit();

        FunctionManager.getInstance().register(new FunctionNoParamNoResult(EmptyFragment.INTERFACE) {
            @Override
            public void function() {
                Toast.makeText(MainActivity.this, "interface", Toast.LENGTH_LONG).show();
            }
        });
    }

}
