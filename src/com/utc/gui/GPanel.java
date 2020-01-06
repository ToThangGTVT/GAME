package com.utc.gui;

import com.utc.background.BackGround;
import com.utc.entire.Entire;
import com.utc.manager.GameManager;
import com.utc.modal.Bullet;
import com.utc.modal.TankPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GPanel extends JPanel implements KeyListener {
    public boolean[] flag = new boolean[256];
    private GameManager gameManager = new GameManager();
    private int lucF2;
    private boolean canNong;
    long t;

    public GPanel() {
        setBackground(new Color(0x1B262D));
        setFocusable(true);
        addKeyListener(this);
        gameManager.initGame();
        GameManager.pauseGame = false;
        Thread t1 = new Thread(r);
        t1.start();
        Thread t2 = new Thread(r2);
        t2.start();
        Thread t3 = new Thread(r3);
        t3.start();
        Thread t4 = new Thread(r4);
        t4.start();
        Thread t5 = new Thread(r5);
        t5.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        gameManager.draw(g2d);
    }

    /**
     * Vẽ đám mây di chuyển
     * */
    Runnable r = new Runnable() {
        @Override
        public void run() {
            while (true) {
                repaint();
                gameManager.moveCloud();
                gameManager.bossBayMove();
                gameManager.bossBayFire();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    /**
     * Di chuyển boss
     * */
    Runnable r2 = new Runnable() {
        @Override
        public void run() {
            while (true) {
                repaint();
                gameManager.moveBoss();
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    /*
    * Boss bắn đạn, đạn bay
    * */
    Runnable r3 = new Runnable() {
        @Override
        public void run() {
            while (true) {
                repaint();
                gameManager.bossFire();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };


    /*
    * Người chơi di chuyển trái phải
    * */
    Runnable r4 = new Runnable() {
        @Override
        public void run() {
            while (GameManager.pauseGame == false) {
                repaint();
                if (flag[KeyEvent.VK_D]) {
                    gameManager.dieuKienTank(Entire.ORIENT_RIGHT);
                    gameManager.diChuyenBackGroundTrai();
                } else if (flag[KeyEvent.VK_A]) {
                    gameManager.dieuKienTank(Entire.ORIENT_LEFT);
                    gameManager.diChuyenBackGroundPhai();
                }
                if (flag[KeyEvent.VK_LEFT]) {
                    gameManager.xoayNong(KeyEvent.VK_LEFT);
                    gameManager.canGoc();
                } else if (flag[KeyEvent.VK_RIGHT]) {
                    gameManager.xoayNong(KeyEvent.VK_RIGHT);
                    gameManager.canGoc();
                }
                gameManager.roiTuDo();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    /*
    * Người chơi bắn đạn, đạn bay
    * */
    Runnable r5 = new Runnable() {
        @Override
        public void run() {
            while (true) {
                repaint();
                if (flag[KeyEvent.VK_SPACE]) {
                    canNong = true;
                    lucF2++;
                    gameManager.canLuc(lucF2);
                }
                if (!flag[KeyEvent.VK_SPACE] && canNong) {
                    gameManager.playerBatDauBan();
                    canNong = false;
                    lucF2 = 0;
                }
                gameManager.playerFire();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        flag[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        flag[e.getKeyCode()] = false;
    }
}
