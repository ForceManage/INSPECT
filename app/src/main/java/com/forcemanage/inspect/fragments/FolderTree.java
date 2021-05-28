package com.forcemanage.inspect.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.forcemanage.inspect.MainActivity;
import com.forcemanage.inspect.OnDocChangeListener;
import com.forcemanage.inspect.OnProjectSelectionChangeListener;
import com.forcemanage.inspect.R;

public class FolderTree extends Fragment implements OnProjectSelectionChangeListener, OnDocChangeListener, View.OnClickListener {

    private MainActivity globalVariables;

    private static final String TAG = "Folder Tree Fragment";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        globalVariables = (MainActivity) getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.project_info, container, false);




        return view;


    }

    @Override
    public void onClick(View v) {


    }

    @Override
    public void OnTabChanged(int treeNameIndex) {

    }

    @Override
    public void OnProjectChanged(int treeNameIndex){

    }

    }
