package com.forcemanage.inspect.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.forcemanage.inspect.MainActivity;
import com.forcemanage.inspect.OnDocChangeListener;
import com.forcemanage.inspect.R;

import java.io.File;

public class MultiPic6PageViewFragment extends Fragment implements OnDocChangeListener {

    private MainActivity globalVariables;
    private ImageView photoA;
    private ImageView photoB;
    private ImageView photoC;
    private ImageView photoD;
    private ImageView photoE;
    private ImageView photoF;
    private  String Title;
    private  String subTitle;
    private String itemTitle;
    private TextView title;
    private TextView subtitle;
    private TextView itemtitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Title = bundle.getString("title");
            subTitle = bundle.getString("subTitle");
            itemTitle = bundle.getString("itemTitle");
        }

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        globalVariables = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.multi_pic6view, container, false);

 //       title = (TextView) view.findViewById(R.id.branchTitle);
 //       subtitle = (TextView) view.findViewById(R.id.parentLabel);
 //       itemtitle = (TextView) view.findViewById(R.id.branchName);

   //     title.setText(Title);
   //     subtitle.setText(subTitle);
  //      itemtitle.setText(itemTitle);

        photoA = (ImageView) view.findViewById(R.id.imageView);
        //photoA.setOnClickListener(this);
        photoB = (ImageView) view.findViewById(R.id.imageView2);
        //    photoB.setOnClickListener(this);
        photoC = (ImageView) view.findViewById(R.id.imageView3);
        //    photoC.setOnClickListener(this);
        photoD = (ImageView) view.findViewById(R.id.imageView4);
        //    photoD.setOnClickListener(this);
        photoE = (ImageView) view.findViewById(R.id.imageView5);
        photoF = (ImageView) view.findViewById(R.id.imageView6);

        for (int i = 0; i < 6; i++) {
            if (globalVariables.photos[i] == null) {
                globalVariables.photos[i] = "";
                //    cameraSnap = photos[i];
            }


            if ( globalVariables.photos[i].length() > 12) {
                String dirName =  globalVariables.photos[i].substring(6, 14);
                String root = Environment.getExternalStorageDirectory().toString();
                File Image = new File(root + "/A2D_" + dirName + "/" +  globalVariables.photos[i]);
                Bitmap myBitmap = BitmapFactory.decodeFile(Image.getAbsolutePath());

                switch (i) {
                    case 0:
                        // globalVariables.mPhotoImageView = (ImageView) view.findViewById(R.id.imageView);
                        photoA.setImageBitmap(myBitmap);

                        break;

                    case 1:
                        //           mPhotoImageView = (ImageView) findViewById(R.id.imageView2);
                        photoB.setImageBitmap(myBitmap);

                        break;

                    case 2:
                        //            mPhotoImageView = (ImageView) findViewById(R.id.imageView3);
                        photoC.setImageBitmap(myBitmap);

                        break;

                    case 3:
                        //           mPhotoImageView = (ImageView) findViewById(R.id.imageView4);
                        photoD.setImageBitmap(myBitmap);
                        //          imageName4.setText(dirName);
                        break;

                    case 4:
                        //           mPhotoImageView = (ImageView) findViewById(R.id.imageView5);
                        photoE.setImageBitmap(myBitmap);
                        //         imageName5.setText(dirName);
                        break;

                    case 5:
                        //           mPhotoImageView = (ImageView) findViewById(R.id.imageView5);
                        photoF.setImageBitmap(myBitmap);
                        //         imageName5.setText(dirName);
                        break;


                } //End of switch
            } //End if there is an image file
            else {

                switch (i) {

                    case 0:
                        //   mPhotoImageView = (ImageView) findViewById(R.id.imageView);
                        photoA.setImageResource(R.drawable.ic_camera);
                        break;

                    case 1:
                        //     mPhotoImageView = (ImageView) findViewById(R.id.imageView2);
                        photoB.setImageResource(R.drawable.ic_camera);
                        //     imageName2.setText("No Photo Record");
                        break;

                    case 2:
                        //        mPhotoImageView = (ImageView) findViewById(R.id.imageView3);
                        photoC.setImageResource(R.drawable.ic_camera);
                        //         imageName3.setText("No Photo Record");
                        break;

                    case 3:
                        //        mPhotoImageView = (ImageView) findViewById(R.id.imageView4);
                        photoD.setImageResource(R.drawable.ic_camera);
                        //        imageName4.setText("No Photo Record");
                        break;

                    case 4:
                        //        mPhotoImageView = (ImageView) findViewById(R.id.imageView5);
                        photoE.setImageResource(R.drawable.ic_camera);
                        //        imageName5.setText("No Photo Record");
                        break;

                    case 5:
                        //        mPhotoImageView = (ImageView) findViewById(R.id.imageView5);
                        photoF.setImageResource(R.drawable.ic_camera);
                        //        imageName5.setText("No Photo Record");
                        break;


                }//End of switch
            }//End of else

        }//End of loop


        return view;

    }

    @Override
    public void OnTabChanged(int treeNameIndex) {

    }
}
