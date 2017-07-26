package com.jojpeg.controllers;

import com.jojpeg.ProcessingCore;
import com.jojpeg.SaveSystem;
import com.jojpeg.controllers.actionController.ActionController;
import com.jojpeg.controllers.actionController.ModeActionController;
import com.jojpeg.input.Action;
import com.jojpeg.input.Input;
import processing.core.PApplet;

public class ModeController extends Controller {

    ProcessingCore core;
    Controller[] controllers;
    ModeActionController actionController;


    public ModeController(ProcessingCore core, Controller[] controllers) {
        this.core = core;
        this.controllers = controllers;
        actionController = new ModeActionController(core);
    }

    @Override
    public void update(PApplet p) {


    }

    @Override
    public void lateUpdate(PApplet p) {


        //TODO: Draw on Plane Canvas (implement "PGraphics plane.beginDraw(); plane.endDraw();" in Renderer)

        int textSize = 15;
        int padding = 5;
        p.strokeWeight(3);
        p.fill(0);
        p.textSize(textSize);
        float y = 50;
//                p.height/2 - ((textSize + padding) * Input.actions.keySet().size() / 2);

        for(Action a : Input.actions){
            p.text(a.getKeyName() + " : " + a.getKeyInfo(), 20, y);
            y += textSize + padding;
        }
    }


    @Override
    public void save(SaveSystem saveSystem) {

    }

    @Override
    public void load(SaveSystem saveSystem) {

    }

    @Override
    public ActionController<ModeActionController> getActionController() {
        return actionController;
    }
}
