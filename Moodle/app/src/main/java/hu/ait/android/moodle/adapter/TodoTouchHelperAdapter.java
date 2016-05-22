package hu.ait.android.moodle.adapter;

/**
 * Created by ruthwu on 5/21/16.
 */
public interface TodoTouchHelperAdapter {

    void onItemDismiss(int position);

    void onItemMove(int fromPosition, int toPosition);

}