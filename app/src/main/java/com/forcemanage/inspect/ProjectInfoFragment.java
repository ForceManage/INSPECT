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
    private ImageView mPhotoImageView;
    private ImageView photo_cam;
    private ImageView info_file;
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
    private String infoF;
    private String infoG;
    private String infoH;



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
            infoF = bundle.getString("infoF");
            infoG = bundle.getString("infoG");
            infoH = bundle.getString("infoH");

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
        bNote = (EditText) view.findViewById(R.id.note);
        bNote.setText(note);


        final TextView TVprojectID = (TextView) view.findViewById(R.id.branchTitle);
        TVprojectID.setText("Project ID:  "+branchHead);
        TVprojectID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              editProject("Project ID",TVprojectID.getText().toString());
            }
        });

        final TextView projAddress = (TextView) view.findViewById(R.id.Text1);
        projAddress.setText(ProjAddress);
        projAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Project Title",projAddress.getText().toString());
            }
        });

        final TextView TVinfoA = (TextView) view.findViewById(R.id.Text2);
        TVinfoA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Note A",TVinfoA.getText().toString());
            }
        });

        final TextView TVinfoB = (TextView) view.findViewById(R.id.Text3);
        TVinfoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Note B",TVinfoB.getText().toString());
            }
        });

        final TextView TVinfoC = (TextView) view.findViewById(R.id.Text4);
        TVinfoC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Note C",TVinfoC.getText().toString());
            }
        });

        final TextView TVinfoD = (TextView) view.findViewById(R.id.Text5);
        TVinfoD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Note D",TVinfoD.getText().toString());
            }
        });
        final TextView TVinfoE = (TextView) view.findViewById(R.id.Text6);
        TVinfoE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Note E",TVinfoE.getText().toString());
            }
        });

        final TextView TVinfoF = (TextView) view.findViewById(R.id.Text7);
        TVinfoF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Note F", TVinfoF.getText().toString());
            }
        });

        final TextView TVinfoG = (TextView) view.findViewById(R.id.Text8);
        TVinfoG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Note G", TVinfoG.getText().toString());
            }
        });

        final TextView TVinfoH = (TextView) view.findViewById(R.id.Text9);
        TVinfoH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Note H", TVinfoH.getText().toString());
            }
        });


        mPhotoImageView = (ImageView) view.findViewById(R.id.photo);
        photo_cam = (ImageView) view.findViewById(R.id.imageView_cam);
        photo_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.mPhotoImageView = mPhotoImageView;
                globalVariables.takeImageFromCamera(null);
            }
        });

        info_file = (ImageView) view.findViewById(R.id.imageView_info);
        info_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String root = Environment.getExternalStorageDirectory().getPath();
                File propImage = new File(root + "/" + projectId + "INFO/");
                //  File propImage = new File(root, propId+"INFO/");
                //  File propImage = new File(root, "ESM/test.jpg");
                //  String dir = propId+"INFO/";
                //  File propImage = new File(root);
                Intent galleryIntent = new Intent(Intent.ACTION_VIEW);

                Uri data = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", propImage);
                // Uri data = Uri.parse(propImage.getAbsolutePath());
                galleryIntent.setDataAndType(data, "*/*");

                // galleryIntent.setDataAndType(Uri.withAppendedPath(Uri.fromFile(propImage) ,dir),"image/*" );
                // galleryIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                // galleryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                try {

                    //    startActivityForResult(Intent.createChooser(galleryIntent, "SELECT FILE"), REQUEST_OPEN_RESULT_CODE);
                    //  startActivity(galleryIntent);
                    startActivity(Intent.createChooser(galleryIntent, "OPEN"));
                    // startActivity(galleryIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("tag", "Exception found while listing " + e);


                }

            }
        });


        if(infoA == "null")TVinfoA.setText("Note A"); else TVinfoA.setText(infoA);
        if(infoB == "null")TVinfoB.setText("Note B"); else TVinfoB.setText(infoB);
        if(infoC == "null")TVinfoC.setText("Note C"); else TVinfoC.setText(infoC);
        if(infoD == "null")TVinfoD.setText("Note D"); else TVinfoD.setText(infoD);
        if(infoE == "null")TVinfoE.setText("Note E"); else TVinfoE.setText(infoE);
        if(infoF == "null")TVinfoF.setText("Note F"); else TVinfoF.setText(infoF);
        if(infoG == "null")TVinfoG.setText("Note G"); else TVinfoG.setText(infoG);
        if(infoH == "null")TVinfoH.setText("Note H"); else TVinfoH.setText(infoH);


        if (globalVariables.propPhoto == null)
            globalVariables.propPhoto = "";

        if (globalVariables.propPhoto.length() > 12) {
            String dirName = globalVariables.propPhoto.substring(6, 14);
            String root = Environment.getExternalStorageDirectory().toString();
            File Image = new File(root + "/ESM_" + dirName + "/" + globalVariables.propPhoto);
            Bitmap myBitmap = BitmapFactory.decodeFile(Image.getAbsolutePath());
            mPhotoImageView.setImageBitmap(myBitmap);
        }
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




    @Override
    public void onClick(View v) {

    }


    public void editProject(final String  item, String value){

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View promptView = layoutInflater.inflate(R.layout.add_location, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptView);
        final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
        itemTitle.setText("Project Information ");//Integer.parseInt(locationId)
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

                            case "Project Title":{
                                dbHandler.updateProject(projectId,item ,branchText.getText().toString(),0);
                                globalVariables.OnSelectionChanged(0);
                                break;
                            }
                            case "Project ID":{
                                dbHandler.updateProject(projectId,item ,branchText.getText().toString(),0);
                                globalVariables.OnSelectionChanged(0);
                                break;
                            }
                            case "Note A":{
                                dbHandler.updateProject(projectId,"infoA" ,branchText.getText().toString(),0);
                                globalVariables.OnSelectionChanged(0);
                                break;
                            }
                            case "Note B":{
                                dbHandler.updateProject(projectId,"infoB" ,branchText.getText().toString(),0);
                                globalVariables.OnSelectionChanged(0);
                                break;
                            }
                            case "Note C":{
                                dbHandler.updateProject(projectId,"infoC",branchText.getText().toString(),0 );
                                globalVariables.OnSelectionChanged(0);
                                break;
                            }
                            case "Note D":{
                                dbHandler.updateProject(projectId,"infoD",branchText.getText().toString(),0);
                                globalVariables.OnSelectionChanged(0);
                                break;
                            }
                            case "Note E":{
                                dbHandler.updateProject(projectId,"infoE",branchText.getText().toString(),0);
                                break;
                            }
                            case "Note F":{
                                dbHandler.updateProject(projectId,"infoF",branchText.getText().toString(),0);
                                break;
                            }
                            case "Note G":{
                                dbHandler.updateProject(projectId,"infoG",branchText.getText().toString(),0);
                                break;
                            }
                            case "Note H":{
                                dbHandler.updateProject(projectId,"infoH",branchText.getText().toString(),0);
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
