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
public class ViewMoodListActivity extends AppCompatActivity{
    public static final String KEY_EDIT= "KEY_EDIT";
    public static final int FLAG_ACTIVITY_CLEAR_TOP = 102;
    public static final int REQUEST_CODE_EDIT_TODO = 101;
    public static final int REQUEST_CODE_ADD_TODO = 100;

    private TodoAdapter todoRecyclerAdapter;
    private Mood todoEditHolder;
    private int todoToEditPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mood_list);
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
    }


    public void showMoodActivity(Mood moodToEdit, int position) {
        Intent intentEditTodo = new Intent(ViewMoodListActivity.this,
                AddDescriptionActivity.class);
        todoEditHolder = moodToEdit;
        todoToEditPosition = position;

        intentEditTodo.putExtra(KEY_EDIT, moodToEdit);
        startActivityForResult(intentEditTodo, REQUEST_CODE_EDIT_TODO);
    }

    public void showMainActivity(){
            Intent intentAddTodo = new Intent(ViewMoodListActivity.this, MainActivity
                    .class);
            startActivityForResult(intentAddTodo,
                    FLAG_ACTIVITY_CLEAR_TOP);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                if (requestCode == REQUEST_CODE_ADD_TODO) {
                    Mood mood = (Mood) data.getSerializableExtra(
                            AddDescriptionActivity.KEY_TODO);

                    todoRecyclerAdapter.addTodo(mood);
                } else if (requestCode == REQUEST_CODE_EDIT_TODO) {
                    Mood todoTemp = (Mood) data.getSerializableExtra(
                            AddDescriptionActivity.KEY_TODO);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back:
                showMainActivity();
                break;
            default:
                break;
        }

        return true;
    }

}
