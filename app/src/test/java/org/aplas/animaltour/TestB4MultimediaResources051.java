package org.aplas.animaltour;

import android.widget.ImageView;
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

public class TestB4MultimediaResources051 extends ViewTest {

    @Test
    public void check_01_Layout_SubInvert_Title() {
        ActivityScenario<SubInvertActivity> main = launchSubInvertApp();
        main.onActivity(activity -> {
            testViewExist("subLayout","LinearLayout",activity);
            String imgId = "mainPic";
            testViewExist(imgId,"ImageView",activity);
            ImageView image = (ImageView) getViewFromActivity(imgId, activity);
            String transName =  getStringResource("transition_name",activity);
            testItem(transName, image.getTransitionName(),
                    "ImageView with id '" + imgId + "' should has transitionName '"+transName+"'",1);
            testViewExist("mainName","TextView",activity);
            testViewExist("mainDetail","TextView",activity);
        });
    }

    public ActivityScenario<SubInvertActivity> launchSubInvertApp() {
        ActivityScenario<SubInvertActivity> scenario = ActivityScenario.launch(SubInvertActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        return scenario;
    }

}
