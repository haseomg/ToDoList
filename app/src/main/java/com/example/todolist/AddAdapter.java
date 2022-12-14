package com.example.todolist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AddAdapter extends RecyclerView.Adapter<AddAdapter.ViewHolder> implements Serializable {


    private int REQUEST_TEST = 200;

    PreferenceManager preferenceManager;


//    implements ItemTouchHelperListener

    ArrayList<AddItem> addItems;
    Context ctx;

    ArrayList<AddItem> completeItems;
    CompleteAdapter completeAdapter = new CompleteAdapter();

    int position;
    int count;
    int goldPlus;
    int lvUp = 1;

    int progress;

    String level;
    String checkToDo;

    //    MainActivity main;
//    AddItem addItem;
    ProgressBar progressBar;

    ItemClickListener itemClickListener;

    LinearLayoutManager linearLayoutManager;

    SharedPreferences shared;
    SharedPreferences.Editor editor;

    androidx.recyclerview.widget.RecyclerView recyclerView;

    public AddAdapter(Context ctx, SharedPreferences shared, SharedPreferences.Editor editor) {
        this.ctx = ctx;
        this.shared = shared;
        this.editor = editor;
    }


    public AddAdapter() {

    }

    public void setArrayList(ArrayList<AddItem> addItems, ArrayList<AddItem> completeItems, Context context) {
        this.addItems = addItems;
        this.completeItems = completeItems;
        this.ctx = context;
    }

//    public void setCompleteItems(ArrayList<AddItem> completeItems, Context context) {
//        this.completeItems = completeItems;
//        this.ctx = context;
//    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    public void setItemClickListener(ItemClickListener listener) {
        itemClickListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        EditText addWrite;

        TextView changeText;

        View itemView;

        LinearLayout layout;

        CheckBox checkBox;

        ItemClickListener itemClickListener;

        Context ctx;

        SharedPreferences shared;

        SharedPreferences.Editor editor;

        public ViewHolder(View view, Context ctx, ItemClickListener itemClickListener) {
            super(view);

            addWrite = (EditText) view.findViewById(R.id.addWrite);
            changeText = (TextView) view.findViewById(R.id.changeTextView);
            layout = (LinearLayout) view.findViewById(R.id.linearlayout);
            checkBox = (CheckBox) view.findViewById(R.id.checkBox);


            // ???????????? ?????????
            // ????????? ??????
            shared = ((MainActivity) MainActivity.ctx).shared;
            editor = ((MainActivity) MainActivity.ctx).editor;

            this.itemView = view;
            this.itemClickListener = itemClickListener;
            this.ctx = ctx;

//            addWrite.setOnClickListener((View.OnClickListener) this);
//            changeText.setOnClickListener((View.OnClickListener) this);
//            layout.setOnClickListener((View.OnClickListener) this);
//            checkBox.setOnClickListener((View.OnClickListener) this);

//            view.setOnCreateContextMenuListener((View.OnCreateContextMenuListener) this);
        }

        public ViewHolder(View view) {
            super(view);

            addWrite = (EditText) view.findViewById(R.id.addWrite);
            changeText = (TextView) view.findViewById(R.id.changeTextView);
            layout = (LinearLayout) view.findViewById(R.id.linearlayout);
            checkBox = (CheckBox) view.findViewById(R.id.checkBox);


            // ???????????? ?????????
            shared = ((MainActivity) MainActivity.ctx).shared;
            editor = ((MainActivity) MainActivity.ctx).editor;

            this.itemView = view;
            this.itemClickListener = itemClickListener;
            this.ctx = ctx;

//            addWrite.setOnClickListener((View.OnClickListener) this);
//            changeText.setOnClickListener((View.OnClickListener) this);
//            layout.setOnClickListener((View.OnClickListener) this);
//            checkBox.setOnClickListener((View.OnClickListener) this);

//            view.setOnCreateContextMenuListener((View.OnCreateContextMenuListener) this);
        }

        public void setItem(AddItem addItem) {
            addWrite.setText(addItem.getToDoList());
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(getAdapterPosition());
        }

        public void onBind(@NonNull AddItem addItem) {
            addWrite.setText(addItem.getToDoList());
        }

        public LinearLayout getLayout() {
            return layout;
        }

//        @Override
//        public void onCreateContextMenu(ContextMenu menu, View v,
//                                        ContextMenu.ContextMenuInfo menuInfo) {
//
//        }
    }


    public AddAdapter(ArrayList<AddItem> items) {
        this.addItems = items;
    }

    public AddAdapter(Context context, ItemClickListener itemClickListener) {
        this.ctx = context;
        this.itemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ctx = parent.getContext();
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        LayoutInflater inflater = (LayoutInflater)
                ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.recycler_add, parent, false);
        AddAdapter.ViewHolder vh = new AddAdapter.ViewHolder(view);

        return vh;
//        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewholder, @SuppressLint("RecyclerView") int position) {

        shared = ((MainActivity) MainActivity.ctx).ctx.getSharedPreferences("toDoList", Activity.MODE_PRIVATE);
        editor = ((MainActivity) MainActivity.ctx).editor;



        String btnMent = shared.getString("myBlackTheme", "");
        try {
            if (btnMent.equals("?????? ??????")) {
                viewholder.layout.setBackgroundColor(Color.BLACK);
                viewholder.changeText.setTextColor(Color.WHITE);
                viewholder.addWrite.setTextColor(Color.WHITE);
                Log.i("??????????????? ?????? ????????? AddAdapter","");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        String whiteBtnMent = shared.getString("myWhiteTheme", "");
        try {
            if (whiteBtnMent.equals("?????? ??????")) {
                viewholder.layout.setBackgroundColor(Color.WHITE);
                viewholder.changeText.setTextColor(Color.BLACK);
                viewholder.addWrite.setTextColor(Color.BLACK);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        String ableDBtnMent = shared.getString("myAbleDTheme", "");
        try {
            if (ableDBtnMent.equals("?????? ??????")) {
                viewholder.layout.setBackgroundColor(Color.RED);
                viewholder.changeText.setTextColor(Color.WHITE);
                viewholder.addWrite.setTextColor(Color.WHITE);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


//        try {
//            if (((Shop) Shop.ctx).originalBtnMent.equals("?????? ??????")) {
//                viewholder.layout.setBackgroundColor(Color.parseColor("#7CBBE7"));
//            }
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }


        if (!addItems.get(position).getToDoList().equals(null)) {
            viewholder.addWrite.setVisibility(View.GONE);
            viewholder.changeText.setVisibility(View.VISIBLE);
        }


        linearLayoutManager = new LinearLayoutManager(((MainActivity) MainActivity.ctx).ctx);

        final AddItem addItem = addItems.get(position);
        viewholder.setItem(addItem);

        if (addItem.getToDoList().equals("")) {
            viewholder.addWrite.setVisibility(View.VISIBLE);
        }

        viewholder.addWrite.setText(addItem.getToDoList());
        viewholder.changeText.setText(addItem.getToDoList());

        Log.i("?????????????????? ???????????? ????????? ????????? ??????", "" + addItems.get(position) + addItem.getToDoList());


//        String addText = "";
//        viewholder.addWrite.setText(Html.fromHtml(addText));

//        realMainLayout = ((MainActivity) MainActivity.ctx).realMainLayout;
//
//        realMainLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("???????????????????????? ?????????!", "");
//                if (viewholder.addWrite.length() > 1) {
//                    Log.i("???????????????????????? ?????? ??? addItem.getToDoList", "" +
//                            addItem.getToDoList());
//                    viewholder.changeText.setText(viewholder.addWrite.getText().toString());
//                    viewholder.changeText.setVisibility(View.VISIBLE);
//                    viewholder.addWrite.setVisibility(View.GONE);
//                    Log.i("???????????????????????? ?????? ???", "+ ?????? ??????????????? ??? ??????" +
//                            viewholder.addWrite.getText().toString());
//                    addItem.setToDoList(viewholder.addWrite.getText().toString());
//                }
//            }
//        });


        viewholder.addWrite.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

//                if (hasFocus) {
//                    Log.i("???????????? ??????????????? ????????? ?????????", "");
////                    hasFocus = false;
//                }
                if (!hasFocus) {
                    Log.i("???????????? ??????????????? ???????????? ??? ?????? ???", "");
                    if (!viewholder.changeText.getText().toString()
                            .equals(viewholder.addWrite.getText().toString())) {

                        viewholder.changeText.setText(viewholder.addWrite.getText().toString());
//                        viewholder.changeText.setVisibility(View.VISIBLE);

                        viewholder.addWrite.setText(viewholder.changeText.getText().toString());
                        viewholder.addWrite.setVisibility(View.GONE);

                        addItem.setToDoList(viewholder.changeText.getText().toString());

//                        setShared();


                        String sharedIn = new String();

                        try {
                            // ????????? ????????? ???????????? ???
                            // i = ???????????? ???????????? ???????????? ?????????
                            // ????????? ?????? ????????? i??? ????????????.
                            Log.i("addItems.size()", "" + addItems.size());
                            for (int i = 0; i < addItems.size(); i++) {
                                sharedIn +=
                                        addItems.get(i).getToDoList()
                                                + " __ ";
                                Log.i("addItems.get(i).getToDoList()", "" + sharedIn);
//                            ((MainActivity) MainActivity.ctx).addItems.add(addItem);
                            }
                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }

                        int lastIndex = addItems.size() - 1;
                        Log.i("lastIndex", "" + lastIndex);

//
////                    editor.putString("toDolist", "");
//                    editor.putString("toDoList", sharedIn);

//                    editor.apply();
//                    editor.commit();


                        long now = System.currentTimeMillis();

                        Date date = new Date(now);

                        SimpleDateFormat simpleDateFormat
                                = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                        String getTime = simpleDateFormat.format(date);

                        Log.d("myToDo : " + sharedIn, " ???????????? : " + getTime);

                        preferenceManager = new PreferenceManager();
                        preferenceManager.setString(((MainActivity) MainActivity.ctx).getApplication()
                                , "toDoList", getTime + " / " + sharedIn);

//                        preferenceManager.setString(((MainActivity) MainActivity.ctx).getApplication()
//                                , "toDoList", "" + sharedIn);

//                    editor.apply();
                        editor.commit();


//                        ((MainActivity) MainActivity.ctx).editor.putString("toDoList", save);
//                        ((MainActivity) MainActivity.ctx).editor.apply();
//                        ((MainActivity) MainActivity.ctx).editor.commit();

                    }
                }
                if (hasFocus) {
                    if (viewholder.addWrite.length() > 1) {
                        Log.i("???????????? ???????????? ????????? addItem.getToDoList", "" +
                                addItem.getToDoList());

                        viewholder.addWrite.setText(viewholder.changeText.getText().toString());
                        viewholder.addWrite.setVisibility(View.VISIBLE);

                        viewholder.changeText.setText(viewholder.addWrite.getText().toString());
                        viewholder.changeText.setVisibility(View.GONE);
                        Log.i("???????????? ??????????????? ???????????? ?????????", "+ ?????? ??????????????? ??? ??????" +
                                viewholder.addWrite.getText().toString());
                    }
                }
            }
        });


        // TextView -> EditText??? ????????? ???
        // ???????????? ?????? -> ??? ???????????? ??????


        // TextView ???????????? EditText??? ????????? (?????? ?????? **??????)???
//        viewholder.changeText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                String text = viewholder.changeText.getText().toString().isEmpty() ? "" :
////                        viewholder.changeText.getText().toString();
//                viewholder.addWrite.setVisibility(View.VISIBLE);
//                viewholder.addWrite.setText(addItem.getToDoList());
//                Log.i("addWrite getText()", "" + viewholder.addWrite.getText().toString());
//                viewholder.changeText.setText(addItem.getToDoList());
//                Log.i("changeText getText()", "" + viewholder.changeText.getText().toString());
//                viewholder.changeText.setVisibility(View.GONE);
//                Log.i("??????????????? TextView ??????)", "" + position);
//
//
//                //on Focus
////                viewholder.addWrite.setText(viewholder.changeText.getText().toString());
////                viewholder.addWrite.setVisibility(View.VISIBLE);
////
////                viewholder.changeText.setText(viewholder.addWrite.getText().toString());
////                viewholder.changeText.setVisibility(View.GONE);
//            }
//        });


        // ??????????????? ???????????? changeText??? ?????? ???????????? TextView??? ??????
        // setText
        viewholder.layout.setOnClickListener(new View.OnClickListener() {
            boolean input;

            @Override
            public void onClick(View v) {

//                notifyDataSetChanged();

                // ????????? ?????? ??? ?????? ??????
                if (viewholder.changeText.length() > 0 && input == true) {
                    viewholder.addWrite.setVisibility(View.VISIBLE);
                    viewholder.addWrite.setText(addItem.getToDoList());
                    Log.i("addWrite getText()", "" + viewholder.addWrite.getText().toString());
                    viewholder.changeText.setText(addItem.getToDoList());
                    Log.i("changeText getText()", "" + viewholder.changeText.getText().toString());
                    viewholder.changeText.setVisibility(View.GONE);
                    Log.i("??????????????? TextView ??????)", "" + position);

                    Log.i("input ?????? (??? 22)", "" + input);
                    input = false;
                    Log.i("input ?????? (??? 22)", "" + input);

                } else if (viewholder.addWrite.length() > 0 && input == false) {
                    viewholder.layout.setClickable(true);

                    InputMethodManager imm = ((MainActivity) MainActivity.ctx).imm;
                    imm.hideSoftInputFromWindow(viewholder.addWrite.getWindowToken(), 0);

                    viewholder.changeText.setVisibility(View.VISIBLE);
//                    viewholder.addWrite.setText();
                    viewholder.changeText.setText(viewholder.addWrite.getText().toString());

                    // ????????? ?????? ArrayList?????? AddItem??? ????????? ???????????? ????????? ??????????????????..
                    addItem.setToDoList(viewholder.addWrite.getText().toString());


                    String sharedIn = new String();

                    try {
                        // ????????? ????????? ???????????? ???
                        // i = ???????????? ???????????? ???????????? ?????????
                        // ????????? ?????? ????????? i??? ????????????.
                        Log.i("addItems.size()", "" + addItems.size());
                        for (int i = 0; i < addItems.size(); i++) {
                            sharedIn +=
                                    addItems.get(i).getToDoList()
                                            + " __ ";
                            Log.i("addItems.get(i).getToDoList()", "" + sharedIn);
//                            ((MainActivity) MainActivity.ctx).addItems.add(addItem);
                        }
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }

                    int lastIndex = addItems.size() - 1;
                    Log.i("lastIndex", "" + lastIndex);


                    // shared ??????
                    long now = System.currentTimeMillis();

                    Date date = new Date(now);

                    SimpleDateFormat simpleDateFormat
                            = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                    String getTime = simpleDateFormat.format(date);

                    Log.d("myToDo : " + sharedIn, " ???????????? : " + getTime);

                    preferenceManager = new PreferenceManager();
                    preferenceManager.setString(((MainActivity) MainActivity.ctx).getApplication()
                            , "toDoList", getTime + " / " + sharedIn);

//                    preferenceManager.setString(((MainActivity) MainActivity.ctx).getApplication()
//                            , "toDoList", "" + sharedIn);


//                    editor.apply();
                    editor.commit();


                    viewholder.addWrite.setVisibility(View.GONE);

                    viewholder.checkBox.setChecked(false);

                    addItem.setToDoList(viewholder.addWrite.getText().toString());

//                    Log.i("???????????? str ??? : ", "" + addItems.get(position).getToDoList());

//                    Log.i("??????????????? ???????????? ?????? ??? : ", "" + addItems.get(position).getToDoList());
////                    addItem.setToDoList("");
//                    // layout ?????? ????????? ???????????? ?????????
                    Log.i("??????????????? layout ??????) : ", "" + position);

                    Log.i("input ?????? (???)", "" + input);
                    input = true;
                    Log.i("input ?????? (???)", "" + input);


                } else if (addItems.get(position).getToDoList().length() == 0) {
                    viewholder.checkBox.setChecked(false);
                    Log.e("????????? ????????? 0??? ???", "setChecked(false)??? : " + position);
                    viewholder.layout.setClickable(false);
                    Log.e("????????? ????????? 0??? ???", "layout(false) : " + position);
                    Toast.makeText(ctx, "??? ?????? ?????? ??????????????????.", Toast.LENGTH_SHORT);
                } else {
                    viewholder.checkBox.setChecked(false);
                    viewholder.layout.setClickable(false);
                    Log.e("??????????????? layout ??????", "?????? : " + position);
                    Toast.makeText(ctx, "??? ?????? ?????? ??????????????????.", Toast.LENGTH_SHORT);
                }
                viewholder.layout.setClickable(true);
            }
        });


        Log.i("?????? ??????????????? add ??????", "");

        // ?????? ???????????? null
        viewholder.checkBox.setOnCheckedChangeListener(null);

        viewholder.checkBox.setChecked(addItem.isSelected());

//        viewholder.checkBox.setChecked(addItems.contains(String.valueOf(position)));

        viewholder.checkBox.setOnClickListener((View.OnClickListener) ctx);
        // setOnClickListener ????????? ???????????? ?????? ??????

        viewholder.checkBox.setTag(position);

        progressBar = ((MainActivity) MainActivity.ctx).progressBar;
        level = ((MainActivity) MainActivity.ctx).myLv;
        checkToDo = viewholder.addWrite.getText().toString();

        // ????????? ??????
//        viewholder.changeText.setPaintFlags(1);
        viewholder.changeText.setPaintFlags
                (viewholder.changeText.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        Log.i("setPaintFlages","TextView ????????? ??????");

//        if (viewholder.changeText.getPaintFlags() != 0 && viewholder.addWrite == viewholder.changeText) {
//        } else {
//            viewholder.changeText.getPaintFlags();
//            Log.i("getPaintFlage() != 0","???????????? ?????? ??????????????? : " + addItems.get(position).toDoList);
//        }
        // ???????????? ?????? ??? ?????? ??????, ????????????????????? ??????????????? ??????
        viewholder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                int position = viewholder.getAdapterPosition();

                if (count >= 0) {
                    if (viewholder.addWrite.length() != 0 &&
                            viewholder.changeText.length() != 0 && viewholder.checkBox.isChecked()) {
                        Log.i("AddAdapter ??????????????? ?????? 0??? ?????? ???", "");
                        Log.i("AddAdapter ??????????????? ?????? ?????? ???", "");

                        // ??????
//                        addItem.setSelected(isChecked);
//                        viewholder.checkBox.setChecked(!addItem.isSelected);

//                        if (viewholder.checkBox.isChecked()) {

                        // ?????? ???????????? ??? ?????? ??????????????? ?????? ??????
                        if (viewholder.changeText.getPaintFlags() == 0) {
                            Log.i("getPaintFlags() == 0","");
                        } else {
                            viewholder.changeText.setPaintFlags(viewholder.changeText.getPaintFlags() |
                                    Paint.STRIKE_THRU_TEXT_FLAG);
                            Log.i("getPaintFlags","????????? ??????");
                        }

                        addItems.remove(position);
                        notifyItemRemoved(position);

                        // ?????? 50?????? ??????
                        String myGold = shared.getString("myGold", "0");
                        goldPlus = Integer.parseInt(myGold);
                        if (goldPlus >= 0) {
                            goldPlus = goldPlus + 50;
                            ((MainActivity) MainActivity.ctx).gold.setText("??? ?????? : " + goldPlus);
                            Log.i("???????????? ????????????", "goldPlus : " + goldPlus);
                            // ??????????????? ????????? ??? ?????? ?????? ??????
                            ((MainActivity) MainActivity.ctx).goldAnimation.startAnimation(
                                    ((MainActivity) MainActivity.ctx).animation);


                            // saveInGoldShared
                            preferenceManager = new PreferenceManager();
                            preferenceManager.setString(((MainActivity) MainActivity.ctx).getApplication()
                                    , "myGold", "" + goldPlus);
                            Log.i("myGold ????????? ??????", "" + goldPlus);

                            editor.commit();
                        } else {

                        }


                        String checkOk = addItem.getToDoList();
                        Log.i("checkOk", "" + checkOk);
                        AddItem completeItem = new AddItem(checkOk);
                        completeItems.add(completeItem);
                        try {
                            Log.i("completeItems.size()", "" + completeItems.size());
                            Log.i("completeItem.getToDoList()", "" + completeItem.getToDoList());

                            // ??? ?????? ?????? ?????? ????????? ????????? ??????
//                            Log.i("completeItems.get(position).getToDoList()","" + completeItems.get(getPosition()).getToDoList());
//
//                            for (int i = 0; i < completeItems.size(); i++) {
//                             ????????? ??? ?????????
//                            int okPosition = completeAdapter.
//                             IndexOutOfBoundsException
//                            Log.i("completeItem getToDoList()", "" + completeItems.get(okP).getToDoList());
//                            }

                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }


                        // ?????????

                        String sharedIn = new String();

                        try {
                            Log.i("completeItems.size()", "" + completeItems.size());
                            for (int i = 0; i < completeItems.size(); i++) {
                                sharedIn +=
                                        completeItems.get(i).getToDoList()
                                                + " __ ";
                                Log.i("completeItems.get(i).getToDoList()", "" + sharedIn);
                            }
                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }

                        int lastIndex = completeItems.size() - 1;
                        Log.i("lastIndex", "" + lastIndex);


                        completeAdapter.notifyItemRangeInserted(position, completeItems.size());
                        Log.i("position", "" + position);
                        completeAdapter.notifyDataSetChanged();


                        // saveInCompleteListShared
                        preferenceManager = new PreferenceManager();
                        preferenceManager.setString(((MainActivity) MainActivity.ctx).getApplication()
                                , "completeList", "" + sharedIn);

                        editor.commit();


                        // ????????? 'Complete'??????????????????
                        // MainActivity ?????? ????????? ??? ArrayList<AddItem> completeItems ??????????????? ????????????????????? ??????????????????.
                        // ?????? ????????????, ???

                        // ?????????
                        // ???????????? ??????
                        // ???????????? onCreate() ??? ??? ??? ??? ???????????? ????????????


                        Log.i("???????????? ????????????", "isChecked : " + position);


                        String completeCount = shared.getString("myComplete", "0");
                        count = Integer.parseInt(completeCount);
                        // ?????? ??? ?????? ??????
                        count = count + 1;
                        Log.i("???????????? ????????????", "check count : " + count);
                        ((MainActivity) MainActivity.ctx).toDid.setText("?????? : " + count);

                        // saveInCompleteShared
                        preferenceManager = new PreferenceManager();
                        preferenceManager.setString(((MainActivity) MainActivity.ctx).getApplication()
                                , "myComplete", "" + count);

                        editor.commit();


                        String myExp = shared.getString("myExp", "0");
                        progress = Integer.parseInt(myExp);
//                        progressBar.incrementProgressBy(50);
                        progress = progress + 20;
                        progressBar.setProgress(progress);
                        ((MainActivity) MainActivity.ctx).myProgress.setText(progress + "%");
                        // ?????? progressBar??? ???????????? 100??? ??????
                        // 1. ????????? ????????? +1 (ok)
                        // 2. progressBar ???????????? 0?????? ????????????.
                        // *3. ????????? ???????????? ????????? ????????? ????????? ????????? 1??? ???????????? ???????????????

                        String levelShared = shared.getString("myLevel", "1");
                        lvUp = Integer.parseInt(levelShared);
                        Log.i("AddAdapter lvUp Check (???)", "" + lvUp);


                        if (progress == 100) {
                            progress = 0;
                            progressBar.setProgress(progress);
                            ((MainActivity) MainActivity.ctx).myProgress.setText(progress + "%");
                            // ?????? + 1
//                            if (progress == 100) {
                            lvUp = lvUp + 1;
                            Log.i("AddAdapter lvUp Check (???)", "" + lvUp);
                            ((MainActivity) MainActivity.ctx).userLv.setText("LV." + lvUp);

                            // ?????? ?????????
                            if (lvUp == 5) {
                                Toast.makeText((MainActivity) MainActivity.ctx, "????????? ?????? ?????????????????????!", Toast.LENGTH_SHORT).show();
                            }
                            if (lvUp == 20) {
                                Toast.makeText((MainActivity) MainActivity.ctx, "???????????? ????????? ?????? ?????????????????????!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        // saveInExpShared
                        preferenceManager = new PreferenceManager();
                        preferenceManager.setString(((MainActivity) MainActivity.ctx).getApplication()
                                , "myExp", "" + progress);

                        editor.commit();


                        // saveInLevelShared
                        preferenceManager = new PreferenceManager();
                        preferenceManager.setString(((MainActivity) MainActivity.ctx).getApplication()
                                , "myLevel", "" + lvUp);

                        editor.commit();

//                        notifyDataSetChanged();

//                        saveInShared();

                    } else if (viewholder.addWrite.equals("") || viewholder.addWrite.length() == 0) {
                        viewholder.checkBox.setChecked(addItem.isSelected);
                        addItem.setSelected(!isChecked);
                        Log.i("AddAdapter ??????????????? ?????? ????????? ???", "");
                    }


                }


                // ??????????????? ????????? ??? ??? ???????????? ??????

//                        String sharedText = new String();
//                     editor.putString("toDoList", save);
//                    editor.apply();
//                    editor.commit();
//                        for (int i = 0; i <= completeItems.size(); i++) {
//                            sharedText +=
//                                    completeItems.get(i).getChangeText();
//                        }
//
//                        editor.putString("CompleteItems", sharedText);
//                        editor.commit();

//                        AddItem data = new AddItem();
//                        data.setToDoList("");

//                        notifyDataSetChanged();
//                   }  else if (!viewholder.checkBox.isChecked() && viewholder.addWrite.length() == 0 &&
//                            viewholder.changeText.length() == 0) {
//                        addItem.setSelected(!isChecked);
//                        viewholder.checkBox.setChecked(false);
//                        viewholder.changeText.setPaintFlags(0);
//                    } else {
////                        goldPlus = goldPlus - 100;
//                        ((MainActivity) MainActivity.ctx).gold.setText("??? ?????? : " + goldPlus);
//                        Log.i("???????????? ????????????", "goldPlus : " + goldPlus);
//
//                        addItem.setSelected(!isChecked);
//                        viewholder.checkBox.setChecked(false);
//                        viewholder.changeText.setPaintFlags(0);
//                        selected_position = viewholder.getAdapterPosition();
//                        selected_position = selected_position - 1;
//
//                        Log.i("???????????? ????????????", "UnChecked : " + viewholder.getAdapterPosition());
//
//                        // ???????????? ?????? ??????
////                        count = count - 1;
//                        Log.i("???????????? uncheck count", "" + count);
//                        ((MainActivity) MainActivity.ctx).toDid.setText("?????? : " + count);
//
////                        progressBar.incrementProgressBy(-100);
//
////                        lvUp = lvUp - 1;
////                        ((MainActivity) MainActivity.ctx).userLv.setText("LV." + lvUp);
//                    }
//                }
            }
        });

//        viewholder.addWrite.setText(addItems.get(position).getToDoList());
    }


    public void setPositionData(ArrayList<AddItem> addItems) {
        this.addItems = addItems;
    }

    public AddItem getAddItem(int position) {
        return addItems.get(position);
    }

    public void setAddItem(int position, AddItem addItem) {
        addItems.add(position, addItem);
    }

    public void setAddItem(AddItem addItem) {
        addItems.add(addItem);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int getItemCount() {
        return (null != addItems ? addItems.size() : 0);
//        return addItems.size();
    }

//    public void removeItem(int position) {
////         ?????? ?????? ?????? ????????? ????????? ???????????????
//        addItems.remove(position);
////        notifyDataSetChanged();
//    }


    public void removeAllItem() {
        addItems.clear();
    }

    public void saveInShared() {
        // ??? ??? ????????????
        String sharedIn = new String();

        try {
            // ????????? ????????? ???????????? ???
            // i = ???????????? ???????????? ???????????? ?????????
            // ????????? ?????? ????????? i??? ????????????.
            Log.i("addItems.size()", "" + addItems.size());
            for (int i = 0; i < addItems.size(); i++) {
                sharedIn +=
                        addItems.get(i).getToDoList()
                                + " __ ";
                Log.i("addItems.get(i).getToDoList()", "" + sharedIn);
//                            ((MainActivity) MainActivity.ctx).addItems.add(addItem);
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        int lastIndex = addItems.size() - 1;
        Log.i("lastIndex", "" + lastIndex);


        // shared ??????
        long now = System.currentTimeMillis();

        Date date = new Date(now);

        SimpleDateFormat simpleDateFormat
                = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        String getTime = simpleDateFormat.format(date);

        Log.d("myToDo : " + sharedIn, " ???????????? : " + getTime);


        preferenceManager = new PreferenceManager();
        preferenceManager.setString(((MainActivity) MainActivity.ctx).getApplication()
                , "toDoList", getTime + " / " + sharedIn);

//        preferenceManager.setString(((MainActivity) MainActivity.ctx).getApplication()
//                , "toDoList", "" + sharedIn);

//        editor.apply();
        editor.commit();
    }

}
