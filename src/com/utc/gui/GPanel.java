package com.utc.gui;

import com.utc.background.BackGround;
import com.utc.background.Cloud;
import com.utc.manager.GameManager;

import javax.swing.*;
import java.awt.*;

public class GPanel extends JPanel implements Runnable {
    private  GameManager gameManager = new GameManager();
    public GPanel() {
        setBackground(new Color(0x1B262D));
        gameManager.initGame();
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        BackGround backGround = new BackGround();
        backGround.getBackGround(g2d);
        gameManager.draw(g2d);
    }

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
}
