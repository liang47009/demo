package com.yunfeng.demo.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunfeng.demo.IMyAidlInterface;
import com.yunfeng.demo.R;
import com.yunfeng.demo.data.Data;
import com.yunfeng.demo.service.AIDLService;

import static android.content.Context.BIND_AUTO_CREATE;

public class MeFragment extends MMFragment {
    private IMyAidlInterface aidl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_me, container, false);
        View view = rootView.findViewById(R.id.shadeView);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("app", "MeFragment shade view onclick");
                Data data = new Data();
                try {
                    aidl.send(data);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                aidl = IMyAidlInterface.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                aidl = null;
            }
        };
        Context context = this.getContext();
        if (context != null) {
            Intent intent1 = new Intent(this.getContext(), AIDLService.class);
            context.bindService(intent1, connection, BIND_AUTO_CREATE);
        }

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("app", "MeFragment has destory: " + this);
    }

    @Override
    void setPositionOffset(float positionOffset) {

    }
}

