package com.yunfeng.aop;

import android.util.Log;

import com.yunfeng.Const;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * proxy
 * Created by xll on 2018/8/24.
 */
public class JDKDynamicProxy implements InvocationHandler {

    private Object target;


    public JDKDynamicProxy(Object target) {
        this.target = target;
    }

    public <T> T getProxy() {
        Object obj = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
        return (T) obj;
    }

//    final String name = "张五";
//    Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(PeopleService.class);
//    //目标对象拦截器，实现MethodInterceptor
//    //Object object为目标对象
//    //Method method为目标方法
//    //Object[] args 为参数，
//    //MethodProxy proxy CGlib方法代理对象
//    MethodInterceptor interceptor = new MethodInterceptor() {
//        @Override
//        public Object intercept(Object object, Object[] args, MethodProxy methodProxy) throws Throwable {
//            Object obj = null;
//            if(name.equals("张三")){
//                obj = methodProxy.invokeSuper(object, args); ;
//            }else{
//                System.out.println("----对不起，您没有权限----");
//            }
//            return obj;
//        }
//    };
//    //NoOp.INSTANCE：这个NoOp表示no operator，即什么操作也不做，代理类直接调用被代理的方法不进行拦截
//        enhancer.setCallbacks(new MethodInterceptor[]{interceptor,NoOp.INSTANCE});
//        enhancer.setCallbackFilter(new CallbackFilter() {
//        //过滤方法
//        //返回的值为数字，代表了Callback数组中的索引位置，要到用的Callback
//        @Override
//        public int accept(Method method) {
//            if(method.getName().equals("select")){
//                return 0;
//            }
//            return 1;
//        }
//    });
//    PeopleService ps = (PeopleService) enhancer.create();
//        ps.add();
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object obj = method.invoke(target, args);
        after();
        return obj;
    }

    private void after() {
        Log.d(Const.TAG, "invoke after");
    }

    private void before() {
        Log.d(Const.TAG, "invoke before");
    }
}
