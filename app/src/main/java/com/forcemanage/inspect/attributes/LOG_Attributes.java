package com.forcemanage.inspect.attributes;

public class LOG_Attributes {


        private int uID;
        private int projId;
        private int iId;
        private String start;
        private String end;

    public LOG_Attributes(int uID, int projId, int iId, String start, String end) {
        this.uID = uID;
        this.projId = projId;
        this.iId = iId;
        this.start = start;
        this.end = end;
    }

    public int getuID() {
        return uID;
    }

    public int getProjId() {
        return projId;
    }

    public int getiId() {
        return iId;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }
}
