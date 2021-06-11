package com.forcemanage.inspect.attributes;

public class Projectlistdata {

    private String address;
    private String projectId;
    private String addressNo;
    private String status;
    private String type;
    private String suburb;



    public Projectlistdata(String address, String projectId, String addressNo, String status, String type, String suburb) {

        this.address = address;
        this.projectId = projectId;
        this.addressNo = addressNo;
        this.status = status;
        this.type = type;
        this.suburb = suburb;
    }



    public String getprojectId() {
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

    public String getaddressNo() {
        return addressNo;
    }

    public String getsuburb() {
        return suburb;
    }



}
