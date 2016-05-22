package hu.ait.android.moodle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import hu.ait.android.moodle.data.Mood;

/**
 * Created by ruthwu on 5/21/16.
 */
public class AddDescriptionActivity extends AppCompatActivity {
    public static final String KEY_TODO = "KEY_TODO";


    private Mood moodToEdit = null;
    private EditText etDescriptionText;
    private int moodCategory;
    private boolean isPeriod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_description);

        if (getIntent().getSerializableExtra(MainActivity.KEY_MOOD) != null) {
            moodToEdit = (Mood) getIntent().getSerializableExtra(MainActivity.KEY_MOOD);
        }

        etDescriptionText = (EditText) findViewById(R.id.etDescription);

        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTodo();
            }
        });

        if (moodToEdit != null) {
            etDescriptionText.setText(moodToEdit.getDescription());
        }
    }

    public void itemClicked(View v) {
        //code to check if this checkbox is checked!
        CheckBox checkBox = (CheckBox) findViewById(R.id.cbPeriod);
        if(checkBox.isChecked()){
            isPeriod = true;
        } else{
            isPeriod = false;
        }
    }

    private void saveTodo() {
        if ("".equals(etDescriptionText.getText().toString())) {
            etDescriptionText.setError(getString(R.string.error_field_empty));
        } else {
            Intent intentResult = new Intent();
            Mood todoResult = null;
            if (moodToEdit != null) {
                todoResult = moodToEdit;
            } else {
                todoResult = new Mood();
            }

            moodCategory = MainActivity.category;
            todoResult.setDescription(etDescriptionText.getText().toString());

            switch(moodCategory){
                case 1: todoResult.setCategory("good");
                    break;
                case -1: todoResult.setCategory("bad");
                    break;
                default:
                    todoResult.setCategory("ok");
                    break;
            }
            todoResult.setPeriod(isPeriod);
            intentResult.putExtra(KEY_TODO, todoResult);
            setResult(RESULT_OK, intentResult);
            finish();
        }
    }
}
