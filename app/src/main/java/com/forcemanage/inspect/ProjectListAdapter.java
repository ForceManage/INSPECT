package com.forcemanage.inspect;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ViewHolder>  {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter1;
    private List<Projectlistdata> jobList;
    private Context context;
    public int listposition;
    private projectchangelistener projectchangelistener;
    int row_index = -1;

    public ProjectListAdapter(List<Projectlistdata> projectList, projectchangelistener mjobchangelistener) {

        this.jobList = jobList;
        this.context = context;
        projectchangelistener = mjobchangelistener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.report_list_item, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return  vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.address.setText(jobList.get(position).getpropNo()+"  "+jobList.get(position).getaddress()+", "+jobList.get(position).getsuburb()+" ("+jobList.get(position).getpropId()+" )");
        holder.type.setText(jobList.get(position).gettype());


        if(position == listposition){
            holder.linearLayout.setBackgroundColor(Color.LTGRAY);
        }
        else
            holder.linearLayout.setBackgroundColor(Color.TRANSPARENT);

        if(jobList.get(position).getstatus().equals("n") ) holder.progress.setImageResource(R.drawable.ic_alpha_f_circle);
        if(jobList.get(position).getstatus().equals("p") ) holder.progress.setImageResource(R.drawable.ic_y_circle);
        if(jobList.get(position).getstatus().equals("c") ) holder.progress.setImageResource(R.drawable.ic_g_circle);
    }



    @Override
    public int getItemCount() {
        return jobList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView address;
        public TextView type;
        public TextView status;
        public ImageView progress;
        public LinearLayout linearLayout;


        public ViewHolder( View v) {
            super(v);

            address = (TextView) itemView.findViewById(R.id.textView7);
            type = (TextView) itemView.findViewById(R.id.textView11);
            linearLayout = (LinearLayout)v.findViewById(R.id.reportView);
            progress = (ImageView) v.findViewById(R.id._status);



            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   projectchangelistener.OnjobChanged(getAdapterPosition());
                   listposition = getAdapterPosition();
                   notifyDataSetChanged();


                }
            });

        }
    }
}
