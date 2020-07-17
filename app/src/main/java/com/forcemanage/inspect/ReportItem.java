package com.forcemanage.inspect;

/**
 * Created by DAP on 3/03/2020.
 */

public class ReportItem {


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
    private String label;
    private String date_time;
    private String permit;
    private String address;
    private String stage;


    public ReportItem(String BranchHead, String ParentLabel, String overview, String relevantInfo, String notes, String image1, String com1, String image2, String com2,
                      String image3, String com3, String image4, String com4, String image5, String com5, String label, String date_time, String permit,
                      String address, String stage) {

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
        this.label = label;
        this.date_time = date_time;
        this.permit = permit;
        this.address = address;
        this.stage = stage;
       }

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

    public String getStage() { return stage;
    }

}
