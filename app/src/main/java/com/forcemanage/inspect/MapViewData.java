package com.forcemanage.inspect;

/**
 * Created by cindyoakes on 9/23/16.
 */
public class MapViewData
{

    private int _level;
    private String _name;
    private String _id;
    private String _parentId;

    public MapViewData() {}

    public MapViewData(int level, String name, String id, String parentId)
    {
        this._level = level;
        this._name = name;
        this._id = id;
        this._parentId = parentId;
    }

    public int getLevel()
    {
        return _level;
    }

    public void setLevel(int level)
    {
        this._level = level;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        this._name = name;
    }

    public String getID()
    {
        return _id;
    }

    public void setID(String id)
    {
        this._id = id;
    }

    public String getParentID()
    {
        return _parentId;
    }

    public void setParentID(String parentId)
    {
        this._parentId = parentId;
    }

}
