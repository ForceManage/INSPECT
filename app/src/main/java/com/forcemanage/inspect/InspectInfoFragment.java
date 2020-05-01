package com.forcemanage.inspect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InspectInfoFragment extends Fragment implements View.OnClickListener{

    private MainActivity globalVariables;

    private static final String TAG = "Project Info Fragment";

    private TextView title;
    private TextView branch;
    private EditText bNote;

    private String branchHead ="";
    private String branchLabel = "";
    private String branchNote = "";
    private String inspectionDate;
    private String typeInspection;
    private String auditor;
    private String note;
    private Boolean Edited;
    private String projectId;
    private String inspectionId;
    private String dateInspected;
    private String endTime;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle != null){
      //      branchHead = bundle.getString("branchHead");
      //      branchLabel = bundle.getString("branchLabel");
      //      branchNote = bundle.getString("notes");
            inspectionDate = bundle.getString("date");
            note = bundle.getString("note");
            auditor = bundle.getString("auditor");
            typeInspection = bundle.getString("inspectType");
            dateInspected = bundle.getString("dateInspected");
            endTime = bundle.getString("endTime");
            Edited = false;

        }

        projectId = globalVariables.projectId;
        inspectionId = globalVariables.inspectionId;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        globalVariables = (MainActivity) getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.inspect_info,container,false);


        Log.d(TAG, "oncreateview: started");

        title = (TextView) view.findViewById(R.id.title);
        branch = (TextView) view.findViewById(R.id.level);
        bNote = (EditText) view.findViewById(R.id.note);
        TextView inspectDate = (TextView) view.findViewById(R.id.Text1);
        TextView inspectionType = (TextView) view.findViewById(R.id.Text2);
        TextView inspectedDate = (TextView) view.findViewById(R.id.Text3);
        TextView inspector = (TextView) view.findViewById(R.id.Text4);
        Button inspectionBtn = (Button) view.findViewById(R.id.InspectionButton);


        inspectionBtn.setOnClickListener(this);


         setText();
         endTime = stringdate(endTime,3);
         inspectDate.setText("Activity raised:  "+stringdate(inspectionDate,1));
         inspectionType.setText("Type of Activity:  "+typeInspection);
         inspectedDate.setText("Activity recorded: "+stringdate(dateInspected,2)+"  -  "+endTime);
         inspector.setText("Auditor:  "+ auditor);
         bNote.setText(note);

        inspectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
               Intent theIntent = new Intent(getActivity(), InspectionActivity.class);
                saveData();
                bundle.putString("PROJECT_ID", globalVariables.projectId);
                bundle.putString("INSPECTION_ID", globalVariables.inspectionId);
                theIntent.putExtras(bundle);
                startActivity(theIntent);

            }
        });



        bNote.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            Edited = true;
                // String serviceDate = inspectionDate.getText().toString();
                // work out the next service date in three months time

            }

        });



        return view;
    }



    private void setText(){

        if (!branchHead.equals("")){
           title.setText(branchHead);
           branch.setText(branchLabel);
           bNote.setText(branchNote);
        }
    }


    @Override
    public void onClick(View v) {

    }

    public String stringdate(String date, int type){

        switch (type) {

            case 1: {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                Date d = null;
                try {
                    d = sdf.parse(date);
                } catch (ParseException ex) {
                    Log.v("Exception", ex.getLocalizedMessage());
                }
                sdf.applyPattern("dd MMM yyyy");
                date = sdf.format(d);
                break;
            }
            case 2: {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d = null;
                try {
                    d = sdf.parse(date);
                } catch (ParseException ex) {
                    Log.v("Exception", ex.getLocalizedMessage());
                }
                sdf.applyPattern("dd MMM yyyy, HH:mm");
                date = sdf.format(d);
                break;

            }

            case 3: {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d = null;
                try {
                    d = sdf.parse(date);
                } catch (ParseException ex) {
                    Log.v("Exception", ex.getLocalizedMessage());
                }
                sdf.applyPattern("HH:mm");
                date = sdf.format(d);
                break;

            }

        }

        return date;
    }

    public void saveData(){

        DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);
        note = bNote.getText().toString();

        dbHandler.updateInspection(projectId, inspectionId, note);
}

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (Edited = true) saveData();
    }
}
