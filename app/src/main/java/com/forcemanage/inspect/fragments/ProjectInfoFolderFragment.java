package com.forcemanage.inspect.fragments;

import android.content.Context;
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

import com.forcemanage.inspect.BuildConfig;
import com.forcemanage.inspect.DBHandler;
import com.forcemanage.inspect.MainActivity;
import com.forcemanage.inspect.R;

import java.io.File;

public class ProjectInfoFolderFragment extends Fragment implements  View.OnClickListener {

    private MainActivity globalVariables;

    private static final String TAG = "Project Info Fragment";

    private static final int PDF_SELECTION_CODE = 4;
    private TextView branch;
    private EditText bNote;
    private ImageView mPhotoImageView;
    private ImageView cam1;
    private ImageView info_file;
    private ImageView photo_file;
    private String projectId;
    private int projId = 0;
    private int iId = 0;
    private String propPhoto;
    private  int aId;
    private int Level;
    private int USER_ID = 0;
    private Boolean Edited = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {


            propPhoto = bundle.getString("folderPhoto");

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

        View view = inflater.inflate(R.layout.project_info_folder, container, false);


        Log.d(TAG, "oncreateview: started");


        mPhotoImageView = (ImageView) view.findViewById(R.id.photo);
        cam1 = (ImageView) view.findViewById(R.id.cam1);
        cam1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.mPhotoImageView = mPhotoImageView;
                globalVariables.takeImageFromCamera(null);
                Edited = true;
            }
        });

        info_file = (ImageView) view.findViewById(R.id.imageView_info);
        info_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
             //   intent.addCategory(Intent.ACTION_EDIT);
             //  Intent intent = new Intent(Intent.ACTION_EDIT);
            //   Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            //   intent.addCategory(Intent.ACTION_VIEW);

            //   String root = Environment.getExternalStorageDirectory().getPath();

           //    File pdf = new File(root + "/14081INFO/19004.pdf" );

                File pdf = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ABC.pdf");

              //  File pdf = new File(root + "/A2D_20200518/3_2_3.pdf" );
              //   File pdf = new File(root + "/14081INFO/" );
                Uri uri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", pdf);
             //   intent.setDataAndType(uri, "application/pdf");
                intent.setDataAndType(uri, "application/pdf");

             //   intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
             //   intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(Intent.createChooser(intent, "Open folder"));

             //   globalVariables.startActivityForResult(intent, PDF_SELECTION_CODE);


/*
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);



                // Optionally, specify a URI for the file that should appear in the
                // system file picker when it loads.
                String root = Environment.getExternalStorageDirectory().getPath();
                File pdf = new File(root + "/14081INFO/" );
                Uri uri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", pdf);
                intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
               intent.setDataAndType(Uri.parse(root + "/14081INFO/"), "application/pdf" );

 */


             }


        });


        photo_file = (ImageView) view.findViewById(R.id.imageView_file);

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





        if (propPhoto == null)
            propPhoto = "";

        if (propPhoto.length() > 12) {
            String dirName = propPhoto.substring(6, 14);
            String root = Environment.getExternalStorageDirectory().toString();
            File Image = new File(root + "/A2D_" + dirName + "/" + propPhoto);
            Bitmap myBitmap = BitmapFactory.decodeFile(Image.getAbsolutePath());
            mPhotoImageView.setImageBitmap(myBitmap);
        }

        return view;
    }


    @Override
    public void onClick(View v) {

    }




}
