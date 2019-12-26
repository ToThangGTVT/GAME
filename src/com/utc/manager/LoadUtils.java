package com.utc.manager;

import javax.swing.*;
import java.awt.*;

public class LoadUtils {
    public static Image getImage(String strImage){
        ImageIcon icon = new ImageIcon(
                LoadUtils.class.getResource("/resource/"+strImage));
        return icon.getImage();
    }
}
