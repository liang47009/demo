package com.yunfeng.demo.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunfeng.demo.R;

public class MeFragment extends MMFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_me, container, false);
        View view = rootView.findViewById(R.id.shadeView);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("app", "MeFragment shade view onclick");
            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("app", "MeFragment has destory: " + this);
    }

    @Override
    void setPositionOffset(float positionOffset) {

    }
}

