package com.forcemanage.inspect.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.util.ArrayList;
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



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            projId = bundle.getInt("projId");
            USER_ID = bundle.getInt("USER_ID");
            iId = bundle.getInt("iID");
            doc = bundle.getBoolean("doc");



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

        DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);

      //  ArrayList<HashMap<String, String>> Folders = dbHandler.getFolderMap(USER_ID, projId);
        maplistItems = new ArrayList<>();

        //   projectlistItems = new ArrayList<>();
      //  MapViewData listItem;




        ArrayList<HashMap<String, String>> SiteMapData = dbHandler.getMap(projId, iId, 15); //child < 15 includes all types

        listItems = new ArrayList<>();
        MapViewData listItem;

        for (int i = 0; i < (SiteMapData.size()); i++) {

            if(i == 0 && doc == true)
                listItem = new MapViewData(

                        Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_PROJECT_ID)),
                        Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_LEVEL)),
                        Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_CAT_ID)),
                        Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_CHILD)),
                        GlobalVariables.name,
                        Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_A_ID)),
                        Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_INSPECTION_ID)),
                        Integer.parseInt(SiteMapData.get(i).get(MyConfig.TAG_PARENT)),
                        SiteMapData.get(i).get(MyConfig.TAG_IMAGE1),
                        SiteMapData.get(i).get(MyConfig.TAG_NOTES)
                );
            else

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

        GlobalVariables.dataList = (ArrayList<MapViewData>)  maplistItems;
        //   MapViewLists.LoadDisplayList();

        GlobalVariables.modified = false;


        if (view.findViewById(R.id.mainfragment_container_1) != null) {

            // However if we are being restored from a previous state, then we don't
            // need to do anything and should return or we could end up with overlapping Fragments
            FolderTreeFragment folderTreeFragment = new FolderTreeFragment();
            getChildFragmentManager().beginTransaction().add(R.id.mainfragment_container_1, folderTreeFragment)
                    .commit();
            // Create an Instance of Fragment

        }


        GlobalVariables.dataList = (ArrayList<MapViewData>) maplistItems;
        //     TreeViewLists.LoadDisplayList();
        MapViewLists.LoadDisplayList();
        // if (GlobalVariables.dataList.isEmpty() != true)
        GlobalVariables.modified = true;

        if(doc == false) view.findViewById(R.id.folder_info).setVisibility(View.VISIBLE);
           else view.findViewById(R.id.document_info).setVisibility(View.VISIBLE);

         return view;

    }

    @Override
    public void onClick(View v) {


    }

    @Override
    public void OnTabChanged(int treeNameIndex) {

    }



    }
