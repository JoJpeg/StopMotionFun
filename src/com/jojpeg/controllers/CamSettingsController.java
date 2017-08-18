package com.jojpeg.controllers;

import com.jojpeg.cam.Cam;
import com.jojpeg.Model;
import com.jojpeg.Renderer;
import com.jojpeg.SaveSystem;
import com.jojpeg.controllers.actionController.ActionHandler;
import com.jojpeg.controllers.actionController.CamSettingsActionHandler;
import processing.core.PApplet;
import processing.core.PGraphics;

public class CamSettingsController extends Controller {
    public CamSettingsActionHandler actionHandler = new CamSettingsActionHandler(this);
    public Cam cam;
    public CamSettingsModel model = new CamSettingsModel();

    public class CamSettingsModel extends Model<CamSettingsModel>{
        int camIndex = 0;
    }

    public CamSettingsController(Cam cam) {
        this.cam = cam;
    }

    @Override
    public void update(PApplet p, Renderer renderer) {

    }

    @Override
    public void lateUpdate(PApplet p, Renderer renderer) {

        PGraphics canvas = p.createGraphics(renderer.getPlane().renderWidth, renderer.getPlane().renderHeight);
        canvas.beginDraw();

        int textSize = 15;
        int padding = 5;
        canvas.noStroke();
        canvas.textSize(textSize);
        float y = 50;
        float x = 200;


        int scope = 5;
        for (int i = getCamIndex() - scope; i < getCamIndex() + scope; i++) {

            int index = i;
            if(scope * 2 + 1 < cam.getAvailableCamsCached().length) {
                if (i < 0) {
                    index = (cam.getAvailableCamsCached().length - 1) + i;
                }
            }else {
                index = i + scope;
                if(index > cam.getAvailableCamsCached().length - 1) break;
            }

            canvas.fill(160);
            if(index == getCamIndex())canvas.fill(255);

            canvas.rect(x - 5 ,y + 1, 800,-19);
            canvas.fill(0);

            String text = index + " >" + cam.getAvailableCamsCached()[index];//.split("name=")[1];

            canvas.text(text, x, y);
            y += textSize + padding;
        }
        canvas.endDraw();

        renderer.addLayer(cam.getRealtime(), 255);

        renderer.addLayer(canvas,255);
    }

    @Override
    public void save(SaveSystem saveSystem) {

    }

    @Override
    public void load(SaveSystem saveSystem) {

    }

    @Override
    public ActionHandler<?> getActionController() {
        return actionHandler;
    }

    public void setCamIndex(int index){
        int max = cam.getAvailableCamsCached().length - 1;
        if(index < 0) {
            model.camIndex = max;
            return;
        }
        if(index > max){
            model.camIndex = 0;
            return;
        }
        model.camIndex = index;
    }

    public int getCamIndex(){
        return model.camIndex;
    }

}
