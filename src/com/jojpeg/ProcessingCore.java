package com.jojpeg;

import com.jojpeg.input.Action;
import com.jojpeg.input.Input;
import com.jojpeg.input.KeyBoardInput;
import com.jojpeg.controllers.AnimatingController;
import com.jojpeg.controllers.Controller;
import com.jojpeg.controllers.ModeController;
import com.jojpeg.controllers.ProjectionController;
import com.jojpeg.input.NumPadInput;
import processing.core.PApplet;

/**
 * Created by J4ck on 12.07.2017.
 */

public class ProcessingCore extends PApplet {
    private Renderer renderer;
    private Input input;
    private Controller currentController;
    public static ProjectionController projectionController;
    public static AnimatingController animatingController;
    public static ModeController modeController;

    @Override
    public void settings() {
        size(1200, 800, P2D);
//        fullScreen();
    }

    public void setup() {

        Cam cam = new Cam(this);
        Animation animation = new Animation(this);

        input = new NumPadInput();

//        animation.addFrameAtPosition(loadImage("frame (1).gif"), 0);
//        animation.addFrameAtPosition(loadImage("frame (2).gif"), 1);
//        animation.addFrameAtPosition(loadImage("frame (3).gif"), 2);

        SaveSystem saveSystem = new SaveSystem(this);
        renderer = new Renderer(this, width, height);

        projectionController = new ProjectionController(renderer, saveSystem);
        animatingController = new AnimatingController(this, animation, cam, renderer, saveSystem);
        modeController = new ModeController(this, new Controller[]{projectionController, modeController});

        setCurrentController(animatingController);

        Input.defaultActions.add(new  Action(Input.Key.BACKSPACE, "Select Mode"){
            @Override
            public void Invoke() {
                if (currentController == animatingController) {
                    setCurrentController(modeController);
                } else {
                    setCurrentController(animatingController);
                }
            }
        });
    }

    public void draw() {
        background(136);
        currentController.update(this);
        renderer.draw(this);
        stroke(0,0,0);
        currentController.lateUpdate(this);
        input.draw(this);
        input.clearDownEvents();
    }


    public void keyPressed(){
        input.newKey(keyCode, key) ;
        input.update();
        println("____");
        println(key + "  KeyCode: " + keyCode + " -> (char):" + (char)keyCode);
        println(key + "  (int): " + (int) key);
        println(input.keys());
        key = 0; // Prevent ESC key to be registered
    }

    public void keyReleased() {
//        Toolkit.getDefaultToolkit().beep();
        input.released(keyCode);

    }

    public void setCurrentController(Controller currentController) {
        this.currentController = currentController;
        input.addActions(currentController.getActionController());
    }
}
