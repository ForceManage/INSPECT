package com.forcemanage.inspect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    private InspectionActivity globalVariables;

    private static final String TAG = "Base Fragment";
    private TextView Text;
    private ImageView Img1;
    private ImageView Img2;
    private ImageView Img3;
    private ImageView Img4;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        globalVariables = (InspectionActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.base_fragment,container,false);
 //       btnInspection = (Button) view.findViewById(R.id.btnInspection);


        Log.d(TAG, "oncreateview: started");

        Text = (TextView) view.findViewById(R.id.Zone);
        Img1 = (ImageView) view.findViewById(R.id.imageView);
        Img2 = (ImageView) view.findViewById(R.id.imageView_file);
        Img3 = (ImageView) view.findViewById(R.id.imageView_cam);
        Img4 = (ImageView) view.findViewById(R.id.imageView_draw);

        Img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.photoframe = 1;
                globalVariables.mPhotoImageView = Img1;
                globalVariables.takeImageFromCamera(null);
            }
        });

        Img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                startActivityForResult(galleryIntent.createChooser(galleryIntent, "Select Picture"),1);

                globalVariables.takeImageFromCamera(null);
                globalVariables.photoframe = 1;
            }
        });
        return view;
    }



}
