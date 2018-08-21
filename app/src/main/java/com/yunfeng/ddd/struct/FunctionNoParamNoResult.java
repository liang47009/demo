package com.yunfeng.ddd.struct;

/**
 * fun
 * Created by xll on 2018/8/21.
 */
public abstract class FunctionNoParamNoResult extends Function {

    public FunctionNoParamNoResult(String functionName) {
        super(functionName);
    }

    public abstract void function();
}
