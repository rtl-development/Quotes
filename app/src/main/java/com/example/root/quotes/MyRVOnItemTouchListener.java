package com.example.root.quotes;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyRVOnItemTouchListener implements RecyclerView.OnItemTouchListener
{

    public interface OnRVItemClickListener {
        void onItemClick(View view, int position);
        void onLongClick(View view, int position);
    }

    private OnRVItemClickListener itemClickListener;
    private GestureDetector gestureDetector;

    public MyRVOnItemTouchListener(Context context, final RecyclerView recyclerView,
                                   final OnRVItemClickListener listener)
    {
        itemClickListener = listener;

        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if(childView != null && itemClickListener != null)
                    itemClickListener.onLongClick(childView, recyclerView.getChildAdapterPosition(childView));
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        View childView = rv.findChildViewUnder(e.getX(), e.getY());
        if(childView != null && itemClickListener != null && gestureDetector.onTouchEvent(e))
            itemClickListener.onItemClick(childView, rv.getChildAdapterPosition(childView));

        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
