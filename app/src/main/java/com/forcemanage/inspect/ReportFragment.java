package com.forcemanage.inspect;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReportFragment extends Fragment {

    private InspectionActivity globalVariables;

    private static final String TAG = "Report Fragment";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private int projId = 1;
    private int iId = 1;
    private List<ReportItem> listItems;

    private ListView listView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    globalVariables.setContentView(R.layout.listitem);

     //   getActivity().setContentView(R.id.list);
        listView = (ListView) globalVariables.findViewById(R.id.list);

      }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        globalVariables = (InspectionActivity) getActivity();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    //


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    //
   //     setListAdapter(adapter);

   //     globalVariables.setContentView(R.layout.listitem);

        View view = inflater.inflate(R.layout.listitem, container, false);

         listItems = globalVariables.reportlistItems;

        listView = (ListView) view.findViewById(R.id.list);

       ReportAdapter adapter = new ReportAdapter(getActivity(), 0, globalVariables.reportlistItems);


        listView.setAdapter(adapter);

         // adapter = new ReportAdapter(reportlistItems, this);
        //  recyclerView.setAdapter(adapter);
        //setListAdapter();

       // ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource()


    //   View header = getLayoutInflater().inflate(R.layout.report_header, null);

     //   listView.addHeaderView(header);
        return view;
    }


}
