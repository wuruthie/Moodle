package hu.ait.android.moodle;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import hu.ait.android.moodle.adapter.TodoAdapter;
import hu.ait.android.moodle.adapter.TodoItemTouchHelperCallback;
import hu.ait.android.moodle.data.Mood;

/**
 * Created by ruthwu on 5/22/16.
 */
public class ViewMoodListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    public static final String KEY_MOOD_LIST = "KEY_MOOD_LIST";
    public static final String KEY_MOOD= "KEY_MOOD";
    public static final int REQUEST_CODE_VIEW_MOOD = 102;
    public static final int REQUEST_CODE_VIEW_MOOD_LIST = 101;
    public static final int REQUEST_CODE_ADD_TODO = 100;

    private TodoAdapter todoRecyclerAdapter;
    private Mood todoEditHolder;
    private int todoToEditPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //recycler view
        todoRecyclerAdapter = new TodoAdapter(this);
        final RecyclerView recyclerView =
                (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        // RecyclerView layout manager
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(todoRecyclerAdapter);

        ItemTouchHelper.Callback callback =
                new TodoItemTouchHelperCallback(todoRecyclerAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);


        //NAV
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                if (requestCode == REQUEST_CODE_ADD_TODO) {
                    Mood mood = (Mood) data.getSerializableExtra(
                            AddDescriptionActivity.KEY_TODO);

                    todoRecyclerAdapter.addTodo(mood);
                } else if (requestCode == REQUEST_CODE_VIEW_MOOD) {
                    Mood todoTemp = (Mood) data.getSerializableExtra(
                            ViewMoodActivity.KEY_TODO);

                    todoEditHolder.setCategory(todoTemp.getCategory());
                    todoEditHolder.setPeriod(todoTemp.isPeriod());
                    todoEditHolder.setDescription(todoTemp.getDescription());

                    if (todoToEditPosition != -1) {
                        todoRecyclerAdapter.updateTodo(todoToEditPosition, todoEditHolder);
                        todoToEditPosition = -1;
                    } else {
                        todoRecyclerAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case RESULT_CANCELED:
                Toast.makeText(ViewMoodListActivity.this, "Action Cancelled", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void showViewMoodListActivity(){
        Intent intentAddTodo = new Intent(ViewMoodListActivity.this, ViewMoodListActivity.class);
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
