package com.forcemanage.inspect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;
import android.telecom.CallAudioState;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Level;

/**
 * Created by DAP on 12/03/2020.
 */

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 8;
    private static final String DATABASE_NAME = "Inspection.db";

    public static final String TABLE_PROJECT_INFO = "project_info";

    public static final String COLUMN_PROJECT_ID = "ProjectId";
    public static final String COLUMN_CLIENT_ID = "ClientId";
    public static final String COLUMN_BUILD_TYPE = "BuildType";
    public static final String COLUMN_BUILD_PERMIT_NMBR = "BuildPermitNmbr";
    public static final String COLUMN_BUILDING_CLASS = "BuildingClass";
    public static final String COLUMN_NO_LEVELS = "NmbrLevels";
    public static final String COLUMN_PROJECT_NOTE = "ProjectNote";
    public static final String COLUMN_ADDRESS_NUMBER = "AddressNumber";
    public static final String COLUMN_PROJECT_ADDRESS = "ProjectAddress";
    public static final String COLUMN_PROJECT_SUBURB = "ProjectSuburb";
    public static final String COLUMN_PROJECT_PHOTO = "ProjectPhoto";
    public static final String COLUMN_KEY_REQUIRED = "KeyRequired";
    public static final String COLUMN_OC_DATE = "OCDate";
    public static final String COLUMN_OC_NUMBER = "OCNumber";
    public static final String COLUMN_FLOOR_TYPE = "FloorType";
    public static final String COLUMN_ROOF_TYPE = "RoofType";
    public static final String COLUMN_WALL_TYPE = "WallType";

    public static final String TABLE_INSPECTION = "Inspection";

    public static final String COLUMN_INSPECTION_ID = "InspectionId";
    public static final String COLUMN_INSPECTION_DATE = "InspectionDate";
    public static final String COLUMN_INSPECTOR = "Inspector";
    public static final String COLUMN_INSPECTION_STATUS = "InspectionStatus";
    public static final String COLUMN_INSPECTION_TYPE = "InspectionType";

    public static final String TABLE_INSPECTION_ITEM = "InspectionItem";
    public static final String TABLE_ACTION_ITEM = "ActionItem";

    public static final String COLUMN_DATE_TIME_START = "DateTimeStart";
    public static final String COLUMN_DATE_TIME_FINISH = "DateTimeFinish";
    public static final String COLUMN_DATE_INSPECTED = "DateInspected";
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


    public static final String TABLE_MAP = "Map";

    public static final String COLUMN_CAT_ID = "CatId";
    public static final String COLUMN_LEVEL = "Level";
    public static final String COLUMN_PARENT = "Parent";
    public static final String COLUMN_LABEL = "Label";
    public static final String COLUMN_CHILD = "Child";
    public static final String COLUMN_A_ID = "aID";


    public static final String TABLE_A_OR = "A_OR";
    public static final String TABLE_B_OR = "B_OR";
    public static final String TABLE_C_OR = "C_OR";
    public static final String TABLE_D_OR = "D_OR";
    public static final String TABLE_E_OR = "E_OR";

    public static final String COLUMN_NUM = "Num";
    public static final String COLUMN_SUB_CAT = "Subcat";
    public static final String COLUMN_TYPE = "Type";
    public static final String COLUMN_NOTE = "Note";


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
                + COLUMN_BUILD_TYPE + " TEXT,"
                + COLUMN_BUILD_PERMIT_NMBR + " TEXT,"
                + COLUMN_BUILDING_CLASS + " TEXT,"
                + COLUMN_PROJECT_NOTE + " TEXT,"
                + COLUMN_ADDRESS_NUMBER + " TEXT,"
                + COLUMN_PROJECT_ADDRESS + " TEXT,"
                + COLUMN_PROJECT_SUBURB + " TEXT,"
                + COLUMN_KEY_REQUIRED + " TEXT,"
                + COLUMN_NO_LEVELS + " INTEGER,"
                + COLUMN_ROOF_TYPE + " TEXT,"
                + COLUMN_WALL_TYPE + " TEXT,"
                + COLUMN_FLOOR_TYPE + " TEXT,"
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
                + COLUMN_IMG1 + " TEXT,"
                + COLUMN_NOTES + " TEXT,"
                + "PRIMARY KEY " + "(" + COLUMN_PROJECT_ID + "," + COLUMN_A_ID + "))";
        db.execSQL(CREATE_MAP_TABLE);

        String CREATE_INSPECTION_TABLE = "CREATE TABLE " +
                TABLE_INSPECTION + "("
                + COLUMN_INSPECTION_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_INSPECTION_TYPE + " TEXT,"
                + COLUMN_INSPECTION_STATUS + " TEXT,"
                + COLUMN_PROJECT_ID + " INTEGER,"
                + COLUMN_INSPECTION_DATE + " TEXT,"
                + COLUMN_INSPECTOR + " TEXT,"
                + COLUMN_DATE_TIME_START + " INTEGER,"
                + COLUMN_DATE_TIME_FINISH + " INTEGER" + ")";

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
                + COLUMN_NUM + " INTEGER PRIMARY KEY,"
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

        String CREATE_E_OR_TABLE = "CREATE TABLE " +
                TABLE_E_OR + "("
                + COLUMN_NUM + " INTEGER PRIMARY KEY,"
                + COLUMN_SUB_CAT + " TEXT,"
                + COLUMN_TYPE + " INTEGER,"
                + COLUMN_NOTE + " TEXT )";

        db.execSQL(CREATE_E_OR_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECT_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSPECTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSPECTION_ITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTION_ITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_A_OR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_B_OR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_C_OR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_D_OR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_E_OR);
        onCreate(db);
    }

    public void clearAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROJECT_INFO, null, null);
        db.delete(TABLE_INSPECTION, null, null);
        db.delete(TABLE_INSPECTION_ITEM, null, null);
        db.delete(TABLE_ACTION_ITEM, null, null);
        db.delete(TABLE_MAP, null, null);
        db.delete(TABLE_A_OR, null, null);
        db.delete(TABLE_B_OR, null, null);
        db.delete(TABLE_C_OR, null, null);
        db.delete(TABLE_D_OR, null, null);
        db.delete(TABLE_E_OR, null, null);
        db.close();
    }
    // public boolean insertLocation(int propertyId, int locationId, int subLocationId,  String locationDescription){
    //     SQLiteDatabase db = this.getWritableDatabase();
    //


    //Store values from MySQL on server to local SQLite
    public void updateFromServer(ProjectAttributes projectAttributes, InspectionAttributes inspectionAttributes, InspectionItemAttributes inspectionItemAttributes, ActionItemAttributes actionItemAttributes) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_PROJECT_ID, projectAttributes.getProjectId());
        values.put(COLUMN_ADDRESS_NUMBER, projectAttributes.getAddressNumber());
        values.put(COLUMN_PROJECT_ADDRESS, projectAttributes.getprojectAddress());
        values.put(COLUMN_PROJECT_SUBURB, projectAttributes.getprojectSuburb());
        values.put(COLUMN_BUILD_TYPE, projectAttributes.getbuildType());
        values.put(COLUMN_BUILD_PERMIT_NMBR, projectAttributes.getbuildPermitNmbr());
        values.put(COLUMN_BUILDING_CLASS, projectAttributes.getbuildClass());
        values.put(COLUMN_NO_LEVELS, projectAttributes.getnumberLevels());
        values.put(COLUMN_PROJECT_PHOTO, projectAttributes.getprojectPhoto());
        values.put(COLUMN_KEY_REQUIRED, projectAttributes.getkeyRequired());
        //       values.put(COLUMN_OC_DATE, projectAttributes.getocDate());
        //       values.put(COLUMN_OC_NUMBER, projectAttributes.getocNmbr());
        values.put(COLUMN_FLOOR_TYPE, projectAttributes.getfloorType());
        values.put(COLUMN_ROOF_TYPE, projectAttributes.getroofType());
        values.put(COLUMN_WALL_TYPE, projectAttributes.getwallType());
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
        //       valuesInspect.put(COLUMN_DATE_TIME_START, inspectionAttributes.getstartDateTime());
        //       valuesInspect.put(COLUMN_DATE_TIME_FINISH, inspectionAttributes.getendDateTime());

        db.replace(TABLE_INSPECTION, null, valuesInspect);


        ContentValues valuesItem = new ContentValues();
        valuesItem.put(COLUMN_INSPECTION_ID, inspectionAttributes.getinspectionId());
        valuesItem.put(COLUMN_PROJECT_ID, inspectionAttributes.getprojectId());
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


        //db.execSQL("delete from "+ TABLE_ESM_INSPECTION_ITEM);

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
        values.put(COLUMN_IMG1, mapAttributes.getimage1());
        values.put(COLUMN_NOTES, mapAttributes.getnote());

        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("delete from "+ TABLE_ASSET_REGISTER);
        //replace will delete the row if the asset ID already exists
        db.replace(TABLE_MAP, null, values);

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


    public void updateInspection(int projId, int iId, int aId, String date, String overview, String servicedBy, String relevantInfo, String ServiceLevel
            , String reportImage, String Img1, String com1, String Img2, String com2, String Img3, String com3, String Img4, String com4,
                                 String Img5, String com5, String Img6, String com6, String Img7, String com7, String ItemStatus, String Notes) {

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
        contentValues.put(COLUMN_IMG1, Img1);
        contentValues.put(COLUMN_COM1, com1);
        contentValues.put(COLUMN_IMG2, Img2);
        contentValues.put(COLUMN_COM2, com2);
        contentValues.put(COLUMN_IMG3, Img3);
        contentValues.put(COLUMN_COM3, com3);
        contentValues.put(COLUMN_IMG4, Img4);
        contentValues.put(COLUMN_COM4, com4);
        contentValues.put(COLUMN_IMG5, Img5);
        contentValues.put(COLUMN_COM5, com5);
        contentValues.put(COLUMN_IMG6, Img6);
        contentValues.put(COLUMN_COM6, com6);
        contentValues.put(COLUMN_IMG7, Img7);
        contentValues.put(COLUMN_COM7, com7);
        contentValues.put(COLUMN_ITEM_STATUS, ItemStatus);
        contentValues.put(COLUMN_NOTES, Notes);


        db.update(TABLE_INSPECTION_ITEM, contentValues, COLUMN_PROJECT_ID + " = ? AND " + COLUMN_INSPECTION_ID + " = ? AND " + COLUMN_A_ID + " = ? ", new String[]{proj_id, inspectionId, a_Id});
        db.close();


    }


    //start inspection (clone previous inspection and put status "p": in progress)
    public void createNewInspection(int projectId, int inspectionId) {
        // Open a database for reading and writing

        /*
        SQLiteDatabase database = this.getWritableDatabase();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;
        String inspectionStatus = "n";
        String testmessage = "Start";

        // SELECT * FROM `ESMInspection` WHERE JobId = 1067
        selectQuery= "SELECT "+COLUMN_INSPECTION_STATUS
                +" FROM "+TABLE_INSPECTION
                +" WHERE "+COLUMN_PROJECT_ID+" = "+projectId;

        cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            jobStatus = cursor.getString(0);
            prevJobId = cursor.getInt(1);
        }

        if (jobStatus.equals("n")){
            //clone previous job
            selectQuery= "SELECT "+COLUMN_ASSET_ID+", "+COLUMN_RECOMMEND_NO+", "+COLUMN_SERVICED_BY+", "+COLUMN_SERVICE_LEVEL
                    +" FROM "+TABLE_ESM_INSPECTION_ITEM
                    +" WHERE "+COLUMN_JOB_ID+" = "+prevJobId;
            cursor = database.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    ContentValues valuesItem = new ContentValues();
                    valuesItem.put(COLUMN_JOB_ID, jobId);
                    valuesItem.put(COLUMN_ASSET_ID, cursor.getInt(0));
                    valuesItem.put(COLUMN_RECOMMEND_NO, cursor.getInt(1));
                    valuesItem.put(COLUMN_SERVICED_BY, cursor.getString(2));
                    valuesItem.put(COLUMN_SERVICE_LEVEL, cursor.getString(3));
                    db.replace(TABLE_ESM_INSPECTION_ITEM, null, valuesItem);
                } while (cursor.moveToNext());
            }
            //put status to P (in progress) and record start time and inspection date

            //UPDATE `ESMInspection` SET `JobStatus`= "p" WHERE JobId = jobId set ESM_JOB_DATE = current date
            Calendar c = Calendar.getInstance();
            System.out.println("Current time => " + c.getTime());

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c.getTime());
            String status = "p";
            String jobIdSQL = String.valueOf(jobId);
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ESM_INSPECTION_STATUS, status);
            contentValues.put(COLUMN_ESM_JOB_DATE, formattedDate);
            db.update(TABLE_ESM_INSPECTION, contentValues, COLUMN_JOB_ID+" = ?",new String[] { jobIdSQL });
            db.close();
            database.close();
        }

         */
    }


    public void updatePropPhoto(String projectId, String photo) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_PROJECT_PHOTO, photo);
        db.update(TABLE_PROJECT_INFO, values, COLUMN_PROJECT_ID + " = " + projectId, null);
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

    public void updateBranchLabel(int projId, int aId, String label) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(COLUMN_LABEL, label);


        db.update(TABLE_MAP, values, COLUMN_PROJECT_ID + " = " + projId + " AND " +
                COLUMN_A_ID + " = " + aId, null);

    }

    public void updateBranchNote(int projId, int aId, String Note, String photo) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_IMG1, photo);

        values.put(COLUMN_NOTES, Note);


        db.update(TABLE_MAP, values, COLUMN_PROJECT_ID + " = " + projId + " AND " +
                COLUMN_A_ID + " = " + aId, null);

    }


    public void deleteInspectionItem(Integer projId, Integer aId) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_INSPECTION_ITEM, COLUMN_PROJECT_ID + " = " + projId + " AND " + COLUMN_A_ID + " = " + aId, null);
        db.close();
    }

    public void deleteActionItem(Integer projId, Integer aId) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();


        db.delete(TABLE_ACTION_ITEM, COLUMN_PROJECT_ID + " = " + projId + " AND " + COLUMN_A_ID + " = " + aId, null);
        db.close();
    }

    public void deleteMapBranch(int projId, int aId) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_ACTION_ITEM, COLUMN_PROJECT_ID + " = " + projId + " AND " + COLUMN_A_ID + " = " + aId, null);
        db.delete(TABLE_INSPECTION_ITEM, COLUMN_PROJECT_ID + " = " + projId + " AND " + COLUMN_A_ID + " = " + aId, null);
        db.delete(TABLE_MAP, COLUMN_PROJECT_ID + " = " + projId + " AND " + COLUMN_A_ID + " = " + aId, null);


        db.close();
    }

    public void deleteAsset(Integer propId, int aId) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        //       db.delete(TABLE_ASSET_REGISTER,COLUMN_PROPERTY_ID+" = "+ propId +" AND "+
        //               COLUMN_ASSET_ID+" = "+aId ,null);


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
    }

    //Get the current max AssetID Number for the property


    //Add sublocation to the location
    public int addLevel(int projID, int aId, int CatID, int Level, int parent, String Label) {
        // Open a database for reading and writing

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;
        int maxAId = 0;
        int maxcatID = 1;

        selectQuery = "SELECT " + COLUMN_PROJECT_ID + " FROM "
                + TABLE_INSPECTION_ITEM
                + " WHERE " + COLUMN_PROJECT_ID + " = " + projID + " AND " + COLUMN_A_ID + " = " + aId;

        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {

        } else {
            selectQuery = "SELECT MAX(M." + COLUMN_A_ID + "), MAX(M." + COLUMN_CAT_ID + ") FROM "
                    + TABLE_MAP + " M"
                    + " WHERE M." + COLUMN_PROJECT_ID + " = " + projID;  //+" AND E2."+COLUMN_LOCATION_ID+" = "+locationId;

            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                maxAId = cursor.getInt(0);
                maxcatID = cursor.getInt(1);
                maxAId = maxAId + 1;
                maxcatID = maxcatID + 1;
            }

            ContentValues values = new ContentValues();

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
            values.put(COLUMN_IMG1, "");
            values.put(COLUMN_NOTES, "");
            db.insert(TABLE_MAP, null, values);


            //  int  maxvalues[] = new int[]{maxSublocation, maxAssetId};
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

        //check if there a subbranch exists
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
                        result = 1;

                        int newId = addLevel(projId, aId, CatID, Level, aId, Label);

                        SQLiteDatabase db_2 = this.getWritableDatabase();

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

                        db_2.insert(TABLE_INSPECTION_ITEM, null, values);
                        db_2.close();
                    }
                }

            }
        } else {
            result = 1;

            int newId = addLevel(projId, aId, CatID, Level, aId, Label);

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
                +" FROM "+TABLE_ESM_INSPECTION_ITEM+" I"
                +" JOIN "+TABLE_ASSET_REGISTER+" A"
                +" ON I."+COLUMN_PROPERTY_ID+" = A."+COLUMN_PROPERTY_ID+" AND A."+COLUMN_ASSET_ID+" = I."+COLUMN_ASSET_ID
                +" WHERE I."+COLUMN_PROPERTY_ID+" = "+propId+" AND A."+COLUMN_SUB_LOCATION_ID+" = 2" ;

        cursor = db.rawQuery(selectQuery, null);

        int items = cursor.getCount();

        selectQuery = "SELECT I."+COLUMN_PROPERTY_ID+", I."+COLUMN_ASSET_ID
                +" FROM "+TABLE_ESM_INSPECTION_ITEM+" I"
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


    // Retrieve individual current inspection
    public HashMap<String, String> getInspection(int projId, int aId, int iId) {
        // Open a database for reading and writing

        HashMap<String, String> inspectionItem = new HashMap<String, String>();

        ArrayList<HashMap<String, String>> inspectionItemList;
        inspectionItemList = new ArrayList<HashMap<String, String>>();

        SQLiteDatabase database = this.getWritableDatabase();


        String selectQuery = "SELECT I." + COLUMN_DATE_INSPECTED + ", I." + COLUMN_OVERVIEW + ", I." + COLUMN_RELEVANT_INFO + ", I." + COLUMN_IMG1 + ", I." + COLUMN_COM1
                + ", I." + COLUMN_IMG2 + ", I." + COLUMN_COM2 + ", I." + COLUMN_IMG3 + ", I." + COLUMN_COM3 + ", I." + COLUMN_IMG4 + ", I." + COLUMN_COM4
                + ", I." + COLUMN_IMG5 + ", I." + COLUMN_COM5 + ", I." + COLUMN_IMG6 + ", I." + COLUMN_COM6 + ", I." + COLUMN_IMG7 + ", I." + COLUMN_COM7
                + ", I." + COLUMN_ITEM_STATUS + ", I." + COLUMN_NOTES + ", I." + COLUMN_SERVICED_BY
                + " FROM " + TABLE_INSPECTION_ITEM + " I "
                + " WHERE I." + COLUMN_PROJECT_ID + " = " + projId + " AND " + COLUMN_A_ID + " = " + aId + " AND " + COLUMN_INSPECTION_ID + " = " + iId;


        //add additional fields: status,  notes, print flag
        Cursor cursor = database.rawQuery(selectQuery, null);

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
                inspectionItem.put(MyConfig.TAG_SERVICED_BY, cursor.getString(19));
                inspectionItemList.add(inspectionItem);
            } while (cursor.moveToNext());
        }


        // return inspection data for propertyid, Jobid, inspection id
        database.close();
        return inspectionItem;


    }


    public ArrayList<HashMap<String, String>> getAllProjects() {

        // ArrayList that contains every row in the database
        // and each row key / value stored in a HashMap

        ArrayList<HashMap<String, String>> propertyArrayList;

        propertyArrayList = new ArrayList<HashMap<String, String>>();

        String selectQuery = "SELECT " +
                TABLE_PROJECT_INFO + "." + COLUMN_PROJECT_ID + ", " + COLUMN_ADDRESS_NUMBER + ", " + COLUMN_PROJECT_ADDRESS + ", " + COLUMN_PROJECT_SUBURB
                + ", " + COLUMN_INSPECTION_ID + ", " + COLUMN_INSPECTION_TYPE + ", " + COLUMN_PROJECT_PHOTO + ", " + COLUMN_INSPECTION_STATUS
                + " FROM " + TABLE_PROJECT_INFO
                + " INNER JOIN " + TABLE_INSPECTION
                + " ON " + TABLE_PROJECT_INFO + "." + COLUMN_PROJECT_ID + " = " + TABLE_INSPECTION + "." + COLUMN_PROJECT_ID
                + " WHERE " + COLUMN_INSPECTION_STATUS + " = 'n' OR " + COLUMN_INSPECTION_STATUS + " = 'p' OR " + COLUMN_INSPECTION_STATUS + " = 'c'"
                + " ORDER BY " + COLUMN_PROJECT_ADDRESS;


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
                propertyMap.put(MyConfig.TAG_INSPECTION_ID, cursor.getString(4));
                propertyMap.put(MyConfig.TAG_INSPECTION_TYPE, cursor.getString(5));
                propertyMap.put(MyConfig.TAG_PROJECT_PHOTO, cursor.getString(6));
                propertyMap.put(MyConfig.TAG_INSPECTION_STATUS, cursor.getString(7));

                propertyArrayList.add(propertyMap);
            } while (cursor.moveToNext()); // Move Cursor to the next row
        }
        cursor.close();
        // return contact list
        return propertyArrayList;

    }


    public ArrayList<HashMap<String, String>> getInspectedItemPhotos() {

        // ArrayList that contains every row in the database
        // and each row key / value stored in a HashMap

        ArrayList<HashMap<String, String>> photoArrayList;

        photoArrayList = new ArrayList<HashMap<String, String>>();

        String selectQuery = "SELECT I." +
                COLUMN_IMG1 + ", I." + COLUMN_IMG2 + ", I." + COLUMN_IMG3 + ", I." + COLUMN_IMG4 + ", I." + COLUMN_IMG5
                + " FROM " + TABLE_INSPECTION_ITEM + " I JOIN " + TABLE_INSPECTION + " E ON E." + COLUMN_INSPECTION_ID + " = I." + COLUMN_INSPECTION_ID
                + " WHERE " + COLUMN_INSPECTION_STATUS + " = 'c' OR " + COLUMN_INSPECTION_STATUS + " = 'p'";

   /*     String selectQuery = "SELECT "+
                TABLE_ESM_INSPECTION+"."+COLUMN_JOB_ID+", "+COLUMN_IMG1+", "+
                COLUMN_IMG2+", "+COLUMN_IMG3+", "+COLUMN_IMG4+", "+COLUMN_IMG5
                +" FROM "+TABLE_ESM_INSPECTION
                +" INNER JOIN "+TABLE_ESM_INSPECTION_ITEM
                +" ON "+TABLE_ESM_INSPECTION+"."+COLUMN_JOB_ID+" = "+TABLE_ESM_INSPECTION_ITEM+"."+COLUMN_JOB_ID
                +" WHERE "+COLUMN_ESM_INSPECTION_STATUS+" = 'c' OR "+COLUMN_ESM_INSPECTION_STATUS+" = 'p'";
*/

        // Open a database for reading and writing

        SQLiteDatabase database = this.getWritableDatabase();

        // Cursor provides read and write access for the
        // data returned from a database query

        // rawQuery executes the query and returns the result as a Cursor

        Cursor cursor = database.rawQuery(selectQuery, null);

        // Move to the first row

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> photoMap = new HashMap<String, String>();

                // Store the key / value pairs in a HashMap
                // Access the Cursor data by index that is in the same order
                // as query


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

        // return contact list
        return photoArrayList;
    }


    //Select inspection items for syncing to server
    public ArrayList<HashMap<String, String>> getInspections() {
        HashMap<String, String> inspectionsMap = new HashMap<String, String>();
        ArrayList<HashMap<String, String>> inspectionArrayList = new ArrayList<>();


        SQLiteDatabase dtabase = this.getReadableDatabase();

        String selectQuery = "SELECT  E1." + COLUMN_INSPECTION_ID + ", E1." + COLUMN_INSPECTION_DATE + ", E1." + COLUMN_INSPECTION_STATUS
                + " FROM " + TABLE_INSPECTION + " E1 ORDER BY " + COLUMN_INSPECTION_ID + " ASC";
        Cursor cursor = dtabase.rawQuery(selectQuery, null);
        // Move to the first row
        if (cursor.moveToFirst()) {
            do {
                inspectionsMap = new HashMap<String, String>();
                inspectionsMap.put(MyConfig.TAG_INSPECTION_ID, (String.valueOf(cursor.getInt(0))));
                inspectionsMap.put(MyConfig.TAG_INSPECTION_DATE, cursor.getString(1));
                inspectionsMap.put(MyConfig.TAG_INSPECTION_STATUS, cursor.getString(2));
                //       inspectionsMap.put (MyConfig.TAG_START_DATE_TIME, cursor.getString(3));
                //        inspectionsMap.put (MyConfig.TAG_END_DATE_TIME, cursor.getString(4));
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

        String selectQuery = "SELECT * FROM " + TABLE_MAP + " ORDER BY " + MyConfig.TAG_PROJECT_ID;

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
                SiteMap.put(MyConfig.TAG_IMAGE1, cursor.getString(7));
                SiteMap.put(MyConfig.TAG_NOTES, cursor.getString(8));
                AssArrayList.add(SiteMap);
            } while (cursor.moveToNext());
        }

        dtabase.close();
        return AssArrayList;

    }

    //Select inspection items for syncing to server
    public ArrayList<HashMap<String, String>> getInspectionItems() {
        HashMap<String, String> inspectionItem;
        ArrayList<HashMap<String, String>> inspectionItemsList;

        inspectionItemsList = new ArrayList<HashMap<String, String>>();


        SQLiteDatabase dtabase = this.getReadableDatabase();

        String selectQuery = "SELECT I." + COLUMN_DATE_INSPECTED + ", I." + COLUMN_OVERVIEW + ", I." + COLUMN_RELEVANT_INFO + ", I." + COLUMN_IMG1 + ", I." + COLUMN_COM1
                + ", I." + COLUMN_IMG2 + ", I." + COLUMN_COM2 + ", I." + COLUMN_IMG3 + ", I." + COLUMN_COM3 + ", I." + COLUMN_IMG4 + ", I." + COLUMN_COM4
                + ", I." + COLUMN_IMG5 + ", I." + COLUMN_COM5 + ", I." + COLUMN_IMG6 + ", I." + COLUMN_COM6 + ", I." + COLUMN_IMG7 + ", I." + COLUMN_COM7
                + ", I." + COLUMN_ITEM_STATUS + ", I." + COLUMN_NOTES + ", I." + COLUMN_INSPECTION_ID + ", I." + COLUMN_PROJECT_ID + ", I." + COLUMN_A_ID
                + ", I." + COLUMN_SERVICED_BY + ", I." + COLUMN_SERVICE_LEVEL
                + " FROM " + TABLE_INSPECTION_ITEM + " I ";


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
                inspectionItem.put(MyConfig.TAG_INSPECTION_ID, cursor.getString(19));
                inspectionItem.put(MyConfig.TAG_PROJECT_ID, cursor.getString(20));
                inspectionItem.put(MyConfig.TAG_A_ID, cursor.getString(21));
                inspectionItem.put(MyConfig.TAG_SERVICED_BY, cursor.getString(22));
                inspectionItem.put(MyConfig.TAG_SERVICE_LEVEL, cursor.getString(23));
                inspectionItemsList.add(inspectionItem);
            } while (cursor.moveToNext());
        }

        dtabase.close();

        return inspectionItemsList;

    }

    //Get a list of sublocations to populate the sub location spinner
    public int getSubItemMap(int projId, int aId) {

        HashMap<String, String> subItemMap;
        ArrayList<HashMap<String, String>> subItemArrayList;

        subItemArrayList = new ArrayList<HashMap<String, String>>();

        SQLiteDatabase dtabase = this.getReadableDatabase();

        String selectQuery = "SELECT " + COLUMN_LEVEL//+", "+COLUMN_LOCATION_DESCRIPTION
                + " FROM " + TABLE_MAP
                + " WHERE " + COLUMN_PROJECT_ID + " = " + projId + " AND " + COLUMN_A_ID + " = " + aId;

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


    //Get a list of sublocations to populate the sub location spinner
    public ArrayList<HashMap<String, String>> getMap(int projID) {


        HashMap<String, String> SiteMap;
        ArrayList<HashMap<String, String>> SiteMapArrayList;

        SiteMapArrayList = new ArrayList<HashMap<String, String>>();


        SQLiteDatabase dtabase = this.getReadableDatabase();

        String selectQuery = "SELECT M." + COLUMN_CAT_ID + ", M." + COLUMN_LEVEL + ", M." + COLUMN_PARENT + ", M." + COLUMN_LABEL
                + ", M." + COLUMN_CHILD + ", M." + COLUMN_A_ID + ", M." + COLUMN_IMG1 + ", M." + COLUMN_NOTES//CASE WHEN A."+COLUMN_SUB_LOCATION_ID+" = 0 THEN 0 ELSE 1 END AS 'LEVEL'"

                + " FROM " + TABLE_MAP + " M"

                + " WHERE M." + COLUMN_PROJECT_ID + " = " + projID//+" AND (A."+COLUMN_CATEGORY_ID+" = 'A' OR A."+COLUMN_CATEGORY_ID+" = 'Z')"
                + " ORDER BY M." + COLUMN_CAT_ID;
        //add additional fields: status,  notes, print flag
        Cursor cursor = dtabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SiteMap = new HashMap<String, String>();

                SiteMap.put(MyConfig.TAG_CAT_ID, (String.valueOf(cursor.getInt(0))));
                SiteMap.put(MyConfig.TAG_LEVEL, (String.valueOf(cursor.getInt(1))));
                SiteMap.put(MyConfig.TAG_PARENT, (String.valueOf(cursor.getInt(2))));
                SiteMap.put(MyConfig.TAG_LABEL, cursor.getString(3));
                SiteMap.put(MyConfig.TAG_CHILD, (String.valueOf(cursor.getInt(4))));
                SiteMap.put(MyConfig.TAG_A_ID, (String.valueOf(cursor.getInt(5))));
                SiteMap.put(MyConfig.TAG_IMAGE1, cursor.getString(6));
                SiteMap.put(MyConfig.TAG_NOTES, cursor.getString(7));


                SiteMapArrayList.add(SiteMap);
            } while (cursor.moveToNext());
        }

        dtabase.close();

        return SiteMapArrayList;

    }


    public HashMap<String, String> getMapItem(int projID, int aId) {


        HashMap<String, String> SiteMap = new HashMap<String, String>();
        ArrayList<HashMap<String, String>> SiteMapArrayList;

        SiteMapArrayList = new ArrayList<HashMap<String, String>>();


        SQLiteDatabase dtabase = this.getReadableDatabase();

        String selectQuery = "SELECT M." + COLUMN_CAT_ID + ", M." + COLUMN_LEVEL + ", M." + COLUMN_PARENT + ", M." + COLUMN_LABEL
                + ", M." + COLUMN_CHILD + ", M." + COLUMN_A_ID + ", M." + COLUMN_IMG1 + ", M." + COLUMN_NOTES//CASE WHEN A."+COLUMN_SUB_LOCATION_ID+" = 0 THEN 0 ELSE 1 END AS 'LEVEL'"

                + " FROM " + TABLE_MAP + " M"

                + " WHERE M." + COLUMN_PROJECT_ID + " = " + projID + " AND M." + COLUMN_A_ID + " = " + aId;

        //add additional fields: status,  notes, print flag
        Cursor cursor = dtabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SiteMap = new HashMap<String, String>();

                SiteMap.put(MyConfig.TAG_CAT_ID, (String.valueOf(cursor.getInt(0))));
                SiteMap.put(MyConfig.TAG_LEVEL, (String.valueOf(cursor.getInt(1))));
                SiteMap.put(MyConfig.TAG_PARENT, (String.valueOf(cursor.getInt(2))));
                SiteMap.put(MyConfig.TAG_LABEL, cursor.getString(3));
                SiteMap.put(MyConfig.TAG_CHILD, (String.valueOf(cursor.getInt(4))));
                SiteMap.put(MyConfig.TAG_A_ID, (String.valueOf(cursor.getInt(5))));
                SiteMap.put(MyConfig.TAG_IMAGE1, cursor.getString(6));
                SiteMap.put(MyConfig.TAG_NOTES, cursor.getString(7));


                SiteMapArrayList.add(SiteMap);
            } while (cursor.moveToNext());
        }

        dtabase.close();

        return SiteMap;

    }


    //Get a list of locations to upload to server
    public ArrayList<HashMap<String, String>> getActions() {


        HashMap<String, String> ActionItemMap;
        ArrayList<HashMap<String, String>> locationList;

        locationList = new ArrayList<HashMap<String, String>>();

        SQLiteDatabase dtabase = this.getReadableDatabase();

        String selectQuery = "SELECT * "
                + " FROM " + TABLE_ACTION_ITEM
                + " ORDER BY " + COLUMN_A_ID;
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

        dtabase.close();


        return locationList;

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
        if (CAT_TAB.equals("E")) CAT_TAB = TABLE_E_OR;

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


    public ArrayList<HashMap<String, String>> getInspectedItems(int projId, int iId) {

        // ArrayList that contains every row in the database
        // and each row key / value stored in a HashMap

        ArrayList<HashMap<String, String>> inspectedItemsList;

        inspectedItemsList = new ArrayList<HashMap<String, String>>();

        SQLiteDatabase dbase = this.getReadableDatabase();


        //First query sets out the zones and the related esential measures in those zones


        String selectQuery = "SELECT  " + COLUMN_CAT_ID + " , " + COLUMN_LABEL +
                " FROM " + TABLE_MAP +

                " WHERE " + COLUMN_PROJECT_ID + " = " + projId + " AND " + COLUMN_LEVEL + " = "+0+" ORDER BY " + COLUMN_CAT_ID;

        Cursor cursor = dbase.rawQuery(selectQuery, null);
        int i = 1;
        // Move to the first row
        if (cursor.moveToFirst()) {
            do {

                int catId = cursor.getInt(0);
                String BranchHead = cursor.getString(1);

                String selectQueryB = "SELECT  I." + COLUMN_OVERVIEW + ", I." + COLUMN_RELEVANT_INFO + ", I." + COLUMN_NOTES +
                        ", I." + COLUMN_IMG1 + ", I." + COLUMN_COM1 + ", I." + COLUMN_IMG2 + ", I." + COLUMN_COM2 + ", I." + COLUMN_IMG3 + ", I." + COLUMN_COM3
                        + ", I." + COLUMN_IMG4 + ", I." + COLUMN_COM4 + ", I." + COLUMN_IMG5 + ", I." + COLUMN_COM5 + ", M." + COLUMN_LABEL
                        + " FROM " + TABLE_INSPECTION_ITEM + " I " +
                        " JOIN " + TABLE_MAP + " M ON M." + COLUMN_A_ID + " = I." + COLUMN_A_ID + " AND M." + COLUMN_PROJECT_ID + " = I." + COLUMN_PROJECT_ID +

                        " WHERE I." + COLUMN_PROJECT_ID + " = " + projId + " AND I." + COLUMN_INSPECTION_ID + " = " + iId + " AND M." + COLUMN_CAT_ID + " = " + catId +
                        " ORDER BY I." + COLUMN_A_ID;

                Cursor cursorB = dbase.rawQuery(selectQueryB, null);

                // Move to the first row

                if (cursorB.moveToFirst()) {
                    do {
                        HashMap<String, String> inspectionItemMap = new HashMap<String, String>();

                        // Store the key / value pairs in a HashMap
                        // Access the Cursor data by index that is in the same order
                        // as query
                        inspectionItemMap.put("BranchHead", BranchHead);
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
                    } while (cursorB.moveToNext()); // Move Cursor to the next row
                }


            } while (cursor.moveToNext());
        }

        return inspectedItemsList;
    }
}

