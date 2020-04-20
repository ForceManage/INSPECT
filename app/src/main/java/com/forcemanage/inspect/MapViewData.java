package com.forcemanage.inspect;

/**
 * Created by AP on 3/23/20
 */
public class MapViewData
{

    private int level;
    private int catId;
    private int branchCat;
    private String label;
    private int aId;
    private int iId;
    private int parent;
    private String image1;
    private String notes;

    public MapViewData() {}

    public MapViewData(int Level, int CatID, int branchCat, String Label, int aID, int iID, int Parent, String Image1, String Notes)
    {
        this.level= Level;
        this.catId = CatID;
        this.branchCat = branchCat;
        this.label = Label;
        this.aId = aID;
        this.iId = iID;
        this.parent = Parent;
        this.image1 = Image1;
        this.notes = Notes;

    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int Level)
    {
        this.level = Level;
    }

    public int getCatID()
    {
        return catId;
    }

    public void setCatID(int CatID)
    {
        this.catId = CatID;
    }

    public int getBranchCat()
    {
        return branchCat;
    }

    public void setbranchCat(int branchCat)
    {
        this.branchCat = branchCat;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String Label) {this.label = Label; }

    public int getaID()
    {
        return aId;
    }

    public void setaID(int aID)
    {
        this.aId = aID;
    }

    public int getiID()
    {
        return iId;
    }

    public void setiID(int iID)
    {
        this.aId = iID;
    }

    public int getParent()
    {
        return parent;
    }

    public void setParent(int Parent)
    {
        this.parent = Parent;
    }

    public String getimage1()
    {
        return image1;
    }

    public void setimage1(String Image1) {this.image1 = Image1; }

    public String getNotes()
    {
        return notes;
    }

    public void setNotes(String Notes) {this.notes = Notes; }
}
