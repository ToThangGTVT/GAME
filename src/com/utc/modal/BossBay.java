package com.utc.modal;

import com.utc.entire.Entire;
import com.utc.manager.LoadUtils;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class BossBay extends Entire {
    private boolean dinhElip;
    private int xPlan;
    private int yPlan;
    private int a;
    private int b;
    private boolean trungDan;

    public BossBay(int xPlan, int yPlan, int a, int b, int x) {
        this.xPlan = xPlan;
        this.yPlan = yPlan;
        this.a = a;
        this.b = b;
    }

    @Override
    protected void createOrient() {

    }

    @Override
    public void move() {
        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!dinhElip) {
            x++;
            if (x == a) dinhElip = true;
        } else {
            x--;
            if (x == -a) dinhElip = false;
        }
        y = (int) (b * Math.sqrt(a * a - (x * x/1.2f) / 1.9f) / a);
    }

    @Override
    public void draw(Graphics2D g2d) {
        Image imagePlan = LoadUtils.getImage("flyMan_still_jump.png");
        g2d.drawImage(imagePlan, x + xPlan, y + yPlan, null);
    }

    @Override
    public Rectangle getRet() {
        return new Rectangle(x + xPlan, y + yPlan, 20, 20);
    }

    public void fire(BulletDrop bulletDrop) {
        bulletDrop.move();
    }

    public boolean isTrungDan() {
        return trungDan;
    }

    public void setTrungDan(boolean trungDan) {
        this.trungDan = trungDan;
    }

    public int getToaDoX() {
        return x + xPlan;
    }

    public int getToaDoY() {
        return y + yPlan;
    }
}
