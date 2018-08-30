package com.yunfeng.aop.struct;

public interface EnhancerInterface {

    public void setMethodInterceptor$Enhancer$(MethodInterceptor methodInterceptor);

    public Object executeSuperMethod$Enhancer$(String methodName, Class[] argsType, Object[] argsValue);

    public void setCallBacksMethod$Enhancer$(MethodInterceptor[] methodInterceptor);

    public void setCallBackFilterMethod$Enhancer$(CallbackFilter callbackFilter);

}
