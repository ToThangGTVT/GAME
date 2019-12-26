package com.utc.entire;

public abstract class Entire {
    public static final int ORIENT_LEFT = 0;
    public static final int ORIENT_RIGHT = 1;
    protected int x;
    protected int y;
    public int orient;
    public abstract void createOrient();
    public abstract void move();
}
