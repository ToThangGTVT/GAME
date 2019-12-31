package com.utc.entire;

import java.awt.*;

public abstract class Entire {
    public static final int ORIENT_LEFT = 0;
    public static final int ORIENT_RIGHT = 1;
    protected int x;
    protected int y;
    protected int orient;
    protected abstract void createOrient();
    protected abstract void move();
    public abstract void draw(Graphics2D g2d);

    protected void doiNguocHuong() {
        switch (orient) {
            case ORIENT_LEFT:
                orient = ORIENT_RIGHT;
                break;
            case ORIENT_RIGHT:
                orient = ORIENT_LEFT;
                break;
        }
    }

    public void setToaDo(int x, int y){
        this.y = y;
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
