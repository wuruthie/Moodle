package hu.ait.android.moodle.data;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by ruthwu on 5/21/16.
 */
public class Mood extends SugarRecord implements Serializable {
    private String category;
    private boolean period;
    private String description;

    public Mood(){

    }

    public Mood(String category, boolean period, String description){
        this.category = category;
        this.period = false;
        this.description = description;

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isPeriod() {
        return period;
    }

    public void setPeriod(boolean period) {
        this.period = period;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
