package com.jojpeg.controllers;


import com.jojpeg.*;
import com.jojpeg.cam.Cam;
import com.jojpeg.controllers.actionController.ActionHandler;
import com.jojpeg.controllers.actionController.SettingsActionHandler;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;


/**
 * Created by J4ck on 18.07.2017.
 */
public class SettingsController extends Controller {
    public int projectionMoveIndex = 1;
    public Renderer renderer;
    public SaveSystem saveSystem;
    QuadGrid plane;
    PImage referenceImage;
    SettingsActionHandler actionController = new SettingsActionHandler(this);

    public Cam cam;

    public Settings settings = new Settings();

    public SettingsController(Renderer renderer, SaveSystem saveSystem, Cam cam) {
        this.renderer = renderer;
        this.cam = cam;
        plane = renderer.getPlane();
        this.saveSystem = saveSystem;
        settings.quadModel = plane.model;
        settings.animModel = ProcessingCore.animatingController.model;
    }

    @Override
    public void lateUpdate(PApplet p, Renderer renderer) {
        if (p.mousePressed) {
            PVector move = new PVector(p.mouseX - p.pmouseX, p.mouseY - p.pmouseY);
            movePlaneAnchor(move);
        }

        renderer.setBackLayer(referenceImage);

        ProcessingCore.animatingController.drawThumbsBar(p);
//        renderer.drawOnCanvas(box,renderer.getPlane().renderWidth / 2 - 60, AnimatingController.thumbsPosition - 5);
        drawUI(p);

    }

    public void takeReferencePicture(Cam cam){
        referenceImage = cam.getFrame().getImage();
    }

    @Override
    public void update(PApplet p, Renderer renderer) {
        drawUI(p);
    }

    private void drawUI(PApplet p){

        for (int i = 0; i < 4; i++) {

            float x = plane.getCornerX(i);
            float y = plane.getCornerY(i);

            if(i == projectionMoveIndex) {
                p.stroke(0,0,255);
            }
            else {
                p.stroke(0);
            }

            p.strokeWeight(40);
            p.point(x, y);

            p.stroke(255);
            p.fill(255);
            p.strokeWeight(8);
            p.text(i + 1, x, y);
            p.point(x, y);
        }


        float x = plane.model.shift[0];
        float y = plane.model.shift[1];

        if(projectionMoveIndex == 4) {
            p.stroke(0,0,255);
        }
        else {
            p.stroke(0);
        }

        p.strokeWeight(40);
        p.point(x, y);

        p.stroke(255);
        p.fill(255);

//        p.strokeWeight(1);
        p.strokeWeight(8);
        p.text("5", x, y);
        p.point(x, y);

        x = plane.renderWidth/2;
        y = ProcessingCore.animatingController.model.thumbsPosition;

        if(projectionMoveIndex == 5) {
            p.stroke(0,0,255);
        }
        else {
            p.stroke(0);
        }

        p.strokeWeight(40);
        p.point(x, y);

        p.stroke(255);
        p.fill(255);

        p.strokeWeight(8);
        p.text("(6) ThumbsPos", x, y);
        p.point(x, y);
    }

    public void movePlaneAnchor(PVector value) {
        if (projectionMoveIndex <= 3) {
            float x = plane.getCornerX(projectionMoveIndex);
            float y = plane.getCornerY(projectionMoveIndex);
            plane.setCorner(projectionMoveIndex, x + value.x, y + value.y);
        } else {
            if (projectionMoveIndex == 4) {
                float x = plane.getShiftX();
                float y = plane.getShiftY();
                plane.setShift(x + value.x, y + value.y);
            }
            if(projectionMoveIndex == 5){
                ProcessingCore.animatingController.model.thumbsPosition += (int)value.y;
            }
        }
    }

    @Override
    public void save(SaveSystem saveSystem) {
        saveSystem.save(settings);
    }

    @Override
    public void load(SaveSystem saveSystem) {
        settings = (Settings)saveSystem.load(settings);
        plane.model = settings.quadModel;
        plane.UpdateCorners();
        ProcessingCore.animatingController.model = settings.animModel;
    }

    @Override
    public ActionHandler<SettingsActionHandler> getActionController() {
        return actionController;
    }
}
