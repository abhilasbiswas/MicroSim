package com.ulink.engine;

import com.ulink.engine.menu.Components;
import com.ulink.engine.menu.Properties;
import com.ulink.engine.menu.Simfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Application {
    public JFrame frame;
    public Engine canvas;
    Simfo simfo;
    public Components components;
    public Properties properties;
    int tool_height=200;
    int tool_width1=100;
    int tool_width2=100;

    int width,height;

    public Application(int w, int h)
    {
        this.width=w;
        this.height=h;
        frame=new JFrame("MicroSim");
        canvas = new Engine(width-tool_width1-tool_width2, height-tool_height);
        canvas.x_offset=tool_width1;
        simfo = new Simfo(width,height,tool_height);
        components = new Components(tool_width1,height-tool_height);
        properties = new Properties(width,tool_width2,height-tool_height);
        frame.setLayout(null);

        frame.getContentPane().add(canvas);
        frame.getContentPane().add(simfo.main);
        frame.getContentPane().add(components.main);
        frame.getContentPane().add(properties.main);

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                System.out.println("changing...");
                Application.this.width=frame.getWidth();
                Application.this.height=frame.getHeight();

                canvas.setSize(width-tool_width1-tool_width2,height-tool_height);
                simfo.setSize(width,height);
                components.setSize(tool_width1,height-tool_height);
                properties.setSize(width,tool_width2,height-tool_height);

            }
        });



        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(width,height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
