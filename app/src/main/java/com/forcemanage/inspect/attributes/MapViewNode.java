package com.forcemanage.inspect.attributes;

/**
 * Created by cindyoakes on 9/23/16.
 */

import java.util.ArrayList;

public class MapViewNode
{
    private int _projId;
    private int _nodeLevel;
    private int _catId;
    private int _iID;
    private int _aID;
    private int _branchCat;
    private String _isExpanded;
    private String _nodeName;
    private ArrayList<MapViewNode> _nodeChildren;

    public MapViewNode() {}

    public MapViewNode(int projId, int nodeLevel, int catId, int iID, int aID, int branchCat, String isExpanded, String nodeName, ArrayList<MapViewNode> nodeChildren)
    {
        this._projId = projId;
        this._nodeLevel = nodeLevel;
        this._catId = catId;
        this._aID = aID;
        this._iID = iID;
        this._branchCat = branchCat;
        this._isExpanded = isExpanded;
        this._nodeName = nodeName;
        this._nodeChildren = nodeChildren;
    }

    public  int getprojId()
    {
        return _projId;
    }

    public void setcatId(int _catId) {this._catId = _catId; }

    public int getcatId() { return _catId;}

    public void setiprojId(int projId)
    {
        this._projId = projId;
    }

    public int getNodeLevel()
    {
        return _nodeLevel;
    }

    public void setNodeLevel(int nodeLevel)
    {
        this._nodeLevel = nodeLevel;
    }

    public int getaID()
    {
        return _aID;
    }

    public void setaID(int aID)
    {
        this._aID = aID;
    }

    public int getiID()
    {
        return _iID;
    }

    public void setiID(int iID)
    {
        this._iID = iID;
    }

    public int getbranchCat()
    {
        return _branchCat;
    }

    public void setbrancCat(int branchCat)
    {
        this._branchCat = branchCat;
    }

    public String getIsExpanded()
    {
        return _isExpanded;
    }

    public void setIsExpanded(String isExpanded)
    {
        this._isExpanded = isExpanded;
    }

    public String getNodeName()
    {
        return _nodeName;
    }

    public void setNodeName(String nodeName)
    {
        this._nodeName = nodeName;
    }

    public ArrayList<MapViewNode> getNodeChildren()
    {
        return _nodeChildren;
    }

    public void setNodeChildern(ArrayList<MapViewNode> nodeChildren)
    {
        this._nodeChildren = nodeChildren;
    }
}