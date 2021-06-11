package com.forcemanage.inspect;

/**
 * Created by cindyoakes on 9/23/16.
 */
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.forcemanage.inspect.attributes.MapViewNode;


public class DetailFragment extends Fragment
{


    final static String KEY_POSITION = "position";
    int mCurrentPosition = -1;
    int id;
    int aID;
    int cat;


    String Name;

    TextView mDetailTextView;

    public DetailFragment() {  }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // If the Activity is recreated, the savedInstanceStare Bundle isn't empty
        // we restore the previous version name selection set by the Bundle.
        // This is necessary when in two pane layout

        if (savedInstanceState != null){
            mCurrentPosition = GlobalVariables.pos;  //savedInstanceState.getInt(KEY_POSITION);
            id=mCurrentPosition;
        }


        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        mDetailTextView = (TextView) view.findViewById(R.id.detail_text);


       return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // During the startup, we check if there are any arguments passed to the fragment.
        // onStart() is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method below
        // that sets the description text
        Bundle args = getArguments();
        if (args != null){
            // Set description based on argument passed in
            setDetail(args.getInt(KEY_POSITION));
        } else if(mCurrentPosition != -1){
            // Set description based on savedInstanceState defined during onCreateView()
            setDetail(mCurrentPosition);
        }
    }

    public void setDetail(int detailIndex) {


        if(detailIndex < GlobalVariables.displayNodes.size()) {

            MapViewNode node = GlobalVariables.displayNodes.get(detailIndex);

            mCurrentPosition = detailIndex;
            mDetailTextView.setText(node.getNodeName());
            Name = node.getNodeName();
            aID = node.getaID();
            cat = node.getbranchCat();

        }
        else   Log.v("set Detail ", "index > display Nodes ");

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

         // Save the current description selection in case we need to recreate the fragment
        outState.putInt(KEY_POSITION,mCurrentPosition);


    //    outState.clear();
     //   onSaveInstanceState(Bundle.EMPTY);

    }



    private void showMessage(String message) {
        //Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

        Log.v("show message", message);
    }

}

