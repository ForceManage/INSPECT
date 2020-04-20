package com.forcemanage.inspect;

import android.app.Activity;
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

import java.io.File;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.GRAY;
import static android.graphics.Color.WHITE;

public class BaseFragment extends Fragment implements View.OnClickListener{

    private InspectionActivity globalVariables;

    private static final String TAG = "Base Fragment";
    private static final int ACTIVITY_DRAW_FILE = 2;
    private TextView title;
    private TextView branch;
    private EditText bNote;
    private ImageView photoA;
    private ImageView photo_cam;
    private ImageView photo_draw;
    private ImageView photo_file;


    private String branchHead ="";
    private String branchLabel = "";
    private String branchNote = "";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            branchHead = bundle.getString("branchHead");
            branchLabel = bundle.getString("branchLabel");
            branchNote = bundle.getString("notes");
        }


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        globalVariables = (InspectionActivity) getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.base_fragment,container,false);


        Log.d(TAG, "oncreateview: started");

        title = (TextView) view.findViewById(R.id.title);
        branch = (TextView) view.findViewById(R.id.level);
        bNote = (EditText) view.findViewById(R.id.note);
        photoA = (ImageView) view.findViewById(R.id.imageView);
        photo_cam = (ImageView) view.findViewById(R.id.imageView_cam);
        photo_cam.setOnClickListener(this);

        photo_draw = (ImageView) view.findViewById(R.id.imageView_draw);
        photo_draw.setOnClickListener(this);

        photo_file = (ImageView) view.findViewById(R.id.imageView_file);
        photo_file.setOnClickListener(this);


         setText();



        bNote.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) globalVariables.Edited = true;

            }
        });



        if (globalVariables.photoBranch.length() > 12) {
            String dirName = globalVariables.photoBranch.substring(6, 14);
            String root = Environment.getExternalStorageDirectory().toString();
            File Image = new File(root + "/ESM_" + dirName + "/" + globalVariables.photoBranch);
            Bitmap myBitmap = BitmapFactory.decodeFile(Image.getAbsolutePath());
            photoA.setImageBitmap(myBitmap);
         }


        photo_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.photoframe = 1;
                globalVariables.mPhotoImageView = photoA;
                globalVariables.takeImageFromCamera(null);
                globalVariables.Edited = true;

            }
        });

        photo_draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!globalVariables.photo1.equals("")) {
                    String dirName = globalVariables.photo1.substring(6, 14);
                    String root = Environment.getExternalStorageDirectory().toString();
                    File photo_image = new File(root + "/ESM_" + dirName + "/" + globalVariables.photo1);


                    Intent galleryIntent = new Intent();
                    galleryIntent.setAction(Intent.ACTION_VIEW);


                    Uri data = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", photo_image);
                    // Uri data = Uri.parse(photo_image.getAbsolutePath());

                    galleryIntent.setDataAndType(data, "image/*");
                    startActivityForResult(galleryIntent.createChooser(galleryIntent, "Select Picture"), ACTIVITY_DRAW_FILE);

                }


            }

        });

        photo_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.filephoto = 1;
                globalVariables.Edited = true;
                Intent galleryIntent = new Intent();
                // galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                // galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                // galleryIntent.setAction(Intent.ACTION_VIEW);
                galleryIntent.setAction(Intent.ACTION_PICK);
                galleryIntent.setType("image/*");
                //   String[] mimetypes = {"image/*"};
                //   galleryIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes); //            setType("image/*");
                //     dirName = photos[0].substring(6, 14);
                //     String root = Environment.getExternalStorageDirectory().toString();
                //    File Image = new File(root + "/ESM_" + dirName + "/" );//+ photos[0]


                //    Uri data = FileProvider.getUriForFile(InspectionActivity.this,BuildConfig.APPLICATION_ID+".provider",Image);
                //    galleryIntent.setDataAndType(data,"image/*");
                //    String[] mimeTypes = {"image/jpeg", "image/png"};
                // galleryIntent.putExtra(galleryIntent.EXTRA_MIME_TYPES,mimeTypes);

                //startActivityForResult(galleryIntent, ACTIVITY_GET_FILE);
               // startActivityForResult(galleryIntent.createChooser(galleryIntent, "Select Picture"),1);
                globalVariables.startActivityForResult(galleryIntent.createChooser(galleryIntent, "Select Picture"),globalVariables.ACTIVITY_GET_FILE);
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
                                globalVariables.editLocation(LocationText.getText().toString());


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
}