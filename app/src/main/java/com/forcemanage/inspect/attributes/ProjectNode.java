package com.forcemanage.inspect.attributes;

/**
 * Created by AP on 10/23/20.
 */

import java.util.ArrayList;

public class ProjectNode
{
    private int _projId;
    private int _nodeLevel;
    private int _iID;
    private int _aID;
    private int _branchCat;
    private String _isExpanded;
    private String _nodeName;
    private ArrayList<ProjectNode> _nodeChildren;

    public ProjectNode() {}

    public ProjectNode(int projId, int nodeLevel, int iID, int aID, int branchCat, String isExpanded, String nodeName, ArrayList<ProjectNode> nodeChildren)
    {
        this._projId = projId;
        this._nodeLevel = nodeLevel;
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

    public ArrayList<ProjectNode> getNodeChildren()
    {
        return _nodeChildren;
    }

    public void setNodeChildern(ArrayList<ProjectNode> nodeChildren)
    {
        this._nodeChildren = nodeChildren;
    }
}
