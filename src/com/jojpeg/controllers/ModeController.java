package com.jojpeg.controllers;

import com.jojpeg.ProcessingCore;
import com.jojpeg.Renderer;
import com.jojpeg.SaveSystem;
import com.jojpeg.controllers.actionController.ActionHandler;
import com.jojpeg.controllers.actionController.ModeActionHandler;
import com.jojpeg.input.Action;
import com.jojpeg.input.Input;
import processing.core.PApplet;

public class ModeController extends Controller {

    ProcessingCore core;
    ModeActionHandler actionController;
    Renderer renderer;
    Input input;

    public ModeController(ProcessingCore core, Renderer renderer, Input input) {
        this.input = input;
        this.core = core;
        this.renderer = renderer;
        actionController = new ModeActionHandler(core);
    }

    @Override
    public void update(PApplet p) {
        input.drawKeyMap(p, renderer);
    }

    @Override
    public void lateUpdate(PApplet p) {
        //TODO: Draw on Plane Canvas (implement "PGraphics plane.beginDraw(); plane.endDraw();" in Renderer)
        /*
        int textSize = 15;
        int padding = 5;
        p.strokeWeight(3);
        p.fill(0);
        p.textSize(textSize);
        float y = 50;
//                p.height/2 - ((textSize + padding) * Input.triggerActions.keySet().size() / 2);

        for(Action a : Input.triggerActions){
            p.text(a.getKeyName() + " : " + a.getKeyInfo(), 20, y);
            y += textSize + padding;
        }
        */
    }


    @Override
    public void save(SaveSystem saveSystem) {

    }

    @Override
    public void load(SaveSystem saveSystem) {

    }

    @Override
    public ActionHandler<ModeActionHandler> getActionController() {
        return actionController;
    }
}
