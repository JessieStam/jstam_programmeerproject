package jstam.programmeerproject_scubascan.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jstam.programmeerproject_scubascan.R;

/**
 * Created by Jessie on 21/01/2017.
 */

public class RootFirstNewDiveFragment extends Fragment {

    private static final String TAG = "RootFragment";

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    //private String fragment;
    //int cur_check_pos = 0;


    public static RootFirstNewDiveFragment newInstance(int page) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        RootFirstNewDiveFragment fragment = new RootFirstNewDiveFragment();
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
        transaction.replace(R.id.root_frame, new FirstNewDiveFragment());

        transaction.commit();

        Log.d("test", "In the root");

        return view;
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        outState.putString("fragment", fragment);
//
//        outState.putInt("curChoice", cur_check_pos);
//
//    }

}
