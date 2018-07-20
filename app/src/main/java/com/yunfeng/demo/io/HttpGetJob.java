package com.yunfeng.demo.io;

import com.yunfeng.demo.utils.Okhttp3Utils;
import com.yunfeng.demo.utils.ThreadPool;

import java.io.IOException;

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
    public HttpResponse run(ThreadPool.JobContext jc) {
        try {
            String resp = Okhttp3Utils.getJsonContent(url);
            HttpGetResponse hgr = new HttpGetResponse();
            return hgr;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
