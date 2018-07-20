package com.yunfeng.demo.io;

/**
 * get resp
 * Created by xll on 2018/7/20.
 */
public class HttpGetResponse implements HttpResponse {

    private Object result;

    @Override
    public void setResult(Object result) {
        this.result = result;
    }
}
