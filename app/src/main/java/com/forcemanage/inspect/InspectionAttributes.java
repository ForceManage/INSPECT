package com.forcemanage.inspect;

       //**
       // * Created by DAP 16.03.2020

public class InspectionAttributes {

    private int _inspectionId;
    private String _inspectionType;
    private String _inspectionStatus;
    private int _projectId;
    private String _inspectionDate;
    private String _inspector;
    private String _startDateTime;
    private String _endDateTime;

    public InspectionAttributes() {
    }
    public InspectionAttributes(int inspectionId, String inspectionType, String inspectionStatus, int projectId, String inspectionDate, String inspector, String startDateTime, String endDateTime) {
        this._inspectionId = inspectionId;
        this._inspectionType = inspectionType;
        this._inspectionStatus = inspectionStatus;
        this._projectId = projectId;
        this._inspectionType = inspectionType;
        this._inspectionDate = inspectionDate;
        this._inspector = inspector;
        this._startDateTime = startDateTime;
        this._endDateTime = endDateTime;
     }


    public void setinspectionId(int inspectionId) {this._inspectionId = inspectionId; }
    public int getinspectionId() {
        return this._inspectionId;
    }
    public void setinspectionType(String inspectionType) {this._inspectionType = inspectionType; }
    public String getinspectionType() {
        return this._inspectionType;
    }
    public void setinspectionStatus(String inspectionStatus){this._inspectionStatus = inspectionStatus; }
    public String getinspectionStatus() {return this._inspectionStatus; }
    public void setprojectId(int projectId) {this._projectId = projectId; }
    public int getprojectId() {
        return this._projectId;
    }
    public void setinspectionDate(String inspectionDate) {this._inspectionDate = inspectionDate; }
    public String getinspectionDate() {return this._inspectionDate; }
    public void setinspector(String inspector) {this._inspector = inspector; }
    public String getinspector() {return this._inspector; }
    public void setstartDateTime (String startDateTime) {this._startDateTime = startDateTime; }
    public String getstartDateTime() {return this._startDateTime; }
    public void setendDateTime (String endDateTime) {this._endDateTime = endDateTime; }
    public String getendDateTime() {return this._endDateTime;}


}
