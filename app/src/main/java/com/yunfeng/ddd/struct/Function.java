package com.yunfeng.ddd.struct;

/**
 * function
 * Created by xll on 2018/8/21.
 */
public abstract class Function {

    private String mFunctionName;

    public Function(String functionName) {
        this.mFunctionName = functionName;
    }

    public String getmFunctionName() {
        return mFunctionName;
    }

    public void setmFunctionName(String mFunctionName) {
        this.mFunctionName = mFunctionName;
    }

}
