package com.forcemanage.inspect;

/**
 * Created by cindyoakes on 9/23/16.
 */

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.fragment.app.ListFragment;

import com.forcemanage.inspect.adapters.MapListAdapter;
import com.forcemanage.inspect.attributes.MapViewData;
import com.forcemanage.inspect.attributes.MapViewNode;

import java.util.ArrayList;
import java.util.HashMap;


public class MapViewFragment extends ListFragment {

    MapListAdapter mAdapter;

    public MapViewFragment() {
    }

    @Override
     public void onActivityCreated(Bundle savedInstanceState){
         super.onActivityCreated(savedInstanceState);

        GlobalVariables.dataList = MapViewLists.LoadInitialData();

        GlobalVariables.nodes = MapViewLists.LoadInitialNodes(GlobalVariables.dataList);

        MapViewLists.LoadDisplayList();

        // Create an empty adapter we will use to display the loaded data.
        mAdapter = new MapListAdapter(getActivity());
        setListAdapter(mAdapter);

        }



    @Override
    public  void  onListItemClick(ListView l, final View v, final int position, long id) {

       GlobalVariables.pos = position;
        MapViewNode node = GlobalVariables.displayNodes.get(position);

        int proj = node.getprojId();

        if(GlobalVariables.folder_Id != proj){

            if (position != ListView.INVALID_POSITION) {

                if (node.getIsExpanded() == GlobalVariables.TRUE) {
                    node.setIsExpanded(GlobalVariables.FALSE);

                } else {
                    if (node.getNodeChildren() != null)
                        node.setIsExpanded(GlobalVariables.TRUE);

                }
            }

            tabchangelistener listener = (tabchangelistener) getActivity();
            listener.OnTabChanged(position);
            MapViewLists.LoadDisplayList();
            mAdapter.notifyDataSetChanged();

        }

        if (position != ListView.INVALID_POSITION) {

                if (node.getIsExpanded() == GlobalVariables.TRUE) {
                    node.setIsExpanded(GlobalVariables.FALSE);

                } else {
                    if (node.getNodeChildren() != null)
                        node.setIsExpanded(GlobalVariables.TRUE);

                }
            }

     //   mAdapter.notifyDataSetChanged();

            tabchangelistener listener = (tabchangelistener) getActivity();
            listener.OnTabChanged(position);

            MapViewLists.LoadDisplayList();
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
