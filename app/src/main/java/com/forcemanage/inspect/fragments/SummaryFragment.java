package com.forcemanage.inspect.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.forcemanage.inspect.DBHandler;
import com.forcemanage.inspect.MainActivity;
import com.forcemanage.inspect.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SummaryFragment extends Fragment implements View.OnClickListener {

    private MainActivity globalVariables;

    private static final String TAG = "ActionItem Fragment";


    private TextView title;
    private TextView branch;
    private EditText descriptionE;
    private EditText ComA;
    private EditText ComB;
    private EditText ComC;
    private EditText notesE;
    private boolean Edited = false;
    private String branchTitle = "Title";
    private String branchName = "Branch";
    private String desciption = "Desc";
    private String projectId;
    private String inspectionId;
    private Integer projId;
    private Integer iId;
    private String headA = "Title A";
    private String comA = "Comments";
    private String headB = "Title B";
    private String comB = "Comments";
    private String headC = "Title C";
    private String comC = "Comments";
    private TextView HeadA;
    private TextView HeadB;
    private TextView HeadC;
    private ImageView play1;
    private ImageView play2;
    private ImageView play3;
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
            branchTitle = bundle.getString("branchHead");
            branchName = bundle.getString("branchLabel");
            headA = bundle.getString("head_A");
            comA = bundle.getString("com_A");
            headB = bundle.getString("head_B");
            comB = bundle.getString("com_B");
            headC = bundle.getString("head_C");
            comC = bundle.getString("com_C");
         }


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.summary, container, false);
        //       btnInspection = (Button) view.findViewById(R.id.btnInspection);


        Log.d(TAG, "oncreateview: started");

        projId = Integer.parseInt(projectId);
        iId = Integer.parseInt(inspectionId);

        title = (TextView) view.findViewById(R.id.title);
        branch = (TextView) view.findViewById(R.id.level);
        notesE = (EditText) view.findViewById(R.id.note);


         descriptionE = (EditText) view.findViewById(R.id.desc);
         ComA = (EditText) view.findViewById(R.id.comA);
        ComB = (EditText) view.findViewById(R.id.comB);
        ComC = (EditText) view.findViewById(R.id.comC);
         notesE = (EditText) view.findViewById(R.id.notes);
         HeadA = (TextView) view.findViewById(R.id.Head_A);
        HeadB = (TextView) view.findViewById(R.id.Head_B);
        HeadC = (TextView) view.findViewById(R.id.Head_C);
        play1 = (ImageView) view.findViewById(R.id.title1_voice);
        play2 = (ImageView) view.findViewById(R.id.title2_voice);
        play3 = (ImageView) view.findViewById(R.id.title3_voice);

        t1 = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.ENGLISH);
                }
            }
        });

        play1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = ComA.getText().toString();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        play2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = ComB.getText().toString();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        play3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = ComC.getText().toString();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });


        title.setText(branchTitle);
        branch.setText(branchName);
        if(headA != null) HeadA.setText(headA); else HeadA.setText("TITLE");
        if(headB != null) HeadB.setText(headB); else HeadB.setText("TITLE");
        if(headC != null) HeadC.setText(headC); else HeadC.setText("TITLE");
        if(ComA != null) ComA.setText(comA);
        if(ComB != null) ComB.setText(comB);
        if(ComC != null) ComC.setText(comC);



        HeadA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               editLabel("head_A", "Set Title");
                Edited = true;
            }
        });


         ComA.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus) Edited = true;

                }
            });

        HeadB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editLabel("head_B", "Set Title");
                Edited = true;
            }
        });

        ComB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) Edited = true;

            }
        });

        HeadC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editLabel("head_C", "Set Title");
                Edited = true;
            }
        });

        ComC.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) Edited = true;

            }
        });


        return view;
    }

        @Override
    public void onClick(View v) {

    }

    public void editLabel(final String  item, String value){

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View promptView = layoutInflater.inflate(R.layout.add_location, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptView);
        final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
        itemTitle.setText("Set Title ");//Integer.parseInt(locationId)
        final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
        locationText.setText("Title");//Integer.parseInt(locationId)
        final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
        branchText.setHint(value);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        switch (item) {

                            case "head_A":{
                                headA = branchText.getText().toString();
                                HeadA.setText(headA);
                                break;
                            }
                            case "head_B":{
                                headB = branchText.getText().toString();
                                HeadB.setText(headB);
                                break;
                            }
                            case "head_C":{
                                headC = branchText.getText().toString();
                                HeadC.setText(headC);
                                break;
                            }

                        }

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


    @Override
    public void onDestroy() {
        super.onDestroy();

        if(Edited){

            DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);
            headA = HeadA.getText().toString();
            headB = HeadB.getText().toString();
            headC = HeadC.getText().toString();
            comA = ComA.getText().toString();
            comB = ComB.getText().toString();
            comC = ComC.getText().toString();
            dbHandler.updateSummary(projectId, inspectionId, headA, comA, headB, comB , headC, comC);
            dbHandler.statusChanged(projId, iId);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(Edited){
            DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);
            headA = HeadA.getText().toString();
            headB = HeadB.getText().toString();
            headC = HeadC.getText().toString();
            comA = ComA.getText().toString();
            comB = ComB.getText().toString();
            comC = ComC.getText().toString();
            dbHandler.updateSummary(projectId, inspectionId, headA, comA, headB, comB , headC, comC);
            dbHandler.statusChanged(projId, iId);
            globalVariables.photo_load_close();

        }
    }
}