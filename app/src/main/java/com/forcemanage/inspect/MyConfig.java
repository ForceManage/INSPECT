package com.forcemanage.inspect;

public class MyConfig {


    public static final String URL_GET_JOB_INFO = "https://dev.force-management.com/TAB_APP/ESM/getESMJobInfo.php?InspectorId=";
    public static final String URL_GET_ADDITIONAL_INFO = "https://dev.force-management.com/TAB_APP/ESM/getAdditionalInfo.php?InspectorId=";
    public static final String URL_GET_CATEGORY_INFO = "https://dev.force-management.com/TAB_APP/ESM/getCategoryInfo.php";
    public static final String URL_GET_OR_INFO = "https://dev.force-management.com/TAB_APP/ESM/get_OR_Info.php?cat=";
    public static final String URL_SYNC_INSPECTION_TO_SERVER = "https://dev.force-management.com/TAB_APP/ESM/syncInspectionToServer.php";
    public static final String URL_SYNC_INSPECTION_ITEMS_TO_SERVER = "https://dev.force-management.com/TAB_APP/ESM/syncInspItemsToServer.php";
    public static final String URL_SYNC_ASSET_TO_SERVER = "https://dev.force-management.com/TAB_APP/ESM/syncAssetsToServer.php";
    public static final String URL_SYNC_PROPERTY_TO_SERVER = "https://dev.force-management.com/TAB_APP/ESM/syncPropertyToServer.php";
    public static final String URL_SYNC_LOCATIONS_TO_SERVER = "https://dev.force-management.com/TAB_APP/ESM/syncLocationsToServer.php";
    //JSON Tags
    //Property informtion
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_PROJECT_ID = "ProjectId";
    public static final String TAG_ADDRESS_NO = "AddressNo";
    public static final String TAG_PROJECT_ADDRESS = "ProjectAddress";
    public static final String TAG_PROJECT_SUBURB = "ProjectSuburb";
    public static final String TAG_BUILD_PERMIT_NMBR = "BuildPermitNmbr";
    public static final String TAG_BUILD_TYPE = "BuildType";
    public static final String TAG_BUILD_CLASS = "BuildClass";
    public static final String TAG_KEY_REQUIRED = "KeyRequired";
    public static final String TAG_NMBR_LEVELS = "NoLevels";
    public static final String TAG_FLOOR_TYPE = "FloorType";
    public static final String TAG_ROOF_TYPE = "RoofType";
    public static final String TAG_WALL_TYPE = "WallType";
    public static final String TAG_PROJECT_NOTE = "ProjectNote";
    public static final String TAG_PROJECT_PHOTO = "ProjectPhoto";

    //Inspection information
    public static final String TAG_INSPECTION_ID = "InspectionId";
    public static final String TAG_INSPECTION_TYPE = "InspectionType";
    public static final String TAG_INSPECTION_STATUS = "InspectionStatus";
    public static final String TAG_INSPECTION_DATE = "InspectionDate";
    public static final String TAG_INSPECTOR = "Inspector";
    public static final String TAG_START_DATE_TIME = "StartDateTime";
    public static final String TAG_END_DATE_TIME = "EndDateTime";


    //Inspection Item
    public static final String TAG_A_ID = "aId";
    public static final String TAG_DATE = "Date";
    public static final String TAG_OVERVIEW = "Overview";
    public static final String TAG_RELEVANT_INFO = "RelevantInfo";
    public static final String TAG_SERVICE_LEVEL = "ServiceLevel";
    public static final String TAG_SERVICED_BY = "ServicedBy";
    public static final String TAG_REPORT_IMAGE = "ReportImg";
    public static final String TAG_IMAGE1 = "Img1";
    public static final String TAG_COM1 = "Com1";
    public static final String TAG_IMAGE2 = "Img2";
    public static final String TAG_COM2 = "Com2";
    public static final String TAG_IMAGE3 = "Img3";
    public static final String TAG_COM3 = "Com3";
    public static final String TAG_IMAGE4 = "Img4";
    public static final String TAG_COM4 = "Com4";
    public static final String TAG_IMAGE5 = "Img5";
    public static final String TAG_COM5 = "Com5";
    public static final String TAG_IMAGE6 = "Img6";
    public static final String TAG_COM6 = "Com6";
    public static final String TAG_IMAGE7 = "Img7";
    public static final String TAG_COM7 = "Com7";
    public static final String TAG_ITEM_STATUS = "ItemStatus";
    public static final String TAG_NOTES = "Notes";



    //Map
    public static final String TAG_CAT_ID = "CatId";
    public static final String TAG_LEVEL = "Level";
    public static final String TAG_PARENT = "Parent";
    public static final String TAG_LABEL = "Label";
    public static final String TAG_CHILD = "Child";





    //A category Observation and recommendations
    public static final String TAG_NUM = "num";
    public static final String TAG_SUBCAT = "subCat";
    public static final String TAG_TYPE = "type";
    public static final String TAG_NOTE = "note";
}
