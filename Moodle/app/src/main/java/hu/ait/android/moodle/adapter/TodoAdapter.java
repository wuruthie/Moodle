package hu.ait.android.moodle.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import hu.ait.android.moodle.MainActivity;
import hu.ait.android.moodle.R;
import hu.ait.android.moodle.data.Mood;

/**
 * Created by ruthwu on 5/21/16.
 */
public class TodoAdapter
        extends RecyclerView.Adapter<TodoAdapter.ViewHolder>
        implements TodoTouchHelperAdapter {

    private Context context;
    private List<Mood> moods = new ArrayList<Mood>();

    public TodoAdapter(Context context) {
        this.context = context;
        moods = Mood.listAll(Mood.class);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_list_row, parent, false);

        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());

        holder.tvDate.setText(formattedDate);
        holder.btnViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).showViewMoodActivity(moods.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moods.size();
    }

    public void addTodo(Mood mood) {
        moods.add(0, mood);

        mood.save();

        // ths refreshes the whole list
        notifyDataSetChanged();

        // this refreshes only the first item, more optimal!
        //notifyItemInserted(0);
    }

    public void removeTodo(int position) {
        moods.get(position).delete();
        moods.remove(position);
        notifyItemRemoved(position);
    }

    public void updateTodo(int index, Mood todo) {
        moods.set(index, todo);
        todo.save();
        notifyItemChanged(index);
    }

    @Override
    public void onItemDismiss(int position) {
        removeTodo(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(moods, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(moods, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        //add in stuff to display mood
        //ImageView Mood
        private TextView tvDate;
        private Button btnViewDetails;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            btnViewDetails = (Button) itemView.findViewById(R.id.btnViewDetails);

        }
    }

}
