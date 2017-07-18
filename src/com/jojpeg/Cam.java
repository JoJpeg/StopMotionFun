package com.jojpeg;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.video.Capture;

/**
 * Created by J4ck on 18.07.2017.
 */
public class Cam {
    Capture capture;
    PApplet p;

    public Cam(PApplet p) {
        this.p = p;
        String[] cameras = Capture.list();

        if(cameras != null && cameras.length > 0){
            capture = new Capture(p, cameras[0]);
            capture.start();
        }

    }

    public PImage getRealtime(){
         capture.read();
        return capture;
    }

    public PImage getImage(){
        if (capture != null && capture.available()) {
            capture.read();
            return new PImage(capture.getImage());
        }

        return Renderer.makeImage(new PGraphics(), p);

    }
}
