package com.jojpeg.interactionStates;

import com.jojpeg.ProcessingCore;
import com.jojpeg.SaveSystem;
import com.jojpeg.input.Input;
import processing.core.PApplet;

import java.awt.*;

public class ModeController extends Controller {

    ProcessingCore core;
    Controller[] controllers;
    public ModeController(ProcessingCore core, Controller[] controllers) {
        this.core = core;
        this.controllers = controllers;
    }

    @Override
    public void update(PApplet p) {


    }

    @Override
    public void lateUpdate(PApplet p) {


        //TODO: Draw on Plane Canvas (implement "PGraphics beginDraw(); endDraw();" in Renderer)

        int textSize = 15;
        int padding = 5;
        p.strokeWeight(3);
        p.fill(0);
        p.textSize(textSize);
        float y = p.height/2 - ((textSize + padding) * Input.actionInformations.keySet().size() / 2);
         y = 50;

        for(String key : Input.actionInformations.keySet()){

            p.text(key + " : " + Input.actionInformations.get(key), 20, y);
            y += textSize + padding;
        }
    }

    @Override
    public void processInput(Input input) {

        if(input.keyIsDown('a', "Go to Animating")){
            core.setCurrentController(ProcessingCore.animatingController);
        }

        if(input.keyIsDown('m', "Switch To Projection Movement")){
            core.setCurrentController(ProcessingCore.projectionController);
        }
    }


    @Override
    public void save(SaveSystem saveSystem) {

    }

    @Override
    public void load(SaveSystem saveSystem) {

    }
}
