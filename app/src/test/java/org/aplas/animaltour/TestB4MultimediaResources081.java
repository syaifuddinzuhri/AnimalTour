package org.aplas.animaltour;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

@RunWith(RobolectricTestRunner.class)
@Config(manifest= Config.NONE, qualifiers = "en-rUS-w1080dp-h2160dp-xhdpi")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestB4MultimediaResources081 extends ViewTest {
    TestB4MultimediaResources041 b441;
    TestB4MultimediaResources031 b431;

    @Test
    public void check_01_InsectPic_Click() {
        ActivityScenario<InvertActivity> main = b441.launchInvertApp();
        main.onActivity(activity -> {
            String animal="Insect";

            Activity sub = testInvertAnimalClick(animal,activity);
            testSubInvertContent(animal,sub);
        });
    }

    @Test
    public void check_02_ArachnidPic_Click() {
        ActivityScenario<InvertActivity> main = b441.launchInvertApp();
        main.onActivity(activity -> {
            String animal="Arachnid";

            Activity sub = testInvertAnimalClick(animal,activity);
            testSubInvertContent(animal,sub);
        });
    }

    @Test
    public void check_03_MolluscPic_Click() {
        ActivityScenario<InvertActivity> main = b441.launchInvertApp();
        main.onActivity(activity -> {
            String animal="Mollusc";

            Activity sub = testInvertAnimalClick(animal,activity);
            testSubInvertContent(animal,sub);
        });
    }

    @Test
    public void check_04_CrustaceanPic_Click() {
        ActivityScenario<InvertActivity> main = b441.launchInvertApp();
        main.onActivity(activity -> {
            String animal="Crustacean";

            Activity sub = testInvertAnimalClick(animal,activity);
            testSubInvertContent(animal,sub);
        });
    }

    @Test
    public void check_05_Layout_SubInvertActivity_Click() {
        ActivityScenario<InvertActivity> main = b441.launchInvertApp();
        main.onActivity(activity -> {
            String animal="Insect";
            SubInvertActivity sub = (SubInvertActivity)testInvertAnimalClick(animal,activity);
            //getRootLayout(sub).performClick();
            //System.out.println(getRootLayout(sub).getClass().getSimpleName()+"--"+R.id.subLayout+" .getClass().getSimpleName()"+1);
            sub.findViewById(R.id.subLayout).performClick();
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();
            testItem(0, sub.isFinishing(),
                    "SubInvertActivity should be closed after the layout have been clicked",3);
        });
    }

    @Test
    public void check_06_MediaButton_Click() {
        ActivityScenario<InvertActivity> main = b441.launchInvertApp();
        main.onActivity(activity -> {
            ImageButton button = (ImageButton)getViewFromActivity("mediaButton",activity);
            button.performClick();

            Intent intent = Shadows.shadowOf(activity).getNextStartedActivity();
            testItem(".MediaActivity",intent.getComponent().getShortClassName(),
                    "Intent 'MediaActivity' should be activated",1);

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

    @Test
    public void check_06_MediaFinish_Click() {
        ActivityScenario<InvertActivity> main = b441.launchInvertApp();
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

    private Activity testInvertAnimalClick(String animal, Activity activity) {
        String imgId = animal.toLowerCase()+"Pic";
        ImageView img = (ImageView) getViewFromActivity(imgId, activity);
        img.performClick();

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent intent = shadowActivity.getNextStartedActivity();
        testItem(".SubInvertActivity",intent.getComponent().getShortClassName(),
                "Intent 'SubInverActivity' should be activated after '"+imgId+"' clicked",1);
        testItem(0,activity.getWindow().getSharedElementsUseOverlay(),
                "Shared element transition should be activated after '"+imgId+"' clicked",3);

        String plural=animal+"s";
        Activity sub = Robolectric.buildActivity(SubInvertActivity.class,intent).create().get();
        String extraId = "TITLE_ANIMAL";
        String extra = intent.getStringExtra(extraId);
        testItem(0,extra,"The next view should contain extra '"+extraId+"'",6);
        testItem(plural,extra,"Extra of '"+extraId+"' should be '"+plural+"'",1);

        return sub;
    }

    private void testSubInvertContent(String animal, Activity activity) {
        String plural=animal+"s";

        //Check ImageView
        String viewId = "mainPic";
        ImageView mainImg = (ImageView) getViewFromActivity(viewId, activity);
        String imgId = plural.toLowerCase();
        Drawable imgSrc = mainImg.getDrawable();
        testItem(0,Shadows.shadowOf(imgSrc).getCreatedFromResId()==getRscId(imgId,"drawable",activity),
                "ImageView '"+viewId+"' in SubInvertActivity should contain image src = '"+imgId+".(jpg.png)' from drawable resource",3);

        //Check Title
        viewId = "mainName";
        TextView mainTitle = (TextView) getViewFromActivity(viewId, activity);
        testItem(plural,mainTitle.getText().toString(),
                "TextView '"+viewId+"' in SubInvertActivity should with text '"+plural+"'",1);

        //Check Detail
        viewId = "mainDetail";
        TextView mainDetail = (TextView) getViewFromActivity(viewId, activity);
        String contentName = animal.toLowerCase()+"_content";
        testItem(0,mainDetail.getText().toString().equals(getStringResource(contentName,activity)),
                "TextView '"+viewId+"' in SubInvertActivity should has text from string resource '"+contentName+"'",3);
    }
    public ActivityScenario<SubInvertActivity> launchSubInvertApp() {
        ActivityScenario<SubInvertActivity> scenario = ActivityScenario.launch(SubInvertActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        return scenario;
    }

}
