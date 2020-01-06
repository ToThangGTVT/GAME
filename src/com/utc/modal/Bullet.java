package com.utc.modal;

import com.utc.background.BackGround;
import com.utc.entire.Entire;
import com.utc.gui.GFrame;
import com.utc.manager.LoadUtils;

import java.awt.*;
import java.util.Random;

public class Bullet extends Entire {
    public Image imgBullet = LoadUtils.getImage("tank_explosion5.png");
    private float t;
    private int xBullet;
    private int yBullet;
    private int vBullet;
    public static int gocBan;
    private BackGround bg = new BackGround();

    public void setT(float t) {
        this.t = t;
    }

    public void setvBullet(int vBullet) {
        this.vBullet = vBullet;
    }

    @Override
    public void setToaDo(int x, int y) {
        xBullet = x;
        yBullet = y;
    }

    @Override
    public void createOrient() {
        Random rnd = new Random();
        orient = rnd.nextInt(80 - 60) + 60;
        gocBan = orient;
    }

    public static int getGocBan(){
        return gocBan;
    }

    @Override
    public void move() {
        x = (int) (vBullet * Math.cos(Math.toRadians(orient)) * t) / 1200;
        y = (int) (-vBullet * Math.sin(Math.toRadians(orient)) * t + 4.905 * t * t) / 1200;
        setToaDo(xBullet - x, yBullet + y);
    }

    @Override
    public void draw(Graphics2D g2d) {
        imgBullet = LoadUtils.getImage("tank_explosion5.png");
        g2d.drawImage(imgBullet, this.xBullet, this.yBullet, 10, 10, null);
    }

    public boolean checkMap(BackGround bg) {
        Rectangle ret = bg.getRet().intersection(getRet());
        if (!ret.isEmpty() || xBullet < 0 || yBullet > GFrame.H_FRAME) {
            return true;
        }
        return false;
    }

    public boolean checkDie(TankPlayer tankPlayer){
        Rectangle ret = tankPlayer.getRet().intersection(getRet());
        if (!ret.isEmpty()){
            return true;
        }
        return false;
    }

    public boolean checkWin(BossTank bossTank){
        Rectangle ret = bossTank.getRet().intersection(getRet());
        if (!ret.isEmpty()){
            return true;
        }
        return false;
    }

    public Rectangle getRet() {
        return new Rectangle(xBullet, yBullet, 10, 10);
    }
}
