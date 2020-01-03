package com.utc.manager;

import com.utc.background.BackGround;
import com.utc.background.Cloud;
import com.utc.gui.GFrame;
import com.utc.modal.Boss;
import com.utc.modal.Bullet;
import com.utc.modal.TankPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.ArrayList;
import java.util.Random;

public class GameManager {
    private ArrayList<Cloud> arrCloud;
    private Boss boss;
    private ArrayList<Bullet> arrBullet;
    private TankPlayer tankPlayer;
    private BackGround backGround;
    private boolean roiTudo;
    public static boolean khaiHoa = true;
    public static boolean playerKhaiHoa = true;
    private static boolean playerBatDauBan;
    private int soLanVeMay;
    private boolean soLanVeTank = true;
    private boolean viTriRoi;
    private float t;
    private float t2;
    private int lucF = 0;
    private float goc = 90;
    int hoanhDo = (int) (460 + Math.random() * 140);
    int tungDo = 240;

    public void initGame() {
        backGround = new BackGround();
        arrCloud = new ArrayList<>();
        boss = new Boss();
        arrBullet = new ArrayList<>();
        initBullet();
        tankPlayer = new TankPlayer();
        tankPlayer.setToaDo(100, 370);
    }

    private void initCloud() {
        if (arrCloud.size() <= 1) {
            arrCloud.add(new Cloud(45));
        }
    }

    private void initBullet() {
        for (int i = 0; i < 2; i++) {
            arrBullet.add(new Bullet());
        }
    }

    public void dieuKienTank(int orient) {
        tankPlayer.setOrient(orient);
        tankPlayer.move();
        if (tankPlayer.getX() >= 300) {
            roiTudo = true;
            viTriRoi = true;
        } else if (tankPlayer.getX() <= 50) {
            roiTudo = true;
            viTriRoi = false;
        }
    }

    public void draw(Graphics2D g2d) {
        Random rnd = new Random();
        backGround.getBackGround(g2d);
        int i = 0;
        for (Cloud c : arrCloud) {
            if (soLanVeMay == 0) {
                c.setToaDo(rnd.nextInt(500), 30 + i * 120);
            }
            soLanVeMay++;
            c.draw(g2d);
            i++;
        }
        if (soLanVeTank) {
            boss.setToaDo(hoanhDo, tungDo);
        }
        soLanVeTank = false;
        boss.draw(g2d);
        tankPlayer.draw(g2d);
        arrBullet.get(0).draw(g2d);
        arrBullet.get(1).draw(g2d);

        //thanh căn lực
        g2d.setColor(new Color(0x50B450));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(5, 5, 200, 10);
        g2d.fillRect(5, 5, lucF, 10);

        //thanh căn góc
        g2d.setColor(new Color(0xB49B4C));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(5, 20, 200, 10);
        if (goc > 90) {
            g2d.fillRect(105, 20, (int) (goc-100), 10);
        } else {
            g2d.fillRect((int) (goc), 20, (int) (100-goc), 10);
        }


    }

    public void roiTuDo() {
        if (roiTudo) {
            if (viTriRoi) {
                tankPlayer.setX(300);
            } else {
                tankPlayer.setX(50);
            }
            tankPlayer.roiTuDo();
        }
        if (tankPlayer.getY() > GFrame.H_FRAME) {
            roiTudo = false;
            tankPlayer.setToaDo(100, 370);
            tankPlayer.setT(0);
            backGround.setX(0);
        }
    }

    public void bossFire() {
        if (khaiHoa) {
            t = 0;
            arrBullet.get(0).createOrient();
            int k = Bullet.gocBan;
            arrBullet.get(0).setvBullet(300);
            arrBullet.get(0).setToaDo(
                    (int) (boss.getX() - 35 * Math.cos(Math.toRadians(k))),
                    (int) (boss.getY() - 35 * Math.sin(Math.toRadians(k))));
        }
        t++;
        arrBullet.get(0).setT((int) t);
        khaiHoa = false;
        arrBullet.get(0).move();
        if (arrBullet.get(0).checkMap(backGround)) {
            khaiHoa = true;
        }
    }

    public void playerBatDauBan() {
        playerBatDauBan = true;
    }

    public void playerFire() {
        if (playerBatDauBan) {
            if (playerKhaiHoa) {
                t2 = 0;
                arrBullet.get(1).setToaDo(tankPlayer.getX(), tankPlayer.getY());
                arrBullet.get(1).setOrient(tankPlayer.getAngle());
                arrBullet.get(1).setvBullet(320 * lucF / 100);
            }
            t2++;
            playerKhaiHoa = false;
            arrBullet.get(1).setT((int) t2);
            arrBullet.get(1).move();
            if (arrBullet.get(1).checkMap(backGround)) {
                playerKhaiHoa = true;
                playerBatDauBan = false;
                arrBullet.get(1).setToaDo(-10, -10);
            }
        }
    }

    public void moveCloud() {
        initCloud();
        for (Cloud c : arrCloud) {
            if (c.getSoLanDoiHuong() == 0) {
                c.setSoLanDoiHuong(1);
                c.createOrient();
            }
            c.move();
        }
    }

    public void moveBoss() {
        boss.createOrient();
        boss.move();
    }

    public void diChuyenBackGroundTrai() {
        if (!roiTudo) {
            backGround.diChuyenBackGroundTrai();
        }
    }

    public void diChuyenBackGroundPhai() {
        if (!roiTudo) {
            backGround.diChuyenBackGroundPhai();
        }
    }

    public void xoayNong(int phimAn) {
        tankPlayer.xoayNong(phimAn);
    }

    public void canLuc(int lucF) {
        this.lucF = lucF * 2;
    }

    public void canGoc(){
        int gocTrungGian = tankPlayer.getAngle();
        if (gocTrungGian>150) gocTrungGian =148;
        if (gocTrungGian<30) gocTrungGian = 30;
        this.goc = (gocTrungGian-27)/0.6f;
    }
}
