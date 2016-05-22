package hu.ait.android.moodle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public static final String KEY_EDIT = "KEY_EDIT";
    public static final int REQUEST_CODE_VIEW_MOOD_LIST = 101;
    public static final int REQUEST_CODE_ADD_TODO = 104;

    public static int category = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //NAV
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Good Button
        Button btnGood = (Button) findViewById(R.id.btnGood);
        btnGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = 1;
                showAddDescriptionActivity();
            }
        });

        //ok Button
        Button btnOkay = (Button) findViewById(R.id.btnOkay);
        btnOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = 0;
                showAddDescriptionActivity();
            }
        });

        //bad Button
        Button btnBad = (Button) findViewById(R.id.btnBad);
        btnBad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = -1;
                showAddDescriptionActivity();
            }
        });

    }
    private void showAddDescriptionActivity() {
        Intent intentAddTodo = new Intent(MainActivity.this, AddDescriptionActivity.class);
        startActivityForResult(intentAddTodo,
                REQUEST_CODE_ADD_TODO);
    }

    public void showViewMoodListActivity(){
        Intent intentAddTodo = new Intent(MainActivity.this, ViewMoodListActivity
                .class);
        startActivityForResult(intentAddTodo,
                REQUEST_CODE_VIEW_MOOD_LIST);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    public void closeDrawer(){
        DrawerLayout mDrawerLayout;
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawers();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_log) {
            closeDrawer();
        } else if (id == R.id.nav_view) {
            showViewMoodListActivity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
