package com.sample.postjson;

/**
 * Created by JinYoung on 2016-11-21.
 */

public class Person {
    private String ID;
    private String PW;

    public String getID() { return ID; }
    public void setID(String ID) { this.ID = ID; }
    public String getPW() { return PW; }
    public void setPW(String PW) { this.PW = PW; }

    @Override
    public String toString() { return "Person [ID=" + ID + ", PW =" + PW + "]"; }
}
