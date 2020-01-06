package com.utc.modal;

import com.utc.background.BackGround;
import com.utc.entire.Entire;
import com.utc.gui.GFrame;
import com.utc.manager.LoadUtils;

import java.awt.*;

public class BulletDrop extends Entire {
    public Image imgBullet = LoadUtils.getImage("flame.png");
    private int t;

    private int xBullet;
    private int yBullet;

    public BulletDrop(int xBullet, int yBullet) {
        this.xBullet = xBullet;
        this.yBullet = yBullet;
    }

    @Override
    protected void createOrient() {

    }

    @Override
    public void move() {
        t++;
        y = y + (int) (4.905 * t * t) / 10000;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(imgBullet, this.xBullet, this.yBullet+this.y, 10, 10, null);
    }

    @Override
    public Rectangle getRet() {
        return null;
    }

    public boolean checkMap(BackGround bg) {
        Rectangle ret = bg.getRet().intersection(getRet());
        if (!ret.isEmpty() || xBullet < 0 || yBullet > GFrame.H_FRAME) {
            return true;
        }
        return false;
    }
}
