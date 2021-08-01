package com.ulink.engine;

import com.ulink.engine.layers.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Main extends Canvas2D{

    public Main(Engine display) {
        super(display);
        init();
    }

    static Application app;
    public static void main(String[] args) {
        app=new Application(800,800);
        app.canvas.addLayer(new Main(app.canvas));
    }

    Component c2;

    public void init()
    {
        c2 = new Component();
        hover=c2;

        app.components.select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hover.hover=false;
                mode=MODE_SELECT;
            }
        });
        app.components.parts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hover.hover=true;
                mode=MODE_HOVER;
            }
        });
        app.properties.delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selected!=null)
                    remove(selected);
            }
        });
    }

    @Override
    public void onUpdate() {
    }

    @Override
    public void onKeyEvent(KeyEvent e) {
        super.onKeyEvent(e);
    }

    @Override
    public void onMouseEvent(MouseEvent e) {
        super.onMouseEvent(e);
//        System.out.println("Mouse: "+e.paramString()+"  "+e.getButton());

        engine.positionOffset(e);
        c2.setIndex((Canvas2D.qi((int) ((e.getX()-engine.position.x)/engine.scale))),  (Canvas2D.qi((int)( (e.getY()-engine.position.y)/engine.scale))));
        if (e.getClickCount()==2)
        {
            switch (mode) {
                case MODE_HOVER: {
                    if (isValid(c2))
                        addComponent(c2.copy());
                    break;
                }
                case MODE_SELECT:{

                    if (selected!=null)
                        selected.select=false;

                    selected=getComponentById(getId(e.getX(),e.getY()));
                    if (selected!=null) {
                        app.properties.info.setText("ID: " + selected.id);
                        selected.select=true;
                    }
                    break;
                }
            }
        }
    }
}
