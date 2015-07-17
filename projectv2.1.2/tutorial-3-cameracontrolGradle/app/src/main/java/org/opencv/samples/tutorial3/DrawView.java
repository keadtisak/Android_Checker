package org.opencv.samples.tutorial3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by CaptainCom on 5/4/2015.
 */
public class DrawView extends View {
    Paint paint = new Paint();

    public DrawView(Context context) {
        super(context);
        paint.setColor(Color.RED);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawLine(0, 0, 200, 200, paint);
        canvas.drawLine(200, 0, 0, 200, paint);
    }


}
