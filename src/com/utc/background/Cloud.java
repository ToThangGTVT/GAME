package com.utc.background;

import com.utc.entire.Entire;
import com.utc.gui.GFrame;
import com.utc.manager.LoadUtils;

import java.awt.*;
import java.util.Random;

public class Cloud extends Entire {
    private static Image imgCloud = LoadUtils.getImage("forest_pack_107.png");;
    protected int soLanDoiHuong;

    public int getSoLanDoiHuong() {
        return soLanDoiHuong;
    }

    public void setSoLanDoiHuong(int soLanDoiHuong) {
        this.soLanDoiHuong = soLanDoiHuong;
    }

    public Cloud(int y) {
        this.y = y;
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

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(imgCloud, x, y, null);
    }

}
