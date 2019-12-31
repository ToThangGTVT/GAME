package com.utc.entire;

import com.utc.modal.Bullet;

import java.awt.*;
import java.util.Random;

public abstract class Tank extends Entire {
    private Bullet bullet;
    private int satThuong;
    private int heart;

    public void fire(int xBullet, int yBullet){
        bullet = new Bullet();
        bullet.setToaDo(xBullet,yBullet);
        bullet.move();
    }

    public void setOrient(int orient){
        this.orient = orient;
    }

    @Override
    public void createOrient() {
        Random rnd = new Random();
        int xacSuat = rnd.nextInt(100);
        if (xacSuat>98){
            orient = rnd.nextInt(2);
        }
    }

    @Override
    public void move() {
        switch (orient){
            case ORIENT_LEFT:
                x-=1;
                break;
            case ORIENT_RIGHT:
                x+=1;
                break;
        }
    }
}
