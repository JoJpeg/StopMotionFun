package com.jojpeg;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import sun.util.resources.cldr.ar.CalendarData_ar_YE;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by J4ck on 12.07.2017.
 */
public class Renderer {
    public int rotation = 0;

    public boolean black = false;

    QuadGrid plane;
    public static PImage NullImage;
    PApplet p;
    PGraphics canvas;

    ArrayList<Layer> layers = new ArrayList<>();

    class Layer{
        PImage frame;
        int opacity = 255;
    }

    public Renderer(PApplet p, int width, int height) {

        this.p = p;
        // renderWidth, renderHeight, nbr slices accross, nbr slices down

        plane = new QuadGrid(width, height, 10, 10);
        plane.setCorner(0,0,0);
        plane.setCorner(1, p.width,0);
        plane.setCorner(2, p.width, p.height);
        plane.setCorner(3,0, p.height);
        plane.setShift(p.width/2, p.height/2);

        canvas = p.createGraphics(plane.renderWidth, plane.renderHeight);
        canvas.beginDraw();
        canvas.clear();
        canvas.endDraw();
        NullImage = makeImage((PGraphics) NullImage, p);
        setFrame(NullImage);
    }

    public void draw(PApplet p){

        //TODO: Rotate

        if(black){
            p.background(0);
            return;
        }

        if(layers.size() == 0 || layers.get(0) == null) {
            setFrame(NullImage);
            System.out.println("Set Base to NullFrame");
        }
        p.background(137);

        for (Layer layer : layers) {
            if(layer.frame != null) {
                p.tint(255, layer.opacity);
                plane.draw(p, layer.frame);
            }
        }

        p.tint(255,255);
        if(canvas != null) {
            plane.draw(p, canvas);
            if (canvas.pixels != null) {
                canvas.beginDraw();
                canvas.clear();
                canvas.endDraw();
            }
        }
        p.strokeWeight(5);
        p.point(plane.model.shift[0], plane.model.shift[1]);
//        Layer base = layers.get(0);
//        layers.clear();
//        layers.add(base);
    }

    public static PImage makeImage(PGraphics img, PApplet p) {
        img = p.createGraphics(640, 480, p.P2D);
        img.beginDraw();
        img.background(255, 255, 200);
        img.fill(255, 200, 200);
        img.textSize(10);
        img.noSmooth();
        int c = 0;
        for (int x = 0; x < img.width; x += 40) {
            for (int y = 0; y < img.height; y += 40){
                if ( c++ % 2 == 0 ) {
                    img.stroke(168, 0, 0);
                    img.rect(x, y, 40, 40);
                }
                img.fill(0);

                img.text( x/40 + " | " + y/40 , x ,y + 40);
                img.fill(255, 200, 200);
            }
            c++;
        }
        img.endDraw();
        return img;
    }

    public void setFrame(PImage frame){
        if(frame != null) setLayer(frame,0,255);
    }

    public void setLayer(PImage frame, int layerIndex, int opacity) {
        Layer layer = new Layer();
        layer.frame = frame;
        layer.opacity = opacity;
        if(layers.size() > 0 && layerIndex < layers.size()) {
            this.layers.set(layerIndex, layer);
        }
        else layers.add(layer);
    }

    public void drawOnCanvas(PImage frame, int x, int y){
        if(this.layers.size() == 0 || this.layers.get(0) == null){
            setFrame(NullImage);
            return;
        }
//        if(canvas.pixels != null) canvas.clear();
        canvas.beginDraw();
        canvas.image(canvas,0,0);
        canvas.image(frame,x,y);
        canvas.endDraw();
    }

    public void rotate() {
        rotation += 90;
        rotation = rotation % 360;
    }

    public QuadGrid getPlane() {
        return plane;
    }
}
