package com.forcemanage.inspect;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SummaryFragment extends Fragment implements View.OnClickListener {

    private InspectionActivity globalVariables;

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
    private String headA = "Title A";
    private String comA = "Comments";
    private String headB = "Title B";
    private String comB = "Comments";
    private String headC = "Title C";
    private String comC = "Comments";
    private TextView HeadA;
    private TextView HeadB;
    private TextView HeadC;
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
            branchTitle = bundle.getString("branchHead");
            branchName = bundle.getString("branchLabel");
            desciption = bundle.getString("description");
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

        title = (TextView) view.findViewById(R.id.title);
        branch = (TextView) view.findViewById(R.id.level);
        notesE = (EditText) view.findViewById(R.id.note);


         descriptionE = (EditText) view.findViewById(R.id.desc);
         ComA = (EditText) view.findViewById(R.id.com1);
        ComB = (EditText) view.findViewById(R.id.com2);
        ComC = (EditText) view.findViewById(R.id.com3);
         notesE = (EditText) view.findViewById(R.id.notes);
         HeadA = (TextView) view.findViewById(R.id.Head_A);
        HeadB = (TextView) view.findViewById(R.id.Head_B);
        HeadC = (TextView) view.findViewById(R.id.Head_C);

  //       final CheckBox checkBox = (CheckBox) view.findViewById(R.id.sign_checkBox);


         //Fill in the edittext with saved data fro Bundle

        title.setText(branchTitle);
        branch.setText(branchName);
        descriptionE.setText(desciption);
        HeadA.setText(headA);
        ComA.setText(comA);
        ComB.setText(comB);
        ComC.setText(comC);



        HeadA.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) Edited = true;


            }
        });




         ComA.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus) Edited = true;

                }
            });

        HeadB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) Edited = true;

            }
        });

        ComB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) Edited = true;

            }
        });

        HeadC.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) Edited = true;

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


    @Override
    public void onDestroy() {
        super.onDestroy();

        if(Edited){

            DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);

            dbHandler.updateSummary(projectId, inspectionId, headA, comA,
                                      headB, comB
                    , headC, comC);
            dbHandler.statusChanged(Integer.parseInt(projectId));

           Edited = false;

        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(Edited){
            DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);

            dbHandler.updateSummary(projectId, inspectionId, headA, comA,
                    headB, comB
                    , headC, comC);
            dbHandler.statusChanged(Integer.parseInt(projectId));

            Edited = false;

        }
    }
}