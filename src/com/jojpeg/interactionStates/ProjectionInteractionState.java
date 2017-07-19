package com.jojpeg.interactionStates;


import com.jojpeg.QuadGrid;
import com.jojpeg.Renderer;
import com.jojpeg.SaveSystem;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Created by J4ck on 18.07.2017.
 */
public class ProjectionInteractionState extends InteractionState {
    int projectionMoveIndex = 1;
    Renderer renderer;

    public ProjectionInteractionState(Renderer renderer) {
        this.renderer = renderer;

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
    public void keyReleased(PApplet p, char key) {
        if (key >= '1' && key <= '5') {
            projectionMoveIndex = (key - '0') - 1;
            return;
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
