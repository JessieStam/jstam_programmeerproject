package jstam.programmeerproject_scubascan.Helpers;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.FinishedFragments.SixthFragmentFinished;
import jstam.programmeerproject_scubascan.Fragments.RootFragments.RootFifthDetailsFragment;
import jstam.programmeerproject_scubascan.Fragments.RootFragments.RootFifthNewDiveFragment;
import jstam.programmeerproject_scubascan.Fragments.RootFragments.RootFirstDetailsFragment;
import jstam.programmeerproject_scubascan.Fragments.RootFragments.RootFirstNewDiveFragment;
import jstam.programmeerproject_scubascan.Fragments.RootFragments.RootFourthDetailsFragment;
import jstam.programmeerproject_scubascan.Fragments.RootFragments.RootFourthNewDiveFragment;
import jstam.programmeerproject_scubascan.Fragments.RootFragments.RootSecondDetailsFragment;
import jstam.programmeerproject_scubascan.Fragments.RootFragments.RootSecondNewDiveFragment;
import jstam.programmeerproject_scubascan.Fragments.RootFragments.RootThirdDetailsFragment;
import jstam.programmeerproject_scubascan.Fragments.RootFragments.RootThirdNewDiveFragment;
import jstam.programmeerproject_scubascan.Items.DiveItem;

/**
 * Created by Jessie on 28/01/2017.
 */

public class DiveLogFragmentPageAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 6;
    private String tabTitles[] = new String[] { "1", "2", "3" , "4", "5", "6" };
    private Context context;
    private String dive_number;
    private DiveItem dive_item;

    public DiveLogFragmentPageAdapter(FragmentManager frag_manager, Context context, String dive_number, DiveItem dive_item) {
        super(frag_manager);
        this.context = context;
        this.dive_number = dive_number;
        this.dive_item = dive_item;
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
                return RootFirstDetailsFragment.newInstance(position + 1, dive_number, dive_item);
            case 1:
                return RootSecondDetailsFragment.newInstance(position + 1, dive_number, dive_item);
            case 2:
                return RootThirdDetailsFragment.newInstance(position + 1, dive_number, dive_item);
            case 3:
                return RootFourthDetailsFragment.newInstance(position + 1, dive_number, dive_item);
            case 4:
                return RootFifthDetailsFragment.newInstance(position + 1, dive_number, dive_item);
            case 5:
                return SixthFragmentFinished.newInstance(position + 1, dive_number, dive_item);
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