package com.jojpeg.interactionStates;

import com.jojpeg.SaveSystem;
import com.jojpeg.input.Input;
import processing.core.PApplet;

/**
 * Created by J4ck on 18.07.2017.
 */
public abstract class Controller {

    public abstract void update(PApplet p);

    public abstract void lateUpdate(PApplet p);

    public abstract void processInput(Input input);

    public abstract void save(SaveSystem saveSystem);

    public abstract void load(SaveSystem saveSystem);
}
