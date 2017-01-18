package jstam.programmeerproject_scubascan.Items;

/**
 * Created by Jessie on 18/01/2017.
 */

public class DiveItem {

    // fields for email and username
    private String date, country, dive_spot, buddy, air_temp, surface_temp, bottom_temp, visibility,
            water_type, dive_type;

    private int dive_number;

    public DiveItem() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public DiveItem(String date, String country, String dive_spot, String buddy, String air_temp,
                    String surface_temp, String bottom_temp, String visibility, String water_type,
                    String dive_type) {

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

    }

    //methods for number
    public int getNumber() { return dive_number; }
    public void setNumber(int dive_number) { int new_dive_number = dive_number; }

    // methods for date
    public String getDate() { return date; }
    public void setDate(String new_date) { date = new_date; }

    // methods for country
    public String getCountry() { return country; }
    public void setCountry(String new_country) { country = new_country; }

    // methods for dive spot
    public String getDiveSpot() { return dive_spot; }
    public void setDiveSpot(String new_dive_spot) { country = new_dive_spot; }

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

}
