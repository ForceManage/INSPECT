package com.forcemanage.inspect.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.forcemanage.inspect.R;
import com.forcemanage.inspect.attributes.ReportItem;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReportAdapter extends ArrayAdapter<ReportItem> {


    private List reportlistItems;
    private  final static int  sublocat = 1, location = 0;
    private int locat;
    private Context context;
    private ImageView imageView, imageView1, imageView2;



    public ReportAdapter(Context context, int resource, List<ReportItem> items  ) {
       super(context, resource);

        reportlistItems = items;
        this.context = context;
    }


@Override
    public long getItemId(int position) {
        return position;
    }
@Override
    public int getCount() {
        return reportlistItems.size();
    }
@Override
    public View getView(int position, View convertView, ViewGroup parent) {

    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    ReportItem listItem = (ReportItem) reportlistItems.get(position);
    View row = convertView;

            if(listItem.getTypeObject().equals("inspectionObject")) {
            row = inflater.inflate(R.layout.report_body_fragment, null, true);
            TextView title = (TextView) row.findViewById(R.id.branchTitle);
            TextView parentLabel = (TextView) row.findViewById(R.id.parentLabel);
            TextView overview = (TextView) row.findViewById(R.id.Overview);
            TextView com1 = (TextView) row.findViewById(R.id.Observation1);
            TextView com2 = (TextView) row.findViewById(R.id.Observation2);
            TextView com3 = (TextView) row.findViewById(R.id.Observation3);
            TextView com4 = (TextView) row.findViewById(R.id.Observation4);
            TextView com5 = (TextView) row.findViewById(R.id.Observation5);
            TextView label = (TextView) row.findViewById(R.id.branchName);
            TextView relInfo = (TextView) row.findViewById(R.id.RelevantInfo);
/*
            TextView fig0 = (TextView) row.findViewById(R.id.fig0);
            TextView fig1 = (TextView) row.findViewById(R.id.fig1);
            TextView fig2 = (TextView) row.findViewById(R.id.fig2);
            TextView fig3 = (TextView) row.findViewById(R.id.fig3);
            TextView fig4 = (TextView) row.findViewById(R.id.fig4);

 */


            title.setText(listItem.getBranchHead());
            parentLabel.setText(listItem.getParentLabel());
            overview.setText(listItem.getOverview());
            String itemNo = Integer.toString(position + 1);
            if (!listItem.getImage1().equals(""))
                com1.setText(listItem.getCom1() + " (Fig " + itemNo + ".0)");
            if (!listItem.getImage2().equals(""))
                com2.setText(listItem.getCom2() + " (Fig " + itemNo + ".1)");
            if (!listItem.getImage3().equals(""))
                com3.setText(listItem.getCom3() + " (Fig " + itemNo + ".2)");
            if (!listItem.getImage4().equals(""))
                com4.setText(listItem.getCom4() + " (Fig " + itemNo + ".3)");
            if (!listItem.getImage5().equals(""))
                com5.setText(listItem.getCom5() + " (Fig " + itemNo + ".4)");

            label.setText(listItem.getLabel());
            relInfo.setText(listItem.getRelevantInfo());

            String[] photos = {listItem.getImage1(), listItem.getImage2(), listItem.getImage3(), listItem.getImage4(), listItem.getImage5()};
            ImageView image1 = (ImageView) row.findViewById(R.id.imageView1);
            ImageView image2 = (ImageView) row.findViewById(R.id.imageView2);
            ImageView image3 = (ImageView) row.findViewById(R.id.imageView3);
            ImageView image4 = (ImageView) row.findViewById(R.id.imageView4);
            ImageView image5 = (ImageView) row.findViewById(R.id.imageView5);


            for (int i = 0; i < 5; i++) {

                if (photos[i].length() > 12) {

                    String dirName = photos[i].substring(6, 14);
                    String root = Environment.getExternalStorageDirectory().toString();
                    File propImage = new File(root + "/A2D_" + dirName + "/" + photos[i]);

                    if (propImage.exists()) {

                        Bitmap myBitmap = BitmapFactory.decodeFile(propImage.getAbsolutePath());
                        switch (i) {
                            case 0: {
                                image1.setVisibility(View.VISIBLE);
                                image1.setImageBitmap(myBitmap);
                             //   fig0.setText("Fig " + itemNo + ".0");
                                break;
                            }
                            case 1: {
                                image2.setImageBitmap(myBitmap);
                                image2.setVisibility(View.VISIBLE);
                         //       fig1.setText("Fig " + itemNo + ".1");
                                break;
                            }
                            case 2: {
                                image3.setImageBitmap(myBitmap);
                                image3.setVisibility(View.VISIBLE);
                       //         fig2.setText("Fig " + itemNo + ".2");
                                break;
                            }
                            case 3: {
                                image4.setImageBitmap(myBitmap);
                                image4.setVisibility(View.VISIBLE);
                        //        fig3.setText("Fig " + itemNo + ".3");
                                break;
                            }
                            case 4: {
                                image5.setImageBitmap(myBitmap);
                                image5.setVisibility(View.VISIBLE);
                        //        fig4.setText("Fig " + itemNo + ".4");
                                break;
                            }
                        }
                    } else {

                        switch (i) {

                            case 0: {
                                image1.setVisibility(View.GONE);
                                break;
                            }
                            case 1: {
                                image2.setVisibility(View.GONE);
                                break;
                            }
                            case 2: {
                                image3.setVisibility(View.GONE);
                                break;
                            }
                            case 3: {
                                image4.setVisibility(View.GONE);
                                break;
                            }
                            case 4: {
                                image5.setVisibility(View.GONE);
                                break;
                            }

                        }
                    }


                } else {
                    switch (i) {

                        case 0: {
                            image1.setVisibility(View.GONE);
                            break;
                        }
                        case 1: {
                            image2.setVisibility(View.GONE);
                            break;
                        }
                        case 2: {
                            image3.setVisibility(View.GONE);
                            break;
                        }
                        case 3: {
                            image4.setVisibility(View.GONE);
                            break;
                        }
                        case 4: {
                            image5.setVisibility(View.GONE);
                            break;
                        }

                    }
                }
            }
        }



          if(listItem.getTypeObject().equals("ActionItemObject")) {

                row = inflater.inflate(R.layout.report_actionitem_fragment, null, true);
                TextView title = (TextView) row.findViewById(R.id.branchTitle);
                TextView overview = (TextView) row.findViewById(R.id.desc);
                TextView scope = (TextView) row.findViewById(R.id.scope);
                TextView perform = (TextView) row.findViewById(R.id.perform);
                TextView notes = (TextView) row.findViewById(R.id.notes);
                TextView label = (TextView) row.findViewById(R.id.branchName);


                title.setText("Action Item Scope");
                overview.setText(listItem.getOverview());
                label.setText(listItem.getLabel());
                scope.setText(listItem.getCom1());
                perform.setText(listItem.getRelevantInfo());
                notes.setText(listItem.getNotes());


                String[] photos = {listItem.getImage1()};
                ImageView image1 = (ImageView) row.findViewById(R.id.imageView1);


                for (int i = 0; i < 1; i++) {

                    if (photos[i].length() > 12) {

                        String dirName = photos[i].substring(6, 14);
                        String root = Environment.getExternalStorageDirectory().toString();
                        File propImage = new File(root + "/A2D_" + dirName + "/" + photos[i]);

                        if (propImage.exists()) {

                            Bitmap myBitmap = BitmapFactory.decodeFile(propImage.getAbsolutePath());
                            if (i == 0) {
                                image1.setImageBitmap(myBitmap);
                            }
                        }
                    }
                }
            }




        if (listItem.getTypeObject().equals("SummaryObject")) {

            row = inflater.inflate(R.layout.summary_fragment, null, true);
            TextView Head_A = (TextView) row.findViewById(R.id.Head_A);
            TextView Com_A = (TextView) row.findViewById(R.id.Com_A);
            TextView Head_B = (TextView) row.findViewById(R.id.Head_B);
            TextView Com_B = (TextView) row.findViewById(R.id.Com_B);
            TextView Head_C = (TextView) row.findViewById(R.id.Head_C);
            TextView Com_C = (TextView) row.findViewById(R.id.Com_C);

            String headA = listItem.getTitle_A();
            String headB = listItem.getTitle_B();
            String headC = listItem.getTitle_C();
            String comA = listItem.getCom_A();
            String comB = listItem.getCom_B();
            String comC = listItem.getCom_C();
            if(!listItem.getTitle_A().equals("TITLE")) {
                Head_A.setText(listItem.getTitle_A());
                Com_A.setText(listItem.getCom_A());
            }
            else{
                Head_A.setText("");
                Com_A.setText("");
            }
            if(!listItem.getTitle_B().equals("TITLE")) {
                Head_B.setText(listItem.getTitle_B());
                Com_B.setText(listItem.getCom_B());
            }
            else{
                Head_B.setText("");
                Com_B.setText("");
            }
            if(!listItem.getTitle_C().equals("TITLE")) {
                Head_C.setText(listItem.getTitle_C());
                Com_C.setText(listItem.getCom_C());
            }
            else{
                Head_C.setText("");
                Com_C.setText("");
            }
        }


        if (listItem.getTypeObject().equals("CertInspectionObject")) {

            row = inflater.inflate(R.layout.cert_inspection_fragment, null, true);
            TextView title = (TextView) row.findViewById(R.id.branchTitle);
            TextView date_time = (TextView) row.findViewById(R.id.date_time);
            TextView overview = (TextView) row.findViewById(R.id.desc);
            TextView permit = (TextView) row.findViewById(R.id.permit);
            TextView address = (TextView) row.findViewById(R.id.address);
            TextView stage = (TextView) row.findViewById(R.id.stage);
            TextView notes = (TextView) row.findViewById(R.id.notes);


            title.setText("Certificate Inspection");
            date_time.setText(stringdate(listItem.getDate_time(),2));
            overview.setText(listItem.getOverview());
            permit.setText(listItem.getPermit());
            address.setText(listItem.getAddress());
            stage.setText(listItem.getStage());
            notes.setText(listItem.getNotes());
        }





   return row;

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
    }


