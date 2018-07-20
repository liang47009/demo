package com.yunfeng.demo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yunfeng.demo.R;
import com.yunfeng.demo.io.HttpGetJob;
import com.yunfeng.demo.io.HttpResponse;
import com.yunfeng.demo.utils.Future;
import com.yunfeng.demo.utils.ThreadPool;

public class EmptyFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_empty, container, false);
        Button order_view = rootView.findViewById(R.id.order);
        order_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://172.17.14.68:8081/nexus/content/groups/public/org/apache/httpcomponents/httpcore/4.2.5/httpcore-4.2.5.pom";
                Future<HttpResponse> ret = ThreadPool.getInstance().submit(new HttpGetJob(url));
                ret.get();
            }
        });
        return rootView;
    }
}

