package com.yunfeng.demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.yunfeng.demo.IMyAidlInterface;
import com.yunfeng.demo.data.Data;

/**
 * service
 * Created by xll on 2018/8/2.
 */
public class AIDLService extends Service {

    private IBinder binder = new IMyAidlInterface.Stub() {
        @Override
        public void send(Data data) throws RemoteException {
            Log.d("app", data.toString());
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
