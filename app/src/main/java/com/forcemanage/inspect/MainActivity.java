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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


import static com.amazonaws.regions.Regions.AP_SOUTHEAST_2;
import static java.lang.Integer.parseInt;


public class MainActivity extends AppCompatActivity implements OnVerseNameSelectionChangeListener,  View.OnClickListener {

    public static final int REQUEST_CODE = 20;
    private Button buttonDownload;
    private Button buttonSyncAll;
    private Button buttonClearAll;
    private Button buttonDownloadPhotos;
    private Button buttonSyncPhotos;
    private Button buttonInspection;
    private Button buttonLoadJobList;
    private Button btnLogin;
    private Button btnAddActivity;
    private EditText TextMessage;
    DBHandler ESMdb;
    private String JSON_STRING;
    private String JSON_STRING_ADDITIONAL;
    private String JSON_STRING_ACTION;
    private String JSON_STRING_CERTINSP;
    private String JSON_STRING_PROJECT_LIST;
    private String JSON_STRING_OR;
    private String JSON_STRING_USER;
    private int USER_ID = 0;
    private String USER_NAME = "";
    private String PASS_WORD = "";
    private String CLIENT = "no-client";
    private ListView listView;
    private CheckBox checkBox;
    private Switch ToggleTB;
    public ImageView mPhotoImageView;
    private ImageView info_icon;
    private ImageView photo_cam;
    private File photo;
    private String dirName;
    public String inspectionId;
    public String projectId = "null";
    private int projId;
    private int iId;
    private String fname;
    private String cat;
    private List<MapViewData> listItems;
    private String mImageFileLocation;
    private static final int REQUEST_OPEN_RESULT_CODE = 0, REQUEST_GET_SINGLE_FILE = 1;
    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private static final int PICK_CONTACT = 3;
    private RecyclerView recyclerView;
    //  private List<Joblistdata> jobList;
    private String FragDisplay;
    private Fragment fragment_obj;
    public ArrayList reportlistItems;
    public String propPhoto;
    private ProgressBar progressBar1;


    AmazonS3 s3Client;
    String bucket = "iappfiles";
    String root;
    TransferUtility transferUtility;
    List<String> listing;
    String[] file_names;
    String file_name;
    EditText prop_name;

    private View view;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ESMdb = new DBHandler(this, null, null, 1);


        s3credentialsProvider();

        // callback method to call the setTransferUtility method
        setTransferUtility();

        TextView projectlist_title = (TextView) findViewById(R.id.ProjectList);
        DBHandler dbHandlerA = new DBHandler(this, null, null, 1);

        init();

        ArrayList<HashMap<String, String>> Projects = dbHandlerA.getProjects(USER_ID);
        progressBar1 =  findViewById(R.id.progressBar1);
        listItems = new ArrayList<>();
        MapViewData listItem;

        for (int i = 0; i < (Projects.size()); i++){

            listItem = new MapViewData(

                    Integer.parseInt(Projects.get(i).get(MyConfig.TAG_PROJECT_ID)),

                    Integer.parseInt(Projects.get(i).get(MyConfig.TAG_LEVEL)),
                    Integer.parseInt(Projects.get(i).get(MyConfig.TAG_LEVEL)),
                    Integer.parseInt(Projects.get(i).get(MyConfig.TAG_LEVEL)),
                    Projects.get(i).get(MyConfig.TAG_LABEL),
                    Integer.parseInt(Projects.get(i).get(MyConfig.TAG_P_ID)),
                    Integer.parseInt(Projects.get(i).get(MyConfig.TAG_INSPECTION_ID)),
                    Integer.parseInt(Projects.get(i).get(MyConfig.TAG_PARENT)),
                    Projects.get(i).get(MyConfig.TAG_IMAGE1),
                    Projects.get(i).get(MyConfig.TAG_NOTES)

            );
            listItems.add(listItem);
        }

        GlobalVariables.dataList = (ArrayList<MapViewData>) listItems;
        //     TreeViewLists.LoadDisplayList();


        if (findViewById(R.id.fragment_container) != null){

            // However if we are being restored from a previous state, then we don't
            // need to do anything and should return or we could end up with overlapping Fragments
            if (savedInstanceState != null){
                return;
            }

            // Create an Instance of Fragment
            MapViewFragment treeFragment = new MapViewFragment();
            treeFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, treeFragment)
                    .commit();

        }



        buttonDownload = (Button) findViewById(R.id.btnDownloadJobs);
        buttonDownload.setOnClickListener(this);
        //     buttonDownloadPhotos = (Button) findViewById(R.id.btnDownloadPhotos);
        //     buttonDownloadPhotos.setOnClickListener(this);
        buttonClearAll = (Button) findViewById(R.id.btnClearData);
        buttonClearAll.setOnClickListener(this);
        buttonSyncAll = (Button) findViewById(R.id.btnSyncAll);
        buttonSyncAll.setOnClickListener(this);
        //     buttonSyncPhotos = (Button) findViewById(R.id.btnSyncPhotos);
        //     buttonSyncPhotos.setOnClickListener(this);
        //     buttonInspection = (Button) findViewById(R.id.btnInspection);
        //     buttonInspection.setOnClickListener(this);
        buttonLoadJobList = (Button) findViewById(R.id.btnloadJobs);
        buttonLoadJobList.setOnClickListener(this);

        btnLogin = (Button) findViewById(R.id.btnlogin);
        btnLogin.setOnClickListener(this);
        btnAddActivity = (Button) findViewById(R.id.addProject);
        btnAddActivity.setOnClickListener(this);
        //     buttonLoadNotes.setOnClickListener(this);
        //       info_icon = (ImageView) findViewById(R.id.imageView_info);
        //      info_icon.setOnClickListener(this);
        //      photo_cam =(ImageView) findViewById(R.id.imageView_cam);
        //      photo_cam.setOnClickListener(this);
        buttonClearAll.setEnabled(false);
        // listView = (ListView)findViewById(R.id.lstMain);



        ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cManager.getActiveNetworkInfo();
        if (nInfo != null && nInfo.isConnected()) {
            //getJSON();
        } else {
            buttonDownload.setEnabled(false);
            Toast.makeText(this, "No internet available", Toast.LENGTH_LONG).show();
        }
        //   updatePropList();



        dirName = new SimpleDateFormat("yyyyMMdd").format(new Date());
        Integer date = parseInt(dirName) - 7;

        ToggleTB=(Switch)findViewById(R.id.switch2);

        ToggleTB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) buttonClearAll.setEnabled(true);
                else buttonClearAll.setEnabled(false);

            }

        });


    }

    private void init(){

        ProjectInfoFragment fragment = new ProjectInfoFragment();
        doFragmentTransaction(fragment,"ProjectInfoFragment",false,"");

    }

    //returns the date/datetime depending on type required in YYYYMMDD format
    private String dayTime(int Type){

        String daytime = "20000101";

        switch (Type){

            case (1):{

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                Date date_  = Calendar.getInstance().getTime();
                daytime = (dateFormat.format(date_));
                break;
            }

            case (2):{

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
                Date date_  = Calendar.getInstance().getTime();
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

    private String datetoString(String date){
        String datestring = "";

        String[] idate = date.split("-");
        datestring = idate[0]+idate[1]+idate[2];

        return datestring;
    }



    private void doFragmentTransaction(Fragment fragment, String name, boolean addToBackStack, String message){
        androidx.fragment.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FragDisplay = name;
        transaction.replace(R.id.mainfragment_container,fragment,name);
        if(addToBackStack){
            transaction.addToBackStack(name);
        }
        transaction.commit();
    }

    @Override
    public void OnSelectionChanged(int treeNameIndex) {


        MapViewNode node = GlobalVariables.displayNodes.get(GlobalVariables.pos);


        projectId = Integer.toString(node.getprojId()); //This is setup in MainActivity as BranchCat to work with MapList
        inspectionId = Integer.toString(node.getiID());
        projId = node.getprojId();
        iId = node.getiID();


        TextView projlist = (TextView) findViewById(R.id.ProjectList);


        switch (node.getNodeLevel()) {




            case 0: {

                DBHandler dbHandler = new DBHandler(this, null, null, 1);

                HashMap<String, String> projectItem = dbHandler.getProjectInfo(projectId);
                //              mPhotoImageView = (ImageView) findViewById(R.id.imageView6);
                propPhoto =  projectItem.get(MyConfig.TAG_PROJECT_PHOTO);



                Bundle bundle = new Bundle();
                bundle.putString("branchHead", projectItem.get(MyConfig.TAG_ADDRESS_NO));
                bundle.putString("address", projectItem.get(MyConfig.TAG_PROJECT_ADDRESS));
                bundle.putString("note", projectItem.get(MyConfig.TAG_PROJECT_NOTE));
                bundle.putString("infoA", projectItem.get(MyConfig.TAG_INFO_A));
                bundle.putString("infoB", projectItem.get(MyConfig.TAG_INFO_B));
                bundle.putString("infoC", projectItem.get(MyConfig.TAG_INFO_C));
                bundle.putString("infoD", projectItem.get(MyConfig.TAG_INFO_D));
                bundle.putString("photo", projectItem.get(MyConfig.TAG_PROJECT_PHOTO));
                bundle.putString("infoE", projectItem.get(MyConfig.TAG_INFO_E));
                bundle.putString("infoF", projectItem.get(MyConfig.TAG_INFO_F));
                bundle.putString("infoG", projectItem.get(MyConfig.TAG_INFO_G));
                bundle.putString("infoH", projectItem.get(MyConfig.TAG_INFO_H));





                ProjectInfoFragment fragment = new ProjectInfoFragment();
                doFragmentTransaction(fragment, "ProjectInfoFragment", false, "");
                fragment.setArguments(bundle);
                //            fragment_obj = (ProjectInfoFragment) getSupportFragmentManager().findFragmentByTag("ProjectInfoFragment");
                break;
            }

            case 1: {

                DBHandler dbHandler = new DBHandler(this, null, null, 1);

                HashMap<String, String> projectItem = dbHandler.getInspection(projectId, inspectionId);

                //               mPhotoImageView = (ImageView) findViewById(R.id.imageView6);
                propPhoto =  projectItem.get(MyConfig.TAG_IMAGE);

 /*               if(propPhoto.length() > 14)
                {
                    dirName = propPhoto.substring(6, 14);
                    root = Environment.getExternalStorageDirectory().toString();
                    File propImage = new File(root + "/ESM_" + dirName + "/" + propPhoto);

                    if (propImage.exists()) {

                        Bitmap myBitmap = BitmapFactory.decodeFile(propImage.getAbsolutePath());
                        mPhotoImageView.setImageBitmap(myBitmap);
                    }
                }
                else {
                    Integer draw = android.R.drawable.ic_menu_camera;
                    mPhotoImageView.setImageDrawable(getDrawable(draw));
                }

  */

                Bundle bundle = new Bundle();
                bundle.putString("branchHead", projectItem.get(MyConfig.TAG_ADDRESS_NO));
                bundle.putString("branchLabel", projectItem.get(MyConfig.TAG_LABEL));
                bundle.putString("projectId",projectId);
                bundle.putString("inspectionId", inspectionId);
                bundle.putString("date", projectItem.get(MyConfig.TAG_INSPECTION_DATE));
                bundle.putString("dateInspected", projectItem.get(MyConfig.TAG_START_DATE_TIME));
                bundle.putString("endTime", projectItem.get(MyConfig.TAG_END_DATE_TIME));
                bundle.putString("note", projectItem.get(MyConfig.TAG_NOTE));
                bundle.putString("inpectType", projectItem.get(MyConfig.TAG_INSPECTION_TYPE));
                bundle.putString("auditor",projectItem.get(MyConfig.TAG_USER_ID));


                InspectInfoFragment fragment = new InspectInfoFragment();
                doFragmentTransaction(fragment, "InspectInfoFragment", false, "");
                fragment.setArguments(bundle);
                break;
            }
        }
    }



    @Override
    public void onResume(){

        super.onResume();
        //    updatePropList();


        GlobalVariables.dataList = (ArrayList<MapViewData>) listItems;
        MapViewLists.LoadDisplayList();
        GlobalVariables.modified = true;
        //    MapListAdapter mAdapter = new MapListAdapter(this);
        //   mAdapter.notifyDataSetChanged();
        //   OnSelectionChanged(0);
        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detail_text);


        if (GlobalVariables.modified == true) {
            MapViewFragment newDetailFragment = new MapViewFragment();
            Bundle args = new Bundle();
            detailFragment.mCurrentPosition = -1;

            args.putInt(DetailFragment.KEY_POSITION, 0);
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
            //       MapListAdapter mapListAdapter = new MapListAdapter(this);
            //       mapListAdapter.notifyDataSetChanged();
            //   OnSelectionChanged(0);

        }


    }


    private void requestProject(final String levelName){

        class reqAct extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... params) {
                RequestHandler_ rh = new RequestHandler_();
                rh.sendRequestParam(MyConfig.URL_REQUEST_PROJECT, levelName+"&USERID="+USER_ID);

                return null;

            }

        }
        reqAct req = new reqAct();
        req.execute();

    }




    @Override
    public void onClick(View v) {




        if (v == btnAddActivity) {


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add a new Project or Activity to existing Project ");
            // add a list
            String[] actions = {"Request New Project",
                    "Request New Activity for Existing Project ",
                    "Cancel Request "};

            builder.setItems(actions, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {

                        case 0: {

                            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                            View promptView = layoutInflater.inflate(R.layout.add_location, null);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                            alertDialogBuilder.setView(promptView);
                            final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                            itemTitle.setText("New Project Request  ");//Integer.parseInt(locationId)
                            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                            locationText.setText("Project ID : ");//Integer.parseInt(locationId)
                            final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
                            branchText.setHint("Enter your Project identification.");
                            alertDialogBuilder.setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            if(USER_ID > 0) {
                                                requestProject(branchText.getText().toString());
                                                downloadprojects();
                                            }
                                            else
                                                Toast.makeText(MainActivity.this, "Log in required ",Toast.LENGTH_LONG).show();



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

                            DBHandler dbHandler = new DBHandler(getBaseContext(), null, null, 1);

                            if(USER_ID > 0) {


                                final int pId = dbHandler.getInspectionpId(parseInt(projectId));
                                if(!projectId.equals("null")) {

                                    class reqAct extends AsyncTask<Void, Void, Void> {
                                        @Override
                                        protected Void doInBackground(Void... params) {
                                            RequestHandler_ rh = new RequestHandler_();
                                            rh.sendRequestParam(MyConfig.URL_REQUEST_ACTIVITY, projectId + "&pId=" + pId + "&USERID=" + USER_ID);
                                            return null;
                                        }

                                    }
                                    reqAct req = new reqAct();
                                    req.execute();
                                }
                                else
                                    Toast.makeText(MainActivity.this, "Select a Project ",Toast.LENGTH_LONG).show();
                            }
                            else
                                Toast.makeText(MainActivity.this, "Log in required ",Toast.LENGTH_LONG).show();


                            break;

                        }

                        case 2: {


                            break;
                        }

                        case 3: {


                            break;
                        }


                    }

                }
            });

            AlertDialog dialog = builder.create();

            dialog.show();


        }


        if (v == buttonDownload) {

            final DBHandler dbHandler = new DBHandler(getBaseContext(), null, null, 1);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Downloads");
            // add a list
            String[] actions = {"Download Projects",
                    "Download photos ",
                    "Cancel Request "};

            builder.setItems(actions, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    switch (which) {

                        case 0: {

                            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                            View promptView = layoutInflater.inflate(R.layout.call_log, null);
                            AlertDialog.Builder passDialog = new AlertDialog.Builder(MainActivity.this);
                            final EditText passText = (EditText) promptView.findViewById(R.id.code);
                            passDialog.setView(promptView);
                            passDialog.setTitle("Enter User Code");
                            passDialog.setCancelable(false).setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    USER_ID = dbHandler.checkCode(passText.getText().toString());
                                    if(USER_ID>0){
                                        if(dbHandler.checkstatus(USER_ID)==0)
                                            downloadprojects();
                                        else
                                            Toast.makeText(MainActivity.this, "Upload current data prior to downloading",Toast.LENGTH_SHORT).show();


                                    }
                                    else
                                        Toast.makeText(MainActivity.this, "Log in required for downloading",Toast.LENGTH_LONG).show();


                                }
                            });

                            // create an alert dialog
                            AlertDialog alert = passDialog.create();
                            alert.show();


                            break;

                        }

                        case 1: {

                            if(USER_ID == 0) {

                                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                                View promptView = layoutInflater.inflate(R.layout.call_log, null);
                                AlertDialog.Builder passDialog = new AlertDialog.Builder(MainActivity.this);
                                final EditText passText = (EditText) promptView.findViewById(R.id.code);
                                passDialog.setView(promptView);
                                passDialog.setTitle("Enter User Code");
                                passDialog.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        USER_ID = dbHandler.checkCode(passText.getText().toString());
                                        if (USER_ID > 0) {

                                            downloadphotos();

                                        } else
                                            Toast.makeText(MainActivity.this, "incorrect code or not logged in", Toast.LENGTH_LONG).show();


                                    }
                                });

                                // create an alert dialog
                                AlertDialog alert = passDialog.create();
                                alert.show();
                            }
                            else  downloadphotos();


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

        if (v == btnLogin) {

            final DBHandler dbHandler = new DBHandler(getBaseContext(), null, null, 1);

            if(dbHandler.checkstatus(USER_ID)>0){

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("LOGIN WARNING");
                builder.setMessage("Login will delete current unsaved data. Upload data prior to login.");

                builder.setPositiveButton("UPLOAD UNSAVED DATA", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                        View promptView = layoutInflater.inflate(R.layout.call_log, null);
                        AlertDialog.Builder passDialog = new AlertDialog.Builder(MainActivity.this);
                        final EditText passText = (EditText) promptView.findViewById(R.id.code);
                        passDialog.setView(promptView);
                        passDialog.setTitle("Enter User Code");
                        passDialog.setCancelable(false).setPositiveButton("OK",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                USER_ID = dbHandler.checkCode(passText.getText().toString());
                                if(USER_ID>0){
                                    uploaddata();
                                    uploadphotos();
                                }
                                else
                                    Toast.makeText(MainActivity.this, "Invalid Code",Toast.LENGTH_LONG).show();


                            }
                        });

                        // create an alert dialog
                        AlertDialog alert = passDialog.create();
                        alert.show();
                    }
                });

                builder.setNegativeButton("PROCEED WITH LOGIN", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                        View promptView = layoutInflater.inflate(R.layout.login, null);
                        AlertDialog.Builder loginDialog = new AlertDialog.Builder(MainActivity.this);
                        final EditText user = (EditText) promptView.findViewById(R.id.username);
                        final EditText password = (EditText) promptView.findViewById(R.id.password);
                        final TextView text = (TextView) promptView.findViewById(R.id.text);
                        loginDialog.setView(promptView);
                        loginDialog.setTitle("Login");
                        loginDialog.setCancelable(true).setPositiveButton("OK",new DialogInterface.OnClickListener() {


                            public void onClick(DialogInterface dialog, int id) {
                                USER_NAME=user.getText().toString();
                                PASS_WORD=password.getText().toString();
                                //         clearTablet();
                                updatePropList();
                                get_user_JSON();
                            }
                        });
                        AlertDialog alert = loginDialog.create();
                        alert.show();
                    }
                });


                AlertDialog alert = builder.create();
                alert.show();
            }
            else {

                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View promptView = layoutInflater.inflate(R.layout.login, null);
                AlertDialog.Builder loginDialog = new AlertDialog.Builder(MainActivity.this);
                final EditText user = (EditText) promptView.findViewById(R.id.username);
                final EditText password = (EditText) promptView.findViewById(R.id.password);
                final TextView text = (TextView) promptView.findViewById(R.id.text);
                loginDialog.setView(promptView);
                loginDialog.setTitle("Login");
                loginDialog.setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int id) {
                        USER_NAME = user.getText().toString();
                        PASS_WORD = password.getText().toString();
                        //      clearTablet();
                        updatePropList();
                        get_user_JSON();
                    }
                });
                AlertDialog alert = loginDialog.create();
                alert.show();

            }
        }


        if (v == buttonClearAll) {
            //Delete the information in the tables initially implemented for testing purposes
            clearTablet();
            updatePropList();
        }


        //Loads jobs from the tablet database
        if (v == buttonLoadJobList) {

            updatePropList();
        }


        if (v == buttonSyncAll) {
            final DBHandler dbHandler = new DBHandler(getBaseContext(), null, null, 1);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Cload Storage Upload Options");
            // add a list
            String[] actions = {"Upload All Activity Data recorded",
                    "Upload Text Data only",
                    "Upload Photo Data only",
                    "Cancel Request "};

            builder.setItems(actions, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {

                        case 0: {


                            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                            View promptView = layoutInflater.inflate(R.layout.call_log, null);
                            AlertDialog.Builder passDialog = new AlertDialog.Builder(MainActivity.this);
                            final EditText passText = (EditText) promptView.findViewById(R.id.code);
                            passDialog.setView(promptView);
                            passDialog.setTitle("Enter User Code");
                            passDialog.setCancelable(false).setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    USER_ID = dbHandler.checkCode(passText.getText().toString());
                                    if(USER_ID>0){
                                        uploaddata();
                                        uploadphotos();
                                    }
                                    else
                                        Toast.makeText(MainActivity.this, "Invalid Code",Toast.LENGTH_LONG).show();


                                }
                            });

                            // create an alert dialog
                            AlertDialog alert = passDialog.create();
                            alert.show();


                            break;


                        }

                        case 1: {

                            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                            View promptView = layoutInflater.inflate(R.layout.call_log, null);
                            AlertDialog.Builder passDialog = new AlertDialog.Builder(MainActivity.this);
                            final EditText passText = (EditText) promptView.findViewById(R.id.code);
                            passDialog.setView(promptView);
                            passDialog.setTitle("Enter User Code");
                            passDialog.setCancelable(false).setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    USER_ID = dbHandler.checkCode(passText.getText().toString());
                                    if(USER_ID>0)  uploaddata();
                                    else
                                        Toast.makeText(MainActivity.this, "Invalid Code",Toast.LENGTH_LONG).show();


                                }
                            });

                            // create an alert dialog
                            AlertDialog alert = passDialog.create();
                            alert.show();






                            break;

                        }

                        case 2: {


                            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                            View promptView = layoutInflater.inflate(R.layout.call_log, null);
                            AlertDialog.Builder passDialog = new AlertDialog.Builder(MainActivity.this);
                            final EditText passText = (EditText) promptView.findViewById(R.id.code);
                            passDialog.setView(promptView);
                            passDialog.setTitle("Enter User Code");
                            passDialog.setCancelable(false).setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    USER_ID = dbHandler.checkCode(passText.getText().toString());
                                    if(USER_ID>0) {

                                        CLIENT = dbHandler.getClient(passText.getText().toString());

                                        Thread thread = new Thread(new Runnable() {

                                            @Override
                                            public void run() {
                                                boolean exists = s3Client.doesBucketExist(CLIENT);
                                                if(!exists)
                                                    s3Client.createBucket(CLIENT);

                                            }

                                        });
                                        thread.start();

                                    }
                                    else
                                        Toast.makeText(MainActivity.this, "Invalid Code",Toast.LENGTH_LONG).show();

                                    uploadphotos();
                                }
                            });

                            // create an alert dialog

                            AlertDialog alert = passDialog.create();
                            alert.show();


                            break;
                        }

                        case 3: {


                            break;
                        }
                    }

                }
            });

            AlertDialog dialog = builder.create();

            dialog.show();

        }



        if (v == photo_cam) {



        }



        if (v == info_icon) {

            root = Environment.getExternalStorageDirectory().getPath();
            File propImage = new File(root+ "/"+projectId+"INFO/");
            //  File propImage = new File(root, propId+"INFO/");
            //  File propImage = new File(root, "ESM/test.jpg");
            //  String dir = propId+"INFO/";
            //  File propImage = new File(root);
            Intent galleryIntent = new Intent(Intent.ACTION_VIEW);

            Uri data = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".provider", propImage);
            // Uri data = Uri.parse(propImage.getAbsolutePath());
            galleryIntent.setDataAndType(data, "*/*");

            // galleryIntent.setDataAndType(Uri.withAppendedPath(Uri.fromFile(propImage) ,dir),"image/*" );
            // galleryIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            // galleryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


            try {

                //    startActivityForResult(Intent.createChooser(galleryIntent, "SELECT FILE"), REQUEST_OPEN_RESULT_CODE);
                //  startActivity(galleryIntent);
                startActivity(Intent.createChooser(galleryIntent,"OPEN"));
                // startActivity(galleryIntent);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("tag", "Exception found while listing " + e);


            }
        }


    }





    public void reportMenu() {



        // setup the alert builder

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choose an action");
        // add a list
        String[] actions = {"Compile and review the inspection report.",
                "Compile and email report to user",
                "Compile and email report to Contact List entity",
                "Compile inspection certificate",
                "Cancel this operation."};
        builder.setItems(actions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: {

                        Bundle bundle = new Bundle();
                        bundle.putInt("projectId", projId);
                        bundle.putInt("iId", iId);


                        DBHandler dbHandler = new DBHandler(getApplicationContext(), null, null, 1);
                        ArrayList<HashMap<String, String>> listItemsmap = dbHandler.getInspectedItems(projId, iId);

                        //     recyclerView  = (RecyclerView) findViewById(R.id.reportView);
                        //     recyclerView.setHasFixedSize(true);
                        //     recyclerView.setLayoutManager(new LinearLayoutManager(this));


                        reportlistItems = new ArrayList<>();
                        ReportItem listItem;

                        for (int i = 0; i <= (listItemsmap.size() - 1); i++) {
                            listItem = new ReportItem(
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
                                    listItemsmap.get(i).get(MyConfig.TAG_STAGE)


                            );

                            reportlistItems.add(listItem);

                            Log.v("report list", listItemsmap.get(i).get("BranchHead")+", ");
                        }


                        ReportFragment fragment = new ReportFragment();
                        fragment.setArguments(bundle);
                        doFragmentTransaction(fragment, "ReportFragment", false, "");
                        //    fragment_obj = (ReportFragment)getSupportFragmentManager().findFragmentByTag("ReportFragment");


                        break;
                    }
                    case 1: {

                        Intent intentContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        startActivityForResult(intentContact, PICK_CONTACT);

                        break;

                    } //
                    case 2: {

               /*          LayoutInflater layoutInflater = LayoutInflater.from(InspectionActivity.this);
                            View promptView = layoutInflater.inflate(R.layout.delete_location, null);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InspectionActivity.this);
                            alertDialogBuilder.setView(promptView);
                            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                            locationText.setText("Warning - this will delete the zone and associated data");//location.getText().toString());




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


                */
                        break;
                    }

                }
                //end of case 0
            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();

        dialog.show();

    }



    public void takeImageFromCamera(View view) {

        // Toast.makeText(this, "just before cameraintent",Toast.LENGTH_SHORT).show();
        Intent camera_intent = new Intent();
        camera_intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        // Toast.makeText(this, "photoframe ="+ photoframe,Toast.LENGTH_SHORT).show();


        photo = null;
        try {
            photo = createPhotoFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".provider", photo));
        startActivityForResult(camera_intent, ACTIVITY_START_CAMERA_APP);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {



        if (requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK) {

            MapViewNode node = GlobalVariables.displayNodes.get(GlobalVariables.pos);
            DBHandler dbHandler = new DBHandler(this, null, null, 1);

            switch (node.getNodeLevel()){

                case 0:{

                    dbHandler.updatePropPhoto(projectId, photo.getName());
                }

                case 1:{

                    dbHandler.updateInspectionPhoto(projectId, inspectionId, photo.getName());

                }
            }


            try {
                rotateImage(resizePhoto());
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(photo)));




            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if (requestCode == ACTIVITY_START_CAMERA_APP) {
            photo.delete();

        }



        if (requestCode == PICK_CONTACT) {

            final String emailAdress;
            emailAdress = getContactInfo(data);


            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
            View promptView = layoutInflater.inflate(R.layout.email_accept, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            alertDialogBuilder.setView(promptView);
            final TextView locationText = (TextView) promptView.findViewById(R.id.email);
            locationText.setText(emailAdress);//location.getText().toString());

            alertDialogBuilder.setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            reportMailer();
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

    protected String getContactInfo(Intent data)
    {

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
                email = cursor.getString(emailIdx);
                name = cursor.getString(nameId);
                Log.v(" Email", "Got email: " + email);
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
        return email;
    }//getContactInfo

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


    //Update the list to choose from by selecting the information from the SQLite db
    public void updatePropList() {

        //Get the property information for all the properties to inspect
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        //   final ArrayList<HashMap<String, String>> list = dbHandler.getAllProjects();
        //Get a list of all the images for the properties to inspect
        //  DBHandler dbHandlerphoto = new DBHandler(this, null, null, 1);
        //   ArrayList<HashMap<String, String>> photolist = dbHandler.getInspectedItemPhotos();
        ArrayList<HashMap<String, String>> Projects = dbHandler.getProjects(USER_ID);
        listItems = new ArrayList<>();
        MapViewData listItem;

        TextView projectlist_title = (TextView) findViewById(R.id.ProjectList);
         projectlist_title.setText(dbHandler.getUser(USER_ID)+"    Project list:");
        for (int i = 0; i < (Projects.size()); i++){

            listItem = new MapViewData(

                    Integer.parseInt(Projects.get(i).get(MyConfig.TAG_PROJECT_ID)),
                    Integer.parseInt(Projects.get(i).get(MyConfig.TAG_LEVEL)),
                    Integer.parseInt(Projects.get(i).get(MyConfig.TAG_LEVEL)),
                    Integer.parseInt(Projects.get(i).get(MyConfig.TAG_LEVEL)),
                    Projects.get(i).get(MyConfig.TAG_LABEL),
                    Integer.parseInt(Projects.get(i).get(MyConfig.TAG_P_ID)),
                    Integer.parseInt(Projects.get(i).get(MyConfig.TAG_INSPECTION_ID)),
                    Integer.parseInt(Projects.get(i).get(MyConfig.TAG_PARENT)),
                    Projects.get(i).get(MyConfig.TAG_IMAGE1),
                    Projects.get(i).get(MyConfig.TAG_NOTES)
            );
            listItems.add(listItem);
        }

        GlobalVariables.dataList = (ArrayList<MapViewData>) listItems;
        //     TreeViewLists.LoadDisplayList();


        MapViewLists.LoadDisplayList();
        GlobalVariables.modified = true;
        //    MapListAdapter mAdapter = new MapListAdapter(this);
        //   mAdapter.notifyDataSetChanged();
        //   OnSelectionChanged(0);
        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detail_text);


        if (GlobalVariables.modified == true) {
            MapViewFragment newDetailFragment = new MapViewFragment();
            Bundle args = new Bundle();
            detailFragment.mCurrentPosition = -1;

            args.putInt(DetailFragment.KEY_POSITION, 0);
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
            //       MapListAdapter mapListAdapter = new MapListAdapter(this);
            //       mapListAdapter.notifyDataSetChanged();
            //   OnSelectionChanged(0);
        }


    }


    //Get projects from server

    private void updateProjects() {
        JSONObject jsonObject = null;

        //editTextMessage.setText("Print String: " + JSON_STRING);
        //editTextMessage.setText("Test");
        try {
            jsonObject = new JSONObject(JSON_STRING_PROJECT_LIST);
            JSONArray result = jsonObject.getJSONArray(MyConfig.TAG_JSON_ARRAY);
            //   editTextMessage.setText("Test begin additional");

            for (int i = 0; i < result.length(); i++) {
                // testing only -
                //  String imsg = Integer.toString(i);
                //   String rmsg = Integer.toString(result.length());
                //   String msg = "Testing Loop additional " + imsg + " result no " + rmsg;
                //     editTextMessage.setText(msg);

                JSONObject jo = result.getJSONObject(i);
                String projectId = jo.getString(MyConfig.TAG_PROJECT_ID);
                String addressNo = jo.getString(MyConfig.TAG_ADDRESS_NO);
                String address = jo.getString(MyConfig.TAG_PROJECT_ADDRESS);
                String suburb = jo.getString(MyConfig.TAG_PROJECT_SUBURB);
                String infoA = jo.getString(MyConfig.TAG_INFO_A);
                String infoB = jo.getString(MyConfig.TAG_INFO_B);
                String infoC = jo.getString(MyConfig.TAG_INFO_C);
                String infoD = jo.getString(MyConfig.TAG_INFO_D);
                String projectNote = jo.getString(MyConfig.TAG_PROJECT_NOTE);
                String projectPhoto = jo.getString(MyConfig.TAG_PROJECT_PHOTO);
                String infoE = jo.getString(MyConfig.TAG_INFO_E);
                String infoF = jo.getString(MyConfig.TAG_INFO_F);
                String infoG = jo.getString(MyConfig.TAG_INFO_G);
                String infoH = jo.getString(MyConfig.TAG_INFO_H);
                String infoI = jo.getString(MyConfig.TAG_INFO_I);
                String infoJ = jo.getString(MyConfig.TAG_INFO_J);
                String inspectionId = jo.getString(MyConfig.TAG_INSPECTION_ID);
                String inspectionType = jo.getString(MyConfig.TAG_INSPECTION_TYPE);
                String inspectionDate = datetoString(jo.getString(MyConfig.TAG_INSPECTION_DATE));
                String inspector = jo.getString(MyConfig.TAG_INSPECTOR);
                String inspectionStatus = jo.getString(MyConfig.TAG_INSPECTION_STATUS);
                String startDateTime = jo.getString(MyConfig.TAG_START_DATE_TIME);
                String endDateTime = jo.getString(MyConfig.TAG_END_DATE_TIME);
                String Label = jo.getString(MyConfig.TAG_LABEL);
                String Level = jo.getString(MyConfig.TAG_LEVEL);
                String Parent = jo.getString(MyConfig.TAG_PARENT);
                String pID = jo.getString(MyConfig.TAG_P_ID);
                String Image = jo.getString(MyConfig.TAG_IMAGE);
                String Note = jo.getString(MyConfig.TAG_NOTE);

                DBHandler dbHandler = new DBHandler(this, null, null, 1);

                int projId = parseInt(projectId);
                int InspectionId = parseInt(inspectionId);
                int level = parseInt(Level);
                int parent = parseInt(Parent);
                int p_Id = parseInt(pID);


                ProjectAttributes projectrow =
                        new ProjectAttributes(projId, addressNo, address, suburb, infoA, infoB, infoC, infoD, projectPhoto, infoE, infoF, infoG, infoH, infoI, infoJ, projectNote );
                // editTextMessage.setText("Test 1");


                InspectionAttributes inspectionRow = new InspectionAttributes(InspectionId, inspectionType, inspectionStatus, projId, inspectionDate, inspector, startDateTime, endDateTime, Label, level, parent, p_Id, Image, Note);

                // editTextMessage.setText("Test 2");


                //editTextMessage.setText("Test 5");

                dbHandler.updateProjectsFromServer(projectrow, inspectionRow);

                // testing only -
                //   editTextMessage.setText("Test end additional");

                updatePropList();

            }

        } catch (JSONException e) {
            // editTextMessage.setText(" Exception");
            Log.e("ESM", "unexpected JSON exception", e);
            //e.printStackTrace();
        }
        // testing only -
        // editTextMessage.setText("Test 4");
    }


    //Update the inspection items SQLite db with data from MySQL db
    private void updatePropInfo() {
        JSONObject jsonObject = null;

        //editTextMessage.setText("Print String: " + JSON_STRING);
        //editTextMessage.setText("Test");
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(MyConfig.TAG_JSON_ARRAY);
            //  editTextMessage.setText("Test begin");

            for (int i = 0; i < result.length(); i++) {
                // testing only -
                //  String imsg = Integer.toString(i);
                //  String rmsg = Integer.toString(result.length());
                //  String msg = "Testing Loop "+imsg+" result no "+rmsg;
                //  TextMessage.setText(msg);

                JSONObject jo = result.getJSONObject(i);
                String projid = jo.getString(MyConfig.TAG_PROJECT_ID);
                String iId = jo.getString(MyConfig.TAG_INSPECTION_ID);
                String aId = jo.getString(MyConfig.TAG_A_ID);
                String dateInspected = datetoString(jo.getString(MyConfig.TAG_DATE_INSPECTED));
                String overview = jo.getString(MyConfig.TAG_OVERVIEW);
                String servicedBy = jo.getString(MyConfig.TAG_SERVICED_BY);
                String relevantInfo = jo.getString(MyConfig.TAG_RELEVANT_INFO);
                String serviceLevel = jo.getString(MyConfig.TAG_SERVICE_LEVEL);
                String reportImg = jo.getString(MyConfig.TAG_REPORT_IMAGE);
                String image1 = jo.getString(MyConfig.TAG_IMAGE1);
                String com1 = jo.getString(MyConfig.TAG_COM1);
                String image2 = jo.getString(MyConfig.TAG_IMAGE2);
                String com2 = jo.getString(MyConfig.TAG_COM2);
                String image3 = jo.getString(MyConfig.TAG_IMAGE3);
                String com3 = jo.getString(MyConfig.TAG_COM3);
                String image4 = jo.getString(MyConfig.TAG_IMAGE4);
                String com4 = jo.getString(MyConfig.TAG_COM4);
                String image5 = jo.getString(MyConfig.TAG_IMAGE5);
                String com5 = jo.getString(MyConfig.TAG_COM5);
                String image6 = jo.getString(MyConfig.TAG_IMAGE6);
                String com6 = jo.getString(MyConfig.TAG_COM6);
                String image7 = jo.getString(MyConfig.TAG_IMAGE7);
                String com7 = jo.getString(MyConfig.TAG_COM7);
                String itemStatus = jo.getString(MyConfig.TAG_ITEM_STATUS);
                String notes = jo.getString(MyConfig.TAG_NOTES);



                DBHandler dbHandler = new DBHandler(this, null, null, 1);

                int projId = parseInt(projid);
                int InspectionId = parseInt(iId);
                int a_Id = parseInt(aId);

//                int startDateTime_ = parseInt(startDateTime);
//                int endDateTime_ = parseInt(endDateTime);
                //             int ServiceLevel = parseInt(serviceLevel);


                InspectionItemAttributes itemRow = new InspectionItemAttributes(InspectionId, projId, a_Id, dateInspected, overview, servicedBy, relevantInfo, serviceLevel, reportImg, image1, com1, image2, com2
                        , image3, com3, image4, com4, image5, com5, image6, com6, image7, com7, itemStatus, notes);

                //editTextMessage.setText("Test 5");


                dbHandler.updateFromServer(itemRow);

            }

        } catch (JSONException e) {
            // editTextMessage.setText(" Exception");
            Log.e("ESM", "unexpected JSON exception", e);
            //e.printStackTrace();
        }
        // testing only -
        // editTextMessage.setText("Test 4");
    }


    //Update the MAP SQLite db with additional from MAP MySQL db
    private void updateAdditionalInfo() {
        JSONObject jsonObject = null;

        //editTextMessage.setText("Print String: " + JSON_STRING);
        //editTextMessage.setText("Test");
        try {
            jsonObject = new JSONObject(JSON_STRING_ADDITIONAL);
            JSONArray result = jsonObject.getJSONArray(MyConfig.TAG_JSON_ARRAY);
            //   editTextMessage.setText("Test begin additional");

            for (int i = 0; i < result.length(); i++) {
                // testing only -
                //  String imsg = Integer.toString(i);
                //   String rmsg = Integer.toString(result.length());
                //   String msg = "Testing Loop additional " + imsg + " result no " + rmsg;
                //     editTextMessage.setText(msg);

                JSONObject jo = result.getJSONObject(i);
                String projId = jo.getString(MyConfig.TAG_PROJECT_ID);
                String catId = jo.getString(MyConfig.TAG_CAT_ID);
                String Level = jo.getString(MyConfig.TAG_LEVEL);
                String Parent = jo.getString(MyConfig.TAG_PARENT);
                String Label = jo.getString(MyConfig.TAG_LABEL);
                String Child = jo.getString(MyConfig.TAG_CHILD);
                String aId = jo.getString(MyConfig.TAG_A_ID);
                String iId = jo.getString(MyConfig.TAG_INSPECTION_ID);
                String Photo = jo.getString(MyConfig.TAG_IMAGE1);
                String Note = jo.getString(MyConfig.TAG_NOTES);


                DBHandler dbHandler = new DBHandler(this, null, null, 1);

                int ProjId = parseInt(projId);
                int a_Id = parseInt(aId);
                int i_Id = parseInt(iId);
                int CatId = parseInt(catId);
                int level = parseInt(Level);
                int parent = parseInt(Parent);
                int child = parseInt(Child);


                MAPattributes mapRow = new MAPattributes(ProjId, CatId, level, parent, Label, child, a_Id, i_Id, Photo, Note);
                // editTextMessage.setText("Additional Test 1");

                //editTextMessage.setText("Test 5");

                dbHandler.updateAdditionalFromServer(mapRow);

                // testing only -
                //   editTextMessage.setText("Test end additional");

            }

        } catch (JSONException e) {
            // editTextMessage.setText(" Exception");
            Log.e("ESM", "unexpected JSON exception", e);
            //e.printStackTrace();
        }
        // testing only -
        // editTextMessage.setText("Test 4");
    }

    // Get ActionItems from the server
    private void updateAction() {
        JSONObject jsonObject = null;

        //editTextMessage.setText("Print String: " + JSON_STRING);
        //editTextMessage.setText("Test");
        try {
            jsonObject = new JSONObject(JSON_STRING_ACTION);
            JSONArray result = jsonObject.getJSONArray(MyConfig.TAG_JSON_ARRAY);
            //  editTextMessage.setText("Test begin");

            for (int i = 0; i < result.length(); i++) {
                // testing only -
                //  String imsg = Integer.toString(i);
                //  String rmsg = Integer.toString(result.length());
                //  String msg = "Testing Loop "+imsg+" result no "+rmsg;
                //  TextMessage.setText(msg);

                JSONObject jo = result.getJSONObject(i);
                String projid = jo.getString(MyConfig.TAG_PROJECT_ID);
                String iId = jo.getString(MyConfig.TAG_INSPECTION_ID);
                String aId = jo.getString(MyConfig.TAG_A_ID);
                String dateInspected = datetoString(jo.getString(MyConfig.TAG_DATE_INSPECTED));
                String overview = jo.getString(MyConfig.TAG_OVERVIEW);
                String servicedBy = jo.getString(MyConfig.TAG_SERVICED_BY);
                String relevantInfo = jo.getString(MyConfig.TAG_RELEVANT_INFO);
                String serviceLevel = jo.getString(MyConfig.TAG_SERVICE_LEVEL);
                String reportImg = jo.getString(MyConfig.TAG_REPORT_IMAGE);
                String image1 = jo.getString(MyConfig.TAG_IMAGE1);
                String com1 = jo.getString(MyConfig.TAG_COM1);
                String itemStatus = jo.getString(MyConfig.TAG_ITEM_STATUS);
                String notes = jo.getString(MyConfig.TAG_NOTES);



                DBHandler dbHandler = new DBHandler(this, null, null, 1);

                int projId = parseInt(projid);
                int InspectionId = parseInt(iId);
                int a_Id = parseInt(aId);


                ActionItemAttributes actionrow = new ActionItemAttributes(InspectionId, projId, a_Id, dateInspected, overview, servicedBy, relevantInfo, serviceLevel, reportImg, image1, com1,itemStatus, notes);



                dbHandler.updateActionFromServer(actionrow);

            }

        } catch (JSONException e) {
            // editTextMessage.setText(" Exception");
            Log.e("ESM", "unexpected JSON exception", e);
            //e.printStackTrace();
        }
        // testing only -
        // editTextMessage.setText("Test 4");
    }


    // Get ActionItems from the server
    private void updateCertificateInspection() {
        JSONObject jsonObject = null;

        //editTextMessage.setText("Print String: " + JSON_STRING);
        //editTextMessage.setText("Test");
        try {
            jsonObject = new JSONObject(JSON_STRING_CERTINSP);
            JSONArray result = jsonObject.getJSONArray(MyConfig.TAG_JSON_ARRAY);
            //  editTextMessage.setText("Test begin");

            for (int i = 0; i < result.length(); i++) {
                // testing only -
                //  String imsg = Integer.toString(i);
                //  String rmsg = Integer.toString(result.length());
                //  String msg = "Testing Loop "+imsg+" result no "+rmsg;
                //  TextMessage.setText(msg);

                JSONObject jo = result.getJSONObject(i);
                String projid = jo.getString(MyConfig.TAG_PROJECT_ID);
                String iId = jo.getString(MyConfig.TAG_INSPECTION_ID);
                String datetime = datetoString(jo.getString(MyConfig.TAG_DATE_TIME));
                String overview = jo.getString(MyConfig.TAG_OVERVIEW);
                String permit = jo.getString(MyConfig.TAG_PERMIT);
                String projectaddress = jo.getString(MyConfig.TAG_PROJECT_ADDRESS);
                String stage = jo.getString(MyConfig.TAG_STAGE);
                String notes = jo.getString(MyConfig.TAG_NOTES);



                DBHandler dbHandler = new DBHandler(this, null, null, 1);

                int projId = parseInt(projid);
                int InspectionId = parseInt(iId);

                CertificateInspectionAttributes certinsprow = new CertificateInspectionAttributes(InspectionId, projId, datetime, overview, permit, projectaddress, stage, notes);



                dbHandler.updateCertificateInspectionFromServer(certinsprow);

            }

        } catch (JSONException e) {
            // editTextMessage.setText(" Exception");
            Log.e("ESM", "unexpected JSON exception", e);
            //e.printStackTrace();
        }
        // testing only -
        // editTextMessage.setText("Test 4");
    }

    private void update_USER_Info() {
        JSONObject jsonObject = null;

        //editTextMessage.setText("Print String: " + JSON_STRING);
        //editTextMessage.setText("Test");
        try {
            jsonObject = new JSONObject(JSON_STRING_USER);
            JSONArray result = jsonObject.getJSONArray(MyConfig.TAG_JSON_ARRAY);
            //    editTextMessage.setText("Test begin Category " + result);

            for (int i = 0; i < result.length(); i++) {
                // testing only -
                String imsg = Integer.toString(i);
                String rmsg = Integer.toString(result.length());
                String msg = "Testing Loop category " + imsg + " result no " + rmsg;
                //       editTextMessage.setText(msg);

                JSONObject jo = result.getJSONObject(i);
                String userID = jo.getString(MyConfig.TAG_USER_ID);
                String userName = jo.getString(MyConfig.TAG_USER_NAME);
                String userCode = jo.getString(MyConfig.TAG_USER_CODE);
                String clientName = jo.getString(MyConfig.TAG_CLIENT_NAME);


                DBHandler dbHandler = new DBHandler(this, null, null, 1);

                int uID = parseInt(userID);
                int uCode = parseInt(userCode);

                USER_Attributes user_attributes = new USER_Attributes(uID,userName, uCode,clientName);


                //editTextMessage.setText("Test 5");

                dbHandler.update_USER_FromServer(user_attributes);

                // testing only -
                //   editTextMessage.setText("Test Category end");

            }

        } catch (JSONException e) {
            // editTextMessage.setText(" Exception");
            Log.e("ESM", "unexpected JSON exception", e);
            //e.printStackTrace();
        }
        // testing only -
        // editTextMessage.setText("Test 4");
    }


    private void update_OR_Info(String CAT) {
        JSONObject jsonObject = null;
        cat = "a";
        //editTextMessage.setText("Print String: " + JSON_STRING);
        //editTextMessage.setText("Test");
        try {
            jsonObject = new JSONObject(JSON_STRING_OR);
            JSONArray result = jsonObject.getJSONArray(MyConfig.TAG_JSON_ARRAY);
            //    editTextMessage.setText("Test begin Category " + result);

            for (int i = 0; i < result.length(); i++) {
                // testing only -
                String imsg = Integer.toString(i);
                String rmsg = Integer.toString(result.length());
                String msg = "Testing Loop category " + imsg + " result no " + rmsg;
                //       editTextMessage.setText(msg);

                JSONObject jo = result.getJSONObject(i);
                String num = jo.getString(MyConfig.TAG_NUM);
                String subCat = jo.getString(MyConfig.TAG_SUBCAT);
                String type = jo.getString(MyConfig.TAG_TYPE);
                String note = jo.getString(MyConfig.TAG_NOTE);

                DBHandler dbHandler = new DBHandler(this, null, null, 1);

                int typ = parseInt(type);
                int numb = parseInt(num);

                A_Attributes OR_Row = new A_Attributes(numb, subCat, typ, note);


                //editTextMessage.setText("Test 5");

                dbHandler.update_OR_FromServer(OR_Row, CAT);

                // testing only -
                //   editTextMessage.setText("Test Category end");

            }

        } catch (JSONException e) {
            // editTextMessage.setText(" Exception");
            Log.e("ESM", "unexpected JSON exception", e);
            //e.printStackTrace();
        }
        // testing only -
        // editTextMessage.setText("Test 4");
    }


    private void clearTablet() {
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        dbHandler.clearAllData();

    }


    private void getProjectsJSON() {
        class GetProjectsJSON extends AsyncTask<Void, Void, String> {


            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Connecting to the server", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING_PROJECT_LIST = s;
                //    TextMessage.setText("JSON_STRING " + s);

                updateProjects();
                // testing only - editTextMessage.setText(JSON_STRING);
            }

            @Override
            protected String doInBackground(Void... params) {

                RequestHandler_ rh = new RequestHandler_();
                String s = rh.sendGetRequestParam(MyConfig.URL_GET_PROJECTS, Integer.toString(USER_ID));
                return s;
            }



        }
        GetProjectsJSON gj = new GetProjectsJSON();
        gj.execute();
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {



            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Connecting to the server", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                //    TextMessage.setText("JSON_STRING " + s);

                updatePropInfo();
                // testing only - editTextMessage.setText(JSON_STRING);
            }

            @Override
            protected String doInBackground(Void... params) {

                RequestHandler_ rh = new RequestHandler_();
                String s = rh.sendGetRequestParam(MyConfig.URL_GET_PROJECT_INFO, Integer.toString(USER_ID));
                return s;
            }

        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void getAdditionalJSON() {

        class GetAdditionalJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Connecting to the server", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING_ADDITIONAL = s;
                //    TextMessage.setText("JSON_STRING " + s);

                updateAdditionalInfo();
                // testing only - editTextMessage.setText(JSON_STRING);
            }

            @Override
            protected String doInBackground(Void... params) {

                RequestHandler_ rh = new RequestHandler_();
                String s = rh.sendGetRequestParam(MyConfig.URL_GET_ADDITIONAL_INFO, Integer.toString(USER_ID));
                return s;
            }

        }
        GetAdditionalJSON gj = new GetAdditionalJSON();
        gj.execute();
    }


    private void getActionJSON() {
        class GetActionJSON extends AsyncTask<Void, Void, String> {


            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Connecting to the server", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING_ACTION = s;
                //    TextMessage.setText("JSON_STRING " + s);

                updateAction();
                // testing only - editTextMessage.setText(JSON_STRING);
            }

            @Override
            protected String doInBackground(Void... params) {

                RequestHandler_ rh = new RequestHandler_();
                String s = rh.sendGetRequestParam(MyConfig.URL_GET_ACTION, Integer.toString(USER_ID));
                return s;
            }



        }
        GetActionJSON gj = new GetActionJSON();
        gj.execute();
    }


    private void getCertInspJSON() {

        class GetCertInspJSON extends AsyncTask<Void, Void, String> {


            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Connecting to the server", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING_CERTINSP = s;
                //    TextMessage.setText("JSON_STRING " + s);

                updateCertificateInspection();
                // testing only - editTextMessage.setText(JSON_STRING);
            }

            @Override
            protected String doInBackground(Void... params) {

                RequestHandler_ rh = new RequestHandler_();
                String s = rh.sendGetRequestParam(MyConfig.URL_GET_CERT_INSPECTION, Integer.toString(USER_ID));
                return s;
            }



        }
        GetCertInspJSON gj = new GetCertInspJSON();
        gj.execute();
    }


    private void get_AOR_JSON() {

        class Get_OR_JSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;



            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Connecting to the server", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING_OR = s;

                update_OR_Info("A");

            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler_ rh = new RequestHandler_();
                String s = rh.sendGetRequestParam(MyConfig.URL_GET_OR_INFO,"A_OR");
                return s;
            }

        }
        Get_OR_JSON gj = new Get_OR_JSON();
        gj.execute();
    }

    private void get_BOR_JSON() {

        class Get_BOR_JSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Connecting to the server", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING_OR = s;

                update_OR_Info("B");
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler_ rh = new RequestHandler_();
                String s = rh.sendGetRequestParam(MyConfig.URL_GET_OR_INFO,"B_OR");
                return s;
            }

        }
        Get_BOR_JSON gj = new Get_BOR_JSON();
        gj.execute();
    }


    private void get_user_JSON() {

        class Get_USER_JSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Connecting to the server", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING_USER = s;
                if(s == "User not found" || s == "incorrect password") {
                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                }
                else {
                    update_USER_Info();
                    Toast.makeText(MainActivity.this, "logged in", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler_ rh = new RequestHandler_();
                String s = rh.sendGetRequestParam(MyConfig.URL_GET_USER_INFO,USER_NAME+"&password="+PASS_WORD);
                return s;
            }

        }
        Get_USER_JSON gj = new Get_USER_JSON();
        gj.execute();
    }

    //Upload tablet inspection data to the server
    private String inspToServer() throws IOError {
        String res = "initiated";
        HashMap<String, String> inspMap = new HashMap<String, String>();
        DBHandler dbHandler = new DBHandler(MainActivity.this, null, null, 1);
        //           dbHandler.puTestData();
        ArrayList<HashMap<String, String>> inspList = dbHandler.getInspections(USER_ID);
        String testString = "";
        String tempString = "";

        //  EditText propertyPhoto;

        //  propertyPhoto.setText(inspList);

        JSONObject json;
        JSONArray jsonArray = new JSONArray();
        int j = 0;

        if (inspList.size() == 0) {
            //no items toupdatelist
            res = "no_records";
        } else {
            for (int i = 0; i < inspList.size(); i++) {
                tempString = "Iteration, " + Integer.toString(i) + "| " + MyConfig.TAG_INSPECTION_ID + ", " + inspList.get(i).get(MyConfig.TAG_INSPECTION_ID) + " ^-^ ";
                testString = testString + tempString;


                try {
                    json = new JSONObject();
                    json.put("Iteration", Integer.toString(j));
                    json.put(MyConfig.TAG_INSPECTION_ID, inspList.get(i).get(MyConfig.TAG_INSPECTION_ID));
                    if(inspList.get(i).get(MyConfig.TAG_INSPECTION_DATE)==null){json.put(MyConfig.TAG_INSPECTION_DATE, "20190601");}
                    else {json.put(MyConfig.TAG_INSPECTION_DATE, inspList.get(i).get(MyConfig.TAG_INSPECTION_DATE));}
                    json.put(MyConfig.TAG_INSPECTION_TYPE, inspList.get(i).get(MyConfig.TAG_INSPECTION_TYPE));
                    json.put(MyConfig.TAG_INSPECTION_STATUS, "p"); //
                    json.put(MyConfig.TAG_PROJECT_ID, inspList.get(i).get(MyConfig.TAG_PROJECT_ID));
                    json.put(MyConfig.TAG_INSPECTOR, inspList.get(i).get(MyConfig.TAG_INSPECTOR));
                    if(inspList.get(i).get(MyConfig.TAG_START_DATE_TIME)==null) json.put(MyConfig.TAG_START_DATE_TIME, "20200101010101");
                    else
                        json.put(MyConfig.TAG_START_DATE_TIME, inspList.get(i).get(MyConfig.TAG_START_DATE_TIME));
                    if(inspList.get(i).get(MyConfig.TAG_END_DATE_TIME)==null) json.put(MyConfig.TAG_END_DATE_TIME, "20200101010101");
                    else
                        json.put(MyConfig.TAG_END_DATE_TIME, inspList.get(i).get(MyConfig.TAG_END_DATE_TIME));
                    json.put(MyConfig.TAG_LABEL, inspList.get(i).get(MyConfig.TAG_LABEL));
                    json.put(MyConfig.TAG_LEVEL, inspList.get(i).get(MyConfig.TAG_LEVEL));
                    json.put(MyConfig.TAG_PARENT, inspList.get(i).get(MyConfig.TAG_PARENT));
                    json.put(MyConfig.TAG_P_ID, inspList.get(i).get(MyConfig.TAG_P_ID));
                    json.put(MyConfig.TAG_IMAGE, inspList.get(i).get(MyConfig.TAG_IMAGE));
                    json.put(MyConfig.TAG_NOTE, inspList.get(i).get(MyConfig.TAG_NOTE));


                    jsonArray.put(json);

                    j = j + 1;

                } catch (Throwable t) {
                    res = "Request failed: " + t.toString();
                    return res;
                }
            }
            RequestHandler_ rh = new RequestHandler_();

            String regex = "\\/'";   //,?\s*"a[^"]*z"\s*:[^\}]+

            String jsonString = jsonArray.toString().replaceAll(regex,"");





            res = rh.sendJsonPostRequest(MyConfig.URL_SYNC_INSPECTION_TO_SERVER, jsonString);
            //       res = jsonString;
            //   Log.v("MAP JSON", jsonString);
        }

        return res;

    }


    //Upload tablet inspection data to the server
    private String inspItemsToServer() throws IOError {
        String res = "initiated";
        HashMap<String, String> inspItemMap = new HashMap<String, String>();
        DBHandler dbHandler = new DBHandler(MainActivity.this, null, null, 1);
        ArrayList<HashMap<String, String>> inspItemList = dbHandler.getInspectedItems(USER_ID);
        String tempString = "";
        String testString = "";

        JSONObject jsonItem;
        JSONArray jsonArray = new JSONArray();

        if (inspItemList.size() == 0) {
            //display an empty list
            res = "no_records";
        } else {
            for (int i = 0; i < inspItemList.size(); i++) {
                tempString = "Iteration, " + Integer.toString(i) + "| " + MyConfig.TAG_INSPECTION_ID + ", " + inspItemList.get(i).get(MyConfig.TAG_PROJECT_ID) + " | " + MyConfig.TAG_A_ID + ", " + inspItemList.get(i).get(MyConfig.TAG_A_ID) + " ^-^ ";
                testString = testString + tempString;

                try {
                    jsonItem = new JSONObject();
                    jsonItem.put(MyConfig.TAG_INSPECTION_ID, inspItemList.get(i).get(MyConfig.TAG_INSPECTION_ID));
                    jsonItem.put(MyConfig.TAG_PROJECT_ID, inspItemList.get(i).get(MyConfig.TAG_PROJECT_ID));
                    jsonItem.put(MyConfig.TAG_A_ID, inspItemList.get(i).get(MyConfig.TAG_A_ID));
                    jsonItem.put(MyConfig.TAG_DATE_INSPECTED, inspItemList.get(i).get(MyConfig.TAG_DATE_INSPECTED));
                    jsonItem.put(MyConfig.TAG_OVERVIEW, inspItemList.get(i).get(MyConfig.TAG_OVERVIEW));
                    jsonItem.put(MyConfig.TAG_SERVICED_BY, inspItemList.get(i).get(MyConfig.TAG_SERVICED_BY));
                    jsonItem.put(MyConfig.TAG_RELEVANT_INFO, inspItemList.get(i).get(MyConfig.TAG_RELEVANT_INFO));
                    jsonItem.put(MyConfig.TAG_SERVICE_LEVEL, inspItemList.get(i).get(MyConfig.TAG_SERVICE_LEVEL));
                    jsonItem.put(MyConfig.TAG_REPORT_IMAGE, inspItemList.get(i).get(MyConfig.TAG_REPORT_IMAGE));
                    jsonItem.put(MyConfig.TAG_IMAGE1, inspItemList.get(i).get(MyConfig.TAG_IMAGE1));
                    jsonItem.put(MyConfig.TAG_COM1, inspItemList.get(i).get(MyConfig.TAG_COM1));
                    jsonItem.put(MyConfig.TAG_IMAGE2, inspItemList.get(i).get(MyConfig.TAG_IMAGE2));
                    jsonItem.put(MyConfig.TAG_COM2, inspItemList.get(i).get(MyConfig.TAG_COM2));
                    jsonItem.put(MyConfig.TAG_IMAGE3, inspItemList.get(i).get(MyConfig.TAG_IMAGE3));
                    jsonItem.put(MyConfig.TAG_COM3, inspItemList.get(i).get(MyConfig.TAG_COM3));
                    jsonItem.put(MyConfig.TAG_IMAGE4, inspItemList.get(i).get(MyConfig.TAG_IMAGE4));
                    jsonItem.put(MyConfig.TAG_COM4, inspItemList.get(i).get(MyConfig.TAG_COM4));
                    jsonItem.put(MyConfig.TAG_IMAGE5, inspItemList.get(i).get(MyConfig.TAG_IMAGE5));
                    jsonItem.put(MyConfig.TAG_COM5, inspItemList.get(i).get(MyConfig.TAG_COM5));
                    jsonItem.put(MyConfig.TAG_IMAGE6, inspItemList.get(i).get(MyConfig.TAG_IMAGE6));
                    jsonItem.put(MyConfig.TAG_COM6, inspItemList.get(i).get(MyConfig.TAG_COM6));
                    jsonItem.put(MyConfig.TAG_IMAGE7, inspItemList.get(i).get(MyConfig.TAG_IMAGE7));
                    jsonItem.put(MyConfig.TAG_COM7, inspItemList.get(i).get(MyConfig.TAG_COM7));
                    jsonItem.put(MyConfig.TAG_ITEM_STATUS, inspItemList.get(i).get(MyConfig.TAG_ITEM_STATUS));
                    jsonItem.put(MyConfig.TAG_NOTES, inspItemList.get(i).get(MyConfig.TAG_NOTES));
                    jsonArray.put(jsonItem);

                } catch (Throwable t) {
                    res = "Request failed: " + t.toString();
                    return res;
                }
            }
            RequestHandler_ rh = new RequestHandler_();

            String regex = "'";

            String jsonString = jsonArray.toString().replaceAll(regex,"");

            Log.v("INSPECTION ITEM JSON", jsonString);

            res = rh.sendJsonPostRequest(MyConfig.URL_SYNC_INSPECTION_ITEMS_TO_SERVER, jsonString);
//            res = jsonString;
//            res = testString;
//            TextView propertyPhoto;
//            propertyPhoto = (TextView) findViewById(R.id.editText6);
            //           propertyPhoto.setText(jsonString);

        }
        return res;

    }


    //Upload tablet inspection data to the server
    private String MapToServer() throws IOError {
        String res = "initiated";
        HashMap<String, String> AssetMap = new HashMap<String, String>();
        DBHandler dbHandler = new DBHandler(MainActivity.this, null, null, 1);
        //           dbHandler.puTestData();
        ArrayList<HashMap<String, String>> MapList = dbHandler.getSiteMap();
        String testString = "";
        String tempString = "";

        JSONObject json;
        JSONArray jsonArray = new JSONArray();
        int j = 0;

        if (MapList.size() == 0) {
            //no items toupdatelist
            res = "no_records";
        } else {
            for (int i = 0; i < MapList.size(); i++) {
                tempString = "Iteration, " + Integer.toString(i) + "| " + MyConfig.TAG_A_ID + " ^-^ ";
                testString = testString + tempString;


                try {
                    json = new JSONObject();
                    json.put("Iteration", Integer.toString(j));
                    json.put(MyConfig.TAG_PROJECT_ID, MapList.get(i).get(MyConfig.TAG_PROJECT_ID));
                    json.put(MyConfig.TAG_CAT_ID, MapList.get(i).get(MyConfig.TAG_CAT_ID));
                    json.put(MyConfig.TAG_LEVEL, MapList.get(i).get(MyConfig.TAG_LEVEL));
                    json.put(MyConfig.TAG_PARENT, MapList.get(i).get(MyConfig.TAG_PARENT));
                    json.put(MyConfig.TAG_LABEL, MapList.get(i).get(MyConfig.TAG_LABEL));
                    json.put(MyConfig.TAG_CHILD, MapList.get(i).get(MyConfig.TAG_CHILD));
                    json.put(MyConfig.TAG_A_ID, MapList.get(i).get(MyConfig.TAG_A_ID));
                    json.put(MyConfig.TAG_INSPECTION_ID, MapList.get(i).get(MyConfig.TAG_INSPECTION_ID));
                    json.put(MyConfig.TAG_IMAGE1, MapList.get(i).get(MyConfig.TAG_IMAGE1));
                    json.put(MyConfig.TAG_NOTES, MapList.get(i).get(MyConfig.TAG_NOTES));
                    jsonArray.put(json);
                    j = j + 1;

                } catch (Throwable t) {
                    res = "Request failed: " + t.toString();
                    return res;
                }
            }
            RequestHandler_ rh = new RequestHandler_();
            String jsonString = jsonArray.toString();
            res = rh.sendJsonPostRequest(MyConfig.URL_SYNC_MAP_TO_SERVER, jsonString);
            //           Log.v("MAP JSON", jsonString);
//                res = jsonString;
        }
        return res;

    }

    //Upload tablet inspection data to the server
    private String projToServer() throws IOError {
        String res = "initiated";
        HashMap<String, String> propMap = new HashMap<String, String>();
        DBHandler dbHandler = new DBHandler(MainActivity.this, null, null, 1);
        //           dbHandler.puTestData();
        ArrayList<HashMap<String, String>> propList = dbHandler.getAllProjects(USER_ID);
        String testString = "";
        String tempString = "";

        JSONObject json;
        JSONArray jsonArray = new JSONArray();
        int j = 0;

        if (propList.size() == 0) {
            //no items toupdatelist
            res = "no_records";
        } else {
            for (int i = 0; i < propList.size(); i++) {
                tempString = "Iteration, " + Integer.toString(i) + "| " + MyConfig.TAG_PROJECT_PHOTO + " ^-^ ";
                testString = testString + tempString;


                try {
                    json = new JSONObject();
                    json.put("Iteration", Integer.toString(j));
                    json.put(MyConfig.TAG_PROJECT_ID, propList.get(i).get(MyConfig.TAG_PROJECT_ID));
                    json.put(MyConfig.TAG_PROJECT_PHOTO, propList.get(i).get(MyConfig.TAG_PROJECT_PHOTO));

                    jsonArray.put(json);
                    j = j + 1;

                } catch (Throwable t) {
                    res = "Request failed: " + t.toString();
                    return res;
                }
            }
            RequestHandler_ rh = new RequestHandler_();
            String jsonString = jsonArray.toString();
            res = rh.sendJsonPostRequest(MyConfig.URL_SYNC_PROJECT_TO_SERVER, jsonString);
//                res = jsonString;

        }
        return res;

    }


    //Upload tablet inspection data to the server
    private String actionsToServer() throws IOError {
        String res = "initiated";
        HashMap<String, String> actionItemList = new HashMap<String, String>();
        DBHandler dbHandler = new DBHandler(MainActivity.this, null, null, 1);
        //           dbHandler.puTestData();
        ArrayList<HashMap<String, String>> ActionList = dbHandler.getActions();
        String testString = "";
        String tempString = "";

        JSONObject json;
        JSONArray jsonArray = new JSONArray();
        int j = 0;

        if (ActionList.size() == 0) {
            //no items toupdatelist
            res = "no_records";
        } else {
            for (int i = 0; i < ActionList.size(); i++) {
                tempString = "Iteration, " + Integer.toString(i) + "| " + MyConfig.TAG_PROJECT_ID + " ^-^ ";
                testString = testString + tempString;


                try {
                    json = new JSONObject();
                    json.put("Iteration", Integer.toString(j));
                    json.put(MyConfig.TAG_INSPECTION_ID, ActionList.get(i).get(MyConfig.TAG_INSPECTION_ID));
                    json.put(MyConfig.TAG_PROJECT_ID, ActionList.get(i).get(MyConfig.TAG_PROJECT_ID));
                    json.put(MyConfig.TAG_A_ID, ActionList.get(i).get(MyConfig.TAG_A_ID));
                    json.put(MyConfig.TAG_DATE_INSPECTED, ActionList.get(i).get(MyConfig.TAG_DATE_INSPECTED));
                    json.put(MyConfig.TAG_OVERVIEW, ActionList.get(i).get(MyConfig.TAG_OVERVIEW));
                    json.put(MyConfig.TAG_SERVICED_BY, ActionList.get(i).get(MyConfig.TAG_SERVICED_BY));
                    json.put(MyConfig.TAG_RELEVANT_INFO, ActionList.get(i).get(MyConfig.TAG_RELEVANT_INFO));
                    json.put(MyConfig.TAG_SERVICE_LEVEL, ActionList.get(i).get(MyConfig.TAG_SERVICE_LEVEL));
                    json.put(MyConfig.TAG_REPORT_IMAGE, ActionList.get(i).get(MyConfig.TAG_REPORT_IMAGE));
                    json.put(MyConfig.TAG_IMAGE1, ActionList.get(i).get(MyConfig.TAG_IMAGE1));
                    json.put(MyConfig.TAG_COM1, ActionList.get(i).get(MyConfig.TAG_COM1));
                    json.put(MyConfig.TAG_ITEM_STATUS, ActionList.get(i).get(MyConfig.TAG_ITEM_STATUS));
                    json.put(MyConfig.TAG_NOTES, ActionList.get(i).get(MyConfig.TAG_NOTES));
                    jsonArray.put(json);
                    j = j + 1;

                } catch (Throwable t) {
                    res = "Request failed: " + t.toString();
                    return res;
                }
            }
            RequestHandler_ rh = new RequestHandler_();
            String jsonString = jsonArray.toString();
            res = rh.sendJsonPostRequest(MyConfig.URL_SYNC_ACTIONS_TO_SERVER, jsonString);
//                res = jsonString;
        }
        return res;

    }


    //Upload tablet inspection data to the server
    private String certInspToServer() throws IOError {
        String res = "initiated";
        HashMap<String, String> CertificateInspectionList = new HashMap<String, String>();
        DBHandler dbHandler = new DBHandler(MainActivity.this, null, null, 1);
        //           dbHandler.puTestData();
        ArrayList<HashMap<String, String>> CertInspectionList = dbHandler.getCertInspections();
        String testString = "";
        String tempString = "";

        JSONObject json;
        JSONArray jsonArray = new JSONArray();
        int j = 0;

        if (CertInspectionList.size() == 0) {
            //no items toupdatelist
            res = "no_records";
        } else {
            for (int i = 0; i < CertInspectionList.size(); i++) {
                tempString = "Iteration, " + Integer.toString(i) + "| " + MyConfig.TAG_PROJECT_ID + " ^-^ ";
                testString = testString + tempString;


                try {
                    json = new JSONObject();
                    json.put("Iteration", Integer.toString(j));
                    json.put(MyConfig.TAG_INSPECTION_ID, CertInspectionList.get(i).get(MyConfig.TAG_INSPECTION_ID));
                    json.put(MyConfig.TAG_PROJECT_ID, CertInspectionList.get(i).get(MyConfig.TAG_PROJECT_ID));
                    json.put(MyConfig.TAG_DATE_TIME, CertInspectionList.get(i).get(MyConfig.TAG_DATE_TIME));
                    json.put(MyConfig.TAG_OVERVIEW, CertInspectionList.get(i).get(MyConfig.TAG_OVERVIEW));
                    json.put(MyConfig.TAG_PERMIT, CertInspectionList.get(i).get(MyConfig.TAG_PERMIT));
                    json.put(MyConfig.TAG_PROJECT_ADDRESS, CertInspectionList.get(i).get(MyConfig.TAG_PROJECT_ADDRESS));
                    json.put(MyConfig.TAG_STAGE, CertInspectionList.get(i).get(MyConfig.TAG_STAGE));
                    json.put(MyConfig.TAG_NOTES, CertInspectionList.get(i).get(MyConfig.TAG_NOTES));
                    jsonArray.put(json);
                    j = j + 1;

                } catch (Throwable t) {
                    res = "Request failed: " + t.toString();
                    return res;
                }
            }
            RequestHandler_ rh = new RequestHandler_();
            String jsonString = jsonArray.toString();
            res = rh.sendJsonPostRequest(MyConfig.URL_SYNC_CERT_INSPECTION_TO_SERVER, jsonString);
//                res = jsonString;
            Log.v("CERTIFICATE JSON", jsonString);
        }

        return res;

    }


    //Upload tablet inspection data to the server
    private String compileReport()  {
        String res = "initiated";

        RequestHandler_ rh = new RequestHandler_();
        res = rh.sendRequestParam(MyConfig.URL_EMAIL_REPORT, projectId+"&iId="+ inspectionId+"&USERID="+USER_ID);


        return res;

    }



    //Syncing to the server
    private void syncToServer() {

        class syncInspections extends AsyncTask<Void, Void, String> {

            private ProgressDialog loading;
            private String res = "Before try";


            DBHandler dbHandler = new DBHandler(MainActivity.this, null, null, 1);
            //dbHandler.getInspections();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Syncing...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {

                // Call functions to get data from local database and post to the server
                String inspSaved;
                String itemSaved;
                String MapSaved;
                String projSaved;
                String actionsSaved;
                String CertInspSaved;


       //         String yes = "y";
                String message = "initiated";


                inspSaved = inspToServer();
                itemSaved = inspItemsToServer();
                MapSaved = MapToServer();
                projSaved = projToServer();
                actionsSaved = actionsToServer();
                CertInspSaved = certInspToServer();


                //    message = inspToServer();



                    if ((inspSaved.matches("y|no_records") && (itemSaved.matches("y|no_records")) && (MapSaved.matches("y|no_records")
                            && projSaved.matches("y|no_records"))&&
                            (actionsSaved.matches("y|no_records") && (CertInspSaved.matches("y|no_records") )))){

                        dbHandler.statusUploaded(USER_ID);

                        message = "Inspections uploaded successfully";

                        //Disable the cleartablet function for August 2019 inspections.
                        //   clearTablet();


                    }
                    else {

                        if(!inspSaved.equals("y")) message = "Activity process ";
                        if(!itemSaved.equals("y")) message = message + "  Activity Item process ";
                        if(!MapSaved.equals("y")) message = message + "  Map process ";
                        if(!projSaved.equals("y")) message = message + "  Project process ";
                        if(!actionsSaved.equals("y")) message = message + "  Action process ";
                        if(!CertInspSaved.equals("y")) message = message + "  Certificate process ";

                    }


                return message;
            }
        }

        syncInspections ue = new syncInspections();
        ue.execute();

    }



    private void reportMailer() {

        class Send_Report extends AsyncTask<Void, Void, String> {
            private ProgressDialog loading;
            private String res = "Before try";

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Compiling and sending report...", "Processing.... this may take several minutes", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(Void... params) {

                RequestHandler_ rh = new RequestHandler_();
                String message = rh.sendRequestParam(MyConfig.URL_EMAIL_REPORT, projectId+"&iId="+ inspectionId+"&USERID="+USER_ID);
                return message;
            }

        }
        Send_Report rep = new Send_Report();
        rep.execute();

    }


// Remove?
    //    private HashMap<String, String> getInspectionRecords() {
//        String JSONString = "";

//        editTextMessage.setText(JSONString);

//        return JSONString;
//    }

    public void s3credentialsProvider() {

        // Initialize the AWS Credential
        CognitoCachingCredentialsProvider cognitoCachingCredentialsProvider =
                new CognitoCachingCredentialsProvider(
                        getApplicationContext(),
                        "ap-southeast-2:857c63b9-7d71-4dc0-bb3a-1c805d5afe4f", // Identity pool ID
                        AP_SOUTHEAST_2 // Region

                );
        createAmazonS3Client(cognitoCachingCredentialsProvider);


    }

    /**
     * Create a AmazonS3Client constructor and pass the credentialsProvider.
     *
     * @param credentialsProvider
     */
    public void createAmazonS3Client(CognitoCachingCredentialsProvider
                                             credentialsProvider) {

        // Create an S3 client

        s3Client = new AmazonS3Client(credentialsProvider);

        //  s3Client = new AmazonS3Client(credentialsProvider, Region.getRegion(Regions.AP_SOUTHEAST_2));

        // Set the region of your S3 bucket
        s3Client.setRegion(Region.getRegion(AP_SOUTHEAST_2));





    }

    public void setTransferUtility() {

        transferUtility = new TransferUtility(s3Client, getApplicationContext());


    }

    /**
     * This method is used to upload the file to S3 by using TransferUtility class
     *
     * @param
     */
    public void upLoadPhotos(View view) {


        // AWS transfer service - transferutility requires this for restarting if connection is lost during transfer
        //    getApplicationContext().startService(new Intent(getApplicationContext(), TransferService.class));

        //Get the property information for all the properties to inspect
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        ArrayList<HashMap<String, String>> list = dbHandler.getAllProjects(USER_ID);
        //Get a list of all the images for the properties to inspect
        ArrayList<HashMap<String, String>> photolist = dbHandler.getInspectedItemPhotos(USER_ID);
        ArrayList<HashMap<String, String>> inspectphotolist = dbHandler.getInspectionPhotos(USER_ID);
        ArrayList<HashMap<String, String>> actionlist = dbHandler.getActionPhotos(USER_ID);


        String photo_name;
        root = Environment.getExternalStorageDirectory().toString();
        int i = 0;
        while (i < list.size()) {
            photo_name = list.get(i).get(MyConfig.TAG_PROJECT_PHOTO);
            if(!photo_name.equals("")) uploadFileToS3(view, photo_name);
            i++;
        }

        i = 0;

        while (i  < photolist.size() ) {   //photolist.size()

            photo_name = photolist.get(i).get(MyConfig.TAG_IMAGE1);
            if(!photo_name.equals("")) uploadFileToS3(view, photo_name);

            photo_name = photolist.get(i).get(MyConfig.TAG_IMAGE2);
            if(!photo_name.equals("")) uploadFileToS3(view, photo_name);

            photo_name = photolist.get(i).get(MyConfig.TAG_IMAGE3);
            if(!photo_name.equals("")) uploadFileToS3(view, photo_name);
            photo_name = photolist.get(i).get(MyConfig.TAG_IMAGE4);
            if(!photo_name.equals("")) uploadFileToS3(view, photo_name);

            photo_name = photolist.get(i).get(MyConfig.TAG_IMAGE5);
            if(!photo_name.equals("")) uploadFileToS3(view, photo_name);

            i++;
        }

        i = 0;

        while (i  < inspectphotolist.size() ) {   //photolist.size()

            photo_name = inspectphotolist.get(i).get(MyConfig.TAG_IMAGE);
            if(!photo_name.equals("")) uploadFileToS3(view, photo_name);

            i++;
        }


    }


    public void uploadFileToS3(final View view, final String photo_name) {



        if (photo_name.length() > 14) {
            dirName = photo_name.substring(6, 14);
            File Folder = new File(root + "/ESM_" + dirName + "/");
            if (Folder.exists()) {
                final  File F = new File(root + "/ESM_" + dirName + "/" + photo_name);
                if (F.exists()) {



                    class upload_photo extends AsyncTask<Void, Void, String> {
                        private ProgressDialog loading;
                        private String res = "Before try";


                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();

                    //        ProgressDialog loading = new ProgressDialog(getBaseContext());
                    //       loading.show(MainActivity.this, "uploading images...", "Processing.... this may take several minutes", false, false);
                            progressBar1.setVisibility(view.VISIBLE);
                        }

                        @Override
                        protected void onPostExecute(final String s) {
                            super.onPostExecute(s);
                            progressBar1.setVisibility(view.INVISIBLE);
                            Thread thread = new Thread(new Runnable() {

                                @Override
                                public void run() {
                                    try {
                                        boolean exists = s3Client.doesObjectExist(CLIENT, s);

                                        if (exists) {

                                            s3Client.setObjectAcl(CLIENT, s, CannedAccessControlList.PublicRead);

                                            //     Toast.makeText(MainActivity.this, "Image "+photo_name+ " exists",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    catch (Exception e) {

                                        Log.e("HAND SHAKE AWS", "TIME OUT",e );

                                    }

                                }
                            });
                            thread.start();
                            //            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        protected String doInBackground(Void... params) {

                            try {
                                //      progressBar1.setVisibility(view.VISIBLE);
                                boolean exists = s3Client.doesObjectExist(CLIENT, "images/"+photo_name);
                                if(exists){

                                    //    s3Client.setObjectAcl("iapp-apted","images/"+photo_name,CannedAccessControlList.PublicRead);

                                    //     Toast.makeText(MainActivity.this, "Image "+photo_name+ " exists",Toast.LENGTH_LONG).show();
                                }
                                else {


                                    TransferUtility transferUtility = TransferUtility.builder().context(getApplicationContext()).s3Client(s3Client).build();

                                    TransferObserver transferObserver = transferUtility.upload(
                                            CLIENT,    // The bucket to upload to
                                            "images/"+photo_name,    // The key for the uploaded object
                                            F       // The file where the data to upload exists
                                    );


                                    transferObserverListener(transferObserver);

                                }

                            } catch (Exception e) {

                                Log.e("PUBLIC READ", "PUBLIC READ ERROR ",e );
                            }

                            return "images/"+photo_name;
                        }

                    }


                    upload_photo up_photo = new upload_photo();
                    up_photo.execute();



                }//if file exists

            } //if folder exists

        }//if name is valid

    }






    /**
     * This method is used to Download the file to S3 by using transferUtility class
     *
     * @param view
     **/
    public void downloadFileFromS3(View view, final String  img_name) {



        if (img_name.length() > 14) {
            dirName = img_name.substring(6, 14);
            String info = img_name.substring(5, 9);
            File storageDirectory;
            final File F;

            root = Environment.getExternalStorageDirectory().toString();

            if (info.equals("INFO")){
                storageDirectory = new File(root + "/14080INFO/");}
            else {
                storageDirectory = new File(root + "/ESM_" + dirName + "/");}

            if (!storageDirectory.exists()) {
                storageDirectory.mkdirs();
            }

            if (info.equals("INFO")) {
                F = new File(root + "/" + img_name);
            }   else {
                F = new File(root + "/ESM_" + dirName + "/" + img_name);
            }

            if (F.exists() != true) {
                bucket = CLIENT;

                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {

                        TransferObserver transferObserver = transferUtility.download(
                                bucket,     /* The bucket to download from */
                                "images/"+img_name,    /* The key for the object to download */
                                F      /* The file to download the object to */

                        );

                        transferObserverListener(transferObserver);


                    }
                });
                thread.start();







            }
        }

    }

    public List<String> getObjectslistFromFolder(final String bucketName, final String folderKey) {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {



                    //  Looper.prepare();
                    ListObjectsRequest listObjectsRequest =
                            new ListObjectsRequest()
                                    .withBucketName(bucketName)
                                    .withPrefix(folderKey+"/");



                    ArrayList<String> transferRecord = new ArrayList<>();

                    ObjectListing objects = s3Client.listObjects(listObjectsRequest);
                    for (; ; ) {
                        List<S3ObjectSummary> summaries = objects.getObjectSummaries();
                        if (summaries.size() < 1) {
                            break;
                        }
                        for (int i = 0; i < summaries.size(); i++) {
                            ArrayList<String> file = new ArrayList<>();
                            file.add(summaries.get(i).getKey());
                            transferRecord.add(summaries.get(i).getKey());

                            downloadFileFromS3(view,transferRecord.get(i) );

                        }
                        objects = s3Client.listNextBatchOfObjects(objects);
                    }



                    //  Looper.loop();


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("tag", "Exception found while listing " + e);


                }
            }
        });

        thread.start();




        return null;
    }



    public void fetchFileFromS3(View view){

        // Get List of files from S3 Bucket
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {

                try {
                    Looper.prepare();
                    listing = getObjectNamesForBucket(bucket, s3Client);

                    for (int i=0; i< listing.size(); i++){
                        Toast.makeText(MainActivity.this, listing.get(i),Toast.LENGTH_LONG).show();
                    }
                    Looper.loop();
                    // Log.e("tag", "listing "+ listing);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Log.e("tag", "Exception found while listing "+ e);
                }

            }
        });
        thread.start();
    }

    /**
     * @desc This method is used to return list of files name from S3 Bucket
     * @param bucket
     * @param s3Client
     * @return object with list of files
     */
    private List<String> getObjectNamesForBucket(String bucket, AmazonS3 s3Client) {
        ObjectListing objects=s3Client.listObjects(bucket);
        List<String> objectNames=new ArrayList<String>(objects.getObjectSummaries().size());
        Iterator<S3ObjectSummary> iterator=objects.getObjectSummaries().iterator();
        while (iterator.hasNext()) {
            objectNames.add(iterator.next().getKey());
        }
        while (objects.isTruncated()) {
            objects=s3Client.listNextBatchOfObjects(objects);
            iterator=objects.getObjectSummaries().iterator();
            while (iterator.hasNext()) {
                objectNames.add(iterator.next().getKey());
            }
        }
        return objectNames;
    }

    /**
     * This is listener method of the TransferObserver
     * Within this listener method, we get status of uploading and downloading file,
     * to display percentage of the part of file to be uploaded or downloaded to S3
     * It displays an error, when there is a problem in  uploading or downloading file to or from S3.
     * @param transferObserver
     */

    public void transferObserverListener(TransferObserver transferObserver){


        // registerReceiver(TransferNetworkLossHandler.getInstance(getApplicationContext()), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        TransferNetworkLossHandler.getInstance(getApplicationContext()).onReceive(this, new Intent().setAction(ConnectivityManager.CONNECTIVITY_ACTION));


        transferObserver.setTransferListener(new TransferListener(){

            @Override
            public void onStateChanged(int id, TransferState state) {   //TransferState
                Toast.makeText(getApplicationContext(), "Cloud file transfer in progress"
                        , Toast.LENGTH_SHORT).show();
                if(state == TransferState.WAITING || state == TransferState.FAILED || state == TransferState.WAITING_FOR_NETWORK){
                    // HERE end service and notice user !!!
                    Toast.makeText(getApplicationContext(), "Connection Pending"
                            , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                int percentage = (int) percentDonef;
                Toast.makeText(getApplicationContext(), "Progress in %"
                        + percentage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int id, Exception ex) {
                Log.e("error","File not in Bucket ");
                Toast.makeText(getApplicationContext(), "File not found"
                        , Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void downloadprojects(){

        //update the SQLite db with data from MySQL db if there is and internet connection
        ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cManager.getActiveNetworkInfo();
        if (nInfo != null && nInfo.isConnected()) {
            getJSON();
            getAdditionalJSON();
            getProjectsJSON();
            getActionJSON();
            getCertInspJSON();
            //       getCategoryJSON();
            get_AOR_JSON();
            //   get_BOR_JSON();
            //    get_OR_JSON("TABLE_B_OR");
        } else {
            buttonDownload.setEnabled(false);
            Toast.makeText(this, "No internet available", Toast.LENGTH_LONG).show();
        }
    }

    public void downloadphotos(){

        //Get the property information for all the properties to inspect
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        ArrayList<HashMap<String, String>> list = dbHandler.getAllProjects(USER_ID);
        //Get a list of all the images for the properties to inspect
        ArrayList<HashMap<String, String>> ilist = dbHandler.getInspectionPhotos(USER_ID);
        ArrayList<HashMap<String, String>> photolist = dbHandler.getInspectedItemPhotos(USER_ID);

        int i = 0;
        while (i < list.size()) {

            downloadFileFromS3(view, list.get(i).get(MyConfig.TAG_PROJECT_PHOTO));
            String s = list.get(i).get(MyConfig.TAG_PROJECT_PHOTO);
            i++;
        }

        //for each propertyID call method to search the propertyIDINFO create the directory and download the information

        i = 0;
        while (i < ilist.size()) {
            downloadFileFromS3(view, ilist.get(i).get(MyConfig.TAG_IMAGE));
            i++;
        }


        i = 0;
        while (i < photolist.size() ) {  //
            //try {
            if (photolist.get(i).get(MyConfig.TAG_IMAGE1) != null || photolist.get(i).get(MyConfig.TAG_IMAGE1) != "")
                downloadFileFromS3(view, photolist.get(i).get(MyConfig.TAG_IMAGE1));
            if (photolist.get(i).get(MyConfig.TAG_IMAGE2) != null || photolist.get(i).get(MyConfig.TAG_IMAGE2) != "")
                downloadFileFromS3(view, photolist.get(i).get(MyConfig.TAG_IMAGE2));
            if (photolist.get(i).get(MyConfig.TAG_IMAGE3) != null || photolist.get(i).get(MyConfig.TAG_IMAGE3) != "")
                downloadFileFromS3(view, photolist.get(i).get(MyConfig.TAG_IMAGE3));
            if (photolist.get(i).get(MyConfig.TAG_IMAGE4) != null || photolist.get(i).get(MyConfig.TAG_IMAGE4) != "")
                downloadFileFromS3(view, photolist.get(i).get(MyConfig.TAG_IMAGE4));
            if (photolist.get(i).get(MyConfig.TAG_IMAGE5) != null || photolist.get(i).get(MyConfig.TAG_IMAGE5) != "")
                downloadFileFromS3(view, photolist.get(i).get(MyConfig.TAG_IMAGE5));


            i++;
            //  } catch (Exception e) {
            //      e.printStackTrace();
            //      Log.e("tag", "Exception found while listing "+ e);
            //  }


        }
    }

    public void uploaddata(){

        //update the MySQL db on the server with he inspection data, if there is and internet connection
        ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cManager.getActiveNetworkInfo();
        if (nInfo != null && nInfo.isConnected()) {
            //editTextMessage.setText(selectedItem ); // possibly intended to use this tocheckwhat was uploaded....
            syncToServer();



        } else {
//                buttonUpdatePropInfo.setEnabled(false);
            Toast.makeText(this, "No internet available", Toast.LENGTH_LONG).show();
        }
    }

    public void uploadphotos(){

        ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cManager.getActiveNetworkInfo();
        if (nInfo != null && nInfo.isConnected()) {
            //    editTextMessage.setText("Sync Inspected Only");
            //Upload photos to Bucket

            // AWS transfer service - transferutility requires this for restarting if connection is lost during transfer
            //  getApplicationContext().startService(new Intent(getApplicationContext(), TransferService.class));
            upLoadPhotos(null);

        } else {
            buttonDownload.setEnabled(false);
            Toast.makeText(this, "No internet available", Toast.LENGTH_LONG).show();
        }
    }

}
