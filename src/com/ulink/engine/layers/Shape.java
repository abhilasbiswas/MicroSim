package com.ulink.engine.layers;

import com.ulink.engine.utils.Vec2;

import java.util.ArrayList;

public class Shape {
    public static final int LINE=0;
    public static final int ARC=8;
    public static final int RECT=2;
    public static final int RECT_FILL=3;
    public static final int CIRCLE=1;
    public static final int CIRCLE_FILL=7;
    public static final int POLYLINE=4;
    public static final int POLYGON=5;
    public static final int POLYGON_FILL=6;
    
    public ArrayList<Integer> cmds=new ArrayList<Integer>();
    public ArrayList<Shape> shapes=new ArrayList<Shape>();
    public Vec2 pos=new Vec2();
    int i=0;

    public Shape()
    {

    }
    public Shape(Vec2 pos)
    {
        this.pos=pos;
    }
    public void set(Shape shape)
    {
        cmds=new ArrayList<>();
        shapes=new ArrayList<>();
        cmds.addAll(shape.cmds);
        Shape sh;
        for(Shape s:shape.shapes)
        {
            sh=new Shape(pos);
            sh.set(s);
            shapes.add(sh);
        }
    }


    public void draw(Canvas2D canvas)
    {
        int a,b,c;
        for (i=0;i<cmds.size();i++)
        {
            switch(cmds.get(i)){
                case LINE:{
                    canvas.drawLine(next()+pos.x,next()+pos.y,next()+pos.x,next()+pos.y) ;
                    break;
                }
                case ARC:{
                    canvas.drawArc(next()+pos.x,next()+pos.y,next(),next(),next()) ;
                    break;
                }
                case RECT:{
                    canvas.drawRect(next()+pos.x,next()+pos.y,next()+pos.x,next()+pos.y);
                    break;
                }
                case RECT_FILL:{
                    canvas.drawRectFill(next()+pos.x,next()+pos.y,next()+pos.x,next()+pos.y);
                    break;
                }
                case CIRCLE:{
                    canvas.drawCircle(next()+pos.x,next()+pos.y,next()) ;
                    break;
                }
                case CIRCLE_FILL:{
                    canvas.drawCircleFill(next()+pos.x,next()+pos.y,next()); ;
                    break;
                }
                case POLYGON:{
                    double[] pts=new double[next()];
                    for (int i=0;i<pts.length;i+=2) {
                        pts[i] = next()+pos.x;
                        pts[i+1] = next()+pos.y;
                    }
                    canvas.drawPolygon(pts);
                    break;
                }
                case POLYGON_FILL:{
                    double[] pts=new double[next()];
                    for (int i=0;i<pts.length;i+=2) {
                        pts[i] = next()+pos.x;
                        pts[i+1] = next()+pos.y;
                    }
                    canvas.drawPolygonFill(pts);
                    break;
                }
                case POLYLINE:{
                    double[] pts=new double[next()];
                    for (int i=0;i<pts.length;i+=2) {
                        pts[i] = next()+pos.x;
                        pts[i+1] = next()+pos.y;
                    }
                    canvas.drawPolyline(pts);
                    break;
                }
            }
        }

        for (Shape shape:shapes)
            shape.draw(canvas);
    }

    int next()
    {
        return cmds.get(++i);
    }

    public void add(int c, double... arg)
    {
        cmds.add(c);
        for (double d:arg)
            cmds.add((int)d);
    }
    public void addLine(Vec2 a, Vec2 b)
    {
        add(LINE,a.x,a.y,b.x,b.y);
    }
    public void addArc(Vec2 a, int r, int start_angle, int end_angle)
    {
        add(ARC,a.x,a.y,r,start_angle,end_angle);
    }
    public void addRect(Vec2 a, Vec2 b)
    {
        add(RECT,a.x,a.y,b.x,b.y);
    }
    public void addRect(int x1, int y1, int x2, int y2)
    {
        add(RECT,x1,y1,x2,y2);
    }
    public void addRectFill(Vec2 a, Vec2 b)
    {
        add(RECT_FILL,a.x,a.y,b.x,b.y);
    }
    public void addRectFill(int x1, int y1, int x2, int y2)
    {
        add(RECT_FILL,x1,y1,x2,y2);
    }
    public void addCircle(Vec2 p, int radius)
    {
        add(CIRCLE,p.x,p.y,radius);
    }
    public void addCircle(int x, int y, int radius)
    {
        add(CIRCLE,x,y,radius);
    }
    public void addCircleFill(Vec2 p, int radius)
    {
        add(CIRCLE_FILL,p.x,p.y,radius);
    }
    public void addCircleFill(int x, int y, int radius)
    {
        add(CIRCLE_FILL,x,y,radius);
    }
    public void addPolyLine(Vec2... p)
    {
        cmds.add(POLYLINE);
        cmds.add(p.length);

        for (Vec2 v:p)
        {
            cmds.add((int) v.x);
            cmds.add((int) v.y);
        }
    }
    public void addPolygon(Vec2... p)
    {
        cmds.add(POLYGON);
        cmds.add(p.length);

        for (Vec2 v:p)
        {
            cmds.add((int) v.x);
            cmds.add((int) v.y);
        }
    }
    public void addPolygonFill(Vec2... p)
    {
        cmds.add(POLYGON_FILL);
        cmds.add(p.length);

        for (Vec2 v:p)
        {
            cmds.add((int) v.x);
            cmds.add((int) v.y);
        }
    }
    public void addShape(Shape shape)
    {
        shape.pos=pos;
        shapes.add(shape);
    }
}
