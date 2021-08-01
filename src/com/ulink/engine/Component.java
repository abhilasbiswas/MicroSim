package com.ulink.engine;

import com.ulink.engine.layers.Canvas2D;
import com.ulink.engine.layers.Shape;
import com.ulink.engine.utils.Vec2;

import java.awt.*;

public class Component {

    public boolean hover=false;
    public static int ID=1;
    public int id;
    public int px,py;
    public int width,height;
    public Shape shape;
    public Vec2 position=new Vec2();
    int x1,y1,x2,y2;
    public boolean select;

    public Component(){
        id=ID++;
        px=0;
        py=0;
        width=4;
        height=4;
        shape=new Shape(position);
        shape.addCircle(0,0,40);
        setPosition(0,0);
    }

    public Component(Component c)
    {
        id=ID++;
        px=c.px;
        py=c.py;
        width=c.width;
        height=c.height;
        position.set(c.position);
        x1=c.x1;
        x2=c.x2;
        y1=c.y1;
        y2=c.y2;
        shape=new Shape(position);
        shape.set(c.shape);
    }

    public void draw(Canvas2D canvas2D)
    {
        if (!hover)
        {
            if (select)
            {

                canvas2D.graphics.setColor(Color.GRAY.darker().darker());
                canvas2D.drawRect(x1, y1, x2, y2);
                canvas2D.graphics.setColor(Color.WHITE);
            }
            else
            canvas2D.graphics.setColor(Color.WHITE.darker());
        }
        else if (Canvas2D.isValid(this)) {
            canvas2D.graphics.setColor(Color.GRAY.darker().darker());
            canvas2D.drawRect(x1, y1, x2, y2);
            canvas2D.graphics.setColor(Color.WHITE.darker());
        }
        else
        {
            canvas2D.graphics.setColor(Color.red.darker().darker());
            canvas2D.drawRect(x1, y1, x2, y2);
        }
        shape.draw(canvas2D);
    }

    public void setPosition(int x, int y)
    {
        px=x/Canvas2D.step;
        py=y/Canvas2D.step;
        x1=x;
        y1=y;

        position.set(x1,y1);
        x2=x1+width*Canvas2D.step;
        y2=y1+height*Canvas2D.step;
    }
    public void setIndex(int x, int y)
    {
        px=x;
        py=y;
        x1=px*Canvas2D.step;
        y1=py*Canvas2D.step;

        position.set(x1,y1);
        x2=x1+width*Canvas2D.step;
        y2=y1+height*Canvas2D.step;
    }
    public Component copy()
    {
        return new Component(this);
    }
}
