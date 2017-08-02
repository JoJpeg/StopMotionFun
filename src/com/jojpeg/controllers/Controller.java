package com.jojpeg.controllers;

import com.jojpeg.Renderer;
import com.jojpeg.SaveSystem;
import com.jojpeg.controllers.actionController.ActionHandler;
import com.jojpeg.input.Input;
import processing.core.PApplet;

/**
 * Created by J4ck on 18.07.2017.
 */
public abstract class Controller {

    protected Input input;

    /***
     * Is called Before the Renderer draws. Use this to modify the Renderer
     * @param p
     */
    public abstract void update(PApplet p, Renderer renderer);

    /***
     * Is called After the Renderer draws. Use this for drawing things unrelated to the Renderer.Plane
     * @param p
     */
    public abstract void lateUpdate(PApplet p, Renderer renderer);

//    public abstract void processInput(Input input);

    public abstract void save(SaveSystem saveSystem);

    public abstract void load(SaveSystem saveSystem);

    public abstract ActionHandler<?> getActionController();

}
