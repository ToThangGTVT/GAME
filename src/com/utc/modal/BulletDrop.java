package com.utc.modal;

import com.utc.background.BackGround;
import com.utc.entire.Entire;
import com.utc.gui.GFrame;
import com.utc.manager.LoadUtils;

import java.awt.*;

public class BulletDrop extends Entire {
    public Image imgBullet = LoadUtils.getImage("flame.png");
    private int t=30;
    private int xBullet;
    private int yBullet;
    private boolean trungDan;
    private long timeExplosive;
    private boolean visible;

    public BulletDrop(int xBullet, int yBullet) {
        this.xBullet = xBullet;
        this.yBullet = yBullet;
        visible= true;
    }

    public long getTimeExplosive() {
        return timeExplosive;
    }

    public void setTimeExplosive(long timeExplosive) {
        this.timeExplosive = timeExplosive;
    }

    public void setToaDo(int x, int y) {
        this.xBullet = x;
        this.yBullet = y;
        this.visible = true;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isTrungDan() {
        return trungDan;
    }

    public void setTrungDan(boolean trungDan) {
        this.trungDan = trungDan;
    }

    public void setT(int t){
        this.t = t;
    }

    public void setY(int y){
        this.y = y;
    }

    @Override
    protected void createOrient() {

    }

    @Override
    public void move() {
        t++;
        y =(int) (4.905 * t * t) / 500;
    }


    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(imgBullet, this.xBullet, this.yBullet+this.y, null);
    }

    @Override
    public Rectangle getRet() {
        return new Rectangle(xBullet, yBullet+y, 5, 5);
    }

    public boolean checkMap(BackGround bg) {
        Rectangle ret = bg.getRet().intersection(getRet());
        if (!ret.isEmpty()) {
            return true;
        } else if (xBullet < 0 || yBullet+this.y > GFrame.H_FRAME){
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
}
