package com.forcemanage.inspect.adapters;

/**
 * Created by cindyoakes on 9/23/16.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.forcemanage.inspect.GlobalVariables;
import com.forcemanage.inspect.InspectionActivity;
import com.forcemanage.inspect.MainActivity;
import com.forcemanage.inspect.ProjectListViewHolder;
import com.forcemanage.inspect.R;
import com.forcemanage.inspect.attributes.MapViewNode;
import com.forcemanage.inspect.attributes.ProjectNode;


public class ProjectListAdapter extends ArrayAdapter<ProjectNode>
{

    private final LayoutInflater mInflater;
    private Context context;


    public ProjectListAdapter(Context context) {    //,int resource, List<TreeViewNode> items
        super(context, android.R.layout.simple_gallery_item);

        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {return GlobalVariables.projectdisplayNodes.size();
    }

    @Override
    public ProjectNode getItem(int position) {return GlobalVariables.projectdisplayNodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Populate new items in the list.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ProjectListViewHolder holder = null;

        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.doc_item, parent, false);

            holder = new ProjectListViewHolder();
            holder.content = (TextView) convertView.findViewById(R.id.text);
            holder.arrow = (ImageView) convertView.findViewById(R.id.arrow);
            holder.file_open = (ImageView) convertView.findViewById(R.id.file);
            holder.print = (ImageView) convertView.findViewById(R.id.print);
            holder.position = position;



            ((ImageView) convertView.findViewById(R.id.arrow)).setOnClickListener(mArrowClickListener);
            ((ImageView) convertView.findViewById(R.id.file)).setOnClickListener(mFileClickListener);
            ((ImageView) convertView.findViewById(R.id.print)).setOnClickListener(mPrintClickListener);

            convertView.setTag(holder);

        } else
        {
            holder = (ProjectListViewHolder) convertView.getTag();
        }


        ProjectNode node = GlobalVariables.projectdisplayNodes.get(position);
     //   GlobalVariables.doc_pos = position;
        holder.content.setText(node.getNodeName());



        int base_node = node.getNodeLevel();

        switch (node.getNodeLevel()){

            case 0:{

               holder.content.setTextColor(Color.DKGRAY);
                //  holder.content.setTextSize(17);
                if(base_node == 0) holder.content.setTextAppearance(android.R.style.TextAppearance_Material_Title);
                if(base_node > 0) holder.content.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
                  holder.arrow.setImageResource(R.drawable.folder2_red);
                  holder.print.setImageResource(R.drawable.ic_more_vert);
                  holder.file_open.setVisibility(View.GONE);

                break;
            }
            case 1: {
                holder.content.setTextColor(Color.GRAY);
                //    holder.content.setTextSize(17);
                holder.content.setTextAppearance(android.R.style.TextAppearance_Material_Medium);

                //   holder.arrow.setImageResource(R.drawable.ic_folder_24);
                   holder.arrow.setVisibility(View.GONE);
                   holder.file_open.setImageResource(R.drawable.ic_book);
                   holder.print.setImageResource(R.drawable.ic_print_24px);
                   break;
            }
            case 2: {
                holder.content.setTextColor(Color.GRAY);
                //     holder.content.setTextSize(17);
                holder.content.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
                if (node.getNodeLevel() == 1)
                    holder.arrow.setImageResource(R.drawable.ic_file_tree);
                else
                    holder.arrow.setImageResource(R.drawable.ic_filetree2);
                break;
            }
            case 9: {
                //      holder.content.setTextSize(17);
                holder.content.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
                holder.arrow.setImageResource(R.drawable.ic_clipboard_text);
                holder.content.setTextColor(Color.BLUE);
                break;
            }
            case 10: {
                //      holder.content.setTextSize(17);
                holder.content.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
                holder.arrow.setImageResource(R.drawable.ic_book_check_outline);
                holder.content.setTextColor(Color.BLUE);
                break;
            }

            case 11: {
                //      holder.content.setTextSize(17);
                holder.content.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
                holder.arrow.setImageResource(R.drawable.ic_action_name);
                holder.content.setTextColor(Color.BLUE);
                break;
            }
        }


        holder.arrow.setTag(position);
        holder.content.setTag(position);
        holder.file_open.setTag(position);
        holder.print.setTag(position);

        int lvl = node.getNodeLevel();
        int newWidth = (lvl * 20) ;
        ((TextView) convertView.findViewById(R.id.spacer)).getLayoutParams().width = newWidth;
        ((TextView) convertView.findViewById(R.id.spacer)).requestLayout();

        showMessage("TreelistAdapter pos:  "+position);

        return convertView;
    }

    public OnClickListener mArrowClickListener, mFileClickListener, mPrintClickListener;


    {


        mFileClickListener = new OnClickListener() {
            //   @Override
            public void onClick(View v) {

                int position = (int) v.getTag();

                ProjectNode node = GlobalVariables.projectdisplayNodes.get(position);

                final int projId = node.getprojId();
                final int iId = node.getiID();
                GlobalVariables.iId = iId;

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
                        getContext().startActivity(theIntent);
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
                                getContext().startActivity(theIntent);
                                dialog.cancel();
                            }
                        });

                // create an alert dialog
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();


                // showMessage("Adapter listener "+node.getNodeChildren().toString());
            }
        };

            mPrintClickListener = new OnClickListener() {
                //   @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    final ProjectNode node = GlobalVariables.projectdisplayNodes.get(position);
                    if(position == 0){
                        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                        View promptView = layoutInflater.inflate(R.layout.add_location, null);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                        alertDialogBuilder.setView(promptView);
                        final TextView itemTitle = (TextView) promptView.findViewById(R.id.textItem);
                        itemTitle.setText("Create new File ");//Integer.parseInt(locationId)
                        final TextView locationText = (TextView) promptView.findViewById(R.id.textView);
                        locationText.setText("File Name : ");//Integer.parseInt(locationId)
                        final EditText branchText = (EditText) promptView.findViewById(R.id.locationtext);
                        branchText.setHint("Title of File");
                        alertDialogBuilder.setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        if (GlobalVariables.User_id > 0) {
                                            ((MainActivity)getContext()).requestActivity(branchText.getText().toString());
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
                    else {


                        GlobalVariables.iId = node.getiID();
                        ((MainActivity) getContext()).reportMenu();
                        // showMessage("Adapter listener "+node.getNodeChildren().toString());
                    }
                }
        };

    }

    public void reset() {
        //Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();


        ProjectNode node = GlobalVariables.projectdisplayNodes.get(0);

        if (node.getIsExpanded() == GlobalVariables.TRUE) {
            node.setIsExpanded(GlobalVariables.FALSE);

        } else {
            if (node.getNodeChildren() != null)
                node.setIsExpanded(GlobalVariables.TRUE);

        }

        //   TreeViewLists.LoadDisplayList();
        notifyDataSetChanged();

        //

    }




    private void showMessage(String message) {
        //Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();


        Log.v("show message", message);
    }
}
