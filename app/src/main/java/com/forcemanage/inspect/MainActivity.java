package com.forcemanage.inspect;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.handlers.RequestHandler;
import com.amazonaws.handlers.RequestHandler2;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferManager;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferService;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static com.amazonaws.regions.Regions.AP_SOUTHEAST_2;
import static java.lang.Integer.parseInt;
import static java.util.Objects.isNull;


public class MainActivity extends AppCompatActivity implements OnVerseNameSelectionChangeListener,  View.OnClickListener {

    public static final int REQUEST_CODE = 20;
    private Button buttonUpdatePropInfo;
    private Button buttonSyncAll;
    private Button buttonClearAll;
    private Button buttonDownloadPhotos;
    private Button buttonSyncPhotos;
    private Button buttonInspection;
    private Button buttonLoadJobList;
    private Button buttonLoadNotes;
    private EditText TextMessage;
    DBHandler ESMdb;
    private String JSON_STRING;
    private String JSON_STRING_ADDITIONAL;
    private String JSON_STRING_CATEGORIES;
    private String JSON_STRING_OR;
    private ListView listView;
    private CheckBox checkBox;
    private Switch ToggleTB;
    private ImageView mPhotoImageView;
    private ImageView info_icon;
    private ImageView photo_cam;
    private File photo;
    private String dirName;
    public String inspectionId;
    public String projectId;
    private String fname;
    private String cat;

    private List<MapViewData> listItems;
    private String mImageFileLocation;
    private static final int REQUEST_OPEN_RESULT_CODE = 0, REQUEST_GET_SINGLE_FILE = 1;
    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private RecyclerView recyclerView;
  //  private List<Joblistdata> jobList;
    private String FragDisplay;

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

        //prop_name = (textView) findViewById(R.id.textView4);
// Spinner element
        Spinner spinInspector = (Spinner) findViewById(R.id.spinnerInspectorID);

        // Spinner click listener
        // spinInspector.setOnClickListener(this);

        // Spinner Drop down elements

        // callback method to call credentialsProvider method.
        s3credentialsProvider();

        // callback method to call the setTransferUtility method
        setTransferUtility();


        DBHandler dbHandlerA = new DBHandler(this, null, null, 1);


        //  ItemNumbers = (TextView) findViewById(R.id.RecordCount);
        //  ItemNumbers.setText("Property has "+Integer.toString(itemNumbers.size())+" items.");


        init();

        ArrayList<HashMap<String, String>> Projects = dbHandlerA.getProjects();

        listItems = new ArrayList<>();
        MapViewData listItem;

        for (int i = 0; i < (Projects.size()); i++){

            listItem = new MapViewData(


                    Integer.parseInt(Projects.get(i).get(MyConfig.TAG_LEVEL)),
                 //
                    Integer.parseInt(Projects.get(i).get(MyConfig.TAG_LEVEL)),
                    Integer.parseInt(Projects.get(i).get(MyConfig.TAG_LEVEL)),

                    Projects.get(i).get(MyConfig.TAG_LABEL),

                    Integer.parseInt(Projects.get(i).get(MyConfig.TAG_LEVEL)),
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

        List<String> inspectors = new ArrayList<String>();
        inspectors.add("AP");
        inspectors.add("NP");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, inspectors);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinInspector.setAdapter(dataAdapter);

        // editTextMessage.setText("Inspector: " + String.valueOf(spinInspector.getSelectedItem()));

        // Toast.makeText(this, "Inspector: "+ String.valueOf(spinInspector.getSelectedItem()), Toast.LENGTH_LONG).show();

        buttonUpdatePropInfo = (Button) findViewById(R.id.btnDownloadJobs);
        buttonUpdatePropInfo.setOnClickListener(this);
        buttonDownloadPhotos = (Button) findViewById(R.id.btnDownloadPhotos);
        buttonDownloadPhotos.setOnClickListener(this);
        buttonClearAll = (Button) findViewById(R.id.btnClearData);
        buttonClearAll.setOnClickListener(this);
        buttonSyncAll = (Button) findViewById(R.id.btnSyncAll);
        buttonSyncAll.setOnClickListener(this);
        buttonSyncPhotos = (Button) findViewById(R.id.btnSyncPhotos);
        buttonSyncPhotos.setOnClickListener(this);
   //     buttonInspection = (Button) findViewById(R.id.btnInspection);
   //     buttonInspection.setOnClickListener(this);
        buttonLoadJobList = (Button) findViewById(R.id.btnloadJobs);
        buttonLoadJobList.setOnClickListener(this);
        buttonLoadNotes = (Button) findViewById(R.id.btnDownload_O_R);
        buttonLoadNotes.setOnClickListener(this);
        info_icon = (ImageView) findViewById(R.id.imageView_info);
        info_icon.setOnClickListener(this);
        photo_cam =(ImageView) findViewById(R.id.imageView_cam);
        photo_cam.setOnClickListener(this);
        buttonClearAll.setEnabled(false);
        // listView = (ListView)findViewById(R.id.lstMain);



        ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cManager.getActiveNetworkInfo();
        if (nInfo != null && nInfo.isConnected()) {
            //getJSON();
        } else {
            buttonUpdatePropInfo.setEnabled(false);
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


        projectId = Integer.toString(node.getbranchCat()); //This is setup in MainActivity as BranchCat to work with MapList
        inspectionId = Integer.toString(node.getiID());


        switch (node.getNodeLevel()){

            case 0:{

                ProjectInfoFragment fragment = new ProjectInfoFragment();
                doFragmentTransaction(fragment,"ProjectInfoFragment",false,"");
                break;
            }
            case 1:{

                InspectInfoFragment fragment = new InspectInfoFragment();
                doFragmentTransaction(fragment, "ProjectInfoFragment", false, "");
                break;
            }
        }


    }

    @Override
    public void onResume(){

        super.onResume();
        //   updatePropList();
       //   Toast.makeText(this, "This has just resumed. Do a login", Toast.LENGTH_LONG).show();


  /*      DBHandler dbHandler = new DBHandler(this, null, null, 1);
        ArrayList<HashMap<String, String>> Projects = dbHandler.getProjects();

        listItems = new ArrayList<>();
        MapViewData listItem;

        for (int i = 0; i < (Projects.size()); i++){

            listItem = new MapViewData(


                    Integer.parseInt(Projects.get(i).get(MyConfig.TAG_LEVEL)),
                    //   Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_CAT_ID)),
                    Integer.parseInt(Projects.get(i).get(MyConfig.TAG_LEVEL)),
                    Integer.parseInt(Projects.get(i).get(MyConfig.TAG_PROJECT_ID)),

                    Projects.get(i).get(MyConfig.TAG_LABEL),
                    Integer.parseInt(Projects.get(i).get(MyConfig.TAG_INSPECTION_ID)),
                    Integer.parseInt(Projects.get(i).get(MyConfig.TAG_PARENT)),
                    Projects.get(i).get(MyConfig.TAG_IMAGE1),
                    Projects.get(i).get(MyConfig.TAG_NOTES)
            );
            listItems.add(listItem);
        }

        GlobalVariables.dataList = (ArrayList<MapViewData>) listItems;
        //     TreeViewLists.LoadDisplayList();

*/

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


    @Override
    public void onClick(View v) {

        Spinner spinner1 = (Spinner) findViewById(R.id.spinnerInspectorID);
        String selectedItem = "Sync All: " + String.valueOf(spinner1.getSelectedItem());

        if (v == buttonUpdatePropInfo) {
            //update the SQLite db with data from MySQL db if there is and internet connection
            ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cManager.getActiveNetworkInfo();
            if (nInfo != null && nInfo.isConnected()) {
                getJSON();
                getAdditionalJSON();
         //       getCategoryJSON();
                get_AOR_JSON();
             //   get_BOR_JSON();
                //    get_OR_JSON("TABLE_B_OR");
            } else {
                buttonUpdatePropInfo.setEnabled(false);
                Toast.makeText(this, "No internet available", Toast.LENGTH_LONG).show();
            }
        }
        if (v == buttonClearAll) {
            //Delete the information in the tables initially implemented for testing purposes
            clearTablet();
            updatePropList();
        }

        if (v == buttonLoadNotes) {
            ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cManager.getActiveNetworkInfo();
            if (nInfo != null && nInfo.isConnected()) {
                get_AOR_JSON();
               // get_BOR_JSON();
               // get_COR_JSON();
               // get_DOR_JSON();
               // get_EOR_JSON();

            } else {
                buttonLoadNotes.setEnabled(false);
                Toast.makeText(this, "No internet available", Toast.LENGTH_LONG).show();
            }
        }

        //Loads jobs from the tablet database
        if (v == buttonLoadJobList) {

            updatePropList();
        }

        if (v == buttonDownloadPhotos) {

            //Get the property information for all the properties to inspect
            DBHandler dbHandler = new DBHandler(this, null, null, 1);
            ArrayList<HashMap<String, String>> list = dbHandler.getAllProjects();
            //Get a list of all the images for the properties to inspect
            DBHandler dbHandlerphoto = new DBHandler(this, null, null, 1);
            ArrayList<HashMap<String, String>> photolist = dbHandler.getInspectedItemPhotos();

            int i = 0;
            while (i < list.size()) {

                downloadFileFromS3(view, list.get(i).get(MyConfig.TAG_PROJECT_PHOTO));
                i++;
            }

            //for each propertyID call method to search the propertyIDINFO create the directory and download the information

            i = 0;
            while (i < list.size()) {

                getObjectslistFromFolder(bucket,list.get(i).get(MyConfig.TAG_PROJECT_ID)+"INFO");

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

        if (v == buttonSyncAll) {
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
        if (v == buttonSyncPhotos) {
            //update the SQLite db with data from MySQL db if there is and internet connection
            ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cManager.getActiveNetworkInfo();
            if (nInfo != null && nInfo.isConnected()) {
                //    editTextMessage.setText("Sync Inspected Only");
                //Upload photos to Bucket

                // AWS transfer service - transferutility requires this for restarting if connection is lost during transfer
                //  getApplicationContext().startService(new Intent(getApplicationContext(), TransferService.class));
                upLoadPhotos(null);

            } else {
                buttonUpdatePropInfo.setEnabled(false);
                Toast.makeText(this, "No internet available", Toast.LENGTH_LONG).show();
            }
        }

 /*       if (v == buttonInspection) {
            Intent theIntent = new Intent(getApplication(), InspectionActivity.class);

            //Intent theIntent = new Intent(getApplication(), ViewReportActivity.class);
            Bundle extras = new Bundle();
            if(projectId == null ){Toast.makeText(this, "Select a project", Toast.LENGTH_LONG).show();}

            else {

                GlobalVariables.modified = true;
                DBHandler dbHandler = new DBHandler(this, null, null, 1);


                extras.putString("PROJECT_ID", projectId);
                extras.putString("INSPECTION_ID", inspectionId);
                theIntent.putExtras(extras);
                startActivity(theIntent);

            }
        }

  */

        if (v == photo_cam) {

            mPhotoImageView = (ImageView) findViewById(R.id.imageView6);
            takeImageFromCamera(null);

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

        camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(MainActivity.this,BuildConfig.APPLICATION_ID + ".provider", photo));
        startActivityForResult(camera_intent, ACTIVITY_START_CAMERA_APP);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {



        if (requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK) {

            try {
                rotateImage(resizePhoto());
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(photo)));
                DBHandler dbHandler = new DBHandler(this, null, null, 1);
                dbHandler.updatePropPhoto(projectId, photo.getName());


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if (requestCode == ACTIVITY_START_CAMERA_APP) {
            photo.delete();

        }
    }

    File createPhotoFile()throws IOException {

        fname = new SimpleDateFormat("yyyyMMddHH").format(new Date());
        dirName = new SimpleDateFormat("yyyyMMdd").format(new Date());
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
    private void updatePropList() {

        //Get the property information for all the properties to inspect
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        final ArrayList<HashMap<String, String>> list = dbHandler.getAllProjects();
        //Get a list of all the images for the properties to inspect
      //  DBHandler dbHandlerphoto = new DBHandler(this, null, null, 1);
        ArrayList<HashMap<String, String>> photolist = dbHandler.getInspectedItemPhotos();
        ArrayList<HashMap<String, String>> Projects = dbHandler.getProjects();
        listItems = new ArrayList<>();
        MapViewData listItem;

        for (int i = 0; i < (Projects.size()); i++){

            listItem = new MapViewData(


                    Integer.parseInt(Projects.get(i).get(MyConfig.TAG_LEVEL)),
                    //   Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_CAT_ID)),
                    Integer.parseInt(Projects.get(i).get(MyConfig.TAG_LEVEL)),
                    Integer.parseInt(Projects.get(i).get(MyConfig.TAG_PROJECT_ID)),

                    Projects.get(i).get(MyConfig.TAG_LABEL),
                    Integer.parseInt(Projects.get(i).get(MyConfig.TAG_INSPECTION_ID)),
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
       //     if (savedInstanceState != null){
       //         return;
       //     }

            // Create an Instance of Fragment
            MapViewFragment treeFragment = new MapViewFragment();
            treeFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, treeFragment)
                    .commit();

        }

        // To Implement: populate the string and preferably go to item in list... otherwise find a way to include property ID without displaying
        //for(int i = 0; i<list.size(); i++){
        //    propertyList[i] = "Lake street, 25, Reservoir";
        //}
   /*     if (list.size() == 0) {

            recyclerView  = (RecyclerView) findViewById(R.id.reportView);
            recyclerView.setAdapter(null);

            //display an empty list

        } else if (list.size() != 0) {
            // Get the ListView and assign an event handler to it

            TextView record;
            record = (TextView) findViewById(R.id.textView3);
            record.setText(Integer.toString(list.size()) + " Properties to inspect: ");

            // Download the property images and store them in their corresponding directories


            final List<Projectlistdata> projectlistdata =   new ArrayList<>();
            Projectlistdata listItem;

            for (int i = 0; i <= (list.size()-1); i++){
                listItem = new Projectlistdata(

                        list.get(i).get(MyConfig.TAG_PROJECT_ADDRESS),
                        list.get(i).get(MyConfig.TAG_PROJECT_ID),
                        list.get(i).get(MyConfig.TAG_ADDRESS_NO),
                        list.get(i).get(MyConfig.TAG_INSPECTION_STATUS),
                        list.get(i).get(MyConfig.TAG_BUILD_TYPE),
                        list.get(i).get(MyConfig.TAG_PROJECT_SUBURB)
                );
                projectlistdata.add(listItem);
            }


            recyclerView  = (RecyclerView) findViewById(R.id.reportView);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            ProjectListAdapter projectlistAdapter = new ProjectListAdapter(projectlistdata ,new projectchangelistener() {
                @Override
                public void OnjobChanged(int position) {

                    Log.v("List position: ", Integer.toString(position));



                    TextView propName = (TextView) findViewById(R.id.textView4);

                    // Convert that contactId into a String

                    //                   String propertyIdValue = propertyId.getText().toString();
//




                    propName.setText(list.get(position).get(MyConfig.TAG_ADDRESS_NO) + "  " + list.get(position).get(MyConfig.TAG_PROJECT_ADDRESS)
                            + "  File: " + list.get(position).get(MyConfig.TAG_PROJECT_PHOTO));


                    mPhotoImageView = (ImageView) findViewById(R.id.imageView6);
                    String propPhoto = list.get(position).get(MyConfig.TAG_PROJECT_PHOTO);
                    //      String jobstat = list.get(position).get(MyConfig.TAG_JOB_STATUS);

                    projectId = list.get(position).get(MyConfig.TAG_PROJECT_ID);
                    inspectionId = list.get(position).get(MyConfig.TAG_INSPECTION_ID);

                    if(propPhoto.length() > 14)
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

                }

            });

            recyclerView.setAdapter(projectlistAdapter);



        }

    */
    }


    //Update the SQLite db with data from MySQL db
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
                String id = jo.getString(MyConfig.TAG_PROJECT_ID);
                String addressNo = jo.getString(MyConfig.TAG_ADDRESS_NO);
                String address = jo.getString(MyConfig.TAG_PROJECT_ADDRESS);
                String suburb = jo.getString(MyConfig.TAG_PROJECT_SUBURB);
                String buildType = jo.getString(MyConfig.TAG_BUILD_TYPE);
                String buildPermitNmbr = jo.getString(MyConfig.TAG_BUILD_PERMIT_NMBR);
                String buildClass = jo.getString(MyConfig.TAG_BUILD_CLASS);
                String nmbrLevels = jo.getString(MyConfig.TAG_NMBR_LEVELS);
                String projectNote = jo.getString(MyConfig.TAG_PROJECT_NOTE);
                String projectPhoto = jo.getString(MyConfig.TAG_PROJECT_PHOTO);
                String keyRequired = jo.getString(MyConfig.TAG_KEY_REQUIRED);
                String floorType = jo.getString(MyConfig.TAG_FLOOR_TYPE);
                String roofType = jo.getString(MyConfig.TAG_ROOF_TYPE);
                String wallType = jo.getString(MyConfig.TAG_WALL_TYPE);
                String inspectionId = jo.getString(MyConfig.TAG_INSPECTION_ID);
                String inspectionType = jo.getString(MyConfig.TAG_INSPECTION_TYPE);
                String inspectionDate = jo.getString(MyConfig.TAG_INSPECTION_DATE);
                String inspector = jo.getString(MyConfig.TAG_INSPECTOR);
                String inspectionStatus = jo.getString(MyConfig.TAG_INSPECTION_STATUS);
//               String startDateTime = jo.getString(MyConfig.TAG_START_DATE_TIME);
//                String endDateTime = jo.getString(MyConfig.TAG_END_DATE_TIME);
                String Label = jo.getString(MyConfig.TAG_LABEL);
                String Level = jo.getString(MyConfig.TAG_LEVEL);
                String Parent = jo.getString(MyConfig.TAG_PARENT);
                String pID = jo.getString(MyConfig.TAG_P_ID);
                String Image = jo.getString(MyConfig.TAG_IMAGE);
                String Note = jo.getString(MyConfig.TAG_NOTE);
                String dateInspected = jo.getString(MyConfig.TAG_DATE_INSPECTED);
                String overview = jo.getString(MyConfig.TAG_OVERVIEW);
                String servicedBy = jo.getString(MyConfig.TAG_SERVICED_BY);
                String relevantInfo = jo.getString(MyConfig.TAG_RELEVANT_INFO);
                String aId = jo.getString(MyConfig.TAG_A_ID);
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

                int projId = parseInt(id);
                int InspectionId = parseInt(inspectionId);
                int noLevels = parseInt(nmbrLevels);
                int a_Id = parseInt(aId);
                int p_Id = parseInt(pID);
                int level = parseInt(Level);
                int parent = parseInt(Parent);
//                int startDateTime_ = parseInt(startDateTime);
//                int endDateTime_ = parseInt(endDateTime);
   //             int ServiceLevel = parseInt(serviceLevel);

                ProjectAttributes projectrow =
                        new ProjectAttributes(projId, addressNo, address, suburb, buildType, buildPermitNmbr, buildClass, noLevels, projectPhoto, keyRequired, floorType, roofType, wallType, projectNote );
                // editTextMessage.setText("Test 1");


                InspectionAttributes inspectionRow = new InspectionAttributes(InspectionId, inspectionType, inspectionStatus, projId, inspectionDate, inspector, Label, level, parent, p_Id, Image, Note);

                // editTextMessage.setText("Test 2");


                InspectionItemAttributes itemRow = new InspectionItemAttributes(InspectionId, projId, a_Id, dateInspected, overview, servicedBy, relevantInfo, serviceLevel, reportImg, image1, com1, image2, com2
                                                      , image3, com3, image4, com4, image5, com5, image6, com6, image7, com7, itemStatus, notes);

                //editTextMessage.setText("Test 5");

               ActionItemAttributes actionrow = new ActionItemAttributes(InspectionId, projId, a_Id, dateInspected, overview, servicedBy, relevantInfo, serviceLevel, reportImg, image1, com1,itemStatus, notes);



                dbHandler.updateFromServer(projectrow, inspectionRow, itemRow, actionrow);


                // testing only -
                // editTextMessage.setText("Test end");

            }

        } catch (JSONException e) {
            // editTextMessage.setText(" Exception");
            Log.e("ESM", "unexpected JSON exception", e);
            //e.printStackTrace();
        }
        // testing only -
        // editTextMessage.setText("Test 4");
        updatePropList();

    }

    //Update the SQLite db with additional from MySQL db
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

    //Update the SQLite db with additional from MySQL db
 /*   private void updateCategoryInfo() {
        JSONObject jsonObject = null;

        //editTextMessage.setText("Print String: " + JSON_STRING);
        //editTextMessage.setText("Test");
        try {
            jsonObject = new JSONObject(JSON_STRING_CATEGORIES);
            JSONArray result = jsonObject.getJSONArray(MyConfig.TAG_JSON_ARRAY);
            //    editTextMessage.setText("Test begin Category " + result);

            for (int i = 0; i < result.length(); i++) {
                // testing only -
                String imsg = Integer.toString(i);
                String rmsg = Integer.toString(result.length());
                String msg = "Testing Loop category " + imsg + " result no " + rmsg;
                //       editTextMessage.setText(msg);

                JSONObject jo = result.getJSONObject(i);
                String catId = jo.getString(MyConfig.TAG_CAT_ID);
                String esmCatId = jo.getString(MyConfig.TAG_CATEGORY_ID);
                String esmCatName = jo.getString(MyConfig.TAG_CATEGORY_NAME);
                String esmSubCatId = jo.getString(MyConfig.TAG_SUB_CATEGORY_ID);
                String esmSubCatName = jo.getString(MyConfig.TAG_SUB_CATEGORY_NAME);
                String freq = jo.getString(MyConfig.TAG_FREQUENCY);
                String freqNote = jo.getString(MyConfig.TAG_FREQUENCY_NOTE);

                DBHandler dbHandler = new DBHandler(this, null, null, 1);

                int cId = parseInt(catId);

                ESMCategoryAttributes categoryRow =
                        new ESMCategoryAttributes(cId, esmCatId, esmCatName, esmSubCatId, esmSubCatName, freq, freqNote);


                //editTextMessage.setText("Test 5");

                dbHandler.updateCategoriesFromServer(categoryRow);

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
*/

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

    private void postInspectionJSON() {

    }

    private void clearTablet() {
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        dbHandler.clearAllData();

    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            Spinner spinner1 = (Spinner) findViewById(R.id.spinnerInspectorID);
            String ServicePerson = String.valueOf(spinner1.getSelectedItem());

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
                String s = rh.sendGetRequestParam(MyConfig.URL_GET_PROJECT_INFO, ServicePerson);
                return s;
            }

        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void getAdditionalJSON() {
        class GetAdditionalJSON extends AsyncTask<Void, Void, String> {

            Spinner spinner1 = (Spinner) findViewById(R.id.spinnerInspectorID);
            String ServicePerson = String.valueOf(spinner1.getSelectedItem());

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
                String s = rh.sendGetRequestParam(MyConfig.URL_GET_ADDITIONAL_INFO, ServicePerson);
                return s;
            }



        }
        GetAdditionalJSON gj = new GetAdditionalJSON();
        gj.execute();
    }

    /*
    private void getCategoryJSON() {
        class GetCategoryJSON extends AsyncTask<Void, Void, String> {

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
                JSON_STRING_CATEGORIES = s;

                updateCategoryInfo();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler_ rh = new RequestHandler_();
                String s = rh.sendGetRequest(MyConfig.URL_GET_CATEGORY_INFO);
                return s;
            }

        }
        GetCategoryJSON gj = new GetCategoryJSON();
        gj.execute();
    }
*/
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

    private void get_COR_JSON() {

        class Get_COR_JSON extends AsyncTask<Void, Void, String> {

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

                update_OR_Info("C");
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler_ rh = new RequestHandler_();
                String s = rh.sendGetRequestParam(MyConfig.URL_GET_OR_INFO,"C_OR");
                return s;
            }

        }
        Get_COR_JSON gj = new Get_COR_JSON();
        gj.execute();
    }

    private void get_DOR_JSON() {

        class Get_DOR_JSON extends AsyncTask<Void, Void, String> {

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

                update_OR_Info("D");
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler_ rh = new RequestHandler_();
                String s = rh.sendGetRequestParam(MyConfig.URL_GET_OR_INFO,"D_OR");
                return s;
            }

        }
        Get_DOR_JSON gj = new Get_DOR_JSON();
        gj.execute();
    }
    private void get_EOR_JSON() {

        class Get_EOR_JSON extends AsyncTask<Void, Void, String> {

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

                update_OR_Info("E");
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler_ rh = new RequestHandler_();
                String s = rh.sendGetRequestParam(MyConfig.URL_GET_OR_INFO,"E_OR");
                return s;
            }

        }
        Get_EOR_JSON gj = new Get_EOR_JSON();
        gj.execute();
    }

    //Upload tablet inspection data to the server
    private String inspToServer() throws IOError {
        String res = "initiated";
        HashMap<String, String> inspMap = new HashMap<String, String>();
        DBHandler dbHandler = new DBHandler(MainActivity.this, null, null, 1);
        //           dbHandler.puTestData();
        ArrayList<HashMap<String, String>> inspList = dbHandler.getInspections();
        String testString = "";
        String tempString = "";

        //  EditText propertyPhoto;

        //  propertyPhoto.setText(inspList);

        JSONObject json;
        JSONArray jsonArray = new JSONArray();
        int j = 0;

        if (inspList.size() == 0) {
            //no items toupdatelist
            res = "No inspections to upload";
        } else {
            for (int i = 0; i < inspList.size(); i++) {
                tempString = "Iteration, " + Integer.toString(i) + "| " + MyConfig.TAG_INSPECTION_ID + ", " + inspList.get(i).get(MyConfig.TAG_INSPECTION_ID) + " ^-^ ";
                testString = testString + tempString;


                try {
                    json = new JSONObject();
                    json.put("Iteration", Integer.toString(j));
                    json.put(MyConfig.TAG_INSPECTION_ID, inspList.get(i).get(MyConfig.TAG_INSPECTION_ID));
                    if(inspList.get(i).get(MyConfig.TAG_INSPECTION_DATE)==null){json.put(MyConfig.TAG_INSPECTION_DATE, "2019-06-01");}
                    else {json.put(MyConfig.TAG_INSPECTION_DATE, inspList.get(i).get(MyConfig.TAG_INSPECTION_DATE));}
                    json.put(MyConfig.TAG_INSPECTION_STATUS, inspList.get(i).get(MyConfig.TAG_INSPECTION_STATUS));
                 //   json.put(MyConfig.TAG_START_DATE_TIME, inspList.get(i).get(MyConfig.TAG_START_DATE_TIME));
                 //   json.put(MyConfig.TAG_END_DATE_TIME, inspList.get(i).get(MyConfig.TAG_END_DATE_TIME));

                    jsonArray.put(json);
                    j = j + 1;

                } catch (Throwable t) {
                    res = "Request failed: " + t.toString();
                    return res;
                }
            }
            RequestHandler_ rh = new RequestHandler_();
            String jsonString = jsonArray.toString();

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
        ArrayList<HashMap<String, String>> inspItemList = dbHandler.getInspectionItems();
        String tempString = "";
        String testString = "";

        JSONObject jsonItem;
        JSONArray jsonArray = new JSONArray();

        if (inspItemList.size() == 0) {
            //display an empty list
            res = "No inspected items to upload";
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
            String jsonString = jsonArray.toString();



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
            res = "No SiteMap to upload";
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
        ArrayList<HashMap<String, String>> propList = dbHandler.getAllProjects();
        String testString = "";
        String tempString = "";

        JSONObject json;
        JSONArray jsonArray = new JSONArray();
        int j = 0;

        if (propList.size() == 0) {
            //no items toupdatelist
            res = "No Assets to upload";
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

        if (actionItemList.size() == 0) {
            //no items toupdatelist
            res = "No Locations to upload";
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

                String yes = "y";
                String message = "initiated";

                inspSaved = inspToServer();
                itemSaved = inspItemsToServer();
                MapSaved = MapToServer();
                projSaved = projToServer();
                actionsSaved = actionsToServer();

                message = itemSaved;

                if (inspSaved.equals(yes) && (itemSaved.equals(yes)) && (MapSaved.equals(yes)) && (projSaved.equals(yes))&& (actionsSaved.equals(yes))){

                    message = "Inspections uploaded successfully";

                    //Disable the cleartablet function for August 2019 inspections.
                    //   clearTablet();


                }


                return message;
            }
        }

        syncInspections ue = new syncInspections();
        ue.execute();

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
        ArrayList<HashMap<String, String>> list = dbHandler.getAllProjects();
        //Get a list of all the images for the properties to inspect
        DBHandler dbHandlerphoto = new DBHandler(this, null, null, 1);
        ArrayList<HashMap<String, String>> photolist = dbHandler.getInspectedItemPhotos();

        String photo_name;
        root = Environment.getExternalStorageDirectory().toString();
        int i = 0;
        while (i < list.size()) {
            photo_name = list.get(i).get(MyConfig.TAG_PROJECT_PHOTO);
            if(!photo_name.equals("1")) uploadFileToS3(view, photo_name);
            i++;
        }

        i = 0;

        while (i  < photolist.size() ) {   //photolist.size()

            photo_name = photolist.get(i).get(MyConfig.TAG_IMAGE1);
            if(!photo_name.equals("photo1")) uploadFileToS3(view, photo_name);

            photo_name = photolist.get(i).get(MyConfig.TAG_IMAGE2);
            if(!photo_name.equals("photo2")) uploadFileToS3(view, photo_name);

            photo_name = photolist.get(i).get(MyConfig.TAG_IMAGE3);
            if(!photo_name.equals("photo3")) uploadFileToS3(view, photo_name);
            photo_name = photolist.get(i).get(MyConfig.TAG_IMAGE4);
            if(!photo_name.equals("photo4")) uploadFileToS3(view, photo_name);

            photo_name = photolist.get(i).get(MyConfig.TAG_IMAGE5);
            if(!photo_name.equals("photo5")) uploadFileToS3(view, photo_name);

            i++;
        }

    }


    public void uploadFileToS3(View view, final String photo_name) {



        if (photo_name.length() > 14) {
            dirName = photo_name.substring(6, 14);
            File Folder = new File(root + "/ESM_" + dirName + "/");
            if (Folder.exists()) {
                final  File F = new File(root + "/ESM_" + dirName + "/" + photo_name);
                if (F.exists()) {


                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {


                            try {
                                boolean exists = s3Client.doesObjectExist(bucket, photo_name);
                                if(exists){

                                    Toast.makeText(MainActivity.this, "Image "+photo_name+ " exists",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    //          s3Client.putObject(bucket,photo_name,F);


                                    //      TransferUtility transferUtility = TransferUtility.builder().defaultBucket(bucket).context(getApplicationContext()).s3Client(s3Client).build();

                                    TransferUtility transferUtility =
                                            TransferUtility.builder()
                                                    .context(getApplicationContext())
                                                    .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                                    .s3Client(s3Client)
                                                    .build();

                                    TransferObserver transferObserver = transferUtility.upload(
                                            bucket,    // The bucket to upload to
                                            photo_name,    // The key for the uploaded object
                                            F       // The file where the data to upload exists
                                    );


                                    transferObserverListener(transferObserver);

                                }

                            } catch (Exception e) {


                            }
                        }

                    });

                    thread.start();

                }


            } else {


            }

        }

    }


    /**
     * This method is used to Download the file to S3 by using transferUtility class
     *
     * @param view
     **/
    public void downloadFileFromS3(View view, String img_name) {



        if (img_name.length() > 14) {
            dirName = img_name.substring(6, 14);
            String info = img_name.substring(5, 9);
            File storageDirectory;
            File F;

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
                TransferObserver transferObserver = transferUtility.download(
                        bucket,     /* The bucket to download from */
                        img_name,    /* The key for the object to download */
                        F      /* The file to download the object to */

                );

                transferObserverListener(transferObserver);

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

}
