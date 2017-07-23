package com.jojpeg.prototypes;

import processing.core.PApplet;

public class PieChart extends PApplet{
    int[] angles = { 30, 10, 45, 35, 60, 38, 75, 67 };

    public void setup() {
        size(640, 360);
        noStroke();
        //noLoop();  // Run once and stop
    }

    public void draw() {
        background(100);
        pieChart(200, angles);
    }

    void pieChart(float diameter, int[] data) {
        float lastAngle = 0;
        for (int i = 0; i < data.length; i++) {
            float gray = map(i, 0, data.length, 0, 255);
            fill(gray);
            arc(width/2, height/2, diameter+i*5, diameter+i*5, lastAngle + mouseX/10, lastAngle+radians(data[i]) + mouseX/10);
            lastAngle += radians(data[i]);
        }
    }

}
