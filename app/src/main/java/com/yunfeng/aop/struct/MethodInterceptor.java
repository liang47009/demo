package com.yunfeng.aop.struct;

public interface MethodInterceptor {

    Object intercept(Object object, Object[] args, MethodProxy methodProxy) throws Exception;

}
