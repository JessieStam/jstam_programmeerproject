package jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import jstam.programmeerproject_scubascan.Activities.LogDetailsActivity;
import jstam.programmeerproject_scubascan.Activities.NewDiveActivity;
import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.FinishedFragments.FirstFragFinished;
import jstam.programmeerproject_scubascan.R;

/**
 * Scuba Scan - FirstFragUnfinished
 *
 * Jessie Stam
 * 10560599
 *
 * This fragment parses the user for a date, country, dive spot and buddy. This data is then given
 * to NewDiveActivity or LogDetailsActivity.
 */
@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class FirstFragUnfinished extends Fragment {

    String date, country, dive_spot, buddy;

    EditText date_input, country_input, dive_spot_input, buddy_input;
    Button next_button;
    ImageView saved_image;

    FragmentActivity listener;
    FirstNewDiveFragmentListener activityCommander;

    /* Listener that calls a function in NewDiveActivity or LogDetailsActivity. */
    public interface FirstNewDiveFragmentListener {
        public void saveGeneralData(String date, String country, String dive_spot, String buddy);
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
            activityCommander = (FirstNewDiveFragmentListener) context;
        }catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.frag_first_unfinished, parent, false);

        date_input = (EditText) view.findViewById(R.id.newdive_date_input);
        country_input = (EditText) view.findViewById(R.id.newdive_country_input);
        dive_spot_input = (EditText) view.findViewById(R.id.newdive_divespot_input);
        buddy_input = (EditText) view.findViewById(R.id.newdive_buddy_input);

        next_button = (Button) view.findViewById(R.id.frag_firstnewdive_button);
        saved_image = (ImageView) view.findViewById(R.id.data_saved_image1);

        next_button.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        buttonClicked(v);
                    }
                }
        );
        return view;
    }

    /* When button is clicked, parse data to NewDiveActivity of LogDetailsActivity. */
    public void buttonClicked(View view) {

        date = date_input.getText().toString();
        country = country_input.getText().toString();
        dive_spot = dive_spot_input.getText().toString();
        buddy = buddy_input.getText().toString();

        if (validateForm()) {
            if (saved_image.getVisibility() == View.INVISIBLE) {
                saved_image.setVisibility(View.VISIBLE);
                next_button.setText(R.string.edit);
            }
            else {
                saved_image.setVisibility(View.INVISIBLE);
                next_button.setText(R.string.save);
            }

            // save data in activity
            activityCommander.saveGeneralData(date, country, dive_spot, buddy);

            // change fragment to finished fragment with required data
            Fragment new_frag = new FirstFragFinished();

            Bundle data_input = new Bundle();
            FragmentTransaction trans = getFragmentManager().beginTransaction();

            data_input.putString("date", date);
            data_input.putString("country", country);
            data_input.putString("dive_spot", dive_spot);
            data_input.putString("buddy", buddy);

            new_frag.setArguments(data_input);

            trans.replace(R.id.root_frame, new_frag);
            trans.commit();
        }
    }

    /* Validate user's data. */
    private boolean validateForm() {
        boolean valid = true;

        // validate date
        if (TextUtils.isEmpty(date)) {
            date_input.setError(getString(R.string.required));
            valid = false;
        } else {
            date_input.setError(null);

            if (date.length() != 10) {
                activityCommander.showFragmentToast(getString(R.string.invalid_date));
                valid = false;
            } else if (date.charAt(2) != '-' && date.charAt(5) != '-') {
                activityCommander.showFragmentToast(getString(R.string.invalid_date));
                valid = false;
            }
        }

        // validate country
        if (TextUtils.isEmpty(country)) {
            country_input.setError(getString(R.string.required));
            valid = false;
        } else {
            country_input.setError(null);
        }

        // validate dive spot
        if (TextUtils.isEmpty(dive_spot)) {
            dive_spot_input.setError(getString(R.string.required));
            valid = false;
        } else {
            dive_spot_input.setError(null);
        }

        // validate buddy
        if (TextUtils.isEmpty(buddy)) {
            buddy_input.setError(getString(R.string.required));
            valid = false;
        } else {
            buddy_input.setError(null);
        }
        return valid;
    }
}
