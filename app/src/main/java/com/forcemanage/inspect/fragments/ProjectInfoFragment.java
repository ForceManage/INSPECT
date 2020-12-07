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
import com.forcemanage.inspect.DetailFragment;
import com.forcemanage.inspect.DetailProjectFragment;
import com.forcemanage.inspect.GlobalVariables;
import com.forcemanage.inspect.InspectionActivity;
import com.forcemanage.inspect.MainActivity;
import com.forcemanage.inspect.MapViewLists;
import com.forcemanage.inspect.ProjectDetailFragment;
import com.forcemanage.inspect.ProjectViewFragment;
import com.forcemanage.inspect.ProjectViewList;
import com.forcemanage.inspect.attributes.MapViewData;
import com.forcemanage.inspect.MapViewFragment;
import com.forcemanage.inspect.MyConfig;
import com.forcemanage.inspect.R;
import com.forcemanage.inspect.attributes.MapViewNode;
import com.forcemanage.inspect.attributes.ProjectData;
import com.forcemanage.inspect.attributes.ProjectNode;
import com.forcemanage.inspect.tabchangelistener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProjectInfoFragment extends Fragment implements tabchangelistener, View.OnClickListener {

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
    private String FragDisplay;
    private List<ProjectData> listItems;
    private  int aId;
    private int Level;
    private int USER_ID = 0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            projId = bundle.getInt("projId");
            USER_ID = bundle.getInt("USER_ID");
            branchHead = bundle.getString("branchHead");
            //branchLabel = bundle.getString("branchLabel");
            //      branchNote = bundle.getString("notes");
   /*         ProjAddress = bundle.getString("address");
            note = bundle.getString("note");
            infoA = bundle.getString("infoA");
            infoB = bundle.getString("infoB");
            infoC = bundle.getString("infoC");
            infoD = bundle.getString("infoD");
            infoE = bundle.getString("infoE");
            infoF = bundle.getString("infoF");
            infoG = bundle.getString("infoG");
            infoH = bundle.getString("infoH");
            propPhoto = bundle.getString("propPhoto");


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

        View view = inflater.inflate(R.layout.project_info, container, false);


        Log.d(TAG, "oncreateview: started");


        branch = (TextView) view.findViewById((R.id.branchName));
        branch.setText(branchHead);
    //    bNote = (EditText) view.findViewById(R.id.note);
    //    bNote.setText(note);


        DBHandler dbHandler = new DBHandler(getContext(), null, null, 1);
        ArrayList<HashMap<String, String>> SiteMapData = dbHandler.getProjects(USER_ID,projId); //child 9 shows only the types <9

        listItems = new ArrayList<>();
        ProjectData listItem;

        for (int i = 0; i < (SiteMapData.size()); i++) {

            listItem = new ProjectData(
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_PROJECT_ID)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_LEVEL)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_LEVEL)),
                    Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_LEVEL)),
                    SiteMapData.get(i).get(MyConfig.TAG_LABEL)+" Documents",
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
            ProjectViewFragment treeFragment = new ProjectViewFragment();

            //  treeFragment.setArguments(getIntent().getExtras());


            getChildFragmentManager().beginTransaction().add(R.id.fragment_folder, treeFragment)
                    .commit();
        }


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

        Bundle bundle = new Bundle();

        bundle.putString("note", note);
        bundle.putString("infoA", infoA);
  /*      bundle.putString("infoB", projectItem.get(MyConfig.TAG_INFO_B));
        bundle.putString("infoC", projectItem.get(MyConfig.TAG_INFO_C));
        bundle.putString("infoD", projectItem.get(MyConfig.TAG_INFO_D));
        bundle.putString("photo", projectItem.get(MyConfig.TAG_PROJECT_PHOTO));
        bundle.putString("infoE", projectItem.get(MyConfig.TAG_INFO_E));
        bundle.putString("infoF", projectItem.get(MyConfig.TAG_INFO_F));
        bundle.putString("infoG", projectItem.get(MyConfig.TAG_INFO_G));
        bundle.putString("infoH", projectItem.get(MyConfig.TAG_INFO_H));
        bundle.putString("propPhoto", projectItem.get(MyConfig.TAG_PROJECT_PHOTO));

   */

     //   ProjectInfoFolderFragment fragment = new ProjectInfoFolderFragment();
     //   doFragmentTransaction(fragment, "ProjectInfoFolderFragment", true, "");
    //    fragment.setArguments(bundle);
/*
        final TextView projAddress = (TextView) view.findViewById(R.id.Text1);
        projAddress.setText(ProjAddress);
        projAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProject("Folder Title", projAddress.getText().toString());
            }
        });

        ImageView Folder = (ImageView) view.findViewById(R.id.img_folder);
        Folder.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
                editProject("Folder Title", projAddress.getText().toString());
            }
        });

 */
/*
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

 */

/*
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

 */
              //  galleryIntent.setDataAndType(data, "*/*");

                // galleryIntent.setDataAndType(Uri.withAppendedPath(Uri.fromFile(propImage) ,dir),"image/*" );
                // galleryIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                // galleryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        /*

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



         */


/*
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

 */
        return view;


    }

/*
    private void doFragmentTransaction(Fragment fragment, String name, boolean addToBackStack, String message) {
        androidx.fragment.app.FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        FragDisplay = name;
        transaction.replace(R.id.child_mainfragment_container, fragment, name);
        if (addToBackStack) {
            transaction.addToBackStack(name);
        }
        transaction.commit();
    }

 */


    @Override
    public void onClick(View v) {

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
                                                if(GlobalVariables.name == "NULL" || GlobalVariables.name == null)
                                                    Toast.makeText(getContext(), "Invalid TAB", Toast.LENGTH_SHORT).show();
                                                else
                                               // editLocation(LocationText.getText().toString());
                                                editLabel("Label",LocationText.getText().toString());
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



            if (v == delete_Tab) {


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

        if (v == page_Tab) {

            ProjectNode node = GlobalVariables.projectdisplayNodes.get(GlobalVariables.pos);
            projId = node.getprojId();
            iId = node.getiID();


            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
            alertDialogBuilder.setTitle("Log Session");
            alertDialogBuilder.setMessage("Record file session time?");
            alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    Intent theIntent = new Intent(getContext(), InspectionActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("PROJECT_ID", Integer.toString(projId));
                    bundle.putString("INSPECTION_ID", Integer.toString(iId));
                    bundle.putString("DOC_NAME", GlobalVariables.name);
                    bundle.putBoolean("logTime", true);
                    theIntent.putExtras(bundle);
                    startActivity(theIntent);
                    dialog.dismiss();

                }
            })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent theIntent = new Intent(getContext(), InspectionActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("PROJECT_ID", Integer.toString(projId));
                            bundle.putString("INSPECTION_ID", Integer.toString(iId));
                            bundle.putString("DOC_NAME", GlobalVariables.name);
                            bundle.putBoolean("logTime", false);
                            theIntent.putExtras(bundle);
                            startActivity(theIntent);
                            dialog.cancel();
                        }
                    });

            // create an alert dialog
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();


        }

    }





    @Override
    public void OnTabChanged(int treeNameIndex) {


     //  MapViewNode node = GlobalVariables.displayNodes.get(GlobalVariables.pos);

        DetailProjectFragment detailFragment = (DetailProjectFragment) getChildFragmentManager()
                .findFragmentById(R.id.detail_text);


        if (detailFragment != null) {
            // If description is available, we are in two pane layout
            // so we call the method in DescriptionFragment to update its content
            detailFragment.setDetail(treeNameIndex);

        } else {
            ProjectDetailFragment newDetailFragment = new ProjectDetailFragment();
            Bundle args = new Bundle();

          //  args.putInt(DetailFragment.KEY_POSITION, treeNameIndex);
            newDetailFragment.setArguments(args);


            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();


            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the backStack so the User can navigate back
            fragmentTransaction.replace(R.id.tree_fragment, newDetailFragment);

            //  fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }

        if (GlobalVariables.modified == true) {
            ProjectViewFragment newDetailFragment = new ProjectViewFragment();
            Bundle args = new Bundle();
      //      detailFragment.mCurrentPosition = -1;


       //     args.putInt(DetailFragment.KEY_POSITION, treeNameIndex);

            newDetailFragment.setArguments(args);
            androidx.fragment.app.FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the backStack so the User can navigate back
            fragmentTransaction.replace(R.id.tree_fragment, newDetailFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            FragmentManager fm = getChildFragmentManager();

            //fm.popBackStack(DF,0);
            if (getChildFragmentManager().getBackStackEntryCount() > 0) {
                getChildFragmentManager().popBackStackImmediate();
            }

            // fm.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);

            GlobalVariables.modified = false;

            OnTabChanged(GlobalVariables.pos);
        }

    }


    private void addLevel(int Level, String levelName) {

        DBHandler dbHandler = new DBHandler(getContext(), null, null, 1);
        int result = dbHandler.addLevel(projId, GlobalVariables.aId, 0, GlobalVariables.catId, Level, GlobalVariables.aId, levelName, 0);  //this is the ESM category
        if (result == 0)
            Toast.makeText(getContext(), "Cannot place TAB here", Toast.LENGTH_SHORT).show();
        else
        loadMap();
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
                    SiteMapData.get(i).get(MyConfig.TAG_LABEL)+" Documents",
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
