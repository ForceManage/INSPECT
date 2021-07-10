package com.forcemanage.inspect.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.forcemanage.inspect.DBHandler;
import com.forcemanage.inspect.GlobalVariables;
import com.forcemanage.inspect.MainActivity;
import com.forcemanage.inspect.MapViewFragment;
import com.forcemanage.inspect.MapViewLists;
import com.forcemanage.inspect.MyConfig;
import com.forcemanage.inspect.OnDocChangeListener;
import com.forcemanage.inspect.OnProjectSelectionChangeListener;
import com.forcemanage.inspect.ProjectViewList;
import com.forcemanage.inspect.R;
import com.forcemanage.inspect.attributes.MapViewData;
import com.forcemanage.inspect.attributes.ProjectData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FolderTreeFragment extends Fragment implements OnDocChangeListener, View.OnClickListener {

    private MainActivity globalVariables;

    private static final String TAG = "Folder Tree Fragment";
    private List<MapViewData> maplistItems;
    private List<ProjectData> listItems;
    private int USER_ID = 0;
    private int projId = 0;
    private int iId = 0;
    private Boolean doc = false;
    private Boolean Edited = false;
    private String item;
    private TextView branchTitle;
    private TextView branch;
    private TextView folderName;
    private String docName = "";
    private String branchNote = "";
    private String Folder = "";
    private String FolderID = "";
    private String ProjAddress = "";
    private String folderNote = "";
    private String siteNotes = "";
    private String projectId;
    private String inspectionDate;
    private String typeInspection;
    private String auditor;
    private String dateInspected;
    private String startTime = "";
    private String endTime = "";
    private String docPhoto;
    private EditText SiteNotes;
    private EditText FolderNote;
    private TextView TVinfoA;
    private TextView TVinfoB;
    private TextView TVinfoC;
    private TextView TVinfoD;
    private TextView TVinfoE;
    private TextView TVinfoF;
    private TextView TVinfoG;
    private TextView TVinfoH;
    private TextView doc_name;
    private TextView folder_name;
    private String infoA;
    private String infoB;
    private String infoC;
    private String infoD;
    private String infoE;
    private String infoF;
    private String infoG;
    private String infoH;
    private int aId;
    private int Level;
    private int catId;
    private ImageView timer;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            projId = bundle.getInt("projId");
            USER_ID = bundle.getInt("USER_ID");
            doc = bundle.getBoolean("doc");
            FolderID = bundle.getString("Folder_ID");
            Folder = bundle.getString("Folder");
            folderNote = bundle.getString("foldernote");
            siteNotes = bundle.getString("note");
            auditor = bundle.getString("auditor");
            inspectionDate = bundle.getString("date");
            dateInspected = bundle.getString("date");
            typeInspection = bundle.getString("inspectType");
            startTime = bundle.getString("startTime");
            endTime = bundle.getString("endTime");
            docPhoto = bundle.getString("docPhoto");
            docName = bundle.getString("doc_name");
            //   branchLabel = bundle.getString("branchLabel");
            branchNote = bundle.getString("notes");
            ProjAddress = bundle.getString("address");

            infoA = bundle.getString("infoA");
            infoB = bundle.getString("infoB");
            infoC = bundle.getString("infoC");
            infoD = bundle.getString("infoD");
            infoE = bundle.getString("infoE");
            infoF = bundle.getString("infoF");
            infoG = bundle.getString("infoG");
            infoH = bundle.getString("infoH");

            Level = bundle.getInt("Level");
            catId = bundle.getInt("catId");
            aId = bundle.getInt("aId");
            iId = bundle.getInt("iId");


        }

        Edited = false;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        globalVariables = (MainActivity) getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.folder_tree, container, false);

        folderName = (TextView) view.findViewById((R.id.folder_name));
        folderName.setText(Folder);
        branchTitle = (TextView) view.findViewById((R.id.branchTitle));
        branchTitle.setText(Folder);
        branch = (TextView) view.findViewById((R.id.branchName));
        branch.setText("Folder ID: " + FolderID);
        SiteNotes = (EditText) view.findViewById(R.id.siteNotes);
        SiteNotes.setText(siteNotes);
        SiteNotes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Edited = true;
                    item = "docs";
                }

            }
        });

        FolderNote = (EditText) view.findViewById(R.id.folder_note);
        FolderNote.setText(folderNote);
        FolderNote.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Edited = true;
                    item = "folder";
                }

            }
        });

        timer = (ImageView) view.findViewById(R.id.timer);
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Record time", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView preview = (ImageView) view.findViewById(R.id.btn_prev);
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.reportMenuPrev();
            }
        });

        ImageView print = (ImageView) view.findViewById(R.id.btn_prnt);
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.reportMenu();
            }
        });


        DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);

        if (doc == false) {   //if the tab selected  is the project tab
            view.findViewById(R.id.folder_info).setVisibility(View.VISIBLE);

        }
        else view.findViewById(R.id.document_info).setVisibility(View.VISIBLE);


        if (doc == true) { //if the tab selected is a document tab

            HashMap<String, String> inspectionData = dbHandler.getInspectionData(projId, iId);

            String MapBranch;


            //       photoBranch = inspectionData.get(MyConfig.TAG_IMAGE1);
            String inspLabel = inspectionData.get(MyConfig.TAG_LABEL); //This is the inspection label column
            String branchLabel = inspectionData.get("MAP_LABEL"); //This is the Map label column
            String branchNote = inspectionData.get(MyConfig.TAG_NOTES);
            inspectionDate = inspectionData.get(MyConfig.TAG_INSPECTION_DATE);
            typeInspection = inspectionData.get(MyConfig.TAG_INSPECTION_TYPE);
            startTime = inspectionData.get(MyConfig.TAG_START_DATE_TIME);
            endTime = inspectionData.get(MyConfig.TAG_END_DATE_TIME);
            doc_name = (TextView) view.findViewById(R.id.doc_name);
            doc_name.setText(inspLabel);
            branchTitle.setText(inspLabel);

            TextView inspectDate = (TextView) view.findViewById(R.id.Text_2);
            TextView Hrs = (TextView) view.findViewById(R.id.Text_5);
            TextView inspectionType = (TextView) view.findViewById(R.id.Text_3);
            TextView inspectedDate = (TextView) view.findViewById(R.id.Text_4);


          if(inspectDate != null)  inspectDate.setText("Document created:  " + stringdate(inspectionDate, 1));
            inspectionType.setText("Document type:  " + typeInspection);
            if (startTime != null)
                inspectedDate.setText("Initial log recorded: " + stringdate(startTime, 2) + "  -  " + stringdate(endTime, 2));
            Hrs.setText("Allocation:  " + dbHandler.calcTime(Integer.toString(projId), Integer.toString(iId)));
        }

        projectId = Integer.toString(projId);

        final TextView doc_name = (TextView) view.findViewById(R.id.doc_name);
        doc_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DBHandler dbHandler = new DBHandler(getContext(), null, null, 1);

                final String branchTitle = dbHandler.getMapBranchTitle(projId, GlobalVariables.catId); //get Branch head


                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                View promptView = layoutInflater.inflate(R.layout.add_location, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setView(promptView);
                final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                itemTitle.setText("Folder Title: " + branchTitle);//Integer.parseInt(locationId)
                final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                locationText.setText("Document title : " + GlobalVariables.name);//Integer.parseInt(locationId)
                final EditText LocationText = (EditText) promptView.findViewById(R.id.locationtext);
                LocationText.setText(GlobalVariables.name);
                // setup a dialog window
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (LocationText.getText().toString() == "NULL" || LocationText.getText().toString() == null)
                                    Toast.makeText(getContext(), "Invalid TAB", Toast.LENGTH_SHORT).show();
                                else
                                    // editLocation(LocationText.getText().toString());

                                    dbHandler.updateInspectionLabel(Integer.toString(projId), Integer.toString(iId), LocationText.getText().toString());
                                    globalVariables.updatePropList();

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
        });


         folderName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Folder Title", folderName.getText().toString());
            }
        });

        TVinfoA = (TextView) view.findViewById(R.id.text2);
        TVinfoA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Note A", TVinfoA.getText().toString());
            }
        });

        TVinfoB = (TextView) view.findViewById(R.id.text3);
        TVinfoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Note B", TVinfoB.getText().toString());
            }
        });

        TVinfoC = (TextView) view.findViewById(R.id.text4);
        TVinfoC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Note C", TVinfoC.getText().toString());
            }
        });

        TVinfoD = (TextView) view.findViewById(R.id.text5);
        TVinfoD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Note D", TVinfoD.getText().toString());
            }
        });
        TVinfoE = (TextView) view.findViewById(R.id.text6);
        TVinfoE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Note E", TVinfoE.getText().toString());
            }
        });

        TVinfoF = (TextView) view.findViewById(R.id.text7);
        TVinfoF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Note F", TVinfoF.getText().toString());
            }
        });

        TVinfoG = (TextView) view.findViewById(R.id.text8);
        TVinfoG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Note G", TVinfoG.getText().toString());
            }
        });

        TVinfoH = (TextView) view.findViewById(R.id.text9);
        TVinfoH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Note H", TVinfoH.getText().toString());
            }
        });


        return view;

    }

    @Override
    public void onClick(View v) {


    }

    @Override
    public void OnTabChanged(int treeNameIndex) {

    }

    public void editProject(final String  item, String value){

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View promptView = layoutInflater.inflate(R.layout.add_location, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptView);
        final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
        itemTitle.setText("Folder Information ");//Integer.parseInt(locationId)
        final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
        locationText.setText("Current Label: "+item);//Integer.parseInt(locationId)
        final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
        branchText.setHint(value);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        DBHandler dbHandler = new DBHandler(getContext(), null, null, 1);
                        if(projectId == "" | projectId.equals(null))
                            Toast.makeText(getContext(), "Select a Folder ",Toast.LENGTH_LONG).show();
                        else{
                            switch (item) {

                                case "Folder Title": {

                                    if(branchText.getText().toString()=="")
                                        Toast.makeText(getContext(), "Retry with valid text ",Toast.LENGTH_LONG).show();
                                    else {
                                        dbHandler.updateProject(projectId, item, branchText.getText().toString(), 0);
                                        globalVariables.updatePropList();
                                        dbHandler.statusChanged(projId,0);
                                        OnTabChanged(0);
                                        globalVariables.OnTabChanged(0);
                                    }
                                    //
                                    break;
                                }
                                case "Folder ID": {
                                    dbHandler.updateProject(projectId, item, branchText.getText().toString(), 0);
                                    dbHandler.statusChanged(projId,0);
                                    globalVariables.OnProjectChanged(0);
                                    break;
                                }
                                case "Note A": {
                                    dbHandler.updateProject(projectId, "infoA", branchText.getText().toString(), 0);
                                    dbHandler.statusChanged(projId,0);
                                    globalVariables.OnProjectChanged(0);
                                    break;
                                }
                                case "Note B": {
                                    dbHandler.updateProject(projectId, "infoB", branchText.getText().toString(), 0);
                                    dbHandler.statusChanged(projId,0);
                                    globalVariables.OnProjectChanged(0);
                                    break;
                                }
                                case "Note C": {
                                    dbHandler.updateProject(projectId, "infoC", branchText.getText().toString(), 0);
                                    dbHandler.statusChanged(projId,0);
                                    globalVariables.OnProjectChanged(0);
                                    break;
                                }
                                case "Note D": {
                                    dbHandler.updateProject(projectId, "infoD", branchText.getText().toString(), 0);
                                    dbHandler.statusChanged(projId,0);
                                    globalVariables.OnProjectChanged(0);
                                    break;
                                }
                                case "Note E": {
                                    dbHandler.updateProject(projectId, "infoE", branchText.getText().toString(), 0);
                                    dbHandler.statusChanged(projId,0);
                                    break;
                                }
                                case "Note F": {
                                    dbHandler.updateProject(projectId, "infoF", branchText.getText().toString(), 0);
                                    dbHandler.statusChanged(projId,0);
                                    break;
                                }
                                case "Note G": {
                                    dbHandler.updateProject(projectId, "infoG", branchText.getText().toString(), 0);
                                    dbHandler.statusChanged(projId,0);
                                    break;
                                }
                                case "Note H": {
                                    dbHandler.updateProject(projectId, "infoH", branchText.getText().toString(), 0);
                                    dbHandler.statusChanged(projId,0);
                                    break;
                                }
                            } // if there is a valid project
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

    public String stringdate(String date, int type) {

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
        switch (item){

            case "docs":{
                dbHandler.updateInspectionNotes(projId, iId, SiteNotes.getText().toString(), "");

                break;
            }
            case "folder":{
                dbHandler.updateProject(Integer.toString(projId), "FolderNote", FolderNote.getText().toString(),0);
                break;
            }
          }

        dbHandler.statusChanged(projId, iId);
        Edited = false;

    }

 /*   @Override
    public void onDestroy() {
        super.onDestroy();
        if (Edited) saveData();
    }

  */

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (Edited) saveData();
    }

}

