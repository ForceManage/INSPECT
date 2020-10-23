package com.forcemanage.inspect;


import android.util.Log;

import com.forcemanage.inspect.attributes.MapViewData;
import com.forcemanage.inspect.attributes.MapViewNode;
import com.forcemanage.inspect.attributes.ProjectData;
import com.forcemanage.inspect.attributes.ProjectNode;

import java.util.ArrayList;


/**
 * Created by cindyoakes on 9/23/16.
 */

public class ProjectViewList
{


    public static ArrayList<ProjectData> LoadInitialData() {


        ArrayList<ProjectData> data = new ArrayList<ProjectData>();



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


        //   MapViewLists.LoadDisplayList();
        return GlobalVariables.projectList;

        // return GlobalVariables.dataList;

    }


    public static ArrayList<ProjectNode> LoadInitialNodes(ArrayList<ProjectData> dataList)
    {
        ArrayList<ProjectNode> nodes = new ArrayList<ProjectNode>();

        for(int i = 0; i < dataList.size(); i++)
        {
            ProjectData data = dataList.get(i);
            if (data.getLevel() != 0) continue;

            Log.v("LoadInitialNodes", data.getLabel());

            ProjectNode node = new ProjectNode();
            node.setiprojId(data.getProjId());
            node.setNodeLevel(data.getLevel());
            node.setIsExpanded(GlobalVariables.FALSE);
            node.setNodeName(data.getLabel());
            node.setbrancCat(data.getBranchCat());
            node.setaID(data.getaID());
            node.setiID(data.getiID());
            int newLevel = data.getLevel() + 1;
            node.setNodeChildern(null);
            ArrayList<ProjectNode> children = LoadChildrenNodes(dataList, newLevel, data.getaID());
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

    private static ArrayList<ProjectNode> LoadChildrenNodes(ArrayList<ProjectData> dataList, int level, int parentID)
    {
        ArrayList<ProjectNode> nodes = new ArrayList<ProjectNode>();

        for(int i = 0; i < dataList.size(); i++)
        {
            ProjectData data = dataList.get(i);
            if ((data.getLevel() != level) || (data.getParent() != parentID)) continue;



            ProjectNode node = new ProjectNode();
            node.setiprojId(data.getProjId());
            node.setNodeLevel(data.getLevel());
            node.setNodeName(data.getLabel());
            node.setaID(data.getaID());
            node.setiID(data.getiID());
            node.setbrancCat(data.getBranchCat());
            node.setIsExpanded(GlobalVariables.FALSE);
            int newLevel = level + 1;
            node.setNodeChildern(null);
            ArrayList<ProjectNode> children = LoadChildrenNodes(dataList, newLevel, data.getaID());
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

            Log.v("LoadChildrenNodes", String.format("%s %d",data.getLabel(), children.size()));
        }


        return nodes;
    }


    public static void LoadDisplayList()
    {
        GlobalVariables.projectdisplayNodes = new ArrayList<ProjectNode>();
        for(int i = 0; i < GlobalVariables.projectnodes.size(); i++)
        {
            ProjectNode node = GlobalVariables.projectnodes.get(i);
            GlobalVariables.projectdisplayNodes.add(node);

            if (node.getIsExpanded() == GlobalVariables.TRUE)
            {
                ArrayList<ProjectNode> children = node.getNodeChildren();
                if (children != null)
                    if (children.size() != 0)
                        AddChildrenToList(children);
            }
        }
    }

    public static void AddChildrenToList(ArrayList<ProjectNode> children)
    {
        if (children == null) return;

        for(int i = 0; i < children.size(); i++)
        {
            ProjectNode node = children.get(i);
            GlobalVariables.projectdisplayNodes.add(node);

            Log.v("addchildrentolist", String.format("%d %s %d", i, node.getNodeName(), children.size()));

            if (node.getIsExpanded() == GlobalVariables.TRUE)
            {
                ArrayList<ProjectNode> grandChildren = node.getNodeChildren();
                if (grandChildren != null)
                    AddChildrenToList(grandChildren);
            }
        }
    }


}