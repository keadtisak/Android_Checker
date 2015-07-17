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
 * Created by CaptainCom on 7/4/2015.
 */
public class ProcessGroup {
    public static int Group;
    public boolean processGroup(Mat img){
        int [] arrcol = new int[3];
        int[] sumRows = new int[img.height()];
        int[] sumCols = new int[img.width()];
        boolean condition;
        boolean conditionf;
        int n = img.width();
        int m = img.height();

        if (img.height() < img.width()) {
            n = img.height();
            m = img.width();
        }

        int i = 0;
        for (i = 0; i < n; i++) {
            double[] d = Core.sumElems(img.row(i)).val;
            sumRows[i] = (int) d[0];
            double[] d2 = Core.sumElems(img.col(i)).val;
            sumCols[i] = (int) d2[0];
        }
        for (int j = 0; j < m; j++) {
            if (n == img.height()) {
                double[] d = Core.sumElems(img.col(j)).val;
                sumCols[j] = (int) d[0];

            } else {
                double[] d = Core.sumElems(img.row(j)).val;
                sumRows[j] = (int) d[0];
            }
        }
        Log.i("Group Check ", Arrays.toString(sumCols));
        arrcol = setCol(sumCols);
        Log.i("Group cols ", Arrays.toString(arrcol));
        boolean chcols = checkarrcol(arrcol);
        if(chcols == true) {
            CropGroup(img, arrcol[1], 5, arrcol[2] - arrcol[1], img.height());
            return true;
        }else {
            return false;
        }



    }

    private boolean checkarrcol(int[] arrcol) {
        int count = 0;
        for (int i = 0; i < arrcol.length; i++) {
            if (arrcol[i]>0){
                count++;
            }
        }
        if(count==3){
            return true;
        }else{
            return false;
        }

    }

    protected static int[]  setCol(int[] sumcol) {
        List<Integer> list = new ArrayList<Integer>();
        int count = 0, countrow = 0, count0 = 0;
        int [] b = new int[3];
        for (int i = 0; i < sumcol.length; i++) {
            if (sumcol[i] < 3000 && countrow > 20) {
                b[count]= i;
                countrow = 0;
                count++;
            } else {
                countrow++;
            }

        }


        return b;
    }

    public void  CropGroup(Mat img , int startX,int startY,int endX,int endY){

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
        Highgui.imwrite(Environment.getExternalStorageDirectory() + "/DCIM/CameraSnap/CropsubGroup" + Tutorial3View.sum + ".png", cropch);
        CheckGroup(cropch);

    }

    public void CheckGroup (Mat image){
        int[] sumRows= new int[image.height()];
        int n = image.height();
        int i = 0;
        for (i = 0; i < n; i++) {
            double[] d = Core.sumElems(image.row(i)).val;
            sumRows[i] = (int) d[0];

        }
        Log.i("Group Rows ", Arrays.toString(sumRows));
        checkGroupProcess(sumRows,image.rows());


    }
    public void checkGroupProcess (int[] sumcol , int srow) {
        int  GroupPaper;
        int countrow = srow/4;
        double standardcheck = countrow*0.55;
        int pin0=0,pin1=0,pin2=0,pin3=0,pin4=0,pin5=0,pin6=0,pin7=0,pin8=0,pin9=0;
        int rank=1500;

        for (int i = 0; i < sumcol.length; i++) {
            if(sumcol[i] < rank&& i>=countrow&&i<countrow*2){
                pin1++;
            }else if(sumcol[i] <rank&& i>=countrow*2&&i<countrow*3){
                pin2++;
            }else if(sumcol[i] < rank&&i>=countrow*3&&i<countrow*4){
                pin3++;
            }

        }
        //System.out.println("Standard ="+standardcheck+" "+ch1+" "+ch2+" "+ch3+" "+ch4);
         if(pin1>standardcheck){
            GroupPaper =1;
        }else if(pin2>standardcheck){
            GroupPaper=2;
        }else if(pin3>standardcheck){
            GroupPaper =3;
        }else{
            GroupPaper =1;
        }

        Log.i("GroupPaper is ",Integer.toString(GroupPaper));
        Group = GroupPaper;


    }

}
