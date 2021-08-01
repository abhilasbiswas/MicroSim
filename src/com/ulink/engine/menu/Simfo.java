package com.ulink.engine.menu;

import javax.swing.*;

public class Simfo {

    public int height;

    public JPanel main;

    public Simfo(int parent_width, int parent_height, int height) {
        this.height = height;
        setSize(parent_width,parent_height);
    }
    public void setSize(int width, int height) {
        main.setBounds(0,height-this.height,width,this.height);
    }
}
