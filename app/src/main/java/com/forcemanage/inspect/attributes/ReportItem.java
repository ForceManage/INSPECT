package com.forcemanage.inspect.attributes;

/**
 * Created by DAP on 3/03/2020.
 */

public class ReportItem {

    private String typeObject;
    private String BranchHead;
    private String ParentLabel;
    private String overview;
    private String relevantInfo;
    private String notes;
    private String image1;
    private String com1;
    private String image2;
    private String com2;
    private String image3;
    private String com3;
    private String image4;
    private String com4;
    private String image5;
    private String com5;
    private String image6;
    private String com6;
    private String label;
    private String date_time;
    private String permit;
    private String address;
    private String stage;
    private String title_A;
    private String com_A;
    private String title_B;
    private String com_B;
    private String title_C;
    private String com_C;



    public ReportItem(String typeObject, String BranchHead, String ParentLabel, String overview, String relevantInfo, String notes, String image1, String com1, String image2, String com2,
                      String image3, String com3, String image4, String com4, String image5, String com5,  String image6, String com6, String label, String date_time, String permit,
                      String address, String stage, String title_A, String com_A, String title_B, String com_B,String title_C, String com_C) {

        this.typeObject = typeObject;
        this.BranchHead = BranchHead;
        this.ParentLabel = ParentLabel;
        this.overview = overview;
        this.relevantInfo = relevantInfo;
        this.notes = notes;
        this.image1 = image1;
        this.com1 = com1;
        this.image2 = image2;
        this.com2 = com2;
        this.image3 = image3;
        this.com3 = com3;
        this.image4 = image4;
        this.com4 = com4;
        this.image5 = image5;
        this.com5 = com5;
        this.image6 = image6;
        this.com6 = com6;
        this.label = label;
        this.date_time = date_time;
        this.permit = permit;
        this.address = address;
        this.stage = stage;
        this.title_A = title_A;
        this.com_A = com_A;
        this.title_B = title_B;
        this.com_B = com_B;
        this.title_C = title_C;
        this.com_C = com_C;
       }

    public  String getTypeObject() { return  typeObject;}

    public String getBranchHead() {
        return BranchHead;
    }

    public String getParentLabel() {
        return ParentLabel;
    }

    public String getImage1() {
        return image1;
    }

    public String getImage2() {
        return image2;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelevantInfo() {
        return relevantInfo;
    }

    public String getNotes() {
        return notes;
    }

    public String getCom1() {
        return com1;
    }

    public String getCom2() {
        return com2;
    }

    public String getImage3() {
        return image3;
    }

    public String getCom3() {
        return com3;
    }

    public String getImage4() {
        return image4;
    }

    public String getCom4() {
        return com4;
    }

    public String getImage5() {
        return image5;
    }

    public String getCom5() {
        return com5;
    }

    public String getImage6() {
        return image6;
    }

    public String getCom6() {
        return com6;
    }

    public String getLabel() {
        return label;
    }

    public String getDate_time() {
        return date_time;
    }

    public String getPermit() {
        return permit;
    }

    public String getAddress() {
        return address;
    }

    public String getStage() { return stage; }

    public String getTitle_A() { return title_A; }

    public String getTitle_B() { return title_B; }

    public String getTitle_C() { return title_C; }

    public String getCom_A() { return com_A; }

    public String getCom_B() { return com_B; }

    public String getCom_C() { return com_C; }

}
