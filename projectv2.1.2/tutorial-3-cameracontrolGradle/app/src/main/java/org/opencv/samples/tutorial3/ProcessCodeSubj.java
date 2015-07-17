package org.opencv.samples.tutorial3;

import android.os.Environment;
import android.util.Log;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.highgui.Highgui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by CaptainCom on 7/3/2015.
 */
public class ProcessCodeSubj {

    public boolean processCodeSubj(Mat image,int o, int col1, int row2, int col2){

        Rect roi = new Rect(o,col1,row2,col2);
        Mat croppin=new Mat(image,roi);
        Highgui.imwrite(Environment.getExternalStorageDirectory() + "/DCIM/CameraSnap/CropSubject" + Tutorial3View.sum + ".png", croppin);

        int h = croppin.height();
        int[] sumRows = new int[croppin.height()];
        int[] sumCols = new int[croppin.width()];
        int [] arrrow = new int[11];
        int [] arrcol = new int [14];

        List<Integer> lisr = new ArrayList<>();
        List<Integer> lisc = new ArrayList<>();

        int n = croppin.width();
        int m = croppin.height();

        if (croppin.height() < croppin.width()) {
            n = croppin.height();
            m = croppin.width();
        }

        int i = 0;
        for (i = 0; i < n; i++) {
            double[] d = Core.sumElems(croppin.row(i)).val;
            sumRows[i] = (int) d[0];
            double[] d2 = Core.sumElems(croppin.col(i)).val;
            sumCols[i] = (int) d2[0];
        }
        for (int j = 0; j < m; j++) {
            if (n == croppin.height()) {
                double[] d = Core.sumElems(croppin.col(j)).val;
                sumCols[j] = (int) d[0];

            } else {
                double[] d = Core.sumElems(croppin.row(j)).val;
                sumRows[j] = (int) d[0];
            }
        }
        Log.i("Sub Cols  ", Arrays.toString(sumCols));
        Log.i("Sub rows ", Arrays.toString(sumRows));
        arrrow =setRow(sumCols);
        //lisr =setRow(sumCols);
        arrcol = setCol(sumRows);
        Log.i("Sub  Row  ", Arrays.toString(arrrow));
        Log.i("Sub  Cols  ", Arrays.toString(arrcol));


        int lon = h - arrcol[0]-15;
        if (lon > croppin.height()){
            lon = h -arrcol[0]-15;
        }
        boolean condition = chcon(arrrow,arrcol);
        boolean PinCh = true;
        boolean GroCh = true;

        if(condition == true){
            if(StartCheck.PaperType ==2){
                GroCh = CropGroup(croppin,arrrow[7],arrcol[7] ,croppin.width()-arrrow[7],arrcol[11]-arrcol[7]);
            }else{
                GroCh = CropGroup(croppin,arrrow[7],0 ,croppin.width()-arrrow[7],arrcol[4]);
            }


            for (int j =0;j<6;j++) {
                if (PinCh == true) {
                    PinCh = Croppin(croppin, arrrow[j+1], arrcol[1] , arrrow[j+1] - arrrow[j], arrcol[arrcol.length-3 ] - arrcol[0]-3, j);
                    //Croppin(croppin,36,13,15,105,i);
                }else{
                    return false;
                }
            }
            if(PinCh = true){
                return true;
            }else return false;

        }else return false;




    }

    protected static int[] setRow(int [] sumrow){
        List<Integer> lisr = new ArrayList<>();
        int [] a = new int[11];
        int count = 0, countrow = 0;

        for (int i = 0; i < sumrow.length; i++) {
            if (count == 0){
                if(sumrow[i] < 12000&&countrow>2){
                    a[count]= i;
                    countrow =0;
                    count++;
                }else countrow++;
            }
            if(count == 1){
                if(countrow>22){
                    a[count]= i;
                    countrow =0;
                    count++;
                }else countrow++;

            }
            if(count>1){
                if(sumrow[i] < 12000&&countrow>20){
                    a[count]= i;
                    count++;
                    countrow =0;
                }else {
                    countrow++;
                }
            }


        }


        return  a;

    }
    protected static int[]  setCol(int[] sumcol){
        List<Integer> lisr = new ArrayList<>();
        int [] b = new int[14];
        int count = 0, countrow = 0;

        for (int i = 0; i < sumcol.length; i++) {
            if (count == 0){
                if(sumcol[i] < 43000&&countrow>2){
                    b[count]= i;
                    countrow =0;
                    count++;
                }else countrow++;
            }
            if(count == 1){
                if(countrow>25){
                    b[count]= i;
                    countrow =0;
                    count++;
                }else countrow++;

            }
            if(count>1){
                if(sumcol[i] < 43000&&countrow>20){
                    b[count]= i;
                    count++;
                    countrow =0;
                }else {
                    countrow++;
                }
            }


        }
        return  b;

    }


    public boolean  Croppin(Mat img , int startX,int startY,int endX,int endY,int i){

        int a = startY+endY;
        Rect roi ;
        if (a>img.height()) {
            for (int j = 0; a > img.height(); j += 1) {
                a = a - 1;
            }
            roi = new Rect(startX,startY,endX,a-startY);
        }else{
            roi = new Rect(startX,startY,endX,endY);
        }

        Log.e("CropPin Process",Integer.toString(startX)+" "+Integer.toString(startX)+" "+Integer.toString(endX)+" "+Integer.toString(endY)+" a "+Integer.toString(a));

        Mat cropch =new Mat(img,roi);
        Highgui.imwrite(Environment.getExternalStorageDirectory() + "/DCIM/CameraSnap/CropsubSubject" + Tutorial3View.sum +""+i+ ".png", cropch);
        CheckSubject ch = new CheckSubject();
        boolean con = ch.checkSubject(cropch,i);
        Mat img1 = cropch.clone();
        if(con ==true){
            return true;
        }else{
            return false;
        }

    }

    public void show (List show,String a){
        for (int i = 0; i <show.size() ; i++) {
            Log.d("List "+a, Integer.toString((Integer) show.get(i)));
        }


    }

    protected  static  boolean chcon(int[] a,int[] b){
        int counta=0,countb=0;
        for (int l = 0; l < a.length; l++) {
            if (a[l]!=0){
                counta++;
            }
        }
        for (int k = 0; k < b.length; k++) {
            if(b[k]!=0){
                countb++;
            }
        }

        Log.e("Count A B",Integer.toString(counta)+" "+ Integer.toString(countb));

        if(counta==8&&countb==12){
            return true;
        }else{
            return false;
        }

    }
    public boolean  CropGroup (Mat img , int startX,int startY,int endX,int endY){

        int a = startY+endY;
        Rect roi ;
        if (a>img.height()) {
            for (int j = 0; a > img.height(); j += 1) {
                a = a - 1;
            }
            roi = new Rect(startX,startY,endX,a-startY);
        }else{
            roi = new Rect(startX,startY,endX,endY);
        }
        Mat cropch =new Mat(img,roi);
        Highgui.imwrite(Environment.getExternalStorageDirectory() + "/DCIM/CameraSnap/CropGroup" + Tutorial3View.sum +".png", cropch);
        ProcessGroup ch = new ProcessGroup();
        boolean con = ch.processGroup(cropch);
        Mat img1 = cropch.clone();
        if(con ==true){
            return true;
        }else{
            return false;
        }



    }
}
