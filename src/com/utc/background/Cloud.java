package com.utc.background;

import com.utc.entire.Entire;
import com.utc.gui.GFrame;
import com.utc.manager.LoadUtils;

import java.awt.*;
import java.util.Random;

public class Cloud extends Entire {
    protected int soLanDoiHuong;
    private Image imgCloud;

    public void setCaoDo(int y) {
        this.y = y;
    }

    public void setHoanhDo(int x) {
        this.x = x;
    }

    public int getSoLanDoiHuong() {
        return soLanDoiHuong;
    }

    public void setSoLanDoiHuong(int soLanDoiHuong) {
        this.soLanDoiHuong = soLanDoiHuong;
    }

    public Cloud(int y) {
        this.y = y;
    }

    public void drawCloud(Graphics2D g2d) {
        imgCloud = LoadUtils.getImage("forest_pack_107.png");
        g2d.drawImage(imgCloud, x, y, null);
    }

    private void doiNguocHuong() {
        switch (orient) {
            case ORIENT_LEFT:
                orient = ORIENT_RIGHT;
                break;
            case ORIENT_RIGHT:
                orient = ORIENT_LEFT;
                break;
        }
    }

    @Override
    public void createOrient() {
        orient = new Random().nextInt(2);
    }

    @Override
    public void move() {
        imgCloud = LoadUtils.getImage("forest_pack_107.png");
        int vEntire = 1;
        switch (orient) {
            case ORIENT_LEFT:
                x +=vEntire;
                break;
            case ORIENT_RIGHT:
                x -= vEntire;
                break;
        }
        if (x > GFrame.W_FRAME || x < -5-imgCloud.getWidth(null)) {
            doiNguocHuong();
        }
    }
}
