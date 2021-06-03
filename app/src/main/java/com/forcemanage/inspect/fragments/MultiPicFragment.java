package com.forcemanage.inspect.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.forcemanage.inspect.BuildConfig;
import com.forcemanage.inspect.DBHandler;
import com.forcemanage.inspect.InspectionActivity;
import com.forcemanage.inspect.R;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MultiPicFragment extends Fragment implements View.OnClickListener {

    private InspectionActivity globalVariables;

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

            overView = bundle.getString("overview");
            inspectionDate = bundle.getString("date");
            relevantInfo = bundle.getString("relevantInfo");

            Prnt = bundle.getString("prnt");

        }

        //   inspectionDate =  inspectionDate.substring(6,8)+"-"+inspectionDate.substring(4,6)+"-"+inspectionDate.substring(0,4);

        inspectionDate = dayTime(1);
        startTime = dayTime(2);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.multi_pic, container, false);
        //       btnInspection = (Button) view.findViewById(R.id.btnInspection);


        final CheckBox Cbox = (CheckBox) view.findViewById(R.id.print_checkBox);

        if (Prnt.equals("1"))
            Cbox.setChecked(true);
        else
            Cbox.setChecked(false);

        Cbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Cbox.isChecked()) Prnt = "1";
                else Prnt = "0";
                Edited = true;
            }
        });

        Log.d(TAG, "oncreateview: started");

        t1 = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.ENGLISH);
                }
            }
        });



        play1 = (ImageView) view.findViewById(R.id.title1_voice);
        play2 = (ImageView) view.findViewById(R.id.title2_voice);

        projId = Integer.parseInt(projectId);
        iId= Integer.parseInt(inspectionId);
        Overview = (EditText) view.findViewById(R.id.Overview);
        RelevantInfo = (EditText) view.findViewById(R.id.RelevantInfo);
        Overview.setText(overView);
        RelevantInfo.setText(relevantInfo);
        play1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = Overview.getText().toString();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        play2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = RelevantInfo.getText().toString();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        photoA = (ImageView) view.findViewById(R.id.imageView);
        //photoA.setOnClickListener(this);
        photoB = (ImageView) view.findViewById(R.id.imageView2);
        //    photoB.setOnClickListener(this);
        photoC = (ImageView) view.findViewById(R.id.imageView3);
        //    photoC.setOnClickListener(this);
        photoD = (ImageView) view.findViewById(R.id.imageView4);
        //    photoD.setOnClickListener(this);
        photoE = (ImageView) view.findViewById(R.id.imageView5);
        photoF = (ImageView) view.findViewById(R.id.imageView6);



        photoA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.photoframe = 1;
                globalVariables.mPhotoImageView = photoA;
                globalVariables.takeImageFromCamera(null);
                Edited = true;
            }
        });

        photoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.photoframe = 2;
                globalVariables.mPhotoImageView = photoB;
                globalVariables.takeImageFromCamera(null);
                Edited = true;
            }
        });

        photoC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.photoframe = 3;
                globalVariables.mPhotoImageView = photoC;
                globalVariables.takeImageFromCamera(null);
                Edited = true;
            }
        });

        photoD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.photoframe = 4;
                globalVariables.mPhotoImageView = photoD;
                globalVariables.takeImageFromCamera(null);
                Edited = true;
            }
        });

        photoE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.photoframe = 5;
                globalVariables.mPhotoImageView = photoE;
                globalVariables.takeImageFromCamera(null);
                Edited = true;
            }
        });

        photoF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.photoframe = 6;
                globalVariables.mPhotoImageView = photoF;
                globalVariables.takeImageFromCamera(null);
                Edited = true;
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

        for (int i = 0; i < 6; i++) {
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

                    case 4:
                        //           mPhotoImageView = (ImageView) findViewById(R.id.imageView5);
                        photoE.setImageBitmap(myBitmap);
                        //         imageName5.setText(dirName);
                        break;

                    case 5:
                        //           mPhotoImageView = (ImageView) findViewById(R.id.imageView5);
                        photoF.setImageBitmap(myBitmap);
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

                    case 5:
                        //        mPhotoImageView = (ImageView) findViewById(R.id.imageView5);
                        photoF.setImageResource(R.drawable.ic_camera);
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

            dbHandler.updateInspectionItem(Integer.parseInt(projectId), Integer.parseInt(inspectionId), aId, inspectionDate, Overview.getText().toString(),
                    "", RelevantInfo.getText().toString(), Prnt, "1",
                    "", "", ""
                    ,  "",
                    "",  "", "", "m", "");

            dbHandler.statusChanged(projId,iId);

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(Edited){

            DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);
            dbHandler.updateInspectionItem(Integer.parseInt(projectId), Integer.parseInt(inspectionId), aId, inspectionDate, Overview.getText().toString(),
                    "", RelevantInfo.getText().toString(), Prnt, "1",
                    "", "", ""
                    ,  "",
                    "",  "", "", "m", "");

            dbHandler.statusChanged(projId, iId);

        }
    }
}
