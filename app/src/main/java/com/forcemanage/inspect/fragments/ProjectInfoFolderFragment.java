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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.forcemanage.inspect.BuildConfig;
import com.forcemanage.inspect.DBHandler;
import com.forcemanage.inspect.DetailProjectFragment;
import com.forcemanage.inspect.GlobalVariables;
import com.forcemanage.inspect.InspectionActivity;
import com.forcemanage.inspect.MainActivity;
import com.forcemanage.inspect.MyConfig;
import com.forcemanage.inspect.ProjectDetailFragment;
import com.forcemanage.inspect.ProjectViewFragment;
import com.forcemanage.inspect.ProjectViewList;
import com.forcemanage.inspect.R;
import com.forcemanage.inspect.attributes.ProjectData;
import com.forcemanage.inspect.attributes.ProjectNode;
import com.forcemanage.inspect.tabchangelistener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProjectInfoFolderFragment extends Fragment implements  View.OnClickListener {

    private MainActivity globalVariables;

    private static final String TAG = "Project Info Fragment";


    private TextView branch;
    private EditText bNote;
    private ImageView mPhotoImageView;
    private ImageView photo_cam;
    private ImageView info_file;
    private ImageView photo_file;
    private ImageView add_Tab;
    private ImageView edit_Tab;
    private ImageView delete_Tab;
    private ImageView page_Tab;
    private String branchHead = "";
    private String branchLabel = "";
    private String branchNote = "";
    private String ProjAddress = "";
    private Boolean Edited;
    private String note;
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
    private String propPhoto;
    private  int aId;
    private int Level;
    private int USER_ID = 0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {

            note = bundle.getString("note");
            infoA = bundle.getString("infoA");
            propPhoto = bundle.getString("propPhoto");
            /*
            infoB = bundle.getString("infoB");
            infoC = bundle.getString("infoC");
            infoD = bundle.getString("infoD");
            infoE = bundle.getString("infoE");
            infoF = bundle.getString("infoF");
            infoG = bundle.getString("infoG");
            infoH = bundle.getString("infoH");



             */
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

        View view = inflater.inflate(R.layout.project_info_folder, container, false);


        Log.d(TAG, "oncreateview: started");


   //     branch = (TextView) view.findViewById((R.id.branchName));
   //     branch.setText(branchHead);
        bNote = (EditText) view.findViewById(R.id.note);
        bNote.setText(note);

/*
        DBHandler dbHandler = new DBHandler(getContext(), null, null, 1);
        ArrayList<HashMap<String, String>> SiteMapData = dbHandler.getProjects(USER_ID,projId); //child 9 shows only the types <9




        projectId = Integer.toString(projId);
        add_Tab = (ImageView) view.findViewById(R.id.add_Tab);
        add_Tab.setOnClickListener(this);
        edit_Tab = (ImageView) view.findViewById(R.id.edit_Tab);
        edit_Tab.setOnClickListener(this);
        delete_Tab = (ImageView) view.findViewById(R.id.del_Tab);
        delete_Tab.setOnClickListener(this);
        page_Tab = (ImageView) view.findViewById(R.id.page_Tab);
        page_Tab.setOnClickListener(this);

        final TextView TVprojectID = (TextView) view.findViewById(R.id.branchTitle);
        TVprojectID.setText("Folder ID:  " + branchHead);
        TVprojectID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Folder ID", TVprojectID.getText().toString());
            }
        });

        final TextView projAddress = (TextView) view.findViewById(R.id.Text1);
        projAddress.setText(ProjAddress);
        projAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Folder Title", projAddress.getText().toString());
            }
        });
/*
        ImageView Folder = (ImageView) view.findViewById(R.id.img_folder);
        Folder.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
                editProject("Folder Title", projAddress.getText().toString());
            }
        });

 */

        final TextView TVinfoA = (TextView) view.findViewById(R.id.Text2);
        TVinfoA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Note A", TVinfoA.getText().toString());
            }
        });

        final TextView TVinfoB = (TextView) view.findViewById(R.id.Text3);
        TVinfoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Note B", TVinfoB.getText().toString());
            }
        });

        final TextView TVinfoC = (TextView) view.findViewById(R.id.Text4);
        TVinfoC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Note C", TVinfoC.getText().toString());
            }
        });

        final TextView TVinfoD = (TextView) view.findViewById(R.id.Text5);
        TVinfoD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Note D", TVinfoD.getText().toString());
            }
        });
        final TextView TVinfoE = (TextView) view.findViewById(R.id.Text6);
        TVinfoE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Note E", TVinfoE.getText().toString());
            }
        });

        final TextView TVinfoF = (TextView) view.findViewById(R.id.Text7);
        TVinfoF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Note F", TVinfoF.getText().toString());
            }
        });

        final TextView TVinfoG = (TextView) view.findViewById(R.id.Text8);
        TVinfoG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Note G", TVinfoG.getText().toString());
            }
        });

        final TextView TVinfoH = (TextView) view.findViewById(R.id.Text9);
        TVinfoH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Note H", TVinfoH.getText().toString());
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


        photo_file = (ImageView) view.findViewById(R.id.imageView_file);

        photo_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                globalVariables.mPhotoImageView = mPhotoImageView;
                Intent galleryIntent = new Intent();

                galleryIntent.setAction(Intent.ACTION_PICK);
                galleryIntent.setType("image/*");

                globalVariables.startActivityForResult(galleryIntent.createChooser(galleryIntent, "Select Picture"), 1);
            }


        });


        if (infoA == "null") TVinfoA.setText("Note A");
        else TVinfoA.setText(infoA);
        if (infoB == "null") TVinfoB.setText("Note B");
        else TVinfoB.setText(infoB);
        if (infoC == "null") TVinfoC.setText("Note C");
        else TVinfoC.setText(infoC);
        if (infoD == "null") TVinfoD.setText("Note D");
        else TVinfoD.setText(infoD);
        if (infoE == "null") TVinfoE.setText("Note E");
        else TVinfoE.setText(infoE);
        if (infoF == "null") TVinfoF.setText("Note F");
        else TVinfoF.setText(infoF);
        if (infoG == "null") TVinfoG.setText("Note G");
        else TVinfoG.setText(infoG);
        if (infoH == "null") TVinfoH.setText("Note H");
        else TVinfoH.setText(infoH);


        if (propPhoto == null)
            propPhoto = "";

        if (propPhoto.length() > 12) {
            String dirName = propPhoto.substring(6, 14);
            String root = Environment.getExternalStorageDirectory().toString();
            File Image = new File(root + "/A2D_" + dirName + "/" + propPhoto);
            Bitmap myBitmap = BitmapFactory.decodeFile(Image.getAbsolutePath());
            mPhotoImageView.setImageBitmap(myBitmap);
        }
        bNote.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Edited = true;
            }

        });

        return view;
    }


    @Override
    public void onClick(View v) {
/*
        if (v == add_Tab) {

            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            View promptView = layoutInflater.inflate(R.layout.add_location, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
            alertDialogBuilder.setView(promptView);
            final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
            itemTitle.setText("Add New Document to the Folder ");//Integer.parseInt(locationId)
            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
            locationText.setText("Document Name : ");//Integer.parseInt(locationId)
            final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
            branchText.setHint("Document Title");
            alertDialogBuilder.setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (USER_ID > 0) {
                                globalVariables.requestActivity(branchText.getText().toString());
                                //    downloadprojects();
                            } else
                                Toast.makeText(getContext(), "Log in required ", Toast.LENGTH_LONG).show();


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



        if (v == edit_Tab) {

            final DBHandler dbHandler = new DBHandler(getContext(), null, null, 1);

            final String branchTitle = dbHandler.getMapBranchTitle(projId, GlobalVariables.catId); //get Branch head


            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            View promptView = layoutInflater.inflate(R.layout.add_location, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
            alertDialogBuilder.setView(promptView);
            final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
            itemTitle.setText("Item Title: " + branchTitle);//Integer.parseInt(locationId)
            final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
            locationText.setText("Sub-Title to : " + GlobalVariables.name);//Integer.parseInt(locationId)
            final EditText LocationText = (EditText) promptView.findViewById(R.id.locationtext);
            LocationText.setText(GlobalVariables.name);
            // setup a dialog window
            alertDialogBuilder.setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (GlobalVariables.name == "NULL" || GlobalVariables.name == null)
                                Toast.makeText(getContext(), "Invalid TAB", Toast.LENGTH_SHORT).show();
                            else
                                // editLocation(LocationText.getText().toString());
                                editLabel("Label", LocationText.getText().toString());
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
*/
    }




    public void editProject(final String  item, String value){

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View promptView = layoutInflater.inflate(R.layout.add_location, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptView);
        final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
        itemTitle.setText("Project Information ");//Integer.parseInt(locationId)
        final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
        locationText.setText(item);//Integer.parseInt(locationId)
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
                                    }
                                    //        globalVariables.OnSelectionChanged(0);
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
        if(success == 1) ;
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
                                branchLabel = branchText.getText().toString();
                                dbHandler.updateInspection(Integer.toString(projId), Integer.toString(iId), branchLabel, note, "note_2");
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
        note = bNote.getText().toString();
        dbHandler.updateProject(Integer.toString(projId), "Folder Note" , note, 0);
        dbHandler.statusChanged(projId,0);
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
