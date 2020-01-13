package com.utc.modal;

import com.utc.manager.LoadUtils;

import java.awt.*;

public class Player {
    private Image imgHeart = LoadUtils.getImage("platformPack_item017.png");
    private Image imgTankFired = LoadUtils.getImage("tanks_tankDesert1.png");
    private int lucF;
    private int goc = 120;

    public void drawTableScore(Graphics2D g2d) {
        g2d.setColor(new Color(0x50B450));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(5, 5, 200, 10);
        g2d.fillRect(5, 5, this.lucF, 10);

        g2d.setColor(new Color(0xB49B4C));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(5, 20, 200, 10);
        if (this.goc > 90) {
            g2d.fillRect(105, 20, this.goc - 100, 10);
        } else {
            g2d.fillRect(this.goc, 20, 100 - this.goc, 10);
        }
        g2d.drawImage(imgTankFired, 7, 63, 21, 17, null);

        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(imgHeart, -5, 25, null);
        g2d.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        g2d.setColor(new Color(0xD5D5D5));
        g2d.drawString(String.valueOf(TankPlayer.heart), 35, 53);
        g2d.drawString(String.valueOf(TankPlayer.score), 35, 77);
    }

    /**
     * Nhận lực F để tạo tốc độ cho đạn
     * */
    public int getLucF() {
        return lucF;
    }

    /**
     * SET lực F để tạo tốc độ cho đạn
     * */
    public void setLucF(int lucF) {
        this.lucF = lucF;
    }

    public int getGoc() {
        return goc;
    }

    public void setGoc(int goc) {
        this.goc = goc;
    }
}
