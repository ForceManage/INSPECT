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
    private String _label;
    private int _level;
    private int _parent;
    private int _pID;
    private String _image;
    private String _note;

    public InspectionAttributes() {
    }

    public InspectionAttributes(int inspectionId, String inspectionType, String inspectionStatus, int projectId, String inspectionDate,
                                String inspector, String startDateTime, String endDateTime, String label, int level, int parent, int pid, String image, String note){  //, int parent, int aID, String img1, String notes) {
        this._inspectionId = inspectionId;
        this._inspectionType = inspectionType;
        this._inspectionStatus = inspectionStatus;
        this._projectId = projectId;
        this._inspectionType = inspectionType;
        this._inspectionDate = inspectionDate;
        this._inspector = inspector;
        this._label = label;
        this._level = level;
        this._parent = parent;
        this._pID = pid;
        this._image = image;
        this._note = note;
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


    public void setlabel(String label) {this._label = label;
    }

    public String getlabel() {
        return this._label;
    }

    public void setlevel(int level) {
        this._level = level;
    }

    public int getlevel() {
        return _level;
    }

    public void setparent(int parent) {
        this._parent = parent;
    }

    public int getparent() {
        return _parent;
    }

    public void setpID(int pID) {
        this._pID = pID;
    }

    public int getpID() {
        return _pID;
    }

    public String getimage() {
        return _image;
    }

    public String getnote() {
        return _note;
    }

    public void setimage(String image) {
        this._image = image;
    }

    public void setnote(String note) {
        this._note = note;
    }
}
