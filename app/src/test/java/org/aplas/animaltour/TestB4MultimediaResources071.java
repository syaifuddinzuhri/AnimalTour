package org.aplas.animaltour;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.ViewFlipper;
import com.google.android.youtube.player.YouTubePlayerView;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowLooper;
import org.robolectric.shadows.ShadowToast;
import org.robolectric.shadows.ShadowVideoView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;

@RunWith(RobolectricTestRunner.class)
@Config(manifest= Config.NONE, qualifiers = "en-rUS-w1080dp-h2160dp-xhdpi")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestB4MultimediaResources071 extends ViewTest {
    String rvName = "dataView";
    String adapterName = "DataAdapter";
    TestB4MultimediaResources011 b411;
    TestB4MultimediaResources061 b461;
    TestB4MultimediaResources031 b431;

    @Test
    public void check_01_Title_Animation() {
        ActivityScenario<MediaActivity> main = b431.launchMediaApp();
        main.onActivity(activity -> {
            int duration = 1600;
            int repeatCount = 0;

            TextView view = (TextView) testViewExist("mediaTitle","TextView",activity);
            String msg = "TextView 'mediaTitle' should use 'text_fade_in' animation";

            testItem(0,view.getAnimation().getClass().getSimpleName().equals("AnimationSet"),msg,3);
            Animation child = ((AnimationSet)view.getAnimation()).getAnimations().get(0);
            testItem(0,child.getClass().getSimpleName().equals("AlphaAnimation"),msg,3);
            testItem(0,child.getDuration()==(long)duration,msg,3);
            testItem(0,child.getRepeatCount()==repeatCount,msg,3);
        });
    }

    @Test
    public void check_02_When_Fish_Clicked() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.onActivity(activity -> {
            RecyclerView rv = (RecyclerView) getViewFromActivity(rvName, activity);
            int idx = 0;
            View itemRv = b461.clickAnimal(idx,rv);

            Intent intent = Shadows.shadowOf(activity).getNextStartedActivity();
            Activity media = Robolectric.buildActivity(MediaActivity.class,intent).create().start().get();
            media.getFragmentManager().executePendingTransactions();
            testMediaContent(idx,media);
        });
    }

    @Test
    public void check_03_When_Amphibians_Clicked() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.onActivity(activity -> {
            RecyclerView rv = (RecyclerView) getViewFromActivity(rvName, activity);
            int idx = 1;
            View itemRv = b461.clickAnimal(idx,rv);

            Intent intent = Shadows.shadowOf(activity).getNextStartedActivity();
            Activity media = Robolectric.buildActivity(MediaActivity.class,intent).create().start().get();
            media.getFragmentManager().executePendingTransactions();
            testMediaContent(idx,media);
        });
    }

    @Test
    public void check_04_When_Reptiles_Clicked() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.onActivity(activity -> {
            RecyclerView rv = (RecyclerView) getViewFromActivity(rvName, activity);
            int idx = 2;
            View itemRv = b461.clickAnimal(idx,rv);

            Intent intent = Shadows.shadowOf(activity).getNextStartedActivity();
            Activity media = Robolectric.buildActivity(MediaActivity.class,intent).create().start().get();
            media.getFragmentManager().executePendingTransactions();
            testMediaContent(idx,media);
        });
    }

    @Test
    public void check_05_When_Birds_Clicked() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.onActivity(activity -> {
            RecyclerView rv = (RecyclerView) getViewFromActivity(rvName, activity);
            int idx = 3;
            View itemRv = b461.clickAnimal(idx,rv);

            Intent intent = Shadows.shadowOf(activity).getNextStartedActivity();
            Activity media = Robolectric.buildActivity(MediaActivity.class,intent).create().start().get();
            media.getFragmentManager().executePendingTransactions();
            testMediaContent(idx,media);
        });
    }

    @Test
    public void check_06_When_Mammals_Clicked() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.onActivity(activity -> {
            RecyclerView rv = (RecyclerView) getViewFromActivity(rvName, activity);
            int idx = 4;
            View itemRv = b461.clickAnimal(idx,rv);

            Intent intent = Shadows.shadowOf(activity).getNextStartedActivity();
            Activity media = Robolectric.buildActivity(MediaActivity.class,intent).create().start().get();
            media.getFragmentManager().executePendingTransactions();
            testMediaContent(idx,media);
        });
    }

    @Test
    public void check_07_ViewFlipper_Content() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.onActivity(activity -> {
            RecyclerView rv = (RecyclerView) getViewFromActivity(rvName, activity);
            int idx = 0;
            View itemRv = b461.clickAnimal(idx,rv);

            Intent intent = Shadows.shadowOf(activity).getNextStartedActivity();
            Activity media = Robolectric.buildActivity(MediaActivity.class,intent).create().start().get();
            testViewFlipperContent(idx,media);
        });
    }

    @Test
    public void check_08_ViewFlipper_Click_Left() {
        ActivityScenario<MediaActivity> main = b431.launchMediaApp();
        main.onActivity(activity -> {
            testClickViewFlipper(activity, true);
        });
    }

    @Test
    public void check_09_ViewFlipper_Click_Right() {
        ActivityScenario<MediaActivity> main = b431.launchMediaApp();
        main.onActivity(activity -> {
            testClickViewFlipper(activity, false);
        });
    }

    @Test
    public void check_10_ViewFlipper_Swipe_Right() {
        ActivityScenario<MediaActivity> main = b431.launchMediaApp();
        main.onActivity(activity -> {
            testSwipeViewFlipper(activity, true);
        });
    }

    @Test
    public void check_11_ViewFlipper_Swipe_Left() {
        ActivityScenario<MediaActivity> main = b431.launchMediaApp();
        main.onActivity(activity -> {
            testSwipeViewFlipper(activity, false);
        });
    }

    @Test
    public void check_12_PrevButton_Click() {
        ActivityScenario<MediaActivity> main = b431.launchMediaApp();
        main.onActivity(activity -> {
            testClickViewFlipper(activity, true);
        });
    }

    @Test
    public void check_13_NextButton_Click() {
        ActivityScenario<MediaActivity> main = b431.launchMediaApp();
        main.onActivity(activity -> {
            testClickViewFlipper(activity, false);
        });
    }

    @Test
    public void check_14_InvertButton_Click() {
        ActivityScenario<MediaActivity> main = b431.launchMediaApp();
        main.onActivity(activity -> {
            ImageButton button = (ImageButton)getViewFromActivity("invertButton",activity);
            button.performClick();

            Intent intent = Shadows.shadowOf(activity).getNextStartedActivity();
            testItem(".InvertActivity",intent.getComponent().getShortClassName(),
                    "Intent 'InvertActivity' should be activated",1);

            ResourceTest rsc = new ResourceTest(activity.getResources());
            ShadowActivity shadowActivity = Shadows.shadowOf(activity);
            String enterAnim= "fade_in";
            testItem(0,shadowActivity.getPendingTransitionEnterAnimationResourceId()==rsc.getResourceId(enterAnim,"anim"),
                    "Activity enter transition (overridePendingTransition) should use "+enterAnim+".xml",3);
            String exitAnim= "fade_out";
            testItem(0,shadowActivity.getPendingTransitionExitAnimationResourceId()==rsc.getResourceId(exitAnim,"anim"),
                    "Activity exit transition (overridePendingTransition) should use "+exitAnim+".xml",3);
        });
    }

    @Test
    public void check_15_BackButton_Click() {
        ActivityScenario<MediaActivity> main = b431.launchMediaApp();
        main.onActivity(activity -> {
            ImageButton button = (ImageButton)getViewFromActivity("finishButton",activity);
            button.performClick();

            Intent intent = Shadows.shadowOf(activity).getNextStartedActivity();
            testItem(".MainActivity",intent.getComponent().getShortClassName(),
                    "Intent 'MainActivity' should be activated",1);

            ResourceTest rsc = new ResourceTest(activity.getResources());
            ShadowActivity shadowActivity = Shadows.shadowOf(activity);
            String enterAnim= "slide_in_right";
            testItem(0,shadowActivity.getPendingTransitionEnterAnimationResourceId()==rsc.getResourceId(enterAnim,"anim"),
                    "Activity enter transition (overridePendingTransition) should use "+enterAnim+".xml",3);
            String exitAnim= "slide_out_right";
            testItem(0,shadowActivity.getPendingTransitionExitAnimationResourceId()==rsc.getResourceId(exitAnim,"anim"),
                    "Activity exit transition (overridePendingTransition) should use "+exitAnim+".xml",3);
        });
    }

    private String[] getListAnimalTitles(Activity activity) {
        return getStringArrayResource("animal_titles",activity); //activity.getResources().getStringArray(R.array.animal_titles);
    }

    private String getAnimalTitle(int idx, Activity activity) {
        String[] list = getListAnimalTitles(activity);
        return list[idx];
    }

    private String getAnimalVideo(int idx, Activity activity) {
        String[] list = getStringArrayResource("animal_video",activity);
        //activity.getResources().getStringArray(R.array.animal_video);
        return list[idx];
    }

    private int getAnimalColor(int idx, Activity activity) {
        int[] list = getIntArrayResource("animal_color",activity);
        return list[idx];
    }

    private void testMediaContent(int idx, Activity activity) {
        TextView mediaTitle = (TextView) getViewFromActivity("mediaTitle",activity);
        testItem(getAnimalTitle(idx,activity),mediaTitle.getText().toString(),
                "TextView 'mediaTitle' should has text '"+getAnimalTitle(idx,activity)+"'",1);
        //System.out.println(mediaTitle.getBackground().getClass().getSimpleName());
        testCurrentViewFlipper(idx,activity);
        testCurrentPrevNextButton(idx,activity);

        YouTubePlayerView playerView = (YouTubePlayerView) getViewFromActivity("youtubeFrame",activity);
        testItem(0,playerView,"YoutubePlayer is not initialized yet, " +
                "call method 'initializeYoutubePlayer",6);
    }

    private void testCurrentViewFlipper(int idx, Activity activity) {
        ViewFlipper mediaFlipper = (ViewFlipper) getViewFromActivity("mediaFlipper",activity);
        testItem(5, mediaFlipper.getChildCount(),
                "ViewFlipper 'mediaFlipper' should contain 5 videos",1);
        View view = mediaFlipper.getCurrentView();
        VideoView video = (VideoView)testViewExist("videoView","VideoView",view);
        testItem(getHexColor(getAnimalColor(idx,activity)),
                getHexColor(((ColorDrawable)video.getBackground()).getColor()),
                "Background color of current ViewFlipper should be "+getHexColor(getAnimalColor(idx,activity)),1);
        ShadowVideoView shadowVideo = Shadows.shadowOf(video);
        String videoRsc = "raw/"+getAnimalVideo(idx,activity);
        String[] strList = shadowVideo.getVideoURIString().split("/");
        String actVideo = strList[strList.length-2]+"/"+strList[strList.length-1];
        testItem(videoRsc, actVideo,
                "Video resource of current VideoView should be '"+videoRsc+"'",1);
    }

    private void testCurrentPrevNextButton(int idx, Activity activity) {
        String[] list = getListAnimalTitles(activity);

        Button prevButton = (Button) getViewFromActivity("prevButton",activity);
        int prevIdx = (idx==0)?list.length-1:idx-1;
        String expected = "<< "+list[prevIdx];
        testItem(expected.toUpperCase(), prevButton.getText().toString().toUpperCase(),
                "Text of Button 'prevButton' should be "+expected,1);

        Button nextButton = (Button) getViewFromActivity("nextButton",activity);
        int nextIdx = (idx == list.length-1)?0:idx+1;
        expected = list[nextIdx]+" >>";
        testItem(expected.toUpperCase(), nextButton.getText().toString().toUpperCase(),
                "Text of Button 'nextButton' should be "+expected,1);
        /*
        String expected = getHexColor(getAnimalColor(prevIdx,activity));
        int color=-937473;//y.getFillColor().getDefaultColor();
        testItem(expected, getHexColor(color),
                "Background color of Button 'prevButton' should be "+expected,1);

        expected = getHexColor(getAnimalColor(nextIdx,activity));
        testItem(expected, getHexColor(((ColorDrawable)nextButton.getBackground()).getColor()),
                "Background color of Button 'nextButton' should be "+expected,1);
        */
    }

    private void testViewFlipperContent(int startIdx, Activity activity) {
        ViewFlipper mediaFlipper = (ViewFlipper) getViewFromActivity("mediaFlipper",activity);
        int videoCount = 5;
        testItem(videoCount, mediaFlipper.getChildCount(),
                "ViewFlipper 'mediaFlipper' should contain 5 videos",1);
        int pos = startIdx;
        for (int i=0; i<videoCount; i++) {
            View view = mediaFlipper.getCurrentView();
            VideoView video = (VideoView)testViewExist("videoView","VideoView",view);
            testItem(getHexColor(getAnimalColor(pos,activity)),
                    getHexColor(((ColorDrawable)video.getBackground()).getColor()),
                    "Background color of current ViewFlipper should be "+getHexColor(getAnimalColor(pos,activity)),1);
            ShadowVideoView shadowVideo = Shadows.shadowOf(video);
            String videoRsc = "raw/"+getAnimalVideo(pos,activity);
            String[] strList = shadowVideo.getVideoURIString().split("/");
            String actVideo = strList[strList.length-2]+"/"+strList[strList.length-1];
            testItem(videoRsc, actVideo,
                    "Video resource of current VideoView should be '"+videoRsc+"'",1);
            mediaFlipper.showNext();
            pos = (pos==videoCount-1)?0:pos+1;
        }
    }

    private void testClickViewFlipper(Activity activity, boolean isLeft) {
        ViewFlipper vf = (ViewFlipper) getViewFromActivity("mediaFlipper",activity);

        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis();

        int middleX = (vf.getLeft()+vf.getRight())/2;
        int middleY = (vf.getTop()+vf.getBottom())/2;
        int x = (isLeft)?vf.getLeft()+50:vf.getRight()-50;
        MotionEvent event = MotionEvent.obtain(downTime,eventTime, MotionEvent.ACTION_DOWN, x,middleY, 0);
        vf.dispatchTouchEvent(event);

        testAfterAnimalClick(activity,vf,isLeft);
        /*
        int fromX = vf.getRight();
        int fromY = (vf.getBottom()+vf.getTop())/2;
        int toX = (vf.getRight()+vf.getLeft())/2;
        int toY = fromY;

        float y = 0.0f;
        float x = 0.0f;

        int stepCount = 2;
        int yStep = (toY - fromY) / stepCount;
        int xStep = (toX - fromX) / stepCount;

        eventTime = SystemClock.uptimeMillis();
        MotionEvent event = MotionEvent.obtain(
                downTime, eventTime,
                MotionEvent.ACTION_DOWN, fromX, fromY, 0
        );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            event.setSource(InputDevice.SOURCE_TOUCHSCREEN);
        }

        activity.onTouchEvent(event);

        for (int i=0; i<stepCount; i++) {
            y += yStep;
            x += xStep;
            eventTime = SystemClock.uptimeMillis();
            event = MotionEvent.obtain(
                    downTime, eventTime + stepCount,
                    MotionEvent.ACTION_MOVE, x, y, 0
            );
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
                event.setSource(InputDevice.SOURCE_TOUCHSCREEN);
            }
            activity.onTouchEvent(event);
        }

        eventTime = SystemClock.uptimeMillis() + (long)stepCount + 2;
        event = MotionEvent.obtain(
                downTime, eventTime,
                MotionEvent.ACTION_UP, toX, toY, 0
        );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            event.setSource(InputDevice.SOURCE_TOUCHSCREEN);
        }
        activity.onTouchEvent(event);

*/
    }

    private void testSwipeViewFlipper(Activity activity, boolean isLeft) {
        ViewFlipper vf = (ViewFlipper) getViewFromActivity("mediaFlipper",activity);

        float x1,x2,x3;
        if (isLeft) {
            x1 = vf.getLeft()+200;
            x2 = x1+100;
            x3 = x2+100;
        } else {
            x1 = vf.getRight()-200;
            x2 = x1-100;
            x3 = x2-100;
        }

        float yy = vf.getBottom()-50; //obj.getY(); //0.0f;
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis();
        System.out.println(x1+"/"+yy);

        MotionEvent event = MotionEvent.obtain(downTime,eventTime, MotionEvent.ACTION_DOWN, x1,yy, 0);
        vf.dispatchTouchEvent(event);

        eventTime += 100;//= SystemClock.uptimeMillis();
        event = MotionEvent.obtain(downTime,eventTime, MotionEvent.ACTION_MOVE, x2,yy, 0);
        vf.dispatchTouchEvent(event);

        eventTime += 100;
        event = MotionEvent.obtain(downTime,eventTime, MotionEvent.ACTION_MOVE, x3,yy, 0);
        vf.dispatchTouchEvent(event);

        eventTime += 100;
        event = MotionEvent.obtain(downTime,eventTime, MotionEvent.ACTION_UP, x3,yy, 0);
        vf.dispatchTouchEvent(event);
        //ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

        //System.out.println(ShadowToast.getTextOfLatestToast()+"ok");

        testAfterAnimalClick(activity,vf,isLeft);
    }

    private void testAfterAnimalClick(Activity activity, ViewFlipper vf, boolean isLeft) {
        int idx,prevIdx,nextIdx;
        String animIn,animOut;
        if (isLeft) {
            idx = 4;
            prevIdx = 3;
            nextIdx = 0;
            animIn="slide_in_right";
            animOut="slide_out_right";
        } else {
            idx = 1;
            prevIdx = 0;
            nextIdx = 2;
            animIn="slide_in_left";
            animOut="slide_out_left";
        }

        TextView mediaTitle = (TextView) getViewFromActivity("mediaTitle",activity);
        testItem(getAnimalTitle(idx,activity),mediaTitle.getText().toString(),
                "TextView 'mediaTitle' should has text '"+getAnimalTitle(idx,activity)+"'",1);
        Button prevBtn = (Button) getViewFromActivity("prevButton",activity);
        String prevTxt = "<< "+getAnimalTitle(prevIdx,activity);
        testItem(prevTxt,prevBtn.getText().toString(),
                "Button 'prevButton' should has text '"+prevTxt+"'",1);
        Button nextBtn = (Button) getViewFromActivity("nextButton",activity);
        String nextTxt = getAnimalTitle(nextIdx,activity)+" >>";
        testItem(nextTxt,nextBtn.getText().toString(),
                "Button 'nextButton' should has text '"+nextTxt+"'",1);

        testItem(0,vf.getInAnimation().getDuration()==1000,
                "ViewFlipper in Animation should be '"+animIn+"', "+
                "use 'fl.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim."+animIn+"));'",3);
        testItem(0,vf.getOutAnimation().getDuration()==1000,
                "ViewFlipper in Animation should be '"+animOut+"', "+
                "use 'fl.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim."+animOut+"));'",3);
    }
}
