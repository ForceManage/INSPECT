package com.forcemanage.inspect.adapters;

/**
 * Created by cindyoakes on 9/23/16.
 */

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.forcemanage.inspect.GlobalVariables;
import com.forcemanage.inspect.MapListViewHolder;
import com.forcemanage.inspect.MapViewLists;
import com.forcemanage.inspect.attributes.MapViewNode;
import com.forcemanage.inspect.R;


public class MapListAdapter extends ArrayAdapter<MapViewNode>
{

    private final LayoutInflater mInflater;
    private Context context;


    public MapListAdapter(Context context) {    //,int resource, List<TreeViewNode> items
        super(context, android.R.layout.simple_gallery_item);

        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {return GlobalVariables.displayNodes.size();
    }

    @Override
    public MapViewNode getItem(int position) {return GlobalVariables.displayNodes.get(position);
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

        MapListViewHolder holder = null;

        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.single_item, parent, false);

            holder = new MapListViewHolder();
            holder.content = (TextView) convertView.findViewById(R.id.text);
            holder.arrow = (ImageView) convertView.findViewById(R.id.arrow);
            holder.position = position;


            ((ImageView) convertView.findViewById(R.id.arrow)).setOnClickListener(mArrowClickListener);

            convertView.setTag(holder);

        } else
        {
            holder = (MapListViewHolder) convertView.getTag();
        }


        MapViewNode node = GlobalVariables.displayNodes.get(position);
        holder.content.setText(node.getNodeName());



        int base_node = node.getNodeLevel();

        switch (node.getbranchCat()){

            case 0:{

                holder.content.setTextColor(Color.DKGRAY);
              //  holder.content.setTextSize(17);
                if(base_node == 0) holder.content.setTextAppearance(android.R.style.TextAppearance_Material_Title);
                if(base_node > 0) holder.content.setTextAppearance(android.R.style.TextAppearance_Material_Medium);

                if (node.getNodeLevel() == 0)
                   holder.arrow.setImageResource(R.drawable.ic_file_tree);
                else
                    holder.arrow.setImageResource(R.drawable.ic_filetree2);



                break;
           }
            case 1: {
                holder.content.setTextColor(Color.GRAY);
            //    holder.content.setTextSize(17);
                holder.content.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
                holder.arrow.setImageResource(R.drawable.ic_edit_outline);
                break;
            }
            case 2: {
                holder.content.setTextColor(Color.GRAY);
           //     holder.content.setTextSize(17);
                holder.content.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
                holder.arrow.setImageResource(R.drawable.ic_note_text);
                break;
            }
            case 9: {
                //      holder.content.setTextSize(17);
                holder.content.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
                holder.arrow.setImageResource(R.drawable.ic_comment);
                holder.content.setTextColor(Color.BLUE);
                break;
            }
            case 10: {
          //      holder.content.setTextSize(17);
                holder.content.setTextAppearance(android.R.style.TextAppearance_Material_Medium);
                holder.arrow.setImageResource(R.drawable.ic_certificate);
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


        int lvl = node.getNodeLevel();
        int newWidth = (lvl * 25) + 1;
        ((TextView) convertView.findViewById(R.id.spacer)).getLayoutParams().width = newWidth;
        ((TextView) convertView.findViewById(R.id.spacer)).requestLayout();

        showMessage("TreelistAdapter pos:  "+position);

        return convertView;
    }

    public OnClickListener mArrowClickListener;

    {
        mArrowClickListener = new OnClickListener() {
            //   @Override
            public void onClick(View v) {

                int position = (int) v.getTag();
              //  int position = GlobalVariables.pos;

                if (position != ListView.INVALID_POSITION) {
                    MapViewNode node = GlobalVariables.displayNodes.get(position);

                    if (node.getIsExpanded() == GlobalVariables.TRUE) {
                        node.setIsExpanded(GlobalVariables.FALSE);

                    } else {
                        if (node.getNodeChildren() != null)
                            node.setIsExpanded(GlobalVariables.TRUE);

                    }


                  MapViewLists.LoadDisplayList();

                   notifyDataSetChanged();


               }

                // showMessage("Adapter listener "+node.getNodeChildren().toString());
            }
        };
    }

    public void reset() {
        //Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();


        MapViewNode node = GlobalVariables.displayNodes.get(0);

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


