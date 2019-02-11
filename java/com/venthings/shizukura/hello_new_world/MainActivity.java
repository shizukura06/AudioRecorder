package com.venthings.shizukura.hello_new_world;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    MediaRecorder recorder;
    MediaPlayer player;
    Button recordBtn, playBtn;
    String outputFie,extName;
    int i = 1;

    File directory = new File(Environment.getExternalStorageDirectory()+"/Audio Records/Records/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        outputFie = "/Recorded Audio";
        extName = ".wav";

        recordBtn = findViewById(R.id.recordBtn);
        playBtn = findViewById(R.id.playBtn);
        recordBtn.setText("RECORD");
        playBtn.setText("PLAY");

        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (recordBtn.getText() == "RECORD") {
                        recordBtn.setText("RECORDING");
                        directory.mkdirs();

                        if(recorder != null){
                            recorder.release();
                        }
                        File newFile = new File(directory+outputFie+i+extName); //Storage/Audio Records/Records/Recorded Audio955.wav
                        while(newFile.exists()){
                            i = i+1;
                            newFile = new File(directory+outputFie+i+extName);
                        }
                        recorder = new MediaRecorder();
                        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
                        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                        recorder.setOutputFile(directory+outputFie+i+extName);
                        try {
                            recorder.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        recorder.start();
                        Toast.makeText(MainActivity.this,"Recording...",Toast.LENGTH_SHORT).show();
                    }
                    else {
                            recordBtn.setText("RECORD");
                        if(recorder != null){
                            recorder.stop();
                            Toast.makeText(MainActivity.this,"The audio file is saved to "+directory+outputFie+i+extName,Toast.LENGTH_LONG).show();
                        }
                    }
            }
        });

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (playBtn.getText() == "PLAY") {
                        playBtn.setText("PLAYING");
                        if(player != null){
                            player.release();
                        }
                        player = new MediaPlayer();
                        player.setDataSource(directory+outputFie+i+extName);
                        player.prepare();
                        player.start();

                    } else {
                        playBtn.setText("PLAY");
                        if(player != null){
                            player.stop();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
