package com.utc.background;

import com.utc.manager.LoadUtils;

import java.awt.*;

public class BackGround {
    private Image imgTerrainLeft;
    private Image imgTerrainRight;
    private Image imgTerrainCentter;
    private Image imgBackGround;
    private Image imgBackGround2;
    private Image imgMuiTen;
    private Image imgStone;
    private Image imgMuiTenN;
    private Image imgMoon;
    private int x;

    public BackGround() {
        imgTerrainLeft = LoadUtils.getImage("ZigzagGrass_Half_LeftRound.png");
        imgTerrainCentter =LoadUtils.getImage("ZigzagGrass_HalfSquare.png");
        imgTerrainRight =LoadUtils.getImage("ZigzagGrass_Half_RightRound.png");
        imgBackGround = LoadUtils.getImage("bg_layer4-400.png");
        imgBackGround2 = LoadUtils.getImage("bg_layer2-400.png");
        imgStone = LoadUtils.getImage("forest_pack_94.png");
        imgMuiTen =LoadUtils.getImage("SignRight.png");
        imgMuiTenN = LoadUtils.getImage("SignLeft.png");
        imgMoon = LoadUtils.getImage("moon.png");
    }

    public void getBackGround(Graphics2D g2d){
        g2d.drawImage(imgBackGround2,x/5 - imgBackGround2.getWidth(null),150,null);
        g2d.drawImage(imgBackGround2,x/5,150,null);
        g2d.drawImage(imgBackGround2,x/5 + imgBackGround2.getWidth(null),150,null);
        g2d.drawImage(imgBackGround,x/3-imgBackGround.getWidth(null),80,null);
        g2d.drawImage(imgBackGround,x/3,80,null);
        g2d.drawImage(imgBackGround,x/3+ imgBackGround.getWidth(null),80,null);
        g2d.drawImage(imgMoon,100,100,null);

        /**
         * terraint
         * */
        g2d.drawImage(imgTerrainLeft, 50, 400, null);
        g2d.drawImage(imgTerrainCentter,
                50+imgTerrainLeft.getWidth(null),400,null);
        g2d.drawImage(imgTerrainCentter,
                50+imgTerrainLeft.getWidth(null)*2,400,null);
        g2d.drawImage(imgTerrainRight,
                50+imgTerrainLeft.getWidth(null)+
                        imgTerrainCentter.getWidth(null)*2,
                400,null);

        g2d.drawImage(imgTerrainLeft, 440, 270, null);
        g2d.drawImage(imgTerrainCentter,
                440+imgTerrainLeft.getWidth(null),270,null);
        g2d.drawImage(imgTerrainRight,
                440+imgTerrainLeft.getWidth(null)+imgTerrainCentter.getWidth(null),
                270,null);

        g2d.drawImage(imgStone,190,352,null);
        g2d.drawImage(imgMuiTen,450,250,20,20,null);
        g2d.drawImage(imgMuiTenN,250,381,20,20,null);
    }

    public Rectangle getRet(){
        int w = imgTerrainLeft.getWidth(null)+
                imgTerrainCentter.getWidth(null)*2+
                imgTerrainRight.getWidth(null);
        int h = imgTerrainCentter.getHeight(null);
        return new Rectangle(50,400,w,h);
    }

    public void diChuyenBackGroundTrai(){
        x--;
    }
    public void diChuyenBackGroundPhai(){
        x++;
    }

    public void setX(int x) {
        this.x = x;
    }
}
