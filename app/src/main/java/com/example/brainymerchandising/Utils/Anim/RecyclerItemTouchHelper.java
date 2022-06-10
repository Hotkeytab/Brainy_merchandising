package com.example.brainymerchandising.Utils.Anim;

import static androidx.recyclerview.widget.ItemTouchHelper.Callback.getDefaultUIUtil;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.graphics.Canvas;

import android.view.View;

import com.example.brainymerchandising.R;
import com.example.brainymerchandising.Visite.UI.MainVisiteAdapter;
import com.example.brainymerchandising.Visite.UI.MainVisiteViewHolder;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
    private RecyclerItemTouchHelperListener listener;

    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs, RecyclerItemTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            final View foregroundView = ((MainVisiteViewHolder) viewHolder).itemView.findViewById(R.id.Background_item);

            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
        RecyclerView.ViewHolder viewHolder, float dX, float dY,
        int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((MainVisiteViewHolder) viewHolder).itemView.findViewById(R.id.Background_item);
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
            actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = ((MainVisiteViewHolder) viewHolder).itemView.findViewById(R.id.Background_item);
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView,
        RecyclerView.ViewHolder viewHolder, float dX, float dY,
        int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((MainVisiteViewHolder) viewHolder).itemView.findViewById(R.id.Background_item);

        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
            actionState, isCurrentlyActive);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    public interface RecyclerItemTouchHelperListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}