package com.jojpeg;// There is no need to modify the code in this tab.

import processing.core.PApplet;
import processing.core.PImage;

public class QuadGrid {

    private final int nbrCols, nbrRows;
    private final VPoint[][] vertexPoints;
    public QuadModel model;
    public int renderWidth, renderHeight;
    public class QuadModel extends Model<QuadModel> {
        public float[] cornersX = {
                30, 377, 343, 73
        };
        public float[] cornersY = {
                10, 23, 333, 389
        };
        public float[] shift = {0,0};
    }

    /**
     * <a href="/two/profile/param">@param</a> img the image must not be null
     * <a href="/two/profile/param">@param</a> nbrXslices must be >= 1
     * <a href="/two/profile/param">@param</a> nbrYslices must be >= 1
     */

    public QuadGrid(int renderWidth, int renderHeight, int nbrXslices, int nbrYslices) {
        this.renderWidth = renderWidth;
        this.renderHeight = renderHeight;
        model = new QuadModel();
        //this.img = img;
        nbrCols = (nbrXslices >= 1) ? nbrXslices : 1;
        nbrRows = (nbrYslices >= 1) ? nbrYslices : 1;

        vertexPoints = new VPoint[nbrCols + 1][nbrRows + 1];
        // Set corners so top-left is [0,0] and bottom-right is [image renderWidth, image renderHeight]
        float deltaU = 1.0f / nbrCols;
        float deltaV = 1.0f / nbrRows;
        for (int col = 0; col <= nbrCols; col++)
            for (int row = 0; row <= nbrRows; row++)
                vertexPoints[col][row] = new VPoint(col * deltaU, row * deltaV);

    }

    private void setCorners(float[] vx, float[] vy){
        setCorners(vx[0], vy[0], vx[1], vy[1], vx[2], vy[2], vx[3], vy[3] );
    }

    public void UpdateCorners(){
        setCorners(model.cornersX, model.cornersY);
    }

    /**
     * Calculate all the quad coordinates
     * Vetices in order TL, TR, BR, BL
     */
    private void setCorners(float x0, float y0, float x1, float y1, float x2, float y2, float x3, float y3) {
        if (vertexPoints == null) return;

        float sx = PApplet.map(model.shift[0],0, renderWidth, 0, 1 );
        float sy = PApplet.map(model.shift[1],0, renderHeight, 0, 1 );

        // Do outer corners
        vertexPoints[0][0].setXY(x0, y0);
        vertexPoints[nbrCols][0].setXY(x1, y1);
        vertexPoints[nbrCols][nbrRows].setXY(x2, y2);
        vertexPoints[0][nbrRows].setXY(x3, y3);
        // Top row
        float deltaX = (x1 - x0) / nbrCols;
        float deltaY = (y1 - y0) / nbrRows;
        for (int col = 0; col <= nbrCols; col++)
            vertexPoints[col][0].setXY(x0 + col * deltaX, y0 + col * deltaY);
        // Bottom row
        deltaX = (x2 - x3) / nbrCols;
        deltaY = (y2 - y3) / nbrRows;

        for (int col = 0; col <= nbrCols; col++)
            vertexPoints[col][nbrRows].setXY(x3 + col * deltaX, y3 + col * deltaY);

        // Fill each column in the grid in turn
        for (int col = 0; col <= nbrCols; col++) {
            for (int row = 1; row < nbrRows; row++) {
                VPoint vpF = vertexPoints[col][0];
                VPoint vpL = vertexPoints[col][nbrRows];

                deltaX = (vpL.x - vpF.x) / nbrRows;
                deltaY = (vpL.y - vpF.y) / nbrRows;

                float p = (PApplet.map(row, 0, nbrRows, sy, 1));

                float dX = (deltaX * p);
                float dY = (deltaY * p);

                vertexPoints[col][row].setXY(
                        vpF.x + row * (dX),
                        vpF.y + row * (dY)
                );
            }
        }
    }

    public void draw(PApplet app, PImage frame) {
        if (vertexPoints == null) return;
        app.textureMode(PApplet.NORMAL);
        app.noStroke(); // comment out this line to see triangles
        for (int row = 0; row < nbrRows; row++) {
            app.beginShape(PApplet.TRIANGLE_STRIP);
            app.texture(frame);
            for (int col = 0; col <= nbrCols; col++) {

                VPoint p0 = vertexPoints[col][row];
                VPoint p1 = vertexPoints[col][row + 1];
                app.vertex(p0.x , p0.y , p0.u, p0.v);
                app.vertex(p1.x , p1.y , p1.u, p1.v);
            }
            app.endShape();
        }
    }

    public float getCornerX(int index){
        return model.cornersX[index];
    }

    public void setCornerX(int index, float value){
        model.cornersX[index] = value;
        UpdateCorners();
    }

    public float getCornerY(int index){
        return model.cornersY[index];
    }

    public void setCornerY(int index, float value){
        model.cornersY[index] = value;
        UpdateCorners();
    }

    public void setCorner(int index, float x, float y){
        setCornerX(index, x);
        setCornerY(index, y);

    }

    public void setShift(float x, float y){
        model.shift[0] = x;
        model.shift[1] = y;
        UpdateCorners();
    }

    public float getShiftX(){
        return model.shift[0];
    }

    public float getShiftY(){
        return model.shift[1];
    }

    private class VPoint {
        public float x = 0;
        public float y = 0;
        public float u;
        public float v;


        public VPoint(float u, float v) {
            this.u = u;
            this.v = v;
        }

        public void setXY(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}