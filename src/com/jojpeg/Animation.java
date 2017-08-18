package com.jojpeg;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;


public class Animation {

    public static int fps = 10;
    public ArrayList<Frame> frames = new ArrayList<>();
    private PApplet p;

    public int caretPos = 0;
    public int currentFrameIndex = 0;

    public AnimationModel model;

    public class AnimationModel extends Model{
        public String path;
        public String[] names;
        public int[] indices;
    }

    public PImage play(){
        if(frames.size() == 0) return null;
        PImage result = frames.get(currentFrameIndex % frames.size()).getImage();
        currentFrameIndex++;
        return result;
    }

    public Frame getFrame(int index){
        if(index > frames.size() - 1 || index < 0) return Frame.Null;
        if(frames.size() == 0) return  Frame.Null;
        Frame frame = frames.get(index);
        if(frame == null) return Frame.Null;
        return frames.get(index);
    }


    private PImage getThumbnail(int index){
        if(index > frames.size() - 1 || index < 0) return null;
        if(frames.size() == 0) return  null;
        Frame frame = frames.get(index);
        if(frame == null) return null;
        return frames.get(index).getThumbnail();
    }

    public PImage current(){
        if(frames.size() == 0) return  null;
        Frame frame = frames.get(caretPos);
        if(frame == null) return null;
        return frame.getImage();
    }

    public Animation(PApplet pApplet){
            this.p = pApplet;
    }


    public void addFrameAtPosition(Frame frame, int index){
        caretPos = index;
        frame.setName("frame" + index);
        frames.add(index, frame);
    }

    public void replaceFrame(Frame frame){
        frame.setName("frame" + caretPos);
        if(frames.size() > 0) frames.set(caretPos,frame);
        else addFrame(frame);
    }

    public void removeFrame(int index){
        if(index > frames.size() - 1 || index < 0) return;
        frames.remove(index);
        if(index == caretPos) caretLeft();
    }

    public void addFrame(Frame frame){
        frame.setName( "frame" + caretPos);

        if(frames.size() == 0) frames.add(frame);

        else {
            frames.add(caretPos + 1, frame);
            caretPos++;
        }
    }

    public int getFrameCount(){
        return frames.size();
    }

    public void moveFrame(int from, int to){

        if(from == to || to > frames.size()-1 || from > frames.size() - 1){
            System.out.println("Can't move frame from " + from + " to " + to);
        }

        boolean forward = true;
        if(from > to) forward = false;

        Frame frame = frames.get(from);

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


    public void caretLeft(){
        caretPos --;
        if(caretPos < 0) caretPos = frames.size() - 1;
        if(caretPos < 0) caretPos = 0;
    }

    public void caretRight(){
        caretPos ++;
        if(caretPos > frames.size() - 1) caretPos = 0;
    }

    public AnimationModel getModel() {
        if(model == null){
            model = new AnimationModel();
            model.indices = new int[frames.size()];
            model.names = new String[frames.size()];
            for (int i = 0; i < frames.size(); i++) {
                model.indices[i] = i;
                model.names[i] = frames.get(i).getName();

            }
        }

        return model;
    }

    public ArrayList<Frame> getFrames() {
        return frames;
    }

    public Frame makeFrame(PImage image, String name){
        Frame frame = new Frame(image);
        frame.setName(name);
        return frame;
    }

}
