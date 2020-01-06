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
    public boolean nguoiChoiChet;
    public static boolean thaBom = false;
    private boolean batDauThaBom = true;
    private Player player = new Player();

    public void initGame() {
        backGround = new BackGround();
        arrBossBay = new ArrayList<>();
        arrCloud = new ArrayList<>();
        arrBullet = new ArrayList<>();
        arrBulletDrop = new ArrayList<>();
        initCloud();
        initBullet();
        initBossBay();
        initBulletDrop();
        initBossTank();
        tankPlayer = new TankPlayer();
        tankPlayer.setToaDo(100, 370);
    }

    private void initCloud() {
        Random rnd = new Random();
        for (int i = 0; i < 2 ; i++) {
            arrCloud.add(new Cloud(rnd.nextInt(500), 30 + i * 120));
        }
    }

    private void initBullet() {
        for (int i = 0; i < 2; i++) {
            arrBullet.add(new Bullet());
        }
    }

    private void initBossTank(){
        bossTank = new BossTank(hoanhDo, tungDo);
    }

    private void initBossBay() {
        Random rnd = new Random();
        for (int i = 0; i < 2; i++) {
            int a = rnd.nextInt(200) + 100;
            arrBossBay.add(new BossBay(
                    rnd.nextInt(200) + 10,
                    rnd.nextInt(50),
                    a,
                    rnd.nextInt(120) + 80,
                    rnd.nextInt(2 * a) - a));
        }
    }

    public void initBulletDrop() {
        for (int i = 0; i < 2; i++) {
            arrBulletDrop.add(new BulletDrop());
        }
    }

    public void bossBayFire() {
        if (thaBom) {
            for (int j = 0; j < 2; j++) {
                if (batDauThaBom) {
                    arrBulletDrop.get(j).setToaDo(
                            arrBossBay.get(j).getToaDoX(),
                            arrBossBay.get(j).getToaDoY());
                }
                if (j == 1) {
                    batDauThaBom = false;
                }
                arrBulletDrop.get(j).move();
            }

            for (int k = 0; k < 2; k++) {
                if (arrBulletDrop.get(k).checkMap(backGround)) {
                    arrBulletDrop.get(k).setT(30);
                    arrBulletDrop.get(k).setY(0);
                    arrBulletDrop.get(k).setTrungDan(true);
                    if (checkAllTrungDan()) {
                        thaBom = false;
                        batDauThaBom = true;
                    }
                }
            }
        }
    }

    public boolean checkAllTrungDan() {
        for (int i = 0; i < 2; i++) {
            if (!arrBulletDrop.get(i).isTrungDan()) {
                return false;
            }
        }
        return true;
    }

    public void setAllTrungDanFalse() {
        for (int i = 0; i < 2; i++) {
            arrBulletDrop.get(i).setTrungDan(false);
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

        player.drawTableScore(g2d,tankPlayer);
        for (BossBay bb : arrBossBay) {
            bb.draw(g2d);
        }

        for (BulletDrop bd : arrBulletDrop) {
            if (!bd.isTrungDan()){
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

    public void bossBayMove() {
        for (BossBay bb : arrBossBay) {
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
            tankPlayer.setScore(tankPlayer.getScore() + 1);
            bossTank.setToaDo(-100, -100);
            arrBullet.get(1).setToaDo(-100, -100);
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bossTank = new BossTank(hoanhDo, tungDo);
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
        this.goc = (gocTrungGian - 27) / 0.6f;
    }
}
