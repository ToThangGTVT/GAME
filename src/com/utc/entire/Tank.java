package com.utc.entire;

import com.utc.modal.Bullet;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.util.Random;

public abstract class Tank extends Entire {
    private Bullet bullet;
    private int satThuong;
    private int heart;

    public void setOrient(int orient) {
        this.orient = orient;
    }

    @Override
    public void createOrient() {
        Random rnd = new Random();
        int xacSuat = rnd.nextInt(100);
        if (xacSuat > 98) {
            orient = rnd.nextInt(2);
        }
    }

    @Override
    public void move() {
        switch (orient) {
            case ORIENT_LEFT:
                x -= 1;
                break;
            case ORIENT_RIGHT:
                x += 1;
                break;
        }
    }

    public void xoayNong(BufferedImage image, Graphics2D g2d,int x, int y,int angle){
        double rotationRequired = Math.toRadians (angle-180);
        AffineTransform tx = new  AffineTransform();
        tx.translate(image.getWidth(),image.getHeight()*4);
        tx.rotate(rotationRequired);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BICUBIC);
        g2d.drawImage(op.filter(image, null), x-37, y-11, null);
    }

}
