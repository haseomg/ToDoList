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


            // 쉐어드랑 에디터
            // 추가한 코드
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


            // 쉐어드랑 에디터
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
            if (btnMent.equals("현재 테마")) {
                viewholder.layout.setBackgroundColor(Color.BLACK);
                viewholder.changeText.setTextColor(Color.WHITE);
                viewholder.addWrite.setTextColor(Color.WHITE);
                Log.i("현재테마는 블랙 테마임 AddAdapter","");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        String whiteBtnMent = shared.getString("myWhiteTheme", "");
        try {
            if (whiteBtnMent.equals("현재 테마")) {
                viewholder.layout.setBackgroundColor(Color.WHITE);
                viewholder.changeText.setTextColor(Color.BLACK);
                viewholder.addWrite.setTextColor(Color.BLACK);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        String ableDBtnMent = shared.getString("myAbleDTheme", "");
        try {
            if (ableDBtnMent.equals("현재 테마")) {
                viewholder.layout.setBackgroundColor(Color.RED);
                viewholder.changeText.setTextColor(Color.WHITE);
                viewholder.addWrite.setTextColor(Color.WHITE);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


//        try {
//            if (((Shop) Shop.ctx).originalBtnMent.equals("현재 테마")) {
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

        Log.i("애드어댑터의 온바인드 뷰홀더 메서드 실행", "" + addItems.get(position) + addItem.getToDoList());


//        String addText = "";
//        viewholder.addWrite.setText(Html.fromHtml(addText));

//        realMainLayout = ((MainActivity) MainActivity.ctx).realMainLayout;
//
//        realMainLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("리얼메인레이아웃 눌렸다!", "");
//                if (viewholder.addWrite.length() > 1) {
//                    Log.i("리얼메인레이아웃 클릭 시 addItem.getToDoList", "" +
//                            addItem.getToDoList());
//                    viewholder.changeText.setText(viewholder.addWrite.getText().toString());
//                    viewholder.changeText.setVisibility(View.VISIBLE);
//                    viewholder.addWrite.setVisibility(View.GONE);
//                    Log.i("리얼메인레이아웃 클릭 시", "+ 직전 에딧텍스트 값 확인" +
//                            viewholder.addWrite.getText().toString());
//                    addItem.setToDoList(viewholder.addWrite.getText().toString());
//                }
//            }
//        });


        viewholder.addWrite.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

//                if (hasFocus) {
//                    Log.i("어댑터의 에딧텍스트 포커스 맞춰짐", "");
////                    hasFocus = false;
//                }
                if (!hasFocus) {
                    Log.i("어댑터의 에딧텍스트 포커스가 안 맞을 때", "");
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
                            // 쉐어드 한개씩 부족하게 됨
                            // i = 아이템이 추가되는 순차적인 포지션
                            // 작거나 같을 때까지 i를 추가한다.
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

                        Log.d("myToDo : " + sharedIn, " 현재시간 : " + getTime);

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
                        Log.i("어댑터의 포커스가 맞을때 addItem.getToDoList", "" +
                                addItem.getToDoList());

                        viewholder.addWrite.setText(viewholder.changeText.getText().toString());
                        viewholder.addWrite.setVisibility(View.VISIBLE);

                        viewholder.changeText.setText(viewholder.addWrite.getText().toString());
                        viewholder.changeText.setVisibility(View.GONE);
                        Log.i("어댑터의 에딧텍스트 포커스인 테스트", "+ 직전 에딧텍스트 값 확인" +
                                viewholder.addWrite.getText().toString());
                    }
                }
            }
        });


        // TextView -> EditText로 변경할 때
        // 텍스트뷰 클릭 -> 뷰 클릭으로 변경


        // TextView 클릭하면 EditText로 변경함 (입력 상태 **수정)ㅇ
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
//                Log.i("어댑터에서 TextView 클릭)", "" + position);
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


        // 레이아웃을 클릭하면 changeText로 값을 넘겨주고 TextView로 변경
        // setText
        viewholder.layout.setOnClickListener(new View.OnClickListener() {
            boolean input;

            @Override
            public void onClick(View v) {

//                notifyDataSetChanged();

                // 쉐어드 수정 안 되는 상태
                if (viewholder.changeText.length() > 0 && input == true) {
                    viewholder.addWrite.setVisibility(View.VISIBLE);
                    viewholder.addWrite.setText(addItem.getToDoList());
                    Log.i("addWrite getText()", "" + viewholder.addWrite.getText().toString());
                    viewholder.changeText.setText(addItem.getToDoList());
                    Log.i("changeText getText()", "" + viewholder.changeText.getText().toString());
                    viewholder.changeText.setVisibility(View.GONE);
                    Log.i("어댑터에서 TextView 클릭)", "" + position);

                    Log.i("input 확인 (전 22)", "" + input);
                    input = false;
                    Log.i("input 확인 (전 22)", "" + input);

                } else if (viewholder.addWrite.length() > 0 && input == false) {
                    viewholder.layout.setClickable(true);

                    InputMethodManager imm = ((MainActivity) MainActivity.ctx).imm;
                    imm.hideSoftInputFromWindow(viewholder.addWrite.getWindowToken(), 0);

                    viewholder.changeText.setVisibility(View.VISIBLE);
//                    viewholder.addWrite.setText();
                    viewholder.changeText.setText(viewholder.addWrite.getText().toString());

                    // 쉐어드 전에 ArrayList안의 AddItem에 셋팅을 안해줘서 바보짓 오래오래했음..
                    addItem.setToDoList(viewholder.addWrite.getText().toString());


                    String sharedIn = new String();

                    try {
                        // 쉐어드 한개씩 부족하게 됨
                        // i = 아이템이 추가되는 순차적인 포지션
                        // 작거나 같을 때까지 i를 추가한다.
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


                    // shared 구간
                    long now = System.currentTimeMillis();

                    Date date = new Date(now);

                    SimpleDateFormat simpleDateFormat
                            = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                    String getTime = simpleDateFormat.format(date);

                    Log.d("myToDo : " + sharedIn, " 현재시간 : " + getTime);

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

//                    Log.i("어댑터의 str 후 : ", "" + addItems.get(position).getToDoList());

//                    Log.i("어댑터에서 레이아웃 클릭 시 : ", "" + addItems.get(position).getToDoList());
////                    addItem.setToDoList("");
//                    // layout 다시 누르면 원래대로 돌아감
                    Log.i("어댑터에서 layout 클릭) : ", "" + position);

                    Log.i("input 확인 (전)", "" + input);
                    input = true;
                    Log.i("input 확인 (후)", "" + input);


                } else if (addItems.get(position).getToDoList().length() == 0) {
                    viewholder.checkBox.setChecked(false);
                    Log.e("글자의 길이가 0일 때", "setChecked(false)가 : " + position);
                    viewholder.layout.setClickable(false);
                    Log.e("글자의 길이가 0일 때", "layout(false) : " + position);
                    Toast.makeText(ctx, "한 글자 이상 입력해주세요.", Toast.LENGTH_SHORT);
                } else {
                    viewholder.checkBox.setChecked(false);
                    viewholder.layout.setClickable(false);
                    Log.e("어댑터에서 layout 클릭", "취소 : " + position);
                    Toast.makeText(ctx, "한 글자 이상 입력해주세요.", Toast.LENGTH_SHORT);
                }
                viewholder.layout.setClickable(true);
            }
        });


        Log.i("메인 액티비티의 add 버튼", "");

        // 최초 체크박스 null
        viewholder.checkBox.setOnCheckedChangeListener(null);

        viewholder.checkBox.setChecked(addItem.isSelected());

//        viewholder.checkBox.setChecked(addItems.contains(String.valueOf(position)));

        viewholder.checkBox.setOnClickListener((View.OnClickListener) ctx);
        // setOnClickListener 코드를 추가해서 클릭 먹음

        viewholder.checkBox.setTag(position);

        progressBar = ((MainActivity) MainActivity.ctx).progressBar;
        level = ((MainActivity) MainActivity.ctx).myLv;
        checkToDo = viewholder.addWrite.getText().toString();

        // 취소선 해제
//        viewholder.changeText.setPaintFlags(1);
        viewholder.changeText.setPaintFlags
                (viewholder.changeText.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        Log.i("setPaintFlages","TextView 취소선 해제");

//        if (viewholder.changeText.getPaintFlags() != 0 && viewholder.addWrite == viewholder.changeText) {
//        } else {
//            viewholder.changeText.getPaintFlags();
//            Log.i("getPaintFlage() != 0","체크박스 전에 표시해놓음 : " + addItems.get(position).toDoList);
//        }
        // 체크박스 체크 시 골드 획득, 완료하셨습니다 애니메이션 효과
        viewholder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                int position = viewholder.getAdapterPosition();

                if (count >= 0) {
                    if (viewholder.addWrite.length() != 0 &&
                            viewholder.changeText.length() != 0 && viewholder.checkBox.isChecked()) {
                        Log.i("AddAdapter 체크박스의 값이 0이 아닐 때", "");
                        Log.i("AddAdapter 체크박스의 값이 있을 때", "");

                        // 체크
//                        addItem.setSelected(isChecked);
//                        viewholder.checkBox.setChecked(!addItem.isSelected);

//                        if (viewholder.checkBox.isChecked()) {

                        // 다시 추가했을 때 밑줄 쳐져있어서 일단 주석
                        if (viewholder.changeText.getPaintFlags() == 0) {
                            Log.i("getPaintFlags() == 0","");
                        } else {
                            viewholder.changeText.setPaintFlags(viewholder.changeText.getPaintFlags() |
                                    Paint.STRIKE_THRU_TEXT_FLAG);
                            Log.i("getPaintFlags","취소선 적용");
                        }

                        addItems.remove(position);
                        notifyItemRemoved(position);

                        // 골드 50원씩 추가
                        String myGold = shared.getString("myGold", "0");
                        goldPlus = Integer.parseInt(myGold);
                        if (goldPlus >= 0) {
                            goldPlus = goldPlus + 50;
                            ((MainActivity) MainActivity.ctx).gold.setText("내 골드 : " + goldPlus);
                            Log.i("어댑터의 체크박스", "goldPlus : " + goldPlus);
                            // 애니메이션 효과도 줄 거야 골드 통통
                            ((MainActivity) MainActivity.ctx).goldAnimation.startAnimation(
                                    ((MainActivity) MainActivity.ctx).animation);


                            // saveInGoldShared
                            preferenceManager = new PreferenceManager();
                            preferenceManager.setString(((MainActivity) MainActivity.ctx).getApplication()
                                    , "myGold", "" + goldPlus);
                            Log.i("myGold 쉐어드 저장", "" + goldPlus);

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

                            // 안 맞아 처음 누른 아이템 따라서 나와
//                            Log.i("completeItems.get(position).getToDoList()","" + completeItems.get(getPosition()).getToDoList());
//
//                            for (int i = 0; i < completeItems.size(); i++) {
//                             포지션 값 수상해
//                            int okPosition = completeAdapter.
//                             IndexOutOfBoundsException
//                            Log.i("completeItem getToDoList()", "" + completeItems.get(okP).getToDoList());
//                            }

                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }


                        // 쉐어드

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


                        // 그리고 'Complete'액티비티에서
                        // MainActivity 처럼 시작될 때 ArrayList<AddItem> completeItems 추가해주고 리사이클러뷰에 반영시켜준다.
                        // 반영 시켜주면, 끝

                        // 쉐어드
                        // 지워주고 저장
                        // 액티비티 onCreate() 될 때 키 값 가져와서 뿌려주자


                        Log.i("어댑터의 체크박스", "isChecked : " + position);


                        String completeCount = shared.getString("myComplete", "0");
                        count = Integer.parseInt(completeCount);
                        // 체크 시 완료 횟수
                        count = count + 1;
                        Log.i("어댑터의 체크박스", "check count : " + count);
                        ((MainActivity) MainActivity.ctx).toDid.setText("완료 : " + count);

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
                        // 만약 progressBar의 사이즈가 100이 되면
                        // 1. 메인의 레벨은 +1 (ok)
                        // 2. progressBar 진행률은 0으로 돌아간다.
                        // *3. 지금은 체크박스 클릭할 때마다 메인의 레벨이 1씩 추가되게 설정해놓은

                        String levelShared = shared.getString("myLevel", "1");
                        lvUp = Integer.parseInt(levelShared);
                        Log.i("AddAdapter lvUp Check (전)", "" + lvUp);


                        if (progress == 100) {
                            progress = 0;
                            progressBar.setProgress(progress);
                            ((MainActivity) MainActivity.ctx).myProgress.setText(progress + "%");
                            // 레벨 + 1
//                            if (progress == 100) {
                            lvUp = lvUp + 1;
                            Log.i("AddAdapter lvUp Check (후)", "" + lvUp);
                            ((MainActivity) MainActivity.ctx).userLv.setText("LV." + lvUp);

                            // 레벨 바꿔줘
                            if (lvUp == 5) {
                                Toast.makeText((MainActivity) MainActivity.ctx, "테마가 잠금 해제되었습니다!", Toast.LENGTH_SHORT).show();
                            }
                            if (lvUp == 20) {
                                Toast.makeText((MainActivity) MainActivity.ctx, "에이블디 테마가 잠금 해제되었습니다!", Toast.LENGTH_SHORT).show();
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
                        Log.i("AddAdapter 체크박스의 값이 비었을 때", "");
                    }


                }


                // 체크박스를 눌렀다 풀 때 필요했던 코드

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
//                        ((MainActivity) MainActivity.ctx).gold.setText("내 골드 : " + goldPlus);
//                        Log.i("어댑터의 체크박스", "goldPlus : " + goldPlus);
//
//                        addItem.setSelected(!isChecked);
//                        viewholder.checkBox.setChecked(false);
//                        viewholder.changeText.setPaintFlags(0);
//                        selected_position = viewholder.getAdapterPosition();
//                        selected_position = selected_position - 1;
//
//                        Log.i("어댑터의 체크박스", "UnChecked : " + viewholder.getAdapterPosition());
//
//                        // 언체크시 완료 횟수
////                        count = count - 1;
//                        Log.i("어댑터의 uncheck count", "" + count);
//                        ((MainActivity) MainActivity.ctx).toDid.setText("완료 : " + count);
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
////         바로 아래 코드 겹쳐서 두개씩 지워졌었어
//        addItems.remove(position);
////        notifyDataSetChanged();
//    }


    public void removeAllItem() {
        addItems.clear();
    }

    public void saveInShared() {
        // 원 키 다중밸류
        String sharedIn = new String();

        try {
            // 쉐어드 한개씩 부족하게 됨
            // i = 아이템이 추가되는 순차적인 포지션
            // 작거나 같을 때까지 i를 추가한다.
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


        // shared 구간
        long now = System.currentTimeMillis();

        Date date = new Date(now);

        SimpleDateFormat simpleDateFormat
                = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        String getTime = simpleDateFormat.format(date);

        Log.d("myToDo : " + sharedIn, " 현재시간 : " + getTime);


        preferenceManager = new PreferenceManager();
        preferenceManager.setString(((MainActivity) MainActivity.ctx).getApplication()
                , "toDoList", getTime + " / " + sharedIn);

//        preferenceManager.setString(((MainActivity) MainActivity.ctx).getApplication()
//                , "toDoList", "" + sharedIn);

//        editor.apply();
        editor.commit();
    }

}
