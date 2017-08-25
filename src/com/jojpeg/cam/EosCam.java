package com.jojpeg.cam;

import com.jojpeg.Frame;
import com.jojpeg.Renderer;
import com.jojpeg.cam.edsdk.ProcessingCanonCamera;
import edsdk.utils.CanonConstants;
import processing.core.PApplet;
import processing.core.PImage;

import java.io.File;

public class EosCam implements Cam{

    ProcessingCanonCamera cam;

    PApplet p;
    PImage lastLive;

    String dir;

    AsyncFrameHandler asyncFrameHandler;

//    private boolean initialized = false;

    public int qualityDivider = 2;

    public EosCam(PApplet p, String dir) {
        this.p = p;
        asyncFrameHandler = new AsyncFrameHandler(p);
        try{
            initialize(0);
            cam.setDirectory(dir);
            System.out.println("Initialized Cam on Folder: " + dir);
        }catch (Exception e){
            System.out.println("Cam Initialisation failed");
        }
    }

    @Override
    public void initialize(int index) {
        try {
//            initialized = true;
//        cam = new ProcessingCanonCamera(p);
            cam = new ProcessingCanonCamera(p, this);

            p.frameRate(30);

            p.printArray(cam.getAvailableImageQualities());
            cam.setImageQuality(CanonConstants.EdsImageQuality.EdsImageQuality_LJF);

            cam.setDeleteAfterDownload(false);
            cam.setAutoDownload(true);

//         automatically create small thumbnails for every jpeg that was downloaded
            cam.setThumbnailWidth(200);

//         a background thread will fetch the live view when it's on
//         alternatively you can call cam.read() inside the draw() loop.
            cam.setAutoUpdateLiveView(true);

//         start the live view
            cam.beginLiveView();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public PImage getRealtime() {

        if(cam != null && cam.isLiveViewOn() ){
            lastLive = cam.liveViewImage();

            return lastLive;
        }
        if(lastLive != null) return lastLive;
        return null;
    }

    @Override
    public Frame getFrame() {
        cam.takeImage();
        PImage asyncImage = new PImage(Renderer.NullImage.getImage());
        PImage asyncThumb = new PImage(Renderer.NullImage.getImage());

        Frame frame = new Frame(asyncImage);
        frame.setAsync();
        frame.setThumbnail(asyncThumb);

        asyncFrameHandler.addFrameToHandle(frame);
        return frame;
    }

    @Override
    public boolean ready() {
        return false;
    }

    @Override
    public String[] getAvailableCamsCached() {
        return new String[]{"Eos Cam"};
    }

    public void imageTaken(File image, File thumbnail ){
        asyncFrameHandler.loadAsyncFrame(image.getAbsolutePath(), thumbnail.getAbsolutePath());
//        System.out.println( img.width + " x " + img.height );
    }

    public void imageTakenRaw( File file ){
        System.out.println( "Raw Image taken: " + file.getAbsolutePath() );
    }

    public ProcessingCanonCamera getCam() {
        return cam;
    }
}
