package com.utc.modal;

import com.utc.entire.Tank;
import com.utc.gui.GFrame;
import com.utc.manager.LoadUtils;

import java.awt.*;

public class TankPlayer extends Tank {
    private int t = 0;
    private boolean checkBatDauRoi = true;
    private static Image imgNongSungPlayer = LoadUtils.getImage("tanks_turret1.png");
    private static Image imgBanhXePlayer = LoadUtils.getImage("tanks_tankTracks1.png");
    private static Image imgThanXePlayer = LoadUtils.getImage("tanks_tankNavy_body1.png");

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(imgNongSungPlayer, x, y, null);
        g2d.drawImage(imgBanhXePlayer, x - 15, y + 13, null);
        g2d.drawImage(imgThanXePlayer, x - 17, y + 1, 42, 24, null);
    }

    @Override
    public void move() {
        super.move();
    }

    public void setT(int t) {
        this.t = t;
    }

    public void roiTuDo() {
        t++;
        y = y + (int) (4.905 * t * t) / 8000;
    }
}
