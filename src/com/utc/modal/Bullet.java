package com.utc.modal;

import com.utc.entire.Entire;
import com.utc.manager.LoadUtils;

import java.awt.*;
import java.util.Random;

public class Bullet extends Entire {
    private float t;
    private static Image imgBullet= LoadUtils.getImage("tank_explosion10.png");
    private static int xBullet;
    private static int yBullet;
    private int y0;

    public void setT(float t) {
        this.t = t;
    }

    @Override
    public void setToaDo(int x, int y) {
        xBullet = x;
        yBullet = y;
    }

    @Override
    public void createOrient() {
        Random rnd = new Random();
        orient = rnd.nextInt(61);
        orient = 70;
    }

    @Override
    public void move() {
        float vBullet = 300f;
        x = (int) (vBullet * Math.cos(Math.toRadians(orient)) * t) / 1200;
        y = (int) (-vBullet * Math.sin(Math.toRadians(orient)) * t + 4.905 * t * t) / 1200;
        setToaDo(xBullet - x,yBullet + y);
    }

    @Override
    public void draw(Graphics2D g2d) {

    }

    public static void drawBullet(Graphics2D g2d) {
        g2d.drawImage(imgBullet, xBullet, yBullet, 10, 10, null);
    }
}
