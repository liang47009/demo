package com.yunfeng.gui.ui;

import android.content.Context;

public interface IGeometry {
    boolean init(Context context);

    void draw();

    void sizeChanged(int width, int height);
}
