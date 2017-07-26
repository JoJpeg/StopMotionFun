package com.jojpeg.controllers;

import com.jojpeg.ProcessingCore;
import com.jojpeg.input.Action;

public class ModeActionController extends ActionController {

    ProcessingCore core;

    public ModeActionController(ProcessingCore core) {
        this.core = core;
    }

    public Action GUISetup = new Action('m', "Switch To Projection Movement"){
        @Override
        public void Invoke() {
            core.setCurrentController(ProcessingCore.projectionController);
        }
    };
}
