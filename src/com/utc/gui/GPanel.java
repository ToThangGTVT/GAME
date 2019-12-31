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
    private TankPlayer tankPlayer;
    private GameManager gameManager = new GameManager();

    public GPanel() {
        setBackground(new Color(0x1B262D));
        setFocusable(true);
        addKeyListener(this);
        gameManager.initGame();
        Thread t = new Thread(r);
        t.start();
        Thread t2 = new Thread(r2);
        t2.start();
        Thread t3 = new Thread(r3);
        t3.start();
        Thread t4 = new Thread(r4);
        t4.start();

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
            while (true) {
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

    Runnable r4 = new Runnable() {
        @Override
        public void run() {
            while (true) {
                repaint();
                if (flag[KeyEvent.VK_RIGHT] || flag[KeyEvent.VK_D]) {
                    gameManager.dieuKienTank(Entire.ORIENT_RIGHT);
                } else if (flag[KeyEvent.VK_LEFT] || flag[KeyEvent.VK_A]) {
                    gameManager.dieuKienTank(Entire.ORIENT_LEFT);
                }
                try {
                    Thread.sleep(15);
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
