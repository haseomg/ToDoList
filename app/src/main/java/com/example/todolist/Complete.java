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
            Log.i("completeItems.size()??? 0??? ?????? ???", "");
        } else {
            listEmpty.setVisibility(View.VISIBLE);
            recycler.setAdapter(completeAdapter);
            recycler.setEmptyView(findViewById(R.id.listEmpty));
            Log.i("completeItems.size()??? 0??? ???", "");
        }

        completeItem = new AddItem("");

        complete = shared.getString("completeList", "??????");
        if (complete.equals("??????")) {
            Log.i("complete ?????????(X)", "" + complete);
        } else {
            Log.i("complete ?????????(O)", "" + complete);

            String completeArray[] = complete.split(" __ ");

            for (int i = 0; i < completeArray.length; i++) {
                Log.i("???????????? completeArray", "" + completeArray[i]);
                AddItem completeItem = new AddItem(completeArray[i]);
                completeItems.add(completeItem);
            }

            Log.i("String myComplete", "" + complete);
        }


        completeAdapter.setCompleteItems(completeItems, ctx);

        Log.i("???????????? completeItems", "" + completeItems.toString());

        completeAdapter.notifyItemRangeInserted(position, completeItems.size());

        Log.i("position, completeItems.size()", "" + position + completeItems.size());

        completeAdapter.notifyDataSetChanged();

        completeClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();


            }
        });

        // ????????? ???????????? ??????????????? ??????!
        try {
            if (((Shop) Shop.ctx).btnMent.equals("?????? ??????")) {
                // ????????? ?????? ?????? ????????? ?????? (frameLayout)
                frameLayout.setBackgroundColor(Color.BLACK);
                // ???????????? ?????? (completeLayout)
                completeLayout.setBackgroundColor(Color.BLACK);
                // ?????? ??????????????? ?????? (completeClose)
                completeClose.setBackgroundColor(Color.BLACK);
                // ?????? ????????? ?????????
                completeClose.setTextColor(Color.WHITE);
                // ?????????????????? ????????? ??????
            } else {

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            String whiteBtnMent = shared.getString("myWhiteTheme","");
            if (whiteBtnMent.equals("?????? ??????")) {
                // ????????? ?????? ?????? ????????? ?????? (frameLayout)
                frameLayout.setBackgroundColor(Color.WHITE);
                completeTitle.setTextColor(Color.BLACK);
                // ???????????? ?????? (completeLayout)
                completeLayout.setBackgroundColor(Color.WHITE);
                // ?????? ??????????????? ?????? (completeClose)
                completeClose.setBackgroundColor(Color.WHITE);
                // ?????? ????????? ?????????
                completeClose.setTextColor(Color.BLACK);
                // ?????????????????? ????????? ??????
            } else {

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            String ableDBtnMent = shared.getString("myAbleDTheme", "??????");
            if (ableDBtnMent.equals("?????? ??????")) {
                // ????????? ?????? ?????? ????????? ?????? (frameLayout)
                frameLayout.setBackgroundColor(Color.WHITE);
                completeTitle.setTextColor(Color.RED);
                // ???????????? ?????? (completeLayout)
                completeLayout.setBackgroundColor(Color.WHITE);
                // ?????? ??????????????? ?????? (completeClose)
                completeClose.setBackgroundColor(Color.RED);
                // ?????? ????????? ?????????
                completeClose.setTextColor(Color.WHITE);
                // ?????????????????? ????????? ??????
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

        String btnMent = shared.getString("myBlackTheme","??????");
        try {
            if (btnMent.equals("?????? ??????")) {
                // ????????? ?????? ?????? ????????? ?????? (frameLayout)
                frameLayout.setBackgroundColor(Color.BLACK);
                // ???????????? ?????? (completeLayout)
                completeLayout.setBackgroundColor(Color.BLACK);
                // ?????? ??????????????? ?????? (completeClose)
                completeClose.setBackgroundColor(Color.BLACK);
                // ?????? ????????? ?????????
                completeClose.setTextColor(Color.WHITE);
                // ?????????????????? ????????? ??????
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

        // ????????? ????????? ????????? ?????? ?????? ?????? ?????? ???????????? ???????????? ????????? ???

    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i("Complete", "onDestroy()");
    }

    public void setShared() {

        shared = getSharedPreferences("toDoList", Activity.MODE_PRIVATE);

        editor = shared.edit();

        complete = shared.getString("complete", "??? ???");
    }


}