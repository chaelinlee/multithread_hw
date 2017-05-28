package com.example.leechaelin.multithread_hw;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText e1;
    ImageView image;

    final static int[] imagelist={R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4,R.drawable.p5,R.drawable.p6,
            R.drawable.p7,R.drawable.p8,R.drawable.p9,R.drawable.p10,R.drawable.p11,R.drawable.p12,R.drawable.p13};
    TextView t1;
    myTasks task1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = (EditText)findViewById(R.id.edittext);
        image = (ImageView)findViewById(R.id.image);
        t1 = (TextView)findViewById(R.id.textview);
        image.setImageResource(R.drawable.start);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(task1==null) {
                    task1 = new myTasks();
                    String time = e1.getText().toString();
                    task1.execute(Integer.parseInt(time));
                }
            }
        });


    }

    public void onClick(View v){
        if(v.getId()==R.id.init){
            startActivity(new Intent(MainActivity.this,MainActivity.class));
        }
    }
    class myTasks extends AsyncTask<Integer,Integer,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            t1.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Integer... params) {
            Log.d("par",params[0]+"");
            int j=0;
            for(int i=1;i<101;i++){
                if(isCancelled()==true) return null;
                try{
                    Thread.sleep(1000);
                    //그림을 바꾸어야한다
                    if(i%params[0]==0){
                        publishProgress(i,j++);
                    }
                    //
                    else{
                        publishProgress(i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if(values.length==1){
                //초만 바꿔준다
                t1.setText("시작부터 "+values[0]+"초");
            }else{
                t1.setText("시작부터 "+values[0]+"초");
                image.setImageResource(imagelist[values[1]%13]);

            }


        }

        //
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            t1.setText("선택이 완료");

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
