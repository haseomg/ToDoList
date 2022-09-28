package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
import android.widget.Toast;

import org.w3c.dom.Text;

public class Shop extends Activity {

    androidx.constraintlayout.widget.ConstraintLayout shopLayout;

    TextView shopTitle;
    TextView shopGold;
    Button shopClose;
    PreferenceManager preferenceManager;

    SharedPreferences shared;
    SharedPreferences.Editor editor;

    FrameLayout frameLayout;
    FrameLayout black;
    FrameLayout white;
    FrameLayout ableD;
    FrameLayout original;

    TextView blackThemePrice;
    TextView whiteThemePrice;
    TextView ableDThemePrice;

    Button blackAndWhiteBuyBtn;
    Button originalBtn;
    Button ableDBtn;
    Button whiteBtn;

    String btnMent;
    String originalBtnMent;
    String ableDBtnMent;
    String whiteBtnMent;

    int lvUp;
    String level;
    int gold;
    String myGold;


    public static Context ctx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shop);
        Log.i("Shop", "onCreate()");

        ctx = this;

        setAbleDShared();

        setWhiteShared();

        setBlackShared(); // btnMent (shared.getString)

        setOriginalShared(); // originalBtnMent (shared.getString)

        setGoldShared(); // myGold (shared.getString)


        bringGoldInShared(); // String myGold, int gold (Shared)


        bringAbleDThemeInShared();

        bringWhiteThemeInShared();

        bringBlackThemeInShared(); // btnMent

        bringOriginalThemeInShared(); // originalBtnMent

//        String myGold = ((MainActivity) MainActivity.ctx).gold.getText().toString();

        black = findViewById(R.id.blackTheme);
        white = findViewById(R.id.whiteTheme);
        ableD = findViewById(R.id.ableDTheme);


        // 블랙 테마 UI
        blackAndWhiteBuyBtn = findViewById(R.id.blackAndWhiteBuyBtn);
        blackAndWhiteBuyBtn.setText(btnMent);
        blackThemePrice = findViewById(R.id.blackThemePrice);

        // 화이트 테마 UI
        whiteBtn = findViewById(R.id.whiteThemeBuyBtn);
        whiteBtn.setText(whiteBtnMent);
        whiteThemePrice = findViewById(R.id.whiteThemePrice);

        // ableD 테마 UI
        ableDBtn = findViewById(R.id.ableDThemeBuyBtn);
        ableDBtn.setText(ableDBtnMent);
        ableDThemePrice = findViewById(R.id.ableDThemePrice);

        // 오리지널 테마 UI
        originalBtn = findViewById(R.id.originalBtn);
        originalBtn.setText(originalBtnMent);


        shopTitle = findViewById(R.id.shopTitle);
        frameLayout = findViewById(R.id.shopFrameLayout);
        original = findViewById(R.id.original);
        shopLayout = findViewById(R.id.shopLayout);

        shopGold = (TextView) findViewById(R.id.shopGold);
        shopGold.setText("내 골드 : " + myGold);
        shopClose = (Button) findViewById(R.id.shopClose);
        shopClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bringGoldInShared();
                bringBlackThemeInShared();
                bringWhiteThemeInShared();
                bringOriginalThemeInShared();
                bringAbleDThemeInShared();
                finish();
            }
        });


        btnMent = shared.getString("myBlackTheme", "구매");

        whiteBtnMent = shared.getString("myWhiteTheme", "구매");

        originalBtnMent = shared.getString("myOriginalTheme", "적용");

        ableDBtnMent = shared.getString("myAbleDTheme", "구매");

        level = shared.getString("myLevel", "1");
        lvUp = Integer.parseInt(level);

        if (btnMent.equals("현재 테마") || blackAndWhiteBuyBtn.getText().toString().equals("현재 테마")) {
            originalBtnMent = "적용";
            originalBtn.setText(originalBtnMent);
            ((MainActivity) MainActivity.ctx).ableDLogo.setVisibility(View.GONE);
            saveOriginalThemeInShared();
        }

        if (whiteBtnMent.equals("현재 테마") || whiteBtn.getText().toString().equals("현재 테마")) {

            Log.i("여기로 오니?", "");
            originalBtnMent = "적용";
            originalBtn.setText(originalBtnMent);
            saveOriginalThemeInShared();

            ((MainActivity) MainActivity.ctx).title.setTextColor(Color.BLACK);
            ((MainActivity) MainActivity.ctx).title.setVisibility(View.VISIBLE);
            ((MainActivity) MainActivity.ctx).realMainLayout.setBackgroundColor(Color.WHITE);
            ((MainActivity) MainActivity.ctx).recyclerView.setBackgroundColor(Color.WHITE);
            ((MainActivity) MainActivity.ctx).frameLayout.setBackgroundColor(Color.BLACK);
            ((MainActivity) MainActivity.ctx).gold.setTextColor(Color.BLACK);
            ((MainActivity) MainActivity.ctx).ableDLogo.setVisibility(View.GONE);

            frameLayout.setBackgroundColor(Color.WHITE);
            shopTitle.setTextColor(Color.WHITE);

            shopGold.setTextColor(Color.BLACK);
            shopLayout.setBackgroundColor(Color.WHITE);
            shopClose.setBackgroundColor(Color.WHITE);
            shopClose.setTextColor(Color.BLACK);
            shopTitle.setTextColor(Color.BLACK);
            saveWhiteThemeInShared();
        }

        if (ableDBtnMent.equals("현재 테마") || ableDBtn.getText().toString().equals("현재 테마")) {

            Log.i("여기로 오니?", "");
            originalBtnMent = "적용";
            originalBtn.setText(originalBtnMent);

            saveOriginalThemeInShared();
            ((MainActivity) MainActivity.ctx).ableDLogo.setVisibility(View.VISIBLE);
            ((MainActivity) MainActivity.ctx).title.setVisibility(View.GONE);
            ((MainActivity) MainActivity.ctx).realMainLayout.setBackgroundColor(Color.WHITE);
            ((MainActivity) MainActivity.ctx).recyclerView.setBackgroundColor(Color.WHITE);
            ((MainActivity) MainActivity.ctx).frameLayout.setBackgroundColor(Color.RED);

            frameLayout.setBackgroundColor(Color.RED);
            shopTitle.setTextColor(Color.WHITE);
            shopGold.setTextColor(Color.RED);
            shopLayout.setBackgroundColor(Color.WHITE);
            shopClose.setBackgroundColor(Color.RED);
            shopClose.setTextColor(Color.WHITE);

            frameLayout.setBackgroundColor(Color.WHITE);
            shopTitle.setTextColor(Color.BLACK);
            saveAbleDThemeInShared();
        }


        ableDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 레벨 바꿔
                if (lvUp >= 20) {
                    if (ableDBtnMent.equals("구매") || ableDBtn.getText().toString().equals("구매")) {

                        // 골드 바꿔
                        if (gold >= 3000) {
                            // myGold = shared.getString
                            // int gold로 형 변환
                            gold = Integer.parseInt(myGold);
                            // 골드 차감
                            gold = gold - 3000;


                            Log.i("에이블디 테마로 잘 와? 적용?", "");
                            ableDBtnMent = "적용";
                            ableDBtn.setText(ableDBtnMent);
                            saveAbleDThemeInShared();

                            if (btnMent.equals("현재 테마")) {
                                btnMent = "적용";
                                blackAndWhiteBuyBtn.setText(btnMent);
                                saveBlackThemeInShared();
                            }

                            if (whiteBtnMent.equals("현재 테마")) {
                                whiteBtnMent = "적용";
                                whiteBtn.setText(whiteBtnMent);
                                saveWhiteThemeInShared();
                            }

                            if (originalBtnMent.equals("현재 테마")) {
                                originalBtnMent = "적용";
                                originalBtn.setText(originalBtnMent);
                                saveOriginalThemeInShared();
                            }

                            shopGold.setText("내 골드 : " + gold);

                            saveInGoldShared();
                            // 골드 바꿔
                        } else if (gold < 3000) {
                            Toast.makeText(ctx, "골드가 부족합니다.", Toast.LENGTH_SHORT).show();
                        }

                    } else if (ableDBtnMent.equals("적용") || ableDBtn.getText().toString().equals("적용")) {

                        if (btnMent.equals("현재 테마") || blackAndWhiteBuyBtn.getText().toString().equals("현재 테마")) {
                            btnMent = "적용";
                            blackAndWhiteBuyBtn.setText(btnMent);
                            saveBlackThemeInShared();
                        } else if (btnMent.equals("적용") || blackAndWhiteBuyBtn.getText().toString().equals("적용")) {

                        } else if (btnMent.equals("구매") || blackAndWhiteBuyBtn.getText().toString().equals("구매")) {

                        }

                        if (originalBtnMent.equals("현재 테마") || originalBtn.getText().toString().equals("현재 테마")) {
                            originalBtnMent = "적용";
                            originalBtn.setText(originalBtnMent);
                            saveOriginalThemeInShared();
                        } else if (originalBtnMent.equals("적용") || originalBtn.getText().toString().equals("적용")) {

                        } else if (originalBtnMent.equals("구매") || originalBtn.getText().toString().equals("구매")) {

                        }

                        if (whiteBtnMent.equals("현재 테마") || whiteBtn.getText().toString().equals("현재 테마")) {
                            whiteBtnMent = "적용";
                            whiteBtn.setText(whiteBtnMent);
                            saveWhiteThemeInShared();
                        } else if (whiteBtnMent.equals("적용") || whiteBtn.getText().toString().equals("적용")) {

                        } else if (whiteBtnMent.equals("구매") || whiteBtn.getText().toString().equals("구매")) {

                        }

//                        try {
                        ((MainActivity) MainActivity.ctx).ableDLogo.setVisibility(View.VISIBLE);
                        ((MainActivity) MainActivity.ctx).title.setVisibility(View.GONE);
                        // 메인의 타이틀 글씨 색깔 -> (title) white
                        ((MainActivity) MainActivity.ctx).realMainLayout.setBackgroundColor(Color.WHITE);
                        // 메인의 레이아웃 색깔 -> (mainLayout) Black
                        ((MainActivity) MainActivity.ctx).recyclerView.setBackgroundColor(Color.WHITE);
                        // 메인의 리사이클러뷰 색깔 -> (recyclerView) white
                        // 리사이클러뷰 아이템 색깔 -> black
                        ((MainActivity) MainActivity.ctx).frameLayout.setBackgroundColor(Color.RED);
                        // 메인의 하단바 프레임 레이아웃 -> (frameLayout) black

                        // 상점의 상단 프레임 블랙 (frameLayout)
                        frameLayout.setBackgroundColor(Color.RED);
                        // 타이틀바
                        shopTitle.setTextColor(Color.WHITE);
                        // 내 골드 : 블랙 (shopGold)
                        shopGold.setTextColor(Color.RED);
                        // 레이아웃 화이트 (shopLayout)
                        shopLayout.setBackgroundColor(Color.WHITE);
                        // 버튼 백그라운드 블랙 (shopClose)
                        shopClose.setBackgroundColor(Color.RED);
                        shopClose.setTextColor(Color.WHITE);
                        // 버튼 텍스트 화이트 (shopClose)
                        // 현재 테마 버튼 백그라운드 화이트 글씨 블랙
                        ableDBtnMent = "현재 테마";
                        ableDBtn.setText(ableDBtnMent);
                        saveBlackThemeInShared();
                        saveWhiteThemeInShared();
                        saveAbleDThemeInShared();
                        saveOriginalThemeInShared();

                    } else if (ableDBtnMent.equals("현재 테마")) {

                        if (btnMent.equals("현재 테마") || blackAndWhiteBuyBtn.getText().toString().equals("현재 테마")) {
                            btnMent = "적용";
                            blackAndWhiteBuyBtn.setText(btnMent);
                            saveBlackThemeInShared();
                        } else if (btnMent.equals("적용") || blackAndWhiteBuyBtn.getText().toString().equals("적용")) {

                        } else if (btnMent.equals("구매") || blackAndWhiteBuyBtn.getText().toString().equals("구매")) {

                        }

                        if (originalBtnMent.equals("현재 테마") || originalBtn.getText().toString().equals("현재 테마")) {
                            originalBtnMent = "적용";
                            originalBtn.setText(originalBtnMent);
                            saveOriginalThemeInShared();
                        } else if (originalBtnMent.equals("적용") || originalBtn.getText().toString().equals("적용")) {

                        } else if (originalBtnMent.equals("구매") || originalBtn.getText().toString().equals("구매")) {

                        }

                        if (whiteBtnMent.equals("현재 테마") || whiteBtn.getText().toString().equals("현재 테마")) {
                            whiteBtnMent = "적용";
                            whiteBtn.setText(whiteBtnMent);
                            saveWhiteThemeInShared();
                        } else if (whiteBtnMent.equals("적용") || whiteBtn.getText().toString().equals("적용")) {

                        } else if (whiteBtnMent.equals("구매") || whiteBtn.getText().toString().equals("구매")) {

                        }

                        try {

                            ((MainActivity) MainActivity.ctx).ableDLogo.setVisibility(View.VISIBLE);
                            ((MainActivity) MainActivity.ctx).title.setVisibility(View.GONE);
                            ((MainActivity) MainActivity.ctx).realMainLayout.setBackgroundColor(Color.WHITE);
                            ((MainActivity) MainActivity.ctx).recyclerView.setBackgroundColor(Color.WHITE);
                            ((MainActivity) MainActivity.ctx).frameLayout.setBackgroundColor(Color.RED);


                            frameLayout.setBackgroundColor(Color.RED);
                            shopTitle.setTextColor(Color.WHITE);
                            shopGold.setTextColor(Color.RED);
                            shopLayout.setBackgroundColor(Color.WHITE);
                            shopClose.setBackgroundColor(Color.RED);
                            shopClose.setTextColor(Color.WHITE);


                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }

                        ableDBtnMent = "현재 테마";
                        ableDBtn.setText(ableDBtnMent);

                        saveAbleDThemeInShared();
                    }
                } else {
                    Log.i("AbleD Theme lvUp이 20 이하일 때", "");
                    Toast.makeText(ctx, "- LV.20 이상 구입 33가능 -", Toast.LENGTH_SHORT).show();
                }
            }
        });

        originalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 레벨 바꿔
                if (lvUp >= 0) {
                    if (originalBtnMent.equals("구매") || originalBtn.getText().toString().equals("구매")) {

                        // 골드 바꿔
                        if (gold >= 0) {
                            // myGold = shared.getString
                            // int gold로 형 변환
                            gold = Integer.parseInt(myGold);
                            // 골드 차감
                            gold = gold - 0;


                            Log.i("여기로 오는 것 아냐? 적용?", "");
                            originalBtnMent = "적용";
                            originalBtn.setText(originalBtnMent);
                            saveWhiteThemeInShared();

                            if (btnMent.equals("현재 테마")) {
                                btnMent = "적용";
                                blackAndWhiteBuyBtn.setText(btnMent);
                                saveBlackThemeInShared();
                            }

                            if (ableDBtnMent.equals("현재 테마")) {
                                ableDBtnMent = "적용";
                                ableDBtn.setText(ableDBtnMent);
                                saveAbleDThemeInShared();
                            }

                            if (whiteBtnMent.equals("현재 테마")) {
                                whiteBtnMent = "적용";
                                whiteBtn.setText(whiteBtnMent);
                                saveWhiteThemeInShared();
                            }

                            shopGold.setText("내 골드 : " + gold);

                            saveInGoldShared();
                        }

                    } else if (originalBtnMent.equals("적용") || whiteBtn.getText().toString().equals("적용")) {

                        if (btnMent.equals("현재 테마")) {
                            btnMent = "적용";
                            blackAndWhiteBuyBtn.setText(btnMent);
                            saveBlackThemeInShared();
                        }

                        if (ableDBtnMent.equals("현재 테마")) {
                            ableDBtnMent = "적용";
                            ableDBtn.setText(ableDBtnMent);
                            saveAbleDThemeInShared();
                        }

                        if (whiteBtnMent.equals("현재 테마")) {
                            whiteBtnMent = "적용";
                            whiteBtn.setText(whiteBtnMent);
                            saveWhiteThemeInShared();
                        }

                        ((MainActivity) MainActivity.ctx).title.setTextColor(Color.BLACK);
                        // 메인의 타이틀 글씨 색깔 -> (title) white
                        ((MainActivity) MainActivity.ctx).realMainLayout.setBackgroundColor(Color.WHITE);
                        // 메인의 레이아웃 색깔 -> (mainLayout) Black
                        ((MainActivity) MainActivity.ctx).recyclerView.setBackgroundColor(Color.WHITE);
                        // 메인의 리사이클러뷰 색깔 -> (recyclerView) white
                        // 리사이클러뷰 아이템 색깔 -> black
                        ((MainActivity) MainActivity.ctx).frameLayout.setBackgroundColor(Color.BLACK);
                        ((MainActivity) MainActivity.ctx).gold.setTextColor(Color.WHITE);
                        ((MainActivity) MainActivity.ctx).userLv.setTextColor(Color.WHITE);
                        ((MainActivity) MainActivity.ctx).toDid.setTextColor(Color.WHITE);
                        ((MainActivity) MainActivity.ctx).myProgress.setTextColor(Color.WHITE);

                        originalBtnMent = "현재 테마";
                        originalBtn.setText(originalBtnMent);
                        saveBlackThemeInShared();
                        saveWhiteThemeInShared();
                        saveAbleDThemeInShared();
                        saveOriginalThemeInShared();

                    } else if (whiteBtnMent.equals("현재 테마")) {

                        if (btnMent.equals("현재 테마")) {
                            btnMent = "적용";
                            blackAndWhiteBuyBtn.setText(btnMent);
                            saveBlackThemeInShared();
                        }

                        if (ableDBtnMent.equals("현재 테마")) {
                            ableDBtnMent = "적용";
                            ableDBtn.setText(ableDBtnMent);
                            saveAbleDThemeInShared();
                        }

                        if (whiteBtnMent.equals("현재 테마")) {
                            whiteBtnMent = "적용";
                            whiteBtn.setText(whiteBtnMent);
                            saveWhiteThemeInShared();
                        }

                        originalBtnMent = "현재 테마";
                        originalBtn.setText(originalBtnMent);

                        saveWhiteThemeInShared();
                    }
                } else {
                    Log.i("White Theme lvUp이 5 이하일 때", "");
                    Toast.makeText(ctx, "- LV.5 이상 구입 가능 -", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 화이트 버튼 클릭 시
        whiteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 레벨 바꿔
                if (lvUp >= 5) {
                    if (whiteBtnMent.equals("구매") || whiteBtn.getText().toString().equals("구매")) {

                        // 골드 바꿔
                        if (gold >= 1200) {
                            // myGold = shared.getString
                            // int gold로 형 변환
                            gold = Integer.parseInt(myGold);
                            // 골드 차감
                            gold = gold - 1200;


                            Log.i("여기로 오는 것 아냐? 적용?", "");
                            whiteBtnMent = "적용";
                            whiteBtn.setText(whiteBtnMent);
                            saveWhiteThemeInShared();

                            if (btnMent.equals("현재 테마")) {
                                btnMent = "적용";
                                blackAndWhiteBuyBtn.setText(btnMent);
                                saveBlackThemeInShared();
                            }

                            if (ableDBtnMent.equals("현재 테마")) {
                                ableDBtnMent = "적용";
                                ableDBtn.setText(ableDBtnMent);
                                saveAbleDThemeInShared();
                            }

                            shopGold.setText("내 골드 : " + gold);

                            saveInGoldShared();
                        } else if (gold < 1200) {
                            Toast.makeText(ctx, "골드가 부족합니다.", Toast.LENGTH_SHORT).show();
                        }

                    } else if (whiteBtnMent.equals("적용") || whiteBtn.getText().toString().equals("적용")) {

                        if (btnMent.equals("현재 테마") || blackAndWhiteBuyBtn.getText().toString().equals("현재 테마")) {
                            btnMent = "적용";
                            blackAndWhiteBuyBtn.setText(btnMent);
                            saveBlackThemeInShared();
                        } else if (btnMent.equals("적용") || blackAndWhiteBuyBtn.getText().toString().equals("적용")) {

                        } else if (btnMent.equals("구매") || blackAndWhiteBuyBtn.getText().toString().equals("구매")) {

                        }

                        if (originalBtnMent.equals("현재 테마") || originalBtn.getText().toString().equals("현재 테마")) {
                            originalBtnMent = "적용";
                            originalBtn.setText(originalBtnMent);
                            saveOriginalThemeInShared();
                        } else if (originalBtnMent.equals("적용") || originalBtn.getText().toString().equals("적용")) {

                        } else if (originalBtnMent.equals("구매") || originalBtn.getText().toString().equals("구매")) {

                        }

                        if (ableDBtnMent.equals("현재 테마") || ableDBtn.getText().toString().equals("현재 테마")) {
                            ableDBtnMent = "적용";
                            ableDBtn.setText(ableDBtnMent);
                            saveAbleDThemeInShared();
                        } else if (ableDBtnMent.equals("적용") || ableDBtn.getText().toString().equals("적용")) {

                        } else if (ableDBtnMent.equals("구매") || ableDBtn.getText().toString().equals("구매")) {

                        }

//                        try {
                        ((MainActivity) MainActivity.ctx).title.setTextColor(Color.BLACK);
                        // 메인의 타이틀 글씨 색깔 -> (title) white
                        ((MainActivity) MainActivity.ctx).realMainLayout.setBackgroundColor(Color.WHITE);
                        // 메인의 레이아웃 색깔 -> (mainLayout) Black
                        ((MainActivity) MainActivity.ctx).recyclerView.setBackgroundColor(Color.WHITE);
                        // 메인의 리사이클러뷰 색깔 -> (recyclerView) white
                        // 리사이클러뷰 아이템 색깔 -> black
                        ((MainActivity) MainActivity.ctx).frameLayout.setBackgroundColor(Color.BLACK);
                        // 메인의 하단바 프레임 레이아웃 -> (frameLayout) black

                        // 상점의 상단 프레임 블랙 (frameLayout)
                        frameLayout.setBackgroundColor(Color.WHITE);
                        // 타이틀바
                        shopTitle.setTextColor(Color.BLACK);
                        // 내 골드 : 블랙 (shopGold)
                        shopGold.setTextColor(Color.BLACK);
                        // 레이아웃 화이트 (shopLayout)
                        shopLayout.setBackgroundColor(Color.WHITE);
                        // 버튼 백그라운드 블랙 (shopClose)
                        shopClose.setBackgroundColor(Color.WHITE);
                        shopClose.setTextColor(Color.BLACK);


                        whiteBtnMent = "현재 테마";
                        whiteBtn.setText(whiteBtnMent);
                        saveBlackThemeInShared();
                        saveWhiteThemeInShared();
                        saveAbleDThemeInShared();
                        saveOriginalThemeInShared();

                    } else if (whiteBtnMent.equals("현재 테마")) {

                        if (btnMent.equals("현재 테마") || blackAndWhiteBuyBtn.getText().toString().equals("현재 테마")) {
                            btnMent = "적용";
                            blackAndWhiteBuyBtn.setText(btnMent);
                            saveBlackThemeInShared();
                        } else if (btnMent.equals("적용") || blackAndWhiteBuyBtn.getText().toString().equals("적용")) {

                        } else if (btnMent.equals("구매") || blackAndWhiteBuyBtn.getText().toString().equals("구매")) {

                        }

                        if (originalBtnMent.equals("현재 테마") || originalBtn.getText().toString().equals("현재 테마")) {
                            originalBtnMent = "적용";
                            originalBtn.setText(originalBtnMent);
                            saveOriginalThemeInShared();
                        } else if (originalBtnMent.equals("적용") || originalBtn.getText().toString().equals("적용")) {

                        } else if (originalBtnMent.equals("구매") || originalBtn.getText().toString().equals("구매")) {

                        }

                        if (ableDBtnMent.equals("현재 테마") || ableDBtn.getText().toString().equals("현재 테마")) {
                            ableDBtnMent = "적용";
                            ableDBtn.setText(ableDBtnMent);
                            saveAbleDThemeInShared();
                        } else if (ableDBtnMent.equals("적용") || ableDBtn.getText().toString().equals("적용")) {

                        } else if (ableDBtnMent.equals("구매") || ableDBtn.getText().toString().equals("구매")) {

                        }

                        try {

                            ((MainActivity) MainActivity.ctx).title.setTextColor(Color.BLACK);
                            ((MainActivity) MainActivity.ctx).realMainLayout.setBackgroundColor(Color.WHITE);
                            // 메인의 레이아웃 색깔 -> (mainLayout) Black
                            ((MainActivity) MainActivity.ctx).recyclerView.setBackgroundColor(Color.WHITE);
                            // 메인의 리사이클러뷰 색깔 -> (recyclerView) white
                            // 리사이클러뷰 아이템 색깔 -> black
                            ((MainActivity) MainActivity.ctx).frameLayout.setBackgroundColor(Color.BLACK);



                            // 상점의 상단 프레임 블랙 (frameLayout)
                            frameLayout.setBackgroundColor(Color.WHITE);
                            // 타이틀바
                            shopTitle.setTextColor(Color.BLACK);
                            // 내 골드 : 블랙 (shopGold)
                            shopGold.setTextColor(Color.BLACK);
                            // 레이아웃 화이트 (shopLayout)
                            shopLayout.setBackgroundColor(Color.WHITE);
                            // 버튼 백그라운드 블랙 (shopClose)
                            shopClose.setBackgroundColor(Color.WHITE);
                            shopClose.setTextColor(Color.BLACK);
                            // 버튼 텍스트 화이트 (shopClose)
                            // 현재 테마 버튼 백그라운드 화이트 글씨 블랙
//                            blackAndWhiteBuyBtn.setBackgroundColor(Color.parseColor("#0074C2"));
//                            blackAndWhiteBuyBtn.setTextColor(Color.parseColor("#F7F7F7"));
//
//                            originalBtn.setBackgroundColor(Color.parseColor("#0074C2"));
//                            originalBtn.setTextColor(Color.parseColor("#F7F7F7"));
//
//                            blackThemePrice.setTextColor(Color.parseColor("#589FCF"));

//                            // 휴지통 상단 프레임 블랙 (deleteFrameLayout)
//                            ((Delete) Delete.ctx).deleteFrameLayout.setBackgroundColor(Color.parseColor("#0074C2"));
//                            // 레이아웃 블랙 (deleteMainLayout)
//                            (((Delete) Delete.ctx).deleteMainLayout).setBackgroundColor(Color.parseColor("#D0E4ED"));
//                            // 버튼 백그라운드 블랙 (deleteClose)
//                            (((Delete) Delete.ctx).close).setBackgroundColor(Color.parseColor("#F7F7F7"));
//                            // 버튼 텍스트 화이트
//                            (((Delete) Delete.ctx).close).setTextColor(Color.parseColor("#0074C2"));
//                            // 리사이클러뷰 아이템 블랙


                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }

                        whiteBtnMent = "현재 테마";
                        whiteBtn.setText(whiteBtnMent);

                        saveWhiteThemeInShared();
                    }
                } else {
                    Log.i("White Theme lvUp이 5 이하일 때", "");
                    Toast.makeText(ctx, "- LV.5 이상 구입 가능 -", Toast.LENGTH_SHORT).show();
                }
            }
        });


//        gold = ((MainActivity) MainActivity.ctx).goldPlus;

        blackAndWhiteBuyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("블랙테마 구입 버튼 클릭", "");

                Log.i("lvUpCheck", "" + lvUp + " / " + level);
                // 레벨 바꿔 !!!!!!!!!
                if (lvUp >= 5) {
                    Log.i("lvUp이 5 이상일 때", "");
                    if (btnMent.equals("구매") || blackAndWhiteBuyBtn.getText().toString().equals("구매")) {
                        // 골드 바꿔 !!!!!!!
                        if (gold >= 1200) {
                            // myGold = shared.getString
                            // int gold로 형 변환
                            gold = Integer.parseInt(myGold);
                            // 골드 차감
                            gold = gold - 1200;


                            btnMent = "적용";
                            blackAndWhiteBuyBtn.setText(btnMent);
                            saveBlackThemeInShared();

                            shopGold.setText("내 골드 : " + gold);

                            saveInGoldShared();
                        } else if (gold < 1200) {
                            Toast.makeText(ctx, "골드가 부족합니다.", Toast.LENGTH_SHORT).show();
                        }
                    } else if (btnMent.equals("적용") || blackAndWhiteBuyBtn.getText().toString().equals("적용")) {

                        // btnMent만 쉐어드에 저장하고 뿌려주면 돼!
                        btnMent = "현재 테마";
                        blackAndWhiteBuyBtn.setText(btnMent);

                        if (whiteBtnMent.equals("현재 테마") || whiteBtn.getText().toString().equals("현재 테마")) {
                            whiteBtnMent = "적용";
                            whiteBtn.setText(whiteBtnMent);
                            saveWhiteThemeInShared();
                        } else if (btnMent.equals("적용") || whiteBtn.getText().toString().equals("적용")) {

                        } else if (btnMent.equals("구매") || whiteBtn.getText().toString().equals("구매")) {

                        }

                        if (originalBtnMent.equals("현재 테마") || originalBtn.getText().toString().equals("현재 테마")) {
                            originalBtnMent = "적용";
                            originalBtn.setText(originalBtnMent);
                            saveOriginalThemeInShared();
                        } else if (originalBtnMent.equals("적용") || originalBtn.getText().toString().equals("적용")) {

                        } else if (originalBtnMent.equals("구매") || originalBtn.getText().toString().equals("구매")) {

                        }

                        if (ableDBtnMent.equals("현재 테마") || ableDBtn.getText().toString().equals("현재 테마")) {
                            ableDBtnMent = "적용";
                            ableDBtn.setText(ableDBtnMent);
                            saveAbleDThemeInShared();
                        } else if (ableDBtnMent.equals("적용") || ableDBtn.getText().toString().equals("적용")) {

                        } else if (ableDBtnMent.equals("구매") || ableDBtn.getText().toString().equals("구매")) {

                        }


                        try {
                            ((MainActivity) MainActivity.ctx).title.setTextColor(Color.WHITE);
                            // 메인의 타이틀 글씨 색깔 -> (title) white
                            ((MainActivity) MainActivity.ctx).realMainLayout.setBackgroundColor(Color.BLACK);
                            // 메인의 레이아웃 색깔 -> (mainLayout) Black
                            ((MainActivity) MainActivity.ctx).recyclerView.setBackgroundColor(Color.BLACK);
                            // 메인의 리사이클러뷰 색깔 -> (recyclerView) white
                            // 리사이클러뷰 아이템 색깔 -> black
                            ((MainActivity) MainActivity.ctx).frameLayout.setBackgroundColor(Color.BLACK);
                            // 메인의 하단바 프레임 레이아웃 -> (frameLayout) black

                            // 상점의 상단 프레임 블랙 (frameLayout)
                            frameLayout.setBackgroundColor(Color.BLACK);
                            // 타이틀바
                            shopTitle.setTextColor(Color.WHITE);
                            // 내 골드 : 블랙 (shopGold)
                            shopGold.setTextColor(Color.WHITE);
                            // 레이아웃 화이트 (shopLayout)
                            shopLayout.setBackgroundColor(Color.BLACK);
                            // 버튼 백그라운드 블랙 (shopClose)
                            shopClose.setBackgroundColor(Color.BLACK);
                            shopClose.setTextColor(Color.WHITE);
                            // 버튼 텍스트 화이트 (shopClose)
                            // 현재 테마 버튼 백그라운드 화이트 글씨 블랙
                            blackAndWhiteBuyBtn.setBackgroundColor(Color.BLACK);
                            blackAndWhiteBuyBtn.setTextColor(Color.WHITE);
                            blackThemePrice.setTextColor(Color.WHITE);

                            originalBtn.setBackgroundColor(Color.BLACK);
                            originalBtn.setTextColor(Color.WHITE);

                            ableDBtn.setBackgroundColor(Color.BLACK);
                            ableDBtn.setTextColor(Color.WHITE);
                            ableDThemePrice.setTextColor(Color.WHITE);

                            whiteBtn.setBackgroundColor(Color.BLACK);
                            whiteBtn.setTextColor(Color.WHITE);
                            whiteThemePrice.setTextColor(Color.WHITE);

                            // 휴지통 상단 프레임 블랙 (deleteFrameLayout)
                            ((Delete) Delete.ctx).deleteFrameLayout.setBackgroundColor(Color.BLACK);
                            // 레이아웃 블랙 (deleteMainLayout)
                            (((Delete) Delete.ctx).deleteMainLayout).setBackgroundColor(Color.BLACK);
                            // 버튼 백그라운드 블랙 (deleteClose)
                            (((Delete) Delete.ctx).close).setBackgroundColor(Color.BLACK);
                            // 버튼 텍스트 화이트
                            (((Delete) Delete.ctx).close).setTextColor(Color.WHITE);
                            // 리사이클러뷰 아이템 블랙

                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }

                        saveBlackThemeInShared();


                    } else if (btnMent.equals("현재 테마") || blackAndWhiteBuyBtn.getText().toString().equals("현재 테마")) {

                        btnMent = "현재 테마";
                        blackAndWhiteBuyBtn.setText(btnMent);
                        saveBlackThemeInShared();


                        if (whiteBtnMent.equals("현재 테마") || whiteBtn.getText().toString().equals("현재 테마")) {
                            whiteBtnMent = "적용";
                            whiteBtn.setText(whiteBtnMent);
                            saveWhiteThemeInShared();
                        } else if (btnMent.equals("적용") || whiteBtn.getText().toString().equals("적용")) {

                        } else if (btnMent.equals("구매") || whiteBtn.getText().toString().equals("구매")) {

                        }

                        if (originalBtnMent.equals("현재 테마") || originalBtn.getText().toString().equals("현재 테마")) {
                            originalBtnMent = "적용";
                            originalBtn.setText(originalBtnMent);
                            saveOriginalThemeInShared();
                        } else if (originalBtnMent.equals("적용") || originalBtn.getText().toString().equals("적용")) {

                        } else if (originalBtnMent.equals("구매") || originalBtn.getText().toString().equals("구매")) {

                        }

                        if (ableDBtnMent.equals("현재 테마") || ableDBtn.getText().toString().equals("현재 테마")) {
                            ableDBtnMent = "적용";
                            ableDBtn.setText(ableDBtnMent);
                            saveAbleDThemeInShared();
                        } else if (ableDBtnMent.equals("적용") || ableDBtn.getText().toString().equals("적용")) {

                        } else if (ableDBtnMent.equals("구매") || ableDBtn.getText().toString().equals("구매")) {

                        }

                        try {
                            ((MainActivity) MainActivity.ctx).title.setTextColor(Color.WHITE);
                            // 메인의 타이틀 글씨 색깔 -> (title) white
                            ((MainActivity) MainActivity.ctx).realMainLayout.setBackgroundColor(Color.BLACK);
                            // 메인의 레이아웃 색깔 -> (mainLayout) Black
                            ((MainActivity) MainActivity.ctx).recyclerView.setBackgroundColor(Color.BLACK);
                            // 메인의 리사이클러뷰 색깔 -> (recyclerView) white
                            // 리사이클러뷰 아이템 색깔 -> black
                            ((MainActivity) MainActivity.ctx).frameLayout.setBackgroundColor(Color.BLACK);
                            // 메인의 하단바 프레임 레이아웃 -> (frameLayout) black

                            // 상점의 상단 프레임 블랙 (frameLayout)
                            frameLayout.setBackgroundColor(Color.BLACK);
                            // 타이틀바
                            shopTitle.setTextColor(Color.WHITE);
                            // 내 골드 : 블랙 (shopGold)
                            shopGold.setTextColor(Color.WHITE);
                            // 레이아웃 화이트 (shopLayout)
                            shopLayout.setBackgroundColor(Color.BLACK);
                            // 버튼 백그라운드 블랙 (shopClose)
                            shopClose.setBackgroundColor(Color.BLACK);
                            shopClose.setTextColor(Color.WHITE);
                            // 버튼 텍스트 화이트 (shopClose)
                            // 현재 테마 버튼 백그라운드 화이트 글씨 블랙
                            blackAndWhiteBuyBtn.setBackgroundColor(Color.BLACK);
                            blackAndWhiteBuyBtn.setTextColor(Color.WHITE);
                            blackThemePrice.setTextColor(Color.WHITE);

                            originalBtn.setBackgroundColor(Color.BLACK);
                            originalBtn.setTextColor(Color.WHITE);

                            ableDBtn.setBackgroundColor(Color.BLACK);
                            ableDBtn.setTextColor(Color.WHITE);
                            ableDThemePrice.setTextColor(Color.WHITE);

                            whiteBtn.setBackgroundColor(Color.BLACK);
                            whiteBtn.setTextColor(Color.WHITE);
                            whiteThemePrice.setTextColor(Color.WHITE);
                        } catch (NullPointerException e) {

                        }

                        saveBlackThemeInShared();

                        // 수정해줘 LV.5로
                    }
                } else {
                    Log.i("black Theme lvUp이 5 이하일 때", "");
                    Toast.makeText(ctx, "- LV.5 이상 구입 가능 -", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (btnMent.equals("현재 테마")) {
            if (!originalBtnMent.equals("현재 테마") || !whiteBtnMent.equals("현재 테마") || !ableDBtnMent.equals("현재 테마")) {
                // 상점의 상단 프레임 블랙 (frameLayout)
                originalBtnMent.equals("적용");
                originalBtn.setText(originalBtnMent);

                ((MainActivity) MainActivity.ctx).title.setTextColor(Color.WHITE);
                // 메인의 타이틀 글씨 색깔 -> (title) white
                ((MainActivity) MainActivity.ctx).realMainLayout.setBackgroundColor(Color.BLACK);
                // 메인의 레이아웃 색깔 -> (mainLayout) Black
                ((MainActivity) MainActivity.ctx).recyclerView.setBackgroundColor(Color.BLACK);
                // 메인의 리사이클러뷰 색깔 -> (recyclerView) white
                // 리사이클러뷰 아이템 색깔 -> black
                ((MainActivity) MainActivity.ctx).frameLayout.setBackgroundColor(Color.BLACK);
                // 메인의 하단바 프레임 레이아웃 -> (frameLayout) black

                frameLayout.setBackgroundColor(Color.BLACK);
                // 타이틀바
                shopTitle.setTextColor(Color.WHITE);
                // 내 골드 : 블랙 (shopGold)
                shopGold.setTextColor(Color.WHITE);
                // 레이아웃 화이트 (shopLayout)
                shopLayout.setBackgroundColor(Color.BLACK);
                // 버튼 백그라운드 블랙 (shopClose)
                shopClose.setBackgroundColor(Color.BLACK);
                shopClose.setTextColor(Color.WHITE);
                ((MainActivity) MainActivity.ctx).ableDLogo.setVisibility(View.GONE);

                // 버튼 텍스트 화이트 (shopClose)
                // 현재 테마 버튼 백그라운드 화이트 글씨 블랙
                blackAndWhiteBuyBtn.setBackgroundColor(Color.BLACK);
                blackAndWhiteBuyBtn.setTextColor(Color.WHITE);
                blackThemePrice.setTextColor(Color.WHITE);

                originalBtn.setBackgroundColor(Color.BLACK);
                originalBtn.setTextColor(Color.WHITE);

                ableDBtn.setBackgroundColor(Color.BLACK);
                ableDBtn.setTextColor(Color.WHITE);
                ableDThemePrice.setTextColor(Color.WHITE);

                whiteBtn.setBackgroundColor(Color.BLACK);
                whiteBtn.setTextColor(Color.WHITE);
                whiteThemePrice.setTextColor(Color.WHITE);
            }
        }

        if (whiteBtnMent.equals("현재 테마") || whiteBtn.getText().toString().equals("현재 테마")) {
            if (originalBtnMent.equals("현재 테마") || originalBtn.getText().toString().equals("현재 테마")) {
                // 상점의 상단 프레임 블랙 (frameLayout)
                originalBtnMent = "적용";
                originalBtn.setText(originalBtnMent);
                frameLayout.setBackgroundColor(Color.BLACK);
            }
            if (blackAndWhiteBuyBtn.equals("현재 테마") || blackAndWhiteBuyBtn.getText().toString().equals("현재 테마")) {
                btnMent = "적용";
                blackAndWhiteBuyBtn.setText(btnMent);

            }
            if (ableDBtnMent.equals("현재 테마") || ableDBtn.getText().toString().equals("현재 테마")) {
                ableDBtnMent = "적용";
                ableDBtn.setText(ableDBtnMent);
            }

            ((MainActivity) MainActivity.ctx).title.setTextColor(Color.BLACK);
            // 메인의 타이틀 글씨 색깔 -> (title) white
            ((MainActivity) MainActivity.ctx).realMainLayout.setBackgroundColor(Color.WHITE);
            // 메인의 레이아웃 색깔 -> (mainLayout) Black
            ((MainActivity) MainActivity.ctx).recyclerView.setBackgroundColor(Color.WHITE);
            // 메인의 리사이클러뷰 색깔 -> (recyclerView) white
            // 리사이클러뷰 아이템 색깔 -> black
            ((MainActivity) MainActivity.ctx).frameLayout.setBackgroundColor(Color.BLACK);
            // 메인의 하단바 프레임 레이아웃 -> (frameLayout) black
            ((MainActivity) MainActivity.ctx).ableDLogo.setVisibility(View.GONE);

            // 타이틀바
            shopTitle.setTextColor(Color.BLACK);
            // 내 골드 : 블랙 (shopGold)
            shopGold.setTextColor(Color.BLACK);
            // 레이아웃 화이트 (shopLayout)
            shopLayout.setBackgroundColor(Color.WHITE);
            // 버튼 백그라운드 블랙 (shopClose)
            shopClose.setBackgroundColor(Color.WHITE);
            shopClose.setTextColor(Color.BLACK);
            // 버튼 텍스트 화이트 (shopClose)
            // 현재 테마 버튼 백그라운드 화이트 글씨 블랙
//            blackAndWhiteBuyBtn.setBackgroundColor(Color.BLACK);
//            blackAndWhiteBuyBtn.setTextColor(Color.WHITE);
//            blackThemePrice.setTextColor(Color.WHITE);
//
//            originalBtn.setBackgroundColor(Color.BLACK);
//            originalBtn.setTextColor(Color.WHITE);
//
//            ableDBtn.setBackgroundColor(Color.BLACK);
//            ableDBtn.setTextColor(Color.WHITE);
//            ableDThemePrice.setTextColor(Color.WHITE);
//
//            whiteBtn.setBackgroundColor(Color.BLACK);
//            whiteBtn.setTextColor(Color.WHITE);
//            whiteThemePrice.setTextColor(Color.WHITE);

            saveBlackThemeInShared();
            saveWhiteThemeInShared();
            saveOriginalThemeInShared();
            saveAbleDThemeInShared();

        }


        if (originalBtnMent.equals("현재 테마")) {

            if (ableDBtnMent.equals("현재 테마") || ableDBtn.getText().toString().equals("현재 테마")) {
                // 상점의 상단 프레임 블랙 (frameLayout)
                ableDBtnMent = "적용";
                ableDBtn.setText(ableDBtnMent);
//                frameLayout.setBackgroundColor(Color.BLACK);
            }
            if (blackAndWhiteBuyBtn.equals("현재 테마") || blackAndWhiteBuyBtn.getText().toString().equals("현재 테마")) {
                btnMent = "적용";
                blackAndWhiteBuyBtn.setText(btnMent);

            }
            if (whiteBtnMent.equals("현재 테마") || whiteBtn.getText().toString().equals("현재 테마")) {
                whiteBtnMent = "적용";
                whiteBtn.setText(whiteBtnMent);
            }
        }

        if (ableDBtnMent.equals("현재 테마")) {

            if (originalBtnMent.equals("현재 테마") || originalBtn.getText().toString().equals("현재 테마")) {
                // 상점의 상단 프레임 블랙 (frameLayout)
                originalBtnMent = "적용";
                originalBtn.setText(originalBtnMent);
//                frameLayout.setBackgroundColor(Color.BLACK);
            }
            if (blackAndWhiteBuyBtn.equals("현재 테마") || blackAndWhiteBuyBtn.getText().toString().equals("현재 테마")) {
                btnMent = "적용";
                blackAndWhiteBuyBtn.setText(btnMent);

            }
            if (whiteBtnMent.equals("현재 테마") || whiteBtn.getText().toString().equals("현재 테마")) {
                whiteBtnMent = "적용";
                whiteBtn.setText(whiteBtnMent);
            }

            ((MainActivity) MainActivity.ctx).ableDLogo.setVisibility(View.VISIBLE);
            ((MainActivity) MainActivity.ctx).title.setVisibility(View.GONE);
            ((MainActivity) MainActivity.ctx).realMainLayout.setBackgroundColor(Color.WHITE);
            ((MainActivity) MainActivity.ctx).recyclerView.setBackgroundColor(Color.WHITE);
            ((MainActivity) MainActivity.ctx).frameLayout.setBackgroundColor(Color.RED);

            frameLayout.setBackgroundColor(Color.RED);
            shopTitle.setTextColor(Color.WHITE);
            shopGold.setTextColor(Color.RED);
            shopLayout.setBackgroundColor(Color.WHITE);
            shopClose.setBackgroundColor(Color.RED);
            shopClose.setTextColor(Color.WHITE);
        }


        // if whiteBtnMent, ableDBtnMent, originalBtnMent

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        return;
    }

    protected void onStart() {
        super.onStart();
        Log.i("Shop", "onStart()");
    }

    protected void onResume() {
        super.onResume();
        Log.i("Shop", "onResume()");
    }

    protected void onPause() {
        super.onPause();
        Log.i("Shop", "onPause()");
        saveBlackThemeInShared();
    }

    protected void onStop() {
        super.onStop();
        Log.i("Shop", "onStop()");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i("Shop", "onDestroy()");
    }

    public void setGoldShared() {

        shared = getSharedPreferences("toDoList", Activity.MODE_PRIVATE);

        editor = shared.edit();

        myGold = shared.getString("myGold", "0");
    }

    public void setBlackShared() {

        shared = getSharedPreferences("toDoList", Activity.MODE_PRIVATE);

        editor = shared.edit();

        btnMent = shared.getString("myBlackTheme", "구매");

    }

    public void setWhiteShared() {
        shared = getSharedPreferences("toDoList", Activity.MODE_PRIVATE);

        editor = shared.edit();

        whiteBtnMent = shared.getString("myWhiteTheme", "구매");
    }

    public void setOriginalShared() {

        shared = getSharedPreferences("toDoList", Activity.MODE_PRIVATE);

        editor = shared.edit();

        originalBtnMent = shared.getString("myOriginalTheme", "현재 테마");
    }

    public void setAbleDShared() {

        shared = getSharedPreferences("toDoList", Activity.MODE_PRIVATE);

        editor = shared.edit();

        ableDBtnMent = shared.getString("myAbleDTheme", "구매");
    }

    public void saveBlackThemeInShared() {
        preferenceManager = new PreferenceManager();
        preferenceManager.setString(((Shop) Shop.ctx).getApplication()
                , "myBlackTheme", "" + btnMent);
        Log.i("myBlackTheme 쉐어드 저장", "" + btnMent);

        editor.commit();
    }

    public void saveWhiteThemeInShared() {
        preferenceManager = new PreferenceManager();
        preferenceManager.setString(((Shop) Shop.ctx).getApplication()
                , "myWhiteTheme", "" + whiteBtnMent);
        Log.i("myWhiteTheme 쉐어드 저장", "" + whiteBtnMent);

        editor.commit();
    }

    public void saveAbleDThemeInShared() {
        preferenceManager = new PreferenceManager();
        preferenceManager.setString(((Shop) Shop.ctx).getApplication()
                , "myAbleDTheme", "" + ableDBtnMent);
        Log.i("myAbleDTheme 쉐어드 저장", "" + ableDBtnMent);

        editor.commit();
    }

    public void saveOriginalThemeInShared() {
        preferenceManager = new PreferenceManager();
        preferenceManager.setString(((Shop) Shop.ctx).getApplication()
                , "myOriginalTheme", "" + originalBtnMent);
        Log.i("myOriginalTheme 쉐어드 저장", "" + originalBtnMent);

        editor.commit();
    }

    public void bringBlackThemeInShared() {
        Log.i("bringBlackThemeInShared", "구매");
        if (btnMent.equals("구매")) {
            Log.i("myBlackTheme 데이터 (X)", "" + btnMent);
        } else {
            Log.i("myBlackTheme 데이터 (O)", "" + btnMent);
//            blackAndWhiteBuyBtn.setText(btnMent);

        }
    }

    public void bringWhiteThemeInShared() {
        Log.i("bringWhiteThemeInShared", "구매");
        if (whiteBtnMent.equals("구매")) {
            Log.i("myWhiteTheme 데이터 (X)", "" + whiteBtnMent);
        } else {
            Log.i("myWhiteTheme 데이터 (O)", "" + whiteBtnMent);
//            blackAndWhiteBuyBtn.setText(btnMent);

        }
    }

    public void bringAbleDThemeInShared() {
        Log.i("bringAbleDThemeInShared", "구매");
        if (ableDBtnMent.equals("구매")) {
            Log.i("myAbleDTheme 데이터 (X)", "" + ableDBtnMent);
        } else {
            Log.i("myAbleDTheme 데이터 (O)", "" + ableDBtnMent);
//            blackAndWhiteBuyBtn.setText(btnMent);

        }
    }

    public void bringOriginalThemeInShared() {
        Log.i("bringOriginalThemeInShared", "현재 테마");
        if (originalBtnMent.equals("구매")) {
            Log.i("myOriginalTheme 데이터 (X)", "" + originalBtnMent);
        } else {
            Log.i("myOriginalTheme 데이터 (O)", "" + originalBtnMent);
//            blackAndWhiteBuyBtn.setText(btnMent);

        }
    }

    public void bringGoldInShared() {
        Log.i("bringGoldInShared", "");
        myGold = shared.getString("myGold", "0");
        gold = Integer.parseInt(myGold);
        if (gold == 0) {
            Log.i("myGold 데이터 (X)", "" + gold);
        } else {
            try {
                Log.i("myGold 데이터 (O)", "" + gold);
//                myGold = String.valueOf(goldPlus);
                shopGold.setText("내 골드 : " + gold);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveInGoldShared() {
        // saveInGoldShared
        preferenceManager = new PreferenceManager();
        preferenceManager.setString(((MainActivity) MainActivity.ctx).getApplication()
                , "myGold", "" + gold);
        Log.i("myGold 쉐어드 저장", "" + gold);

        editor.commit();
    }
}