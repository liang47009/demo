package com.yunfeng.sensor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.yunfeng.common.jni.JNILib;
import com.yunfeng.common.ui.MyGLSurfaceView;
import com.yunfeng.common.util.UiUtils;

/**
 * native
 * <p>
 * Created by xll on 2018/8/22.
 */
public class MainActivity extends Activity implements SensorEventListener {

    static {
        System.loadLibrary("sensor");
    }

    Sensor rotationVectorSensor;
    Sensor magneticSensor;
    Sensor accelerometerSensor;
    Sensor gyroscopeSensor;
    SensorManager sensorManager;

    // 将纳秒转化为秒
    private static final float NS2S = 1.0f / 1000000000.0f;
    private float timestamp;
    private float angle[] = new float[3];

    private long handler;

    public native static void nativeOnSensorChangedRotation(float x, float y, float z);

    public native static void nativeOnSensorChangedRotationMatrix(float[] rotationMatrix);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);

        MyGLSurfaceView view = new MyGLSurfaceView(this);
        layout.addView(view);
        setContentView(layout);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//注册陀螺仪传感器，并设定传感器向应用中输出的时间间隔类型是SensorManager.SENSOR_DELAY_GAME(20000微秒)
//SensorManager.SENSOR_DELAY_FASTEST(0微秒)：最快。最低延迟，一般不是特别敏感的处理不推荐使用，该模式可能在成手机电力大量消耗，由于传递的为原始数据，诉法不处理好会影响游戏逻辑和UI的性能
//SensorManager.SENSOR_DELAY_GAME(20000微秒)：游戏。游戏延迟，一般绝大多数的实时性较高的游戏都是用该级别
//SensorManager.SENSOR_DELAY_NORMAL(200000微秒):普通。标准延时，对于一般的益智类或EASY级别的游戏可以使用，但过低的采样率可能对一些赛车类游戏有跳帧现象
//SensorManager.SENSOR_DELAY_UI(60000微秒):用户界面。一般对于屏幕方向自动旋转使用，相对节省电能和逻辑处理，一般游戏开发中不使用
        if (null != sensorManager) {
            // 获取旋转矢量传感器
            rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            sensorManager.registerListener(this, rotationVectorSensor, SensorManager.SENSOR_DELAY_UI);
//            sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_UI);
//            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_UI);
//            sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        //TODO Auto-generated method stub
        super.onPause();
        if (null != sensorManager) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//// x,y,z分别存储坐标轴x,y,z上的加速度
//            float x = event.values[0];
//            float y = event.values[1];
//            float z = event.values[2];
//// 根据三个方向上的加速度值得到总的加速度值a
//            float a = (float) Math.sqrt(x * x + y * y + z * z);
//            System.out.println("a---------->" + a);
//// 传感器从外界采集数据的时间间隔为10000微秒
//            System.out.println("magneticSensor.getMinDelay()-------->" + magneticSensor.getMinDelay());
//// 加速度传感器的最大量程
//            System.out.println("event.sensor.getMaximumRange()-------->" + event.sensor.getMaximumRange());
//            Log.d("jarlen", "x------------->" + x);
//            Log.d("jarlen", "y------------>" + y);
//            Log.d("jarlen", "z----------->" + z);
//// showTextView.setText("x---------->" + x + "\ny-------------->" +
//// y + "\nz----------->" + z);
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
//// 三个坐标轴方向上的电磁强度，单位是微特拉斯(micro-Tesla)，用uT表示，也可以是高斯(Gauss),1Tesla=10000Gauss
//            float x = event.values[0];
//            float y = event.values[1];
//            float z = event.values[2];
//// 手机的磁场感应器从外部采集数据的时间间隔是10000微秒
//            System.out.println("magneticSensor.getMinDelay()-------->" + magneticSensor.getMinDelay());
//// 磁场感应器的最大量程
//            System.out.println("event.sensor.getMaximumRange()----------->" + event.sensor.getMaximumRange());
//            System.out.println("x------------->" + x);
//            System.out.println("y------------->" + y);
//            System.out.println("z------------->" + z);
////
//// Log.d("TAG","x------------->" + x);
//// Log.d("TAG", "y------------>" + y);
//// Log.d("TAG", "z----------->" + z);
////
//// showTextView.setText("x---------->" + x + "\ny-------------->" +
//// y + "\nz----------->" + z);
        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
////从 x、y、z 轴的正向位置观看处于原始方位的设备，如果设备逆时针旋转，将会收到正值；否则，为负值
//            if (timestamp != 0) {
//// 得到两次检测到手机旋转的时间差（纳秒），并将其转化为秒
//                final float dT = (event.timestamp - timestamp) * NS2S;
//// 将手机在各个轴上的旋转角度相加，即可得到当前位置相对于初始位置的旋转弧度
//                angle[0] += event.values[0] * dT;
//                angle[1] += event.values[1] * dT;
//                angle[2] += event.values[2] * dT;
//// 将弧度转化为角度
//                float anglex = (float) Math.toDegrees(angle[0]);
//                float angley = (float) Math.toDegrees(angle[1]);
//                float anglez = (float) Math.toDegrees(angle[2]);
//                System.out.println("anglex------------>" + anglex);
//                System.out.println("angley------------>" + angley);
//                System.out.println("anglez------------>" + anglez);
//                System.out.println("gyroscopeSensor.getMinDelay()----------->" + gyroscopeSensor.getMinDelay());
//            }
////将当前时间赋值给timestamp
//            timestamp = event.timestamp;
        } else if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            float[] rotationMatrix = new float[16];
            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
            // Remap coordinate system
            float[] remappedRotationMatrix = new float[16];
            SensorManager.remapCoordinateSystem(rotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Z, remappedRotationMatrix);

            // Convert to orientations
            float[] orientations = new float[3];
            SensorManager.getOrientation(remappedRotationMatrix, orientations);
            for (int i = 0; i < 3; i++) {
                orientations[i] = (float) (Math.toDegrees(orientations[i]));
            }
            if (orientations[1] > 45) {
                getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
            } else if (orientations[1] < -45) {
                getWindow().getDecorView().setBackgroundColor(Color.BLUE);
            } else if (Math.abs(orientations[1]) < 10) {
                getWindow().getDecorView().setBackgroundColor(Color.WHITE);
            }
            nativeOnSensorChangedRotation(orientations[0], orientations[1], orientations[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
