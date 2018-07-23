package com.yunfeng.demo.io;

import android.util.Log;

import com.yunfeng.demo.utils.OkHttpUtils;
import com.yunfeng.demo.utils.ThreadPool;


/**
 * ok3 get
 * Created by xll on 2018/7/20.
 */
public class HttpGetJob implements HttpJob {

    private String url;

    public HttpGetJob(String url) {
        this.url = url;
    }

    @Override
    public HttpResponse run(ThreadPool.JobContext jc) throws Exception {
        String resp = OkHttpUtils.getJsonContent(url);
        Log.e("demo", "resp:" + resp);
        HttpResponse hgr = new HttpGetResponse();
        hgr.setResult(resp);
        return hgr;
    }
}
