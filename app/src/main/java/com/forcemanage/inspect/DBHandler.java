package com.forcemanage.inspect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.forcemanage.inspect.attributes.A_Attributes;
import com.forcemanage.inspect.attributes.ActionItemAttributes;
import com.forcemanage.inspect.attributes.CertificateInspectionAttributes;
import com.forcemanage.inspect.attributes.InspectionAttributes;
import com.forcemanage.inspect.attributes.InspectionItemAttributes;
import com.forcemanage.inspect.attributes.LOG_Attributes;
import com.forcemanage.inspect.attributes.MAPattributes;
import com.forcemanage.inspect.attributes.ProjectAttributes;
import com.forcemanage.inspect.attributes.SummaryAttributes;
import com.forcemanage.inspect.attributes.USER_Attributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by DAP on 12/03/2020.
 */

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 26;
    private static final String DATABASE_NAME = "Inspection.db";

    public static final String TABLE_PROJECT_INFO = "project_info";

    public static final String COLUMN_PROJECT_ID = "ProjectId";
    public static final String COLUMN_CLIENT_ID = "ClientId";
    public static final String COLUMN_INFO_A = "InfoA";
    public static final String COLUMN_INFO_B = "InfoB";
    public static final String COLUMN_INFO_C = "InfoC";
    public static final String COLUMN_INFO_D = "InfoD";
    public static final String COLUMN_PROJECT_NOTE = "ProjectNote";
    public static final String COLUMN_ADDRESS_NUMBER = "AddressNumber";
    public static final String COLUMN_PROJECT_ADDRESS = "ProjectAddress";
    public static final String COLUMN_PROJECT_SUBURB = "ProjectSuburb";
    public static final String COLUMN_PROJECT_PHOTO = "ProjectPhoto";
    public static final String COLUMN_INFO_E = "InfoE";
    public static final String COLUMN_INFO_F = "InfoF";
    public static final String COLUMN_INFO_G = "InfoG";
    public static final String COLUMN_INFO_H = "InfoH";
    public static final String COLUMN_INFO_I = "InfoI";
    public static final String COLUMN_INFO_J = "InfoJ";

    public static final String TABLE_INSPECTION = "Inspection";

    public static final String COLUMN_INSPECTION_ID = "InspectionId";
    public static final String COLUMN_INSPECTION_DATE = "InspectionDate";
    public static final String COLUMN_INSPECTOR = "Inspector";
    public static final String COLUMN_INSPECTION_STATUS = "InspectionStatus";
    public static final String COLUMN_INSPECTION_TYPE = "InspectionType";
    public static final String COLUMN_P_ID = "pID";
    public static final String COLUMN_IMAGE = "Image";

    public static final String TABLE_INSPECTION_ITEM = "InspectionItem";
    public static final String TABLE_ACTION_ITEM = "ActionItem";
    public static final String TABLE_CERTIFICATE_INSPECTION = "CertificateInsp";
    public static final String TABLE_SUMMARY = "Summary";

    public static final String COLUMN_DATE_TIME_START = "DateTimeStart";
    public static final String COLUMN_DATE_TIME_FINISH = "DateTimeFinish";
    public static final String COLUMN_DATE_INSPECTED = "DateInspected";
    public static final String COLUMN_DATE_TIME = "DateTime";
    public static final String COLUMN_OVERVIEW = "Overview";
    public static final String COLUMN_RELEVANT_INFO = "RelevantInfo";
    public static final String COLUMN_SERVICE_LEVEL = "ServiceLevel";
    public static final String COLUMN_SERVICED_BY = "ServicedBy";
    public static final String COLUMN_REPORT_IMAGE = "ReportImage";
    public static final String COLUMN_IMG1 = "Image1";
    public static final String COLUMN_IMG2 = "Image2";
    public static final String COLUMN_IMG3 = "Image3";
    public static final String COLUMN_IMG4 = "Image4";
    public static final String COLUMN_IMG5 = "Image5";
    public static final String COLUMN_IMG6 = "Image6";
    public static final String COLUMN_IMG7 = "Image7";
    public static final String COLUMN_COM1 = "Com1";
    public static final String COLUMN_COM2 = "Com2";
    public static final String COLUMN_COM3 = "Com3";
    public static final String COLUMN_COM4 = "Com4";
    public static final String COLUMN_COM5 = "Com5";
    public static final String COLUMN_COM6 = "Com6";
    public static final String COLUMN_COM7 = "Com7";
    public static final String COLUMN_ITEM_STATUS = "ItemStatus";
    public static final String COLUMN_NOTES = "Notes";

    public static final String COLUMN_COM_A = "comA";
    public static final String COLUMN_COM_B = "comB";
    public static final String COLUMN_COM_C = "comC";

    public static final String COLUMN_HEAD_A = "headA";
    public static final String COLUMN_HEAD_B = "headB";
    public static final String COLUMN_HEAD_C = "headC";

    public static final String TABLE_MAP = "Map";

    public static final String COLUMN_CAT_ID = "CatId";
    public static final String COLUMN_LEVEL = "Level";
    public static final String COLUMN_PARENT = "Parent";
    public static final String COLUMN_LABEL = "Label";
    public static final String COLUMN_CHILD = "Child";
    public static final String COLUMN_A_ID = "aID";

    public static final String COLUMN_PERMIT_NO = "Permit";
    public static final String COLUMN_STAGE = "Stage";


    public static final String TABLE_A_OR = "A_OR";
    public static final String TABLE_B_OR = "B_OR";
    public static final String TABLE_C_OR = "C_OR";
    public static final String TABLE_D_OR = "D_OR";
    public static final String TABLE_USER_LIST = "UserList";
    public static final String TABLE_LOG_TIME = "LogTime";

    public static final String COLUMN_NUM = "Num";
    public static final String COLUMN_SUB_CAT = "Subcat";
    public static final String COLUMN_TYPE = "Type";
    public static final String COLUMN_NOTE = "Note";
    public static final String COLUMN_NOTE_2 = "Note_2";

    public static final String COLUMN_U_ID = "UserID";
    public static final String COLUMN_U_NAME = "UserName";
    public static final String COLUMN_U_CODE = "UserCode";
    public static final String COLUMN_CLIENT_NAME = "ClientName";

    public static final String TABLE_NAME = "TableDel";
    public static final String COLUMN_TABLE_NAME = "TableName";


    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // get Writable database for testing purposes only, remove when ready

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_PROJECT_INFO_TABLE = "CREATE TABLE " +
                TABLE_PROJECT_INFO + "("
                + COLUMN_PROJECT_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_CLIENT_ID + " INTEGER,"
                + COLUMN_INFO_A + " TEXT,"
                + COLUMN_INFO_B + " TEXT,"
                + COLUMN_INFO_C + " TEXT,"
                + COLUMN_PROJECT_NOTE + " TEXT,"
                + COLUMN_ADDRESS_NUMBER + " TEXT,"
                + COLUMN_PROJECT_ADDRESS + " TEXT,"
                + COLUMN_PROJECT_SUBURB + " TEXT,"
                + COLUMN_INFO_D + " TEXT,"
                + COLUMN_INFO_E + " TEXT,"
                + COLUMN_INFO_F+ " TEXT,"
                + COLUMN_INFO_G + " TEXT,"
                + COLUMN_INFO_H + " TEXT,"
                + COLUMN_INFO_I + " TEXT,"
                + COLUMN_INFO_J + " TEXT,"

                + COLUMN_PROJECT_PHOTO + " TEXT" + ")";
        db.execSQL(CREATE_PROJECT_INFO_TABLE);

        String CREATE_MAP_TABLE = "CREATE TABLE " +
                TABLE_MAP + "("
                + COLUMN_PROJECT_ID + " INTEGER,"
                + COLUMN_CAT_ID + " INTEGER,"
                + COLUMN_LEVEL + " INTEGER,"
                + COLUMN_PARENT + " INTEGER,"
                + COLUMN_LABEL + " TEXT,"
                + COLUMN_CHILD + " INTEGER,"
                + COLUMN_A_ID + " INTEGER,"
                + COLUMN_INSPECTION_ID + " INTEGER,"
                + COLUMN_IMG1 + " TEXT,"
                + COLUMN_NOTES + " TEXT,"
                + COLUMN_ITEM_STATUS + " TEXT,"
                + "PRIMARY KEY " + "(" + COLUMN_PROJECT_ID + "," + COLUMN_A_ID + "))";
        db.execSQL(CREATE_MAP_TABLE);

        String CREATE_INSPECTION_TABLE = "CREATE TABLE " +
                TABLE_INSPECTION + "("
                + COLUMN_INSPECTION_ID + " INTEGER,"
                + COLUMN_INSPECTION_TYPE + " TEXT,"
                + COLUMN_INSPECTION_STATUS + " TEXT,"
                + COLUMN_PROJECT_ID + " INTEGER,"
                + COLUMN_INSPECTION_DATE + " TEXT,"
                + COLUMN_INSPECTOR + " TEXT,"
                + COLUMN_DATE_TIME_START + " INTEGER,"
                + COLUMN_DATE_TIME_FINISH + " INTEGER,"
                + COLUMN_LABEL + " TEXT,"
                + COLUMN_LEVEL + " INTEGER,"
                + COLUMN_PARENT + " INTEGER,"
                + COLUMN_P_ID + " INTEGER,"
                + COLUMN_IMAGE + " TEXT,"
                + COLUMN_NOTE + " TEXT,"
                + COLUMN_NOTE_2 + " TEXT,"
                + "PRIMARY KEY " + "(" + COLUMN_INSPECTION_ID + "," + COLUMN_P_ID + "))";

        db.execSQL(CREATE_INSPECTION_TABLE);


        String CREATE_INSPECTION_ITEM_TABLE = "CREATE TABLE " +
                TABLE_INSPECTION_ITEM + "("
                + COLUMN_INSPECTION_ID + " INTEGER,"
                + COLUMN_PROJECT_ID + " INTEGER,"
                + COLUMN_A_ID + " INTEGER,"
                + COLUMN_DATE_INSPECTED + " TEXT,"
                + COLUMN_OVERVIEW + " TEXT,"
                + COLUMN_RELEVANT_INFO + " TEXT,"
                + COLUMN_SERVICED_BY + " TEXT,"
                + COLUMN_SERVICE_LEVEL + " TEXT,"
                + COLUMN_REPORT_IMAGE + " TEXT,"
                + COLUMN_IMG1 + " TEXT,"
                + COLUMN_COM1 + " TEXT,"
                + COLUMN_IMG2 + " TEXT,"
                + COLUMN_COM2 + " TEXT,"
                + COLUMN_IMG3 + " TEXT,"
                + COLUMN_COM3 + " TEXT,"
                + COLUMN_IMG4 + " TEXT,"
                + COLUMN_COM4 + " TEXT,"
                + COLUMN_IMG5 + " TEXT,"
                + COLUMN_COM5 + " TEXT,"
                + COLUMN_IMG6 + " TEXT,"
                + COLUMN_COM6 + " TEXT,"
                + COLUMN_IMG7 + " TEXT,"
                + COLUMN_COM7 + " TEXT,"
                + COLUMN_ITEM_STATUS + " TEXT,"
                + COLUMN_NOTES + " TEXT,"
                + "PRIMARY KEY " + "(" + COLUMN_INSPECTION_ID + "," + COLUMN_PROJECT_ID + "," + COLUMN_A_ID + "))";
        //If ESM recommendation  is not nil print photo on report
        db.execSQL(CREATE_INSPECTION_ITEM_TABLE);


        String CREATE_ACTION_ITEM_TABLE = "CREATE TABLE " +
                TABLE_ACTION_ITEM + "("
                + COLUMN_INSPECTION_ID + " INTEGER,"
                + COLUMN_PROJECT_ID + " INTEGER,"
                + COLUMN_A_ID + " INTEGER,"
                + COLUMN_DATE_INSPECTED + " TEXT,"
                + COLUMN_OVERVIEW + " TEXT,"
                + COLUMN_RELEVANT_INFO + " TEXT,"
                + COLUMN_SERVICE_LEVEL + " TEXT,"
                + COLUMN_SERVICED_BY + " TEXT,"
                + COLUMN_REPORT_IMAGE + " TEXT,"
                + COLUMN_IMG1 + " TEXT,"
                + COLUMN_COM1 + " TEXT,"
                + COLUMN_ITEM_STATUS + " TEXT,"
                + COLUMN_NOTES + " TEXT,"
                + "PRIMARY KEY " + "(" + COLUMN_INSPECTION_ID + "," + COLUMN_PROJECT_ID + "," + COLUMN_A_ID + "))";
        //If ESM recommendation  is not nil print photo on report
        db.execSQL(CREATE_ACTION_ITEM_TABLE);


        String CREATE_CERTIFICATE_INSPECTION_TABLE = "CREATE TABLE " +
                TABLE_CERTIFICATE_INSPECTION + "("
                + COLUMN_INSPECTION_ID + " INTEGER,"
                + COLUMN_PROJECT_ID + " INTEGER,"
                + COLUMN_DATE_TIME + " INTEGER,"
                + COLUMN_OVERVIEW + " TEXT,"
                + COLUMN_PERMIT_NO + " TEXT,"
                + COLUMN_PROJECT_ADDRESS + " TEXT,"
                + COLUMN_STAGE + " TEXT,"
                + COLUMN_NOTES + " TEXT,"
                + COLUMN_ITEM_STATUS + " TEXT,"
                + "PRIMARY KEY " + "(" + COLUMN_INSPECTION_ID + "," + COLUMN_PROJECT_ID + "))";
        //If ESM recommendation  is not nil print photo on report
        db.execSQL(CREATE_CERTIFICATE_INSPECTION_TABLE);

        String CREATE_SUMMARY_TABLE = "CREATE TABLE " +
                TABLE_SUMMARY + "("
                + COLUMN_INSPECTION_ID + " INTEGER,"
                + COLUMN_PROJECT_ID + " INTEGER,"
                + COLUMN_HEAD_A + " TEXT,"
                + COLUMN_COM_A + " TEXT,"
                + COLUMN_HEAD_B + " TEXT,"
                + COLUMN_COM_B + " TEXT,"
                + COLUMN_HEAD_C + " TEXT,"
                + COLUMN_COM_C + " TEXT,"
                + COLUMN_ITEM_STATUS + " TEXT,"
                + "PRIMARY KEY " + "(" + COLUMN_INSPECTION_ID + "," + COLUMN_PROJECT_ID + "))";
        //If ESM recommendation  is not nil print photo on report
        db.execSQL(CREATE_SUMMARY_TABLE);

        String CREATE_TABLE_NAME_TABLE = "CREATE TABLE " +
                TABLE_NAME + "("
                + COLUMN_TABLE_NAME + " STRING,"
                + COLUMN_PROJECT_ID + " INTEGER,"
                + COLUMN_INSPECTION_ID + " INTEGER,"
                + COLUMN_A_ID + " INTEGER)";


        db.execSQL(CREATE_TABLE_NAME_TABLE);

        String CREATE_A_OR_TABLE = "CREATE TABLE " +
                TABLE_A_OR + "("
                + COLUMN_NUM + " INTEGER PRIMARY KEY,"
                + COLUMN_SUB_CAT + " TEXT,"
                + COLUMN_TYPE + " INTEGER,"
                + COLUMN_NOTE + " TEXT )";

        db.execSQL(CREATE_A_OR_TABLE);

        String CREATE_B_OR_TABLE = "CREATE TABLE " +
                TABLE_B_OR + "("
                + COLUMN_NUM + " INTEGER PRIMARY KEY,"
                + COLUMN_SUB_CAT + " TEXT,"
                + COLUMN_TYPE + " INTEGER,"
                + COLUMN_NOTE + " TEXT )";

        db.execSQL(CREATE_B_OR_TABLE);

        String CREATE_C_OR_TABLE = "CREATE TABLE " +
                TABLE_C_OR + "("
                + COLUMN_NUM + " INTEGER PRIMARY KEY ,"
                + COLUMN_SUB_CAT + " TEXT,"
                + COLUMN_TYPE + " INTEGER,"
                + COLUMN_NOTE + " TEXT )";

        db.execSQL(CREATE_C_OR_TABLE);

        String CREATE_D_OR_TABLE = "CREATE TABLE " +
                TABLE_D_OR + "("
                + COLUMN_NUM + " INTEGER PRIMARY KEY,"
                + COLUMN_SUB_CAT + " TEXT,"
                + COLUMN_TYPE + " INTEGER,"
                + COLUMN_NOTE + " TEXT )";

        db.execSQL(CREATE_D_OR_TABLE);

        String CREATE_USER_LIST_TABLE = "CREATE TABLE " +
                TABLE_USER_LIST + "("
                + COLUMN_U_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_U_NAME + " TEXT,"
                + COLUMN_U_CODE + " TEXT,"
                + COLUMN_CLIENT_NAME + " TEXT )";

        db.execSQL(CREATE_USER_LIST_TABLE);

        String CREATE_LOG_TIME_TABLE = "CREATE TABLE " +
                TABLE_LOG_TIME + "("
                + COLUMN_U_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_PROJECT_ID + " INTEGER,"
                + COLUMN_INSPECTION_ID + " INTEGER,"
                + COLUMN_DATE_TIME_START + " INTEGER,"
                + COLUMN_DATE_TIME_FINISH + " INTEGER)";

        db.execSQL(CREATE_LOG_TIME_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECT_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSPECTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSPECTION_ITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTION_ITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CERTIFICATE_INSPECTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUMMARY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_A_OR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_B_OR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_C_OR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_D_OR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_LIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOG_TIME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void clearAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROJECT_INFO, null, null);
        db.delete(TABLE_INSPECTION, null, null);
        db.delete(TABLE_INSPECTION_ITEM, null, null);
        db.delete(TABLE_ACTION_ITEM, null, null);
        db.delete(TABLE_CERTIFICATE_INSPECTION, null, null);
        db.delete(TABLE_SUMMARY, null, null);
        db.delete(TABLE_MAP, null, null);
        db.delete(TABLE_A_OR, null, null);
        db.delete(TABLE_B_OR, null, null);
        db.delete(TABLE_C_OR, null, null);
        db.delete(TABLE_D_OR, null, null);
        db.delete(TABLE_USER_LIST, null, null);
        db.delete(TABLE_LOG_TIME, null, null);
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
    // public boolean insertLocation(int propertyId, int locationId, int subLocationId,  String locationDescription){
    //     SQLiteDatabase db = this.getWritableDatabase();
    //




    public void updateProjectsFromServer(ProjectAttributes projectAttributes, InspectionAttributes inspectionAttributes) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_PROJECT_ID, projectAttributes.getProjectId());
        values.put(COLUMN_ADDRESS_NUMBER, projectAttributes.getAddressNumber());
        values.put(COLUMN_PROJECT_ADDRESS, projectAttributes.getprojectAddress());
        values.put(COLUMN_PROJECT_SUBURB, projectAttributes.getprojectSuburb());
        values.put(COLUMN_INFO_A, projectAttributes.getinfoA());
        values.put(COLUMN_INFO_B, projectAttributes.getinfoB());
        values.put(COLUMN_INFO_C, projectAttributes.getinfoC());
        values.put(COLUMN_INFO_D, projectAttributes.getinfoD());
        values.put(COLUMN_PROJECT_PHOTO, projectAttributes.getprojectPhoto());
        values.put(COLUMN_INFO_E, projectAttributes.getinfoE());
        //       values.put(COLUMN_OC_DATE, projectAttributes.getocDate());
        //       values.put(COLUMN_OC_NUMBER, projectAttributes.getocNmbr());
        values.put(COLUMN_INFO_F, projectAttributes.getinfoF());
        values.put(COLUMN_INFO_G, projectAttributes.getinfoG());
        values.put(COLUMN_INFO_H, projectAttributes.getinfoH());
        values.put(COLUMN_INFO_I, projectAttributes.getinfoI());
        values.put(COLUMN_INFO_J, projectAttributes.getinfoJ());
        values.put(COLUMN_PROJECT_NOTE, projectAttributes.getprojectNote());
        ;

        SQLiteDatabase db = this.getWritableDatabase();
        //replace will delete the row if the propertyID already exists
        // db.execSQL("delete from "+ TABLE_PROPERTY_INFO);

        db.replace(TABLE_PROJECT_INFO, null, values);


        // Add data into the ESM Inspection table - new
        ContentValues valuesInspect = new ContentValues();
        valuesInspect.put(COLUMN_INSPECTION_ID, inspectionAttributes.getinspectionId());
        valuesInspect.put(COLUMN_INSPECTION_TYPE, inspectionAttributes.getinspectionType());
        valuesInspect.put(COLUMN_INSPECTION_STATUS, inspectionAttributes.getinspectionStatus());
        valuesInspect.put(COLUMN_PROJECT_ID, inspectionAttributes.getprojectId());
        valuesInspect.put(COLUMN_INSPECTION_DATE, inspectionAttributes.getinspectionDate());
        valuesInspect.put(COLUMN_INSPECTOR, inspectionAttributes.getinspector());
        valuesInspect.put(COLUMN_DATE_TIME_START, inspectionAttributes.getstartDateTime());
        valuesInspect.put(COLUMN_DATE_TIME_FINISH, inspectionAttributes.getendDateTime());
        valuesInspect.put(COLUMN_LABEL, inspectionAttributes.getlabel());
        valuesInspect.put(COLUMN_LEVEL, inspectionAttributes.getlevel());
        valuesInspect.put(COLUMN_PARENT, inspectionAttributes.getparent());
        valuesInspect.put(COLUMN_P_ID, inspectionAttributes.getpID());
        valuesInspect.put(COLUMN_IMAGE, inspectionAttributes.getimage());
        valuesInspect.put(COLUMN_NOTE, inspectionAttributes.getnote());
        valuesInspect.put(COLUMN_NOTE_2, inspectionAttributes.getnote2());

        db.replace(TABLE_INSPECTION, null, valuesInspect);

        db.close();

    }


    //Store values from MySQL on server to local SQLite
    public void updateFromServer(InspectionItemAttributes inspectionItemAttributes) {
        ContentValues values = new ContentValues();




        ContentValues valuesItem = new ContentValues();
        valuesItem.put(COLUMN_INSPECTION_ID, inspectionItemAttributes.getinspectionId());
        valuesItem.put(COLUMN_PROJECT_ID, inspectionItemAttributes.getprojectId());
        valuesItem.put(COLUMN_A_ID, inspectionItemAttributes.getaId());
        valuesItem.put(COLUMN_DATE_INSPECTED, inspectionItemAttributes.getdateInspected());
        valuesItem.put(COLUMN_OVERVIEW, inspectionItemAttributes.getoverview());
        valuesItem.put(COLUMN_SERVICED_BY, inspectionItemAttributes.getservicedBy());
        valuesItem.put(COLUMN_RELEVANT_INFO, inspectionItemAttributes.getrelevantInfo());
        valuesItem.put(COLUMN_SERVICE_LEVEL, inspectionItemAttributes.getserviceLevel());
        valuesItem.put(COLUMN_REPORT_IMAGE, inspectionItemAttributes.getReportImage());
        valuesItem.put(COLUMN_IMG1, inspectionItemAttributes.getimage1());
        valuesItem.put(COLUMN_COM1, inspectionItemAttributes.getcom1());
        valuesItem.put(COLUMN_IMG2, inspectionItemAttributes.getimage2());
        valuesItem.put(COLUMN_COM2, inspectionItemAttributes.getcom2());
        valuesItem.put(COLUMN_IMG3, inspectionItemAttributes.getimage3());
        valuesItem.put(COLUMN_COM3, inspectionItemAttributes.getcom3());
        valuesItem.put(COLUMN_IMG4, inspectionItemAttributes.getimage4());
        valuesItem.put(COLUMN_COM4, inspectionItemAttributes.getcom4());
        valuesItem.put(COLUMN_IMG5, inspectionItemAttributes.getimage5());
        valuesItem.put(COLUMN_COM5, inspectionItemAttributes.getcom5());
        valuesItem.put(COLUMN_IMG6, inspectionItemAttributes.getimage6());
        valuesItem.put(COLUMN_COM6, inspectionItemAttributes.getcom6());
        valuesItem.put(COLUMN_IMG7, inspectionItemAttributes.getimage7());
        valuesItem.put(COLUMN_COM7, inspectionItemAttributes.getcom7());
        valuesItem.put(COLUMN_ITEM_STATUS, inspectionItemAttributes.get_itemStatus());
        valuesItem.put(COLUMN_NOTES, inspectionItemAttributes.get_notes());

        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("delete from "+ TABLE_A2D_INSPECTION_ITEM);

        db.replace(TABLE_INSPECTION_ITEM, null, valuesItem);

        db.close();

    }


    //Store values from MySQL on server to local SQLite
    public void updateAdditionalFromServer(MAPattributes mapAttributes) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_PROJECT_ID, mapAttributes.getprojectId());
        values.put(COLUMN_CAT_ID, mapAttributes.getcatId());
        values.put(COLUMN_LEVEL, mapAttributes.getlevel());
        values.put(COLUMN_PARENT, mapAttributes.getparent());
        values.put(COLUMN_LABEL, mapAttributes.getlabel());
        values.put(COLUMN_CHILD, mapAttributes.getchild());
        values.put(COLUMN_A_ID, mapAttributes.getaId());
        values.put(COLUMN_INSPECTION_ID, mapAttributes.getiId());
        values.put(COLUMN_IMG1, mapAttributes.getimage1());
        values.put(COLUMN_NOTES, mapAttributes.getnote());


        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("delete from "+ TABLE_ASSET_REGISTER);
        //replace will delete the row if the asset ID already exists
        db.replace(TABLE_MAP, null, values);

        db.close();
    }


    public void updateActionFromServer(ActionItemAttributes actionItemAttributes) {

        ContentValues valuesAction = new ContentValues();
        valuesAction.put(COLUMN_INSPECTION_ID, actionItemAttributes.getinspectionId());
        valuesAction.put(COLUMN_PROJECT_ID, actionItemAttributes.getprojectId());
        valuesAction.put(COLUMN_A_ID, actionItemAttributes.getaId());
        valuesAction.put(COLUMN_DATE_INSPECTED, actionItemAttributes.getdate());
        valuesAction.put(COLUMN_OVERVIEW, actionItemAttributes.getoverview());
        valuesAction.put(COLUMN_SERVICED_BY, actionItemAttributes.getservicedBy());
        valuesAction.put(COLUMN_RELEVANT_INFO, actionItemAttributes.getrelevantInfo());
        valuesAction.put(COLUMN_SERVICE_LEVEL, actionItemAttributes.getserviceLevel());
        valuesAction.put(COLUMN_REPORT_IMAGE, actionItemAttributes.getReportImage());
        valuesAction.put(COLUMN_IMG1, actionItemAttributes.getimage1());
        valuesAction.put(COLUMN_COM1, actionItemAttributes.getcom1());
        valuesAction.put(COLUMN_ITEM_STATUS, actionItemAttributes.get_itemStatus());
        valuesAction.put(COLUMN_NOTES, actionItemAttributes.get_notes());

        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("delete from "+ TABLE_A2D_INSPECTION_ITEM);

        db.replace(TABLE_ACTION_ITEM, null, valuesAction);

        db.close();
    }


    public void updateCertificateInspectionFromServer(CertificateInspectionAttributes certificateInspectionAttributes) {

        ContentValues valuesAction = new ContentValues();
        valuesAction.put(COLUMN_INSPECTION_ID, certificateInspectionAttributes.getinspectionId());
        valuesAction.put(COLUMN_PROJECT_ID, certificateInspectionAttributes.getprojectId());
        valuesAction.put(COLUMN_DATE_TIME, certificateInspectionAttributes.getdatetime());
        valuesAction.put(COLUMN_OVERVIEW, certificateInspectionAttributes.getoverview());
        valuesAction.put(COLUMN_PERMIT_NO, certificateInspectionAttributes.getpermit());
        valuesAction.put(COLUMN_PROJECT_ADDRESS, certificateInspectionAttributes.getaddress());
        valuesAction.put(COLUMN_STAGE, certificateInspectionAttributes.getstage());
        valuesAction.put(COLUMN_NOTES, certificateInspectionAttributes.getnotes());

        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("delete from "+ TABLE_A2D_INSPECTION_ITEM);

        db.replace(TABLE_CERTIFICATE_INSPECTION, null, valuesAction);

        db.close();
    }

    public void updateSummaryFromServer(SummaryAttributes summaryAttributes) {

        ContentValues valuesAction = new ContentValues();
        valuesAction.put(COLUMN_INSPECTION_ID, summaryAttributes.getinspectionId());
        valuesAction.put(COLUMN_PROJECT_ID, summaryAttributes.getprojectId());
        valuesAction.put(COLUMN_HEAD_A, summaryAttributes.getheadA());
        valuesAction.put(COLUMN_HEAD_B, summaryAttributes.getheadB());
        valuesAction.put(COLUMN_HEAD_C, summaryAttributes.getheadC());
        valuesAction.put(COLUMN_COM_A, summaryAttributes.getcomA());
        valuesAction.put(COLUMN_COM_B, summaryAttributes.getcomB());
        valuesAction.put(COLUMN_COM_C, summaryAttributes.getcomC());

        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("delete from "+ TABLE_A2D_INSPECTION_ITEM);

        db.replace(TABLE_SUMMARY, null, valuesAction);

        db.close();
    }


    public void update_OR_FromServer(A_Attributes a_attributes, String CAT) {

        //replace will delete the row if the category already exists
        ContentValues values = new ContentValues();
        values.put(COLUMN_NUM, a_attributes.get_num());
        values.put(COLUMN_SUB_CAT, a_attributes.get_subCat());
        values.put(COLUMN_TYPE, a_attributes.get_type());
        values.put(COLUMN_NOTE, a_attributes.get_note());

        SQLiteDatabase db = this.getWritableDatabase();
        //replace will delete the row if the OR already exists
        switch (CAT) {
            case "A":
                db.replace(TABLE_A_OR, null, values);
                break;
 /*           case "B":
                db.replace(TABLE_B_OR, null, values);
                break;
            case "C":
                db.replace(TABLE_C_OR, null, values);
                break;
            case "D":
                db.replace(TABLE_D_OR, null, values);
                break;
            case "E":
                db.replace(TABLE_E_OR, null, values);
                break;

  */
        }
        db.close();
    }

    public void update_USER_FromServer(USER_Attributes user_attributes) {

        //replace will delete the row if the category already exists
        ContentValues values = new ContentValues();
        values.put(COLUMN_U_ID, user_attributes.getuID());
        values.put(COLUMN_U_NAME, user_attributes.getuName());
        values.put(COLUMN_U_CODE, user_attributes.getuCode());
        values.put(COLUMN_CLIENT_NAME, user_attributes.getClientName());

        SQLiteDatabase db = this.getWritableDatabase();
        //replace will delete the row if the OR already exists

         db.replace(TABLE_USER_LIST, null, values);
         db.close();

    }

    public void  update_LOG_FromServer(LOG_Attributes log_attributes) {

        //replace will delete the row if the category already exists
        ContentValues values = new ContentValues();
        values.put(COLUMN_U_ID, log_attributes.getuID());
        values.put(COLUMN_PROJECT_ID, log_attributes.getProjId());
        values.put(COLUMN_INSPECTION_ID, log_attributes.getiId());
        values.put(COLUMN_DATE_TIME_START, log_attributes.getStart());
        values.put(COLUMN_DATE_TIME_FINISH, log_attributes.getEnd());

        SQLiteDatabase db = this.getWritableDatabase();
        //replace will delete the row if the OR already exists

        db.replace(TABLE_LOG_TIME, null, values);
        db.close();

    }

    public void updateProject(String projId, String item, String txt, int num) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        ContentValues contentValues1 = new ContentValues();
        switch (item){
            case "Folder ID":{
                contentValues.put(COLUMN_ADDRESS_NUMBER, txt);
                db.update(TABLE_PROJECT_INFO, contentValues, COLUMN_PROJECT_ID + " = ?" , new String[]{projId});
                break;
            }
            case "Folder Title":{
                contentValues.put(COLUMN_PROJECT_ADDRESS, txt);
                db.update(TABLE_PROJECT_INFO, contentValues, COLUMN_PROJECT_ID + " = ?" , new String[]{projId});
                contentValues1.put(COLUMN_LABEL, txt);
                contentValues1.put(COLUMN_INSPECTION_STATUS, "m");
                db.update(TABLE_INSPECTION, contentValues1, COLUMN_PROJECT_ID + " = ? AND "+ COLUMN_INSPECTION_ID + " = ?", new String[]{projId, Integer.toString(0)});
                break;
            }
            case "infoA":{
                contentValues.put(COLUMN_INFO_A, txt);
                db.update(TABLE_PROJECT_INFO, contentValues, COLUMN_PROJECT_ID + " = ?" , new String[]{projId});
                break;
            }
            case "infoB":{
                contentValues.put(COLUMN_INFO_B, txt);
                db.update(TABLE_PROJECT_INFO, contentValues, COLUMN_PROJECT_ID + " = ?" , new String[]{projId});
                break;
            }
            case "infoC":{
                contentValues.put(COLUMN_INFO_C, txt);
                db.update(TABLE_PROJECT_INFO, contentValues, COLUMN_PROJECT_ID + " = ?" , new String[]{projId});
                break;
            }
            case "infoD":{
                contentValues.put(COLUMN_INFO_D, txt);
                db.update(TABLE_PROJECT_INFO, contentValues, COLUMN_PROJECT_ID + " = ?" , new String[]{projId});
                break;
            }
            case "infoE":{
                contentValues.put(COLUMN_INFO_E, num);
                db.update(TABLE_PROJECT_INFO, contentValues, COLUMN_PROJECT_ID + " = ?" , new String[]{projId});
                break;
            }
            case "infoH":{
                contentValues.put(COLUMN_INFO_H, txt);
                db.update(TABLE_PROJECT_INFO, contentValues, COLUMN_PROJECT_ID + " = ?" , new String[]{projId});
                break;
            }
            case "infoJ":{
                contentValues.put(COLUMN_INFO_J, txt);
                db.update(TABLE_PROJECT_INFO, contentValues, COLUMN_PROJECT_ID + " = ?" , new String[]{projId});
                break;
            }
            case "infoI":{
                contentValues.put(COLUMN_INFO_I, txt);
                db.update(TABLE_PROJECT_INFO, contentValues, COLUMN_PROJECT_ID + " = ?" , new String[]{projId});
                break;
            }

            case "Folder Note":{
                contentValues.put(COLUMN_PROJECT_NOTE, txt);
                db.update(TABLE_PROJECT_INFO, contentValues, COLUMN_PROJECT_ID + " = ?" , new String[]{projId});
                break;
            }


        }




        db.close();


    }


    public void updateInspection(String projId, String iId, String Label, String Note, String Note_2) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NOTE, Note);
        contentValues.put(COLUMN_NOTE_2, Note_2);
        contentValues.put(COLUMN_LABEL, Label);
        contentValues.put(COLUMN_INSPECTION_STATUS, "m");



        db.update(TABLE_INSPECTION, contentValues, COLUMN_PROJECT_ID + " = ? AND " + COLUMN_INSPECTION_ID + " = ? " , new String[]{projId, iId});
        db.close();


    }

    public void updateMap(String projId, int aId, String Notes) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NOTES, Notes);
        contentValues.put(COLUMN_ITEM_STATUS, "m");
        db.update(TABLE_MAP, contentValues, COLUMN_PROJECT_ID + " = ? AND " + COLUMN_A_ID + " = ? " , new String[]{projId, Integer.toString(aId)});
        db.close();


    }


    public void updateInspectionItem(int projId, int iId, int aId, String date, String overview, String servicedBy, String relevantInfo,
                                     String ServiceLevel, String reportImage, String com1, String com2, String com3, String com4,
                                      String com5, String com6,  String com7, String ItemStatus, String Notes) {

        String inspectionId = String.valueOf(iId);
        String a_Id = String.valueOf(aId);
        String proj_id = String.valueOf(projId);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DATE_INSPECTED, date);
        contentValues.put(COLUMN_OVERVIEW, overview);
        contentValues.put(COLUMN_RELEVANT_INFO, relevantInfo);
        contentValues.put(COLUMN_SERVICE_LEVEL, ServiceLevel);
        contentValues.put(COLUMN_SERVICED_BY, servicedBy);
        contentValues.put(COLUMN_REPORT_IMAGE, reportImage);
        contentValues.put(COLUMN_COM1, com1);
        contentValues.put(COLUMN_COM2, com2);
        contentValues.put(COLUMN_COM3, com3);
        contentValues.put(COLUMN_COM4, com4);
        contentValues.put(COLUMN_COM5, com5);
        contentValues.put(COLUMN_COM6, com6);
        contentValues.put(COLUMN_COM7, com7);
        contentValues.put(COLUMN_ITEM_STATUS, ItemStatus);
        contentValues.put(COLUMN_NOTES, Notes);


        db.update(TABLE_INSPECTION_ITEM, contentValues, COLUMN_PROJECT_ID + " = ? AND " + COLUMN_INSPECTION_ID + " = ? AND " + COLUMN_A_ID + " = ? ", new String[]{proj_id, inspectionId, a_Id});
        db.close();


    }


    public void updateInspectionItemPhoto(int projId, int iId, int aId, String Img1, String Img2, String Img3, String Img4, String Img5
                                            , String Img6, String Img7) {

        String inspectionId = String.valueOf(iId);
        String a_Id = String.valueOf(aId);
        String proj_id = String.valueOf(projId);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_IMG1, Img1);
        contentValues.put(COLUMN_IMG2, Img2);
        contentValues.put(COLUMN_IMG3, Img3);
        contentValues.put(COLUMN_IMG4, Img4);
        contentValues.put(COLUMN_IMG5, Img5);
        contentValues.put(COLUMN_IMG6, Img6);
        contentValues.put(COLUMN_IMG7, Img7);
        contentValues.put(COLUMN_ITEM_STATUS, "m");
        db.update(TABLE_INSPECTION_ITEM, contentValues, COLUMN_PROJECT_ID + " = ? AND " + COLUMN_INSPECTION_ID + " = ? AND " + COLUMN_A_ID + " = ? ", new String[]{proj_id, inspectionId, a_Id});
        db.close();


    }



    public void updateInspectionItemdate() {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DATE_INSPECTED, "20200424");
        contentValues.put(COLUMN_ITEM_STATUS, "m");


        db.update(TABLE_INSPECTION_ITEM, contentValues, COLUMN_DATE_INSPECTED +" = ?" , new String[]{"2020-04-24"});
        db.close();


    }

    public void logInspection(String projID, String iID, String start, String end){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query;
        Cursor cursor;
        int maxID =1;

        Query = "SELECT " + COLUMN_DATE_TIME_START + " FROM "
                + TABLE_INSPECTION
                + " WHERE " + COLUMN_PROJECT_ID + " = " + projID  + " AND " + COLUMN_INSPECTION_ID+ " = " + iID;

        cursor = db.rawQuery(Query, null);
        if (cursor.moveToFirst()) {
            if(cursor.getString(0).equals("null")){
                ContentValues contentValues = new ContentValues();
                contentValues.put(COLUMN_DATE_TIME_START, start);
                contentValues.put(COLUMN_DATE_TIME_FINISH, end);
                db.update(TABLE_INSPECTION, contentValues, COLUMN_PROJECT_ID + " = ? AND " + COLUMN_INSPECTION_ID + " = ?", new String[]{projID, iID});
            }
            Query = "SELECT MAX(" + COLUMN_U_ID + ") FROM "
                    + TABLE_LOG_TIME;

            cursor = db.rawQuery(Query, null);
            if (cursor.moveToFirst()) {
                maxID = cursor.getInt(0);
                maxID = maxID + 1;
            }
            ContentValues values = new ContentValues();
            values.put(COLUMN_U_ID, maxID);
            values.put(COLUMN_PROJECT_ID, projID);
            values.put(COLUMN_INSPECTION_ID, iID);
            values.put(COLUMN_DATE_TIME_START,start);
            values.put(COLUMN_DATE_TIME_FINISH,end);
            db.insert(TABLE_LOG_TIME, null, values);
        }

        db.close();
    }




    public void updateActionItem(String projId, String iId, int aId, String date, String overview, String servicedBy, String relevantInfo, String ServiceLevel
            , String com1, String ItemStatus, String Notes) {


        String a_Id = String.valueOf(aId);


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DATE_INSPECTED, date);
        contentValues.put(COLUMN_OVERVIEW, overview);
        contentValues.put(COLUMN_RELEVANT_INFO, relevantInfo);
        contentValues.put(COLUMN_SERVICE_LEVEL, ServiceLevel);
        contentValues.put(COLUMN_SERVICED_BY, servicedBy);
        contentValues.put(COLUMN_COM1, com1);
        contentValues.put(COLUMN_ITEM_STATUS, ItemStatus);
        contentValues.put(COLUMN_NOTES, Notes);
        db.update(TABLE_ACTION_ITEM, contentValues, COLUMN_PROJECT_ID + " = ? AND " + COLUMN_INSPECTION_ID + " = ? AND " + COLUMN_A_ID + " = ? ", new String[]{projId, iId, a_Id});
        db.close();


    }

    public void updateActionItemPhoto(String projId, String iId, int aId, String Img1) {


        String a_Id = String.valueOf(aId);


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_IMG1, Img1);
        contentValues.put(COLUMN_ITEM_STATUS, "m");
        db.update(TABLE_ACTION_ITEM, contentValues, COLUMN_PROJECT_ID + " = ? AND " + COLUMN_INSPECTION_ID + " = ? AND " + COLUMN_A_ID + " = ? ", new String[]{projId, iId, a_Id});
        db.close();


    }



    public void updateCertificateInspection(String projId, String iId, String datetime, String overview, String permit, String address,
                                            String stage, String notes) {


         SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DATE_TIME, datetime);
        contentValues.put(COLUMN_OVERVIEW, overview);
        contentValues.put(COLUMN_PERMIT_NO, permit);
        contentValues.put(COLUMN_PROJECT_ADDRESS, address);
        contentValues.put(COLUMN_STAGE, stage );
        contentValues.put(COLUMN_NOTES, notes);
        contentValues.put(COLUMN_ITEM_STATUS, "m");
        db.update(TABLE_CERTIFICATE_INSPECTION, contentValues, COLUMN_PROJECT_ID + " = ? AND " + COLUMN_INSPECTION_ID + " = ? " , new String[]{projId, iId});
        db.close();


    }


    public void updateSummary(String projId, String iId, String headA, String comA, String headB, String comB,
                                            String headC, String comC) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_HEAD_A, headA);
        contentValues.put(COLUMN_HEAD_B, headB);
        contentValues.put(COLUMN_HEAD_C, headC);
        contentValues.put(COLUMN_COM_A, comA);
        contentValues.put(COLUMN_COM_B, comB );
        contentValues.put(COLUMN_COM_C, comC);
        contentValues.put(COLUMN_ITEM_STATUS, "m");
        db.update(TABLE_SUMMARY, contentValues, COLUMN_PROJECT_ID + " = ? AND " + COLUMN_INSPECTION_ID + " = ? " , new String[]{projId, iId});
        db.close();


    }


    public void updatePropPhoto(String projectId, String photo) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_PROJECT_PHOTO, photo);
        db.update(TABLE_PROJECT_INFO, values, COLUMN_PROJECT_ID + " = " + projectId, null);
        db.close();

    }

    public void updateInspectionPhoto(String projectId, String iId, String photo) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_IMAGE, photo);
        values.put(COLUMN_INSPECTION_STATUS, "m");
        db.update(TABLE_INSPECTION, values, COLUMN_PROJECT_ID + " = " + projectId+" AND "+COLUMN_INSPECTION_ID+" = "+iId, null);
        db.close();

    }



    public void updateStatus(int projId, int iId, String status, String date) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_INSPECTION_STATUS, status);
        values.put(COLUMN_INSPECTION_DATE, date);

        db.update(TABLE_INSPECTION, values, COLUMN_PROJECT_ID + " = " + projId + " AND " + COLUMN_INSPECTION_ID + " = " + iId, null);
        db.close();
    }

    public int updateMapLabel(int projId, int aId, String label) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        int success = 0;
        try {
            values.put(COLUMN_LABEL, label);
            values.put(COLUMN_ITEM_STATUS, "m");

            db.update(TABLE_MAP, values, COLUMN_PROJECT_ID + " = " + projId + " AND " +
                    COLUMN_A_ID + " = " + aId, null);
            success = 1;
        }
        catch (Exception e){

        }

        db.close();
        return success;
    }

    public void updateBranchPhoto(int projId, int aId, String photo) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_IMG1, photo);
        values.put(COLUMN_ITEM_STATUS, "m");


        db.update(TABLE_MAP, values, COLUMN_PROJECT_ID + " = " + projId + " AND " +
                COLUMN_A_ID + " = " + aId, null);

        db.close();

    }

    public int getMapNodeType(Integer projId, Integer aId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;
        int type = 0;

        selectQuery = "SELECT M." + COLUMN_PARENT + " FROM "
                + TABLE_MAP + " M"
                + " WHERE M." + COLUMN_PROJECT_ID + " = " + projId + " AND M." + COLUMN_PARENT + " = " + aId;

        cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() == 0) {
            selectQuery = "SELECT M." + COLUMN_CHILD + " FROM "
                    + TABLE_MAP + " M"
                    + " WHERE M." + COLUMN_PROJECT_ID + " = " + projId + " AND M." + COLUMN_A_ID + " = " + aId;

            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                type = cursor.getInt(0);
            }
        }
        else{
            type = -1;
        }

        db.close();
        return type;
    }


    public void statusChanged(Integer projId, Integer iId) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_INSPECTION_STATUS, "m");

        db.update(TABLE_INSPECTION, values, COLUMN_PROJECT_ID + " = " + projId + " AND "+COLUMN_INSPECTION_ID+ " = "+ iId , null);
        db.update(TABLE_INSPECTION, values, COLUMN_PROJECT_ID + " = " + projId + " AND "+COLUMN_INSPECTION_ID+ " = "+ 0 , null);

        db.close();

    }

    public void statusUploaded(int user_id) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        String proj_id;


        String selectQueryI = "SELECT I."+COLUMN_PROJECT_ID
                + " FROM " + TABLE_INSPECTION + " I "
                + " WHERE I."+COLUMN_INSPECTOR+" = "+user_id +" AND I."+COLUMN_INSPECTION_STATUS+" = 'm'"
                + " GROUP BY I."+ COLUMN_PROJECT_ID;

        Cursor cursorI = db.rawQuery(selectQueryI, null);
        if (cursorI.moveToFirst()) {
            do {
                proj_id = cursorI.getString(0);
                ContentValues values1 = new ContentValues();
                values1.put(COLUMN_ITEM_STATUS, "p");
                db.update(TABLE_INSPECTION_ITEM, values1, COLUMN_PROJECT_ID + " = " + proj_id+" AND "+COLUMN_ITEM_STATUS+"= 'm' ", null);

                ContentValues values2 = new ContentValues();
                values2.put(COLUMN_ITEM_STATUS, "p");
                db.update(TABLE_ACTION_ITEM, values2, COLUMN_PROJECT_ID + " = " + proj_id+" AND "+COLUMN_ITEM_STATUS+"= 'm' ", null);

                ContentValues values3 = new ContentValues();
                values3.put(COLUMN_ITEM_STATUS, "p");
                db.update(TABLE_SUMMARY, values3, COLUMN_PROJECT_ID + " = " + proj_id+" AND "+COLUMN_ITEM_STATUS+"= 'm' ", null);

                ContentValues values4 = new ContentValues();
                values4.put(COLUMN_ITEM_STATUS, "p");
                db.update(TABLE_CERTIFICATE_INSPECTION, values4, COLUMN_PROJECT_ID + " = " + proj_id+" AND "+COLUMN_ITEM_STATUS+"= 'm' ", null);

                ContentValues values5 = new ContentValues();
                values4.put(COLUMN_ITEM_STATUS, "p");
                db.update(TABLE_MAP, values4, COLUMN_PROJECT_ID + " = " + proj_id+" AND "+COLUMN_ITEM_STATUS+"= 'm' ", null);
            } while (cursorI.moveToNext());
        }

        ContentValues values = new ContentValues();
        values.put(COLUMN_INSPECTION_STATUS, "p");
        db.update(TABLE_INSPECTION, values, COLUMN_INSPECTOR + " = " + user_id , null);

        db.close();
    }

    public void moveTAB(int projId, int aId, int m_aId) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;
        int level = 0;

        selectQuery = "SELECT " + COLUMN_LEVEL + " FROM "
                + TABLE_MAP
                + " WHERE " + COLUMN_PROJECT_ID + " = " + projId  + " AND " + COLUMN_A_ID+ " = " + m_aId;

        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {

            level = cursor.getInt(0)+1;
        }

        ContentValues values = new ContentValues();
        values.put(COLUMN_PARENT, m_aId);
        values.put(COLUMN_LEVEL, level);
        db.update(TABLE_MAP, values, COLUMN_PROJECT_ID + " = " + projId  + " AND " + COLUMN_A_ID+ " = " + aId, null);

        db.close();

    }

    public int checkCode(String code) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;
        int u_ID = 0;

        selectQuery = "SELECT " + COLUMN_U_ID + " FROM "
                + TABLE_USER_LIST
                + " WHERE " + COLUMN_U_CODE + " = "+ code;

        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {

            u_ID = cursor.getInt(0);
        }
        db.close();
        return u_ID;

    }

    public String getClient(int user_id) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;
        String client = "no-client";

        selectQuery = "SELECT " + COLUMN_CLIENT_NAME + " FROM "
                + TABLE_USER_LIST
                + " WHERE " + COLUMN_U_ID + " = "+ user_id;

        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {

            client = cursor.getString(0);
        }
        db.close();
        return client;

    }

    public String getUser(int user_id) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;
        String client = "no-client";

        selectQuery = "SELECT " + COLUMN_U_NAME + " FROM "
                + TABLE_USER_LIST
                + " WHERE " + COLUMN_U_ID + " = "+ user_id;

        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {

            client = cursor.getString(0);
        }
        db.close();
        return client;

    }

    public int getParentId(int projId, int aid) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;
        int pId = -1;

        selectQuery = "SELECT " + COLUMN_PARENT + " FROM "
                + TABLE_MAP
                + " WHERE "+ COLUMN_PROJECT_ID + " = "+ projId+ " AND " + COLUMN_A_ID + " = "+ aid;

        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {

            pId = cursor.getInt(0);
        }
        db.close();
        return pId;

    }

    public int checkstatus(String type, int projId) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;
        int status = 0;

        if(type == "all"){

                   selectQuery = "SELECT M." + COLUMN_INSPECTION_ID + " FROM "
                        + TABLE_INSPECTION + " M"
                        + " WHERE M." + COLUMN_INSPECTION_STATUS + " = 'm'";
                cursor = db.rawQuery(selectQuery, null);

                status = cursor.getCount();
        }

        //select status of the specific project
            if(type == "project") {
                selectQuery = "SELECT M." + COLUMN_INSPECTION_ID + " FROM "
                        + TABLE_INSPECTION + " M"
                        + " WHERE M." + COLUMN_INSPECTION_STATUS + " = 'm' AND " + COLUMN_PROJECT_ID + " = " + projId;
                cursor = db.rawQuery(selectQuery, null);

                status = cursor.getCount();
            }

        db.close();
        return status;

    }



    public String calcTime(String projId, String iId){

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;
        String diffHours = "0";
        long mills = 0;


            selectQuery = "SELECT " + COLUMN_DATE_TIME_START +", "+COLUMN_DATE_TIME_FINISH+ " FROM "
                    + TABLE_LOG_TIME
                    + " WHERE " + COLUMN_PROJECT_ID + " = "+  projId+" AND "+ COLUMN_INSPECTION_ID+" = "+iId;

            cursor = db.rawQuery(selectQuery, null);

           if (cursor.moveToFirst()) {
               do{
              SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               Date date1 = null;
               Date date2 = null;
               try {

                   date1 = df.parse(cursor.getString(0));
                   date2 = df.parse(cursor.getString(1));
               } catch (ParseException e) {
                   e.printStackTrace();
               }

               mills = mills + (date2.getTime() - date1.getTime());

               } while (cursor.moveToNext());

               int hours = (int) (mills/(1000 * 60 * 60));
               int mins = (int) ((mills/(1000*60)) % 60);
               diffHours = hours + " Hrs " + mins+" Min";
        }


            return  diffHours;

    }






    public void deleteInspectionItem(Integer projId, Integer aId) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_INSPECTION_ITEM, COLUMN_PROJECT_ID + " = " + projId + " AND " + COLUMN_A_ID + " = " + aId, null);
     //   db.delete(TABLE_ACTION_ITEM, COLUMN_PROJECT_ID + " = " + projId + " AND " + COLUMN_A_ID + " = " + aId, null);
     //   db.delete(TABLE_MAP, COLUMN_PROJECT_ID + " = " + projId + " AND " + COLUMN_A_ID + " = " + aId, null);
        db.close();
    }

    public void deleteActionItem(Integer projId, Integer aId) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();
        //column reportimage holds the aId in the Map table

        db.delete(TABLE_ACTION_ITEM, COLUMN_PROJECT_ID + " = " + projId + " AND " + COLUMN_REPORT_IMAGE + " = " + aId, null);
        db.close();
    }

    public void deleteMapBranch(int projId, int aId) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_MAP, COLUMN_PROJECT_ID + " = " + projId + " AND " + COLUMN_A_ID + " = " + aId, null);
         db.close();
    }

    public void deleteSummary(int projId, int iId, int aId) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_SUMMARY, COLUMN_PROJECT_ID + " = " + projId + " AND " + COLUMN_INSPECTION_ID + " = " + iId, null);
        db.close();
    }

    public void deleteCertificate(int projId, int iId, int aId) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_CERTIFICATE_INSPECTION, COLUMN_PROJECT_ID + " = " + projId + " AND " + COLUMN_INSPECTION_ID + " = " + iId, null);
        db.close();
    }

    public void deleteTable() {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME,null , null);
        db.close();
    }

    public void deleteRec(String name, int projId, int iId, int aId) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TABLE_NAME, name);
        values.put(COLUMN_PROJECT_ID, projId);
        values.put(COLUMN_INSPECTION_ID, iId);
        values.put(COLUMN_A_ID, aId);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }


    public void deletePhoto(Integer jId, Integer aId, Integer rId, Integer Col) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        String column = "";

        switch (Col) {
            case 2:
                column = COLUMN_IMG2;
                break;

            case 3:
                column = COLUMN_IMG3;
                break;

            case 4:
                column = COLUMN_IMG4;
                break;

            case 5:
                column = COLUMN_IMG5;
                break;
        }
        String selectQuery;
        Cursor cursor;

        selectQuery = "UPDATE " + TABLE_INSPECTION_ITEM + " SET "
                + column
                + " = '' WHERE " + COLUMN_INSPECTION_ID + " = " + jId + "  AND " + COLUMN_A_ID + " = " + aId
        ;

        cursor = db.rawQuery(selectQuery, null);

        db.close();
    }

    //Get the current max AssetID Number for the property
    public void addProject(int user_id, String projID, String label, String pID){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PROJECT_ID, projID);
        values.put(COLUMN_PROJECT_ADDRESS, label);
        values.put(COLUMN_ADDRESS_NUMBER,"");
        values.put(COLUMN_PROJECT_PHOTO,"");
        db.insert(TABLE_PROJECT_INFO, null, values);

        ContentValues valuesi = new ContentValues();
        valuesi.put(COLUMN_INSPECTION_ID, "0");
        valuesi.put(COLUMN_INSPECTION_TYPE, "QE");
        valuesi.put(COLUMN_INSPECTION_STATUS, "n");
        valuesi.put(COLUMN_PROJECT_ID, projID);
        valuesi.put(COLUMN_INSPECTION_DATE, dayTime(1) );
        valuesi.put(COLUMN_INSPECTOR, user_id );
        valuesi.put(COLUMN_DATE_TIME_START, dayTime(4));
        valuesi.put(COLUMN_DATE_TIME_FINISH, dayTime(4));
        valuesi.put(COLUMN_LABEL, label);
        valuesi.put(COLUMN_LEVEL, "0");
        valuesi.put(COLUMN_PARENT, "-1");
        valuesi.put(COLUMN_P_ID, pID);
        valuesi.put(COLUMN_IMAGE, "");
        valuesi.put(COLUMN_NOTE, "");

        db.insert(TABLE_INSPECTION, null, valuesi);

        ContentValues valuesi2 = new ContentValues();
        valuesi2.put(COLUMN_INSPECTION_ID, "1");
        valuesi2.put(COLUMN_INSPECTION_TYPE, "QE");
        valuesi2.put(COLUMN_INSPECTION_STATUS, "n");
        valuesi2.put(COLUMN_PROJECT_ID, projID);
        valuesi2.put(COLUMN_INSPECTION_DATE, dayTime(1) );
        valuesi2.put(COLUMN_INSPECTOR, user_id );
        valuesi2.put(COLUMN_DATE_TIME_START, dayTime(4));
        valuesi2.put(COLUMN_DATE_TIME_FINISH, dayTime(4));
        valuesi2.put(COLUMN_LABEL, "Document");
        valuesi2.put(COLUMN_LEVEL, "1");
        valuesi2.put(COLUMN_PARENT,  pID );
        valuesi2.put(COLUMN_P_ID, Integer.valueOf(pID)+1);
        valuesi2.put(COLUMN_IMAGE, "");
        valuesi2.put(COLUMN_NOTE, "");

        db.insert(TABLE_INSPECTION, null, valuesi2);

      db.close();
    }

    public void addActivity(int user_id, String projID, String iId, String label, String pID, String cpID){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valuesi2 = new ContentValues();
        valuesi2.put(COLUMN_INSPECTION_ID, iId);
        valuesi2.put(COLUMN_INSPECTION_TYPE, "QE");
        valuesi2.put(COLUMN_INSPECTION_STATUS, "m");
        valuesi2.put(COLUMN_PROJECT_ID, projID);
        valuesi2.put(COLUMN_INSPECTION_DATE, dayTime(1) );
        valuesi2.put(COLUMN_INSPECTOR, user_id );
        valuesi2.put(COLUMN_DATE_TIME_START, "null");  //valuesi2.put(COLUMN_DATE_TIME_START, dayTime(4));
        valuesi2.put(COLUMN_DATE_TIME_FINISH, "null");
        valuesi2.put(COLUMN_LABEL, label);
        valuesi2.put(COLUMN_LEVEL, "1");
        valuesi2.put(COLUMN_PARENT,  cpID );
        valuesi2.put(COLUMN_P_ID, Integer.valueOf(pID));
        valuesi2.put(COLUMN_IMAGE, "");
        valuesi2.put(COLUMN_NOTE, "");

        db.insert(TABLE_INSPECTION, null, valuesi2);

        db.close();
    }

    //Add sublocation to the location
    public int addLevel(int projID, int aId, int iId, int CatID, int Level, int parent, String Label, int type) {
        // Open a database for reading and writing

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;
        int maxAId = 0;
        int maxcatID = 1;
        int branchType = 0;

        selectQuery = "SELECT  " + COLUMN_CHILD + " FROM "
                + TABLE_MAP + " WHERE " + COLUMN_PROJECT_ID + " = " + projID + " AND " + COLUMN_A_ID + " = " + aId;

        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
            branchType = cursor.getInt(0);

                selectQuery = "SELECT  MAX(M." + COLUMN_CAT_ID + ") FROM "
                        + TABLE_MAP + " M"
                        + " WHERE M." + COLUMN_PROJECT_ID + " = " + projID
                        + " AND " + COLUMN_CAT_ID + " < 500 ";  //+" AND E2."+COLUMN_LOCATION_ID+" = "+locationId;

                cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    maxcatID = cursor.getInt(0);
                    maxcatID = maxcatID + 1;
                }

                selectQuery = "SELECT MAX(M." + COLUMN_A_ID + ") FROM "
                        + TABLE_MAP + " M"
                        + " WHERE M." + COLUMN_PROJECT_ID + " = " + projID;

                cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    maxAId = cursor.getInt(0);
                    maxAId = maxAId + 1;
                }


                switch (type) {

                    case (0): {
                        ContentValues values = new ContentValues();
                        if(branchType == 0 || branchType >=9) {

                            if (Level == 0) {
                                values.put(COLUMN_CAT_ID, maxcatID);
                                values.put(COLUMN_PARENT, -1);
                            } else {
                                values.put(COLUMN_CAT_ID, CatID);
                                values.put(COLUMN_PARENT, parent);
                            }
                            values.put(COLUMN_PROJECT_ID, projID);
                            values.put(COLUMN_LABEL, Label);
                            values.put(COLUMN_LEVEL, Level);
                            values.put(COLUMN_A_ID, maxAId);
                            values.put(COLUMN_INSPECTION_ID, 0);
                            values.put(COLUMN_CHILD, type);
                            values.put(COLUMN_IMG1, "");
                            values.put(COLUMN_NOTES, "");
                            values.put(COLUMN_ITEM_STATUS, "m");
                            db.insert(TABLE_MAP, null, values);
                        }
                        else maxAId = 0;  //return 0 value
                        break;

                    }
                    case (1): {

                        ContentValues values = new ContentValues();

                        values.put(COLUMN_CAT_ID, CatID);
                        values.put(COLUMN_PARENT, parent);
                        values.put(COLUMN_PROJECT_ID, projID);
                        values.put(COLUMN_LABEL, Label);
                        values.put(COLUMN_LEVEL, Level);
                        values.put(COLUMN_A_ID, maxAId);
                        values.put(COLUMN_INSPECTION_ID, iId);
                        values.put(COLUMN_CHILD, type);
                        values.put(COLUMN_IMG1, "");
                        values.put(COLUMN_NOTES, "");
                        values.put(COLUMN_ITEM_STATUS, "m");
                        db.insert(TABLE_MAP, null, values);

                        break;
                    }

                    case (2): {

                        ContentValues values = new ContentValues();

                        values.put(COLUMN_CAT_ID, CatID);
                        values.put(COLUMN_PARENT, parent);
                        values.put(COLUMN_PROJECT_ID, projID);
                        values.put(COLUMN_LABEL, Label);
                        values.put(COLUMN_LEVEL, Level);
                        values.put(COLUMN_A_ID, maxAId);
                        values.put(COLUMN_INSPECTION_ID, iId);
                        values.put(COLUMN_CHILD, type);
                        values.put(COLUMN_IMG1, "");
                        values.put(COLUMN_NOTES, "");
                        values.put(COLUMN_ITEM_STATUS, "m");
                        db.insert(TABLE_MAP, null, values);

                        break;
                    }

                }


        db.close();

        return maxAId;

    }


    public String getMapBranchTitle(int projID, int CatID) {
        // Open a database for reading and writing

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;
        String label = "Branch Title";

        selectQuery = "SELECT M." + COLUMN_LABEL + " FROM "
                + TABLE_MAP + " M"
                + " WHERE M." + COLUMN_PROJECT_ID + " = " + projID + " AND M." + COLUMN_CAT_ID + " = " + CatID;

        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {

            label = cursor.getString(0);
        }
        db.close();
        return label;

    }


    public Integer addReportBranch(int projId, int iId, int CatID, int Level, int aId, String Label) {
        // Open a database for reading and writing

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;
        int result = 0;
        int a_id = 1; //aId of the branch which has the parent branch clicked

        //First check if current branch is a location branch
        selectQuery = "SELECT  " + COLUMN_CHILD + " FROM "
                + TABLE_MAP + " WHERE " + COLUMN_PROJECT_ID + " = " + projId + " AND " + COLUMN_A_ID + " = " + aId;

        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
            if (cursor.getInt(0) != 0){cursor.close();}
                else{

 /*               //check if there a subbranch exists
                selectQuery = "SELECT  " + COLUMN_A_ID + " FROM "
                        + TABLE_MAP + " WHERE " + COLUMN_PROJECT_ID + " = " + projId + " AND " + COLUMN_PARENT + " = " + aId;

            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                //if subbranch exists

                if (cursor.getCount() > 0) {
                    a_id = cursor.getInt(0);//check if subbranch is an inspection with the current inspection id
                    selectQuery = "SELECT  COUNT (I." + COLUMN_PROJECT_ID + ") FROM "
                            + TABLE_INSPECTION_ITEM + " I "
                            + "JOIN " + TABLE_MAP + " M "
                            + " ON M. " + COLUMN_A_ID + " = I." + COLUMN_A_ID + " AND M." + COLUMN_PROJECT_ID + " = I." + COLUMN_PROJECT_ID
                            + " WHERE I." + COLUMN_PROJECT_ID + " = " + projId + " AND M." + COLUMN_A_ID + " = " + a_id + " AND I." + COLUMN_INSPECTION_ID + " = " + iId;

                    cursor = db.rawQuery(selectQuery, null);
                    if (cursor.moveToFirst()) {
                        //if inspection exits exit
                        if (cursor.getInt(0) > 0) {
                            result = 0;
                        } else //inspection doesn't exist
                        {
     */                       result = 1;

                            int newId = addLevel(projId, aId, iId, CatID, Level, aId, Label, 1); //first aId is aId second aId passed as the parent
                                                                                                    //of the new branch
                            SQLiteDatabase db_2 = this.getWritableDatabase();

                            ContentValues values = new ContentValues();

                            values.put(COLUMN_PROJECT_ID, projId);
                            values.put(COLUMN_INSPECTION_ID, iId);
                            values.put(COLUMN_DATE_INSPECTED, dayTime(1));
                            values.put(COLUMN_A_ID, newId);
                            values.put(COLUMN_OVERVIEW, "");
                            values.put(COLUMN_RELEVANT_INFO, "");
                            values.put(COLUMN_SERVICE_LEVEL, "1");
                            values.put(COLUMN_SERVICED_BY, "");
                            values.put(COLUMN_REPORT_IMAGE, "1");
                            values.put(COLUMN_IMG1, "");
                            values.put(COLUMN_COM1, "");
                            values.put(COLUMN_IMG2, "");
                            values.put(COLUMN_COM2, "");
                            values.put(COLUMN_IMG3, "");
                            values.put(COLUMN_COM3, "");
                            values.put(COLUMN_IMG4, "");
                            values.put(COLUMN_COM4, "");
                            values.put(COLUMN_IMG5, "");
                            values.put(COLUMN_COM5, "");
                            values.put(COLUMN_IMG6, "");
                            values.put(COLUMN_COM6, "");
                            values.put(COLUMN_IMG7, "");
                            values.put(COLUMN_COM7, "");
                            values.put(COLUMN_ITEM_STATUS, "m");
                            values.put(COLUMN_NOTES, "");

                            db_2.insert(TABLE_INSPECTION_ITEM, null, values);
                            db_2.close();
                        }
     /*               }

                }
            } else {
                result = 1;

                int newId = addLevel(projId, aId, CatID, Level, aId, Label, 1);

                SQLiteDatabase db_1 = this.getWritableDatabase();

                ContentValues values = new ContentValues();

                values.put(COLUMN_PROJECT_ID, projId);
                values.put(COLUMN_INSPECTION_ID, iId);
                values.put(COLUMN_DATE_INSPECTED, 20200101);
                values.put(COLUMN_A_ID, newId);
                values.put(COLUMN_OVERVIEW, "");
                values.put(COLUMN_RELEVANT_INFO, "");
                values.put(COLUMN_SERVICE_LEVEL, "1");
                values.put(COLUMN_SERVICED_BY, "");
                values.put(COLUMN_REPORT_IMAGE, "0");
                values.put(COLUMN_IMG1, "");
                values.put(COLUMN_COM1, "");
                values.put(COLUMN_IMG2, "");
                values.put(COLUMN_COM2, "");
                values.put(COLUMN_IMG3, "");
                values.put(COLUMN_COM3, "");
                values.put(COLUMN_IMG4, "");
                values.put(COLUMN_COM4, "");
                values.put(COLUMN_IMG5, "");
                values.put(COLUMN_COM5, "");
                values.put(COLUMN_IMG6, "");
                values.put(COLUMN_COM6, "");
                values.put(COLUMN_IMG7, "");
                values.put(COLUMN_COM7, "");
                values.put(COLUMN_ITEM_STATUS, "");
                values.put(COLUMN_NOTES, "");

                db_1.insert(TABLE_INSPECTION_ITEM, null, values);

                db_1.close();
            }


        }*///close if first query did not return a position branch
        db.close();
        return result;
    }


    public Integer addActionBranch(int projId, int iId, int CatID, int Level, int aId, String Label) {
        // Open a database for reading and writing

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;
        int result = 0;
        int i_id = 0; //aId of the branch which has the parent branch clicked

        //First check if current branch is a inspection branch - child is a code for the current branch
        selectQuery = "SELECT  " + COLUMN_CHILD + " FROM "
                + TABLE_MAP + " WHERE " + COLUMN_PROJECT_ID + " = " + projId + " AND " + COLUMN_A_ID + " = " + aId;

        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
            if (cursor.getInt(0) != 1){cursor.close();}
            else{

                result = 1;
                selectQuery = "SELECT  " + COLUMN_INSPECTION_ID + " FROM "
                        + TABLE_INSPECTION_ITEM + " WHERE " + COLUMN_PROJECT_ID + " = " + projId + " AND " + COLUMN_A_ID + " = " + aId;


                            cursor = db.rawQuery(selectQuery, null);
                            if (cursor.moveToFirst()) {
                                if (cursor.getInt(0) != iId)
                                    i_id = cursor.getInt(0);
                                else
                                    i_id = iId; //make sure that action from inspection has same inspectionId
                            }


                                int newId = addLevel(projId, aId, i_id, CatID, Level, aId, Label, 2);

                                SQLiteDatabase db_2 = this.getWritableDatabase();

                                ContentValues values = new ContentValues();

                                values.put(COLUMN_PROJECT_ID, projId);
                                values.put(COLUMN_INSPECTION_ID, i_id);
                                values.put(COLUMN_DATE_INSPECTED, dayTime(1));
                                values.put(COLUMN_A_ID, newId);
                                values.put(COLUMN_OVERVIEW, "");
                                values.put(COLUMN_RELEVANT_INFO, "");
                                values.put(COLUMN_SERVICE_LEVEL, "1");
                                values.put(COLUMN_SERVICED_BY, "");
                                values.put(COLUMN_REPORT_IMAGE, aId);
                                values.put(COLUMN_IMG1, "");
                                values.put(COLUMN_COM1, "");
                                values.put(COLUMN_ITEM_STATUS, "");
                                values.put(COLUMN_NOTES, "m");

                                db_2.insert(TABLE_ACTION_ITEM, null, values);
                                db_2.close();

            }//close if first query did not return a position branch
        db.close();
        return result;
    }

    public Integer addSummary(int projId, int iId, int CatID, int Level, int aId, String Label) {
        // Open a database for reading and writing

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;
        int result = 0;
        int summaryId = 0;
        int a_id = 1; //aId of the branch which has the parent branch clicked
        int maxAId = 1;

        //First check if current branch is a location branch
        selectQuery = "SELECT  " + COLUMN_PROJECT_ID + " FROM "
                + TABLE_MAP + " WHERE " + COLUMN_CAT_ID + " = 500 "
                +" AND "+COLUMN_PROJECT_ID+" = "+projId+ " AND "+ COLUMN_INSPECTION_ID+ " = "+iId;

        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst());
        //certificates tab exists
        else {
 /*           selectQuery = "SELECT  " + COLUMN_CHILD + " FROM "
                    + TABLE_MAP + " WHERE " + COLUMN_CHILD + " = 9 "
                    +" AND "+COLUMN_PROJECT_ID+" = "+projId+" AND "+COLUMN_INSPECTION_ID+" = "+iId;

            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

            }
            else{

                selectQuery = "SELECT MAX(M." + COLUMN_A_ID + ") FROM "
                        + TABLE_MAP + " M"
                        + " WHERE M." + COLUMN_PROJECT_ID + " = " + projId;  //+" AND E2."+COLUMN_LOCATION_ID+" = "+locationId;

                cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    maxAId = cursor.getInt(0);

                    maxAId = maxAId + 1;

                }

                selectQuery = "SELECT M." + COLUMN_A_ID + " FROM "
                        + TABLE_MAP + " M"
                        + " WHERE M." + COLUMN_PROJECT_ID + " = " + projId+" AND "+COLUMN_CAT_ID+" = 500";  //+" AND E2."+COLUMN_LOCATION_ID+" = "+locationId;

                cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    summaryId = cursor.getInt(0);

                }

                ContentValues values = new ContentValues();
                values.put(COLUMN_CAT_ID, CatID);
                values.put(COLUMN_PARENT, summaryId);
                values.put(COLUMN_PROJECT_ID, projId);
                values.put(COLUMN_LABEL, "COMMENTARY");
                values.put(COLUMN_LEVEL, 1);
                values.put(COLUMN_A_ID, maxAId);
                values.put(COLUMN_INSPECTION_ID, iId);
                values.put(COLUMN_CHILD,9);
                values.put(COLUMN_IMG1, "");
                values.put(COLUMN_NOTES, "");
                db.insert(TABLE_MAP, null, values);


                SQLiteDatabase db_2 = this.getWritableDatabase();

                ContentValues values3 = new ContentValues();

                values3.put(COLUMN_PROJECT_ID, projId);
                values3.put(COLUMN_INSPECTION_ID, iId);
                values3.put(COLUMN_HEAD_A, "Title A");
                values3.put(COLUMN_COM_A, "");
                values3.put(COLUMN_HEAD_B, "Title B");
                values3.put(COLUMN_COM_B, "");
                values3.put(COLUMN_HEAD_C, "Title C");
                values3.put(COLUMN_COM_C, "");

                db_2.insert(TABLE_SUMMARY, null, values3);
                db_2.close();

                result = 1;


            }

        }  //end if certificate does exist
        //if certificates tab doesn't exist
        else {
*/
            selectQuery = "SELECT MAX(M." + COLUMN_A_ID + ") FROM "
                    + TABLE_MAP + " M"
                    + " WHERE M." + COLUMN_PROJECT_ID + " = " + projId;  //+" AND E2."+COLUMN_LOCATION_ID+" = "+locationId;

            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                maxAId = cursor.getInt(0);

                maxAId = maxAId + 1;

            }

            ContentValues values = new ContentValues();
            values.put(COLUMN_CAT_ID, CatID);
            values.put(COLUMN_PARENT, -1);
            values.put(COLUMN_PROJECT_ID, projId);
            values.put(COLUMN_LABEL, Label);
            values.put(COLUMN_LEVEL, Level);
            values.put(COLUMN_A_ID, maxAId);
            values.put(COLUMN_INSPECTION_ID, iId);
            values.put(COLUMN_CHILD,9);
            values.put(COLUMN_IMG1, "");
            values.put(COLUMN_NOTES, "");
            values.put(COLUMN_ITEM_STATUS, "m");
            db.insert(TABLE_MAP, null, values);

 /*           ContentValues values2 = new ContentValues();
            values2.put(COLUMN_CAT_ID, CatID);
            values2.put(COLUMN_PARENT, maxAId);
            values2.put(COLUMN_PROJECT_ID, projId);
            values2.put(COLUMN_LABEL, "SUMMARY");
            values2.put(COLUMN_LEVEL, 1);
            values2.put(COLUMN_A_ID, maxAId+1);
            values2.put(COLUMN_INSPECTION_ID, iId);
            values2.put(COLUMN_CHILD,9);
            values2.put(COLUMN_IMG1, "");
            values2.put(COLUMN_NOTES, "");
            db.insert(TABLE_MAP, null, values2);
*/
            SQLiteDatabase db_2 = this.getWritableDatabase();

            ContentValues values3 = new ContentValues();

            values3.put(COLUMN_PROJECT_ID, projId);
            values3.put(COLUMN_INSPECTION_ID, iId);
            values3.put(COLUMN_HEAD_A, "TITLE");
            values3.put(COLUMN_COM_A, "");
            values3.put(COLUMN_HEAD_B, "TITLE");
            values3.put(COLUMN_COM_B, "");
            values3.put(COLUMN_HEAD_C, "TITLE");
            values3.put(COLUMN_COM_C, "");
            values3.put(COLUMN_ITEM_STATUS, "m");

            db_2.insert(TABLE_SUMMARY, null, values3);
            db_2.close();

            result = 1;
        }


        db.close();

        return result;
    }



    public Integer addCertificate(int projId, int iId, int CatID, int Level, int aId, String Label) {
        // Open a database for reading and writing

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;
        String address = "";
        int result = 0;
        int certId = 0;
        int a_id = 1; //aId of the branch which has the parent branch clicked
        int maxAId = 1;

        //First check if current branch is a location branch
        selectQuery = "SELECT  " + COLUMN_PROJECT_ID + " FROM "
                + TABLE_MAP + " WHERE " + COLUMN_CAT_ID + " = 501 "
                +" AND "+COLUMN_PROJECT_ID+" = "+projId;

        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
            //certificates tab exists
            {
                selectQuery = "SELECT  " + COLUMN_CHILD + " FROM "
                        + TABLE_MAP + " WHERE " + COLUMN_CHILD + " = 10 "
                        +" AND "+COLUMN_PROJECT_ID+" = "+projId+" AND "+COLUMN_INSPECTION_ID+" = "+iId;

                cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {

                }
                else{

                    selectQuery = "SELECT MAX(M." + COLUMN_A_ID + ") FROM "
                            + TABLE_MAP + " M"
                            + " WHERE M." + COLUMN_PROJECT_ID + " = " + projId;  //+" AND E2."+COLUMN_LOCATION_ID+" = "+locationId;

                    cursor = db.rawQuery(selectQuery, null);
                    if (cursor.moveToFirst()) {
                        maxAId = cursor.getInt(0);

                        maxAId = maxAId + 1;

                    }

                    selectQuery = "SELECT M." + COLUMN_A_ID + " FROM "
                            + TABLE_MAP + " M"
                            + " WHERE M." + COLUMN_PROJECT_ID + " = " + projId+" AND "+COLUMN_CAT_ID+" = 501";  //+" AND E2."+COLUMN_LOCATION_ID+" = "+locationId;

                    cursor = db.rawQuery(selectQuery, null);
                    if (cursor.moveToFirst()) {
                        certId = cursor.getInt(0);

                   }

                    ContentValues values = new ContentValues();
                    values.put(COLUMN_CAT_ID, CatID);
                    values.put(COLUMN_PARENT, certId);
                    values.put(COLUMN_PROJECT_ID, projId);
                    values.put(COLUMN_LABEL, "Certificate");
                    values.put(COLUMN_LEVEL, 1);
                    values.put(COLUMN_A_ID, maxAId);
                    values.put(COLUMN_INSPECTION_ID, iId);
                    values.put(COLUMN_CHILD,10);
                    values.put(COLUMN_IMG1, "");
                    values.put(COLUMN_NOTES, "");
                    values.put(COLUMN_ITEM_STATUS, "m");
                    db.insert(TABLE_MAP, null, values);


                    selectQuery = "SELECT  " + COLUMN_PROJECT_ADDRESS + " FROM "
                            + TABLE_PROJECT_INFO + " WHERE " + COLUMN_PROJECT_ID + " = "+projId;

                    cursor = db.rawQuery(selectQuery, null);
                    if (cursor.moveToFirst()){
                        address = cursor.getString(0);

                    }


                    SQLiteDatabase db_2 = this.getWritableDatabase();

                    ContentValues values3 = new ContentValues();

                    values3.put(COLUMN_PROJECT_ID, projId);
                    values3.put(COLUMN_INSPECTION_ID, iId);
                    values3.put(COLUMN_DATE_TIME, dayTime(4));
                    values3.put(COLUMN_OVERVIEW, "");
                    values3.put(COLUMN_PERMIT_NO, "");
                    values3.put(COLUMN_PROJECT_ADDRESS, address);
                    values3.put(COLUMN_STAGE, "");
                    values3.put(COLUMN_NOTES, "");
                    values3.put(COLUMN_ITEM_STATUS, "m");
                    db_2.insert(TABLE_CERTIFICATE_INSPECTION, null, values3);
                    db_2.close();

                    result = 1;


                }

            }  //end if certificate does exist
            //if certificates tab doesn't exist
            else {

                selectQuery = "SELECT MAX(M." + COLUMN_A_ID + ") FROM "
                        + TABLE_MAP + " M"
                        + " WHERE M." + COLUMN_PROJECT_ID + " = " + projId;  //+" AND E2."+COLUMN_LOCATION_ID+" = "+locationId;

                cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    maxAId = cursor.getInt(0);

                    maxAId = maxAId + 1;

                }

                ContentValues values = new ContentValues();
                values.put(COLUMN_CAT_ID, CatID);
                values.put(COLUMN_PARENT, -1);
                values.put(COLUMN_PROJECT_ID, projId);
                values.put(COLUMN_LABEL, Label);
                values.put(COLUMN_LEVEL, Level);
                values.put(COLUMN_A_ID, maxAId);
                values.put(COLUMN_INSPECTION_ID, 0);
                values.put(COLUMN_CHILD,10);
                values.put(COLUMN_IMG1, "");
                values.put(COLUMN_NOTES, "");
                values.put(COLUMN_ITEM_STATUS, "m");
                db.insert(TABLE_MAP, null, values);




            ContentValues values2 = new ContentValues();
            values2.put(COLUMN_CAT_ID, CatID);
            values2.put(COLUMN_PARENT, maxAId);
            values2.put(COLUMN_PROJECT_ID, projId);
            values2.put(COLUMN_LABEL, "Certificate Inspection");
            values2.put(COLUMN_LEVEL, 1);
            values2.put(COLUMN_A_ID, maxAId+1);
            values2.put(COLUMN_INSPECTION_ID, iId);
            values2.put(COLUMN_CHILD,10);
            values2.put(COLUMN_IMG1, "");
            values2.put(COLUMN_NOTES, "");
            values2.put(COLUMN_ITEM_STATUS, "m");
            db.insert(TABLE_MAP, null, values2);


            selectQuery = "SELECT  " + COLUMN_PROJECT_ADDRESS + " FROM "
                    + TABLE_PROJECT_INFO + " WHERE " + COLUMN_PROJECT_ID + " = "+projId;

            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()){
                address = cursor.getString(0);

            }


            SQLiteDatabase db_2 = this.getWritableDatabase();

            ContentValues values3 = new ContentValues();

            values3.put(COLUMN_PROJECT_ID, projId);
            values3.put(COLUMN_INSPECTION_ID, iId);
            values3.put(COLUMN_DATE_TIME, dayTime(4));
            values3.put(COLUMN_OVERVIEW, "");
            values3.put(COLUMN_PERMIT_NO, "");
            values3.put(COLUMN_PROJECT_ADDRESS, address);
            values3.put(COLUMN_STAGE, "");
            values3.put(COLUMN_NOTES, "");
            values3.put(COLUMN_ITEM_STATUS, "m");
            db_2.insert(TABLE_CERTIFICATE_INSPECTION, null, values3);
            db_2.close();

                result = 1;
            }


        db.close();

        return result;
    }


    public Integer addReference(int projId, int iId, int CatID, int Level, int aId, String Label) {
        // Open a database for reading and writing

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;
        String address = "";
        int result = 0;
        int certId = 0;
        int a_id = 1; //aId of the branch which has the parent branch clicked
        int maxAId = 1;

        //First check if current branch is a location branch
        selectQuery = "SELECT  " + COLUMN_PROJECT_ID + " FROM "
                + TABLE_MAP + " WHERE " + COLUMN_CAT_ID + " = 502 "
                +" AND "+COLUMN_PROJECT_ID+" = "+projId;

        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        //Reference tab exists
        {
            selectQuery = "SELECT  " + COLUMN_CHILD + " FROM "
                    + TABLE_MAP + " WHERE " + COLUMN_CHILD + " = 11 "
                    +" AND "+COLUMN_PROJECT_ID+" = "+projId;

            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

            }
            else{

                selectQuery = "SELECT MAX(M." + COLUMN_A_ID + ") FROM "
                        + TABLE_MAP + " M"
                        + " WHERE M." + COLUMN_PROJECT_ID + " = " + projId;  //+" AND E2."+COLUMN_LOCATION_ID+" = "+locationId;

                cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    maxAId = cursor.getInt(0);

                    maxAId = maxAId + 1;

                }

                selectQuery = "SELECT M." + COLUMN_A_ID + " FROM "
                        + TABLE_MAP + " M"
                        + " WHERE M." + COLUMN_PROJECT_ID + " = " + projId+" AND "+COLUMN_CAT_ID+" = 502";  //+" AND E2."+COLUMN_LOCATION_ID+" = "+locationId;

                cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    certId = cursor.getInt(0);

                }

                ContentValues values = new ContentValues();
                values.put(COLUMN_CAT_ID, CatID);
                values.put(COLUMN_PARENT, certId);
                values.put(COLUMN_PROJECT_ID, projId);
                values.put(COLUMN_LABEL, "list");
                values.put(COLUMN_LEVEL, 1);
                values.put(COLUMN_A_ID, maxAId);
                values.put(COLUMN_INSPECTION_ID, 0);
                values.put(COLUMN_CHILD,11);
                values.put(COLUMN_IMG1, "");
                values.put(COLUMN_NOTES, "");
                values.put(COLUMN_ITEM_STATUS, "m");
                db.insert(TABLE_MAP, null, values);


                ContentValues values1 = new ContentValues();
                SQLiteDatabase db_2 = this.getWritableDatabase();

                values1.put(COLUMN_PROJECT_ID, projId);
                values1.put(COLUMN_INSPECTION_ID, iId);
                values1.put(COLUMN_DATE_INSPECTED, dayTime(1));
                values1.put(COLUMN_A_ID, maxAId);
                values1.put(COLUMN_OVERVIEW, "");
                values1.put(COLUMN_RELEVANT_INFO, "");
                values1.put(COLUMN_SERVICE_LEVEL, "1");
                values1.put(COLUMN_SERVICED_BY, "");
                values1.put(COLUMN_REPORT_IMAGE, "0");
                values1.put(COLUMN_IMG1, "");
                values1.put(COLUMN_COM1, "");
                values1.put(COLUMN_IMG2, "");
                values1.put(COLUMN_COM2, "");
                values1.put(COLUMN_IMG3, "");
                values1.put(COLUMN_COM3, "");
                values1.put(COLUMN_IMG4, "");
                values1.put(COLUMN_COM4, "");
                values1.put(COLUMN_IMG5, "");
                values1.put(COLUMN_COM5, "");
                values1.put(COLUMN_IMG6, "");
                values1.put(COLUMN_COM6, "");
                values1.put(COLUMN_IMG7, "");
                values1.put(COLUMN_COM7, "");
                values1.put(COLUMN_ITEM_STATUS, "m");
                values1.put(COLUMN_NOTES, "");

                db_2.insert(TABLE_INSPECTION_ITEM, null, values1);
                db_2.close();


                result = 1;


            }

        }  //end if Reference does exist
        //if Reference tab doesn't exist
        else {

            selectQuery = "SELECT MAX(M." + COLUMN_A_ID + ") FROM "
                    + TABLE_MAP + " M"
                    + " WHERE M." + COLUMN_PROJECT_ID + " = " + projId;  //+" AND E2."+COLUMN_LOCATION_ID+" = "+locationId;

            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                maxAId = cursor.getInt(0);

                maxAId = maxAId + 1;

            }

            ContentValues values = new ContentValues();
            values.put(COLUMN_CAT_ID, CatID);
            values.put(COLUMN_PARENT, -1);
            values.put(COLUMN_PROJECT_ID, projId);
            values.put(COLUMN_LABEL, Label);
            values.put(COLUMN_LEVEL, Level);
            values.put(COLUMN_A_ID, maxAId);
            values.put(COLUMN_INSPECTION_ID, 0);
            values.put(COLUMN_CHILD,11);
            values.put(COLUMN_IMG1, "");
            values.put(COLUMN_NOTES, "");
            values.put(COLUMN_ITEM_STATUS, "m");
            db.insert(TABLE_MAP, null, values);




            ContentValues values2 = new ContentValues();
            values2.put(COLUMN_CAT_ID, CatID);
            values2.put(COLUMN_PARENT, maxAId);
            values2.put(COLUMN_PROJECT_ID, projId);
            values2.put(COLUMN_LABEL, "Information");
            values2.put(COLUMN_LEVEL, 1);
            values2.put(COLUMN_A_ID, maxAId+1);
            values2.put(COLUMN_INSPECTION_ID, 0);
            values2.put(COLUMN_CHILD,11);
            values2.put(COLUMN_IMG1, "");
            values2.put(COLUMN_NOTES, "");
            values2.put(COLUMN_ITEM_STATUS, "m");
            db.insert(TABLE_MAP, null, values2);

            ContentValues values1 = new ContentValues();
            SQLiteDatabase db_2 = this.getWritableDatabase();

            values1.put(COLUMN_PROJECT_ID, projId);
            values1.put(COLUMN_INSPECTION_ID, iId);
            values1.put(COLUMN_DATE_INSPECTED, dayTime(1));
            values1.put(COLUMN_A_ID, maxAId+1);
            values1.put(COLUMN_OVERVIEW, "");
            values1.put(COLUMN_RELEVANT_INFO, "");
            values1.put(COLUMN_SERVICE_LEVEL, "1");
            values1.put(COLUMN_SERVICED_BY, "");
            values1.put(COLUMN_REPORT_IMAGE, "0");
            values1.put(COLUMN_IMG1, "");
            values1.put(COLUMN_COM1, "");
            values1.put(COLUMN_IMG2, "");
            values1.put(COLUMN_COM2, "");
            values1.put(COLUMN_IMG3, "");
            values1.put(COLUMN_COM3, "");
            values1.put(COLUMN_IMG4, "");
            values1.put(COLUMN_COM4, "");
            values1.put(COLUMN_IMG5, "");
            values1.put(COLUMN_COM5, "");
            values1.put(COLUMN_IMG6, "");
            values1.put(COLUMN_COM6, "");
            values1.put(COLUMN_IMG7, "");
            values1.put(COLUMN_COM7, "");
            values1.put(COLUMN_ITEM_STATUS, "m");
            values1.put(COLUMN_NOTES, "");

            db_2.insert(TABLE_INSPECTION_ITEM, null, values1);
            db_2.close();

            result = 1;
        }


        db.close();

        return result;
    }


    //Check if a location contains any sublocations
    public int itemNum(String propId, int aId) {
        // Open a database for reading and writing

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;


        selectQuery = "SELECT * FROM "
                + TABLE_INSPECTION_ITEM + " I"
                + " WHERE I." + COLUMN_PROJECT_ID + " = " + propId + " AND I." + COLUMN_INSPECTION_ID + " = " + aId;

        cursor = db.rawQuery(selectQuery, null);


        //  int  maxvalues[] = new int[]{maxSublocation, maxAssetId};
        db.close();

        return cursor.getCount();

    }


    //Check if a location contains any sublocations
    public String getStatus(int iId, int projId) {
        // Open a database for reading and writing

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;

/*

        selectQuery = "SELECT I."+COLUMN_PROPERTY_ID+", I."+COLUMN_ASSET_ID
                +" FROM "+TABLE_A2D_INSPECTION_ITEM+" I"
                +" JOIN "+TABLE_ASSET_REGISTER+" A"
                +" ON I."+COLUMN_PROPERTY_ID+" = A."+COLUMN_PROPERTY_ID+" AND A."+COLUMN_ASSET_ID+" = I."+COLUMN_ASSET_ID
                +" WHERE I."+COLUMN_PROPERTY_ID+" = "+propId+" AND A."+COLUMN_SUB_LOCATION_ID+" = 2" ;

        cursor = db.rawQuery(selectQuery, null);

        int items = cursor.getCount();

        selectQuery = "SELECT I."+COLUMN_PROPERTY_ID+", I."+COLUMN_ASSET_ID
                +" FROM "+TABLE_A2D_INSPECTION_ITEM+" I"
                +" JOIN "+TABLE_ASSET_REGISTER+" A"
                +" ON I."+COLUMN_PROPERTY_ID+" = A."+COLUMN_PROPERTY_ID+" AND A."+COLUMN_ASSET_ID+" = I."+COLUMN_ASSET_ID
                +" WHERE I."+COLUMN_PROPERTY_ID+" = "+propId+" AND A."+COLUMN_SUB_LOCATION_ID+" = 2 AND I."+COLUMN_ITEM_STATUS+" = 'i'";

        cursor = db.rawQuery(selectQuery, null);

        int i_items = cursor.getCount();

        cursor.close();

        String result = "n";

        if ((items - i_items) == 0 ) result = "c";
        if ((items - i_items) > 0 && (items - i_items) < items  ) result = "p";



        return result;


 */

        return "n";

    }


    public HashMap<String, String> getInspection(int projId, int iId) {
        // Open a database for reading and writing

        HashMap<String, String> inspectionItem = new HashMap<String, String>();

        ArrayList<HashMap<String, String>> inspectionItemList;
        inspectionItemList = new ArrayList<HashMap<String, String>>();

        SQLiteDatabase database = this.getWritableDatabase();


        String selectQuery = "SELECT I." + COLUMN_INSPECTION_DATE + ", I." + COLUMN_INSPECTION_TYPE + ", I." + COLUMN_INSPECTOR
                + ", I." + COLUMN_INSPECTION_STATUS+ ", I." + COLUMN_DATE_TIME_START+ ", I." + COLUMN_DATE_TIME_FINISH
                + ", I."+ COLUMN_IMAGE+ ", I."+ COLUMN_NOTE+ ", I."+ COLUMN_NOTE_2+ ", I."+ COLUMN_LABEL+ ", P."+ COLUMN_ADDRESS_NUMBER
                + " FROM " + TABLE_INSPECTION + " I "
                + " JOIN " + TABLE_PROJECT_INFO + " P "
                + " ON I."+ COLUMN_PROJECT_ID + " =  P."+COLUMN_PROJECT_ID
                + " WHERE I." + COLUMN_PROJECT_ID + " = " + projId + " AND " + COLUMN_INSPECTION_ID + " = " + iId;


        //add additional fields: status,  notes, print flag
        Cursor cursor = database.rawQuery(selectQuery, null);

        // Move to the first row
        if (cursor.moveToFirst()) {
            do {
                inspectionItem = new HashMap<String, String>();
                inspectionItem.put(MyConfig.TAG_INSPECTION_DATE, cursor.getString(0));
                inspectionItem.put(MyConfig.TAG_INSPECTION_TYPE, cursor.getString(1));
                inspectionItem.put(MyConfig.TAG_INSPECTOR, cursor.getString(2));
                inspectionItem.put(MyConfig.TAG_INSPECTION_STATUS, cursor.getString(3));
                inspectionItem.put(MyConfig.TAG_START_DATE_TIME, cursor.getString(4));
                inspectionItem.put(MyConfig.TAG_END_DATE_TIME, cursor.getString(5));
                inspectionItem.put(MyConfig.TAG_IMAGE, cursor.getString(6));
                inspectionItem.put(MyConfig.TAG_NOTE, cursor.getString(7));
                inspectionItem.put(MyConfig.TAG_NOTE_2, cursor.getString(8));
                inspectionItem.put(MyConfig.TAG_LABEL, cursor.getString(9));
                inspectionItem.put(MyConfig.TAG_ADDRESS_NO, cursor.getString(10));


                inspectionItemList.add(inspectionItem);
            } while (cursor.moveToNext());
        }


        // return inspection data for propertyid, Jobid, inspection id
        database.close();
        return inspectionItem;


    }


    // Retrieve individual current inspection
    public HashMap<String, String> getInspectionItem(int projId, int iId, int aId) {
        // Open a database for reading and writing

        HashMap<String, String> inspectionItem = new HashMap<String, String>();

        ArrayList<HashMap<String, String>> inspectionItemList;
        inspectionItemList = new ArrayList<HashMap<String, String>>();

        SQLiteDatabase database = this.getWritableDatabase();


        String selectQuery = "SELECT I." + COLUMN_DATE_INSPECTED + ", I." + COLUMN_OVERVIEW + ", I." + COLUMN_RELEVANT_INFO + ", I." + COLUMN_IMG1 + ", I." + COLUMN_COM1
                + ", I." + COLUMN_IMG2 + ", I." + COLUMN_COM2 + ", I." + COLUMN_IMG3 + ", I." + COLUMN_COM3 + ", I." + COLUMN_IMG4 + ", I." + COLUMN_COM4
                + ", I." + COLUMN_IMG5 + ", I." + COLUMN_COM5 + ", I." + COLUMN_IMG6 + ", I." + COLUMN_COM6 + ", I." + COLUMN_IMG7 + ", I." + COLUMN_COM7
                + ", I." + COLUMN_ITEM_STATUS + ", I." + COLUMN_NOTES + ", I." + COLUMN_SERVICED_BY+ ", I." + COLUMN_REPORT_IMAGE
                + " FROM " + TABLE_INSPECTION_ITEM + " I "
                + " WHERE I." + COLUMN_PROJECT_ID + " = " + projId + " AND " + COLUMN_INSPECTION_ID + " = " + iId + " AND " + COLUMN_A_ID + " = " + aId;


        //add additional fields: status,  notes, print flag
        Cursor cursor = database.rawQuery(selectQuery, null);

        // Move to the first row
        if (cursor.moveToFirst()) {
            do {
                inspectionItem = new HashMap<String, String>();
                inspectionItem.put(MyConfig.TAG_DATE_INSPECTED, cursor.getString(0));  //(String.valueOf(cursor.getInt(0))));
                inspectionItem.put(MyConfig.TAG_OVERVIEW, cursor.getString(1));
                inspectionItem.put(MyConfig.TAG_RELEVANT_INFO, cursor.getString(2));
                inspectionItem.put(MyConfig.TAG_IMAGE1, cursor.getString(3));
                inspectionItem.put(MyConfig.TAG_COM1, cursor.getString(4));
                inspectionItem.put(MyConfig.TAG_IMAGE2, cursor.getString(5));
                inspectionItem.put(MyConfig.TAG_COM2, cursor.getString(6));
                inspectionItem.put(MyConfig.TAG_IMAGE3, cursor.getString(7));
                inspectionItem.put(MyConfig.TAG_COM3, cursor.getString(8));
                inspectionItem.put(MyConfig.TAG_IMAGE4, cursor.getString(9));
                inspectionItem.put(MyConfig.TAG_COM4, cursor.getString(10));
                inspectionItem.put(MyConfig.TAG_IMAGE5, cursor.getString(11));
                inspectionItem.put(MyConfig.TAG_COM5, cursor.getString(12));
                inspectionItem.put(MyConfig.TAG_IMAGE6, cursor.getString(13));
                inspectionItem.put(MyConfig.TAG_COM6, cursor.getString(14));
                inspectionItem.put(MyConfig.TAG_IMAGE7, cursor.getString(15));
                inspectionItem.put(MyConfig.TAG_COM7, cursor.getString(16));
                inspectionItem.put(MyConfig.TAG_ITEM_STATUS, cursor.getString(17));
                inspectionItem.put(MyConfig.TAG_NOTES, cursor.getString(18));
                inspectionItem.put(MyConfig.TAG_SERVICED_BY, cursor.getString(19));
                inspectionItem.put(MyConfig.TAG_REPORT_IMAGE, cursor.getString(20));
                inspectionItemList.add(inspectionItem);
            } while (cursor.moveToNext());
        }


        // return inspection data for propertyid, Jobid, inspection id
        database.close();
        return inspectionItem;


    }

    // Retrieve individual current inspection
    public HashMap<String, String> getActionItem(int projId, int aId, int iId) {
        // Open a database for reading and writing

        HashMap<String, String> actionItem = new HashMap<String, String>();

        ArrayList<HashMap<String, String>> actionItemList;
        actionItemList = new ArrayList<HashMap<String, String>>();

        SQLiteDatabase database = this.getWritableDatabase();


        String selectQuery = "SELECT A." + COLUMN_DATE_INSPECTED + ", A." + COLUMN_OVERVIEW + ", A." + COLUMN_RELEVANT_INFO + ", A." + COLUMN_IMG1 + ", A." + COLUMN_COM1

                + ", A." + COLUMN_ITEM_STATUS + ", A." + COLUMN_NOTES + ", A." + COLUMN_SERVICED_BY
                + " FROM " + TABLE_ACTION_ITEM + " A "
                + " WHERE A." + COLUMN_PROJECT_ID + " = " + projId + " AND A." + COLUMN_A_ID + " = " + aId + " AND A." + COLUMN_INSPECTION_ID + " = " + iId;


        //add additional fields: status,  notes, print flag
        Cursor cursor = database.rawQuery(selectQuery, null);

        // Move to the first row
        if (cursor.moveToFirst()) {
            do {
                actionItem = new HashMap<String, String>();
                actionItem.put(MyConfig.TAG_DATE_INSPECTED, (String.valueOf(cursor.getInt(0))));
                actionItem.put(MyConfig.TAG_OVERVIEW, cursor.getString(1));
                actionItem.put(MyConfig.TAG_RELEVANT_INFO, cursor.getString(2));
                actionItem.put(MyConfig.TAG_IMAGE1, cursor.getString(3));
                actionItem.put(MyConfig.TAG_COM1, cursor.getString(4));
                actionItem.put(MyConfig.TAG_ITEM_STATUS, cursor.getString(5));
                actionItem.put(MyConfig.TAG_NOTES, cursor.getString(6));
                actionItem.put(MyConfig.TAG_SERVICED_BY, cursor.getString(7));
                actionItemList.add(actionItem);
            } while (cursor.moveToNext());
        }


        // return inspection data for propertyid, Jobid, inspection id
        database.close();
        return actionItem;


    }


    public HashMap<String, String> getCert_Inspection(int projId, int iId) {
        // Open a database for reading and writing

        HashMap<String, String> certItem = new HashMap<String, String>();

        ArrayList<HashMap<String, String>> certItemList;
        certItemList = new ArrayList<HashMap<String, String>>();

        SQLiteDatabase database = this.getWritableDatabase();


        String selectQuery = "SELECT C." + COLUMN_DATE_TIME + ", C." + COLUMN_OVERVIEW + ", C." + COLUMN_PERMIT_NO
                + ", C." + COLUMN_PROJECT_ADDRESS + ", C." + COLUMN_STAGE + ", C." + COLUMN_NOTES
                + " FROM " + TABLE_CERTIFICATE_INSPECTION + " C "
                + " WHERE C." + COLUMN_PROJECT_ID + " = " + projId + " AND C." + COLUMN_INSPECTION_ID + " = " + iId;


        //add additional fields: status,  notes, print flag
        Cursor cursor = database.rawQuery(selectQuery, null);

        // Move to the first row
        if (cursor.moveToFirst()) {
            do {
                certItem = new HashMap<String, String>();
                certItem.put(MyConfig.TAG_DATE_TIME, cursor.getString(0));
                certItem.put(MyConfig.TAG_OVERVIEW, cursor.getString(1));
                certItem.put(MyConfig.TAG_PERMIT, cursor.getString(2));
                certItem.put(MyConfig.TAG_PROJECT_ADDRESS, cursor.getString(3));
                certItem.put(MyConfig.TAG_STAGE, cursor.getString(4));
                certItem.put(MyConfig.TAG_NOTES, cursor.getString(5));
                certItemList.add(certItem);
            } while (cursor.moveToNext());
        }


        // return inspection data for propertyid, Jobid, inspection id
        database.close();
        return certItem;


    }

    public HashMap<String, String> getReferenceItem(int projId, int aId) {
        // Open a database for reading and writing

        HashMap<String, String> inspectionItem = new HashMap<String, String>();

        ArrayList<HashMap<String, String>> inspectionItemList;
        inspectionItemList = new ArrayList<HashMap<String, String>>();

        SQLiteDatabase database = this.getWritableDatabase();


        String selectQuery = "SELECT I."  + COLUMN_IMG1 + ", I." + COLUMN_COM1
                + ", I." + COLUMN_IMG2 + ", I." + COLUMN_COM2 + ", I." + COLUMN_IMG3 + ", I." + COLUMN_COM3 + ", I." + COLUMN_IMG4 + ", I." + COLUMN_COM4
                + ", I." + COLUMN_IMG5 + ", I." + COLUMN_COM5 + ", I." + COLUMN_IMG6 + ", I." + COLUMN_COM6 + ", I." + COLUMN_IMG7 + ", I." + COLUMN_COM7
                + " FROM " + TABLE_INSPECTION_ITEM + " I "
                + " WHERE I." + COLUMN_PROJECT_ID + " = " + projId + " AND " + COLUMN_A_ID + " = " + aId;


        //add additional fields: status,  notes, print flag
        Cursor cursor = database.rawQuery(selectQuery, null);

        // Move to the first row
        if (cursor.moveToFirst()) {
            do {
                inspectionItem = new HashMap<String, String>();
                inspectionItem.put(MyConfig.TAG_IMAGE1, cursor.getString(0));
                inspectionItem.put(MyConfig.TAG_COM1, cursor.getString(1));
                inspectionItem.put(MyConfig.TAG_IMAGE2, cursor.getString(2));
                inspectionItem.put(MyConfig.TAG_COM2, cursor.getString(3));
                inspectionItem.put(MyConfig.TAG_IMAGE3, cursor.getString(4));
                inspectionItem.put(MyConfig.TAG_COM3, cursor.getString(5));
                inspectionItem.put(MyConfig.TAG_IMAGE4, cursor.getString(6));
                inspectionItem.put(MyConfig.TAG_COM4, cursor.getString(7));
                inspectionItem.put(MyConfig.TAG_IMAGE5, cursor.getString(8));
                inspectionItem.put(MyConfig.TAG_COM5, cursor.getString(9));
                inspectionItem.put(MyConfig.TAG_IMAGE6, cursor.getString(10));
                inspectionItem.put(MyConfig.TAG_COM6, cursor.getString(11));
                inspectionItem.put(MyConfig.TAG_IMAGE7, cursor.getString(12));
                inspectionItem.put(MyConfig.TAG_COM7, cursor.getString(13));
                inspectionItemList.add(inspectionItem);
            } while (cursor.moveToNext());
        }


        // return inspection data for propertyid, Jobid, inspection id
        database.close();
        return inspectionItem;


    }





    public HashMap<String, String> getSummary(int projId, int iId) {
        // Open a database for reading and writing

        HashMap<String, String> summaryItem = new HashMap<String, String>();

        ArrayList<HashMap<String, String>> summaryList;
        summaryList = new ArrayList<HashMap<String, String>>();

        SQLiteDatabase database = this.getWritableDatabase();


        String selectQuery = "SELECT S." + COLUMN_HEAD_A + ", S." + COLUMN_COM_A + ", S." + COLUMN_HEAD_B
                + ", S." + COLUMN_COM_B + ", S." + COLUMN_HEAD_C + ", S." + COLUMN_COM_C
                + " FROM " + TABLE_SUMMARY + " S "
                + " WHERE S." + COLUMN_PROJECT_ID + " = " + projId + " AND S." + COLUMN_INSPECTION_ID + " = " + iId;


        //add additional fields: status,  notes, print flag
        Cursor cursor = database.rawQuery(selectQuery, null);

        // Move to the first row
        if (cursor.moveToFirst()) {
            do {
                summaryItem = new HashMap<String, String>();
                summaryItem.put(MyConfig.TAG_HEAD_A, cursor.getString(0));
                summaryItem.put(MyConfig.TAG_COM_A, cursor.getString(1));
                summaryItem.put(MyConfig.TAG_HEAD_B, cursor.getString(2));
                summaryItem.put(MyConfig.TAG_COM_B, cursor.getString(3));
                summaryItem.put(MyConfig.TAG_HEAD_C, cursor.getString(4));
                summaryItem.put(MyConfig.TAG_COM_C, cursor.getString(5));
                summaryList.add(summaryItem);
            } while (cursor.moveToNext());
        }


        // return inspection data for propertyid, Jobid, inspection id
        database.close();
        return summaryItem;


    }


    public ArrayList<HashMap<String, String>> getAllProjects(int user_id, String status) {

        // ArrayList that contains every row in the database
        // and each row key / value stored in a HashMap

        ArrayList<HashMap<String, String>> propertyArrayList;

        propertyArrayList = new ArrayList<HashMap<String, String>>();

        String selectQuery = "SELECT P."
                + COLUMN_PROJECT_ID + ", P." + COLUMN_ADDRESS_NUMBER + ", P." + COLUMN_PROJECT_ADDRESS + ", P." + COLUMN_PROJECT_SUBURB
                + ", P." + COLUMN_INFO_A + ", P." + COLUMN_INFO_B + ", P." + COLUMN_INFO_C + ", P." + COLUMN_INFO_D + ", P." + COLUMN_INFO_E + ", P."
                + COLUMN_INFO_F + ", P." + COLUMN_INFO_G + ", P." + COLUMN_INFO_H + ", P." + COLUMN_INFO_I + ", P." + COLUMN_INFO_J + ", P."
                + COLUMN_PROJECT_PHOTO+ ", P." + COLUMN_PROJECT_NOTE
                + " FROM " + TABLE_PROJECT_INFO + " P "
                + " JOIN " + TABLE_INSPECTION + " I "
                + " ON P." + COLUMN_PROJECT_ID + " = I." + COLUMN_PROJECT_ID
                + " WHERE I."+ COLUMN_INSPECTOR + " = "+ user_id+" AND I."+ COLUMN_INSPECTION_STATUS+" = '"+ status+"'"
                + " ORDER BY P." + COLUMN_PROJECT_ID;


        // Open a database for reading and writing

        SQLiteDatabase database = this.getWritableDatabase();

        // Cursor provides read and write access for the
        // data returned from a database query

        // rawQuery executes the query and returns the result as a Cursor

        Cursor cursor = database.rawQuery(selectQuery, null);

        // Move to the first row

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> propertyMap = new HashMap<String, String>();

                // Store the key / value pairs in a HashMap
                // Access the Cursor data by index that is in the same order
                // as query

                propertyMap.put(MyConfig.TAG_PROJECT_ID, (String.valueOf(cursor.getInt(0))));
                propertyMap.put(MyConfig.TAG_ADDRESS_NO, cursor.getString(1));
                propertyMap.put(MyConfig.TAG_PROJECT_ADDRESS, cursor.getString(2));
                propertyMap.put(MyConfig.TAG_PROJECT_SUBURB, cursor.getString(3));
                propertyMap.put(MyConfig.TAG_INFO_A, cursor.getString(4));
                propertyMap.put(MyConfig.TAG_INFO_B, cursor.getString(5));
                propertyMap.put(MyConfig.TAG_INFO_C, cursor.getString(6));
                propertyMap.put(MyConfig.TAG_INFO_D, cursor.getString(7));
                propertyMap.put(MyConfig.TAG_INFO_E, cursor.getString(8));
                propertyMap.put(MyConfig.TAG_INFO_F, cursor.getString(9));
                propertyMap.put(MyConfig.TAG_INFO_G, cursor.getString(10));
                propertyMap.put(MyConfig.TAG_INFO_H, cursor.getString(11));
                propertyMap.put(MyConfig.TAG_INFO_I, cursor.getString(12));
                propertyMap.put(MyConfig.TAG_INFO_J, cursor.getString(13));
                propertyMap.put(MyConfig.TAG_PROJECT_PHOTO, cursor.getString(14));
                propertyMap.put(MyConfig.TAG_PROJECT_NOTE, cursor.getString(15));


                propertyArrayList.add(propertyMap);
            } while (cursor.moveToNext()); // Move Cursor to the next row
        }
        cursor.close();
        database.close();
        // return contact list
        return propertyArrayList;

    }

    public ArrayList<HashMap<String, String>> dwnLoadPropertyPhoto(int proj_id) {

        ArrayList<HashMap<String, String>> photoArrayList;
        photoArrayList = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase database = this.getWritableDatabase();

        String selectQuery = "SELECT E." + COLUMN_PROJECT_PHOTO
                + " FROM " + TABLE_PROJECT_INFO +" E "
                + " WHERE E." + COLUMN_PROJECT_ID + " = " +proj_id;

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> photoMap = new HashMap<String, String>();

                if (cursor.getString(0) != "")
                    photoMap.put(MyConfig.TAG_IMAGE, cursor.getString(0));

                photoArrayList.add(photoMap);
            } while (cursor.moveToNext()); // Move Cursor to the next row
        }
        cursor.close();

        database.close();
        // return contact list
        return photoArrayList;
    }


    public ArrayList<HashMap<String, String>> dwnLoadInspectionPhotos(int proj_id) {

        ArrayList<HashMap<String, String>> photoArrayList;
        photoArrayList = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase database = this.getWritableDatabase();

        String selectQuery = "SELECT E." + COLUMN_IMAGE
                + " FROM " + TABLE_INSPECTION +" E "
                + " WHERE E." + COLUMN_PROJECT_ID + " = " +proj_id;

        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> photoMap = new HashMap<String, String>();

                if (cursor.getString(0) != "")
                    photoMap.put(MyConfig.TAG_IMAGE, cursor.getString(0));

                photoArrayList.add(photoMap);
            } while (cursor.moveToNext()); // Move Cursor to the next row
        }
        cursor.close();

        database.close();
        // return contact list
        return photoArrayList;
    }

    public ArrayList<HashMap<String, String>> dwnLoadInspectionItemPhotos(int proj_id) {
        ArrayList<HashMap<String, String>> photoArrayList;
        photoArrayList = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase database = this.getWritableDatabase();

        String selectQuery = "SELECT I." + COLUMN_IMG1 + ", I." + COLUMN_IMG2 + ", I." + COLUMN_IMG3 + ", I." + COLUMN_IMG4 + ", I." + COLUMN_IMG5
                + " FROM " + TABLE_INSPECTION_ITEM +" I "
                + " WHERE  I."+COLUMN_PROJECT_ID+" = "+proj_id;

        Cursor cursor = database.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> photoMap = new HashMap<String, String>();
                if (cursor.getString(0) != "")
                    photoMap.put(MyConfig.TAG_IMAGE1, cursor.getString(0));
                if (cursor.getString(1) != "")
                    photoMap.put(MyConfig.TAG_IMAGE2, cursor.getString(1));
                if (cursor.getString(2) != "")
                    photoMap.put(MyConfig.TAG_IMAGE3, cursor.getString(2));
                if (cursor.getString(3) != "")
                    photoMap.put(MyConfig.TAG_IMAGE4, cursor.getString(3));
                if (cursor.getString(4) != "")
                    photoMap.put(MyConfig.TAG_IMAGE5, cursor.getString(4));

                photoArrayList.add(photoMap);
            } while (cursor.moveToNext()); // Move Cursor to the next row
        }
        cursor.close();
        database.close();
        // return contact list
        return photoArrayList;
    }

    public ArrayList<HashMap<String, String>> dwnLoadActionPhotos(int proj_id) {
        ArrayList<HashMap<String, String>> photoArrayList;
        photoArrayList = new ArrayList<HashMap<String, String>>();
        SQLiteDatabase database = this.getWritableDatabase();

        String selectQuery = "SELECT A." + COLUMN_IMG1

                + " FROM " + TABLE_ACTION_ITEM +" A "
                + " WHERE A." + COLUMN_PROJECT_ID + " = "+proj_id;


        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> photoMap = new HashMap<String, String>();
                if (cursor.getString(0) != "")
                    photoMap.put(MyConfig.TAG_IMAGE1, cursor.getString(0));

                photoArrayList.add(photoMap);
            } while (cursor.moveToNext()); // Move Cursor to the next row

        }

        cursor.close();
        database.close();
        // return contact list
        return photoArrayList;
    }


    public ArrayList<HashMap<String, String>> getInspectionPhotos(int user_id) {

        // ArrayList that contains every row in the database
        // and each row key / value stored in a HashMap

        ArrayList<HashMap<String, String>> photoArrayList;

        photoArrayList = new ArrayList<HashMap<String, String>>();


        String selectQuery = "SELECT E." + COLUMN_IMAGE
                + " FROM " + TABLE_INSPECTION +" E "
                + " WHERE E." + COLUMN_INSPECTOR+ " = " +user_id;



        // Open a database for reading and writing

        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);


        // Move to the first row

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> photoMap = new HashMap<String, String>();

                if (cursor.getString(0) != "")
                    photoMap.put(MyConfig.TAG_IMAGE, cursor.getString(0));

                photoArrayList.add(photoMap);
            } while (cursor.moveToNext()); // Move Cursor to the next row
        }
        cursor.close();

        database.close();
        // return contact list
        return photoArrayList;
    }


    public ArrayList<HashMap<String, String>> getInspectedItemPhotos(int user_id) {

        // ArrayList that contains every row in the database
        // and each row key / value stored in a HashMap

        ArrayList<HashMap<String, String>> photoArrayList;

        photoArrayList = new ArrayList<HashMap<String, String>>();

        String projID;

        SQLiteDatabase database = this.getWritableDatabase();
        String selectQueryI = "SELECT I."+COLUMN_PROJECT_ID
                + " FROM " + TABLE_INSPECTION + " I "
                + " WHERE I."+COLUMN_INSPECTOR+" = "+user_id+" AND "+COLUMN_INSPECTION_STATUS+ " = 'm'"
                + " GROUP BY I."+ COLUMN_PROJECT_ID;

        Cursor cursorI = database.rawQuery(selectQueryI, null);
        if (cursorI.moveToFirst()) {
            do {
                projID = cursorI.getString(0);


        String selectQuery = "SELECT I." + COLUMN_IMG1 + ", I." + COLUMN_IMG2 + ", I." + COLUMN_IMG3 + ", I." + COLUMN_IMG4 + ", I." + COLUMN_IMG5
                + " FROM " + TABLE_INSPECTION_ITEM +" I "
                + " WHERE  I."+COLUMN_PROJECT_ID+" = "+projID+" AND I."+ COLUMN_ITEM_STATUS+" = 'm'";



        // Open a database for reading and writing



        Cursor cursor = database.rawQuery(selectQuery, null);


        // Move to the first row

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> photoMap = new HashMap<String, String>();
                if (cursor.getString(0) != "")
                    photoMap.put(MyConfig.TAG_IMAGE1, cursor.getString(0));
                if (cursor.getString(1) != "")
                    photoMap.put(MyConfig.TAG_IMAGE2, cursor.getString(1));
                if (cursor.getString(2) != "")
                    photoMap.put(MyConfig.TAG_IMAGE3, cursor.getString(2));
                if (cursor.getString(3) != "")
                    photoMap.put(MyConfig.TAG_IMAGE4, cursor.getString(3));
                if (cursor.getString(4) != "")
                    photoMap.put(MyConfig.TAG_IMAGE5, cursor.getString(4));

                photoArrayList.add(photoMap);
            } while (cursor.moveToNext()); // Move Cursor to the next row
        }
        cursor.close();
            } while (cursorI.moveToNext()); // Move Cursor to the next row
        }
        cursorI.close();

        database.close();
        // return contact list
        return photoArrayList;
    }






    public ArrayList<HashMap<String, String>> getActionPhotos(int user_id) {

        // ArrayList that contains every row in the database
        // and each row key / value stored in a HashMap

        ArrayList<HashMap<String, String>> photoArrayList;

        photoArrayList = new ArrayList<HashMap<String, String>>();

        String projID;

        SQLiteDatabase database = this.getWritableDatabase();
        String selectQueryI = "SELECT I."+COLUMN_PROJECT_ID
                + " FROM " + TABLE_INSPECTION + " I "
                + " WHERE I."+COLUMN_INSPECTOR+" = "+user_id
                + " GROUP BY I."+ COLUMN_PROJECT_ID;

        Cursor cursorI = database.rawQuery(selectQueryI, null);
        if (cursorI.moveToFirst()) {
            do {
                projID = cursorI.getString(0);

        String selectQuery = "SELECT A." + COLUMN_IMG1

                + " FROM " + TABLE_ACTION_ITEM +" A "
               + " WHERE A." + COLUMN_PROJECT_ID + " = "+projID+" AND A."+COLUMN_ITEM_STATUS+" = 'm'";


        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> photoMap = new HashMap<String, String>();
                if (cursor.getString(0) != "")
                    photoMap.put(MyConfig.TAG_IMAGE1, cursor.getString(0));

                photoArrayList.add(photoMap);
            } while (cursor.moveToNext()); // Move Cursor to the next row

        }
        cursor.close();
            } while (cursorI.moveToNext()); // Move Cursor to the next row
        }
        cursorI.close();

        database.close();
        // return contact list
        return photoArrayList;
    }

    //Select inspection items for syncing to server
    public ArrayList<HashMap<String, String>> getInspections(int user_id) {
        HashMap<String, String> inspectionsMap = new HashMap<String, String>();
        ArrayList<HashMap<String, String>> inspectionArrayList = new ArrayList<>();


        SQLiteDatabase dtabase = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM "+ TABLE_INSPECTION
                + " WHERE "+ COLUMN_INSPECTION_STATUS +" = 'm' AND "
                +  COLUMN_INSPECTOR+" = "+user_id //+" AND "+ COLUMN_PARENT+" > 0"
                +" ORDER BY " + COLUMN_PROJECT_ID;
        Cursor cursor = dtabase.rawQuery(selectQuery, null);
        // Move to the first row
        if (cursor.moveToFirst()) {
            do {
                inspectionsMap = new HashMap<String, String>();
                inspectionsMap.put(MyConfig.TAG_INSPECTION_ID, (String.valueOf(cursor.getInt(0))));
                inspectionsMap.put(MyConfig.TAG_INSPECTION_TYPE, cursor.getString(1));
                inspectionsMap.put(MyConfig.TAG_INSPECTION_STATUS, cursor.getString(2));
                inspectionsMap.put(MyConfig.TAG_PROJECT_ID, (String.valueOf(cursor.getInt(3))));
                inspectionsMap.put(MyConfig.TAG_INSPECTION_DATE, cursor.getString(4));
                inspectionsMap.put(MyConfig.TAG_INSPECTOR, cursor.getString(5));
                inspectionsMap.put(MyConfig.TAG_START_DATE_TIME, cursor.getString(6));
                inspectionsMap.put(MyConfig.TAG_END_DATE_TIME, cursor.getString(7));
                inspectionsMap.put(MyConfig.TAG_LABEL, cursor.getString(8));
                inspectionsMap.put(MyConfig.TAG_LEVEL, (String.valueOf(cursor.getInt(9))));
                inspectionsMap.put(MyConfig.TAG_PARENT, (String.valueOf(cursor.getInt(10))));
                inspectionsMap.put(MyConfig.TAG_P_ID, (String.valueOf(cursor.getInt(11))));
                inspectionsMap.put(MyConfig.TAG_IMAGE, cursor.getString(12));
                inspectionsMap.put(MyConfig.TAG_NOTE, cursor.getString(13));
                inspectionsMap.put(MyConfig.TAG_NOTE_2, cursor.getString(14));
                inspectionArrayList.add(inspectionsMap);

            } while (cursor.moveToNext());
        }

        dtabase.close();
        return inspectionArrayList;


    }


    public ArrayList<HashMap<String, String>> getSiteMap() {
        HashMap<String, String> SiteMap = new HashMap<String, String>();
        ArrayList<HashMap<String, String>> AssArrayList = new ArrayList<>();


        SQLiteDatabase dtabase = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_MAP +" WHERE "+COLUMN_ITEM_STATUS +" = 'm' ORDER BY " + MyConfig.TAG_PROJECT_ID;  //

        Cursor cursor = dtabase.rawQuery(selectQuery, null);

        // Move to the first row
        if (cursor.moveToFirst()) {
            do {
                SiteMap = new HashMap<String, String>();
                SiteMap.put(MyConfig.TAG_PROJECT_ID, (String.valueOf(cursor.getInt(0))));
                SiteMap.put(MyConfig.TAG_CAT_ID, (String.valueOf(cursor.getInt(1))));
                SiteMap.put(MyConfig.TAG_LEVEL, (String.valueOf(cursor.getInt(2))));
                SiteMap.put(MyConfig.TAG_PARENT, (String.valueOf(cursor.getInt(3))));
                SiteMap.put(MyConfig.TAG_LABEL, cursor.getString(4));
                SiteMap.put(MyConfig.TAG_CHILD, (String.valueOf(cursor.getInt(5))));
                SiteMap.put(MyConfig.TAG_A_ID, (String.valueOf(cursor.getInt(6))));
                SiteMap.put(MyConfig.TAG_INSPECTION_ID, (String.valueOf(cursor.getInt(7))));
                SiteMap.put(MyConfig.TAG_IMAGE1, cursor.getString(8));
                SiteMap.put(MyConfig.TAG_NOTES, cursor.getString(9));
                AssArrayList.add(SiteMap);
            } while (cursor.moveToNext());
        }

        dtabase.close();
        return AssArrayList;

    }

    //Select inspection items for syncing to server
    public ArrayList<HashMap<String, String>> getInspectedItems_(int user_id) {
        HashMap<String, String> inspectionItem;
        ArrayList<HashMap<String, String>> inspectionItemsList;

        inspectionItemsList = new ArrayList<HashMap<String, String>>();

      //  String projID;
        SQLiteDatabase dtabase = this.getReadableDatabase();

        String selectQueryI = "SELECT I."+COLUMN_PROJECT_ID
                + " FROM " + TABLE_INSPECTION + " I "
                + " WHERE I."+COLUMN_INSPECTOR+" = "+user_id +" AND I."+COLUMN_INSPECTION_STATUS+" = 'm' AND I."+COLUMN_PARENT+" > 0"
                + " GROUP BY I."+ COLUMN_PROJECT_ID;

        Cursor cursorI = dtabase.rawQuery(selectQueryI, null);
        if (cursorI.moveToFirst()) {
            do {
                String projID = cursorI.getString(0);


                String selectQuery = "SELECT I.*"
                        + " FROM " + TABLE_INSPECTION_ITEM + " I "
                        //   + " JOIN " + TABLE_INSPECTION+" E ON I."+COLUMN_PROJECT_ID+" = E."+COLUMN_PROJECT_ID
                        //    + " WHERE E."+COLUMN_INSPECTOR+" = "+user_id +" AND E."+COLUMN_INSPECTION_STATUS+" = 'm'"
                        + " WHERE I." + COLUMN_PROJECT_ID + " = " + projID+" AND I."+COLUMN_ITEM_STATUS+" = 'm'"
                        + " ORDER BY I." + COLUMN_PROJECT_ID;


                //add additional fields: status,  notes, print flag
                Cursor cursor = dtabase.rawQuery(selectQuery, null);

                // Move to the first row
                if (cursor.moveToFirst()) {
                    do {
                        inspectionItem = new HashMap<String, String>();
                        inspectionItem.put(MyConfig.TAG_INSPECTION_ID, cursor.getString(0));
                        inspectionItem.put(MyConfig.TAG_PROJECT_ID, cursor.getString(1));
                        inspectionItem.put(MyConfig.TAG_A_ID, cursor.getString(2));
                        inspectionItem.put(MyConfig.TAG_DATE_INSPECTED, (String.valueOf(cursor.getInt(3))));
                        inspectionItem.put(MyConfig.TAG_OVERVIEW, cursor.getString(4));
                        inspectionItem.put(MyConfig.TAG_RELEVANT_INFO, cursor.getString(5));
                        inspectionItem.put(MyConfig.TAG_SERVICED_BY, cursor.getString(6));
                        inspectionItem.put(MyConfig.TAG_SERVICE_LEVEL, cursor.getString(7));
                        inspectionItem.put(MyConfig.TAG_REPORT_IMAGE, cursor.getString(8));
                        inspectionItem.put(MyConfig.TAG_IMAGE1, cursor.getString(9));
                        inspectionItem.put(MyConfig.TAG_COM1, cursor.getString(10));
                        inspectionItem.put(MyConfig.TAG_IMAGE2, cursor.getString(11));
                        inspectionItem.put(MyConfig.TAG_COM2, cursor.getString(12));
                        inspectionItem.put(MyConfig.TAG_IMAGE3, cursor.getString(13));
                        inspectionItem.put(MyConfig.TAG_COM3, cursor.getString(14));
                        inspectionItem.put(MyConfig.TAG_IMAGE4, cursor.getString(15));
                        inspectionItem.put(MyConfig.TAG_COM4, cursor.getString(16));
                        inspectionItem.put(MyConfig.TAG_IMAGE5, cursor.getString(17));
                        inspectionItem.put(MyConfig.TAG_COM5, cursor.getString(18));
                        inspectionItem.put(MyConfig.TAG_IMAGE6, cursor.getString(19));
                        inspectionItem.put(MyConfig.TAG_COM6, cursor.getString(20));
                        inspectionItem.put(MyConfig.TAG_IMAGE7, cursor.getString(21));
                        inspectionItem.put(MyConfig.TAG_COM7, cursor.getString(22));
                        inspectionItem.put(MyConfig.TAG_ITEM_STATUS, cursor.getString(23));
                        inspectionItem.put(MyConfig.TAG_NOTES, cursor.getString(24));

                        inspectionItemsList.add(inspectionItem);
                    }
                    while (cursor.moveToNext());
                }
            }
                while (cursorI.moveToNext());
        }

        dtabase.close();

        return inspectionItemsList;

    }

    //Get a list of sublocations to populate the sub location spinner
    public int getSubItemMap(int projId, int aId, int user_id) {

        HashMap<String, String> subItemMap;
        ArrayList<HashMap<String, String>> subItemArrayList;

        subItemArrayList = new ArrayList<HashMap<String, String>>();

        SQLiteDatabase dtabase = this.getReadableDatabase();

        String selectQuery = "SELECT M." + COLUMN_LEVEL//+", "+COLUMN_LOCATION_DESCRIPTION
                + " FROM " + TABLE_MAP+ " M "
                + " JOIN " + TABLE_INSPECTION+" E ON E."+COLUMN_PROJECT_ID+" = "+"M."+COLUMN_PROJECT_ID
                + " WHERE E."+COLUMN_INSPECTOR+" = "+user_id+" AND M."+ COLUMN_PROJECT_ID + " = " + projId + " AND M." + COLUMN_A_ID + " = " + aId
                + " ORDER BY I."+ COLUMN_PROJECT_ID;


        //add additional fields: status,  notes, print flag
        Cursor cursor = dtabase.rawQuery(selectQuery, null);

        int Level = 0;
        if (cursor.moveToFirst()) {
            Level = cursor.getInt(0);
        }
        // Move to the first row
        cursor.close();

        dtabase.close();

        SQLiteDatabase db = this.getReadableDatabase();


        String selectQuery1 = "SELECT " + COLUMN_LEVEL//+", "+COLUMN_LOCATION_DESCRIPTION
                + " FROM " + TABLE_MAP
                + " WHERE " + COLUMN_PROJECT_ID + " = " + projId + " AND " + COLUMN_LEVEL + " = " + Level;


        cursor = db.rawQuery(selectQuery1, null);

        int count = cursor.getCount();


        db.close();
        return count;

    }


    public HashMap<String, String> getProjectInfo(String projId) {


        HashMap<String, String> ProjectInfo = new HashMap<>();
        ArrayList<HashMap<String, String>> ProjectInfoArrayList;
        ProjectInfoArrayList = new ArrayList<HashMap<String, String>>();



        SQLiteDatabase dtabase = this.getReadableDatabase();

        String selectQuery = "SELECT "+COLUMN_ADDRESS_NUMBER+", " +  COLUMN_PROJECT_ADDRESS + ", " + COLUMN_PROJECT_SUBURB + ", " + COLUMN_INFO_A
                + ", " + COLUMN_INFO_B + ", " + COLUMN_INFO_C + ", " + COLUMN_INFO_D + ", "+COLUMN_PROJECT_PHOTO+ ", "
                + COLUMN_INFO_E+", "+ COLUMN_INFO_F+", "+ COLUMN_INFO_G+", "+COLUMN_INFO_H + ", "+COLUMN_INFO_I+", "+COLUMN_INFO_J+", "+COLUMN_PROJECT_NOTE

                + " FROM " + TABLE_PROJECT_INFO

                + " WHERE " + COLUMN_PROJECT_ID + " = "+ projId;

        //add additional fields: status,  notes, print flag
        Cursor cursor = dtabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ProjectInfo = new HashMap<String, String>();

                ProjectInfo.put(MyConfig.TAG_ADDRESS_NO, cursor.getString(0));
                ProjectInfo.put(MyConfig.TAG_PROJECT_ADDRESS, cursor.getString(1));
                ProjectInfo.put(MyConfig.TAG_PROJECT_SUBURB, cursor.getString(2));
                ProjectInfo.put(MyConfig.TAG_INFO_A, cursor.getString(3));
                ProjectInfo.put(MyConfig.TAG_INFO_B, cursor.getString(4));
                ProjectInfo.put(MyConfig.TAG_INFO_C, cursor.getString(5));
                ProjectInfo.put(MyConfig.TAG_INFO_D, (String.valueOf(cursor.getInt(6))));
                ProjectInfo.put(MyConfig.TAG_PROJECT_PHOTO, cursor.getString(7));
                ProjectInfo.put(MyConfig.TAG_INFO_E, cursor.getString(8));
                ProjectInfo.put(MyConfig.TAG_INFO_F, cursor.getString(9));
                ProjectInfo.put(MyConfig.TAG_INFO_G, cursor.getString(10));
                ProjectInfo.put(MyConfig.TAG_INFO_H, cursor.getString(11));
                ProjectInfo.put(MyConfig.TAG_INFO_I, cursor.getString(12));
                ProjectInfo.put(MyConfig.TAG_INFO_J, cursor.getString(13));
                ProjectInfo.put(MyConfig.TAG_PROJECT_NOTE, cursor.getString(14));

                ProjectInfoArrayList.add(ProjectInfo);
            } while (cursor.moveToNext());
        }

        dtabase.close();

        return ProjectInfo;

    }


    public ArrayList<HashMap<String, String>> getFolders(int user_id, int folder_id) {


        HashMap<String, String> ProjectMap;
        ArrayList<HashMap<String, String>> ProjectMapArrayList;

        ProjectMapArrayList = new ArrayList<HashMap<String, String>>();


        SQLiteDatabase dtabase = this.getReadableDatabase();

    /*    String selectQuery = "SELECT P."+COLUMN_PROJECT_ADDRESS+", P." +  COLUMN_PROJECT_ID + ", P." + COLUMN_PROJECT_PHOTO + ", I." + COLUMN_INSPECTION_ID
                + ", I." + COLUMN_LABEL + ", I." + COLUMN_LEVEL + ", I." + COLUMN_PARENT + " ,I."+COLUMN_IMAGE+ ", I." + COLUMN_NOTE+", I."+COLUMN_P_ID

                + " FROM " + TABLE_PROJECT_INFO + " P"
                + " JOIN " + TABLE_INSPECTION + " I"
                + " ON P." + COLUMN_PROJECT_ID + " = I."+ COLUMN_PROJECT_ID
                + " WHERE I."+COLUMN_INSPECTOR+" = "+user_id;

     */


        String selectQuery = "SELECT M."+COLUMN_PROJECT_ID+", M." +  COLUMN_CAT_ID + ", M." + COLUMN_LEVEL + ", M."+ COLUMN_PARENT+", M." + COLUMN_LABEL
                + ", M." + COLUMN_CHILD + ", M." + COLUMN_A_ID +", I." + COLUMN_INSPECTION_ID + " , M."+COLUMN_IMG1+ ", M." + COLUMN_NOTES

                + " FROM " + TABLE_MAP + " M "
                + " JOIN " + TABLE_PROJECT_INFO + " P "
                + " JOIN " + TABLE_INSPECTION + " I "
                + " ON P." + COLUMN_PROJECT_ID + " = M."+ COLUMN_PROJECT_ID
                + " AND P."+ COLUMN_PROJECT_ID + " = I."+ COLUMN_PROJECT_ID
                + " WHERE I."+COLUMN_INSPECTOR+" = "+user_id
                + " AND I."+ COLUMN_INSPECTION_ID+ " = '0' "
                + " AND M."+ COLUMN_INSPECTION_ID+ " = '0' "
                + " AND M."+ COLUMN_CAT_ID+ " < '9' "
                + " AND (M."+ COLUMN_PROJECT_ID+ " =  "+folder_id+ " OR M."+COLUMN_PARENT+ " = '-1')";

        //add additional fields: status,  notes, print flag
        Cursor cursor = dtabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ProjectMap = new HashMap<String, String>();

                ProjectMap.put(MyConfig.TAG_PROJECT_ID, (String.valueOf(cursor.getInt(0))));
                ProjectMap.put(MyConfig.TAG_CAT_ID, (String.valueOf(cursor.getInt(1))));
                ProjectMap.put(MyConfig.TAG_LEVEL, (String.valueOf(cursor.getInt(2))));
                ProjectMap.put(MyConfig.TAG_PARENT, (String.valueOf(cursor.getInt(3))));
                ProjectMap.put(MyConfig.TAG_LABEL, cursor.getString(4));
                ProjectMap.put(MyConfig.TAG_CHILD, (String.valueOf(cursor.getInt(5))));
                ProjectMap.put(MyConfig.TAG_A_ID, (String.valueOf(cursor.getInt(6))));
                ProjectMap.put(MyConfig.TAG_INSPECTION_ID, (String.valueOf(cursor.getInt(7))));
                ProjectMap.put(MyConfig.TAG_IMAGE1, cursor.getString(8));
                ProjectMap.put(MyConfig.TAG_NOTES, cursor.getString(9));

                ProjectMapArrayList.add(ProjectMap);
            } while (cursor.moveToNext());
        }

        dtabase.close();

        return ProjectMapArrayList;



    }

    //Get a list of sublocations to populate the sub location spinner
    public ArrayList<HashMap<String, String>> getProjects(int user_id, int folder_id) {


        HashMap<String, String> ProjectMap;
        ArrayList<HashMap<String, String>> ProjectMapArrayList;

        ProjectMapArrayList = new ArrayList<HashMap<String, String>>();


        SQLiteDatabase dtabase = this.getReadableDatabase();

        String selectQuery = "SELECT P."+COLUMN_PROJECT_ADDRESS+", P." +  COLUMN_PROJECT_ID + ", P." + COLUMN_PROJECT_PHOTO + ", I." + COLUMN_INSPECTION_ID
                + ", I." + COLUMN_LABEL + ", I." + COLUMN_LEVEL + ", I." + COLUMN_PARENT + " ,I."+COLUMN_IMAGE+ ", I." + COLUMN_NOTE+", I."+COLUMN_P_ID

                + " FROM " + TABLE_PROJECT_INFO + " P"
                + " JOIN " + TABLE_INSPECTION + " I"
                + " ON P." + COLUMN_PROJECT_ID + " = I."+ COLUMN_PROJECT_ID
                + " WHERE I."+COLUMN_INSPECTOR+" = "+user_id+ " AND I."+COLUMN_PROJECT_ID+ " = " + folder_id;


/*

        String selectQuery = "SELECT M."+COLUMN_LABEL+", M." +  COLUMN_PROJECT_ID + ", P." + COLUMN_PROJECT_PHOTO + ", M." + COLUMN_LEVEL
                + ", M." + COLUMN_LABEL + ", M." + COLUMN_LEVEL + ", M." + COLUMN_PARENT + " , M."+COLUMN_IMG1+ ", M." + COLUMN_NOTES+", M."+COLUMN_A_ID

                + " FROM " + TABLE_MAP + " M "
                + " JOIN " + TABLE_PROJECT_INFO + " P "
                + " JOIN " + TABLE_INSPECTION + " I "
                + " ON P." + COLUMN_PROJECT_ID + " = M."+ COLUMN_PROJECT_ID
                + " AND P."+ COLUMN_PROJECT_ID + " = I."+ COLUMN_PROJECT_ID
                + " WHERE I."+COLUMN_INSPECTOR+" = "+user_id
                + " AND I."+ COLUMN_INSPECTION_ID+ " = '0' "
                + " AND M."+ COLUMN_INSPECTION_ID+ " = '0' "
                + " AND M."+ COLUMN_CAT_ID+ " < '9' "
                + " AND (M."+ COLUMN_PROJECT_ID+ " =  "+folder_id+ " OR M."+COLUMN_PARENT+ " = '-1')";

 */

        //add additional fields: status,  notes, print flag
        Cursor cursor = dtabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ProjectMap = new HashMap<String, String>();

                ProjectMap.put(MyConfig.TAG_PROJECT_ADDRESS, cursor.getString(0));
                ProjectMap.put(MyConfig.TAG_PROJECT_ID, (String.valueOf(cursor.getInt(1))));
                ProjectMap.put(MyConfig.TAG_PROJECT_PHOTO, cursor.getString(2));
                ProjectMap.put(MyConfig.TAG_INSPECTION_ID, (String.valueOf(cursor.getInt(3))));
                ProjectMap.put(MyConfig.TAG_LABEL, cursor.getString(4));
                ProjectMap.put(MyConfig.TAG_LEVEL, (String.valueOf(cursor.getInt(5))));
                ProjectMap.put(MyConfig.TAG_PARENT, (String.valueOf(cursor.getInt(6))));
                ProjectMap.put(MyConfig.TAG_IMAGE, cursor.getString(7));
                ProjectMap.put(MyConfig.TAG_NOTE, cursor.getString(8));
                ProjectMap.put(MyConfig.TAG_P_ID, (String.valueOf(cursor.getInt(9))));

                ProjectMapArrayList.add(ProjectMap);
            } while (cursor.moveToNext());
        }

        dtabase.close();

        return ProjectMapArrayList;



    }



    //Get a list of sublocations to populate the sub location spinner
    public ArrayList<HashMap<String, String>> getFolderMap(int projID, int iID, int Child) {


        HashMap<String, String> SiteMap;
        ArrayList<HashMap<String, String>> SiteMapArrayList;

        SiteMapArrayList = new ArrayList<HashMap<String, String>>();


        SQLiteDatabase dtabase = this.getReadableDatabase();
/*
        String selectQuery = "SELECT M."+ COLUMN_PROJECT_ID+ ", M." + COLUMN_CAT_ID + ", M." + COLUMN_LEVEL + ", M." + COLUMN_PARENT + ", M." + COLUMN_LABEL
                + ", M." + COLUMN_CHILD + ", M." + COLUMN_A_ID + ", M."+ COLUMN_INSPECTION_ID + ", M." + COLUMN_IMG1 + ", M." + COLUMN_NOTES//CASE WHEN A."+COLUMN_SUB_LOCATION_ID+" = 0 THEN 0 ELSE 1 END AS 'LEVEL'"
                + " FROM " + TABLE_MAP + " M"
                + " WHERE M." + COLUMN_PROJECT_ID + " = " + projID+" AND (M."+COLUMN_INSPECTION_ID+" = "+ iID+" OR M."+COLUMN_INSPECTION_ID+" = "+ 0+" )"
                + " AND "+ COLUMN_CHILD + " < " + Child + " ORDER BY M." + COLUMN_CAT_ID;
        //add additional fields: status,  notes, print flag

 */

        String selectQuery = "SELECT P."+COLUMN_PROJECT_ID+", I." +  COLUMN_LEVEL + ", I." + COLUMN_LEVEL + ", I." + COLUMN_PARENT
                + ", I." + COLUMN_LABEL + ", I." + COLUMN_LEVEL + ", I." + COLUMN_P_ID + " ,I."+COLUMN_INSPECTION_ID+ ", I." + COLUMN_IMAGE+", I."+COLUMN_NOTE

                + " FROM " + TABLE_PROJECT_INFO + " P"
                + " JOIN " + TABLE_INSPECTION + " I"
                + " ON P." + COLUMN_PROJECT_ID + " = I."+ COLUMN_PROJECT_ID
                + " WHERE I."+COLUMN_PROJECT_ID+" = "+projID;



        Cursor cursor = dtabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SiteMap = new HashMap<String, String>();
                    SiteMap.put(MyConfig.TAG_PROJECT_ID, (String.valueOf(cursor.getInt(0))));
                    SiteMap.put(MyConfig.TAG_LEVEL, (String.valueOf(cursor.getInt(1))));
                    SiteMap.put(MyConfig.TAG_LEVEL, (String.valueOf(cursor.getInt(2))));
                    SiteMap.put(MyConfig.TAG_PARENT, (String.valueOf(cursor.getInt(3))));
                    SiteMap.put(MyConfig.TAG_LABEL, cursor.getString(4));
                    SiteMap.put(MyConfig.TAG_LEVEL, (String.valueOf(cursor.getInt(5))));
                    SiteMap.put(MyConfig.TAG_P_ID, (String.valueOf(cursor.getInt(6))));
                    SiteMap.put(MyConfig.TAG_INSPECTION_ID, (String.valueOf(cursor.getInt(7))));
                    SiteMap.put(MyConfig.TAG_IMAGE, cursor.getString(8));
                    SiteMap.put(MyConfig.TAG_NOTE, cursor.getString(9));


                SiteMapArrayList.add(SiteMap);

            } while (cursor.moveToNext());
        }

        dtabase.close();

        return SiteMapArrayList;

    }


    public ArrayList<HashMap<String, String>> getMap(int projID, int iID, int Child) {


        HashMap<String, String> SiteMap;
        ArrayList<HashMap<String, String>> SiteMapArrayList;

        SiteMapArrayList = new ArrayList<HashMap<String, String>>();


        SQLiteDatabase dtabase = this.getReadableDatabase();

        String selectQuery = "SELECT M."+ COLUMN_PROJECT_ID+ ", M." + COLUMN_CAT_ID + ", M." + COLUMN_LEVEL + ", M." + COLUMN_PARENT + ", M." + COLUMN_LABEL
                + ", M." + COLUMN_CHILD + ", M." + COLUMN_A_ID + ", M."+ COLUMN_INSPECTION_ID + ", M." + COLUMN_IMG1 + ", M." + COLUMN_NOTES//CASE WHEN A."+COLUMN_SUB_LOCATION_ID+" = 0 THEN 0 ELSE 1 END AS 'LEVEL'"
                + " FROM " + TABLE_MAP + " M"
                + " WHERE M." + COLUMN_PROJECT_ID + " = " + projID+" AND (M."+COLUMN_INSPECTION_ID+" = "+ iID+" OR M."+COLUMN_INSPECTION_ID+" = "+ 0+" )"
                + " AND "+ COLUMN_CHILD + " < " + Child + " ORDER BY M." + COLUMN_CAT_ID;
        //add additional fields: status,  notes, print flag


        Cursor cursor = dtabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SiteMap = new HashMap<String, String>();
                SiteMap.put(MyConfig.TAG_PROJECT_ID, (String.valueOf(cursor.getInt(0))));
                SiteMap.put(MyConfig.TAG_CAT_ID, (String.valueOf(cursor.getInt(1))));
                SiteMap.put(MyConfig.TAG_LEVEL, (String.valueOf(cursor.getInt(2))));
                SiteMap.put(MyConfig.TAG_PARENT, (String.valueOf(cursor.getInt(3))));
                SiteMap.put(MyConfig.TAG_LABEL, cursor.getString(4));
                SiteMap.put(MyConfig.TAG_CHILD, (String.valueOf(cursor.getInt(5))));
                SiteMap.put(MyConfig.TAG_A_ID, (String.valueOf(cursor.getInt(6))));
                SiteMap.put(MyConfig.TAG_INSPECTION_ID, (String.valueOf(cursor.getInt(7))));
                SiteMap.put(MyConfig.TAG_IMAGE1, cursor.getString(8));
                SiteMap.put(MyConfig.TAG_NOTES, cursor.getString(9));


                SiteMapArrayList.add(SiteMap);

            } while (cursor.moveToNext());
        }

        dtabase.close();

        return SiteMapArrayList;

    }


    public HashMap<String, String> getMapItem(int projID, int aId, int iId) {


        HashMap<String, String> SiteMap = new HashMap<String, String>();
        ArrayList<HashMap<String, String>> SiteMapArrayList;

        SiteMapArrayList = new ArrayList<HashMap<String, String>>();


        SQLiteDatabase dtabase = this.getReadableDatabase();

        String selectQuery = "SELECT M." + COLUMN_CAT_ID + ", M." + COLUMN_LEVEL + ", M." + COLUMN_PARENT + ", M." + COLUMN_LABEL
                + ", M." + COLUMN_CHILD + ", M." + COLUMN_A_ID + ", M." + COLUMN_IMG1 + ", M." + COLUMN_NOTES + ", I."+COLUMN_LABEL
                + " FROM " + TABLE_MAP + " M"
                + " JOIN " + TABLE_INSPECTION + " I"
                + " ON M." + COLUMN_PROJECT_ID + " = I."+ COLUMN_PROJECT_ID

                + " WHERE M." + COLUMN_PROJECT_ID + " = " + projID + " AND M." + COLUMN_A_ID + " = " + aId+ " AND I." + COLUMN_INSPECTION_ID + " = " + iId;

        //add additional fields: status,  notes, print flag
        Cursor cursor = dtabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SiteMap = new HashMap<String, String>();

                SiteMap.put(MyConfig.TAG_CAT_ID, (String.valueOf(cursor.getInt(0))));
                SiteMap.put(MyConfig.TAG_LEVEL, (String.valueOf(cursor.getInt(1))));
                SiteMap.put(MyConfig.TAG_PARENT, (String.valueOf(cursor.getInt(2))));
                SiteMap.put("MAP_LABEL", cursor.getString(3));
                SiteMap.put(MyConfig.TAG_CHILD, (String.valueOf(cursor.getInt(4))));
                SiteMap.put(MyConfig.TAG_A_ID, (String.valueOf(cursor.getInt(5))));
                SiteMap.put(MyConfig.TAG_IMAGE1, cursor.getString(6));
                SiteMap.put(MyConfig.TAG_NOTES, cursor.getString(7));
                SiteMap.put(MyConfig.TAG_LABEL, cursor.getString(8));


                SiteMapArrayList.add(SiteMap);
            } while (cursor.moveToNext());
        }

        dtabase.close();

        return SiteMap;

    }


    //Get a list of locations to upload to server
    public ArrayList<HashMap<String, String>> getActions(int user_id) {


        HashMap<String, String> ActionItemMap;
        ArrayList<HashMap<String, String>> locationList;

        locationList = new ArrayList<HashMap<String, String>>();

        String projID;
        SQLiteDatabase dtabase = this.getReadableDatabase();

        String selectQueryI = "SELECT I."+COLUMN_PROJECT_ID
                + " FROM " + TABLE_INSPECTION + " I "
                + " WHERE I."+COLUMN_INSPECTOR+" = "+user_id +" AND I."+COLUMN_INSPECTION_STATUS+" = 'm'"
                + " GROUP BY I."+ COLUMN_PROJECT_ID;

        Cursor cursorI = dtabase.rawQuery(selectQueryI, null);
        if (cursorI.moveToFirst()) {
            do {
                projID = cursorI.getString(0);

        String selectQuery = "SELECT * "
                + " FROM " + TABLE_ACTION_ITEM
          //      +" JOIN "+ TABLE_INSPECTION+" I ON A."+ COLUMN_PROJECT_ID+" = I."+COLUMN_PROJECT_ID
                +" WHERE "+ COLUMN_PROJECT_ID+" = "+ projID
                + " ORDER BY "+COLUMN_PROJECT_ID+", " + COLUMN_A_ID;
        //add additional fields: status,  notes, print flag
        Cursor cursor = dtabase.rawQuery(selectQuery, null);

        // Move to the first row
        if (cursor.moveToFirst()) {
            do {
                ActionItemMap = new HashMap<String, String>();
                ActionItemMap.put(MyConfig.TAG_INSPECTION_ID, (String.valueOf(cursor.getInt(0))));
                ActionItemMap.put(MyConfig.TAG_PROJECT_ID, (String.valueOf(cursor.getInt(1))));
                ActionItemMap.put(MyConfig.TAG_A_ID, (String.valueOf(cursor.getInt(2))));
                ActionItemMap.put(MyConfig.TAG_DATE_INSPECTED, cursor.getString(3));
                ActionItemMap.put(MyConfig.TAG_OVERVIEW, cursor.getString(4));
                ActionItemMap.put(MyConfig.TAG_RELEVANT_INFO, cursor.getString(5));
                ActionItemMap.put(MyConfig.TAG_SERVICE_LEVEL, cursor.getString(6));
                ActionItemMap.put(MyConfig.TAG_SERVICED_BY, cursor.getString(7));
                ActionItemMap.put(MyConfig.TAG_REPORT_IMAGE, cursor.getString(8));
                ActionItemMap.put(MyConfig.TAG_IMAGE1, cursor.getString(9));
                ActionItemMap.put(MyConfig.TAG_COM1, cursor.getString(10));
                ActionItemMap.put(MyConfig.TAG_ITEM_STATUS, cursor.getString(11));
                ActionItemMap.put(MyConfig.TAG_NOTES, cursor.getString(12));

                locationList.add(ActionItemMap);
            } while (cursor.moveToNext());
        }
            } while (cursorI.moveToNext());
        }

        dtabase.close();


        return locationList;

    }


    public ArrayList<HashMap<String, String>> getCertInspections(int user_id) {


        HashMap<String, String> CertificateItemMap;
        ArrayList<HashMap<String, String>> CertificateList;

        CertificateList = new ArrayList<HashMap<String, String>>();

        String projID;
        SQLiteDatabase dtabase = this.getReadableDatabase();

        String selectQueryI = "SELECT I."+COLUMN_PROJECT_ID
                + " FROM " + TABLE_INSPECTION + " I "
                + " WHERE I."+COLUMN_INSPECTOR+" = "+user_id +" AND I."+COLUMN_INSPECTION_STATUS+" = 'm'"
                + " GROUP BY I."+ COLUMN_PROJECT_ID;

        Cursor cursorI = dtabase.rawQuery(selectQueryI, null);
        if (cursorI.moveToFirst()) {
            do {
                projID = cursorI.getString(0);

        String selectQuery = "SELECT * "
                + " FROM " + TABLE_CERTIFICATE_INSPECTION
                +" WHERE "+ COLUMN_PROJECT_ID+" = "+ projID+" AND "+COLUMN_ITEM_STATUS+" = 'm'"
                + " ORDER BY "+COLUMN_PROJECT_ID+", " + COLUMN_INSPECTION_ID;
        //add additional fields: status,  notes, print flag
        Cursor cursor = dtabase.rawQuery(selectQuery, null);

        // Move to the first row
        if (cursor.moveToFirst()) {
            do {
                CertificateItemMap = new HashMap<String, String>();
                CertificateItemMap.put(MyConfig.TAG_INSPECTION_ID, (String.valueOf(cursor.getInt(0))));
                CertificateItemMap.put(MyConfig.TAG_PROJECT_ID, (String.valueOf(cursor.getInt(1))));
                CertificateItemMap.put(MyConfig.TAG_DATE_TIME, cursor.getString(2));
                CertificateItemMap.put(MyConfig.TAG_OVERVIEW, cursor.getString(3));
                CertificateItemMap.put(MyConfig.TAG_PERMIT, cursor.getString(4));
                CertificateItemMap.put(MyConfig.TAG_PROJECT_ADDRESS, cursor.getString(5));
                CertificateItemMap.put(MyConfig.TAG_STAGE, cursor.getString(6));
                CertificateItemMap.put(MyConfig.TAG_NOTES, cursor.getString(7));

                CertificateList.add(CertificateItemMap);
            } while (cursor.moveToNext());
        }
            } while (cursorI.moveToNext());
        }
        dtabase.close();


        return CertificateList;

    }



    public ArrayList<HashMap<String, String>> getAllSummary(int user_id) {


        HashMap<String, String> SummaryItemMap;
        ArrayList<HashMap<String, String>> SummaryList;

        SummaryList = new ArrayList<HashMap<String, String>>();

        String projID;
        SQLiteDatabase dtabase = this.getReadableDatabase();

        String selectQueryI = "SELECT I."+COLUMN_PROJECT_ID
                + " FROM " + TABLE_INSPECTION + " I "
                + " WHERE I."+COLUMN_INSPECTOR+" = "+user_id +" AND I."+COLUMN_INSPECTION_STATUS+" = 'm'"
                + " GROUP BY I."+ COLUMN_PROJECT_ID;

        Cursor cursorI = dtabase.rawQuery(selectQueryI, null);
        if (cursorI.moveToFirst()) {
            do {
                projID = cursorI.getString(0);

                String selectQuery = "SELECT * "
                        + " FROM " + TABLE_SUMMARY
                        +" WHERE "+ COLUMN_PROJECT_ID+" = "+ projID+" AND "+ COLUMN_ITEM_STATUS+" = 'm'"
                        + " ORDER BY "+COLUMN_PROJECT_ID+", " + COLUMN_INSPECTION_ID;
                //add additional fields: status,  notes, print flag
                Cursor cursor = dtabase.rawQuery(selectQuery, null);

                // Move to the first row
                if (cursor.moveToFirst()) {
                    do {
                        SummaryItemMap = new HashMap<String, String>();
                        SummaryItemMap.put(MyConfig.TAG_INSPECTION_ID, (String.valueOf(cursor.getInt(0))));
                        SummaryItemMap.put(MyConfig.TAG_PROJECT_ID, (String.valueOf(cursor.getInt(1))));
                        SummaryItemMap.put(MyConfig.TAG_HEAD_A, cursor.getString(2));
                        SummaryItemMap.put(MyConfig.TAG_COM_A, cursor.getString(3));
                        SummaryItemMap.put(MyConfig.TAG_HEAD_B, cursor.getString(4));
                        SummaryItemMap.put(MyConfig.TAG_COM_B, cursor.getString(5));
                        SummaryItemMap.put(MyConfig.TAG_HEAD_C, cursor.getString(6));
                        SummaryItemMap.put(MyConfig.TAG_COM_C, cursor.getString(7));

                        SummaryList.add(SummaryItemMap);
                    } while (cursor.moveToNext());
                }
            } while (cursorI.moveToNext());
        }
        dtabase.close();


        return SummaryList;

    }

    public ArrayList<HashMap<String, String>> getLOG(int user_id) {


        HashMap<String, String> LOG;
        ArrayList<HashMap<String, String>> LOG_Time;

        LOG_Time = new ArrayList<HashMap<String, String>>();

        String projID;
        SQLiteDatabase dtabase = this.getReadableDatabase();

        String selectQueryI = "SELECT L.* FROM "+TABLE_LOG_TIME+" L "
                + " JOIN " + TABLE_INSPECTION + " I "
                + " ON L." + COLUMN_PROJECT_ID + " = I."+COLUMN_PROJECT_ID
                + " WHERE I."+COLUMN_INSPECTOR+" = "+user_id +" AND I."+COLUMN_INSPECTION_STATUS+" = 'm'"
                + " GROUP BY L."+ COLUMN_U_ID;

        Cursor cursor = dtabase.rawQuery(selectQueryI, null);
                 // Move to the first row
                if (cursor.moveToFirst()) {
                    do {
                        LOG = new HashMap<String, String>();
                        LOG.put(MyConfig.TAG_A_ID, (String.valueOf(cursor.getInt(0))));
                        LOG.put(MyConfig.TAG_PROJECT_ID, (String.valueOf(cursor.getInt(1))));
                        LOG.put(MyConfig.TAG_INSPECTION_ID, cursor.getString(2));
                        LOG.put(MyConfig.TAG_START_DATE_TIME, cursor.getString(3));
                        LOG.put(MyConfig.TAG_END_DATE_TIME, cursor.getString(4));
                        LOG_Time.add(LOG);
                    } while (cursor.moveToNext());
                }


        dtabase.close();


        return LOG_Time;

    }


    public ArrayList<HashMap<String, String>> getDeleted() {


        HashMap<String, String> DELETED;
        ArrayList<HashMap<String, String>> DELETED_items;

        DELETED_items = new ArrayList<HashMap<String, String>>();


        SQLiteDatabase dtabase = this.getReadableDatabase();

        String selectQueryI = "SELECT * FROM "+TABLE_NAME;

        Cursor cursor = dtabase.rawQuery(selectQueryI, null);
        // Move to the first row
        if (cursor.moveToFirst()) {
            do {
                DELETED = new HashMap<String, String>();
                DELETED.put(MyConfig.TAG_TABLE_NAME, cursor.getString(0));
                DELETED.put(MyConfig.TAG_PROJECT_ID, (String.valueOf(cursor.getInt(1))));
                DELETED.put(MyConfig.TAG_INSPECTION_ID, (String.valueOf(cursor.getInt(2))));
                DELETED.put(MyConfig.TAG_A_ID, (String.valueOf(cursor.getInt(3))));
                DELETED_items.add(DELETED);
            } while (cursor.moveToNext());
        }


        dtabase.close();


        return DELETED_items;

    }

    public ArrayList<HashMap<String, String>> getinspectionitemlist(int projId, int aId, int iItem) {

        HashMap<String, String> inspectionItem;
        ArrayList<HashMap<String, String>> inspectionitemsArrayList;


        inspectionitemsArrayList = new ArrayList<HashMap<String, String>>();

        SQLiteDatabase dtabase = this.getReadableDatabase();

        String selectQuery = "SELECT I." + COLUMN_DATE_INSPECTED + ", I." + COLUMN_OVERVIEW + ", I." + COLUMN_RELEVANT_INFO + ", I." + COLUMN_IMG1 + ", I." + COLUMN_COM1
                + ", I." + COLUMN_IMG2 + ", I." + COLUMN_COM2 + ", I." + COLUMN_IMG3 + ", I." + COLUMN_COM3 + ", I." + COLUMN_IMG4 + ", I." + COLUMN_COM4
                + ", I." + COLUMN_IMG5 + ", I." + COLUMN_COM5 + ", I." + COLUMN_IMG6 + ", I." + COLUMN_COM6 + ", I." + COLUMN_IMG7 + ", I." + COLUMN_COM7
                + ", I." + COLUMN_ITEM_STATUS + ", I." + COLUMN_NOTES
                + " FROM " + TABLE_INSPECTION_ITEM + " I "
                + " WHERE I." + COLUMN_PROJECT_ID + " = " + projId + " AND " + COLUMN_A_ID + " = " + aId;


        //add additional fields: status,  notes, print flag
        Cursor cursor = dtabase.rawQuery(selectQuery, null);

        // Move to the first row
        if (cursor.moveToFirst()) {
            do {
                inspectionItem = new HashMap<String, String>();
                inspectionItem.put(MyConfig.TAG_DATE_INSPECTED, (String.valueOf(cursor.getInt(0))));
                inspectionItem.put(MyConfig.TAG_OVERVIEW, cursor.getString(1));
                inspectionItem.put(MyConfig.TAG_RELEVANT_INFO, cursor.getString(2));
                inspectionItem.put(MyConfig.TAG_IMAGE1, cursor.getString(3));
                inspectionItem.put(MyConfig.TAG_COM1, cursor.getString(4));
                inspectionItem.put(MyConfig.TAG_IMAGE2, cursor.getString(5));
                inspectionItem.put(MyConfig.TAG_COM2, cursor.getString(6));
                inspectionItem.put(MyConfig.TAG_IMAGE3, cursor.getString(7));
                inspectionItem.put(MyConfig.TAG_COM3, cursor.getString(8));
                inspectionItem.put(MyConfig.TAG_IMAGE4, cursor.getString(9));
                inspectionItem.put(MyConfig.TAG_COM4, cursor.getString(10));
                inspectionItem.put(MyConfig.TAG_IMAGE5, cursor.getString(11));
                inspectionItem.put(MyConfig.TAG_COM5, cursor.getString(12));
                inspectionItem.put(MyConfig.TAG_IMAGE6, cursor.getString(13));
                inspectionItem.put(MyConfig.TAG_COM6, cursor.getString(14));
                inspectionItem.put(MyConfig.TAG_IMAGE7, cursor.getString(15));
                inspectionItem.put(MyConfig.TAG_COM7, cursor.getString(16));
                inspectionItem.put(MyConfig.TAG_ITEM_STATUS, cursor.getString(17));
                inspectionItem.put(MyConfig.TAG_NOTES, cursor.getString(18));
                inspectionitemsArrayList.add(inspectionItem);
            } while (cursor.moveToNext());
        }

        dtabase.close();


        return inspectionitemsArrayList;

    }


    public ArrayList<HashMap<String, String>> getORlist(String CAT_TAB, int type, String subCat) {

        HashMap<String, String> ORItemMap;
        ArrayList<HashMap<String, String>> ORArrayList;


        ORArrayList = new ArrayList<HashMap<String, String>>();

        SQLiteDatabase dtabase = this.getReadableDatabase();
        if (CAT_TAB.equals("A")) CAT_TAB = TABLE_A_OR;
        if (CAT_TAB.equals("B")) CAT_TAB = TABLE_B_OR;
        if (CAT_TAB.equals("C")) CAT_TAB = TABLE_C_OR;
        if (CAT_TAB.equals("D")) CAT_TAB = TABLE_D_OR;


        String selectQuery = "SELECT " + COLUMN_NOTE
                + " FROM " + CAT_TAB
                + " WHERE " + COLUMN_TYPE + " = " + type + " AND " + COLUMN_SUB_CAT + " = '" + subCat + "'";


        //add additional fields: status,  notes, print flag
        Cursor cursor = dtabase.rawQuery(selectQuery, null);

        // Move to the first row
        if (cursor.moveToFirst()) {
            do {
                ORItemMap = new HashMap<String, String>();
                ORItemMap.put(MyConfig.TAG_NOTE, cursor.getString(0));
                ORArrayList.add(ORItemMap);
            } while (cursor.moveToNext());
        }

        dtabase.close();

        return ORArrayList;

    }

    public ArrayList<HashMap<String, String>> getZonesArray(String PropId) {


        HashMap<String, String> locationItemMap;
        ArrayList<HashMap<String, String>> locationArrayList;

        locationArrayList = new ArrayList<HashMap<String, String>>();

/*

        SQLiteDatabase dtabase = this.getReadableDatabase();

        String selectQuery = "SELECT "+COLUMN_LOCATION_ID+", "+COLUMN_LOCATION_DESCRIPTION

                +" FROM "+TABLE_PROPERTY_LOCATIONS
                +" WHERE "+COLUMN_PROPERTY_ID+" = "+PropId+" AND "+COLUMN_SUB_LOCATION_ID+" = 0 "
                +" ORDER BY "+COLUMN_LOCATION_ID;
        //add additional fields: status,  notes, print flag
        Cursor cursor = dtabase.rawQuery(selectQuery, null);
        int i = 1;
        // Move to the first row
        if (cursor.moveToFirst()) {
            do {
                locationItemMap = new HashMap<String, String>();

                locationItemMap.put(MyConfig.TAG_LOCATION_ID, (String.valueOf(cursor.getInt(0))));
                locationItemMap.put(MyConfig.TAG_LOCATION_DESC, cursor.getString(1));
                locationArrayList.add(locationItemMap);
            } while (cursor.moveToNext());
        }

        dtabase.close();

 */

        return locationArrayList;

    }


    public ArrayList<HashMap<String, String>> getInspectedItems_r(int projId, int iId) {

        // ArrayList that contains every row in the database
        // and each row key / value stored in a HashMap

        ArrayList<HashMap<String, String>> inspectedItemsList;

        inspectedItemsList = new ArrayList<HashMap<String, String>>();

        SQLiteDatabase dbase = this.getReadableDatabase();


        //First query sets out the zones and the related esential measures in those zones


        String selectQuery = "SELECT  " + COLUMN_CAT_ID + " , " + COLUMN_LABEL +
                " FROM " + TABLE_MAP +
                " WHERE " + COLUMN_PROJECT_ID + " = " + projId + " AND " + COLUMN_LEVEL + " = "+0+ " GROUP BY " + COLUMN_CAT_ID+ " ORDER BY " + COLUMN_CAT_ID;

        Cursor cursor = dbase.rawQuery(selectQuery, null);
        int i = 1;
        // Move to the first row
        if (cursor.moveToFirst()) {
            do {

                int catId = cursor.getInt(0);
                String BranchHead = cursor.getString(1);

                String selectQueryB = "SELECT  I." + COLUMN_OVERVIEW + ", I." + COLUMN_RELEVANT_INFO + ", I." + COLUMN_NOTES +
                        ", I." + COLUMN_IMG1 + ", I." + COLUMN_COM1 + ", I." + COLUMN_IMG2 + ", I." + COLUMN_COM2 + ", I." + COLUMN_IMG3 + ", I." + COLUMN_COM3
                        + ", I." + COLUMN_IMG4 + ", I." + COLUMN_COM4 + ", I." + COLUMN_IMG5 + ", I." + COLUMN_COM5 + ", M." + COLUMN_LABEL+ ", M."+ COLUMN_PARENT
                        + ", M."+ COLUMN_A_ID
                        + " FROM " + TABLE_INSPECTION_ITEM + " I " +
                        " JOIN " + TABLE_MAP + " M ON M." + COLUMN_INSPECTION_ID + " = I." + COLUMN_INSPECTION_ID + " AND M." + COLUMN_PROJECT_ID + " = I." + COLUMN_PROJECT_ID +
                                                            " AND M."+COLUMN_A_ID+" = I."+COLUMN_A_ID+
                  //      " JOIN " + TABLE_ACTION_ITEM + " AI ON AI." + COLUMN_INSPECTION_ID + " = I." + COLUMN_INSPECTION_ID + " AND AI." + COLUMN_PROJECT_ID + " = I." + COLUMN_PROJECT_ID +
                   //     " AND AI."+COLUMN_A_ID+" = I."+COLUMN_A_ID+

                        " WHERE I." + COLUMN_PROJECT_ID + " = " + projId + " AND I." + COLUMN_INSPECTION_ID + " = " + iId + " AND M." + COLUMN_CHILD + " > 0"
                        + " AND M." + COLUMN_CAT_ID + " = " + catId +
                        " ORDER BY I." + COLUMN_A_ID;

                Cursor cursorB = dbase.rawQuery(selectQueryB, null);

                // Move to the first row

                if (cursorB.moveToFirst()) {
                    do {

                        //get the parent Label for the inspection item
                        int parentId = cursorB.getInt(14);
                        String selectQueryB1 = "SELECT  " + COLUMN_LABEL
                                + " FROM " + TABLE_MAP
                                +  " WHERE " + COLUMN_PROJECT_ID + " = " + projId + " AND " + COLUMN_A_ID + " = " + parentId ;

                        Cursor cursorB1 = dbase.rawQuery(selectQueryB1, null);
                        cursorB1.moveToFirst();



                        HashMap<String, String> inspectionItemMap = new HashMap<String, String>();

                        // Store the key / value pairs in a HashMap
                        // Access the Cursor data by index that is in the same order
                        // as query
                        inspectionItemMap.put("typeObject", "inspectionObject");
                        inspectionItemMap.put("BranchHead", BranchHead);
                        inspectionItemMap.put("ParentLabel",cursorB1.getString(0));
                        inspectionItemMap.put(MyConfig.TAG_OVERVIEW, cursorB.getString(0));
                        inspectionItemMap.put(MyConfig.TAG_RELEVANT_INFO, cursorB.getString(1));
                        inspectionItemMap.put(MyConfig.TAG_NOTES, cursorB.getString(2));
                        inspectionItemMap.put(MyConfig.TAG_IMAGE1, cursorB.getString(3));
                        inspectionItemMap.put(MyConfig.TAG_COM1, cursorB.getString(4));
                        inspectionItemMap.put(MyConfig.TAG_IMAGE2, cursorB.getString(5));
                        inspectionItemMap.put(MyConfig.TAG_COM2, cursorB.getString(6));
                        inspectionItemMap.put(MyConfig.TAG_IMAGE3, cursorB.getString(7));
                        inspectionItemMap.put(MyConfig.TAG_COM3, cursorB.getString(8));
                        inspectionItemMap.put(MyConfig.TAG_IMAGE4, cursorB.getString(9));
                        inspectionItemMap.put(MyConfig.TAG_COM4, cursorB.getString(10));
                        inspectionItemMap.put(MyConfig.TAG_IMAGE5, cursorB.getString(11));
                        inspectionItemMap.put(MyConfig.TAG_COM5, cursorB.getString(12));
                        inspectionItemMap.put(MyConfig.TAG_LABEL, cursorB.getString(13));

                        inspectedItemsList.add(inspectionItemMap);


                        String selectQueryC = "SELECT  I." + COLUMN_OVERVIEW + ", I." + COLUMN_COM1 + ", I." + COLUMN_RELEVANT_INFO
                                + ", I." + COLUMN_NOTES + ", I." + COLUMN_IMG1
                                + " FROM " + TABLE_ACTION_ITEM + " I " +
                                " JOIN " + TABLE_MAP + " M ON M." + COLUMN_A_ID + " = I." + COLUMN_REPORT_IMAGE + " AND M." + COLUMN_PROJECT_ID + " = I." + COLUMN_PROJECT_ID +
                                " WHERE I." + COLUMN_PROJECT_ID + " = " + projId + " AND I." + COLUMN_INSPECTION_ID + " = " + iId+
                                " AND M."+COLUMN_A_ID+" = "+ cursorB.getString(15) //this is aID of the MAP table
                                + " ORDER BY I." + COLUMN_A_ID;

                        Cursor cursorC = dbase.rawQuery(selectQueryC, null);

                        // Move to the first row

                        if (cursorC.moveToFirst()) {
                            do {
                                HashMap<String, String> actionItemMap = new HashMap<String, String>();
                                // Store the key / value pairs in a HashMap
                                // Access the Cursor data by index that is in the same order
                                // as query
                                actionItemMap.put("typeObject", "ActionItemObject");
                                actionItemMap.put(MyConfig.TAG_OVERVIEW, cursorC.getString(0));
                                actionItemMap.put(MyConfig.TAG_COM1, cursorC.getString(1));
                                actionItemMap.put(MyConfig.TAG_RELEVANT_INFO, cursorC.getString(2));
                                actionItemMap.put(MyConfig.TAG_NOTES, cursorC.getString(3));
                                actionItemMap.put(MyConfig.TAG_IMAGE1, cursorC.getString(4));

                                inspectedItemsList.add(actionItemMap);


                            } while (cursorC.moveToNext()); // Move Cursor to the next row
                        }



                    } while (cursorB.moveToNext());
                }

            } while (cursor.moveToNext());

        }


        //Add Summary to the inspection

        String selectQueryS = "SELECT  S." + COLUMN_HEAD_A + ", S."  + COLUMN_COM_A + ", S." + COLUMN_HEAD_B
                + ", S." + COLUMN_COM_B + ", S." + COLUMN_HEAD_C + ", S." + COLUMN_COM_C
                + " FROM " + TABLE_SUMMARY + " S "
                +  " WHERE S." + COLUMN_PROJECT_ID + " = " + projId + " AND S." + COLUMN_INSPECTION_ID + " = " + iId ;

        Cursor cursorS = dbase.rawQuery(selectQueryS, null);

        // Move to the first row

        if (cursorS.moveToFirst()) {
            do {
                HashMap<String, String> SummaryItemMap = new HashMap<String, String>();
                // Store the key / value pairs in a HashMap
                // Access the Cursor data by index that is in the same order
                // as query
                SummaryItemMap.put("typeObject", "SummaryObject");
                SummaryItemMap.put(MyConfig.TAG_HEAD_A, cursorS.getString(0));
                SummaryItemMap.put(MyConfig.TAG_COM_A, cursorS.getString(1));
                SummaryItemMap.put(MyConfig.TAG_HEAD_B, cursorS.getString(2));
                SummaryItemMap.put(MyConfig.TAG_COM_B, cursorS.getString(3));
                SummaryItemMap.put(MyConfig.TAG_HEAD_C, cursorS.getString(4));
                SummaryItemMap.put(MyConfig.TAG_COM_C, cursorS.getString(5));
                inspectedItemsList.add(SummaryItemMap);


            } while (cursorS.moveToNext()); // Move Cursor to the next row

        }

            //Add certificate to the inspection

            String selectQueryCI = "SELECT  CI." + COLUMN_DATE_TIME + ", CI."  + COLUMN_OVERVIEW + ", CI." + COLUMN_PERMIT_NO
                    + ", CI." + COLUMN_PROJECT_ADDRESS + ", CI." + COLUMN_STAGE + ", CI." + COLUMN_NOTES
                    + " FROM " + TABLE_CERTIFICATE_INSPECTION + " CI "
                    +  " WHERE CI." + COLUMN_PROJECT_ID + " = " + projId + " AND CI." + COLUMN_INSPECTION_ID + " = " + iId ;

            Cursor cursorCI = dbase.rawQuery(selectQueryCI, null);

            // Move to the first row

            if (cursorCI.moveToFirst()) {
                do {
                    HashMap<String, String> CIItemMap = new HashMap<String, String>();
                    // Store the key / value pairs in a HashMap
                    // Access the Cursor data by index that is in the same order
                    // as query
                    CIItemMap.put("typeObject", "CertInspectionObject");
                    CIItemMap.put(MyConfig.TAG_DATE_TIME, cursorCI.getString(0));
                    CIItemMap.put(MyConfig.TAG_OVERVIEW, cursorCI.getString(1));
                    CIItemMap.put(MyConfig.TAG_PERMIT, cursorCI.getString(2));
                    CIItemMap.put(MyConfig.TAG_PROJECT_ADDRESS, cursorCI.getString(3));
                    CIItemMap.put(MyConfig.TAG_STAGE, cursorCI.getString(4));
                    CIItemMap.put(MyConfig.TAG_NOTES, cursorCI.getString(5));
                    inspectedItemsList.add(CIItemMap);


                } while (cursorCI.moveToNext()); // Move Cursor to the next row


            }




        dbase.close();

        return inspectedItemsList;
    }


    private String dayTime(int Type) {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String daytime = sdf.format(new Date());

        switch (Type) {

            case (1): {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                Date date_ = Calendar.getInstance().getTime();
                daytime = (dateFormat.format(date_));
                break;
            }

            case (2): {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
                Date date_ = Calendar.getInstance().getTime();
                daytime = (dateFormat.format(date_));
                break;

            }

            case (3): {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date_ = Calendar.getInstance().getTime();
                daytime = (dateFormat.format(date_));
                break;
            }
            case (4): {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date_ = Calendar.getInstance().getTime();
                daytime = (dateFormat.format(date_));
                break;
            }
        }
        return daytime;
    }
}

