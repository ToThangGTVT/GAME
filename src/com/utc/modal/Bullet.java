package com.utc.modal;

import com.utc.background.BackGround;
import com.utc.entire.Entire;
import com.utc.gui.GFrame;
import com.utc.manager.GameManager;
import com.utc.manager.LoadUtils;

import java.awt.*;
import java.util.Random;

public class Bullet extends Entire {
    private float t;
    private static Image imgBullet = LoadUtils.getImage("tank_explosion10.png");
    private static int xBullet;
    private static int yBullet;
    public static int gocBan;
    private BackGround bg = new BackGround();

    public void setT(float t) {
        this.t = t;
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
        float vBullet = 300f;
        x = (int) (vBullet * Math.cos(Math.toRadians(orient)) * t) / 1200;
        y = (int) (-vBullet * Math.sin(Math.toRadians(orient)) * t + 4.905 * t * t) / 1200;
        setToaDo(xBullet - x, yBullet + y);
        checkMap(bg);
    }

    @Override
    public void draw(Graphics2D g2d) {

    }

    public static void drawBullet(Graphics2D g2d) {
        g2d.drawImage(imgBullet, xBullet, yBullet, 10, 10, null);
    }

    public void checkMap(BackGround bg) {
        Rectangle ret = bg.getRet().intersection(getRet());
        if (!ret.isEmpty() || xBullet < 0 || yBullet > GFrame.H_FRAME) {
            GameManager.khaiHoa = true;
        }
    }

    public Rectangle getRet() {
        int w = imgBullet.getWidth(null);
        int h = imgBullet.getHeight(null);
        return new Rectangle(xBullet, yBullet, w, h);
    }
}
