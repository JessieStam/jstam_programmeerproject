package jstam.programmeerproject_scubascan.Helpers.Adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import jstam.programmeerproject_scubascan.Fragments.RootFragments.RootFifthFragNew;
import jstam.programmeerproject_scubascan.Fragments.RootFragments.RootFirstFragNew;
import jstam.programmeerproject_scubascan.Fragments.RootFragments.RootFourthFragNew;
import jstam.programmeerproject_scubascan.Fragments.RootFragments.RootSecondFragNew;
import jstam.programmeerproject_scubascan.Fragments.RootFragments.RootThirdFragNew;

/**
 * Created by Jessie on 18/01/2017.
 */

public class NewFragPageAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 5;
    private String tabTitles[] = new String[] { "1", "2", "3", "4", "5" };
    private Context context;

    public NewFragPageAdapter(FragmentManager frag_manager, Context context) {
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

        switch (position) {
            case 0:
                return RootFirstFragNew.newInstance(position + 1);
            case 1:
                return RootSecondFragNew.newInstance(position + 1);
            case 2:
                return RootThirdFragNew.newInstance(position + 1);
            case 3:
                return RootFourthFragNew.newInstance(position + 1);
            case 4:
                return RootFifthFragNew.newInstance(position + 1);
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
