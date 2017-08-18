package com.jojpeg;

import com.jojpeg.cam.EosCam;
import com.jojpeg.cam.WebCam;
import com.jojpeg.controllers.*;
import com.jojpeg.input.Action;
import com.jojpeg.input.Input;
import com.jojpeg.input.NumPadInput;
import processing.core.PApplet;

/**
 * Created by J4ck on 12.07.2017.
 */

public class ProcessingCore extends PApplet {
    private Renderer renderer;
    private Input input;
    private Controller currentController;
    public static SettingsController settingsController;
    public static AnimatingController animatingController;
    public static ModeController modeController;
    public static CamSettingsController camController;


    @Override
    public void settings() {
        size(1200, 800, P2D);
//        fullScreen();
    }

    public void setup() {

        println("Initializing Camera...");

        EosCam cam = new EosCam(this);

        println("Initializing AnimationSystem...");
        Animation animation = new Animation(this);

        println("Initializing Input...");
        input = new NumPadInput();

//        animation.addFrameAtPosition(asyncLoadFrame("frame (1).gif"), 0);
//        animation.addFrameAtPosition(asyncLoadFrame("frame (2).gif"), 1);
//        animation.addFrameAtPosition(asyncLoadFrame("frame (3).gif"), 2);

        println("Initializing Save System...");
        SaveSystem saveSystem = new SaveSystem(this);

        println("Initializing Renderer...");
        renderer = new Renderer(this, width, height);

        println("Initializing Controllers...");

        println("...Animating...");
        animatingController = new AnimatingController(this, animation, cam, renderer, saveSystem);
        println("...Mode...");
        modeController = new ModeController(this, renderer, input);

        println("..CamController...");
        camController = new CamSettingsController(cam);

        println("...Settings...");
        settingsController = new SettingsController(renderer, saveSystem, cam);

        Input.defaultTriggerActions.add(new  Action(Input.Key.BACKSPACE, "Select Mode"){
            @Override
            public void Invoke() {
                if (currentController == animatingController) {
                    setCurrentController(modeController);
                } else {
                    setCurrentController(animatingController);
                }
            }
        });

        setCurrentController(animatingController);

        println("Initializing Done...");
    }

    public void draw() {
        input.update();
        background(136);
        currentController.update(this, renderer);
        input.draw(this,renderer);
        renderer.draw(this);
        stroke(0,0,0);
        currentController.lateUpdate(this, renderer);
        input.clearDownEvents();
    }


    public void keyPressed(){
        input.newKey(keyCode, key) ;
//        input.printInfos(this);
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
