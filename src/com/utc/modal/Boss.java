package com.utc.modal;

import com.utc.entire.Tank;
import com.utc.manager.LoadUtils;

import java.awt.*;

public class Boss extends Tank {
    private static Image imgBanhXe = LoadUtils.getImage("tanks_tankTracks1.png");
    private static Image imgThanXe = LoadUtils.getImage("tanks_tankDesert_body1.png");
    private static Image imgNongSung = LoadUtils.getImage("tanks_turret1.png");

    @Override
    public void createOrient() {
        super.createOrient();
    }

    @Override
    public void move() {
        super.move();
        if (x<=460||x>=600){
            doiNguocHuong();
        }
    }

    @Override
    public void fire(int xBullet, int yBullet) {
        super.fire(xBullet,yBullet);
    }


    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(imgNongSung, x, y, null);
        g2d.drawImage(imgBanhXe, x-15, y+13, null);
        g2d.drawImage(imgThanXe, x-17, y+1, null);
    }
}
