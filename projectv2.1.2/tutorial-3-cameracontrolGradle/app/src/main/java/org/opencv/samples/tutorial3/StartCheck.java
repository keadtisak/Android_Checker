package org.opencv.samples.tutorial3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;

/**
 * Created by CaptainCom on 6/16/2015.
 */
public class StartCheck extends Activity implements OnClickListener {
    String [] spiner = {"5","10","15","20","25","30","35","40","45","50","55","60","65","70","75","80","85","90","95","100"};

    String [] spinerGroup = {"1","2","3"};
    TextView TextView2;
    public static int PaperType;
    public static int NumberPaper;
    static Activity thisAc = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        thisAc = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        findViewById(R.id.startCheck_button).setOnClickListener(this);

        TextView2 = (TextView)findViewById(R.id.textView1);
        final Spinner spin = (Spinner)findViewById(R.id.spinnerChoice);
        //spin.setOnItemSelectedListener(this);

        ArrayAdapter<String> arrad = new ArrayAdapter<String>( this,android.R.layout.simple_spinner_item,spiner);
        //arrad.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spin.setAdapter(arrad);
        spin.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {

                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        TextView2.setText("Your Select :"+spiner[position]);
                        //TextView2.setText(spinerGroup[position]);
                        String a = spiner[position];
                        NumberPaper = position*5+5;

                    }


                    public void onNothingSelected(AdapterView<?> parent) {
                        NumberPaper = Integer.parseInt(spiner[0]);
                    }
                }
        );




        Spinner spinG = (Spinner)findViewById(R.id.spinnerGroup);


        ArrayAdapter arrGroup = new ArrayAdapter( StartCheck.this,android.R.layout.simple_spinner_item,spinerGroup);
        arrGroup.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinG.setAdapter(arrGroup);
        spinG.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        PaperType = position;

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        PaperType = 1;

                    }
                }
        );

    }


    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.startCheck_button) {

            Intent intent = new Intent(StartCheck.this,Tutorial3Activity.class);
            startActivity(intent);

        }

    }

 /*    @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // String mas = "";
        // mas = spiner[position].toString() ;
//         mas =mas+];

        //TextView2.setText(mas.toString());
        //TextView2.setText(spinerGroup[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //TextView2.setText("");
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub

        MenuInflater myMenuInflater = getMenuInflater();
        myMenuInflater.inflate(R.layout.mymenu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        switch (item.getItemId()) {
            case R.id.tutorial3_activity_java_surface_view:

                Intent gotutorial3 = new Intent(getApplicationContext(),
                        Tutorial3Activity.class);
                startActivity(gotutorial3);

                return true;



            case R.id.result:

                Intent goResult = new Intent(getApplicationContext(),
                        Result.class);
                startActivity(goResult);

                return true;

            case R.id.paper:

                Intent goPaper = new Intent(getApplicationContext(),
                        Paper.class);
                startActivity(goPaper);

                return true;

            case R.id.help:

                Intent goHelp = new Intent(getApplicationContext(),
                        help.class);
                startActivity(goHelp);

                return true;
            case R.id.about:

                Intent goAbout = new Intent(getApplicationContext(),
                        About.class);
                startActivity(goAbout);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }



}
