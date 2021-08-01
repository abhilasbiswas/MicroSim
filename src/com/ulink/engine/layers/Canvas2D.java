package com.ulink.engine.layers;

import com.ulink.engine.Engine;
import com.ulink.engine.Layer;
import com.ulink.engine.utils.Vec2;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import com.ulink.engine.Component;

public abstract class Canvas2D extends Layer {
    public static int size_x=20,size_y=20;

    public static int[][] mask;
    public static int step=20;
    int mx=-1,my=-1;

    public static float s;
    int x1;
    int y1;
    int x2;
    int y2;

    public final static int MODE_SELECT=1;
    public final static int MODE_HOVER=2;

    public int mode=MODE_SELECT;

    public Component selected, hover;


    ArrayList<Component> shapes=new ArrayList<Component>();
    public Canvas2D(Engine display) {
        super(display);
        s=step*engine.scale;
        mask=new int[size_x][size_y];
    }


    void drawGrid()
    {

        graphics.setColor(Color.gray.darker().darker().darker());


        Vec2 start=index2position(0,0);
        Vec2 end=index2position(size_x,size_y);

        x1= start.x>0? (int) start.x : (int) (engine.position.x % s);
        y1= start.y>0? (int) start.y : (int) (engine.position.y % s);
        x2= end.x<engine.width? (int) end.x :engine.width;
        y2= end.y<engine.height? (int) end.y :engine.height;

//        int px= x1;//(int) (engine.position.x%sx)+x1;
//        int py= y1;//(int) (engine.position.y%sy)+y1;
//
//        int endx= x2;//(int) (width+px);
//        int endy= y2;//(int) (height+py);

        for (int i = (int) x1; i<=x2; i+=s)
        {
            graphics.drawLine(i,y1,i,y2);
        }
        for (int i = (int) y1; i<=y2; i+=s)
        {
            graphics.drawLine(x1,i,x2,i);
        }
    }

    @Override
    public void onDraw(Graphics graphics) {

        ((Graphics2D)graphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        drawGrid();

        if (mx>=0&&my>=0&&mx<size_x&&my<size_y) {
            Vec2 v=index2position(mx,my);
            graphics.setColor(Color.red.darker().darker());
            graphics.fillRect((int) v.x + 1, (int) v.y + 1, (int) s, (int) s);
        }
        graphics.setColor(Color.WHITE.darker());

        for (Component shape : shapes)
            shape.draw(this);

        if (hover.hover)
        {
            hover.draw(this);
        }
    }
    public void addComponents(Component... shape)
    {
        Collections.addAll(shapes, shape);
    }

    public void addComponent(Component shape)
    {
        if (shape.hover)
        {
            shapes.add(shape);
            return;
        }
        shapes.add(shape);
        for (int i=shape.px;i<shape.px+shape.width;i++)
        {
            for (int j=shape.py;j<shape.py+shape.height;j++)
                mask[i][j]=shape.id;
        }
    }
    public static boolean isValid(Component component)
    {
//        System.out.println(component.px+"  "+component.py+"  "+component.width+"  "+component.height);
        try {
            if (component.px >= 0 && component.py >= 0) {
                if (component.px + component.width <= mask.length && component.py + component.height <= mask[0].length) {
                    for (int i = component.px; i < component.px + component.width; i++) {
                        for (int j = component.py; j < component.py + component.height; j++) {
                            if (mask[i][j] > 0) {
                                return false;
                            }
                        }
                    }
                    return true;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public Component getComponentById(int id)
    {
        for (Component c:shapes) {
            if (id == c.id)
                return c;
        }
        return null;
    }

    public void remove(Component c)
    {
        if (c!=null){
            try{
                for (int i=c.px;i<c.px+c.width;i++)
                {
                    for (int j=c.py;j<c.py+c.height;j++)
                    {
                        mask[i][j]=0;
                    }
                }
                shapes.remove(c);
            }
            catch (Exception e){}
        }
    }

    public void drawLine(Vec2 a, Vec2 b)
    {
        drawLine(a.x,a.y,b.x,b.y);
    }
    public void drawArc(Vec2 p,int r, int start_angle, int end_angle)
    {
        drawArc(p.x,p.y,r,start_angle,end_angle);
    }
    public void drawRect(Vec2 a, Vec2 b)
    {
        drawRect(a.x,a.y,b.x,b.y);
    }
    public void drawRectFill(Vec2 a, Vec2 b)
    {
        drawRectFill(a.x,a.y,b.x,b.y);
    }

    public void drawCircle(Vec2 p, double radius)
    {
        drawCircle(p.x,p.y,radius);
    }
    public void drawCircleFill(Vec2 p, double radius)
    {
        drawCircleFill(p.x,p.y,radius);
    }
    public void drawPolyline(Vec2... p)
    {
        double[] pts=new double[p.length*2];
        for (int i=0,j=0;i<p.length;i++,j+=2)
        {
            pts[j]= p[i].x;
            pts[j+1]= p[i].y;

        }
        drawPolyline(pts);
    }
    public void drawPolygon(Vec2... p)
    {
        double[] pts=new double[p.length*2];
        for (int i=0,j=0;i<p.length;i++,j+=2)
        {
            pts[j]= p[i].x;
            pts[j+1]= p[i].y;

        }
        drawPolygon(pts);
    }
    public void drawPolygonFill(Vec2... p)
    {
        double[] pts=new double[p.length*2];
        for (int i=0,j=0;i<p.length;i++,j+=2)
        {
            pts[j]= p[i].x;
            pts[j+1]= p[i].y;

        }
        drawPolygonFill(pts);
    }
    public void drawShape(Shape shape)
    {
        shape.draw(this);
    }
    public void drawShapes(Shape... shapes)
    {
        for (Shape shape : shapes)
            drawShape(shape);
    }

    public void drawLine(double x1, double y1, double x2, double y2)
    {
        graphics.drawLine(x(x1), y(y1), x(x2), y(y2));
    }
    public void drawArc(double x, double y, double radius, int start_angle, int end_angle)
    {
        graphics.drawArc(x(x), y(y),(int)(radius*2*engine.scale), (int)(radius*2*engine.scale), start_angle, end_angle);
    }
    public void drawRect(double x1, double y1, double x2, double y2)
    {
        graphics.drawRect(x(x1), y(y1), (int) (engine.scale*(x2-x1)), (int) (engine.scale*(y2-y1)));
    }
    public void drawRectFill(double x1, double y1, double x2, double y2)
    {
        graphics.fillRect(x(x1), y(y1), (int) (engine.scale*(x2-x1)), (int) (engine.scale*(y2-y1)));
    }
    public void drawCircle(double x, double y, double r)
    {
        graphics.drawOval(x(x), y(y), (int)(r*2*engine.scale), (int)(r*2*engine.scale));
    }
    public void drawCircleFill(double x, double y, double r)
    {
        graphics.fillOval(x(x), y(y), (int)(r*2*engine.scale), (int)(r*2*engine.scale));
    }
    public void drawPolyline(double... pts)
    {
        int l=pts.length/2;
        int[] xpts=new int[l];
        int[] ypts=new int[l];

        for (int i=0,j=0; i<pts.length; i+=2,j++)
        {
            xpts[j]= (int) pts[i];
            ypts[j]= (int) pts[i+1];
        }

        graphics.drawPolyline(xpts, ypts,l);
    }
    public void drawPolygon(double... pts)
    {
        int l=pts.length/2;
        int[] xpts=new int[l];
        int[] ypts=new int[l];

        for (int i=0,j=0; i<pts.length; i+=2,j++)
        {
            xpts[j]= (int) pts[i];
            ypts[j]= (int) pts[i+1];
        }

        graphics.drawPolygon(xpts, ypts,l);
    }
    public void drawPolygonFill(double... pts)
    {
        int l=pts.length/2;
        int[] xpts=new int[l];
        int[] ypts=new int[l];

        for (int i=0,j=0; i<pts.length; i+=2,j++)
        {
            xpts[j]= (int) pts[i];
            ypts[j]= (int) pts[i+1];
        }

        graphics.fillPolygon(xpts, ypts,l);
    }
    int x(double x)
    {
        return engine.X(x);
    }
    int y(double y)
    {
        return engine.Y(y);
    }

    public Vec2 index2position(int x, int y)
    {
        float px= (float) ((x*(step*engine.scale))+engine.position.x);
        float py= (float) ((y*(step*engine.scale))+engine.position.y);
        return new Vec2(px, py);
    }
    public Vec2 position2index(int x, int y)
    {
        float px= (float) (x-engine.position.x)/(step*engine.scale);
        float py= (float) (y-engine.position.y)/(step*engine.scale);
        return new Vec2(px, py);
    }

    @Override
    public void onMouseEvent(MouseEvent e) {
        super.onMouseEvent(e);
        Vec2 v=position2index(e.getX(),e.getY());
        mx= (int) v.x;
        my= (int) v.y;
    }

    public static int q(int p)
    {
        return (int) ( (((int)(p/step))*step));
    }
    public static int qi(int p)
    {
        return (int)(p/step);
    }
    public int getId(int mx, int my)
    {
        Vec2 v=position2index(mx,my);
        return mask[(int) v.x][(int) v.y];
    }
}
