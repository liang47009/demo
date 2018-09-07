package com.yunfeng.sensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.yunfeng.common.ui.MyGLSurfaceView;

/**
 * native
 * <p>
 * Created by xll on 2018/8/22.
 */
public class MainActivity extends Activity implements SensorEventListener {

    static {
        System.loadLibrary("sensor");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("app", "nativecall button on click!");
                if (v.getTag().equals("test")) {

                }
            }
        };
        createButton(layout, "test", listener);
        MyGLSurfaceView view = new MyGLSurfaceView(this);
        layout.addView(view);
        setContentView(layout);
    }

    private void createButton(LinearLayout layout, String name, View.OnClickListener listener) {
        Button button = new Button(this);
        button.setTag(name);
        final LayoutParams paramsBtn = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(paramsBtn);
        button.setText(name);
        button.setOnClickListener(listener);
        layout.addView(button);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
