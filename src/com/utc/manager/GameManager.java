package com.utc.manager;

import com.utc.background.BackGround;
import com.utc.background.Cloud;
import com.utc.entire.Tank;
import com.utc.gui.GFrame;
import com.utc.gui.GPanel;
import com.utc.modal.Boss;
import com.utc.modal.Bullet;
import com.utc.modal.TankPlayer;

import javax.sound.midi.MidiFileFormat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameManager {
    private ArrayList<Cloud> arrCloud;
    private Boss boss;
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
    private Image imgHeart = LoadUtils.getImage("—Pngtree—vector heart icon_4183857.png");

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
        if (nguoiChoiChet == false) tankPlayer.draw(g2d);
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
            g2d.fillRect(105, 20, (int) (goc - 100), 10);
        } else {
            g2d.fillRect((int) (goc), 20, (int) (100 - goc), 10);
        }

        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(imgHeart, 5, 35, null);
        g2d.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        g2d.setColor(new Color(0xD5D5D5));
        g2d.drawString(String.valueOf(tankPlayer.getHeart()), 33, 53);
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

    /**
     * boss bắn
     * */
    public void bossFire(){
        boss.bossFire(khaiHoa,arrBullet.get(0), (int) t);
        t++;
        if (arrBullet.get(0).checkDie(tankPlayer)) {
            nguoiChoiChet = true;
            tankPlayer.setHeart(tankPlayer.getHeart() - 1);
            tankPlayer.setToaDo(-100,-100);
            arrBullet.get(0).setToaDo(-200,-200);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            nguoiChoiChet = false;
            tankPlayer = new TankPlayer();
            tankPlayer.setToaDo(100, 370);
            if (tankPlayer.getHeart()==0){
                pauseGame = true;
                tankPlayer = new TankPlayer();
                if (JOptionPane.showConfirmDialog(
                        null,
                        "Thôi đi ngủ",
                        "Gameover",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
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
        if (arrBullet.get(1).checkWin(boss)) {
            boss.setToaDo(-100, -100);
            arrBullet.get(1).setToaDo(-100, -100);
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boss = new Boss();
            boss.setToaDo(hoanhDo, tungDo);
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
        GameManager.lucF = lucF * 2;
    }

    public void canGoc() {
        int gocTrungGian = tankPlayer.getAngle();
        if (gocTrungGian > 150) gocTrungGian = 148;
        if (gocTrungGian < 30) gocTrungGian = 30;
        this.goc = (gocTrungGian - 27) / 0.6f;
    }
}
