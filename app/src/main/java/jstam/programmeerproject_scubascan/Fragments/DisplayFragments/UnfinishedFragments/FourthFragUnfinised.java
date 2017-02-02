package jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

import jstam.programmeerproject_scubascan.Activities.LogDetailsActivity;
import jstam.programmeerproject_scubascan.Activities.NewDiveActivity;
import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.FinishedFragments.FourthFragFinished;
import jstam.programmeerproject_scubascan.R;

/**
 * Scuba Scan - FourthFragUnfinished
 *
 * Jessie Stam
 * 10560599
 *
 * This fragment parses the user for a the start time, the end time, the bottle pressure at the
 * start and end of the dive, the depth and a safety stop. This data is then given to
 * NewDiveActivity or LogDetailsActivity.
 */
public class FourthFragUnfinised extends Fragment implements View.OnClickListener {

    String time_in, time_out, pressure_in, pressure_out, depth, safetystop;
    ArrayList<String> technicaldata_list;

    EditText time_in_input, time_out_input, pressure_in_input, pressure_out_input, depth_input;

    CheckBox yes, no;
    Button save_button;
    ImageView saved_image;

    FragmentActivity listener;

    FourthFragUnfinised.FourthNewDiveFragmentListener activityCommander;

    /* Listener that calls a function in NewDiveActivity or LogDetailsActivity. */
    public interface FourthNewDiveFragmentListener {
        public void saveTechnicalData(String time_in, String time_out, String pressure_in,
                                      String pressure_out, String depth, String safetystop);
        public void showFragmentToast(String toast);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NewDiveActivity){
            this.listener = (FragmentActivity) context;
        } else if (context instanceof LogDetailsActivity) {
            this.listener = (FragmentActivity) context;
        }

        try{
            activityCommander = (FourthFragUnfinised.FourthNewDiveFragmentListener) context;
        }catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.frag_fourth_unfinished, parent, false);

        time_in_input = (EditText) view.findViewById(R.id.newdive_timein_input);
        time_out_input = (EditText) view.findViewById(R.id.newdive_timeout_input);
        pressure_in_input = (EditText) view.findViewById(R.id.newdive_pressurein_input);
        pressure_out_input = (EditText) view.findViewById(R.id.newdive_pressureout_input);
        depth_input = (EditText) view.findViewById(R.id.newdive_depth_input);

        technicaldata_list = new ArrayList<>();

        safetystop = "";

        save_button = (Button) view.findViewById(R.id.frag_fourthnewdive_button);
        saved_image = (ImageView) view.findViewById(R.id.data_saved_image4);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        save_button.setOnClickListener(this);
        yes = (CheckBox) view.findViewById(R.id.frag_fourthnewdive_checkbox_yes);
        no = (CheckBox) view.findViewById(R.id.frag_fourthnewdive_checkbox_no);

        view.findViewById(R.id.frag_fourthnewdive_checkbox_yes).setOnClickListener(this);
        view.findViewById(R.id.frag_fourthnewdive_checkbox_no).setOnClickListener(this);

    }

    /* When button is clicked, parse data to NewDiveActivity of LogDetailsActivity. */
    @Override
    public void onClick(View view) {

        if (view == no) {
            yes.setChecked(false);
            safetystop = getString(R.string.no_safety);
        } else if (view == yes) {
            no.setChecked(false);
            safetystop = getString(R.string.safety);
        }
        else if (view == save_button) {

            time_in = time_in_input.getText().toString();
            time_out = time_out_input.getText().toString();
            pressure_in = pressure_in_input.getText().toString();
            pressure_out = pressure_out_input.getText().toString();
            depth = depth_input.getText().toString();

            technicaldata_list.add(time_in);
            technicaldata_list.add(time_out);
            technicaldata_list.add(pressure_in);
            technicaldata_list.add(pressure_out);
            technicaldata_list.add(depth);

            if (validateForm()) {
                technicaldata_list.add(safetystop);

                if (saved_image.getVisibility() == View.INVISIBLE) {
                    saved_image.setVisibility(View.VISIBLE);
                    save_button.setText(R.string.edit);
                }
                else {
                    saved_image.setVisibility(View.INVISIBLE);
                    save_button.setText(R.string.save);
                }

                // save data in activity
                activityCommander.saveTechnicalData(time_in, time_out, pressure_in, pressure_out,
                        depth, safetystop);

                // change fragment to finished fragment with required data
                Fragment new_frag = new FourthFragFinished();
                Bundle data_input = new Bundle();
                FragmentTransaction trans = getFragmentManager().beginTransaction();

                data_input.putStringArrayList("technicaldata", technicaldata_list);
                new_frag.setArguments(data_input);

                trans.replace(R.id.root_frame_fourth, new_frag);
                trans.commit();

            }
        }
    }

    /* Validate user's data. */
    private boolean validateForm() {
        boolean valid = true;

        // validate time in
        if (TextUtils.isEmpty(time_in)) {
            time_in_input.setError(getString(R.string.required));
            valid = false;
        } else {
            time_in_input.setError(null);

            if (time_in.length() != 5) {
                activityCommander.showFragmentToast(getString(R.string.invalid_time));
                valid = false;
            } else if (time_in.charAt(2) != ':') {
                activityCommander.showFragmentToast(getString(R.string.invalid_time));
                valid = false;
            }
        }

        // validate time out
        if (TextUtils.isEmpty(time_out)) {
            time_out_input.setError(getString(R.string.required));
            valid = false;
        } else {
            time_out_input.setError(null);

            if (time_out.length() != 5) {
                activityCommander.showFragmentToast(getString(R.string.invalid_time));
                valid = false;
            } else if (time_out.charAt(2) != ':') {
                activityCommander.showFragmentToast(getString(R.string.invalid_time));
                valid = false;
            }
        }

        // validate pressure in
        if (TextUtils.isEmpty(pressure_in)) {
            pressure_in_input.setError(getString(R.string.required));
            valid = false;
        } else {
            pressure_in_input.setError(null);
        }

        // validate pressure out
        if (TextUtils.isEmpty(pressure_out)) {
            pressure_out_input.setError(getString(R.string.required));
            valid = false;
        } else {
            pressure_out_input.setError(null);
        }

        // validate depth
        if (TextUtils.isEmpty(depth)) {
            depth_input.setError(getString(R.string.required));
            valid = false;
        } else {
            depth_input.setError(null);

            int depth_int = Integer.valueOf(depth);

            if (depth_int > 40) {
                activityCommander.showFragmentToast(getString(R.string.invalid_depth));
                valid = false;
            }
        }

        // validate safety stop
        if (safetystop.equals("")) {
            activityCommander.showFragmentToast(getString(R.string.invalid_safertystop));
            valid = false;
        }
        return valid;
    }
}
