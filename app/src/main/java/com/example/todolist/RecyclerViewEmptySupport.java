package com.example.todolist;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewEmptySupport extends RecyclerView {

    private View emptyView;

    private AdapterDataObserver emptyObserver = new AdapterDataObserver() {

        @Override
        public void onChanged() {
            Adapter<?> adapter = getAdapter();
            if (adapter != null && emptyView != null) {
                if (adapter.getItemCount() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                    RecyclerViewEmptySupport.this.setVisibility(View.GONE);
                    Log.i("this 1","" + this);
                } else {
                    emptyView.setVisibility(View.GONE);
                    RecyclerViewEmptySupport.this.setVisibility(View.VISIBLE);
                    Log.i("this 1","" + this);
                }
            }
        }
    };

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(emptyObserver);
        }
        emptyObserver.onChanged();
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }

    public RecyclerViewEmptySupport(@NonNull Context context) {
        super(context);
    }

    public RecyclerViewEmptySupport(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewEmptySupport (@NonNull Context context,
                                     @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
