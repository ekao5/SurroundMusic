package com.example.rachel.audiocapture;

import java.io.File;
import java.io.IOException;
//import java.util.logging.Handler;
import java.util.logging.LogRecord;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.os.Handler;

import android.util.Log;
//import com.example.rachel.audiocapture.R.id.*;

//import com.example.rachel.audiocapture.R.*;







public class MainActivity extends Activity {



    private MediaRecorder myAudioRecorder;
    private String outputFile = null;
    private Button start,stop,play,syncPlay,stopSyncPlay,restart;
    public boolean runSync=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        start = (Button)findViewById(R.id.button1);
        stop = (Button)findViewById(R.id.button2);
        play = (Button)findViewById(R.id.button3);
        syncPlay=(Button)findViewById(R.id.button4);
        stopSyncPlay=(Button)findViewById(R.id.button5);
        restart=(Button)findViewById(R.id.button6);

        stop.setEnabled(false);
        play.setEnabled(false);
        stopSyncPlay.setEnabled(false);
        outputFile = Environment.getExternalStorageDirectory().
                getAbsolutePath() + "/myrecording.3gp";

        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);

    }

    public void restart(View view){

       startActivity(new Intent(MainActivity.this,MainActivity.class));
    }

    public void start(View view){
        try {
            myAudioRecorder.prepare();
            myAudioRecorder.start();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        start.setEnabled(false);
        stop.setEnabled(true);
        syncPlay.setEnabled(false);
        Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();

    }

    public void stop(View view){
        myAudioRecorder.stop();
        myAudioRecorder.release();
        myAudioRecorder  = null;
        stop.setEnabled(false);
        play.setEnabled(true);
        Toast.makeText(getApplicationContext(), "Audio recorded successfully",
                Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void play(View view) throws IllegalArgumentException,
            SecurityException, IllegalStateException, IOException{

        MediaPlayer m = new MediaPlayer();
        m.setDataSource(outputFile);
        m.prepare();
        m.start();
        Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();
    }

public Runnable myRunnable=new Runnable() {
    @Override
    public void run() {


    }
};


    public void syncPlay(View view) throws IllegalArgumentException,SecurityException, IllegalStateException, IOException
    {
        stopSyncPlay.setEnabled(true);

        long startTime=System.currentTimeMillis();
        start(view);


        //Handler myHandler = new Handler();
        //myHandler.postDelayed(myRunnable,3000);

       if(System.currentTimeMillis()-startTime>3000)
       {stop(view);play(view);}


        if(System.currentTimeMillis()-startTime>20000){restart(view);syncPlay(view);}}






//        while(runSync){
//
//            if(System.currentTimeMillis()-startTime==5000)
//            {
//                stop(view);
//                play(view);
//            }
//
//                if(System.currentTimeMillis()-startTime==10000)
//                {
//                    restart(view);
//                    startTime+=10000;
//                    start(view);
//                    //syncPlay(view);
//
//             }
//
//            }
                //restart(view);







    public void stopSyncPlay(View view){
        runSync=false;
    }

}
