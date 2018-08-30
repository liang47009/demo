package com.yunfeng.aop.struct;

public interface NoOp {

    public static final MethodInterceptor INSTANCE = new MethodInterceptor() {

        @Override
        public Object intercept(Object object, Object[] args, MethodProxy methodProxy) throws Exception {
            return methodProxy.invokeSuper(object, args);
        }
    };
    public static final MethodInterceptor INSTANCE_EMPTY = new MethodInterceptor() {

        @Override
        public Object intercept(Object object, Object[] args, MethodProxy methodProxy) throws Exception {
            //methodProxy.invokeSuper(object,args)
            return null;
        }
    };
}
