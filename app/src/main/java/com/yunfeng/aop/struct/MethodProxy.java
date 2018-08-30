package com.yunfeng.aop.struct;

import java.lang.reflect.Method;

public class MethodProxy {

    private Class subClass;
    private String methodName;
    private Class<?>[] argsType;

    public MethodProxy(Class subClass, String methodName, Class<?>[] argsType) {
        this.subClass = subClass;
        this.methodName = methodName;
        this.argsType = argsType;
    }

    public String getMethodName() {
        return methodName;
    }

    public Method getOriginalMethod() {
        try {
            return subClass.getDeclaredMethod(methodName, argsType);
        } catch (NoSuchMethodException e) {
            throw new ProxyException(e.getMessage());
        }
    }

    public Method getProxyMethod() {
        try {
            return subClass.getDeclaredMethod(methodName + Const.SUBCLASS_INVOKE_SUPER_SUFFIX, argsType);
        } catch (NoSuchMethodException e) {
            throw new ProxyException(e.getMessage());
        }
    }

    public Object invokeSuper(Object object, Object[] argsValue) {
        return ((EnhancerInterface) object).executeSuperMethod$Enhancer$(methodName, argsType, argsValue);
    }

}
