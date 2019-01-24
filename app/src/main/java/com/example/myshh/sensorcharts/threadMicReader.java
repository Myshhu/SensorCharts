package com.example.myshh.sensorcharts;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;

public class threadMicReader extends Thread {

    private byte[] soundDataBuffer;
    private double[] maxSoundValue;
    private AudioTrack audioTrack;
    private BooleanHolder isSpeakerOutputEnabled;

    threadMicReader(double[] maxSoundValue, BooleanHolder isSpeakerOutputEnabled) {
        this.maxSoundValue = maxSoundValue;
        this.isSpeakerOutputEnabled = isSpeakerOutputEnabled;
        soundDataBuffer = new byte[1400];
    }

    public void run(){
        AudioRecord recorder = createRecorder();
        recorder.startRecording();

        double sum = -128;

        while (true){
            recorder.read(soundDataBuffer, 0, soundDataBuffer.length);

            //Get max sound value from buffer
            for(int i = 0; i * 2 + 1 < soundDataBuffer.length; i++) {
                sum = Math.max(soundDataBuffer[i * 2 + 1], sum);
            }
            if(sum > 0 && sum < 30)
                sum*=2;
            else if(sum >= 30 && sum < 50)
                sum*=1.5;
            maxSoundValue[0] = sum;
            sum = -128;

            if(isSpeakerOutputEnabled.isBooleanValue())
            {
                new Thread(() -> toSpeaker(soundDataBuffer, audioTrack)).start();
            }
        }
    }

    private AudioRecord createRecorder(){
        int sampleRate = 16000;
        int channelConfig = AudioFormat.CHANNEL_IN_MONO;
        int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
        int minBufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat);
        this.audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                minBufferSize,    //buffer length in bytes
                AudioTrack.MODE_STREAM);
        return new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, channelConfig, audioFormat, minBufferSize);
    }

    //Send sound buffer to speaker
    private static void toSpeaker(byte musicBytes[], AudioTrack audioTrack) {
        try
        {
            audioTrack.write(musicBytes, 0, musicBytes.length);
            audioTrack.play();
        } catch (Exception e) {
            System.out.println("Not working in speakers");
            e.printStackTrace();
        }
    }
}
