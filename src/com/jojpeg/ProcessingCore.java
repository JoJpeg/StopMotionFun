package com.jojpeg;

import com.jojpeg.interactionStates.AnimationInteractionState;
import com.jojpeg.interactionStates.InteractionState;
import com.jojpeg.interactionStates.ProjectionInteractionState;
import processing.core.PApplet;
import processing.core.PGraphics;

import java.io.File;

/**
 * Created by J4ck on 12.07.2017.
 */

public class ProcessingCore extends PApplet {
    SaveSystem saveSystem;

    Renderer renderer;
    PGraphics img;

    Animation animation;
    Cam cam;

    InteractionState currentInteractionState;
    ProjectionInteractionState projectionInput;
    AnimationInteractionState animationInput;

    @Override
    public void settings() {
        size(1200, 800, P2D);
//        fullScreen();
    }

    public void setup() {

        cam = new Cam(this);
        animation = new Animation(this);

//        animation.addFrameAtPosition(loadImage("frame (1).gif"), 0);
//        animation.addFrameAtPosition(loadImage("frame (2).gif"), 1);
//        animation.addFrameAtPosition(loadImage("frame (3).gif"), 2);

        saveSystem = new SaveSystem(this);
        renderer = new Renderer(this, width, height);
        projectionInput = new ProjectionInteractionState(renderer);
        animationInput = new AnimationInteractionState(this, animation, cam, renderer);
        currentInteractionState = animationInput;
        renderer.getPlane().UpdateCorners();
    }


    public void draw() {
//        translate(width/2, height/2);
        rotate(rotation);
//        image(animation.play(), 0 ,0);
//        renderer.frame = animation.play();
        currentInteractionState.update(this);
        renderer.draw(this);
        currentInteractionState.lateUpdate(this);
        rotate(0);
    }


    int rotation = 0;
    public void keyReleased() {
        if(keyCode == TAB){
            currentInteractionState = animationInput;
        }
        if(key == 'm'){
            currentInteractionState = projectionInput;
        }

        if(key =='c'){
            rotation += 90;
            rotation = rotation % 360;
        }

        currentInteractionState.keyReleased(this.keyCode, key);

        if (key == 's'){
            currentInteractionState.save(saveSystem);
            return;
        }

        if (key == 'l'){
//            renderer.getPlane().model = (QuadGrid.QuadModel)saveSystem.load(renderer.getPlane().model);
            currentInteractionState.load(saveSystem);
            return;
        }

        if(key == 'h'){
            renderer.black = !renderer.black;
            return;
        }

    }
}
