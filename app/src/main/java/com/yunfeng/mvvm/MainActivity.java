package com.yunfeng.mvvm;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import com.yunfeng.DividerItemDecoration;
import com.yunfeng.demo.R;
import com.yunfeng.demo.databinding.ActivityMvvmBinding;

/**
 * mvvm
 * Created by xll on 2018/9/25.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMvvmBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm);

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        final StudentAdapter adapter = new StudentAdapter();
        binding.recyclerView.setAdapter(adapter);

        new Handler(this.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.changeValue();
            }
        }, 2000);

    }


}
