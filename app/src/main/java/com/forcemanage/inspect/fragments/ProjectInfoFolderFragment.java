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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.forcemanage.inspect.BuildConfig;
import com.forcemanage.inspect.DBHandler;
import com.forcemanage.inspect.DetailProjectFragment;
import com.forcemanage.inspect.GlobalVariables;
import com.forcemanage.inspect.InspectionActivity;
import com.forcemanage.inspect.MainActivity;
import com.forcemanage.inspect.MyConfig;
import com.forcemanage.inspect.ProjectDetailFragment;
import com.forcemanage.inspect.ProjectViewFragment;
import com.forcemanage.inspect.ProjectViewList;
import com.forcemanage.inspect.R;
import com.forcemanage.inspect.attributes.ProjectData;
import com.forcemanage.inspect.attributes.ProjectNode;
import com.forcemanage.inspect.tabchangelistener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProjectInfoFolderFragment extends Fragment implements  View.OnClickListener {

    private MainActivity globalVariables;

    private static final String TAG = "Project Info Fragment";


    private TextView branch;
    private EditText bNote;
    private ImageView mPhotoImageView;
    private ImageView photo_cam;
    private ImageView info_file;
    private ImageView photo_file;
    private String projectId;
    private int projId = 0;
    private int iId = 0;
    private String propPhoto;
    private  int aId;
    private int Level;
    private int USER_ID = 0;


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
                 Intent galleryIntent = new Intent(Intent.ACTION_VIEW);
                Uri data = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", propImage);
                // Uri data = Uri.parse(propImage.getAbsolutePath());
                galleryIntent.setDataAndType(data, "*/*");

                try {

                    startActivity(Intent.createChooser(galleryIntent, "OPEN"));
                    // startActivity(galleryIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("tag", "Exception found while listing " + e);

                }

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
