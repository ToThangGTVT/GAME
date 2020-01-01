package com.utc.manager;

import com.utc.background.Cloud;
import com.utc.gui.GFrame;
import com.utc.modal.Boss;
import com.utc.modal.Bullet;
import com.utc.modal.TankPlayer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameManager {
    private ArrayList<Cloud> arrCloud;
    private Boss boss;
    private Bullet bullet;
    private TankPlayer tankPlayer;
    private boolean roiTudo;
    public static boolean khaiHoa = true;
    private int soLanVeMay;
    private boolean soLanVeTank = true;
    private boolean viTriRoi;
    private float t;
    int hoanhDo = (int) (460 + Math.random() * 140);
    int tungDo = 240;

    public void initGame() {
        arrCloud = new ArrayList<>();
        boss = new Boss();
        bullet = new Bullet();
        tankPlayer = new TankPlayer();
        tankPlayer.setToaDo(100, 370);
    }

    private void initCloud() {
        if (arrCloud.size() <= 1) {
            arrCloud.add(new Cloud(45));
        }
    }

    public void dieuKienTank(int orient) {
        tankPlayer.setOrient(orient);
        tankPlayer.move();
        if (tankPlayer.getX() > 300){
            roiTudo = true;
            viTriRoi = true;
        }else if ( tankPlayer.getX()<50){
            roiTudo = true;
            viTriRoi =false;
        }
    }

    public void draw(Graphics2D g2d) {
        Random rnd = new Random();
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
        soLanVeTank=false;
        boss.draw(g2d);
        tankPlayer.draw(g2d);
    }

    public void roiTuDo(){
        if (roiTudo){
            if (viTriRoi){
                tankPlayer.setX(300);
            } else {
                tankPlayer.setX(50);
            }
            tankPlayer.roiTuDo();
        }
        if (tankPlayer.getY()> GFrame.H_FRAME){
            roiTudo = false;
            tankPlayer.setToaDo(100, 370);
            tankPlayer.setT(0);
        }
    }

    public void bossFire() {
        if (khaiHoa) {
            t = 0;
            bullet.createOrient();
            int k = Bullet.gocBan;
            bullet.setToaDo(
                    (int) (boss.getX() - 35 * Math.cos(Math.toRadians(k))),
                    (int) (boss.getY()-35*Math.sin(Math.toRadians(k))));
        }
        t = t + 1f;
        bullet.setT((int) t);
        khaiHoa = false;
        bullet.move();
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
}
