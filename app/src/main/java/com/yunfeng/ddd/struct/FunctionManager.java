package com.yunfeng.ddd.struct;

import java.util.HashMap;
import java.util.Map;

/**
 * fun
 * Created by xll on 2018/8/21.
 */
public class FunctionManager {
    private final Map<String, FunctionNoParamNoResult> mMapFunNoParamNoRet = new HashMap<>();

    private static final FunctionManager instance = new FunctionManager();

    private FunctionManager() {
    }

    public static FunctionManager getInstance() {
        return instance;
    }

    public FunctionManager register(FunctionNoParamNoResult functionNoParamNoResult) {
        mMapFunNoParamNoRet.put(functionNoParamNoResult.getmFunctionName(), functionNoParamNoResult);
        return this;
    }

    public void invokeFunctionNoParamNoResult(String functionName) {
        FunctionNoParamNoResult functionNoParamNoResult = mMapFunNoParamNoRet.get(functionName);
        if (functionNoParamNoResult != null) {
            functionNoParamNoResult.function();
        }
    }

}
