package com.forcemanage.inspect.fragments;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.forcemanage.inspect.BuildConfig;
import com.forcemanage.inspect.DBHandler;
import com.forcemanage.inspect.InspectionActivity;
import com.forcemanage.inspect.MainActivity;
import com.forcemanage.inspect.R;

import java.io.File;

public class BaseInfoFragment extends Fragment implements View.OnClickListener {

    private MainActivity globalVariables;

    private static final String TAG = "Base Fragment";
    private static final int ACTIVITY_DRAW_FILE = 2;
    private TextView title;
    private TextView branch;
    private TextView activity;
    private TextView TabId;
    private EditText bNote;
    private ImageView photoA;
    private ImageView photo_cam;
    private ImageView photo_draw;
    private ImageView photo_file;
    private Button reportBtn;

    private Boolean Edited = false;
    private String branchHead = "";
    private String branchLabel = "";
    private String branchNote = "";
    private String inspection;
    private String projectId;
    private String inspectionId;
    private Integer projId;
    private Integer iId;
    private String image;
    private int aId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            branchHead = bundle.getString("branchHead");
            branchLabel = bundle.getString("MAP_LABEL");
            inspection = bundle.getString("inspection");
            branchNote = bundle.getString("notes");
            projectId = bundle.getString("projectID");
            inspectionId = bundle.getString("inspectionID");
            image = bundle.getString("image");
            aId = bundle.getInt("aID");
        }


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        globalVariables = (MainActivity) getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.base_info, container, false);


        Log.d(TAG, "oncreateview: started");
        if(projectId != null) {
            projId = Integer.parseInt(projectId);
            iId = Integer.parseInt(inspectionId);
        }
        title = (TextView) view.findViewById(R.id.title);
        //  activity = (TextView) view.findViewById(R.id.level);
        branch = (TextView) view.findViewById(R.id.Text1);
        TabId = (TextView) view.findViewById(R.id.aId);
        branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editLabel("Label",branch.getText().toString());
            }
        });

        bNote = (EditText) view.findViewById(R.id.note);


        setText();


        bNote.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) Edited = true;
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
                itemTitle.setText("Branch Head Title: " + branchHead);//Integer.parseInt(locationId)
                final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                locationText.setText("Current label : " + branchLabel);//Integer.parseInt(locationId)
                final EditText LocationText = (EditText) promptView.findViewById(R.id.locationtext);
                LocationText.setText(branchLabel);
                // setup a dialog window
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if(aId > 0) {
                                    globalVariables.editLocation(LocationText.getText().toString());
                                }
                                else
                                    Toast.makeText(getContext(), "Select/create a MAP branch ",Toast.LENGTH_LONG).show();
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


        return view;
    }

    private void setText() {

        if (!branchHead.equals("")) {
            title.setText(branchHead);
            //      activity.setText("Activity title:  "+inspection);
            branch.setText(branchLabel);
            bNote.setText(branchNote);
            TabId.setText(Integer.toString(aId));
        }
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
        itemTitle.setText("Activity Title ");//Integer.parseInt(locationId)
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

                            case "Label":{
                                branchLabel = branchText.getText().toString();
                                if(aId > 0) {
                                    dbHandler.updateMapLabel(Integer.parseInt(projectId), aId, branchLabel);
                                    globalVariables.OnTabChanged(0);
                                }
                                else
                                    Toast.makeText(getContext(), "Select/create a MAP branch ",Toast.LENGTH_LONG).show();
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

        if (Edited) {
            DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);
            // String serviceDate = inspectionDate.getText().toString();
            // work out the next service date in three months time
            dbHandler.updateMap(projId, aId, bNote.getText().toString());
            dbHandler.statusChanged(projId,iId);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (Edited) {
            DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);
            // String serviceDate = inspectionDate.getText().toString();
            // work out the next service date in three months time
            if(projectId != null) {
                dbHandler.updateMap(projId, aId, bNote.getText().toString());
                dbHandler.statusChanged(projId,iId);
            }
        }
    }
}
