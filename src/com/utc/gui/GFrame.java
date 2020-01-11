package com.utc.gui;

import javax.swing.*;
import java.awt.*;

public class GFrame extends JFrame {
    public static final int H_FRAME=500;
    public static final int W_FRAME =700;
    public GFrame() throws HeadlessException {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setSize(W_FRAME,H_FRAME);
        setTitle("Bảo vệ bầu trời");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        GPanel gPanel = new GPanel();
        add(gPanel);
        setVisible(true);
    }
}
