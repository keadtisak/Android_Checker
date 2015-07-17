package HorProcess;

import android.os.Environment;
import android.util.Log;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.samples.tutorial3.CheckPin;
import org.opencv.samples.tutorial3.ProcessCodeSubj;
import org.opencv.samples.tutorial3.ProcessPin;
import org.opencv.samples.tutorial3.Tutorial3Activity;
import org.opencv.samples.tutorial3.Tutorial3View;
import org.opencv.samples.tutorial3.gtdata;
import org.opencv.samples.tutorial3.pre_process;
import org.opencv.samples.tutorial3.process;
import org.opencv.samples.tutorial3.setimg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by CaptainCom on 7/8/2015.
 */
public class HPreProcess {
    static gtdata gt = new gtdata();
    static Tutorial3Activity ac = new Tutorial3Activity();
    static setimg se = new setimg();

    static process pro;
    static ProcessPin ppi ;
    public static boolean paperans;

    public static void horPreProcess(Mat img){
        Log.e("HorProcess","pass");
        String a = new String ("00000000");
        Mat image = new Mat(img.height(),img.width(), CvType.CV_8UC4);
        img.copyTo(image);

        HPreProcess.morphology(img);
        Highgui.imwrite(Environment.getExternalStorageDirectory() + "/DCIM/CameraSnap/opnecvmofo" + Tutorial3View.sum + ".jpg", img);
        gt.setImgmorfo(img);
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
        //Log.i("SumCols =", Arrays.toString(sumCols));
        //Log.i("SumRows = ",Arrays.toString(sumRows));
        writetofile(sumCols);
        writetofile(sumRows);

        int[] Row = new int[8];
        int[] q1 = {1, 3, 5, 7};
        int[] q2 = {5, 11, 17, 23};
        List<Integer> lisr = new ArrayList<>();
        List<Integer> lisc = new ArrayList<>();




        lisr = setPointRow(sumCols);
        lisc = setPointCol(sumRows);

        for (int o = 0; o < lisc.size(); o++) {
            Log.i("Rows  ", Integer.toString(lisc.get(o)));
        }
        Row = CutRow(lisr);

        Log.i("Rows  ", Arrays.toString(Row));
        pre_process.chdt = checkimg(Row, lisc.size()); // ส่งค่าrow col  ไปตรวจสอบ
        gt.setSumRow(Row);
        gt.setSumCol(lisc);

        int count = 1;
        if (pre_process.chdt == true) {
            Log.e("Check Crop ", " pass");
            ppi = new ProcessPin();
            condition = ppi.processPin(img, lisc.get(0), lisr.get(10),lisc.get(11)-lisc.get(0));
            ProcessCodeSubj subj = new ProcessCodeSubj();
            conditionf = subj.processCodeSubj(img,lisr.get(10),lisc.get(0)-5, lisr.get(21)-lisr.get(9),lisc.get(11)-lisc.get(0)+10);

            String b = new String(CheckPin.Ids);
            if(condition ==true && conditionf == true) {
                Log.e("Check condition pin ", " pass");
                boolean p =a.equals(b);
                if(p==true){
                    Log.e("Checkid ","true");
                    pre_process.paperans = true;

                }
                else{
                    pre_process.paperans = false;
                    Log.e("Checkid ","false");
                }
                for (int k = 0; k < Row.length; k++) {//ตัดช่องกระดาษคำตอบ
                    if (k % 2 == 0) {
                        //crop2(image, Row[k], lisc.get(0), Row[1] - Row[0] + 2, lisc.get(lisc.size() - 1) - lisc.get(0) + 5, count);
                        crop(img, Row[k]+3, lisc.get(12), Row[3] - Row[2]+3  , lisc.get(lisc.size() - 1) - lisc.get(12)+5 , count);
                        count++;

                    }

                }
            }


        }
    }

    protected static Mat morphology(Mat img){
        // Mat kernel = new MatOfInt(1,0,1,0,1,0,1,0,1);
        //Imgproc.erode(img,img,Imgproc.getStructuringElement(Imgproc.MORPH_RECT,new Size(4,4)));
        //Imgproc.erode(img,img,Imgproc.getStructuringElement(Imgproc.MORPH_RECT,new Size(3,4),new Point(2,2)));

        Mat knl = Mat.zeros(5,5, CvType.CV_8U);
        knl.put(0,0,1);
        knl.put(0,4,1);
        knl.put(1,1,1);
        knl.put(1,3,1);
        knl.put(2,2,1);
        knl.put(3,1,1);
        knl.put(3,3,1);
        knl.put(4,0,1);
        knl.put(4,4,1);
        Mat knl3 = Mat.zeros(7,7, CvType.CV_8U);
        knl3.put(0,0,1);
        knl3.put(0,6,1);
        knl3.put(1,1,1);
        knl3.put(1,5,1);
        knl3.put(2,2,1);
        knl3.put(2,4,1);
        knl3.put(3,3,1);
        knl3.put(4,3,1);
        knl3.put(4,5,1);
        knl3.put(5,1,1);
        knl3.put(5,4,1);
        knl3.put(6,0,1);
        knl3.put(6,6,1);
        Mat knl2 = Mat.zeros(3,3, CvType.CV_8U);
        knl2.put(0,0,1);
        knl2.put(0,2,1);
        knl2.put(1,1,1);
        knl2.put(2,0,1);
        knl2.put(2,2,1);
        Mat knl4 = Mat.zeros(3,3, CvType.CV_8U);
        knl4.put(0,1,1);
        knl4.put(1,0,1);
        knl4.put(1,2,1);
        knl4.put(2,1,1);


        Imgproc.erode(img, img, knl3);
        Imgproc.dilate(img,img,knl2);
        //Imgproc.dilate(img,img,knl4);
        //Imgproc.dilate(img,img,Imgproc.getStructuringElement(Imgproc.MORPH_RECT,new Size(3,3)));
        return img;
    }
    protected static List<Integer>  setPointRow(int[] sumrow){
        List<Integer> lisr = new ArrayList<>();
        int count = 0, countrow = 0, count0 = 0;
        for (int i = 0; i < sumrow.length; i++) {
            if(sumrow[i] < 180000&&countrow>22){
                lisr.add(i);
                countrow =0;
            }else {
                countrow++;
            }
        }


        return  lisr;

    }
    protected static List<Integer>  setPointCol(int[] sumcol){
        List<Integer> lisc = new ArrayList<>();
        int count = 0, countrow = 0, count0 = 0;
        for (int i = 0; i < sumcol.length; i++) {
            if (sumcol[i] < 80000&&countrow>22) {
                lisc.add(i);
                countrow = 0;
            }else{
                countrow++;
            }
        }

        return lisc;
    }
    protected static int[] CutRow(List<Integer> lis){
        int[] cutrow = new int [8];
        int[] d ={0,5,6,11,12,17,18,23};
        int countcut1 =0;
        int s =0 ;

        for (int i = 0; i < lis.size(); i++) {
            if ( d[s]==i ){
                cutrow[countcut1] = lis.get(i);
                s++;
                countcut1++;
            }else{

            }
        }
        return cutrow;
    }
    public static void writetofile(int []a){
        System.out.println(a);
        try{

            FileOutputStream out = new FileOutputStream(new File(Environment.getExternalStorageDirectory().getPath()+"/tsxt.txt"));
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter( out);

            for (int i= 380;i< a.length;i++){
                System.out.printf(a[i]+" ,");
                outputStreamWriter.write(Integer.parseInt(String.valueOf(a[i])));
            }

            out.close();

        } catch (Exception e) { //fehlende Permission oder sd an pc gemountet}
            for (int i= 0;i< a.length;i++) {
                System.out.print(String.valueOf(a[i])+", ");
            }
        }

    }
    protected  static  boolean checkimg(int [] sizerow , int sizecol){// ตรวจสอบค่า row และ col ว่าได้ค่าครบหรือเปล่า
        int count = 0;
        for(int i =0;i<sizerow.length;i++){

            if (sizerow[i] >0){
                count++;
            }
        }
        if (count==8 && sizecol>=38 ){
            Log.e("Check data","true");

            gt.setCheckData(true);
            return true;

        }else{
            Log.e("Check data","Fail");
            gt.setCheckData(false);
            return false;

        }

    }
    public static  void  crop(Mat img , int startX,int startY,int endX,int endY,int i){
        Rect roi = new Rect(startX,startY,endX,endY);
        Mat croped =new Mat(img,roi);
        Highgui.imwrite(Environment.getExternalStorageDirectory() + "/DCIM/CameraSnap/CropRow" + Tutorial3View.sum +" "+i+ ".jpg", croped);
        pro = new process(croped,i);
        Mat img1 = croped.clone();
    }

    public static  void  crop2(Mat img , int startX,int startY,int endX,int endY,int i){
        //setimg se = new setimg();
        Rect roi = new Rect(startX,startY,endX,endY);
        Mat croped =new Mat(img,roi);
        Highgui.imwrite(Environment.getExternalStorageDirectory() + "/DCIM/CameraSnap/CropRow2" + Tutorial3View.sum +" "+i+ ".jpg", croped);
        se.setThImage(croped);
        Mat img1 = croped.clone();
    }



}