package com.jojpeg;

import processing.core.PImage;

public class Frame{
    private PImage thumbnail;
    private PImage image;
    private String name;
    public boolean async = false;
    public boolean imageLoded = true;
    public boolean thumbLoded = true;

    public static Frame Null = new Frame(Renderer.NullImage, Renderer.NullThumbnail);

    private String fullResPath;

    public Frame(PImage frame) {
        this.image = frame;
    }

    public Frame(PImage image, PImage thumbnail) {
        this.image = image;
        this.thumbnail = thumbnail;
    }

    public PImage getImage(){
        if(async){
            if(imageLoded) return image;
            if(thumbLoded) return thumbnail;
        }
        if(image == null) return Renderer.NullImage;
        return image;
    }

    public void setImage(PImage image){
        this.image = image;
    }

    public PImage getThumbnail(){
        if(thumbnail == null){
            return Renderer.NullImage;
        }
        return thumbnail;
    }

    public void setThumbnail(PImage thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullResPath() {
        return fullResPath;
    }

    public void setFullResPath(String fullResPath) {
        this.fullResPath = fullResPath;
    }

    public void setAsync() {
        this.async = true;
        this.imageLoded = false;
        this.thumbLoded = false;
    }
}
