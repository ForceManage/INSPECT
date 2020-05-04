package com.forcemanage.inspect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
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
    if (listItem.getOverview() != null) {
        if (!listItem.getBranchHead().equals("ActionItemOject")) {
            row = inflater.inflate(R.layout.report_body_fragment, null, true);
            TextView title = (TextView) row.findViewById(R.id.branchTitle);
            TextView overview = (TextView) row.findViewById(R.id.Overview);
            TextView com1 = (TextView) row.findViewById(R.id.Observation1);
            TextView com2 = (TextView) row.findViewById(R.id.Observation2);
            TextView com3 = (TextView) row.findViewById(R.id.Observation3);
            TextView com4 = (TextView) row.findViewById(R.id.Observation4);
            TextView com5 = (TextView) row.findViewById(R.id.Observation5);
            TextView label = (TextView) row.findViewById(R.id.branchName);
            TextView relInfo = (TextView) row.findViewById(R.id.RelevantInfo);

            TextView fig0 = (TextView) row.findViewById(R.id.fig0);
            TextView fig1 = (TextView) row.findViewById(R.id.fig1);
            TextView fig2 = (TextView) row.findViewById(R.id.fig2);
            TextView fig3 = (TextView) row.findViewById(R.id.fig3);
            TextView fig4 = (TextView) row.findViewById(R.id.fig4);


            title.setText(listItem.getBranchHead());
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
                    File propImage = new File(root + "/ESM_" + dirName + "/" + photos[i]);

                    if (propImage.exists()) {

                        Bitmap myBitmap = BitmapFactory.decodeFile(propImage.getAbsolutePath());
                        if (i == 0) {
                            image1.setImageBitmap(myBitmap);
                            fig0.setText("Fig " + itemNo + ".0");
                        }
                        if (i == 1) {
                            image2.setImageBitmap(myBitmap);
                            fig1.setText("Fig " + itemNo + ".1");
                        }
                        if (i == 2) {
                            image3.setImageBitmap(myBitmap);
                            fig2.setText("Fig " + itemNo + ".2");
                        }
                        if (i == 3) {
                            image4.setImageBitmap(myBitmap);
                            fig3.setText("Fig " + itemNo + ".3");
                        }
                        if (i == 4) {
                            image5.setImageBitmap(myBitmap);
                            fig4.setText("Fig " + itemNo + ".4");
                        }
                    }
                }
            }


        } else {

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
                    File propImage = new File(root + "/ESM_" + dirName + "/" + photos[i]);

                    if (propImage.exists()) {

                        Bitmap myBitmap = BitmapFactory.decodeFile(propImage.getAbsolutePath());
                        if (i == 0) {
                            image1.setImageBitmap(myBitmap);
                         }
                 }
                }
            }



        }
    }

   return row;

}
    }


