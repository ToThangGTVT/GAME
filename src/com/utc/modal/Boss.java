package com.utc.modal;

import com.utc.entire.Tank;
import com.utc.manager.LoadUtils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Boss extends Tank {
    private Bullet bullet;
    private static Image imgBanhXe = LoadUtils.getImage("tanks_tankTracks1.png");
    private static Image imgThanXe = LoadUtils.getImage("tanks_tankDesert_body1.png");

    @Override
    public void createOrient() {
        super.createOrient();
    }

    @Override
    public void move() {
        super.move();
        if (x <= 460 || x >= 600) {
            doiNguocHuong();
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        xoayNong(LoadUtils.convertToBufferedImage(
                LoadUtils.getImage("tanks_turret1.png")),g2d,x,y-20, Bullet.getGocBan());
        g2d.drawImage(imgBanhXe, x - 15, y + 13, null);
        g2d.drawImage(imgThanXe, x - 17, y + 1, null);
    }

    public Rectangle getRet() {
        int w = imgThanXe.getWidth(null);
        int h = imgThanXe.getHeight(null);
        return new Rectangle(x, y, w, h);
    }
}
