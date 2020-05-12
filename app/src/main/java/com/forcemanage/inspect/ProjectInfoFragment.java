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
    private String infoA;
    private String infoB;
    private String infoC;
    private String infoD;
    private String infoE;
    private String infoH;
    private String infoI;
    private String infoJ;



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


            infoA = bundle.getString("infoA");
            infoB = bundle.getString("infoB");
            infoC = bundle.getString("infoC");
            infoD = bundle.getString("infoD");
            infoE = bundle.getString("infoE");
            infoI = bundle.getString("infoI");
            infoJ = bundle.getString("infoJ");

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

        final TextView TVinfoA = (TextView) view.findViewById(R.id.Text2);
        TVinfoA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("infoA",TVinfoA.getText().toString());
            }
        });

        final TextView TVinfoB = (TextView) view.findViewById(R.id.Text3);
        TVinfoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("infoB",TVinfoB.getText().toString());
            }
        });

        final TextView TVinfoC = (TextView) view.findViewById(R.id.Text4);
        TVinfoC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("infoC",TVinfoC.getText().toString());
            }
        });

        final TextView TVinfoD = (TextView) view.findViewById(R.id.Text5);
        TVinfoD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("infoD",TVinfoD.getText().toString());
            }
        });
        final TextView TVinfoE = (TextView) view.findViewById(R.id.Text6);
        TVinfoE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("infoE",TVinfoE.getText().toString());
            }
        });

        final TextView TVinfoH = (TextView) view.findViewById(R.id.Text7);
        TVinfoH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("infoH", TVinfoH.getText().toString());
            }
        });

        final TextView TVinfoI = (TextView) view.findViewById(R.id.Text8);
        TVinfoI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("infoI", TVinfoI.getText().toString());
            }
        });

        final TextView TVinfoJ = (TextView) view.findViewById(R.id.Text9);
        TVinfoJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("infoJ", TVinfoJ.getText().toString());
            }
        });

         setText();
        TVinfoA.setText(infoA);
        TVinfoB.setText(infoB);
        TVinfoC.setText(infoC);
        TVinfoD.setText(infoD);
        TVinfoE.setText(infoE);
        TVinfoH.setText(infoH);
        TVinfoI.setText(infoI);
        TVinfoJ.setText(infoJ);


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
                            case "infoA":{
                                dbHandler.updateProject(projectId,item ,branchText.getText().toString(),0);
                                globalVariables.OnSelectionChanged(0);
                                break;
                            }
                            case "infoB":{
                                dbHandler.updateProject(projectId,item ,branchText.getText().toString(),0);
                                globalVariables.OnSelectionChanged(0);
                                break;
                            }
                            case "infoC":{
                                dbHandler.updateProject(projectId,item ,branchText.getText().toString(),0 );
                                globalVariables.OnSelectionChanged(0);
                                break;
                            }
                            case "infoD":{
                                dbHandler.updateProject(projectId,item ,branchText.getText().toString(),0);
                                globalVariables.OnSelectionChanged(0);
                                break;
                            }
                            case "infoE":{
                                dbHandler.updateProject(projectId,item ,branchText.getText().toString(),0);
                                break;
                            }
                            case "infoH":{
                                dbHandler.updateProject(projectId,item ,branchText.getText().toString(),0);
                                break;
                            }
                            case "infoI":{
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
