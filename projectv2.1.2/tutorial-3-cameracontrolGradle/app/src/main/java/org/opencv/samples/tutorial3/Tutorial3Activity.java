package org.opencv.samples.tutorial3;

import java.io.File;
import java.lang.reflect.Array;
import java.security.Policy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.*;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static org.bytedeco.javacpp.opencv_core.*;

public class Tutorial3Activity extends Activity implements CvCameraViewListener2, OnTouchListener {
    private static final String TAG = "OCVSample::Activity";
    static Activity thisActivity = null;


    private Tutorial3View mOpenCvCameraView;
    private List<Size> mResolutionList ;
    private MenuItem[] mEffectMenuItems;
    private SubMenu mColorEffectsMenu;
    private MenuItem[] mResolutionMenuItems;
    private SubMenu mResolutionMenu;
    private Tutorial3Activity opencam;
    static {
        if (!OpenCVLoader.initDebug()) {
            // Handle initialization error
        }
    }
    ImageView drawingImageView;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {


        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {

                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                    mOpenCvCameraView.setOnTouchListener(Tutorial3Activity.this);
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }

        }

    };////////////////โหลดopencvมาใช้

    /*public Tutorial3Activity() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);///แนวนอน

      setContentView(R.layout.tutorial3_surface_view);///set layout

        mOpenCvCameraView = (Tutorial3View) findViewById(R.id.tutorial3_activity_java_surface_view);

        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);

        mOpenCvCameraView.setCvCameraViewListener(this);
        drawingImageView = (ImageView) this.findViewById(R.id.DrawingImageView);
        thisActivity  =this;
        Tutorial3View.sum =0;

      /*  final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    String fileName = Environment.getExternalStorageDirectory().getPath() + "/DCIM/CameraSnap/sample_" +(Tutorial3View.sum+1) + ".jpg";
                    mOpenCvCameraView.takePicture(fileName);

                    Thread.sleep(1000);

                }catch (InterruptedException e ){
                    e.printStackTrace();
                }
            }
        });
*/

        //DrawView vi = new DrawView(this);
        delete_all();
        //grabber();


        ///////////////สร้างเฟรม///////////
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        //OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onCameraViewStarted(int width, int height) {

    }

    public void onCameraViewStopped() {

    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {

        return inputFrame.rgba();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub

        MenuInflater myMenuInflater = getMenuInflater();
        myMenuInflater.inflate(R.layout.mymenu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        switch (item.getItemId()) {
            case R.id.tutorial3_activity_java_surface_view:

                Intent gotutorial3 = new Intent(getApplicationContext(),
                        Tutorial3Activity.class);
                startActivity(gotutorial3);

                return true;



            case R.id.result:

                Intent goResult = new Intent(getApplicationContext(),
                        Result.class);
                startActivity(goResult);

                return true;

            case R.id.paper:

                Intent goPaper = new Intent(getApplicationContext(),
                        Paper.class);
                startActivity(goPaper);

                return true;

            case R.id.help:

                Intent goHelp = new Intent(getApplicationContext(),
                        help.class);
                startActivity(goHelp);

                return true;
            case R.id.about:

                Intent goAbout = new Intent(getApplicationContext(),
                        About.class);
                startActivity(goAbout);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }*/

    @SuppressLint("SimpleDateFormat")
    @Override
    public boolean onTouch(View v, MotionEvent event) {



        //mOpenCvCameraView.setResolution();
        Log.i(TAG,"onTouch event");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateandTime = sdf.format(new Date());
        //String fileName = Environment.getExternalStorageDirectory().getPath() + "/sample_" + currentDateandTime + ".jpg";
        String fileName = Environment.getExternalStorageDirectory().getPath() + "/DCIM/CameraSnap/sample_" +(Tutorial3View.sum+1) + ".jpg";
        mOpenCvCameraView.takePicture(fileName);
       // Toast.makeText(this, fileName + " saved", Toast.LENGTH_LONG).show();


        /*Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean t = true;
                while (t) {
                    try {
                        String fileName = Environment.getExternalStorageDirectory().getPath() + "/DCIM/CameraSnap/sample_" + (Tutorial3View.sum + 1) + ".jpg";

                        mOpenCvCameraView.takePicture(fileName);

                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
*/
        return false;
    }





    public void delete_all() {
        Log.e("Delete","success");
        File dir = new File(Environment.getExternalStorageDirectory().getPath() + "/DCIM/CameraSnap/");
        for (File file : dir.listFiles())
            file.delete();
    }
    public static void showtoast(String txt,int time){
        if (time ==1){
            Toast.makeText(thisActivity,txt,Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(thisActivity,txt,Toast.LENGTH_SHORT).show();
        }


    }


    public static void showtoast(String s) {
        Toast.makeText(thisActivity,s,Toast.LENGTH_SHORT).show();
    }
}
