package byteshaft.com.recorder;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.VideoView;

import java.util.ArrayList;

public class CustomVideoView extends VideoView implements MediaPlayer.OnPreparedListener {

    static final int PLAYING = 1;
    static final int PAUSED = 0;

    private Uri currentlyPlayingVideoUri = null;
    private ArrayList<MediaPlayerStateChangedListener> mListeners = new ArrayList<>();
    private int mVideoHeight = 0;
    private int mVideoWidth = 0;

    public CustomVideoView(Context context) {
        super(context);
        setOnPreparedListener(this);
    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnPreparedListener(this);
    }

    public void setMediaPlayerStateChangedListener(MediaPlayerStateChangedListener listener) {
        mListeners.add(listener);
    }

    @Override
    public void start() {
        super.start();
        setKeepScreenOn(true);
        for (MediaPlayerStateChangedListener listener : mListeners) {
            listener.onPlaybackStateChanged(1);
        }
    }

    @Override
    public void pause() {
        super.pause();
        setKeepScreenOn(false);
        for (MediaPlayerStateChangedListener listener : mListeners) {
            listener.onPlaybackStateChanged(0);
        }
    }

    @Override
    public void stopPlayback() {
        super.stopPlayback();
        currentlyPlayingVideoUri = null;
    }

    @Override
    public void setVideoURI(Uri uri) {
        super.setVideoURI(uri);
        currentlyPlayingVideoUri = uri;
    }

    public Uri getVideoURI() {
        return currentlyPlayingVideoUri;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mVideoHeight = mp.getVideoHeight();
        mVideoWidth = mp.getVideoWidth();
    }

    public int getVideoHeight() {
        return mVideoHeight;
    }

    public int getVideoWidth() {
        return mVideoWidth;
    }

    public interface MediaPlayerStateChangedListener {
        public void onPlaybackStateChanged(int state);
    }
}
