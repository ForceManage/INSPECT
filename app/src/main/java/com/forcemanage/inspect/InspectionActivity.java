package com.forcemanage.inspect;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.forcemanage.inspect.adapters.MapListAdapter;
import com.forcemanage.inspect.attributes.MapViewData;
import com.forcemanage.inspect.attributes.MapViewNode;
import com.forcemanage.inspect.attributes.ReportItem;
import com.forcemanage.inspect.fragments.ActionItemFragment;
import com.forcemanage.inspect.fragments.BaseFragment;
import com.forcemanage.inspect.fragments.CertificateInspectionFragment;
import com.forcemanage.inspect.fragments.InspectInfoFragment;
import com.forcemanage.inspect.fragments.InspectionFragment;
import com.forcemanage.inspect.fragments.InspectionInfoFolderFragment;
import com.forcemanage.inspect.fragments.ReferenceFragment;
import com.forcemanage.inspect.fragments.ReportFragment;
import com.forcemanage.inspect.fragments.SummaryFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class InspectionActivity extends AppCompatActivity implements  tabchangelistener, tabchangelistener_2, View.OnClickListener {

    DBHandler ESMdb;
    private String projectId;
    private String inspectionId;
    private String seq = "cur";
    private static final int ACTIVITY_START_CAMERA_APP = 0;
    public static final int ACTIVITY_GET_FILE = 1;
    private static final int ACTIVITY_DRAW_FILE = 2;
    private static final int PICK_CONTACT = 3;

    private ImageView photoA;
    private ImageView photoB;
    private ImageView photoC;
    private ImageView photoD;
    private ImageView photoE;
    private ImageView photoF;
    private ImageView photoG;
    private ImageView tab_edit;
    private ImageView photo_draw;
    private ImageView photo_file;
    private ImageView photo_file2;
    private ImageView photo_file3;
    private ImageView addPage;
    private ImageView addSummary;
    private ImageView addCert;
    private ImageView addInfo;

    public ImageView mPhotoImageView;
    public String photoBranch;
    public String photo1;
    public String photo2;
    public String photo3;
    public String photo4;
    public String photo5;
    public String photo6;
    public String photo7;
    public String[] photos = new String[7];
    private String mImageFileLocation = "";
    private String fname;
    private String dirName;
    private View view;
    public int filephoto;
    public String propPhoto;


    public int photoframe;
    private File photo;
    public String com1 = "";
    public String com2 = "";
    public String com3 = "";
    public String com4 = "";
    public String com5 = "";
    public String com6 = "";
    public String com7 = "";
    public String Overview = "";
    public String relevantInfo = "";
    public String ItemStatus = "";
    public String Notes = "";
    private String aProvider = "Trade";
    private Integer USER_ID = 0;
    private String CLIENT = "no_client";


    //    private TextView Asset;
    private String cameraSnap;
    private EditText Note;
    private TextView ItemNumbers;
    private TextView docTitle;
    //  private String location;
    //  private TextView SubLocation;
    private String ESMtxt;
    private String itemlocation;
    private String[] locationsArr;
    private String[] sublocationsArr;
    private String[] iTitle;
    private String editing = "NO";
    private int catId;
    private String[] observations;
    private String[] recommendations;
    private Integer zone;
    private ArrayAdapter<String> adapter5;
    private ArrayAdapter<MapViewNode> aAdapter;
    private MapListAdapter treeListAdapter;
    private List<MapViewData> listItems;
    public List<ReportItem> reportlistItems;
    private int projId;
    public int aID = 1;
    public int Level = 0;
    public int Parent;
    public int Child;
    public int iID = 1;
    public String inspLabel = "Inspection";
    public String branchLabel = "Label";
    public String branchNote = "Note";
    private int branchCode = 0;
    public boolean Edited = false;
    private Fragment fragment_obj;
    private String FragDisplay;
    private Boolean logTime;
    private String startTime;
    private String endTime;
    private FloatingActionButton fab_add;
    private String Folder;




    //   private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ESMdb = new DBHandler(this, null, null, 1);
        setContentView(R.layout.activity_inspection);
        cameraSnap = "0";
        CLIENT = getIntent().getExtras().getString("CLIENT");
        USER_ID = getIntent().getExtras().getInt("USER_ID");
        projectId = getIntent().getExtras().getString("PROJECT_ID");
        inspectionId = getIntent().getExtras().getString("INSPECTION_ID");
        logTime = getIntent().getExtras().getBoolean("logTime");

        tab_edit = (ImageView) findViewById(R.id.image_edit);
        tab_edit.setOnClickListener(this);
        addPage = (ImageView) findViewById(R.id.add_Page);
        addPage.setOnClickListener(this);
        addSummary = (ImageView) findViewById(R.id.add_Summary);
        addSummary.setOnClickListener(this);
        addCert = (ImageView) findViewById(R.id.add_Cert);
        addCert.setOnClickListener(this);
        addInfo = (ImageView) findViewById(R.id.add_Info);
        addInfo.setOnClickListener(this);
        zone = 0;
        projId = Integer.parseInt(projectId);
        iID = Integer.parseInt(inspectionId);
        docTitle = (TextView) findViewById(R.id.docTitle);
        fab_add = (FloatingActionButton) findViewById(R.id.fab_add);
        fab_add.setOnClickListener(this);
      //  docTitle.setText(getIntent().getExtras().getString("DOC_NAME"));


     //   dateBtn = (Button) findViewById(R.id.btndate);
     //   dateBtn.setOnClickListener(this);




        photos[0] = "";

        startTime = dayTime(4);

        DBHandler dbHandlerA = new DBHandler(this, null, null, 1);
        dbHandlerA.updateInspectionItemdate();

        //  ItemNumbers = (TextView) findViewById(R.id.RecordCount);
        //  ItemNumbers.setText("Property has "+Integer.toString(itemNumbers.size())+" items.");


        init();

        ArrayList<HashMap<String, String>> SiteMapData = dbHandlerA.getMap(projId, iID, 15); //child < 15 includes all types

        listItems = new ArrayList<>();
        MapViewData listItem;

        for (int i = 0; i < (SiteMapData.size()); i++) {

            listItem = new MapViewData(
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_PROJECT_ID)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_LEVEL)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_CAT_ID)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_CHILD)),
                    SiteMapData.get(i).get(MyConfig.TAG_LABEL),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_A_ID)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_INSPECTION_ID)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_PARENT)),
                    SiteMapData.get(i).get(MyConfig.TAG_IMAGE1),
                    SiteMapData.get(i).get(MyConfig.TAG_NOTES)
            );
            listItems.add(listItem);
        }

        GlobalVariables.dataList = (ArrayList<MapViewData>) listItems;
        //   MapViewLists.LoadDisplayList();

        GlobalVariables.modified = false;


        if (findViewById(R.id.fragment_container) != null) {

            // However if we are being restored from a previous state, then we don't
            // need to do anything and should return or we could end up with overlapping Fragments
            //    if (savedInstanceState != null){
            //        return;
            //     }
//
            // Create an Instance of Fragment
            MapViewFragment treeFragment = new MapViewFragment();
            treeFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, treeFragment)
                    .commit();

        }



    }

    private void init() {


        DBHandler dbHandler = new DBHandler(this, null, null, 1);

        HashMap<String, String> projectItem = dbHandler.getInspection(projId, iID);
        Folder = projectItem.get(MyConfig.TAG_ADDRESS_NO);

        Bundle bundle = new Bundle();

        bundle.putString("branchHead", projectItem.get(MyConfig.TAG_ADDRESS_NO));
        bundle.putString("branchLabel", projectItem.get(MyConfig.TAG_LABEL));
        bundle.putInt("projectId", projId);
        bundle.putInt("inspectionId", iID);
        bundle.putString("date", projectItem.get(MyConfig.TAG_INSPECTION_DATE));
        bundle.putString("startTime", projectItem.get(MyConfig.TAG_START_DATE_TIME));
        bundle.putString("endTime", projectItem.get(MyConfig.TAG_END_DATE_TIME));
        bundle.putString("note", projectItem.get(MyConfig.TAG_NOTE));
        bundle.putString("note_2", projectItem.get(MyConfig.TAG_NOTE_2));
        bundle.putString("inpectType", projectItem.get(MyConfig.TAG_INSPECTION_TYPE));
        bundle.putString("auditor", projectItem.get(MyConfig.TAG_USER_ID));
        bundle.putString("inspPhoto", projectItem.get(MyConfig.TAG_IMAGE));


        InspectInfoFragment fragment = new InspectInfoFragment();
        doFragmentTransaction(fragment, "InspectInfoFragment", true, "");
        fragment.setArguments(bundle);

    }

    private String dayTime(int Type) {

        String daytime = "20000101";

        switch (Type) {

            case (1): {

                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyyMMdd");
                Date date_ = Calendar.getInstance().getTime();
                daytime = (dateFormat.format(date_));
                break;
            }

            case (2): {

                java.text.SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
                Date date_ = Calendar.getInstance().getTime();
                daytime = (dateFormat.format(date_));
                break;
            }

            case (3): {

                java.text.SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date_ = Calendar.getInstance().getTime();
                daytime = (dateFormat.format(date_));
                break;
            }

            case (4): {

                java.text.SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date_ = Calendar.getInstance().getTime();
                daytime = (dateFormat.format(date_));
                break;
            }
        }
        return daytime;
    }

    private void doFragmentTransaction(Fragment fragment, String name, boolean addToBackStack, String message) {
        androidx.fragment.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FragDisplay = name;
        transaction.replace(R.id.mainfragment_container, fragment, name);
        if (addToBackStack) {
            transaction.addToBackStack(name);
        }
        transaction.commit();
    }


    public void loadMap() {

        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        ArrayList<HashMap<String, String>> SiteMapData = dbHandler.getMap(projId, iID, 15);

        listItems = new ArrayList<>();
        MapViewData listItem;

        for (int i = 0; i < (SiteMapData.size()); i++) {

            listItem = new MapViewData(
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_PROJECT_ID)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_LEVEL)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_CAT_ID)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_CHILD)),
                    SiteMapData.get(i).get(MyConfig.TAG_LABEL),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_A_ID)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_INSPECTION_ID)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_PARENT)),
                    SiteMapData.get(i).get(MyConfig.TAG_IMAGE1),
                    SiteMapData.get(i).get(MyConfig.TAG_NOTES)
            );
            listItems.add(listItem);
        }


        GlobalVariables.dataList = (ArrayList<MapViewData>) listItems;
        GlobalVariables.modified = true;


        //    MapListAdapter mAdapter = new MapListAdapter(this);
        //   mAdapter.notifyDataSetChanged();

        OnTabChanged(GlobalVariables.pos);
    }


    @Override
    public void OnTab2Changed(int treeNameIndex) {


        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detail_text);

        MapViewNode node = GlobalVariables.displayNodes.get(GlobalVariables.pos);


        if (detailFragment != null) {
            // If description is available, we are in two pane layout
            // so we call the method in DescriptionFragment to update its content
            detailFragment.setDetail(treeNameIndex);

        } else {
            DetailFragment newDetailFragment = new DetailFragment();
            Bundle args = new Bundle();

            args.putInt(DetailFragment.KEY_POSITION, treeNameIndex);
            newDetailFragment.setArguments(args);


            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();


            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the backStack so the User can navigate back
            fragmentTransaction.replace(R.id.fragment_container, newDetailFragment);

            //  fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }

        if (GlobalVariables.modified == true) {
            MapViewFragment newDetailFragment = new MapViewFragment();
            Bundle args = new Bundle();
            detailFragment.mCurrentPosition = -1;


            args.putInt(DetailFragment.KEY_POSITION, treeNameIndex);

            newDetailFragment.setArguments(args);
            androidx.fragment.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the backStack so the User can navigate back
            fragmentTransaction.replace(R.id.fragment_container, newDetailFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            FragmentManager fm = getSupportFragmentManager();

            //fm.popBackStack(DF,0);
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStackImmediate();
            }

            // fm.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);

            GlobalVariables.modified = false;

            OnTab2Changed(GlobalVariables.pos);
        }

        aID = node.getaID();



        //  Toast.makeText(this, "BranchNote from Inspection Acvtivity: "+branchNote, Toast.LENGTH_SHORT).show();
        displayInspectionItem();
    }

    @Override
    public void OnTabChanged(int treeNameIndex) {


        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detail_text);


        if (detailFragment != null) {
            // If description is available, we are in two pane layout
            // so we call the method in DescriptionFragment to update its content
            detailFragment.setDetail(treeNameIndex);

        } else {
            DetailFragment newDetailFragment = new DetailFragment();
            Bundle args = new Bundle();

            args.putInt(DetailFragment.KEY_POSITION, treeNameIndex);
            newDetailFragment.setArguments(args);


            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();


            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the backStack so the User can navigate back
            fragmentTransaction.replace(R.id.fragment_container, newDetailFragment);

            //  fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }

        if (GlobalVariables.modified == true) {
            MapViewFragment newDetailFragment = new MapViewFragment();
            Bundle args = new Bundle();
            detailFragment.mCurrentPosition = -1;


            args.putInt(DetailFragment.KEY_POSITION, treeNameIndex);

            newDetailFragment.setArguments(args);
            androidx.fragment.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the backStack so the User can navigate back
            fragmentTransaction.replace(R.id.fragment_container, newDetailFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            FragmentManager fm = getSupportFragmentManager();

            //fm.popBackStack(DF,0);
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStackImmediate();
            }

            // fm.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);

            GlobalVariables.modified = false;

      //      OnTabChanged(GlobalVariables.pos);
        }



        aID = detailFragment.aID;

             //  Toast.makeText(this, "BranchNote from Inspection Acvtivity: "+branchNote, Toast.LENGTH_SHORT).show();
        displayInspectionItem();


    }


    private void getORArray(String Cat_Table, String subCat) {

        DBHandler dbHandler = new DBHandler(this, null, null, 1);


    }


    private void saveInspectionItem() {
        DBHandler dbHandler = new DBHandler(this, null, null, 1);

        // String serviceDate = inspectionDate.getText().toString();
        // work out the next service date in three months time
        if (FragDisplay == "BaseFragment") {
            dbHandler.updateBranchPhoto(projId, aID, photoBranch);
        }


        String date = dayTime(1);

        String ServiceLevel = "1";
        ItemStatus = "p";


        if (FragDisplay == "InspectionFragment") {
            dbHandler.updateInspectionItemPhoto(projId, iID, aID, photo1, photo2, photo3,photo4,
                    photo5, photo6, photo7);

        }

        if (FragDisplay == "ReferenceFragment") {
            dbHandler.updateInspectionItemPhoto(projId, iID, aID, photo1, photo2, photo3,photo4,
                    photo5, photo6, photo7);

        }

        if (FragDisplay == "ActionItemFragment") {
            dbHandler.updateActionItemPhoto(projectId, inspectionId, aID,  photo1);

        }

        String status = dbHandler.getStatus(iID, projId);

        //      dbHandler.updateStatus(projId, iID, "p", dayTime(1));

        Edited = false;

    }


    public void deleteInspectionItem() {

        DBHandler dbHandler = new DBHandler(this, null, null, 1);


        int type = dbHandler.getMapNodeType(projId, aID);

        switch (type){

            case(-1):{
                Toast.makeText(this, "Cannot delete Parent branch, delete child branches first", Toast.LENGTH_SHORT).show();

                break;
            }

            case (0): {
                dbHandler.deleteMapBranch(projId, aID);
                dbHandler.deleteRec("MAP",projId,iID,aID);
                dbHandler.deleteInspectionItem(projId, aID);
                dbHandler.deleteRec("InspectionItem",projId,iID,aID);
                GlobalVariables.pos = GlobalVariables.pos - 1;
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                loadMap();
                break;
            }
            case (1):{

                dbHandler.deleteMapBranch(projId, aID);
                dbHandler.deleteRec("MAP",projId,iID,aID);
                dbHandler.deleteInspectionItem(projId, aID);
                dbHandler.deleteRec("InspectionItem",projId,iID,aID);
                GlobalVariables.pos = GlobalVariables.pos - 1;
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                loadMap();
                break;
            }
            case (2):{

                dbHandler.deleteMapBranch(projId, aID);
                dbHandler.deleteRec("MAP",projId,iID,aID);
                dbHandler.deleteInspectionItem(projId, aID);
                dbHandler.deleteRec("InspectionItem",projId,iID,aID);
                dbHandler.deleteActionItem(projId,aID);
                dbHandler.deleteRec("ActionItem",projId,iID,aID);
                GlobalVariables.pos = GlobalVariables.pos - 1;
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                loadMap();
                break;
            }

            case (9):{

                dbHandler.deleteSummary(projId,iID,aID);
                dbHandler.deleteRec("Summary",projId,iID,aID);
                dbHandler.deleteMapBranch(projId, aID);
                dbHandler.deleteRec("MAP",projId,iID,aID);
                GlobalVariables.pos = GlobalVariables.pos - 1;
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                loadMap();
                break;
            }

            case (10):{

                dbHandler.deleteCertificate(projId,iID,aID);
                dbHandler.deleteRec("CertificateInsp",projId,iID,aID);
                dbHandler.deleteMapBranch(projId, aID);
                dbHandler.deleteRec("MAP",projId,iID,aID);
                GlobalVariables.pos = GlobalVariables.pos - 1;
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                loadMap();
                break;
            }
        }

     }


    public void deleteMapBranch() {

        DBHandler dbHandler = new DBHandler(this, null, null, 1);



    }


    private void addLevel(int Level, String levelName) {

        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        int result = dbHandler.addLevel(projId, aID, iID, catId, Level, aID, levelName, 0);  //this is the ESM category
        if (result == 0)
            Toast.makeText(this, "Cannot place TAB here", Toast.LENGTH_SHORT).show();
        else
            loadMap();
    }


    private void addReportBranch(int Level, String levelName) {

        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        int result = dbHandler.addReportBranch(projId, iID, catId, Level, aID, levelName);  //this is the ESM category

        if (result == 1)
            loadMap();
        else
            Toast.makeText(this, "Cannot place document TAB here", Toast.LENGTH_SHORT).show();

    }

    private void addActionBranch(int Level, String levelName) {

        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        int result = dbHandler.addActionBranch(projId, iID, catId, Level, aID, levelName);  //this is the ESM category

        if (result == 1)
            loadMap();
        else
            Toast.makeText(this, "Cannot place an Action TAB here", Toast.LENGTH_SHORT).show();

    }

    private void addCertificateBranch(int Level, String levelName) {

        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        int result = dbHandler.addCertificate(projId, iID, 501, 0, aID, levelName);  //this is the ESM category
        if (result == 0)
            Toast.makeText(this, "Cannot place Certificate TAB here", Toast.LENGTH_SHORT).show();
        else
            loadMap();

    }

    private void addSummaryBranch(int Level, String levelName) {

        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        int result = dbHandler.addSummary(projId, iID, 500, 0, aID, levelName);  //this is the ESM category
        if (result == 0)
            Toast.makeText(this, "Cannot place Summary TAB here", Toast.LENGTH_SHORT).show();
        else
            loadMap();

    }

    private void addReferenceBranch(int Level, String levelName) {

        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        int result = dbHandler.addReference(projId, iID, 502, 0, aID, levelName);  //this is the ESM category
        if (result == 0)
            Toast.makeText(this, "Cannot place Reference TAB here", Toast.LENGTH_SHORT).show();
        else
            loadMap();

    }

    public void editLocation(String branchLabel) {

        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        int success = dbHandler.updateMapLabel(projId, aID, branchLabel);
        if(success == 1) loadMap();
        else Toast.makeText(this, "Create/select TAB", Toast.LENGTH_SHORT).show();
    }


    public void displayInspectionItem() {

        //          ItemNumbers.setText("Zone : "+locationId+", Sublocat : "+sublocationId+",  Asset id : "+ aId);
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
     //   if(aID == 0) aID = 1;
        HashMap<String, String> mapItem = dbHandler.getMapItem(projId, aID, iID);

        String MapBranch;

        catId = Integer.parseInt(mapItem.get(MyConfig.TAG_CAT_ID));
        Level = Integer.parseInt(mapItem.get(MyConfig.TAG_LEVEL));
        Parent = Integer.parseInt(mapItem.get(MyConfig.TAG_PARENT));
        photoBranch = mapItem.get(MyConfig.TAG_IMAGE1);
        inspLabel = mapItem.get(MyConfig.TAG_LABEL); //This is the inspection label column
        branchLabel = mapItem.get("MAP_LABEL"); //This is the Map label column
        branchNote = mapItem.get(MyConfig.TAG_NOTES);
        branchCode = Integer.parseInt(mapItem.get(MyConfig.TAG_CHILD));

        String branchHead = dbHandler.getMapBranchTitle(projId, catId);

        switch (branchCode) {


            case 0: {

                if(Level == 0){

                    HashMap<String, String> projectItem = dbHandler.getInspection(projId, iID);

                    Bundle bundle = new Bundle();

                    bundle.putString("branchHead", projectItem.get(MyConfig.TAG_ADDRESS_NO));
                    bundle.putString("branchLabel", projectItem.get(MyConfig.TAG_LABEL));
                    bundle.putInt("projectId", projId);
                    bundle.putInt("inspectionId", iID);
                    bundle.putString("date", projectItem.get(MyConfig.TAG_INSPECTION_DATE));
                    bundle.putString("startTime", projectItem.get(MyConfig.TAG_START_DATE_TIME));
                    bundle.putString("endTime", projectItem.get(MyConfig.TAG_END_DATE_TIME));
                    bundle.putString("note", projectItem.get(MyConfig.TAG_NOTE));
                    bundle.putString("note_2", projectItem.get(MyConfig.TAG_NOTE_2));
                    bundle.putString("inpectType", projectItem.get(MyConfig.TAG_INSPECTION_TYPE));
                    bundle.putString("auditor", projectItem.get(MyConfig.TAG_USER_ID));
                    bundle.putString("inspPhoto", projectItem.get(MyConfig.TAG_IMAGE));


                    InspectInfoFragment fragment = new InspectInfoFragment();
                    doFragmentTransaction(fragment, "InspectInfoFragment", false, "");
                    fragment.setArguments(bundle);

                }
                else{
                Bundle bundle = new Bundle();
                bundle.putString("branchHead", branchHead);
                bundle.putString("inspection", inspLabel);
                bundle.putString("projectID", projectId);
                bundle.putString("inspectionID", inspectionId);
                bundle.putString("image", photoBranch);
                bundle.putString("MAP_LABEL", branchLabel);
                bundle.putInt("aID", aID);
                bundle.putString("notes", branchNote);
                bundle.putString("com2", com2);
                BaseFragment fragment = new BaseFragment();
                fragment.setArguments(bundle);

                doFragmentTransaction(fragment, "BaseFragment", false, "");
                 }
                 break;
            }
            case 1: {


                HashMap<String, String> list = dbHandler.getInspectionItem(projId, iID, aID);
                if(list.size() > 0) {
                    relevantInfo = list.get(MyConfig.TAG_RELEVANT_INFO);
                    Overview = list.get(MyConfig.TAG_OVERVIEW);
                    aProvider = list.get(MyConfig.TAG_SERVICED_BY);
                    com1 = list.get(MyConfig.TAG_COM1);
                    com2 = list.get(MyConfig.TAG_COM2);
                    com3 = list.get(MyConfig.TAG_COM3);
                    com4 = list.get(MyConfig.TAG_COM4);
                    com5 = list.get(MyConfig.TAG_COM5);
                    Notes = list.get(MyConfig.TAG_NOTES);
                    String dateInspected = list.get(MyConfig.TAG_DATE_INSPECTED);
                    String prntReport = list.get(MyConfig.TAG_REPORT_IMAGE);


                    Bundle bundle = new Bundle();
                    bundle.putString("projectID", projectId);
                    bundle.putString("inspectionID", inspectionId);
                    bundle.putInt("aID", aID);
                    bundle.putString("branchHead", branchHead);
                    bundle.putString("branchLabel", inspLabel);
                    bundle.putString("aprovider", aProvider);
                    bundle.putString("overview", Overview);
                    bundle.putString("date", dateInspected);
                    bundle.putString("relevantInfo", relevantInfo);
                    bundle.putString("notes", Notes);
                    bundle.putString("com1", com1);
                    bundle.putString("com2", com2);
                    bundle.putString("com3", com3);
                    bundle.putString("com4", com4);
                    bundle.putString("com5", com5);
                    bundle.putString("prnt", prntReport);


                    photos[0] = list.get(MyConfig.TAG_IMAGE1);
                    photos[1] = list.get(MyConfig.TAG_IMAGE2);
                    photos[2] = list.get(MyConfig.TAG_IMAGE3);
                    photos[3] = list.get(MyConfig.TAG_IMAGE4);
                    photos[4] = list.get(MyConfig.TAG_IMAGE5);

                    //      locationId = list.get(MyConfig.TAG_LOCATION_ID);
                    String tag = list.get(MyConfig.TAG_IMAGE1);

                    photo1 = photos[0];
                    photo2 = photos[1];
                    photo3 = photos[2];
                    photo4 = photos[3];
                    photo5 = photos[4];
                    photo6 = photos[5];
                    photo7 = photos[6];

                    InspectionFragment fragment = new InspectionFragment();
                    fragment.setArguments(bundle);

                    doFragmentTransaction(fragment, "InspectionFragment", false, "");

                    //   int itemNos = dbHandler.getSubItemMap(projId, aID);
                }
                else Toast.makeText(this, "No associated data found",Toast.LENGTH_SHORT).show();


                break;

            }
            case 2: {

                HashMap<String, String> list = dbHandler.getActionItem(projId, aID, iID);

                relevantInfo = list.get(MyConfig.TAG_RELEVANT_INFO);
                Overview = list.get(MyConfig.TAG_OVERVIEW);
                aProvider = list.get(MyConfig.TAG_SERVICED_BY);
                com1 = list.get(MyConfig.TAG_COM1);
                Notes = list.get(MyConfig.TAG_NOTES);


                Bundle bundle = new Bundle();
                bundle.putString("projectID", projectId);
                bundle.putString("inspectionID", inspectionId);
                bundle.putInt("aID", aID);
                bundle.putString("branchHead", branchHead);
                bundle.putString("branchLabel", inspLabel);
                bundle.putString("description", Overview);
                bundle.putString("scope", com1);
                bundle.putString("perform", relevantInfo);
                bundle.putString("notes", Notes);

                ActionItemFragment fragment = new ActionItemFragment();
                fragment.setArguments(bundle);

                doFragmentTransaction(fragment, "ActionItemFragment", false, "");

                //   int itemNos = dbHandler.getSubItemMap(projId, aID);

                photos[0] = list.get(MyConfig.TAG_IMAGE1);
                photo1 = photos[0];

                //      locationId = list.get(MyConfig.TAG_LOCATION_ID);
                String tag = list.get(MyConfig.TAG_IMAGE1);



                break;


            }


            case 9: {

                HashMap<String, String> list = dbHandler.getSummary(projId, iID);


                String head_A;
                String head_B;
                String head_C;
                String com_A;
                String com_B;
                String com_C;

                head_A = list.get(MyConfig.TAG_HEAD_A);
                head_B = list.get(MyConfig.TAG_HEAD_B);
                head_C = list.get(MyConfig.TAG_HEAD_C);
                com_A = list.get(MyConfig.TAG_COM_A);
                com_B = list.get(MyConfig.TAG_COM_B);
                com_C = list.get(MyConfig.TAG_COM_C);

                Bundle bundle = new Bundle();
                bundle.putString("projectID", projectId);
                bundle.putString("inspectionID", inspectionId);
                bundle.putString("branchHead", branchHead);
                bundle.putString("branchLabel", inspLabel);
                bundle.putString("head_A", head_A);
                bundle.putString("head_B", head_B);
                bundle.putString("head_C", head_C);
                bundle.putString("com_A", com_A);
                bundle.putString("com_B", com_B);
                bundle.putString("com_C", com_C);

                SummaryFragment fragment = new SummaryFragment();
                fragment.setArguments(bundle);

                doFragmentTransaction(fragment, "SummaryFragment", false, "");

                //   int itemNos = dbHandler.getSubItemMap(projId, aID);

                break;


            }

            case 10: {

                HashMap<String, String> list = dbHandler.getCert_Inspection(projId, iID);

                String Date_Time;
                String permit;
                String address;
                String stage;

                Date_Time = list.get(MyConfig.TAG_DATE_TIME);
                if(Date_Time == "") Date_Time = dayTime(4);

                relevantInfo = list.get(MyConfig.TAG_RELEVANT_INFO);
                Overview = list.get(MyConfig.TAG_OVERVIEW);
                permit = list.get(MyConfig.TAG_PERMIT);
                address = list.get(MyConfig.TAG_PROJECT_ADDRESS);
                stage = list.get(MyConfig.TAG_STAGE);
                Notes = list.get(MyConfig.TAG_NOTES);



                Bundle bundle = new Bundle();
                bundle.putString("projectID", projectId);
                bundle.putString("inspectionID", inspectionId);
                bundle.putString("branchHead", branchHead);
                bundle.putString("branchLabel", inspLabel);
                bundle.putString("description", Overview);
                bundle.putString("time", Date_Time);
                bundle.putString("permit", permit);
                bundle.putString("address", address);
                bundle.putString("stage", stage);
                bundle.putString("notes", Notes);

                CertificateInspectionFragment fragment = new CertificateInspectionFragment();
                fragment.setArguments(bundle);

                doFragmentTransaction(fragment, "CertificateInspectionFragment", false, "");

                //   int itemNos = dbHandler.getSubItemMap(projId, aID);

                break;


            }

            case 11: {

                if(Level > 0) {
                    HashMap<String, String> list = dbHandler.getReferenceItem(projId, aID);
                    //          if(list.size() > 0) {
                    com1 = list.get(MyConfig.TAG_COM1);
                    com2 = list.get(MyConfig.TAG_COM2);
                    com3 = list.get(MyConfig.TAG_COM3);
                    com4 = list.get(MyConfig.TAG_COM4);
                    com5 = list.get(MyConfig.TAG_COM5);
                    com6 = list.get(MyConfig.TAG_COM6);
                    com7 = list.get(MyConfig.TAG_COM7);


                    Bundle bundle = new Bundle();
                    bundle.putString("projectID", projectId);
                    bundle.putString("inspectionID", inspectionId);
                    bundle.putInt("aID", aID);
                    bundle.putString("com1", com1);
                    bundle.putString("com2", com2);
                    bundle.putString("com3", com3);
                    bundle.putString("com4", com4);
                    bundle.putString("com5", com5);
                    bundle.putString("com6", com6);
                    bundle.putString("com7", com7);

                    photos[0] = list.get(MyConfig.TAG_IMAGE1);
                    photos[1] = list.get(MyConfig.TAG_IMAGE2);
                    photos[2] = list.get(MyConfig.TAG_IMAGE3);
                    photos[3] = list.get(MyConfig.TAG_IMAGE4);
                    photos[4] = list.get(MyConfig.TAG_IMAGE5);
                    photos[5] = list.get(MyConfig.TAG_IMAGE6);
                    photos[6] = list.get(MyConfig.TAG_IMAGE7);
                    //      locationId = list.get(MyConfig.TAG_LOCATION_ID);
                    String tag = list.get(MyConfig.TAG_IMAGE1);

                    photo1 = photos[0];
                    photo2 = photos[1];
                    photo3 = photos[2];
                    photo4 = photos[3];
                    photo5 = photos[4];
                    photo6 = photos[5];
                    photo7 = photos[6];

                    ReferenceFragment fragment = new ReferenceFragment();
                    fragment.setArguments(bundle);

                    doFragmentTransaction(fragment, "ReferenceFragment", false, "");

                    //   int itemNos = dbHandler.getSubItemMap(projId, aID);
                    //         }

                    //         else Toast.makeText(this, "No associated data found",Toast.LENGTH_SHORT).show();
                } //Level > 0
                break;


            }

        }


    }

    public void onClick(View v) {

        if (v == tab_edit) {

            final DBHandler dbHandler = new DBHandler(this, null, null, 1);

            final String branchTitle = dbHandler.getMapBranchTitle(projId, catId); //get Branch head

            // setup the alert builder

            AlertDialog.Builder builder = new AlertDialog.Builder(InspectionActivity.this);
            builder.setTitle("Edit Document Outline ");
            // add a list
            String[] actions = {"Change Selected Text",
                    "Move Selected Page location",
                    "Delete Selected Page/title",
                    ""};

            builder.setItems(actions, new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0: {


                            LayoutInflater layoutInflater = LayoutInflater.from(InspectionActivity.this);
                            View promptView = layoutInflater.inflate(R.layout.add_location, null);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InspectionActivity.this);
                            alertDialogBuilder.setView(promptView);
                            final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                            itemTitle.setText("File TAB name: " + branchTitle);//Integer.parseInt(locationId)
                            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                            locationText.setText("Current TAB name : " + branchLabel);//Integer.parseInt(locationId)
                            final EditText LocationText = (EditText) promptView.findViewById(R.id.locationtext);
                            LocationText.setText(branchLabel);
                            // setup a dialog window
                            alertDialogBuilder.setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            editLocation(LocationText.getText().toString());


                                        }
                                    })
                                    .setNegativeButton("Cancel",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });

                            // create an alert dialog
                            AlertDialog alert = alertDialogBuilder.create();
                            alert.show();
                            break;
                        }

                        case 1: {//

                            LayoutInflater layoutInflater = LayoutInflater.from(InspectionActivity.this);
                            View promptView = layoutInflater.inflate(R.layout.add_location, null);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InspectionActivity.this);
                            alertDialogBuilder.setView(promptView);
                            final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                            itemTitle.setText("File Parent TAB: " + branchTitle);//Integer.parseInt(locationId)
                            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                            locationText.setText("Move Current TAB : " + branchLabel);//Integer.parseInt(locationId)
                            final EditText LocationText = (EditText) promptView.findViewById(R.id.locationtext);
                            LocationText.setHint("Moveto TAB id ->");
                            // setup a dialog window
                            alertDialogBuilder.setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dbHandler.moveTAB(projId, aID, Integer.parseInt(LocationText.getText().toString()));

                                            loadMap();

                                        }
                                    })
                                    .setNegativeButton("Cancel",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });

                            // create an alert dialog
                            AlertDialog alert = alertDialogBuilder.create();
                            alert.show();
                            break;
                        }


                        case 2: {

                            LayoutInflater layoutInflater = LayoutInflater.from(InspectionActivity.this);
                            View promptView = layoutInflater.inflate(R.layout.delete_location, null);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InspectionActivity.this);
                            alertDialogBuilder.setView(promptView);
                            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                            locationText.setText("Warning - this will delete the Branch and ALL the associated data");//location.getText().toString());

                            alertDialogBuilder.setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            deleteInspectionItem();

                                        }
                                    })
                                    .setNegativeButton("Cancel",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });

                            // create an alert dialog
                            AlertDialog alert = alertDialogBuilder.create();
                            alert.show();


                            break;


                        } //end of case 0
                    }

                }
            });
            // create and show the alert dialog
            AlertDialog dialog = builder.create();

            dialog.show();

        }


        if (v == fab_add) {

            DBHandler dbHandler = new DBHandler(this, null, null, 1);
            MapViewNode node = GlobalVariables.displayNodes.get(GlobalVariables.pos);

            final String branchTitle = dbHandler.getMapBranchTitle(projId, catId); //get Branch head
            final String folder = dbHandler.getMapBranchTitle(projId, 0); //get Branch head
            // setup the alert builder
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Document Titles and sub-Titles ");
            // add a list
            String[] actions = {"Add Title TAB to "+folder+" Folder",
                    "Add sub TAB to "+branchTitle+" TAB",
                    "Cancel"};

            builder.setItems(actions, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {

                        case 0: {

                            LayoutInflater layoutInflater = LayoutInflater.from(InspectionActivity.this);
                            View promptView = layoutInflater.inflate(R.layout.add_location, null);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InspectionActivity.this);
                            alertDialogBuilder.setView(promptView);
                            final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                            itemTitle.setText("New Item Title");//Integer.parseInt(locationId)
                            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                            locationText.setText("Title: ");//Integer.parseInt(locationId)
                            final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
                            // setup a dialog window
                            alertDialogBuilder.setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            photoBranch = "";
                                            addLevel(1, branchText.getText().toString());


                                        }
                                    })
                                    .setNegativeButton("Cancel",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });

                            // create an alert dialog
                            AlertDialog alert = alertDialogBuilder.create();
                            alert.show();

                            break;

                        }

                        case 1: {
                            MapViewNode node = GlobalVariables.displayNodes.get(GlobalVariables.pos);
                            if(node.getNodeLevel() > 0) {
                                LayoutInflater layoutInflater = LayoutInflater.from(InspectionActivity.this);
                                View promptView = layoutInflater.inflate(R.layout.add_location, null);
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InspectionActivity.this);
                                alertDialogBuilder.setView(promptView);
                                final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                                itemTitle.setText("Item Title: " + branchTitle);//Integer.parseInt(locationId)
                                final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                locationText.setText("Sub-Title of: " + branchLabel);//Integer.parseInt(locationId)
                                final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
                                // setup a dialog window
                                alertDialogBuilder.setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                photoBranch = "";
                                                addLevel((Level + 1), branchText.getText().toString());

                                            }
                                        })
                                        .setNegativeButton("Cancel",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });

                                // create an alert dialog
                                AlertDialog alert = alertDialogBuilder.create();
                                alert.show();
                            }
                            else Toast.makeText(getBaseContext(), "Choose TAB in folder", Toast.LENGTH_SHORT).show();
                            break;

                        }

                    }
                }
            });

            AlertDialog dialog = builder.create();

            dialog.show();


        }


        if (v == addPage) {

            DBHandler dbHandler = new DBHandler(this, null, null, 1);

            final String branchTitle = dbHandler.getMapBranchTitle(projId, catId); //get Branch head

            // setup the alert builder
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add Pages");
            // add a list
            String[] actions = {
                    "Add Page to Item",
                    "Add Addendum to Page",
                    "Cancel"};

            builder.setItems(actions, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {

                        case 0: {

                            LayoutInflater layoutInflater = LayoutInflater.from(InspectionActivity.this);
                            View promptView = layoutInflater.inflate(R.layout.add_location, null);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InspectionActivity.this);
                            alertDialogBuilder.setView(promptView);
                            final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                            itemTitle.setText("Item Title: " + branchTitle);//Integer.parseInt(locationId)
                            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                            locationText.setText("Page filed in : " + branchLabel);//Integer.parseInt(locationId)
                            final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
                            // setup a dialog window
                            alertDialogBuilder.setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            photoBranch = "";
                                            addReportBranch((Level + 1), branchText.getText().toString());


                                        }
                                    })
                                    .setNegativeButton("Cancel",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });

                            // create an alert dialog
                            AlertDialog alert = alertDialogBuilder.create();
                            alert.show();

                            break;

                        }

                        case 1: {

                            LayoutInflater layoutInflater = LayoutInflater.from(InspectionActivity.this);
                            View promptView = layoutInflater.inflate(R.layout.add_location, null);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InspectionActivity.this);
                            alertDialogBuilder.setView(promptView);
                            final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                            itemTitle.setText("Addendum ");//Integer.parseInt(locationId)
                            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                            locationText.setText("Addendum to Page: " + branchLabel);//Integer.parseInt(locationId)
                            final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
                            // setup a dialog window
                            alertDialogBuilder.setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            photoBranch = "";
                                            addActionBranch((Level + 1), branchText.getText().toString());


                                        }
                                    })
                                    .setNegativeButton("Cancel",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });

                            // create an alert dialog
                            AlertDialog alert = alertDialogBuilder.create();
                            alert.show();


                            break;

                        }

                        case 2: {

                            break;
                        }

                    }
                }
            });

            AlertDialog dialog = builder.create();

            dialog.show();


        }

        if (v == addSummary) {

            DBHandler dbHandler = new DBHandler(this, null, null, 1);

            final String branchTitle = dbHandler.getMapBranchTitle(projId, catId); //get Branch head

            // setup the alert builder
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Document Summary/Discussion ");
            // add a list
            String[] actions = {
                    "Attach Discussion",
                    "Cancel"};

            builder.setItems(actions, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {

                        case 0: {

                            photoBranch = "";
                            addSummaryBranch(500, "Discussion");
                            break;

                        }

                        case 1: {



                            break;

                        }

                    }
                }
            });

            AlertDialog dialog = builder.create();

            dialog.show();


        }



        if (v == addCert) {

            DBHandler dbHandler = new DBHandler(this, null, null, 1);

            final String branchTitle = dbHandler.getMapBranchTitle(projId, catId); //get Branch head

            // setup the alert builder
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(" Certificates ");
            // add a list
            String[] actions = {
                    "Attach Certificate ",
                    "Cancel"};

            builder.setItems(actions, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {

                        case 0: {

                            photoBranch = "";
                            addCertificateBranch(501, "Certificates");
                            break;

                        }

                        case 1: {



                            break;

                        }

                    }
                }
            });

            AlertDialog dialog = builder.create();

            dialog.show();


        }

        if (v == addInfo) {

            DBHandler dbHandler = new DBHandler(this, null, null, 1);

            final String branchTitle = dbHandler.getMapBranchTitle(projId, catId); //get Branch head

            // setup the alert builder
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(" File Information ");
            // add a list
            String[] actions = {
                    "Attach File Information ",
                    "Cancel"};

            builder.setItems(actions, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {

                        case 0: {

                            photoBranch = "";
                            addReferenceBranch(502, "File Information");
                            break;

                        }

                        case 1: {



                            break;

                        }

                    }
                }
            });

            AlertDialog dialog = builder.create();

            dialog.show();
        }


    }

    public void reportMenu() {

       DBHandler dbHandler = new DBHandler(getApplicationContext(), null, null, 1);



                        Bundle bundle = new Bundle();
                        bundle.putInt("projectId", projId);
                        bundle.putInt("iId", iID);


                        ArrayList<HashMap<String, String>> listItemsmap = dbHandler.getInspectedItems_r(projId, iID);

                        reportlistItems = new ArrayList<>();
                        ReportItem listItem;

                        for (int i = 0; i <= (listItemsmap.size() - 1); i++) {
                            listItem = new ReportItem(
                                    listItemsmap.get(i).get("typeObject"),
                                    listItemsmap.get(i).get("BranchHead"),
                                    listItemsmap.get(i).get("ParentLabel"),
                                    listItemsmap.get(i).get(MyConfig.TAG_OVERVIEW),
                                    listItemsmap.get(i).get(MyConfig.TAG_RELEVANT_INFO),
                                    listItemsmap.get(i).get(MyConfig.TAG_NOTES),
                                    listItemsmap.get(i).get(MyConfig.TAG_IMAGE1),
                                    listItemsmap.get(i).get(MyConfig.TAG_COM1),
                                    listItemsmap.get(i).get(MyConfig.TAG_IMAGE2),
                                    listItemsmap.get(i).get(MyConfig.TAG_COM2),
                                    listItemsmap.get(i).get(MyConfig.TAG_IMAGE3),
                                    listItemsmap.get(i).get(MyConfig.TAG_COM3),
                                    listItemsmap.get(i).get(MyConfig.TAG_IMAGE4),
                                    listItemsmap.get(i).get(MyConfig.TAG_COM4),
                                    listItemsmap.get(i).get(MyConfig.TAG_IMAGE5),
                                    listItemsmap.get(i).get(MyConfig.TAG_COM5),
                                    listItemsmap.get(i).get(MyConfig.TAG_LABEL),
                                    listItemsmap.get(i).get(MyConfig.TAG_DATE_TIME),
                                    listItemsmap.get(i).get(MyConfig.TAG_PERMIT),
                                    listItemsmap.get(i).get(MyConfig.TAG_PROJECT_ADDRESS),
                                    listItemsmap.get(i).get(MyConfig.TAG_STAGE),
                                    listItemsmap.get(i).get(MyConfig.TAG_HEAD_A),
                                    listItemsmap.get(i).get(MyConfig.TAG_COM_A),
                                    listItemsmap.get(i).get(MyConfig.TAG_HEAD_B),
                                    listItemsmap.get(i).get(MyConfig.TAG_COM_B),
                                    listItemsmap.get(i).get(MyConfig.TAG_HEAD_C),
                                    listItemsmap.get(i).get(MyConfig.TAG_COM_C)

                            );

                            reportlistItems.add(listItem);

                            Log.v("report list", listItemsmap.get(i).get("typeObject") + ", ");
                        }


                        HashMap<String, String> projectItem = dbHandler.getInspection(projId, iID);

                        //               mPhotoImageView = (ImageView) findViewById(R.id.imageView6);
                        propPhoto = projectItem.get(MyConfig.TAG_IMAGE);

                        bundle.putString("branchHead", projectItem.get(MyConfig.TAG_ADDRESS_NO));
                        bundle.putString("branchLabel", projectItem.get(MyConfig.TAG_LABEL));
                        bundle.putString("projectId", projectId);
                        bundle.putString("inspectionId", inspectionId);
                        bundle.putString("date", projectItem.get(MyConfig.TAG_INSPECTION_DATE));
                        bundle.putString("startTime", projectItem.get(MyConfig.TAG_START_DATE_TIME));
                        bundle.putString("endTime", projectItem.get(MyConfig.TAG_END_DATE_TIME));
                        bundle.putString("note", projectItem.get(MyConfig.TAG_NOTE));
                        bundle.putString("inpectType", projectItem.get(MyConfig.TAG_INSPECTION_TYPE));
                        bundle.putString("auditor", projectItem.get(MyConfig.TAG_USER_ID));

                        ReportFragment reportfragment = new ReportFragment();
                        reportfragment.setArguments(bundle);
                        doFragmentTransaction(reportfragment, "ReportFragment", false, "");
                        reportfragment.setArguments(bundle);

    }

    private void reportMailer(final int type, final String email) {

        class Send_Report extends AsyncTask<Void, Void, String> {
            private ProgressDialog loading;
            private String res = "Before try";
            private String message = "fail";

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getBaseContext(), "Compiling and sending report...", "Processing.... this may take several minutes", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(Void... params) {

                RequestHandler_ rh = new RequestHandler_();
                if(CLIENT.substring(0,2).equals("no")){

                    message = rh.sendRequestParam(MyConfig.URL_EMAIL_REPORT, "iapp-NOCLIENT"+".php?projId="+ projectId+"&iId="+ inspectionId+"&EMAIL="+email+"&USERID="+USER_ID+"&TYPE="+type);}
                else{

                    message = rh.sendRequestParam(MyConfig.URL_EMAIL_REPORT, CLIENT + ".php?projId=" + projectId + "&iId=" + inspectionId + "&EMAIL=" + email + "&USERID=" + USER_ID + "&TYPE=" + type);
                }
                return message;
            }

        }
        Send_Report rep = new Send_Report();
        rep.execute();

    }

    public void takeImageFromCamera(View view) {

        // Toast.makeText(this, "just before cameraintent",Toast.LENGTH_SHORT).show();
        Intent camera_intent = new Intent();
        camera_intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        photo = null;
        try {
            photo = createPhotoFile();
        } catch (IOException  e) {
            e.printStackTrace();
        }

        camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(InspectionActivity.this,BuildConfig.APPLICATION_ID + ".provider", photo));
        startActivityForResult(camera_intent, ACTIVITY_START_CAMERA_APP);

    }

    public static void copy(File src, File dst) throws IOException {
        try (InputStream in = new FileInputStream(src)) {
            try (OutputStream out = new FileOutputStream(dst)) {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_DRAW_FILE && resultCode == 0) {

            onClick(photo_file);

        }


        if (requestCode == ACTIVITY_GET_FILE && resultCode == RESULT_OK) {

            Uri selectedImage = data.getData();

            Cursor returnCursor = getContentResolver().query(selectedImage, null, null, null, null);

            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            String name = returnCursor.getString(nameIndex);
            returnCursor.close();


            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            // Get the cursor
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();
            //Get the column index of MediaStore.Images.Media.DATA
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            //Gets the String value in the column
            String path = cursor.getString(columnIndex);
            cursor.close();
            // Set the Image in ImageView after decoding the String
            // photoA.setImageBitmap(BitmapFactory.decodeFile(path));

            if (FragDisplay == "BaseFragment") {

                fragment_obj = getSupportFragmentManager().findFragmentByTag("BaseFragment");

                switch (filephoto) {
                    case 1:
                        photoA = fragment_obj.getView().findViewById(R.id.imageView);
                        photoA.setImageURI(selectedImage);
                        break;

                }

            }
            // photoA.setImageURI(selectedImage);
            if (FragDisplay == "InspectionFragment") {

                fragment_obj = getSupportFragmentManager().findFragmentByTag("InspectionFragment");

                switch (filephoto) {
                    case 1:
                        photoA = fragment_obj.getView().findViewById(R.id.imageView);
                        photoA.setImageURI(selectedImage);
                        break;

                    case 2:
                        photoB = fragment_obj.getView().findViewById(R.id.imageView2);
                        photoB.setImageURI(selectedImage);
                        break;

                    case 3:
                        photoC = fragment_obj.getView().findViewById(R.id.imageView3);
                        photoC.setImageURI(selectedImage);
                        break;

                    case 4:
                        photoD = fragment_obj.getView().findViewById(R.id.imageView4);
                        photoD.setImageURI(selectedImage);
                        break;

                    case 5:
                        photoE = fragment_obj.getView().findViewById(R.id.imageView5);
                        photoE.setImageURI(selectedImage);
                        break;

                    case 6:
                        photoF = fragment_obj.getView().findViewById(R.id.imageView6);
                        photoF.setImageURI(selectedImage);
                        break;

                    case 7:
                        photoG = fragment_obj.getView().findViewById(R.id.imageView7);
                        photoG.setImageURI(selectedImage);
                        break;

                }

            }

            if (FragDisplay == "ReferenceFragment") {

                fragment_obj = getSupportFragmentManager().findFragmentByTag("ReferenceFragment");

                switch (filephoto) {
                    case 1:
                        photoA = fragment_obj.getView().findViewById(R.id.imageView);
                        photoA.setImageURI(selectedImage);
                        break;

                    case 2:
                        photoB = fragment_obj.getView().findViewById(R.id.imageView2);
                        photoB.setImageURI(selectedImage);
                        break;

                    case 3:
                        photoC = fragment_obj.getView().findViewById(R.id.imageView3);
                        photoC.setImageURI(selectedImage);
                        break;

                    case 4:
                        photoD = fragment_obj.getView().findViewById(R.id.imageView4);
                        photoD.setImageURI(selectedImage);
                        break;

                    case 5:
                        photoE = fragment_obj.getView().findViewById(R.id.imageView5);
                        photoE.setImageURI(selectedImage);
                        break;

                    case 6:
                        photoF = fragment_obj.getView().findViewById(R.id.imageView6);
                        photoF.setImageURI(selectedImage);
                        break;

                    case 7:
                        photoG = fragment_obj.getView().findViewById(R.id.imageView7);
                        photoG.setImageURI(selectedImage);
                        break;

                }

            }

            if (FragDisplay == "ActionItemFragment") {

                fragment_obj = getSupportFragmentManager().findFragmentByTag("ActionItemFragment");

                switch (filephoto) {
                    case 1:
                        photoA = fragment_obj.getView().findViewById(R.id.imageView);
                        photoA.setImageURI(selectedImage);
                        break;


                }

            }




            fname = dayTime(3);
            dirName = dayTime(1);
            fname = projectId + "_" + fname;

            String root = Environment.getExternalStorageDirectory().toString();
            String SD = root + "/A2D_" + dirName + "/";
            File storageDirectory = new File(root + "/A2D_" + dirName + "/");
            // Toast.makeText(this, "should have made directory",Toast.LENGTH_SHORT).show();
            // Toast.makeText(this, "string root name = "+root ,Toast.LENGTH_SHORT).show();
            if (!storageDirectory.exists()) {
                storageDirectory.mkdirs();
            }


                File from = new File(path);
                File to = new File(SD + fname + ".jpg");






            // from.renameTo(to);  This deletes the source file from

            try {

                copy(from, to);
                if(to.length()/1048576 > 1) resizeImage(to);

            } catch (IOException e) {
                e.printStackTrace();
            }


            switch (filephoto) {
                case 1:
                    if (FragDisplay == "BaseFragment") photoBranch = to.getName();
                    photo1 = to.getName();
                    break;

                case 2:
                    //          photoB.setImageURI(selectedImage);
                    photo2 = to.getName();
                    break;

                case 3:
                    //          photoC.setImageURI(selectedImage);
                    photo3 = to.getName();
                    break;

                case 4:
                    //          photoC.setImageURI(selectedImage);
                    photo4 = to.getName();
                    break;

                case 5:
                    //          photoC.setImageURI(selectedImage);
                    photo5 = to.getName();
                    break;

                case 6:
                    //          photoC.setImageURI(selectedImage);
                    photo6 = to.getName();
                    break;

                case 7:
                    //          photoC.setImageURI(selectedImage);
                    photo7 = to.getName();
                    break;
            }

            saveInspectionItem();

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(to)));

            //     getContentResolver().notifyChange(Uri.fromFile(to),null);

            // load original photo to next imageview
            //   Uri photo_image = Uri.fromFile(photo);
            //   photoB.setImageURI(photo_image);
            //   photo2 = photo.getName();

        }


        if (requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK) {

            try {
                rotateImage(resizePhoto());
                cameraSnap = "1";
                //    TextView imageName1 = (TextView) findViewById(R.id.textView16);
                //    imageName1.setText("UPDATED");
                saveInspectionItem();
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(photo)));

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (requestCode == ACTIVITY_START_CAMERA_APP) {
            photo.delete();
            if (cameraSnap != "1") {
                cameraSnap = "0";
                //    Edited = true;
            }

            switch (photoframe) {

                case 0:
                    photoBranch = photos[0];
                    break;

                case 1:
                    photo1 = photos[0];
                    break;

                case 2:
                    photo2 = photos[1];
                    break;

                case 3:
                    photo3 = photos[2];
                    break;

                case 4:
                    photo4 = photos[3];
                    break;

                case 5:
                    photo5 = photos[4];
                    break;

                case 6:
                    photo6 = photos[5];
                    break;

                case 7:
                    photo7 = photos[6];
                    break;
            }
        }

        if (requestCode == PICK_CONTACT) {

            final ArrayList emailAdress;
            emailAdress = getContactInfo(data);


            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View promptView = layoutInflater.inflate(R.layout.email_accept, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setView(promptView);
            final TextView locationText = (TextView) promptView.findViewById(R.id.email);
            if (emailAdress.size() > 0)
                locationText.setText(emailAdress.get(0).toString());//location.getText().toString());

            alertDialogBuilder.setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            reportMailer(1, emailAdress.get(0).toString());
                        }
                    })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            // create an alert dialog
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();

        }


    }

    protected  ArrayList<String> getContactInfo(Intent data)
    {
        ArrayList<String> contactInfo = new ArrayList<>();


        Cursor cursor = null;
        String email = "", name = "";
        try {
            Uri result = data.getData();
            Log.v(" Email", "Got a contact result: " + result.toString());

            // get the contact id from the Uri
            String id = result.getLastPathSegment();

            // query for everything email
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,  null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?", new String[] { id }, null);

            int nameId = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

            int emailIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);

            // let's just get the first email
            if (cursor.moveToFirst()) {
                do {
                    contactInfo = new ArrayList<>();
                    contactInfo.add(cursor.getString(emailIdx));

                    email = contactInfo.get(0);
                    //           name = cursor.getString(nameId);
                    Log.v(" Email", "Got email: " + email);
                }
                while (cursor.moveToNext());
            } else {
                Log.w(" Email", "No results");
            }
        } catch (Exception e) {
            Log.e(" Email", "Failed to get email data", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }


        }
        return contactInfo;
    }//getContactInfo
    File createPhotoFile()throws IOException {

        fname = dayTime(3);
        dirName = dayTime(1);
        fname = projectId+"_"+fname;
        String root = Environment.getExternalStorageDirectory().toString();
        File storageDirectory = new File(root + "/A2D_"+dirName+"/");
        // Toast.makeText(this, "should have made directory",Toast.LENGTH_SHORT).show();
        // Toast.makeText(this, "string root name = "+root ,Toast.LENGTH_SHORT).show();
        if (!storageDirectory.exists()){storageDirectory.mkdirs();}
        File image = File.createTempFile(fname, ".jpg", storageDirectory);
        // codeBox.setText(image.getName())

        //set photo (file) to image
       photo = image;

       switch (photoframe){

           case 0:
                photoBranch = image.getName();
                break;
            case 1:
                photo1 = image.getName();
                 break;
            case 2:
                photo2 = image.getName();
                break;
           case 3:
               photo3 = image.getName();
               break;
            case 4:
               photo4 = image.getName();
               break;
            case 5:
                photo5 = image.getName();
             break;
           case 6:
               photo6 = image.getName();
               break;
           case 7:
               photo7 = image.getName();
               break;
          }

        mImageFileLocation = image.getAbsolutePath();
        return image;
    }

    private Bitmap resizeImage(File image) throws IOException {

        String root = image.getAbsolutePath();
        //  File imgFile = new  File(root);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();

        int screenIamgeWidth = 500; //mPhotoImageView.getWidth();
        int screenIamgeHeight = 500; // mPhotoImageView.getHeight();

        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(root, bmOptions);
        int photoImageWidth = bmOptions.outWidth;
        int photoImageHeight = bmOptions.outHeight;
        int scaleFactor = Math.min(photoImageHeight / screenIamgeHeight, photoImageWidth / screenIamgeWidth);
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inJustDecodeBounds = false;
        Bitmap photoResize = BitmapFactory.decodeFile(root, bmOptions);
       // root.setImageBitmap(photoResize);

        Bitmap thePhotoFile = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);

        //get its orginal dimensions
        int bmOriginalWidth = thePhotoFile.getWidth();
        int bmOriginalHeight = thePhotoFile.getHeight();
        double originalWidthToHeightRatio = 1.0 * bmOriginalWidth / bmOriginalHeight;
        double originalHeightToWidthRatio = 1.0 * bmOriginalHeight / bmOriginalWidth;
        //choose a maximum height
        int maxHeight = 1200;
        //choose a max width
        int maxWidth = 1200;
        //call the method to get the scaled bitmap

        // bmOptions.inJustDecodeBounds = false;


        thePhotoFile = getScaledBitmap(thePhotoFile, bmOriginalWidth, bmOriginalHeight,
                originalWidthToHeightRatio, originalHeightToWidthRatio,
                maxHeight, maxWidth);


        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //compress the photo's bytes into the byte array output stream
        thePhotoFile.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        //construct a File object to save the scaled file to
        File f = new File(root);
        //create the file
        f.createNewFile();

        //create an FileOutputStream on the created file
        FileOutputStream fo = new FileOutputStream(f);
        //write the photo's bytes to the file
        fo.write(bytes.toByteArray());


        //finish by closing the FileOutputStream
        fo.close();


        // return Bitmap.createScaledBitmap(mPhotoImageView,(int)(mPhotoImageView.getWidth()*0.5),(int)(mPhotoImageView.getHeight()*0.5),true);
        //  resizePhoto();

        return thePhotoFile;
    }




    private Bitmap resizePhoto() throws IOException {

        String root = photo.getAbsolutePath();
      //  File imgFile = new  File(root);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();

        int screenIamgeWidth = 500; //mPhotoImageView.getWidth();
        int screenIamgeHeight = 500; // mPhotoImageView.getHeight();

        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mImageFileLocation, bmOptions);
        int photoImageWidth = bmOptions.outWidth;
        int photoImageHeight = bmOptions.outHeight;
        int scaleFactor = Math.min(photoImageHeight / screenIamgeHeight, photoImageWidth / screenIamgeWidth);
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inJustDecodeBounds = false;
        Bitmap photoResize = BitmapFactory.decodeFile(mImageFileLocation, bmOptions);
        mPhotoImageView.setImageBitmap(photoResize);

        Bitmap thePhotoFile = BitmapFactory.decodeFile(photo.getAbsolutePath(), bmOptions);

        //get its orginal dimensions
        int bmOriginalWidth = thePhotoFile.getWidth();
        int bmOriginalHeight = thePhotoFile.getHeight();
        double originalWidthToHeightRatio = 1.0 * bmOriginalWidth / bmOriginalHeight;
        double originalHeightToWidthRatio = 1.0 * bmOriginalHeight / bmOriginalWidth;
        //choose a maximum height
        int maxHeight = 1200;
        //choose a max width
        int maxWidth = 1200;
        //call the method to get the scaled bitmap

        // bmOptions.inJustDecodeBounds = false;


        thePhotoFile = getScaledBitmap(thePhotoFile, bmOriginalWidth, bmOriginalHeight,
                originalWidthToHeightRatio, originalHeightToWidthRatio,
                maxHeight, maxWidth);


        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //compress the photo's bytes into the byte array output stream
        thePhotoFile.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        //construct a File object to save the scaled file to
        File f = new File(root);
        //create the file
        f.createNewFile();

        //create an FileOutputStream on the created file
        FileOutputStream fo = new FileOutputStream(f);
        //write the photo's bytes to the file
        fo.write(bytes.toByteArray());


        //finish by closing the FileOutputStream
        fo.close();


        // return Bitmap.createScaledBitmap(mPhotoImageView,(int)(mPhotoImageView.getWidth()*0.5),(int)(mPhotoImageView.getHeight()*0.5),true);
        //  resizePhoto();

       return thePhotoFile;
    }

    private void rotateImage(Bitmap bitmap) {

        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(mImageFileLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            default:
        }
        Bitmap rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),bitmap.getHeight(),matrix,true );
      //  mPhotoImageView.setImageBitmap(rotateBitmap);
    }



    private static Bitmap getScaledBitmap(Bitmap bm, int bmOriginalWidth, int bmOriginalHeight, double originalWidthToHeightRatio, double originalHeightToWidthRatio, int maxHeight, int maxWidth) {
        if(bmOriginalWidth > maxWidth || bmOriginalHeight > maxHeight) {
            //   Log.v(TAG, format("RESIZING bitmap FROM %sx%s ", bmOriginalWidth, bmOriginalHeight));

            if(bmOriginalWidth > bmOriginalHeight) {
                bm = scaleDeminsFromWidth(bm, maxWidth, bmOriginalHeight, originalHeightToWidthRatio);
            } else {
                bm = scaleDeminsFromHeight(bm, maxHeight, bmOriginalHeight, originalWidthToHeightRatio);
            }

            //    Log.v(TAG, format("RESIZED bitmap TO %sx%s ", bm.getWidth(), bm.getHeight()));
        }
        return bm;
    }

    private static Bitmap scaleDeminsFromHeight(Bitmap bm, int maxHeight, int bmOriginalHeight, double originalWidthToHeightRatio) {
        int newHeight = (int) Math.min(maxHeight, bmOriginalHeight * .55);
        int newWidth = (int) (newHeight * originalWidthToHeightRatio);
        bm = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
        return bm;
    }

    private static Bitmap scaleDeminsFromWidth(Bitmap bm, int maxWidth, int bmOriginalWidth, double originalHeightToWidthRatio) {
        //scale the width
        int newWidth = (int) Math.min(maxWidth, bmOriginalWidth * .75);
        int newHeight = (int) (newWidth * originalHeightToWidthRatio);
        bm = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
        return bm;
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    //    GlobalVariables.modified = true;

        if(Edited)saveInspectionItem();

        endTime = dayTime(4);

        if(logTime) {

            DBHandler dbHandler = new DBHandler(this, null, null, 1);
            dbHandler.logInspection(projectId, inspectionId, startTime, endTime);
            dbHandler.updateStatus(Integer.parseInt(projectId), Integer.parseInt(inspectionId),"m",dayTime(1));
        }


    }



}
