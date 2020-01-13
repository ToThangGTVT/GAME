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

    /**
     * Mảng các đám mây
     * */
    private ArrayList<Cloud> arrCloud;

    /**
    * Mảng các con boss bay
    * */
    private ArrayList<BossBay> arrBossBay;

    /**
     * mảng các đạn rơi từ boss bay.
     */
    private ArrayList<BulletDrop> arrBulletDrop;

    /**
     * Đối tượng boss là xa tăng.
     */
    private BossTank bossTank;

    /**
     * Mảng chứa đạn của tank boss và player
     */
    private ArrayList<Bullet> arrBullet;

    /**
     * Đối tượng tank người chơi
     * */
    private TankPlayer tankPlayer;

    /**
     * Đối tượng khung cảnh
     * */
    private BackGround backGround;

    /**
     * Kiểm tra tank người chơi có bị rơi khỏi map hay ko
     * */
    private boolean roiTudo;

    /**
     * Đánh dấu vị trí bắt đầu tank người chơi rơi khỏi map
     * */
    private boolean viTriRoi;

    /**
     * Hoanh độ nhỏ nhất cho boss tank xuất hiện lại khi chết
     * */
    private static final int X_MIN_GEN_AFTER_DIE = 460;

    /**
     * Độ rộng của map mà boss tank đứng
     * */
    private static final int WIDTH_OF_MAP_BOSSTANK = 140;

    /**
     * Vị trí hoành độ ban đầu xuất hiện và xuất hiện sau khi chết.
     * */
    private int hoanhDo = (int) (X_MIN_GEN_AFTER_DIE + Math.random() * WIDTH_OF_MAP_BOSSTANK);

    /**
     * Vị trí tung độ ban đầu xuất hiện và xuất hiện sau khi chết.
     * */
    private int tungDo = 240;

    /**
     * Kiểm tra khi người chơi hết mạng và dừng game.
     * */
    private boolean pauseGame;

    /**
     * Kiểm tra khi boss bay thả bom xuống
     * */
    private boolean thaBom = true;

    /**
     * Khởi tạo bảng đếm số
     * */
    private Player player = new Player();


    long tBanBossBay;
    long tPlayerDie;
    long TPlayerDie;

    /**
     * check đạn nổ khi bị bắn trúng
     * */
    private boolean explode = false;

    /**
     * số lượng boss bay trên màn hình
     * */
    private int nBossBay = 2;

    /**
     * thứ tự frame của hiệu ứng đạn nổ
     * */
    private int index = 0;

    /**
     * hoành độ của đạn nổ
     * */
    private int xExplode = 0;

    /**
     * tung độ của đạn nổ
     * */
    private int yExplode = 0;

    /**
     * khởi tạo game
     * */
    public void initGame() {
        backGround = new BackGround();
        arrBossBay = new ArrayList<>();
        arrCloud = new ArrayList<>();
        arrBullet = new ArrayList<>();
        arrBulletDrop = new ArrayList<>();
        initCloud();
        initBullet();
        initBossBay();
        initBossTank();
        initPlayer();
    }

    /**
     * khởi tạo đám mây
     * */
    private void initCloud() {
        Random rnd = new Random();
        for (int i = 0; i < 2; i++) {
            arrCloud.add(new Cloud(rnd.nextInt(500), 30 + i * 120));
        }
    }

    /**
     * khởi tạo đạn
     * */
    private void initBullet() {
        for (int i = 0; i < 2; i++) {
            arrBullet.add(new Bullet());
        }
    }

    /**
     * khởi tạo boss là tank
     * */
    private void initBossTank() {
        bossTank = new BossTank(hoanhDo, tungDo);
    }

    /**
     * khởi tạo boss bay được
     * */
    private void initBossBay() {
        Random rnd = new Random();
        int a = rnd.nextInt(200) + 100;
        for (int i = 0; i < 2; i++) {
            arrBossBay.add(new BossBay(
                    rnd.nextInt(200) + 10,
                    rnd.nextInt(50),
                    a,
                    rnd.nextInt(120) + 80,
                    rnd.nextInt(2 * a) - a));
        }
    }

    /**
    * khởi tạo người chơi
    * */
    public void initPlayer() {
        tankPlayer = new TankPlayer();
        tankPlayer.setToaDo(100, 370);
        setNguoiChoiChet(false);
        backGround = new BackGround();
    }

    /**
     * hàm nhận các phím điều khiển để di chuyển tank người chơi.
     * @param orient hướng di chuyển nhận từ bàn phím
     * */
    public void dieuKienTank(final int orient) {
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

    /**
     * Vẽ các đối tượng
     * @param g2d đối tượng đồ họa
     * */
    public void draw(final Graphics2D g2d) {
        backGround.getBackGround(g2d);
        for (Cloud c : arrCloud) {
            c.draw(g2d);
        }
        bossTank.draw(g2d);
        if (!isNguoiChoiChet()) tankPlayer.draw(g2d);
        arrBullet.get(0).draw(g2d);
        if (!tankPlayer.isPlayerBatDauBan()) {
            arrBullet.get(1).setToaDo(-20, -20);
        }
        arrBullet.get(1).draw(g2d);
        player.drawTableScore(g2d);
        for (BossBay bb : arrBossBay) {
            if (!bb.isTrungDan()) {
                bb.draw(g2d);
            }
        }
        for (BulletDrop bd : arrBulletDrop) {
            if (bd.isVisible()) {
                bd.draw(g2d);
            }
        }
        if (isExplode()) {
            arrBullet.get(0).explosive(g2d, index, xExplode, yExplode);
        }
    }

    /**
     * Xe tăng người chơi bị rơi tự do khi ngoài map
     * */
    public void roiTuDo() {
        if (!isNguoiChoiChet()) {
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
    }

    /**
     * Hàm bắn đạn của boss xe tăng
     * */
    public void bossFire() {
        bossTank.bossFire(bossTank.isKhaiHoa(), arrBullet.get(0), bossTank.getT());
        bossTank.setT(bossTank.getT() + 1);
        TPlayerDie = System.currentTimeMillis();
        if (isNguoiChoiChet()) {
            if (TPlayerDie - tPlayerDie > 3000) {
                initPlayer();
            }
        } else if (arrBullet.get(0).checkDie(tankPlayer)) {
            xExplode = tankPlayer.getX() - 50;
            yExplode = tankPlayer.getY() - 50;
            setExplode(true);
            tankPlayer.setHeart(tankPlayer.getHeart() - 1);
            setNguoiChoiChet(true);
            tPlayerDie = System.currentTimeMillis();
            if (tankPlayer.getHeart() == 0) {
                pauseGame = true;
                tankPlayer = new TankPlayer();
                if (JOptionPane.showConfirmDialog(
                        null,
                        "Thôi đi ngủ",
                        "Gameover", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else {
                    TankPlayer.heart = 5;
                }
            }
        }
    }

    /**
     * Hàm nhận lệnh bắn đạn cho xe tăng người chơi
     * */
    public void playerBatDauBan() {
        tankPlayer.setPlayerBatDauBan(true);
    }

    /**
     * Hàm bắn đạn của tank người chơi
     * */
    public void playerFire() {
        tankPlayer.playerFire(arrBullet.get(1));
        tankPlayer.setT2(tankPlayer.getT2() + 1);
        if (arrBullet.get(1).checkWin(bossTank)) {
            xExplode = bossTank.getX() - 50;
            yExplode = bossTank.getY() - 50;
            setExplode(true);
            tankPlayer.setScore(tankPlayer.getScore() + 1);
            bossTank.setToaDo(-100, -100);
            arrBullet.get(1).setToaDo(-100, -100);
            tPlayerDie = System.currentTimeMillis();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bossTank = new BossTank(hoanhDo, tungDo);
            setNguoiChoiChet(false);
        }
        for (int i = 0; i < nBossBay; i++) {
            if (arrBullet.get(1).checkBanTrungBossBay(arrBossBay.get(i))) {
                for (int j = 1; j >= 0; j--) {
                    if (i == j) {
                        arrBossBay.get(j).setTrungDan(true);
                        arrBossBay.remove(j);
                        nBossBay--;
                        tBanBossBay = System.currentTimeMillis();
                        break;
                    }
                }
            }
        }
    }

    /**
     * Khởi tạo đạn rơi do boss bay thả
     * @param arrBossBay mảng các viên đạn
     * */
    public void initBulletBossBay(final ArrayList<BossBay> arrBossBay) {
        int i = 0;
        while (i < arrBossBay.size()) {
            arrBulletDrop.add(new BulletDrop(
                    arrBossBay.get(i).getToaDoX(),
                    arrBossBay.get(i).getToaDoY()));
            i++;
        }
    }

    /**
     * Hàm di chuyển boss bay theo quỹ đạo nửa elispe
     * */
    public void bossBayMove() {
        if (nBossBay < 2) {
            Random rnd = new Random();
            long TBanBossBay = System.currentTimeMillis();
            int a = rnd.nextInt(200) + 100;
            if (TBanBossBay - tBanBossBay > 5000 && tBanBossBay != 0) {
                arrBossBay.add(new BossBay(
                        rnd.nextInt(200) + 10,
                        rnd.nextInt(50),
                        a,
                        rnd.nextInt(120) + 80,
                        rnd.nextInt(2 * a) - a));
                nBossBay++;
            }
        }
        for (int i = 0; i < nBossBay; i++) {
            arrBossBay.get(i).move();
        }
        if (thaBom) {
            initBulletBossBay(arrBossBay);
        }
        thaBom = false;
    }

    /**
     * Hàm thả bom của boss bay
     * */
    public void bossBayThaBom() {
        bossBayMove();
        for (BulletDrop bd : arrBulletDrop) {
            bd.move();
            if (!isNguoiChoiChet()) {
                if (bd.checkMap(backGround) || bd.checkDie(tankPlayer)) {
                    if (bd.checkDie(tankPlayer)) {
                        setNguoiChoiChet(true);
                        tPlayerDie = System.currentTimeMillis();
                    }
                    bd.setVisible(false);
                    break;
                }
            }
        }
        arrBulletDrop.removeIf(bulletDrop -> !bulletDrop.isVisible());
        if (arrBulletDrop.size() == 0 && arrBossBay.size() != 0) {
            thaBom = true;
            bossBayThaBom();
        }
    }

    /**
     * Hàm di chuyển đám mây theo quỹ đạo ngang
     * */
    public void moveCloud() {
        for (Cloud c : arrCloud) {
            if (c.getSoLanDoiHuong() == 0) {
                c.setSoLanDoiHuong(1);
                c.createOrient();
            }
            c.move();
        }
    }

    /**
     * Hàm di chuyển boss tank theo quỹ đạo ngang và ko rơi khỏi map
     * */
    public void moveBoss() {
        bossTank.createOrient();
        bossTank.move();
    }

    /**
     * Di chuyển khung cảnh sang trái khi người chơi di chuyển tank sang phải
     * */
    public void diChuyenBackGroundTrai() {
        if (!roiTudo) {
            backGround.diChuyenBackGroundTrai();
        }
    }

    /**
     * Di chuyển khung cảnh sang phải khi người chơi di chuyển tank sang trái
     * */
    public void diChuyenBackGroundPhai() {
        if (!roiTudo) {
            backGround.diChuyenBackGroundPhai();
        }
    }

    /**
     * Xoay nòng khi người chơi nhấn phím W và S
     * @param phimAn mã phím ấn của phím W và S
     * */
    public void xoayNong(final int phimAn) {
        tankPlayer.xoayNong(phimAn);
    }

    /**
     * Nhận lực từ phím space
     * @param lucF lực nhận được dựa vào thời gian giữ phím space
     * */
    public void canLuc(int lucF) {
        tankPlayer.setLucF(lucF * 2);
        player.setLucF(lucF * 2);
    }

    /**
     * giới hạn góc quay của nòng tank người chơi
     * */
    public void canGoc() {
        int gocTrungGian = tankPlayer.getAngle();
        if (gocTrungGian > 150) gocTrungGian = 148;
        if (gocTrungGian < 30) gocTrungGian = 30;
         player.setGoc((int) ((gocTrungGian - 27) / 0.6f));
    }

    /**
     * Set frame của hiệu ứng nổ khi đạn bắn trúng
     * */
    public void setIndex(final int index) {
        this.index = index;
    }

    /**
     * Kiểm tra dưng game khi người chơi hết mạng
     * */
    public boolean isPauseGame() {
        return pauseGame;
    }

    /**
     * Set pause khi người chơi hết mạng
     * */
    public void setPauseGame(boolean pauseGame) {
        this.pauseGame = pauseGame;
    }

    /**
     * Kiểm tra khi người chơi bị đạn bắn trúng
     * */
    public boolean isNguoiChoiChet() {
        return tankPlayer.isDie();
    }

    /**
    * Set người chơi bị chết sau khi đạn bắn trúng
     * @param  nguoiChoiChet biến true false khi người chơi chết hoặc sống lại
    * */
    public void setNguoiChoiChet(boolean nguoiChoiChet) {
        tankPlayer.setDie(nguoiChoiChet);
    }


    /**
     * Kiểm tra đạn nổ khi đối tượng bị bắn trúng
     * */
    public boolean isExplode() {
        return explode;
    }

    /**
     * Set đạn nổ khi đối tượng bị đạn bắn trúng
     * */
    public void setExplode(boolean explode) {
        this.explode = explode;
    }
}
