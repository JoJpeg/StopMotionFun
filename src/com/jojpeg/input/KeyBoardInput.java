package com.jojpeg.input;


import com.jojpeg.Renderer;
import processing.core.PApplet;

/**
 * Created by J4ck on 21.07.2017.
 */
public class KeyBoardInput extends Input {
    public KeyBoardInput() {

    }

    @Override
    public void translate() {

    }

    @Override
    protected void drawGUI(PApplet p, Renderer renderer) {
        int textSize = 15;
        int padding = 5;
        p.strokeWeight(3);
        p.textSize(textSize);
        float y = 50;

        for(Action a : Input.triggerActions){
            p.fill(0);
            p.rect(15,y, p.textWidth(a.getKeyName() + 5),20);
            p.fill(255);
            p.text(a.getKeyName() + " : " + a.getKeyInfo(), 20, y);
            y += textSize + padding;
        }

    }
}
