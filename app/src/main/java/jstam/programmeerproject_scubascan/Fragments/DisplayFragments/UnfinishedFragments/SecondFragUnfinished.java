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
import jstam.programmeerproject_scubascan.Activities.LogDetailsActivity;
import jstam.programmeerproject_scubascan.Activities.NewDiveActivity;
import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.FinishedFragments.SecondFragFinished;
import jstam.programmeerproject_scubascan.R;

/**
 * Scuba Scan - SecondFragUnfinished
 *
 * Jessie Stam
 * 10560599
 *
 * This fragment parses the user for a temperatures, visibility, dive type and water type. This data
 * is then given to NewDiveActivity or LogDetailsActivity.
 */
public class SecondFragUnfinished extends Fragment implements View.OnClickListener {

    String air_temp, surface_temp, bottom_temp, water_type, visibility, dive_type;
    EditText air_temp_input, surface_temp_input, bottom_temp_input, visibility_input;
    CheckBox salty, sweet, shore, boat;

    Button save_button;
    ImageView saved_image;

    FragmentActivity listener;
    SecondFragUnfinished.SecondNewDiveFragmentListener activityCommander;

    public interface SecondNewDiveFragmentListener {
        public void saveCircumstancesData(String air_temp, String surface_temp, String bottom_temp,
                                         String visibility, String water_type, String dive_type);
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
            activityCommander = (SecondFragUnfinished.SecondNewDiveFragmentListener) context;
        }catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.frag_second_unfinished, parent, false);

        air_temp_input = (EditText) view.findViewById(R.id.newdive_temp_air_input);
        surface_temp_input = (EditText) view.findViewById(R.id.newdive_temp_surface_input);
        bottom_temp_input = (EditText) view.findViewById(R.id.newdive_temp_bottom_input);
        visibility_input = (EditText) view.findViewById(R.id.newdive_visibility_input);

        water_type = "";
        dive_type = "";

        salty = (CheckBox) view.findViewById(R.id.frag_secondnewdive_checkbox_salty);
        sweet = (CheckBox) view.findViewById(R.id.frag_secondnewdive_checkbox_sweet);
        shore = (CheckBox) view.findViewById(R.id.frag_secondnewdive_checkbox_shore);
        boat = (CheckBox) view.findViewById(R.id.frag_secondnewdive_checkbox_boat);

        salty.setOnClickListener(this);
        sweet.setOnClickListener(this);
        shore.setOnClickListener(this);
        boat.setOnClickListener(this);

        save_button = (Button) view.findViewById(R.id.frag_secondnewdive_button);
        save_button.setOnClickListener(this);

        saved_image = (ImageView) view.findViewById(R.id.data_saved_image2);

        return view;
    }

    /* When button is clicked, parse data to NewDiveActivity of LogDetailsActivity. */
    @Override
    public void onClick(View view) {

        if (view == salty) {
            water_type = "salty";
            sweet.setChecked(false);
        } else if (view == sweet) {
            water_type = "sweet";
            salty.setChecked(false);
        } else if (view == shore) {
            dive_type = "boat";
            boat.setChecked(false);
        } else if (view == boat) {
            dive_type = "boat";
            shore.setChecked(false);
        } else if (view == save_button) {

            air_temp = air_temp_input.getText().toString();
            surface_temp = surface_temp_input.getText().toString();
            bottom_temp = bottom_temp_input.getText().toString();
            visibility = visibility_input.getText().toString();

            if (validateForm()) {
                if (saved_image.getVisibility() == View.INVISIBLE) {
                    saved_image.setVisibility(View.VISIBLE);
                    save_button.setText(R.string.edit);
                }
                else {
                    saved_image.setVisibility(View.INVISIBLE);
                    save_button.setText(R.string.save);
                }

                // save data in activity
                activityCommander.saveCircumstancesData(air_temp, surface_temp, bottom_temp,
                        visibility, water_type, dive_type);

                Fragment new_frag = new SecondFragFinished();

                Bundle data_input = new Bundle();
                FragmentTransaction trans = getFragmentManager().beginTransaction();

                data_input.putString("air_temp", air_temp);
                data_input.putString("surface_temp", surface_temp);
                data_input.putString("bottom_temp", bottom_temp);
                data_input.putString("visibility", visibility);
                data_input.putString("water_type", water_type);
                data_input.putString("dive_type", dive_type);

                new_frag.setArguments(data_input);

                trans.replace(R.id.root_frame_second, new_frag);
                trans.commit();
            }
        }
    }

    /* Validate user's data. */
    private boolean validateForm() {
        boolean valid = true;

        // validate air temperature
        if (TextUtils.isEmpty(air_temp)) {
            air_temp_input.setError(getString(R.string.required));
            valid = false;
        } else {
            air_temp_input.setError(null);
        }

        // validate surface temperature
        if (TextUtils.isEmpty(surface_temp)) {
            surface_temp_input.setError(getString(R.string.required));
            valid = false;
        } else {
            surface_temp_input.setError(null);
        }

        // validate bottom temperature
        if (TextUtils.isEmpty(bottom_temp)) {
            bottom_temp_input.setError(getString(R.string.required));
            valid = false;
        } else {
            bottom_temp_input.setError(null);
        }

        // validate visibility
        if (TextUtils.isEmpty(visibility)) {
            visibility_input.setError(getString(R.string.required));
            valid = false;
        } else {
            visibility_input.setError(null);
        }

        // validate water type and dive type
        if (water_type.equals("") || dive_type.equals("")) {
            activityCommander.showFragmentToast(getString(R.string.invalid_checkbox));
            valid = false;
        }
        return valid;
    }
}
