package org.aplas.animaltour;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

@RunWith(RobolectricTestRunner.class)
@Config(manifest= Config.NONE, qualifiers = "en-rUS-w1080dp-h2160dp-xhdpi")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestB4MultimediaResources031 extends ViewTest {

    @Test
    public void check_01_Layout_MediaTitle() {
        ActivityScenario<MediaActivity> main = launchMediaApp();
        main.onActivity(activity -> {
            String name="mediaTitle";
            testViewExist(name,"TextView",activity);
        });
    }

    @Test
    public void check_02_Layout_ViewFlipper() {
        ActivityScenario<MediaActivity> main = launchMediaApp();
        main.onActivity(activity -> {
            String name="mediaFlipper";
            testViewExist(name,"ViewFlipper",activity);
        });
    }

    @Test
    public void check_03_Layout_ViewFlipper_Child() {
        ActivityScenario<MediaActivity> main = launchMediaApp();
        main.onActivity(activity -> {
            View layout = getLayoutFromResource("layout_media", activity, "LinearLayout");
            testViewExist(getViewFromLayout("videoView", (ViewGroup) layout), "VideoView");
        });
    }

    @Test
    public void check_04_Layout_ViewFlipper_Button() {
        ActivityScenario<MediaActivity> main = launchMediaApp();
        main.onActivity(activity -> {
            testViewExist("prevButton","Button",activity);
            testViewExist("nextButton","Button",activity);
        });
    }

    @Test
    public void check_05_Layout_YoutubeFragment() {
        ActivityScenario<MediaActivity> main = launchMediaApp();
        main.onActivity(activity -> {
            testViewExist("youtubeFrame","YouTubePlayerView",activity);
        });
    }

    @Test
    public void check_06_Button_MovingActivity() {
        ActivityScenario<MediaActivity> main = launchMediaApp();
        main.onActivity(activity -> {
            ImageButton button = (ImageButton)testViewExist("finishButton","ImageButton",activity);
            testImageContent(button,"icon_prev");
            button = (ImageButton)testViewExist("invertButton","ImageButton",activity);
            testImageContent(button,"icon_next");
        });
    }

    @Test
    public void check_07_Text_Fade_Animation() {
        ActivityScenario<MediaActivity> main = launchMediaApp();
        main.onActivity(activity -> {
            ResourceTest rsc = new ResourceTest(activity.getResources());
            String rscName = "text_fade_in";
            rsc.testViewAnimationResource(rscName);
            int resId = rsc.getResourceId(rscName,"anim");

            Animation anim = AnimationUtils.loadAnimation(activity,resId);
            testItem("AnimationSet",anim.getClass().getSimpleName(),
                    "Animation type of '"+rscName+".xml' should be Set animation",1);

            Animation child = ((AnimationSet)anim).getAnimations().get(0);
            testItem("AlphaAnimation",child.getClass().getSimpleName(),
                    "'"+rscName+".xml' should contain Alpha animation",1);

            int duration = 1600;
            testItem((long)duration,child.getDuration(),
                    "Duration of alpha in '"+rscName+".xml' should be "+duration,1);
            int repeatCount = 0;
            testItem(repeatCount,child.getRepeatCount(),
                    "Repeat count of alpha in '"+rscName+".xml' should be "+repeatCount,1);
        });
    }

    public static ActivityScenario<MediaActivity> launchMediaApp() {
        ActivityScenario<MediaActivity> scenario = ActivityScenario.launch(MediaActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        return scenario;
    }

    public static ActivityScenario<MediaActivity> launchMediaApp(Intent intent) {
        ActivityScenario<MediaActivity> scenario = ActivityScenario.launch(intent);
        scenario.moveToState(Lifecycle.State.CREATED);
        return scenario;
    }

}
