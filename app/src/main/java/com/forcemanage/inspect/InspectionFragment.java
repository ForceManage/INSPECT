package com.forcemanage.inspect;

import android.content.Context;
import android.os.Bundle;
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
    private Spinner sprObservation;
    private Spinner sprRecommendation;
    private Spinner sprTitle;
    private Spinner sprbuildcat;
    private Spinner sprasset;
    private Spinner sprContractor;
    private EditText relevantInfo;
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
            com1 = bundle.getString("com1");
            com2 = bundle.getString("com2");
        }


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.inspection_fragment, container, false);
        //       btnInspection = (Button) view.findViewById(R.id.btnInspection);


        Log.d(TAG, "oncreateview: started");

        title = (TextView) view.findViewById(R.id.title);
        branch = (TextView) view.findViewById(R.id.level);
        notes = (EditText) view.findViewById(R.id.note);
        Img1 = (ImageView) view.findViewById(R.id.imageView);
        Img2 = (ImageView) view.findViewById(R.id.imageView_file);
        Img3 = (ImageView) view.findViewById(R.id.imageView_cam);
        Img4 = (ImageView) view.findViewById(R.id.imageView_draw);



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


        sprObservation = (Spinner) view.findViewById(R.id.spinnerObservation);
        sprRecommendation = (Spinner) view.findViewById(R.id.spinnerRecommendation);
        sprContractor = (Spinner) view.findViewById(R.id.spinnerContractor);

         Overview = (EditText) view.findViewById(R.id.Overview);
         relevantInfo = (EditText) view.findViewById(R.id.relevantInfo);
         ServiceCont = (EditText) view.findViewById(R.id.textServicedBy);
         com1Text  = (EditText) view.findViewById(R.id.com1);
         com2Text = (EditText) view.findViewById(R.id.com2);
         com3Text  = (EditText) view.findViewById(R.id.com3);
         com4Text  = (EditText) view.findViewById(R.id.com4);
         com5Text  = (EditText) view.findViewById(R.id.com5);
         final CheckBox checkBox = (CheckBox) view.findViewById(R.id.sign_checkBox);

         final EditText Note = (EditText) view.findViewById(R.id.note);

         com1Text.setText(com1);
         com2Text.setText(com2);

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




         Overview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus) globalVariables.Edited = true;
                    globalVariables.Overview = Overview.getText().toString();
                }
            });

        com1Text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) globalVariables.Edited = true;
                globalVariables.com1 = com1Text.getText().toString();
            }
        });

        com2Text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) globalVariables.Edited = true;
                globalVariables.com2 = com2Text.getText().toString();
            }
        });

        relevantInfo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) globalVariables.Edited = true;
                globalVariables.relevantInfo = relevantInfo.getText().toString();

            }
        });

        Note.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) globalVariables.Edited = true;
                globalVariables.Notes = Note.getText().toString();
            }
        });
/*
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
                    Overview.setText(observations[position]);
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




*/

        return view;
    }



    @Override
    public void onClick(View v) {

    }
}