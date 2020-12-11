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

        photoA = (ImageView) view.findViewById(R.id.photo);
        photo_cam = (ImageView) view.findViewById(R.id.imageView_cam);
        photo_cam.setOnClickListener(this);

        photo_draw = (ImageView) view.findViewById(R.id.imageView_draw);
        photo_draw.setOnClickListener(this);

        photo_file = (ImageView) view.findViewById(R.id.imageView_file);
        photo_file.setOnClickListener(this);



        photo_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aId > 0) {
                 //   globalVariables.photoframe = 0;
                    globalVariables.mPhotoImageView = photoA;
                    globalVariables.takeImageFromCamera(null);
                }
                else
                    Toast.makeText(getContext(), "Select/create a Document Item ",Toast.LENGTH_LONG).show();
            }
        });

        photoA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                globalVariables.mPhotoImageView = photoA;
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_PICK);
                galleryIntent.setType("image/*");
                globalVariables.startActivityForResult(galleryIntent.createChooser(galleryIntent, "Select Picture"), 1);
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
          //      globalVariables.filephoto = 1;
                Edited = true;
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
                //    File Image = new File(root + "/A2D_" + dirName + "/" );//+ photos[0]


                //    Uri data = FileProvider.getUriForFile(InspectionActivity.this,BuildConfig.APPLICATION_ID+".provider",Image);
                //    galleryIntent.setDataAndType(data,"image/*");
                //    String[] mimeTypes = {"image/jpeg", "image/png"};
                // galleryIntent.putExtra(galleryIntent.EXTRA_MIME_TYPES,mimeTypes);

                //startActivityForResult(galleryIntent, ACTIVITY_GET_FILE);
                // startActivityForResult(galleryIntent.createChooser(galleryIntent, "Select Picture"),1);



         //       globalVariables.startActivityForResult(galleryIntent.createChooser(galleryIntent, "Select Picture"), globalVariables.ACTIVITY_GET_FILE);
            }


        });

        if (image == null)
            image = "";

        if (image.length() > 12) {
            String dirName = image.substring(6, 14);
            String root = Environment.getExternalStorageDirectory().toString();
            File Image = new File(root + "/A2D_" + dirName + "/" + image);
            Bitmap myBitmap = BitmapFactory.decodeFile(Image.getAbsolutePath());
            photoA.setImageBitmap(myBitmap);
        }



        return view;
    }


    @Override
    public void onClick(View v) {

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