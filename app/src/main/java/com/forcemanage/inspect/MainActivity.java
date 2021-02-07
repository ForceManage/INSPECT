package com.forcemanage.inspect;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
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
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.forcemanage.inspect.adapters.MapListAdapter;
import com.forcemanage.inspect.attributes.A_Attributes;
import com.forcemanage.inspect.attributes.ActionItemAttributes;
import com.forcemanage.inspect.attributes.CertificateInspectionAttributes;
import com.forcemanage.inspect.attributes.InspectionAttributes;
import com.forcemanage.inspect.attributes.InspectionItemAttributes;
import com.forcemanage.inspect.attributes.LOG_Attributes;
import com.forcemanage.inspect.attributes.MAPattributes;
import com.forcemanage.inspect.attributes.MapViewData;
import com.forcemanage.inspect.attributes.MapViewNode;
import com.forcemanage.inspect.attributes.ProjectAttributes;
import com.forcemanage.inspect.attributes.ProjectData;
import com.forcemanage.inspect.attributes.ProjectNode;
import com.forcemanage.inspect.attributes.ReportItem;
import com.forcemanage.inspect.attributes.SummaryAttributes;
import com.forcemanage.inspect.attributes.USER_Attributes;
import com.forcemanage.inspect.fragments.BaseFragment;
import com.forcemanage.inspect.fragments.BaseInfoFolderFragment;
import com.forcemanage.inspect.fragments.BaseInfoFragment;
import com.forcemanage.inspect.fragments.InspectInfoFragment;
import com.forcemanage.inspect.fragments.InspectionInfoFolderFragment;
import com.forcemanage.inspect.fragments.ProjectInfoFolderFragment;
import com.forcemanage.inspect.fragments.ProjectInfoFragment;
import com.forcemanage.inspect.fragments.RegisterFragment;
import com.forcemanage.inspect.fragments.ReportFragment;
import com.forcemanage.inspect.InspectionActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


import static com.amazonaws.regions.Regions.AP_SOUTHEAST_2;
import static com.forcemanage.inspect.InspectionActivity.copy;
import static java.lang.Integer.parseInt;


public class MainActivity extends AppCompatActivity implements OnVerseNameSelectionChangeListener, tabchangelistener, View.OnClickListener {

    public static final int REQUEST_CODE = 20;

    private EditText TextMessage;
    private String JSON_STRING;
    private String JSON_STRING_ADDITIONAL;
    private String JSON_STRING_ACTION;
    private String JSON_STRING_CERTINSP;
    private String JSON_STRING_SUMMARY;
    private String JSON_STRING_PROJECT_LIST;
    private String JSON_STRING_NEW_PROJECT;
    private String JSON_STRING_NEW_ACTIVITY;
    private String JSON_STRING_OR;
    private String JSON_STRING_USER;
    private String JSON_STRING_LOG;
    private int USER_ID = 0;
    private String USER_NAME = "";
    private String PASS_WORD = "";
    private String USER_CODE = "";
    private String CLIENT = "no-client";
    private ListView listView;
    private CheckBox checkBox;
    private Switch ToggleTB;
    public ImageView mPhotoImageView;
    private ImageView Folders_img;
    private ImageView settings;
    private ImageView listFolders;
    private ImageView info_icon;
    private ImageView imgCloud;
    private ImageView imgLogin;
    public File photo;
    private String dirName;
    public String inspectionId;
    public String projectId = "null";
    private int projId = 0;
    private int iId;
    private String fname;
    private String cat;
    public String photoBranch;
    private List<MapViewData> maplistItems;
    private List<ProjectData> projectlistItems;
    private String mImageFileLocation;
    private static final int REQUEST_OPEN_RESULT_CODE = 0, REQUEST_GET_SINGLE_FILE = 1;
    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private static final int ACTIVITY_GET_FILE = 1;
    private static final int PICK_CONTACT = 3;
    private static final int PDF_SELECTION_CODE = 4;
    private RecyclerView recyclerView;
    //  private List<Joblistdata> jobList;
    private String FragDisplay;
    private Fragment fragment_obj;
    public ArrayList reportlistItems;
    public String propPhoto;
    private ProgressBar progressBar1;
    private Boolean connected;
    private TextView client;
    public String focus = "null";
    private FloatingActionButton fab_add;



    AmazonS3 s3Client;
    String bucket = "iappfiles";
    String root;
    TransferUtility transferUtility;
    List<String> listing;
    String[] map_menu;
    String file_name;
    EditText prop_name;

    private View view;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  ESMdb = new DBHandler(this, null, null, 1);


        client = (TextView) findViewById(R.id.ProjectList);
        client.setOnClickListener(this);
        DBHandler dbHandler = new DBHandler(this, null, null, 1);

        init();

        ArrayList<HashMap<String, String>> Folders = dbHandler.getFolders(USER_ID, projId);
        maplistItems = new ArrayList<>();

     //   projectlistItems = new ArrayList<>();
        MapViewData listItem;

        for (int i = 0; i < (Folders.size()); i++) {

            listItem = new MapViewData(

                    Integer.parseInt(Folders.get(i).get(MyConfig.TAG_PROJECT_ID)),
                    Integer.parseInt(Folders.get(i).get(MyConfig.TAG_LEVEL)),
                    Integer.parseInt(Folders.get(i).get(MyConfig.TAG_CAT_ID)),
                    Integer.parseInt(Folders.get(i).get(MyConfig.TAG_CHILD)),
                    Folders.get(i).get(MyConfig.TAG_LABEL),
                    Integer.parseInt(Folders.get(i).get(MyConfig.TAG_A_ID)),
                    Integer.parseInt(Folders.get(i).get(MyConfig.TAG_INSPECTION_ID)),
                    Integer.parseInt(Folders.get(i).get(MyConfig.TAG_PARENT)),
                    Folders.get(i).get(MyConfig.TAG_IMAGE1),
                    Folders.get(i).get(MyConfig.TAG_NOTES)

            );
            maplistItems.add(listItem);
        }

        GlobalVariables.dataList = (ArrayList<MapViewData>) maplistItems;
        //     TreeViewLists.LoadDisplayList();


        if (findViewById(R.id.fragment_container) != null) {

            // However if we are being restored from a previous state, then we don't
            // need to do anything and should return or we could end up with overlapping Fragments
            if (savedInstanceState != null) {
                return;
            }

            // Create an Instance of Fragment
            MapViewFragment treeFragment = new MapViewFragment();
            treeFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, treeFragment)
                    .commit();

        }


      //  buttonDownload = (Button) findViewById(R.id.btnDownloadJobs);
     //   buttonDownload.setOnClickListener(this);
        //     buttonDownloadPhotos = (Button) findViewById(R.id.btnDownloadPhotos);
        //     buttonDownloadPhotos.setOnClickListener(this);

      //  buttonUpload = (Button) findViewById(R.id.btnUpload);
     //   buttonUpload.setOnClickListener(this);
        //     buttonSyncPhotos = (Button) findViewById(R.id.btnSyncPhotos);
        //     buttonSyncPhotos.setOnClickListener(this);
        //     buttonInspection = (Button) findViewById(R.id.btnInspection);
        //     buttonInspection.setOnClickListener(this);

      //  btnLogin = (Button) findViewById(R.id.btnlogin);
      //  btnLogin.setOnClickListener(this);
        settings = (ImageView) findViewById(R.id.settings);
        settings.setOnClickListener(this);
        listFolders = (ImageView) findViewById(R.id.list_Folders);
        listFolders.setOnClickListener(this);
        Folders_img = (ImageView) findViewById(R.id.imageView_projectfolder);
        Folders_img.setOnClickListener(this);
        imgCloud = (ImageView) findViewById(R.id.image_cloud);
        imgCloud.setOnClickListener(this);
        imgLogin = (ImageView) findViewById(R.id.image_login);
        imgLogin.setOnClickListener(this);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        fab_add = (FloatingActionButton) findViewById(R.id.fab_add);
        fab_add.setOnClickListener(this);


        ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cManager.getActiveNetworkInfo();
        if (nInfo != null && nInfo.isConnected()) {
            s3credentialsProvider();
            setTransferUtility();
            connected = true;
            //getJSON();
        } else {

            imgCloud.setEnabled(false);
            Toast.makeText(this, "No internet available", Toast.LENGTH_LONG).show();
            connected = false;
        }

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_CONTACTS,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                android.Manifest.permission.CAMERA
        };

        if (!hasPermissions(getBaseContext(), PERMISSIONS)) {
            ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, PERMISSION_ALL);
        }


        dirName = new SimpleDateFormat("yyyyMMdd").format(new Date());
        Integer date = parseInt(dirName) - 7;

        if (USER_ID == 0) {

            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
            View promptView = layoutInflater.inflate(R.layout.call_log, null);
            AlertDialog.Builder passDialog = new AlertDialog.Builder(MainActivity.this);
            final EditText passText = (EditText) promptView.findViewById(R.id.code);
            passDialog.setView(promptView);
            passDialog.setTitle("User Code");
            passDialog.setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    DBHandler dbHandler = new DBHandler(getApplicationContext(), null, null, 1);

                    if (Pattern.matches("\\d{4}", passText.getText().toString())) {
                        USER_ID = dbHandler.checkCode(passText.getText().toString());
                        GlobalVariables.User_id = USER_ID;
                        if (USER_ID > 0) {
                            CLIENT = dbHandler.getClient(USER_ID);
                            updatePropList();
                            if (CLIENT.equals("no-client")) {
                                CLIENT = CLIENT + "-" + USER_ID;
                            }
                            if(connected) {
                                Thread thread = new Thread(new Runnable() {

                                    @Override
                                    public void run() {
                                        boolean exists = s3Client.doesBucketExist(CLIENT);
                                        if (!exists)
                                            s3Client.createBucket(CLIENT);

                                    }

                                });
                                thread.start();

                            }

                        } else
                            Toast.makeText(MainActivity.this, "Incorrect PIN or User Login required", Toast.LENGTH_LONG).show();

                    } else
                        Toast.makeText(MainActivity.this, "Invalid Input", Toast.LENGTH_LONG).show();
                }
            });

            // create an alert dialog
            AlertDialog alert = passDialog.create();
            alert.show();
        }

    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    private void init() {

        ProjectInfoFragment fragment = new ProjectInfoFragment();
        doFragmentTransaction(fragment, "ProjectInfoFragment", false, "");

    }

    //returns the date/datetime depending on type required in YYYYMMDD format
    private String dayTime(int Type) {

        String daytime = "20000101";

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

    private String datetoString(String date) {
        String datestring = "";

        String[] idate = date.split("-");
        datestring = idate[0] + idate[1] + idate[2];

        return datestring;
    }


    private void doFragmentTransaction(Fragment fragment, String name, boolean addToBackStack, String message) {
        androidx.fragment.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FragDisplay = name;
        if(FragDisplay == "RegisterFragment"){



            LinearLayout main1 = (LinearLayout) findViewById(R.id.mainfragment_container);
            main1.setVisibility(View.VISIBLE);

            transaction.replace(R.id.mainfragment_container, fragment, name);
        }
        else{
            LinearLayout main1 = (LinearLayout) findViewById(R.id.mainfragment_container);
            main1.setVisibility(View.GONE);
            transaction.replace(R.id.mainfragment_container_1, fragment, name);
        }
        if (addToBackStack) {
            transaction.addToBackStack(name);
        }
        transaction.commit();
    }

    public String stringdate(String date, int type){

        switch (type) {

            case 1: {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                Date d = null;
                try {
                    d = sdf.parse(date);
                } catch (ParseException ex) {
                    Log.v("Exception", ex.getLocalizedMessage());
                }
                sdf.applyPattern("dd MMM yyyy");
                date = sdf.format(d);
                break;
            }
            case 2: {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d = null;
                try {
                    d = sdf.parse(date);
                } catch (ParseException ex) {
                    Log.v("Exception", ex.getLocalizedMessage());
                }
                sdf.applyPattern("dd MMM yyyy, HH:mm");
                date = sdf.format(d);
                break;

            }

            case 3: {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d = null;
                try {
                    d = sdf.parse(date);
                } catch (ParseException ex) {
                    Log.v("Exception", ex.getLocalizedMessage());
                }
                sdf.applyPattern("HH:mm");
                date = sdf.format(d);
                break;

            }

        }

        return date;
    }


    private void doFragmentFolderInfoTransaction(Fragment fragment, String name, boolean addToBackStack, String message) {
        androidx.fragment.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FragDisplay = name;
        LinearLayout main1 = (LinearLayout) findViewById(R.id.mainfragment_container);
        main1.setVisibility(View.GONE);

        transaction.replace(R.id.mainfragment_container_2, fragment, name);
        if (addToBackStack) {
            transaction.addToBackStack(name);
        }
        transaction.commit();
    }


    @Override
    public void OnTabChanged(int treeNameIndex){

   //     GlobalVariables.modified = false;
        focus = "FOLDER";
      //  MapViewNode node = GlobalVariables.dataList.get(GlobalVariables.pos);
        MapViewNode node =  GlobalVariables.displayNodes.get(GlobalVariables.pos);
      //  ProjectNode node = GlobalVariables.projectdisplayNodes.get(GlobalVariables.pos);
        GlobalVariables.folder_Id = node.getprojId();
        GlobalVariables.name = node.getNodeName();
        GlobalVariables.aId = node.getaID();
        DBHandler dbHandler = new DBHandler(this, null, null, 1);


           projectId = Integer.toString(node.getprojId()); //This is setup in MainActivity as BranchCat to work with MapList
          // inspectionId = Integer.toString(node.getiID());
           projId = node.getprojId();

        //   iId = node.getiID();


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
            androidx.fragment.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the backStack so the User can navigate back
            fragmentTransaction.replace(R.id.fragment_container, newDetailFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }





        if(node.getNodeLevel() == 0) {


               HashMap<String, String> projectItem = dbHandler.getProjectInfo(projectId);
                //              mPhotoImageView = (ImageView) findViewById(R.id.imageView6);
              //  propPhoto = projectItem.get(MyConfig.TAG_PROJECT_PHOTO);


                Bundle bundle = new Bundle();
                bundle.putInt("projId", projId);
                bundle.putInt("USER_ID", USER_ID);
                bundle.putInt("Level", 0);
                bundle.putString("Folder_ID", projectItem.get(MyConfig.TAG_ADDRESS_NO));
                bundle.putString("Folder", projectItem.get(MyConfig.TAG_PROJECT_ADDRESS));
                bundle.putString("foldernote", projectItem.get(MyConfig.TAG_PROJECT_NOTE));
                bundle.putString("infoA", projectItem.get(MyConfig.TAG_INFO_A));
                bundle.putString("infoB", projectItem.get(MyConfig.TAG_INFO_B));
                bundle.putString("infoC", projectItem.get(MyConfig.TAG_INFO_C));
                bundle.putString("infoD", projectItem.get(MyConfig.TAG_INFO_D));
                bundle.putString("infoE", projectItem.get(MyConfig.TAG_INFO_E));
                bundle.putString("infoF", projectItem.get(MyConfig.TAG_INFO_F));
                bundle.putString("infoG", projectItem.get(MyConfig.TAG_INFO_G));
                bundle.putString("infoH", projectItem.get(MyConfig.TAG_INFO_H));
                bundle.putString("folderPhoto", projectItem.get(MyConfig.TAG_PROJECT_PHOTO));



                ProjectInfoFragment fragment = new ProjectInfoFragment();
                doFragmentTransaction(fragment, "ProjectInfoFragment", false, "");
                fragment.setArguments(bundle);

                ProjectInfoFolderFragment fragment2 = new ProjectInfoFolderFragment();
                doFragmentFolderInfoTransaction(fragment2, "ProjectInfoFolderFragment", false, "");
                fragment2.setArguments(bundle);



            }

            else {

                Integer aID = node.getaID();
                Integer iID = node.getiID();

              //  if(aID == 0)  aID = 1;
              //  if(iID == 0)  iID = 1;

                inspectionId = Integer.toString(iID);

                    HashMap<String, String> mapItem = dbHandler.getMapItem(projId, aID, iID);

                String MapBranch;

                Integer catId = Integer.parseInt(mapItem.get(MyConfig.TAG_CAT_ID));
                Integer Level = Integer.parseInt(mapItem.get(MyConfig.TAG_LEVEL));
                Integer Parent = Integer.parseInt(mapItem.get(MyConfig.TAG_PARENT));
                photoBranch = mapItem.get(MyConfig.TAG_IMAGE1);
                String inspLabel = mapItem.get(MyConfig.TAG_LABEL); //This is the inspection label column
                String branchLabel = mapItem.get("MAP_LABEL"); //This is the Map label column
                String branchNote = mapItem.get(MyConfig.TAG_NOTES);
                Integer branchCode = Integer.parseInt(mapItem.get(MyConfig.TAG_CHILD));

                String branchHead = dbHandler.getMapBranchTitle(projId, catId);

                Bundle bundle = new Bundle();
                bundle.putInt("projId", projId);
                bundle.putInt("USER_ID", USER_ID);
                bundle.putInt("Level", node.getNodeLevel());
                bundle.putInt("catId", node.getcatId());
                bundle.putString("branchHead", branchHead);
                bundle.putString("inspection", inspLabel);
                bundle.putString("projectID", projectId);
                bundle.putString("inspectionID", inspectionId);
                bundle.putString("image", photoBranch);
                bundle.putString("MAP_LABEL", branchLabel);
                bundle.putInt("aID", node.getaID());
                bundle.putString("notes", branchNote);
              //  bundle.putString("com2", com2);

                ProjectInfoFragment fragment = new ProjectInfoFragment();
                doFragmentTransaction(fragment, "ProjectInfoFragment", false, "");
                fragment.setArguments(bundle);


                BaseInfoFolderFragment fragment2 = new BaseInfoFolderFragment();
                doFragmentFolderInfoTransaction(fragment2, "BaseInfoFolderFragment", false, "");
                fragment2.setArguments(bundle);


            }


    }

    @Override
    public void OnProjectChanged(int treeNameIndex){

        ProjectNode node = GlobalVariables.projectdisplayNodes.get(GlobalVariables.doc_pos);

            GlobalVariables.aId = node.getaID();
            GlobalVariables.Level = node.getNodeLevel();
            GlobalVariables.iId = node.getiID();
       //     GlobalVariables.catId = node.getcatId();
            GlobalVariables.name = node.getNodeName();
     //   GlobalVariables.modified = true;

        Bundle bundle = new Bundle();
        switch (node.getNodeLevel()) {


            case 0: {

                focus = "FOLDER";

                fragment_obj = getSupportFragmentManager().findFragmentByTag("ProjectInfoFragment");

                LinearLayout linearLayout = fragment_obj.getView().findViewById(R.id.document_info);
                linearLayout.setVisibility(View.GONE);
                LinearLayout linearLayout_ = fragment_obj.getView().findViewById(R.id.folder_info);
                linearLayout_.setVisibility(View.VISIBLE);


               DBHandler dbHandler = new DBHandler(this, null, null, 1);

                HashMap<String, String> projectItem = dbHandler.getProjectInfo(projectId);
                //              mPhotoImageView = (ImageView) findViewById(R.id.imageView6);
                //  propPhoto = projectItem.get(MyConfig.TAG_PROJECT_PHOTO);



                bundle.putInt("projId", projId);
                bundle.putString("branchHead", projectItem.get(MyConfig.TAG_ADDRESS_NO));
                bundle.putString("address", projectItem.get(MyConfig.TAG_PROJECT_ADDRESS));
                bundle.putString("foldernote", projectItem.get(MyConfig.TAG_PROJECT_NOTE));
                bundle.putString("infoA", projectItem.get(MyConfig.TAG_INFO_A));
                bundle.putString("infoB", projectItem.get(MyConfig.TAG_INFO_B));
                bundle.putString("infoC", projectItem.get(MyConfig.TAG_INFO_C));
                bundle.putString("infoD", projectItem.get(MyConfig.TAG_INFO_D));
                bundle.putString("photo", projectItem.get(MyConfig.TAG_PROJECT_PHOTO));
                bundle.putString("infoE", projectItem.get(MyConfig.TAG_INFO_E));
                bundle.putString("infoF", projectItem.get(MyConfig.TAG_INFO_F));
                bundle.putString("infoG", projectItem.get(MyConfig.TAG_INFO_G));
                bundle.putString("infoH", projectItem.get(MyConfig.TAG_INFO_H));
                bundle.putString("folderPhoto", projectItem.get(MyConfig.TAG_PROJECT_PHOTO));

                ProjectInfoFolderFragment fragment = new ProjectInfoFolderFragment();
                doFragmentFolderInfoTransaction(fragment, "ProjectInfoFolderFragment", false, "");
                fragment.setArguments(bundle);



                TextView folder = fragment_obj.getView().findViewById(R.id.folder_name);
                TextView InfoA = fragment_obj.getView().findViewById(R.id.text2);
                TextView InfoB = fragment_obj.getView().findViewById(R.id.text3);
                TextView InfoC = fragment_obj.getView().findViewById(R.id.text4);
                TextView InfoD = fragment_obj.getView().findViewById(R.id.text5);
                folder.setText(projectItem.get(MyConfig.TAG_PROJECT_ADDRESS));

                if(projectItem.get(MyConfig.TAG_INFO_A) == "" || projectItem.get(MyConfig.TAG_INFO_A)== null )
                    InfoA.setText("Note A");
                    else
                    InfoA.setText(projectItem.get(MyConfig.TAG_INFO_A));

                if(projectItem.get(MyConfig.TAG_INFO_B)== "" | projectItem.get(MyConfig.TAG_INFO_B) == null )
                    InfoB.setText("Note B");
                    else
                    InfoB.setText(projectItem.get(MyConfig.TAG_INFO_B));

                if(projectItem.get(MyConfig.TAG_INFO_C)== "" | projectItem.get(MyConfig.TAG_INFO_C) == null )
                    InfoC.setText("Note C");
                else
                    InfoC.setText(projectItem.get(MyConfig.TAG_INFO_C));

                if(projectItem.get(MyConfig.TAG_INFO_D)== "" | projectItem.get(MyConfig.TAG_INFO_D) == null )
                    InfoD.setText("Note D");
                else
                    InfoD.setText(projectItem.get(MyConfig.TAG_INFO_D));

                break;
            }

            case 1: {

          //      DBHandler dbHandler = new DBHandler(this, null, null, 1);

                focus = "DOC";

                fragment_obj = getSupportFragmentManager().findFragmentByTag("ProjectInfoFragment");
                LinearLayout linearLayout_ = fragment_obj.getView().findViewById(R.id.folder_info);
                linearLayout_.setVisibility(View.GONE);
                LinearLayout linearLayout = fragment_obj.getView().findViewById(R.id.document_info);
                linearLayout.setVisibility(View.VISIBLE);

                DBHandler dbHandler = new DBHandler(this, null, null, 1);
                projId = node.getprojId();
                iId = node.getiID();
                inspectionId = Integer.toString(iId);
                HashMap<String, String> projectItem = dbHandler.getInspection(projId, iId);


                bundle.putString("folderNote", projectItem.get(MyConfig.TAG_NOTE));
                bundle.putInt("projectId", projId);
                bundle.putInt("inspectionId", iId);
                bundle.putString("preamble", projectItem.get(MyConfig.TAG_NOTE_2));
                bundle.putString("inspPhoto", projectItem.get(MyConfig.TAG_IMAGE));
                bundle.putString("doc_name", projectItem.get(MyConfig.TAG_LABEL));
                bundle.putString("date", projectItem.get(MyConfig.TAG_INSPECTION_DATE));
                bundle.putString("startTime", projectItem.get(MyConfig.TAG_START_DATE_TIME));
                bundle.putString("endTime", projectItem.get(MyConfig.TAG_END_DATE_TIME));
                bundle.putString("inspectType", projectItem.get(MyConfig.TAG_INSPECTION_TYPE));
                bundle.putString("auditor", projectItem.get(MyConfig.TAG_USER_ID));

                InspectionInfoFolderFragment fragment = new InspectionInfoFolderFragment();
                doFragmentFolderInfoTransaction(fragment, "InspectionInfoFolderFragment", false, "");
                fragment.setArguments(bundle);



                TextView inspectDate = fragment_obj.getView().findViewById(R.id.Text_2);
                TextView Hrs = fragment_obj.getView().findViewById(R.id.Text_5);
                TextView inspectionType = fragment_obj.getView().findViewById(R.id.Text_3);
                TextView inspectedDate = fragment_obj.getView().findViewById(R.id.Text_4);
                TextView doc_title = fragment_obj.getView().findViewById(R.id.doc_name);
                doc_title.setText(projectItem.get(MyConfig.TAG_LABEL));
                inspectDate.setText("Document created:  " + stringdate(projectItem.get(MyConfig.TAG_INSPECTION_DATE), 1));
                inspectionType.setText("Document type:  " + projectItem.get(MyConfig.TAG_INSPECTION_TYPE));
                if (!projectItem.get(MyConfig.TAG_START_DATE_TIME).equals("null"))
                    inspectedDate.setText("Initial log recorded: " + stringdate(projectItem.get(MyConfig.TAG_START_DATE_TIME), 2) + "  -  " + stringdate(projectItem.get(MyConfig.TAG_END_DATE_TIME), 2));
                Hrs.setText("Allocation:  " + dbHandler.calcTime(Integer.toString(projId), Integer.toString(iId)));


                break;
            }
        }
        }


    @Override
    public void onResume() {

        super.onResume();

        GlobalVariables.doc_mode = 0;
        ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cManager.getActiveNetworkInfo();
      //  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
      //  buttonDownload = (Button) findViewById(R.id.btnDownloadJobs);
      //  buttonDownload.setOnClickListener(this);
     //   buttonUpload = (Button) findViewById(R.id.btnUpload);
     //   buttonUpload.setOnClickListener(this);
     //   USER_ID = 0;
        fab_add = (FloatingActionButton) findViewById(R.id.fab_add);
        fab_add.setOnClickListener(this);
        listFolders = (ImageView) findViewById(R.id.list_Folders);
        listFolders.setOnClickListener(this);

    //    btnLogin = (Button) findViewById(R.id.btnlogin);
    //    btnLogin.setOnClickListener(this);
        imgCloud = (ImageView) findViewById(R.id.image_cloud);
        imgCloud.setOnClickListener(this);
        imgLogin = (ImageView) findViewById(R.id.image_login);
        imgLogin.setOnClickListener(this);

        Folders_img = (ImageView) findViewById(R.id.imageView_projectfolder);
        Folders_img.setOnClickListener(this);

        if (nInfo != null && nInfo.isConnected()) {
            s3credentialsProvider();
            setTransferUtility();
            imgCloud.setEnabled(true);
            connected = true;
            //getJSON();
        } else {
            imgCloud.setEnabled(false);
            Toast.makeText(this, "No internet available", Toast.LENGTH_LONG).show();
            connected = false;
        }


        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        ArrayList<HashMap<String, String>> Folders = dbHandler.getFolders(USER_ID, projId);

        maplistItems = new ArrayList<>();

        //   projectlistItems = new ArrayList<>();
        MapViewData listItem;

        for (int i = 0; i < (Folders.size()); i++) {

            listItem = new MapViewData(


                    Integer.parseInt(Folders.get(i).get(MyConfig.TAG_PROJECT_ID)),

                    Integer.parseInt(Folders.get(i).get(MyConfig.TAG_LEVEL)),
                    Integer.parseInt(Folders.get(i).get(MyConfig.TAG_CAT_ID)),
                    Integer.parseInt(Folders.get(i).get(MyConfig.TAG_CHILD)),
                    Folders.get(i).get(MyConfig.TAG_LABEL),
                    Integer.parseInt(Folders.get(i).get(MyConfig.TAG_A_ID)),
                    Integer.parseInt(Folders.get(i).get(MyConfig.TAG_INSPECTION_ID)),
                    Integer.parseInt(Folders.get(i).get(MyConfig.TAG_PARENT)),
                    Folders.get(i).get(MyConfig.TAG_IMAGE1),
                    Folders.get(i).get(MyConfig.TAG_NOTES)

            );
            maplistItems.add(listItem);
        }

        GlobalVariables.dataList = (ArrayList<MapViewData>) maplistItems;

        MapViewLists.LoadInitialData();
        MapViewLists.LoadDisplayList();
    //    GlobalVariables.displayNodes = (ArrayList<MapViewNode>) maplistItems;
     //   ProjectViewList.LoadInitialData();
    //    ProjectViewList.LoadDisplayList();
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
            //    FragmentManager fm = getSupportFragmentManager();


            //fm.popBackStack(DF,0);
            //     if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            //         getSupportFragmentManager().popBackStackImmediate();
            //     }

            // fm.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
            GlobalVariables.modified = false;
            //       MapListAdapter mapListAdapter = new MapListAdapter(this);
            //       mapListAdapter.notifyDataSetChanged();
            //  OnSelectionChanged(0);


        }

    }




    private void requestProject(final String project_Name, final int template) {


        ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cManager.getActiveNetworkInfo();
        if (nInfo != null && nInfo.isConnected()) {
            final DBHandler dbHandler = new DBHandler(this, null, null, 1);
            class reqproj extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loading = ProgressDialog.show(MainActivity.this, "connection with server", "please wait...", false, false);
                    progressBar1.setVisibility(View.VISIBLE);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);

                    JSON_STRING_NEW_PROJECT = s;
                    JSONObject jsonObject = null;

                    try {
                        jsonObject = new JSONObject(s);
                        JSONArray result = jsonObject.getJSONArray(MyConfig.TAG_JSON_ARRAY);
                        //   editTextMessage.setText("Test begin additional");

                        for (int i = 0; i < result.length(); i++) {
                            JSONObject jo = result.getJSONObject(i);
                            String projId_ = jo.getString(MyConfig.TAG_PROJECT_ID);

                            // testing only -
                            //   editTextMessage.setText("Test end additional");
                            projId = Integer.parseInt(projId_);
                        }

                    } catch (JSONException e) {
                        // editTextMessage.setText(" Exception");
                        Log.e("ESM", "unexpected JSON exception", e);
                        //e.printStackTrace();
                    }

                    switch (template){

                        case 1: {
                            dbHandler.addLevel(projId, 1, 0, 1, 1, 0, "Topic 1", 0);  //this is the ESM category
                     //       dbHandler.addLevel(projId, 2, 0, 2, 1, 0, "Item Title 2", 0);  //this is the ESM category
                            dbHandler.addReportBranch(projId, 1, 1, 2, 1, "Information Page");  //this is the ESM category
                     //       dbHandler.addReportBranch(projId, 1, 2, 2, 2, "Topic data Page");  //this is the ESM category
                            break;
                        }
                    }


                    update_NewProject();
                    updatePropList();
                    loading.dismiss();
                    progressBar1.setVisibility(View.GONE);
                }

                @Override
                protected String doInBackground(Void... params) {
                    RequestHandler_ rh = new RequestHandler_();
                    String s = rh.sendRequestParam(MyConfig.URL_REQUEST_PROJECT, project_Name + "&USERID=" + USER_ID);

                    return s;

                }

            }
            reqproj req = new reqproj();
            req.execute();


        } else {
            //          buttonDownload.setEnabled(false);
            Toast.makeText(this, "No internet available", Toast.LENGTH_LONG).show();
        }
    }


    public void requestActivity(final String activity_Name) {


        ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cManager.getActiveNetworkInfo();
        if (nInfo != null && nInfo.isConnected()) {
            //    editTextMessage.setText("Sync Inspected Only");
            //Upload photos to Bucket

            // AWS transfer service - transferutility requires this for restarting if connection is lost during transfer
            //  getApplicationContext().startService(new Intent(getApplicationContext(), TransferService.class));
            if (USER_ID > 0) {
                if (!projectId.equals("null")) {

                    class reqAct extends AsyncTask<Void, Void, String> {

                        ProgressDialog loading;

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            loading = ProgressDialog.show(MainActivity.this, "connection with server", "please wait...", false, false);
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            JSON_STRING_NEW_ACTIVITY = s;
                            update_NewActivity();
                            updatePropList();
                            loading.dismiss();
                        }

                        @Override
                        protected String doInBackground(Void... params) {
                            RequestHandler_ rh = new RequestHandler_();
                            String s = rh.sendRequestParam(MyConfig.URL_REQUEST_ACTIVITY, projectId + "&label=" + activity_Name + "&USERID=" + USER_ID);
                            return s;
                        }

                    }
                    reqAct req = new reqAct();
                    req.execute();
                } else
                    Toast.makeText(MainActivity.this, "Create or select a Folder ", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(MainActivity.this, "Log in required ", Toast.LENGTH_LONG).show();


        } else {
            //          buttonDownload.setEnabled(false);
            Toast.makeText(this, "No internet available", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onClick(View v) {

        if(v==client){

        }

        if(v==settings){

            final DBHandler dbHandler = new DBHandler(getBaseContext(), null, null, 1);


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Settings");
            // add a list
            String[] actions = {"Change User Code",
                    " ",
                    " "};

            builder.setItems(actions, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    switch (which) {

                        case 0: {


                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                                builder.setTitle("CHANGE USER CODE");
                                builder.setMessage("Four Digit pass code access for uploading/downloading and Reporting");


                                builder.setNegativeButton("Change Code", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {

                                        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                                        View promptView = layoutInflater.inflate(R.layout.code_reset, null);
                                        AlertDialog.Builder passDialog = new AlertDialog.Builder(MainActivity.this);
                                        final EditText code = (EditText) promptView.findViewById(R.id.code);
                                        final EditText password = (EditText) promptView.findViewById(R.id.password);
                                        passDialog.setView(promptView);
                                        passDialog.setTitle("Change User Code");
                                        passDialog.setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                if (Pattern.matches("\\d{4}", code.getText().toString())) {


                                                        USER_CODE = code.getText().toString();
                                                        PASS_WORD = password.getText().toString();
                                                        change_usercode_JSON();


                                                } else
                                                    Toast.makeText(MainActivity.this, "Invalid Code Input", Toast.LENGTH_LONG).show();
                                            }
                                        });

                                        // create an alert dialog
                                        AlertDialog alert = passDialog.create();
                                        alert.show();
                                    }

                                });


                                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });


                                AlertDialog alert = builder.create();
                                alert.show();




                            break;

                        }

                        case 1: {



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


        if(v==fab_add){

            final ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
            DBHandler dbHandler = new DBHandler(MainActivity.this, null, null, 1);
            final String branchTitle = dbHandler.getMapBranchTitle(projId, 0);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Folders / Topics ");
            // add a list


            if(GlobalVariables.aId == 0) {

                map_menu = new String[] {"Create a New Folder",
                        "Add a Topic to the | " + branchTitle + " | Folder",
                        "Cancel Request "};

                builder.setItems(map_menu, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {

                            case 0: {

                                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                                View promptView = layoutInflater.inflate(R.layout.add_location, null);
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                                alertDialogBuilder.setView(promptView);
                                final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                                itemTitle.setText("Create a New Folder and File  ");//Integer.parseInt(locationId)
                                final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                locationText.setText("Folder Name : ");//Integer.parseInt(locationId)
                                final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
                                branchText.setHint("Title of project");
                                alertDialogBuilder.setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                if (USER_ID > 0) {
                                                    requestProject(branchText.getText().toString(),1);

                                                    //    downloadprojects();
                                                } else
                                                    Toast.makeText(MainActivity.this, "Log in required ", Toast.LENGTH_LONG).show();


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

                                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                                View promptView = layoutInflater.inflate(R.layout.add_location, null);
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                                alertDialogBuilder.setView(promptView);
                                final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                                itemTitle.setText("Folder: " + branchTitle);//Integer.parseInt(locationId)
                                final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                locationText.setText("Add a Topic to " + branchTitle+ " Folder");//Integer.parseInt(locationId)
                                final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
                                // setup a dialog window
                                alertDialogBuilder.setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //      photoBranch = "";
                                                if (projId != 0)
                                                    addLevel(1, branchText.getText().toString());

                                                else
                                                    Toast.makeText(MainActivity.this, "Create or Select a Folder", Toast.LENGTH_SHORT).show();


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
            else {

                map_menu = new String[]{"Create a New Folder",
                        "Add a Topic to the | " + branchTitle + " | Folder",
                        "Place Sub Topic to the [ " + GlobalVariables.name + " ] Topic",
                        "Cancel Request "};

                builder.setItems(map_menu, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {

                            case 0: {

                                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                                View promptView = layoutInflater.inflate(R.layout.add_location, null);
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                                alertDialogBuilder.setView(promptView);
                                final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                                itemTitle.setText("Create a New Folder and Document  ");//Integer.parseInt(locationId)
                                final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                locationText.setText("Folder Name : ");//Integer.parseInt(locationId)
                                final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
                                branchText.setHint("Title of project");
                                alertDialogBuilder.setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                if (USER_ID > 0) {
                                                    requestProject(branchText.getText().toString(), 1);

                                                    //    downloadprojects();
                                                } else
                                                    Toast.makeText(MainActivity.this, "Log in required ", Toast.LENGTH_LONG).show();


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

                                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                                View promptView = layoutInflater.inflate(R.layout.add_location, null);
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                                alertDialogBuilder.setView(promptView);
                                final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                                itemTitle.setText("Folder: " + branchTitle);//Integer.parseInt(locationId)
                                final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                locationText.setText("Add a Topic to " + branchTitle + " Folder");//Integer.parseInt(locationId)
                                final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
                                // setup a dialog window
                                alertDialogBuilder.setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //      photoBranch = "";
                                                if (projId != 0)
                                                    addLevel(1, branchText.getText().toString());

                                                else
                                                    Toast.makeText(MainActivity.this, "Create or Select a Folder", Toast.LENGTH_SHORT).show();


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

                                DBHandler dbHandler = new DBHandler(MainActivity.this, null, null, 1);

                                final String branchTitle = dbHandler.getMapBranchTitle(projId, GlobalVariables.catId); //get Branch head

                                // setup the alert builder

                                final MapViewNode node = GlobalVariables.displayNodes.get(GlobalVariables.pos);

                                if (node.getNodeLevel() > 0) {
                                    LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                                    View promptView = layoutInflater.inflate(R.layout.add_location, null);
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                                    alertDialogBuilder.setView(promptView);
                                    final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                                    itemTitle.setText("Folder: " + branchTitle);//Integer.parseInt(locationId)
                                    final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                    locationText.setText("Sub Topic to : " + node.getNodeName());//Integer.parseInt(locationId)
                                    final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
                                    // setup a dialog window
                                    alertDialogBuilder.setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    if (node.getNodeName() == "NULL" || node.getNodeName() == null)
                                                        Toast.makeText(MainActivity.this, "Select a Topic", Toast.LENGTH_SHORT).show();
                                                    else
                                                        addLevel((node.getNodeLevel() + 1), branchText.getText().toString());

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
                                } else
                                    Toast.makeText(MainActivity.this, "Select a Topic in the folder", Toast.LENGTH_SHORT).show();
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
        }

        if(v == Folders_img) {

            //Delete the information in the tables initially implemented for testing purposes
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View promptView = layoutInflater.inflate(R.layout.delete_location, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setView(promptView);
            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
            locationText.setText("Warning - DELETE ALL THE APP2DOC DATA STORED ON THIS DEVICE");//location.getText().toString());

            alertDialogBuilder.setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            USER_ID = 0;
                            GlobalVariables.User_id = 0;
                            clearTablet();
                            updatePropList();
                            ProjectInfoFragment fragment = new ProjectInfoFragment();
                            doFragmentTransaction(fragment, "ProjectInfoFragment", true, "");

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


        if (v == imgCloud) {

            final DBHandler dbHandler = new DBHandler(this, null, null, 1);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Downloads");
            // add a list
            String[] actions = {"Upload New/Edited data",
                    "Download Folders and Files",
                    "Download Images ",
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
                            passDialog.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    DBHandler dbHandler = new DBHandler(getApplicationContext(), null, null, 1);

                                    if (Pattern.matches("\\d{4}", passText.getText().toString())) {
                                        USER_ID = dbHandler.checkCode(passText.getText().toString());
                                        GlobalVariables.User_id = USER_ID;
                                        if (USER_ID > 0) {
                                            uploaddata();
                                            uploadphotos();
                                        } else
                                            Toast.makeText(MainActivity.this, "Incorrect PIN or User Login required", Toast.LENGTH_LONG).show();
                                    } else
                                        Toast.makeText(MainActivity.this, "Invalid Input", Toast.LENGTH_LONG).show();



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
                            passDialog.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    if (Pattern.matches("\\d{4}", passText.getText().toString())) {

                                    USER_ID = dbHandler.checkCode(passText.getText().toString());
                                        GlobalVariables.User_id = USER_ID;
                                    if (USER_ID > 0) {
                                        CLIENT = dbHandler.getClient(USER_ID);
                                        if (dbHandler.checkstatus("all", USER_ID) == 0)
                                            downloadprojects();
                                        else
                                            Toast.makeText(MainActivity.this, "Upload current file data prior to downloading", Toast.LENGTH_SHORT).show();


                                    } else
                                        Toast.makeText(MainActivity.this, "Log in required for downloading", Toast.LENGTH_LONG).show();
                                }
                                    else
                                        Toast.makeText(MainActivity.this, "Invalid Input", Toast.LENGTH_LONG).show();
                                }

                            });

                            // create an alert dialog
                            AlertDialog alert = passDialog.create();
                            alert.show();


                            break;

                        }

                        case 2: {

                            if (USER_ID == 0) {

                                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                                View promptView = layoutInflater.inflate(R.layout.call_log, null);
                                AlertDialog.Builder passDialog = new AlertDialog.Builder(MainActivity.this);
                                final EditText passText = (EditText) promptView.findViewById(R.id.code);
                                passDialog.setView(promptView);
                                passDialog.setTitle("Enter User Code");
                                passDialog.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        if (Pattern.matches("\\d{4}", passText.getText().toString())) {
                                        USER_ID = dbHandler.checkCode(passText.getText().toString());
                                            GlobalVariables.User_id = USER_ID;
                                        if (USER_ID > 0) {
                                            downloadphotos();

                                        } else
                                            Toast.makeText(MainActivity.this, "incorrect code or not logged in", Toast.LENGTH_LONG).show();

                                    } else
                                            Toast.makeText(MainActivity.this, "Invalid Input", Toast.LENGTH_LONG).show();
                                    }
                                });

                                // create an alert dialog
                                AlertDialog alert = passDialog.create();
                                alert.show();
                            } else downloadphotos();


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

        if (v == imgLogin) {

            final DBHandler dbHandler = new DBHandler(getBaseContext(), null, null, 1);

            if (dbHandler.checkstatus("all", 1) > 0) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("LOGIN WARNING");
                builder.setMessage("A new Login will delete current new and modified file data. Upload data prior to login.");


                builder.setNeutralButton("UPLOAD UNSAVED FILE DATA", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                        View promptView = layoutInflater.inflate(R.layout.call_log, null);
                        AlertDialog.Builder passDialog = new AlertDialog.Builder(MainActivity.this);
                        final EditText passText = (EditText) promptView.findViewById(R.id.code);
                        passDialog.setView(promptView);
                        passDialog.setTitle("Enter User Code");
                        passDialog.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                USER_ID = 0;
                                if (Pattern.matches("\\d{4}", passText.getText().toString())) {
                                USER_ID = dbHandler.checkCode(passText.getText().toString());
                                    GlobalVariables.User_id = USER_ID;
                                if (USER_ID > 0) {
                                    uploaddata();
                                    uploadphotos();
                                } else
                                    Toast.makeText(MainActivity.this, "Invalid Code", Toast.LENGTH_LONG).show();

                                } else
                                    Toast.makeText(MainActivity.this, "Invalid Input", Toast.LENGTH_LONG).show();
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
                        loginDialog.setCancelable(true).setNegativeButton("REGISTER", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                RegisterFragment fragment = new RegisterFragment();
                                doFragmentTransaction(fragment, "RegisterFragment", false, "");

                            }
                        });

                        loginDialog.setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {


                            public void onClick(DialogInterface dialog, int id) {
                                USER_ID = 0;
                                GlobalVariables.User_id = 0;
                                USER_NAME = user.getText().toString();
                                PASS_WORD = password.getText().toString();
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
            else // if all data is saved
                {


                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    builder.setTitle("LOGIN ");
                    builder.setMessage("Login / Register ");


                    builder.setCancelable(true).setNeutralButton("REGISTER NEW USER", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            RegisterFragment fragment = new RegisterFragment();
                            doFragmentTransaction(fragment, "RegisterFragment", true, "");

                        }

                    });


                    builder.setCancelable(true).setNegativeButton("PROCEED WITH LOGIN", new DialogInterface.OnClickListener() {

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

                            loginDialog.setCancelable(true).setNeutralButton("Forgot Password", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    RegisterFragment fragment = new RegisterFragment();
                                    doFragmentTransaction(fragment, "RegisterFragment", false, "");
                                }
                            });

                            loginDialog.setCancelable(true).setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {


                                public void onClick(DialogInterface dialog, int id) {
                                    USER_ID = 0;
                                    GlobalVariables.User_id = 0;
                                    USER_NAME = user.getText().toString();
                                    PASS_WORD = password.getText().toString();
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

        }


        //Loads jobs from the tablet database
        if (v == listFolders) {


            if (USER_ID == 0) {

                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View promptView = layoutInflater.inflate(R.layout.call_log, null);
                AlertDialog.Builder passDialog = new AlertDialog.Builder(MainActivity.this);
                final EditText passText = (EditText) promptView.findViewById(R.id.code);
                passDialog.setView(promptView);
                passDialog.setTitle("Enter User Code");
                passDialog.setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DBHandler dbHandler = new DBHandler(getApplicationContext(), null, null, 1);

                        if (Pattern.matches("\\d{4}", passText.getText().toString())) {
                            USER_ID = dbHandler.checkCode(passText.getText().toString());
                            GlobalVariables.User_id = USER_ID;
                            if (USER_ID > 0) {
                                CLIENT = dbHandler.getClient(USER_ID);
                                updatePropList();
                                if (CLIENT.equals("no-client")) {
                                    CLIENT = CLIENT + "-" + USER_ID;
                                }
                                if(connected) {
                                    Thread thread = new Thread(new Runnable() {

                                        @Override
                                        public void run() {
                                            boolean exists = s3Client.doesBucketExist(CLIENT);
                                            if (!exists)
                                                s3Client.createBucket(CLIENT);

                                        }

                                    });
                                    thread.start();
                                }
                                int PERMISSION_ALL = 1;
                                String[] PERMISSIONS = {
                                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                        android.Manifest.permission.READ_CONTACTS,
                                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        android.Manifest.permission.ACCESS_NETWORK_STATE,
                                        Manifest.permission.INTERNET,
                                        android.Manifest.permission.CAMERA
                                };

                                if (!hasPermissions(getBaseContext(), PERMISSIONS)) {
                                    ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, PERMISSION_ALL);
                                }

                                updatePropList();

                            } else
                                Toast.makeText(MainActivity.this, "Incorrect PIN or User Login required", Toast.LENGTH_LONG).show();

                        } else
                            Toast.makeText(MainActivity.this, "Invalid Input", Toast.LENGTH_LONG).show();
                    }
                });

                // create an alert dialog
                AlertDialog alert = passDialog.create();
                alert.show();
            }

        }



        if (v == info_icon) {

            root = Environment.getExternalStorageDirectory().getPath();
            File propImage = new File(root + "/" + projectId + "INFO/");
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
                startActivity(Intent.createChooser(galleryIntent, "OPEN"));
                // startActivity(galleryIntent);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("tag", "Exception found while listing " + e);


            }
        }


    }



    public void loadMap(int projId) {

        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        ArrayList<HashMap<String, String>> SiteMapData = dbHandler.getFolders(USER_ID, projId);
        int position = GlobalVariables.pos;

        maplistItems = new ArrayList<>();
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
            maplistItems.add(listItem);
        }


        GlobalVariables.dataList = (ArrayList<MapViewData>) maplistItems;
        GlobalVariables.modified = true;


        OnTabChanged(GlobalVariables.pos);


   /*     DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detail_text);

        MapViewFragment newDetailFragment = new MapViewFragment();

        androidx.fragment.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the backStack so the User can navigate back
        fragmentTransaction.replace(R.id.fragment_container, newDetailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();



    */
      //  GlobalVariables.displayNodes.get(GlobalVariables.pos);




        // GlobalVariables.modified = true;


    }

    public void editLocation(String branchLabel) {
        MapViewNode node = GlobalVariables.displayNodes.get(GlobalVariables.pos);
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        int success = dbHandler.updateMapLabel(node.getprojId(), node.getaID(), branchLabel);
        if(success == 1) loadMap(projId);
        else Toast.makeText(this, "Create/select TAB", Toast.LENGTH_SHORT).show();
    }



    private void addLevel(int Level, String levelName) {

        MapViewNode node = GlobalVariables.displayNodes.get(GlobalVariables.pos);
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        int result = dbHandler.addLevel(projId, node.getaID(), 0, node.getcatId(), Level, node.getaID(), levelName, 0);  //this is the ESM category
        if (result == 0)
            Toast.makeText(this, "Cannot place TAB here", Toast.LENGTH_SHORT).show();
        else
         loadMap(projId);
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

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK) {

            DBHandler dbHandler = new DBHandler(this, null, null, 1);

            if(focus == "FOLDER") {
                MapViewNode mapViewNode =  GlobalVariables.displayNodes.get(GlobalVariables.pos);
                if(mapViewNode.getNodeLevel() == 0) {
                    dbHandler.updatePropPhoto(projectId, photo.getName());
                }
                else{
                    dbHandler.updateBranchPhoto(projId, mapViewNode.getaID(), photo.getName());
                }
            }
            if(focus == "DOC") {
                ProjectNode projectNode = GlobalVariables.projectdisplayNodes.get(GlobalVariables.doc_pos);
                if(projectNode.getNodeLevel() ==0) {
                         dbHandler.updatePropPhoto(projectId, photo.getName());
                         }
                    else {
                        dbHandler.updateInspectionPhoto(projectId, inspectionId, photo.getName());

                    }
                }

            try {
                rotateImage(resizePhoto());
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(photo)));


            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (requestCode == ACTIVITY_START_CAMERA_APP) {
            photo.delete();

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

            if (FragDisplay == "InspectionInfoFolderFragment") {
                fragment_obj = getSupportFragmentManager().findFragmentByTag("InspectionInfoFolderFragment");
                ImageView photoA = fragment_obj.getView().findViewById(R.id.photo);
                photoA.setImageURI(selectedImage);
             }


            if (FragDisplay == "ProjectInfoFolderFragment") {
                fragment_obj = getSupportFragmentManager().findFragmentByTag("ProjectInfoFragment");
                ImageView photoA = fragment_obj.getView().findViewById(R.id.photo);
                photoA.setImageURI(selectedImage);
            }

            if (FragDisplay == "BaseInfoFolderFragment") {
                fragment_obj = getSupportFragmentManager().findFragmentByTag("BaseInfoFolderFragment");
                ImageView photoA = fragment_obj.getView().findViewById(R.id.photo);
                photoA.setImageURI(selectedImage);
            }




            File from = new File(path);


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

            File to = new File(SD + fname + ".jpg");


            // from.renameTo(to);  This deletes the source file from

            try {
                copy(from, to);
                if(to.length()/1048576 > 1) resizeImage(to);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (FragDisplay == "BaseInfoFolderFragment") {
                fragment_obj = getSupportFragmentManager().findFragmentByTag("BaseInfoFolderFragment");
                DBHandler dbHandler = new DBHandler(this, null, null, 1);
                String inspectionPhoto = to.getName();
                dbHandler.updateMapPhoto(projectId, GlobalVariables.aId, inspectionPhoto);

            }

            if (FragDisplay == "InspectionInfoFolderFragment") {
                DBHandler dbHandler = new DBHandler(this, null, null, 1);
                String inspectionPhoto = to.getName();
                dbHandler.updateInspectionPhoto(projectId, inspectionId, inspectionPhoto);
            }

            if (FragDisplay == "ProjectInfoFolderFragment") {
                DBHandler dbHandler = new DBHandler(this, null, null, 1);
                String projectPhoto = to.getName();
                dbHandler.updatePropPhoto(projectId, projectPhoto);
            }


            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(to)));

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



            if (requestCode == PDF_SELECTION_CODE && resultCode == Activity.RESULT_OK && data != null) {
             //   Intent intent = new Intent(Intent.ACTION_VIEW);


             //   File pdf = new File(root + "/14081INFO/19004.pdf" );
            //    Uri uri = FileProvider.getUriForFile(getBaseContext(), BuildConfig.APPLICATION_ID + ".provider", pdf);
            //    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //   File pdf = new File(data.getData().toString());
           //    Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", pdf);
                Uri uri = data.getData();

           //     data.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
          //      data.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
          //      data.addCategory(Intent.CATEGORY_OPENABLE);




         //       this.getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION );
                data.setDataAndType(uri, "application/pdf");
                data.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            //          intent.setDataAndType(uri, "application/pdf");
                //      intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
               // startActivity(data);
                startActivity(Intent.createChooser(data, "Open folder"));

                try {
             //         startActivity(intent);
              //      startActivity(data.createChooser(data, "Open folder"));
                }
                catch (ActivityNotFoundException e) {
                    Toast.makeText(this,
                            "No Application Available to View PDF",
                            Toast.LENGTH_SHORT).show();
                }

        }

    }  //end request activity


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

        mImageFileLocation = image.getAbsolutePath();
        return image;
    }

    private Bitmap resizeImage(File image) throws IOException {

        String root = image.getAbsolutePath();
        //  File imgFile = new  File(root);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();

        int screenIamgeWidth = 700; //mPhotoImageView.getWidth();
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
        int maxHeight = 1800;
        //choose a max width
        int maxWidth = 1800;
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

        int screenIamgeWidth = 2500; //mPhotoImageView.getWidth();
        int screenIamgeHeight = 1875; // mPhotoImageView.getHeight();

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
        int maxHeight = 860;
        //choose a max width
        int maxWidth = 1150;
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
        int newHeight = (int) Math.min(maxHeight, bmOriginalHeight * .8);
        int newWidth = (int) (newHeight * originalWidthToHeightRatio);
        bm = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
        return bm;
    }

    private static Bitmap scaleDeminsFromWidth(Bitmap bm, int maxWidth, int bmOriginalWidth, double originalHeightToWidthRatio) {
        //scale the width
        int newWidth = (int) Math.min(maxWidth, bmOriginalWidth * .8);
        int newHeight = (int) (newWidth * originalHeightToWidthRatio);
        bm = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
        return bm;
    }


    //Update the list to choose from by selecting the information from the SQLite db
    public void updatePropList() {

        //Get the property information for all the properties to inspect
        DBHandler dbHandler = new DBHandler(this, null, null, 1);

        TextView projectlist_title = (TextView) findViewById(R.id.ProjectList);
         projectlist_title.setText("Folder list for: "+dbHandler.getUser(USER_ID));


        ArrayList<HashMap<String, String>> Folders = dbHandler.getFolders(USER_ID, projId);
        maplistItems = new ArrayList<>();

        //   projectlistItems = new ArrayList<>();
        MapViewData listItem;

        for (int i = 0; i < (Folders.size()); i++) {

            listItem = new MapViewData(
                            Integer.parseInt(Folders.get(i).get(MyConfig.TAG_PROJECT_ID)),
                            Integer.parseInt(Folders.get(i).get(MyConfig.TAG_LEVEL)),
                            Integer.parseInt(Folders.get(i).get(MyConfig.TAG_CAT_ID)),
                            Integer.parseInt(Folders.get(i).get(MyConfig.TAG_CHILD)),
                            Folders.get(i).get(MyConfig.TAG_LABEL),
                            Integer.parseInt(Folders.get(i).get(MyConfig.TAG_A_ID)),
                            Integer.parseInt(Folders.get(i).get(MyConfig.TAG_INSPECTION_ID)),
                            Integer.parseInt(Folders.get(i).get(MyConfig.TAG_PARENT)),
                            Folders.get(i).get(MyConfig.TAG_IMAGE1),
                            Folders.get(i).get(MyConfig.TAG_NOTES)

            );
            maplistItems.add(listItem);
        }

        GlobalVariables.dataList = (ArrayList<MapViewData>) maplistItems;


        MapViewLists.LoadInitialData();
        MapViewLists.LoadInitialNodes(GlobalVariables.dataList);

      //  ProjectViewList.LoadDisplayList();
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
    /*
            FragmentManager fm = getSupportFragmentManager();


     //        fm.popBackStack();
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStackImmediate();
            }

             fm.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
            GlobalVariables.modified = false;
            //       MapListAdapter mapListAdapter = new MapListAdapter(this);
            //       mapListAdapter.notifyDataSetChanged();
            //   OnSelectionChanged(0);

     */
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
                String Note_2 = jo.getString(MyConfig.TAG_NOTE_2);

                DBHandler dbHandler = new DBHandler(this, null, null, 1);

                int projId = parseInt(projectId);
                int InspectionId = parseInt(inspectionId);
                int level = parseInt(Level);
                int parent = parseInt(Parent);
                int p_Id = parseInt(pID);


                ProjectAttributes projectrow =
                        new ProjectAttributes(projId, addressNo, address, suburb, infoA, infoB, infoC, infoD, projectPhoto, infoE, infoF, infoG, infoH, infoI, infoJ, projectNote );

                InspectionAttributes inspectionRow = new InspectionAttributes(InspectionId, inspectionType, inspectionStatus, projId, inspectionDate, inspector, startDateTime, endDateTime, Label, level, parent, p_Id, Image, Note, Note_2);
                dbHandler.updateProjectsFromServer(projectrow, inspectionRow);
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



                InspectionItemAttributes itemRow = new InspectionItemAttributes(InspectionId, projId, a_Id, dateInspected, overview, servicedBy, relevantInfo, serviceLevel, reportImg, image1, com1, image2, com2
                        , image3, com3, image4, com4, image5, com5, image6, com6, image7, com7, itemStatus, notes);


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
                String Notes = jo.getString(MyConfig.TAG_NOTES);


                DBHandler dbHandler = new DBHandler(this, null, null, 1);

                int ProjId = parseInt(projId);
                int a_Id = parseInt(aId);
                int i_Id = parseInt(iId);
                int CatId = parseInt(catId);
                int level = parseInt(Level);
                int parent = parseInt(Parent);
                int child = parseInt(Child);


                MAPattributes mapRow = new MAPattributes(ProjId, CatId, level, parent, Label, child, a_Id, i_Id, Photo, Notes);
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


                JSONObject jo = result.getJSONObject(i);
                String projid = jo.getString(MyConfig.TAG_PROJECT_ID);
                String iId = jo.getString(MyConfig.TAG_INSPECTION_ID);
                String datetime = jo.getString(MyConfig.TAG_DATE_TIME);
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


    private void updateSummary() {
        JSONObject jsonObject = null;

        //editTextMessage.setText("Print String: " + JSON_STRING);
        //editTextMessage.setText("Test");
        try {
            jsonObject = new JSONObject(JSON_STRING_SUMMARY);
            JSONArray result = jsonObject.getJSONArray(MyConfig.TAG_JSON_ARRAY);
            //  editTextMessage.setText("Test begin");

            for (int i = 0; i < result.length(); i++) {

                JSONObject jo = result.getJSONObject(i);
                String projid = jo.getString(MyConfig.TAG_PROJECT_ID);
                String iId = jo.getString(MyConfig.TAG_INSPECTION_ID);
                String headA = jo.getString(MyConfig.TAG_HEAD_A);
                String headB = jo.getString(MyConfig.TAG_HEAD_B);
                String headC = jo.getString(MyConfig.TAG_HEAD_C);
                String comA = jo.getString(MyConfig.TAG_COM_A);
                String comB = jo.getString(MyConfig.TAG_COM_B);
                String comC = jo.getString(MyConfig.TAG_COM_C);



                DBHandler dbHandler = new DBHandler(this, null, null, 1);

                int projId = parseInt(projid);
                int InspectionId = parseInt(iId);

                SummaryAttributes summary = new SummaryAttributes(InspectionId, projId, headA, headB, headC, comA, comB, comC);
                dbHandler.updateSummaryFromServer(summary);

            }


        } catch (JSONException e) {
            // editTextMessage.setText(" Exception");
            Log.e("SUMMARY", "unexpected JSON exception", e);
            //e.printStackTrace();
        }
        // testing only -
        // editTextMessage.setText("Test 4");
    }


    private void update_user_code(){
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        dbHandler.update_USER_code(USER_CODE, USER_ID);

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
                USER_ID = uID;
                GlobalVariables.User_id = USER_ID;
                CLIENT = clientName;
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

    private void update_LOG() {
        JSONObject jsonObject = null;

        //editTextMessage.setText("Print String: " + JSON_STRING);
        //editTextMessage.setText("Test");
        try {
            jsonObject = new JSONObject(JSON_STRING_LOG);
            JSONArray result = jsonObject.getJSONArray(MyConfig.TAG_JSON_ARRAY);
            //    editTextMessage.setText("Test begin Category " + result);

            for (int i = 0; i < result.length(); i++) {
                // testing only -
                String imsg = Integer.toString(i);
                String rmsg = Integer.toString(result.length());
                String msg = "Testing Loop category " + imsg + " result no " + rmsg;
                //       editTextMessage.setText(msg);

                JSONObject jo = result.getJSONObject(i);
                String aID = jo.getString(MyConfig.TAG_A_ID);
                String projId = jo.getString(MyConfig.TAG_PROJECT_ID);
                String iId = jo.getString(MyConfig.TAG_INSPECTION_ID);
                String start = jo.getString(MyConfig.TAG_START_DATE_TIME);
                String end = jo.getString(MyConfig.TAG_END_DATE_TIME);

                DBHandler dbHandler = new DBHandler(this, null, null, 1);

                int uID = parseInt(aID);
                int ProjId = parseInt(projId);
                int i_Id = parseInt(iId);

                LOG_Attributes log_attributes = new LOG_Attributes(uID, ProjId, i_Id, start, end);


                //editTextMessage.setText("Test 5");

                dbHandler.update_LOG_FromServer(log_attributes);

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


    private void update_NewProject() {
        JSONObject jsonObject = null;

        //editTextMessage.setText("Print String: " + JSON_STRING);
        //editTextMessage.setText("Test");
        try {
            jsonObject = new JSONObject(JSON_STRING_NEW_PROJECT);
            JSONArray result = jsonObject.getJSONArray(MyConfig.TAG_JSON_ARRAY);
            //    editTextMessage.setText("Test begin Category " + result);

            for (int i = 0; i < result.length(); i++) {
                // testing only -
                String imsg = Integer.toString(i);
                String rmsg = Integer.toString(result.length());
                String msg = "Testing Loop category " + imsg + " result no " + rmsg;
                //       editTextMessage.setText(msg);

                JSONObject jo = result.getJSONObject(i);
                String projID = jo.getString("ProjectID");
                String label = jo.getString("Label");
                String pId = jo.getString("pID");
                DBHandler dbHandler = new DBHandler(this, null, null, 1);

                dbHandler.addProject(USER_ID,projID,label,pId);

                // testing only -
                //   editTextMessage.setText("Test Category end");

            }

        } catch (JSONException e) {
            // editTextMessage.setText(" Exception");
            Log.e("New Project ", "unexpected JSON exception", e);
            //e.printStackTrace();
        }
        // testing only -
        // editTextMessage.setText("Test 4");
    }

    private void update_NewActivity() {
        JSONObject jsonObject = null;

        //editTextMessage.setText("Print String: " + JSON_STRING);
        //editTextMessage.setText("Test");
        try {
            jsonObject = new JSONObject(JSON_STRING_NEW_ACTIVITY);
            JSONArray result = jsonObject.getJSONArray(MyConfig.TAG_JSON_ARRAY);
            //    editTextMessage.setText("Test begin Category " + result);

            for (int i = 0; i < result.length(); i++) {
                // testing only -
                String imsg = Integer.toString(i);
                String rmsg = Integer.toString(result.length());
                String msg = "Testing Loop category " + imsg + " result no " + rmsg;
                //       editTextMessage.setText(msg);

                JSONObject jo = result.getJSONObject(i);
                String cpID = jo.getString("cpID");
                String label = jo.getString("Label");
                String pId = jo.getString("pID");
                String iId = jo.getString("iID");
                String projID = jo.getString("ProjectID");

                DBHandler dbHandler = new DBHandler(this, null, null, 1);

                dbHandler.addActivity(USER_ID, projID, iId,label,pId,cpID);

                // testing only -
                //   editTextMessage.setText("Test Category end");

            }

        } catch (JSONException e) {
            // editTextMessage.setText(" Exception");
            Log.e("New Project ", "unexpected JSON exception", e);
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
                loading = ProgressDialog.show(MainActivity.this, "connection with server", "please wait...", false, false);
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
           //     loading = ProgressDialog.show(MainActivity.this, "Connecting to the server", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
          //      loading.dismiss();
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
          //      loading = ProgressDialog.show(MainActivity.this, "Connecting to the server", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
          //      loading.dismiss();
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
        //        loading = ProgressDialog.show(MainActivity.this, "Connecting to the server", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
        //        loading.dismiss();
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
        //        loading = ProgressDialog.show(MainActivity.this, "Connecting to the server", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
        //        loading.dismiss();
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


    private void getSummaryJSON() {

        class GetSummaryJSON extends AsyncTask<Void, Void, String> {


            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
       //         loading = ProgressDialog.show(MainActivity.this, "Connecting to the server", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
        //        loading.dismiss();
                JSON_STRING_SUMMARY = s;
                //    TextMessage.setText("JSON_STRING " + s);

                updateSummary();
                // testing only - editTextMessage.setText(JSON_STRING);
            }

            @Override
            protected String doInBackground(Void... params) {

                RequestHandler_ rh = new RequestHandler_();
                String s = rh.sendGetRequestParam(MyConfig.URL_GET_SUMMARY, Integer.toString(USER_ID));
                return s;
            }



        }
        GetSummaryJSON gj = new GetSummaryJSON();
        gj.execute();
    }


    private void get_AOR_JSON() {

        class Get_OR_JSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;



            @Override
            protected void onPreExecute() {
                super.onPreExecute();
      //          loading = ProgressDialog.show(MainActivity.this, "Connecting to the server", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
      //          loading.dismiss();
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
      //          loading = ProgressDialog.show(MainActivity.this, "Connecting to the server", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
     //           loading.dismiss();
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



    private void change_usercode_JSON() {

        class Change_USERCODE_JSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //     loading = ProgressDialog.show(MainActivity.this, "Connecting to the server", "Wait...", false, false);
                progressBar1.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //    loading.dismiss();

                JSON_STRING_USER = s;
                if(s.equals("User not found")  || s.equals("incorrect password")) {
                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                    progressBar1.setVisibility(View.GONE);
                }
                else {
                    DBHandler dbHandler = new DBHandler(MainActivity.this, null, null, 1);

                    dbHandler.update_USER_code(USER_CODE,USER_ID);

                    Toast.makeText(MainActivity.this, "User Code Updated", Toast.LENGTH_LONG).show();
                    progressBar1.setVisibility(View.GONE);
                }
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler_ rh = new RequestHandler_();
                String s = rh.sendGetRequestParam(MyConfig.URL_CHANGE_USER_CODE,USER_ID+"&code="+USER_CODE+"&password="+PASS_WORD);
                return s;
            }

        }
        Change_USERCODE_JSON gj = new Change_USERCODE_JSON();
        gj.execute();
    }





    private void get_user_JSON() {

        class Get_USER_JSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
           //     loading = ProgressDialog.show(MainActivity.this, "Connecting to the server", "Wait...", false, false);
                progressBar1.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            //    loading.dismiss();

                JSON_STRING_USER = s;
                if(s.equals("User not found")  || s.equals("incorrect password")) {
                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                    progressBar1.setVisibility(View.GONE);
                }
                else {
                    clearTablet();
                    update_USER_Info();
                    downloadprojects();
                    updatePropList();
                    ProjectInfoFragment fragment = new ProjectInfoFragment();
                    doFragmentTransaction(fragment, "ProjectInfoFragment", true, "");
                    Toast.makeText(MainActivity.this, "logged in", Toast.LENGTH_LONG).show();
                    progressBar1.setVisibility(View.GONE);
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


    private void get_LOG_JSON() {

        class Get_LOG_JSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
     //           loading = ProgressDialog.show(MainActivity.this, "Connecting to the server", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
     //           loading.dismiss();
                JSON_STRING_LOG = s;
                update_LOG();

            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler_ rh = new RequestHandler_();
                String s = rh.sendGetRequestParam(MyConfig.URL_GET_LOG,Integer.toString(USER_ID));
                return s;
            }

        }
        Get_LOG_JSON gj = new Get_LOG_JSON();
        gj.execute();
    }

    //Upload tablet inspection data to the server
    private String inspToServer() throws IOError {
        String res = "initiated";
        HashMap<String, String> inspMap = new HashMap<String, String>();
        DBHandler dbHandler = new DBHandler(MainActivity.this, null, null, 1);

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
                    json.put(MyConfig.TAG_NOTE_2, inspList.get(i).get(MyConfig.TAG_NOTE_2));

                    jsonArray.put(json);

                    j = j + 1;

                } catch (Throwable t) {
                    res = "Request failed: " + t.toString();
                    return res;
                }
            }
            RequestHandler_ rh = new RequestHandler_();



            String regex = "'";   //,?\s*"a[^"]*z"\s*:[^\}]+


           String jsonString = jsonArray.toString().replace(regex,"\\\\'");


            res = rh.sendJsonPostRequest(MyConfig.URL_SYNC_INSPECTION_TO_SERVER, jsonString);
            //       res = jsonString;

        }

        return res;

    }


    //Upload tablet inspection data to the server
    private String inspItemsToServer() throws IOError {
        String res = "initiated";
        HashMap<String, String> inspItemMap = new HashMap<String, String>();
        DBHandler dbHandler = new DBHandler(MainActivity.this, null, null, 1);
        ArrayList<HashMap<String, String>> inspItemList = dbHandler.getInspectedItems_(USER_ID);
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
                    jsonItem.put(MyConfig.TAG_ITEM_STATUS, "p");
                    jsonItem.put(MyConfig.TAG_NOTES, inspItemList.get(i).get(MyConfig.TAG_NOTES));
                    jsonArray.put(jsonItem);

                } catch (Throwable t) {
                    res = "Request failed: " + t.toString();
                    return res;
                }
            }
            RequestHandler_ rh = new RequestHandler_();

            String regex = "'";

            String jsonString = jsonArray.toString().replace(regex, "\\\\'");

      //      Log.v("INSPECTION ITEM JSON", jsonString);

            res = rh.sendJsonPostRequest(MyConfig.URL_SYNC_INSPECTION_ITEMS_TO_SERVER, jsonString);


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
            String regex = "'";

            String jsonString = jsonArray.toString().replace(regex,"\\\\'");
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
        ArrayList<HashMap<String, String>> propList = dbHandler.getAllProjects(USER_ID, "m");
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
                    json.put(MyConfig.TAG_ADDRESS_NO, propList.get(i).get(MyConfig.TAG_ADDRESS_NO));
                    json.put(MyConfig.TAG_PROJECT_ADDRESS, propList.get(i).get(MyConfig.TAG_PROJECT_ADDRESS));
                    json.put(MyConfig.TAG_PROJECT_SUBURB, propList.get(i).get(MyConfig.TAG_PROJECT_SUBURB));
                    json.put(MyConfig.TAG_INFO_A, propList.get(i).get(MyConfig.TAG_INFO_A));
                    json.put(MyConfig.TAG_INFO_B, propList.get(i).get(MyConfig.TAG_INFO_B));
                    json.put(MyConfig.TAG_INFO_C, propList.get(i).get(MyConfig.TAG_INFO_C));
                    json.put(MyConfig.TAG_INFO_D, propList.get(i).get(MyConfig.TAG_INFO_D));
                    json.put(MyConfig.TAG_INFO_E, propList.get(i).get(MyConfig.TAG_INFO_E));
                    json.put(MyConfig.TAG_INFO_F, propList.get(i).get(MyConfig.TAG_INFO_F));
                    json.put(MyConfig.TAG_INFO_G, propList.get(i).get(MyConfig.TAG_INFO_G));
                    json.put(MyConfig.TAG_INFO_H, propList.get(i).get(MyConfig.TAG_INFO_H));
                    json.put(MyConfig.TAG_INFO_I, propList.get(i).get(MyConfig.TAG_INFO_I));
                    json.put(MyConfig.TAG_INFO_J, propList.get(i).get(MyConfig.TAG_INFO_J));
                    json.put(MyConfig.TAG_PROJECT_NOTE, propList.get(i).get(MyConfig.TAG_PROJECT_NOTE));
                    json.put(MyConfig.TAG_PROJECT_PHOTO, propList.get(i).get(MyConfig.TAG_PROJECT_PHOTO));


                    jsonArray.put(json);
                    j = j + 1;

                } catch (Throwable t) {
                    res = "Request failed: " + t.toString();
                    return res;
                }
            }
            RequestHandler_ rh = new RequestHandler_();
            String regex = "'";

            String jsonString = jsonArray.toString().replace(regex,"\\\\'");


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
        ArrayList<HashMap<String, String>> ActionList = dbHandler.getActions(USER_ID);
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
            String regex = "'";

            String jsonString = jsonArray.toString().replace(regex,"\\\\'");
   //         Log.v("INSPECTION ITEM JSON", jsonString);
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
        ArrayList<HashMap<String, String>> CertInspectionList = dbHandler.getCertInspections(USER_ID);
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
            String regex = "'";

            String jsonString = jsonArray.toString().replace(regex,"\\\\'");
            res = rh.sendJsonPostRequest(MyConfig.URL_SYNC_CERT_INSPECTION_TO_SERVER, jsonString);
//                res = jsonString;
//            Log.v("CERTIFICATE JSON", jsonString);
        }

        return res;

    }

    private String summaryToServer() throws IOError {
        String res = "initiated";
     //   HashMap<String, String> summaryList = new HashMap<String, String>();
        DBHandler dbHandler = new DBHandler(MainActivity.this, null, null, 1);
        //           dbHandler.puTestData();
        ArrayList<HashMap<String, String>> SummaryList = dbHandler.getAllSummary(USER_ID);
        String testString = "";
        String tempString = "";

        JSONObject json;
        JSONArray jsonArray = new JSONArray();
        int j = 0;

        if (SummaryList.size() == 0) {
            //no items toupdatelist
            res = "no_records";
        } else {
            for (int i = 0; i < SummaryList.size(); i++) {
                tempString = "Iteration, " + Integer.toString(i) + "| " + MyConfig.TAG_PROJECT_ID + " ^-^ ";
                testString = testString + tempString;


                try {
                    json = new JSONObject();
                    json.put("Iteration", Integer.toString(j));
                    json.put(MyConfig.TAG_INSPECTION_ID, SummaryList.get(i).get(MyConfig.TAG_INSPECTION_ID));
                    json.put(MyConfig.TAG_PROJECT_ID, SummaryList.get(i).get(MyConfig.TAG_PROJECT_ID));
                    json.put(MyConfig.TAG_HEAD_A, SummaryList.get(i).get(MyConfig.TAG_HEAD_A));
                    json.put(MyConfig.TAG_COM_A, SummaryList.get(i).get(MyConfig.TAG_COM_A));
                    json.put(MyConfig.TAG_HEAD_B, SummaryList.get(i).get(MyConfig.TAG_HEAD_B));
                    json.put(MyConfig.TAG_COM_B, SummaryList.get(i).get(MyConfig.TAG_COM_B));
                    json.put(MyConfig.TAG_HEAD_C, SummaryList.get(i).get(MyConfig.TAG_HEAD_C));
                    json.put(MyConfig.TAG_COM_C, SummaryList.get(i).get(MyConfig.TAG_COM_C));
                    jsonArray.put(json);
                    j = j + 1;

                } catch (Throwable t) {
                    res = "Request failed: " + t.toString();
                    return res;
                }
            }
            RequestHandler_ rh = new RequestHandler_();
            String regex = "'";

            String jsonString = jsonArray.toString().replace(regex,"\\\\'");
            res = rh.sendJsonPostRequest(MyConfig.URL_SYNC_SUMMARY_TO_SERVER, jsonString);
//                res = jsonString;
//            Log.v("SUMMARY JSON", jsonString);
        }

        return res;

    }

    private String LOGToServer() throws IOError {
        String res = "initiated";
        //   HashMap<String, String> summaryList = new HashMap<String, String>();
        DBHandler dbHandler = new DBHandler(MainActivity.this, null, null, 1);
        //           dbHandler.puTestData();
        ArrayList<HashMap<String, String>> LOGList = dbHandler.getLOG(USER_ID);
        String testString = "";
        String tempString = "";

        JSONObject json;
        JSONArray jsonArray = new JSONArray();
        int j = 0;

        if (LOGList.size() == 0) {
            //no items toupdatelist
            res = "no_records";
        } else {
            for (int i = 0; i < LOGList.size(); i++) {
                tempString = "Iteration, " + Integer.toString(i) + "| " + MyConfig.TAG_PROJECT_ID + " ^-^ ";
                testString = testString + tempString;


                try {
                    json = new JSONObject();
                    json.put("Iteration", Integer.toString(j));
                    json.put(MyConfig.TAG_A_ID, LOGList.get(i).get(MyConfig.TAG_A_ID));
                    json.put(MyConfig.TAG_PROJECT_ID, LOGList.get(i).get(MyConfig.TAG_PROJECT_ID));
                    json.put(MyConfig.TAG_INSPECTION_ID, LOGList.get(i).get(MyConfig.TAG_INSPECTION_ID));
                    json.put(MyConfig.TAG_START_DATE_TIME, LOGList.get(i).get(MyConfig.TAG_START_DATE_TIME));
                    json.put(MyConfig.TAG_END_DATE_TIME, LOGList.get(i).get(MyConfig.TAG_END_DATE_TIME));

                    jsonArray.put(json);
                    j = j + 1;

                } catch (Throwable t) {
                    res = "Request failed: " + t.toString();
                    return res;
                }
            }
            RequestHandler_ rh = new RequestHandler_();
            String jsonString = jsonArray.toString();
            res = rh.sendJsonPostRequest(MyConfig.URL_SYNC_LOG_TO_SERVER, jsonString);
//                res = jsonString;

        }

        return res;

    }


    private String DELETEToServer() throws IOError {
        String res = "initiated";
        //   HashMap<String, String> summaryList = new HashMap<String, String>();
        DBHandler dbHandler = new DBHandler(MainActivity.this, null, null, 1);
        //           dbHandler.puTestData();
        ArrayList<HashMap<String, String>> DeleteList = dbHandler.getDeleted();
        String testString = "";
        String tempString = "";

        JSONObject json;
        JSONArray jsonArray = new JSONArray();
        int j = 0;

        if (DeleteList.size() == 0) {
            //no items toupdatelist
            res = "no_records";
        } else {
            for (int i = 0; i < DeleteList.size(); i++) {
                tempString = "Iteration, " + Integer.toString(i) + "| " + MyConfig.TAG_PROJECT_ID + " ^-^ ";
                testString = testString + tempString;


                try {
                    json = new JSONObject();
                    json.put("Iteration", Integer.toString(j));
                    json.put(MyConfig.TAG_TABLE_NAME, DeleteList.get(i).get(MyConfig.TAG_TABLE_NAME));
                    json.put(MyConfig.TAG_PROJECT_ID, DeleteList.get(i).get(MyConfig.TAG_PROJECT_ID));
                    json.put(MyConfig.TAG_INSPECTION_ID, DeleteList.get(i).get(MyConfig.TAG_INSPECTION_ID));
                    json.put(MyConfig.TAG_A_ID, DeleteList.get(i).get(MyConfig.TAG_A_ID));

                    jsonArray.put(json);
                    j = j + 1;

                } catch (Throwable t) {
                    res = "Request failed: " + t.toString();
                    return res;
                }
            }
            RequestHandler_ rh = new RequestHandler_();
            String jsonString = jsonArray.toString();
            res = rh.sendJsonPostRequest(MyConfig.URL_SYNC_DELETED_TO_SERVER, jsonString);
//                res = jsonString;

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
 //               loading = ProgressDialog.show(MainActivity.this, "Syncing...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
 //               loading.dismiss();
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
                String SummarySaved;
                String LogSaved;
                String Deleted;


       //         String yes = "y";
                String message = "initiated";


                inspSaved = inspToServer();
                itemSaved = inspItemsToServer();
                MapSaved = MapToServer();
                projSaved = projToServer();
                actionsSaved = actionsToServer();
                CertInspSaved = certInspToServer();
                SummarySaved = summaryToServer();
                LogSaved = LOGToServer();
                Deleted = DELETEToServer();


                //    message = inspToServer();



                    if ((inspSaved.matches("y|no_records")) && (itemSaved.matches("y|no_records")) && (MapSaved.matches("y|no_records"))
                            && (projSaved.matches("y|no_records")) && (actionsSaved.matches("y|no_records"))
                            && (SummarySaved.matches("y|no_records")) && (CertInspSaved.matches("y|no_records"))
                            && (Deleted.matches("y|no_records"))){

                        dbHandler.statusUploaded(USER_ID);
                        dbHandler.deleteTable();

                        message = "Folders/Documents uploaded successfully";



                    }
                    else {

                        if(!inspSaved.equals("y")) message = "File process ";
                        if(!itemSaved.equals("y")) message = message + "  File Item process ";
                        if(!MapSaved.equals("y")) message = message + "  Map process ";
                        if(!projSaved.equals("y")) message = message + "  Project process ";
                        if(!actionsSaved.equals("y")) message = message + "  Action process ";
                        if(!CertInspSaved.equals("y")) message = message + "  Certificate process ";
                        if(!SummarySaved.equals("y")) message = message + "  Summary process ";
                        if(!Deleted.equals("y")) message = message + "  Delete process ";
                    }


                return message;
            }
        }

        syncInspections ue = new syncInspections();
        ue.execute();

    }



    private void reportMailer(final int type, final String email) {

        class Send_Report extends AsyncTask<Void, Void, String> {
            private ProgressDialog loading;
            private String res = "Before try";
            private String message = "fail";

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

    public void reportMenu() {

        // setup the alert builder
        final DBHandler dbHandler = new DBHandler(getApplicationContext(), null, null, 1);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an action");
        // add a list
        String[] actions = {
                "Compile and email document to user",
                "Compile and email document to Contact List entity",
                "Compile file certificate",
                "Cancel this operation."};
        builder.setItems(actions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {

                    case 0: {


                        if (dbHandler.checkstatus("project", projId) == 0)
                            reportMailer(0, "");

                        else {
                            Toast.makeText(getBaseContext(), "* Data Upload required", Toast.LENGTH_LONG).show();
                        }
                        break;

                    } //
                    case 1: {
                        if (dbHandler.checkstatus("project", projId) == 0) {
                            Intent intentContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                            startActivityForResult(intentContact, PICK_CONTACT);

                        } else
                            Toast.makeText(getBaseContext(), "* Data Upload required", Toast.LENGTH_LONG).show();
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
/*
        if(CLIENT.equals("no-client")) CLIENT=CLIENT+"-"+USER_ID;

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                boolean exists = s3Client.doesBucketExist(CLIENT);
                if(!exists)
                    s3Client.createBucket(CLIENT);

            }

        });
        thread.start();

 */
        if(CLIENT.equals("no-client")) CLIENT=CLIENT+"-"+USER_ID;
        //Get the property information for all the properties to inspect
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        ArrayList<HashMap<String, String>> list = dbHandler.getAllProjects(USER_ID, "m");
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
            if(!photo_name.equals("") ) uploadFileToS3(view, photo_name);

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

        i = 0;
        while (i  < actionlist.size() ) {   //photolist.size()
            photo_name = actionlist.get(i).get(MyConfig.TAG_IMAGE1);
            if(!photo_name.equals("")) uploadFileToS3(view, photo_name);
            i++;
        }

   //     dbHandler.statusUploaded(USER_ID);
    }


    public void uploadFileToS3(final View view, final String photo_name) {



        if (photo_name.length() > 14) {
            dirName = photo_name.substring(6, 14);
            File Folder = new File(root + "/A2D_" + dirName + "/");
            if (Folder.exists()) {
                final  File F = new File(root + "/A2D_" + dirName + "/" + photo_name);
                if (F.exists()) {



                    class upload_photo extends AsyncTask<Void, Void, String> {

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            progressBar1.setVisibility(View.VISIBLE);
                        }

                        @Override
                        protected void onPostExecute(final String s) {
                            super.onPostExecute(s);
                            progressBar1.setVisibility(View.GONE);

                                 }


                        @Override
                        protected String doInBackground(Void... params) {

                            try {

                                boolean exists = s3Client.doesObjectExist(CLIENT, "images/"+photo_name);
                                if(exists){

                                      s3Client.setObjectAcl(CLIENT,"images/"+photo_name,CannedAccessControlList.PublicRead);

                                    //     Toast.makeText(MainActivity.this, "Image "+photo_name+ " exists",Toast.LENGTH_LONG).show();
                                }
                                else {


                                    TransferUtility transferUtility = TransferUtility.builder().context(getApplicationContext()).s3Client(s3Client).build();

                                    TransferObserver transferObserver = transferUtility.upload(
                                            CLIENT,    // The bucket to upload to
                                            "images/"+photo_name,    // The key for the uploaded object
                                            F ,     // The file where the data to upload exists
                                            CannedAccessControlList.PublicRead

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
                storageDirectory = new File(root + "/A2D_" + dirName + "/");}

            if (!storageDirectory.exists()) {
                storageDirectory.mkdirs();
            }

            if (info.equals("INFO")) {
                F = new File(root + "/" + img_name);
            }   else {
                F = new File(root + "/A2D_" + dirName + "/" + img_name);
            }

            if (F.exists() != true) {
                bucket = CLIENT;


                class download_photo extends AsyncTask<Void, Void, String> {

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        progressBar1.setVisibility(View.VISIBLE);
                    }

                    @Override
                    protected void onPostExecute(final String s) {
                        super.onPostExecute(s);
                        progressBar1.setVisibility(View.GONE);

                    }


                    @Override
                    protected String doInBackground(Void... params) {

                        try {

                            TransferObserver transferObserver = transferUtility.download(
                                    bucket,     /* The bucket to download from */
                                    "images/"+img_name,    /* The key for the object to download */
                                    F      /* The file to download the object to */

                            );

                            transferObserverListener(transferObserver);

                        } catch (Exception e) {

                            Log.e("PUBLIC READ", "PUBLIC READ ERROR ",e );
                        }

                        return "images/";
                    }

                }


                download_photo dwn_photo = new download_photo();
                dwn_photo.execute();



            }//if file exists

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
                    Toast.makeText(getApplicationContext(), "connection Pending"
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
            getSummaryJSON();
            //       getCategoryJSON();
            get_AOR_JSON();
            get_LOG_JSON();
            //   get_BOR_JSON();
            //    get_OR_JSON("TABLE_B_OR");
        } else {
  //          buttonDownload.setEnabled(false);
            Toast.makeText(this, "No internet available", Toast.LENGTH_LONG).show();
        }
    }

    public void downloadphotos(){

        if(CLIENT.equals("no-client")) CLIENT=CLIENT+"-"+USER_ID;
        //Get the property information for all the properties to inspect
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        ArrayList<HashMap<String, String>> plist = dbHandler.dwnLoadPropertyPhoto(projId);
        ArrayList<HashMap<String, String>> list = dbHandler.dwnLoadInspectionPhotos(projId);
        //Get a list of all the images for the properties to inspect
        ArrayList<HashMap<String, String>> ilist = dbHandler.dwnLoadInspectionItemPhotos(projId);
        ArrayList<HashMap<String, String>> photolist = dbHandler.dwnLoadActionPhotos(projId);

        int i = 0;
        while (i < plist.size()) {
            if (plist.get(i).get(MyConfig.TAG_IMAGE) != null || plist.get(i).get(MyConfig.TAG_IMAGE) != "")
                downloadFileFromS3(view, plist.get(i).get(MyConfig.TAG_IMAGE));
            String s = plist.get(i).get(MyConfig.TAG_IMAGE);
            i++;
        }

        i = 0;
        while (i < list.size()) {
            if (list.get(i).get(MyConfig.TAG_IMAGE) != null || list.get(i).get(MyConfig.TAG_IMAGE) != "")
            downloadFileFromS3(view, list.get(i).get(MyConfig.TAG_IMAGE));
            String s = list.get(i).get(MyConfig.TAG_IMAGE);
            i++;
        }

        //for each propertyID call method to search the propertyIDINFO create the directory and download the information

        i = 0;

        while (i < ilist.size()) {
            if (ilist.get(i).get(MyConfig.TAG_IMAGE1) != null || ilist.get(i).get(MyConfig.TAG_IMAGE1) != "")
                downloadFileFromS3(view, ilist.get(i).get(MyConfig.TAG_IMAGE1));
            if (ilist.get(i).get(MyConfig.TAG_IMAGE2) != null || ilist.get(i).get(MyConfig.TAG_IMAGE2) != "")
                downloadFileFromS3(view, ilist.get(i).get(MyConfig.TAG_IMAGE2));
            if (ilist.get(i).get(MyConfig.TAG_IMAGE3) != null || ilist.get(i).get(MyConfig.TAG_IMAGE3) != "")
                downloadFileFromS3(view, ilist.get(i).get(MyConfig.TAG_IMAGE3));
            if (ilist.get(i).get(MyConfig.TAG_IMAGE4) != null || ilist.get(i).get(MyConfig.TAG_IMAGE4) != "")
                downloadFileFromS3(view, ilist.get(i).get(MyConfig.TAG_IMAGE4));
            if (ilist.get(i).get(MyConfig.TAG_IMAGE5) != null || ilist.get(i).get(MyConfig.TAG_IMAGE5) != "")
                downloadFileFromS3(view, ilist.get(i).get(MyConfig.TAG_IMAGE5));
            i++;
        }


        i = 0;
        while (i < photolist.size() ) {  //
            if (photolist.get(i).get(MyConfig.TAG_IMAGE1) != null || photolist.get(i).get(MyConfig.TAG_IMAGE1) != "")
            downloadFileFromS3(view, photolist.get(i).get(MyConfig.TAG_IMAGE1));
            String s = photolist.get(i).get(MyConfig.TAG_IMAGE1);
            i++;
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
  //          buttonDownload.setEnabled(false);
            Toast.makeText(this, "No internet available", Toast.LENGTH_LONG).show();
        }
    }

}
