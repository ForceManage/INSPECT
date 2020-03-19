package com.forcemanage.inspect;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class InspectionActivity extends Activity  implements OnVerseNameSelectionChangeListener,  View.OnClickListener  {

    DBHandler ESMdb;
    private Button buttonInsert;
    private Button buttonDelete;
    private Button btnViewReport;
    private Button buttonEdit;
    private String propertyId;
    private String jobId;
    private String locationId;
    private String sublocationId;
    private int propId;
    private int jId;
    private int aId = 1;
    private int rId = 1;
    private String seq = "cur";
    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private static final int ACTIVITY_GET_FILE = 1;
    private static final int ACTIVITY_DRAW_FILE = 2;
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
    private ImageView mPhotoImageView;
    private ImageView del_img2;
    private ImageView del_img3;
    private ImageView del_img4;
    private ImageView del_img5;
    private String photo1;
    private String photo2;
    private String photo3;
    private String photo4;
    private String photo5;
    private String[]  photos = new String[5];
    private String mImageFileLocation = "";
    private String fname;
    private String dirName;
    private View view;
    private Integer filephoto;
    private int photoframe;
    private File photo;
    private Spinner sprObservation;
    private Spinner sprRecommendation;
    private Spinner sprESM_category;
    private Spinner sprbuildcat;
    private Spinner sprasset;
    private Spinner sprContractor;
    private EditText Recommendation;
    private EditText Observation;
    private EditText ServiceCont;
    private TextView Buildcat;
    private TextView imageName1;
//    private TextView Asset;
    private String cameraSnap ;
    private EditText Note;
    private TextView ItemNumbers;
  //  private TextView location;
  //  private String location;
  //  private TextView SubLocation;
    private String ESMtxt;
    private String itemlocation;
    private String[] locationsArr;
    private String[] sublocationsArr;
    private String[] esm_asset;
    private String [] esm_cat;
    private String editing = "NO";
    private String esmcatId;
    private String esmsubcatId;
    private int inspArrayPosition;
    private  String[] assetIdlist;
    private  String [] locationIdlist;
    private String [] sublocationIdlist;
    private String [] recitemlist;
    private String [] observations;
    private String [] recommendations;
    private Integer zone;
    private ArrayAdapter<String> adapter5;
    private ArrayAdapter<MapViewNode> aAdapter;
    private MapListAdapter treeListAdapter;
    private List<MapViewData> listItems;
    public static  int[] level;
    public static  String[] locationdesc;
    public static   String[] id;
    public static  String[] parent;
    private boolean Edited = false;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ESMdb = new DBHandler(this, null, null, 1);
        setContentView(R.layout.activity_inspection);
        cameraSnap = "0";
        propertyId = getIntent().getExtras().getString("PROPERTY_ID");
        jobId = getIntent().getExtras().getString("JOB_ID");
        buttonInsert = (Button) findViewById(R.id.button2);
        buttonInsert.setOnClickListener(this);
        btnViewReport = (Button) findViewById(R.id.btnViewReport);
        btnViewReport.setOnClickListener(this);
        buttonDelete = (Button) findViewById(R.id.button4);
        buttonDelete.setOnClickListener(this);
        buttonEdit = (Button) findViewById(R.id.button_edit);
        buttonEdit.setOnClickListener(this);
        zone = 0;
        propId = Integer.parseInt(propertyId);
        jId = Integer.parseInt(jobId);

        DBHandler dbHandlerA = new DBHandler(this, null, null, 1);

        //Get number of items for the inspection
        ArrayList<HashMap<String, String>> itemNumbers = dbHandlerA.getInspectedItems(jobId, propertyId);
        ItemNumbers = (TextView) findViewById(R.id.RecordCount);
        ItemNumbers.setText("Property has "+Integer.toString(itemNumbers.size())+" items.");
        sublocationId = "0";
        inspArrayPosition = 0;
        getinspectionArray();
        displayInspectionItem(propId, jId, seq);




        photoA = (ImageView) findViewById(R.id.imageView);
        //photoA.setOnClickListener(this);
        photoB = (ImageView) findViewById(R.id.imageView2);
        photoB.setOnClickListener(this);
        photoC = (ImageView) findViewById(R.id.imageView3);
        photoC.setOnClickListener(this);
        photoD = (ImageView) findViewById(R.id.imageView4);
        photoD.setOnClickListener(this);
        photoE = (ImageView) findViewById(R.id.imageView5);
        photoE.setOnClickListener(this);

        photo_cam = (ImageView) findViewById(R.id.imageView_cam);
        photo_cam.setOnClickListener(this);

        photo_draw = (ImageView) findViewById(R.id.imageView_draw);
        photo_draw.setOnClickListener(this);

        photo_file = (ImageView) findViewById(R.id.imageView_file);
        photo_file.setOnClickListener(this);

        photo_file2 = (ImageView) findViewById(R.id.imageView2_file);
        photo_file2.setOnClickListener(this);

        photo_file3 = (ImageView) findViewById(R.id.imageView3_file);
        photo_file3.setOnClickListener(this);

        del_img2 = (ImageView) findViewById(R.id.imageView2_del);
        del_img2.setOnClickListener(this);

        del_img3 = (ImageView) findViewById(R.id.imageView3_del);
        del_img3.setOnClickListener(this);

        del_img4 = (ImageView) findViewById(R.id.imageView4_del);
        del_img4.setOnClickListener(this);

        del_img5 = (ImageView) findViewById(R.id.imageView5_del);
        del_img5.setOnClickListener(this);


        sprObservation = (Spinner) findViewById(R.id.spinnerObservation);
        sprRecommendation = (Spinner) findViewById(R.id.spinnerRecommendation);
        sprContractor = (Spinner) findViewById(R.id.spinnerContractor);

         Observation = (EditText) findViewById(R.id.Observation);
         Recommendation = (EditText) findViewById(R.id.Recommendation);
         ServiceCont = (EditText) findViewById(R.id.textServicedBy);
         final CheckBox checkBox = (CheckBox) findViewById(R.id.sign_checkBox);

         Note = (EditText) findViewById(R.id.note);

         getZonesArray();  //Load the zone spinner dropdown

        // An array containing list of observations


        observations = new String[]{"Observation"};
        recommendations = new String[]{"Recommendation"};


        ArrayList<HashMap<String, String>> zoneListB = dbHandlerA.getLocations(propertyId);

        listItems = new ArrayList<>();
        MapViewData listItem;

        level = new int[zoneListB.size()];
        locationdesc = new String[zoneListB.size()];
        id = new String[zoneListB.size()];
        parent = new String[zoneListB.size()];
        String Label = null;
        for (int i = 0; i < (zoneListB.size()); i++){

            int tagLevel = Integer.parseInt(zoneListB.get(i).get("level"));
            switch (tagLevel){

                case 0: Label = zoneListB.get(i).get(MyConfig.TAG_LOCATION_DESC); break;
                case 1: Label = zoneListB.get(i).get(MyConfig.TAG_ASSET_DESCRIPTION); break;
                case 2: Label = zoneListB.get(i).get(MyConfig.TAG_ASSET_DESCRIPTION); break;
                case 3: Label = zoneListB.get(i).get(MyConfig.TAG_ITEM_NAME); break;


            }

            listItem = new TreeViewData(
                    level[i] = Integer.parseInt(zoneListB.get(i).get("level")),
                    locationdesc[i] = Label,
                    id[i] = String.valueOf(i+1),
                    parent[i] = zoneListB.get(i).get("parent")

            );
            listItems.add(listItem);
        }



        ArrayList<TreeViewData> data = new ArrayList<TreeViewData>();

        for (int i = 0; i < zoneListB.size(); i++) {

            data.add(new TreeViewData(level[i], locationdesc[i], id[i], parent[i]));

        }

        GlobalVariables.dataList = data;
   //     TreeViewLists.LoadDisplayList();


   //     Note = (EditText) findViewById(R.id.note);
   //     Note.setText(Arrays.toString(level)+" "+Arrays.toString(locationdesc)+" "+Arrays.toString(id)+" "+Arrays.toString(parent));




        if (findViewById(R.id.fragment_container) != null){

            // However if we are being restored from a previous state, then we don't
            // need to do anything and should return or we could end up with overlapping Fragments
            if (savedInstanceState != null){
                return;
            }

            // Create an Instance of Fragment
            MapViewFragment treeFragment = new MapViewFragment();
            treeFragment.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, treeFragment)
                    .commit();

        }





        esm_cat = new String[]{
                "ESM",
                "Means of egress",
                "Fire fighting services and equipment",
                "Signage",
                "Lighting",
                "Building fire integrity"
        };

        final String[] buildcat = new String[]{
                "Building category",
                "Division 1, Subdivision 2 (Pre 1994 )",
                "Division 1, Subdivision 1 (1 July 1994 - 1 May 2004 )",
                "Division 1, Subdivision 1 (From May 2004.  Post 1994 )",
         };

        esm_asset = new String[]{
                "Measure",
                "Path of travel to exit",
                "Discharge from the exits (including paths of travel from open spaces to the public roads to which they are connected)",
                "Exits (including fire-isolated stairways and ramps, stair treads, balustrades and handrails associated with exits, and " +
                        "fire isolated passageways)",
                "Smoke lobbies to fire isolated exits",
                "Open access ramps or balconies for fire isolated exits",
                "Doors (other than smoke or fire doors) in a required exit, forming part of a required exit or in a path of travel to a " +
                        "required exit, and associated self-closing, automatic closing and latching mechanisms"
        };

        final String[] contractor = new String[]{
                "Contractor",
                "Building Essentials - 94695294",
                "FSS",
                "Northern Fire",
                "Link Fire",
        };



        // Array adapter to set data in Spinner Widget
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.my_spinner,observations);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.my_spinner, recommendations);
        ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(this, R.layout.my_spinner, contractor);
         // Setting the array adapter containing country list to the spinner widget
        sprObservation.setAdapter(adapter);
        sprRecommendation.setAdapter(adapter2);
        sprContractor.setAdapter(adapter6);



        ServiceCont.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

              //  Edited = true;

            }
        });

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 //   Edited = true;
                }
            });


            Observation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus) Edited = true;
                }
            });



        Recommendation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) Edited = true;
            }
        });

        Note.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) Edited = true;
            }
        });

         OnItemSelectedListener recommendationSelectedListener = new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> my_spinner, View container,
                                       int position, long id) {

            if (position !=0) {
                String ex_text = Recommendation.getText().toString();
                if (ex_text.equals(""))
                Recommendation.setText(recommendations[position]);
                else
                Recommendation.setText(ex_text +"\n"+recommendations[position]);
            }
            Edited = true;
            sprRecommendation.setSelection(0);


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }


        };

        OnItemSelectedListener observationSelectedListener = new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> my_spinner, View container,
                                       int position, long id) {

                if (position !=0)
                    Observation.setText(observations[position]);
                    Edited = true;

                     sprObservation.setSelection(0);


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }


        };




        OnItemSelectedListener contractorSelectedListener = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> my_spinner, View container,
                                       int position, long id) {
                if (position !=0)
                    ServiceCont.setText(contractor[position]);
                    Edited = true;



                sprContractor.setSelection(0);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        };


        // Setting ItemClick Handler for Spinner Widget
        sprRecommendation.setOnItemSelectedListener(recommendationSelectedListener);
        sprObservation.setOnItemSelectedListener(observationSelectedListener);
        sprContractor.setOnItemSelectedListener(contractorSelectedListener);




    }



    public void loadLocations()  {


        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        ArrayList<HashMap<String, String>> zoneListB = dbHandler.getLocations(propertyId);



        listItems = new ArrayList<>();
        MapViewData listItem ;
        level = new int[zoneListB.size()];
        locationdesc = new String[zoneListB.size()];
        id = new String[zoneListB.size()];
        parent = new String[zoneListB.size()];

        String Label = null;
        for (int i = 0; i < (zoneListB.size()); i++){

            int tagLevel = Integer.parseInt(zoneListB.get(i).get("level"));
            switch (tagLevel){

                case 0: Label = zoneListB.get(i).get(MyConfig.TAG_LOCATION_DESC); break;
                case 1: Label = zoneListB.get(i).get(MyConfig.TAG_ASSET_DESCRIPTION); break;
                case 2: Label = zoneListB.get(i).get(MyConfig.TAG_ASSET_DESCRIPTION); break;
                case 3: Label = zoneListB.get(i).get(MyConfig.TAG_ITEM_NAME); break;
                    
            }
                
              listItem = new MapViewData(
                    level[i] = Integer.parseInt(zoneListB.get(i).get("level")),
                    locationdesc[i] = Label,
                    id[i] = String.valueOf(i+1),
                    parent[i] = zoneListB.get(i).get("parent")

            );
            listItems.add(listItem);

        }


  //      DetailFragment detailFragment = (DetailFragment) getFragmentManager()
  //              .findFragmentById(R.id.detail_text);


       GlobalVariables.modified = true;
       OnSelectionChanged(0);



   
/*
        ArrayList<TreeViewData> data = new ArrayList<TreeViewData>();

        for (int i = 0; i < zoneListB.size(); i++) {
            data.add(new TreeViewData(level[i], locationdesc[i], id[i], parent[i]));
        }

        GlobalVariables.dataList = data;
      //  GlobalVariables.dataList = TreeViewLists.LoadInitialData();
        GlobalVariables.nodes = TreeViewLists.LoadInitialNodes(GlobalVariables.dataList);

        TreeViewLists.LoadDisplayList();


        DetailFragment detailFragment = (DetailFragment) getFragmentManager()
                .findFragmentById(R.id.detail_text);
        int DF = detailFragment.getId();

           TreeViewFragment newDetailFragment = new TreeViewFragment();
           Bundle args = new Bundle();


            args.putInt(DetailFragment.KEY_POSITION, 0);
        //args.putInt(DetailFragment.KEY_POSITION,GlobalVariables.pos);
            newDetailFragment.setArguments(args);



        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the backStack so the User can navigate back
          //  fragmentTransaction.remove(detailFragment);


            fragmentTransaction.replace(R.id.fragment_container, newDetailFragment);

            fragmentTransaction.addToBackStack(DetailFragment.KEY_POSITION);
            fragmentTransaction.commit();

            FragmentManager fm = getFragmentManager();
        //    fm.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);

            fm.popBackStack(DF,0);

       //     int count = fm.getBackStackEntryCount();
       //     while (count>1){
        //        fm.popBackStackImmediate();
        //       count --;
        //    }







        detailFragment.mCurrentPosition = 0;

       // detailFragment.setDetail(0);

        int rec = 0;
        for (int i = 0; i < locationdesc.length; i++)
            if (locationdesc[i] == detailFragment.Name)//detailFragment.Name
                rec = i;

        //  System.out.println(GlobalVariables.dataList.get(rec).getName());



        // getinspectionArray(); //Loads the inspection items for the selected zone

        //     location.setText("Zone "+Integer.toString(zone)+" - "+locationsArr[zone+1]);

        seq = "cur";
        inspArrayPosition = rec;
        saveInspectionItem(jId,aId,rId);
        displayInspectionItem(propId, jId, seq);
        if(esmcatId.equals("A")) getORArray(esmcatId,esmsubcatId);

*/
    }

@Override
    public void OnSelectionChanged(int treeNameIndex) {
        DetailFragment detailFragment = (DetailFragment) getFragmentManager()
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



            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();



            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the backStack so the User can navigate back
            fragmentTransaction.replace(R.id.fragment_container, newDetailFragment);
          //  fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }

        if (GlobalVariables.modified ==true){
            MapViewFragment newDetailFragment = new MapViewFragment();
            Bundle args = new Bundle();
            detailFragment.mCurrentPosition = -1;

            args.putInt(DetailFragment.KEY_POSITION, treeNameIndex);
            newDetailFragment.setArguments(args);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the backStack so the User can navigate back
            fragmentTransaction.replace(R.id.fragment_container, newDetailFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            FragmentManager fm = getFragmentManager();


            //fm.popBackStack(DF,0);
           if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStackImmediate();
            }

           // fm.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
            GlobalVariables.modified = false;
            OnSelectionChanged(0);

        }



        int rec = 0;
        for (int i = 0; i < locationdesc.length; i++)
            if (detailFragment.Name != null)
            if (locationdesc[i] == detailFragment.Name)//detailFragment.Name
                rec = i;

      //  System.out.println(GlobalVariables.dataList.get(rec).getName());



        // getinspectionArray(); //Loads the inspection items for the selected zone

        //     location.setText("Zone "+Integer.toString(zone)+" - "+locationsArr[zone+1]);

        seq = "cur";
        inspArrayPosition = rec;
        if(Edited == true) saveInspectionItem(jId,aId,rId);
        displayInspectionItem(propId, jId, seq);
        switch (esmcatId){
         case "A":
                getORArray(esmcatId,esmsubcatId);
            break;
         case "B":
                getORArray(esmcatId,esmsubcatId);
                break;
         case "C":
                getORArray(esmcatId,esmsubcatId);
                break;
         case "D":
                getORArray(esmcatId,esmsubcatId);
                break;
         case "E":
                getORArray(esmcatId,esmsubcatId);
                break;
    }
       // if(esmcatId.equals("A")) getORArray(esmcatId,esmsubcatId);
       // getORArray(esmcatId,esmsubcatId);
    }


    private void getORArray(String Cat_Table, String subCat){

        DBHandler dbHandler = new DBHandler(this, null, null, 1);


        ArrayList<HashMap<String, String>> OList = dbHandler.getORlist(Cat_Table,1,subCat);

         observations = new String[OList.size()+1];
         observations[0] = "Observation";
        for (int i = 0; i < OList.size(); i++) {
                observations[i+1] = OList.get(i).get(MyConfig.TAG_NOTE);

              }

        ArrayList<HashMap<String, String>> RList = dbHandler.getORlist(Cat_Table,2,subCat);
        recommendations = new String[RList.size()+1];
        recommendations[0] = "Recommendation";
        for (int i = 0; i < RList.size(); i++) {
             recommendations[i+1] = RList.get(i).get(MyConfig.TAG_NOTE);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.my_spinner, observations);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.my_spinner, recommendations);
        sprObservation.setAdapter(adapter);
        sprRecommendation.setAdapter(adapter2);
        }




    private void saveInspectionItem(int jId, int aId, int rId){
        DBHandler dbHandler = new DBHandler(this, null, null, 1);

       // String serviceDate = inspectionDate.getText().toString();
        // work out the next service date in three months time



        String nextServiceDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Integer year = Integer.parseInt(nextServiceDate.substring(0,4));
        String date;
        date = nextServiceDate.substring(4,6);


        switch (date){

            case "01": nextServiceDate = Integer.toString(year)+"0415";
                       break;
            case "02": nextServiceDate = Integer.toString(year)+"0515";
                break;
            case "03": nextServiceDate = Integer.toString(year)+"0615";
                break;
            case "04": nextServiceDate = Integer.toString(year)+"0715";
                break;
            case "05": nextServiceDate = Integer.toString(year)+"0815";
                break;
            case "06": nextServiceDate = Integer.toString(year)+"0915";
                break;
            case "07": nextServiceDate = Integer.toString(year)+"1015";
                break;
            case "08": nextServiceDate = Integer.toString(year)+"1115";
                break;
            case "09": nextServiceDate = Integer.toString(year)+"1215";
                break;
            case "010": nextServiceDate = Integer.toString(year+1)+"0115";
                break;
            case "011": nextServiceDate = Integer.toString(year+1)+"0215";
                break;
            case "012": nextServiceDate = Integer.toString(year+1)+"0315";
                break;

        }

        String inspectionObservation = Observation.getText().toString();
        String inspectionRecommendation = Recommendation.getText().toString();
        String ServiceLevel = "1";
        String ServicedBy = ServiceCont.getText().toString();
        String tag = cameraSnap;
        String Img1 = photo1;
        String Img2 = photo2;
        String Img3 = photo3;
        String Img4 = photo4;
        String Img5 = photo5;
        String ItemStatus = "i";
        String Notes = Note.getText().toString();


        dbHandler.updateInspection(jId, aId, rId, nextServiceDate, inspectionObservation, inspectionRecommendation, ServiceLevel, ServicedBy
                                   ,tag , Img1, Img2, Img3, Img4, Img5, ItemStatus, Notes);



        String status =  dbHandler.getStatus(jobId, propertyId);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date_  = Calendar.getInstance().getTime();

        dbHandler.updateStatus(jobId, status, dateFormat.format(date_));

        Edited = false;


    }


    public void deleteInspectionItem(){

        DBHandler dbHandler = new DBHandler(this, null, null, 1);


        if (!sublocationId.equals("0")) {

            if (rId >1 ){

                dbHandler.deleteInspectionItem(jId, aId, rId);
                getinspectionArray();
                inspArrayPosition = inspArrayPosition - 1;

                loadLocations();


                }

                else {

                    dbHandler.deleteInspectionItem(jId, aId, rId);
                    dbHandler.deleteAsset(propId, aId);
                    seq = "cur";

                    getinspectionArray();
                    if (inspArrayPosition > 0) inspArrayPosition = inspArrayPosition - 1;
                    displayInspectionItem(propId, jId, seq);

                    //Get number of items for the inspection

                   loadLocations();

                }





       //     if (inspArrayPosition > 0) inspArrayPosition = inspArrayPosition-1;
            }


               else {
                        dbHandler.deleteInspectionItem(jId, aId, rId);
                        dbHandler.deleteAsset(propId, aId);
                        dbHandler.deleteLocation(propId,locationId);
                   Toast.makeText(this, "Cannot delete a primary Zone", Toast.LENGTH_SHORT).show();
                }


     }



    private void getinspectionArray(){
        DBHandler dbHandler = new DBHandler(this, null, null, 1);

        ArrayList<HashMap<String, String>> inspectionitems = dbHandler.getinspectionitemlist(propId, jId, zone);

        assetIdlist = new String[inspectionitems.size()];
        locationIdlist = new String[inspectionitems.size()];
        sublocationIdlist = new String[inspectionitems.size()];
        recitemlist = new String[inspectionitems.size()];

        for (int i = 0; i < inspectionitems.size(); i++) {
            assetIdlist[i] = inspectionitems.get(i).get(MyConfig.TAG_ASSET_ID);
            locationIdlist[i] = inspectionitems.get(i).get(MyConfig.TAG_LOCATION_ID);
            sublocationIdlist[i] = inspectionitems.get(i).get(MyConfig.TAG_SUB_LOCATION_ID);
            recitemlist[i] = inspectionitems.get(i).get(MyConfig.TAG_RECOMMEND_NO);
    }
    }

    public void getZonesArray(){

        DBHandler dbHandler = new DBHandler(this, null, null, 1);


        ArrayList<HashMap<String, String>> zoneList = dbHandler.getZonesArray(propertyId);

        final String  zones[] = new String[zoneList.size()];

        for (int i = 0; i < zoneList.size(); i++) {
            zones[i] = zoneList.get(i).get(MyConfig.TAG_LOCATION_DESC);

        }
        locationsArr = Arrays.copyOf(zones, zones.length);


      //  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.my_spinner,zones );
      //  adapter.setDropDownViewResource(R.layout.my_spinner);


        try {

        //    sprZones.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("tag", "Exception found while listing " + e);
        }


    }


    private void addObservation(String item){

        DBHandler dbHandler = new DBHandler(this, null, null, 1);

        if (sublocationId.equals("0")){
            Toast.makeText(this, "Select the relevant ESM category",Toast.LENGTH_SHORT).show();
             }
           else {

               rId = dbHandler.addObservationItem(propertyId, jobId, aId, item);

               Note.setText("");
               Observation.setText("");
               Recommendation.setText("");
               photoA.setImageResource(R.mipmap.ic_camera);
               photoB.setImageResource(R.mipmap.ic_camera);
               photoC.setImageResource(R.mipmap.ic_camera);
               photoD.setImageResource(R.mipmap.ic_camera);
               photoE.setImageResource(R.mipmap.ic_camera);
               photo1 = "";
               photo2 = "";
               photo3 = "";
               photo4 = "";
               photo5 = "";


               seq = "cur";

               getinspectionArray();
               inspArrayPosition = inspArrayPosition + 1;


               loadLocations();
           }
    }




    private void addPrimLocation(String Description){

        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        locationId =  dbHandler.addPrimlocation(propertyId, Description);
        aId = dbHandler.addItem(propertyId,locationId,"0","Z","z",esmcatId);


        sublocationId = "0";
        rId = dbHandler.addObservationItem(propertyId,jobId,aId,"Primary Zone");


      //  aId = dbHandler.addItem(propertyId, jobId, aId, locationId, sublocationId); // adds new asset item to asset register table
      //  rId = dbHandler.addObservationItem(propertyId, jobId, aId,""); //add new item to the inspectionitem table under the new asset label

      //  editing = "YES";

       // location.setEnabled(true);
       // location.setFocusable(true);


     //   location.setText(Description);
      //  SubLocation.setText("");
    //    Asset.setText("");
        Note.setText("");
        Observation.setText("");
        Recommendation.setText("");
        photoA.setImageResource(R.mipmap.ic_camera);
        photoB.setImageResource(R.mipmap.ic_camera);
        photoC.setImageResource(R.mipmap.ic_camera);
        photoD.setImageResource(R.mipmap.ic_camera);
        photoE.setImageResource(R.mipmap.ic_camera);
        photo1="";
        photo2="";
        photo3="";
        photo4="";
        photo5="";

    //    inspArrayPosition = inspArrayPosition+1;
        getZonesArray();
        getinspectionArray();

        inspArrayPosition = inspArrayPosition + 1;


        loadLocations();
    }



    private void addsubLocation(String location){

        String esmcat = "A";

        switch (esmcatId){

            case "A":
            esmcat = esm_cat[1]; //esmcat is the title of the ESM category A- esmcat = Means of egress
            break;
            case "B":
                esmcat = esm_cat[2];
                break;
            case "C":
                esmcat = esm_cat[3];
                break;
            case "D":
                esmcat = esm_cat[4];
                break;
            case "E":
                esmcat = esm_cat[5];
                break;

        }


        EsmDBHandler dbHandler = new EsmDBHandler(this, null, null, 1);
        dbHandler.addESM(propertyId, jobId, locationId, sublocationId, location, esmcat, esmcatId, esmsubcatId);  //this is the ESM category

        ArrayList<HashMap<String, String>> sublocationList = dbHandler.getsubLocations(propertyId, locationId);

        final String  sublocations[] = new String[sublocationList.size()+1];
        sublocations[0] = "("+sublocationList.size()+")"+" Sub-locations";
        for (int i = 0; i < sublocationList.size(); i++) {
            sublocations[i+1] = sublocationList.get(i).get(MyConfig.TAG_LOCATION_DESC);
        }
      //  SubLocation.setText("Position: "+location);

        seq = "cur";


        sublocationsArr = Arrays.copyOf(sublocations, sublocations.length);
        getinspectionArray();
        inspArrayPosition = inspArrayPosition + 1;



        loadLocations();

      //  addItem(); // Adds another inspection item


    }

    private void editLocation(String location){

        if(sublocationId.equals('1'))
          Toast.makeText(this, "Cannot edit ESM catergory", Toast.LENGTH_SHORT).show();
        else {
            EsmDBHandler dbHandler = new EsmDBHandler(this, null, null, 1);
            dbHandler.updatelocations(propertyId, locationId, sublocationId, location, aId, rId);
            loadLocations();
            displayInspectionItem(propId, jId, "cur");
             }
     }


    private void displayInspectionItem(int propId, int jobId, String sequence){

            switch (sequence) {

                case "cur":

                    aId = Integer.parseInt(assetIdlist[inspArrayPosition]);
                    locationId = locationIdlist[inspArrayPosition];
                    sublocationId = sublocationIdlist[inspArrayPosition];
                    rId = Integer.parseInt(recitemlist[inspArrayPosition]);
                    break;
            }

            ItemNumbers.setText("Zone : "+locationId+", Sublocat : "+sublocationId+",  Asset id : "+ aId+",  rec id : "+ rId);
            EsmDBHandler dbHandler = new EsmDBHandler(this, null, null, 1);
            HashMap<String, String> list = dbHandler.getInspection(propId, jobId, aId,locationId, sublocationId, rId);

            esmcatId = list.get(MyConfig.TAG_CATEGORY_ID);
            esmsubcatId = list.get(MyConfig.TAG_SUB_CATEGORY_ID);

        //    TextView Asset = (TextView) findViewById(R.id.esmasset);
            TextView observationTextView = (TextView) findViewById(R.id.Observation);
            EditText servicedTextView = (EditText) findViewById(R.id.textServicedBy);
            EditText recommendationTextView = (EditText) findViewById(R.id.Recommendation);
        //    TextView  esm_cat = (TextView) findViewById(R.id.ESM_category);
        //    TextView location =(TextView) findViewById(R.id.Location);
         //   TextView sublocation =(TextView) findViewById(R.id.SubLocation);
            TextView notes = (TextView) findViewById(R.id.note);
            TextView  imageName1 = (TextView) findViewById(R.id.textView16);
            TextView  imageName2 = (TextView) findViewById(R.id.textView17);
            TextView  imageName3 = (TextView) findViewById(R.id.textView18);
            TextView  imageName4 = (TextView) findViewById(R.id.textView19);
            TextView  imageName5 = (TextView) findViewById(R.id.textView20);
            TextView ZONE = (TextView) findViewById(R.id.Zone);
            TextView Position = (TextView) findViewById(R.id.Position);
            TextView ESM = (TextView) findViewById(R.id.ESM);
            String sign = list.get(MyConfig.TAG_ITEM_STATUS);
            final CheckBox checkBox = (CheckBox)findViewById(R.id.sign_checkBox);
            if(sign.equals("i")  ){
               checkBox.setChecked(true);
            }else {
                checkBox.setChecked(false);
            }
            String assetText = list.get(MyConfig.TAG_ASSET_DESCRIPTION);
            String observationText = list.get(MyConfig.TAG_INSPECTION_OBSERVATION);
            String servicedByText = list.get(MyConfig.TAG_SERVICED_BY);
            String recommendationText = list.get(MyConfig.TAG_INSPECTION_RECOMMENDATION);
            String categoryText = list.get(MyConfig.TAG_CATEGORY_NAME);
            String subCategoryText = list.get(MyConfig.TAG_SUB_CATEGORY_NAME);
            String Notes = list.get(MyConfig.TAG_NOTES);
            if(rId == 1) itemlocation = list.get(MyConfig.TAG_LOCATION_DESC);
               else itemlocation = list.get(MyConfig.TAG_ITEM_NAME);
            String zoneText = dbHandler.zone(propId, locationId);
            ZONE.setText("Zone: "+ zoneText);
            Position.setText("Position: "+ itemlocation);
            ESM.setText("Safety Measure: "+ subCategoryText);
       //     String assetIdText = list.get(MyConfig.TAG_ASSET_ID);
      //     location.setText(zoneText);
      //      String recommendIdText = list.get(MyConfig.TAG_RECOMMEND_NO);
      //      String serviceDate = list.get(MyConfig.TAG_JOB_ACTUAL_DATE);

            photos [0] = list.get(MyConfig.TAG_IMAGE1);
            photos [1] = list.get(MyConfig.TAG_IMAGE2);
            photos [2] = list.get(MyConfig.TAG_IMAGE3);
            photos [3] = list.get(MyConfig.TAG_IMAGE4);
            photos [4] = list.get(MyConfig.TAG_IMAGE5);

      //      locationId = list.get(MyConfig.TAG_LOCATION_ID);
            String tag = list.get(MyConfig.TAG_REPORT_IMAGE);



          // EsmDBHandler dbHandler1 = new EsmDBHandler(this, null, null, 1);
            ArrayList<HashMap<String, String>> sublocationList = dbHandler.getsubLocations(propertyId, Integer.toString(aId) );

            final String  sublocations[] = new String[sublocationList.size()];
            for (int i = 0; i < sublocationList.size(); i++) {
                sublocations[i] = sublocationList.get(i).get(MyConfig.TAG_ASSET_DESCRIPTION);
            }


            sublocationsArr = Arrays.copyOf(sublocations, sublocations.length);





          //  esm_cat.setText("ESM :   "+categoryText);
          //  Asset.setText("Measure:   "+subCategoryText);
            observationTextView.setText(observationText);
            servicedTextView.setText(servicedByText);
            notes.setText(Notes);
            recommendationTextView.setText(recommendationText);


          //  String[] photos = {Img1, Img2, Img3, Img4, Img5};


            for(int i=0; i < 5; i++  ) {
                if (photos[i] == null) {
                    photos[i] = "";
                //    cameraSnap = photos[i];
                }


                if (photos[i].length() > 12) {
                    dirName = photos[i].substring(6, 14);
                    String root = Environment.getExternalStorageDirectory().toString();
                    File Image = new File(root + "/ESM_" + dirName + "/" + photos[i]);
                    Bitmap myBitmap = BitmapFactory.decodeFile(Image.getAbsolutePath());

                    switch (i) {
                        case 0:
                            mPhotoImageView = (ImageView) findViewById(R.id.imageView);
                            mPhotoImageView.setImageBitmap(myBitmap);
                            break;

                        case 1:
                            mPhotoImageView = (ImageView) findViewById(R.id.imageView2);
                            mPhotoImageView.setImageBitmap(myBitmap);
                            imageName2.setText(dirName);
                            break;

                        case 2:
                            mPhotoImageView = (ImageView) findViewById(R.id.imageView3);
                            mPhotoImageView.setImageBitmap(myBitmap);
                            imageName3.setText(dirName);
                            break;

                        case 3:
                            mPhotoImageView = (ImageView) findViewById(R.id.imageView4);
                            mPhotoImageView.setImageBitmap(myBitmap);
                            imageName4.setText(dirName);
                            break;

                        case 4:
                            mPhotoImageView = (ImageView) findViewById(R.id.imageView5);
                            mPhotoImageView.setImageBitmap(myBitmap);
                            imageName5.setText(dirName);
                            break;


                    } //End of switch
                } //End if there is an image file
                    else{

                        switch (i) {

                            case 0:
                                mPhotoImageView = (ImageView) findViewById(R.id.imageView);
                                mPhotoImageView.setImageResource(R.mipmap.ic_camera);
                                break;

                            case 1:
                                mPhotoImageView = (ImageView) findViewById(R.id.imageView2);
                                mPhotoImageView.setImageResource(R.mipmap.ic_camera);
                                imageName2.setText("No Photo Record");
                                break;

                            case 2:
                                mPhotoImageView = (ImageView) findViewById(R.id.imageView3);
                                mPhotoImageView.setImageResource(R.mipmap.ic_camera);
                                imageName3.setText("No Photo Record");
                                break;

                            case 3:
                                mPhotoImageView = (ImageView) findViewById(R.id.imageView4);
                                mPhotoImageView.setImageResource(R.mipmap.ic_camera);
                                imageName4.setText("No Photo Record");
                                break;

                            case 4:
                                mPhotoImageView = (ImageView) findViewById(R.id.imageView5);
                                mPhotoImageView.setImageResource(R.mipmap.ic_camera);
                                imageName5.setText("No Photo Record");
                                break;


                        }//End of switch
                    }//End of else

        }//End of loop

            photo1 = photos[0];
            photo2 = photos[1];
            photo3 = photos[2];
            photo4 = photos[3];
            photo5 = photos[4];

            if (tag.equals("1")) {
                imageName1.setText("UPDATED");
            } else {
                imageName1.setText("REQUIRES UPDATING");
            }

            cameraSnap = tag;




    }

    public void onClick(View v) {


        if (v == buttonEdit){



            // setup the alert builder

            AlertDialog.Builder builder = new AlertDialog.Builder(InspectionActivity.this);
            builder.setTitle("Choose an action");
            // add a list
            String[] actions = {"Change the Zone or inspection item description",
                    "",
                    "",
                    ""};
            builder.setItems(actions, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                          case 0:{

                           LayoutInflater layoutInflater = LayoutInflater.from(InspectionActivity.this);
                            View promptView = layoutInflater.inflate(R.layout.add_location, null);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InspectionActivity.this);
                            alertDialogBuilder.setView(promptView);
                            TextView locationText = (TextView) promptView.findViewById(R.id.textView);

                            locationText.setText("Current description: " + itemlocation);
                            final EditText LocationText = (EditText) promptView.findViewById(R.id.locationtext);
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


        if (v == buttonInsert){

            // setup the alert builder
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose an action");
            // add a list
            String[] actions = {"Add safety measure inspection item.",
                                "Add ESM category for the current Zone",
                                "Create a new zone for the property.",
                                "Cancel Add/Create "};

            builder.setItems(actions, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {

                        case 0: {
                            if(sublocationId.equals("2") == true){

                                LayoutInflater layoutInflater = LayoutInflater.from(InspectionActivity.this);
                                View promptView = layoutInflater.inflate(R.layout.add_location, null);
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InspectionActivity.this);
                                alertDialogBuilder.setView(promptView);
                                TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                locationText.setText("Safety measure : " + sublocationsArr[0]);//Integer.parseInt(locationId)
                                final EditText sublocationText = (EditText) promptView.findViewById(R.id.locationtext);
                                // setup a dialog window
                                alertDialogBuilder.setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                addObservation(sublocationText.getText().toString());


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
                            else {
                                Toast.makeText(InspectionActivity.this, "Select the relevant Safety Measure", Toast.LENGTH_SHORT).show();
                            }
                            break;

                        }


                        case 1: {
                            LayoutInflater layoutInflater = LayoutInflater.from(InspectionActivity.this);
                            View promptView = layoutInflater.inflate(R.layout.add_esm, null);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InspectionActivity.this);
                            alertDialogBuilder.setView(promptView);
                            TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                            locationText.setText("Zone in property: " + locationsArr[Integer.parseInt(locationId)-1]);//location.getText().toString());
                            sprESM_category = (Spinner) promptView.findViewById(R.id.esm);


                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(InspectionActivity.this, R.layout.my_spinner, esm_cat );
                            adapter.setDropDownViewResource(R.layout.my_spinner);
                            sprESM_category.setAdapter(adapter);

                            adapter5 = new ArrayAdapter<String>(InspectionActivity.this, R.layout.my_spinner, esm_asset );
                            adapter5.setDropDownViewResource(R.layout.my_spinner);
                            sprasset = (Spinner) promptView.findViewById(R.id.measure);

                            final TextView esmtxt = (TextView) promptView.findViewById(R.id.textView3);

                            OnItemSelectedListener ESM_catSelectedListener = new OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> my_spinner, View container,
                                                           int position, long id) {


                                    switch (position) {
                                        case (1):
                                            esmcatId = "A";
                                            esm_asset = new String[]{
                                                    "Measure",
                                                    "Path of travel to exit",
                                                    "Discharge from the exits (including paths of travel from open spaces to the public roads to which they are connected)",
                                                    "Exits (including fire-isolated stairways and ramps, stair treads, balustrades and handrails associated with exits, and " +
                                                            "fire isolated passageways)",
                                                    "Smoke lobbies to fire isolated exits",
                                                    "Open access ramps or balconies for fire isolated exits",
                                                    "Doors (other than smoke or fire doors) in a required exit, forming part of a required exit or in a path of travel to a " +
                                                            "required exit, and associated self-closing, automatic closing and latching mechanisms"
                                            };
                                            adapter5 = new ArrayAdapter<String>(InspectionActivity.this, R.layout.my_spinner, esm_asset);
                                            sprasset.setAdapter(adapter5);

                                            break;
                                        case (2):
                                            esmcatId = "B";

                                            esm_asset = new String[]{
                                                    "Measure",
                                                    "Fire hydrant system (including on site pump set and fire service booster connection)",
                                                    "Fire hose reel system",
                                                    "Sprinkler system",
                                                    "Portable fire extinguishers",
                                                    "Fire control centres (or rooms)"

                                            };
                                            adapter5 = new ArrayAdapter<String>(InspectionActivity.this, R.layout.my_spinner, esm_asset);
                                            sprasset.setAdapter(adapter5);

                                            break;
                                        case (3):
                                            esmcatId = "C";
                                            esm_asset = new String[]{
                                                    "Measure",
                                                    "Exit signs (including direction signs)",
                                                    "Signs warning against the use of lifts in the event of fire",
                                                    "Warning signs on sliding fire doors and doors to non required stairways, ramps and escalators",
                                                    "Signs, intercommunication systems, or alarm systems on doors of fire isolated exits stating that re entry to a storey is available",
                                                    "Signs alerting persons that the operation of doors must not be impaired",
                                                    "Signs required on doors, in alpine areas, alerting people that they open inwards",
                                                    "Fire order notices required in alpine areas"

                                            };
                                            adapter5 = new ArrayAdapter<String>(InspectionActivity.this, R.layout.my_spinner, esm_asset);
                                            sprasset.setAdapter(adapter5);
                                            break;
                                        case (4):
                                            esmcatId = "D";
                                            esm_asset = new String[]{
                                                    "Measure",
                                                    "Emergency Lighting"

                                            };
                                            adapter5 = new ArrayAdapter<String>(InspectionActivity.this, R.layout.my_spinner, esm_asset);
                                            sprasset.setAdapter(adapter5);
                                            break;
                                        case (5):
                                            esmcatId = "E";

                                            esm_asset = new String[]{
                                                    "Measure",
                                                    "Building elements required to satisfy prescribed fire resistance levels",
                                                    "Materials and assemblies required to have fire hazard properties",
                                                    "Elements required to be non combustible, provide fire protection, compartmentation or separation",
                                                    "Wall-wetting sprinklers (including doors and windows required in conjunction with wall wetting sprinklers)",
                                                    "Fire doors (including sliding fire doors and their associated warning systems) and associated self closing, automatic closing and latching mechanisms",
                                                    "Fire windows (including windows that are automatic or permanently fixed in the closed position)",
                                                    "Fire shutters",
                                                    "Solid core doors and associated self closing, automatic closing and latching mechanisms",
                                                    "Fire-protection at service penetrations through elements required to be fire resisting with respect to integrity or insulation, or to have a resistance to the incipient spread of fire",
                                                    "Fire protection associated with construction joints, spaces and the like in and between building elements required to be fire-resisting with respect to integrity and insulation",
                                                    "Smoke doors and associated self closing, automatic closing and latching mechanisms",
                                                    "Proscenium walls (including proscenium curtains)"
                                            };
                                            adapter5 = new ArrayAdapter<String>(InspectionActivity.this, R.layout.my_spinner, esm_asset);
                                            sprasset.setAdapter(adapter5);




                                            break;

                                        case (6):
                                            esmcatId = "F";
                                            break;
                                        case (7):
                                            esmcatId = "G";
                                            break;
                                    }

                                }



                            @Override
                            public void onNothingSelected(AdapterView<?> arg0) {
                                // TODO Auto-generated method stub
                            }

                            // setup a dialog window

                        };

                            sprESM_category.setSelection(0);


                        OnItemSelectedListener assettSelectedListener = new OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> my_spinner, View container,
                                                           int position, long id) {


                                    if (position !=0) {
                                    //    Asset =("Measure: "+ esm_asset[position]);
                                   //     editing = "YES";
                                          switch (position) {


                                            case (1):
                                                esmsubcatId = "a";
                                                if(esmcatId.equals("A")) ESMtxt = "Paths of travel to exits";
                                                if(esmcatId.equals("B")) ESMtxt = "Fire hydrant system (including on site pump";
                                                if(esmcatId.equals("C")) ESMtxt = "Exit signs (including direction signs)";
                                                if(esmcatId.equals("D")) ESMtxt = "Emergency Lighting";
                                                if(esmcatId.equals("E")) ESMtxt = "Building elements required to satisfy ";
                                                if(esmcatId.equals("F")) ESMtxt = "Paths of travel to exits";
                                                if(esmcatId.equals("G")) ESMtxt = "Paths of travel to exits";
                                                break;
                                            case (2):
                                                esmsubcatId = "b";
                                                if(esmcatId.equals("A")) ESMtxt = "Discharge from exits to roadway";
                                                if(esmcatId.equals("B")) ESMtxt = "Fire hose reel system";
                                                if(esmcatId.equals("C")) ESMtxt = "Signs warning against the use of lifts";
                                                if(esmcatId.equals("D")) ESMtxt = "Paths of travel to exits";
                                                if(esmcatId.equals("E")) ESMtxt = "Materials and assemblies required to have";
                                                if(esmcatId.equals("F")) ESMtxt = "Paths of travel to exits";
                                                if(esmcatId.equals("G")) ESMtxt = "Paths of travel to exits";
                                                break;
                                            case (3):
                                                esmsubcatId = "c";
                                                if(esmcatId.equals("A")) ESMtxt = "Exits-stairwells/passageways - assoc. items";
                                                if(esmcatId.equals("B")) ESMtxt = "Sprinkler system";
                                                if(esmcatId.equals("C")) ESMtxt = "Warning signs on sliding fire doors and doors";
                                                if(esmcatId.equals("D")) ESMtxt = "Paths of travel to exits";
                                                if(esmcatId.equals("E")) ESMtxt = "Elements required to be non combustible,";
                                                if(esmcatId.equals("F")) ESMtxt = "Paths of travel to exits";
                                                if(esmcatId.equals("G")) ESMtxt = "Paths of travel to exits";
                                                break;
                                            case (4):

                                                esmsubcatId = "d";
                                                if(esmcatId.equals("A")) ESMtxt = "Smoke lobbies to fire isolated exits";
                                                if(esmcatId.equals("B")) ESMtxt = "Portable fire extinguishers";
                                                if(esmcatId.equals("C")) ESMtxt = "Signs, intercommunication systems, or alarm ";
                                                if(esmcatId.equals("D")) ESMtxt = "Paths of travel to exits";
                                                if(esmcatId.equals("E")) ESMtxt = "Wall-wetting sprinklers (including doors ";
                                                if(esmcatId.equals("F")) ESMtxt = "Paths of travel to exits";
                                                if(esmcatId.equals("G")) ESMtxt = "Paths of travel to exits";
                                                break;
                                            case (5):
                                                esmsubcatId = "e";
                                                if(esmcatId.equals("A")) ESMtxt = "Open access ramps or balconies for fire ";
                                                if(esmcatId.equals("B")) ESMtxt = "Fire control centres (or rooms)";
                                                if(esmcatId.equals("C")) ESMtxt = "Signs alerting persons that the operation ";
                                                if(esmcatId.equals("D")) ESMtxt = "Paths of travel to exits";
                                                if(esmcatId.equals("E")) ESMtxt = "Fire doors (including sliding fire doors and ";
                                                if(esmcatId.equals("F")) ESMtxt = "Paths of travel to exits";
                                                if(esmcatId.equals("G")) ESMtxt = "Paths of travel to exits";
                                                break;
                                            case (6):
                                                esmsubcatId = "f";
                                                if(esmcatId.equals("A")) ESMtxt = "Doors (other than smoke or fire doors) in ";
                                                if(esmcatId.equals("B")) ESMtxt = "Paths of travel to exits";
                                                if(esmcatId.equals("C")) ESMtxt = "Signs required on doors, in alpine areas, ";
                                                if(esmcatId.equals("D")) ESMtxt = "Paths of travel to exits";
                                                if(esmcatId.equals("E")) ESMtxt = "Fire windows (including windows that are auto";
                                                if(esmcatId.equals("F")) ESMtxt = "Paths of travel to exits";
                                                if(esmcatId.equals("G")) ESMtxt = "Paths of travel to exits";
                                                break;
                                            case (7):
                                                esmsubcatId = "g";
                                                if(esmcatId.equals("A")) ESMtxt = "Smoke lobbies to fire isolated exits";
                                                if(esmcatId.equals("B")) ESMtxt = "Paths of travel to exits";
                                                if(esmcatId.equals("C")) ESMtxt = "Fire order notices required in alpine areas";
                                                if(esmcatId.equals("D")) ESMtxt = "Paths of travel to exits";
                                                if(esmcatId.equals("E")) ESMtxt = "Fire Shutters";
                                                if(esmcatId.equals("F")) ESMtxt = "Paths of travel to exits";
                                                if(esmcatId.equals("G")) ESMtxt = "Paths of travel to exits";
                                                break;
                                            case (8):
                                                esmsubcatId = "h";
                                                if(esmcatId.equals("B")) ESMtxt = "Paths of travel to exits";
                                                if(esmcatId.equals("C")) ESMtxt = "Paths of travel to exits";
                                                if(esmcatId.equals("D")) ESMtxt = "Paths of travel to exits";
                                                if(esmcatId.equals("E")) ESMtxt = "Solid core doors and associated self closing,";
                                                if(esmcatId.equals("F")) ESMtxt = "Paths of travel to exits";
                                                if(esmcatId.equals("G")) ESMtxt = "Paths of travel to exits";
                                                break;
                                            case (9):
                                                esmsubcatId = "i";
                                                if(esmcatId.equals("E")) ESMtxt = "Fire-protection at service penetrations";

                                                break;
                                            case (10):
                                                esmsubcatId = "j";
                                                if(esmcatId.equals("E")) ESMtxt = "Fire protection associated with construc ";
                                                break;
                                            case (11):
                                                esmsubcatId = "k";
                                                if(esmcatId.equals("E")) ESMtxt = "Smoke doors and associated self closing, ";
                                                break;
                                              case (12):
                                                esmsubcatId = "l";
                                                if(esmcatId.equals("E")) ESMtxt ="Proscenium walls (including proscenium";


                                        }


                                          esmtxt.setText("Description:  "+ESMtxt);

                                     //   sprasset.setSelection(0);
                                     //   getORArray(esmcatId,esmsubcatId);

                                    }


                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {
                                    // TODO Auto-generated method stub
                                }

                            };




                            sprasset.setOnItemSelectedListener(assettSelectedListener);
                            sprESM_category.setOnItemSelectedListener(ESM_catSelectedListener);

                                            alertDialogBuilder.setCancelable(false)
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            addsubLocation(ESMtxt);







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

                        case 2:{

                            LayoutInflater layoutInflater = LayoutInflater.from(InspectionActivity.this);
                            View promptView = layoutInflater.inflate(R.layout.add_location, null);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InspectionActivity.this);
                            alertDialogBuilder.setView(promptView);
                            TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                            locationText.setText("Eg: DRIVEWAY ENTRANCE GATE/ENTRANCE DOOR TO LOBBY, ETC....");
                            final EditText locationDesc = (EditText) promptView.findViewById(R.id.locationtext);
                            // setup a dialog window
                            alertDialogBuilder.setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            addPrimLocation(locationDesc.getText().toString());


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


                    }
                }
            });

            AlertDialog dialog = builder.create();

            dialog.show();
        }

        if (v == buttonDelete){



            // setup the alert builder

            AlertDialog.Builder builder = new AlertDialog.Builder(InspectionActivity.this);
            builder.setTitle("Choose an action");
            // add a list
            String[] actions = {"Delete the current inspection item.",
                    "Delete this ESM category.",
                    "Delete the current Zone.",
                    "Cancel this operation."};
            builder.setItems(actions, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0: {deleteInspectionItem(); break;}
                        case 1: {deleteInspectionItem(); break;} //
                        case 2:{

                            LayoutInflater layoutInflater = LayoutInflater.from(InspectionActivity.this);
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

                            break;
                        }
                        case 3: //
                        break;
                    }
                 //end of case 0
                }
            });
            // create and show the alert dialog
            AlertDialog dialog = builder.create();

            dialog.show();



        }



        if (v == photo_cam) {
            photoframe = 1;
            mPhotoImageView = (ImageView) findViewById(R.id.imageView);
            takeImageFromCamera(null);
        }

        if (v == photo_draw) {

            dirName = photo1.substring(6, 14);
            String root = Environment.getExternalStorageDirectory().toString();
            File photo_image = new File(root + "/ESM_" + dirName + "/"+photo1);


            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_VIEW);



            Uri data = FileProvider.getUriForFile(InspectionActivity.this,BuildConfig.APPLICATION_ID+".provider",photo_image);
           // Uri data = Uri.parse(photo_image.getAbsolutePath());

            galleryIntent.setDataAndType(data ,"image/*");
            startActivityForResult(galleryIntent.createChooser(galleryIntent, "Select Picture"),ACTIVITY_DRAW_FILE);

        }

        if (v == del_img2) {

         //   EsmDBHandler dbHandler = new EsmDBHandler(this, null, null, 1);
         //   dbHandler.deletePhoto(jId,aId, rId, 2);

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked
                            photo2 = "";
                            photoB.setImageResource(R.mipmap.ic_camera);

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(InspectionActivity.this);
            builder.setMessage("Are you sure?").setPositiveButton("Yes",dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

         }

        if (v == del_img3) {

            //   EsmDBHandler dbHandler = new EsmDBHandler(this, null, null, 1);
            //   dbHandler.deletePhoto(jId,aId, rId, 2);

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked
                            photo3 = "";
                            photoC.setImageResource(R.mipmap.ic_camera);

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(InspectionActivity.this);
            builder.setMessage("Are you sure?").setPositiveButton("Yes",dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        }

        if (v == del_img4) {

            //   EsmDBHandler dbHandler = new EsmDBHandler(this, null, null, 1);
            //   dbHandler.deletePhoto(jId,aId, rId, 2);

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked
                            photo4 = "";
                            photoD.setImageResource(R.mipmap.ic_camera);

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(InspectionActivity.this);
            builder.setMessage("Are you sure?").setPositiveButton("Yes",dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        }

        if (v == del_img5) {

            //   EsmDBHandler dbHandler = new EsmDBHandler(this, null, null, 1);
            //   dbHandler.deletePhoto(jId,aId, rId, 2);

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked
                            photo5 = "";
                            photoE.setImageResource(R.mipmap.ic_camera);

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(InspectionActivity.this);
            builder.setMessage("Are you sure?").setPositiveButton("Yes",dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        }


        if (v == photo_file) {
            filephoto = 1;
           Intent galleryIntent = new Intent();
            // galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
           // galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            // galleryIntent.setAction(Intent.ACTION_VIEW);
            galleryIntent.setAction(Intent.ACTION_PICK);
            galleryIntent.setType("image/*");
         //   String[] mimetypes = {"image/*"};
         //   galleryIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes); //            setType("image/*");
        //     dirName = photos[0].substring(6, 14);
        //     String root = Environment.getExternalStorageDirectory().toString();
         //    File Image = new File(root + "/ESM_" + dirName + "/" );//+ photos[0]


        //    Uri data = FileProvider.getUriForFile(InspectionActivity.this,BuildConfig.APPLICATION_ID+".provider",Image);
        //    galleryIntent.setDataAndType(data,"image/*");
        //    String[] mimeTypes = {"image/jpeg", "image/png"};
           // galleryIntent.putExtra(galleryIntent.EXTRA_MIME_TYPES,mimeTypes);

            //startActivityForResult(galleryIntent, ACTIVITY_GET_FILE);
            startActivityForResult(galleryIntent.createChooser(galleryIntent, "Select Picture"),ACTIVITY_GET_FILE);

        }

        if (v == photo_file2) {
            filephoto = 2;
            Intent galleryIntent = new Intent();
            // galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
            // galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            // galleryIntent.setAction(Intent.ACTION_VIEW);
            galleryIntent.setAction(Intent.ACTION_PICK );
            galleryIntent.setType("image/*");
            String[] mimeTypes = {"image/jpeg", "image/png"};
            galleryIntent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
            startActivityForResult(galleryIntent,ACTIVITY_GET_FILE);

          //  startActivityForResult(galleryIntent.createChooser(galleryIntent, "Select Picture"),1);

        }

        if (v == photo_file3) {
            filephoto = 3;
            Intent galleryIntent = new Intent();
           //  galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
           //  galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            // galleryIntent.setAction(Intent.ACTION_VIEW);
            galleryIntent.setAction(Intent.ACTION_PICK );
            galleryIntent.setType("image/*");

            //startActivityForResult(galleryIntent, ACTIVITY_GET_FILE);
            startActivityForResult(galleryIntent.createChooser(galleryIntent, "Select Picture"),1);

        }

        if (v == photoB) {
            photoframe = 2;
            mPhotoImageView = (ImageView) findViewById(R.id.imageView2);
            takeImageFromCamera(null);
        }

        if (v == photoC) {
            photoframe = 3;
            mPhotoImageView = (ImageView) findViewById(R.id.imageView3);
            takeImageFromCamera(null);
        }
        if (v == photoD) {
            photoframe = 4;
            mPhotoImageView = (ImageView) findViewById(R.id.imageView4);
            takeImageFromCamera(null);
        }
        if (v == photoE) {
            photoframe = 5;
            mPhotoImageView = (ImageView) findViewById(R.id.imageView5);
            takeImageFromCamera(null);
        }
        if (v == btnViewReport) {
            saveInspectionItem(jId, aId, rId);

            Intent intent = new Intent(this, ViewReportActivity.class);
            intent.putExtra("jobId",jobId);
            intent.putExtra("propId", propertyId);
            startActivity(intent);
        //    finish();
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


        if (requestCode == ACTIVITY_DRAW_FILE && resultCode == 0){

            onClick(photo_file);

        }


        if (requestCode == ACTIVITY_GET_FILE && resultCode == RESULT_OK){

            Uri selectedImage = data.getData();

   /*          Cursor returnCursor = getContentResolver().query(selectedImage, null, null, null, null);

           int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            String name = returnCursor.getString(nameIndex);
            returnCursor.close();

            TextView f_name = (TextView) findViewById(R.id.textView16);
            f_name.setText(name);

*/

            String[] filePathColumn = { MediaStore.Images.Media.DATA };
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

            // photoA.setImageURI(selectedImage);

            switch (filephoto) {
                case 1:
                    photoA.setImageURI(selectedImage);
                    break;

                case 2:
                    photoB.setImageURI(selectedImage);
                    break;

                case 3:
                    photoC.setImageURI(selectedImage);
                    break;

            }



            File from = new File(path);


            fname = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            dirName = new SimpleDateFormat("yyyyMMdd").format(new Date());
            fname = propertyId+"_"+fname;

            String root = Environment.getExternalStorageDirectory().toString();
            String SD = root + "/ESM_"+dirName+"/";
            File storageDirectory = new File(root + "/ESM_"+dirName+"/");
            // Toast.makeText(this, "should have made directory",Toast.LENGTH_SHORT).show();
            // Toast.makeText(this, "string root name = "+root ,Toast.LENGTH_SHORT).show();
            if (!storageDirectory.exists()){storageDirectory.mkdirs();}

            File to = new File(SD+fname+".jpg");



           // from.renameTo(to);  This deletes the source file from

            try {
                copy(from,to);
            } catch (IOException e) {
                e.printStackTrace();
            }


            switch (filephoto) {
                case 1:
                    //           photoA.setImageURI(selectedImage);
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

            }


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
                TextView imageName1 = (TextView) findViewById(R.id.textView16);
                imageName1.setText("UPDATED");
                saveInspectionItem(jId,aId,rId);
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(photo)));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if (requestCode == ACTIVITY_START_CAMERA_APP) {
            photo.delete();
            if(cameraSnap != "1") {
                cameraSnap = "0";
                Edited = true;
            }

            switch (photoframe) {
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

        fname = new SimpleDateFormat("yyyyMMddHH").format(new Date());
        dirName = new SimpleDateFormat("yyyyMMdd").format(new Date());
        fname = propertyId+"_"+fname;
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

        if(Edited == true )saveInspectionItem(jId, aId, rId);




    }


}
