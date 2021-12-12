package org.aplas.animaltour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.ViewFlipper;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import java.util.Arrays;

public class MediaActivity extends AppCompatActivity {
    private TextView mediaTitle;
    private VideoView vid;
    private Button prevBtn, nextBtn;
    private ImageButton finishBtn, invertBtn;
    private ViewFlipper flipper;
    private GestureDetector detector;
    private YouTubePlayer youPlayer;
    private String[] listTitles, listYoutube, listVideo;
    private int[] listColor;
    private int currIndex = 0;
    private Animation titleAnimation, leftInAnimation, leftOutAnimation, rightInAnimation, rightOutAnimation;
    private static final String API_KEY = "AIzaSyBo5KRuoOVO4xkYIVMp6t2c5l22tkKLF4I";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        mediaTitle = (TextView) findViewById(R.id.mediaTitle);
        vid = (VideoView) findViewById(R.id.videoView);
        prevBtn = (Button) findViewById(R.id.prevButton);
        nextBtn = (Button) findViewById(R.id.nextButton);
        finishBtn = (ImageButton) findViewById(R.id.finishButton);
        invertBtn = (ImageButton) findViewById(R.id.invertButton);
        flipper = (ViewFlipper) findViewById(R.id.mediaFlipper);

        listTitles = getResources().getStringArray(R.array.animal_titles);
        listYoutube = getResources().getStringArray(R.array.animal_youtube);
        listVideo = getResources().getStringArray(R.array.animal_video);

        listColor = getResources().getIntArray(R.array.animal_color);

        titleAnimation = AnimationUtils.loadAnimation(this, R.anim.text_fade_in);
        leftInAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        leftOutAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
        rightInAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        rightOutAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);

        Intent intent = getIntent();
        String title = intent.getStringExtra("TITLE_ANIMAL");
        String youtube = intent.getStringExtra("YOUTUBE_LINK");
        String video = intent.getStringExtra("VIDEO_ANIMAL");
        if (title == null) {
            currIndex=0;
        } else {
            currIndex = Arrays.asList(listTitles).indexOf(title);
        }
        setTitleAndBtn(currIndex);

        addMedia(flipper);
        flipper.setDisplayedChild(currIndex);

        initializeYoutubePlayer();

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPrevFlipper(flipper);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextFlipper(flipper);
            }
        });

        CustomGestureDetector customGestureDetector = new CustomGestureDetector();
        detector = new GestureDetector(this, customGestureDetector);
        flipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);
                return true;
            }
        });

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });

        invertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent invert = new Intent(getApplicationContext(),InvertActivity.class);
                startActivity(invert);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    private void setTitleAndBtn(int idx) {
        mediaTitle.setBackgroundColor(listColor[idx]);
        mediaTitle.setText(listTitles[idx]);
        mediaTitle.startAnimation(titleAnimation);
        int prevIdx = (idx==0)?listColor.length-1:idx-1;
        int nextIdx = (idx>=listColor.length-1)?0:idx+1;
        prevBtn.setText("<< "+listTitles[prevIdx]);
        prevBtn.setBackgroundColor(listColor[prevIdx]);
        nextBtn.setText(listTitles[nextIdx]+" >>");
        nextBtn.setBackgroundColor(listColor[nextIdx]);
    }

    private void addMedia(ViewFlipper fl) {
        for (int i = 0; i < listTitles.length; i++) {
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.layout_media, null);
            fl.addView(view);
            vid = (VideoView) view.findViewById(R.id.videoView);
            vid.setBackgroundColor(listColor[i]);
            vid.setZOrderOnTop(true);
            MediaController m = new MediaController(this);
            m.setAnchorView(vid);
            vid.setMediaController(m);
            Uri u = getMedia(listVideo[i]);
            vid.setVideoURI(u);
        }
    }

    private Uri getMedia(String mediaName) {
        return Uri.parse("android.resource://" + getPackageName() + "/raw/" + mediaName);
    }

    private void initializeYoutubePlayer() {
        YouTubePlayerFragment youFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtubeFrame);
        if (youFragment == null) return;
        youFragment.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {
                    youPlayer = player;
                    youPlayer.cueVideo(listYoutube[currIndex]);
                }
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
                String errorMessage = String.format("There was an error initializing the YoutubePlayer (%1$s)", error.toString());
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
            } });
    }

    private void showPrevFlipper(ViewFlipper fl) {
        VideoView v = (VideoView) fl.getCurrentView().findViewById(R.id.videoView);
        v.pause();
        fl.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right));
        fl.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right));
        fl.showPrevious();
        currIndex = fl.getDisplayedChild();
        setTitleAndBtn(currIndex);
        if (youPlayer != null) {
            youPlayer.cueVideo(listYoutube[currIndex]);
        }
    }

    private void showNextFlipper(ViewFlipper fl) {
        VideoView v = (VideoView) fl.getCurrentView().findViewById(R.id.videoView);
        v.pause();
        fl.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_left));
        fl.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_left));
        fl.showNext();
        currIndex = fl.getDisplayedChild();
        setTitleAndBtn(currIndex);
        if (youPlayer != null) {
            youPlayer.cueVideo(listYoutube[currIndex]);
        }
    }

    class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {
        //put the methods here…
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // Swipe left (next)
            if (e1.getX() > e2.getX()) {
                showNextFlipper(flipper);
            }

            // Swipe right (previous)
            if (e1.getX() < e2.getX()) {
                showPrevFlipper(flipper);
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            if (e.getX() < flipper.getLeft() + 100) {
                showPrevFlipper(flipper);
            } else if (e.getX() > flipper.getRight() - 100) {
                showNextFlipper(flipper);
            }
            return true;
        }
    }

    private void openMediaActivity(String title) {
        Intent media = new Intent(getApplicationContext(),MediaActivity.class);
        media.putExtra("TITLE_ANIMAL", title);
        startActivity(media);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    private void openInvertActivity() {
        Intent intent = new Intent(getApplicationContext(),InvertActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


}