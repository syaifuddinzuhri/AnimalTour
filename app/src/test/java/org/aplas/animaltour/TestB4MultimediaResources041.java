package org.aplas.animaltour;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowDrawable;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

@RunWith(RobolectricTestRunner.class)
@Config(manifest= Config.NONE, qualifiers = "en-rUS-w1080dp-h2160dp-xhdpi")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestB4MultimediaResources041 extends ViewTest {

    @Test
    public void check_01_Layout_InvertTitle() {
        ActivityScenario<InvertActivity> main = launchInvertApp();
        main.onActivity(activity -> {
            TextView view = (TextView)testViewExist("title1","TextView",activity);
            testViewContains(view,"Invertebrates");
            view = (TextView)testViewExist("title2","TextView",activity);
            testViewContains(view,"World");
        });
    }

    @Test
    public void check_02_Layout_GridLayout() {
        ActivityScenario<InvertActivity> main = launchInvertApp();
        main.onActivity(activity -> {
            ResourceTest rsc = new ResourceTest(activity.getResources());

            GridLayout view = (GridLayout) testViewExist("invertGrid","GridLayout",activity);
            testItem(2, view.getColumnCount(), "GridLayout should contain 2 columns",1);
            testItem(2, view.getRowCount(), "GridLayout should contain 2 rows",1);
            //C1R1
            String[] invertAnimal = {"insect","arachnid","mollusc","crustacean"};
            for (int i=0; i<invertAnimal.length; i++) {
                View child = view.getChildAt(i); //Insects
                String imgId = invertAnimal[i]+"Pic";
                ImageView image = (ImageView) getViewFromLayout(imgId, (ViewGroup) child);
                ShadowDrawable img = Shadows.shadowOf(image.getDrawable());
                String imgSrc = invertAnimal[i]+"s";
                testItem(0, img.getCreatedFromResId() == rsc.getResourceId(imgSrc, "drawable"),
                        "ImageView with id '" + imgId + "' should has source " + imgSrc + ".xml", 3);
                String transName =  getStringResource("transition_name",activity);
                testItem(transName, image.getTransitionName(),
                        "ImageView with id '" + imgId + "' should has transitionName '"+transName+"'",1);
            }
        });
    }

    @Test
    public void check_03_Button_MovingActivity() {
        ActivityScenario<InvertActivity> main = launchInvertApp();
        main.onActivity(activity -> {
            ImageButton button = (ImageButton)testViewExist("mediaButton","ImageButton",activity);
            testImageContent(button,"icon_prev");
            button = (ImageButton)testViewExist("finishButton","ImageButton",activity);
            testImageContent(button,"finish_icon");
        });
    }

    public static ActivityScenario<InvertActivity> launchInvertApp() {
        ActivityScenario<InvertActivity> scenario = ActivityScenario.launch(InvertActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        return scenario;
    }

}
