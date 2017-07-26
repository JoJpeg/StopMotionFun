package com.jojpeg.input;

import processing.core.PApplet;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by J4ck on 21.07.2017.
 */
public abstract class Input {

    protected int drawGUI = Modifier.ALT;

    public static class Key{
        public static int ESC = 27;
        public static int SPACE = 32;
        public static int TAB = 9;
        public static int UP = 38;
        public static int RIGHT = 39;
        public static int DOWN = 40;
        public static int LEFT = 37;
    }

    public static class Modifier{
        public static int SHIFT = 16;
        public static int CTRL = 17;
        public static int ALT = 18;
    }

    static int[] keyCodes;
    static int[] keyCodesDown;
    static char[] chars;
    static char[] charsDown;

    public static HashMap<String, String> actionInformations;
    static int lastKey;
    static char lastChar;

    Input(){
        keyCodes = new int[10];
        chars = new char[10];
        actionInformations = new HashMap<>();
    }

    public void draw(PApplet p){
        if(keyCodeIsDown(drawGUI)){
            drawGUI(p);
        }
        actionInformations.clear();
    }

    protected abstract void drawGUI(PApplet p);

    /***
     *
     * @param key to check if up
     * @param actionInfo to access the keyStroke intention
     * @return key is up
     */
    public boolean keyIsUp(char key, String actionInfo){
        actionInformations.put(key+"", actionInfo);
        return lastKey == key;
    }

    /***
     *
     * @param key to check if up
     * @param actionInfo to access the keyStroke intention
     * @return key is up
     */
    public boolean keyIsUp(int key, String actionInfo){
        String actionKey = getKeyCodeName(key);
        String keyName = actionKey != null? actionKey : key + "";

        actionInformations.put(keyName+"", actionInfo);
        return lastKey == key;
    }

    /***
     *
     * @param key to check if pressed
     * @param actionInfo to access the keyStroke intention
     * @return key is pressed
     */
    public boolean onIsDown(char key, String actionInfo){
        actionInformations.put(key+"", actionInfo);
        return onCharIsDown(key);
    }

    /***
     *
     * @param key to check if pressed
     * @param actionInfo to access the keyStroke intention
     * @return key is pressed
     */
    public boolean onIsDown(int key, String actionInfo){
        String actionKey = getKeyCodeName(key);
        String keyName = actionKey != null? actionKey : key + "";

        actionInformations.put(keyName, actionInfo);
        return onCodeIsDown(key);
    }

    /***
     *
     * @param key to check if pressed
     * @param actionInfo to access the keyStroke intention
     * @return key is pressed
     */
    public boolean keyIsDown(char key, String actionInfo){
        actionInformations.put(key+"", actionInfo);
        return keyCharIsDown(key);
    }

    /***
     *
     * @param key to check if pressed
     * @param actionInfo to access the keyStroke intention
     * @return key is pressed
     */
    public boolean keyIsDown(int key, String actionInfo){
        String actionKey = getKeyCodeName(key);
        String keyName = actionKey != null? actionKey : key + "";

        actionInformations.put(keyName, actionInfo);
        return keyCodeIsDown(key);
    }

    /***
     *
     * @param key to check if pressed
     * @param modifier  to check if pressed
     * @param actionInfo to access the keyStroke intention
     * @return key combination is pressed
     */
    public boolean keyComboIsDown(char key, int modifier, String actionInfo){
        actionInformations.put(key + "+" + modifier, actionInfo);
        boolean keyDown = keyCharIsDown(key);
        boolean modifierDown = keyCodeIsDown(modifier);
        return keyDown && modifierDown;
    }

    private boolean keyCharIsDown(char key){
        for (int i = 0; i < chars.length; i++) {
            char k = chars[i];
            if(k == key){
                return keyCodeIsDown(keyCodes[i]);
            }
        }
        return false;
    }

    private boolean keyCodeIsDown(int keyCode){
        for (int k : keyCodes) {
            if (keyCode == k) return true;
        }
        return false;
    }

    private boolean onCharIsDown(char key){
        for (int i = 0; i < charsDown.length; i++) {
            char k = charsDown[i];
            if(k == key){
                return onCodeIsDown(keyCodesDown[i]);
            }
        }
        return false;
    }

    private boolean onCodeIsDown(int keyCode){
        for (int k : keyCodesDown) {
            if (keyCode == k) return true;
        }
        return false;
    }

    public boolean keyIsDown(){
        for (int key : keyCodes) {
            if (key != 0) return true;
        }
        return false;
    }

    boolean mouseIsDown(int i){
        for (int key : keyCodes) {
            if (key == '`') return true;
        }
        return false;
    }


    public void released(int k){
        int i = getIndexOf(k);
        lastKey = keyCodes[i];
        lastChar = chars[i];
        chars[i] = 0;
        keyCodes[i] = 0;

        //showKeys();
    }

    public void newKey(int code, char key){
        if(isNewEntry(code)){
            int i = getNewIndex();

//            chars[i] = chars[0];
            chars[i] = key;
            charsDown[i] = key;
//            keyCodes[i] = keyCodes[0];
            keyCodes[i] = code;
            keyCodesDown[i] = code;

            //showKeys();
        }
    }

    public static void clearDownEvents(){
        keyCodesDown = new int[10];
        charsDown = new char[10];
    }

    /*
    public PVector getDir(char[] keys) {
        PVector dir = new PVector(0,0);
        int up = keys[0];
        int down = keys[1];
        int left = keys[2];
        int right = keys[3];

        if (keyIsDown(up)) dir.y = -1;
        else if (keyIsDown(down)) dir.y = 1;
        if (keyIsDown(left)) dir.x = -1;
        else if (keyIsDown(right)) dir.x = 1;

        return dir;
    }
    */

    boolean isNewEntry(int k){
        for (int i = 0; i < keyCodes.length; ++i) {
            if (keyCodes[i] == k) return false;
        }
        return true;
    }

    int getIndexOf(int k){
        for (int i = 0; i < keyCodes.length; ++i) {
            if (keyCodes[i] == k) return i;
        }
        return 0;
    }

    int getNewIndex(){
        for (int i = 0; i < keyCodes.length; ++i) {
            if (keyCodes[i] == 0) return i;
        }
        return -1;
    }

    public String showKeys(){
        return (
                 keyCodes[0] + "|"
                + keyCodes[1] + "|"
                + keyCodes[2] + "|"
                + keyCodes[3] + "|"
                + keyCodes[4] + "|"
                + keyCodes[5] + "|"
                + keyCodes[6] + "|"
                + keyCodes[7] + "|"
                + keyCodes[8] + "|"
                + keyCodes[9]
        );
    }

    public static String getKeyCodeName(int keyCode){
        for(Field f : Modifier.class.getDeclaredFields()){
            try {
                int field = f.getInt(f);
                if (keyCode == field){
                    return f.getName();
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        for(Field f : Key.class.getDeclaredFields()){
            try {
                int field = f.getInt(f);
                if (keyCode == field){
                    return f.getName();
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return  null;
    }



}// end Input


