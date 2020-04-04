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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private ImageView photo_file2;
    private ImageView photo_file3;
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
            branchTitle = bundle.getString("branchHead");
            branchName = bundle.getString("branchLabel");
            aProvider = bundle.getString("aprovider");
            overView = bundle.getString("overview");
            relevantInfo = bundle.getString("relevantInfo");
            Notes = bundle.getString("notes");
            com1 = bundle.getString("com1");
            com2 = bundle.getString("com2");
            com3 = bundle.getString("com3");
            com4 = bundle.getString("com4");
            com5 = bundle.getString("com5");

        }


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.inspection_fragment, container, false);
        //       btnInspection = (Button) view.findViewById(R.id.btnInspection);


        Log.d(TAG, "oncreateview: started");

        title = (TextView) view.findViewById(R.id.branchTitle);
        branch = (TextView) view.findViewById(R.id.branchName);
        notes = (EditText) view.findViewById(R.id.note);

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

        photo_cam = (ImageView) view.findViewById(R.id.imageView_cam);
        photo_cam.setOnClickListener(this);

        photo_draw = (ImageView) view.findViewById(R.id.imageView_draw);
        photo_draw.setOnClickListener(this);

        photo_file = (ImageView) view.findViewById(R.id.imageView_file);
        photo_file.setOnClickListener(this);

        photo_file2 = (ImageView) view.findViewById(R.id.imageView2_file);
        photo_file2.setOnClickListener(this);

        photo_file3 = (ImageView) view.findViewById(R.id.imageView3_file);
        photo_file3.setOnClickListener(this);

        del_img2 = (ImageView) view.findViewById(R.id.imageView2_del);
        del_img2.setOnClickListener(this);

        del_img3 = (ImageView) view.findViewById(R.id.imageView3_del);
        del_img3.setOnClickListener(this);

        del_img4 = (ImageView) view.findViewById(R.id.imageView4_del);
        del_img4.setOnClickListener(this);

        del_img5 = (ImageView) view.findViewById(R.id.imageView5_del);
        del_img5.setOnClickListener(this);



         Overview = (EditText) view.findViewById(R.id.Overview);
         RelevantInfo = (EditText) view.findViewById(R.id.RelevantInfo);
         ServiceCont = (EditText) view.findViewById(R.id.textServicedBy);
         com1Text  = (EditText) view.findViewById(R.id.com1);
         com2Text = (EditText) view.findViewById(R.id.com2);
         com3Text  = (EditText) view.findViewById(R.id.com3);
         com4Text  = (EditText) view.findViewById(R.id.com4);
         com5Text  = (EditText) view.findViewById(R.id.com5);
         final CheckBox checkBox = (CheckBox) view.findViewById(R.id.sign_checkBox);



         //Fill in the edittext with saved data fro Bundle

        title.setText(branchTitle);
        branch.setText(branchName);
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
                String dirName = globalVariables.photo1.substring(6, 14);
                String root = Environment.getExternalStorageDirectory().toString();
                File photo_image = new File(root + "/ESM_" + dirName + "/"+globalVariables.photo1);


                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_VIEW);



                Uri data = FileProvider.getUriForFile(getActivity(),BuildConfig.APPLICATION_ID+".provider",photo_image);
                // Uri data = Uri.parse(photo_image.getAbsolutePath());

                galleryIntent.setDataAndType(data ,"image/*");
                startActivityForResult(galleryIntent.createChooser(galleryIntent, "Select Picture"),ACTIVITY_DRAW_FILE);

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


        photoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.photoframe = 2;
                globalVariables.mPhotoImageView = photoB;
                globalVariables.takeImageFromCamera(null);
                globalVariables.Edited = true;
            }
        });

        photo_file2.setOnClickListener(new View.OnClickListener() {
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

        del_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                globalVariables.Edited = true;
                                com2Text.setText("");
                                globalVariables.photo2 ="";
                                photoB.setImageResource(R.mipmap.ic_camera);

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
                globalVariables.photoframe = 3;
                globalVariables.mPhotoImageView = photoC;
                globalVariables.takeImageFromCamera(null);
                globalVariables.Edited = true;

            }
        });

        photo_file3.setOnClickListener(new View.OnClickListener() {
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

        del_img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                globalVariables.Edited = true;
                                com2Text.setText("");
                                globalVariables.photo2 ="";
                                photoB.setImageResource(R.mipmap.ic_camera);

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
                globalVariables.photoframe = 4;
                globalVariables.mPhotoImageView = photoD;
                globalVariables.takeImageFromCamera(null);
                globalVariables.Edited = true;

            }
        });

        photoE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.photoframe = 5;
                globalVariables.mPhotoImageView = photoE;
                globalVariables.takeImageFromCamera(null);
                globalVariables.Edited = true;

            }
        });


        ServiceCont.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) globalVariables.Edited = true;


            }
        });

        RelevantInfo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) globalVariables.Edited = true;

            }
        });

         Overview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus) globalVariables.Edited = true;

                }
            });

        com1Text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) globalVariables.Edited = true;

            }
        });

        com2Text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) globalVariables.Edited = true;

            }
        });

        RelevantInfo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) globalVariables.Edited = true;


            }
        });

        notes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) globalVariables.Edited = true;

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
                             photoA.setImageResource(R.mipmap.ic_camera);
                        break;

                    case 1:
                        //     mPhotoImageView = (ImageView) findViewById(R.id.imageView2);
                              photoB.setImageResource(R.mipmap.ic_camera);
                        //     imageName2.setText("No Photo Record");
                        break;

                    case 2:
                        //        mPhotoImageView = (ImageView) findViewById(R.id.imageView3);
                                photoC.setImageResource(R.mipmap.ic_camera);
                        //         imageName3.setText("No Photo Record");
                        break;

                    case 3:
                        //        mPhotoImageView = (ImageView) findViewById(R.id.imageView4);
                                photoD.setImageResource(R.mipmap.ic_camera);
                        //        imageName4.setText("No Photo Record");
                        break;

                    case 4:
                        //        mPhotoImageView = (ImageView) findViewById(R.id.imageView5);
                                photoE.setImageResource(R.mipmap.ic_camera);
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


}