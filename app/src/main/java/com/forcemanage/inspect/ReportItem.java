package com.forcemanage.inspect;

/**
 * Created by pijpe on 9/08/2017.
 */

public class ReportItem {


    private String safety_measure;
    private String observation;
    private String recommend;
    private String image1;
    private String image2;
    private String location;
    private String zone;
    private String esm;
    private String esm_cat;
    private String status;


    public ReportItem(String safety_measure, String observation, String recommend, String image1, String image2, String location, String zone, String esm, String esm_cat, String status) {

        this.safety_measure = safety_measure;
        this.observation = observation;
        this.recommend = recommend;
        this.image1 = image1;
        this.image2 = image2;
        this.location = location;
        this.zone = zone;
        this.esm = esm;
        this.esm_cat = esm_cat;
        this.status = status;
    //    this.sublocation = Integer.parseInt(sublocation);
    }

    public String getSafety_measure(){
        return safety_measure;
    }
    public String getLocation(){
        return location;
    }
    public String getObserv() {
        return observation;
    }
    public String getRecommend() {
        return recommend;
    }
    public String getEsm() { return esm;}
    public String getImage1() {return image1;}
    public String getImage2() {return image2;}
    public String getZone() {return zone;}
    public String getEsm_cat() {return esm_cat;}
    public String getStatus() {return status;}
 //   public int getSublocation() {return sublocation;}

}
