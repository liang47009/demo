package com.yunfeng.gui.render;

import android.content.Context;

public interface IProgramId {

    String POSITION = "position";
    String MATRIX = "matrix";
    String COLOR = "color";
    String TEXTURE = "texture";

    void init(Context context);

    int get(String key);

    void start();

    void end();

    int getType();
}
