package com.jojpeg.cam;

import com.jojpeg.Frame;
import processing.core.PImage;

/**
 * Created by J4ck on 18.07.2017.
 */
public interface Cam {
    void initialize(int index);

    /***
     *
     * @return the camera Input pointer.
     */
    PImage getRealtime();
    /***
     *
     * @return the Picture of the Frame it's called.
     */
    Frame getFrame();

    boolean ready();

    String[] getAvailableCamsCached();

}
