package com.forcemanage.inspect.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.forcemanage.inspect.R;
import com.forcemanage.inspect.attributes.ProjectData;

import java.util.ArrayList;

public class ReportDocsAdapter extends RecyclerView.Adapter<ReportDocsAdapter.ViewHolder> {

    public ArrayList<ProjectData> mProjectData;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.address);
        }
    }

    public ReportDocsAdapter(ArrayList<ProjectData> projectList) {
         mProjectData = projectList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        ViewHolder pdh = new ViewHolder(v);
        return pdh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ProjectData currentData = mProjectData.get(position);
            holder.mTextView.setText(currentData.getLabel());
    }

    @Override
    public int getItemCount() {
        return mProjectData.size();
    }
}
