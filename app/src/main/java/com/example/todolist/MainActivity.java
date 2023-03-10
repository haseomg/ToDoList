package com.example.todolist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ClickInterface, Serializable {

    public static Context ctx;

    TextView toDid;
    TextView userLv;
    TextView gold;
    TextView myProgress;

    ImageView ableDLogo;

    String deleted;

    String myToDo; // 할 일
    String myComplete;

    String myLv; // 내 레벨
    String myDid; // 완료한 일
    String myGold; // 내 골드
    String myExp;

    String btnMent;

    int lvUp;
    int goldPlus;
    int didCount;
    int progress; // 프로그레스 바 경험치


    int position;

    Button add;
    CheckBox checkBox;

    ImageView setting;
    ImageView delete;
    ImageView complete;
    ImageView shop;
    ImageView goldAnimation;

    Animation animation;

    TextView title;

    androidx.recyclerview.widget.RecyclerView recyclerView;

    ProgressBar progressBar;

    ArrayList<AddItem> addItems;
    ArrayList<AddItem> deleteItems;
    ArrayList<AddItem> completeItems;

    AddAdapter addAdapter;
    DeleteAdapter deleteAdapter;
    CompleteAdapter completeAdapter;


    InputMethodManager imm;

    LinearLayoutManager linearLayoutManager;

    PreferenceManager preferenceManager;

    androidx.constraintlayout.widget.ConstraintLayout mainLayout;
    androidx.constraintlayout.widget.ConstraintLayout realMainLayout;

    androidx.constraintlayout.widget.ConstraintLayout frameLayout;

    SharedPreferences shared;
    SharedPreferences.Editor editor;


    public MainActivity() {

    }

    public void setAddItems(ArrayList<AddItem> addItems) {
        this.addItems = addItems;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("MainActivity", "OnCreate()");

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce_effect);

        ctx = this;

        mainLayout = findViewById(R.id.frameLayout);
        realMainLayout = findViewById(R.id.mainLayout);

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        ableDLogo = findViewById(R.id.ableDLogo);
        ableDLogo.setVisibility(View.GONE);

        toDid = findViewById(R.id.toDid);
        userLv = findViewById(R.id.userLevelMain);
        gold = findViewById(R.id.gold);
        myProgress = findViewById(R.id.myProgress);
        goldAnimation = findViewById(R.id.goldAnimation);
//        goldAnimation.setVisibility(View.GONE);
        title = findViewById(R.id.title);
        frameLayout = (androidx.constraintlayout.widget.ConstraintLayout) findViewById(R.id.frameLayout);

        add = findViewById(R.id.addList);

        add.setFocusableInTouchMode(true);

        complete = findViewById(R.id.completeList);
        delete = findViewById(R.id.deleteList);
        shop = findViewById(R.id.shop);
        progressBar = findViewById(R.id.progressBar);

        recyclerView = findViewById(R.id.recyclerView);

        setVariable();

        checkBox = recyclerView.findViewById(R.id.checkBox);

        recyclerView.setHasFixedSize(true); // 리사이클러뷰 셋팅

        preferenceManager = new PreferenceManager(); // ?

//        linearLayoutManager
//                = new LinearLayoutManager((Context) this);

        linearLayoutManager = new LinearLayoutManager
                (this, LinearLayoutManager.VERTICAL, false);


        addAdapter = new AddAdapter(getApplicationContext(), shared, editor);

        addAdapter.notifyDataSetChanged();

        addAdapter.setItemClickListener(new AddAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.e("메인액티비티 addAdapter onItemClick", "Position : " + position);
            }
        });

        recyclerView.setAdapter(addAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL
                , false));

        // arraylist 1
        addItems = new ArrayList<>();

        completeItems = new ArrayList<>();

        deleteItems = new ArrayList<>();


        // JSON 사용해본 부분

//        Collection<?> colValue = shared.getAll().values();
//        Iterator<?> itValue = colValue.iterator();
//        Collection<?> colKey = shared.getAll().keySet();
//        Iterator<?> itKey = colKey.iterator();
//
//        while (itValue.hasNext() && itKey.hasNext()) {
//            String key = (String) itKey.next();
//            String value = (String) itValue.next();
//
//            try {
//                JSONObject jsonObject = new JSONObject(value);
////                myToDo = (String) jsonObject.getString("myToDo");
////                myToDo = shared.getString("toDoList", "sharedTest");
////                AddItem addItem = new AddItem(myToDo);
////                addItems.add(addItem);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
////            addAdapter.notifyDataSetChanged();
//        }


        myToDo = shared.getString("toDoList", "없음");
        if (myToDo.equals("없음")) {
            Log.i("myToDo 데이터(X) 없음 (defValue)", "" + myToDo);
        } else if (myToDo.equals("")) {
            Log.i("myToDo 데이터(X) 빈값 (defValue)", "" + myToDo);
        } else {
            // 데이터가 있는 상태면 리사이클러뷰에 뿌려줘야 돼!
            Log.i("myToDo 데이터(O)", "" + myToDo);

            // 잘라서 ArrayList에 갯수에 맞춰서 각자 뿌려줘야 돼

            String getTimeArray[] = myToDo.split(" / ");
            // 시간
//            Log.i("getTimeArray[0]","" + getTimeArray[0]);
//            // 추가한 할 일들이야
//            Log.i("getTimeArray[1]","" + getTimeArray[1]);

            // 추가한 할 일들에서 __ 만 또 삭제해주려는 것이다
            if (getTimeArray.length == 1) {
                Log.i("if (getTimeArray.length == 1)", "");
                Log.i("getTimeArray[0]", "" + getTimeArray[0]);

            } else {
                String myToDoArray[] = getTimeArray[1].split(" __ ");
//            String myToDoArray[] = myToDo.split(" __ ");
                //반복문 돌려야 해 length 맞게 아이템추가하고 어레이리스트에도 추가해주면 돼
                for (int i = 0; i < myToDoArray.length; i++) {
                    Log.i("순서대로 myToDoArray", "" + myToDoArray[i]);
                    AddItem myToDoItem = new AddItem(myToDoArray[i]);
                    addItems.add(myToDoItem);

                }
            }


//            Log.i("myToDoArrya[0]","" + myToDoArray[0]);
//            Log.i("myToDoArrya[1]","" + myToDoArray[1]);
//            Log.i("myToDoArrya[2]","" + myToDoArray[2]);

        }
        Log.i("String myToDo : ", "" + myToDo);


        // 잘라주고 ArrayList에 다시 넣어줘야 해


        // 아이템이 하나만 생성 됨
        // 이렇게 추가하고 Pinni Hello로 수정은 되지만, defValue는 쉐어드에 남아있다
        // 아이템을 더 추가하고 다시 켜보면 마지막 추가 및 수정한 아이템만 리사이클러뷰에 있다
        // 쉐어드 값 저장하면 textview 상태로 가져와야 함


        // 쉐어드 레벨 값 가져오기
        bringLevelInShared();

        bringGoldInShared();

        bringProgressInShared();

        bringDidCountInShared();

        bringBlackThemeInShared();

//        bringAddShared();

        bringCompleteInShared();

        bringDeleteInShared();


        addAdapter.setArrayList(addItems, completeItems, getApplicationContext());

        addAdapter.notifyItemRangeInserted(position, addItems.size());

//        addAdapter.notifyDataSetChanged();


//        String myToDo에 shared.getString으로 키값이랑 value(default)값 넣어주고
//        ArrayList에 추가해주고 어댑터에 반영시켜주면 될 것 같은데..


        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("메인액티비티 리사이클러뷰 눌렸음 ㅎ", "");
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                position = viewHolder.getAdapterPosition();
                Log.e("메인액티비티 지울 때 포지션", "" + position);


                for (int i = 0; i < addItems.size(); i++) {

                    // 포지션이랑 getToDoList
                    addItems.get(i).getToDoList();
                    Log.i("i번 째 getToDoList", " " + i + "번 째" + addItems.get(i).getToDoList());

                }

                switch (direction) {
                    case ItemTouchHelper.LEFT:

                        Log.e("메인액티비티 지울 때 addItems.size() : ", "" + addItems.size());
                        // 복구하고 나서 아이
                        Log.e("메인액티비티 지울 때 get(position).getToDoList() : ", "" + addItems.get(position).getToDoList());
                        Log.e("메인액티비티 지울 때 포지션 : ", "" + position);


                        // 삭제 - 해야할 일
                        // deleteItems 랑 completeItems 는 메인액티비티 것을 가져다 쓰자

                        Log.i("( 스와이프 시 아이템확인 시작 완인===================================================== ", "");

                        String addShared = new String();
                        for (int i = 0; i < addItems.size(); i++) {
                            addShared +=
                                    addItems.get(i).getToDoList()
                                            + " && ";
                            Log.i("(전) 스와이프 시 아이템확인 (addItems)", "" + addShared);
                        }


                        String sharedIn = new String();

                        try {
                            Log.i("스와이프 시 deleteItems.size", "" + deleteItems.size());
                            for (int i = 0; i < deleteItems.size(); i++) {
                                sharedIn +=
                                        deleteItems.get(i).getToDoList()
                                                + " __ ";
                                Log.i("(전) 스와이프 시 아이템 확인 (deleteItems)", "" + sharedIn);
                            }
                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }

                        // ArrayList에 추가하겠다는 것
                        String myDelete = addItems.get(position).getToDoList();
                        Log.i("스와이프 시 myDelete", "" + myDelete);

                        AddItem deleteItem = new AddItem(myDelete);
                        deleteItems.add(deleteItem);

                        int lastIndex = deleteItems.size() - 1;
                        Log.i("스와이프 시 lastIndex", "" + lastIndex);

//                        Log.d("스와이프 시 myDelete : ", "" + sharedIn);
//                        Log.i("swipeDelete sharedIn---------------------------------------", "");
//                        Log.i("(전) swipeDelete sharedIn 000 ", "" + sharedIn);


                        preferenceManager = new PreferenceManager();
                        preferenceManager.setString(((MainActivity) MainActivity.ctx).getApplication(),
                                "deleteList", "" + sharedIn + myDelete + " __ ");

                        Log.i(" swipeDelete start--------------------------------------- ", "");
                        Log.i(" swipeDelete --------------------------------------- ", "");
                        Log.i(" swipeDelete myDelete + sharedIn 000 ", "" + myDelete + sharedIn);


                        String swipeDelete = shared.getString("deleteList", "없음");
                        Log.i("                                           ", "swipeDelete");

                        if (swipeDelete.equals("없음")) {
                            Log.i("swipeDelete 데이터(X) 111 ", "" + swipeDelete);
                        } else if (swipeDelete.equals("")) {
                            Log.i("swipeDelete 데이터(X) 222 ", "" + swipeDelete);
                        } else {
                            Log.i("swipeDelete 데이터(O) 333 ", "" + swipeDelete);

//                            AddItem deleteItem = new AddItem(myDelete);
//                            deleteItems.add(deleteItem);
                            try {
                                Log.i("swipeDelete deleteItems.size() ", "" + deleteItems.size());
                                Log.i("swipeDelete deleteItems.getToDoList() ", "" + myDelete);
                            } catch (IndexOutOfBoundsException e) {
                                e.printStackTrace();
                            }
                            Log.i("swipeDelete end---------------------------------------", "");
                            Log.i("                                           ", "swipeDelete");

                        }


//                        editor.commit();

//                       saveInDeleteShared();


                        // 그리고 'Delete'액티비티에서
                        // MainActivity 처럼 시작될 때 ArrayList<AddItem> deleteItems에 추가해주고 리사이클러뷰에 반영시켜준다.


                        // 반영 시켜주면, onSwipe에 삭제와 복구 기능 넣어주는데,
                        // 삭제 - 완전삭제! 지워버려!
                        // 복구 - MainActivity onSwiped의 스낵바 참고해서 복구하면
                        // ArrayList<AddItem> addItems (투두리스트)에 다시 추가해줘서 복귀해주자

                        // 쉐어드
                        // 지워주고 저장
                        // 액티비티 onCreate() 될 때 키 값 가져와서 뿌려주자

                        // 쉐어드

                        addItems.remove(position);
//                        addItems.remove(addItems.size() - 1);

                        for (int i = 0; i < addItems.size(); i++) {
                            // 포지션이랑 getToDoList
//                    addItems.get(position).getToDoList();
                            Log.i("삭제 후 getToDoList", " " + i + "번 째" + addItems.get(i).getToDoList());

                        }


//                        addAdapter.removeItem(position);
                        addAdapter.notifyItemRemoved(position);


                        Log.e("메인액티비티 지울 때 addItems.size()", "" + addItems.size());

                        addAdapter.notifyDataSetChanged();

                        saveInShared();

//                        Snackbar.make(recyclerView, deleteItem.getToDoList(), Snackbar.LENGTH_LONG)
//                                .setAction("복구", new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//
////                                        addItems.add(position, deleteItem);
//                                        addAdapter.setAddItem(position, deleteItem);
//                                        addAdapter.notifyItemInserted(position);
//                                        addAdapter.notifyDataSetChanged();
//                                    }
//                                }).show();
//                        break;

                        Log.i(" 스와이프 시 아이템확인 ---------------------------------------", "");

                        String addShare = new String();
                        for (int i = 0; i < addItems.size(); i++) {
                            addShare +=
                                    addItems.get(i).getToDoList()
                                            + " && ";
                            Log.i("(후) 스와이프 시 아이템확인 (addItems)", "" + addShare);
                        }


                        String deleteShare = new String();

                        try {
                            Log.i("스와이프 시 deleteItems.size", "" + deleteItems.size());
                            for (int i = 0; i < deleteItems.size(); i++) {
                                deleteShare +=
                                        deleteItems.get(i).getToDoList()
                                                + " __ ";
                                Log.i("(후) 스와이프 시 아이템 확인 (deleteItems)", "" + deleteShare);
                            }
                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }

                        Log.i("( 스와이프 시 아이템확인 =====================================================", "");

                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView
                    , @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
                                    boolean isCurrentlyActive) {

                new RecyclerViewSwipeDecorator.Builder(canvas, recyclerView, viewHolder,
                        dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(Color.WHITE)
                        .addSwipeLeftLabel("삭제")
                        .setSwipeLeftLabelColor(Color.DKGRAY)
                        .create()
                        .decorate();

                super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(recyclerView);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration
                (recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        // String myToDo 에 어댑터의 addWrite getText 해와서


        // 할 일 추가 버튼
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                position = recyclerView.getAdapter().getItemCount();
//                position = addAdapter.getItemCount();

//                addAdapter = new AddAdapter(addItems);


                // 추가 1
                AddItem data = new AddItem();

                add.requestFocus();

//                 RecyclerView의 첫 줄에 삽입
//                addItems.add(0, data);
                // RecyclerView의 마지막 줄에 삽입
                // 추가 2
                addItems.add(data);

                myLv = myLv + 0;
                myGold = myGold + 0;

//                addAdapter.setAddItem(data);

                // 직전 아이템 다시 추가되는 이유는 어댑터의 뷰홀더가
                // 계속 들어가질 때마다 onBindViewHolder에서 UI에 반영이 안 됐었기 때문이였다!
                // 아이템 위치가 제멋대로 생성되는데..
                Log.i("메인액티비티", "아이템 추가할 때 addItems.size() : " + addItems.size());

                saveInShared();

//                addAdapter.notifyItemInserted(position);
                // 추가 3
                addAdapter.notifyItemRangeInserted(position, addItems.size());
                Log.i("메인액티비티", "아이템 추가할 때 addItems.size() : " + addItems.size());
//                addAdapter.notifyItemRangeChanged(position, addItems.size());
                Log.i("메인액티비티", "아이템 추가할 때 position + addItems.size() : " + position + " + " + addItems.size());
//                addAdapter.notifyItemChanged(position);

                addAdapter.notifyDataSetChanged();
            }
        });


        // 삭제된 목록
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                saveInShared();


                Intent intent = new Intent(MainActivity.this, Delete.class);

//                ArrayList<String> deleteItems = new ArrayList<>();

//                intent.putExtra("deleteItems", deleteItems);

//                saveInDeleteShared();

                startActivity(intent);
            }
        });


        // 완료된 목록
        complete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                for (int i = 0; i < completeItems.size(); i++) {
                    Log.i("completeItems.getToDoList() 보냄", "" + completeItems.get(i).getToDoList());
                }

                Intent intent = new Intent(getApplicationContext(), Complete.class);

                intent.putExtra("completeItems", completeItems);

                startActivity(intent);
            }
        });

        // 상점
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 상점 클릭시 내 음표횟수 확인하고 노래나 테마 구입?
                Intent intent = new Intent(getApplicationContext(), Shop.class);

                startActivity(intent);
            }
        });

        String btnMent = shared.getString("myBlackTheme", "구매");
        if (btnMent.equals("현재 테마")) {
            // 테마 바꿔주고 쉐어드 반영하고 저장하고 불러와
            title.setTextColor(Color.WHITE);
            // 메인의 타이틀 글씨 색깔 -> (title) white
            realMainLayout.setBackgroundColor(Color.BLACK);
            // 메인의 레이아웃 색깔 -> (mainLayout) Black
            recyclerView.setBackgroundColor(Color.BLACK);
            // 메인의 리사이클러뷰 색깔 -> (recyclerView) white
            // 리사이클러뷰 아이템 색깔 -> black
            frameLayout.setBackgroundColor(Color.BLACK);
            // 메인의 하단바 프레임 레이아웃 -> (frameLayout) black

        }

        try {
            String whiteBtnMent = shared.getString("myWhiteTheme", "구매");
            if (whiteBtnMent.equals("현재 테마") || ((Shop) Shop.ctx).whiteBtn.getText().toString().equals("현재 테마")) {
                Log.i("여기 와?", "");
                title.setTextColor(Color.BLACK);
                gold.setTextColor(Color.BLACK);
                userLv.setTextColor(Color.BLACK);
                toDid.setTextColor(Color.BLACK);
                myProgress.setTextColor(Color.BLACK);
                // 메인의 타이틀 글씨 색깔 -> (title) white
                realMainLayout.setBackgroundColor(Color.WHITE);
                // 메인의 레이아웃 색깔 -> (mainLayout) Black
                recyclerView.setBackgroundColor(Color.WHITE);
                // 메인의 리사이클러뷰 색깔 -> (recyclerView) white
                // 리사이클러뷰 아이템 색깔 -> black
                frameLayout.setBackgroundColor(Color.BLACK);
                // 메인의 하단바 프레임 레이아웃 -> (frameLayout) black
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        String originalBtnMent = shared.getString("myOriginalTheme", "구매");
        if (originalBtnMent.equals("현재 테마")) {
            ableDLogo.setVisibility(View.GONE);

            title.setTextColor(Color.parseColor("#589FCF"));
            // 메인의 타이틀 글씨 색깔 -> (title) white
            realMainLayout.setBackgroundColor(Color.parseColor("#FFBCD7EE"));
            // 메인의 레이아웃 색깔 -> (mainLayout) Black
            recyclerView.setBackgroundColor(Color.parseColor("#FFCBE2F2"));
            // 메인의 리사이클러뷰 색깔 -> (recyclerView) white
            // 리사이클러뷰 아이템 색깔 -> black
            frameLayout.setBackgroundColor(Color.parseColor("#0074C2"));
            // 메인의 하단바 프레임 레이아웃 -> (frameLayout) black
            gold.setTextColor(Color.WHITE);
            userLv.setTextColor(Color.WHITE);
            toDid.setTextColor(Color.WHITE);
            myProgress.setTextColor(Color.WHITE);

        }

        String ableDBtnMent = shared.getString("myAbleDTheme", "구매");
        if (ableDBtnMent.equals("현재 테마")) {
            ableDLogo.setVisibility(View.VISIBLE);
            title.setVisibility(View.GONE);
            // 메인의 타이틀 글씨 색깔 -> (title) white
            realMainLayout.setBackgroundColor(Color.WHITE);
            // 메인의 레이아웃 색깔 -> (mainLayout) Black
            recyclerView.setBackgroundColor(Color.WHITE);
            // 메인의 리사이클러뷰 색깔 -> (recyclerView) white
            // 리사이클러뷰 아이템 색깔 -> black
            frameLayout.setBackgroundColor(Color.RED);
            // 메인의 하단바 프레임 레이아웃 -> (frameLayout) black
            gold.setTextColor(Color.RED);
            userLv.setTextColor(Color.RED);
            toDid.setTextColor(Color.RED);
            myProgress.setTextColor(Color.RED);
        }

        if (!ableDBtnMent.equals("현재 테마")) {
            ableDLogo.setVisibility(View.GONE);
        }
    }

    public void setVariable() {

        shared = getSharedPreferences("toDoList", Activity.MODE_PRIVATE);

        editor = shared.edit();

        myToDo = shared.getString("toDo", "빈 값");
    }


    private ConstraintLayout findViewById(androidx.constraintlayout.widget.ConstraintLayout mainLayout) {
        return null;
    }

    public InputMethodManager getImm() {
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        return imm;
    }

    public void setImm(InputMethodManager imm) {
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        this.imm = imm;
    }

    public MainActivity(InputMethodManager imm) {
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        this.imm = imm;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changeTextView:
                Log.i("메인 액티비티의 OnClick", "changeText");
                // 뷰홀더가 갖고있는 UI에 VISIBLE / GONE 적용 시킴
                break;

            case R.id.addWrite:
                Log.i("메인 액티비티의 OnClick", "addWrite");
                break;

            case R.id.linearlayout:
                Log.i("메인 액티비티의 OnClick", "linearLayout");
                break;
        }
    }


    public void onClick(AddItem position) {
        addItems.add(position);
        addAdapter.setPositionData(addItems);
    }

    protected void onStart() {
        super.onStart();
        Log.i("MainActivity", "onStart()");

        setVariable();
        Log.i("--------------Main onStart()", "setVariable();");
    }

    protected void onResume() {
        super.onResume();
        Log.i("MainActivity", "onResume()");

        try {
            String whiteBtnMent = shared.getString("myWhiteTheme", "");
            if (whiteBtnMent.equals("현재 테마") || ((Shop) Shop.ctx).whiteBtn.getText().toString().equals("현재 테마")) {
                Log.i("이거 돼?", "");
                title.setTextColor(Color.BLACK);
                // 메인의 타이틀 글씨 색깔 -> (title) white
                realMainLayout.setBackgroundColor(Color.WHITE);
                // 메인의 레이아웃 색깔 -> (mainLayout) Black
                recyclerView.setBackgroundColor(Color.WHITE);
                // 메인의 리사이클러뷰 색깔 -> (recyclerView) white
                // 리사이클러뷰 아이템 색깔 -> black
                frameLayout.setBackgroundColor(Color.BLACK);
                gold.setTextColor(Color.BLACK);
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

//        saveInShared();
//        saveInDeleteShared();

        // 똑같은 객체 생성 or 똑같은 데이터 연결
//        setVariable();
        bringGoldInShared();
        bringBlackThemeInShared();
        bringWhiteThemeInShared();
        bringAbleDThemeInShared();
        bringOriginalThemeInShared();


        Log.i("--------------Main onResume()", "addItems clear");
        Log.i("(전) addItems clear 클리어", "addItems clear---------------------------------" + addItems.size());
        addItems.clear();
        Log.i("(중간) addItems clear 클리어", "addItems clear---------------------------------" + addItems.size());
        bringAddShared();
        addAdapter.notifyDataSetChanged();
        Log.i("(후) addItems clear 클리어", "addItems clear---------------------------------" + addItems.size());
//        saveInShared();


        Log.i("(전) clear", "---------------------------------" + deleteItems.size());
        deleteItems.clear();
        Log.i("(중간) clear", "---------------------------------" + deleteItems.size());
        bringDeleteInShared();
        Log.i("(후) clear", "---------------------------------" + deleteItems.size());
    }

    protected void onPause() {
        super.onPause();
        Log.i("MainActivity", "onPause()");

        String whiteBtnMent = shared.getString("myWhiteTheme", "");
        if (whiteBtnMent.equals("현재 테마")) {
            Log.i("이거 돼?", "");
            title.setTextColor(Color.BLACK);
            // 메인의 타이틀 글씨 색깔 -> (title) white
            realMainLayout.setBackgroundColor(Color.WHITE);
            // 메인의 레이아웃 색깔 -> (mainLayout) Black
            recyclerView.setBackgroundColor(Color.WHITE);
            // 메인의 리사이클러뷰 색깔 -> (recyclerView) white
            // 리사이클러뷰 아이템 색깔 -> black
            frameLayout.setBackgroundColor(Color.BLACK);
            gold.setTextColor(Color.BLACK);

        }

        saveInShared();
        saveInDeleteShared();
    }

    protected void onStop() {
        super.onStop();
        Log.i("MainActivity", "onStop()");

        // toDoList 쉐어드 반영
        saveInShared();
        saveInDeleteShared();

    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i("MainActivity", "onDestroy()");

        // 쉐어드 저장
        saveInShared();
        saveInDeleteShared();

    }

    public void saveInShared() {


        // 원 키 다중벨류
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

    @Override
    public void onBackPressed() {
        return;
    }


    public void bringLevelInShared() {
        Log.i("bringLevelInShared", "");
        String level = shared.getString("myLevel", "0");
        lvUp = Integer.parseInt(level);
        if (lvUp == 0) {
            Log.i("myLevel 데이터 (X)", "" + lvUp);
        } else {
            Log.i("myLevel 데이터 (0)", "" + lvUp);
            myLv = String.valueOf(lvUp);
            userLv.setText("LV." + myLv);
        }
    }

    public void bringGoldInShared() {
        Log.i("bringGoldInShared", "");
        String golds = shared.getString("myGold", "0");
        goldPlus = Integer.parseInt(golds);
        if (goldPlus == 0) {
            Log.i("myGold 데이터 (X)", "" + goldPlus);
        } else {
            Log.i("myGold 데이터 (O)", "" + goldPlus);
            myGold = String.valueOf(goldPlus);
            gold.setText("내 골드 : " + myGold);
        }
    }

    public void bringProgressInShared() {
        Log.i("bringProgressInShared", "");
        String exp = shared.getString("myExp", "0");
        progress = Integer.parseInt(exp);
        if (progress == 0) {
            Log.i("myExp 데이터 (X)", "" + progress);
        } else {
            Log.i("myExp 데이터 (0)", "" + progress);
            progressBar.setProgress(progress);
            myProgress.setText(progress + "%");
        }
    }

    public void bringDidCountInShared() {
        Log.i("bringDidCountInShared", "");
        String completeCount = shared.getString("myComplete", "0");
        didCount = Integer.parseInt(completeCount);
        if (didCount == 0) {
            Log.i("myComplete 데이터 (X)", "" + didCount);
        } else {
            Log.i("myComplete 데이터 (O)", "" + didCount);
            myDid = String.valueOf(didCount);
            toDid.setText("완료 : " + myDid);
        }

    }


    //쉐어드의 컴플리트를 가져오다
    public void bringCompleteInShared() {
        myComplete = shared.getString("completeList", "없음");
        if (myComplete.equals("없음")) {
            Log.i("myComplete 데이터(X)", "" + myComplete);
        } else {
            // 데이터가 있는 상태면 리사이클러뷰에 뿌려줘야 돼!
            Log.i("myComplete 데이터(O)", "" + myComplete);

            String myCompleteArray[] = myComplete.split(" __ ");
            //반복문 돌려야 해 length 맞게 아이템추가하고 어레이리스트에도 추가해주면 돼
            for (int i = 0; i < myCompleteArray.length; i++) {
                Log.i("순서대로 myCompleteArray", "" + myCompleteArray[i]);
                AddItem myCompleteItem = new AddItem(myCompleteArray[i]);
                completeItems.add(myCompleteItem);

            }


//            Log.i("myToDoArrya[0]","" + myToDoArray[0]);
//            Log.i("myToDoArrya[1]","" + myToDoArray[1]);
//            Log.i("myToDoArrya[2]","" + myToDoArray[2]);

        }
        Log.i("String myToDo : ", "" + myToDo);

    }


    public void bringDeleteInShared() {

        Log.i("                                                                      ", "bringDeleteInShared");
        Log.i("bringDeleteInShared start===================================", "");

        deleted = shared.getString("deleteList", "없음");
        if (deleted.equals("없음")) {
            Log.i("bringDeleteInShared---------------------------------------", "");
            Log.i("bringDeleteInShared 데이터(X) 111 ", "" + deleted);
        } else if (deleted.equals("")) {
            Log.i("bringDeleteInShared 데이터(X) 222 ", "" + deleted);
        } else {
            // 데이터가 있는 상태면 리사이클러뷰에 뿌려줘야 돼!
            Log.i("bringDeleteInShared 데이터(O) 333", "" + deleted);
            Log.i("bringDeleteInShared---------------------------------------", "");

            String deleteArray[] = deleted.split(" __ ");
            //반복문 돌려야 해 length 맞게 아이템추가하고 어레이리스트에도 추가해주면 돼
            for (int i = 0; i < deleteArray.length; i++) {
                Log.i("bringDeleteInShared 순서대로 myDeleteArray", "" + deleteArray[i]);

                AddItem deleteItem = new AddItem(deleteArray[i]);
                deleteItems.add(deleteItem);

            }


        }
        Log.i("bringDeleteInShared End===================================", "");
        Log.i("                                                                      ", "bringDeleteInShared");

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
                    addItems.add(myReAddItem);

                }
            }

        }
        Log.i("bringDeleteInShared End===================================", "");
        Log.i("                                                                      ", "bringDeleteInShared");

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

    public void bringBlackThemeInShared() {
        btnMent = shared.getString("myBlackTheme", "구매");
        Log.i("bringBlackThemeInShared", "구매");
        if (btnMent.equals("구매")) {
            Log.i("myBlackTheme 데이터 (X)", "" + btnMent);
        } else {
            Log.i("myBlackTheme 데이터 (O)", "" + btnMent);
//            blackAndWhiteBuyBtn.setText(btnMent);

        }
    }

    public void bringWhiteThemeInShared() {
        String whiteBtnMent = shared.getString("myWhiteTheme", "구매");
        Log.i("bringWhiteThemeInShared", "구매");
        if (whiteBtnMent.equals("구매")) {
            Log.i("myWhiteTheme 데이터 (X)", "" + whiteBtnMent);
        } else {
            Log.i("myWhiteTheme 데이터 (O)", "" + whiteBtnMent);
//            blackAndWhiteBuyBtn.setText(btnMent);

        }
    }

    public void bringAbleDThemeInShared() {
        String ableDBtnMent = shared.getString("myAbleDTheme", "구매");
        Log.i("bringAbleDThemeInShared", "구매");
        if (ableDBtnMent.equals("구매")) {
            Log.i("myAbleDTheme 데이터 (X)", "" + ableDBtnMent);
        } else {
            Log.i("myAbleDTheme 데이터 (O)", "" + ableDBtnMent);
//            blackAndWhiteBuyBtn.setText(btnMent);

        }
    }

    public void bringOriginalThemeInShared() {
        String originalBtnMent = shared.getString("myOriginalTheme", "구매");
        Log.i("bringOriginalThemeInShared", "현재 테마");
        if (originalBtnMent.equals("구매")) {
            Log.i("myOriginalTheme 데이터 (X)", "" + originalBtnMent);
        } else {
            Log.i("myOriginalTheme 데이터 (O)", "" + originalBtnMent);
//            blackAndWhiteBuyBtn.setText(btnMent);

        }
    }


}