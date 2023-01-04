package com.javaflappybird;

import com.almasb.fxgl.entity.Entity;
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.spawn;

/**
 * @author hdnguyen7702
 * */

public class FloorControl {
    private final Entity floor01;
    private final Entity floor02;
    private final Entity floor03;

    private static final double width = 336f;
    private static final double height = 112f;

    private double xFloor;

    public FloorControl(){
        int zIndex = 10; // cao hơn pipe ( pipe= 5 )
        xFloor = 0;

        floor01 = spawn("floor",0f,getAppHeight() - height);
        floor02 = spawn("floor",width,getAppHeight() - height);
        floor03 = spawn("floor",width * 2, getAppHeight() - height);

        floor01.getViewComponent().setZIndex(zIndex);
        floor02.getViewComponent().setZIndex(zIndex);
        floor03.getViewComponent().setZIndex(zIndex);
    }

    public void onUpdate(){  // gọi hàm này trong update App
        floor01.setX(xFloor);
        floor02.setX(xFloor + width);
        floor03.setX(xFloor + 2 * width);
        if (xFloor <= -width) {
            xFloor = 0;
        }
        xFloor -= 1;
    }
}
