package com.jojpeg.cam;

import com.jojpeg.Frame;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class AsyncFrameHandler {

    PApplet p;

    private ArrayList<Frame> frameQueue = new ArrayList<>();

    public AsyncFrameHandler(PApplet p) {
        this.p = p;
    }

    public Frame loadAsyncFrame(String imgPath, String thumbPath) {
        Frame frame = frameQueue.get(0);
        frame.setFullResPath(imgPath);

        Thread loadImage = new Thread() {
            @Override
            public void run() {
                PImage image = frame.getFullResImage();
                PImage newImage = p.loadImage(imgPath);
                System.out.println(" > Image Requested");
                while (newImage.width == 0){

                }
                if(newImage.width != -1){
                    replacePixels(image, newImage);
                    frame.imageLoded = true;
                    frame.calculateScreenResImage(p.width,p.height, true);
                    System.out.println(" > Image Loaded");
                    return;
                }
                System.out.println(" > Image: Loading Failed");
            }

            private void replacePixels(PImage oldImage, PImage newImage){
                newImage.loadPixels();
                oldImage.resize(newImage.width, newImage.height);
                oldImage.pixels = newImage.pixels;
                oldImage.updatePixels();
            }
        };

        Thread loadThumb = new Thread(){
            @Override
            public void run() {
                PImage thumb = frame.getThumbnail();
                PImage newThumb = p.loadImage(thumbPath);
                System.out.println(" > ThumbRequested");
                while (newThumb.width == 0) {

                }
                if (newThumb.width != -1) {
                    replacePixels(thumb, newThumb);
                    frame.thumbLoded = true;
                    System.out.println(" > Thumb Loaded");
                    return;
                }
                System.out.println(" > Thumb: Loading Failed");
            }

            private void replacePixels(PImage oldImage, PImage newImage){
                newImage.loadPixels();
                oldImage.resize(newImage.width, newImage.height);
                oldImage.pixels = newImage.pixels;
                oldImage.updatePixels();
            }
        };

        loadImage.start();
        loadThumb.start();

        frameQueue.remove(0);
        return frame;
    }

    public void addFrameToHandle(Frame frame){
        frameQueue.add(frame);
    }
}
