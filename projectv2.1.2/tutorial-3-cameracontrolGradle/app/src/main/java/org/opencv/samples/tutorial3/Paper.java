package org.opencv.samples.tutorial3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Created by phonphilai on 5/29/2015.
 */
public class Paper extends Activity implements OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paper);
        findViewById(R.id.share_button).setOnClickListener(this);

    }
        @Override
        public void onClick(View v) {
            if (v.getId()== R.id.share_button) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "https://www.dropbox.com/s/fnz0wrkusgwbb2y/%E0%B8%81%E0%B8%A3%E0%B8%B0%E0%B8%94%E0%B8%B2%E0%B8%A9%E0%B8%84%E0%B8%B3%E0%B8%95%E0%B8%AD%E0%B8%9A.pdf?dl=0");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Marking Answer Sheets");
                startActivity(Intent.createChooser(intent, "Share"));
            }

        }



}

