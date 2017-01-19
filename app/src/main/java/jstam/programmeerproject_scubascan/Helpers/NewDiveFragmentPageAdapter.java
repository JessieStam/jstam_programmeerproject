package jstam.programmeerproject_scubascan.Helpers;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import jstam.programmeerproject_scubascan.Fragments.FirstNewDiveFragment;
import jstam.programmeerproject_scubascan.Fragments.FourthNewDiveFragment;
import jstam.programmeerproject_scubascan.Fragments.SecondNewDiveFragment;
import jstam.programmeerproject_scubascan.Fragments.ThirdNewDiveFragment;

/**
 * Created by Jessie on 18/01/2017.
 */

public class NewDiveFragmentPageAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[] { "One", "Two", "Three", "Four" };
    private Context context;

    public NewDiveFragmentPageAdapter(FragmentManager frag_manager, Context context) {
        super(frag_manager);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Fragment getItem(int position) {

        String pos = String.valueOf(position);

        switch (position) {
            case 0:
                return FirstNewDiveFragment.newInstance(position + 1);
            case 1:
                return SecondNewDiveFragment.newInstance(position + 1);
            case 2:
                return ThirdNewDiveFragment.newInstance(position + 1);
            case 3:
                return FourthNewDiveFragment.newInstance(position + 1);
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
