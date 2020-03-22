package com.forcemanage.inspect;

/**
 * Created by DAP on 17/03/2020.
 */

public class InspectionItemAttributes {




        private int _inspectionId;
        private int _projectId;
        private int _aId;
        private String _dateInspected;
        private String _overview;
        private String _servicedBy;
        private String _relevantInfo;
        private int _serviceLevel;
        private String _reportImage;
        private String _image1;
        private String _com1;
        private String _image2;
        private String _com2;
        private String _image3;
        private String _com3;
        private String _image4;
        private String _com4;
        private String _image5;
        private String _com5;
        private String _image6;
        private String _com6;
        private String _image7;
        private String _com7;
        private String _itemStatus;
        private String _notes;

        public InspectionItemAttributes() {
        }

        public InspectionItemAttributes(int inspectionId, int projectId, int aId, String dateInspected, String overview, String servicedBy, String relevantInfo, int serviceLevel, String reportImage, String image1
                , String com1, String image2,String com2, String image3, String com3, String image4, String com4, String image5, String com5, String image6, String com6, String image7, String com7
                ,String itemStatus, String notes) {

            this._inspectionId = inspectionId;
            this._projectId = projectId;
            this._aId = aId;
            this._dateInspected = dateInspected;
            this._overview = overview;
            this._servicedBy = servicedBy;
            this._relevantInfo = relevantInfo;
            this._serviceLevel = serviceLevel;
            this._reportImage = reportImage;
            this._image1 = image1;
            this._com1 = com1;
            this._image2 = image2;
            this._com2 = com2;
            this._image3 = image3;
            this._com3 = com3;
            this._image4 = image4;
            this._com4 = com4;
            this._image5 = image5;
            this._com5 = com5;
            this._image6 = image6;
            this._com6 = com6;
            this._image7 = image7;
            this._com7 = com7;
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
        public void setdateInspected(String dateInspected) {this._dateInspected = dateInspected; }
        public String getdateInspected() {
            return this._dateInspected;
        }
        public void setoverview(String overview){this._overview = overview; }
        public String getoverview() {return this._overview; }
        public void setservicedBy(String servicedBy) {this._servicedBy = servicedBy; }
        public String getservicedBy() {return this._servicedBy; }
        public void setrelevantInfo(String relevantInfo){this._relevantInfo = relevantInfo; }
        public String getrelevantInfo() {return this._relevantInfo; }
        public void setserviceLevel (int serviceLevel) {this._serviceLevel = serviceLevel; }
        public int getserviceLevel() {return this._serviceLevel;}
        public void setReportImage(String reportImage) {this._reportImage = reportImage; }
        public String getReportImage() {return this._reportImage; }
        public void setImage1(String image1) {this._image1 = image1; }
        public String getimage1() {return this._image1; }
        public void setcom1(String com1) {this._com1 = com1; }
        public String getcom1() {return this._com1; }
        public void setImage2(String image2) {this._image2 = image2; }
        public String getimage2() {return this._image2; }
        public void setcom2(String com2) {this._com2 = com2; }
        public String getcom2() {return this._com2; }
        public void setImage3(String image3) {this._image3 = image3; }
        public String getimage3() {return this._image3; }
        public void setcom3(String com3) {this._com3 = com3; }
        public String getcom3() {return this._com3; }
        public void setImage4(String image4) {this._image4 = image4; }
        public String getimage4() {return this._image4; }
        public void setcom4(String com4) {this._com4 = com4; }
        public String getcom4() {return this._com4; }
        public void setImage5(String image5) {this._image5 = image5; }
        public String getimage5() {return this._image5; }
        public void setcom5(String com5) {this._com5 = com5; }
        public String getcom5() {return this._com5; }
        public void setImage6(String image6) {this._image6 = image6; }
        public String getimage6() {return this._image6; }
        public void setcom6(String com6) {this._com6 = com6; }
        public String getcom6() {return this._com6; }
        public void setImage7(String image7) {this._image7 = image7; }
        public String getimage7() {return this._image7; }
        public void setcom7(String com7) {this._com7 = com7; }
        public String getcom7() {return this._com7; }
        public void setitemStatus(String itemStatus) {this._itemStatus = itemStatus; }
        public String get_itemStatus() {return this._itemStatus; }
        public void setnotes(String notes) {this._notes = notes; }
        public String get_notes() {return this._notes; }

    }