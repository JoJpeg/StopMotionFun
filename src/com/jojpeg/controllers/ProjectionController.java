package com.jojpeg.controllers;


import com.jojpeg.QuadGrid;
import com.jojpeg.Renderer;
import com.jojpeg.SaveSystem;
import com.jojpeg.controllers.actionController.ActionController;
import com.jojpeg.controllers.actionController.ProjectionActionController;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;


/**
 * Created by J4ck on 18.07.2017.
 */
public class ProjectionController extends Controller {
    public int projectionMoveIndex = 1;
    public Renderer renderer;
    public SaveSystem saveSystem;
    QuadGrid plane;
    ProjectionActionController actionController = new ProjectionActionController(this);

    public ProjectionController(Renderer renderer, SaveSystem saveSystem) {
        this.renderer = renderer;
        plane = renderer.getPlane();
        this.saveSystem = saveSystem;
    }

    @Override
    public void update(PApplet p) {
        if (p.mousePressed) {
            PVector move = new PVector(p.mouseX - p.pmouseX, p.mouseY - p.pmouseY);
            movePlaneAnchor(move);
        }
    }

    @Override
    public void lateUpdate(PApplet p) {
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
        y = AnimatingController.thumbsPosition;

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

        PImage box = p.createImage(400,111, p.RGB);
        box.loadPixels();
        for (int j = 0; j < box.pixels.length; j++) {
            box.pixels[j] = p.color(255,0,0);
        }

        box.updatePixels();
        renderer.drawOnCanvas(box,renderer.getPlane().renderWidth / 2 - 60, AnimatingController.thumbsPosition - 5);

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
                AnimatingController.thumbsPosition += (int)value.y;
            }
        }
    }

    @Override
    public void save(SaveSystem saveSystem) {
        saveSystem.save(plane.model);
    }

    @Override
    public void load(SaveSystem saveSystem) {
        plane.model = (QuadGrid.QuadModel)saveSystem.load(plane.model);
        plane.UpdateCorners();
    }

    @Override
    public ActionController<ProjectionActionController> getActionController() {
        return actionController;
    }
}
