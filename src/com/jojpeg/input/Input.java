/*TODO:
    CustomInput class, that loads a json with user defined Input Strokes
*/

package com.jojpeg.input;
import com.jojpeg.Renderer;
import com.jojpeg.controllers.actionController.ActionHandler;
import processing.core.PApplet;
import processing.core.PGraphics;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;

/**
 * Created by J4ck on 21.07.2017.
 */
public abstract class Input {

    protected Action drawGui = new Action(Modifier.ALT, "Show Key Mapping"){

        @Override
        public void SetParameters() {
            continous = true;
        }

        @Override
        public void Invoke() {

        }
    };

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

    public static ArrayList<Action> defaultTriggerActions = new ArrayList<>();
    public static ArrayList<Action> triggerActions = new ArrayList<>();

    public static ArrayList<Action> defaultContinuousActions = new ArrayList<>();
    public static ArrayList<Action> continuousActions = new ArrayList<>();

    private ArrayList<Action> allActions = new ArrayList<>();

    static HashMap<ActionHandler, ArrayList<ArrayList<Action>>> actionMap = new HashMap<>();

    private boolean actionsModified = false;

    Input(){
        defaultContinuousActions.add(drawGui);
        keyCodes = new int[10];
        chars = new char[10];
        triggerActions = new ArrayList<>();
    }

    protected abstract void drawGUI(PApplet p, Renderer renderer);

    public abstract void translate();

    public void update() {

        //TODO: Add conts in trigger() remove when key released
        for (Action action : continuousActions){
            if(keyIsDown(action.key.value) || keyIsDown(action.getSecondary().value)) {
                action.Invoke();
            }
        }
    }

    public void trigger(){
        actionsModified = false;

        try {
            for (Action action : allActions) {
                if (keyIsDown(action.key.value) || keyIsDown(action.getSecondary().value)) {
                    System.out.println(action.getKeyInfo());
                    if (!action.continous) {
                        action.Invoke();
                        if(actionsModified) break;
                    }
                }
            }
        } catch (ConcurrentModificationException e){
            System.out.println("Concurrent Modification Exception");
        }
    }

    public void draw(PApplet p, Renderer renderer){
        if(keyIsDown(drawGui.key.value) || keyIsDown(drawGui.secondaryKey.value)){
            drawKeyMap(p,renderer);
        }
    }

    public void drawKeyMap(PApplet p, Renderer renderer){
        PGraphics canvas = p.createGraphics(renderer.getPlane().renderWidth, renderer.getPlane().renderHeight);
        canvas.beginDraw();

        //TODO: Size

        int textSize = 15;
        int padding = 5;
        canvas.noStroke();
        canvas.textSize(textSize);
        float y = 50;

        for(Action a : Input.triggerActions){
            canvas.fill(0);
            canvas.rect(15 ,y + 1, 200,-19);
            canvas.fill(255);
            canvas.text(a.getKeyName() + " : " + a.getKeyInfo(), 20, y);
            y += textSize + padding;
        }
        canvas.endDraw();

        renderer.addLayer(canvas,255);

        drawGUI(p, renderer);
    }

    /***
     *
     * @param key to check if pressed
     * @param modifier  to check if pressed
     * @param actionInfo to access the keyStroke intention
     * @return value combination is pressed
     */
    private boolean keyComboIsDown(char key, int modifier, String actionInfo){
//        triggerActions.put(value + "+" + modifier, actionInfo);
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
        if(key == null) return false;
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
        if(key == null) return false;
        if(key.getClass().equals(Character.class)) return keyCharIsDown((char)key);
        if(key.getClass().equals(Integer.class)) return keyCodeIsDown((int)key);
        System.out.println("ERROR: False type: " + key.getClass());
        return false;
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
        trigger();
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

    private void registerActionController(ActionHandler controller){
        actionsModified = true;
        ArrayList<Action> newTriggers = new ArrayList<>();
        ArrayList<Action> newContinuous = new ArrayList<>();
        Field[] fields = controller.getClass().getFields();

        for (Field field : fields){
            try {
//                System.out.println("Registered Action: " + field.getName());
                if(field.getType().equals(Action.class)){
                    Action action = (Action)field.get(controller);
                    if(action.continous) newContinuous.add(action);
                    else newTriggers.add(action);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<ArrayList<Action>> actions = new ArrayList<>();
        actions.add(newTriggers);
        actions.add(newContinuous);

        actionMap.put(controller, actions);

    }

    public void addActions(ActionHandler controller){
        if(actionMap.containsKey(controller)){
            System.out.println("Adding existing triggerActions of " + controller);
        }else {
            System.out.println("Registering triggerActions of " + controller);
            registerActionController(controller);
        }

        triggerActions.clear();
        continuousActions.clear();
        allActions.clear();

        triggerActions.addAll(actionMap.get(controller).get(0));
        triggerActions.addAll(defaultTriggerActions);

        continuousActions.addAll(actionMap.get(controller).get(1));
        continuousActions.addAll(defaultContinuousActions);

        allActions.addAll(triggerActions);
        allActions.addAll(continuousActions);

        PApplet.printArray(allActions);

        translate();
    }

    public void printInfos(PApplet p){
        p.println("____");
        p.println(p.key + "  KeyCode: " + p.keyCode + " -> (char):" + (char)p.keyCode);
        p.println(p.key + "  (int): " + (int) p.key);
        p.println(keys());

    }

}// end Input


