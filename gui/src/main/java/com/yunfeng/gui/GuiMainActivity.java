package com.yunfeng.gui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.yunfeng.gui.view.MyGLSurfaceView;

/**
 * main
 * Created by xll on 2018/9/11.
 */
public class GuiMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);

        MyGLSurfaceView view = new MyGLSurfaceView(this);

        layout.addView(view);
        setContentView(layout);
    }

}
