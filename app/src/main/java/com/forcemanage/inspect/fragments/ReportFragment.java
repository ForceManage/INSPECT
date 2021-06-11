package com.forcemanage.inspect.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.forcemanage.inspect.InspectionActivity;
import com.forcemanage.inspect.MainActivity;
import com.forcemanage.inspect.R;
import com.forcemanage.inspect.adapters.ReportAdapter;
import com.forcemanage.inspect.attributes.ReportItem;

import java.util.List;

public class ReportFragment extends Fragment {

    private MainActivity globalVariables;


    private static final String TAG = "Report Fragment";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private int projId = 1;
    private int iId = 1;
    private List<ReportItem> listItems;
    private Button exit;

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

       globalVariables = (MainActivity) getActivity();
    }
/*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    //


    }
*/

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        final Bundle bundle = this.getArguments();
        if(bundle != null) {
            bundle.putString("branchHead", bundle.getString("branchHead"));
            bundle.putString("branchLabel", bundle.getString("branchLabel"));
            bundle.putString("projectId", bundle.getString("projectId"));
            bundle.putString("inspectionId", bundle.getString("inspectionId"));
            bundle.putString("date", bundle.getString("date"));
            bundle.putString("startTime", bundle.getString("startTime"));
            bundle.putString("endTime", bundle.getString("endTime"));
            bundle.putString("note", bundle.getString("note"));
            bundle.putString("inpectType", bundle.getString("inspectType"));
            bundle.putString("auditor", bundle.getString("auditor"));
        }





        //
   //     setListAdapter(adapter);

   //     globalVariables.setContentView(R.layout.listitem);

        final View view = inflater.inflate(R.layout.listitem, container, false);

        listItems = globalVariables.reportlistItems;

        listView = (ListView) view.findViewById(R.id.list);


       ReportAdapter adapter = new ReportAdapter(getActivity(), 0, globalVariables.reportlistItems);


        listView.setAdapter(adapter);


        return view;


    }



}
