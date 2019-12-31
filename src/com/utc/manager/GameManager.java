package com.utc.manager;

import com.utc.background.Cloud;
import com.utc.modal.Boss;
import com.utc.modal.Bullet;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameManager {
    private ArrayList<Cloud> arrCloud;
    private ArrayList<Boss> arrBosses;
    private ArrayList<Bullet> arrBullet;
    private ArrayList<Integer> viTriX;
    private ArrayList<Integer> viTriY;
    private Bullet bullet;
    private boolean khaiHoa = true;
    private int soLanVeMay;
    private int soLanVeTank;
    int hoanhDo = (int) (460 + Math.random() * 150);
    int tungDo = 240;

    public void initGame() {
        arrCloud = new ArrayList<>();
        arrBosses = new ArrayList<>();
        arrBullet = new ArrayList<>();
        bullet = new Bullet();
    }

    private void initCloud() {
        if (arrCloud.size() <= 1) {
            arrCloud.add(new Cloud(45));
        }
    }

    private void initBoss() {
        if (arrBosses.size() < 1) {
            arrBosses.add(new Boss());
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

        for (Boss b : arrBosses) {
            if (soLanVeTank == 0) {
                b.setToaDo(hoanhDo, tungDo);
            }
            soLanVeTank++;
            b.draw(g2d);
        }
    }

    public void bossFire() {
        if (khaiHoa) {
            bullet.setToaDo(hoanhDo, tungDo);
            bullet.createOrient();
        }
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
        initBoss();
        for (Boss b : arrBosses) {
            b.createOrient();
            b.move();
        }
    }
}
