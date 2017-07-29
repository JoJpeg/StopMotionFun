package com.jojpeg.input;

import com.jojpeg.ProcessingCore;
import com.jojpeg.Renderer;
import com.jojpeg.controllers.AnimatingController;
import com.jojpeg.controllers.ModeController;
import com.jojpeg.controllers.SettingsController;
import processing.core.PApplet;

public class NumPadInput extends Input{

    public NumPadInput() {
        drawGui.setSecondary(',');
    }

    @Override
    public void translate() {
        SettingsController pc = ProcessingCore.settingsController;
        AnimatingController ac = ProcessingCore.animatingController;
        ModeController mc = ProcessingCore.modeController;

        mc.getActionController().get().GUISetup.setKey('1');

        pc.getActionController().get().save.setKey('/');
        pc.getActionController().get().load.setKey('*');
        pc.getActionController().get().takeFrame.setKey('\n');

        ac.getActionController().get().addFrame.setKey('\n');
        ac.getActionController().get().playPause.setKey('+');
        ac.getActionController().get().onion.setKey('5');
        ac.getActionController().get().project.setKey('6');

        ac.getActionController().get().removeFrame.setKey('4');
        ac.getActionController().get().replaceFrame.setKey('1');

        ac.getActionController().get().blackOutScreen.setKey('-');

        ac.getActionController().get().caretLeft.setKey('2');
        ac.getActionController().get().caretLeft.setSecondary(Key.LEFT);
        ac.getActionController().get().caretRight.setKey('3');
        ac.getActionController().get().caretRight.setSecondary(Key.RIGHT);

        ac.getActionController().get().save.setKey('/');
        ac.getActionController().get().load.setKey('*');
    }

    @Override
    protected void drawGUI(PApplet p, Renderer renderer) {

    }
}
