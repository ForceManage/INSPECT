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
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.forcemanage.inspect.BuildConfig;
import com.forcemanage.inspect.DBHandler;
import com.forcemanage.inspect.DetailFragment;
import com.forcemanage.inspect.DetailProjectFragment;
import com.forcemanage.inspect.GlobalVariables;
import com.forcemanage.inspect.InspectionActivity;
import com.forcemanage.inspect.MainActivity;
import com.forcemanage.inspect.MapViewLists;
import com.forcemanage.inspect.OnVerseNameSelectionChangeListener;
import com.forcemanage.inspect.ProjectDetailFragment;
import com.forcemanage.inspect.ProjectViewFragment;
import com.forcemanage.inspect.ProjectViewList;
import com.forcemanage.inspect.adapters.ReportDocsAdapter;
import com.forcemanage.inspect.attributes.MapViewData;
import com.forcemanage.inspect.MapViewFragment;
import com.forcemanage.inspect.MyConfig;
import com.forcemanage.inspect.R;
import com.forcemanage.inspect.attributes.MapViewNode;
import com.forcemanage.inspect.attributes.ProjectData;
import com.forcemanage.inspect.attributes.ProjectNode;
import com.forcemanage.inspect.attributes.Projectlistdata;
import com.forcemanage.inspect.tabchangelistener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ProjectInfoFragment extends Fragment implements tabchangelistener, View.OnClickListener {

    private MainActivity globalVariables;

    private static final String TAG = "Project Info Fragment";

 //   private RecyclerView mRecyclerView;
 //   private RecyclerView.Adapter mAdapter;
  //  private RecyclerView.LayoutManager mLayoutManager;


    private TextView branch;
    private ImageView mPhotoImageView;
    private ImageView photo_cam;
    private ImageView info_file;
    private ImageView photo_file;
    private ImageView edit_Tab;
    private ImageView delete_Tab;
    private ImageView page_Tab;
    private ImageView printer;
    private String Folder = "";
    private String FolderID = "";
    private String docName = "";

    private String branchNote = "";
    private String ProjAddress = "";
    private Boolean Edited = false;
    private String folderNote="";
    private String siteNotes ="";
    private String projectId;
    private int projId = 0;
    private int iId = 0;
    private String infoA;
    private String infoB;
    private String infoC;
    private String infoD;
    private String infoE;
    private String infoF;
    private String infoG;
    private String infoH;
    private String folderPhoto;
    private String FragDisplay;
    private List<ProjectData> listItems;
    private  int aId;
    private int Level;
    private int catId;
    private int USER_ID = 0;
    private LinearLayout linearLayout;
    private String inspectionDate;
    private String typeInspection;
    private String auditor;
    private String dateInspected;
    private String startTime ="";
    private String endTime ="";
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




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            projId = bundle.getInt("projId");
            USER_ID = bundle.getInt("USER_ID");
            FolderID = bundle.getString("Folder_ID");
            Folder = bundle.getString("Folder");
            folderNote = bundle.getString("foldernote");
            siteNotes = bundle.getString("siteNotes");
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
            aId = bundle.getInt("aID");




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

        View view = inflater.inflate(R.layout.project_info, container, false);


        Log.d(TAG, "oncreateview: started");



        final TextView folder = (TextView) view.findViewById((R.id.branchTitle));
        folder.setText("Files - "+ Folder);

        branch = (TextView) view.findViewById((R.id.branchName));
        branch.setText("Folder ID: "+FolderID);
        SiteNotes = (EditText) view.findViewById(R.id.siteNotes);
        SiteNotes.setText(siteNotes);
        SiteNotes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) Edited = true;

            }
        });
        FolderNote = (EditText) view.findViewById(R.id.folder_note);
        FolderNote.setText(folderNote);
        FolderNote.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) Edited = true;

            }
        });


        DBHandler dbHandler = new DBHandler(getContext(), null, null, 1);

        ArrayList<HashMap<String, String>> SiteMapData = dbHandler.getProjects(USER_ID,projId, Level, catId, aId); //child 9 shows only the types <9

        listItems = new ArrayList<>();
        ProjectData listItem;

        for (int i = 0; i < (SiteMapData.size()); i++) {

            listItem = new ProjectData(
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_PROJECT_ID)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_LEVEL)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_LEVEL)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_LEVEL)),
                    SiteMapData.get(i).get(MyConfig.TAG_LABEL),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_P_ID)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_INSPECTION_ID)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_PARENT)),
                    SiteMapData.get(i).get(MyConfig.TAG_IMAGE),
                    SiteMapData.get(i).get(MyConfig.TAG_NOTE)
            );
            listItems.add(listItem);
        }

        GlobalVariables.projectList = (ArrayList<ProjectData>) listItems;
        ProjectViewList.LoadDisplayList();
       // if (GlobalVariables.dataList.isEmpty() != true)
         GlobalVariables.modified = true;


        if (view.findViewById(R.id.fragment_folder) != null) {

            // However if we are being restored from a previous state, then we don't
            // need to do anything and should return or we could end up with overlapping Fragments
            //    if (savedInstanceState != null){
            //        return;
            //     }
//
            // Create an Instance of Fragment
            ProjectViewFragment projectViewFragment = new ProjectViewFragment();

            //  treeFragment.setArguments(getIntent().getExtras());


            getChildFragmentManager().beginTransaction().add(R.id.fragment_folder, projectViewFragment)
                    .commit();
        }

        if(globalVariables.focus == "DOCS") {
            TextView inspectDate = (TextView) view.findViewById(R.id.Text_2);
            TextView Hrs = (TextView) view.findViewById(R.id.Text_5);
            TextView inspectionType = (TextView) view.findViewById(R.id.Text_3);
            TextView inspectedDate = (TextView) view.findViewById(R.id.Text_4);


            inspectDate.setText("Document created:  " + stringdate(inspectionDate, 1));
            inspectionType.setText("Document type:  " + typeInspection);
            if (!startTime.equals("null"))
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
                                if(LocationText.getText().toString() == "NULL" || LocationText.getText().toString() == null)
                                    Toast.makeText(getContext(), "Invalid TAB", Toast.LENGTH_SHORT).show();
                                else
                                    // editLocation(LocationText.getText().toString());
                                    dbHandler.updateInspectionLabel(Integer.toString(projId), Integer.toString(GlobalVariables.iId), LocationText.getText().toString());
                                OnTabChanged(0);

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


        final TextView projAddress = (TextView) view.findViewById(R.id.folder_name);
        projAddress.setText(ProjAddress);
        projAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Folder Title", projAddress.getText().toString());
            }
        });


  /*      ImageView Folder = (ImageView) view.findViewById(R.id.img_folder);
        Folder.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
                editProject("Folder Title", projAddress.getText().toString());
            }
        });

   */



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


            if (v == delete_Tab) {

                if(globalVariables.focus != "DOC"){
                    Toast.makeText(getContext(), "Select a document", Toast.LENGTH_SHORT).show();
                    return;
                }

            // setup the alert builder

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Choose an action");
            // add a list
            String[] actions = {"Delete the current Title",
            };
            builder.setItems(actions, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {

                        case 0: {

                            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                            View promptView = layoutInflater.inflate(R.layout.delete_location, null);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                            alertDialogBuilder.setView(promptView);
                            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                            locationText.setText("Warning - this will delete the Title and ALL the associated data");//location.getText().toString());

                            alertDialogBuilder.setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            deleteInspectionItem();
                                            OnTabChanged(0);

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

                        } //end of case 0
                    }

                }
            });
            // create and show the alert dialog
            AlertDialog dialog = builder.create();

            dialog.show();
         }




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



    @Override
    public void OnTabChanged(int treeNameIndex) {

    }


    @Override
    public void OnTabChanged_(int treeNameIndex) {

    }



    public void deleteInspectionItem() {

        DBHandler dbHandler = new DBHandler(getContext(), null, null, 1);


        int type = dbHandler.getMapNodeType(projId, GlobalVariables.aId);

        switch (type){

            case(-1):{
                Toast.makeText(getContext(), "Cannot delete Title with contents, delete contents first", Toast.LENGTH_SHORT).show();

                break;
            }

            case (0): {
                dbHandler.deleteMapBranch(projId, GlobalVariables.aId);
                dbHandler.deleteRec("MAP",projId,0,GlobalVariables.aId);


                GlobalVariables.pos = GlobalVariables.pos - 1;
                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                loadMap();
                break;
            }
            case (1):{

                dbHandler.deleteMapBranch(projId, GlobalVariables.aId);
                dbHandler.deleteRec("MAP",projId,0,GlobalVariables.aId);
                GlobalVariables.pos = GlobalVariables.pos - 1;
                Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                loadMap();
                break;
            }

        }

    }

    public void loadMap() {
/*
        DBHandler dbHandler = new DBHandler(getContext(), null, null, 1);
        ArrayList<HashMap<String, String>> SiteMapData = dbHandler.getFolderMap(projId, 0, 9); //child 9 shows only the types <9

        listItems = new ArrayList<>();
        ProjectData listItem;

        for (int i = 0; i < (SiteMapData.size()); i++) {

            listItem = new ProjectData(
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_PROJECT_ID)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_LEVEL)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_LEVEL)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_LEVEL)),
                    SiteMapData.get(i).get(MyConfig.TAG_LABEL),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_P_ID)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_INSPECTION_ID)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_PARENT)),
                    SiteMapData.get(i).get(MyConfig.TAG_IMAGE),
                    SiteMapData.get(i).get(MyConfig.TAG_NOTE)
            );
            listItems.add(listItem);
        }

        GlobalVariables.projectList = (ArrayList<ProjectData>) listItems;
        ProjectViewList.LoadDisplayList();
        // if (GlobalVariables.dataList.isEmpty() != true)
        GlobalVariables.modified = true;


        //    MapListAdapter mAdapter = new MapListAdapter(this);
        //   mAdapter.notifyDataSetChanged();

        OnTabChanged(GlobalVariables.pos);

 */
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

    public void editLocation(String branchLabel) {

        DBHandler dbHandler = new DBHandler(getContext(), null, null, 1);
        int success = dbHandler.updateMapLabel(projId, GlobalVariables.aId, branchLabel);
        if(success == 1) loadMap();
        else Toast.makeText(getContext(), "Create/select Item", Toast.LENGTH_SHORT).show();
    }

    public void editLabel(final String  item, String value){

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View promptView = layoutInflater.inflate(R.layout.add_location, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptView);
        final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
        itemTitle.setText("Document Information ");//Integer.parseInt(locationId)
        final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
        locationText.setText("Document Title");//Integer.parseInt(locationId)
        final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
        branchText.setHint(value);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        DBHandler dbHandler = new DBHandler(getContext(), null, null, 1);
                        switch (item) {

                            case "Label":{
                                Folder = branchText.getText().toString();
                                dbHandler.updateInspectionLabel(Integer.toString(projId), Integer.toString(GlobalVariables.iId), Folder);
                                //    globalVariables.OnProjectChanged(0);
                                //   globalVariables.updatePropList();
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
        folderNote = FolderNote.getText().toString();
        dbHandler.updateProject(Integer.toString(projId), "Folder Note" , folderNote, 0);
        dbHandler.updateInspectionNotes(Integer.toString(projId),Integer.toString(iId),SiteNotes.getText().toString(),FolderNote.getText().toString());
        dbHandler.statusChanged(projId,0);
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
