package com.ulink.engine;

import com.ulink.engine.layers.Canvas2D;
import com.ulink.engine.menu.Components;
import com.ulink.engine.menu.Properties;
import com.ulink.engine.menu.Simfo;
import com.ulink.engine.utils.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class Engine extends JPanel{


    public int width=640,height=480;
    public Vec2 offset=new Vec2();

    BufferedImage canvas;
    Graphics graphics;

    boolean wait=false;

    boolean RUN=true;
    public static double t1,t2,t,dt, fix_dt=20;
    public static double time_scale=0.1f;
    boolean dt_fix=true;

    public boolean external_frame=false;
    public int anti_alias=0; //2^x
    public int anti_alias_net;
    public int a_width;
    public int a_height;
    public int x_offset,y_offset;

    public float scale=2;
    public Vec2 position=new Vec2();

    public ArrayList<Layer> layers=new ArrayList<>();

    public Engine(int width, int height)
    {
        setSize(width, height);
        initFrame();
        initEvent();

        if (wait)
        {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void initAntiAlias()
    {
        anti_alias_net= (int) Math.pow(2,anti_alias);
        a_width= (int) (width*anti_alias_net);
        a_height= (int) (height*anti_alias_net);
        pixels=new int[a_height][a_width];
    }

    boolean SETTING_SIZE=false;
    public void setSize(int width, int height)
    {
        SETTING_SIZE=true;
        this.width=width;
        this.height=height;
        setBounds(x_offset,y_offset,width,height);

        canvas=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        graphics=canvas.getGraphics();
        ((Graphics2D)graphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D)graphics).setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);

        for (Layer layer:layers) {
            layer.graphics=graphics;
            layer.width=width;
            layer.height=height;
        }
        SETTING_SIZE=false;
    }

    public void initFrame()
    {
        t1=System.currentTimeMillis();

//        position.set(width/2,height/2);
    }

    @Override
    public void paintComponent(Graphics g) {
        if (SETTING_SIZE)
            return;
        super.paintComponent(g);
        if (canvas!=null)
        {
            pixels=new int[a_height][a_width];
            t2=System.currentTimeMillis();
            if (dt_fix)
            dt=fix_dt;
            else
            dt=(t2-t1);
            dt*=time_scale;
            t+=dt;
            t1=t2;
            try {
                for (Layer layer:layers) {
                        layer.update();
                }
            }
            catch (Exception e){}
//            graphics.drawString((int)((1000/Engine.dt))+"",10,20);
            if (external_frame) {
                drawFrame();
//                System.out.println("Drawing...");
            }
            g.drawImage(canvas,0,0,null);

            graphics.clearRect(0,0, width, height);
//            if (RUN)
            //            System.out.println("FPS: "+(1000/dt)*time_scale);
        }
        repaint();

    }


    public void initEvent()
    {

        System.out.println("Key pressed ");
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                for(Layer layer:layers)
                    layer.onMouseEvent(e);
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                for(Layer layer:layers)
                    layer.onMouseEvent(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                for(Layer layer:layers)
                    layer.onMouseEvent(e);
            }

        });

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                for(Layer layer:layers)
                    layer.onKeyEvent(e);
            }
        });

        setFocusable(true);
        requestFocus();
    }



    public void play()
    {
        RUN=true;
        t1=System.currentTimeMillis();
        repaint();
    }
    public void pause()
    {
        RUN=false;
    }

    public void addLayer(Layer... layer)
    {
        layers.addAll(Arrays.asList(layer));
    }

    int[][] pixels;
    public void drawFrame()
    {
        processPixels();
        int offset= anti_alias_net,p;
        int shift=anti_alias*2;
        for (int y=0,yy=0;y<height;y++,yy+=offset)
        {
            for (int x=0,xx=0;x<width;x++,xx+=offset)
            {
                p=pixels[yy][xx]>>shift;
                if (p>0) {
                    graphics.setColor(new Color(p, p, p));
                    drawPoint(x, y);
                }
            }
        }
    }

    public void processPixels()
    {
        int space=0,offset;
        for (int i=1;i<=anti_alias;i++)
        {
            space = (int) Math.pow(2, i);
            offset = (int) Math.pow(2, i-1);

            for (int y = 0; y < a_height; y++)
            {
                for (int x1=0;x1<a_width;x1+=space)
                {
                    pixels[y][x1]+=pixels[y][x1+offset];
                }
            }
        }
        int x_offset= anti_alias_net;
        for (int i=1;i<=anti_alias;i++)
        {
            space = (int) Math.pow(2, i);
            offset = (int) Math.pow(2, i-1);

            for (int x = 0; x < a_width; x+=x_offset)
            {
                for (int y1=0;y1<a_height;y1+=space)
                {
                    pixels[y1][x]+=pixels[y1+offset][x];
                }
            }
        }
    }


    double d;
    int l;
    float ix,iy;
    float xp,yp;

    public void drawLine(Vec2 a, Vec2 b)
    {
        d=a.distance(b);
        l= (int) (d*anti_alias_net);
        ix= (float) ((b.x-a.x)/d);
        iy= (float) ((b.y-a.y)/d);
        xp= (float) (a.x*anti_alias_net);
        yp= (float) (a.y*anti_alias_net);

        for (int i=0;i<anti_alias_net;i++)
        {
            for (int j=0;j<anti_alias_net;j++)
            {
                drawLineX(xp+i,yp+j);
            }
        }

    }
    private void drawLineX(float x1, float y1)
    {
        float x=x1, y=y1;

        for (int i=0;i<l;i++,x+=ix,y+=iy)
        {
            if (y>=0&&x>=0&&y<a_height&&x<a_width)
            pixels[(int) y][(int) x]=200;
        }
    }

    public void drawPoint(float x, float y)
    {
        graphics.drawLine((int)x,(int)y,(int)x,(int)y);
    }


    public Vec2 transform(Vec2 v)
    {
        return v.mul(scale).add(position);
    }
    public void setScale(float scale)
    {
        this.scale = scale;
        Canvas2D.s=(float)Canvas2D.step*scale;
    }
    public int X(double x)
    {
        return (int) ((x*scale)+position.x);
    }
    public int Y(double y)
    {
        return (int) ((y*scale)+position.y);

    }

    public boolean move=false;
    int px,py,mx,my;

    public void positionOffset(MouseEvent e)
    {
        if (e.getButton()==1)
        {
            px = e.getX();
            py = e.getY();
            mx= (int) position.x;
            my= (int) position.y;
            move=true;
        }
        else if (move&&e.getModifiers()== InputEvent.BUTTON1_MASK) {
            position.set(mx+e.getX()-px,my+e.getY()-py);
        }
        else
            move=false;
    }

}
