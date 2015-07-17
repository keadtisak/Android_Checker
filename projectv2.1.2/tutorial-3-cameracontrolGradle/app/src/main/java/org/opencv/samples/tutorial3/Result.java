package org.opencv.samples.tutorial3;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;

/**
 * Created by phonphilai on 5/29/2015.
 */
public class Result extends Activity {
    static final String [] point = new String[]{
            "55023241   10","55023454   10","55023599  9"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        gtdata gt = new gtdata();
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        ListView list = (ListView)findViewById(R.id.list_of_point);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_list_item_1,covert.total
        );
        sevecsv("TotalPoint.txt");

        list.setAdapter(adapter);
       list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

           }
       });



    }
    public void sevecsv(String sFileName){

        try
        {
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            for (int i = 0 ;i<covert.sPoint.size();i++ ){
                writer.write(covert.sCode.get(i)+","+covert.sPoint.get(i));
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