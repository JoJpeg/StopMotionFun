package com.jojpeg.controllers.actionController;

import com.jojpeg.controllers.SettingsController;
import com.jojpeg.input.Action;
import com.jojpeg.input.Input;
import processing.core.PVector;

public class SettingsActionHandler extends ActionHandler {

    SettingsController controller;

    public SettingsActionHandler(SettingsController controller) {
        this.controller = controller;
    }

    public Action moveCornerUp = new Action(Input.Key.UP, "Selected Corner Up"){

        @Override
        public void SetParameters() {
            continous = true;
        }

        @Override
        public void Invoke() {
            controller.movePlaneAnchor(new PVector(0,-1));
        }
    };

    public Action moveCornerDown  =  new Action(Input.Key.DOWN, "Selected Corner Down"){

        @Override
        public void SetParameters() {
            continous = true;
        }

        @Override
        public void Invoke() {
            controller.movePlaneAnchor(new PVector(0,1));
        }
    };

    public Action moveCornerLeft = new Action(Input.Key.LEFT, "Selected Corner Up"){
        @Override
        public void SetParameters() {
            continous = true;
        }

        @Override
        public void Invoke() {
            controller.movePlaneAnchor(new PVector(-1,0));

        }
    };

    public Action getMoveCornerRight =   new Action(Input.Key.RIGHT, "Selected Corner Up"){
        @Override
        public void SetParameters() {
            continous = true;
        }

        @Override
          public void Invoke() {
              controller.movePlaneAnchor(new PVector(1,0));

          }
      };

    public Action selectFirstCorner = new Action('1', "Move Corner 1"){
        @Override
        public void Invoke() {
            controller.projectionMoveIndex = 0;
        }
    };

    public Action selectSecondCorner =  new Action('2', "Move Corner 2"){
        @Override
        public void Invoke() {
            controller.projectionMoveIndex = 1;
        }
    };

    public Action selectThirdCorner = new Action('3', "Move Corner 3"){
        @Override
        public void Invoke() {
            controller.projectionMoveIndex = 2;
        }
    };

    public Action slectFourthCorner =  new Action('4', "Move Corner 4"){
          @Override
          public void Invoke() {
              controller.projectionMoveIndex = 3;
          }
    };

    public Action selectPerspectiveShif =  new Action('5', "Shift Perspective"){
        @Override
        public void Invoke() {
            controller.projectionMoveIndex = 4;
        }
    };

    public Action selectThumbsPositioning =    new Action('6', "Thumbs Position"){
        @Override
        public void Invoke(){
            controller.projectionMoveIndex = 5;
        }
    };

    public Action rotateView =    new Action('c',"Rotate View"){
        @Override
        public void Invoke(){
            controller.renderer.rotate();
        }
    };

    public Action save =    new Action('s', "Save"){
        @Override
        public void Invoke(){
            controller.save(controller.saveSystem);
        }
    };

    public Action load =    new Action('l', "Load"){
        @Override
        public void Invoke(){
            controller.load(controller.saveSystem);
        }
    };

    public Action takeFrame =  new Action('f', "Take Reference Picture"){
        @Override
        public void Invoke(){
            controller.takeReferencePicture(controller.cam);
        }
    };
}
