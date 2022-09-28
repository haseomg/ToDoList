package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Complete extends Activity {

    FrameLayout frameLayout;

    TextView completeTitle;
    TextView listEmpty;
    Button completeClose;
    RecyclerViewEmptySupport recycler;
    LinearLayoutManager linearLayoutManager;

    androidx.constraintlayout.widget.ConstraintLayout completeLayout;

    ArrayList<AddItem> completeItems;
    AddItem completeItem;
    CompleteAdapter completeAdapter;

    int position;
    public static Context ctx;

    String complete;

    SharedPreferences shared;
    SharedPreferences.Editor editor;

    String btnMent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_complete);
        Log.i("Complete", "onStart()");

//        shared = getSharedPreferences("shared", Activity.MODE_PRIVATE);
//        editor = shared.edit();

        ctx = this;

        completeLayout = (androidx.constraintlayout.widget.ConstraintLayout) findViewById(R.id.completeMainLayout);
        frameLayout = findViewById(R.id.completeFrameLayout);

        completeTitle = findViewById(R.id.completeTitle);

        listEmpty = findViewById(R.id.listEmpty);

        recycler = findViewById(R.id.recycler);

        completeClose = findViewById(R.id.completeClose);

        recycler.setHasFixedSize(true);

        completeAdapter = new CompleteAdapter(ctx, shared, editor);

        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        recycler.setLayoutManager(linearLayoutManager);

        setShared();


        completeItems = new ArrayList<>();


        if (completeItems.size() != 0) {
            listEmpty.setVisibility(View.GONE);
            recycler.setAdapter(completeAdapter);
            Log.i("completeItems.size()가 0이 아닐 때", "");
        } else {
            listEmpty.setVisibility(View.VISIBLE);
            recycler.setAdapter(completeAdapter);
            recycler.setEmptyView(findViewById(R.id.listEmpty));
            Log.i("completeItems.size()가 0일 때", "");
        }

        completeItem = new AddItem("");

        complete = shared.getString("completeList", "없음");
        if (complete.equals("없음")) {
            Log.i("complete 데이터(X)", "" + complete);
        } else {
            Log.i("complete 데이터(O)", "" + complete);

            String completeArray[] = complete.split(" __ ");

            for (int i = 0; i < completeArray.length; i++) {
                Log.i("순서대로 completeArray", "" + completeArray[i]);
                AddItem completeItem = new AddItem(completeArray[i]);
                completeItems.add(completeItem);
            }

            Log.i("String myComplete", "" + complete);
        }


        completeAdapter.setCompleteItems(completeItems, ctx);

        Log.i("액티비티 completeItems", "" + completeItems.toString());

        completeAdapter.notifyItemRangeInserted(position, completeItems.size());

        Log.i("position, completeItems.size()", "" + position + completeItems.size());

        completeAdapter.notifyDataSetChanged();

        completeClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();


            }
        });

        // 쉐어드 저장하고 뿌려줘봐야 알듯!
        try {
            if (((Shop) Shop.ctx).btnMent.equals("현재 테마")) {
                // 완료된 목록 상단 프레임 블랙 (frameLayout)
                frameLayout.setBackgroundColor(Color.BLACK);
                // 레이아웃 블랙 (completeLayout)
                completeLayout.setBackgroundColor(Color.BLACK);
                // 버튼 백그라운드 블랙 (completeClose)
                completeClose.setBackgroundColor(Color.BLACK);
                // 버튼 텍스트 화이트
                completeClose.setTextColor(Color.WHITE);
                // 리사이클러뷰 아이템 블랙
            } else {

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            String whiteBtnMent = shared.getString("myWhiteTheme","");
            if (whiteBtnMent.equals("현재 테마")) {
                // 완료된 목록 상단 프레임 블랙 (frameLayout)
                frameLayout.setBackgroundColor(Color.WHITE);
                completeTitle.setTextColor(Color.BLACK);
                // 레이아웃 블랙 (completeLayout)
                completeLayout.setBackgroundColor(Color.WHITE);
                // 버튼 백그라운드 블랙 (completeClose)
                completeClose.setBackgroundColor(Color.WHITE);
                // 버튼 텍스트 화이트
                completeClose.setTextColor(Color.BLACK);
                // 리사이클러뷰 아이템 블랙
            } else {

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            String ableDBtnMent = shared.getString("myAbleDTheme", "구매");
            if (ableDBtnMent.equals("현재 테마")) {
                // 완료된 목록 상단 프레임 블랙 (frameLayout)
                frameLayout.setBackgroundColor(Color.WHITE);
                completeTitle.setTextColor(Color.RED);
                // 레이아웃 블랙 (completeLayout)
                completeLayout.setBackgroundColor(Color.WHITE);
                // 버튼 백그라운드 블랙 (completeClose)
                completeClose.setBackgroundColor(Color.RED);
                // 버튼 텍스트 화이트
                completeClose.setTextColor(Color.WHITE);
                // 리사이클러뷰 아이템 블랙
            } else {

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
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
        Log.i("Complete", "onStart()");

        String btnMent = shared.getString("myBlackTheme","구매");
        try {
            if (btnMent.equals("현재 테마")) {
                // 완료된 목록 상단 프레임 블랙 (frameLayout)
                frameLayout.setBackgroundColor(Color.BLACK);
                // 레이아웃 블랙 (completeLayout)
                completeLayout.setBackgroundColor(Color.BLACK);
                // 버튼 백그라운드 블랙 (completeClose)
                completeClose.setBackgroundColor(Color.BLACK);
                // 버튼 텍스트 화이트
                completeClose.setTextColor(Color.WHITE);
                // 리사이클러뷰 아이템 블랙
            } else {

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    protected void onResume() {
        super.onResume();
        Log.i("Complete", "onResume()");
    }

    protected void onPause() {
        super.onPause();
        Log.i("Complete", "onPause()");
    }

    protected void onStop() {
        super.onStop();
        Log.i("Complete", "onStop()");

        // 쉐어드 여기에 저장할 거야 어플 꺼질 때나 액티비티 넘어가서 사라질 때

    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i("Complete", "onDestroy()");
    }

    public void setShared() {

        shared = getSharedPreferences("toDoList", Activity.MODE_PRIVATE);

        editor = shared.edit();

        complete = shared.getString("complete", "빈 값");
    }


}