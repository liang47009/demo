package com.yunfeng.demo.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yunfeng.demo.R;
import com.yunfeng.demo.io.HttpGetResponse;
import com.yunfeng.demo.io.HttpResponse;
import com.yunfeng.demo.io.IHttpRequestListener;
import com.yunfeng.demo.utils.Future;
import com.yunfeng.demo.utils.OkHttpUtils;

import java.lang.ref.WeakReference;

public class EmptyFragment extends MMFragment {

    private static final int HTTP_RET = 1;

    private final HttpHandler m_handler = new HttpHandler(this);

    @Override
    void setPositionOffset(float positionOffset) {

    }

    private static class HttpHandler extends Handler {
        WeakReference<EmptyFragment> w_emptyFragment;

        HttpHandler(EmptyFragment emptyFragment) {
            w_emptyFragment = new WeakReference<>(emptyFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            final EmptyFragment emptyFragment = w_emptyFragment.get();
            if (null != emptyFragment) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                switch (msg.what) {
                    case HTTP_RET:
                        emptyFragment.httpRet(bundle);
                        break;
                }
            }
        }
    }

    private void httpRet(Bundle bundle) {
        String str = bundle.getString("ret");
        pb.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);
    }

    private View pb;
    private View content;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_empty, container, false);
        Button order_view = rootView.findViewById(R.id.order);
        pb = rootView.findViewById(R.id.progressBar);
        pb.setVisibility(View.GONE);
        content = rootView.findViewById(R.id.contentPanel);
        order_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content.setVisibility(View.GONE);
                String url = "https://www.google.com.hk";
                OkHttpUtils.createGetRequest(url, new HttpRequestListener());
                pb.setVisibility(View.VISIBLE);
            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("app", "emptyfragement has destory: " + this);
    }

    private final class HttpRequestListener implements IHttpRequestListener {
        @Override
        public void onFutureDone(Future<HttpResponse> future) {
            HttpGetResponse resp = (HttpGetResponse) future.get();
            String ret = "";
            if (null != resp) {
                ret = resp.getResult();
            }
            Message msg = Message.obtain(m_handler);
            msg.getData().putString("ret", ret);
            msg.what = HTTP_RET;
            msg.sendToTarget();
        }
    }
}

