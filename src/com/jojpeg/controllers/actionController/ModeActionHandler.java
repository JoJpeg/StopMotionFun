package com.jojpeg.controllers.actionController;

import com.jojpeg.ProcessingCore;
import com.jojpeg.input.Action;

public class ModeActionHandler extends ActionHandler {

    ProcessingCore core;

    public ModeActionHandler(ProcessingCore core) {
        this.core = core;
    }

    public Action GUISetup = new Action('m', "Settings"){
        @Override
        public void Invoke() {
            System.out.println("Switched to Projection Controller");
            core.setCurrentController(ProcessingCore.settingsController);
        }
    };

//    public Action Back = new Action(Input.Key.BACKSPACE, "Back"){
//        @Override
//        public void Invoke() {
//            System.out.println("Switched to Projection Controller");
//            core.setCurrentController(ProcessingCore.settingsController);
//        }
//    };
}
