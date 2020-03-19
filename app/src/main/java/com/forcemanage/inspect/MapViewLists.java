package com.forcemanage.inspect;


import android.util.Log;

import java.util.ArrayList;


/**
 * Created by cindyoakes on 9/23/16.
 */

public class MapViewLists
{




    public static ArrayList<MapViewData> LoadInitialData() {


        ArrayList<MapViewData> data = new ArrayList<MapViewData>();



/*
        data.add(new TreeViewData(0, "cindy's family tree", "1", "-1"));
        data.add(new TreeViewData(0, "jack's family tree", "2", "-1"));
        data.add(new TreeViewData(1, "katherine", "3",  "1"));
        data.add(new TreeViewData(1, "kyle", "4", "1"));
        data.add(new TreeViewData(2, "hayley","5", "3"));
        data.add(new TreeViewData(2, "macey", "6", "3"));
        data.add(new TreeViewData(1, "katelyn", "7", "2"));
        data.add(new TreeViewData(1, "jared", "8", "2"));
        data.add(new TreeViewData(1, "denyee", "9", "2"));
        data.add(new TreeViewData(2, "cayleb", "10", "4"));
        data.add(new TreeViewData(2, "carter", "11", "4"));
        data.add(new TreeViewData(2, "braylon", "12", "4"));
        data.add(new TreeViewData(3, "samson", "13", "5"));
        data.add(new TreeViewData(3, "samson", "14", "6"));


        return data;

*/


        for (int i = 0; i < InspectionActivity.level.length; i++) {

            data.add(new MapViewData(InspectionActivity.level[i], InspectionActivity.locationdesc[i], InspectionActivity.id[i], InspectionActivity.parent[i]));
        }

     //   TreeViewLists.LoadDisplayList();
        return data;

        // return GlobalVariables.dataList;


      }






    public static ArrayList<MapViewNode> LoadInitialNodes(ArrayList<MapViewData> dataList)
    {
        ArrayList<MapViewNode> nodes = new ArrayList<MapViewNode>();

        for(int i = 0; i < dataList.size(); i++)
        {
            MapViewData data = dataList.get(i);
            if (data.getLevel() != 0) continue;

            Log.v("LoadInitialNodes", data.getName());

            MapViewNode node = new MapViewNode();
            node.setNodeLevel(data.getLevel());
            node.setIsExpanded(GlobalVariables.FALSE);
            node.setNodeName(data.getName());
            int newLevel = data.getLevel() + 1;
            node.setNodeChildern(null);
            ArrayList<MapViewNode> children = LoadChildrenNodes(dataList, newLevel, data.getID());
            //node.setNodeChildern(LoadChildrenNodes(dataList, newLevel, data.getID()));
            //if (node.getNodeChildren().size() == 0)
            if (children.size() == 0)
            {
                node.setNodeChildern(null);
            }
            else
            {
                node.setNodeChildern(children);
            }

            nodes.add(node);

        }


        return nodes;
    }

    private static ArrayList<MapViewNode> LoadChildrenNodes(ArrayList<MapViewData> dataList, int level, String parentID)
    {
        ArrayList<MapViewNode> nodes = new ArrayList<MapViewNode>();

        for(int i = 0; i < dataList.size(); i++)
        {
            MapViewData data = dataList.get(i);
            if ((data.getLevel() != level) || (data.getParentID() != parentID)) continue;



            MapViewNode node = new MapViewNode();
            node.setNodeLevel(data.getLevel());
            node.setNodeName(data.getName());
            node.setIsExpanded(GlobalVariables.FALSE);
            int newLevel = level + 1;
            node.setNodeChildern(null);
            ArrayList<MapViewNode> children = LoadChildrenNodes(dataList, newLevel, data.getID());
           // node.setNodeChildern(LoadChildrenNodes(dataList, newLevel, data.getID()));
            if (children.size() == 0)
            {
                node.setNodeChildern(null);
            }
            else
            {
                node.setNodeChildern(children);
            }

            nodes.add(node);

            Log.v("LoadChildrenNodes", String.format("%s %d",data.getName(), children.size()));
        }


        return nodes;
    }


    public static void LoadDisplayList()
    {
        GlobalVariables.displayNodes = new ArrayList<MapViewNode>();
        for(int i = 0; i < GlobalVariables.nodes.size(); i++)
        {
            MapViewNode node = GlobalVariables.nodes.get(i);
            GlobalVariables.displayNodes.add(node);

            if (node.getIsExpanded() == GlobalVariables.TRUE)
            {
                ArrayList<MapViewNode> children = node.getNodeChildren();
                if (children != null)
                    if (children.size() != 0)
                        AddChildrenToList(children);
            }
        }
    }

    public static void AddChildrenToList(ArrayList<MapViewNode> children)
    {
        if (children == null) return;

        for(int i = 0; i < children.size(); i++)
        {
            MapViewNode node = children.get(i);
            GlobalVariables.displayNodes.add(node);

            Log.v("addchildrentolist", String.format("%d %s %d", i, node.getNodeName(), children.size()));

            if (node.getIsExpanded() == GlobalVariables.TRUE)
            {
                ArrayList<MapViewNode> grandChildren = node.getNodeChildren();
                if (grandChildren != null)
                    AddChildrenToList(grandChildren);
            }
        }
    }










}
