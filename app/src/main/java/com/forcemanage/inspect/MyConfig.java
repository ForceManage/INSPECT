package com.forcemanage.inspect;

public class MyConfig {

    public static final String URL_GET_PROJECTS = "https://dev.force-management.com/TAB_INSPECT/Inspection/getProjects.php?InspectorId=";
    public static final String URL_GET_PROJECT_INFO = "https://dev.force-management.com/TAB_INSPECT/Inspection/getInspectionInfo.php?InspectorId=";
    public static final String URL_GET_ADDITIONAL_INFO = "https://dev.force-management.com/TAB_INSPECT/Inspection/getAdditionalInfo.php?InspectorId=";
    public static final String URL_GET_ACTION = "https://dev.force-management.com/TAB_INSPECT/Inspection/getActionItem.php?InspectorId=";
    public static final String URL_GET_CERT_INSPECTION = "https://dev.force-management.com/TAB_INSPECT/Inspection/getCertInsp.php?InspectorId=";
    public static final String URL_GET_SUMMARY = "https://dev.force-management.com/TAB_INSPECT/Inspection/getSummary.php?InspectorId=";
    public static final String URL_GET_OR_INFO = "https://dev.force-management.com/TAB_INSPECT/Inspection/get_OR_Info.php?cat=";
    public static final String URL_GET_USER_INFO = "https://dev.force-management.com/TAB_INSPECT/Inspection/user_login.php?user=";
    public static final String URL_GET_LOG = "https://dev.force-management.com/TAB_INSPECT/Inspection/get_LOG.php?InspectorId=";
    public static final String URL_CHANGE_USER_CODE = "https://dev.force-management.com/TAB_INSPECT/Inspection/user_change_code.php?user_id=";
    public static final String URL_SYNC_INSPECTION_TO_SERVER = "https://dev.force-management.com/TAB_INSPECT/Inspection/syncInspectionToServer.php";
    public static final String URL_SYNC_INSPECTION_ITEMS_TO_SERVER = "https://dev.force-management.com/TAB_INSPECT/Inspection/syncInspItemsToServer.php";
    public static final String URL_SYNC_MAP_TO_SERVER = "https://dev.force-management.com/TAB_INSPECT/Inspection/syncMAPToServer.php";
    public static final String URL_SYNC_PROJECT_TO_SERVER = "https://dev.force-management.com/TAB_INSPECT/Inspection/syncProjectToServer.php";
    public static final String URL_SYNC_ACTIONS_TO_SERVER = "https://dev.force-management.com/TAB_INSPECT/Inspection/syncActionsToServer.php";
    public static final String URL_SYNC_CERT_INSPECTION_TO_SERVER = "https://dev.force-management.com/TAB_INSPECT/Inspection/syncCertInspToServer.php";
    public static final String URL_SYNC_SUMMARY_TO_SERVER = "https://dev.force-management.com/TAB_INSPECT/Inspection/syncSummaryToServer.php";
    public static final String URL_SYNC_LOG_TO_SERVER = "https://dev.force-management.com/TAB_INSPECT/Inspection/syncLOGToServer.php";
    public static final String URL_SYNC_DELETED_TO_SERVER = "https://dev.force-management.com/TAB_INSPECT/Inspection/syncDeletedToServer.php";

    public static final String URL_REQUEST_ACTIVITY= "https://dev.force-management.com/TAB_INSPECT/Inspection/requestActivity.php?projId=";
    public static final String URL_REQUEST_PROJECT= "https://dev.force-management.com/TAB_INSPECT/Inspection/requestProject.php?projId=";
    public static final String URL_EMAIL_REPORT= "https://dev.force-management.com/TAB_INSPECT/Inspection/";

    //JSON Tags
    //Property informtion
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_PROJECT_ID = "ProjectID";
    public static final String TAG_ADDRESS_NO = "AddressNo";
    public static final String TAG_PROJECT_ADDRESS = "ProjectAddress";
    public static final String TAG_PROJECT_SUBURB = "ProjectSuburb";
    public static final String TAG_INFO_B = "InfoB";
    public static final String TAG_INFO_A = "InfoA";
    public static final String TAG_INFO_C = "InfoC";
    public static final String TAG_INFO_E = "InfoE";
    public static final String TAG_INFO_D = "InfoD";
    public static final String TAG_INFO_F = "InfoF";
    public static final String TAG_INFO_G = "InfoG";
    public static final String TAG_INFO_H = "InfoH";
    public static final String TAG_INFO_I = "InfoI";
    public static final String TAG_INFO_J = "InfoJ";
    public static final String TAG_PROJECT_NOTE = "ProjectNote";
    public static final String TAG_PROJECT_PHOTO = "ProjectPhoto";

    //Inspection information
    public static final String TAG_INSPECTION_ID = "InspectionID";
    public static final String TAG_INSPECTION_TYPE = "InspectionType";
    public static final String TAG_INSPECTION_STATUS = "InspectionStatus";
    public static final String TAG_INSPECTION_DATE = "InspectionDate";
    public static final String TAG_INSPECTOR = "Inspector";
    public static final String TAG_START_DATE_TIME = "StartDateTime";
    public static final String TAG_END_DATE_TIME = "EndDateTime";


    //Inspection Item
    public static final String TAG_IMAGE = "Image";
    public static final String TAG_P_ID = "pID";
    public static final String TAG_A_ID = "aID";
    public static final String TAG_DATE_INSPECTED = "dateInspected";
    public static final String TAG_OVERVIEW = "Overview";
    public static final String TAG_RELEVANT_INFO = "RelevantInfo";
    public static final String TAG_SERVICE_LEVEL = "ServiceLevel";
    public static final String TAG_SERVICED_BY = "ServicedBy";
    public static final String TAG_REPORT_IMAGE = "ReportImg";
    public static final String TAG_IMAGE1 = "Img1";
    public static final String TAG_COM1 = "com1";
    public static final String TAG_IMAGE2 = "Img2";
    public static final String TAG_COM2 = "com2";
    public static final String TAG_IMAGE3 = "Img3";
    public static final String TAG_COM3 = "com3";
    public static final String TAG_IMAGE4 = "Img4";
    public static final String TAG_COM4 = "com4";
    public static final String TAG_IMAGE5 = "Img5";
    public static final String TAG_COM5 = "com5";
    public static final String TAG_IMAGE6 = "Img6";
    public static final String TAG_COM6 = "com6";
    public static final String TAG_IMAGE7 = "Img7";
    public static final String TAG_COM7 = "com7";
    public static final String TAG_ITEM_STATUS = "ItemStatus";
    public static final String TAG_NOTES = "Notes";




    //Map
    public static final String TAG_CAT_ID = "CatID";
    public static final String TAG_LEVEL = "Level";
    public static final String TAG_PARENT = "Parent";
    public static final String TAG_LABEL = "Label";
    public static final String TAG_CHILD = "Child";

    //Certificate Inspection
    public static final String TAG_DATE_TIME = "DateTime";
    public static final String TAG_PERMIT = "Permit";
    public static final String TAG_STAGE = "Stage";

    //Summary
    public static final String TAG_HEAD_A = "headA";
    public static final String TAG_HEAD_B = "headB";
    public static final String TAG_HEAD_C = "headC";
    public static final String TAG_COM_A = "comA";
    public static final String TAG_COM_B = "comB";
    public static final String TAG_COM_C = "comC";

    //A category Observation and recommendations
    public static final String TAG_NUM = "num";
    public static final String TAG_SUBCAT = "subCat";
    public static final String TAG_TYPE = "type";
    public static final String TAG_NOTE = "Note";
    public static final String TAG_NOTE_2 = "Note_2";

    //Users
    public static final String TAG_USER_ID = "userID";
    public static final String TAG_USER_NAME = "userName";
    public static final String TAG_USER_CODE = "userCode";
    public static final String TAG_CLIENT_NAME = "clientName";

    //TableName
    public static final String TAG_TABLE_NAME = "TableName";
}
