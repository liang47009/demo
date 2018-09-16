package com.yunfeng.demo.ui;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yunfeng.demo.R;
import com.yunfeng.demo.io.HttpGetJob;
import com.yunfeng.demo.io.HttpResponse;
import com.yunfeng.demo.utils.Future;
import com.yunfeng.demo.utils.ThreadPool;

public class DummySectionFragment extends MMFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_dummy, container, false);
        final Button view = (Button) rootView.findViewById(R.id.login_btn);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.animate().translationX(500).translationY(500).setInterpolator(new TimeInterpolator() {
                    @Override
                    public float getInterpolation(float input) {
                        return input;
                    }
                }).setDuration(5000);
            }
        }, 2000);

        final Button toGaodeBtn = (Button) rootView.findViewById(R.id.btn_to_gaode);
        toGaodeBtn.postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(toGaodeBtn, "translationX", 200).setDuration(5000);
                objectAnimator.start();
            }
        }, 2000);
        return rootView;
    }

    @Override
    void setPositionOffset(float positionOffset) {

    }
}

