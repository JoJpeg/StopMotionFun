package com.jojpeg.controllers;

import com.jojpeg.*;
import com.jojpeg.controllers.actionController.ActionController;
import com.jojpeg.controllers.actionController.AnimatingActionController;
import gifAnimation.GifMaker;
import processing.core.PApplet;
import processing.core.PImage;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by J4ck on 18.07.2017.
 */
public class AnimatingController extends Controller {
    public PApplet p;
    public Animation animation;
    public Renderer renderer;
    public Cam cam;
    public SaveSystem saveSystem;
    AnimatingActionController actionController = new AnimatingActionController(this);

    public static int thumbsPosition = 14;

    int onionFront = 1;
    int onionBack = 1;

    int projectionOpacity = 127;
    int onionOpacity = 127;
    public boolean play = false;
    public boolean project = false;
    public boolean onion = false;

    public AnimatingController(PApplet p, Animation animation, Cam cam, Renderer renderer, SaveSystem saveSystem) {
        this.saveSystem = saveSystem;
        this.p = p;
        this.animation = animation;
        this.cam = cam;
        this.renderer = renderer;
    }

    @Override
    public void lateUpdate(PApplet p) {
        if(play) {
            renderer.setFrame(animation.play());
        }
        else {
            renderer.setFrame(animation.current());
            if(project) {
                renderer.setLayer(cam.getRealtime(),1, projectionOpacity);
            }
            drawThumbsBar(p);
        }

        if(onion) {
            int layer = 1;
            for (int i = animation.caretPos - onionBack; i < animation.caretPos + onionFront + 1; i++) {
                PImage onion =   animation.getFrame(i);
                if(onion != null && i != animation.caretPos) {
                    renderer.setLayer(onion, layer, onionOpacity);
                    layer ++;
                }

            }
        }
    }

    @Override
    public void update(PApplet p) {

    }

    @Override
    public void save(SaveSystem saveSystem) {
        saveSystem.setMessage("Save Animation");
        saveSystem.save(animation.getModel());
        File selection = saveSystem.getLastSelection();
        if(selection == null) return;
        ArrayList<Animation.Frame> frames = animation.getFrames();
        String parentPath = selection.getParent() + "\\";
        GifMaker gif = new GifMaker(p, parentPath  + "_Anim_" +frames.get(0).getName() + ".gif");
        gif.setRepeat(0);

        for (int i = 0; i < frames.size(); i++) {
            Animation.Frame frame = frames.get(i);
            String path = parentPath + frame.getName()+ " - " + p.nf(i, 5) + ".png";
            System.out.println(path);
            frame.getImage().save(path);

            gif.setDelay(1000 / Animation.fps);
            gif.addFrame(frame.getImage());
        }

        gif.finish();

    }

    @Override
    public void load(SaveSystem saveSystem) {
        animation.model = (Animation.AnimationModel)saveSystem.load(animation.getModel());
        if(saveSystem.getLastSelection() == null) return;
        animation.model.path = saveSystem.getLastSelection().getParent() + "\\";

        animation.frames.clear();
        while (animation.frames.size() < animation.model.indices.length) {
            animation.frames.add(null);
        }
        animation.caretPos = 0;

        String[] names = animation.model.names;
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            PImage image = p.loadImage(animation.model.path  + name + " - " + p.nf(animation.model.indices[i], 5) + ".png");
            animation.frames.set(
                    animation.model.indices[i],
                    animation.makeFrame(image, name)
            );
        }
    }

    private void drawThumbsBar(PApplet p){

        PImage[] thumbs = new PImage[7];
        int index = 0;
        for (int i = animation.caretPos - 3; i < animation.caretPos + 4; i++) {
            thumbs[index] = animation.getThumbnail(i);
            index ++;
        }

        PImage box = p.createImage(111,111, p.RGB);
        box.loadPixels();
        for (int j = 0; j < box.pixels.length; j++) {
            box.pixels[j] = p.color(255,0,0);
        }

        box.updatePixels();
        renderer.drawOnCanvas(box,renderer.getPlane().renderWidth / 2 - 60, thumbsPosition - 5);

        for (int i = 0; i < thumbs.length; i++) {
            PImage t = thumbs[i];
            int x = (renderer.getPlane().renderWidth / 2 - 385) + (i * 110);
            int y = thumbsPosition;
            if(t == null){
                t = p.createImage(100,100, p.RGB);
                t.loadPixels();
                for (int j = 0; j < t.pixels.length; j++) {
                    t.pixels[j] = p.color(180);
                }
                t.updatePixels();
            }
            else {
                t.resize(100,100);
            }
            renderer.drawOnCanvas(t, x, y);
        }
    }

    @Override
    public ActionController<AnimatingActionController> getActionController() {
        return actionController;
    }

}
