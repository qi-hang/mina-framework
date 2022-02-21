package com.test.mina.message;

import com.test.mina.annotation.MessageMeta;

@MessageMeta(module=2)
public class TestMessage extends Message{
    private int intVal;
    private double doubleVal;
    private boolean boolVal;
    //private byte bytesVal;
    private String str1;
    //private Long longval;

    public int getIntVal() {
        return intVal;
    }

    public void setIntVal(int intVal) {
        this.intVal = intVal;
    }

    public double getDoubleVal() {
        return doubleVal;
    }

    public void setDoubleVal(double doubleVal) {
        this.doubleVal = doubleVal;
    }

    public boolean isBoolVal() {
        return boolVal;
    }

    public void setBoolVal(boolean boolVal) {
        this.boolVal = boolVal;
    }


    public String getStr1() {
        return str1;
    }

    public void setStr1(String str1) {
        this.str1 = str1;
    }

}
