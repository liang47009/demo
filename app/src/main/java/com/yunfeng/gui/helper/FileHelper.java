package com.yunfeng.gui.helper;

import android.content.Context;

import java.io.DataInputStream;
import java.io.InputStream;

public class FileHelper {
    public static String readShaderFromStream(Context context, String fileName) {
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            DataInputStream dis = new DataInputStream(in);
            byte buf[] = new byte[dis.available()];
            StringBuilder sb = new StringBuilder();
            while (dis.read(buf) != -1) {
                sb.append(new String(buf));
            }
            dis.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
