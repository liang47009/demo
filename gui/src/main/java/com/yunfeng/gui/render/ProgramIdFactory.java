package com.yunfeng.gui.render;

import android.util.Log;

import static com.yunfeng.gui.render.ProgramIdManager.SQUARE_PROGRAM;
import static com.yunfeng.gui.render.ProgramIdManager.TRIANGLE_PROGRAM;

/**
 * factory
 * Created by xll on 2018/9/18.
 */
public class ProgramIdFactory {

    public static IProgramId createProgrameId(int type) {
        IProgramId programId = null;
        switch (type) {
            case TRIANGLE_PROGRAM:
                programId = new TriangleProgramId();
                break;
            case SQUARE_PROGRAM:
                programId = new SquareProgramId();
                break;
            default:
                Log.e("app", "not exist type: " + type);
        }
        return programId;
    }
}
