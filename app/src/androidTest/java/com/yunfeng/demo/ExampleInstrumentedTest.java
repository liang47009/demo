package com.yunfeng.demo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.yunfeng.demo.data.Data;
import com.yunfeng.demo.service.AIDLService;
import com.yunfeng.demo.utils.ShareUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.content.Context.BIND_AUTO_CREATE;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.yunfeng.demo", appContext.getPackageName());
    }

    @Test
    public void testShareSend() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        ShareUtils.sendFileByOtherApp(appContext, "/sdcard/DCIM/a.png");
    }

    @Test
    public void testService() throws RemoteException, InterruptedException {
        final IMyAidlInterface[] aidl = {null};
        final Context appContext = InstrumentationRegistry.getTargetContext();
        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                aidl[0] = IMyAidlInterface.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                aidl[0] = null;
            }
        };
        Intent intent1 = new Intent(appContext, AIDLService.class);
        appContext.bindService(intent1, connection, BIND_AUTO_CREATE);

        Thread.sleep(2000);

        Data data = new Data();
        aidl[0].send(data);

    }
}
