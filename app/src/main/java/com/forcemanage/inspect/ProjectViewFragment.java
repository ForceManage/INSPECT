package com.forcemanage.inspect;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.fragment.app.ListFragment;

import com.forcemanage.inspect.adapters.ProjectListAdapter;
import com.forcemanage.inspect.attributes.MapViewData;
import com.forcemanage.inspect.attributes.MapViewNode;
import com.forcemanage.inspect.attributes.ProjectData;
import com.forcemanage.inspect.attributes.ProjectNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ProjectViewFragment extends ListFragment {

    ProjectListAdapter mAdapter;

    public ProjectViewFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        GlobalVariables.projectList = ProjectViewList.LoadInitialData();

        GlobalVariables.projectnodes = ProjectViewList.LoadInitialNodes(GlobalVariables.projectList);


        ProjectViewList.LoadDisplayList();

        // Create an empty adapter we will use to display the loaded data.
        mAdapter = new ProjectListAdapter(getActivity());
        setListAdapter(mAdapter);

    }


    @Override
    public  void  onListItemClick(ListView l, final View v, int position, long id) {

        Boolean vary = false;
      //  GlobalVariables.pos = position;

        ProjectNode node = GlobalVariables.projectdisplayNodes.get(position);
     //   GlobalVariables.iId = node.getiID();
        if(position == 0) node.setIsExpanded(GlobalVariables.FALSE);
        int proj = node.getprojId();


        if(GlobalVariables.folder_Id != proj) {

            int expanded = GlobalVariables.projectdisplayNodes.size();


            DBHandler dbHandler = new DBHandler(getContext(), null, null, 1);
            ArrayList<HashMap<String, String>> Folders = dbHandler.getFolders(GlobalVariables.User_id);
            List<ProjectData> projectlistItems = new ArrayList<>();

            //   projectlistItems = new ArrayList<>();
            ProjectData listItem;

            for (int i = 0; i < (Folders.size()); i++) {

                listItem = new ProjectData(

                        Integer.parseInt(Folders.get(i).get(MyConfig.TAG_PROJECT_ID)),
                        Integer.parseInt(Folders.get(i).get(MyConfig.TAG_LEVEL)),
                        Integer.parseInt(Folders.get(i).get(MyConfig.TAG_LEVEL)),
                        Integer.parseInt(Folders.get(i).get(MyConfig.TAG_LEVEL)),
                        Folders.get(i).get(MyConfig.TAG_LABEL),
                        Integer.parseInt(Folders.get(i).get(MyConfig.TAG_P_ID)),
                        Integer.parseInt(Folders.get(i).get(MyConfig.TAG_INSPECTION_ID)),
                        Integer.parseInt(Folders.get(i).get(MyConfig.TAG_PARENT)),
                        Folders.get(i).get(MyConfig.TAG_IMAGE),
                        Folders.get(i).get(MyConfig.TAG_NOTE)

                );
                projectlistItems.add(listItem);
            }

            GlobalVariables.projectList = (ArrayList<ProjectData>) projectlistItems;

            GlobalVariables.projectList = ProjectViewList.LoadInitialData();

            GlobalVariables.projectnodes = ProjectViewList.LoadInitialNodes(GlobalVariables.projectList);

            ProjectViewList.LoadDisplayList();

            int foldersize = GlobalVariables.projectnodes.size();

            if (expanded > foldersize) {
                if (position > GlobalVariables.pos) {
                    GlobalVariables.pos = position - (expanded - foldersize);
                    vary = true;

                }
            }


        }

        if (!vary ) {
            GlobalVariables.pos = position;
        }



        if (position != ListView.INVALID_POSITION) {

            if (node.getIsExpanded() == GlobalVariables.TRUE) {
                node.setIsExpanded(GlobalVariables.FALSE);

            } else {
                if (node.getNodeChildren() != null)
                    node.setIsExpanded(GlobalVariables.TRUE);

            }

            node.setIsExpanded(GlobalVariables.TRUE);
        }




        OnProjectSelectionChangeListener listener = (OnProjectSelectionChangeListener) getActivity();
        listener.OnProjectChanged(GlobalVariables.pos);

        ProjectViewList.LoadDisplayList();

        l.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        l.setItemChecked(GlobalVariables.pos,true);

        mAdapter.notifyDataSetChanged();

        v.post(new Runnable() {
            @Override
            public void run() {
                v.setSelected(true);
            }
        });

        showMessage(Integer.toString(position));



    }


    private void showMessage(String message) {
        //Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

        Log.v("List position: ", message);
    }





}
