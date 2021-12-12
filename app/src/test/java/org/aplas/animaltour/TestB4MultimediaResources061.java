package org.aplas.animaltour;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
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
import org.robolectric.shadows.ShadowDrawable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;

@RunWith(RobolectricTestRunner.class)
@Config(manifest= Config.NONE, qualifiers = "en-rUS-w1080dp-h2160dp-xhdpi")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestB4MultimediaResources061 extends ViewTest {
    String rvName = "dataView";
    String adapterName = "DataAdapter";
    TestB4MultimediaResources011 b411;

    @Test
    public void check_01_RecyclerView_Layout() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.onActivity(activity -> {
            RecyclerView rv = (RecyclerView) getViewFromActivity(rvName, activity);

            String[] listTitles = getStringArrayResource("animal_titles",activity);

            testItem("LinearLayoutManager", rv.getLayoutManager().getClass().getSimpleName(),
                    "RecyclerView '"+rvName+"' Layout Manager should be LinearLayoutManager",1);
            testItem("DefaultItemAnimator", rv.getItemAnimator().getClass().getSimpleName(),
                    "RecyclerView '"+rvName+"' Item Animator should be DefaultItemAnimator",1);
            testItem("DataAdapter", rv.getAdapter().getClass().getSimpleName(),
                    "RecyclerView '"+rvName+"' adapter should be in "+adapterName+" type",1);

            DataAdapter adapter = (DataAdapter) rv.getAdapter();
            testItem(listTitles.length+1,adapter.getItemCount(),
                    "RecyclerView 'dataView' item count should be "+(listTitles.length+1), 1);
        });
    }

    @Test
    public void check_02_RecyclerView_Content() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.moveToState(Lifecycle.State.STARTED);
        main.onActivity(activity -> {
            RecyclerView rv = (RecyclerView) getViewFromActivity(rvName, activity);
            ResourceTest rsc = new ResourceTest(activity.getResources());

            List<String> titleList = new ArrayList<String>();
            Collections.addAll(titleList, getStringArrayResource("animal_titles",activity));
            titleList.add("Invertebrates");

            List<Integer> colorList = new ArrayList<Integer>();
            int[] arr = getIntArrayResource("animal_color",activity);
            for (int i : arr) colorList.add(i);
            colorList.add(getColorResource("invert_color",activity));

            List<String> infoList = new ArrayList<String>();
            Collections.addAll(infoList, getStringArrayResource("animal_info",activity));
            infoList.add(getStringResource("invert_info",activity));

            List<String> iconList = new ArrayList<String>();
            Collections.addAll(iconList, getStringArrayResource("animal_icon",activity));
            iconList.add("animinverts");

            int rvItem = Objects.requireNonNull(rv.getAdapter()).getItemCount();
            int rvChild = rv.getChildCount();

            for (int i = 0; i < rvChild; i++) {
                int no = i+1;
                View view = Objects.requireNonNull(rv.findViewHolderForLayoutPosition(i)).itemView; //Objects.requireNonNull(rv.findViewHolderForAdapterPosition(i)).itemView;
                testItemRecyclerView(view, no, titleList.get(i), colorList.get(i), iconList.get(i),
                        infoList.get(i), rsc);
            }

            if (rvItem>rv.getChildCount()) {
                rv.scrollToPosition(rvItem-1);
                for (int i = 0; i < rvItem - rvChild; i++) {
                    int no = rvChild+i;
                    View view = rv.getChildAt(rvChild-1+i);
                    testItemRecyclerView(view, no, titleList.get(rvChild+i), colorList.get(rvChild+i),
                            iconList.get(rvChild+i),infoList.get(rvChild+i), rsc);
                }
            }
        });
    }

    @Test
    public void check_03_RecyclerItem_Click_Vertebrates() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.moveToState(Lifecycle.State.STARTED);
        main.onActivity(activity -> {
            RecyclerView rv = (RecyclerView) getViewFromActivity(rvName, activity);
            ResourceTest rsc = new ResourceTest(activity.getResources());

            for (int i=0; i<5; i++) {
                View view = clickAnimal(i,rv);
                String animal = ((TextView)getViewFromLayout("animalTitle",view)).getText().toString();
                //System.out.print("Test clicking "+animal+" : ");

                Intent intent = Shadows.shadowOf(activity).getNextStartedActivity();
                testItem(".MediaActivity",intent.getComponent().getShortClassName(),
                        "Intent 'MediaActivity' should be activated",1);
                String extra = "TITLE_ANIMAL";

                testItem(animal, intent.getStringExtra(extra),
                        "When animal '"+animal+"' clicked, 'MediaActivity' Intent should has Extra '"+extra+"' "+
                                "with value from Clicked animal title '"+animal+"'",1);
                ShadowActivity shadowActivity = Shadows.shadowOf(activity);
                String enterAnim= "slide_in_left";
                testItem(0,shadowActivity.getPendingTransitionEnterAnimationResourceId()==rsc.getResourceId(enterAnim,"anim"),
                        "Activity enter transition (overridePendingTransition) should use "+enterAnim+".xml",3);
                String exitAnim= "slide_out_left";
                testItem(0,shadowActivity.getPendingTransitionExitAnimationResourceId()==rsc.getResourceId(exitAnim,"anim"),
                        "Activity exit transition (overridePendingTransition) should use "+exitAnim+".xml",3);

                MediaActivity media = Robolectric.buildActivity(MediaActivity.class,intent).create().get();
                media.finish();
                //System.out.println("SUCCESS");
                //testItem(media.getLifecycle().getCurrentState().name(),activity.getLifecycle().getCurrentState().name()+"g",
                //        "Shared element transition should be activated after screen clicked",1);
            }
        });
    }

    @Test
    public void check_04_RecyclerItem_Click_Invert() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.moveToState(Lifecycle.State.STARTED);
        main.onActivity(activity -> {
            RecyclerView rv = (RecyclerView) getViewFromActivity(rvName, activity);
            ResourceTest rsc = new ResourceTest(activity.getResources());

            View view = clickAnimal(5,rv);
            String animal = ((TextView)getViewFromLayout("animalTitle",view)).getText().toString();
            System.out.print("Test clicking "+animal+" : ");

            Intent intent = Shadows.shadowOf(activity).getNextStartedActivity();
            testItem(".InvertActivity",intent.getComponent().getShortClassName(),
                        "Intent 'InvertActivity' should be activated",1);

            ShadowActivity shadowActivity = Shadows.shadowOf(activity);
            String enterAnim= "fade_in";
            testItem(0,shadowActivity.getPendingTransitionEnterAnimationResourceId()==rsc.getResourceId(enterAnim,"anim"),
                    "Activity enter transition (overridePendingTransition) should use "+enterAnim+".xml",3);
            String exitAnim= "fade_out";
            testItem(0,shadowActivity.getPendingTransitionExitAnimationResourceId()==rsc.getResourceId(exitAnim,"anim"),
                    "Activity exit transition (overridePendingTransition) should use "+exitAnim+".xml",3);

            System.out.println("SUCCESS");
        });
    }

    @Test
    public void check_05_RecyclerItem_LongClick() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.moveToState(Lifecycle.State.STARTED);
        main.onActivity(activity -> {
            RecyclerView rv = (RecyclerView) getViewFromActivity(rvName, activity);

            for (int i=0; i<rv.getChildCount(); i++) {
                View view = longClickAnimal(i,rv);

                //String animal = ((TextView)getViewFromLayout("animalTitle",view)).getText().toString();
                Intent intent = Shadows.shadowOf(activity).getNextStartedActivity();
                testItem(0,intent,"Event long click in RecyclerView should not open another activity",5);
            }
        });
    }

    @Test
    public void check_06_Simulate_Move_Fishes() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.moveToState(Lifecycle.State.STARTED);
        main.onActivity(activity -> {
            RecyclerView rv = (RecyclerView) getViewFromActivity(rvName, activity);
            ArrayList<String> listTitle = getListAnimalTitles(activity);
            testMoveItemRecycler(activity,rv,0,3,listTitle);
        });
    }

    @Test
    public void check_07_Simulate_Move_Amphibians() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.moveToState(Lifecycle.State.STARTED);
        main.onActivity(activity -> {
            RecyclerView rv = (RecyclerView) getViewFromActivity(rvName, activity);
            ArrayList<String> listTitle = getListAnimalTitles(activity);
            testMoveItemRecycler(activity,rv,1,4,listTitle);
        });
    }

    @Test
    public void check_08_Simulate_Move_Reptiles() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.moveToState(Lifecycle.State.STARTED);
        main.onActivity(activity -> {
            RecyclerView rv = (RecyclerView) getViewFromActivity(rvName, activity);
            ArrayList<String> listTitle = getListAnimalTitles(activity);
            testMoveItemRecycler(activity,rv,2,0,listTitle);
        });
    }

    @Test
    public void check_09_Simulate_Move_Birds() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.moveToState(Lifecycle.State.STARTED);
        main.onActivity(activity -> {
            RecyclerView rv = (RecyclerView) getViewFromActivity(rvName, activity);
            ArrayList<String> listTitle = getListAnimalTitles(activity);
            testMoveItemRecycler(activity,rv,3,5,listTitle);
        });
    }

    @Test
    public void check_10_Simulate_Move_Mammals() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.moveToState(Lifecycle.State.STARTED);
        main.onActivity(activity -> {
            RecyclerView rv = (RecyclerView) getViewFromActivity(rvName, activity);
            ArrayList<String> listTitle = getListAnimalTitles(activity);
            testMoveItemRecycler(activity,rv,4,1,listTitle);
        });
    }

    @Test
    public void check_11_Simulate_Move_Inverts() {
        ActivityScenario<MainActivity> main = b411.launchMainApp();
        main.moveToState(Lifecycle.State.STARTED);
        main.onActivity(activity -> {
            RecyclerView rv = (RecyclerView) getViewFromActivity(rvName, activity);
            ArrayList<String> listTitle = getListAnimalTitles(activity);
            testMoveItemRecycler(activity,rv,5,1,listTitle);
        });
    }

    private void testItemRecyclerView(View view, int no, String title, int color, String icon, String info, ResourceTest rsc) {
        TextView text = (TextView) getViewFromLayout("animalTitle", view);
        testItem(title,text.getText().toString(),
                "Title "+no+" in RecyclerView should be "+title,1);
        testItem(0,((ColorDrawable)text.getBackground()).getColor()==color,
                "Background color of title "+title+" should be "+rsc.getHexColor(color),3);

        ImageView iconView = (ImageView) getViewFromLayout("animalIcon", view);
        Drawable anim = iconView.getBackground();
        testItem("AnimationDrawable",anim.getClass().getSimpleName(),
                "android:src in 'animalIcon' for "+title+" should be Animation Drawable",1);
        ShadowDrawable animShadow = Shadows.shadowOf(anim);
        testItem(0,animShadow.getCreatedFromResId()==rsc.getResourceId(icon,"drawable"),
                "android:src in 'animalIcon' "+title+" should be "+icon+".xml",3);

        text = (TextView) getViewFromLayout("animalInfo", view);
        testItem(info,text.getText().toString(),
                "Animal info "+no+" for "+title+" should be "+info,1);
    }

    public static View clickAnimal (int idx, RecyclerView rv) {
        int rvChild = rv.getChildCount();
        View view;

        if (idx < rvChild) {
            view = Objects.requireNonNull(rv.findViewHolderForAdapterPosition(idx)).itemView;
        } else {
            rv.scrollToPosition(rvChild);
            view = rv.getChildAt(rvChild-1);
        }
        view.performClick();
        return view;
    }

    public static View longClickAnimal (int idx, RecyclerView rv) {
        int rvChild = rv.getChildCount();
        View view;

        if (idx < rvChild) {
            view = Objects.requireNonNull(rv.findViewHolderForAdapterPosition(idx)).itemView;
        } else {
            rv.scrollToPosition(rvChild);
            view = rv.getChildAt(rvChild-1);
        }
        view.performLongClick();
        //longClickView(view);
        return view;
    }

    public static void longClickView(View v) {
        final int viewWidth = v.getWidth();
        final int viewHeight = v.getHeight();

        final float x = viewWidth / 2.0f;
        final float y = viewHeight / 2.0f;

        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis();

        MotionEvent event = MotionEvent.obtain(downTime, eventTime,
                MotionEvent.ACTION_DOWN, x, y, 0);
        v.onTouchEvent(event);

        eventTime = SystemClock.uptimeMillis();
        final int touchSlop = ViewConfiguration.get(v.getContext()).getScaledTouchSlop();
        event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_MOVE,
                x + touchSlop / 2, y + touchSlop / 2, 0);
        v.onTouchEvent(event);

        v.postDelayed(() -> {
            long eventTime2 = SystemClock.uptimeMillis();
            MotionEvent event2 = MotionEvent.obtain(downTime, eventTime2, MotionEvent.ACTION_UP, x, y, 0);
            v.onTouchEvent(event2);
        }, (long) (ViewConfiguration.getLongPressTimeout() * 1.5f));
    }

    public static String clickAnimalGetTitle (int idx, RecyclerView rv) {
        View view = clickAnimal(idx,rv);
        TextView title = view.findViewById(R.id.animalTitle);
        return title.getText().toString();
    }

    private ArrayList<String> getListItemRecycler(Activity activity, RecyclerView rv) {
        ResourceTest rsc = new ResourceTest(activity.getResources());

        ArrayList<String> titleList = new ArrayList<String>();
        int rvItem = Objects.requireNonNull(rv.getAdapter()).getItemCount();
        int rvChild = rv.getChildCount();

        for (int i = 0; i < rvChild; i++) {
            int no = i+1;
            View view = rv.getChildAt(i);//Objects.requireNonNull(rv.findViewHolderForAdapterPosition(i)).itemView;
            String title = ((TextView)getViewFromLayout("animalTitle",view)).getText().toString();
            titleList.add(title);
            //System.out.println("Id: "+i+". "+title);
        }

        if (rvItem>rv.getChildCount()) {
            rv.scrollToPosition(rvItem-1);
            for (int i = 0; i < rvItem - rvChild; i++) {
                int no = rvChild+i;
                View view = rv.getChildAt(rvChild-1+i);
                String title = ((TextView)getViewFromLayout("animalTitle",view)).getText().toString();
                titleList.add(title);
            }
            rv.scrollToPosition(0);
        }
        //rv.scrollToPosition(0);
        return titleList;
    }

    private ArrayList<String> getListAnimalTitles(Activity activity) {
        String[] titles = getStringArrayResource("animal_titles",activity);
        ArrayList<String> list = new ArrayList<String>();
        Collections.addAll(list, titles);
        list.add("Invertebrates");
        return list;
    }

    private void testMoveItemRecycler(Activity activity, RecyclerView rv, int itemIdx, int destIdx, ArrayList<String> listTitle) {
        RecyclerView.ViewHolder v1 = rv.getChildViewHolder(rv.getChildAt(itemIdx));
        RecyclerView.ViewHolder v2 = rv.getChildViewHolder(rv.getChildAt(destIdx));
        //RecyclerView.ViewHolder v1 = Objects.requireNonNull(rv.findViewHolderForAdapterPosition(0));
        //RecyclerView.ViewHolder v2 = Objects.requireNonNull(rv.findViewHolderForAdapterPosition(3));

        Objects.requireNonNull(rv.getAdapter()).notifyItemMoved(v1.getAdapterPosition(), v2.getAdapterPosition());

        if (itemIdx<destIdx) {
            listTitle.add(destIdx+1, listTitle.get(itemIdx));
            listTitle.remove(itemIdx);
        } else {
            listTitle.add(destIdx, listTitle.get(itemIdx));
            listTitle.remove(itemIdx+1);
        }
        ArrayList<String> actual = getListItemRecycler(activity,rv);
        testItem(listTitle.toString(), actual.toString(),
                "Moving fishes gives wrong item contents",1);
        //System.out.println(listTitle.toString()+"-\n"+getListItemRecycler(activity,rv).toString());
    }
}
