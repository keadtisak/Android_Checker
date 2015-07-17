package org.opencv.samples.tutorial3;

import android.util.Log;

/**
 * Created by CaptainCom on 5/31/2015.
 */
public class CheckAns {
    static int point = 0;
    public void checkans() {

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 25; j++) {
                //System.out.println(checkpoint.choice[i][j][0]+" "+arr[i][j][0]);
                if (CheckChoice.AnsChoice[i][j][0] != 0 && CheckChoice.choice[i][j][0] != 0) {

                    int count=0;
                    int sum=0;
                    while (true) {
                        if (CheckChoice.AnsChoice[i][j][count] == CheckChoice.choice[i][j][count]) {
                            //System.out.println(ans[i][j][0]+" "+arr[i][j][0]);
                            //Log.e("CheckAns",Integer.toString(i)+" "+Integer.toString(j)+"Checked");

                            //point++;
                            sum++;
                        } else {
                            Log.e("CheckAns", Integer.toString(i) + " " + Integer.toString(j) + " wrong : "
                            +Integer.toString(CheckChoice.AnsChoice[i][j][count])+ "!=" +Integer.toString(CheckChoice.choice[i][j][count]));
                        }
                        count++;
                        if(CheckChoice.AnsChoice[i][j][count]== 0&&CheckChoice.choice[i][j][count]==0){
                            break;
                        }


                    }
                    if(count == sum ){
                        point++;
                    }
                }


            }


        }

    }

}
