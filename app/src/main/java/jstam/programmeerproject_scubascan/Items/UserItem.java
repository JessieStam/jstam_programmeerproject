package jstam.programmeerproject_scubascan.Items;

import java.util.ArrayList;

/**
 * Created by Jessie on 12/01/2017.
 */

public class UserItem {

    // fields for email and username
    private String username;
    private String email;

    public UserItem() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public UserItem(String username, String email) {
        this.username = username;
        this.email = email;
    }

    // methods for username
    public String getUsername() { return username; }
    public void setUsername(String new_username) { username = new_username; }

    // methods for e-mail
    public String getEmail() { return email; }
    public void setEmail(String new_email) { email = new_email; }

}
