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
 /*           bundle.putString("branchHead", projectItem.get(MyConfig.TAG_PROJECT_ADDRESS));
            bundle.putString("branchLabel", branchLabel);
            bundle.putString("address", projectItem.get(MyConfig.TAG_PROJECT_ADDRESS) + ", " + projectItem.get(MyConfig.TAG_PROJECT_SUBURB));
            bundle.putString("buildType", projectItem.get(MyConfig.TAG_BUILD_TYPE));
            bundle.putString("permit", branchNote);
            bundle.putString("class", branchNote);
            bundle.putString("levels", branchNote);
            bundle.putString("photo", com2);
            bundle.putString("key", com2);
            bundle.putString("floor", com2);
            bundle.putString("roof", com2);
            bundle.putString("wall", com2);
            bundle.putString("notes", com2);

  */
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

       TextView projAddress = (TextView) view.findViewById(R.id.Text1);
       TextView projNumber = (TextView) view.findViewById(R.id.branchTitle);

       projAddress.setText(ProjAddress);
       projNumber.setText("Project ID:  "+branchHead);
       bNote.setText(note);

         setText();


/*
        bNote.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) globalVariables.Edited = true;

            }
        });




        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View promptView = layoutInflater.inflate(R.layout.add_location, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setView(promptView);
                final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                itemTitle.setText("Branch Head Title: "+ branchHead);//Integer.parseInt(locationId)
                final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                locationText.setText("Current label : "+ branchLabel );//Integer.parseInt(locationId)
                final EditText LocationText = (EditText) promptView.findViewById(R.id.locationtext);
                LocationText.setText(branchLabel);
                // setup a dialog window
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                          //      globalVariables.editLocation(LocationText.getText().toString());


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
        });

    */

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

    public void saveData(){

        DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);
        note = bNote.getText().toString();

        dbHandler.updateProject(projectId, note);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (Edited = true) saveData();
    }
}
