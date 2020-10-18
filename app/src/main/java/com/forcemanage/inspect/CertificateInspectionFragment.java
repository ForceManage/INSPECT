package com.forcemanage.inspect;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CertificateInspectionFragment extends Fragment implements View.OnClickListener {

    private InspectionActivity globalVariables;

    private static final String TAG = "ActionItem Fragment";
    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private static final int ACTIVITY_GET_FILE = 1;
    private static final int ACTIVITY_DRAW_FILE = 2;

    private TextView title;
    private TextView branch;
    private ImageView Img1;
    private ImageView Img2;
    private ImageView Img3;
    private ImageView Img4;
    private ImageView photoA;
    private ImageView photoB;
    private ImageView photoC;
    private ImageView photoD;
    private ImageView photoE;
    private ImageView photo_cam;
    private ImageView photo_draw;
    private ImageView photo_file;
    private TextView time;
    private EditText descriptionE;
    private EditText permit;
    private EditText address;
    private EditText stage;
    private EditText notesE;
    private boolean Edited = false;
    private String branchTitle = "Title";
    private String branchName = "Branch";
    private String desciption = "Desc";
    private String dTime = "Time";
    private String Permit = "Permit No.";
    private String Address = "Address";
    private TextView timetxt;
    private String Notes = "Notes";
    private String projectId;
    private String inspectionId;
    private Integer projId;
    private Integer iId;
    private String Stage;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        globalVariables = (InspectionActivity) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            projectId = bundle.getString("projectID");
            inspectionId = bundle.getString("inspectionID");
            branchTitle = bundle.getString("branchHead");
            branchName = bundle.getString("branchLabel");
            desciption = bundle.getString("description");
            dTime = bundle.getString("time");
            Permit = bundle.getString("permit");
            Address = bundle.getString("address");
            Stage = bundle.getString("stage");
            Notes = bundle.getString("notes");
         }


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.certificate_inspection, container, false);
        //       btnInspection = (Button) view.findViewById(R.id.btnInspection);
        projId = Integer.parseInt(projectId);
        iId = Integer.parseInt(inspectionId);

        Log.d(TAG, "oncreateview: started");

        title = (TextView) view.findViewById(R.id.title);
        branch = (TextView) view.findViewById(R.id.level);
        notesE = (EditText) view.findViewById(R.id.note);

         time  = (TextView) view.findViewById(R.id.time);
         descriptionE = (EditText) view.findViewById(R.id.desc);
         permit = (EditText) view.findViewById(R.id.buildp);
         address = (EditText) view.findViewById(R.id.address);
         stage = (EditText) view.findViewById(R.id.stage);
         notesE = (EditText) view.findViewById(R.id.notes);
         timetxt = (TextView) view.findViewById(R.id.Time);


  //       final CheckBox checkBox = (CheckBox) view.findViewById(R.id.sign_checkBox);


         //Fill in the edittext with saved data fro Bundle

        title.setText(branchTitle);
        branch.setText(branchName);
        time.setText(stringdate(dTime));
        descriptionE.setText(desciption);
        permit.setText(Permit);
        address.setText(Address);
        stage.setText(Stage);
        notesE.setText(Notes);



        address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) Edited = true;


            }
        });


         time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                final int date_time = cldr.get(Calendar.HOUR_OF_DAY);
                final int date_min = cldr.get(Calendar.MINUTE);
                final int date_sec = cldr.get(Calendar.SECOND);
                 // date picker dialog
                DatePickerDialog picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                time.setText(stringdate(year + "-" + (monthOfYear + 1) + "-" +dayOfMonth+ " "+date_time+":"+date_min+":"+date_sec));
                            }
                        }, year, month, day);
                Edited = true;
                picker.show();

            }
        });

        timetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                TimePickerDialog picker = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                String date = time.getText().toString().substring(0,11);
                                time.setText(date+", " +sHour + ":" + sMinute);
                            }
                        }, hour, minutes, false);
                Edited = true;
                picker.show();
            }
        });

         descriptionE.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus) Edited = true;

                }
            });

        permit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) Edited = true;

            }
        });

        stage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) Edited = true;

            }
        });

        notesE.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) Edited = true;

            }
        });




        return view;
    }




        @Override
    public void onClick(View v) {

    }


    public String stringdate(String date){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = sdf.parse(date);
        } catch (ParseException ex) {
            Log.v("Exception", ex.getLocalizedMessage());
        }
        sdf.applyPattern("dd MMM yyyy, hh:mm:a");
        date = sdf.format(d);

        return date;

    }


    public String dateString (String date){

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, hh:mm");
        Date d = null;
        try {
            d = sdf.parse(date);
        } catch (ParseException ex) {
            date = stringdate(dTime);
            Log.v("Exception", ex.getLocalizedMessage());
        }
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        date = sdf.format(d);

        return date;

    }

    private String dayTime(int Type) {

        String daytime = "20000101";

        switch (Type) {

            case (1): {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                Date date_ = Calendar.getInstance().getTime();
                daytime = (dateFormat.format(date_));
                break;
            }

            case (2): {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
                Date date_ = Calendar.getInstance().getTime();
                daytime = (dateFormat.format(date_));
                break;
            }

            case (3): {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date_ = Calendar.getInstance().getTime();
                daytime = (dateFormat.format(date_));
                break;
            }
        }
        return daytime;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if(Edited){

            DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);

            // String serviceDate = inspectionDate.getText().toString();
            // work out the next service date in three months time

            dbHandler.updateCertificateInspection(projectId, inspectionId, dateString(time.getText().toString()), descriptionE.getText().toString(),
                                      permit.getText().toString(), address.getText().toString()
                    , stage.getText().toString(), notesE.getText().toString());
            dbHandler.statusChanged(projId, iId);

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(Edited){

            DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);

            // String serviceDate = inspectionDate.getText().toString();
            // work out the next service date in three months time

            dbHandler.updateCertificateInspection(projectId, inspectionId, dateString(time.getText().toString()), descriptionE.getText().toString(),
                    permit.getText().toString(), address.getText().toString()
                    , stage.getText().toString(), notesE.getText().toString());
            dbHandler.statusChanged(projId, iId);

        }
    }
}