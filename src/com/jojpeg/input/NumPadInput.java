package com.jojpeg.input;

import com.jojpeg.ProcessingCore;
import com.jojpeg.controllers.AnimatingController;
import com.jojpeg.controllers.ModeController;
import com.jojpeg.controllers.ProjectionController;
import processing.core.PApplet;

public class NumPadInput extends Input{

    public NumPadInput() {
        drawGUI = ',';
    }

    @Override
    public void translate() {
        ProjectionController pc = ProcessingCore.projectionController;
        AnimatingController ac = ProcessingCore.animatingController;
        ModeController mc = ProcessingCore.modeController;

        mc.getActionController().get().GUISetup.setKey('1');

        pc.getActionController().get().save.setKey('/');
        pc.getActionController().get().load.setKey('*');

        ac.getActionController().get().addFrame.setKey('\n');
        ac.getActionController().get().playPause.setKey('+');
        ac.getActionController().get().onion.setKey('5');
        ac.getActionController().get().project.setKey('6');

        ac.getActionController().get().removeFrame.setKey('4');
        ac.getActionController().get().replaceFrame.setKey('1');

        ac.getActionController().get().blackOutScreen.setKey('-');
        ac.getActionController().get().caretLeft.setKey('2');
        ac.getActionController().get().caretRight.setKey('3');
        ac.getActionController().get().save.setKey('/');
        ac.getActionController().get().load.setKey('*');
    }

    @Override
    protected void drawGUI(PApplet p) {
        int textSize = 15;
        int padding = 5;
        p.strokeWeight(3);
        p.textSize(textSize);
        float y = 50;

        for(Action a : Input.actions){
            p.fill(0);
            p.rect(15,y, 200,-18);
            p.fill(255);
            p.text(a.getKeyName() + " : " + a.getKeyInfo(), 20, y);
            y += textSize + padding;
        }

    }
}
