package com.yunfeng.gui.render;

import com.yunfeng.gui.ui.IGeometry;

public interface IRenderer {
    boolean init();

    void sizeChanged(int width, int height);

    void drawFrame();

    void addView(IGeometry geometry, float x, float y);
}
