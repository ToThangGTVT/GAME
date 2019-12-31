package com.utc.modal;

import com.utc.entire.Entire;
import com.utc.manager.LoadUtils;

import java.awt.*;
import java.util.Random;

public class Bullet extends Entire {
    private int t = 1;
    private static int xBullet;
    private static int yBullet;
    private int vBullet = 60;
    private int y0;

    @Override
    public void setToaDo(int x, int y) {
        xBullet = x;
        yBullet = y;
    }

    @Override
    public void createOrient() {
        Random rnd = new Random();
        orient = rnd.nextInt(61);
    }

    @Override
    public void move() {
        x = (int) (vBullet * Math.cos(Math.toRadians(orient)) * t);
        y = (int) (y0 - vBullet * Math.sin(Math.toRadians(orient)) * t + 4.905 * t * t);
        setToaDo(x,y);
    }

    @Override
    public void draw(Graphics2D g2d) {

    }

    public static void drawBullet(Graphics2D g2d) {
        Image imgBullet = LoadUtils.getImage("tank_explosion10.png");
        g2d.drawImage(imgBullet, xBullet, yBullet, 10, 10, null);
    }
}
