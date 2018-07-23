package com.yunfeng.demo.utils;

import android.util.Log;

import com.yunfeng.demo.io.HttpGetJob;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * okhttps
 * Created by xll on 2018/7/20.
 */
public class OkHttpUtils {
    /**
     * 通过okhttp的get请求方式获取网络数据
     */
    public static String getJsonContent(String url) throws IOException {
//        1.导包  复制jar包---》黏贴到libs文件下---》选中jar包，鼠标右键---》选择Add As Library --》选择ok
//        2.创建客户端对象
        OkHttpClient client = new OkHttpClient();
//        3.设置发起请求的对象
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(url).build();
//        4.客户端发起请求，获取响应对象
        Response response = client.newCall(request).execute();
//        5.通过响应对象，判断请求是否成功
        if (response.isSuccessful()) {
//            6.获得响应体内容
            ResponseBody body = response.body();
            if (null == body) {
                return null;
            }
//            7.获取对应的数据
            return body.string();
        }
        return null;
    }

    /**
     * 通过okhttp的post请求方式获取网络数据
     **/
    public static String postJsonContent(String path, Map<String, String> map) throws IOException {
//        1.导包
//        2.创建客户端对象
        OkHttpClient client = new OkHttpClient();
//        获取提交的请求参数
        FormBody.Builder builder = new FormBody.Builder();
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            String value = map.get(key);
            builder.add(key, value);   //向请求体当中添加键值对的过程
        }
        RequestBody body = builder.build();
//        3.创建请求对象
        Request request = new Request.Builder().post(body).url(path).build();
//        4.发起请求，获得响应对象
        Response response = client.newCall(request).execute();
//            5.判断请求是否成功
        if (response.isSuccessful()) {
//                6.获得请求体内容
            ResponseBody rspbody = response.body();
            if (null == rspbody) {
                return null;
            }
            return rspbody.string();
        }
        return null;
    }

    public static void createGetRequest(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Future ret = ThreadPool.getInstance().submit(new HttpGetJob(url));
                Object obj = ret.get();
                Log.e("demo", "ret: " + obj);
            }
        }).start();
    }
}
