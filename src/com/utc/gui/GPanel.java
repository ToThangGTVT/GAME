package com.utc.gui;

import com.utc.background.BackGround;
import com.utc.manager.GameManager;
import com.utc.modal.Bullet;

import javax.swing.*;
import java.awt.*;

public class GPanel extends JPanel {
    private  GameManager gameManager = new GameManager();
    public GPanel() {
        setBackground(new Color(0x1B262D));
        gameManager.initGame();
        Thread t = new Thread(r);
        t.start();
        Thread t2 = new Thread(r2);
        t2.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        BackGround backGround = new BackGround();
        backGround.getBackGround(g2d);
        gameManager.draw(g2d);
        Bullet.drawBullet(g2d);

    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
            while (true){
                repaint();
                gameManager.moveCloud();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    Runnable r2 = new Runnable() {
        @Override
        public void run() {
            while (true){
                repaint();
                gameManager.moveBoss();
                gameManager.bossFire();
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

}
