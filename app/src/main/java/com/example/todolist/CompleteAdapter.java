package com.example.todolist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CompleteAdapter extends RecyclerView.Adapter<CompleteAdapter.ViewHolder> {

    ArrayList<AddItem> completeItems = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    Context ctx;

    SharedPreferences shared;
    SharedPreferences.Editor editor;

    public ItemClickListener itemClickListener;

    public CompleteAdapter(Context ctx, SharedPreferences shared, SharedPreferences.Editor editor) {
        this.ctx = ctx;
        this.shared = shared;
        this.editor = editor;
    }

    public CompleteAdapter(Context context, AddAdapter.ItemClickListener itemClickListener) {
        this.ctx = context;
        this.itemClickListener = (ItemClickListener) itemClickListener;
    }

    public CompleteAdapter() {

    }


    public void setCompleteItems(ArrayList<AddItem> list, Context ctx) {
        this.completeItems = list;
        this.ctx = ctx;
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    public void setItemClickListener(ItemClickListener listener) {
        itemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        TextView complete;

        View itemView;

        LinearLayout layout;

        ItemClickListener itemClickListener;

        Context ctx;

        public ViewHolder(View view, Context ctx, ItemClickListener itemClickListener) {
            super(view);

            int pos = getAdapterPosition();

            complete = (TextView) view.findViewById(R.id.completeText);
            layout = (LinearLayout) view.findViewById(R.id.comleteLayout);

            this.itemView = view;
            this.itemClickListener = itemClickListener;
            this.ctx = ctx;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(getAdapterPosition());
        }

//        @Override
//        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//
//        }
    }

    public CompleteAdapter(ArrayList<AddItem> items) {
        this.completeItems = items;
    }

    public CompleteAdapter(Context context, ItemClickListener itemClickListener) {
        this.ctx = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_complete, parent, false);
        ctx = parent.getContext();

        return new ViewHolder(view, ctx, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        shared = ((MainActivity) MainActivity.ctx).ctx.getSharedPreferences("toDoList", Activity.MODE_PRIVATE);
        editor = ((MainActivity) MainActivity.ctx).editor;

        Log.i("CompleteAdapter onBindViewHolder","");
        linearLayoutManager = new LinearLayoutManager(ctx);

        final AddItem completeItem = completeItems.get(position);

        holder.complete.setText(completeItem.getToDoList());

        // shop에서 구입하면 체인지
        // 근데 여기도 쉐어드 뿌려줘봐야 알 듯..
        try {
            String btnMent = shared.getString("myBlackTheme","구매");
            if (btnMent.equals("현재 테마") || ((Shop) Shop.ctx).blackAndWhiteBuyBtn.equals("현재 테마")) {
                holder.layout.setBackgroundColor(Color.BLACK);
                Log.i("현재테마는 블랙 테마임 DeleteAdapter", "");
            } else {

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            String whiteBtnMent = shared.getString("myWhiteTheme", "");
            if (whiteBtnMent.equals("현재 테마")) {
                holder.layout.setBackgroundColor(Color.WHITE);
                holder.complete.setTextColor(Color.BLACK);
                Log.i("현재테마는 화이트 테마임 DeleteAdapter", "");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            String ableDBtnMent = shared.getString("myAbleDTheme","");
            if (ableDBtnMent.equals("현재 테마")) {
                holder.layout.setBackgroundColor(Color.RED);
                holder.complete.setTextColor(Color.WHITE);
                Log.i("현재 테마는 에이블디 테마임 DeleteAdapter","");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        Log.i("완료어댑터의 온바인드 뷰홀더 메서드 실행", ""
                + completeItems.get(position) + completeItem.getToDoList());

    }

    @Override
    public int getItemCount() {
        return (null != completeItems ? completeItems.size() : 0);
    }
}
