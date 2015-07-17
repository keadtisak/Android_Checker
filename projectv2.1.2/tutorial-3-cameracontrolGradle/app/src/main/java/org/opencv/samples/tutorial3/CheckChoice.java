package org.opencv.samples.tutorial3;

import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by CaptainCom on 5/24/2015.
 */
public class CheckChoice {
    static int[][][] choice = new int[10][26][4];
    static int [][][] AnsChoice = new int [10][25][4];
    public static boolean chcospace= true;
    static int []sumPoi = new int[4];
    static String [][] ChoiceNew = new String[101][1];

    public CheckChoice (Mat image,int  name ,int num,int countnew){
        Mat knl4 = Mat.zeros(3,3, CvType.CV_8U);
        knl4.put(0,1,1);
        knl4.put(1,0,1);
        knl4.put(1,2,1);
        knl4.put(2,1,1);
        //Imgproc.dilate(image, image, knl4);
        //Imgproc.erode(image,image,knl4);
        //Highgui.imwrite(Environment.getExternalStorageDirectory() + "/DCIM/CameraSnap/CropRowChoice" + Tutorial3View.sum + " " + num + " " + countnew + ".png", image);

        int[] sumCols = new int[image.width()];
        int n = image.width();

        for (int i = 0; i < n; i++) {
            double[] d2 = Core.sumElems(image.col(i)).val;
            sumCols[i] = (int) d2[0];
        }
        //Log.i("Rows  ", Arrays.toString(sumCols));

        String answer="";
        int s1 = image.cols()/5;
        int s2 = s1*2;
        int s3 = s1*3;
        int s4 = s1*4;
        int [] count = new int[5];
        count[0]=s1;count[1]=s2;count[2]=s3;count[3]=s4;count[4]=image.cols();




        //answer = checkchioce(sumrow, scol,num+1,countnew);
        List<Integer> lisCount = new ArrayList();
        List<Integer> lisR = new ArrayList<>();
        lisR = SumRows(sumCols,image.width());
        int l;
       /* for (int k = 0; k <6 ; k++) {
            Log.e("ListRowsubch",Integer.toString(lisR.get(k)));
        }*/


        for (int i = 0; i <4 ; i++) {
            int s = CropsSubCh(image,lisR.get(i+1)+3,5,lisR.get(i+2)-lisR.get(i+1)-3,image.height()-5,i,Integer.toString(name));
            //int s = CropsSubCh(image,count[i]+2,5,count[i+1]-count[i]-6,image.height()-5,i,Integer.toString(name));
            lisCount.add(s);

        }
        answer = CountPoint(lisCount,num+1,countnew);

        ChoiceNew[name][0] = answer;

        Log.e("Check Choice :",Tutorial3View.sum +" "+name+" "+ answer);
        String a = new String("Space");
        String b = new String(answer);
        boolean ch = a.equals(b);
        //Log.e("CheckPIn", Arrays.toString(sumrow));
        if (pre_process.paperans==true&&ch == true ){
            this.chcospace = false;

        }else {
            this.chcospace = true;
        }


    }

    protected static int CropsSubCh(Mat img , int startX,int startY,int endX,int endY,int i,String name){

        Rect roi = new Rect(startX,startY,endX,endY);
        Mat dis  = new Mat(img,roi);
        sumPoi[i] = dis.width()*dis.height();


       /* Mat knl2 = Mat.zeros(3,3, CvType.CV_8U);
        knl2.put(0,0,1);
        knl2.put(0,2,1);
        knl2.put(1,1,1);
        knl2.put(2,0,1);
        knl2.put(2,2,1);
        Imgproc.erode(dis,dis,knl2);
        Imgproc.dilate(dis,dis,Imgproc.getStructuringElement(Imgproc.MORPH_RECT,new Size(3,3)));*/


        int sum = (dis.width()*dis.height())-Core.countNonZero(dis);
        //Log.e("countBlack:",name+" "+Integer.toString(i)+" "+Integer.toString(sum));
        //Highgui.imwrite(Environment.getExternalStorageDirectory() + "/DCIM/CameraSnap/CropSubChoice" + name+ " " + i + ".png", dis);
        return sum;

    }

    protected static String CountPoint (List <Integer>r ,int num,int countnew){
        String ans="";
        int stan = 220;
        double stan1,stan2,stan3,stan4;
        double persent= 0.75;
        if(StartCheck.PaperType ==2){
             stan = 278;
            stan1 = sumPoi[0]*persent;
            stan2 = sumPoi[1]*persent;
            stan3 = sumPoi[2]*persent;
            stan4 = sumPoi[3]*persent;
        }else{
             stan = 220;
            stan1 = sumPoi[0]*persent;
            stan2 = sumPoi[1]*persent;
            stan3 = sumPoi[2]*persent;
            stan4 = sumPoi[3]*persent;
        }

        int count =0;
        if (pre_process.paperans == true) {
            if (r.get(0)>stan1){
                AnsChoice[num - 1][countnew - 1][count] = 1;
                count++;
                ans="A";
            }if(r.get(1)>stan2){
                if (count >0){ans =ans+",";}
                AnsChoice[num - 1][countnew - 1][count] = 2;
                ans=ans+"B";
                count++;
            }if(r.get(2)>stan3){
                if (count >0){ans =ans+",";}
                AnsChoice[num - 1][countnew - 1][count] = 3;
                ans=ans+"C";
                count++;
            }if(r.get(3)>stan4){
                if (count >0){ans =ans+",";}
                AnsChoice[num - 1][countnew - 1][count] = 4;
                ans = ans+"D";
            }if(r.get(0)<=stan&&r.get(1)<=stan&&r.get(2)<=stan&&r.get(3)<=stan){
                AnsChoice[num - 1][countnew - 1][count] = 0;
                ans = "Space";
            }
        }else{
            if (r.get(0)>stan1){

                choice[num - 1][countnew - 1][count] = 1;
                count++;
                ans="A";
            }if(r.get(1)>stan2){
                if (count >0){ans =ans+",";}
                choice[num - 1][countnew - 1][count] = 2;
                count++;
                ans=ans+"B";

            }if(r.get(2)>stan3){
                if (count >0){ans =ans+",";}
                choice[num - 1][countnew - 1][count] = 3;
                count++;
                ans=ans+"C";

            }if(r.get(3)>stan4){
                if (count >0){ans =ans+",";}
                choice[num - 1][countnew - 1][count] = 4;
                ans = ans+"D";

            }if(r.get(0)<=stan&&r.get(1)<=stan&&r.get(2)<=stan&&r.get(3)<=stan){
                choice[num - 1][countnew - 1][count] = 0;
                ans = "Space";
            }

        }



        return ans;


    }
    protected static List<Integer> SumRows(int [] sumrows,int width) {
        List a = new ArrayList();
        int count = 0, countrow = 0;

        for (int i = 0; i < sumrows.length; i++) {
            if (count == 0) {
                if (sumrows[i] < 300  ) {
                    a.add(i);
                    countrow = 0;
                    count++;
                } else if (countrow >4){
                    a.add(0);
                    count++;
                }else countrow++;
            }
            if(count>=1&&count <5){
                if(sumrows[i] < 500&&countrow>20){
                    a.add(i);
                    count++;
                    countrow =0;
                }else {
                    countrow++;
                }
            }
            if (count ==5){
                a.add(width);
            }
        }
        /*if(a.size() ==5){
            if (a.get(5)==null){
                a.add(width);
            }
        }
*/
        return a;

    }

    protected void nChoice(String ans,int num ){


    }

}
