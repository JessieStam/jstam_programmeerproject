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
import android.widget.EditText;
import android.widget.ImageView;
import jstam.programmeerproject_scubascan.Activities.LogDetailsActivity;
import jstam.programmeerproject_scubascan.Activities.NewDiveActivity;
import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.FinishedFragments.FifthFragFinished;
import jstam.programmeerproject_scubascan.R;

/**
 * Scuba Scan - FifthFragUnfinished
 *
 * Jessie Stam
 * 10560599
 *
 * This fragment parses the user for notes. This data is then given to NewDiveActivity or
 * LogDetailsActivity.
 */
public class FifthFragUnfinished extends Fragment {

    String notes;
    EditText notes_input;

    Button save_button;
    ImageView saved_image;

    FragmentActivity listener;
    FifthFragUnfinished.FifthNewDiveFragmentListener activityCommander;

    /* Listener that calls a function in NewDiveActivity or LogDetailsActivity. */
    public interface FifthNewDiveFragmentListener {
        public void saveExtraData(String notes);
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
            activityCommander = (FifthFragUnfinished.FifthNewDiveFragmentListener) context;
        }catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.frag_fifth_unfinished, parent, false);

        notes_input = (EditText) view.findViewById(R.id.newdive_notes_input);
        notes = "";

        save_button = (Button) view.findViewById(R.id.frag_fifthnewdive_button);
        saved_image = (ImageView) view.findViewById(R.id.data_saved_image5);

        save_button.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        buttonClicked(v);
                    }
                }
        );
        return view;
    }

    /* When button is clicked, parse data to NewDiveActivity of LogDetailsActivity. */
    private void buttonClicked(View v) {

        notes = notes_input.getText().toString();

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
            activityCommander.saveExtraData(notes);

            // change fragment to finished fragment with required data
            Fragment new_frag = new FifthFragFinished();
            Bundle data_input = new Bundle();
            FragmentTransaction trans = getFragmentManager().beginTransaction();

            data_input.putString("notes", notes);
            new_frag.setArguments(data_input);

            trans.replace(R.id.root_frame_fifth, new_frag);
            trans.commit();
        }
    }

    /* Validate user's data. */
    private boolean validateForm() {
        boolean valid = true;

        if (TextUtils.isEmpty(notes)) {
            notes_input.setError(getString(R.string.required));
            valid = false;
        } else {
            notes_input.setError(null);
        }
        return valid;
    }
}
