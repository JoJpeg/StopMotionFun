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

    public ProjectionController(Renderer renderer, SaveSystem saveSystem) {
        this.renderer = renderer;
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

        p.stroke(255);
        for (int i = 0; i < 4; i++) {
            float x = renderer.getPlane().getCornersX()[i];
            float y = renderer.getPlane().getCornersY()[i];
            p.strokeWeight(1);
            p.text(i + 1, x, y);
            p.strokeWeight(8);
            p.point(x, y);
        }

        float x = renderer.getPlane().model.shift[0];
        float y = renderer.getPlane().model.shift[1];
        p.strokeWeight(1);
        p.text("5", x, y);
        p.strokeWeight(8);
        p.point(x, y);

    }

    private void movePlaneAnchor(PVector value) {
        if (projectionMoveIndex <= 3) {
            renderer.getPlane().getCornersX()[projectionMoveIndex] += value.x;
            renderer.getPlane().getCornersY()[projectionMoveIndex] += value.y;
        } else {
            if (projectionMoveIndex == 4) {
                renderer.getPlane().model.shift[0] += value.x;
                renderer.getPlane().model.shift[1] += value.y;
            }
        }
        renderer.getPlane().UpdateCorners();
    }

    @Override
    public void save(SaveSystem saveSystem) {
        saveSystem.save(renderer.getPlane().model);
    }

    @Override
    public void load(SaveSystem saveSystem) {
        renderer.getPlane().model = (QuadGrid.QuadModel)saveSystem.load(renderer.getPlane().model);
        renderer.getPlane().UpdateCorners();
    }
}
