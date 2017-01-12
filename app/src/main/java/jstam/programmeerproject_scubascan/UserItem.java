package jstam.programmeerproject_scubascan;

import java.util.ArrayList;

/**
 * Created by Jessie on 12/01/2017.
 */

public class UserItem {

    // fields for title, author and image
    private String email;

    // private ArrayList<ArrayList<BookItem>> my_lists;
    private ArrayList<UserItem> tbr_jar;
    private ArrayList<UserItem> favorites;
    private ArrayList<UserItem> reading;
    private ArrayList<UserItem> finished;

    // constructor
    //public BookItem(String new_string) { title = new_string; }

    // methods for e-mail
    public String getEmail() { return email; }
    public void setEmail(String new_email) { email = new_email; }

//    // methods for tbr list
//    public ArrayList<UserItem> getTBR() { return tbr_jar; }
//    public void setTBR(ArrayList<UserItem> new_tbr) { tbr_jar = new_tbr; }
//
//    public ArrayList<UserItem> getFavorites() { return favorites; }
//    public void setFavorites(ArrayList<UserItem> new_favorites) { favorites = new_favorites; }
//
//    public ArrayList<UserItem> getReading() { return reading; }
//    public void setReading(ArrayList<UserItem> new_reading) { reading = new_reading; }
//
//    public ArrayList<UserItem> getFinished() { return finished; }
//    public void setFinished(ArrayList<UserItem> new_finished) { finished = new_finished; }

}
