package com.yunfeng.ddd.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunfeng.ddd.MainActivity;
import com.yunfeng.ddd.struct.FunctionManager;
import com.yunfeng.demo.R;

/**
 * empty
 * Created by xll on 2018/8/21.
 */
public class EmptyFragment extends BaseFragment {

    public static final String INTERFACE = MainActivity.class.getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ui_button, container, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FunctionManager.getInstance().invokeFunctionNoParamNoResult(INTERFACE);
            }
        });
        return view;
    }

}
