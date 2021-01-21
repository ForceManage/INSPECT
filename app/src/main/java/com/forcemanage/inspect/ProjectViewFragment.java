package com.forcemanage.inspect;

/**
 * Created by cindyoakes on 9/23/16.
 */

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.forcemanage.inspect.adapters.MapListAdapter;
import com.forcemanage.inspect.adapters.ProjectListAdapter;
import com.forcemanage.inspect.attributes.MapViewNode;
import com.forcemanage.inspect.attributes.ProjectNode;


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
    public  void  onListItemClick(ListView l, final View v, final int position, long id) {

        GlobalVariables.doc_pos = position;
        ProjectNode node = GlobalVariables.projectdisplayNodes.get(position);
        GlobalVariables.iId = node.getiID();

        if (position != ListView.INVALID_POSITION) {

            if (node.getIsExpanded() == GlobalVariables.TRUE) {
                node.setIsExpanded(GlobalVariables.FALSE);

            } else {
                if (node.getNodeChildren() != null)
                    node.setIsExpanded(GlobalVariables.TRUE);

            }


            node.setIsExpanded(GlobalVariables.TRUE);
        }

        l.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        l.setItemChecked(GlobalVariables.doc_pos,true);

        OnVerseNameSelectionChangeListener listener = (OnVerseNameSelectionChangeListener) getActivity();
        listener.OnProjectChanged(position);

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
