package com.jojpeg;

import com.google.gson.Gson;
import processing.core.PApplet;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by J4ck on 12.07.2017.
 */
public class SaveSystem {

    String message = "Select File";
    Gson gson = new Gson();
    FileWriter writer;
    FileReader reader;
    File lastSelection;
    PApplet p;
    public static SaveSystem Instance;

    public SaveSystem(PApplet p) {
        this.p = p;
        Instance = this;
    }


    public void save(Object object){

        File selectedFile;
        FileDialog chooser = new FileDialog(p.frame,message,FileDialog.SAVE);
        chooser.setDirectory(System.getProperty("user.dir"));
        chooser.setVisible(true);
        chooser.setAlwaysOnTop(true);
        String result = chooser.getDirectory();
        String filename = chooser.getFile();
        if(filename != null) {
            selectedFile = new File(result, filename);
            lastSelection = selectedFile;
            saveObject(object, selectedFile);
        }
//        chooser.setFile(defaultSelection.getName());
//        p.selectOutput(message, "fileSelected");
    }

    public Object load(Object object){
        File selectedFile;
        FileDialog chooser = new FileDialog(p.frame,message,FileDialog.LOAD);
        chooser.setDirectory(System.getProperty("user.dir"));
        chooser.setVisible(true);
        chooser.setAlwaysOnTop(true);
        String result = chooser.getDirectory();
        String filename = chooser.getFile();
        if(filename != null) {
            selectedFile = new File(result, filename);
            lastSelection = selectedFile;
            return loadObject(object, selectedFile);
        }
        System.out.println("Loaded Null");
        return null;
    }

    public void saveObject(Object object, File selection){
        try {
            writer = new FileWriter(selection.getPath());
            gson.toJson(object, writer);
            writer.close();
            System.out.println("Save Success");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object loadObject(Object object, File selection){
        try{

            reader = new FileReader(selection);
            Object result = gson.fromJson(reader, object.getClass());
            reader.close();
            System.out.println("Load Success");
            return result;

        }catch (Exception e){
            e.printStackTrace();
        }
        return object;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public File getLastSelection() {
        return lastSelection;
    }

    public String dirDialog(){
        FileDialog chooser = new FileDialog(p.frame,message,FileDialog.LOAD);
        return chooser.getDirectory();
    }
}
