package com.forcemanage.inspect;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

public class InspectionFragment extends Fragment implements View.OnClickListener {

    private InspectionActivity globalVariables;

    private static final String TAG = "Inspection Fragment";
    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private static final int ACTIVITY_GET_FILE = 1;
    private static final int ACTIVITY_DRAW_FILE = 2;

    private TextView title;
    private TextView branch;
    private EditText notes;
    private ImageView cam1;
    private ImageView cam2;
    private ImageView cam3;
    private ImageView cam4;
    private ImageView cam5;
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
    private ImageView photo_file4;
    private ImageView photo_file5;
    private ImageView del_img2;
    private ImageView del_img3;
    private ImageView del_img4;
    private ImageView del_img5;
    private Spinner sprTitle;
    private Spinner sprbuildcat;
    private Spinner sprasset;
    private Spinner sprContractor;
    private EditText RelevantInfo;
    private EditText Overview;
    private EditText ServiceCont;
    private EditText com1Text;
    private EditText com2Text;
    private EditText com3Text;
    private EditText com4Text;
    private EditText com5Text;
    private TextView Buildcat;
    private TextView imageName1;
    private Button reportBtn;
    private boolean Edited = false;
    private String com1 ="Commentary :";
    private String com2 = "";
    private String com3 = "";
    private String com4 = "";
    private String com5 = "";
    private String branchTitle = "Title";
    private String branchName = "Branch";
    private String aProvider = "Trade";
    private String overView;
    private String relevantInfo;
    private String Notes;
    private String inspectionDate;
    private String startTime;
    private String endTime;
    private String projectId;
    private String inspectionId;
    private int aId;
    private int projId;
    private int iId;


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
            aProvider = bundle.getString("aprovider");
            overView = bundle.getString("overview");
            inspectionDate = bundle.getString("date");
            relevantInfo = bundle.getString("relevantInfo");
            Notes = bundle.getString("notes");
            com1 = bundle.getString("com1");
            com2 = bundle.getString("com2");
            com3 = bundle.getString("com3");
            com4 = bundle.getString("com4");
            com5 = bundle.getString("com5");

        }

     //   inspectionDate =  inspectionDate.substring(6,8)+"-"+inspectionDate.substring(4,6)+"-"+inspectionDate.substring(0,4);

           inspectionDate = dayTime(1);
           startTime = dayTime(2);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.inspection_fragment, container, false);
        //       btnInspection = (Button) view.findViewById(R.id.btnInspection);




        Log.d(TAG, "oncreateview: started");

        projId = Integer.parseInt(projectId);
        iId= Integer.parseInt(inspectionId);

        title = (TextView) view.findViewById(R.id.branchTitle);
        branch = (TextView) view.findViewById(R.id.branchName);
        notes = (EditText) view.findViewById(R.id.note);

        cam1 = (ImageView) view.findViewById(R.id.cameraClick1);
        cam1.setOnClickListener(this);
        cam2 = (ImageView) view.findViewById(R.id.cameraClick2);
        cam2.setOnClickListener(this);
        cam3 = (ImageView) view.findViewById(R.id.cameraClick3);
        cam3.setOnClickListener(this);
        cam4 = (ImageView) view.findViewById(R.id.cameraClick4);
        cam4.setOnClickListener(this);
        cam5 = (ImageView) view.findViewById(R.id.cameraClick5);
        cam5.setOnClickListener(this);
        photoA = (ImageView) view.findViewById(R.id.imageView);
        //photoA.setOnClickListener(this);
        photoB = (ImageView) view.findViewById(R.id.imageView2);
        photoB.setOnClickListener(this);
        photoC = (ImageView) view.findViewById(R.id.imageView3);
        photoC.setOnClickListener(this);
        photoD = (ImageView) view.findViewById(R.id.imageView4);
        photoD.setOnClickListener(this);
        photoE = (ImageView) view.findViewById(R.id.imageView5);
        photoE.setOnClickListener(this);

        photo_draw = (ImageView) view.findViewById(R.id.imageView_draw);
        photo_draw.setOnClickListener(this);

        photo_file = (ImageView) view.findViewById(R.id.imageView_file);
        photo_file.setOnClickListener(this);

        photo_file2 = (ImageView) view.findViewById(R.id.imageView2_file);
        photo_file2.setOnClickListener(this);

        photo_file3 = (ImageView) view.findViewById(R.id.imageView3_file);
        photo_file3.setOnClickListener(this);

        photo_file4 = (ImageView) view.findViewById(R.id.imageView4_file);
        photo_file4.setOnClickListener(this);

        photo_file5 = (ImageView) view.findViewById(R.id.imageView5_file);
        photo_file5.setOnClickListener(this);

        del_img2 = (ImageView) view.findViewById(R.id.imageView2_del);
        del_img2.setOnClickListener(this);

        del_img3 = (ImageView) view.findViewById(R.id.imageView3_del);
        del_img3.setOnClickListener(this);

        del_img4 = (ImageView) view.findViewById(R.id.imageView4_del);
        del_img4.setOnClickListener(this);

        del_img5 = (ImageView) view.findViewById(R.id.imageView5_del);
        del_img5.setOnClickListener(this);



         if(!globalVariables.photo1.equals(""))
            cam1.setBackgroundResource(R.drawable.edit_border_solid);
        if(!globalVariables.photo2.equals(""))
            cam2.setBackgroundResource(R.drawable.edit_border_solid);
        if(!globalVariables.photo3.equals(""))
            cam3.setBackgroundResource(R.drawable.edit_border_solid);
        if(!globalVariables.photo4.equals(""))
            cam4.setBackgroundResource(R.drawable.edit_border_solid);
        if(!globalVariables.photo5.equals(""))
            cam5.setBackgroundResource(R.drawable.edit_border_solid);
      //  else
       //     cam1.setBackgroundResource(R.drawable.edit_border);


         Overview = (EditText) view.findViewById(R.id.Overview);
         RelevantInfo = (EditText) view.findViewById(R.id.RelevantInfo);
         ServiceCont = (EditText) view.findViewById(R.id.textServicedBy);
         com1Text  = (EditText) view.findViewById(R.id.com1);
         com2Text = (EditText) view.findViewById(R.id.com2);
         com3Text  = (EditText) view.findViewById(R.id.com3);
         com4Text  = (EditText) view.findViewById(R.id.com4);
         com5Text  = (EditText) view.findViewById(R.id.com5);

         final CheckBox checkBox = (CheckBox) view.findViewById(R.id.sign_checkBox);

         reportBtn = (Button) view.findViewById(R.id.btnViewReport);

         //Fill in the edittext with saved data fro Bundle




        title.setText(branchTitle);
        branch.setText(branchName+" date: "+stringdate(inspectionDate));
        ServiceCont.setText(aProvider);
        Overview.setText(overView);
        RelevantInfo.setText(relevantInfo);
        notes.setText(Notes);
        com1Text.setText(com1);
        com2Text.setText(com2);
        com3Text.setText(com3);
        com4Text.setText(com4);
        com5Text.setText(com5);


        checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 //   Edited = true;
                }
            });


        cam1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.photoframe = 1;
                globalVariables.mPhotoImageView = photoA;
                globalVariables.takeImageFromCamera(null);
                cam1.setBackgroundResource(R.drawable.edit_border_solid);

            }
        });

        cam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.photoframe = 2;
                globalVariables.mPhotoImageView = photoB;
                globalVariables.takeImageFromCamera(null);
                cam2.setBackgroundResource(R.drawable.edit_border_solid);

            }
        });

        cam3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.photoframe = 3;
                globalVariables.mPhotoImageView = photoC;
                globalVariables.takeImageFromCamera(null);
                cam3.setBackgroundResource(R.drawable.edit_border_solid);

            }
        });

        cam4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.photoframe = 4;
                globalVariables.mPhotoImageView = photoD;
                globalVariables.takeImageFromCamera(null);
                cam4.setBackgroundResource(R.drawable.edit_border_solid);

            }
        });

        cam5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.photoframe = 5;
                globalVariables.mPhotoImageView = photoE;
                globalVariables.takeImageFromCamera(null);
                cam5.setBackgroundResource(R.drawable.edit_border_solid);

            }
        });

        photo_draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!globalVariables.photo1.equals("")) {
                    String dirName = globalVariables.photo1.substring(6, 14);
                    String root = Environment.getExternalStorageDirectory().toString();
               //     File photo_image = new File(root + "/ESM_" + dirName + "/" + globalVariables.photo1);


                    Intent galleryIntent = new Intent();
                    galleryIntent.setAction(Intent.ACTION_VIEW);

              //      Uri data = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", photo_image);
                    // Uri data = Uri.parse(photo_image.getAbsolutePath());

                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent.createChooser(galleryIntent, "Select Picture"), ACTIVITY_DRAW_FILE);
                }

                else  Toast.makeText(getActivity(), "Function requires a photograph",Toast.LENGTH_SHORT).show();
            }
        });


        photo_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.filephoto = 1;
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


        photoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent galleryIntent = new Intent();
                // galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
              //   galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                 galleryIntent.setAction(Intent.ACTION_VIEW);
              //  galleryIntent.setAction(Intent.ACTION_EDIT);
               // galleryIntent.setAction(Intent.ACTION_PICK);
             //   galleryIntent.setType("image/*");
              //     String[] mimetypes = {"image/*"};
               //    galleryIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes); //            setType("image/*");
                try {
                    String dirName = globalVariables.photo2.substring(6, 14);
                    String root = Environment.getExternalStorageDirectory().toString();
                    File Image = new File(root + "/ESM_" + dirName + "/" + globalVariables.photo2);//+ photos[0]


                galleryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                galleryIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);


                    Uri data = FileProvider.getUriForFile(getContext(),BuildConfig.APPLICATION_ID+".provider",Image);
                    galleryIntent.setDataAndType(data,"image/*");
                  //  galleryIntent.setData(data);
                  //  String[] mimeTypes = {"image/jpeg", "image/png"};

               //  galleryIntent.putExtra(galleryIntent.EXTRA_MIME_TYPES,mimeTypes);
               // galleryIntent.putExtra(galleryIntent.ACTION_EDIT,mimeTypes);

                    startActivity(galleryIntent);
                }
                catch(Exception e){};


               // startActivityForResult(galleryIntent.createChooser(galleryIntent, "Select Picture"), ACTIVITY_DRAW_FILE);
               //  startActivityForResult(new Intent(galleryIntent.ACTION_VIEW, Uri.parse(root + "/ESM_" + dirName + "/"+ globalVariables.photos[1])));

               //  startActivityForResult(galleryIntent, ACTIVITY_DRAW_FILE);
               // globalVariables.startActivityForResult(galleryIntent.createChooser(galleryIntent, "Select Picture"),globalVariables.ACTIVITY_GET_FILE);



                // galleryIntent.setDataAndType(Uri.withAppendedPath(Uri.fromFile(propImage) ,dir),"image/*" );





            }
        });

        photo_file2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.filephoto = 2;
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

        del_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);
                                com2Text.setText("");
                                globalVariables.photo2 ="";
                                Edited = true;
                                photoB.setImageResource(R.drawable.ic_camera);
                                dbHandler.updateInspectionItemPhoto(projId,iId,aId,globalVariables.photo1,"",globalVariables.photo3,
                                                                    globalVariables.photo4, globalVariables.photo5,"Img6", "Img7");
                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure?").setPositiveButton("Yes",dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        photoC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_VIEW);
                try {
                String dirName = globalVariables.photo3.substring(6, 14);
                String root = Environment.getExternalStorageDirectory().toString();
                File Image = new File(root + "/ESM_" + dirName + "/"+ globalVariables.photo3 );//+ photos[0]
                galleryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                galleryIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);


                Uri data = FileProvider.getUriForFile(getContext(),BuildConfig.APPLICATION_ID+".provider",Image);
                galleryIntent.setDataAndType(data,"image/*");

                    startActivity(galleryIntent);
                }
                catch(Exception e){};

            }
        });

        photo_file3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.filephoto = 3;
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

        del_img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);

                                dbHandler.updateInspectionItemPhoto(projId,iId,aId,globalVariables.photo1,globalVariables.photo2,"",
                                        globalVariables.photo4, globalVariables.photo5,"Img6", "Img7");
                               com3Text.setText("");
                                globalVariables.photo3 ="";
                                photoC.setImageResource(R.drawable.ic_camera);

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure?").setPositiveButton("Yes",dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        photoD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_VIEW);
                try {
                String dirName = globalVariables.photo4.substring(6, 14);
                String root = Environment.getExternalStorageDirectory().toString();
                File Image = new File(root + "/ESM_" + dirName + "/"+ globalVariables.photo4 );//+ photos[0]
                galleryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                galleryIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);


                Uri data = FileProvider.getUriForFile(getContext(),BuildConfig.APPLICATION_ID+".provider",Image);
                galleryIntent.setDataAndType(data,"image/*");

                    startActivity(galleryIntent);
                }
                catch(Exception e){};
            }
        });

        photo_file4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.filephoto = 4;
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

        del_img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);

                                dbHandler.updateInspectionItemPhoto(projId,iId,aId,globalVariables.photo1,globalVariables.photo2,
                                        globalVariables.photo3,"", globalVariables.photo5,"Img6", "Img7");
                                com4Text.setText("");
                                globalVariables.photo4 ="";
                                photoD.setImageResource(R.drawable.ic_camera);

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure?").setPositiveButton("Yes",dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        photoE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_VIEW);
                try {
                String dirName = globalVariables.photo5.substring(6, 14);
                String root = Environment.getExternalStorageDirectory().toString();
                File Image = new File(root + "/ESM_" + dirName + "/"+ globalVariables.photo5 );//+ photos[0]
                galleryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                galleryIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);


                Uri data = FileProvider.getUriForFile(getContext(),BuildConfig.APPLICATION_ID+".provider",Image);
                galleryIntent.setDataAndType(data,"image/*");

                    startActivity(galleryIntent);
                }
                catch(Exception e){};
            }
        });

        photo_file5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.filephoto = 5;
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

        del_img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);

                                dbHandler.updateInspectionItemPhoto(projId,iId,aId,globalVariables.photo1,globalVariables.photo2,
                                        globalVariables.photo3, globalVariables.photo4,"","Img6", "Img7");
                                com5Text.setText("");
                                globalVariables.photo5 ="";
                                photoE.setImageResource(R.drawable.ic_camera);

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure?").setPositiveButton("Yes",dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        ServiceCont.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) Edited = true;


            }
        });

        RelevantInfo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) Edited = true;

            }
        });

         Overview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus) Edited = true;

                }
            });

        com1Text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) Edited = true;

            }
        });

        com2Text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) Edited = true;

            }
        });

        com3Text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) Edited = true;

            }
        });

        com4Text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) Edited = true;

            }
        });

        com5Text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) Edited = true;

            }
        });


        notes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) Edited = true;

            }
        });

        for (int i = 0; i < 5; i++) {
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

                    case 1:
                        //           mPhotoImageView = (ImageView) findViewById(R.id.imageView2);
                                  photoB.setImageBitmap(myBitmap);

                        break;

                    case 2:
                        //            mPhotoImageView = (ImageView) findViewById(R.id.imageView3);
                                  photoC.setImageBitmap(myBitmap);

                        break;

                    case 3:
                        //           mPhotoImageView = (ImageView) findViewById(R.id.imageView4);
                                  photoD.setImageBitmap(myBitmap);
                        //          imageName4.setText(dirName);
                        break;

                    case 4:
                        //           mPhotoImageView = (ImageView) findViewById(R.id.imageView5);
                                 photoE.setImageBitmap(myBitmap);
                        //         imageName5.setText(dirName);
                        break;


                } //End of switch
            } //End if there is an image file
            else {

                switch (i) {

                    case 0:
                        //   mPhotoImageView = (ImageView) findViewById(R.id.imageView);
                             photoA.setImageResource(R.drawable.ic_camera);
                        break;

                    case 1:
                        //     mPhotoImageView = (ImageView) findViewById(R.id.imageView2);
                              photoB.setImageResource(R.drawable.ic_camera);
                        //     imageName2.setText("No Photo Record");
                        break;

                    case 2:
                        //        mPhotoImageView = (ImageView) findViewById(R.id.imageView3);
                                photoC.setImageResource(R.drawable.ic_camera);
                        //         imageName3.setText("No Photo Record");
                        break;

                    case 3:
                        //        mPhotoImageView = (ImageView) findViewById(R.id.imageView4);
                                photoD.setImageResource(R.drawable.ic_camera);
                        //        imageName4.setText("No Photo Record");
                        break;

                    case 4:
                        //        mPhotoImageView = (ImageView) findViewById(R.id.imageView5);
                                photoE.setImageResource(R.drawable.ic_camera);
                        //        imageName5.setText("No Photo Record");
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

       if(Edited){

           DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);

           // String serviceDate = inspectionDate.getText().toString();
           // work out the next service date in three months time



               dbHandler.updateInspectionItem(Integer.parseInt(projectId), Integer.parseInt(inspectionId), aId, inspectionDate, Overview.getText().toString(),
                       ServiceCont.getText().toString(), RelevantInfo.getText().toString(), "1", "reportImage",
                       com1Text.getText().toString(), com2Text.getText().toString(), com3Text.getText().toString()
                       ,  com4Text.getText().toString(),
                       com5Text.getText().toString(), "Img6", " com6", "p", notes.getText().toString());

               Edited = false;



           }



 //       endTime = dayTime(2);
 //       DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);
//        dbHandler.logInspection(projectId, inspectionId, startTime, endTime);


    }
}