package com.jojpeg;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

import java.util.ArrayList;

/**
 * Created by J4ck on 12.07.2017.
 */

public class ProcessingCore extends PApplet {
    SaveSystem saveSystem;

    Renderer renderer;
    PImage errorImage;

    PGraphics img;
    int state = 1;

    @Override
    public void settings() {
        size(600, 500, P2D);
//        fullScreen();
    }

    ArrayList<PImage> initFrames = new ArrayList<>();
    Animation animation = new Animation(this);
    public void setup() {
        animation.addFrame(loadImage("frame (1).gif"), 0);
        animation.addFrame(loadImage("frame (2).gif"), 1);
        animation.addFrame(loadImage("frame (3).gif"), 2);

        initFrames.add(loadImage("frame (1).gif"));
        initFrames.add(loadImage("frame (2).gif"));
        initFrames.add(loadImage("frame (3).gif"));


        animation.moveFrame(2, 0);

        saveSystem = new SaveSystem(System.getProperty("user.dir"));
//        renderer = new Renderer(this);
    }


    public void draw() {
        int i = 0;
        for (PImage frame : initFrames){
            image(frame, (frame.width * i) , 0);
            i++;
        }
        i = 0;

        for (PImage frame : animation.frames){
            image(frame, (frame.width * i), frame.height);
            i++;
        }



        /*
        renderer.draw(this);

        if(mousePressed){
            if(state <= 3) {
                renderer.getPlane().getCornersX()[state] += mouseX - pmouseX;
                renderer.getPlane().getCornersY()[state] += mouseY - pmouseY;
            }
            else{
                if(state == 4){
                    renderer.getPlane().model.shift[0] = mouseX;
                    renderer.getPlane().model.shift[1] = mouseY;
                    println(renderer.getPlane().model.shift[0]);
                }
            }
            renderer.getPlane().UpdateCorners();
        }
        */
    }

    public void keyReleased() {
        if (key >= '1' && key <= '5')
            state = (key - '0') - 1;
        if (key == 's'){
            saveSystem.save(renderer.plane.model);
        }
        if (key == 'l'){
            renderer.getPlane().model = (QuadGrid.QuadModel)saveSystem.load(renderer.getPlane().model);
            renderer.getPlane().UpdateCorners();
        }
        if(key == 'h'){
            renderer.black = !renderer.black;
        }
        if(key == 'f'){
            renderer.snapFrame();
        }
    }

    public void SaveData(){

    }

}
