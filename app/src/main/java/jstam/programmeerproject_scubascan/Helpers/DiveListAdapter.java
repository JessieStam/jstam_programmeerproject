package jstam.programmeerproject_scubascan.Helpers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import jstam.programmeerproject_scubascan.Activities.DiveLogDetailsActivity;
import jstam.programmeerproject_scubascan.R;

/**
 * Created by Jessie on 24/01/2017.
 */

public class DiveListAdapter extends RecyclerView.Adapter<DiveListAdapter.ViewHolder>{

    private Context context;
    ArrayList<String> dive_number_list;
    ArrayList<String> location_list;
    ArrayList<String> date_list;


    /**
     * This function constructs the BookAdapter
     */
    public DiveListAdapter (Context context, ArrayList<String> dive_number_list, ArrayList<String> location_list, ArrayList<String> date_list) {

        this.context = context;
        this.dive_number_list = dive_number_list;
        this.location_list = location_list;
        this.date_list = date_list;
    }

    /**
     * Construct ViewHolder to display title, author and image
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView my_number_view;
        TextView my_location_view;
        TextView my_date_view;

        public ViewHolder(View itemView) {
            super(itemView);
            my_number_view = (TextView) itemView.findViewById(R.id.dive_num_row);
            my_location_view = (TextView) itemView.findViewById(R.id.location_row);
            my_date_view = (TextView) itemView.findViewById(R.id.date_row);
        }
    }

    // set onClickListener to RecyclerView
    View.OnClickListener listener = new View.OnClickListener() {

        // when item is clicked, move to bookinfo activity
        @Override
        public void onClick(View view) {

            // get title of clicked item
            TextView dive_number_view = (TextView) view.findViewById(R.id.dive_num_row);
            String dive_number = dive_number_view.getText().toString();

            // start new activity, add title
            Intent viewDive = new Intent(context, DiveLogDetailsActivity.class);
            viewDive.putExtra("clicked_dive", dive_number);
            context.startActivity(viewDive);

        }
    };

    @Override
    public DiveListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dive_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // add data to the RecyclerView
        viewHolder.my_number_view.setText(dive_number_list.get(position));
        viewHolder.my_location_view.setText(location_list.get(position));
        viewHolder.my_date_view.setText(date_list.get(position));

        // set onclick listener, so we acn move on to BookDetailsActivity later
        viewHolder.itemView.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return dive_number_list.size();
    }

}
