package com.jojpeg;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by J4ck on 16.07.2017.
 */
public class Animation {

    public ArrayList<PImage> frames = new ArrayList<PImage>();
    private PApplet p;


    public Animation(PApplet pApplet){
            this.p = pApplet;
    }

    public void addFrame(PImage frame, int index){
        frames.add(index, frame);
    }

    public void moveFrame(int from, int to){

        if(from == to || to > frames.size()-1 || from > frames.size() - 1){
            System.out.println("Can't move frame from " + from + " to " + to);
        }

        boolean forward = true;
        if(from > to) forward = false;

        PImage frame = frames.get(from);

        int firstIndexToMove = from + 1;
        int lastIndexToMove = to;

        if (!forward) {
            firstIndexToMove = from - 1;
            lastIndexToMove = to;
        }
        int loopDir = forward ? 1 : -1;

        for (int i = firstIndexToMove; i != lastIndexToMove + loopDir; i += loopDir) {
//            p.println(i + " " + (i - loopDir));
            frames.set(i - loopDir, frames.get(i));
        }
        frames.set(to, frame);

    }



}
