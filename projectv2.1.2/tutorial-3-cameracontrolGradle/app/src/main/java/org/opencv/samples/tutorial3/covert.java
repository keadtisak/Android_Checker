package org.opencv.samples.tutorial3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.bytedeco.javacpp.opencv_core;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import HorProcess.HPreProcess;

/**
 * Created by CaptainCom on 4/7/2015.
 */
public class covert {
    gtdata gt = new gtdata();
    IDdata id = new IDdata();
    setimg se = new setimg();
    boolean check = true;
    private long StartTime;
    private long EndTime;
    public static List<String> total = new ArrayList();
    public static List<String> sCode = new ArrayList();
    public static List sPoint = new ArrayList();
    static String idstud;
    static int sumCh ;

    public void covert1(Mat img){
        Log.e("Integer Start App", "จำนวนข้อ"+Integer.toString(StartCheck.NumberPaper)+" กระดาษคำตอบแบบที่"+Integer.toString(StartCheck.PaperType));
        StartTime  = android.os.SystemClock.uptimeMillis();
        pre_process.paperans=false;
        CheckAns.point =0;
        String a = new String ("00000000");
        idstud = CheckPin.Ids;
        sumCh = StartCheck.NumberPaper;

        CheckPin.Ids ="";
        CheckSubject.IdSubject="";
        img = tobina(img);
        if (StartCheck.PaperType ==2){
            HPreProcess.horPreProcess(gt.getimgcrop());
        }else{
            pre_process.srsc(gt.getimgcrop());
        }
        boolean chid = CheckIdStud();

        EndTime = android.os.SystemClock.uptimeMillis();

        Log.e("Time Process is",Long.toString(EndTime-StartTime)+" Scode"+CheckSubject.IdSubject+" ชุดที่ "+Integer.toString(ProcessGroup.Group));


        Log.e("IDStudent ",CheckPin.Ids);
        ShowF();

    }
    public Mat tobina (Mat img){
        //Log.e("tobina","true");
        //Imgproc.resize(img ,img ,new Size(860,640));
       // Mat Gray =new Mat(img.cols(), img.rows(), opencv_core.cvTypeOf(CV_8));
        Imgproc.adaptiveThreshold(img,img,255,Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,Imgproc.THRESH_BINARY,3,3);
        se.setThImage(img);
        Highgui.imwrite(Environment.getExternalStorageDirectory()+"/DCIM/CameraSnap/opnecv to binary"+Tutorial3View.sum+".jpg",img);

        Log.e("size picture ",Integer.toString(img.height())+" "+Integer.toString(img.width()));
         crop(img,155,35,980,640);


       // Log.e("convert tobina ","true");
        return img;
    }

    public Mat crop(Mat img , int startX,int startY,int endX,int endY){
        Rect roi = new Rect(startX,startY,endX,endY);
        Mat croped =new Mat(img,roi);
        //pre_process.srsc(croped);
        if (StartCheck.PaperType == 2){

            /*org.opencv.core.Point point = new org.opencv.core.Point(img.height()/2,img.width()/2);
            Mat ro = Imgproc.getRotationMatrix2D(point,-90,1.0);
            Imgproc.warpAffine(img,dst ,ro, new org.opencv.core.Size(720,1280));*/
            Mat dst = new Mat(croped.height(),croped.width(),3);
            Core.transpose(croped,dst);
            Core.flip(dst, dst, 1);
            croped = dst;
            gt.setImgcrop(croped);
            Highgui.imwrite(Environment.getExternalStorageDirectory()+"/DCIM/CameraSnap/opnecvRotate"+Tutorial3View.sum+".jpg",dst);
        }else {
            gt.setImgcrop(croped);
        }
        Highgui.imwrite(Environment.getExternalStorageDirectory()+"/DCIM/CameraSnap/opnecvCrop"+Tutorial3View.sum+".jpg",croped);
        Mat img1 = croped.clone();

        return img1;
    }
    protected void sevecsv(String sFileName, String sCode,String sPoint){
        try
        {
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);

            writer.write(sCode+","+ sPoint);
            writer.flush();
            writer.close();

        }
        catch(IOException e)
        {
            e.printStackTrace();

        }
    }
    protected void ShowF (){
        CheckAns an = new CheckAns();
        String a = CheckPin.Ids.toString();
        String b = CheckSubject.IdSubject.toString();
        int CountPin = a.length();
        int CountSubj = b.length();
        Log.e("count pin and codesubj",Integer.toString(CountPin)+" "+Integer.toString(CountSubj));

        if (pre_process.chdt ==true&& CheckPin.chPin == true&& (CountPin ==8 && CountSubj==6)) {
            if (pre_process.paperans == true) {
                Tutorial3Activity.showtoast("เก็บข้อมูลกระดาษคำตอบเรียบร้อย");
            } else {

                an.checkans();
                Tutorial3Activity.showtoast("รหัสวิชา"+CheckSubject.IdSubject+"  รหัส " + CheckPin.Ids + " ได้ " + Integer.toString(CheckAns.point) + "คะแนน");
                String c = new String("รหัส " + CheckPin.Ids +"ข้อสอบชุดที่"+Integer.toString(ProcessGroup.Group)+" ได้ " + Integer.toString(CheckAns.point) + "คะแนน");
                sCode.add(CheckPin.Ids );sPoint.add(CheckAns.point);

                total.add(c);
                Log.e("Point Student  ", Integer.toString(CheckAns.point));
                SaveCsv(CheckSubject.IdSubject+"_"+CheckPin.Ids+"_"+Integer.toString(ProcessGroup.Group)+".txt");
            }
        }else{
            Tutorial3Activity.showtoast("เงื่อนไขผิดพลาด กรุณาถ่ายภาพใหม่");
        }

    }

    protected boolean CheckIdStud (){
        String a = new String(idstud);
        String b = new String(CheckPin.Ids);
        boolean ch = a.equals(b);
        if (ch == true){
            Log.e("Check IdStud :","false");
            return false;
        }else {
            Log.e("Check IdStud :","true");
            return  true;
        }

    }
    public void SaveCsv(String sFileName){

        try
        {
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            for (int i = 1 ;i<101;i++ ){
                writer.write(Integer.toString(i)+", "+CheckChoice.ChoiceNew[i][0]);
                writer.append("\n");
            }



            writer.flush();
            writer.close();

        }
        catch(IOException e)
        {
            e.printStackTrace();

        }
    }


}
