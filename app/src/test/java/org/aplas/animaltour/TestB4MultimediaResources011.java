package org.aplas.animaltour;

import android.os.Build;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
@Config(manifest= Config.NONE, qualifiers = "en-rUS-w1080dp-h2160dp-xhdpi")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestB4MultimediaResources011 extends ViewTest {
    private ActivityScenario<MainActivity> mainScenario;

    private final String layoutName = "activity_main";
    private final String appName  = "AnimalTour";
    private final String packageName = "org.aplas.animaltour";
    private final String targetDevice = "9";
    private final String actName = "MainActivity";
    private final String backwardComp = "androidx.appcompat.app.AppCompatActivity";
    private final int minSDK = 23;

    private ResourceTest rsc;

    @Test
    public void check_01_Project_Configuration() {
        ActivityScenario<MainActivity> main = launchMainApp();
        main.onActivity(activity -> {
            assertEquals("Application Name is Wrong", appName.toLowerCase(), getAppName(activity.getPackageName()));
            String packName = packageName;
            assertEquals("Package Name is Wrong", packName, activity.getPackageName());
            assertEquals("Target Device is Wrong", targetDevice, Build.VERSION.RELEASE);
            assertEquals("Activity Name is Wrong", actName, activity.getClass().getSimpleName());

            int resId = activity.getResources().getIdentifier(layoutName, "layout", activity.getPackageName());
            assertNotEquals("Layout Name is Wrong", 0, resId);

            assertEquals("Activity super class is Wrong", backwardComp, activity.getClass().getSuperclass().getName());
        });
    }

    private String getAppName(String packName) {
        String[] list = packName.split("\\.");
        String res = list[list.length-1];
        return res;
    }

    //Resource Test
    @Test
    public void check_02_String_Resources() {
        ActivityScenario<MainActivity> main = launchMainApp();
        main.onActivity(activity -> {
            rsc = new ResourceTest(activity.getResources());
            rsc.testStringResource("app_name", "APLAS Animal Tour");
            rsc.testStringResource("transition_name", "fade_trans");
            rsc.testStringResource("insect_content");
            rsc.testStringResource("arachnid_content");
            rsc.testStringResource("mollusc_content");
            rsc.testStringResource("crustacean_content");
            rsc.testStringResource("invert_info");
        });
    }

    @Test
    public void check_03_StringArray_Resource() {
        ActivityScenario<MainActivity> main = launchMainApp();
        main.onActivity(activity -> {
            rsc = new ResourceTest(activity.getResources());
            //Hashtable<String, String[]> data = new Hashtable<>();
            String[] data1 = {"animfishes", "animamphibians", "animreptiles", "animbirds", "animmammals"};
            rsc.testStringArrayResource("animal_icon", data1);
            String[] data2 = {"videofishes", "videoamphibians", "videoreptiles", "videobirds", "videomammals"};
            rsc.testStringArrayResource("animal_video", data2);
            String[] data3 = {"Fishes", "Amphibians", "Reptiles", "Birds", "Mammals"};
            rsc.testStringArrayResource("animal_titles", data3);
            String[] data4 = {"A group of cold-blooded vertebrates that breathe with gills rather than lungs, live in water, and generally lay eggs, although some bear their young alive.",
                    "A group of cold-blooded animals which can live on land or in water and who breathes with gills when young and with lungs as an adult.",
                    "A group of cold-blooded animals which have skins covered with small hard plates called scales and lay eggs.",
                    "A group of warm-blooded vertebrates distinguished by their feathers and their two legs and two wings.",
                    "A group of warm-blooded animals that is distinguished by the possession of hair or fur, females that secrete milk for the nourishment of the young, and (typically) the birth of live young."};
            rsc.testStringArrayResource("animal_info", data4);
            String[] data5 = {"TJN3gJoZqlY", "XI8GPsf6TAc", "6B0apT6VZKk", "8vL_2rF8JHU", "hGonwMTPV6g"};
            rsc.testStringArrayResource("animal_youtube", data5);
        });
    }

    @Test
    public void check_04_ImageResource() {
        ActivityScenario<MainActivity> main = launchMainApp();
        main.onActivity(activity -> {
            rsc = new ResourceTest(activity.getResources());
            String[] images = {"amphibians1", "amphibians2", "amphibians3", "amphibians4", "amphibians5",
                    "animalicon", "arachnids", "birds1", "birds2", "birds3", "birds4", "birds5",
                    "crustaceans", "finish_icon", "fishes1", "fishes2", "fishes3", "fishes4", "fishes5",
                    "icon_next", "icon_prev", "insects", "inverts1", "inverts2", "inverts3", "inverts4", "inverts5",
                    "invertbg", "mammals1", "mammals2", "mammals3", "mammals4", "mammals5", "molluscs",
                    "reptiles1", "reptiles2", "reptiles3", "reptiles4", "reptiles5"
            };
            for (int i = 0; i < images.length; i++) {
                rsc.testImgResource(images[i]);
            }
        });
    }

    @Test
    public void check_05_ImageAnimationResource() {
        ActivityScenario<MainActivity> main = launchMainApp();
        main.onActivity(activity -> {
            rsc = new ResourceTest(activity.getResources());
            String[] images = {"animamphibians", "animbirds", "animfishes", "animinverts",
                    "animmammals", "animreptiles"
            };
            for (int i = 0; i < images.length; i++) {
                rsc.testImgAnimationResource(images[i]);
            }
        });
    }



    @Test
    public void check_07_VideoResource() {
        ActivityScenario<MainActivity> main = launchMainApp();
        main.onActivity(activity -> {
            rsc = new ResourceTest(activity.getResources());
            String[] images = {"videoamphibians", "videobirds", "videofishes", "videomammals", "videoreptiles"};
            for (int i = 0; i < images.length; i++) {
                rsc.testVideoResource(images[i]);
            }
        });
    }

    @Test
    public void check_08_ColorResource() {
        ActivityScenario<MainActivity> main = launchMainApp();
        main.onActivity(activity -> {
            rsc = new ResourceTest(activity.getResources());
            rsc.testColorResource(activity, "textTitle", "#FCF08F");
            rsc.testColorResource(activity, "invert_color", "#ACACAC");

            String[] data = {"#FFEB3B", "#FAC2C6", "#A2FA92", "#7491FF", "#F1B1FF"};
            rsc.testColorArrayResource("animal_color", data);
        });
    }

    @Test
    public void check_09_ManifestFile() {
        ActivityScenario<MainActivity> main = launchMainApp();
        main.onActivity(activity -> {
            rsc = new ResourceTest(activity.getResources());
            String theme = "AppTheme";
            rsc.testStyleResource(theme);
            rsc.testAppTheme(activity, theme);
            rsc.testAppPermission(activity, "android.permission.INTERNET");
            //rsc.testAppActivities(activity,"MainActivity",packageName);
        });
    }

    @Test
    public void check_02_Implemented_Class() {
        checkClassImplementation("androidx.recyclerview.widget.RecyclerView");
        checkClassImplementation("androidx.cardview.widget.CardView");
        checkClassImplementation("androidx.appcompat.app.AppCompatActivity");
        checkClassImplementation("com.google.android.youtube.player.YouTubePlayer");
    }

    public void checkClassImplementation(String className) {
        try {
            Class.forName(className);
        } catch (Exception e) {
            String[] arr = className.split(".");
            fail("Project did not implement "+arr[arr.length-1]+" yet. Check gradle configuration");
        }
    }

    public static ActivityScenario<MainActivity> launchMainApp() {
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        return scenario;
    }

}
