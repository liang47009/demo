package com.yunfeng.rxcpp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * rxcpp
 * Created by xll on 2018/10/17.
 */
public class MainActivity extends Activity {

    static {
        System.loadLibrary("RxCpp");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nativeRxCpp(this.getExternalCacheDir() + "/log.txt");
    }

    /**
     * rxcpp sample
     *
     * @param str external cache path log.txt
     */
    private native static void nativeRxCpp(String str);
}
