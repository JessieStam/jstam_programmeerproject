package jstam.programmeerproject_scubascan.Fragments.RootFragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.FinishedFragments.ThirdFragFinished;
import jstam.programmeerproject_scubascan.Items.DiveItem;
import jstam.programmeerproject_scubascan.R;

/**
 * Scuba Scan - RootThirdFragDetail
 *
 * Jessie Stam
 * 10560599
 *
 * The container fragment that stores the third display fragments.
 */
public class RootThirdFragDetail extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_NUMBER = "ARG_NUMBER";
    public static final String ARG_DIVEITEM = "ARG_DIVEITEM";

    private String dive_number;
    private DiveItem dive_item;

    private int mPage;

    /* Initializes root fragment */
    public static RootThirdFragDetail newInstance(int page, String dive_number, DiveItem dive_item) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString(ARG_NUMBER, dive_number);
        args.putParcelable(ARG_DIVEITEM, dive_item);
        RootThirdFragDetail fragment = new RootThirdFragDetail();
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPage = getArguments().getInt(ARG_PAGE);
        dive_number = getArguments().getString(ARG_NUMBER);
        dive_item = getArguments().getParcelable(ARG_DIVEITEM);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_root_third, container, false);

        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();

        // give dive name and DiveItem to fragment
        Bundle dive_bundle = new Bundle();
        dive_bundle.putString("dive_number", dive_number);
        dive_bundle.putParcelable("dive_item", dive_item);

        ThirdFragFinished new_frag = new ThirdFragFinished();
        new_frag.setArguments(dive_bundle);

        transaction.replace(R.id.root_frame_third, new_frag);
        transaction.commit();

        return view;
    }
}
