package com.yunfeng.gui.render;

public interface IRenderer {
    boolean init();

    void sizeChanged(int width, int height);

    void drawFrame();
}
