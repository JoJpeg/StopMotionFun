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

    String[] camList;


    public Cam(PApplet p) {
        this(p,false);
    }



    public Cam(PApplet p, boolean initLater) {
        this.p = p;
        if(!initLater) initialize();
    }

    public String[] getCamListCached(){
        if(camList == null) getCamList();
        return camList;
    }

    public String[] getCamList(){
        this.camList = Capture.list();
        return camList;
    }

    public void setCam(String cam){
        int index = 0;

        String[] camListCached = getCamListCached();
        for (int i = 0; i < camListCached.length; i++) {
            String camName = camListCached[i];
            if(camListCached[i].equals(camName)){
                index = i;
                break;
            }
        }
        initialize(index);
    }

    public void initialize(){
        initialize(0);
    }

    public void initialize(int index){
//        System.out.println("Listing Caputre Devices");
        String[] cameras = getCamListCached();
//        PApplet.printArray(cameras);

        if(cameras != null && cameras.length > 0){
            if(capture != null){
                capture.stop();
            }
            capture = new Capture(p, cameras[index]);
            capture.start();
        }
        System.out.println("Cam Init Done");
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
