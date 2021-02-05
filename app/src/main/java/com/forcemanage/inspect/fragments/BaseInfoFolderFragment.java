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
import com.forcemanage.inspect.MainActivity;
import com.forcemanage.inspect.R;

import java.io.File;

public class BaseInfoFolderFragment extends Fragment implements View.OnClickListener {

    private MainActivity globalVariables;

    private static final String TAG = "Base Fragment";
    private static final int ACTIVITY_DRAW_FILE = 2;
    private TextView title;
    private TextView branch;
    private TextView activity;
    private TextView TabId;
    private EditText bNote;
    private ImageView photo_cam;
    private ImageView photo_draw;
    private ImageView photo_file;
    private ImageView mPhotoImageView;
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
            aId = bundle.getInt("aID");
            image = bundle.getString("image");
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

        View view = inflater.inflate(R.layout.base_info_folder, container, false);


        Log.d(TAG, "oncreateview: started");
        if(projectId != null) {
            projId = Integer.parseInt(projectId);
            iId = Integer.parseInt(inspectionId);
        }
        mPhotoImageView = (ImageView) view.findViewById(R.id.photo);
        photo_cam = (ImageView) view.findViewById(R.id.imageView_cam);
        photo_cam.setOnClickListener(this);

        photo_draw = (ImageView) view.findViewById(R.id.imageView_draw);
        photo_draw.setOnClickListener(this);

        photo_file = (ImageView) view.findViewById(R.id.imageView_file);
        photo_file.setOnClickListener(this);

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


        branch.setText(branchLabel);
        bNote.setText(branchNote);


        bNote.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) Edited = true;
            }
        });



        photo_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aId > 0) {
                 //   globalVariables.photoframe = 0;
                    globalVariables.mPhotoImageView = mPhotoImageView;
                    globalVariables.takeImageFromCamera(null);
                }
                else
                    Toast.makeText(getContext(), "Select/create a folder topic tab. ",Toast.LENGTH_LONG).show();
            }
        });


        photo_draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!globalVariables.photoBranch.equals("")) {
                    String dirName = globalVariables.photoBranch.substring(6, 14);
                    String root = Environment.getExternalStorageDirectory().toString();
                    File photo_image = new File(root + "/A2D_" + dirName + "/" + globalVariables.photo);


                    Intent galleryIntent = new Intent();
                    galleryIntent.setAction(Intent.ACTION_VIEW);


                   Uri data = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", photo_image);
                    // Uri data = Uri.parse(photo_image.getAbsolutePath());

                    galleryIntent.setDataAndType(data, "image/*");
                    startActivityForResult(galleryIntent.createChooser(galleryIntent, "Select Picture"), ACTIVITY_DRAW_FILE);

                }
                else  Toast.makeText(getActivity(), "Function requires a photograph",Toast.LENGTH_SHORT).show();


            }

        });

        photo_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               globalVariables.mPhotoImageView = mPhotoImageView;
                Intent galleryIntent = new Intent();

                galleryIntent.setAction(Intent.ACTION_PICK);
                galleryIntent.setType("image/*");

                globalVariables.startActivityForResult(galleryIntent.createChooser(galleryIntent, "Select Picture"), 1);


            }


        });




        if (image == null)
            image = "";

        if (image.length() > 12) {
            String dirName = image.substring(6, 14);
            String root = Environment.getExternalStorageDirectory().toString();
            File Image = new File(root + "/A2D_" + dirName + "/" + image);
            Bitmap myBitmap = BitmapFactory.decodeFile(Image.getAbsolutePath());
            mPhotoImageView.setImageBitmap(myBitmap);
        }



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
            dbHandler.updateMap(projectId, aId, bNote.getText().toString());
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
                dbHandler.updateMap(projectId, aId, bNote.getText().toString());
                dbHandler.statusChanged(projId,iId);
            }
        }
    }
}
