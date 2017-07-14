package com.jojpeg;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by J4ck on 12.07.2017.
 */
public class SaveSystem {

    String path;
    Gson gson = new Gson();
    FileWriter writer;
    FileReader reader;

    public SaveSystem(String path) {
        this.path = path;
    }

    public void save(Object object){
        try {
            writer = new FileWriter(path + "/save.json");
            gson.toJson(object, writer);
            writer.close();
            System.out.println("Save Success");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object load(Object object){
        try{
            reader = new FileReader(path + "/save.json");
            Object result = gson.fromJson(reader, object.getClass());
            reader.close();
            System.out.println("Load Success");
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return object;
    }

}
