package com.forcemanage.inspect;

/**
 * Created by DAP on 17/03/2020.
 */

public class SummaryAttributes {


    private int _inspectionId;
    private int _projectId;
    private String _headA;
    private String _headB;
    private String _headC;
    private String _comA;
    private String _comB;
    private String _comC;

    public SummaryAttributes() {
    }

    public SummaryAttributes(int inspectionId, int projectId, String headA, String headB, String headC, String comA,
                             String comB, String comC) {

        this._inspectionId = inspectionId;
        this._projectId = projectId;
        this._headA = headA;
        this._headB = headB;
        this._headC = headC;
        this._comA = comA;
        this._comB = comB;
        this._comC = comC;

    }


    public void setinspectionId(int inspectionId) {this._inspectionId = inspectionId; }
    public int getinspectionId() {return this._inspectionId; }
    public void setprojectId(int projectId) {this._projectId = projectId; }
    public int getprojectId() {
        return this._projectId;
    }
    public void setheadA(String headA) {this._headA = headA; }
    public String getheadA() {
        return this._headA;
    }
    public void setheadB(String headB){this._headB = headB; }
    public String getheadB() {return this._headB; }
    public void setheadC(String headC) {this._headC = headC; }
    public String getheadC() {return this._headC; }
    public void comA(String comA){this._comA = comA; }
    public String getcomA() {return this._comA; }
    public void setcomB (String comB) {this._comB = comB; }
    public String getcomB() {return this._comB; }
    public void setcomC(String notes) {this._comC = notes; }
    public String getcomC() {return this._comC; }

}