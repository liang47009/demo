package com.yunfeng.nativefork;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

/**
 * native
 * <p>
 * Created by xll on 2018/8/22.
 */
public class MainActivity extends Activity {

    static {
        System.loadLibrary("app");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout layout = new FrameLayout(this);
        final LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);

        Button button = new Button(this);
        final LayoutParams paramsBtn = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(paramsBtn);
        button.setText("NativeFork");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("app", "native fork on click!");
                fork();
            }
        });

        layout.addView(button);
        setContentView(layout);
        fork();
    }

    private static native void fork();
}
