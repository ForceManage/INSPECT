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
import android.widget.LinearLayout;
import android.widget.TextView;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InspectInfoFragment extends Fragment implements View.OnClickListener{

    private InspectionActivity globalVariables;

    private static final String TAG = "Project Info Fragment";

    private TextView title;
    private TextView Label;
    private EditText bNote;
    private EditText Note2;
    private EditText DumbV;
    private ImageView mPhotoImageView;
    private ImageView photo_cam;
    private ImageView photo_file;
    private ImageView info_file;
    private String branchHead ="";
    private String branchLabel = "";
    private String branchNote = "";
    private String inspectionDate;
    private String typeInspection;
    private String auditor;
    private String note;
    private String note_2;
    private Boolean Edited = false;
    private Integer projId;
    private Integer iId;
    private String dateInspected;
    private String startTime;
    private String endTime;
    private String inspPhoto;
    private ImageView printer;







    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            branchHead = bundle.getString("branchHead");
            branchLabel = bundle.getString("branchLabel");
            inspectionDate = bundle.getString("date");
            projId = bundle.getInt("projectId");
            iId = bundle.getInt("inspectionId");
            note = bundle.getString("note");
            note_2 = bundle.getString("note_2");
            auditor = bundle.getString("auditor");
            dateInspected = bundle.getString("date");
            typeInspection = bundle.getString("inspectType");
            startTime = bundle.getString("startTime");
            endTime = bundle.getString("endTime");
            inspPhoto = bundle.getString("inspPhoto");


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

        View view = inflater.inflate(R.layout.inspect_info,container,false);

        DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);
        Log.d(TAG, "oncreateview: started");

        Edited = false;




        title = (TextView) view.findViewById(R.id.branchTitle);
 //       branch = (TextView) view.findViewById(R.id.level);
        bNote = (EditText) view.findViewById(R.id.note);
        Note2 = (EditText) view.findViewById(R.id.preamble);
        Label = (TextView) view.findViewById(R.id.Text1);
        printer = (ImageView) view.findViewById(R.id.printer);
        TextView inspectDate = (TextView) view.findViewById(R.id.Text2);
        TextView Hrs = (TextView) view.findViewById(R.id.Text5);
        TextView inspectionType = (TextView) view.findViewById(R.id.Text3);
        TextView inspectedDate = (TextView) view.findViewById(R.id.Text4);
   //     TextView inspector = (TextView) view.findViewById(R.id.Text5);
        printer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         //      globalVariables.reportMenu();

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
                File propImage = new File(root + "/" + Integer.toString(projId) + "INFO/");
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



   //      if (!endTime.equals("null"))
  //       endTime = "2020-01-01 01:01:00";

         inspectDate.setText("File created:  "+stringdate(inspectionDate,1));
         inspectionType.setText("File type:  "+typeInspection);
         if(!startTime.equals("null"))
         inspectedDate.setText("Initial log recorded: "+stringdate(startTime,2)+"  -  "+stringdate(endTime,2));
         Hrs.setText("Allocation:  " + dbHandler.calcTime(Integer.toString(projId), Integer.toString(iId)));

    //     inspector.setText("Auditor:  "+ auditor);

         bNote.setText(note);
         Note2.setText(note_2);
        Label.setText(branchLabel);
        setText();
        photo_file = (ImageView) view.findViewById(R.id.imageView_file);

        photo_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                globalVariables.mPhotoImageView = mPhotoImageView;
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
                globalVariables.startActivityForResult(galleryIntent.createChooser(galleryIntent, "Select Picture"), 1);
            }


        });


        if (inspPhoto == null)
            inspPhoto = "";

        if (inspPhoto.length() > 12) {
            String dirName = inspPhoto.substring(6, 14);
            String root = Environment.getExternalStorageDirectory().toString();
            File Image = new File(root + "/A2D_" + dirName + "/" + inspPhoto);
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

        Note2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
           title.setText("File");
  //         branch.setText(branchLabel);
           }
    }


    @Override
    public void onClick(View v) {

    }

    public String stringdate(String date, int type){

        switch (type) {

            case 1: {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                Date d = null;
                try {
                    d = sdf.parse(date);
                } catch (ParseException ex) {
                    Log.v("Exception", ex.getLocalizedMessage());
                }
                sdf.applyPattern("dd MMM yyyy");
                date = sdf.format(d);
                break;
            }
            case 2: {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d = null;
                try {
                    d = sdf.parse(date);
                } catch (ParseException ex) {
                    Log.v("Exception", ex.getLocalizedMessage());
                }
                sdf.applyPattern("dd MMM yyyy, HH:mm");
                date = sdf.format(d);
                break;

            }

            case 3: {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d = null;
                try {
                    d = sdf.parse(date);
                } catch (ParseException ex) {
                    Log.v("Exception", ex.getLocalizedMessage());
                }
                sdf.applyPattern("HH:mm");
                date = sdf.format(d);
                break;

            }

        }

        return date;
    }


    public void saveData(){

        DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);
        note = bNote.getText().toString();
        note_2 = Note2.getText().toString();

        dbHandler.updateInspectionNotes(projId, iId, note, note_2);
        dbHandler.statusChanged(projId, iId);
        Edited = false;

}

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (Edited) saveData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (Edited) saveData();
    }
}
