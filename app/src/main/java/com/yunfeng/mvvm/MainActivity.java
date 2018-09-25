package com.yunfeng.mvvm;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.os.Handler;

import com.yunfeng.demo.R;
import com.yunfeng.demo.databinding.ActivityMvvmBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * mvvm
 * Created by xll on 2018/9/25.
 */
public class MainActivity extends Activity {

    private ActivityMvvmBinding binding;
    private ObservableListAdapter<Student> adapter;
    private List<Student> students = new ObservableArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm);
        adapter = new ObservableListAdapter<>(this, students, R.layout.mvvm_adapter, 0, R.id.mvvm_tv);
        binding.listItem.setAdapter(adapter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                students.add(new Student(1, "sadf"));
                students.add(new Student(2, "sadf"));
            }
        }, 5000);
    }


}
