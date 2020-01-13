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
    private boolean die;
    public static int heart = 1;
    public static int score;
    private Image imgNongSungPlayer = LoadUtils.getImage("tanks_turret1.png");
    private Image imgBanhXePlayer = LoadUtils.getImage("tanks_tankTracks1.png");
    private Image imgThanXePlayer = LoadUtils.getImage("tanks_tankNavy_body1.png");
    private boolean playerKhaiHoa = true;
    private boolean playerBatDauBan;
    private int t2;
    private int lucF;

    public TankPlayer() {
        die = false;
    }

    @Override
    public void draw(Graphics2D g2d) {
        xoayNong(LoadUtils.convertToBufferedImage(imgNongSungPlayer), g2d, x, y - 20, angle);
        g2d.drawImage(imgBanhXePlayer, x - 15, y + 13, null);
        g2d.drawImage(imgThanXePlayer, x - 17, y + 1, 42, 24, null);
    }

    @Override
    public Rectangle getRet() {
        return new Rectangle(x, y, 42, 24);
    }

    @Override
    public void move() {
        super.move();
    }

    public void roiTuDo() {
        t++;
        y = y + (int) (4.905 * t * t) / 8000;
    }

    public void xoayNong(int phimAn) {
        if (angle > 150) setAngle(150);
        if (angle < 30) setAngle(30);
        if (phimAn == KeyEvent.VK_S) {
            angle++;
        } else if (phimAn == KeyEvent.VK_W) {
            angle--;
        }
    }

    public void playerFire(Bullet bullet) {
        if (playerBatDauBan) {
            if (playerKhaiHoa) {
                this.t2 = 0;
                bullet.setToaDo(this.x, this.y);
                bullet.setOrient(this.angle);
                bullet.setvBullet(320 * this.lucF / 100);
            }
            playerKhaiHoa = false;
            bullet.setT(this.t2);
            bullet.move();
            if (bullet.checkMap(new BackGround())) {
                playerKhaiHoa = true;
                playerBatDauBan = false;
                bullet.setToaDo(-10, -10);
            }
        }
    }

    public boolean isPlayerBatDauBan() {
        return playerBatDauBan;
    }

    public void setPlayerBatDauBan(boolean playerBatDauBan) {
        this.playerBatDauBan = playerBatDauBan;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public void setT(int t) {
        this.t = t;
    }

    public boolean isDie() {
        return die;
    }

    public void setDie(boolean die) {
        this.die = die;
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

    public int getT2() {
        return t2;
    }

    public void setT2(int t2) {
        this.t2 = t2;
    }

    public int getLucF() {
        return lucF;
    }

    public void setLucF(int lucF) {
        this.lucF = lucF;
    }


}
