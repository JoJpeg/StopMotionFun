package com.jojpeg.input;

public class Action {
    class Key<T>{
        public T value;
    }
    public Key key;
    public Key secondaryKey;

    private String keyName;
    private String actionInformation;
    public boolean continous = false;

    public Action(int key, String actionInformation) {
        setKey(key);
        this.actionInformation = actionInformation;
        SetParameters();
    }

    public Action(char key, String actionInformation) {
        setKey(key);
        this.actionInformation = actionInformation;
        SetParameters();
    }

    public void Invoke(){

    }

    public void SetParameters(){

    }

    public void setKey(int key){
        this.key = new Key<Integer>();
        this.key.value = key;
        String actionKey = Input.getKeyCodeName(key);
        String keyName = actionKey != null? actionKey : key + "";

        this.keyName = keyName;
    }

    public void setKey(char key){
        this.key = new Key<Character>();
        this.key.value = key;
        this.keyName = key + "";
    }

    public void setSecondary(int key){
        this.secondaryKey = new Key<Integer>();
        this.secondaryKey.value = key;
//        String actionKey = Input.getKeyCodeName(key);
//        String keyName = actionKey != null? actionKey : key + "";
//
//        this.keyName = keyName;
    }

    public void setSecondary(char key){
        this.secondaryKey= new Key<Character>();
        this.secondaryKey.value = key;
//        this.keyName = key + "";
    }

    public Key getSecondary(){
        if(secondaryKey == null){
            return new Key<Integer>();
        }
        return secondaryKey;
    }

    public String getKeyName() {
        return keyName;
    }

    public String getKeyInfo(){
        return actionInformation;
    }

    @Override
    public String toString() {
        return "" + actionInformation + '\'';
    }
}
