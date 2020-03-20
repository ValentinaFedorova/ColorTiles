package com.example.colortiles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class MyView extends View {

    Context context;

    Paint p = new Paint();
    MyRect rects [] = new MyRect[16];
    int width,height;
    class MyRect{
        int id,color,row,column;



        Random r = new Random();

        public float[] getCoordinations(int row,int column){
            float [] coords  = {column * (width / 4) + (width / 4) / 10, row * (height / 4) + (height / 4) / 10, column * (width / 4) + width / 4 - (width / 4) / 10, row * (height / 4) + height / 4 - (height / 4) / 10};
            return coords;
        }


        public void DrawRect(Canvas canvas){
            float left,top,right,bottom;
            float [] coord = getCoordinations(row,column);

            left = coord[0];
            top = coord[1];
            right = coord[2];
            bottom = coord[3];
            if (color==0){
                p.setColor(Color.GRAY);
            }
            else {
                p.setColor(Color.LTGRAY);
            }
            canvas.drawRect(left,top,right,bottom,p);
        }

    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Random r = new Random();

        int id = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                MyRect newrect = new MyRect();
                newrect.id = id;
                if (r.nextInt() % 2 == 0) {
                    newrect.color = 0;
                } else {
                    newrect.color = 1;
                }
                newrect.column = j;
                newrect.row = i;

                rects[id] = newrect;
                id++;


            }

        }
    }

    public MyView(Context context) {
        super(context);
    }

    float x, y;

    @Override
    public boolean onTouchEvent (MotionEvent event){
        x = event.getX();
        y = event.getY();
        float [] coordinates = new float[4];
        for (int i = 0; i < rects.length; i++) {
            coordinates = rects[i].getCoordinations(rects[i].row,rects[i].column);
            if (x >= coordinates[0] & x <= coordinates[2] & y >= coordinates[1] & y <= coordinates[3]) {
                for (int j = 0; j < rects.length; j++) {
                    if (rects[j].row==rects[i].row || rects[j].column == rects[i].column){
                        if (rects[j].color==0){
                            rects[j].color = 1;
                        }
                        else {
                            rects[j].color = 0;
                        }
                    }
                }
                break;

            }

        }
        int color = rects[0].color;
        int sum=0;
        for (int i = 1; i < rects.length ; i++) {
            if (rects[i].color == color) {
                sum += 1;
            }
        }

            if (sum==15){
                Toast toast = Toast.makeText(context,
                        "Вы выиграли!", Toast.LENGTH_SHORT);
                toast.show();
            }
            
        invalidate();
        //return true;
        return super.onTouchEvent(event);
    }


    @Override
    protected void onDraw (Canvas canvas){

        width = canvas.getWidth();
        height = canvas.getHeight();

        for (int i = 0; i < rects.length; i++) {
            rects[i].DrawRect(canvas);
        }

                //canvas.drawRect(j * (w / 4) + (w / 4) / 10, i * (h / 4) + (h / 4) / 10, j * (w / 4) + w / 4 - (w / 4) / 10, i * (h / 4) + h / 4 - (h / 4) / 10, p);



    }
}

