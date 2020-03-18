package com.forcemanage.inspect;

/**
 * Created by DAP on 16/03/2020.
 */

public class ProjectAttributes {


    private int _projectId;
    private String _addressNumber;
    private String _projectAddress;
    private String _projectSuburb;
    private String _buildType;
    private String _buildPermitNmbr;
    private String _buildClass;
    private int _numberLevels;
    private String _projectPhoto;
    private String _keyRequired;
 //   private String _ocDate;
 //   private String _ocNmbr;
    private String _floorType;
    private String _roofType;
    private String _wallType;
    private String _projectNote;


    public ProjectAttributes() {
    }
    public ProjectAttributes(int projectId,  String addressNumber, String projectAddress, String projectSuburb, String buildType, String buildPermitNmbr, String buildClass, int numberLevels,
                             String projectPhoto, String keyRequired, String floorType, String roofType, String wallType, String projectNote) {
        this._projectId = projectId;
        this._addressNumber = addressNumber;
        this._projectAddress = projectAddress;
        this._projectSuburb = projectSuburb;
        this._buildType = buildType;
        this._buildPermitNmbr = buildPermitNmbr;
        this._buildClass = buildClass;
        this._numberLevels = numberLevels;
        this._projectPhoto = projectPhoto;
        this._keyRequired = keyRequired;
 //       this._ocDate = ocDate;
  //      this._ocNmbr = ocNmbr;
        this._floorType = floorType;
        this._roofType = roofType;
        this._wallType = wallType;
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
    public void setbuildType (String buildType) {this._buildType = buildType; }
    public String getbuildType () {return this._buildType;}
    public void setbuildPermitNmbr (String buildPermitNmbr) {this._buildPermitNmbr = buildPermitNmbr; }
    public String getbuildPermitNmbr () {return this._buildPermitNmbr;}
    public void setbuildClass(String buildClass) {this._buildClass = buildClass; }
    public String getbuildClass() {return this._buildClass; }
    public void setnumberLevels (int numberLevels) {this._numberLevels = numberLevels; }
    public int getnumberLevels () {return this._numberLevels; }
    public void setprojectPhoto (String projectPhoto) {this._projectPhoto = projectPhoto; }
    public String getprojectPhoto () {return this._projectPhoto; }
    public void setkeyRequired (String keyRequired) {this._keyRequired = keyRequired; }
    public String getkeyRequired () {return this._keyRequired; }
 //   public void setocDate (String ocDate) {this._ocDate = ocDate; }
 //   public String getocDate () {return this._ocDate; }
 //   public void setocNmbr (String ocNmbr) {this._ocNmbr = ocNmbr; }
 //   public String getocNmbr () {return this._ocNmbr; }
    public void setflooytype (String floorType) {this._floorType = floorType; }
    public String getfloorType () {return this._floorType; }
    public void setrooftype (String roofType) {this._roofType = roofType; }
    public String getroofType () {return this._roofType; }
    public void setwalltype (String wallType) {this._wallType = wallType; }
    public String getwallType () {return this._wallType; }
    public void setprojectNote (String projectNote) {this._projectNote = projectNote; }
    public String getprojectNote () {return this._projectNote; }

    }

