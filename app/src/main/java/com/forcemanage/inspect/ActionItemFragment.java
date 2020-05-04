package com.forcemanage.inspect;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ActionItemFragment extends Fragment implements View.OnClickListener {

    private InspectionActivity globalVariables;

    private static final String TAG = "ActionItem Fragment";
    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private static final int ACTIVITY_GET_FILE = 1;
    private static final int ACTIVITY_DRAW_FILE = 2;

    private TextView title;
    private TextView branch;
    private ImageView Img1;
    private ImageView Img2;
    private ImageView Img3;
    private ImageView Img4;
    private ImageView photoA;
    private ImageView photoB;
    private ImageView photoC;
    private ImageView photoD;
    private ImageView photoE;
    private ImageView photo_cam;
    private ImageView photo_draw;
    private ImageView photo_file;
    private EditText descriptionE;
    private EditText scopeE;
    private EditText performE;
    private EditText notesE;
    private boolean Edited = false;
    private String branchTitle = "Title";
    private String branchName = "Branch";
    private String desciption = "Desc";
    private String scope = "Desc";
    private String perform = "Desc";
    private String notes = "Desc";
    private String projectId;
    private String inspectionId;
    private int aId;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        globalVariables = (InspectionActivity) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            projectId = bundle.getString("projectID");
            inspectionId = bundle.getString("inspectionID");
            aId = bundle.getInt("aID");
            branchTitle = bundle.getString("branchHead");
            branchName = bundle.getString("branchLabel");
            desciption = bundle.getString("description");
            scope = bundle.getString("scope");
            perform = bundle.getString("perform");
            notes = bundle.getString("notes");
         }


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.action_fragment, container, false);
        //       btnInspection = (Button) view.findViewById(R.id.btnInspection);


        Log.d(TAG, "oncreateview: started");

        title = (TextView) view.findViewById(R.id.title);
        branch = (TextView) view.findViewById(R.id.level);
        notesE = (EditText) view.findViewById(R.id.note);

        photoA = (ImageView) view.findViewById(R.id.imageView);
        //photoA.setOnClickListener(this);

        photo_cam = (ImageView) view.findViewById(R.id.imageView_cam);
        photo_cam.setOnClickListener(this);

        photo_draw = (ImageView) view.findViewById(R.id.imageView_draw);
        photo_draw.setOnClickListener(this);

        photo_file = (ImageView) view.findViewById(R.id.imageView_file);
        photo_file.setOnClickListener(this);



         descriptionE = (EditText) view.findViewById(R.id.desc);
         scopeE = (EditText) view.findViewById(R.id.scope);
         performE = (EditText) view.findViewById(R.id.perform);
         notesE = (EditText) view.findViewById(R.id.notes);

  //       final CheckBox checkBox = (CheckBox) view.findViewById(R.id.sign_checkBox);



         //Fill in the edittext with saved data fro Bundle

        title.setText(branchTitle);
        branch.setText(branchName);
        descriptionE.setText(desciption);
        scopeE.setText(scope);
        performE.setText(perform);
        notesE.setText(notes);


       photo_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.photoframe = 1;
                globalVariables.mPhotoImageView = photoA;
                globalVariables.takeImageFromCamera(null);
                globalVariables.Edited = true;

            }
        });

        photo_draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!globalVariables.photo1.equals("")) {
                    String dirName = globalVariables.photo1.substring(6, 14);
                    String root = Environment.getExternalStorageDirectory().toString();
                    File photo_image = new File(root + "/ESM_" + dirName + "/" + globalVariables.photo1);


                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_VIEW);



                Uri data = FileProvider.getUriForFile(getActivity(),BuildConfig.APPLICATION_ID+".provider",photo_image);
                // Uri data = Uri.parse(photo_image.getAbsolutePath());

                galleryIntent.setDataAndType(data ,"image/*");
                startActivityForResult(galleryIntent.createChooser(galleryIntent, "Select Picture"),ACTIVITY_DRAW_FILE);
            }
                else  Toast.makeText(getActivity(), "Function requires a photograph",Toast.LENGTH_SHORT).show();

            }
        });


        photo_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.filephoto = 1;
                globalVariables.Edited = true;
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
                globalVariables.startActivityForResult(galleryIntent.createChooser(galleryIntent, "Select Picture"),globalVariables.ACTIVITY_GET_FILE);

            }
        });




        descriptionE.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) globalVariables.Edited = true;


            }
        });

        scopeE.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) globalVariables.Edited = true;

            }
        });

         performE.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus) globalVariables.Edited = true;

                }
            });

        notesE.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) globalVariables.Edited = true;

            }
        });


        for (int i = 0; i < 1; i++) {
            if (globalVariables.photos[i] == null) {
                globalVariables.photos[i] = "";
                //    cameraSnap = photos[i];
            }


            if ( globalVariables.photos[i].length() > 12) {
                String dirName =  globalVariables.photos[i].substring(6, 14);
                String root = Environment.getExternalStorageDirectory().toString();
                File Image = new File(root + "/ESM_" + dirName + "/" +  globalVariables.photos[i]);
                Bitmap myBitmap = BitmapFactory.decodeFile(Image.getAbsolutePath());

                switch (i) {
                    case 0:
                       // globalVariables.mPhotoImageView = (ImageView) view.findViewById(R.id.imageView);
                                photoA.setImageBitmap(myBitmap);

                        break;

                  } //End of switch
            } //End if there is an image file
            else {

                switch (i) {

                    case 0:
                        //   mPhotoImageView = (ImageView) findViewById(R.id.imageView);
                             photoA.setImageResource(R.drawable.ic_camera);
                        break;
                }//End of switch
            }//End of else

        }//End of loop

        return view;
    }




        @Override
    public void onClick(View v) {

    }


    public String stringdate(String date){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date d = null;
        try {
            d = sdf.parse(date);
        } catch (ParseException ex) {
            Log.v("Exception", ex.getLocalizedMessage());
        }
        sdf.applyPattern("dd MMM yyyy");
        date = sdf.format(d);

        return date;
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
        }
        return daytime;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if(globalVariables.Edited == true){

            DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);

            // String serviceDate = inspectionDate.getText().toString();
            // work out the next service date in three months time

            dbHandler.updateActionItem(Integer.parseInt(projectId), Integer.parseInt(inspectionId), aId, dayTime(1), descriptionE.getText().toString(),
                                      "", performE.getText().toString(), ""
                    , globalVariables.photos[0], scopeE.getText().toString(), "p", notesE.getText().toString());

            globalVariables.Edited = false;

        }


        //       endTime = dayTime(2);
        //       DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);
//        dbHandler.logInspection(projectId, inspectionId, startTime, endTime);


    }
}