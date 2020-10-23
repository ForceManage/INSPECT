package com.forcemanage.inspect.attributes;

public class USER_Attributes {
    private int uID;
    private String uName;
    private int uCode;
    private String clientName;

    public USER_Attributes(int uID, String uName, int uCode, String clientName) {
        this.uID = uID;
        this.uName = uName;
        this.uCode = uCode;
        this.clientName = clientName;
     }

    public int getuID() {
        return uID;
    }

    public String getuName() {return uName; }

    public int getuCode() {
        return uCode;
    }

    public String getClientName() {
        return clientName;
    }


 }
