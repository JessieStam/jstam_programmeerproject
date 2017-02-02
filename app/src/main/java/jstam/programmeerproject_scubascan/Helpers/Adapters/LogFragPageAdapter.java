package jstam.programmeerproject_scubascan.Helpers.Adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.FinishedFragments.SixthFragFinished;
import jstam.programmeerproject_scubascan.Fragments.RootFragments.RootFifthFragDetail;
import jstam.programmeerproject_scubascan.Fragments.RootFragments.RootFirstFragDetail;
import jstam.programmeerproject_scubascan.Fragments.RootFragments.RootFourthFragDetail;
import jstam.programmeerproject_scubascan.Fragments.RootFragments.RootSecondFragDetail;
import jstam.programmeerproject_scubascan.Fragments.RootFragments.RootThirdFragDetail;
import jstam.programmeerproject_scubascan.Items.DiveItem;

/**
 * Created by Jessie on 28/01/2017.
 */

public class LogFragPageAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 6;
    private String tabTitles[] = new String[] { "1", "2", "3" , "4", "5", "6" };
    private Context context;
    private String dive_number;
    private DiveItem dive_item;

    public LogFragPageAdapter(FragmentManager frag_manager, Context context, String dive_number, DiveItem dive_item) {
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
                return RootFirstFragDetail.newInstance(position + 1, dive_number, dive_item);
            case 1:
                return RootSecondFragDetail.newInstance(position + 1, dive_number, dive_item);
            case 2:
                return RootThirdFragDetail.newInstance(position + 1, dive_number, dive_item);
            case 3:
                return RootFourthFragDetail.newInstance(position + 1, dive_number, dive_item);
            case 4:
                return RootFifthFragDetail.newInstance(position + 1, dive_number, dive_item);
            case 5:
                return SixthFragFinished.newInstance(position + 1, dive_number, dive_item);
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