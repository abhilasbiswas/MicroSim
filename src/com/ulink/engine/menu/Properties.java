package com.ulink.engine.menu;

import javax.swing.*;

public class Properties {
    public JPanel main;
    public JLabel info;
    public JButton delete;

    public Properties(int parent_width, int width, int height)
    {
        main.setBounds(parent_width-width,0,width,height);
    }

    public void setSize(int parent_width, int width, int height)
    {
        main.setBounds(parent_width-width,0,width,height);
    }
}
