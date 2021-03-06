package com.utc.modal;

import com.utc.background.BackGround;
import com.utc.entire.Tank;
import com.utc.manager.LoadUtils;


import java.awt.*;
import java.util.concurrent.TimeUnit;

public class BossTank extends Tank {
    private Bullet bullet;
    private Image imgBanhXe = LoadUtils.getImage("tanks_tankTracks1.png");
    private Image imgThanXe = LoadUtils.getImage("tanks_tankDesert_body1.png");
    private boolean khaiHoa = true;
    private int t;

    public BossTank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void createOrient() {
        super.createOrient();
    }

    @Override
    public void move() {
        super.move();
        if (x <= 460 || x >= 600) {
            doiNguocHuong();
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        xoayNong(LoadUtils.convertToBufferedImage(
                LoadUtils.getImage("tanks_turret1.png")), g2d, x, y - 20, Bullet.getGocBan());
        g2d.drawImage(imgBanhXe, x - 15, y + 13, null);
        g2d.drawImage(imgThanXe, x - 17, y + 1, null);
    }

    public Rectangle getRet() {
        int w = imgThanXe.getWidth(null);
        int h = imgThanXe.getHeight(null);
        return new Rectangle(x, y, w, h);
    }

    public void bossFire(boolean khaiHoa, Bullet bullet, int t) {
        if (khaiHoa) {
            t = 0;
            bullet.createOrient();
            int k = Bullet.gocBan;
            bullet.setvBullet(300);
            bullet.setToaDo(
                    (int) (this.x - 35 * Math.cos(Math.toRadians(k))),
                    (int) (this.y - 35 * Math.sin(Math.toRadians(k))));
        }
        t++;
        bullet.setT(t);
        this.khaiHoa = false;
        bullet.move();
        if (bullet.checkMap(new BackGround())) {
            this.khaiHoa = true;
            this.t = 0;
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isKhaiHoa() {
        return khaiHoa;
    }

    public void setKhaiHoa(boolean khaiHoa) {
        this.khaiHoa = khaiHoa;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }
}
