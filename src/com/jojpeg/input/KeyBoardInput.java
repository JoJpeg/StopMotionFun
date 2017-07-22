package com.jojpeg.input;

import com.sun.deploy.util.StringUtils;
import processing.core.PApplet;

/**
 * Created by J4ck on 21.07.2017.
 */
public class KeyBoardInput extends Input {
    public KeyBoardInput() {

    }

    @Override
    protected void drawGUI(PApplet p) {
        for(String key : actionInformations.keySet()){
            System.out.println(key+ " : " + actionInformations.get(key));
        }
    }
}
