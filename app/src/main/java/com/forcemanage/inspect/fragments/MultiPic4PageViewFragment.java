package com.forcemanage.inspect.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.forcemanage.inspect.DBHandler;
import com.forcemanage.inspect.InspectionActivity;
import com.forcemanage.inspect.MainActivity;
import com.forcemanage.inspect.R;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MultiPic4PageViewFragment extends Fragment implements View.OnClickListener {

    private MainActivity globalVariables;

    private static final String TAG = "Inspection Fragment";
    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private static final int ACTIVITY_GET_FILE = 1;
    private static final int ACTIVITY_DRAW_FILE = 2;
    private String projectId;
    private String inspectionId;
    private String overView;
    private String relevantInfo;
    private EditText RelevantInfo;
    private EditText Overview;
    private ImageView cam1;
    private ImageView cam2;
    private ImageView cam3;
    private ImageView cam4;
    private ImageView cam5;
    private ImageView cam6;
    private ImageView photoA;
    private ImageView photoB;
    private ImageView photoC;
    private ImageView photoD;
    private ImageView photoE;
    private ImageView photoF;
    private ImageView del_img;
    private ImageView del_img2;
    private ImageView del_img3;
    private ImageView del_img4;
    private ImageView del_img5;
    private ImageView del_img6;
    private ImageView photo_draw;
    private ImageView photo_file;
    private ImageView photo_file2;
    private ImageView photo_file3;
    private ImageView photo_file4;
    private ImageView photo_file5;
    private ImageView photo_file6;
    private ImageView Photo_insert;
    private ImageView Photo_insert2;
    private ImageView Photo_insert3;
    private ImageView Photo_insert4;
    private ImageView Photo_insert5;
    private ImageView Photo_insert6;
    private EditText com1Text;
    private EditText com2Text;
    private EditText com3Text;
    private EditText com4Text;
    private EditText com5Text;
    private EditText com6Text;
    private TextView Buildcat;
    private TextView imageName1;
    private Button reportBtn;
    private String com1 ="Commentary :";
    private String com2 = "";
    private String com3 = "";
    private String com4 = "";
    private String com5 = "";
    private String com6 = "";
    private int aId;
    private int projId;
    private int iId;

    private String inspectionDate;
    private String startTime;
    private String endTime;
    private String Prnt;
    private boolean Edited = false;
    private ImageView play1;
    private ImageView play2;

    TextToSpeech t1;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        globalVariables = (MainActivity) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            projectId = bundle.getString("projectID");
            inspectionId = bundle.getString("inspectionID");
            aId = bundle.getInt("aID");

            inspectionDate = bundle.getString("date");
            relevantInfo = bundle.getString("relevantInfo");
            com1 = bundle.getString("com1");
            com2 = bundle.getString("com2");
            com3 = bundle.getString("com3");
            com4 = bundle.getString("com4");
            com5 = bundle.getString("com5");
            com6 = bundle.getString("com6");
            Prnt = bundle.getString("prnt");



        }

        //   inspectionDate =  inspectionDate.substring(6,8)+"-"+inspectionDate.substring(4,6)+"-"+inspectionDate.substring(0,4);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.multi_pic4view, container, false);
        //       btnInspection = (Button) view.findViewById(R.id.btnInspection);


        final CheckBox Cbox = (CheckBox) view.findViewById(R.id.print_checkBox);


        Log.d(TAG, "oncreateview: started");





        photoA = (ImageView) view.findViewById(R.id.imageView);
        //photoA.setOnClickListener(this);
        photoB = (ImageView) view.findViewById(R.id.imageView2);
        //    photoB.setOnClickListener(this);
        photoC = (ImageView) view.findViewById(R.id.imageView3);
        //    photoC.setOnClickListener(this);
        photoD = (ImageView) view.findViewById(R.id.imageView4);
        //    photoD.setOnClickListener(this);


         //    photo_file4.setOnClickListener(this);







        com1Text  = (EditText) view.findViewById(R.id.com1);
        com2Text = (EditText) view.findViewById(R.id.com2);
        com3Text  = (EditText) view.findViewById(R.id.com3);
        com4Text  = (EditText) view.findViewById(R.id.com4);


        com1Text.setText(com1);
        com2Text.setText(com2);
        com3Text.setText(com3);
        com4Text.setText(com4);


        for (int i = 0; i < 4; i++) {
            if (globalVariables.photos[i] == null) {
                globalVariables.photos[i] = "";
                //    cameraSnap = photos[i];
            }


            if ( globalVariables.photos[i].length() > 12) {
                String dirName =  globalVariables.photos[i].substring(6, 14);
                String root = Environment.getExternalStorageDirectory().toString();
                File Image = new File(root + "/A2D_" + dirName + "/" +  globalVariables.photos[i]);
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

                }//End of switch
            }//End of else

        }//End of loop

        return view;

    }

    @Override
    public void onClick(View v) {

    }




}



