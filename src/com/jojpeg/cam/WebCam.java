
package com.jojpeg.cam;

import com.jojpeg.Renderer;
import processing.core.PApplet;
import processing.core.PImage;
//import processing.video.Capture;

//TODO:
// the JNA lib inside the WebCam Video Library collides with the JNA of the Canon Lib

public class WebCam {
  /*
    Capture capture;
    PApplet p;

    String[] camList;

    public WebCam(PApplet p) {
        this(p,true);
    }

    public WebCam(PApplet p, boolean initLater) {
        this.p = p;
        if(!initLater) initialize();
    }

    public String[] getAvailableCamsCached(){
        if(camList == null) getCamList();
        return camList;
    }

    public String[] getCamList(){
        this.camList = Capture.list();
        return camList;
    }

    public void setCam(String cam){
        int index = 0;

        String[] camListCached = getAvailableCamsCached();
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
        String[] cameras = getAvailableCamsCached();
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

    public PImage getRealtime(){
        if(!ready()) return Renderer.NullImage;
        capture.read();
        return capture;
    }

    public PImage getFrame(){
        if (ready()) {
            capture.read();
            return new PImage(capture.getFrame());
        }

        return Renderer.NullImage;

    }

    public boolean ready(){
        if (capture != null && capture.available()) {
            return true;
        }
        return false;
    }
    */
}
