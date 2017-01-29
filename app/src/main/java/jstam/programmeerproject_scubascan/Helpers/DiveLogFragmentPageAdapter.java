package jstam.programmeerproject_scubascan.Helpers;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import jstam.programmeerproject_scubascan.Fragments.RootFragments.RootFifthNewDiveFragment;
import jstam.programmeerproject_scubascan.Fragments.RootFragments.RootFirstDetailsFragment;
import jstam.programmeerproject_scubascan.Fragments.RootFragments.RootFirstNewDiveFragment;
import jstam.programmeerproject_scubascan.Fragments.RootFragments.RootFourthNewDiveFragment;
import jstam.programmeerproject_scubascan.Fragments.RootFragments.RootSecondNewDiveFragment;
import jstam.programmeerproject_scubascan.Fragments.RootFragments.RootThirdNewDiveFragment;

/**
 * Created by Jessie on 28/01/2017.
 */

public class DiveLogFragmentPageAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "One", "Two" }; //, "Three", "Four", "Five" };
    private Context context;
    private String dive_number;

    public DiveLogFragmentPageAdapter(FragmentManager frag_manager, Context context, String dive_number) {
        super(frag_manager);
        this.context = context;
        this.dive_number = dive_number;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Fragment getItem(int position) {

        Log.d("test6", "dive numer in divelogfragmentpageadapter: " + dive_number);

        switch (position) {
            case 0:
                return RootFirstDetailsFragment.newInstance(position + 1, dive_number);
            case 1:
                return RootSecondNewDiveFragment.newInstance(position + 1);
//            case 2:
//                return RootThirdNewDiveFragment.newInstance(position + 1);
//            case 3:
//                return RootFourthNewDiveFragment.newInstance(position + 1);
//            case 4:
//                return RootFifthNewDiveFragment.newInstance(position + 1);
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}