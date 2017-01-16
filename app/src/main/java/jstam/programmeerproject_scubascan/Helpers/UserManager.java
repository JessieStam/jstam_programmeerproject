package jstam.programmeerproject_scubascan.Helpers;

import java.util.ArrayList;

import jstam.programmeerproject_scubascan.Items.UserItem;

/**
 * Created by Jessie on 12/01/2017.
 */

public class UserManager {

    // define instance
    private static UserManager ourInstance = null;

    private ArrayList<UserItem> users = new ArrayList<>();

    private String current_user = "";

    // construct the instance
    public static UserManager getOurInstance(){

        if (ourInstance == null) {
            ourInstance = new UserManager();
        }
        return ourInstance;
    }

    /**
     * Create User and add User to users list.
     */
    public void create_user(String username, String user_email) {

        current_user = user_email;

        boolean not_in_list = false;
        current_user = user_email;

        // check if user is already in list
        for (UserItem user : users) {
            if (user_email.equals(user.getEmail())) {
                not_in_list = true;
            }
        }

        // if not already in list, add new user to list
        if (not_in_list) {

            UserItem user = new UserItem();
            user.setUsername(username);
            user.setEmail(user_email);

//            user.setTBR(tbr_jar);
//            user.setFavorites(favorites);
//            user.setReading(nowreading);
//            user.setFinished(finished);

            users.add(user);
        }
    }

    /**
     * Returns the current user.
     */
    public String getCurrent_user() { return current_user; }

    /**
     * Logs the users out.
     */
    public void logout_user() {
        current_user = "";
    }

}
