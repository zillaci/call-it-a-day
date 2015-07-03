package com.pivotal_er.ciad.callitaday;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.pivotal_er.ciad.callitaday.fragments.AdjustFragment;
import com.pivotal_er.ciad.callitaday.fragments.HomeFragment;
import com.pivotal_er.ciad.callitaday.fragments.LeftDrawerFragment;


public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private LeftDrawerFragment mLeftDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //v7 toolbar instead of actionbar
        mToolbar = (Toolbar) findViewById(R.id.toobar_actionbar);
        //set toolbar as an actionbar
        setSupportActionBar(mToolbar);

        //get drawer layout and set toggle
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                makeToastMsg("opened");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                makeToastMsg("closed");
            }
        };
        //set true toggle animation hamberger to arrow
        mDrawerToggle.setDrawerIndicatorEnabled(true);

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mLeftDrawerFragment = (LeftDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.left_drawer_fragment);
        mLeftDrawerFragment.setup();

        replaceContent(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void makeToastMsg(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
    }

    public void replaceContent(int position) {
        Fragment fragment = null;


        switch (position) {
            case 0: //home
                fragment = HomeFragment.newInstance();
                break;
            case 1: //adjust
                fragment = AdjustFragment.newInstance();
                break;
            case 2: //vacation
                break;
            case 3: //plans
                break;
            case 4: //statistics
                break;
            case 5: //policy
                break;
            case 6: //settings
                break;

        }

        if(fragment != null) {
            Bundle args = new Bundle();
            fragment.setArguments(args);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            ((DrawerLayout)findViewById(R.id.drawer_layout)).closeDrawers();

        }
    }
}
