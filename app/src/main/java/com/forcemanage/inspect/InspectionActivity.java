package com.forcemanage.inspect;

import android.app.AlertDialog;
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

import static com.amazonaws.services.cognitoidentityprovider.model.AttributeDataType.DateTime;


public class InspectionActivity extends AppCompatActivity implements OnVerseNameSelectionChangeListener, View.OnClickListener {

    DBHandler ESMdb;
    private Button buttonInsert;
    private Button buttonDelete;
    private Button buttonEdit;
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
    private ImageView photo_cam;
    private ImageView photo_draw;
    private ImageView photo_file;
    private ImageView photo_file2;
    private ImageView photo_file3;


    public ImageView mPhotoImageView;
    public String photoBranch;
    public String photo1;
    public String photo2;
    public String photo3;
    public String photo4;
    public String photo5;
    public String[] photos = new String[5];
    private String mImageFileLocation = "";
    private String fname;
    private String dirName;
    private View view;
    public int filephoto;


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


    //    private TextView Asset;
    private String cameraSnap;
    private EditText Note;
    private TextView ItemNumbers;
    //  private TextView location;
    //  private String location;
    //  private TextView SubLocation;
    private String ESMtxt;
    private String itemlocation;
    private String[] locationsArr;
    private String[] sublocationsArr;
    private String[] iTitle;
    private String[] esm_cat;
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
    public String branchLabel = "Label";
    public String branchNote = "Note";
    private int branchCode = 0;
    public boolean Edited = false;
    private Fragment fragment_obj;
    private String FragDisplay;
    private Boolean logTime;
    private String startTime;
    private String endTime;




    //   private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ESMdb = new DBHandler(this, null, null, 1);
        setContentView(R.layout.activity_inspection);
        cameraSnap = "0";
        projectId = getIntent().getExtras().getString("PROJECT_ID");
        inspectionId = getIntent().getExtras().getString("INSPECTION_ID");
        logTime = getIntent().getExtras().getBoolean("logTime");
        buttonInsert = (Button) findViewById(R.id.button2);
        buttonInsert.setOnClickListener(this);
        buttonDelete = (Button) findViewById(R.id.button4);
        buttonDelete.setOnClickListener(this);
        buttonEdit = (Button) findViewById(R.id.button_edit);
        buttonEdit.setOnClickListener(this);
        zone = 0;
        projId = Integer.parseInt(projectId);
        iID = Integer.parseInt(inspectionId);


     //   dateBtn = (Button) findViewById(R.id.btndate);
     //   dateBtn.setOnClickListener(this);




        photos[0] = "";

        startTime = dayTime(4);

        DBHandler dbHandlerA = new DBHandler(this, null, null, 1);
        dbHandlerA.updateInspectionItemdate();

        //  ItemNumbers = (TextView) findViewById(R.id.RecordCount);
        //  ItemNumbers.setText("Property has "+Integer.toString(itemNumbers.size())+" items.");


        init();

        ArrayList<HashMap<String, String>> SiteMapData = dbHandlerA.getMap(projId, iID);

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

        BaseFragment fragment = new BaseFragment();
        doFragmentTransaction(fragment, "BaseFragment", false, "");

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
        ArrayList<HashMap<String, String>> SiteMapData = dbHandler.getMap(projId, iID);

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

        OnSelectionChanged(GlobalVariables.pos);
    }

    @Override
    public void OnSelectionChanged(int treeNameIndex) {


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

            OnSelectionChanged(GlobalVariables.pos);
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
                    photo5, "Img6", "Img7");

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
                dbHandler.deleteInspectionItem(projId, aID);
                GlobalVariables.pos = GlobalVariables.pos - 1;
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                loadMap();
                break;
            }
            case (1):{

                dbHandler.deleteMapBranch(projId, aID);
                dbHandler.deleteInspectionItem(projId, aID);
                GlobalVariables.pos = GlobalVariables.pos - 1;
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                loadMap();
                break;
            }
            case (2):{

                dbHandler.deleteMapBranch(projId, aID);
                dbHandler.deleteInspectionItem(projId, aID);
                dbHandler.deleteActionItem(projId,aID);
                GlobalVariables.pos = GlobalVariables.pos - 1;
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                loadMap();
                break;
            }
            case (10):{

                dbHandler.deleteCertificate(projId,iID,aID);
                dbHandler.deleteMapBranch(projId, aID);
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
            Toast.makeText(this, "Cannot place navigation branch at this position", Toast.LENGTH_SHORT).show();
        else
            loadMap();
    }


    private void addReportBranch(int Level, String levelName) {

        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        int result = dbHandler.addReportBranch(projId, iID, catId, Level, aID, levelName);  //this is the ESM category

        if (result == 1)
            loadMap();
        else
            Toast.makeText(this, "Cannot place report branch at this position", Toast.LENGTH_SHORT).show();

    }

    private void addActionBranch(int Level, String levelName) {

        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        int result = dbHandler.addActionBranch(projId, iID, catId, Level, aID, levelName);  //this is the ESM category

        if (result == 1)
            loadMap();
        else
            Toast.makeText(this, "Cannot place an Action branch at this position", Toast.LENGTH_SHORT).show();

    }

    private void addCertificateBranch(int Level, String levelName) {

        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        int result = dbHandler.addCertificate(projId, iID, 500, 0, aID, levelName);  //this is the ESM category
        if (result == 0)
            Toast.makeText(this, "Cannot place navigation branch at this position", Toast.LENGTH_SHORT).show();
        else
            loadMap();

    }
    public void editLocation(String branchLabel) {

        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        int success = dbHandler.updateMapLabel(projId, aID, branchLabel);
        if(success == 1) loadMap();
        else Toast.makeText(this, "Create/select a MAP branch", Toast.LENGTH_SHORT).show();
    }


    public void displayInspectionItem() {

        //          ItemNumbers.setText("Zone : "+locationId+", Sublocat : "+sublocationId+",  Asset id : "+ aId);
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        if(aID == 0) aID = 1;
        HashMap<String, String> mapItem = dbHandler.getMapItem(projId, aID, iID);

        String MapBranch;

        catId = Integer.parseInt(mapItem.get(MyConfig.TAG_CAT_ID));
        Level = Integer.parseInt(mapItem.get(MyConfig.TAG_LEVEL));
        Parent = Integer.parseInt(mapItem.get(MyConfig.TAG_PARENT));
        photoBranch = mapItem.get(MyConfig.TAG_IMAGE1);
        branchLabel = mapItem.get(MyConfig.TAG_LABEL); //This is the inspection label column
        MapBranch = mapItem.get("MAP_LABEL"); //This is the Map label column
        branchNote = mapItem.get(MyConfig.TAG_NOTES);
        branchCode = Integer.parseInt(mapItem.get(MyConfig.TAG_CHILD));

        String branchHead = dbHandler.getMapBranchTitle(projId, catId);

        switch (branchCode) {


            case 0: {


                Bundle bundle = new Bundle();
                bundle.putString("branchHead", branchHead);
                bundle.putString("inspection", branchLabel);
                bundle.putString("projectID", projectId);
                bundle.putString("inspectionID", inspectionId);
                bundle.putString("image", photoBranch);
                bundle.putString("MAP_LABEL", MapBranch);
                bundle.putInt("aID", aID);
                bundle.putString("notes", branchNote);
                bundle.putString("com2", com2);
                BaseFragment fragment = new BaseFragment();
                fragment.setArguments(bundle);

                doFragmentTransaction(fragment, "BaseFragment", false, "");

                 break;
            }
            case 1: {


                HashMap<String, String> list = dbHandler.getInspectionItem(projId, iID, aID);

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


                Bundle bundle = new Bundle();
                bundle.putString("projectID", projectId);
                bundle.putString("inspectionID", inspectionId);
                bundle.putInt("aID", aID);
                bundle.putString("branchHead", branchHead);
                bundle.putString("branchLabel", branchLabel);
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


                InspectionFragment fragment = new InspectionFragment();
                fragment.setArguments(bundle);

                doFragmentTransaction(fragment, "InspectionFragment", false, "");

             //   int itemNos = dbHandler.getSubItemMap(projId, aID);



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
                bundle.putString("branchLabel", branchLabel);
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

            case 10: {

                HashMap<String, String> list = dbHandler.getCert_Inspection(projId, iID);

                String Date_Time;
                String permit;
                String address;
                String stage;

                Date_Time = list.get(MyConfig.TAG_DATE_TIME);
                if(Date_Time == null) Date_Time = dayTime(4);

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
                bundle.putString("branchLabel", branchLabel);
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

        }


    }

    public void onClick(View v) {


        if (v == buttonEdit) {

            DBHandler dbHandler = new DBHandler(this, null, null, 1);

            final String branchTitle = dbHandler.getMapBranchTitle(projId, catId); //get Branch head

            // setup the alert builder

            AlertDialog.Builder builder = new AlertDialog.Builder(InspectionActivity.this);
            builder.setTitle("Modify Branch Tabs ");
            // add a list
            String[] actions = {"Change the Branch tab text",
                    "",
                    "",
                    ""};
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
                            itemTitle.setText("Branch Text: " + branchTitle);//Integer.parseInt(locationId)
                            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                            locationText.setText("Current label : " + branchLabel);//Integer.parseInt(locationId)
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

                        case 1: //
                            break;
                    }
                }
            });
            // create and show the alert dialog
            AlertDialog dialog = builder.create();

            dialog.show();

        }


        if (v == buttonInsert) {

            DBHandler dbHandler = new DBHandler(this, null, null, 1);

            final String branchTitle = dbHandler.getMapBranchTitle(projId, catId); //get Branch head

            // setup the alert builder
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add Titles, sub-Titles, Notes, Action Scope, Certificates ");
            // add a list
            String[] actions = {"Create a New Title Branch ",
                    "Add Sub Title to the current Branch",
                    "Attach NotePad to the current Title/Sub Title ",
                    "Attach a Scope to the current NotePad",
                    "Attach a Certificate for this Activity",
                    "Cancel Add/Create "};

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
                            itemTitle.setText("Branch Head Title: " + branchTitle);//Integer.parseInt(locationId)
                            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                            locationText.setText("Branch below : " + branchLabel);//Integer.parseInt(locationId)
                            final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
                            // setup a dialog window
                            alertDialogBuilder.setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            photoBranch = "";
                                            addLevel(0, branchText.getText().toString());


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
                            itemTitle.setText("Branch Head Title: " + branchTitle);//Integer.parseInt(locationId)
                            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                            locationText.setText("Branch below : " + branchLabel);//Integer.parseInt(locationId)
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

                            break;

                        }

                        case 2: {


                            LayoutInflater layoutInflater = LayoutInflater.from(InspectionActivity.this);
                            View promptView = layoutInflater.inflate(R.layout.add_location, null);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InspectionActivity.this);
                            alertDialogBuilder.setView(promptView);
                            final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                            itemTitle.setText("Branch Head Title: " + branchTitle);//Integer.parseInt(locationId)
                            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                            locationText.setText("Attach a NoteBook to the : " + branchLabel+" Title ");//Integer.parseInt(locationId)
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

                        case 3: {

                            LayoutInflater layoutInflater = LayoutInflater.from(InspectionActivity.this);
                            View promptView = layoutInflater.inflate(R.layout.add_location, null);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InspectionActivity.this);
                            alertDialogBuilder.setView(promptView);
                            final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                            itemTitle.setText("Branch Head Title: " + branchTitle);//Integer.parseInt(locationId)
                            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                            locationText.setText("Relevant Branch : " + branchLabel);//Integer.parseInt(locationId)
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

                        case 4: {
                            photoBranch = "";
                            addCertificateBranch(500, "Certificates");
                            break;
                        }

                        case 5: {


                            break;
                        }

                    }
                }
            });

            AlertDialog dialog = builder.create();

            dialog.show();


        }


        if (v == buttonDelete) {


            // setup the alert builder

            AlertDialog.Builder builder = new AlertDialog.Builder(InspectionActivity.this);
            builder.setTitle("Choose an action");
            // add a list
            String[] actions = {"Delete the current branch",
                    };
            builder.setItems(actions, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {

                        case 0: {

                            LayoutInflater layoutInflater = LayoutInflater.from(InspectionActivity.this);
                            View promptView = layoutInflater.inflate(R.layout.delete_location, null);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InspectionActivity.this);
                            alertDialogBuilder.setView(promptView);
                            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                            locationText.setText("Warning - this will delete the branch and ALL the associated data");//location.getText().toString());

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

            File from = new File(path);


            fname = dayTime(3);
            dirName = dayTime(1);
            fname = projectId + "_" + fname;

            String root = Environment.getExternalStorageDirectory().toString();
            String SD = root + "/ESM_" + dirName + "/";
            File storageDirectory = new File(root + "/ESM_" + dirName + "/");
            // Toast.makeText(this, "should have made directory",Toast.LENGTH_SHORT).show();
            // Toast.makeText(this, "string root name = "+root ,Toast.LENGTH_SHORT).show();
            if (!storageDirectory.exists()) {
                storageDirectory.mkdirs();
            }

            File to = new File(SD + fname + ".jpg");


            // from.renameTo(to);  This deletes the source file from

            try {
                copy(from, to);
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


            }
        }

        }


    File createPhotoFile()throws IOException {

        fname = dayTime(3);
        dirName = dayTime(1);
        fname = projectId+"_"+fname;
        String root = Environment.getExternalStorageDirectory().toString();
        File storageDirectory = new File(root + "/ESM_"+dirName+"/");
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
          }

        mImageFileLocation = image.getAbsolutePath();
        return image;
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

        if(Edited == true )saveInspectionItem();

        endTime = dayTime(4);

        if(logTime==true) {

            DBHandler dbHandler = new DBHandler(this, null, null, 1);
            dbHandler.logInspection(projectId, inspectionId, startTime, endTime);
            dbHandler.updateStatus(Integer.parseInt(projectId), Integer.parseInt(inspectionId),"p",dayTime(1));
        }


    }



}
