package com.forcemanage.inspect;

/**
 * Created by DAP on 17/03/2020.
 */

public class CertificateInspectionAttributes {


    private int _inspectionId;
    private int _projectId;
    private String _datetime;
    private String _overview;
    private String _permit;
    private String _address;
    private String _stage;
    private String _notes;

    public CertificateInspectionAttributes() {
    }

    public CertificateInspectionAttributes(int inspectionId, int projectId, String datetime, String overview, String permit, String address,
                                           String stage,String notes) {

        this._inspectionId = inspectionId;
        this._projectId = projectId;
        this._datetime = datetime;
        this._overview = overview;
        this._permit = permit;
        this._address = address;
        this._stage = stage;
        this._notes = notes;

    }


    public void setinspectionId(int inspectionId) {this._inspectionId = inspectionId; }
    public int getinspectionId() {return this._inspectionId; }
    public void setprojectId(int projectId) {this._projectId = projectId; }
    public int getprojectId() {
        return this._projectId;
    }
    public void setdatetime(String datetime) {this._datetime = datetime; }
    public String getdatetime() {
        return this._datetime;
    }
    public void setoverview(String overview){this._overview = overview; }
    public String getoverview() {return this._overview; }
    public void stage(String stage) {this._stage = stage; }
    public String getstage() {return this._stage; }
    public void address(String address){this._address = address; }
    public String getaddress() {return this._address; }
    public void setpermit (String permit) {this._permit = permit; }
    public String getpermit() {return this._permit; }
    public void setnotes(String notes) {this._notes = notes; }
    public String getnotes() {return this._notes; }

}