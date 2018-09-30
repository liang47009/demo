package com.yunfeng.gui.render;

import com.yunfeng.gui.ui.IGeometry;

public interface IRenderer {
    boolean init();

    void sizeChanged(int width, int height);

    void drawFrame();

    void addView(IGeometry geometry, final float x, final float y);

    void onTouch(final float x, final float y);
}
