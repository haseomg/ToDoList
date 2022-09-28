package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class Delete extends Activity {

    TextView listEmpty;
    TextView deleteTitle;


    Button close;

    PreferenceManager preferenceManager;

    androidx.constraintlayout.widget.ConstraintLayout deleteMainLayout;
    FrameLayout deleteFrameLayout;

    private RecyclerViewEmptySupport recycler;

    ArrayList<AddItem> deleteItems;
    AddItem deleteItem;
    private DeleteAdapter deleteAdapter;

    ArrayList<AddItem> reAddItems;
    AddAdapter reAddAdapter = new AddAdapter();

    String delete;

    int position;
    static Context ctx;

    LinearLayoutManager linearLayoutManager;

    SharedPreferences shared;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_delete);
        Log.i("Delete", "onCreate()");

        ctx = this;

        deleteMainLayout = findViewById(R.id.deleteMainLayout);

        deleteFrameLayout = findViewById(R.id.deleteFrameLayout);

        deleteTitle = findViewById(R.id.deleteTitle);

        listEmpty = findViewById(R.id.deleteListEmpty);

        recycler = (RecyclerViewEmptySupport) findViewById(R.id.deleteRecycler);

        close = findViewById(R.id.deleteClose);

        recycler.setHasFixedSize(true);

        deleteAdapter = new DeleteAdapter(this, shared, editor);

        linearLayoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);

        recycler.setLayoutManager(linearLayoutManager);


        // 메인의 애드어댑터 쓰려고
//        reAddAdapter = new AddAdapter(getApplicationContext(), shared, editor);
//        addAdapter = new AddAdapter(this, shared,editor);

        setShared();


//        deleteItems = new ArrayList<>();
        deleteItems = new ArrayList<>();

        // 메인의 arrayList 쓰려공
//        reAddItems = ((MainActivity) MainActivity.ctx).addItems;
        reAddItems = new ArrayList<>();

//        saveInAddShared();


        if (deleteItems.size() != 0) {
            listEmpty.setVisibility(View.GONE);
            recycler.setAdapter(deleteAdapter);
            Log.i("deleteItems.size()가 0이 아닐 때", "");
        } else {
            listEmpty.setVisibility(View.VISIBLE);
            recycler.setAdapter(deleteAdapter);
            recycler.setEmptyView(findViewById(R.id.deleteListEmpty));
            Log.i("deleteItems.size()가 0일 때", "");
        }


//        position = deleteAdapter.getPosition(position);

//        deleteItem = new AddItem("");

        bringDeleteInShared();

//        saveInShared();
//        editor.commit();

        swipeDelete();

        deleteAdapter.setArrayList(deleteItems, reAddItems, getApplicationContext());

        Log.i("액티비티 deleteItems", "" + deleteItems.toString());

        deleteAdapter.notifyItemRangeInserted(position, deleteItems.size());

        Log.i("position, deleteItems.size()", "" + position + deleteItems.size());

        deleteAdapter.notifyDataSetChanged();

        deleteAdapter.setItemClickListener(new DeleteAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.e("deleteActivity deleteAdapter onItemClick", "Position : " + position);
            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                reAddAdapter.notifyDataSetChanged();
                Log.i("close 버튼 클릭시 saveInShared() 작동", "");

                saveInShared();

                for (int i = 0; i < reAddItems.size(); i++) {
                    Log.i("addItems.getToDoList()음 보냄", "" + reAddItems.get(i).getToDoList());
                }

//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//
//                intent.putExtra("reAddItems", reAddItems);

                finish();
            }
        });

        String btnMent = shared.getString("myBlackTheme", "구매");
        if (btnMent.equals("현재 테마")) {
            // 휴지통 상단 프레임 블랙 (deleteFrameLayout)
            deleteFrameLayout.setBackgroundColor(Color.BLACK);
            // 레이아웃 블랙 (deleteMainLayout)
            deleteMainLayout.setBackgroundColor(Color.BLACK);
            // 버튼 백그라운드 블랙 (deleteClose)
            close.setBackgroundColor(Color.BLACK);
            // 버튼 텍스트 화이트
            close.setTextColor(Color.WHITE);
            // 리사이클러뷰 아이템 블랙
        } else {

        }

        String whiteBtnMent = shared.getString("myWhiteTheme", "구매");
        if (whiteBtnMent.equals("현재 테마")) {
            // 휴지통 상단 프레임 블랙 (deleteFrameLayout)
            deleteFrameLayout.setBackgroundColor(Color.WHITE);
            // 레이아웃 블랙 (deleteMainLayout)
            deleteMainLayout.setBackgroundColor(Color.WHITE);
            deleteTitle.setTextColor(Color.BLACK);
            // 버튼 백그라운드 블랙 (deleteClose)
            close.setBackgroundColor(Color.WHITE);
            // 버튼 텍스트 화이트
            close.setTextColor(Color.BLACK);
            // 리사이클러뷰 아이템 블랙
        } else {

        }

        String ableDBtnMent = shared.getString("myAbleDTheme", "구매");
        if (ableDBtnMent.equals("현재 테마")) {
            // 휴지통 상단 프레임 블랙 (deleteFrameLayout)
            deleteFrameLayout.setBackgroundColor(Color.WHITE);
            // 레이아웃 블랙 (deleteMainLayout)
            deleteMainLayout.setBackgroundColor(Color.WHITE);
            deleteTitle.setTextColor(Color.RED);
            // 버튼 백그라운드 블랙 (deleteClose)
            close.setBackgroundColor(Color.RED);
            // 버튼 텍스트 화이트
            close.setTextColor(Color.WHITE);
            // 리사이클러뷰 아이템 블랙
        } else {

        }

        String originalBtnMent = shared.getString("myOriginalTheme","적용");
            if (originalBtnMent.equals("현재 테마")) {

        }
    }

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

    protected void onStart() {
        super.onStart();
        Log.i("Delete", "onStart()");

//        bringDeleteInShared();
    }

    protected void onResume() {
        super.onResume();
        Log.i("Delete", "onResume()");

//        saveInShared();
//        saveInAddShared();

    }

    protected void onPause() {
        super.onPause();
        Log.i("Delete", "onPause()");

        //        버튼이나 정지 누르고 나오면 onPause()

//        saveInShared();
//        saveInAddShared();
    }

    protected void onStop() {
        super.onStop();
        Log.i("Delete", "onStop()");

        saveInShared();
//        saveInAddShared();
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i("Delete", "onDestroy()");

//        saveInShared();
//        saveInAddShared();
    }

    public void saveInShared() {


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


        Log.d("myDelete : ", "" + sharedIn);


        preferenceManager = new PreferenceManager();
        preferenceManager.setString(((MainActivity) MainActivity.ctx).getApplication()
                , "deleteList", "" + sharedIn);

//        editor.apply();
        editor.commit();
    }

    public void saveInAddShared() {
        // 원 키 다중밸류
        String sharedIn = new String();

        try {
            // 쉐어드 한개씩 부족하게 됨
            // i = 아이템이 추가되는 순차적인 포지션
            // 작거나 같을 때까지 i를 추가한다.
            Log.i("addItems.size()", "" + reAddItems.size());
            for (int i = 0; i < reAddItems.size(); i++) {
                sharedIn +=
                        reAddItems.get(i).getToDoList()
                                + " __ ";
                Log.i("addItems.get(i).getToDoList()", "" + sharedIn);
//                            ((MainActivity) MainActivity.ctx).addItems.add(addItem);
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        int lastIndex = reAddItems.size() - 1;
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

    public void bringAddShared() {

        Log.i("                                                                      ", "bringAddShared");
        Log.i("bringAddShared start===================================", "");

        String reAdd = shared.getString("toDoList", "없음");
        if (reAdd.equals("없음")) {
            Log.i("bringAddShared---------------------------------------", "");
            Log.i("bringAddShared 데이터(X) 111 ", "" + reAdd);
        } else if (reAdd.equals("")) {
            Log.i("bringAddShared 데이터(X) 222 ", "" + reAdd);
        } else {
            // 데이터가 있는 상태면 리사이클러뷰에 뿌려줘야 돼!
            Log.i("bringAddShared 데이터(O) 333", "" + reAdd);
            Log.i("bringAddShared---------------------------------------", "");


            String getTimeArray[] = reAdd.split(" / ");
            // 시간
//            Log.i("getTimeArray[0]","" + getTimeArray[0]);
//            // 추가한 할 일들이야
//            Log.i("getTimeArray[1]","" + getTimeArray[1]);

            // 추가한 할 일들에서 __ 만 또 삭제해주려는 것이다
            if (getTimeArray.length == 1) {
                Log.i("if (getTimeArray.length == 1)", "");
                Log.i("getTimeArray[0]", "" + getTimeArray[0]);

            } else {
                String reAddArray[] = getTimeArray[1].split(" __ ");
//            String reAddArray[] = reAdd.split(" __ ");
                //반복문 돌려야 해 length 맞게 아이템추가하고 어레이리스트에도 추가해주면 돼
                for (int i = 0; i < reAddArray.length; i++) {
                    Log.i("순서대로 reAddArray", "" + reAddArray[i]);
                    AddItem myReAddItem = new AddItem(reAddArray[i]);
                    reAddItems.add(myReAddItem);

                }
            }

        }
        Log.i("bringDeleteInShared End===================================", "");
        Log.i("                                                                      ", "bringDeleteInShared");

    }


    // 이 메서드 지우면 휴지통에 아무것도 음슴
    public void bringDeleteInShared() {
        delete = shared.getString("deleteList", "없음");
        Log.i("delete shared", "" + delete);

        String pinni = shared.getString("Pinni", "");
        Log.i("Pinni shared", "" + pinni);

        if (delete.equals("")) {
            Log.i("delete 데이터(X) 1 (빈값) ", "" + delete);
//
        } else if (delete.equals("없음")) {
            Log.i("delete 데이터(X) 2 (없음) ", "" + delete);

        } else {
//             데이터가 있는 상태면 리사이클러뷰에 뿌려줘야 돼!
            Log.i("delete 데이터(O) 3 ", "" + delete);


            String deleteArray[] = delete.split(" __ ");
            //반복문 돌려야 해 length 맞게 아이템추가하고 어레이리스트에도 추가해주면 돼

            for (int i = 0; i < deleteArray.length; i++) {

                Log.i("순서대로 myDeleteArray", "" + deleteArray[i]);

                AddItem deleteItem = new AddItem(deleteArray[i]);
                deleteItems.add(deleteItem);

                Log.i("deleteArray[i]", "< " + deleteItem + " >");
            }
        }
        Log.i("String myDelete : ", "" + delete);
    }

    public void setShared() {
        shared = getSharedPreferences("toDoList", Activity.MODE_PRIVATE);

        editor = shared.edit();

        delete = shared.getString("delete", "빈 값");
    }

    public void swipeDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                position = viewHolder.getAdapterPosition();
                Log.e("메인액티비티 지울 때 포지션", "" + position);


                for (int i = 0; i < deleteItems.size(); i++) {
                    // 포지션이랑 getToDoList
                    deleteItems.get(i).getToDoList();
                    Log.i("i번 째 getToDoList", " " + i + "번 째" + deleteItems.get(i).getToDoList());

                }

                switch (direction) {
                    case ItemTouchHelper.LEFT:

                        Log.e("메인액티비티 지울 때 deleteItems.size() : ", "" + deleteItems.size());
                        Log.e("메인액티비티 지울 때 get(position).getToDoList() : ", "" + deleteItems.get(position).getToDoList());
                        Log.e("메인액티비티 지울 때 포지션 : ", "" + position);


                        // 삭제 - 해야할 일
                        // deleteItems랑 completeItems는 메인액티비티 것을 가져다 쓰자

                        // ArrayList에 추가하겠다는 것
                        String myDelete = deleteItems.get(position).getToDoList();
                        Log.i("스와이프 시 myDelete", "" + myDelete);

                        int lastIndex = deleteItems.size() - 1;
                        Log.i("스와이프 시 lastIndex", "" + lastIndex);

//                        addItems.remove(addItems.size() - 1);

                        for (int i = 0; i < deleteItems.size(); i++) {
                            // 포지션이랑 getToDoList
//                    addItems.get(position).getToDoList();
                            Log.i("삭제 후 getToDoList", " " + i + "번 째" + deleteItems.get(i).getToDoList());
                        }

                        Log.i("삭제 전 deleteItems.size()", "" + deleteItems.size());

//                        addAdapter.removeItem(position);

//                        잘라서 쉐어드에 반영해야 돼

//                        saveInShared();

                        String sharedIn = new String();

                        try {
                            Log.i("스와이프 시 deleteItems.size", "" + deleteItems.size());
                            for (int i = 0; i < deleteItems.size(); i++) {
                                sharedIn +=
                                        deleteItems.get(i).getToDoList()
                                                + " __ ";
                            }
                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }

                        Log.i("(전) 휴지통에서 스와이프 시 deleteItems.get(i).getToDoList", "" + sharedIn);


//                        preferenceManager = new PreferenceManager();
//                        preferenceManager.settingShared
//                                (((MainActivity) MainActivity.ctx).getApplication(),
//                                "deleteList", "" + sharedIn);


                        deleteItems.remove(position);

                        String sharedCheck = new String();

                        try {
                            for (int i = 0; i < deleteItems.size(); i++) {
                                sharedCheck +=
                                        deleteItems.get(i).getToDoList()
                                                + " __ ";
                                Log.i("완전 삭제 후 deleteItems.get(i).getToDoList ", "" + sharedCheck);
                            }
                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }


                        Log.i("(후) 스와이프 시 deleteItems.get(i).getToDoList ", "" + sharedCheck);


                        String deleteShared = shared.getString("deleteList", "zz");
                        Log.i("deleteShared (전) ", "" + deleteShared);
//
                        preferenceManager = new PreferenceManager();
                        preferenceManager.settingShared
                                (((Delete) Delete.ctx).getApplication(),
                                        "deleteList", "" + sharedCheck);

                        deleteAdapter.notifyItemRemoved(position);

                        String deletedShared = shared.getString("deleteList", "zz");
                        Log.i("deleteShared (후) ", "" + deletedShared);


                        Log.e("Delete 완전삭제 시 deleteItems.size()", "" + deleteItems.size());

                        deleteAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView
                    , @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
                                    boolean isCurrentlyActive) {

                new RecyclerViewSwipeDecorator.Builder(canvas, recyclerView, viewHolder,
                        dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(Color.WHITE)
                        .addSwipeLeftLabel("완전 삭제")
                        .setSwipeLeftLabelColor(Color.DKGRAY)
                        .create()
                        .decorate();

                super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(recycler);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration
                (recycler.getContext(), linearLayoutManager.getOrientation());
        recycler.addItemDecoration(dividerItemDecoration);

    }

}