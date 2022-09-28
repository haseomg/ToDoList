package com.example.todolist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DeleteAdapter extends RecyclerView.Adapter<DeleteAdapter.ViewHolder> implements Serializable {

    PreferenceManager preferenceManager;

    ArrayList<AddItem> deleteItems = new ArrayList<>();
    Context ctx;

    ArrayList<AddItem> addItems;
    AddAdapter addAdapter = new AddAdapter();


    int position;

    String delete;

    ItemClickListener itemClickListener;

    LinearLayoutManager linearLayoutManager;

    SharedPreferences shared;
    SharedPreferences.Editor editor;


//    public DeleteAdapter() {
//
//    }


    public DeleteAdapter(Context ctx, SharedPreferences shared, SharedPreferences.Editor editor) {
        this.ctx = ctx;
        this.shared = shared;
        this.editor = editor;
    }

    public DeleteAdapter(ArrayList<AddItem> items) {
        this.deleteItems = items;
    }

    public DeleteAdapter(Context context, ItemClickListener itemClickListener) {
        this.ctx = context;
        this.itemClickListener = itemClickListener;
    }

    public ArrayList<AddItem> getListData() {
        return deleteItems;
    }


    public void setArrayList(ArrayList<AddItem> deleteItems, ArrayList<AddItem> addItems, Context context) {
        this.deleteItems = deleteItems;
        this.addItems = addItems;
        this.ctx = context;
    }

    public void setDeleteItems(ArrayList<AddItem> deleteItems, Context context) {
        this.deleteItems = deleteItems;
        this.ctx = context;
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    public void setItemClickListener(ItemClickListener listener) {
        itemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

//        ArrayList addItems = ((MainActivity) MainActivity.ctx).addItems;

        TextView delete;

        View itemView;

        Button reAdd;

        LinearLayout layout;

        ItemClickListener itemClickListener;

        Context ctx;

        RecyclerViewEmptySupport recycler;

        public ViewHolder(View view) {
            super(view);

            delete = (TextView) view.findViewById(R.id.deleteText);
            layout = (LinearLayout) view.findViewById(R.id.deleteLayout);
            reAdd = (Button) view.findViewById(R.id.reAdd);
            recycler = (RecyclerViewEmptySupport) view.findViewById(R.id.deleteRecycler);

            this.itemView = view;
            this.itemClickListener = itemClickListener;
            this.ctx = ctx;

//             주석 해제 하면
//             java.lang.ClassCastException: com.example.todolist.DeleteAdapter$ViewHolder cannot be cast to android.view.View$OnClickListener
//            delete.setOnClickListener((View.OnClickListener) this);

//            String deleteText = delete.getText().toString();

//            deleteItems.get(pos).setChangeText(deleteText);

//            view.setOnCreateContextMenuListener(this);
        }

        public ViewHolder(View view, Context ctx, ItemClickListener itemClickListener) {
            super(view);

            delete = (TextView) view.findViewById(R.id.deleteText);
            layout = (LinearLayout) view.findViewById(R.id.deleteLayout);
            reAdd = (Button) view.findViewById(R.id.reAdd);
            recycler = (RecyclerViewEmptySupport) view.findViewById(R.id.deleteRecycler);

            this.itemView = view;
            this.itemClickListener = itemClickListener;
            this.ctx = ctx;

//             주석 해제 하면
//             java.lang.ClassCastException: com.example.todolist.DeleteAdapter$ViewHolder cannot be cast to android.view.View$OnClickListener
//            delete.setOnClickListener((View.OnClickListener) this);

//            String deleteText = delete.getText().toString();

//            deleteItems.get(pos).setChangeText(deleteText);

//            view.setOnCreateContextMenuListener(this);
        }

//        @Override
//        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//
//        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(getAdapterPosition());
        }

        public void onBind(AddItem deleteItem) {
            delete.setText(deleteItem.getToDoList());
        }
    }

//    public void deleteItem(int position, AddItem deleteItem) {
//        this.position = position;
//        deleteItems.add(deleteItem);
//    }

//    public int getPosition(int position) {
//        this.position = position;
//        return position;
//    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_delete, parent, false);
//        DeleteAdapter.ViewHolder vh = new DeleteAdapter.ViewHolder(view);
        ctx = parent.getContext();

        return new ViewHolder(view, ctx, itemClickListener);
//        return vh;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

//        shared = ((Delete) Delete.ctx).ctx.getSharedPreferences("deleteList", Activity.MODE_PRIVATE);
//        editor = ((Delete) Delete.ctx).editor;

        shared = ((Delete) Delete.ctx).ctx.getSharedPreferences("toDoList", Activity.MODE_PRIVATE);
        editor = ((Delete) Delete.ctx).editor;

        linearLayoutManager = new LinearLayoutManager(((Delete) Delete.ctx).ctx);

        final AddItem deleteItem = deleteItems.get(position);

        Log.i("DeleteAdapter onBindViewholder", "");

        holder.onBind(deleteItem);


        try {
            String btnMent = shared.getString("myBlackTheme", "");
            if (btnMent.equals("현재 테마")) {
                holder.layout.setBackgroundColor(Color.BLACK);
                Log.i("현재테마는 블랙 테마임 DeleteAdapter", "");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            String whiteBtnMent = shared.getString("myWhiteTheme", "");
            if (whiteBtnMent.equals("현재 테마")) {
                holder.layout.setBackgroundColor(Color.WHITE);
                holder.delete.setTextColor(Color.BLACK);
                Log.i("현재테마는 블랙 테마임 DeleteAdapter", "");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            String ableDBtnMent = shared.getString("myAbleDTheme","");
            if (ableDBtnMent.equals("현재 테마")) {
                holder.layout.setBackgroundColor(Color.RED);
                holder.delete.setTextColor(Color.WHITE);
                holder.reAdd.setBackgroundColor(Color.WHITE);
                holder.reAdd.setTextColor(Color.RED);
                Log.i("현재테마는 블랙 테마임 DeleteAdapter", "");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        holder.reAdd.setOnClickListener(new View.OnClickListener() {

//            int position = holder.getAdapterPosition();

            @Override
            public void onClick(View v) {


                String addShared = shared.getString("toDoList","z");
                Log.i("addShared (전)","" + addShared);
                String reAddCheck = deleteItems.get(position).getToDoList();
                String reAddPlus = reAddCheck + " __ ";
                AddItem reAddItem = new AddItem(reAddCheck);
                Log.i("reAddCheck", "" + reAddCheck);
                addItems.add(reAddItem);
                Log.i("addShared (중간)","" + addShared);
//
//                for (int i = 0; i < addItems.size(); i ++) {
//                    Log.i("addItems.get(i).getToDoList 뿌애애애애앵","" + addItems.get(i).getToDoList());
//                }
//                saveInShared();

                try {
                    Log.i("addItems.size()", "" + addItems.size());
                    Log.i("reAddItem.getToDoList()", "" + reAddItem.getToDoList());
                } catch (IndexOutOfBoundsException e) {
                    Log.e("addItem의 사이즈와 reAddItem의 getToDoList IndextOutofBoundsException", "");
                }
//                Log.i("addItems.size 확인","" + addItems.size());

//                Log.i("addItems 내용 확인","" + addItems.get(position).getToDoList());

                deleteItems.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();




                saveInDeleteShared();

                String sharedIn = new String();

                try {
                    for (int i = 0; i < addItems.size(); i++) {
                        sharedIn += addItems.get(i).getToDoList()
                                + " __ ";
                        Log.i("sharedIn 쉐어드 저장되는 아이템들", "" + sharedIn);
                    }
                } catch (IndexOutOfBoundsException e) {
                    Log.i("메롱 :P", "");
                }


                int lastIndex = addItems.size() - 1;
                Log.i("addItems lastIndex check", "" + lastIndex);

                addAdapter.notifyItemRangeInserted(position, addItems.size());
//                addAdapter.notifyDataSetChanged();

                long now = System.currentTimeMillis();

                Date date = new Date(now);

                SimpleDateFormat simpleDateFormat
                        = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                String getTime = simpleDateFormat.format(date);

                Log.d("myToDo : " + sharedIn, " 현재시간 : " + getTime);

//                // 여기 좀 손봐야 해 계속 IndexofBoundException 뜸 ㅂㄷ
//
//                if (deleteItems.size() >= 0) {
//                    deleteItems.remove(position);
//                    notifyItemRemoved(position);
//                    Log.i("DeleteAdapter-------------------------------", "");
////                    Log.i("deleteItems.remove(position)", "" + deleteItems.remove(position));
////                    Log.i("deleteItems.get(position).getToDoList()", "" + deleteItems.get(position).getToDoList());
//                } else {
//                    Log.i("deleteItems.size 0이다", "");
//                }
//
//                notifyDataSetChanged();
//
////                saveInShared();
//
////                String reAdd = deleteItems.get(position).getToDoList();
                String fuckShared = shared.getString("toDoList","");
                try {
                    String fuckUShared = shared.getString("toDoList","");
                    Log.i("addShared fuck (전)","" + fuckUShared);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

                String sharedPlus = new String();
                for (int i=0; i<deleteItems.size(); i++) {
                    sharedPlus += deleteItems.get(i).getToDoList() + " __ ";
                }

                preferenceManager = new PreferenceManager();
//                preferenceManager = ((MainActivity) MainActivity.ctx).preferenceManager;
                preferenceManager.setString(((MainActivity) MainActivity.ctx).getApplication()
                        , "toDoList", "" + fuckShared + reAddPlus);
                // 원래 쉐어드에 지금 내가 추가한 아이템만 넣어주면 돼

                editor.commit();


                try {
                    String fuckingShared = shared.getString("toDoList","");
                    Log.i("addShared fuck (후)","" + fuckingShared);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != deleteItems ? deleteItems.size() : 0);
    }

    public void saveInDeleteShared() {
        // 원 키 다중밸류
        String sharedIn = new String();

        try {
            // i = 아이템이 추가되는 순차적인 포지션
            for (int i = 0; i < deleteItems.size(); i++) {
                sharedIn +=
                        deleteItems.get(i).getToDoList()
                                + " __ ";
                Log.i("deleteItems.size()", "" + deleteItems.size());
                Log.i("deleteItems.get(i).getToDoList()", "" + sharedIn);
//                            ((MainActivity) MainActivity.ctx).addItems.add(addItem);
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        int lastIndex = deleteItems.size() - 1;
        Log.i("lastIndex", "" + lastIndex);


        Log.d("myToDo : ", "" + sharedIn);


        preferenceManager = new PreferenceManager();
        preferenceManager.setString(((MainActivity) MainActivity.ctx).getApplication()
                , "deleteList", "" + sharedIn);

        editor.commit();
    }


}
