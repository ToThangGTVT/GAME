package com.utc.manager;

import com.utc.background.Cloud;
import com.utc.entire.Entire;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameManager {
    private ArrayList<Cloud> arrCloud;
    private ArrayList<Integer> viTriX;
    private ArrayList<Integer> viTriY;
    private int lanLapI;

    public void initGame(){
        arrCloud = new ArrayList<>();
    }

    private void initCloud(){
        if (arrCloud.size()<=1){
            arrCloud.add(new Cloud(45));
        }
    }

    public void draw(Graphics2D g2d){
        Random rnd = new Random();
        int i = 0;
        for (Cloud c: arrCloud) {
            c.setCaoDo(30+i*120);
            if (lanLapI==0){
                c.setHoanhDo(rnd.nextInt(500));
            }
            lanLapI++;
            c.drawCloud(g2d);
            i++;
        }
    }

    public void moveCloud(){
        initCloud();
        for (Cloud c: arrCloud) {
            if (c.getSoLanDoiHuong()==0){
                c.setSoLanDoiHuong(1);
                c.createOrient();
            }
            c.move();
        }
    }
}
