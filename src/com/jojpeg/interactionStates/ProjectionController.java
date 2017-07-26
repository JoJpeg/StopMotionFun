package com.jojpeg.interactionStates;


import com.jojpeg.QuadGrid;
import com.jojpeg.Renderer;
import com.jojpeg.SaveSystem;
import com.jojpeg.input.Input;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Created by J4ck on 18.07.2017.
 */
public class ProjectionController extends Controller {
    int projectionMoveIndex = 1;
    Renderer renderer;
    SaveSystem saveSystem;
    QuadGrid plane;

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

    @Override
    public void processInput(Input input) {

        if(input.keyIsDown(Input.Key.UP, "Selected Corner Up")){
            movePlaneAnchor(new PVector(0,-1));
        }

        if(input.keyIsDown(Input.Key.DOWN, "Selected Corner Up")){
            movePlaneAnchor(new PVector(0,1));
        }

        if(input.keyIsDown(Input.Key.LEFT, "Selected Corner Up")){
            movePlaneAnchor(new PVector(-1,0));
        }

        if(input.keyIsDown(Input.Key.RIGHT, "Selected Corner Up")){
            movePlaneAnchor(new PVector(1,0));
        }

        if(input.keyIsDown('1', "Move Corner 1")){
            projectionMoveIndex = 0;
        }

        if(input.keyIsDown('2', "Move Corner 2")){
            projectionMoveIndex = 1;
        }

        if(input.keyIsDown('3', "Move Corner 3")){
            projectionMoveIndex = 2;
        }

        if(input.keyIsDown('4', "Move Corner 4")){
            projectionMoveIndex = 3;
        }

        if(input.keyIsDown('5', "Shift Perspective")){
            projectionMoveIndex = 4;
        }

        if(input.keyIsDown('c',"Rotate View")){
            renderer.rotate();
        }

        if (input.keyIsDown('s', "Save")){
            save(saveSystem);
        }

        if (input.keyIsDown('l', "Load")){
            load(saveSystem);
        }
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

    }

    private void movePlaneAnchor(PVector value) {
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
}
