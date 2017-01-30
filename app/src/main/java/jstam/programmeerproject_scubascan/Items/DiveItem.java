package jstam.programmeerproject_scubascan.Items;

import java.util.ArrayList;

/**
 * Created by Jessie on 18/01/2017.
 */

public class DiveItem {

    // fields for email and username
    private String date, country, dive_spot, buddy, air_temp, surface_temp, bottom_temp, visibility,
            water_type, dive_type, lead, time_in, time_out, pressure_in, pressure_out, depth,
            safetystop, notes, previous_level, nitrogen_level, interval_level;

    private ArrayList<String> clothing_list;

    private int dive_number;

    public DiveItem() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public DiveItem(int dive_number, String date, String country, String dive_spot, String buddy, String air_temp,
                    String surface_temp, String bottom_temp, String visibility, String water_type,
                    String dive_type, String lead, ArrayList<String> clothing_list,
                    String time_in, String time_out, String pressure_in,
                    String pressure_out, String depth, String safetystop,
                    String notes, String previous_level, String nitrogen_level, String interval_level) {

        this.dive_number = dive_number;
        this.date = date;
        this.country = country;
        this.dive_spot = dive_spot;
        this.buddy = buddy;
        this.air_temp = air_temp;
        this.surface_temp = surface_temp;
        this.bottom_temp = bottom_temp;
        this.visibility = visibility;
        this.water_type = water_type;
        this.dive_type = dive_type;
        this.lead = lead;
        this.clothing_list = clothing_list;
        this.time_in = time_in;
        this.time_out = time_out;
        this.pressure_in = pressure_in;
        this.pressure_out = pressure_out;
        this.depth = depth;
        this.safetystop = safetystop;
        this.notes = notes;
        this.previous_level = previous_level;
        this.nitrogen_level = nitrogen_level;
        this.interval_level = interval_level;

    }

    //methods for number
    public int getDiveNumber() { return dive_number; }
    public void setDiveNumber(int new_dive_number) { dive_number = new_dive_number; }

    // methods for date
    public String getDate() { return date; }
    public void setDate(String new_date) { date = new_date; }

    // methods for country
    public String getCountry() { return country; }
    public void setCountry(String new_country) { country = new_country; }

    // methods for dive spot
    public String getDiveSpot() { return dive_spot; }
    public void setDiveSpot(String new_dive_spot) { dive_spot = new_dive_spot; }

    // methods for buddy
    public String getBuddy() { return buddy; }
    public void setBuddy(String new_buddy) { buddy = new_buddy; }

    // methods for air temperature
    public String getAirTemp() { return air_temp; }
    public void setAirTemp(String new_air_temp) { air_temp = new_air_temp; }

    // methods for surface temperature
    public String getSurfaceTemp() { return surface_temp; }
    public void setSurfaceTemp(String new_surface_temp) { surface_temp = new_surface_temp; }

    // methods for bottom temperature
    public String getBottomTemp() { return bottom_temp; }
    public void setBottomTemp(String new_bottom_temp) { bottom_temp = new_bottom_temp; }

    // methods for visibility
    public String getVisibility() { return visibility; }
    public void setVisibility(String new_visibility) { visibility = new_visibility; }

    // methods for water type
    public String getWaterType() { return water_type; }
    public void setWaterType(String new_water_type) { water_type = new_water_type; }

    // methods for dive type
    public String getDiveType() { return dive_type; }
    public void setDiveType(String new_dive_type) { dive_type = new_dive_type; }

    // methods for amount of lead
    public String getLead() { return lead; }
    public void setLead(String new_lead) { lead = new_lead; }

    // methods for list of clothing
    public ArrayList<String> getClothingList() { return clothing_list; }
    public void setClothingList(ArrayList<String> new_clothing_list) { clothing_list = new_clothing_list; }

    // methods for time in
    public String getTimeIn() { return time_in; }
    public void setTimeIn(String new_time_in) { time_in = new_time_in; }

    // methods for time out
    public String getTimeOut() { return time_out; }
    public void setTimeOut(String new_time_out) { time_out = new_time_out; }

    // methods for pressure in
    public String getPressureIn() { return pressure_in; }
    public void setPressureIn(String new_pressure_in) { pressure_in = new_pressure_in; }

    // methods for pressure out
    public String getPressureOut() { return pressure_out; }
    public void setPressureOut(String new_pressure_out) { pressure_out = new_pressure_out; }

    // methods for depth
    public String getDepth() { return depth; }
    public void setDepth(String new_depth) { depth = new_depth; }

    // methods for safetystop
    public String getSafetystop() { return safetystop; }
    public void setSafetystop(String new_safetystop) { safetystop = new_safetystop; }

    // methods for notes
    public String getNotes() { return notes; }
    public void setNotes(String new_notes) { notes = new_notes; }

    // methods for previous_level
    public String getPreviousLevel() { return previous_level; }
    public void setPreviousLevel(String new_previous_level) { previous_level = new_previous_level; }

    // methods for previous_level
    public String getNitrogenLevel() { return nitrogen_level; }
    public void setNitrogenLevel(String new_nitrogen_level) { nitrogen_level = new_nitrogen_level; }

    // methods for previous_level
    public String getIntervalLevel() { return interval_level; }
    public void setIntervalLevel(String new_interval_level) { previous_level = new_interval_level; }

}
