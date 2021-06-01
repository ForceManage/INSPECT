package com.forcemanage.inspect.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.forcemanage.inspect.InspectionActivity;
import com.forcemanage.inspect.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MultiPicFragment extends Fragment implements View.OnClickListener {

    private InspectionActivity globalVariables;

    private static final String TAG = "Inspection Fragment";
    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private static final int ACTIVITY_GET_FILE = 1;
    private static final int ACTIVITY_DRAW_FILE = 2;
    private String projectId;
    private String inspectionId;
    private String overView;
    private String relevantInfo;
    private ImageView cam1;
    private ImageView cam2;
    private ImageView cam3;
    private ImageView cam4;
    private ImageView cam5;
    private ImageView cam6;
    private ImageView photoA;
    private ImageView photoB;
    private ImageView photoC;
    private ImageView photoD;
    private ImageView photoE;
    private ImageView photoF;
    private ImageView del_img2;
    private ImageView del_img3;
    private ImageView del_img4;
    private ImageView del_img5;
    private ImageView del_img6;
    private int aId;
    private int projId;
    private int iId;
    private String inspectionDate;
    private String startTime;
    private String endTime;
    private String Prnt;
    private boolean Edited = false;

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
            aId = bundle.getInt("aID");

            overView = bundle.getString("overview");
            inspectionDate = bundle.getString("date");
            relevantInfo = bundle.getString("relevantInfo");

            Prnt = bundle.getString("prnt");

        }

        //   inspectionDate =  inspectionDate.substring(6,8)+"-"+inspectionDate.substring(4,6)+"-"+inspectionDate.substring(0,4);

        inspectionDate = dayTime(1);
        startTime = dayTime(2);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.multi_pic, container, false);
        //       btnInspection = (Button) view.findViewById(R.id.btnInspection);


        final CheckBox Cbox = (CheckBox) view.findViewById(R.id.print_checkBox);

        if (Prnt.equals("1"))
            Cbox.setChecked(true);
        else
            Cbox.setChecked(false);

        Cbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Cbox.isChecked()) Prnt = "1";
                else Prnt = "0";
                Edited = true;
            }
        });

        return view;

    }

    @Override
    public void onClick(View v) {

    }

    public String stringdate(String date){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date d = null;
        try {
            d = sdf.parse(date);
        } catch (ParseException ex) {
            Log.v("Exception", ex.getLocalizedMessage());
        }
        sdf.applyPattern("dd MMM yyyy");
        date = sdf.format(d);

        return date;
    }


    private String dayTime(int Type) {

        String daytime = "20000101";

        switch (Type) {

            case (1): {

                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyyMMdd");
                Date date_ = Calendar.getInstance().getTime();
                daytime = (dateFormat.format(date_));
                break;
            }

            case (2): {

                java.text.SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
                Date date_ = Calendar.getInstance().getTime();
                daytime = (dateFormat.format(date_));
                break;
            }

            case (3): {

                java.text.SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date_ = Calendar.getInstance().getTime();
                daytime = (dateFormat.format(date_));
                break;
            }
        }
        return daytime;
    }
}
