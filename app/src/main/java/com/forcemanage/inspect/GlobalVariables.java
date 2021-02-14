package com.forcemanage.inspect;

import com.forcemanage.inspect.attributes.MapViewData;
import com.forcemanage.inspect.attributes.MapViewNode;
import com.forcemanage.inspect.attributes.ProjectData;
import com.forcemanage.inspect.attributes.ProjectNode;

import java.util.ArrayList;

/**
 * Created by cindyoakes on 9/23/16.
 */
public class GlobalVariables
{
    public static String TRUE = "true";
    public static String FALSE = "false";


    public static ArrayList<MapViewData> dataList = new ArrayList<MapViewData>();
    public static ArrayList<MapViewNode> nodes = new ArrayList<MapViewNode>();
    public static ArrayList<MapViewNode> displayNodes = new ArrayList<MapViewNode>();
    public static ArrayList<ProjectData> projectList = new ArrayList<ProjectData>();
    public static ArrayList<ProjectNode> projectnodes = new ArrayList<ProjectNode>();
    public static ArrayList<ProjectNode> projectdisplayNodes = new ArrayList<ProjectNode>();
    public static  int doc_mode;
    public static int doc_pos;
    public static int pos;
    public static int aId;
    public static int iId;
    public static int catId;
    public static int Level;
    public static int folder_Id;
    public static String name;
    public static int User_id;
    public static int folderIndex;


    public static Boolean modified = false;






}
