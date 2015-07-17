package org.opencv.samples.tutorial3;

import android.os.Environment;
import android.util.Log;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.highgui.Highgui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by CaptainCom on 5/23/2015.
 */
public class process {
    //gtdata gt = new gtdata();
    //setimg se = new setimg();
    static  int num;
    CheckChoice check;
    /*static int countCh = StartCheck.NumberPaper;
    static int sumCh =countCh;*/

    public  process(Mat image, int num ){

        this.num = num;
        int sum = 0;


        List<Integer> liscol = new ArrayList<>();

        int[] sumRows = new int[image.height()];
        int[] sumCols = new int[image.width()];

        int n = image.width();
        int m = image.height();

        if (image.height() < image.width()) {
            n = image.height();
            m = image.width();
        }

        int i = 0;
        for (i = 0; i < n; i++) {
            double[] d = Core.sumElems(image.row(i)).val;
            sumRows[i] = (int) d[0];
            double[] d2 = Core.sumElems(image.col(i)).val;
            sumCols[i] = (int) d2[0];
        }
        for (int j = 0; j < m; j++) {
            if (n == image.height()) {
                double[] d = Core.sumElems(image.col(j)).val;
                sumCols[j] = (int) d[0];

            } else {
                double[] d = Core.sumElems(image.row(j)).val;
                sumRows[j] = (int) d[0];
            }
        }

        liscol = sumcol(sumRows,image);
        //Log.i("Rows  ", Arrays.toString(sumRows));

        /*for(int k = 0 ; k<liscol.size();k++){
            Log.i("choice :",Integer.toString(liscol.get(k)));
        }*/
        int countnew=1;
        if (CheckChoice.chcospace==true) {
            for (int j = 0; j < (liscol.size()- 1)&& covert.sumCh > 0 ; j++) {
                //crop(se.getThImage(), 0, liscol.get(j), image.cols(), liscol.get(j + 1) - liscol.get(j), countnew, num);
                crop(image, 0, liscol.get(j), image.cols(), liscol.get(j + 1) - liscol.get(j), countnew, num);
                covert.sumCh--;
                //String name = Tutorial3View.sum +" "+num+" "+i;
                //check = new CheckChoice();
                countnew++;
            }
        }

    }

    private static List<Integer> sumcol(int[] sumcol,Mat image ) {
        List<Integer> list = new ArrayList<Integer>();
        int count = 0, countrow = 0, count0 = 0;
        for (int i = 0; i < sumcol.length; i++) {
            if (sumcol[i] < 1500&&countrow>15) {
                list.add(i);
                countrow = 0;
            }else{
                countrow++;
            }

        }
        Log.e("Size list chioce",Integer.toString(list.size()));
        if(list.size() == 25) {
            /*if (list.get(26) == null  ) {

            }*/
            list.add(image.height());
        }
        return list;
    }

    public void  crop(Mat img , int startX,int startY,int endX,int endY,int i,int num){
        Rect roi = new Rect(startX,startY,endX,endY);
        Mat cropch =new Mat(img,roi);
        //Highgui.imwrite(Environment.getExternalStorageDirectory() + "/DCIM/CameraSnap/CropRowChoice" + Tutorial3View.sum +" "+num+" "+i+ ".png", cropch);
        int n = num ;
        if (n ==2){
            n = 26;
        }else if(n ==3 ){
            n = 51;
        }else if(n ==4){
            n = 76;
        }
        int su = n+i-1;
        String name = Integer.toString(su);
        check = new CheckChoice(cropch,su,num,i);

        Mat img1 = cropch.clone();


    }



}
