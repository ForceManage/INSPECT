package com.forcemanage.inspect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
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
import androidx.fragment.app.Fragment;

import java.io.File;

public class InspectionFragment extends Fragment implements View.OnClickListener {

    private InspectionActivity globalVariables;

    private static final String TAG = "Inspection Fragment";
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


     //    getZonesArray();  //Load the zone spinner dropdown

        // An array containing list of observations



    //    mBranchNodeAdapter = new BranchNodeAdapter(getSupportFragmentManager());
     //   mViewPager = (ViewPager) findViewById(R.id.container);
    //    setupViewPager(mViewPager);
    //    mViewPager.canScrollHorizontally(0);



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

        photoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.photoframe = 1;
                globalVariables.mPhotoImageView = photoB;
                globalVariables.takeImageFromCamera(null);

            }
        });

        photoC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.photoframe = 1;
                globalVariables.mPhotoImageView = photoC;
                globalVariables.takeImageFromCamera(null);

            }
        });

        photoD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.photoframe = 1;
                globalVariables.mPhotoImageView = photoD;
                globalVariables.takeImageFromCamera(null);

            }
        });

        photoE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.photoframe = 1;
                globalVariables.mPhotoImageView = photoE;
                globalVariables.takeImageFromCamera(null);

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