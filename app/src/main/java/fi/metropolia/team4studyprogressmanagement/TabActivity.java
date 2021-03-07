package fi.metropolia.team4studyprogressmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import fi.metropolia.team4studyprogressmanagement.fragment.PagerAdapter;

/**
 * This activity will show a tabView with 3 fragments which include all main functions of this app,
 * such as edit, search, and statistic.
 */

public class TabActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabItem tabItemCourse, tabItemTools, tabItemStatistic;
    private PagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        initialization();
//create PagerAdapter instance
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,tabLayout.getTabCount());
//set adapter for ViewPager instance
        viewPager.setAdapter(pagerAdapter);
//change the tabs view when the tab is selected
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
//initialize toolbar, viewPager, tabLayout, and 3 tabs
    private void initialization(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabItemCourse = (TabItem) findViewById(R.id.tabItemCourse);
        tabItemTools = (TabItem) findViewById(R.id.tabItemTools);
        tabItemStatistic = (TabItem) findViewById(R.id.tabItemStatistic);
    }
}