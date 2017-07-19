package com.jojpeg.interactionStates;

import com.jojpeg.SaveSystem;
import processing.core.PApplet;

/**
 * Created by J4ck on 18.07.2017.
 */
public abstract class InteractionState {

    public abstract void update(PApplet p);

    public abstract void lateUpdate(PApplet p);

    public abstract void keyReleased(PApplet p, char key);

    public abstract void save(SaveSystem saveSystem);

    public abstract void load(SaveSystem saveSystem);
}
