package com.forcemanage.inspect;

/**
 * Created by DAP on 17/03/2020.
 */

public class ActionItemAttributes {


    private int _inspectionId;
    private int _projectId;
    private int _aId;
    private String _date;
    private String _overview;
    private String _servicedBy;
    private String _relevantInfo;
    private String _serviceLevel;
    private String _reportImage;
    private String _image1;
    private String _com1;
    private String _itemStatus;
    private String _notes;

    public ActionItemAttributes() {
    }

    public ActionItemAttributes(int inspectionId, int projectId, int aId, String date, String overview, String servicedBy, String relevantInfo, String serviceLevel, String reportImage, String image1
            , String com1, String itemStatus, String notes) {

        this._inspectionId = inspectionId;
        this._projectId = projectId;
        this._aId = aId;
        this._date = date;
        this._overview = overview;
        this._servicedBy = servicedBy;
        this._relevantInfo = relevantInfo;
        this._serviceLevel = serviceLevel;
        this._reportImage = reportImage;
        this._image1 = image1;
        this._com1 = com1;
        this._itemStatus = itemStatus;
        this._notes = notes;

    }


    public void setinspectionId(int inspectionId) {this._inspectionId = inspectionId; }
    public int getinspectionId() {return this._inspectionId; }
    public void setprojectId(int projectId) {this._projectId = projectId; }
    public int getprojectId() {
        return this._projectId;
    }
    public void setaId(int aId) {this._aId = aId; }
    public int getaId() {return this._aId; }
    public void setdate(String date) {this._date = date; }
    public String getdate() {
        return this._date;
    }
    public void setoverview(String overview){this._overview = overview; }
    public String getoverview() {return this._overview; }
    public void setservicedBy(String servicedBy) {this._servicedBy = servicedBy; }
    public String getservicedBy() {return this._servicedBy; }
    public void setrelevantInfo(String relevantInfo){this._relevantInfo = relevantInfo; }
    public String getrelevantInfo() {return this._relevantInfo; }
    public void setserviceLevel (String serviceLevel) {this._serviceLevel = serviceLevel; }
    public String getserviceLevel() {return this._serviceLevel;}
    public void setReportImage(String reportImage) {this._reportImage = reportImage; }
    public String getReportImage() {return this._reportImage; }
    public void setImage1(String image1) {this._image1 = image1; }
    public String getimage1() {return this._image1; }
    public void setcom1(String com1) {this._com1 = com1; }
    public String getcom1() {return this._com1; }

    public void setitemStatus(String itemStatus) {this._itemStatus = itemStatus; }
    public String get_itemStatus() {return this._itemStatus; }
    public void setnotes(String notes) {this._notes = notes; }
    public String get_notes() {return this._notes; }

}