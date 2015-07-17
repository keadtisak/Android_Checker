package org.opencv.samples.tutorial3;

import android.os.Environment;
import android.util.Log;

import org.bytedeco.javacpp.opencv_stitching;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;

/**
 * Created by CaptainCom on 5/29/2015.
 */
public class CheckPin {
    //IDdata id = new IDdata();
    public  static String Ids="11111111" ;
    public static  boolean chPin;

    public boolean checkpin(Mat image ,int num){
        Mat knl4 = Mat.zeros(3,3, CvType.CV_8U);
        knl4.put(0,1,1);
        knl4.put(1,0,1);
        knl4.put(1,2,1);
        knl4.put(2,1,1);
        Imgproc.dilate(image, image, knl4);
        int h = image.height();
        String Ans;
        String ch = new String("null");
        int[] sumRows= new int[image.height()];
        int n = image.height();
        int g[][] = new int[9][3];
        int i = 0;
        int sum =0,count = 0,CountSize=0;
        for (i = 0; i < n; i++) {
            double[] d = Core.sumElems(image.row(i)).val;
            sumRows[i] = (int) d[0];

        }
        String ans =checkpinprocess(sumRows,h,num);

        Ids = Ids+ans;
        //id.setIDstudent(ans);
       // Log.i("Pin Check ", Arrays.toString(sumRows));
        Log.e("Check Pin:",num+" "+ans );
        String s = new String(ans);
        boolean con = s.equals(ch);

        if(con  == true){
            chPin= false;
            Log.d("return pin ","false");
            return false;
        }else{
            chPin= true;
            Log.d("return pin ","true");
            return true;
        }




    }




    private static  String checkpinprocess (int[] sumcol , int srow,int num) {
        String ans;
        int countrow = srow/10;
        double standardcheck = countrow*0.55;
        int pin0=0,pin1=0,pin2=0,pin3=0,pin4=0,pin5=0,pin6=0,pin7=0,pin8=0,pin9=0;
        int rank=1000;

        for (int i = 0; i < sumcol.length; i++) {
            if (sumcol[i] < rank&& i>0&&i<countrow) {
                pin0++;
            }else if(sumcol[i] < rank&& i>=countrow&&i<countrow*2){
                pin1++;
            }else if(sumcol[i] <rank&& i>=countrow*2&&i<countrow*3){
                pin2++;
            }else if(sumcol[i] < rank&&i>=countrow*3&&i<countrow*4){
                pin3++;
            }else if(sumcol[i] < rank&& i>=countrow*4&&i<countrow*5){
                pin4++;
            }else if(sumcol[i] < rank&& i>=countrow*5&&i<countrow*6+2){
                pin5++;
            }else if(sumcol[i] < rank&& i>=countrow*6+2&&i<countrow*7+2){
                pin6++;
            }else if(sumcol[i] < rank&& i>=countrow*7+2&&i<countrow*8+2){
                pin7++;
            }else if(sumcol[i] < rank&& i>=countrow*8+2&&i<countrow*9+2){
                pin8++;
            }else if(sumcol[i] < rank&& i>=countrow*9+2&&i<srow){
                pin9++;
            }

        }
        //System.out.println("Standard ="+standardcheck+" "+ch1+" "+ch2+" "+ch3+" "+ch4);
        if(pin0>standardcheck){
            ans ="0";
        }else if(pin1>standardcheck){
            ans ="1";
        }else if(pin2>standardcheck){
            ans ="2";
        }else if(pin3>standardcheck){
            ans ="3";
        }else if(pin4>standardcheck){
            ans ="4";
        }else if(pin5>standardcheck){
            ans ="5";
        }else if(pin6>standardcheck){
            ans ="6";
        }else if(pin7>standardcheck){
            ans ="7";
        }else if(pin8>standardcheck){
            ans ="8";
        }else if(pin9>standardcheck){
            ans ="9";
        }else{
            ans ="null";
        }
        return ans;

    }

    protected static  String countPin (int [] SunRows){
        String ans="";


        return ans;
    }



}
