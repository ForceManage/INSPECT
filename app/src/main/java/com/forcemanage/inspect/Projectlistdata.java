package com.forcemanage.inspect;

public class Projectlistdata {

    private String address;
    private String projectId;
    private String projectNo;
    private String status;
    private String type;
    private String suburb;


    public Projectlistdata() {
    }

    public Projectlistdata(String address, String projectId, String projNo, String status, String type, String suburb) {

        this.address = address;
        this.projectId = projectId;
        this.projectNo = projectNo;
        this.status = status;
        this.type = type;
        this.suburb = suburb;
    }



    public String getpropId() {
        return projectId;
    }

    public String getaddress() {
        return address;
    }

    public String gettype() {
        return type;
    }

    public String getstatus() {
        return status;
    }

    public String getpropNo() {
        return projectNo;
    }

    public String getsuburb() {
        return suburb;
    }



}
