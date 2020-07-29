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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InspectInfoFragment extends Fragment implements View.OnClickListener{

    private MainActivity globalVariables;


    private static final String TAG = "Project Info Fragment";

    private TextView title;
    private TextView Label;
    private EditText bNote;
    private ImageView mPhotoImageView;
    private ImageView photo_cam;
    private ImageView info_file;
    private String branchHead ="";
    private String branchLabel = "";
    private String branchNote = "";
    private String inspectionDate;
    private String typeInspection;
    private String auditor;
    private String note;
    private Boolean Edited;
    private String projectId;
    private String inspectionId;
    private String dateInspected;
    private String startTime;
    private String endTime;
    private Button reportBtn;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            branchHead = bundle.getString("branchHead");
            branchLabel = bundle.getString("branchLabel");
            inspectionDate = bundle.getString("date");
            projectId = bundle.getString("projectId");
            inspectionId = bundle.getString("inspectionId");
            note = bundle.getString("note");
            auditor = bundle.getString("auditor");
            dateInspected = bundle.getString("date");
            typeInspection = bundle.getString("inspectType");
            startTime = bundle.getString("startTime");
            endTime = bundle.getString("endTime");
            Edited = false;

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

        View view = inflater.inflate(R.layout.inspect_info,container,false);


        Log.d(TAG, "oncreateview: started");

        title = (TextView) view.findViewById(R.id.branchTitle);
 //       branch = (TextView) view.findViewById(R.id.level);
        bNote = (EditText) view.findViewById(R.id.note);

        Label = (TextView) view.findViewById(R.id.Text1);
        Label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editLabel("Label",Label.getText().toString());
            }
        });
        TextView inspectDate = (TextView) view.findViewById(R.id.Text2);
        TextView inspectionType = (TextView) view.findViewById(R.id.Text3);
        TextView inspectedDate = (TextView) view.findViewById(R.id.Text4);
        TextView inspector = (TextView) view.findViewById(R.id.Text5);
        Button inspectionBtn = (Button) view.findViewById(R.id.InspectionButton);
        Button reportBtn = (Button) view.findViewById(R.id.btnViewReport);
        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               globalVariables.reportMenu();

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


         setText();
   //      if (!endTime.equals("null"))
  //       endTime = "2020-01-01 01:01:00";

         inspectDate.setText("Activity created:  "+stringdate(inspectionDate,1));
         inspectionType.setText("Type of Activity:  "+typeInspection);
         if(!startTime.equals("null"))
         inspectedDate.setText("Activity recorded: "+stringdate(startTime,2)+"  -  "+stringdate(endTime,2));

         inspector.setText("Auditor:  "+ auditor);
         Label.setText(branchLabel);
         bNote.setText(note);


        if (globalVariables.propPhoto == null)
            globalVariables.propPhoto = "";

        if (globalVariables.propPhoto.length() > 12) {
            String dirName = globalVariables.propPhoto.substring(6, 14);
            String root = Environment.getExternalStorageDirectory().toString();
            File Image = new File(root + "/ESM_" + dirName + "/" + globalVariables.propPhoto);
            Bitmap myBitmap = BitmapFactory.decodeFile(Image.getAbsolutePath());
            mPhotoImageView.setImageBitmap(myBitmap);
        }

        inspectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select Activity Method");
                // add a list
                String[] actions = {"Data collection and Log time",
                        "View/Edit collected information",
                };

                builder.setItems(actions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: {
                                Intent theIntent = new Intent(getActivity(), InspectionActivity.class);
                                Bundle bundle = new Bundle();

                                //               saveData();
                                bundle.putString("PROJECT_ID", globalVariables.projectId);
                                bundle.putString("INSPECTION_ID", globalVariables.inspectionId);
                                bundle.putBoolean("logTime", true);
                                theIntent.putExtras(bundle);
                                startActivity(theIntent);


                                break;
                            }

                            case 1: //
                                Intent theIntent = new Intent(getActivity(), InspectionActivity.class);
                                Bundle bundle = new Bundle();

                                //               saveData();
                                bundle.putString("PROJECT_ID", globalVariables.projectId);
                                bundle.putString("INSPECTION_ID", globalVariables.inspectionId);
                                bundle.putBoolean("logTime", false);
                                theIntent.putExtras(bundle);
                                startActivity(theIntent);

                                break;
                        }


                    }
                });
                // create and show the alert dialog
                AlertDialog dialog = builder.create();

                dialog.show();


            }
        });



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



    private void setText(){

        if (!branchHead.equals("")){
           title.setText("Project ID:  "+branchHead);
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


    public void editLabel(final String  item, String value){

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View promptView = layoutInflater.inflate(R.layout.add_location, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptView);
        final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
        itemTitle.setText("Activity Information ");//Integer.parseInt(locationId)
        final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
        locationText.setText("Activity Title");//Integer.parseInt(locationId)
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
                                dbHandler.updateInspection(projectId, inspectionId, branchLabel, note);
                                globalVariables.OnSelectionChanged(0);
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

        dbHandler.updateInspection(projectId, inspectionId, branchLabel, note);
        dbHandler.statusChanged(Integer.parseInt(projectId));

}

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (Edited = true) saveData();
    }
}
