package com.ulink.engine;

import com.ulink.engine.utils.Vec2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public abstract class Layer {
    protected Graphics graphics;

    protected Engine engine;
//    protected BufferedImage canvas;

    public int width,height;
    public Layer(Engine display)
    {
        this.engine =display;
        graphics=display.graphics;
        width=engine.width;
        height=engine.height;
//        canvas=new BufferedImage(engine.width ,engine.height,BufferedImage.TYPE_INT_ARGB);
//        graphics=canvas.getGraphics();

    }

    public Layer(Engine display,int width, int height)
    {
        this.engine =display;
//        main_canvas=display.graphics;
        this.width=width;
        this.height=height;
//        canvas=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
//        graphics=canvas.getGraphics();

    }

    public void update()
    {
        onUpdate();
//        graphics.clearRect(0,0,width,height);
        onDraw(graphics);
//        main_canvas.drawImage(canvas,0,0,null);
    }
    public void onKeyEvent(KeyEvent e)
    {}
    public void onMouseEvent(MouseEvent e)
    {}
    public abstract void onUpdate();
    public abstract void onDraw(Graphics graphics);

}
