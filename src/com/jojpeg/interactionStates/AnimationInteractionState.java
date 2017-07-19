package com.jojpeg.interactionStates;

import com.jojpeg.*;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Created by J4ck on 18.07.2017.
 */
public class AnimationInteractionState extends InteractionState {
    PApplet p;
    Animation animation;
    Renderer renderer;
    Cam cam;

    int onionFront = 1;
    int onionBack = 1;

    int projectionOpacity = 127;
    int onionOpacity = 127;
    boolean play = false;
    boolean project = false;
    boolean onion = false;

    public AnimationInteractionState(Animation animation, Cam cam, Renderer renderer) {
        this.animation = animation;
        this.cam = cam;
        this.renderer = renderer;
    }


    @Override
    public void lateUpdate(PApplet p) {
        if(play) {
            renderer.setFrame(animation.play());
        }
        else {
            renderer.setFrame(animation.current());
            if(project) {
                renderer.setLayer(cam.getRealtime(),1, projectionOpacity);
            }
            drawThumbsBar(p);
        }

        if(onion) {
            int layer = 1;
            for (int i = animation.caretPos - onionBack; i < animation.caretPos + onionFront + 1; i++) {
                PImage onion =   animation.getFrame(i);
                if(onion != null && i != animation.caretPos) {
                    renderer.setLayer(onion, layer, onionOpacity);
                    layer ++;
                }

            }
        }
    }

    @Override
    public void keyReleased(PApplet p, char key) {
        if(p.keyCode == PApplet.LEFT){
            animation.caretLeft();
        }
        if(p.keyCode == PApplet.RIGHT){
            animation.caretRight();
        }

        if(key == ' '){
            play = !play;
            if(play) {
                onion = false;
                p.frameRate(10);
            }
            else {
                p.frameRate(60);
            }
        }

        if(key == 'f'){
            animation.addFrame(cam.getImage());
        }

        if(key == 'r'){
            animation.replaceFrame(cam.getImage());
        }

        if(key == 'p'){
            project = !project;
        }

        if(key == 'o'){
            onion = !onion;
        }

        if(key == 'x'){
            animation.removeFrame(animation.caretPos);
        }

    }

    @Override
    public void update(PApplet p) {

    }

    @Override
    public void save(SaveSystem saveSystem) {

    }

    @Override
    public void load(SaveSystem saveSystem) {

    }

    private void drawThumbsBar(PApplet p){

        PImage[] thumbs = new PImage[7];
        int index = 0;
        for (int i = animation.caretPos - 3; i < animation.caretPos + 3; i++) {
            thumbs[index] = animation.getThumbnail(i);
            index ++;
        }


        PImage box = p.createImage(111,111, p.RGB);
        box.loadPixels();
        for (int j = 0; j < box.pixels.length; j++) {
            box.pixels[j] = p.color(255,0,0);
        }
        box.updatePixels();
        renderer.drawOnCanvas(box,renderer.getPlane().renderWidth / 2 - 60, 15);

        for (int i = 0; i < thumbs.length; i++) {
            PImage t = thumbs[i];
            int x = (renderer.getPlane().renderWidth / 2 - 385) + (i * 110);
            int y = 20;

            if(t == null){
                t = p.createImage(100,100, p.RGB);
                t.loadPixels();
                for (int j = 0; j < t.pixels.length; j++) {
                    t.pixels[j] = p.color(180);
                }
                t.updatePixels();
            }

            else {
                t.resize(100,100);
            }

            renderer.drawOnCanvas(t, x, y);
        }

    }
}
