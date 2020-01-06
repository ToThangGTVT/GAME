package com.utc.modal;

import com.utc.entire.Entire;
import com.utc.manager.LoadUtils;

import java.awt.*;

public class BossBay extends Entire {
    private BulletDrop bulletDrop;
    private boolean dinhElip;
    private int xPlan;
    private int yPlan;
    private int a;
    private int b;

    public BossBay(int xPlan, int yPlan, int a, int b, int x) {
        this.xPlan = xPlan;
        this.yPlan = yPlan;
        this.a = a;
        this.b = b;
    }

    @Override
    protected void createOrient() {

    }

    public int getToaDoX(){
        return x + xPlan;
    }

    public int getToaDoY(){
        return y + yPlan;
    }

    @Override
    public void move() {
        if (!dinhElip) {
            x++;
            if (x == a) dinhElip = true;
        } else {
            x--;
            if (x == -a) dinhElip = false;
        }
        y = (int) (b * Math.sqrt(a * a - (x * x)/1.7f) / a);
    }

    @Override
    public void draw(Graphics2D g2d) {
        Image imagePlan = LoadUtils.getImage("flyMan_still_jump.png");
        g2d.drawImage(imagePlan, x + xPlan, y + yPlan, null);
    }

    @Override
    public Rectangle getRet() {
        return null;
    }

    public void fire(BulletDrop bulletDrop){
        bulletDrop.move();
    }
}
