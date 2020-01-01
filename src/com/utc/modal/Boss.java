package com.utc.modal;

import com.utc.entire.Tank;
import com.utc.manager.LoadUtils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Boss extends Tank {
    private static Image imgBanhXe = LoadUtils.getImage("tanks_tankTracks1.png");
    private static Image imgThanXe = LoadUtils.getImage("tanks_tankDesert_body1.png");
    private static Image imgNongSung = LoadUtils.getImage("tanks_turret1.png");
    ;

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

    public void xoayNong(BufferedImage image, Graphics2D g2d,int x, int y,int angle){
        double rotationRequired = Math.toRadians (angle-180);
        int locationX = image.getWidth();
        int locationY = 20;
        AffineTransform tx = new  AffineTransform();
        tx.translate(image.getWidth(),image.getHeight()*4);
        tx.rotate(rotationRequired);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BICUBIC);
        g2d.drawImage(op.filter(image, null), x-37, y-11, null);
    }

    @Override
    public void fire(int xBullet, int yBullet) {
        super.fire(xBullet, yBullet);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(imgBanhXe, x - 15, y + 13, null);
        g2d.drawImage(imgThanXe, x - 17, y + 1, null);
        xoayNong(LoadUtils.convertToBufferedImage(
                LoadUtils.getImage("tanks_turret1.png")),g2d,x,y-20,Bullet.getGocBan());

    }

    public Rectangle getRet() {
        int w = imgThanXe.getWidth(null);
        int h = imgThanXe.getHeight(null);
        return new Rectangle(x, y, w, h);
    }
}
