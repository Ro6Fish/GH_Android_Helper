package me.rokevin.android.lib.helper.util.media;

import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.IOException;

/**
 * Created by rokevin on 16/8/5.
 * <p>
 * 录音帮助类
 */
public class RRecorder implements Runnable {

    private String mFilePath;

    private static int MAX_TIME = 30;    //最长录制时间，单位秒，0为无时间限制
    private static int MIX_TIME = 1;     //最短录制时间，单位秒，0为无时间限制，建议设为1

    private static int STATE = 0;      //录音的状态
    private static int IDEL = 0;  //不在录音
    private static int RECORDING = 1;   //正在录音

    private static float recodeTime = 0.0f;    //录音的时间
    private static double voiceValue = 0.0;    //麦克风获取的音量值

    private boolean isAlive = false;

    private MediaRecorder mRecorder;

    public RRecorder(String path) {
        mFilePath = path;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);

            double ratio = getAmplitude() / 100;
            double db = 0;// 分贝

            //默认的最大音量是100,可以修改，但其实默认的，在测试过程中就有不错的表现
            //你可以传自定义的数字进去，但需要在一定的范围内，比如0-200，就需要在xml文件中配置maxVolume
            //同时，也可以配置灵敏度sensibility
            if (ratio > 1) {
                db = 20 * Math.log10(ratio);
            }
            //只要有一个线程，不断调用这个方法，就可以使波形变化
            //主要，这个方法必须在ui线程中调用

            //RLog.e(TAG, "分贝:" + db);
            // todocVoiceRect.setVolume((int) (db));

            if (mOnAmplitudeListener != null) {
                mOnAmplitudeListener.onAmplitude(db);
            }
        }
    };

    @Override
    public void run() {

        while (isAlive) {

            handler.sendEmptyMessage(0);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取当前录音状态
     *
     * @return
     */
    public int getStatus() {
        return STATE;
    }

    /**
     * 开始录制
     */
    public void start() {

        isAlive = true;

        Thread thread = new Thread(this);
        thread.start();

        if (STATE == IDEL) {

            STATE = RECORDING;
            try {
                recordStart();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // mRecorder.start();

        } else {

            try {
                recordStop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 停止录制
     */
    public void stop() {

        isAlive = false;

        STATE = IDEL;

        try {
            recordStop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private OnAmplitudeListener mOnAmplitudeListener;

    public void setOnAmplitudeListener(OnAmplitudeListener onAmplitudeListener) {
        mOnAmplitudeListener = onAmplitudeListener;
    }

    /**
     * 录音音量监听
     */
    public interface OnAmplitudeListener {

        void onAmplitude(double db);
    }

    private static int SAMPLE_RATE_IN_HZ = 8000;

    // 设置音频的录制的声道CHANNEL_IN_STEREO为双声道，CHANNEL_CONFIGURATION_MONO为单声道
    private static int channelConfig = AudioFormat.CHANNEL_CONFIGURATION_MONO;

    // 音频数据格式:PCM 16位每个样本。保证设备支持。PCM 8位每个样本。不一定能得到设备支持。
    private static int audioFormat = AudioFormat.ENCODING_PCM_16BIT;

    public void recordStart() throws IOException {

        String state = Environment.getExternalStorageState();

        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            throw new IOException("SD Card is not mounted,It is  " + state + ".");
        }
        File directory = new File(mFilePath).getParentFile();
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Path to file could not be created");
        }

        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
        }

        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mRecorder.setAudioSamplingRate(SAMPLE_RATE_IN_HZ);

        //mRecorder.setAudioSamplingRate();
        //AudioTrack.getMinBufferSize(SAMPLE_RATE_IN_HZ, channelConfig, audioFormat);

        mRecorder.setOutputFile(mFilePath);
        mRecorder.prepare();
        mRecorder.start();
    }

    public void recordStop() throws IOException {

        if (mRecorder != null && isAlive) {

            mRecorder.stop();
            mRecorder.reset();
            mRecorder.release();
        }

        mRecorder = null;
    }

    /**
     * 获取分贝
     *
     * @return
     */
    public double getAmplitude() {

        if (mRecorder != null) {
            return (mRecorder.getMaxAmplitude());
        } else {
            return 0;
        }
    }
}
