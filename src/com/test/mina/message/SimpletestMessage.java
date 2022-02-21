package com.test.mina.message;

import com.test.mina.annotation.*;

@MessageMeta(module=3)
public class SimpletestMessage extends Message{
    //private  int intVal;
    //private  String len1str;
    //private boolean boolVal;
    @ListField(value=1)
    private byte[] len1byte;
    //private  double doubleVal;
    private boolean boolVal2;
    //@StringField(value=1)
    //private  String len2str;
    @ListField(value=0)
    private byte[] len2byte;
    private long uintval;

    public long getUintval() {
        return uintval;
    }

    public void setUintval(long uintval) {
        this.uintval = uintval;
    }

    public byte[] getLen1byte() {
        return len1byte;
    }

    public void setLen1byte(byte[] len1byte) {
        this.len1byte = len1byte;
    }

    public boolean isBoolVal2() {
        return boolVal2;
    }

    public void setBoolVal2(boolean boolVal2) {
        this.boolVal2 = boolVal2;
    }

    public byte[] getLen2byte() {
        return len2byte;
    }

    public void setLen2byte(byte[] len2byte) {
        this.len2byte = len2byte;
    }
}
