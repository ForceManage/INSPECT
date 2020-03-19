package com.forcemanage.inspect;

/**
 * Created by cindyoakes on 9/23/16.
 */

import java.util.ArrayList;

public class MapViewNode
{
    private int _nodeLevel;
    private String _isExpanded;
    private String _nodeName;
    private ArrayList<MapViewNode> _nodeChildren;

    public MapViewNode() {}

    public MapViewNode(int nodeLevel, String isExpanded, String nodeName, ArrayList<MapViewNode> nodeChildren)
    {
        this._nodeLevel = nodeLevel;
        this._isExpanded = isExpanded;
        this._nodeName = nodeName;
        this._nodeChildren = nodeChildren;
    }

    public int getNodeLevel()
    {
        return _nodeLevel;
    }

    public void setNodeLevel(int nodeLevel)
    {
        this._nodeLevel = nodeLevel;
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