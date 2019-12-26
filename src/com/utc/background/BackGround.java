package com.utc.background;

import com.utc.manager.LoadUtils;

import java.awt.*;
import java.util.ArrayList;

public class BackGround {
    private Image imgTerrainLeft;
    private Image imgTerrainRight;
    private Image imgTerrainCentter;
    private Image imgBackGround;
    private Image imgBackGround2;
    private Image imgStone;
    private Image imgSnow;
    private Image imagePlane;


    public BackGround() {
        imgTerrainLeft = LoadUtils.getImage("ZigzagGrass_Half_LeftRound.png");
        imgTerrainCentter =LoadUtils.getImage("ZigzagGrass_HalfSquare.png");
        imgTerrainRight =LoadUtils.getImage("ZigzagGrass_Half_RightRound.png");
        imgBackGround = LoadUtils.getImage("bg_layer4-400.png");
        imgBackGround2 = LoadUtils.getImage("bg_layer2-400.png");
        imgStone = LoadUtils.getImage("forest_pack_94.png");
    }

    public void getBackGround(Graphics2D g2d){

        g2d.drawImage(imgBackGround2,0,150,null);
        g2d.drawImage(imgBackGround2,imgBackGround2.getWidth(null),150,null);
        g2d.drawImage(imgBackGround,0,80,null);
        g2d.drawImage(imgBackGround,imgBackGround.getWidth(null),80,null);

        /**
         * terraint
         * */
        g2d.drawImage(imgTerrainLeft, 50, 400, null);
        g2d.drawImage(imgTerrainCentter,
                50+imgTerrainLeft.getWidth(null),400,null);
        g2d.drawImage(imgTerrainRight,
                50+imgTerrainLeft.getWidth(null)+imgTerrainCentter.getWidth(null),
                400,null);

        g2d.drawImage(imgStone,190,352,null);
    }

}
