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
    Boolean chosen=false,start=false;

    final static int[] imagelist={R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4,R.drawable.p5,R.drawable.p6,
            R.drawable.p7,R.drawable.p8,R.drawable.p9,R.drawable.p10,R.drawable.p11,R.drawable.p12,R.drawable.p13,
    R.drawable.p14,R.drawable.p14,R.drawable.p15,R.drawable.p16,R.drawable.p17};

    String [] name = {"강다니엘","김사무엘","김재환","김종현","라이관린","황민현","이우진","이대휘","이건휘 ","유선호","옹성우","박성우","박지훈",
    "하성운 ","켄타","이의웅","권현빈 "};
    TextView t1;
    myTasks task1;

    private int result = 0;
    private int namenum = 0;
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
                //사진이 선택이 되어버렸다
                Log.d("chosen",String.valueOf(chosen));
                Log.d("start",String.valueOf(start));
                if(chosen){
                    if(start==true){
                        t1.setText(name[namenum%17]+"를 선택!("+result+")초");
                        start = false;
                    }
                    task1.cancel(true);
                    chosen = false;
                    Log.d("start1",String.valueOf(start));

                }else{
                    task1 = new myTasks();
                    String time = e1.getText().toString();
                    task1.execute(Integer.parseInt(time));
                    chosen = true;
                    start = true;
                }
            }
        });


    }

    public void onClick(View v){
        if(v.getId()==R.id.init){
//            startActivity(new Intent(MainActivity.this,MainActivity.class));
//            finish();
            image.setImageResource(R.drawable.start);
            t1.setText("");
            t1.setVisibility(View.INVISIBLE);
            e1.setText("");
            result = 0;
            Log.d("chosen",String.valueOf(chosen));
            Log.d("start",String.valueOf(start));

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
                    result = i;
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
                namenum = values[1];
                t1.setText("시작부터 "+values[0]+"초");
                image.setImageResource(imagelist[values[1]%17]);

            }


        }

        //
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
