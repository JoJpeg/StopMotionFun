package com.jojpeg;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.video.Capture;

/**
 * Created by J4ck on 12.07.2017.
 */
public class Renderer {
    boolean black = false;
    Capture cam;
    PImage frame;
    QuadGrid plane;
    PImage errorImage;

    public Renderer(PApplet p) {
        String[] cameras = Capture.list();
        if(cameras != null && cameras.length > 0){
            cam = new Capture(p, cameras[0]);
            cam.start();
        }
        // width, height, nbr slices accross, nbr slices down
        plane = new QuadGrid(640, 480, 10, 10);

        errorImage = makeImage((PGraphics)errorImage, p);
    }

    public void draw(PApplet p){
        if(black){
            p.background(0);
            return;
        }

        if (cam != null && cam.available()) {
            cam.read();
//            frame = cam;
        }

        if(cam == null || !cam.available()) frame = errorImage;
        p.background(137);

        plane.draw(p, frame);

        p.strokeWeight(5);
        p.point(plane.model.shift[0], plane.model.shift[1]);
    }

    public PImage makeImage(PGraphics img, PApplet p) {
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

    public void snapFrame(){
        frame = cam.get();
    }

    public QuadGrid getPlane() {
        return plane;
    }
}
