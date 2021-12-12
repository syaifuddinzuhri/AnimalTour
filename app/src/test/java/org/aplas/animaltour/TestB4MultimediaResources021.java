package org.aplas.animaltour;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowDrawable;

import java.util.Arrays;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;

import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
@Config(manifest= Config.NONE, qualifiers = "en-rUS-w1080dp-h2160dp-xhdpi")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestB4MultimediaResources021 extends ViewTest {
    TestB4MultimediaResources011 b411;

    @Test
    public void check_01_Layout_MainTitle() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.onActivity(activity -> {
            String name="mainTitle";
            String content="Animal Tour";
            TextView view = (TextView)testViewExist(name,"TextView",activity);
            testViewContains(view,content);
        });
    }

    @Test
    public void check_02_Layout_RecycleView() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.onActivity(activity -> {
            String name = "dataView";
            View view = testViewExist(name, "RecyclerView", activity);
            testItem(true, ((RecyclerView) view).isVerticalScrollBarEnabled(),
                    "Value of 'android:scrollbars' in RecyclerView should be 'vertical'", 1);
        });
    }

    @Test
    public void check_03_Layout_RecyclerView_Data() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.onActivity(activity -> {
            View layout = getLayoutFromResource("layout_data", activity, "CardView");
            testViewExist(getViewFromLayout("animalTitle", (ViewGroup) layout), "TextView");
            testViewExist(getViewFromLayout("animalIcon", (ViewGroup) layout), "ImageView");
            testViewExist(getViewFromLayout("animalInfo", (ViewGroup) layout), "TextView");
        });
    }

    @Test
    public void check_04_FishesAnimation() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.onActivity(activity -> {
            testAnimationDrawable(activity,"fishes");
        });
    }

    @Test
    public void check_05_AmphibiansAnimation() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.onActivity(activity -> {
            testAnimationDrawable(activity,"amphibians");
        });
    }

    @Test
    public void check_06_ReptilesAnimation() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.onActivity(activity -> {
            testAnimationDrawable(activity,"reptiles");
        });
    }

    @Test
    public void check_07_BirdsAnimation() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.onActivity(activity -> {
            testAnimationDrawable(activity,"birds");
        });
    }

    @Test
    public void check_08_MammalsAnimation() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.onActivity(activity -> {
            testAnimationDrawable(activity,"mammals");
        });
    }

    @Test
    public void check_09_InvertAnimation() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.onActivity(activity -> {
            testAnimationDrawable(activity,"inverts");
        });
    }

    @Test
    public void check_10_SlideInLeftAnimation() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.onActivity(activity -> {
            testSlideAnimation(activity, "slide_in_left");
        });
    }

    @Test
    public void check_11_SlideInRightAnimation() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.onActivity(activity -> {
            testSlideAnimation(activity, "slide_in_right");
        });
    }

    @Test
    public void check_12_SlideOutLeftAnimation() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.onActivity(activity -> {
            testSlideAnimation(activity, "slide_out_left");
        });
    }

    @Test
    public void check_13_SlideOutRightAnimation() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.onActivity(activity -> {
            testSlideAnimation(activity, "slide_out_right");
        });
    }

    @Test
    public void check_14_FadeInAnimation() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.onActivity(activity -> {
            testFadeAnimation(activity, "fade_in");
        });
    }

    @Test
    public void check_15_FadeOutAnimation() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.onActivity(activity -> {
            testFadeAnimation(activity, "fade_out");
        });
    }

    private void testAnimationDrawable(Activity activity, String animal) {
        ResourceTest rsc = new ResourceTest(activity.getResources());
        String rscName = "anim"+animal;
        rsc.testImgAnimationResource(rscName);
        int resId = rsc.getResourceId(rscName,"drawable");
        try {
            Drawable drawable = AnimationDrawable.createFromXml(activity.getResources(), activity.getResources().getXml(resId));
            testItem("AnimationDrawable",drawable.getClass().getSimpleName(),
                    "'"+rscName+".xml' should be AnimationDrawable",1);
            AnimationDrawable anim = (AnimationDrawable)drawable;
            testItem(5,anim.getNumberOfFrames(),
                    "'"+rscName+".xml' should contain 5 images",1);
            testItem(false,anim.isOneShot(),
                    "android:oneshot value in '"+rscName+".xml' should be false",1);

            for (int i=0; i<5; i++) {
                ShadowDrawable frame = Shadows.shadowOf(anim.getFrame(i));
                int no = i+1;
                int imgId = rsc.getResourceId(animal+no,"drawable");

                testItem(0,frame.getCreatedFromResId()==imgId,
                        "Image "+no+" should be "+animal+no+".png",3);
                testItem(0,(anim.getDuration(i)>=500 && anim.getDuration(i)<=1500),
                        "Duration of image "+no+" should between 500-1500, NOT: "+anim.getDuration(i),3);
                //System.out.println("From"+frame.getCreatedFromResId()+" - "+ imgId +" - Duration:"+anim.getDuration(i));
            }
        } catch (Exception e) {
            fail("Can not find extract '"+rscName+".xml'");
        }
    }

    private void testSlideAnimation(Activity activity, String rscName) {
        ResourceTest rsc = new ResourceTest(activity.getResources());
        rsc.testViewAnimationResource(rscName);
        int resId = rsc.getResourceId(rscName,"anim");

        Animation anim = AnimationUtils.loadAnimation(activity,resId);
        testItem("AnimationSet",anim.getClass().getSimpleName(),
                "Animation type of '"+rscName+".xml' should be SET animation",1);

        List<Animation> list = ((AnimationSet)anim).getAnimations();
        String errMsg = "Animation '"+rscName+".xml' should contain 'translate' and 'alpha' value";
        testItem(2,list.size(), errMsg,1);

        String[] animClass = {list.get(0).getClass().getSimpleName(), list.get(1).getClass().getSimpleName()};
        int trnsIdx = Arrays.asList(animClass).indexOf("TranslateAnimation");
        int alphaIdx = Arrays.asList(animClass).indexOf("AlphaAnimation");
        testItem(0,(trnsIdx>=0 && alphaIdx>=0),errMsg,3);

        TranslateAnimation trns = (TranslateAnimation)list.get(trnsIdx);
        int value = 1000;
        testItem((long)value,trns.getDuration(),"Duration of translate in '"+rscName+".xml' should be "+value,1);

        AlphaAnimation alpha = (AlphaAnimation)list.get(alphaIdx);
        value = 1000;
        testItem((long)value,alpha.getDuration(),"Duration of alpha in '"+rscName+".xml' should be "+value,1);
    }

    private void testFadeAnimation(Activity activity, String rscName) {
        ResourceTest rsc = new ResourceTest(activity.getResources());
        rsc.testViewAnimationResource(rscName);
        int resId = rsc.getResourceId(rscName,"anim");

        Animation anim = AnimationUtils.loadAnimation(activity,resId);
        testItem("AlphaAnimation",anim.getClass().getSimpleName(),
                "Animation type of '"+rscName+".xml' should be Alpa animation",1);

        int value = 500;
        testItem((long)value,anim.getDuration(),"Duration of alpha in '"+rscName+".xml' should be "+value,1);
    }

}
