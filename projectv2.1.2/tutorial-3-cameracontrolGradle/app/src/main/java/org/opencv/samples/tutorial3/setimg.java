package org.opencv.samples.tutorial3;

import org.opencv.core.Mat;

/**
 * Created by CaptainCom on 6/16/2015.
 */
public class setimg {
    private Mat ThImage;

    public void setThImage(Mat Image){
        this.ThImage = Image;
    }
    public Mat getThImage(){
        return this.ThImage ;
    }
}
