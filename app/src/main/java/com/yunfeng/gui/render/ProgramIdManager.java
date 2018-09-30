package com.yunfeng.gui.render;

import java.util.HashMap;
import java.util.Map;

/**
 * programid
 * Created by xll on 2018/9/18.
 */
public class ProgramIdManager {

    public static final int TRIANGLE_PROGRAM = 1;
    public static final int SQUARE_PROGRAM = 2;

    private static final ProgramIdManager instance = new ProgramIdManager();
    private Map<Integer, IProgramId> programeIds = new HashMap<>(8);

    private ProgramIdManager() {
    }

    public static ProgramIdManager getInstance() {
        return instance;
    }

    public void removeProgrameId(int type) {
        programeIds.remove(type);
    }

    public IProgramId getProgrameId(int type) {
        IProgramId pro = programeIds.get(type);
        if (null == pro) {
            pro = ProgramIdFactory.createProgrameId(type);
            programeIds.put(type, pro);
        }
        return pro;
    }
}
