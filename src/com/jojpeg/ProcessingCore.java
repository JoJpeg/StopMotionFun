package com.jojpeg;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

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
        size(1600, 1000, P2D);
//        fullScreen();
    }

    public void setup() {
        saveSystem = new SaveSystem(System.getProperty("user.dir"));
        renderer = new Renderer(this);
    }


    public void draw() {
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
