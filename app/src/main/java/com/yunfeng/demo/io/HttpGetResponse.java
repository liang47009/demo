package com.yunfeng.demo.io;

/**
 * get resp
 * Created by xll on 2018/7/20.
 */
public class HttpGetResponse implements HttpResponse {

    private String result;

    @Override
    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

}
