/*TODO:
    CustomInput class, that loads a json with user defined Input Strokes
*/

package com.jojpeg.input;

import com.jojpeg.controllers.actionController.ActionController;
import processing.core.PApplet;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by J4ck on 21.07.2017.
 */
public abstract class Input {

    protected int drawGUI = Modifier.ALT;


    public static class Key{
        public static int ESC = 27;
        public static int SPACE = 32;
        public static int BACKSPACE = 8;
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

    static int lastKey;
    static char lastChar;

    public static ArrayList<Action> defaultActions = new ArrayList<>();
    public static ArrayList<Action> actions = new ArrayList<>();
    static HashMap<ActionController, ArrayList<Action>> actionMap = new HashMap<>();

    Input(){
        keyCodes = new int[10];
        chars = new char[10];
        actions = new ArrayList<>();
    }

    public void update() {
        ArrayList<Action> actionList = new ArrayList<>();
        actionList.addAll(defaultActions);
        actionList.addAll(actions);


        for (Action action : actionList){
            if(action.continous){
                if(keyIsDown(action.key.value)) {
                    action.Invoke();
                }
            }else {
                if(onIsDown(action.key.value)){
                    action.Invoke();
                }
            }
        }
    }

    public void draw(PApplet p){
        if(keyCodeIsDown(drawGUI)){
            drawGUI(p);
        }
    }

    protected abstract void drawGUI(PApplet p);
    public abstract void translate();

    /***
     *
     * @param key to check if pressed
     * @param modifier  to check if pressed
     * @param actionInfo to access the keyStroke intention
     * @return value combination is pressed
     */
    private boolean keyComboIsDown(char key, int modifier, String actionInfo){
//        actions.put(value + "+" + modifier, actionInfo);
        boolean keyDown = keyCharIsDown(key);
        boolean modifierDown = keyCodeIsDown(modifier);
        return keyDown && modifierDown;
    }

    /***
     *
     * @param key to check if up
     * @return value is up
     */
    public boolean keyIsUp(char key){
        return lastKey == key;
    }

    /***
     *
     * @param key to check if up
     * @return value is up
     */
    public boolean keyIsUp(int key){
        return lastKey == key;
    }

    /***
     *
     * @param key to check if pressed
     * @return value is pressed
     */
    public boolean onIsDown(Object key){
        if(key.getClass().equals(Character.class))return onCharIsDown((char)key);
        if(key.getClass().equals(Integer.class))return onCodeIsDown((int)key);
        System.out.println("ERROR: False type: " + key.getClass());
        return false;
    }

    /***
     *
     * @param key to check if pressed
     * @return value is pressed
     */
    public boolean keyIsDown(Object key){
        if(key.getClass().equals(Character.class)) return keyCharIsDown((char)key);
        if(key.getClass().equals(Integer.class))return keyCodeIsDown((int)key);
        System.out.println("ERROR: False type: " + key.getClass());
        return false;
    }

    private boolean keyCharIsDown(char key){
        for (int i = 0; i < chars.length; i++) {
            char k = chars[i];
            if(k == key){
                System.out.println(k + " = " + key);
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

    private boolean onCodeIsDown(int keyCode){
        for (int k : keyCodesDown) {
            if (keyCode == k) return true;
        }
        return false;
    }

    private boolean onCharIsDown(char key){
        for (int i = 0; i < charsDown.length; i++) {
            char k = chars[i];
            if(k == key){
                System.out.println(k + " = " + key);
                return onCodeIsDown(keyCodesDown[i]);
            }
        }
        return false;
    }

    public void released(int k){
        int i = getIndexOf(k);
        lastKey = keyCodes[i];
        lastChar = chars[i];
        chars[i] = 0;
        keyCodes[i] = 0;
        //keys();
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

            //keys();
        }
    }

    public static void clearDownEvents(){
        keyCodesDown = new int[10];
        charsDown = new char[10];
    }

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

    public String keys(){
        return (
                  chars[0] + "|"
                + chars[1] + "|"
                + chars[2] + "|"
                + chars[3] + "|"
                + chars[4] + "|"
                + chars[5] + "|"
                + chars[6] + "|"
                + chars[7] + "|"
                + chars[8] + "|"
                + chars[9]
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

    private void registerActionController(ActionController controller){

        ArrayList<Action> newActions = new ArrayList<>();
        Field[] fields = controller.getClass().getFields();

        for (Field field : fields){
            try {
                System.out.println("Registered Action: " + field.getName());
                if(field.getType().equals(Action.class)){
                    newActions.add((Action)field.get(controller));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        actionMap.put(controller,newActions);
        actions = newActions;
        System.out.println("Actions size: " + actions.size());
    }

    public void addActions(ActionController controller){
        if(actionMap.containsKey(controller)){
            System.out.println("Adding existing actions of " + controller);
            actions = actionMap.get(controller);
        }else {
            System.out.println("Registering actions of " + controller);
            registerActionController(controller);
        }

        translate();
    }



}// end Input


