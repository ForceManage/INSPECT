package com.forcemanage.inspect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import androidx.fragment.app.Fragment;

import java.io.File;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.GRAY;
import static android.graphics.Color.WHITE;

public class BaseFragment extends Fragment implements View.OnClickListener{

    private InspectionActivity globalVariables;

    private static final String TAG = "Base Fragment";
    private TextView title;
    private TextView branch;
    private EditText bNote;
    private ImageView Img1;
    private ImageView Img2;
    private ImageView Img3;
    private ImageView Img4;


    private String branchTitle ="";
    private String branchtxt = "";
    private String branchNote = "";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            branchTitle = bundle.getString("aID");
            branchtxt = bundle.getString("label");
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
        Img1 = (ImageView) view.findViewById(R.id.imageView);
        Img2 = (ImageView) view.findViewById(R.id.imageView_file);
        Img3 = (ImageView) view.findViewById(R.id.imageView_cam);
        Img4 = (ImageView) view.findViewById(R.id.imageView_draw);



         setText();



        bNote.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) globalVariables.Edited = true;

            }
        });



        if (globalVariables.photo1.length() > 12) {
            String dirName = globalVariables.photo1.substring(6, 14);
            String root = Environment.getExternalStorageDirectory().toString();
            File Image = new File(root + "/ESM_" + dirName + "/" + globalVariables.photo1);
            Bitmap myBitmap = BitmapFactory.decodeFile(Image.getAbsolutePath());
            Img1.setImageBitmap(myBitmap);
            branch.setText(globalVariables.photo1);
        }


        Img3.setOnClickListener(new View.OnClickListener() {
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


            }
        });
        return view;
    }

    private void setText(){

        if (!branchTitle.equals("")){
           title.setText(branchTitle);
           branch.setText(branchtxt);
           bNote.setText(branchNote);
        }
    }


    @Override
    public void onClick(View v) {

    }
}
