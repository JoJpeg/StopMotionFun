package com.jojpeg.controllers;

import com.jojpeg.SaveSystem;
import com.jojpeg.input.Action;
import com.jojpeg.input.Input;
import jogamp.graph.font.UbuntuFontLoader;
import processing.core.PApplet;

import java.util.ArrayList;

/**
 * Created by J4ck on 18.07.2017.
 */
public abstract class Controller {

    protected Input input;

    public abstract void update(PApplet p);

    public abstract void lateUpdate(PApplet p);

//    public abstract void processInput(Input input);

    public abstract void save(SaveSystem saveSystem);

    public abstract void load(SaveSystem saveSystem);

    public abstract ActionController<?> getActionController();

}
