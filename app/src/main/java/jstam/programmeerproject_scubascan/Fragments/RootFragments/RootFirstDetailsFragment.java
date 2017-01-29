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

import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.FinishedFragments.FirstNewDiveFragmentFinished;
import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments.FirstNewDiveFragment;
import jstam.programmeerproject_scubascan.R;

/**
 * Created by Jessie on 28/01/2017.
 */

public class RootFirstDetailsFragment extends Fragment {

    private static final String TAG = "RootFragment";

    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_NUMBER = "ARG_NUMBER";

    private String dive_number;

    private int mPage;

    //private String fragment;
    //int cur_check_pos = 0;


    public static RootFirstDetailsFragment newInstance(int page, String dive_number) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString(ARG_NUMBER, dive_number);
        RootFirstDetailsFragment fragment = new RootFirstDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ArrayList<Thing> things = new ArrayList<Thing>();
        //adapter = new ThingsAdapter(getActivity(), things);
        mPage = getArguments().getInt(ARG_PAGE);
        dive_number = getArguments().getString(ARG_NUMBER);

//        if (savedInstanceState == null) {
//            Log.d("test", "oncreate root instance is null");
//
//            cur_check_pos = savedInstanceState.getInt("curChoice", 0);
//
//            fragment = "unfinished";
//        } else {
//            fragment = savedInstanceState.getString("fragment");
//        }

    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		/* Inflate the layout for this fragment */
        View view = inflater.inflate(R.layout.fragment_root, container, false);

        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
		/*
		 * When this container fragment is created, we fill it with our first
		 * "real" fragment
		 */

        Log.d("test6", "dive numer in rootfirstdetailsfragment: " + dive_number);

        Bundle dive_number_bundle = new Bundle();
        dive_number_bundle.putString("dive_number", dive_number);
        // set Fragmentclass Arguments
        FirstNewDiveFragmentFinished new_frag = new FirstNewDiveFragmentFinished();
        new_frag.setArguments(dive_number_bundle);

        transaction.replace(R.id.root_frame, new_frag);

        transaction.commit();

        Log.d("test", "In the root");

        return view;
    }

}
