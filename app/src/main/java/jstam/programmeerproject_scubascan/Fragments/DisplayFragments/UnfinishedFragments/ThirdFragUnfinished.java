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
import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.FinishedFragments.ThirdFragFinished;
import jstam.programmeerproject_scubascan.R;

/**
 * Scuba Scan - ThirdFragUnfinished
 *
 * Jessie Stam
 * 10560599
 *
 * This fragment parses the user for a equipment. This data is then given to NewDiveActivity or
 * LogDetailsActivity.
 */
public class ThirdFragUnfinished extends Fragment implements View.OnClickListener {

    ArrayList<String> clothes;

    String lead = "";
    EditText lead_input;

    Button save_button;

    ImageView saved_image;

    //ThingsAdapter adapter;
    FragmentActivity listener;
    ThirdFragUnfinished.ThirdNewDiveFragmentListener activityCommander;

    /* Listener that calls a function in NewDiveActivity or LogDetailsActivity. */
    public interface ThirdNewDiveFragmentListener {
        public void saveEquipmentData(String lead, ArrayList<String> clothes);
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
            activityCommander = (ThirdFragUnfinished.ThirdNewDiveFragmentListener) context;
        }catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.frag_third_unfinished, parent, false);

        clothes = new ArrayList<>();

        lead_input = (EditText) view.findViewById(R.id.newdive_lead_input);
        save_button = (Button) view.findViewById(R.id.frag_thirdnewdive_button);
        saved_image = (ImageView) view.findViewById(R.id.data_saved_image3);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        save_button.setOnClickListener(this);
        view.findViewById(R.id.frag_thirdnewdive_checkbox_swimsuit).setOnClickListener(this);
        view.findViewById(R.id.frag_thirdnewdive_checkbox_shorty).setOnClickListener(this);
        view.findViewById(R.id.frag_thirdnewdive_checkbox_wetsuit).setOnClickListener(this);
        view.findViewById(R.id.frag_thirdnewdive_checkbox_drysuit).setOnClickListener(this);
        view.findViewById(R.id.frag_thirdnewdive_checkbox_hood).setOnClickListener(this);
        view.findViewById(R.id.frag_thirdnewdive_checkbox_pair0of0gloves).setOnClickListener(this);
        view.findViewById(R.id.frag_thirdnewdive_checkbox_set0of0fins).setOnClickListener(this);
        view.findViewById(R.id.frag_thirdnewdive_checkbox_pair0of0shoes).setOnClickListener(this);
        view.findViewById(R.id.frag_thirdnewdive_checkbox_flashlight).setOnClickListener(this);
        view.findViewById(R.id.frag_thirdnewdive_checkbox_mask).setOnClickListener(this);
    }

    /* When button is clicked, parse data to NewDiveActivity of LogDetailsActivity. */
    @Override
    public void onClick(View view) {

        int id = view.getId();

        // if clicked item is checkbox, check if item is in list
        if (id == R.id.frag_thirdnewdive_checkbox_swimsuit || id == R.id.frag_thirdnewdive_checkbox_shorty
                || id == R.id.frag_thirdnewdive_checkbox_wetsuit || id == R.id.frag_thirdnewdive_checkbox_drysuit
                || id == R.id.frag_thirdnewdive_checkbox_hood || id == R.id.frag_thirdnewdive_checkbox_pair0of0gloves
                || id == R.id.frag_thirdnewdive_checkbox_set0of0fins || id == R.id.frag_thirdnewdive_checkbox_pair0of0shoes
                || id == R.id.frag_thirdnewdive_checkbox_flashlight || id == R.id.frag_thirdnewdive_checkbox_mask) {

            String item_name = getResources().getResourceEntryName(id)
                    .replace("frag_thirdnewdive_checkbox_", "").replace("0", " ");

            checkIfInList(item_name);

        } else if (view == save_button) {

            lead = lead_input.getText().toString();

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
                activityCommander.saveEquipmentData(lead, clothes);

                // change fragment to finished fragment with required data
                Fragment new_frag = new ThirdFragFinished();
                Bundle data_input = new Bundle();
                FragmentTransaction trans = getFragmentManager().beginTransaction();

                data_input.putString("lead", lead);
                data_input.putStringArrayList("clothes_strings", clothes);

                new_frag.setArguments(data_input);

                trans.replace(R.id.root_frame_third, new_frag);
                trans.commit();

            }
        }
    }

    /* Checks is equipment item is in the list, if not, it's added, if it is, it's removed. */
    public void checkIfInList(String item_name) {

        boolean in_list = false;
        String clothes_item = "";

        for (String item : clothes) {
            if (item.equals(item_name)) {
                in_list = true;
                clothes_item = item;
            }
        }

        if (in_list) {
            clothes.remove(clothes_item);
        } else {
            clothes.add(item_name);
        }
    }

    /* Validate user's data. */
    private boolean validateForm() {
        boolean valid = true;

        // validate lead
        if (TextUtils.isEmpty(lead)) {
            lead_input.setError(getString(R.string.required));
            valid = false;
        } else {
            lead_input.setError(null);
        }
        return valid;
    }
}
