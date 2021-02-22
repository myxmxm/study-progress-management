package fi.metropolia.team4studyprogressmanagement.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import fi.metropolia.team4studyprogressmanagement.fragment.CourseFragment;
import fi.metropolia.team4studyprogressmanagement.fragment.StatisticFragment;
import fi.metropolia.team4studyprogressmanagement.fragment.ToolsFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    private int numberOfTabs;

    public  PagerAdapter(@NonNull FragmentManager fm, int behavior, int numberOfTabs){
        super(fm,behavior);
        this.numberOfTabs = numberOfTabs;

    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new CourseFragment();
            case 1:
                return new ToolsFragment();
            case 2:
                return new StatisticFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
