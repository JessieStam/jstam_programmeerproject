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

import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments.FirstFragUnfinished;
import jstam.programmeerproject_scubascan.R;

/**
 * Scuba Scan - RootFirstFragNew
 *
 * Jessie Stam
 * 10560599
 *
 * The container fragment that stores the first new dive fragments.
 */
public class RootFirstFragNew extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;

    /* Initializes root fragment */
    public static RootFirstFragNew newInstance(int page) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        RootFirstFragNew fragment = new RootFirstFragNew();
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPage = getArguments().getInt(ARG_PAGE);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_root_first, container, false);

        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();

        transaction.replace(R.id.root_frame, new FirstFragUnfinished());
        transaction.commit();

        return view;
    }
}
