package com.ulink.engine.menu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Components {
    public JPanel main;
    public JButton select;
    public JButton parts;

    public Components(int width, int height)
    {
        main.setBounds(0,0,width,height);

    }
    public void setSize(int width, int height)
    {
        main.setBounds(0,0,width,height);
    }
}
