package com.jojpeg.interactionStates;

import processing.core.PApplet;

/**
 * Created by J4ck on 18.07.2017.
 */
public abstract class InteractionState {

    public abstract void update(PApplet p);

    public abstract void keyReleased(PApplet p, char key);
}
