package com.yunfeng;

public class Res {

    public static final int getLayoutId(String name) {
        return ResUtil.getLayoutId(getPrefix() + name);
    }

    public static final int getConfigLayoutId(String name) {
        String prefix = getPrefix() + "test" + "_";
        return ResUtil.getLayoutId(prefix + name);
    }

    public static final int getViewId(String name) {
        return ResUtil.getViewId(getPrefix() + name);
    }

    public static final int getDrawableId(String name) {
        return ResUtil.getDrawableId(getPrefix() + name);
    }

    public static final int getStringId(String name) {
        String prefix = getPrefix();
        return ResUtil.getStringId(prefix + name);
    }

    public static final String getString(String name) {
        String prefix = getPrefix();
        return ResUtil.getString(prefix + name);
    }

    public static final String getConfigString(String name) {
        String prefix = getPrefix() + "test_";
        return ResUtil.getString(prefix + name);
    }

    public static final String getPrefix() {
        return "demo_";
    }
}
