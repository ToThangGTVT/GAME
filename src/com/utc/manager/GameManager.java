package com.utc.manager;

import com.utc.background.BackGround;
import com.utc.background.Cloud;
import com.utc.gui.GFrame;
import com.utc.modal.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameManager {
    private ArrayList<Cloud> arrCloud;
    private ArrayList<BossBay> arrBossBay;
    private ArrayList<BulletDrop> arrBulletDrop;
    private BossTank bossTank;
    private ArrayList<Bullet> arrBullet;
    private TankPlayer tankPlayer;
    private BackGround backGround;
    private boolean roiTudo;
    public static boolean khaiHoa = true;
    public static boolean playerKhaiHoa = true;
    public static boolean playerBatDauBan;
    private int soLanVeMay;
    private boolean soLanVeTank = true;
    private boolean viTriRoi;
    public static float t;
    public static float t2;
    public static int lucF = 0;
    private float goc = 90;
    int hoanhDo = (int) (460 + Math.random() * 140);
    int tungDo = 240;
    public static boolean pauseGame;
    public boolean nguoiChoiChet;
    public boolean thaBom;
    private Image imgHeart = LoadUtils.getImage("platformPack_item017.png");
    private Image imgTankFired = LoadUtils.getImage("tanks_tankDesert1.png");

    public void initGame() {
        backGround = new BackGround();
        arrBossBay = new ArrayList<>();
        arrCloud = new ArrayList<>();
        bossTank = new BossTank();
        arrBullet = new ArrayList<>();
        arrBulletDrop = new ArrayList<>();
        initBullet();
        initBossBay();
        initBulletDrop();
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

    private void initBossBay(){
        Random rnd  = new Random();
        for (int i = 0; i < 2; i++) {
            int a = rnd.nextInt(200)+100;
            arrBossBay.add(new BossBay(
                    rnd.nextInt(200)+10,
                    rnd.nextInt(50),
                    a,
                    rnd.nextInt(120)+80,
                    rnd.nextInt(2*a)-a));
        }
    }

    public void initBulletDrop(){
        for (int i = 0; i < 2; i++) {
            arrBulletDrop.add(new BulletDrop(
                    arrBossBay.get(i).getToaDoX(),
                    arrBossBay.get(i).getToaDoY()));
        }
    }

    public void bossBayFire(){
        for (BulletDrop bd : arrBulletDrop) {
            bd.move();
            bd.checkMap(backGround);
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
            bossTank.setToaDo(hoanhDo, tungDo);
        }
        soLanVeTank = false;
        bossTank.draw(g2d);
        if (nguoiChoiChet == false) tankPlayer.draw(g2d);
        arrBullet.get(0).draw(g2d);
        if (!playerBatDauBan){
            arrBullet.get(1).setToaDo(-20,-20);
        }
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
            g2d.fillRect(105, 20, (int) (goc - 100), 10);
        } else {
            g2d.fillRect((int) (goc), 20, (int) (100 - goc), 10);
        }
        g2d.drawImage(imgTankFired, 7, 63, 21, 17, null);

        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(imgHeart, -5, 25, null);
        g2d.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        g2d.setColor(new Color(0xD5D5D5));
        g2d.drawString(String.valueOf(tankPlayer.getHeart()), 35, 53);
        g2d.drawString(String.valueOf(tankPlayer.getScore()), 35, 77);

        for (BossBay bb : arrBossBay) {
            bb.draw(g2d);
        }
        if (thaBom){
            for (BulletDrop bd: arrBulletDrop) {
                bd.draw(g2d);
            }
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

    public void bossBayMove(){
        for (BossBay bb: arrBossBay) {
            bb.move();
        }
    }

    /**
     * boss bắn
     */
    public void bossFire() {
        bossTank.bossFire(khaiHoa, arrBullet.get(0), (int) t);
        t++;
        if (arrBullet.get(0).checkDie(tankPlayer)) {
            nguoiChoiChet = true;
            tankPlayer.setHeart(tankPlayer.getHeart() - 1);
            tankPlayer.setToaDo(-100, -100);
            arrBullet.get(0).setToaDo(-200, -200);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            nguoiChoiChet = false;
            tankPlayer = new TankPlayer();
            tankPlayer.setToaDo(100, 370);
            if (tankPlayer.getHeart() == 0) {
                pauseGame = true;
                tankPlayer = new TankPlayer();
                if (JOptionPane.showConfirmDialog(
                        null,
                        "Thôi đi ngủ",
                        "Gameover", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        }
    }

    public void playerBatDauBan() {
        playerBatDauBan = true;
    }

    public void playerFire() {
        tankPlayer.playerFire(arrBullet.get(1));
        t2++;
        if (arrBullet.get(1).checkWin(bossTank)) {
            tankPlayer.setScore(tankPlayer.getScore()+1);
            bossTank.setToaDo(-100, -100);
            arrBullet.get(1).setToaDo(-100, -100);
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bossTank = new BossTank();
            bossTank.setToaDo(hoanhDo, tungDo);
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
        bossTank.createOrient();
        bossTank.move();
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
        GameManager.lucF = lucF * 2;
    }

    public void canGoc() {
        int gocTrungGian = tankPlayer.getAngle();
        if (gocTrungGian > 150) gocTrungGian = 148;
        if (gocTrungGian < 30) gocTrungGian = 30;
        this.goc = (gocTrungGian - 27) / 0.6f;
    }
}
