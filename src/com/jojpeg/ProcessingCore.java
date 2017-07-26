package com.jojpeg;

import com.jojpeg.input.Input;
import com.jojpeg.input.KeyBoardInput;
import com.jojpeg.interactionStates.AnimatingController;
import com.jojpeg.interactionStates.Controller;
import com.jojpeg.interactionStates.ModeController;
import com.jojpeg.interactionStates.ProjectionController;
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
    private ModeController modeController;

    @Override
    public void settings() {
        size(1200, 800, P2D);
//        fullScreen();
    }

    public void setup() {

        Cam cam = new Cam(this);
        Animation animation = new Animation(this);

        input = new KeyBoardInput();

//        animation.addFrameAtPosition(loadImage("frame (1).gif"), 0);
//        animation.addFrameAtPosition(loadImage("frame (2).gif"), 1);
//        animation.addFrameAtPosition(loadImage("frame (3).gif"), 2);

        SaveSystem saveSystem = new SaveSystem(this);
        renderer = new Renderer(this, width, height);

        projectionController = new ProjectionController(renderer, saveSystem);
        animatingController = new AnimatingController(this, animation, cam, renderer, saveSystem);
        modeController = new ModeController(this, new Controller[]{projectionController, modeController});
        setCurrentController(modeController);
    }

    public void draw() {
        background(136);
        currentController.update(this);
        renderer.draw(this);
        currentController.processInput(input);
        stroke(0,0,0);
        currentController.lateUpdate(this);
        input.draw(this);
        input.clearDownEvents();
    }


    public void keyPressed(){
        input.newKey(keyCode, key) ;
        if(input.keyIsDown(Input.Key.TAB, "Select Mode")){
            currentController = modeController;
        }
        println("____");
        println(key + "  KeyCode: " + keyCode + " -> (char):" + (char)keyCode);
        println(key + "  (int): " + (int) key);
        println(input.showKeys());
        //aprintln(key + "  (KeyStroke): " + (int)KeyCode.getKeyCode(key));

    }

    public void keyReleased() {
//        Toolkit.getDefaultToolkit().beep();
        input.released(keyCode);
    }

    public void setCurrentController(Controller currentController) {
        this.currentController = currentController;
        currentController.processInput(input);
    }
}
