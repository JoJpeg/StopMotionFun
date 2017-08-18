package com.jojpeg.controllers.actionController;


import com.jojpeg.Animation;
import com.jojpeg.Frame;
import com.jojpeg.controllers.AnimatingController;
import com.jojpeg.input.Action;
import com.jojpeg.input.Input;

public class AnimatingActionHandler extends ActionHandler {

    AnimatingController controller;

    public AnimatingActionHandler(AnimatingController controller) {
        this.controller = controller;
    }

    public Action caretLeft =   new Action(Input.Key.LEFT, "Caret Left"){
        @Override
        public void Invoke() {
            controller.animation.caretLeft();
        }
    };

    public Action caretRight =   new Action(Input.Key.RIGHT, "Caret Right"){
        @Override
        public void Invoke() {
            controller.animation.caretRight();

        }
    };

    public Action playPause = new Action(Input.Key.SPACE, "Play/Stop"){
         @Override
         public void Invoke() {
            controller.play = !controller.play;

            if(controller.play) {
                controller.model.onion = false;
                controller.p.frameRate(Animation.fps);
            }
            else {
                controller.p.frameRate(60);
            }
         }
    };

    public Action addFrame = new Action('f', "Add Frame"){
        @Override
        public void Invoke() {
            controller.animation.addFrame(controller.cam.getFrame());
        }
    };

    public Action replaceFrame =   new Action('r', "Replace Frame"){
        @Override
        public void Invoke() {
            controller.animation.replaceFrame(controller.cam.getFrame());
        }
    };

    public Action project = new Action('p', "Project Camera Input"){
        @Override
        public void Invoke() {
            controller.model.project = !controller.model.project;
        }
    };

    public Action onion = new Action('o', "Activate Onion Skin"){
        @Override
        public void Invoke() {
            controller.model.onion = !controller.model.onion;
        }
    };

    public Action removeFrame = new Action('x', "Delete Frame"){
        @Override
        public void Invoke() {
            controller.animation.removeFrame(controller.animation.caretPos);
        }
    };

    public Action blackOutScreen = new Action('h', "Black Out Screen"){
        @Override
        public void Invoke() {
            controller.renderer.black = !controller.renderer.black;
        }
    };

    public Action save =  new Action('s', "Save"){
        @Override
        public void Invoke(){
            controller.save(controller.saveSystem);
        }
    };

    public Action load = new Action('l', "Load") {
        @Override
        public void Invoke() {
            controller.load(controller.saveSystem);
        }
    };

}
