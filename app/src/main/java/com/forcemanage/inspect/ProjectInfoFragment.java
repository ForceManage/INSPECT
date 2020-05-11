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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;

public class ProjectInfoFragment extends Fragment implements View.OnClickListener{

    private MainActivity globalVariables;

    private static final String TAG = "Project Info Fragment";

    private TextView title;
    private TextView branch;
    private EditText bNote;



    private String branchHead ="";
    private String branchLabel = "";
    private String branchNote = "";

    private String ProjAddress = "";
    private Boolean Edited;
    private String note;
    private String projectId;
    private String BuildType;
    private String BuildPermNo;
    private String BuildClass;
    private String Levels;
    private String AccessKey;
    private String Floor;
    private String Walls;
    private String Roof;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            branchHead = bundle.getString("branchHead");
      //      branchLabel = bundle.getString("branchLabel");
      //      branchNote = bundle.getString("notes");
            ProjAddress = bundle.getString("address");
            note = bundle.getString("note");


            BuildType = bundle.getString("buildType");
            BuildPermNo = bundle.getString("permit");
            Levels = bundle.getString("levels");
            AccessKey = bundle.getString("key");
            Floor = bundle.getString("floor");
            Walls = bundle.getString("wall");
            Roof = bundle.getString("roof");

         }

            Edited = false;
        projectId = globalVariables.projectId;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        globalVariables = (MainActivity) getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.project_info,container,false);


        Log.d(TAG, "oncreateview: started");

        title = (TextView) view.findViewById(R.id.title);
        branch = (TextView) view.findViewById(R.id.level);
        bNote = (EditText) view.findViewById(R.id.note);

       final TextView projAddress = (TextView) view.findViewById(R.id.Text1);
       TextView projNumber = (TextView) view.findViewById(R.id.branchTitle);

       projAddress.setText(ProjAddress);
       projNumber.setText("Project ID:  "+branchHead);
       bNote.setText(note);


        final TextView TVprojectID = (TextView) view.findViewById(R.id.branchTitle);
        TVprojectID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              editProject("Project ID",TVprojectID.getText().toString());
            }
        });
        projAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Project Address",projAddress.getText().toString());
            }
        });

        final TextView TVbuildType = (TextView) view.findViewById(R.id.Text2);
        TVbuildType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Build Type",TVbuildType.getText().toString());
            }
        });

        final TextView TVbuildPerm = (TextView) view.findViewById(R.id.Text3);
        TVbuildPerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Building Permit No.",TVbuildPerm.getText().toString());
            }
        });

        final TextView TVbuildClass = (TextView) view.findViewById(R.id.Text4);
        TVbuildClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Building Class",TVbuildClass.getText().toString());
            }
        });

        final TextView TVlevels = (TextView) view.findViewById(R.id.Text5);
        TVlevels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("No. of levels",TVlevels.getText().toString());
            }
        });
        final TextView TVaccessCode = (TextView) view.findViewById(R.id.Text6);
        TVaccessCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Access key/code",TVaccessCode.getText().toString());
            }
        });

        final TextView TVfloorType = (TextView) view.findViewById(R.id.Text7);
        TVfloorType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Floor Type", TVfloorType.getText().toString());
            }
        });

        final TextView TVwallType = (TextView) view.findViewById(R.id.Text8);
        TVwallType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Wall Type", TVwallType.getText().toString());
            }
        });

        final TextView TVroofType = (TextView) view.findViewById(R.id.Text9);
        TVroofType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Roof Type", TVroofType.getText().toString());
            }
        });

         setText();
        TVbuildType.setText("Build Type:  "+BuildType);
        TVaccessCode.setText("Access key/code:  "+AccessKey);
        TVbuildClass.setText("Building Class:  "+BuildClass);
        TVlevels.setText("Levels:  "+Levels);
        TVbuildPerm.setText("Building Permit No.:  "+BuildPermNo);

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
   //        title.setText(branchHead);
   //        branch.setText(branchLabel);
   //        bNote.setText(branchNote);
        }
    }


    @Override
    public void onClick(View v) {

    }


    public void editProject(final String  item, String value){

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View promptView = layoutInflater.inflate(R.layout.add_location, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptView);
        final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
        itemTitle.setText("Project Details ");//Integer.parseInt(locationId)
        final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
        locationText.setText(item);//Integer.parseInt(locationId)
        final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
        branchText.setHint(value);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        DBHandler dbHandler = new DBHandler(getContext(), null, null, 1);
                        switch (item) {

                            case "Project Address":{
                                dbHandler.updateProject(projectId,item ,branchText.getText().toString(),0);
                                globalVariables.OnSelectionChanged(0);
                                break;
                            }
                            case "Project ID":{
                                dbHandler.updateProject(projectId,item ,branchText.getText().toString(),0);
                                globalVariables.OnSelectionChanged(0);
                                break;
                            }
                            case "Build Type":{
                                dbHandler.updateProject(projectId,item ,branchText.getText().toString(),0);
                                globalVariables.OnSelectionChanged(0);
                                break;
                            }
                            case "Building Permit No.":{
                                dbHandler.updateProject(projectId,item ,branchText.getText().toString(),0);
                                globalVariables.OnSelectionChanged(0);
                                break;
                            }
                            case "No. of levels":{
                                dbHandler.updateProject(projectId,item ,"", Integer.parseInt(branchText.getText().toString()) );
                                globalVariables.OnSelectionChanged(0);
                                break;
                            }
                            case "Access key/code":{
                                dbHandler.updateProject(projectId,item ,branchText.getText().toString(),0);
                                globalVariables.OnSelectionChanged(0);
                                break;
                            }
                            case "Floor Type":{
                                dbHandler.updateProject(projectId,item ,branchText.getText().toString(),0);
                                break;
                            }
                            case "Wall Type":{
                                dbHandler.updateProject(projectId,item ,branchText.getText().toString(),0);
                                break;
                            }
                            case "Roof Type":{
                                dbHandler.updateProject(projectId,item ,branchText.getText().toString(),0);
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

    public void saveData(){

        DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);
        note = bNote.getText().toString();

        dbHandler.updateProject(projectId, "Project Note" , note, 0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (Edited = true) saveData();
    }
}
