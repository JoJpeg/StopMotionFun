package com.jojpeg.controllers.actionController;

import com.jojpeg.controllers.CamSettingsController;
import com.jojpeg.input.Action;
import com.jojpeg.input.Input;

public class CamSettingsActionHandler extends ActionHandler{

    public CamSettingsController controller;

    public CamSettingsActionHandler(CamSettingsController controller) {
        this.controller = controller;
    }

    public Action initCam = new Action('1', "Initialize Cam"){
        @Override
        public void Invoke() {

            controller.cam.initialize(controller.getCamIndex());
        }
    };

    public Action increaseCamIndex = new Action(Input.Key.RIGHT, "Select Next Cam"){
        @Override
        public void SetParameters() {
            continous = true;
        }

        @Override
        public void Invoke() {
            controller.setCamIndex(controller.getCamIndex() + 1);
        }
    };

    public Action decreaseCamIndex = new Action(Input.Key.LEFT, "Select Previous Cam"){
        @Override
        public void SetParameters() {
            continous = true;
        }

        @Override
        public void Invoke() {
            controller.setCamIndex(controller.getCamIndex() - 1);
        }
    };

}
