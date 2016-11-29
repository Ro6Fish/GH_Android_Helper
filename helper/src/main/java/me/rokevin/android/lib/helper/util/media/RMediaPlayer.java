package me.rokevin.android.lib.helper.util.media;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by rokevin on 16/8/5.
 * <p/>
 * 播放帮助类
 */
public class RMediaPlayer {

    private MediaPlayer mMediaPlayer;

    public void start() {

        mMediaPlayer = new MediaPlayer();
        String url = "file:///sdcard/my/voice.amr";
        try {
            //模拟器里播放传url，真机播放传getAmrPath()
            mMediaPlayer.setDataSource(url);
            //mMediaPlayer.setDataSource(getAmrPath());
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            //设置播放结束时监听
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {

                    if (mOnMediaPlayFinishListener != null) {
                        mOnMediaPlayFinishListener.onFinish();
                    }
                }
            });
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void stop() {

        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public OnMediaPlayFinishListener mOnMediaPlayFinishListener;

    public void setOnMediaPlayFinishListener(OnMediaPlayFinishListener onMediaPlayFinishListener) {
        mOnMediaPlayFinishListener = onMediaPlayFinishListener;
    }

    public interface OnMediaPlayFinishListener {
        void onFinish();
    }
}
