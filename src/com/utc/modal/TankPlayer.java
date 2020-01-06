package com.utc.modal;

import com.utc.background.BackGround;
import com.utc.entire.Tank;
import com.utc.gui.GFrame;
import com.utc.manager.GameManager;
import com.utc.manager.LoadUtils;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TankPlayer extends Tank {
    private int t = 0;
    private int angle = 120;
    private static int heart=5;
    private static int score;
    private static Image imgNongSungPlayer = LoadUtils.getImage("tanks_turret1.png");
    private static Image imgBanhXePlayer = LoadUtils.getImage("tanks_tankTracks1.png");
    private static Image imgThanXePlayer = LoadUtils.getImage("tanks_tankNavy_body1.png");

    @Override
    public void draw(Graphics2D g2d) {
        xoayNong(LoadUtils.convertToBufferedImage(imgNongSungPlayer), g2d, x, y - 20, angle);
        g2d.drawImage(imgBanhXePlayer, x - 15, y + 13, null);
        g2d.drawImage(imgThanXePlayer, x - 17, y + 1, 42, 24, null);
    }

    public int getHeart() {
        return heart;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        TankPlayer.score = score;
    }

    public void setHeart(int heart) {
        TankPlayer.heart = Math.max(heart, 0);
    }

    @Override
    public Rectangle getRet() {
        return new Rectangle(x, y, 42, 24);
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

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public void xoayNong(int phimAn) {
        if (angle > 150) setAngle(150);
        if (angle < 30) setAngle(30);
        if (phimAn == KeyEvent.VK_LEFT) {
            angle--;
        } else if (phimAn == KeyEvent.VK_RIGHT) {
            angle++;
        }
    }

    public void playerFire(Bullet bullet){
        if (GameManager.playerBatDauBan) {
            if (GameManager.playerKhaiHoa) {
                GameManager.t2 = 0;
                bullet.setToaDo(this.x, this.y);
                bullet.setOrient(this.angle);
                bullet.setvBullet(320 * GameManager.lucF / 100);
            }
            GameManager.playerKhaiHoa = false;
            bullet.setT(GameManager.t2);
            bullet.move();
            if (bullet.checkMap(new BackGround())) {
                GameManager.playerKhaiHoa = true;
                GameManager.playerBatDauBan = false;
                bullet.setToaDo(-10, -10);
            }
        }
    }
}
