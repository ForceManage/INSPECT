package com.forcemanage.inspect;

/**
 * Created by DAP on 16/03/2020.
 */

public class ProjectAttributes {


    private int _projectId;
    private String _addressNumber;
    private String _projectAddress;
    private String _projectSuburb;
    private String _infoA;
    private String _infoB;
    private String _infoC;
    private String _infoD;
    private String _projectPhoto;
    private String _infoE;
 //   private String _ocDate;
 //   private String _ocNmbr;
    private String _infoH;
    private String _infoI;
    private String _infoJ;
    private String _projectNote;


    public ProjectAttributes() {
    }
    public ProjectAttributes(int projectId,  String addressNumber, String projectAddress, String projectSuburb, String infoA, String infoB, String infoC, String infoD,
                             String projectPhoto, String infoE, String infoH, String infoI, String infoJ, String projectNote) {
        this._projectId = projectId;
        this._addressNumber = addressNumber;
        this._projectAddress = projectAddress;
        this._projectSuburb = projectSuburb;
        this._infoA = infoA;
        this._infoB = infoB;
        this._infoC = infoC;
        this._infoD = infoD;
        this._projectPhoto = projectPhoto;
        this._infoE = infoE;
 //       this._ocDate = ocDate;
  //      this._ocNmbr = ocNmbr;
        this._infoH = infoH;
        this._infoI = infoI;
        this._infoJ = infoJ;
        this._projectNote = projectNote;
    }


    public void setprojectId(int projectId) {this._projectId = projectId; }
    public int getProjectId() {return this._projectId; }
    public void setAddressNumber(String addressNumber) {this._addressNumber = addressNumber; }
    public String getAddressNumber() {return this._addressNumber; }
    public void setprojectAddress(String projectAddress){this._projectAddress = projectAddress; }
    public String getprojectAddress() {return this._projectAddress; }
    public void setprojectSuburb (String projectSuburb) {this._projectSuburb = projectSuburb; }
    public String getprojectSuburb() {return this._projectSuburb; }
    public void setinfoA (String infoA) {this._infoA = infoA; }
    public String getinfoA () {return this._infoA;}
    public void setinfoB (String infoB) {this._infoB = infoB; }
    public String getinfoB () {return this._infoB;}
    public void setinfoC(String infoC) {this._infoC = infoC; }
    public String getinfoC() {return this._infoC; }
    public void setinfoD (String infoD) {this._infoD = infoD; }
    public String getinfoD () {return this._infoD; }
    public void setprojectPhoto (String projectPhoto) {this._projectPhoto = projectPhoto; }
    public String getprojectPhoto () {return this._projectPhoto; }
    public void setinfoE (String infoE) {this._infoE = infoE; }
    public String getinfoE () {return this._infoE; }
 //   public void setocDate (String ocDate) {this._ocDate = ocDate; }
 //   public String getocDate () {return this._ocDate; }
 //   public void setocNmbr (String ocNmbr) {this._ocNmbr = ocNmbr; }
 //   public String getocNmbr () {return this._ocNmbr; }
    public void setflooytype (String infoH) {this._infoH = infoH; }
    public String getinfoH () {return this._infoH; }
    public void setinfoI (String infoI) {this._infoI = infoI; }
    public String getinfoI () {return this._infoI; }
    public void setinfoJ (String infoJ) {this._infoJ = infoJ; }
    public String getinfoJ () {return this._infoJ; }
    public void setprojectNote (String projectNote) {this._projectNote = projectNote; }
    public String getprojectNote () {return this._projectNote; }

    }

