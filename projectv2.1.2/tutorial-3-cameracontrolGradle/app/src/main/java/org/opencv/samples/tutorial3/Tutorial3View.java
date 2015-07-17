package org.opencv.samples.tutorial3;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.opencv.android.JavaCameraView;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.widget.Toast;

import static org.bytedeco.javacpp.opencv_highgui.cvLoadImage;
import static org.bytedeco.javacpp.opencv_highgui.cvSaveImage;

public class Tutorial3View extends JavaCameraView implements PictureCallback {

    private static final String TAG = "Sample::Tutorial3View";
    private String mPictureFileName;
    public static int  sum =0;
    Paint paint = new Paint();
    private Paint linePaint;




    public Tutorial3View(Context context, AttributeSet attrs ) {
        super(context, attrs);
        setWillNotDraw(false);
        init();

    }
    protected void init(){
        Resources r = this.getResources();
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setAlpha(200);
        linePaint.setStrokeWidth(1);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.argb(128,0,0,0));
    }

    public List<String> getEffectList() {
        return mCamera.getParameters().getSupportedColorEffects();
    }

    public boolean isEffectSupported() {
        return (mCamera.getParameters().getColorEffect() != null);
    }

    public String getEffect() {
        return mCamera.getParameters().getColorEffect();
    }

    public void setEffect(String effect) {
        Camera.Parameters params = mCamera.getParameters();
        params.setColorEffect(effect);
        mCamera.setParameters(params);
    }

    public List<Size> getResolutionList() {
        return mCamera.getParameters().getSupportedPreviewSizes();
    }

    public void setResolution(Size resolution) {
        disconnectCamera();
        mMaxHeight = resolution.height;
        mMaxWidth = resolution.width;
        connectCamera(getWidth(), getHeight());
    }

    public Size getResolution() {

        return mCamera.getParameters().getPreviewSize();
    }

    public void takePicture(final String fileName) {

        Log.i(TAG, "Taking picture");
        this.mPictureFileName = fileName;
        int resolution = 0;
        // Postview and jpeg are sent in the same buffers if the queue is not empty when performing a capture.
        // Clear up buffers to avoid mCamera.takePicture to be stuck because of a memory issue
        Camera.Parameters params = mCamera.getParameters();
        List<Camera.Size> picsize = params.getSupportedPictureSizes();
        for(int i=0;i< picsize.size();i++) {
            if (picsize.get(i).height==720 && picsize.get(i).width ==1280) {
                resolution =i;
            }
        }
        Camera.Size selected = picsize.get(resolution);
        params.setPictureSize(selected.width,selected.height);
        mCamera.setParameters(params);
        String a = Integer.toString(selected.width);
        String b = Integer.toString(selected.height);
        //for(int i =0; i< picsize.size();i++){
            Log.e("size img =",Integer.toString(picsize.get(resolution).height)+" "+Integer.toString(picsize.get(resolution).width));
       // }


        mCamera.setPreviewCallback(null);

        // PictureCallback is implemented by the current class
        mCamera.takePicture(null, null, this);




    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        sum++;

        Log.i(TAG, "Saving a bitmap to file");
        // The camera preview was automatically stopped. Start it again.
        mCamera.startPreview();
        mCamera.setPreviewCallback(this);


        // Write the image in a file (in jpeg format)
        try {
            FileOutputStream fos = new FileOutputStream(mPictureFileName);
            //gtdata gt = new gtdata();

            fos.write(data);
            fos.close();
            Bitmap picture = BitmapFactory.decodeByteArray(data, 0, data.length);
            Mat img = new Mat(picture.getHeight(), picture.getWidth(),CvType.CV_8UC4);
            Utils.bitmapToMat(picture, img);
            Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);

            covert cv = new covert();
            cv.covert1(img);
            Highgui.imwrite(Environment.getExternalStorageDirectory()
                    + "/DCIM/CameraSnap/captureFromOpenCV"+sum + ".jpg", img);



           /* int x = MainActivity.x1 * 2;
            int y = MainActivity.y1 * 2;
            Rect roi = new Rect(new Point(x, y), new Point(img.cols() - x,
                    img.rows() - y));
            cropped = new Mat(img, roi);
            Highgui.imwrite(Environment.getExternalStorageDirectory()
                    + "/project/Crop.jpg", cropped);*/


        } catch (java.io.IOException e) {
            Log.e("PictureDemo", "Exception in photoCallback", e);
        }

    }


    protected void onDraw(Canvas canvas){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        int h1 = (height/100)*5;
        int h2 = height -(height/100)*5;
        int w1 = (width/100)*12;
        int w2 = width -w1;

        paint.setColor(Color.RED);
        paint.setStrokeWidth(4);
        canvas.drawRect(w1,h1,w1+10,h2,paint);
        canvas.drawRect(w1,h1,w2,h1+10,paint);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(4);
        canvas.drawRect(w2,h1,w2+10,h2,paint);
        canvas.drawRect(w1,h2,w2,h2+10,paint);
        /*
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Pass",250,250,paint);*/





       // canvas.drawRect(288,22,288+10,864,paint);
       // canvas.drawRect(288,22,1692,22+10,paint);
       //canvas.drawRect(1747,22,1692+10,864,paint);
       // canvas.drawRect(288,864,1692,864+10,paint);

       // paint.setStrokeWidth(0);
        //paint.setColor(Color.CYAN);

    }






}
