package com.ulink.engine.utils;

public class Vec2 {
    public double x,y;
    public Vec2()
    {
        this.x=0;
        this.y=0;
    }
    public Vec2( double x,  double y)
    {
        this.x=x;
        this.y=y;
    }


    public Vec2 copy()
    {
        return new Vec2(x,y);
    }
    public void set(Vec2 v)
    {
        x=v.x;
        y=v.y;
    }
    public void set(double x, double y)
    {
        this.x=x;
        this.y=y;
    }

    public static Vec2 polar(double l, double a)
    {
        return new Vec2((l*Math.cos(a)),(l*Math.sin(a)));
    }
    public static Vec2 getPolar(Vec2 v)
    {
        return new Vec2(Math.sqrt(v.x*v.x+v.y*v.y),Math.atan2(v.y,v.x));
    }
    public  double magnitude()
    {
        return  Math.sqrt(x*x+y*y);
    }
    public static  double magnitude(Vec2 v)
    {
        return  Math.sqrt(v.x*v.x+v.y*v.y);
    }
    public  double angle()
    {
        return  Math.atan2(y,x);
    }
    public static  double angle(Vec2 v)
    {
        return  Math.atan2(v.y,v.x);
    }
    public void addSelf(Vec2 a)
    {
        x+=a.x;
        y+=a.y;
    }
    public Vec2 add(Vec2 a)
    {
        return new Vec2(a.x+x,a.y+y);
    }
    public Vec2 add( double x1,  double y1)
    {
        return new Vec2(x1+x,y1+y);
    }

    public static Vec2 add(Vec2 a, Vec2 b)
    {
        return new Vec2(a.x+b.x,a.y+b.y);
    }
    public Vec2 sub(Vec2 a)
    {
        return new Vec2(x-a.x,y-a.y);
    }
    public static Vec2 sub(Vec2 a, Vec2 b)
    {
        return new Vec2(a.x-b.x,a.y-b.y);
    }
    public static Vec2 scale(Vec2 a,  double m)
    {
        return new Vec2(a.x*m,a.y*m);
    }
    public Vec2 mul( double m)
    {
        return new Vec2(x*m,y*m);
    }
    public static Vec2 mul(Vec2 a,  double m)
    {
        return new Vec2(a.x*m,a.y*m);
    }
    public static Vec2 div(Vec2 a,  double m)
    {
        return new Vec2(a.x/m,a.y/m);
    }

    public static Vec2 mul2D(Vec2 a, Vec2 b)
    {
         double angle=a.angle()+b.angle();
         double len=a.magnitude()*b.magnitude();
        return Vec2.polar(len,angle);//new Vec2( (len*Math.cos(angle)), (len*Math.sin(angle)),0);
    }

    public void rotate(double a)
    {
        Vec2 v=getPolar(this);
        set(polar(v.x,v.y+a));
    }

    public  double distance(Vec2 b)
    {
         double xx=b.x-x;
         double yy=b.y-y;
        return   Math.sqrt(xx*xx+yy*yy);
    }

    public static Vec2 complex(double x)
    {
        return new Vec2( Math.cos(x), Math.sin(x));
    }

    public static Vec2 interpolate(Vec2 a, Vec2 b,  double current,  double total) {
        return a.sub(b).mul(current / total).add(b);
    }

    public void log(String cmt)
    {
        System.out.println(cmt+" x:"+x+" y:"+y);
    }
}

