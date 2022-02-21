package com.test.mina.message;

import com.test.mina.annotation.MessageMeta;

@MessageMeta(module=1)
public class ReqLogin extends Message {
    private String str1;
    private String str2;
    private Long len1;

    public String getStr1() {
        return str1;
    }

    public void setStr1(String str1) {
        this.str1 = str1;
    }

    public String getStr2() {
        return str2;
    }

    public void setStr2(String str2) {
        this.str2 = str2;
    }

    public Long getLen1() {
        return len1;
    }

    public void setLen1(Long len1) {
        this.len1 = len1;
    }
}
