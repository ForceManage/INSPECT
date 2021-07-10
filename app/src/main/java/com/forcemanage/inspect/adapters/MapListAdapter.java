package com.forcemanage.inspect.adapters;

/**
 * Created by cindyoakes on 9/23/16.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.forcemanage.inspect.DBHandler;
import com.forcemanage.inspect.GlobalVariables;
import com.forcemanage.inspect.MainActivity;
import com.forcemanage.inspect.MapListViewHolder;
import com.forcemanage.inspect.MapViewFragment;
import com.forcemanage.inspect.MapViewLists;
import com.forcemanage.inspect.MyConfig;
import com.forcemanage.inspect.OnProjectSelectionChangeListener;
import com.forcemanage.inspect.ProjectViewList;
import com.forcemanage.inspect.attributes.MapViewData;
import com.forcemanage.inspect.attributes.MapViewNode;
import com.forcemanage.inspect.R;
import com.forcemanage.inspect.OnDocChangeListener;
import com.forcemanage.inspect.attributes.ProjectData;
import com.forcemanage.inspect.attributes.ProjectNode;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MapListAdapter extends ArrayAdapter<MapViewNode>
{

    private MainActivity MainActivityVariables;
    private final LayoutInflater mInflater;
    private Context context;
    private String[] pdf_files;
    private String pdf_name;
    private int iId;


    public MapListAdapter(Context context) {    //,int resource, List<TreeViewNode> items
        super(context, android.R.layout.simple_gallery_item);

        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {return GlobalVariables.displayNodes.size();
    }

    @Override
    public MapViewNode getItem(int position) {return GlobalVariables.displayNodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Populate new items in the list.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        MapListViewHolder holder = null;

        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.folder_item, parent, false);

            holder = new MapListViewHolder();
            holder.content = (TextView) convertView.findViewById(R.id.text);
            holder.arrow = (ImageView) convertView.findViewById(R.id.arrow);
            holder.more = (ImageView) convertView.findViewById(R.id.btn_menu);
       //     holder.note = (ImageView) convertView.findViewById(R.id.btn_prnt);
            holder.position = position;


            ((ImageView) convertView.findViewById(R.id.arrow)).setOnClickListener(mArrowClickListener);

            holder.more.setOnClickListener(mMenuClickListener);
        //    ((TextView) convertView.findViewById(R.id.text)).setOnClickListener(mTextClickListener);
       //     holder.note.setOnClickListener(mNote);

            convertView.setTag(holder);

        } else
        {
            holder = (MapListViewHolder) convertView.getTag();
        }

   //     GlobalVariables.pos = position;
        MapViewNode node = GlobalVariables.displayNodes.get(position);
        holder.content.setText(node.getNodeName());

        iId = GlobalVariables.iId;

        int base_node = node.getNodeLevel();


        switch (node.getbranchCat()){

            case 0:{
            //    holder.more.setImageResource(R.drawable.ic_more_vert);

                holder.content.setTextColor(Color.DKGRAY);
              //  holder.content.setTextSize(17);


                switch (node.getNodeLevel()) {

                    case 0:
                        if(GlobalVariables.doc_pos == 1 || GlobalVariables.doc_mode == 1) {
                            if (position == 0) {
                                holder.arrow.setImageResource(R.drawable.folder2_red); // holder.arrow.setImageResource(R.drawable.ic_book);
                                holder.more.setVisibility(View.GONE);
                            }
                            if(position == 1 )
                                holder.arrow.setImageResource(R.drawable.ic_book);
                             else
                            holder.arrow.setImageResource(R.drawable.folder2_red);

                        }
                            holder.content.setTextAppearance(android.R.style.TextAppearance_Material_Title);

                        break;
                    case 1:
                        holder.arrow.setImageResource(R.drawable.ic_file_tree);
                        holder.content.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
                        break;
                    case 2:
                        holder.arrow.setImageResource(R.drawable.ic_filetree2);
                        holder.content.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
                        break;
                   }
                if (node.getNodeLevel() > 2) {
                    holder.arrow.setImageResource(R.drawable.ic_filetree2);
                    holder.content.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
                  }

                 break;
           }

            case 1: {  // Standard page
                holder.content.setTextColor(Color.GRAY);
            //    holder.content.setTextSize(17);
                holder.content.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
                holder.arrow.setImageResource(R.drawable.ic_edit_outline);
                break;
            }
            case 2: { // Scope Page
                holder.content.setTextColor(Color.GRAY);
           //     holder.content.setTextSize(17);
                holder.content.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
                holder.arrow.setImageResource(R.drawable.ic_note_text);
                break;
            }

            case 3: { // Multipic 6 Page
                holder.content.setTextColor(Color.GRAY);
                //     holder.content.setTextSize(17);
                holder.content.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
                holder.arrow.setImageResource(R.drawable.multipic);
                break;
            }

            case 4: { // Multipic 4 Page
                holder.content.setTextColor(Color.GRAY);
                //     holder.content.setTextSize(17);
                holder.content.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
                holder.arrow.setImageResource(R.drawable.multipic);
            //    if(GlobalVariables.doc_mode == 0) holder.more.setVisibility(View.GONE);
            //    else holder.more.setVisibility(View.VISIBLE);

                break;
            }

            case 5: {  // Blank Page
                holder.content.setTextColor(Color.GRAY);
                //     holder.content.setTextSize(17);
                holder.content.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
                holder.arrow.setImageResource(R.drawable.blankpage);
           //     if(GlobalVariables.doc_mode == 0) holder.more.setVisibility(View.GONE);
          //      else holder.more.setVisibility(View.VISIBLE);
                break;
            }

            case 9: {
                //      holder.content.setTextSize(17);
                holder.content.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
                holder.arrow.setImageResource(R.drawable.ic_comment);
                holder.more.setImageResource(R.drawable.ic_more_vert);
                holder.content.setTextColor(Color.BLUE);
       //         if(GlobalVariables.doc_mode == 0) holder.more.setVisibility(View.GONE);
       //         else holder.more.setVisibility(View.VISIBLE);
                break;
            }
            case 10: {
          //      holder.content.setTextSize(17);
                holder.content.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
                holder.arrow.setImageResource(R.drawable.ic_certificate);
                holder.more.setImageResource(R.drawable.ic_more_vert);
                holder.content.setTextColor(Color.BLUE);
       //         if(GlobalVariables.doc_mode == 0) holder.more.setVisibility(View.GONE);
       //         else holder.more.setVisibility(View.VISIBLE);
                break;
            }

            case 11: {
                //      holder.content.setTextSize(17);
                holder.content.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
                holder.arrow.setImageResource(R.drawable.ic_action_name);
                holder.content.setTextColor(Color.BLUE);
                holder.more.setImageResource(R.drawable.ic_more_vert);
      //          if(GlobalVariables.doc_mode == 0) holder.more.setVisibility(View.GONE);
      //          else holder.more.setVisibility(View.VISIBLE);
                break;
            }

            case 12: {
                //      holder.content.setTextSize(17);
                holder.content.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
                holder.arrow.setImageResource(R.drawable.ic_action_name);
                holder.content.setTextColor(Color.BLUE);
                holder.more.setImageResource(R.drawable.ic_more_vert);
         //       if(GlobalVariables.doc_mode == 0) holder.more.setVisibility(View.GONE);
         //       else holder.more.setVisibility(View.VISIBLE);
                break;
            }


        }


        holder.arrow.setTag(position);
        holder.content.setTag(position);
        holder.more.setTag(position);



        int lvl = node.getNodeLevel();
        int newWidth = (lvl * 20) ;
        ((TextView) convertView.findViewById(R.id.spacer)).getLayoutParams().width = newWidth;
        ((TextView) convertView.findViewById(R.id.spacer)).requestLayout();

     //   showMessage("MapView Adapter pos:  "+position);

        return convertView;
    }

 /*
    public OnClickListener mNote;

    {

        mNote = new OnClickListener() {

            //   @Override
            public void onClick(View v) {



                GlobalVariables.doc_mode = 0;
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Add, delete or edit pages in the document ");

                String[] actions = {"Open document",
                                    "Cancel "};


                builder.setItems(actions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {

                            case 0: {



                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                alertDialogBuilder.setTitle("Log Session");
                                alertDialogBuilder.setMessage("Record file session time?");
                                alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        Intent theIntent = new Intent(getContext(), InspectionActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("PROJECT_ID", Integer.toString(GlobalVariables.folder_Id));
                                        bundle.putString("INSPECTION_ID", Integer.toString(GlobalVariables.iId));
                                        bundle.putString("DOC_NAME", GlobalVariables.name);
                                        bundle.putBoolean("logTime", true);
                                        theIntent.putExtras(bundle);
                                        getContext().startActivity(theIntent);
                                        dialog.dismiss();

                                    }
                                })
                                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Intent theIntent = new Intent(getContext(), InspectionActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putString("PROJECT_ID", Integer.toString(GlobalVariables.folder_Id));
                                                bundle.putString("INSPECTION_ID", Integer.toString(iId));
                                                bundle.putString("DOC_NAME", GlobalVariables.name);
                                                bundle.putBoolean("logTime", false);
                                                theIntent.putExtras(bundle);
                                                getContext().startActivity(theIntent);
                                                dialog.cancel();
                                            }
                                        });

                                // create an alert dialog
                                AlertDialog alert = alertDialogBuilder.create();
                                alert.show();
                                break;
                            }

                            case 1: {


                                break;

                            }


                        }

                    }
                });

                AlertDialog dialog = builder.create();


                dialog.show();



            }



        };



    }
                */
     public OnClickListener mArrowClickListener;

{
        mArrowClickListener = new OnClickListener() {
            //   @Override
            public void onClick(View v) {

                int position = (int) v.getTag();
              //  int position = GlobalVariables.pos;

                if (position != ListView.INVALID_POSITION) {
                    MapViewNode node = GlobalVariables.displayNodes.get(GlobalVariables.pos);

                    if (node.getIsExpanded() == GlobalVariables.TRUE) {
                        node.setIsExpanded(GlobalVariables.FALSE);

                    } else {
                        if (node.getNodeChildren() != null)
                            node.setIsExpanded(GlobalVariables.TRUE);

                    }


                  MapViewLists.LoadDisplayList();

                   notifyDataSetChanged();


               }

                // showMessage("Adapter listener "+node.getNodeChildren().toString());
            }
        };


    }


    public OnClickListener mMenuClickListener;

    {
        mMenuClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {


                int position = (int) v.getTag();
                final MapViewNode node = GlobalVariables.displayNodes.get(position);


                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Topic Labels");
                // add a list

                if (GlobalVariables.doc_mode == 0) { //Folder template project setup

                    if(node.getcatId() == 0){ // if the Folder label is selected

                        String[] actions = {"Edit Folder Title",
                                "Add Title Label ",
                                "Cancel "};


                        builder.setItems(actions, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                switch (which) {

                                    case 0: {

                                        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                        View promptView = layoutInflater.inflate(R.layout.add_location, null);
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                        alertDialogBuilder.setView(promptView);
                                        final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                                        itemTitle.setText("Folder Information ");//Integer.parseInt(locationId)
                                        final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                        locationText.setText("Current Folder Title: " + node.getNodeName());//Integer.parseInt(locationId)
                                        final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
                                        branchText.setHint("Folder title");
                                        // setup a dialog window
                                        alertDialogBuilder.setCancelable(true)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {

                                                                DBHandler dbHandler = new DBHandler(getContext(), null, null, 1);


                                                                if (branchText.getText().toString() == "")
                                                                    Toast.makeText(getContext(), "Retry with valid text ", Toast.LENGTH_LONG).show();
                                                                else {
                                                                    dbHandler.updateProject(Integer.toString(node.getprojId()), "Folder Title", branchText.getText().toString(), 0);
                                                                    loadFolderMap(GlobalVariables.User_id, node.getprojId());
                                                                }
                                                                //



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

                                        break;
                                }


                                    case 1: {


                                        //  int position = GlobalVariables.pos;
                                        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                        View promptView = layoutInflater.inflate(R.layout.add_location, null);
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                        alertDialogBuilder.setView(promptView);
                                        final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                                        itemTitle.setText("Folder: " + "branchTitle");//Integer.parseInt(locationId)
                                        final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                        locationText.setText("Add a Title Heading Tab to " + node.getNodeName() + " Folder");//Integer.parseInt(locationId)
                                        final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
                                        // setup a dialog window
                                        alertDialogBuilder.setCancelable(false)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        //      photoBranch = "";
                                                        //     if (projId != 0)
                                                        //         addLevel(1, branchText.getText().toString());

                                                        //      else
                                                        //           Toast.makeText(getContext(), "Create or Select a Folder", Toast.LENGTH_SHORT).show();

                                                        DBHandler dbHandler = new DBHandler(getContext(), null, null, 1);
                                                        int result = dbHandler.addLevel(node.getprojId(), node.getaID(), 0, node.getcatId(), node.getNodeLevel() + 1, node.getaID(), branchText.getText().toString(), "", 0);  //this is the ESM category
                                                        loadProjectMap(node.getprojId());
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


                                        break;

                                    }

                                    case 2: {


                                        break;

                                    }


                                }

                            }
                        });

                        AlertDialog dialog = builder.create();


                        dialog.show();

                    }



                    else { // Still in project mode but the subfolder is selected

                        String[] actions = {"Edit Label",
                                "Add Sub-Title Label ",
                                "Cancel "};


                        builder.setItems(actions, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                switch (which) {

                                    case 0: {

                                        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                        View promptView = layoutInflater.inflate(R.layout.add_location, null);
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                        alertDialogBuilder.setView(promptView);
                                        final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                                        itemTitle.setText("Edit Folder label ");//Integer.parseInt(locationId)
                                        final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                        locationText.setText(node.getNodeName());//Integer.parseInt(locationId)
                                        final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
                                        branchText.setHint("Label title");
                                        // setup a dialog window
                                        alertDialogBuilder.setCancelable(false)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {

                                                        DBHandler dbHandler = new DBHandler(getContext(), null, null, 1);

                                                               dbHandler.updateMapLabel((node.getprojId()), node.getaID(), branchText.getText().toString());
                                                                loadProjectMap(node.getprojId());;
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

                                        break;

                                    }

                                    case 1: {


                                        //  int position = GlobalVariables.pos;
                                        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                        View promptView = layoutInflater.inflate(R.layout.add_location, null);
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                        alertDialogBuilder.setView(promptView);
                                        final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                                        itemTitle.setText("Folder: " + "branchTitle");//Integer.parseInt(locationId)
                                        final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                        locationText.setText("Add a Sub-Title Label to " + node.getNodeName() + " Folder");//Integer.parseInt(locationId)
                                        final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
                                        // setup a dialog window
                                        alertDialogBuilder.setCancelable(false)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        //      photoBranch = "";
                                                        //     if (projId != 0)
                                                        //         addLevel(1, branchText.getText().toString());

                                                        //      else
                                                        //           Toast.makeText(getContext(), "Create or Select a Folder", Toast.LENGTH_SHORT).show();

                                                        DBHandler dbHandler = new DBHandler(getContext(), null, null, 1);
                                                        int result = dbHandler.addLevel(node.getprojId(), node.getaID(), 0, node.getcatId(), node.getNodeLevel() + 1, node.getaID(), branchText.getText().toString(), "", 0);  //this is the ESM category
                                                        loadProjectMap( node.getprojId());;
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


                                        break;

                                    }

                                    case 2: {


                                        break;

                                    }

                                    case 3: {


                                        break;
                                    }


                                }

                            }
                        });

                        AlertDialog dialog = builder.create();


                        dialog.show();
                    } // else if not folder

                }  else //if not in folder mode then must be In doc mode

                    {

                    final DBHandler dbHandler = new DBHandler(getContext(), null, null, 1);

                    switch (node.getbranchCat()) {



                        case 0: { //first order tabs id doc. The location tabs, not the discusssion, reference tabs, etc

                            if (node.getNodeLevel() == 0 && position > 0) { // if focus is the folder tab fragment


                                String[] actions = {
                                        "Add Title Heading Tab Label to the Folder",
                                        "Add Commentry Section to the document",
                                        "Add Certificate to the document ",
                                        "Add General Information Tab",
                                        "Add PDF Document Tab",
                                        "Cancel "};


                                builder.setItems(actions, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        switch (which) {
                                            case 0:{

                                                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                                View promptView = layoutInflater.inflate(R.layout.add_location, null);
                                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                                alertDialogBuilder.setView(promptView);
                                                final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                                                itemTitle.setText("Folder: " + "branchTitle");//Integer.parseInt(locationId)
                                                final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                                locationText.setText("Add a Heading Tab to " + node.getNodeName() + " Folder");//Integer.parseInt(locationId)
                                                final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
                                                // setup a dialog window
                                                alertDialogBuilder.setCancelable(false)
                                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                //      photoBranch = "";
                                                                //     if (projId != 0)
                                                                //         addLevel(1, branchText.getText().toString());

                                                                //      else
                                                                //           Toast.makeText(getContext(), "Create or Select a Folder", Toast.LENGTH_SHORT).show();

                                                                DBHandler dbHandler = new DBHandler(getContext(), null, null, 1);
                                                                int result = dbHandler.addLevel(node.getprojId(), node.getaID(), 0, node.getcatId(), node.getNodeLevel() + 1, node.getaID(), branchText.getText().toString(), "", 0);  //this is the ESM category
                                                                loadProjectMap(node.getprojId());
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

                                                break;
                                            }

                                            case 1: {

                                                int result = dbHandler.addSummary(node.getprojId(), iId, 500, 0, node.getaID(), "Discussion");  //this is the ESM category
                                                loadProjectMap(node.getprojId());
                                                break;

                                            }

                                            case 2: {

                                                int result = dbHandler.addCertificate(node.getprojId(), iId, 501, 0, node.getaID(), "Certificates");  //this is the ESM category
                                                loadProjectMap(node.getprojId());

                                                break;

                                            }

                                            case 3: {

                                                int result = dbHandler.addReference(node.getprojId(), 510, 0, node.getaID(), "Reference Items");  //this is the ESM category
                                                loadProjectMap(node.getprojId());

                                                break;

                                            }

                                            case 4: {

                                                int result = dbHandler.addPdf_Doc(node.getprojId(), iId, 505, 0, node.getaID(), "PDF Files");  //this is the ESM category
                                                loadProjectMap(node.getprojId());
                                                break;
                                            }
                                            case 5: {

                                                break;
                                            }


                                        }

                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.setTitle("Folder/Document Options");

                                dialog.show();


                                break;


                            } else //focus other than the folder tab, and if doc is true

                                {


                                  if(node.getcatId() == 505) { //if label is pdf

                                      String[] actions = {"Edit Label",
                                              "Add sub-Title label",
                                              "Add Pdf file under [" + node.getNodeName() + "] label",
                                              "Cancel "};

                                      final String branchTitle = dbHandler.getMapBranchTitle(node.getprojId(), node.getcatId()); //get Branch head
                                      builder.setItems(actions, new DialogInterface.OnClickListener() {
                                          @Override
                                          public void onClick(DialogInterface dialog, int which) {

                                              switch (which) {

                                                  case 0: {
                                                      //     final DBHandler dbHandler = new DBHandler(this, null, null, 1);

                                                      //   final String branchTitle = dbHandler.getMapBranchTitle(projId, catId); //get Branch head

                                                      // setup the alert builder

                                                      AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                      builder.setTitle("Edit Document Topic label ");
                                                      // add a list
                                                      String[] actions = {"Change Selected Text",
                                                                          "Delete Selected Page/title",
                                                                            "Cancel"};

                                                      builder.setItems(actions, new DialogInterface.OnClickListener() {
                                                          public void onClick(DialogInterface dialog_sub, int which) {
                                                              switch (which) {
                                                                  case 0: {


                                                                      LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                                                      View promptView = layoutInflater.inflate(R.layout.add_location, null);
                                                                      AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                                                      alertDialogBuilder.setView(promptView);
                                                                      final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                                                                      itemTitle.setText("Topic label: " + branchTitle);//Integer.parseInt(locationId)
                                                                      final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                                                      locationText.setText("Topic label : " + node.getNodeName());//Integer.parseInt(locationId)
                                                                      final EditText LocationText = (EditText) promptView.findViewById(R.id.locationtext);
                                                                      LocationText.setText(node.getNodeName());
                                                                      // setup a dialog window
                                                                      alertDialogBuilder.setCancelable(false)
                                                                              .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                                  public void onClick(DialogInterface dialog, int id) {
                                                                                      editLocation(node.getprojId(), node.getaID(), LocationText.getText().toString());


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
                                                                      break;
                                                                  }

                                                                  case 1: {//

                                                                      LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                                                      View promptView = layoutInflater.inflate(R.layout.add_location, null);
                                                                      AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                                                      alertDialogBuilder.setView(promptView);
                                                                      final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                                                                      itemTitle.setText("File Title: " + branchTitle);//Integer.parseInt(locationId)
                                                                      final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                                                      locationText.setText("Move Current TAB : " + node.getNodeName());//Integer.parseInt(locationId)
                                                                      final EditText LocationText = (EditText) promptView.findViewById(R.id.locationtext);
                                                                      LocationText.setHint("Moveto TAB id ->");
                                                                      // setup a dialog window
                                                                      alertDialogBuilder.setCancelable(false)
                                                                              .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                                  public void onClick(DialogInterface dialog, int id) {
                                                                                      dbHandler.moveTAB(node.getprojId(), node.getaID(), Integer.parseInt(LocationText.getText().toString()));

                                                                                      loadProjectMap(node.getprojId());

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


                                                                      break;

                                                                  }
                                                              }
                                                          }
                                                      });

                                                      AlertDialog dialog_sub = builder.create();

                                                      dialog_sub.show();

                                                      break;
                                                  }


                                                  case 1: { //add sublabel


                                                      //  int position = GlobalVariables.pos;
                                                      LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                                      View promptView = layoutInflater.inflate(R.layout.add_location, null);
                                                      AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                                      alertDialogBuilder.setView(promptView);
                                                      final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                                                      itemTitle.setText("Folder: " + branchTitle);//Integer.parseInt(locationId)
                                                      final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                                      locationText.setText("Add a Title Heading Tab to " + node.getNodeName() + " Folder");//Integer.parseInt(locationId)
                                                      final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
                                                      // setup a dialog window
                                                      alertDialogBuilder.setCancelable(false)
                                                              .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                  public void onClick(DialogInterface dialog, int id) {
                                                                      //      photoBranch = "";
                                                                      //     if (projId != 0)
                                                                      //         addLevel(1, branchText.getText().toString());

                                                                      //      else
                                                                      //           Toast.makeText(getContext(), "Create or Select a Folder", Toast.LENGTH_SHORT).show();

                                                                      int result = dbHandler.addLevel(node.getprojId(), node.getaID(), 0, node.getcatId(), node.getNodeLevel() + 1, node.getaID(), branchText.getText().toString(), "", 0);  //this is the ESM category
                                                                      loadProjectMap(node.getprojId());
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


                                                      break;

                                                  }

                                                  case 2: { //add pdf file


                                                      ArrayList<String> pdfList = Search_Dir("pdf");

                                                      pdf_files = new String[pdfList.size() + 1];
                                                      pdf_files[0] = "Select PDF from list";
                                                      for (int i = 0; i < pdfList.size(); i++) {
                                                          pdf_files[i + 1] = pdfList.get(i);
                                                      }

                                                      //     ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.my_spinner, pdf_files);


                                                      LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                                      View promptView = layoutInflater.inflate(R.layout.add_file, null);
                                                      AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                                      alertDialogBuilder.setView(promptView);
                                                      final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                                                      itemTitle.setText("Attach PDF file");//Integer.parseInt(locationId)
                                                      final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                                      final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
                                                      branchText.setHint("Label for pdf file");
                                                  //    final TextView file_name = (TextView) promptView.findViewById(R.id.file_name);
                                                      final Spinner spnrFiles = (Spinner) promptView.findViewById(R.id.spinner_file);

                                                      ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.my_spinner, pdf_files);
                                                      spnrFiles.setAdapter(adapter);

                                                      AdapterView.OnItemSelectedListener fileSelectedListener = new AdapterView.OnItemSelectedListener() {
                                                          @Override
                                                          public void onItemSelected(AdapterView<?> my_spinner, View container,
                                                                                     int position, long id) {
                                                              if (position != 0)
                                                          //        file_name.setText(pdf_files[position]);
                                                              pdf_name = pdf_files[position];
                                                              //     dbHandler.updateMap(node.getprojId(), node.getaID(),pdf_files[position]);
                                                              //  Edited = true;
                                                              spnrFiles.setSelection(position);
                                                          }

                                                          @Override
                                                          public void onNothingSelected(AdapterView<?> arg0) {
                                                              // TODO Auto-generated method stub
                                                          }
                                                      };


                                                      spnrFiles.setOnItemSelectedListener(fileSelectedListener);


                                                      // setup a dialog window
                                                      alertDialogBuilder.setCancelable(true)
                                                              .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                  public void onClick(DialogInterface dialog, int id) {

                                                                            int result = dbHandler.addLevel(node.getprojId(), node.getaID(), 0, node.getcatId(), node.getNodeLevel() + 1, node.getaID(), branchText.getText().toString(), pdf_name, 12);  //this is the ESM category
                                                                            loadProjectMap(node.getprojId());


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

                                                      break;

                                                  }

                                                  case 3: {


                                                      break;
                                                  }


                                              }

                                          }
                                      });

                                      AlertDialog dialog = builder.create();


                                      dialog.show();


                                      break;




                                  } // end if the PDF tab cat = 505

                                  else {

                                    String[] actions = {"Edit Topic Label",
                                            "Add sub-Title Label",
                                            "Place page in [ " + node.getNodeName() + " ] topic",
                                            "Delete label",
                                            "Cancel "};

                                    final String branchTitle = dbHandler.getMapBranchTitle(node.getprojId(), node.getcatId()); //get Branch head
                                    builder.setItems(actions, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            switch (which) {

                                                case 0: { //Edit topic label goes into sub menu


                                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                    builder.setTitle("Edit Document Outline ");
                                                    // add a list
                                                    String[] actions = {"Change Selected Text",
                                                            "Move Selected Page location",
                                                            "Delete Selected Page/title",
                                                            "Cancel"};

                                                    builder.setItems(actions, new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog_sub, int which) {
                                                            switch (which) {
                                                                case 0: {


                                                                    LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                                                    View promptView = layoutInflater.inflate(R.layout.add_location, null);
                                                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                                                    alertDialogBuilder.setView(promptView);
                                                                    final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                                                                    itemTitle.setText("Label: " + branchTitle);//Integer.parseInt(locationId)
                                                                    final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                                                    locationText.setText("Title Heaning Tab label : " + node.getNodeName());//Integer.parseInt(locationId)
                                                                    final EditText LocationText = (EditText) promptView.findViewById(R.id.locationtext);
                                                                    LocationText.setText(node.getNodeName());
                                                                    // setup a dialog window
                                                                    alertDialogBuilder.setCancelable(false)
                                                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                                public void onClick(DialogInterface dialog, int id) {
                                                                                    editLocation(node.getprojId(), node.getaID(), LocationText.getText().toString());


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
                                                                    break;
                                                                }

                                                                case 1: {//

                                                                    LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                                                    View promptView = layoutInflater.inflate(R.layout.add_location, null);
                                                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                                                    alertDialogBuilder.setView(promptView);
                                                                    final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                                                                    itemTitle.setText("File Title: " + branchTitle);//Integer.parseInt(locationId)
                                                                    final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                                                    locationText.setText("Move Current TAB : " + node.getNodeName());//Integer.parseInt(locationId)
                                                                    final EditText LocationText = (EditText) promptView.findViewById(R.id.locationtext);
                                                                    LocationText.setHint("Moveto TAB id ->");
                                                                    // setup a dialog window
                                                                    alertDialogBuilder.setCancelable(false)
                                                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                                public void onClick(DialogInterface dialog, int id) {
                                                                                    dbHandler.moveTAB(node.getprojId(), node.getaID(), Integer.parseInt(LocationText.getText().toString()));

                                                                                    loadProjectMap(node.getprojId());

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


                                                                    break;

                                                                }
                                                                case 2:{
                                                                    LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                                                    View promptView = layoutInflater.inflate(R.layout.delete_location, null);
                                                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                                                    alertDialogBuilder.setView(promptView);
                                                                    final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                                                    locationText.setText("Warning - this will delete the label and ALL the associated data");//location.getText().toString());

                                                                    alertDialogBuilder.setCancelable(false)
                                                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                                public void onClick(DialogInterface dialog, int id) {
                                                                                    deleteInspectionItem(node.getprojId(), node.getaID());

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

                                                                    break;
                                                                }
                                                                case 3: {

                                                                    break;
                                                                }

                                                            }
                                                        }
                                                    });

                                                    AlertDialog dialog_sub = builder.create();


                                                    dialog_sub.show();

                                                    break;
                                                }


                                                case 1: { //add sublabel


                                                    //  int position = GlobalVariables.pos;
                                                    LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                                    View promptView = layoutInflater.inflate(R.layout.add_location, null);
                                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                                    alertDialogBuilder.setView(promptView);
                                                    final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                                                    itemTitle.setText("Folder: " + branchTitle);//Integer.parseInt(locationId)
                                                    final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                                    locationText.setText("Add a Title Heading Tab to " + node.getNodeName() + " Folder");//Integer.parseInt(locationId)
                                                    final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
                                                    // setup a dialog window
                                                    alertDialogBuilder.setCancelable(false)
                                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    //      photoBranch = "";
                                                                    //     if (projId != 0)
                                                                    //         addLevel(1, branchText.getText().toString());

                                                                    //      else
                                                                    //           Toast.makeText(getContext(), "Create or Select a Folder", Toast.LENGTH_SHORT).show();

                                                                    int result = dbHandler.addLevel(node.getprojId(), node.getaID(), 0, node.getcatId(), node.getNodeLevel() + 1, node.getaID(), branchText.getText().toString(), "", 0);  //this is the ESM category
                                                                    loadProjectMap(node.getprojId());
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


                                                    break;

                                                }

                                                case 2: { //add page to label

                                                    if(GlobalVariables.doc_mode == 1) {
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                        builder.setTitle("Add Page ");
                                                        // add a list
                                                        String[] actions = {"Standard Note Page (6 pics)",
                                                                "Multi-4-pic Page",
                                                                "Multi-6-pic Page",
                                                                "Blank Page",
                                                                "Cancel"};

                                                        builder.setItems(actions, new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog_sub, int which) {
                                                                switch (which) {
                                                                    case 0: { // Add a commentary Page

                                                                        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                                                        View promptView = layoutInflater.inflate(R.layout.add_location, null);
                                                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                                                        alertDialogBuilder.setView(promptView);
                                                                        final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                                                                        itemTitle.setText("Title: " + branchTitle);//Integer.parseInt(locationId)
                                                                        final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                                                        locationText.setText("File page in the [ " + node.getNodeName() + " ] Folder Label Tab");//Integer.parseInt(locationId)
                                                                        final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
                                                                        // setup a dialog window

                                                                        alertDialogBuilder.setCancelable(false)
                                                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                                    public void onClick(DialogInterface dialog, int id) {
                                                                                        //        InspectionVariables.photoBranch = "";
                                                                                        int result = dbHandler.addReportBranch(node.getprojId(), iId, node.getcatId(), node.getNodeLevel() + 1, node.getaID(), branchText.getText().toString(), 1);
                                                                                        loadProjectMap(node.getprojId());
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

                                                                        break;

                                                                    }
                                                                    case 1: { // Add a Multi Pic Page
                                                                        int result = dbHandler.addReportBranch(node.getprojId(), iId, node.getcatId(), node.getNodeLevel() + 1, node.getaID(), "Multi-4-Pic", 4);
                                                                        loadProjectMap(node.getprojId());
                                                                        break;
                                                                    }

                                                                    case 2: { // Add a Multi Pic Page
                                                                        int result = dbHandler.addReportBranch(node.getprojId(), iId, node.getcatId(), node.getNodeLevel() + 1, node.getaID(), "Multi-6-Pic", 3);
                                                                        loadProjectMap(node.getprojId());
                                                                        break;
                                                                    }
                                                                    case 3: { // Add a Blank Page
                                                                        int result = dbHandler.addReportBranch(node.getprojId(), iId, node.getcatId(), node.getNodeLevel() + 1, node.getaID(), "Blank Page", 5);
                                                                        loadProjectMap(node.getprojId());
                                                                        break;
                                                                    }
                                                                    case 4: {

                                                                        break;
                                                                    }

                                                                }
                                                            }
                                                        });

                                                        AlertDialog dialog_sub = builder.create();


                                                        dialog_sub.show();
                                                    }
                                                    else Toast.makeText(getContext(), "Select a document", Toast.LENGTH_SHORT).show();
                                                break;
                                                    }

                                                case 3: { //delete

                                                    LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                                    View promptView = layoutInflater.inflate(R.layout.delete_location, null);
                                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                                    alertDialogBuilder.setView(promptView);
                                                    final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                                    locationText.setText("Warning - this will delete the label and ALL the associated data");//location.getText().toString());

                                                    alertDialogBuilder.setCancelable(false)
                                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    deleteInspectionItem(node.getprojId(), node.getaID());

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

                                                    break;
                                                }

                                                case 4: {


                                                    break;
                                                }

                                            }

                                        }
                                    });

                                    AlertDialog dialog = builder.create();


                                    dialog.show();

                                    break;

                                }
                            }
                        } //in BranchCat - switch node.getCat end case cat = 0


                        case 1: {//if page label

                            String[] actions = {"Edit Label",
                                    "Add Suplementary Page ",
                                    "Delete label",
                                    "Move label Up",
                                    "Move label Down",
                                    "Cancel "};

                            final String branchTitle = dbHandler.getMapBranchTitle(node.getprojId(), node.getcatId()); //get Branch head

                            builder.setItems(actions, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    switch (which) {

                                                case 0: {

                                                    LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                                    View promptView = layoutInflater.inflate(R.layout.add_location, null);
                                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                                    alertDialogBuilder.setView(promptView);
                                                    final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                                                    itemTitle.setText("Title Label: " + branchTitle);//Integer.parseInt(locationId)
                                                    final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                                    locationText.setText("Label : " + node.getNodeName());//Integer.parseInt(locationId)
                                                    final EditText LocationText = (EditText) promptView.findViewById(R.id.locationtext);
                                                    LocationText.setText(node.getNodeName());
                                                    // setup a dialog window
                                                    alertDialogBuilder.setCancelable(false)
                                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    editLocation(node.getprojId(), node.getaID(), LocationText.getText().toString());


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
                                                    break;

                                                }

                                                case 1: {


                                                    LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                                    View promptView = layoutInflater.inflate(R.layout.add_location, null);
                                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                                    alertDialogBuilder.setView(promptView);
                                                    final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                                                    itemTitle.setText("Page attachment ");//Integer.parseInt(locationId)
                                                    final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                                    locationText.setText("Attachment to Page: " + node.getNodeName());//Integer.parseInt(locationId)
                                                    final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
                                                    // setup a dialog window
                                                    alertDialogBuilder.setCancelable(false)
                                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    //     globalVariables.photoBranch = "";

                                                                    int result = dbHandler.addActionBranch(node.getprojId(), iId, node.getcatId(), node.getNodeLevel() + 1, node.getaID(), branchText.getText().toString());
                                                                    loadProjectMap(node.getprojId());
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


                                                    break;

                                                }

                                                case 2: {

                                                    LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                                    View promptView = layoutInflater.inflate(R.layout.delete_location, null);
                                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                                    alertDialogBuilder.setView(promptView);
                                                    final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                                    locationText.setText("Warning - this will delete the label and ALL the associated data");//location.getText().toString());

                                                    alertDialogBuilder.setCancelable(false)
                                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    deleteInspectionItem(node.getprojId(), node.getaID());

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
                                                    break;

                                                }

                                                case 3:{
                                                    dbHandler.changePos(node.getprojId(),node.getaID(),node.getiID(),1,node.getcatId(),"UP", 1);
                                                    loadProjectMap(node.getprojId());
                                                    break;

                                                }
                                                case 4:{
                                                    dbHandler.changePos(node.getprojId(),node.getaID(),node.getiID(),1,node.getcatId(),"DWN", 1);
                                                    loadProjectMap(node.getprojId());
                                                    break;
                                                }
                                                case 5:{

                                                    break;
                                                }

                                            }

                                        }
                                    });

                                    AlertDialog dialog = builder.create();


                                    dialog.show();

                                    break;

                                } // end case branchcat = 1 - page cat


                        case 2: {//if supplementary page label

                            String[] actions = {"Edit Label",
                                    "Delete label",
                                    "Move label UP",
                                    "Move label DOWN",
                                    "Cancel "};

                            final String branchTitle = dbHandler.getMapBranchTitle(node.getprojId(), node.getcatId()); //get Branch head

                            builder.setItems(actions, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    switch (which) {

                                        case 0: {

                                            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                            View promptView = layoutInflater.inflate(R.layout.add_location, null);
                                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                            alertDialogBuilder.setView(promptView);
                                            final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                                            itemTitle.setText("Title label: " + branchTitle);//Integer.parseInt(locationId)
                                            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                            locationText.setText("Label : " + node.getNodeName());//Integer.parseInt(locationId)
                                            final EditText LocationText = (EditText) promptView.findViewById(R.id.locationtext);
                                            LocationText.setText(node.getNodeName());
                                            // setup a dialog window
                                            alertDialogBuilder.setCancelable(false)
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            editLocation(node.getprojId(), node.getaID(), LocationText.getText().toString());


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
                                            break;

                                        }

                                        case 1: {

                                            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                            View promptView = layoutInflater.inflate(R.layout.delete_location, null);
                                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                            alertDialogBuilder.setView(promptView);
                                            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                            locationText.setText("Warning - this will delete the label and ALL the associated data");//location.getText().toString());

                                            alertDialogBuilder.setCancelable(false)
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            deleteInspectionItem(node.getprojId(), node.getaID());

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


                                            break;

                                        }

                                        case 2: {

                                            dbHandler.changePos(node.getprojId(),node.getaID(),node.getiID(),2,node.getcatId(),"UP", 2);
                                            loadProjectMap(node.getprojId());
                                            break;

                                        }
                                        case 3: {

                                            dbHandler.changePos(node.getprojId(),node.getaID(),node.getiID(),2,node.getcatId(),"DWN", 2);
                                            loadProjectMap(node.getprojId());
                                            break;

                                        }
                                        case 4: {


                                            break;

                                        }

                                    }

                                }
                            });

                            AlertDialog dialog = builder.create();


                            dialog.show();

                            break;

                        } // end case branchcat = 2 - supplementary page cat



                        case 3: {//if Multi 6 Pic Page

                            String[] actions = {
                                    "Move label UP",
                                    "Move label DOWN",
                                    "Delete label",
                                    "Cancel "};

                            final String branchTitle = dbHandler.getMapBranchTitle(node.getprojId(), node.getcatId()); //get Branch head

                            builder.setItems(actions, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    switch (which) {


                                        case 0: {

                                            dbHandler.changePos(node.getprojId(),node.getaID(),node.getiID(),3,node.getcatId(),"UP", 1);
                                            loadProjectMap(node.getprojId());
                                            break;

                                        }

                                        case 1: {

                                            dbHandler.changePos(node.getprojId(),node.getaID(),node.getiID(),3,node.getcatId(),"DWN", 1);
                                            loadProjectMap(node.getprojId());
                                            break;

                                        }

                                        case 2: {

                                            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                            View promptView = layoutInflater.inflate(R.layout.delete_location, null);
                                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                            alertDialogBuilder.setView(promptView);
                                            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                            locationText.setText("Warning - this will delete the label and ALL the associated data");//location.getText().toString());

                                            alertDialogBuilder.setCancelable(false)
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            deleteInspectionItem(node.getprojId(), node.getaID());

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


                                            break;

                                        }
                                        case 3: {


                                            break;

                                        }

                                    }

                                }
                            });

                            AlertDialog dialog = builder.create();


                            dialog.show();

                            break;

                        } // end case branchcat = 2 - supplementary page cat


                        case 4: {//if Multi 4 Pic Page

                            String[] actions = {
                                    "Move label UP",
                                    "Move label DOWN",
                                    "Delete label",
                                    "Cancel "};

                            final String branchTitle = dbHandler.getMapBranchTitle(node.getprojId(), node.getcatId()); //get Branch head

                            builder.setItems(actions, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    switch (which) {


                                        case 0: {

                                            dbHandler.changePos(node.getprojId(),node.getaID(),node.getiID(),4,node.getcatId(),"UP", 1);
                                            loadProjectMap(node.getprojId());
                                            break;

                                        }
                                        case 1: {

                                            dbHandler.changePos(node.getprojId(),node.getaID(),node.getiID(),4,node.getcatId(),"DWN", 1);
                                            loadProjectMap(node.getprojId());
                                            break;

                                        }

                                        case 2: {

                                            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                            View promptView = layoutInflater.inflate(R.layout.delete_location, null);
                                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                            alertDialogBuilder.setView(promptView);
                                            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                            locationText.setText("Warning - this will delete the label and ALL the associated data");//location.getText().toString());

                                            alertDialogBuilder.setCancelable(false)
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            deleteInspectionItem(node.getprojId(), node.getaID());

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


                                            break;

                                        }
                                        case 3: {


                                            break;

                                        }

                                    }

                                }
                            });

                            AlertDialog dialog = builder.create();


                            dialog.show();

                            break;

                        } // end case branchcat = 2 - supplementary page cat


                        case 5: {//if Multi Pic Page

                            String[] actions = {

                                    "Move label UP",
                                    "Move label DOWN",
                                    "Delete label",
                                    "Cancel "};

                            final String branchTitle = dbHandler.getMapBranchTitle(node.getprojId(), node.getcatId()); //get Branch head

                            builder.setItems(actions, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    switch (which) {


                                        case 0: {

                                            dbHandler.changePos(node.getprojId(),node.getaID(),node.getiID(),5,node.getcatId(),"UP", 1);
                                            loadProjectMap(node.getprojId());
                                            break;

                                        }
                                        case 1: {

                                            dbHandler.changePos(node.getprojId(),node.getaID(),node.getiID(),5,node.getcatId(),"DWN", 1);
                                            loadProjectMap(node.getprojId());
                                            break;

                                        }

                                        case 2: {

                                            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                            View promptView = layoutInflater.inflate(R.layout.delete_location, null);
                                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                            alertDialogBuilder.setView(promptView);
                                            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                            locationText.setText("Warning - this will delete the label and ALL the associated data");//location.getText().toString());

                                            alertDialogBuilder.setCancelable(false)
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            deleteInspectionItem(node.getprojId(), node.getaID());

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


                                            break;

                                        }
                                        case 3: {


                                            break;

                                        }

                                    }

                                }
                            });

                            AlertDialog dialog = builder.create();


                            dialog.show();

                            break;

                        } // end case branchcat = 5 - Blank page cat



                        case 11:{ //if focus is the reference tab


                            String[] actions = {"Edit Label",
                                    "Add Information subLabel under the [ " + node.getNodeName() + " ] tab",
                                    "Delete label",
                                    "Cancel "};

                            final String branchTitle = dbHandler.getMapBranchTitle(node.getprojId(), node.getcatId()); //get Branch head
                            builder.setItems(actions, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    switch (which) {

                                        case 0: { //Edit topic label goes into sub menu


                                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                            builder.setTitle("Edit Document Outline ");
                                            // add a list
                                            String[] actions = {"Change Label Text",
                                                    "Delete tab",
                                                    "Cancel"};

                                            builder.setItems(actions, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog_sub, int which) {
                                                    switch (which) {
                                                        case 0: {


                                                            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                                            View promptView = layoutInflater.inflate(R.layout.add_location, null);
                                                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                                            alertDialogBuilder.setView(promptView);
                                                            final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                                                            itemTitle.setText("Label: " + branchTitle);//Integer.parseInt(locationId)
                                                            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                                            locationText.setText("Label name : " + node.getNodeName());//Integer.parseInt(locationId)
                                                            final EditText LocationText = (EditText) promptView.findViewById(R.id.locationtext);
                                                            LocationText.setText(node.getNodeName());
                                                            // setup a dialog window
                                                            alertDialogBuilder.setCancelable(false)
                                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                        public void onClick(DialogInterface dialog, int id) {
                                                                            editLocation(node.getprojId(), node.getaID(), LocationText.getText().toString());


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
                                                            break;
                                                        }


                                                        case 1:{
                                                            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                                            View promptView = layoutInflater.inflate(R.layout.delete_location, null);
                                                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                                            alertDialogBuilder.setView(promptView);
                                                            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                                            locationText.setText("Warning - this will delete the label and ALL the associated data");//location.getText().toString());

                                                            alertDialogBuilder.setCancelable(false)
                                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                        public void onClick(DialogInterface dialog, int id) {
                                                                            deleteInspectionItem(node.getprojId(), node.getaID());

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

                                                            break;
                                                        }
                                                        case 2: {

                                                            break;
                                                        }

                                                    }
                                                }
                                            });

                                            AlertDialog dialog_sub = builder.create();


                                            dialog_sub.show();

                                            break;
                                        }




                                        case 1: { //add page to label


                                            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                            View promptView = layoutInflater.inflate(R.layout.add_location, null);
                                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                            alertDialogBuilder.setView(promptView);
                                            final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                                            itemTitle.setText("Title Label: " + branchTitle);//Integer.parseInt(locationId)
                                            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                            locationText.setText("File page in the [ " + node.getNodeName() + " ] Label");//Integer.parseInt(locationId)
                                            final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
                                            // setup a dialog window

                                            alertDialogBuilder.setCancelable(false)
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            //        InspectionVariables.photoBranch = "";
                                                            int result = dbHandler.addReference(node.getprojId(), node.getcatId(), node.getNodeLevel() + 1, node.getaID(), branchText.getText().toString());
                                                            loadProjectMap(node.getprojId());
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

                                            break;

                                        }

                                        case 2: { //delete

                                            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                            View promptView = layoutInflater.inflate(R.layout.delete_location, null);
                                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                            alertDialogBuilder.setView(promptView);
                                            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                            locationText.setText("Warning - this will delete the label and ALL the associated data");//location.getText().toString());

                                            alertDialogBuilder.setCancelable(false)
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            deleteInspectionItem(node.getprojId(), node.getaID());

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

                                            break;
                                        }

                                        case 3: {


                                            break;
                                        }

                                    }

                                }
                            });

                            AlertDialog dialog = builder.create();


                            dialog.show();

                            break;

                        }


                        case 12: { // if pdf label

                            String[] actions = {"Edit Label",
                                    "Add PDF sublabel ",
                                    "Place PDF file in ["+node.getNodeName()+"] label",
                                    "Delete label",
                                    "Cancel "};

                            builder.setItems(actions, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    final String branchTitle = dbHandler.getMapBranchTitle(node.getprojId(), node.getcatId()); //get Branch head
                                    switch (which) {

                                        case 0: {
                                            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                            View promptView = layoutInflater.inflate(R.layout.add_location, null);
                                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                            alertDialogBuilder.setView(promptView);
                                            final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                                            itemTitle.setText("Title label: " + branchTitle);//Integer.parseInt(locationId)
                                            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                            locationText.setText("Label : " + node.getNodeName());//Integer.parseInt(locationId)
                                            final EditText LocationText = (EditText) promptView.findViewById(R.id.locationtext);
                                            LocationText.setText(node.getNodeName());
                                            // setup a dialog window
                                            alertDialogBuilder.setCancelable(false)
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            editLocation(node.getprojId(), node.getaID(), LocationText.getText().toString());


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
                                            break;
                                        }

                                        case 1: {
                                            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                            View promptView = layoutInflater.inflate(R.layout.add_location, null);
                                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                            alertDialogBuilder.setView(promptView);
                                            final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                                            itemTitle.setText("Folder: " + branchTitle);//Integer.parseInt(locationId)
                                            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                            locationText.setText("Add a Title to " + node.getNodeName() + " Folder");//Integer.parseInt(locationId)
                                            final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
                                            // setup a dialog window
                                            alertDialogBuilder.setCancelable(false)
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            //      photoBranch = "";
                                                            //     if (projId != 0)
                                                            //         addLevel(1, branchText.getText().toString());

                                                            //      else
                                                            //           Toast.makeText(getContext(), "Create or Select a Folder", Toast.LENGTH_SHORT).show();

                                                            int result = dbHandler.addLevel(node.getprojId(), node.getaID(), 0, node.getcatId(), node.getNodeLevel() + 1, node.getaID(), branchText.getText().toString(), "", 0);  //this is the ESM category
                                                            loadProjectMap(node.getprojId());
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

                                            break;
                                        }

                                        case 2: {

                                            ArrayList<String> pdfList = Search_Dir("pdf");

                                            pdf_files = new String[pdfList.size() + 1];
                                            pdf_files[0] = "Select PDF File from list";
                                            for (int i = 0; i < pdfList.size(); i++) {
                                                pdf_files[i + 1] = pdfList.get(i);
                                            }

                                            //     ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.my_spinner, pdf_files);


                                            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                            View promptView = layoutInflater.inflate(R.layout.add_file, null);
                                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                            alertDialogBuilder.setView(promptView);
                                            final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                                            itemTitle.setText("PDF file label");//Integer.parseInt(locationId)
                                            final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
                                            branchText.setHint("Label Title ... file name, etc");
                                          //  final TextView file_name = (TextView) promptView.findViewById(R.id.file_name);
                                            final Spinner spnrFiles = (Spinner) promptView.findViewById(R.id.spinner_file);

                                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.my_spinner, pdf_files);
                                            spnrFiles.setAdapter(adapter);

                                            AdapterView.OnItemSelectedListener fileSelectedListener = new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> my_spinner, View container,
                                                                           int position, long id) {
                                                    if (position != 0)
                                                //        file_name.setText(pdf_files[position]);
                                                        pdf_name = pdf_files[position];
                                                   //     dbHandler.updateMap(node.getprojId(), node.getaID(),pdf_files[position]);
                                                    //  Edited = true;
                                                    spnrFiles.setSelection(position);
                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> arg0) {
                                                    // TODO Auto-generated method stub
                                                }
                                            };


                                            spnrFiles.setOnItemSelectedListener(fileSelectedListener);


                                            // setup a dialog window
                                            alertDialogBuilder.setCancelable(true)
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            if(pdf_name != null) {
                                                                int result = dbHandler.addLevel(node.getprojId(), node.getaID(), 0, node.getcatId(), node.getNodeLevel() + 1, node.getaID(), branchText.getText().toString(), pdf_name, 12);  //this is the ESM category
                                                                loadProjectMap(node.getprojId());
                                                            }
                                                            else Toast.makeText(getContext(), "Select a PDF file", Toast.LENGTH_SHORT).show();

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

                                            break;

                                        }
                                        case 3: {
                                            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                            View promptView = layoutInflater.inflate(R.layout.delete_location, null);
                                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                            alertDialogBuilder.setView(promptView);
                                            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                                            locationText.setText("Warning - this will delete the label and ALL the associated data");//location.getText().toString());

                                            alertDialogBuilder.setCancelable(false)
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            deleteInspectionItem(node.getprojId(), node.getaID());

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
                                            break;
                                        }

                                        case 4:{

                                            break;
                                        }

                                    }
                                }
                            });


                            AlertDialog dialog = builder.create();


                            dialog.show();


                            break;
                        } // end if PDF label

                    } // end of branchcat switch
                }
            }
        };
    }




    public void deleteInspectionItem(int projId, int aID) {

        DBHandler dbHandler = new DBHandler(getContext(), null, null, 1);


        int type = dbHandler.getMapNodeType(projId, aID);

        switch (type){

            case(-1):{
                Toast.makeText(getContext(), "Cannot delete Parent branch, delete child branches first", Toast.LENGTH_SHORT).show();

                break;
            }

            case (0): {
                dbHandler.deleteMapBranch(projId, aID);
                dbHandler.deleteRec("MAP",projId, iId,aID);
                dbHandler.deleteInspectionItem(projId, aID);
                dbHandler.deleteRec("InspectionItem",projId, iId,aID);

             /*     GlobalVariables.dataList.remove(GlobalVariables.pos);

                GlobalVariables.pos = GlobalVariables.pos - 1;
                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                GlobalVariables.modified = true;

                GlobalVariables.dataList = MapViewLists.LoadInitialData();

               // GlobalVariables.nodes = MapViewLists.LoadInitialNodes(GlobalVariables.dataList);
                MapViewNode node = GlobalVariables.displayNodes.get(GlobalVariables.pos);
                          if (GlobalVariables.pos != ListView.INVALID_POSITION) {

                              if (node.getIsExpanded() == GlobalVariables.TRUE) {
                                  node.setIsExpanded(GlobalVariables.FALSE);

                              } else {
                                  if (node.getNodeChildren() != null)
                                      node.setIsExpanded(GlobalVariables.TRUE);

                              }
                              node.setIsExpanded(GlobalVariables.TRUE);
                          }


                MapViewLists.LoadDisplayList();
                OnProjectSelectionChangeListener listener = (OnProjectSelectionChangeListener) getContext();

                listener.OnTabChanged(GlobalVariables.pos);

              */



               loadProjectMap(projId);
                break;
            }

            case (1):
            case (3):
            case (4):
            case (5):    {
                dbHandler.deleteMapBranch(projId, aID);
                dbHandler.deleteRec("MAP",projId, iId,aID);
                dbHandler.deleteInspectionItem(projId, aID);
                dbHandler.deleteRec("InspectionItem",projId, iId,aID);
                GlobalVariables.pos = GlobalVariables.pos - 1;
                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                loadProjectMap(projId);
                break;
            }
            case (2):{

                dbHandler.deleteMapBranch(projId, aID);
                dbHandler.deleteRec("MAP",projId, iId,aID);
                dbHandler.deleteInspectionItem(projId, aID);
                dbHandler.deleteRec("InspectionItem",projId, iId,aID);
                dbHandler.deleteActionItem(projId,aID);
                dbHandler.deleteRec("ActionItem",projId, iId,aID);
                GlobalVariables.pos = GlobalVariables.pos - 1;
                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                loadProjectMap(projId);
                break;
            }

            case (9):{

                dbHandler.deleteSummary(projId, iId,aID);
                dbHandler.deleteRec("Summary",projId, iId,aID);
                dbHandler.deleteMapBranch(projId, aID);
                dbHandler.deleteRec("MAP",projId, iId,aID);
                GlobalVariables.pos = GlobalVariables.pos - 1;
                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                loadProjectMap(projId);
                break;
            }

            case (10):{

                dbHandler.deleteCertificate(projId, iId,aID);
                dbHandler.deleteRec("CertificateInsp",projId, iId,aID);
                dbHandler.deleteMapBranch(projId, aID);
                dbHandler.deleteRec("MAP",projId, iId,aID);
                GlobalVariables.pos = GlobalVariables.pos - 1;
                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                loadProjectMap(projId);
                break;
            }

            case (11):{

                dbHandler.deleteMapBranch(projId, aID);
                dbHandler.deleteRec("MAP",projId, iId,aID);
                dbHandler.deleteInspectionItem(projId, aID);
                dbHandler.deleteRec("InspectionItem",projId, iId,aID);
                GlobalVariables.pos = GlobalVariables.pos - 1;
                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                loadProjectMap(projId);
                break;
            }

            case (12):{

                dbHandler.deleteMapBranch(projId, aID);
                dbHandler.deleteRec("MAP",projId, iId,aID);
                GlobalVariables.pos = GlobalVariables.pos - 1;
                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                loadProjectMap(projId);
                break;
            }

        }

    }









        // showMessage("Adapter listener "+node.getNodeChildren().toString());


    public void editLocation(int projId, int aID, String branchLabel) {

        DBHandler dbHandler = new DBHandler(getContext(), null, null, 1);
        int success = dbHandler.updateMapLabel(projId, aID, branchLabel);
        if(success == 1) loadProjectMap(projId);

    }

    public ArrayList<String> Search_Dir(String fileType) {
        ArrayList<String> files = new ArrayList<String>();


        File FileList[] = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).listFiles();

        if (FileList != null) {
            for (int i = 0; i < FileList.length; i++) {
                if (FileList[i].getName().endsWith(fileType)){
                    //here you have that file.

                    files.add(FileList[i].getName());
                }

            }
        }
        return files;
    }

/*
    public void loadMap(int projId) {

        DBHandler dbHandler = new DBHandler(getContext(), null, null, 1);
        ArrayList<HashMap<String, String>> SiteMapData = dbHandler.getMap(projId, iId, 15);

        ArrayList listItems = new ArrayList<>();
        MapViewData listItem;

        for (int i = 0; i < (SiteMapData.size()); i++) {

            listItem = new MapViewData(
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_PROJECT_ID)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_LEVEL)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_CAT_ID)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_CHILD)),
                    SiteMapData.get(i).get(MyConfig.TAG_LABEL),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_A_ID)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_INSPECTION_ID)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_PARENT)),
                    SiteMapData.get(i).get(MyConfig.TAG_IMAGE1),
                    SiteMapData.get(i).get(MyConfig.TAG_NOTES)
            );
            listItems.add(listItem);
        }


        GlobalVariables.dataList = (ArrayList<MapViewData>) listItems;
        GlobalVariables.modified = true;

        GlobalVariables.dataList = MapViewLists.LoadInitialData();

        GlobalVariables.nodes = MapViewLists.LoadInitialNodes(GlobalVariables.dataList);
        MapViewLists.LoadInitialData();
        MapViewLists.LoadDisplayList();

      //  MapListAdapter mAdapter = new MapListAdapter(getContext());
     //   mAdapter.notifyDataSetChanged();

        OnProjectSelectionChangeListener listener = (OnProjectSelectionChangeListener) getContext();
        listener.OnTabChanged(0);




    }


 */

    public void loadFolderMap(int USER_ID, int projId) {

        DBHandler dbHandler = new DBHandler(getContext(), null, null, 1);



        ArrayList<HashMap<String, String>> SiteMapData = dbHandler.getMap(projId, iId, 15); //child < 15 includes all types

        ArrayList maplistItems = new ArrayList<>();
        MapViewData listItem;

        for (int i = 0; i < (SiteMapData.size()); i++) {

            listItem = new MapViewData(

                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_PROJECT_ID)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_LEVEL)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_CAT_ID)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_CHILD)),
                    SiteMapData.get(i).get(MyConfig.TAG_LABEL),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_A_ID)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_INSPECTION_ID)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_PARENT)),
                    SiteMapData.get(i).get(MyConfig.TAG_IMAGE1),
                    SiteMapData.get(i).get(MyConfig.TAG_NOTES)
            );
            maplistItems.add(listItem);
        }

        GlobalVariables.modified = true;
        GlobalVariables.dataList = (ArrayList<MapViewData>) maplistItems;
        GlobalVariables.modified = true;

        GlobalVariables.dataList = MapViewLists.LoadInitialData();

        GlobalVariables.nodes = MapViewLists.LoadInitialNodes(GlobalVariables.dataList);

        MapViewLists.LoadDisplayList();

       // tabchangelistener listener = (tabchangelistener) getContext();
      //  listener.OnTabChanged_(GlobalVariables.folderIndex);

     //   MapListAdapter mAdapter = new MapListAdapter(getContext());
    //    mAdapter.notifyDataSetChanged();




    }

    public void loadProjectMap(int projId) {

        DBHandler dbHandler = new DBHandler(getContext(), null, null, 1);

        List<MapViewData> maplistItems = new ArrayList<>();

        ArrayList<HashMap<String, String>> SiteMapData = dbHandler.getMap(projId, iId, 15); //child < 15 includes all types

        List<ProjectData> listItems = new ArrayList<>();
        MapViewData listItem = null;





        listItem = new MapViewData(

                Integer.parseInt(SiteMapData.get(0).get(MyConfig.TAG_PROJECT_ID)),
                0,
                0,
                0,
                SiteMapData.get(0).get(MyConfig.TAG_LABEL),
                -1,
                0,
                -1,
                SiteMapData.get(0).get(MyConfig.TAG_IMAGE1),
                SiteMapData.get(0).get(MyConfig.TAG_NOTES)
        );
        maplistItems.add(listItem);


        listItem = new MapViewData(

                Integer.parseInt(SiteMapData.get(0).get(MyConfig.TAG_PROJECT_ID)),
                0,
                0,
                0,
                SiteMapData.get(0).get("INSPECTION_LABEL"),
                0,
                0,
                0,
                SiteMapData.get(0).get(MyConfig.TAG_IMAGE1),
                SiteMapData.get(0).get(MyConfig.TAG_NOTES)
        );
        maplistItems.add(listItem);
        for (int i = 1; i < (SiteMapData.size()); i++) {


            listItem = new MapViewData(

                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_PROJECT_ID)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_LEVEL)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_CAT_ID)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_CHILD)),
                    SiteMapData.get(i).get(MyConfig.TAG_LABEL),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_A_ID)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_INSPECTION_ID)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_PARENT)),
                    SiteMapData.get(i).get(MyConfig.TAG_IMAGE1),
                    SiteMapData.get(i).get(MyConfig.TAG_NOTES)


            );


            maplistItems.add(listItem);

        }

        GlobalVariables.modified = true;
        GlobalVariables.dataList = (ArrayList<MapViewData>) maplistItems;
        MapViewLists.LoadInitialData();
        MapViewLists.LoadDisplayList();

        OnProjectSelectionChangeListener listener = (OnProjectSelectionChangeListener) getContext();
        listener.OnTabChanged(GlobalVariables.pos);



    }

    private void showMessage(String message) {
        //Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();


        Log.v("show message", message);
    }
}


