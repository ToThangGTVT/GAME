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
    private boolean viTriRoi;
    public static float t;
    public static float t2;
    public static int lucF = 0;
    public static float goc = 90;
    int hoanhDo = (int) (460 + Math.random() * 140);
    int tungDo = 240;
    public static boolean pauseGame;
    public static boolean nguoiChoiChet = false;
    public static boolean thaBom = true;
    private Player player = new Player();
    long tBanBossBay;
    long tPlayerDie;
    long TPlayerDie;
    public static boolean explode = false;
    private int nBossBay = 2;
    private int index = 0;
    private int xExplode = 0;
    private int yExplode = 0;

    public void initGame() {
        backGround = new BackGround();
        arrBossBay = new ArrayList<>();
        arrCloud = new ArrayList<>();
        arrBullet = new ArrayList<>();
        arrBulletDrop = new ArrayList<>();
        initCloud();
        initBullet();
        initBossBay();
        initBossTank();
        initPlayer();
    }

    private void initCloud() {
        Random rnd = new Random();
        for (int i = 0; i < 2; i++) {
            arrCloud.add(new Cloud(rnd.nextInt(500), 30 + i * 120));
        }
    }

    private void initBullet() {
        for (int i = 0; i < 2; i++) {
            arrBullet.add(new Bullet());
        }
    }

    private void initBossTank() {
        bossTank = new BossTank(hoanhDo, tungDo);
    }

    private void initBossBay() {
        Random rnd = new Random();
        int a = rnd.nextInt(200) + 100;
        for (int i = 0; i < 2; i++) {
            arrBossBay.add(new BossBay(
                    rnd.nextInt(200) + 10,
                    rnd.nextInt(50),
                    a,
                    rnd.nextInt(120) + 80,
                    rnd.nextInt(2 * a) - a));
        }
    }

    public void initPlayer() {
        tankPlayer = new TankPlayer();
        tankPlayer.setToaDo(100, 370);
        nguoiChoiChet = false;
        backGround = new BackGround();
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
        backGround.getBackGround(g2d);
        for (Cloud c : arrCloud) {
            c.draw(g2d);
        }
        bossTank.draw(g2d);
        if (!nguoiChoiChet) tankPlayer.draw(g2d);
        arrBullet.get(0).draw(g2d);
        if (!playerBatDauBan) {
            arrBullet.get(1).setToaDo(-20, -20);
        }
        arrBullet.get(1).draw(g2d);
        player.drawTableScore(g2d);
        for (BossBay bb : arrBossBay) {
            if (!bb.isTrungDan()) {
                bb.draw(g2d);
            }
        }
        for (BulletDrop bd : arrBulletDrop) {
            if (bd.isVisible()) {
                bd.draw(g2d);
            }
        }
        if (explode) {
            arrBullet.get(0).explosive(g2d, index, xExplode, yExplode);
        }
    }

    public void roiTuDo() {
        if (!nguoiChoiChet) {
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
    }

    public void bossFire() {
        bossTank.bossFire(khaiHoa, arrBullet.get(0), (int) t);
        t++;
        TPlayerDie = System.currentTimeMillis();
        if (nguoiChoiChet) {
            if (TPlayerDie - tPlayerDie > 3000) {
                initPlayer();
            }
        } else if (arrBullet.get(0).checkDie(tankPlayer)) {
            xExplode = tankPlayer.getX() - 50;
            yExplode = tankPlayer.getY() - 50;
            explode = true;
            tankPlayer.setHeart(tankPlayer.getHeart() - 1);
            nguoiChoiChet = true;
            tPlayerDie = System.currentTimeMillis();
            if (tankPlayer.getHeart() == 0) {
                pauseGame = true;
                tankPlayer = new TankPlayer();
                if (JOptionPane.showConfirmDialog(
                        null,
                        "Thôi đi ngủ",
                        "Gameover", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else {
                    TankPlayer.heart = 5;
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
            xExplode = bossTank.getX() - 50;
            yExplode = bossTank.getY() - 50;
            explode = true;
            tankPlayer.setScore(tankPlayer.getScore() + 1);
            bossTank.setToaDo(-100, -100);
            arrBullet.get(1).setToaDo(-100, -100);
            tPlayerDie = System.currentTimeMillis();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bossTank = new BossTank(hoanhDo, tungDo);
            nguoiChoiChet = false;
        }
        for (int i = 0; i < nBossBay; i++) {
            if (arrBullet.get(1).checkBanTrungBossBay(arrBossBay.get(i))) {
                for (int j = 1; j >= 0; j--) {
                    if (i == j) {
                        arrBossBay.get(j).setTrungDan(true);
                        arrBossBay.remove(j);
                        nBossBay--;
                        tBanBossBay = System.currentTimeMillis();
                        break;
                    }
                }
            }
        }
    }

    public void initBulletBossBay(ArrayList<BossBay> arrBossBay) {
        int i = 0;
        while (i < arrBossBay.size()) {
            arrBulletDrop.add(new BulletDrop(
                    arrBossBay.get(i).getToaDoX(),
                    arrBossBay.get(i).getToaDoY()));
            i++;
        }
    }

    public void bossBayMove() {
        if (nBossBay < 2) {
            Random rnd = new Random();
            long TBanBossBay = System.currentTimeMillis();
            int a = rnd.nextInt(200) + 100;
            if (TBanBossBay - tBanBossBay > 5000 && tBanBossBay != 0) {
                arrBossBay.add(new BossBay(
                        rnd.nextInt(200) + 10,
                        rnd.nextInt(50),
                        a,
                        rnd.nextInt(120) + 80,
                        rnd.nextInt(2 * a) - a));
                nBossBay++;
            }
        }
        for (int i = 0; i < nBossBay; i++) {
            arrBossBay.get(i).move();
        }
        if (thaBom) {
            initBulletBossBay(arrBossBay);
        }
        thaBom = false;
    }

    public void bossBayThaBom() {
        bossBayMove();
        for (BulletDrop bd : arrBulletDrop) {
            bd.move();
            if (!nguoiChoiChet) {
                if (bd.checkMap(backGround) || bd.checkDie(tankPlayer)) {
                    if (bd.checkDie(tankPlayer)) {
                        nguoiChoiChet = true;
                        tPlayerDie = System.currentTimeMillis();
                    }
                    bd.setVisible(false);
                    break;
                }
            }
        }
        arrBulletDrop.removeIf(bulletDrop -> !bulletDrop.isVisible());
        if (arrBulletDrop.size() == 0 && arrBossBay.size() != 0) {
            thaBom = true;
            bossBayThaBom();
        }
    }


    public void moveCloud() {
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
        goc = (gocTrungGian - 27) / 0.6f;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
