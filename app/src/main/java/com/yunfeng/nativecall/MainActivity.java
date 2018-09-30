package com.yunfeng.nativecall;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.yunfeng.Const;
import com.yunfeng.common.ui.MyGLSurfaceView;

/**
 * native
 * <p>
 * Created by xll on 2018/8/22.
 */
public class MainActivity extends Activity {

    static {
        System.loadLibrary("nativecall");
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
                Log.d(Const.TAG, "nativecall button on click!");
                if (v.getTag().equals("test")) {
                    test();
                }
            }
        };
        createButton(layout, "test", listener);
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

    private static native void test();

}
