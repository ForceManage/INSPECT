package com.forcemanage.inspect.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.FloatMath;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.forcemanage.inspect.DBHandler;
import com.forcemanage.inspect.InspectionActivity;
import com.forcemanage.inspect.MainActivity;
import com.forcemanage.inspect.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Callable;

import static com.forcemanage.inspect.InspectionActivity.copy;
import static java.lang.Math.sqrt;

public class PhotoEditFragment extends Fragment implements View.OnClickListener, View.OnTouchListener, GestureDetector.OnGestureListener, ScaleGestureDetector.OnScaleGestureListener{

    private InspectionActivity globalVariables;

    private static final String TAG = "PhotoEdit Fragment";
    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private static final int ACTIVITY_GET_FILE = 1;
    private static final int ACTIVITY_DRAW_FILE = 2;
    private int frame;
    private boolean Edited = false;
    private float xCoOrdinate, yCoOrdinate, xPos, yPos;
    public Bitmap mybitmap,newbmp,bitmap,bmp;
    private ImageView photo, camera, close, save, photo_insert;
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mGestureDetector;
    private float mScaleFactor = 0.5f;
    private int projectId;
    private int inspectionId;
    private int aId;
    private String fname;
    private String dirName;
    private String name;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {

                projectId = bundle.getInt("projectID");
                inspectionId = bundle.getInt("inspectionID");
                aId = bundle.getInt("aID");

        }

        Edited = false;




    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        globalVariables = (InspectionActivity) getActivity();
    }


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.photo_edit, container, false);

        // These matrices will be used to move and zoom image


        photo = (ImageView) view.findViewById(R.id.imageView);
        photo_insert = (ImageView) view.findViewById(R.id.photo_insert);
        camera = (ImageView) view.findViewById(R.id.cameraClick);
        close = (ImageView) view.findViewById(R.id.Close);
        save = (ImageView) view.findViewById(R.id.Save);

        photo_insert.setOnTouchListener(this);






        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVariables.photoframe = 1;
                globalVariables.mPhotoImageView = photo_insert;
                globalVariables.takeImageFromCamera(null);
                close.setBackgroundResource(R.drawable.edit_border_solid);
                Edited=true;

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photo.setDrawingCacheEnabled(true);
                photo_insert.setDrawingCacheEnabled(true);
                mybitmap = mergeBitmaps(photo.getDrawingCache(),photo_insert.getDrawingCache(),xPos,yPos);
                try {
                    File file = createPhotoFile();
                    name = file.getName();
                    FileOutputStream fOut = new FileOutputStream(file);

                    mybitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                    fOut.flush();
                    fOut.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                DBHandler dbHandler = new DBHandler(getActivity(), null, null, 1);
                dbHandler.updateInspectionItemPhoto_insert(projectId,inspectionId,aId, name,globalVariables.filephoto);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();

                Edited=true;

            }
        });


        if ( globalVariables.photos[globalVariables.filephoto-1].length() > 12) {
            String dirName =  globalVariables.photos[globalVariables.filephoto-1].substring(6, 14);
            String root = Environment.getExternalStorageDirectory().toString();
            File Image = new File(root + "/A2D_" + dirName + "/" +  globalVariables.photos[globalVariables.filephoto-1]);
            mybitmap = BitmapFactory.decodeFile(Image.getAbsolutePath());
            photo.setImageBitmap(mybitmap);

        } //End if there is an image file



        return view;
    }



    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                xCoOrdinate = view.getX() - event.getRawX();
                yCoOrdinate = view.getY() - event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                view.animate().x(event.getRawX() + xCoOrdinate).y(event.getRawY() + yCoOrdinate).setDuration(0).start();
                xPos = view.getX();
                yPos = view.getY();
                break;

            default:
                return false;
        }
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {


        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
     //   mScaleFactor *= scaleGestureDetector.getScaleFactor();
        photo_insert.setScaleX(mScaleFactor);
        photo_insert.setScaleY(mScaleFactor);
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return false;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }



    @Override
    public void onClick(View v) {

    }


    public static Bitmap mergeBitmaps(Bitmap bitmapBg, Bitmap bitmapFg, float fgLeftPos, float fgTopPos) {

        // Calculate the size of the merged Bitmap
        int mergedImageWidth = bitmapBg.getWidth();
        int mergedImageHeight = bitmapBg.getHeight();

        // Create the return Bitmap (and Canvas to draw on)
        Bitmap mergedBitmap = Bitmap.createBitmap(mergedImageWidth, mergedImageHeight, bitmapBg.getConfig());
        Canvas mergedBitmapCanvas = new Canvas(mergedBitmap);

        // Draw the background image
        mergedBitmapCanvas.drawBitmap(bitmapBg, 0f, 0f, null);

        //Draw the foreground image
        mergedBitmapCanvas.drawBitmap(bitmapFg, fgLeftPos, fgTopPos, null);

        return mergedBitmap;

    }

    File createPhotoFile()throws IOException {

        fname = dayTime(3);
        dirName = dayTime(1);
        fname = projectId + "_" + fname;

        String root = Environment.getExternalStorageDirectory().toString();
        String SD = root + "/A2D_" + dirName + "/";
        File storageDirectory = new File(root + "/A2D_" + dirName + "/");
        // Toast.makeText(this, "should have made directory",Toast.LENGTH_SHORT).show();
        // Toast.makeText(this, "string root name = "+root ,Toast.LENGTH_SHORT).show();

        if (!storageDirectory.exists()) {
            storageDirectory.mkdirs();
        }
        File image = File.createTempFile(fname, ".jpg", storageDirectory);





        // from.renameTo(to);  This deletes the source file from

    //    try {

          //  copy(from, to);
            //   if(to.length()/1048576 > 1) resizeImage(to);

   //     } catch (IOException e) {
     //       e.printStackTrace();
     //   }
        return image;
    }


    private String dayTime(int Type) {

        String daytime = "20000101";

        switch (Type) {

            case (1): {

                java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyyMMdd");
                Date date_ = Calendar.getInstance().getTime();
                daytime = (dateFormat.format(date_));
                break;
            }

            case (2): {

                java.text.SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
                Date date_ = Calendar.getInstance().getTime();
                daytime = (dateFormat.format(date_));
                break;
            }

            case (3): {

                java.text.SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date_ = Calendar.getInstance().getTime();
                daytime = (dateFormat.format(date_));
                break;
            }

            case (4): {

                java.text.SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date_ = Calendar.getInstance().getTime();
                daytime = (dateFormat.format(date_));
                break;
            }
        }
        return daytime;
    }

}
