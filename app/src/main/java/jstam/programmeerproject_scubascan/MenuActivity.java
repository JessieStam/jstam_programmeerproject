package jstam.programmeerproject_scubascan;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

/**
 * Created by Jessie on 12/01/2017.
 */

public class MenuActivity extends LoginActivity {

    private Toolbar toolbar;
    ToolbarHelper toolbar_helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // construct toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar_helper = new ToolbarHelper();

    }


}
