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
        this(p,false);
    }

    public Cam(PApplet p, boolean initLater) {
        this.p = p;
        if(!initLater) initialize();
    }

    public void initialize(){
        System.out.println("Listing Caputre Devices");
        String[] cameras = Capture.list();
        PApplet.printArray(cameras);

        if(cameras != null && cameras.length > 0){
            capture = new Capture(p, cameras[0]);
            capture.start();
        }
    }

    /***
     *
     * @return the camera Input pointer.
     */
    public PImage getRealtime(){
        if(!ready()) return Renderer.NullImage;
        capture.read();
        return capture;
    }


    /***
     *
     * @return the Picture of the Frame it's called.
     */
    public PImage getImage(){
        if (ready()) {
            capture.read();
            return new PImage(capture.getImage());
        }

        return Renderer.NullImage;

    }

    public boolean ready(){
        if (capture != null && capture.available()) {
            return true;
        }
        return false;
    }

}
