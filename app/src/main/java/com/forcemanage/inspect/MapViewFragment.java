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
import java.util.List;


public class MapViewFragment extends ListFragment {

    MapListAdapter mAdapter;

    public MapViewFragment() {
    }

    @Override
     public void onActivityCreated(Bundle savedInstanceState){
         super.onActivityCreated(savedInstanceState);

       GlobalVariables.dataList =  MapViewLists.LoadInitialData();

        GlobalVariables.nodes = MapViewLists.LoadInitialNodes(GlobalVariables.dataList);

        MapViewLists.LoadDisplayList();

        // Create an empty adapter we will use to display the loaded data.
        mAdapter = new MapListAdapter(getActivity());
        setListAdapter(mAdapter);

        }



    @Override
    public  void  onListItemClick(ListView l, final View v, int position, long id) {

        Boolean vary = false;

        MapViewNode node = GlobalVariables.displayNodes.get(position);

        if(position == 0) node.setIsExpanded(GlobalVariables.FALSE);
        int proj = node.getprojId();


        if(GlobalVariables.folder_Id != proj) {

            int expanded = GlobalVariables.displayNodes.size();


            DBHandler dbHandler = new DBHandler(getContext(), null, null, 1);
            ArrayList<HashMap<String, String>> Folders = dbHandler.getFolders(GlobalVariables.User_id);
            List<MapViewData> maplistItems = new ArrayList<>();

            //   projectlistItems = new ArrayList<>();
            MapViewData listItem;

            for (int i = 0; i < (Folders.size()); i++) {

                listItem = new MapViewData(

                        Integer.parseInt(Folders.get(i).get(MyConfig.TAG_PROJECT_ID)),
                        Integer.parseInt(Folders.get(i).get(MyConfig.TAG_LEVEL)),
                        Integer.parseInt(Folders.get(i).get(MyConfig.TAG_CAT_ID)),
                        Integer.parseInt(Folders.get(i).get(MyConfig.TAG_CHILD)),
                        Folders.get(i).get(MyConfig.TAG_LABEL),
                        Integer.parseInt(Folders.get(i).get(MyConfig.TAG_A_ID)),
                        Integer.parseInt(Folders.get(i).get(MyConfig.TAG_INSPECTION_ID)),
                        Integer.parseInt(Folders.get(i).get(MyConfig.TAG_PARENT)),
                        Folders.get(i).get(MyConfig.TAG_IMAGE1),
                        Folders.get(i).get(MyConfig.TAG_NOTES)

                );
                maplistItems.add(listItem);
            }

            GlobalVariables.dataList = (ArrayList<MapViewData>) maplistItems;

            GlobalVariables.dataList = MapViewLists.LoadInitialData();

            GlobalVariables.nodes = MapViewLists.LoadInitialNodes(GlobalVariables.dataList);

            MapViewLists.LoadDisplayList();

            int foldersize = GlobalVariables.nodes.size();

            if (expanded > foldersize) {
                if (position > GlobalVariables.pos) {
                    GlobalVariables.pos = position - (expanded - foldersize);
                    vary = true;

                }
            }


        }

            if (!vary) {
                GlobalVariables.pos = position;
            }


            if (GlobalVariables.pos != ListView.INVALID_POSITION) {

                if (node.getIsExpanded() == GlobalVariables.TRUE) {
                    node.setIsExpanded(GlobalVariables.FALSE);

                } else {
                    if (node.getNodeChildren() != null)
                        node.setIsExpanded(GlobalVariables.TRUE);

                }
                node.setIsExpanded(GlobalVariables.TRUE);
            }



            OnDocChangeListener listener = (OnDocChangeListener) getActivity();
            listener.OnTabChanged(GlobalVariables.pos);


            MapViewLists.LoadDisplayList();





      //  final ListView listView = getListView();
        l.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        l.setItemChecked(GlobalVariables.pos,true);

        mAdapter.notifyDataSetChanged();
     //   Toast.makeText(getActivity(), Integer.toString(l.getCheckedItemPosition()), Toast.LENGTH_LONG).show();


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
