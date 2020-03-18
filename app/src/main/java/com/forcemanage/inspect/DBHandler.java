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

/**
 * Created by DAP on 12/03/2020.
 */

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
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
    public static final String COLUMN_INSPECTION_STATUS = "ESMInspectionStatus";
    public static final String COLUMN_INSPECTION_TYPE = "InspectionType";

    public static final String TABLE_INSPECTION_ITEM = "InspectionItem";
    public static final String TABLE_ACTION_ITEM = "ActionItem";

    public static final String COLUMN_DATE_TIME_START = "DateTimeStart";
    public static final String COLUMN_DATE_TIME_FINISH = "DateTimeFinish";
    public static final String COLUMN_DATE = "Date";
    public static final String COLUMN_OVERVIEW = "ESMInspectionObservation";
    public static final String COLUMN_RELEVANT_INFO = "ESMRecommendation";
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
    public static final String COLUMN_CHILD= "Child";
    public static final String COLUMN_A_ID= "aID";


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
                + COLUMN_NOTE + " TEXT,"
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
                + COLUMN_DATE_TIME_FINISH + " INTEGER"+ ")";

        db.execSQL(CREATE_INSPECTION_TABLE);


        String CREATE_INSPECTION_ITEM_TABLE = "CREATE TABLE " +
                TABLE_INSPECTION_ITEM + "("
                + COLUMN_INSPECTION_ID + " INTEGER,"
                + COLUMN_PROJECT_ID + " INTEGER,"
                + COLUMN_A_ID + " INTEGER,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_OVERVIEW + " TEXT,"
                + COLUMN_RELEVANT_INFO + " TEXT,"
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
                + "PRIMARY KEY " + "(" + COLUMN_INSPECTION_ID + "," + COLUMN_PROJECT_ID + "," + COLUMN_A_ID +"))";
        //If ESM recommendation  is not nil print photo on report
        db.execSQL(CREATE_INSPECTION_ITEM_TABLE);


        String CREATE_ACTION_ITEM_TABLE = "CREATE TABLE " +
                TABLE_ACTION_ITEM + "("
                + COLUMN_INSPECTION_ID + " INTEGER,"
                + COLUMN_PROJECT_ID + " INTEGER,"
                + COLUMN_A_ID + " INTEGER,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_OVERVIEW + " TEXT,"
                + COLUMN_RELEVANT_INFO + " TEXT,"
                + COLUMN_SERVICE_LEVEL + " TEXT,"
                + COLUMN_REPORT_IMAGE + " TEXT,"
                + COLUMN_IMG1 + " TEXT,"
                + COLUMN_COM1 + " TEXT,"
                + COLUMN_ITEM_STATUS + " TEXT,"
                + COLUMN_NOTES + " TEXT,"
                + "PRIMARY KEY " + "(" + COLUMN_INSPECTION_ID + "," + COLUMN_PROJECT_ID + "," + COLUMN_A_ID +"))";
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
    public void updateFromServer( ProjectAttributes projectAttributes, InspectionAttributes inspectionAttributes, InspectionItemAttributes inspectionItemAttributes ) {
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
        valuesInspect.put(COLUMN_DATE_TIME_START, inspectionAttributes.getstartDateTime());
        valuesInspect.put(COLUMN_DATE_TIME_FINISH, inspectionAttributes.getendDateTime());

        db.replace(TABLE_INSPECTION, null, valuesInspect);



        ContentValues valuesItem = new ContentValues();
        valuesItem.put(COLUMN_INSPECTION_ID, inspectionAttributes.getinspectionId());
        valuesItem.put(COLUMN_PROJECT_ID, inspectionAttributes.getprojectId());
        valuesItem.put(COLUMN_A_ID, inspectionItemAttributes.getaId());
        valuesItem.put(COLUMN_DATE, inspectionItemAttributes.getdate());
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
    public void updateAdditionalFromServer( MAPattributes mapAttributes ) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PROJECT_ID, mapAttributes.get_projectId());
        values.put(COLUMN_CAT_ID, mapAttributes.get_catId());
        values.put(COLUMN_LEVEL, mapAttributes.get_level());
        values.put(COLUMN_PARENT, mapAttributes.get_parent());
        values.put(COLUMN_LABEL, mapAttributes.get_label());
        values.put(COLUMN_CHILD, mapAttributes.get_child());
        values.put(COLUMN_A_ID, mapAttributes.get_aId());
        values.put(COLUMN_IMG1, mapAttributes.get__image1());
        values.put(COLUMN_NOTES, mapAttributes.get_note());

        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("delete from "+ TABLE_ASSET_REGISTER);
        //replace will delete the row if the asset ID already exists
        db.replace(TABLE_MAP, null, values);

         db.close();
    }


    public void update_OR_FromServer( A_Attributes a_attributes, String CAT) {

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
            case "B":
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
        }
        db.close();
    }


    public  void updateInspection(int jId, int aId, int rId, String nextServiceDate, String inspectionObservation, String inspectionRecommendation, String ServiceLevel
            , String ServicedBy, String tag, String Img1, String Img2, String Img3, String Img4, String Img5, String ItemStatus, String Notes){
        String jobId = String.valueOf(jId);
        String assetId = String.valueOf(aId);
        String recommendId = String.valueOf(rId);



        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NEXT_DATE_SERVICE, nextServiceDate);
        contentValues.put(COLUMN_ESM_INSPECTION_OBSERVATION, inspectionObservation);
        contentValues.put(COLUMN_ESM_RECOMMENDATION, inspectionRecommendation);
        contentValues.put(COLUMN_SERVICE_LEVEL, ServiceLevel);
        contentValues.put(COLUMN_SERVICED_BY, ServicedBy);
        contentValues.put(COLUMN_REPORT_IMAGE,tag);
        contentValues.put(COLUMN_IMG1,Img1);
        contentValues.put(COLUMN_IMG2,Img2);
        contentValues.put(COLUMN_IMG3,Img3);
        contentValues.put(COLUMN_IMG4,Img4);
        contentValues.put(COLUMN_IMG5,Img5);
        contentValues.put(COLUMN_ITEM_STATUS,ItemStatus);
        contentValues.put(COLUMN_NOTES,Notes);



        db.update(TABLE_ESM_INSPECTION_ITEM, contentValues, COLUMN_JOB_ID + " = ? AND "+COLUMN_ASSET_ID + " = ? AND "+COLUMN_RECOMMEND_NO + " = ?", new String[]{jobId, assetId, recommendId});
        db.close();

    }



    //start inspection (clone previous inspection and put status "p": in progress)
    public void createNewInspection(int propertyId,int jobId) {
        // Open a database for reading and writing
        SQLiteDatabase database = this.getWritableDatabase();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;
        String jobStatus = " ";
        int prevJobId = 0;
        String testmessage = "Start";

        // SELECT * FROM `ESMInspection` WHERE JobId = 1067
        selectQuery= "SELECT "+COLUMN_ESM_INSPECTION_STATUS+", "+COLUMN_PREV_JOB_ID
                +" FROM "+TABLE_ESM_INSPECTION
                +" WHERE "+COLUMN_JOB_ID+" = "+jobId;

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
    }


    public void updatePropPhoto(String propertyId, String photo) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_PROPERTY_PHOTO, photo);
        db.update(TABLE_PROPERTY_INFO, values, COLUMN_PROPERTY_ID + " = " + propertyId, null);

    }

    public void updateStatus(String jobId, String status, String date) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_ESM_INSPECTION_STATUS, status);
        values.put(COLUMN_ESM_JOB_DATE, date);

        db.update(TABLE_ESM_INSPECTION, values, COLUMN_JOB_ID + " = " + jobId, null);

    }

    public void updateassetreg(String propId, int aId, String locId, String sublocId, String catId, String subcatId, String adesc,
                               String reg, String freq) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // values.put(COLUMN_PROPERTY_ID, propId);
        // values.put(COLUMN_ASSET_ID, aId);
        // values.put(COLUMN_LOCATION_ID, locId);
        values.put(COLUMN_SUB_LOCATION_ID, sublocId);
        values.put(COLUMN_CATEGORY_ID, catId);
        values.put(COLUMN_SUB_CATEGORY_ID, subcatId);
        values.put(COLUMN_ASSET_DESCRIPTION, adesc);
        values.put(COLUMN_REGULATION, reg);
        values.put(COLUMN_FREQUENCY, freq);

        // db.update(TABLE_ASSET_REGISTER, values, COLUMN_PROPERTY_ID + " = " + propId+" AND "+
        //                  COLUMN_ASSET_ID+" = "+aId,null);

        db.update(TABLE_ASSET_REGISTER, values, COLUMN_PROPERTY_ID + " = " + propId+" AND "+
                COLUMN_ASSET_ID+" = "+aId+" AND "+COLUMN_LOCATION_ID+" = "+locId, null);

    }

    public void updatelocations(String propId, String locId, String sublocId, String locdesc, int aid, int rid) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // values.put(COLUMN_PROPERTY_ID, propId);
        // values.put(COLUMN_ASSET_ID, aId);
        // values.put(COLUMN_LOCATION_ID, locId);
        // values.put(COLUMN_SUB_LOCATION_ID, sublocId);
        if(sublocId.equals("0")) {
            values.put(COLUMN_LOCATION_DESCRIPTION, locdesc);
            db.update(TABLE_PROPERTY_LOCATIONS, values, COLUMN_PROPERTY_ID + " = " + propId + " AND " +
                    COLUMN_LOCATION_ID + " = " + locId + " AND " +
                    COLUMN_SUB_LOCATION_ID + " = " + sublocId, null);
        } else {
            values.put(COLUMN_ITEM_NAME, locdesc);
            values.put(COLUMN_NOTES, locdesc);
            db.update(TABLE_ESM_INSPECTION_ITEM, values, COLUMN_PROPERTY_ID + " = " + propId + " AND " +
                    COLUMN_ASSET_ID + " = " + aid + " AND " +
                    COLUMN_RECOMMEND_NO + " = " + rid, null);
        }
    }

    public void deleteInspectionItem(Integer jId, Integer aId, Integer rId) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Cursor cursor;
        String selectQuery = "SELECT "+COLUMN_RECOMMEND_NO+" FROM "
                +TABLE_ESM_INSPECTION_ITEM
                +" WHERE "+COLUMN_JOB_ID+" = "+jId+" AND "+COLUMN_ASSET_ID+" = "+aId+" AND "+ COLUMN_RECOMMEND_NO+" > "+rId;

        if(rId > 1) {
            db.delete(TABLE_ESM_INSPECTION_ITEM,COLUMN_JOB_ID+" = "+ jId +" AND "+COLUMN_ASSET_ID+" = "+ aId +" AND "+COLUMN_RECOMMEND_NO+" = "+rId,null);
            db.close();
            db = this.getWritableDatabase();
            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    values.put(COLUMN_RECOMMEND_NO,(cursor.getInt(0))-1);
                    db.update(TABLE_ESM_INSPECTION_ITEM, values, COLUMN_JOB_ID + " = " + jId + " AND " +
                            COLUMN_ASSET_ID + " = " + aId +" AND "+ COLUMN_RECOMMEND_NO+" = "+ cursor.getInt(0) , null);
                } while (cursor.moveToNext()); // Move Cursor to the next row
            }

        }

        if(rId == 1) db.delete(TABLE_ESM_INSPECTION_ITEM,COLUMN_JOB_ID+" = "+ jId +" AND "+COLUMN_ASSET_ID+" = "+ aId, null);
        db.close();


    }

    public void deleteLocation(Integer propId, String locationId) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_PROPERTY_LOCATIONS,COLUMN_PROPERTY_ID+" = "+ propId +" AND "+COLUMN_LOCATION_ID+" = "+ locationId,null);
        db.delete(TABLE_ASSET_REGISTER,COLUMN_PROPERTY_ID+" = "+ propId +" AND "+COLUMN_LOCATION_ID+" = "+ locationId,null);


    }

    public void deleteAsset(Integer propId, int aId) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_ASSET_REGISTER,COLUMN_PROPERTY_ID+" = "+ propId +" AND "+
                COLUMN_ASSET_ID+" = "+aId ,null);

    }


    public void deletePhoto(Integer jId, Integer aId, Integer rId, Integer Col) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        String column = "";

        switch (Col){
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

        selectQuery = "UPDATE "+TABLE_ESM_INSPECTION_ITEM+" SET "
                +column
                +" = '' WHERE "+ COLUMN_JOB_ID+" = "+jId+"  AND "+COLUMN_ASSET_ID+" = "+aId
                +"  AND "+COLUMN_RECOMMEND_NO+" = "+rId;

        cursor = db.rawQuery(selectQuery, null);
    }

    //Get the current max AssetID Number for the property
    public int sublocationitems(int propId, String locationId, String sublocatioId) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery;
        Cursor cursor;
        int maxrecNo = 0;

        selectQuery = "SELECT MAX("+COLUMN_ASSET_ID+") FROM "
                +TABLE_ASSET_REGISTER
                +" WHERE "+ COLUMN_PROPERTY_ID+" = "+propId+"  AND "+COLUMN_LOCATION_ID+" = "+locationId
                +"  AND "+COLUMN_SUB_LOCATION_ID+" = "+sublocatioId;

        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            maxrecNo = cursor.getInt(0);

        }
        return maxrecNo;
    }

    public String zone(int propId, String locationId) {
        // Open a database for reading and writing
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery;
        Cursor cursor;
        String propZone = "Zone not registered";


        selectQuery = "SELECT "+COLUMN_LOCATION_DESCRIPTION+" FROM "
                +TABLE_PROPERTY_LOCATIONS
                +" WHERE "+ COLUMN_PROPERTY_ID+" = "+propId+"  AND "+COLUMN_LOCATION_ID+" = "+locationId
                +"  AND "+COLUMN_SUB_LOCATION_ID+" = 0 ";

        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            propZone = cursor.getString(0);

        }
        if (propZone.equals(null)) propZone = "Zone not registered for this location";

        return propZone;
    }



    public String addPrimlocation(String propId, String locationDesc) {
        // Open a database for reading and writing

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;
        int maxlocation = 0;


        selectQuery = "SELECT MAX(E2."+COLUMN_LOCATION_ID+") FROM "
                +TABLE_PROPERTY_LOCATIONS+" E2"
                +" WHERE E2."+ COLUMN_PROPERTY_ID+" = "+propId;

        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            maxlocation = cursor.getInt(0);
            maxlocation = maxlocation+1;
        }

        ContentValues values = new ContentValues();

        values.put(COLUMN_PROPERTY_ID, propId);
        values.put(COLUMN_LOCATION_ID, maxlocation);
        values.put(COLUMN_SUB_LOCATION_ID, 0);
        values.put(COLUMN_LOCATION_DESCRIPTION, locationDesc);

        db.insert(TABLE_PROPERTY_LOCATIONS,null,values);

        return Integer.toString(maxlocation);

    }

    public  void addESM(String propId, String jobId, String locationId, String sublocationId, String esmdesc, String esmcat, String esmId, String esmsubId) {
        // Open a database for reading and writing

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;




        // if an ESM category exists (1,1   1,2 )

        selectQuery = "SELECT "+COLUMN_ASSET_ID+" FROM "
                +TABLE_ASSET_REGISTER
                +" WHERE "+ COLUMN_PROPERTY_ID+" = '"+propId+"' AND "
                + COLUMN_LOCATION_ID+" = '"+locationId+"' AND "
                + COLUMN_CATEGORY_ID+" = '"+esmId+"' AND "
                + COLUMN_SUB_CATEGORY_ID+" = 'z'";

        cursor = db.rawQuery(selectQuery, null);


        //check if the esm category already exists. If not create it.

        if(cursor.getCount() == 0){
            addSublocation(propId, locationId, "1", esmcat, true);
            int assettId = addItem(propId, locationId, "1", esmId, "z", esmcat);
            addObservationItem(propId, jobId, assettId, esmcat);
            addSublocation(propId, locationId,"2", esmdesc, true);
            assettId = addItem(propId, locationId, "2",  esmId, esmsubId, esmdesc );
            addObservationItem(propId, jobId, assettId, esmdesc);
        }
        else {
            db.close();
            db = this.getWritableDatabase();

            selectQuery = "SELECT " + COLUMN_SUB_LOCATION_ID + " FROM "
                    + TABLE_PROPERTY_LOCATIONS
                    + " WHERE " + COLUMN_PROPERTY_ID+" = "+propId+" AND "
                    + COLUMN_LOCATION_ID + " = '" + locationId+"'"
                    + " AND " + COLUMN_SUB_LOCATION_ID + " = '2'";

            cursor = db.rawQuery(selectQuery, null);

            if (cursor.getCount() == 0) {
                addSublocation(propId, locationId,"2", esmdesc, true);
            }
            db.close();
            db = this.getWritableDatabase();

            selectQuery= "SELECT " + COLUMN_CATEGORY_ID + ", " + COLUMN_SUB_CATEGORY_ID + " FROM "
                    + TABLE_ASSET_REGISTER
                    + " WHERE " + COLUMN_PROPERTY_ID+" = "+propId+" AND "
                    + COLUMN_LOCATION_ID + " = '" + locationId+"' AND "
                    + COLUMN_CATEGORY_ID + " = '" + esmId+"'"
                    + " AND " + COLUMN_SUB_CATEGORY_ID + " = '" + esmsubId+"'";

            cursor = db.rawQuery(selectQuery,null);

            if (cursor.getCount() > 0) {
                //     Toast.makeText(InspectionActivit, "No additional items to Principal Zones (only positions)",Toast.LENGTH_SHORT).show();
            }
            else {

                //   int assettId = addItem(propId, locationId, "1",  esmId, "z",esmcat );
                //   addObservationItem(propId, jobId, assettId, locationDesc);


                int assettId = addItem(propId, locationId, "2",  esmId, esmsubId, esmdesc );
                addObservationItem(propId, jobId, assettId, esmdesc);
            }


        }
    }
    //Add sublocation to the location
    public void addSublocation(String propId, String locationId,  String sublocationId, String locationDesc, Boolean add ) {
        // Open a database for reading and writing

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;


        if(add == true) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_PROPERTY_ID, propId);
            values.put(COLUMN_LOCATION_ID, locationId);
            values.put(COLUMN_SUB_LOCATION_ID, sublocationId);
            values.put(COLUMN_LOCATION_DESCRIPTION, locationDesc);
            db.insert(TABLE_PROPERTY_LOCATIONS, null, values);
            db.close();
        }



    }

    // creates an AssetID for a new inspection item
    public int addItem(String propId, String locationId, String sublocationId, String esmId, String esmsubId, String esmcat) {
        // Open a database for reading and writing

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;
        int maxAssetId =1;


        selectQuery = "SELECT MAX(E2."+COLUMN_ASSET_ID+") FROM "
                +TABLE_ASSET_REGISTER+" E2"
                +" WHERE E2."+ COLUMN_PROPERTY_ID+" = "+propId;  //+" AND E2."+COLUMN_LOCATION_ID+" = "+locationId;

        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            maxAssetId = cursor.getInt(0);
            maxAssetId = maxAssetId+1;
        }

        ContentValues assetvalues = new ContentValues();

        assetvalues.put(COLUMN_PROPERTY_ID, propId);
        assetvalues.put(COLUMN_ASSET_ID, maxAssetId);
        assetvalues.put(COLUMN_LOCATION_ID, locationId);
        assetvalues.put(COLUMN_SUB_LOCATION_ID, sublocationId);
        assetvalues.put(COLUMN_CATEGORY_ID, esmId);
        assetvalues.put(COLUMN_SUB_CATEGORY_ID, esmsubId);
        assetvalues.put(COLUMN_ASSET_DESCRIPTION, esmcat);

        db.insert(TABLE_ASSET_REGISTER,null,assetvalues);

        db.close();

        //  int  maxvalues[] = new int[]{maxSublocation, maxAssetId};

        return maxAssetId;

    }



    public Integer addObservationItem(String PropId, String jobId, int assetId, String item) {
        // Open a database for reading and writing

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;
        int maxRecommendNo = 1;

        selectQuery = "SELECT MAX(E2."+COLUMN_RECOMMEND_NO+") FROM "
                +TABLE_ESM_INSPECTION_ITEM+" E2"
                +" WHERE E2."+ COLUMN_JOB_ID+" = "+jobId+" AND E2."+COLUMN_ASSET_ID+" = "+assetId;

        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            maxRecommendNo = cursor.getInt(0);
            maxRecommendNo = maxRecommendNo+1;
        }

        ContentValues values = new ContentValues();

        values.put(COLUMN_JOB_ID, jobId);
        values.put(COLUMN_PROPERTY_ID,PropId);
        values.put(COLUMN_ASSET_ID, assetId);
        values.put(COLUMN_RECOMMEND_NO, maxRecommendNo);
        values.put(COLUMN_ESM_INSPECTION_OBSERVATION,"");
        values.put(COLUMN_ESM_RECOMMENDATION,"");
        values.put(COLUMN_SERVICE_LEVEL,"1");
        values.put(COLUMN_SERVICED_BY,"");
        values.put(COLUMN_REPORT_IMAGE,"0");
        values.put(COLUMN_IMG1,"");
        values.put(COLUMN_IMG2,"");
        values.put(COLUMN_IMG3,"");
        values.put(COLUMN_IMG4,"");
        values.put(COLUMN_IMG5,"");
        values.put(COLUMN_ITEM_STATUS,"");
        values.put(COLUMN_NOTES,"");
        values.put(COLUMN_ITEM_NAME,item);


        db.insert(TABLE_ESM_INSPECTION_ITEM,null,values);
        db.close();


        return maxRecommendNo;

    }


    //Check if a location contains any sublocations
    public int itemNum(String propId, int assetId) {
        // Open a database for reading and writing

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;



        selectQuery = "SELECT * FROM "
                +TABLE_ESM_INSPECTION_ITEM+" I"
                +" WHERE I."+ COLUMN_PROPERTY_ID+" = "+propId+" AND I."+COLUMN_ASSET_ID+" = "+assetId;

        cursor = db.rawQuery(selectQuery, null);


        //  int  maxvalues[] = new int[]{maxSublocation, maxAssetId};

        return cursor.getCount();

    }


    //Check if a location contains any sublocations
    public String getStatus(String jobId, String propId) {
        // Open a database for reading and writing

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;



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




    }


    // Retrieve individual current inspection
    public HashMap <String, String> getInspection(int propertyId, int jobId, int assetId, String locationId, String sublocationId, int rId ) {
        // Open a database for reading and writing
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;


        selectQuery = "SELECT P."+COLUMN_PROPERTY_ID+", E."+COLUMN_CATEGORY_ID+", E."+COLUMN_CATEGORY_NAME+", E."+COLUMN_SUB_CATEGORY_ID+", E."+COLUMN_SUB_CATEGORY_NAME+", PL."+COLUMN_LOCATION_DESCRIPTION
                +", I."+COLUMN_NOTES+", I."+COLUMN_ESM_INSPECTION_OBSERVATION+", I."+COLUMN_ESM_RECOMMENDATION+", I."+COLUMN_RECOMMEND_NO+", I."+COLUMN_SERVICED_BY
                +", I."+COLUMN_IMG1+", I."+COLUMN_IMG2+", I."+COLUMN_IMG3+", I."+COLUMN_IMG4+", I."+COLUMN_IMG5
                +", I."+COLUMN_REPORT_IMAGE+", I."+COLUMN_ITEM_NAME+" , I."+COLUMN_ITEM_STATUS
                +" FROM "+TABLE_PROPERTY_INFO+" P"
                +" JOIN "+TABLE_ESM_INSPECTION+" E1"
                +" ON P."+COLUMN_PROPERTY_ID+" = E1."+COLUMN_PROPERTY_ID
                +" JOIN "+TABLE_ESM_INSPECTION_ITEM+" I"
                +" ON E1."+COLUMN_JOB_ID+" = I."+COLUMN_JOB_ID
                +" JOIN "+TABLE_ASSET_REGISTER+" A"
                +" ON P."+COLUMN_PROPERTY_ID+" = A."+COLUMN_PROPERTY_ID+" AND A."+COLUMN_ASSET_ID+" = I."+COLUMN_ASSET_ID
                +" JOIN "+TABLE_ESM_CATEGORIES+" E ON E."+COLUMN_CATEGORY_ID+" = A."+COLUMN_CATEGORY_ID+" AND E."+COLUMN_SUB_CATEGORY_ID+" = A."+COLUMN_SUB_CATEGORY_ID
                +" JOIN "+TABLE_PROPERTY_LOCATIONS+" PL ON PL."+COLUMN_LOCATION_ID+" = A."+COLUMN_LOCATION_ID+" AND PL."+COLUMN_SUB_LOCATION_ID+" = A."+COLUMN_SUB_LOCATION_ID
                +" AND PL."+COLUMN_PROPERTY_ID+" = P."+COLUMN_PROPERTY_ID
                +" WHERE I."+COLUMN_ASSET_ID+" = "+assetId+" AND PL."+COLUMN_LOCATION_ID+" = "+locationId+" AND PL."+COLUMN_SUB_LOCATION_ID+" = "+sublocationId
                +" AND A."+COLUMN_PROPERTY_ID+" = "+propertyId+" AND I."+COLUMN_JOB_ID+" = "+jobId+" AND I."+COLUMN_RECOMMEND_NO+" = "+rId
                +" ORDER BY A."+COLUMN_LOCATION_ID+", A."+COLUMN_SUB_LOCATION_ID;


        cursor = database.rawQuery(selectQuery, null);

        HashMap<String, String> inspectionItemMap = new HashMap<String, String>();

        // Move to the first row

        if (cursor.moveToFirst()) {
            // Store the key / value pairs in a HashMap
            // Access the Cursor data by index that is in the same order
            // as query

            inspectionItemMap.put(MyConfig.TAG_PROPERTY_ID, (String.valueOf(cursor.getInt(0))));
            inspectionItemMap.put(MyConfig.TAG_CATEGORY_ID, cursor.getString(1));
            inspectionItemMap.put(MyConfig.TAG_CATEGORY_NAME, cursor.getString(2));
            inspectionItemMap.put(MyConfig.TAG_SUB_CATEGORY_ID, cursor.getString(3));
            inspectionItemMap.put(MyConfig.TAG_SUB_CATEGORY_NAME, cursor.getString(4));
            inspectionItemMap.put(MyConfig.TAG_LOCATION_DESC, cursor.getString(5));
            inspectionItemMap.put(MyConfig.TAG_NOTES, cursor.getString(6));
            inspectionItemMap.put(MyConfig.TAG_INSPECTION_OBSERVATION, cursor.getString(7));
            inspectionItemMap.put(MyConfig.TAG_INSPECTION_RECOMMENDATION, cursor.getString(8));
            inspectionItemMap.put(MyConfig.TAG_RECOMMEND_NO, (String.valueOf(cursor.getInt(9))));
            inspectionItemMap.put(MyConfig.TAG_SERVICED_BY, cursor.getString(10));
            inspectionItemMap.put(MyConfig.TAG_IMAGE1, cursor.getString(11));
            inspectionItemMap.put(MyConfig.TAG_IMAGE2, cursor.getString(12));
            inspectionItemMap.put(MyConfig.TAG_IMAGE3, cursor.getString(13));
            inspectionItemMap.put(MyConfig.TAG_IMAGE4, cursor.getString(14));
            inspectionItemMap.put(MyConfig.TAG_IMAGE5, cursor.getString(15));
            inspectionItemMap.put(MyConfig.TAG_REPORT_IMAGE, cursor.getString(16));
            inspectionItemMap.put(MyConfig.TAG_ITEM_NAME, cursor.getString(17));
            inspectionItemMap.put(MyConfig.TAG_ITEM_STATUS, cursor.getString(18));

            //    inspectionItemMap.put("RecordNumber", (Integer.toString(inspectionItemMap.size())));


        }

        // return inspection data for propertyid, Jobid, inspection id
        database.close();
        return inspectionItemMap;
    }


    //retrieve individual last inspection
    public HashMap<String, String> getLastInspection(int propertyId,int jobId, int assetId, int recommendNo, String Sequence){

        // Open a database for reading and writing
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;
        int maxRecommendNo = 1;
        int maxAssetId = 1;

        // Cursor provides read and write access for the
        // data returned from a database query
        // rawQuery executes the query and returns the result as a Cursor

        if(Sequence == "prev"){
            if (recommendNo > 1){
                recommendNo = recommendNo - 1;
            }
            else if(assetId > 1) {
                assetId = assetId - 1;
            }
            else if (recommendNo == 1 && assetId == 1){
                //find highests assetId and within that the highest recomendNo
                selectQuery= "SELECT MAX(E2."+COLUMN_ASSET_ID+")"
                        +" FROM "+TABLE_ESM_INSPECTION_ITEM+" E2"
                        +" JOIN "+TABLE_ESM_INSPECTION+" E1"
                        +" ON E1."+COLUMN_PREV_JOB_ID+" = E2."+COLUMN_JOB_ID
                        +" WHERE E1."+COLUMN_JOB_ID+" = "+jobId;

                cursor = database.rawQuery(selectQuery, null);

                if (cursor.moveToFirst()){
                    assetId = cursor.getInt(0);



                    selectQuery = "SELECT MAX(E2."+COLUMN_RECOMMEND_NO+") FROM "
                            +TABLE_ESM_INSPECTION_ITEM+" E2"
                            +" JOIN "+TABLE_ESM_INSPECTION+" E1"
                            +" ON E1."+COLUMN_PREV_JOB_ID+" = E2."+COLUMN_JOB_ID
                            +" WHERE E1."+ COLUMN_JOB_ID+" = "+jobId+" AND E2."+COLUMN_ASSET_ID+" = "+assetId;

                    cursor = database.rawQuery(selectQuery, null);
                    if (cursor.moveToFirst()) {
                        recommendNo = cursor.getInt(0);
                    }
                }
            }
        }else if(Sequence == "next"){
            selectQuery = "SELECT MAX(E2."+COLUMN_RECOMMEND_NO+") FROM "
                    +TABLE_ESM_INSPECTION_ITEM+" E2"
                    +" JOIN "+TABLE_ESM_INSPECTION+" E1"
                    +" ON E1."+COLUMN_PREV_JOB_ID+" = E2."+COLUMN_JOB_ID
                    +" WHERE E1."+ COLUMN_JOB_ID+" = "+jobId+" AND E2."+COLUMN_ASSET_ID+" = "+assetId;

            cursor = database.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                maxRecommendNo = cursor.getInt(0);
            }
            if (recommendNo < maxRecommendNo){
                recommendNo = recommendNo +1;
            }
            else if(recommendNo == maxRecommendNo) {
                //check if there are more assets otherwise go to beginning
                selectQuery= "SELECT MAX(E2."+COLUMN_ASSET_ID+")"
                        +" FROM "+TABLE_ESM_INSPECTION_ITEM+" E2"
                        +" JOIN "+TABLE_ESM_INSPECTION+" E1"
                        +" ON E1."+COLUMN_PREV_JOB_ID+" = E2."+COLUMN_JOB_ID
                        +" WHERE E1."+COLUMN_JOB_ID+" = "+jobId;

                cursor = database.rawQuery(selectQuery, null);

                if (cursor.moveToFirst()){
                    maxAssetId = cursor.getInt(0);
                }
                if (assetId < maxAssetId)
                    //go to next assetId
                    assetId = assetId +1;
                else {
                    assetId = 1;
                    recommendNo = 1;
                }
            }
        }




//use (current)jobId to get previous job information by joining two rows of the inspection item table
        selectQuery = "SELECT P."+COLUMN_PROPERTY_ID+", P."+COLUMN_ADDRESS_NUMBER+", P."+COLUMN_PROPERTY_ADDRESS+", P."+
                COLUMN_PROPERTY_SUBURB+", P."+COLUMN_PROPERTY_NOTE+", E2."+COLUMN_ESM_INSPECTION_OBSERVATION+", E2."+COLUMN_SERVICED_BY+", E2."+COLUMN_ESM_RECOMMENDATION
                +", A."+COLUMN_ASSET_DESCRIPTION+", EC."+COLUMN_CATEGORY_NAME+", EC."+COLUMN_SUB_CATEGORY_NAME+", PL."+COLUMN_LOCATION_DESCRIPTION+", PL."+COLUMN_LOCATION_ID
                +", E2."+COLUMN_ASSET_ID+", E2."+COLUMN_RECOMMEND_NO
                +" FROM "+TABLE_PROPERTY_INFO+" P"
                +" JOIN "+TABLE_ESM_INSPECTION+" E1"
                +" ON P."+COLUMN_PROPERTY_ID+" = E1."+COLUMN_PROPERTY_ID
                +" JOIN "+TABLE_ESM_INSPECTION_ITEM+" E2"
                +" ON E1."+COLUMN_PREV_JOB_ID+" = E2."+COLUMN_JOB_ID
                +" JOIN "+TABLE_ASSET_REGISTER+" A"
                +" ON P."+COLUMN_PROPERTY_ID+" = A."+COLUMN_PROPERTY_ID+" AND A."+COLUMN_ASSET_ID+" = E2."+COLUMN_ASSET_ID
                +" JOIN "+TABLE_ESM_CATEGORIES+" EC ON EC."+COLUMN_CATEGORY_ID+" = A."+COLUMN_CATEGORY_ID+" AND EC."+COLUMN_SUB_CATEGORY_ID+" = A."+COLUMN_SUB_CATEGORY_ID
                +" JOIN "+TABLE_PROPERTY_LOCATIONS+" PL ON PL."+COLUMN_LOCATION_ID+" = A."+COLUMN_LOCATION_ID+" AND PL."+COLUMN_SUB_LOCATION_ID+" = A."+COLUMN_SUB_LOCATION_ID
                +" AND PL."+COLUMN_PROPERTY_ID+" = P."+COLUMN_PROPERTY_ID
                +" WHERE P."+COLUMN_PROPERTY_ID+" = "+propertyId+" AND E1."+COLUMN_JOB_ID+" = "+jobId+" AND E2."+COLUMN_ASSET_ID+" = "+assetId+" AND E2."+COLUMN_RECOMMEND_NO+" = "+recommendNo;

        cursor = database.rawQuery(selectQuery, null);

        HashMap<String, String> inspectionItemMap = new HashMap<String, String>();

        // Move to the first row

        if (cursor.moveToFirst()) {
            // Store the key / value pairs in a HashMap
            // Access the Cursor data by index that is in the same order
            // as query

            inspectionItemMap.put(MyConfig.TAG_PROPERTY_ID, (String.valueOf(cursor.getInt(0))));
            inspectionItemMap.put(MyConfig.TAG_ADDRESS_NO, cursor.getString(1));
            inspectionItemMap.put(MyConfig.TAG_PROPERTY_ADDRESS, cursor.getString(2));
            inspectionItemMap.put(MyConfig.TAG_PROPERTY_SUBURB, cursor.getString(3));
            inspectionItemMap.put(MyConfig.TAG_PROPERTY_NOTE, cursor.getString(4));
            inspectionItemMap.put(MyConfig.TAG_INSPECTION_OBSERVATION, cursor.getString(5));
            inspectionItemMap.put(MyConfig.TAG_SERVICED_BY, cursor.getString(6));
            inspectionItemMap.put(MyConfig.TAG_INSPECTION_RECOMMENDATION, cursor.getString(7));
            inspectionItemMap.put(MyConfig.TAG_ASSET_DESCRIPTION, cursor.getString(8));
            inspectionItemMap.put(MyConfig.TAG_CATEGORY_NAME, cursor.getString(9));
            inspectionItemMap.put(MyConfig.TAG_SUB_CATEGORY_NAME, cursor.getString(10));
            inspectionItemMap.put(MyConfig.TAG_LOCATION_DESC, cursor.getString(11));
            inspectionItemMap.put(MyConfig.TAG_LOCATION_ID, (String.valueOf(cursor.getInt((12)))));
            inspectionItemMap.put(MyConfig.TAG_ASSET_ID, (String.valueOf(cursor.getInt((13)))));
            inspectionItemMap.put(MyConfig.TAG_RECOMMEND_NO, (String.valueOf(cursor.getInt(14))));

        }

        // return inspection data for propertyid, Jobid, inspection id
        database.close();
        return inspectionItemMap;
    }


    public ArrayList<HashMap<String, String>> getAllProjects() {

        // ArrayList that contains every row in the database
        // and each row key / value stored in a HashMap

        ArrayList<HashMap<String, String>> propertyArrayList;

        propertyArrayList = new ArrayList<HashMap<String, String>>();

        String selectQuery = "SELECT "+
                TABLE_PROJECT_INFO+"."+COLUMN_PROJECT_ID+", "+COLUMN_ADDRESS_NUMBER+", "+COLUMN_PROJECT_ADDRESS+", "+COLUMN_PROJECT_SUBURB
                +", "+COLUMN_INSPECTION_ID+", "+COLUMN_INSPECTION_TYPE+", "+COLUMN_PROJECT_PHOTO+", "+COLUMN_INSPECTION_STATUS
                +" FROM "+TABLE_PROJECT_INFO
                +" INNER JOIN "+TABLE_INSPECTION
                +" ON "+TABLE_PROJECT_INFO+"."+COLUMN_PROJECT_ID+" = "+TABLE_INSPECTION+"."+COLUMN_PROJECT_ID
                +" WHERE "+COLUMN_INSPECTION_STATUS+" = 'n' OR "+COLUMN_INSPECTION_STATUS+" = 'p' OR "+COLUMN_INSPECTION_STATUS+" = 'c'"
                +" ORDER BY "+COLUMN_PROJECT_ADDRESS;



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

                propertyMap.put(MyConfig.TAG_PROPERTY_ID, (String.valueOf(cursor.getInt(0))));
                propertyMap.put(MyConfig.TAG_ADDRESS_NO, cursor.getString(1));
                propertyMap.put(MyConfig.TAG_PROPERTY_ADDRESS, cursor.getString(2));
                propertyMap.put(MyConfig.TAG_PROPERTY_SUBURB, cursor.getString(3));
                propertyMap.put(MyConfig.TAG_JOB_ID, cursor.getString(4));
                propertyMap.put(MyConfig.TAG_JOB_TYPE, cursor.getString(5));
                propertyMap.put(MyConfig.TAG_PROPERTY_PHOTO, cursor.getString(6));
                propertyMap.put(MyConfig.TAG_JOB_STATUS, cursor.getString(7));

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

        String selectQuery = "SELECT I."+
                COLUMN_IMG1+", I."+COLUMN_IMG2+", I."+COLUMN_IMG3+", I."+COLUMN_IMG4+", I."+COLUMN_IMG5
                +" FROM "+TABLE_ESM_INSPECTION_ITEM+" I JOIN "+TABLE_ESM_INSPECTION+" E ON E."+COLUMN_JOB_ID+" = I."+COLUMN_JOB_ID
                +" WHERE "+COLUMN_ESM_INSPECTION_STATUS+" = 'c' OR "+COLUMN_ESM_INSPECTION_STATUS+" = 'p'";

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


                if(cursor.getString(0) != "") photoMap.put(MyConfig.TAG_IMAGE1, cursor.getString(0));
                if(cursor.getString(1) != "") photoMap.put(MyConfig.TAG_IMAGE2, cursor.getString(1));
                if(cursor.getString(2) != "") photoMap.put(MyConfig.TAG_IMAGE3, cursor.getString(2));
                if(cursor.getString(3) != "") photoMap.put(MyConfig.TAG_IMAGE4, cursor.getString(3));
                if(cursor.getString(4) != "") photoMap.put(MyConfig.TAG_IMAGE5, cursor.getString(4));

                photoArrayList.add( photoMap);
            } while (cursor.moveToNext()); // Move Cursor to the next row
        }
        cursor.close();

        // return contact list
        return photoArrayList;
    }



    public ArrayList<HashMap<String, String>> getInspectedItems(String jobId, String propId) {

        // ArrayList that contains every row in the database
        // and each row key / value stored in a HashMap

        ArrayList<HashMap<String, String>> inspectedItemsList;

        inspectedItemsList = new ArrayList<HashMap<String, String>>();




        //First query sets out the zones and the related esential measures in those zones


        String selectQuery = "SELECT  C."+ COLUMN_CATEGORY_NAME+" AS 'SAFETY_MEASURE', I."+COLUMN_ESM_RECOMMENDATION+", A."+COLUMN_LOCATION_ID+",A."+COLUMN_SUB_LOCATION_ID+
                ", A."+COLUMN_CATEGORY_ID+",I."+COLUMN_NEXT_DATE_SERVICE+", A."+COLUMN_FREQUENCY+


                ",CASE WHEN A."+COLUMN_SUB_LOCATION_ID+" = 0 THEN L."+COLUMN_LOCATION_DESCRIPTION+" END AS 'ZONE', "+
                "CASE WHEN A."+COLUMN_SUB_LOCATION_ID+" = 1 THEN C."+COLUMN_CATEGORY_NAME+" ELSE '' END AS 'ESM' ,"+
                "CASE WHEN A."+COLUMN_SUB_LOCATION_ID+" = 2 THEN C."+COLUMN_SUB_CATEGORY_NAME+" ELSE '' END AS 'ESMcat' ,"+
                "CASE WHEN A."+COLUMN_SUB_LOCATION_ID+" = 2 AND I."+COLUMN_ESM_RECOMMENDATION+" = 'Nil' THEN 'OK'"+
                "WHEN A."+COLUMN_SUB_LOCATION_ID+" = 2 AND I."+COLUMN_ESM_RECOMMENDATION+" != 'Nil' THEN I."+COLUMN_ESM_RECOMMENDATION+" END AS 'STATUS'"+

                " FROM "+TABLE_ESM_INSPECTION_ITEM+" I "+
                " JOIN "+ TABLE_ESM_INSPECTION+" EI ON I."+COLUMN_JOB_ID+" = EI."+COLUMN_JOB_ID+
                " JOIN "+ TABLE_ASSET_REGISTER+" A ON A."+COLUMN_PROPERTY_ID+" = EI."+COLUMN_PROPERTY_ID+" AND A."+COLUMN_ASSET_ID+" = I."+COLUMN_ASSET_ID+
                " JOIN "+ TABLE_ESM_CATEGORIES+" C ON A."+COLUMN_CATEGORY_ID+" = C."+COLUMN_CATEGORY_ID+" AND A."+COLUMN_SUB_CATEGORY_ID+" = C."+COLUMN_SUB_CATEGORY_ID+
                " JOIN "+ TABLE_PROPERTY_LOCATIONS+" L ON L."+COLUMN_PROPERTY_ID+" = A."+COLUMN_PROPERTY_ID+" AND L."+ COLUMN_LOCATION_ID+" = A."+COLUMN_LOCATION_ID+
                " AND L."+ COLUMN_SUB_LOCATION_ID+ " = A."+COLUMN_SUB_LOCATION_ID+
                " JOIN "+ TABLE_PROPERTY_INFO+" P ON P."+COLUMN_PROPERTY_ID+"= EI."+COLUMN_PROPERTY_ID+

                " WHERE (A."+ COLUMN_CATEGORY_ID+ " = 'Z' OR A."+COLUMN_CATEGORY_ID+" = 'A') AND I."+COLUMN_PROPERTY_ID+" = "+propId+" AND I."+COLUMN_RECOMMEND_NO+
                " = 1 ORDER BY A."+COLUMN_LOCATION_ID+", A."+COLUMN_SUB_LOCATION_ID+", A."+COLUMN_CATEGORY_ID+", A."+COLUMN_SUB_CATEGORY_ID;



        String selectQueryB = "SELECT  L."+ COLUMN_LOCATION_DESCRIPTION+", I."+COLUMN_ESM_INSPECTION_OBSERVATION+", I."+COLUMN_ESM_RECOMMENDATION+
                ", I."+COLUMN_IMG1+", I."+COLUMN_IMG2+", I."+COLUMN_ITEM_NAME+


                " FROM "+TABLE_ESM_INSPECTION_ITEM+" I "+
                " JOIN "+ TABLE_ASSET_REGISTER+" A ON A."+COLUMN_PROPERTY_ID+" = I."+COLUMN_PROPERTY_ID+" AND A."+COLUMN_ASSET_ID+" = I."+COLUMN_ASSET_ID+
                " JOIN "+ TABLE_PROPERTY_LOCATIONS+" L ON L."+COLUMN_PROPERTY_ID+" = A."+COLUMN_PROPERTY_ID+" AND L."+ COLUMN_LOCATION_ID+" = A."+COLUMN_LOCATION_ID+
                " AND L."+ COLUMN_SUB_LOCATION_ID+ " = 0"+

                " WHERE (A."+ COLUMN_CATEGORY_ID+ " = 'Z' OR A."+COLUMN_CATEGORY_ID+" = 'A') AND I."+COLUMN_PROPERTY_ID+" = "+propId+" AND I."+COLUMN_RECOMMEND_NO+
                " > 1 AND I."+COLUMN_ESM_RECOMMENDATION+" != 'Nil'";




        // Open a database for reading and writing



        SQLiteDatabase database = this.getWritableDatabase();

        // Cursor provides read and write access for the
        // data returned from a database query

        // rawQuery executes the query and returns the result as a Cursor

        Cursor cursor = database.rawQuery(selectQuery, null);

        // Move to the first row

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> inspectionItemMap = new HashMap<String, String>();

                // Store the key / value pairs in a HashMap
                // Access the Cursor data by index that is in the same order
                // as query

                inspectionItemMap.put("SAFETY_MEASURE", cursor.getString(0));
                inspectionItemMap.put(MyConfig.TAG_INSPECTION_RECOMMENDATION, cursor.getString(1));
                inspectionItemMap.put(MyConfig.TAG_LOCATION_ID, (String.valueOf(cursor.getInt(2))));
                inspectionItemMap.put(MyConfig.TAG_SUB_LOCATION_ID, (String.valueOf(cursor.getInt(3))));
                inspectionItemMap.put(MyConfig.TAG_CATEGORY_ID, (String.valueOf(cursor.getInt(4))));
                inspectionItemMap.put(MyConfig.TAG_NEXT_SERVICE_DATE, cursor.getString(5));
                inspectionItemMap.put(MyConfig.TAG_FREQUENCY, cursor.getString(6));
                inspectionItemMap.put("ZONE", cursor.getString(7));
                inspectionItemMap.put("ESM", cursor.getString(8));
                inspectionItemMap.put("ESM_CAT", cursor.getString(9));
                inspectionItemMap.put("STATUS", cursor.getString(10));



                inspectedItemsList.add(inspectionItemMap);
            } while (cursor.moveToNext()); // Move Cursor to the next row
        }


        Cursor cursorB = database.rawQuery(selectQueryB, null);

        // Move to the first row

        if (cursorB.moveToFirst()) {
            do {
                HashMap<String, String> inspectionItemMap = new HashMap<String, String>();

                // Store the key / value pairs in a HashMap
                // Access the Cursor data by index that is in the same order
                // as query

                inspectionItemMap.put(MyConfig.TAG_LOCATION_DESC, cursorB.getString(0));
                inspectionItemMap.put(MyConfig.TAG_INSPECTION_OBSERVATION, cursorB.getString(1));
                inspectionItemMap.put(MyConfig.TAG_INSPECTION_RECOMMENDATION, cursorB.getString(2));
                inspectionItemMap.put(MyConfig.TAG_IMAGE1, cursorB.getString(3));
                inspectionItemMap.put(MyConfig.TAG_IMAGE2, cursorB.getString(4));
                inspectionItemMap.put(MyConfig.TAG_ITEM_NAME, cursorB.getString(5));

                inspectedItemsList.add(inspectionItemMap);

            } while (cursorB.moveToNext()); // Move Cursor to the next row
        }


        // return contact list
        return inspectedItemsList;
    }

    //Select inspection items for syncing to server
    public ArrayList<HashMap<String, String>> getInspections(){
        HashMap<String, String> inspectionsMap = new HashMap<String, String>();
        ArrayList<HashMap<String, String>> inspectionArrayList = new ArrayList<>();


        SQLiteDatabase dtabase = this.getReadableDatabase();

        String selectQuery = "SELECT E1."+COLUMN_INSPECTION_ID+", E1."+COLUMN_INSPECTION_DATE+", E1."+COLUMN_INSPECTION_STATUS
                +" FROM "+TABLE_INSPECTION+" E1 ORDER BY "+COLUMN_INSPECTION_ID+" ASC";
        Cursor cursor = dtabase.rawQuery(selectQuery, null);
        // Move to the first row
        if (cursor.moveToFirst()) {
            do {
                inspectionsMap = new HashMap<String,String>();
                inspectionsMap.put(MyConfig.TAG_INSPECTION_ID, (String.valueOf(cursor.getInt(0))));
                inspectionsMap.put (MyConfig.TAG_INSPECTION_DATE, cursor.getString(1));
                inspectionsMap.put (MyConfig.TAG_INSPECTION_STATUS, cursor.getString(2));
                inspectionsMap.put (MyConfig.TAG_START_DATE_TIME, cursor.getString(3));
                inspectionsMap.put (MyConfig.TAG_END_DATE_TIME, cursor.getString(4));
                inspectionArrayList.add(inspectionsMap);

            } while (cursor.moveToNext());
        }

        dtabase.close();
        return inspectionArrayList;

    }


    public ArrayList<HashMap<String, String>> getSiteMap(){
        HashMap<String, String> SiteMap = new HashMap<String, String>();
        ArrayList<HashMap<String, String>> AssArrayList = new ArrayList<>();


        SQLiteDatabase dtabase = this.getReadableDatabase();

        String selectQuery = "SELECT * "
                +" FROM "+TABLE_MAP+" ORDER BY "+ MyConfig.TAG_PROJECT_ID;

        Cursor cursor = dtabase.rawQuery(selectQuery, null);

        // Move to the first row
        if (cursor.moveToFirst()) {
            do {
                SiteMap = new HashMap<String,String>();
                SiteMap.put(MyConfig.TAG_PROJECT_ID, (String.valueOf(cursor.getInt(0))));
                SiteMap.put (MyConfig.TAG_CAT_ID, (String.valueOf(cursor.getInt(1))));
                SiteMap.put (MyConfig.TAG_LEVEL, (String.valueOf(cursor.getInt(2))));
                SiteMap.put (MyConfig.TAG_PARENT, (String.valueOf(cursor.getInt(3))));
                SiteMap.put (MyConfig.TAG_LABEL, cursor.getString(4));
                SiteMap.put (MyConfig.TAG_CHILD, cursor.getString(5));
                SiteMap.put (MyConfig.TAG_A_ID, cursor.getString(6));
                SiteMap.put (MyConfig.TAG_IMAGE1, cursor.getString(7));
                SiteMap.put (MyConfig.TAG_NOTES, cursor.getString(8));
                AssArrayList.add(SiteMap);
            } while (cursor.moveToNext());
        }

        dtabase.close();
        return AssArrayList;

    }

    //Select inspection items for syncing to server
    public ArrayList<HashMap<String, String>> getInspectionItems(){
        HashMap<String, String> inspectionItemMap;
        ArrayList<HashMap<String, String>> inspectionItemsArrayList;

        inspectionItemsArrayList = new ArrayList<HashMap<String, String>>();


        SQLiteDatabase dtabase = this.getReadableDatabase();

        String selectQuery = "SELECT E1."+COLUMN_INSPECTION_ID+", E1."+COLUMN_A_ID+", E1."+COLUMN_INSPECTION_DATE+", E1."
                +COLUMN_OVERVIEW+", E1."+COLUMN_RELEVANT_INFO+", E1."+ COLUMN_SERVICE_LEVEL+", E1."
                +COLUMN_SERVICED_BY+", E1."+COLUMN_IMG1+",E1."+COLUMN_COM1+", E1."+COLUMN_IMG2+",E1."+COLUMN_COM2+", E1."+COLUMN_IMG3
                +",E1."+COLUMN_COM3+", E1."+COLUMN_IMG4+", E1."+COLUMN_COM4+",E1."+ COLUMN_IMG5+",E1."+COLUMN_COM5+", E1."+COLUMN_IMG6
                +", E1."+COLUMN_COM6+",E1."+COLUMN_IMG7+", E1."+COLUMN_COM7+",E2."+COLUMN_PROJECT_ID+", E1."+COLUMN_NOTES
                +", E2."+COLUMN_INSPECTION_STATUS+", E1."+COLUMN_REPORT_IMAGE
                +" FROM "+TABLE_INSPECTION_ITEM+" E1"
                +" JOIN "+TABLE_INSPECTION+" E2"
                +" ON E1."+COLUMN_PROJECT_ID+" = E2."+COLUMN_INSPECTION_ID
                +" ORDER BY E1."+COLUMN_INSPECTION_ID;

        // +" JOIN "+TABLE_ASSET_REGISTER+" A"
        // +" ON E1."+COLUMN_JOB_ID+" = E2."+COLUMN_JOB_ID+" AND E2."+COLUMN_PROPERTY_ID+" = A."+COLUMN_PROPERTY_ID
        //         +" ORDER BY E1."+COLUMN_ASSET_ID;

        //add additional fields: status,  notes, print flag
        Cursor cursor = dtabase.rawQuery(selectQuery, null);

        // Move to the first row
        if (cursor.moveToFirst()) {
            do {
                inspectionItemMap = new HashMap<String, String>();
                inspectionItemMap.put(MyConfig.TAG_INSPECTION_ID, (String.valueOf(cursor.getInt(0))));
                inspectionItemMap.put(MyConfig.TAG_A_ID, (String.valueOf(cursor.getInt(1))));
                inspectionItemMap.put (MyConfig.TAG_INSPECTION_DATE, cursor.getString(2));
                inspectionItemMap.put (MyConfig.TAG_OVERVIEW, cursor.getString(3));
                inspectionItemMap.put (MyConfig.TAG_RELEVANT_INFO, cursor.getString(4));
                inspectionItemMap.put (MyConfig.TAG_SERVICE_LEVEL, cursor.getString(5));
                inspectionItemMap.put (MyConfig.TAG_SERVICED_BY, cursor.getString(6));
                inspectionItemMap.put (MyConfig.TAG_IMAGE1, cursor.getString(7));
                inspectionItemMap.put (MyConfig.TAG_COM1, cursor.getString(8));
                inspectionItemMap.put (MyConfig.TAG_IMAGE2, cursor.getString(9));
                inspectionItemMap.put (MyConfig.TAG_COM2, cursor.getString(10));
                inspectionItemMap.put (MyConfig.TAG_IMAGE3, cursor.getString(11));
                inspectionItemMap.put (MyConfig.TAG_COM3, cursor.getString(12));
                inspectionItemMap.put (MyConfig.TAG_IMAGE4, cursor.getString(13));
                inspectionItemMap.put (MyConfig.TAG_COM4, cursor.getString(14));
                inspectionItemMap.put (MyConfig.TAG_IMAGE5, cursor.getString(15));
                inspectionItemMap.put (MyConfig.TAG_COM5, cursor.getString(16));
                inspectionItemMap.put (MyConfig.TAG_IMAGE6, cursor.getString(17));
                inspectionItemMap.put (MyConfig.TAG_COM6, cursor.getString(18));
                inspectionItemMap.put (MyConfig.TAG_IMAGE7, cursor.getString(19));
                inspectionItemMap.put (MyConfig.TAG_COM7, cursor.getString(20));
                inspectionItemMap.put (MyConfig.TAG_PROJECT_ID, (String.valueOf(cursor.getInt(21))));
                inspectionItemMap.put (MyConfig.TAG_NOTES, cursor.getString(22));
                inspectionItemMap.put (MyConfig.TAG_INSPECTION_STATUS, cursor.getString(23));
                inspectionItemMap.put (MyConfig.TAG_REPORT_IMAGE, cursor.getString(24));



                inspectionItemsArrayList.add(inspectionItemMap);
            } while (cursor.moveToNext());
        }

        dtabase.close();
        return inspectionItemsArrayList;

    }

    //Get a list of sublocations to populate the sub location spinner
    public ArrayList<HashMap<String, String>> getsubLocations(String propId, String aId){

        HashMap<String, String> sublocationItemMap;
        ArrayList<HashMap<String, String>> sublocationArrayList;

        sublocationArrayList = new ArrayList<HashMap<String, String>>();

        SQLiteDatabase dtabase = this.getReadableDatabase();

        String selectQuery = "SELECT "+COLUMN_ASSET_DESCRIPTION//+", "+COLUMN_LOCATION_DESCRIPTION
                +" FROM "+TABLE_ASSET_REGISTER
                +" WHERE "+COLUMN_PROPERTY_ID+" = "+ propId+" AND "+COLUMN_ASSET_ID+" = "+aId;

        //add additional fields: status,  notes, print flag
        Cursor cursor = dtabase.rawQuery(selectQuery, null);

        // Move to the first row
        if (cursor.moveToFirst()) {
            do {
                sublocationItemMap = new HashMap<String, String>();
                sublocationItemMap.put(MyConfig.TAG_ASSET_DESCRIPTION, cursor.getString(0));
                sublocationArrayList.add(sublocationItemMap);
            } while (cursor.moveToNext());
        }

        dtabase.close();

        return sublocationArrayList;

    }


    //Get a list of sublocations to populate the sub location spinner
    public ArrayList<HashMap<String, String>> getMap(String projectId){


        HashMap<String, String> SiteMap;
        ArrayList<HashMap<String, String>> SiteMapArrayList;

        SiteMapArrayList = new ArrayList<HashMap<String, String>>();



        SQLiteDatabase dtabase = this.getReadableDatabase();

        String selectQuery = "SELECT L."+COLUMN_CAT_ID+", L."+COLUMN_LEVEL+", I."+COLUMN_PARENT+", L."+COLUMN_LABEL
                +", L."+COLUMN_CHILD+", L."+COLUMN_A_ID+", L."+COLUMN_IMG1+", L."+COLUMN_NOTES//CASE WHEN A."+COLUMN_SUB_LOCATION_ID+" = 0 THEN 0 ELSE 1 END AS 'LEVEL'"

                +" FROM "+TABLE_MAP+" L"

                +" WHERE I."+COLUMN_PROJECT_ID+" = "+ projectId//+" AND (A."+COLUMN_CATEGORY_ID+" = 'A' OR A."+COLUMN_CATEGORY_ID+" = 'Z')"
                +" ORDER BY A."+COLUMN_CAT_ID;
        //add additional fields: status,  notes, print flag
        Cursor cursor = dtabase.rawQuery(selectQuery, null);
        int i = 1;
        int ya =1;
        int yb =1;
        int yc =1;
        int yd =1;
        int ye =1;
        int yf =1;
        int n = 1;
        // Move to the first row
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

                if(cursor.getInt(1)==0){
                    i = cursor.getPosition()+1;
                    locationItemMap.put("parent","-1");
                    locationItemMap.put("level","0");}
                else {
                    switch (cursor.getString(5)){

                        case "A":

                            if (cursor.getString(6).equals("z")) {
                                ya = cursor.getPosition() + 1;
                                locationItemMap.put("parent", String.valueOf(i));
                                locationItemMap.put("level", "1");
                            } else {
                                if (cursor.getInt(2) > 1) {
                                    locationItemMap.put("parent", String.valueOf(n + 1 - cursor.getInt(2)));
                                    locationItemMap.put("level", "3");
                                } else {
                                    locationItemMap.put("parent", Integer.toString(ya));
                                    locationItemMap.put("level", "2");
                                }
                            }

                            break;
                        case "B":
                            if (cursor.getString(6).equals("z")) {
                                yb = cursor.getPosition() + 1;
                                locationItemMap.put("parent", String.valueOf(i));
                                locationItemMap.put("level", "1");
                            } else {
                                if (cursor.getInt(2) > 1) {
                                    locationItemMap.put("parent", String.valueOf(n + 1 - cursor.getInt(2)));
                                    locationItemMap.put("level", "3");
                                } else {
                                    locationItemMap.put("parent", Integer.toString(yb));
                                    locationItemMap.put("level", "2");
                                }
                            }
                            break;
                        case "C":
                            if (cursor.getString(6).equals("z")) {
                                yc = cursor.getPosition() + 1;
                                locationItemMap.put("parent", String.valueOf(i));
                                locationItemMap.put("level", "1");
                            } else {
                                if (cursor.getInt(2) > 1) {
                                    locationItemMap.put("parent", String.valueOf(n + 1 - cursor.getInt(2)));
                                    locationItemMap.put("level", "3");
                                } else {
                                    locationItemMap.put("parent", Integer.toString(yc));
                                    locationItemMap.put("level", "2");
                                }
                            }
                            break;
                        case "D":
                            if (cursor.getString(6).equals("z")) {
                                yd = cursor.getPosition() + 1;
                                locationItemMap.put("parent", String.valueOf(i));
                                locationItemMap.put("level", "1");
                            } else {
                                if (cursor.getInt(2) > 1) {
                                    locationItemMap.put("parent", String.valueOf(n + 1 - cursor.getInt(2)));
                                    locationItemMap.put("level", "3");
                                } else {
                                    locationItemMap.put("parent", Integer.toString(yd));
                                    locationItemMap.put("level", "2");
                                }
                            }
                            break;
                        case "E":
                            if (cursor.getString(6).equals("z")) {
                                ye = cursor.getPosition() + 1;
                                locationItemMap.put("parent", String.valueOf(i));
                                locationItemMap.put("level", "1");
                            } else {
                                if (cursor.getInt(2) > 1) {
                                    locationItemMap.put("parent", String.valueOf(n + 1 - cursor.getInt(2)));
                                    locationItemMap.put("level", "3");
                                } else {
                                    locationItemMap.put("parent", Integer.toString(ye));
                                    locationItemMap.put("level", "2");
                                }
                            }
                            break;
                        case "F":
                            if (cursor.getString(6).equals("z")) {
                                yf = cursor.getPosition() + 1;
                                locationItemMap.put("parent", String.valueOf(i));
                                locationItemMap.put("level", "1");
                            } else {
                                if (cursor.getInt(2) > 1) {
                                    locationItemMap.put("parent", String.valueOf(n + 1 - cursor.getInt(2)));
                                    locationItemMap.put("level", "3");
                                } else {
                                    locationItemMap.put("parent", Integer.toString(yf));
                                    locationItemMap.put("level", "2");
                                }
                            }
                            break;
                    }
                }
                n=n+1;
                locationArrayList.add(locationItemMap);
            } while (cursor.moveToNext());
        }





        dtabase.close();

        return locationArrayList;

    }


    //Get a list of locations to upload to server
    public ArrayList<HashMap<String, String>> getAllLocations(){


        HashMap<String, String> locationItemMap;
        ArrayList<HashMap<String, String>> locationList;

        locationList = new ArrayList<HashMap<String, String>>();



        SQLiteDatabase dtabase = this.getReadableDatabase();

        String selectQuery = "SELECT * "
                +" FROM "+TABLE_PROPERTY_LOCATIONS
                +" ORDER BY "+COLUMN_PROPERTY_ID;
        //add additional fields: status,  notes, print flag
        Cursor cursor = dtabase.rawQuery(selectQuery, null);

        // Move to the first row
        if (cursor.moveToFirst()) {
            do {
                locationItemMap = new HashMap<String, String>();
                locationItemMap.put(MyConfig.TAG_PROPERTY_ID, (String.valueOf(cursor.getInt(0))));
                locationItemMap.put (MyConfig.TAG_LOCATION_ID, (String.valueOf(cursor.getInt(1))));
                locationItemMap.put (MyConfig.TAG_SUB_LOCATION_ID, (String.valueOf(cursor.getInt(2))));
                locationItemMap.put (MyConfig.TAG_LOCATION_DESC,cursor.getString(3));

                locationList.add(locationItemMap);
            } while (cursor.moveToNext());
        }

        dtabase.close();

        return locationList;

    }

    public ArrayList<HashMap<String, String>> getinspectionitemlist(int propId, int jobId, int zone){

        HashMap<String, String> inspectionItemMap;
        ArrayList<HashMap<String, String>> inspectionitemsArrayList;

        String op = " = ";

        if (zone == 0) {
            op = " > ";

        }
        inspectionitemsArrayList = new ArrayList<HashMap<String, String>>();

        SQLiteDatabase dtabase = this.getReadableDatabase();

        String selectQuery = "SELECT A."+COLUMN_ASSET_ID+", A."+COLUMN_LOCATION_ID+", A."+COLUMN_SUB_LOCATION_ID+", I."+COLUMN_RECOMMEND_NO
                +" FROM "+TABLE_ESM_INSPECTION_ITEM+" I "
                +" JOIN "+TABLE_ASSET_REGISTER+" A"
                +" ON I."+COLUMN_ASSET_ID+" = A."+COLUMN_ASSET_ID
                +" JOIN "+TABLE_ESM_INSPECTION+" EI"
                +" ON I."+COLUMN_JOB_ID+" = EI."+COLUMN_JOB_ID
                +" WHERE A."+COLUMN_PROPERTY_ID+" = "+ propId+" AND I."+COLUMN_JOB_ID+" = "+jobId+" AND "+COLUMN_LOCATION_ID+op+zone
                +" ORDER BY "+COLUMN_LOCATION_ID+", "+COLUMN_SUB_LOCATION_ID;

        //add additional fields: status,  notes, print flag
        Cursor cursor = dtabase.rawQuery(selectQuery, null);

        // Move to the first row
        if (cursor.moveToFirst()) {
            do {
                inspectionItemMap = new HashMap<String, String>();
                inspectionItemMap.put(MyConfig.TAG_ASSET_ID, (String.valueOf(cursor.getInt(0))));
                inspectionItemMap.put(MyConfig.TAG_LOCATION_ID, cursor.getString(1));
                inspectionItemMap.put(MyConfig.TAG_SUB_LOCATION_ID, cursor.getString(2));
                inspectionItemMap.put(MyConfig.TAG_RECOMMEND_NO, cursor.getString(3));

                inspectionitemsArrayList.add(inspectionItemMap);
            } while (cursor.moveToNext());
        }

        dtabase.close();

        return inspectionitemsArrayList;

    }


    public ArrayList<HashMap<String, String>> getORlist(String CAT_TAB, int type, String subCat){

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
                + " WHERE " + COLUMN_TYPE + " = " + type + " AND " + COLUMN_SUB_CAT + " = '" + subCat+"'";


        //add additional fields: status,  notes, print flag
        Cursor cursor = dtabase.rawQuery(selectQuery, null);

        // Move to the first row
        if (cursor.moveToFirst()) {
            do {
                ORItemMap = new HashMap<String, String>();
                ORItemMap.put(MyConfig.TAG_NOTE,cursor.getString(0));
                ORArrayList.add(ORItemMap);
            } while (cursor.moveToNext());
        }

        dtabase.close();

        return ORArrayList;

    }

    public ArrayList<HashMap<String, String>> getZonesArray(String PropId){


        HashMap<String, String> locationItemMap;
        ArrayList<HashMap<String, String>> locationArrayList;

        locationArrayList = new ArrayList<HashMap<String, String>>();



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

        return locationArrayList;

    }

}
