package org.opencv.samples.tutorial3;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CaptainCom on 5/7/2015.
 */
public class gtdata {
    private int[] SumRow ;
    private List SumCol;
    private Mat imgcrop;
    private Mat imgmorfo;
    private  boolean check;
    private List<String> SumPoint = new ArrayList<>();
    public int[] getSumRow(){
        return this.SumRow;
    }

    public void setSumRow(int []sumRow){

        this.SumRow = sumRow;
    }
    public List getSumCol(){
        return this.SumCol;
    }

    public void setSumCol(List sumCol){

        this.SumCol = sumCol;
    }
    public Mat getimgcrop(){
        return this.imgcrop;
    }
    public  void setImgcrop(Mat crop_img){
        this.imgcrop = crop_img;
    }
    public Mat getimgmorfo(){
        return  this.imgmorfo;
    }
    public void setImgmorfo(Mat image_morfo){
        this.imgmorfo =image_morfo;
    }
    public boolean getcheckadta(){
        return this.check;
    }
    public void setCheckData(boolean check){
        this.check = check;
    }

    public List getSumPoint (){
        return  this.SumPoint;
    }
    public void setSumPoint(String point){
        String s = new String(point);
        this.SumPoint.add(s);

    }
}
