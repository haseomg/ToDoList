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


        // ?????? ?????? UI
        blackAndWhiteBuyBtn = findViewById(R.id.blackAndWhiteBuyBtn);
        blackAndWhiteBuyBtn.setText(btnMent);
        blackThemePrice = findViewById(R.id.blackThemePrice);

        // ????????? ?????? UI
        whiteBtn = findViewById(R.id.whiteThemeBuyBtn);
        whiteBtn.setText(whiteBtnMent);
        whiteThemePrice = findViewById(R.id.whiteThemePrice);

        // ableD ?????? UI
        ableDBtn = findViewById(R.id.ableDThemeBuyBtn);
        ableDBtn.setText(ableDBtnMent);
        ableDThemePrice = findViewById(R.id.ableDThemePrice);

        // ???????????? ?????? UI
        originalBtn = findViewById(R.id.originalBtn);
        originalBtn.setText(originalBtnMent);


        shopTitle = findViewById(R.id.shopTitle);
        frameLayout = findViewById(R.id.shopFrameLayout);
        original = findViewById(R.id.original);
        shopLayout = findViewById(R.id.shopLayout);

        shopGold = (TextView) findViewById(R.id.shopGold);
        shopGold.setText("??? ?????? : " + myGold);
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


        btnMent = shared.getString("myBlackTheme", "??????");

        whiteBtnMent = shared.getString("myWhiteTheme", "??????");

        originalBtnMent = shared.getString("myOriginalTheme", "??????");

        ableDBtnMent = shared.getString("myAbleDTheme", "??????");

        level = shared.getString("myLevel", "1");
        lvUp = Integer.parseInt(level);

        if (btnMent.equals("?????? ??????") || blackAndWhiteBuyBtn.getText().toString().equals("?????? ??????")) {
            originalBtnMent = "??????";
            originalBtn.setText(originalBtnMent);
            ((MainActivity) MainActivity.ctx).ableDLogo.setVisibility(View.GONE);
            saveOriginalThemeInShared();
        }

        if (whiteBtnMent.equals("?????? ??????") || whiteBtn.getText().toString().equals("?????? ??????")) {

            Log.i("????????? ???????", "");
            originalBtnMent = "??????";
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

        if (ableDBtnMent.equals("?????? ??????") || ableDBtn.getText().toString().equals("?????? ??????")) {

            Log.i("????????? ???????", "");
            originalBtnMent = "??????";
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
                // ?????? ??????
                if (lvUp >= 20) {
                    if (ableDBtnMent.equals("??????") || ableDBtn.getText().toString().equals("??????")) {

                        // ?????? ??????
                        if (gold >= 3000) {
                            // myGold = shared.getString
                            // int gold??? ??? ??????
                            gold = Integer.parseInt(myGold);
                            // ?????? ??????
                            gold = gold - 3000;


                            Log.i("???????????? ????????? ??? ???? ???????", "");
                            ableDBtnMent = "??????";
                            ableDBtn.setText(ableDBtnMent);
                            saveAbleDThemeInShared();

                            if (btnMent.equals("?????? ??????")) {
                                btnMent = "??????";
                                blackAndWhiteBuyBtn.setText(btnMent);
                                saveBlackThemeInShared();
                            }

                            if (whiteBtnMent.equals("?????? ??????")) {
                                whiteBtnMent = "??????";
                                whiteBtn.setText(whiteBtnMent);
                                saveWhiteThemeInShared();
                            }

                            if (originalBtnMent.equals("?????? ??????")) {
                                originalBtnMent = "??????";
                                originalBtn.setText(originalBtnMent);
                                saveOriginalThemeInShared();
                            }

                            shopGold.setText("??? ?????? : " + gold);

                            saveInGoldShared();
                            // ?????? ??????
                        } else if (gold < 3000) {
                            Toast.makeText(ctx, "????????? ???????????????.", Toast.LENGTH_SHORT).show();
                        }

                    } else if (ableDBtnMent.equals("??????") || ableDBtn.getText().toString().equals("??????")) {

                        if (btnMent.equals("?????? ??????") || blackAndWhiteBuyBtn.getText().toString().equals("?????? ??????")) {
                            btnMent = "??????";
                            blackAndWhiteBuyBtn.setText(btnMent);
                            saveBlackThemeInShared();
                        } else if (btnMent.equals("??????") || blackAndWhiteBuyBtn.getText().toString().equals("??????")) {

                        } else if (btnMent.equals("??????") || blackAndWhiteBuyBtn.getText().toString().equals("??????")) {

                        }

                        if (originalBtnMent.equals("?????? ??????") || originalBtn.getText().toString().equals("?????? ??????")) {
                            originalBtnMent = "??????";
                            originalBtn.setText(originalBtnMent);
                            saveOriginalThemeInShared();
                        } else if (originalBtnMent.equals("??????") || originalBtn.getText().toString().equals("??????")) {

                        } else if (originalBtnMent.equals("??????") || originalBtn.getText().toString().equals("??????")) {

                        }

                        if (whiteBtnMent.equals("?????? ??????") || whiteBtn.getText().toString().equals("?????? ??????")) {
                            whiteBtnMent = "??????";
                            whiteBtn.setText(whiteBtnMent);
                            saveWhiteThemeInShared();
                        } else if (whiteBtnMent.equals("??????") || whiteBtn.getText().toString().equals("??????")) {

                        } else if (whiteBtnMent.equals("??????") || whiteBtn.getText().toString().equals("??????")) {

                        }

//                        try {
                        ((MainActivity) MainActivity.ctx).ableDLogo.setVisibility(View.VISIBLE);
                        ((MainActivity) MainActivity.ctx).title.setVisibility(View.GONE);
                        // ????????? ????????? ?????? ?????? -> (title) white
                        ((MainActivity) MainActivity.ctx).realMainLayout.setBackgroundColor(Color.WHITE);
                        // ????????? ???????????? ?????? -> (mainLayout) Black
                        ((MainActivity) MainActivity.ctx).recyclerView.setBackgroundColor(Color.WHITE);
                        // ????????? ?????????????????? ?????? -> (recyclerView) white
                        // ?????????????????? ????????? ?????? -> black
                        ((MainActivity) MainActivity.ctx).frameLayout.setBackgroundColor(Color.RED);
                        // ????????? ????????? ????????? ???????????? -> (frameLayout) black

                        // ????????? ?????? ????????? ?????? (frameLayout)
                        frameLayout.setBackgroundColor(Color.RED);
                        // ????????????
                        shopTitle.setTextColor(Color.WHITE);
                        // ??? ?????? : ?????? (shopGold)
                        shopGold.setTextColor(Color.RED);
                        // ???????????? ????????? (shopLayout)
                        shopLayout.setBackgroundColor(Color.WHITE);
                        // ?????? ??????????????? ?????? (shopClose)
                        shopClose.setBackgroundColor(Color.RED);
                        shopClose.setTextColor(Color.WHITE);
                        // ?????? ????????? ????????? (shopClose)
                        // ?????? ?????? ?????? ??????????????? ????????? ?????? ??????
                        ableDBtnMent = "?????? ??????";
                        ableDBtn.setText(ableDBtnMent);
                        saveBlackThemeInShared();
                        saveWhiteThemeInShared();
                        saveAbleDThemeInShared();
                        saveOriginalThemeInShared();

                    } else if (ableDBtnMent.equals("?????? ??????")) {

                        if (btnMent.equals("?????? ??????") || blackAndWhiteBuyBtn.getText().toString().equals("?????? ??????")) {
                            btnMent = "??????";
                            blackAndWhiteBuyBtn.setText(btnMent);
                            saveBlackThemeInShared();
                        } else if (btnMent.equals("??????") || blackAndWhiteBuyBtn.getText().toString().equals("??????")) {

                        } else if (btnMent.equals("??????") || blackAndWhiteBuyBtn.getText().toString().equals("??????")) {

                        }

                        if (originalBtnMent.equals("?????? ??????") || originalBtn.getText().toString().equals("?????? ??????")) {
                            originalBtnMent = "??????";
                            originalBtn.setText(originalBtnMent);
                            saveOriginalThemeInShared();
                        } else if (originalBtnMent.equals("??????") || originalBtn.getText().toString().equals("??????")) {

                        } else if (originalBtnMent.equals("??????") || originalBtn.getText().toString().equals("??????")) {

                        }

                        if (whiteBtnMent.equals("?????? ??????") || whiteBtn.getText().toString().equals("?????? ??????")) {
                            whiteBtnMent = "??????";
                            whiteBtn.setText(whiteBtnMent);
                            saveWhiteThemeInShared();
                        } else if (whiteBtnMent.equals("??????") || whiteBtn.getText().toString().equals("??????")) {

                        } else if (whiteBtnMent.equals("??????") || whiteBtn.getText().toString().equals("??????")) {

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

                        ableDBtnMent = "?????? ??????";
                        ableDBtn.setText(ableDBtnMent);

                        saveAbleDThemeInShared();
                    }
                } else {
                    Log.i("AbleD Theme lvUp??? 20 ????????? ???", "");
                    Toast.makeText(ctx, "- LV.20 ?????? ?????? 33?????? -", Toast.LENGTH_SHORT).show();
                }
            }
        });

        originalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ?????? ??????
                if (lvUp >= 0) {
                    if (originalBtnMent.equals("??????") || originalBtn.getText().toString().equals("??????")) {

                        // ?????? ??????
                        if (gold >= 0) {
                            // myGold = shared.getString
                            // int gold??? ??? ??????
                            gold = Integer.parseInt(myGold);
                            // ?????? ??????
                            gold = gold - 0;


                            Log.i("????????? ?????? ??? ??????? ???????", "");
                            originalBtnMent = "??????";
                            originalBtn.setText(originalBtnMent);
                            saveWhiteThemeInShared();

                            if (btnMent.equals("?????? ??????")) {
                                btnMent = "??????";
                                blackAndWhiteBuyBtn.setText(btnMent);
                                saveBlackThemeInShared();
                            }

                            if (ableDBtnMent.equals("?????? ??????")) {
                                ableDBtnMent = "??????";
                                ableDBtn.setText(ableDBtnMent);
                                saveAbleDThemeInShared();
                            }

                            if (whiteBtnMent.equals("?????? ??????")) {
                                whiteBtnMent = "??????";
                                whiteBtn.setText(whiteBtnMent);
                                saveWhiteThemeInShared();
                            }

                            shopGold.setText("??? ?????? : " + gold);

                            saveInGoldShared();
                        }

                    } else if (originalBtnMent.equals("??????") || whiteBtn.getText().toString().equals("??????")) {

                        if (btnMent.equals("?????? ??????")) {
                            btnMent = "??????";
                            blackAndWhiteBuyBtn.setText(btnMent);
                            saveBlackThemeInShared();
                        }

                        if (ableDBtnMent.equals("?????? ??????")) {
                            ableDBtnMent = "??????";
                            ableDBtn.setText(ableDBtnMent);
                            saveAbleDThemeInShared();
                        }

                        if (whiteBtnMent.equals("?????? ??????")) {
                            whiteBtnMent = "??????";
                            whiteBtn.setText(whiteBtnMent);
                            saveWhiteThemeInShared();
                        }

                        ((MainActivity) MainActivity.ctx).title.setTextColor(Color.BLACK);
                        // ????????? ????????? ?????? ?????? -> (title) white
                        ((MainActivity) MainActivity.ctx).realMainLayout.setBackgroundColor(Color.WHITE);
                        // ????????? ???????????? ?????? -> (mainLayout) Black
                        ((MainActivity) MainActivity.ctx).recyclerView.setBackgroundColor(Color.WHITE);
                        // ????????? ?????????????????? ?????? -> (recyclerView) white
                        // ?????????????????? ????????? ?????? -> black
                        ((MainActivity) MainActivity.ctx).frameLayout.setBackgroundColor(Color.BLACK);
                        ((MainActivity) MainActivity.ctx).gold.setTextColor(Color.WHITE);
                        ((MainActivity) MainActivity.ctx).userLv.setTextColor(Color.WHITE);
                        ((MainActivity) MainActivity.ctx).toDid.setTextColor(Color.WHITE);
                        ((MainActivity) MainActivity.ctx).myProgress.setTextColor(Color.WHITE);

                        originalBtnMent = "?????? ??????";
                        originalBtn.setText(originalBtnMent);
                        saveBlackThemeInShared();
                        saveWhiteThemeInShared();
                        saveAbleDThemeInShared();
                        saveOriginalThemeInShared();

                    } else if (whiteBtnMent.equals("?????? ??????")) {

                        if (btnMent.equals("?????? ??????")) {
                            btnMent = "??????";
                            blackAndWhiteBuyBtn.setText(btnMent);
                            saveBlackThemeInShared();
                        }

                        if (ableDBtnMent.equals("?????? ??????")) {
                            ableDBtnMent = "??????";
                            ableDBtn.setText(ableDBtnMent);
                            saveAbleDThemeInShared();
                        }

                        if (whiteBtnMent.equals("?????? ??????")) {
                            whiteBtnMent = "??????";
                            whiteBtn.setText(whiteBtnMent);
                            saveWhiteThemeInShared();
                        }

                        originalBtnMent = "?????? ??????";
                        originalBtn.setText(originalBtnMent);

                        saveWhiteThemeInShared();
                    }
                } else {
                    Log.i("White Theme lvUp??? 5 ????????? ???", "");
                    Toast.makeText(ctx, "- LV.5 ?????? ?????? ?????? -", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // ????????? ?????? ?????? ???
        whiteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ?????? ??????
                if (lvUp >= 5) {
                    if (whiteBtnMent.equals("??????") || whiteBtn.getText().toString().equals("??????")) {

                        // ?????? ??????
                        if (gold >= 1200) {
                            // myGold = shared.getString
                            // int gold??? ??? ??????
                            gold = Integer.parseInt(myGold);
                            // ?????? ??????
                            gold = gold - 1200;


                            Log.i("????????? ?????? ??? ??????? ???????", "");
                            whiteBtnMent = "??????";
                            whiteBtn.setText(whiteBtnMent);
                            saveWhiteThemeInShared();

                            if (btnMent.equals("?????? ??????")) {
                                btnMent = "??????";
                                blackAndWhiteBuyBtn.setText(btnMent);
                                saveBlackThemeInShared();
                            }

                            if (ableDBtnMent.equals("?????? ??????")) {
                                ableDBtnMent = "??????";
                                ableDBtn.setText(ableDBtnMent);
                                saveAbleDThemeInShared();
                            }

                            shopGold.setText("??? ?????? : " + gold);

                            saveInGoldShared();
                        } else if (gold < 1200) {
                            Toast.makeText(ctx, "????????? ???????????????.", Toast.LENGTH_SHORT).show();
                        }

                    } else if (whiteBtnMent.equals("??????") || whiteBtn.getText().toString().equals("??????")) {

                        if (btnMent.equals("?????? ??????") || blackAndWhiteBuyBtn.getText().toString().equals("?????? ??????")) {
                            btnMent = "??????";
                            blackAndWhiteBuyBtn.setText(btnMent);
                            saveBlackThemeInShared();
                        } else if (btnMent.equals("??????") || blackAndWhiteBuyBtn.getText().toString().equals("??????")) {

                        } else if (btnMent.equals("??????") || blackAndWhiteBuyBtn.getText().toString().equals("??????")) {

                        }

                        if (originalBtnMent.equals("?????? ??????") || originalBtn.getText().toString().equals("?????? ??????")) {
                            originalBtnMent = "??????";
                            originalBtn.setText(originalBtnMent);
                            saveOriginalThemeInShared();
                        } else if (originalBtnMent.equals("??????") || originalBtn.getText().toString().equals("??????")) {

                        } else if (originalBtnMent.equals("??????") || originalBtn.getText().toString().equals("??????")) {

                        }

                        if (ableDBtnMent.equals("?????? ??????") || ableDBtn.getText().toString().equals("?????? ??????")) {
                            ableDBtnMent = "??????";
                            ableDBtn.setText(ableDBtnMent);
                            saveAbleDThemeInShared();
                        } else if (ableDBtnMent.equals("??????") || ableDBtn.getText().toString().equals("??????")) {

                        } else if (ableDBtnMent.equals("??????") || ableDBtn.getText().toString().equals("??????")) {

                        }

//                        try {
                        ((MainActivity) MainActivity.ctx).title.setTextColor(Color.BLACK);
                        // ????????? ????????? ?????? ?????? -> (title) white
                        ((MainActivity) MainActivity.ctx).realMainLayout.setBackgroundColor(Color.WHITE);
                        // ????????? ???????????? ?????? -> (mainLayout) Black
                        ((MainActivity) MainActivity.ctx).recyclerView.setBackgroundColor(Color.WHITE);
                        // ????????? ?????????????????? ?????? -> (recyclerView) white
                        // ?????????????????? ????????? ?????? -> black
                        ((MainActivity) MainActivity.ctx).frameLayout.setBackgroundColor(Color.BLACK);
                        // ????????? ????????? ????????? ???????????? -> (frameLayout) black

                        // ????????? ?????? ????????? ?????? (frameLayout)
                        frameLayout.setBackgroundColor(Color.WHITE);
                        // ????????????
                        shopTitle.setTextColor(Color.BLACK);
                        // ??? ?????? : ?????? (shopGold)
                        shopGold.setTextColor(Color.BLACK);
                        // ???????????? ????????? (shopLayout)
                        shopLayout.setBackgroundColor(Color.WHITE);
                        // ?????? ??????????????? ?????? (shopClose)
                        shopClose.setBackgroundColor(Color.WHITE);
                        shopClose.setTextColor(Color.BLACK);


                        whiteBtnMent = "?????? ??????";
                        whiteBtn.setText(whiteBtnMent);
                        saveBlackThemeInShared();
                        saveWhiteThemeInShared();
                        saveAbleDThemeInShared();
                        saveOriginalThemeInShared();

                    } else if (whiteBtnMent.equals("?????? ??????")) {

                        if (btnMent.equals("?????? ??????") || blackAndWhiteBuyBtn.getText().toString().equals("?????? ??????")) {
                            btnMent = "??????";
                            blackAndWhiteBuyBtn.setText(btnMent);
                            saveBlackThemeInShared();
                        } else if (btnMent.equals("??????") || blackAndWhiteBuyBtn.getText().toString().equals("??????")) {

                        } else if (btnMent.equals("??????") || blackAndWhiteBuyBtn.getText().toString().equals("??????")) {

                        }

                        if (originalBtnMent.equals("?????? ??????") || originalBtn.getText().toString().equals("?????? ??????")) {
                            originalBtnMent = "??????";
                            originalBtn.setText(originalBtnMent);
                            saveOriginalThemeInShared();
                        } else if (originalBtnMent.equals("??????") || originalBtn.getText().toString().equals("??????")) {

                        } else if (originalBtnMent.equals("??????") || originalBtn.getText().toString().equals("??????")) {

                        }

                        if (ableDBtnMent.equals("?????? ??????") || ableDBtn.getText().toString().equals("?????? ??????")) {
                            ableDBtnMent = "??????";
                            ableDBtn.setText(ableDBtnMent);
                            saveAbleDThemeInShared();
                        } else if (ableDBtnMent.equals("??????") || ableDBtn.getText().toString().equals("??????")) {

                        } else if (ableDBtnMent.equals("??????") || ableDBtn.getText().toString().equals("??????")) {

                        }

                        try {

                            ((MainActivity) MainActivity.ctx).title.setTextColor(Color.BLACK);
                            ((MainActivity) MainActivity.ctx).realMainLayout.setBackgroundColor(Color.WHITE);
                            // ????????? ???????????? ?????? -> (mainLayout) Black
                            ((MainActivity) MainActivity.ctx).recyclerView.setBackgroundColor(Color.WHITE);
                            // ????????? ?????????????????? ?????? -> (recyclerView) white
                            // ?????????????????? ????????? ?????? -> black
                            ((MainActivity) MainActivity.ctx).frameLayout.setBackgroundColor(Color.BLACK);



                            // ????????? ?????? ????????? ?????? (frameLayout)
                            frameLayout.setBackgroundColor(Color.WHITE);
                            // ????????????
                            shopTitle.setTextColor(Color.BLACK);
                            // ??? ?????? : ?????? (shopGold)
                            shopGold.setTextColor(Color.BLACK);
                            // ???????????? ????????? (shopLayout)
                            shopLayout.setBackgroundColor(Color.WHITE);
                            // ?????? ??????????????? ?????? (shopClose)
                            shopClose.setBackgroundColor(Color.WHITE);
                            shopClose.setTextColor(Color.BLACK);
                            // ?????? ????????? ????????? (shopClose)
                            // ?????? ?????? ?????? ??????????????? ????????? ?????? ??????
//                            blackAndWhiteBuyBtn.setBackgroundColor(Color.parseColor("#0074C2"));
//                            blackAndWhiteBuyBtn.setTextColor(Color.parseColor("#F7F7F7"));
//
//                            originalBtn.setBackgroundColor(Color.parseColor("#0074C2"));
//                            originalBtn.setTextColor(Color.parseColor("#F7F7F7"));
//
//                            blackThemePrice.setTextColor(Color.parseColor("#589FCF"));

//                            // ????????? ?????? ????????? ?????? (deleteFrameLayout)
//                            ((Delete) Delete.ctx).deleteFrameLayout.setBackgroundColor(Color.parseColor("#0074C2"));
//                            // ???????????? ?????? (deleteMainLayout)
//                            (((Delete) Delete.ctx).deleteMainLayout).setBackgroundColor(Color.parseColor("#D0E4ED"));
//                            // ?????? ??????????????? ?????? (deleteClose)
//                            (((Delete) Delete.ctx).close).setBackgroundColor(Color.parseColor("#F7F7F7"));
//                            // ?????? ????????? ?????????
//                            (((Delete) Delete.ctx).close).setTextColor(Color.parseColor("#0074C2"));
//                            // ?????????????????? ????????? ??????


                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }

                        whiteBtnMent = "?????? ??????";
                        whiteBtn.setText(whiteBtnMent);

                        saveWhiteThemeInShared();
                    }
                } else {
                    Log.i("White Theme lvUp??? 5 ????????? ???", "");
                    Toast.makeText(ctx, "- LV.5 ?????? ?????? ?????? -", Toast.LENGTH_SHORT).show();
                }
            }
        });


//        gold = ((MainActivity) MainActivity.ctx).goldPlus;

        blackAndWhiteBuyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("???????????? ?????? ?????? ??????", "");

                Log.i("lvUpCheck", "" + lvUp + " / " + level);
                // ?????? ?????? !!!!!!!!!
                if (lvUp >= 5) {
                    Log.i("lvUp??? 5 ????????? ???", "");
                    if (btnMent.equals("??????") || blackAndWhiteBuyBtn.getText().toString().equals("??????")) {
                        // ?????? ?????? !!!!!!!
                        if (gold >= 1200) {
                            // myGold = shared.getString
                            // int gold??? ??? ??????
                            gold = Integer.parseInt(myGold);
                            // ?????? ??????
                            gold = gold - 1200;


                            btnMent = "??????";
                            blackAndWhiteBuyBtn.setText(btnMent);
                            saveBlackThemeInShared();

                            shopGold.setText("??? ?????? : " + gold);

                            saveInGoldShared();
                        } else if (gold < 1200) {
                            Toast.makeText(ctx, "????????? ???????????????.", Toast.LENGTH_SHORT).show();
                        }
                    } else if (btnMent.equals("??????") || blackAndWhiteBuyBtn.getText().toString().equals("??????")) {

                        // btnMent??? ???????????? ???????????? ???????????? ???!
                        btnMent = "?????? ??????";
                        blackAndWhiteBuyBtn.setText(btnMent);

                        if (whiteBtnMent.equals("?????? ??????") || whiteBtn.getText().toString().equals("?????? ??????")) {
                            whiteBtnMent = "??????";
                            whiteBtn.setText(whiteBtnMent);
                            saveWhiteThemeInShared();
                        } else if (btnMent.equals("??????") || whiteBtn.getText().toString().equals("??????")) {

                        } else if (btnMent.equals("??????") || whiteBtn.getText().toString().equals("??????")) {

                        }

                        if (originalBtnMent.equals("?????? ??????") || originalBtn.getText().toString().equals("?????? ??????")) {
                            originalBtnMent = "??????";
                            originalBtn.setText(originalBtnMent);
                            saveOriginalThemeInShared();
                        } else if (originalBtnMent.equals("??????") || originalBtn.getText().toString().equals("??????")) {

                        } else if (originalBtnMent.equals("??????") || originalBtn.getText().toString().equals("??????")) {

                        }

                        if (ableDBtnMent.equals("?????? ??????") || ableDBtn.getText().toString().equals("?????? ??????")) {
                            ableDBtnMent = "??????";
                            ableDBtn.setText(ableDBtnMent);
                            saveAbleDThemeInShared();
                        } else if (ableDBtnMent.equals("??????") || ableDBtn.getText().toString().equals("??????")) {

                        } else if (ableDBtnMent.equals("??????") || ableDBtn.getText().toString().equals("??????")) {

                        }


                        try {
                            ((MainActivity) MainActivity.ctx).title.setTextColor(Color.WHITE);
                            // ????????? ????????? ?????? ?????? -> (title) white
                            ((MainActivity) MainActivity.ctx).realMainLayout.setBackgroundColor(Color.BLACK);
                            // ????????? ???????????? ?????? -> (mainLayout) Black
                            ((MainActivity) MainActivity.ctx).recyclerView.setBackgroundColor(Color.BLACK);
                            // ????????? ?????????????????? ?????? -> (recyclerView) white
                            // ?????????????????? ????????? ?????? -> black
                            ((MainActivity) MainActivity.ctx).frameLayout.setBackgroundColor(Color.BLACK);
                            // ????????? ????????? ????????? ???????????? -> (frameLayout) black

                            // ????????? ?????? ????????? ?????? (frameLayout)
                            frameLayout.setBackgroundColor(Color.BLACK);
                            // ????????????
                            shopTitle.setTextColor(Color.WHITE);
                            // ??? ?????? : ?????? (shopGold)
                            shopGold.setTextColor(Color.WHITE);
                            // ???????????? ????????? (shopLayout)
                            shopLayout.setBackgroundColor(Color.BLACK);
                            // ?????? ??????????????? ?????? (shopClose)
                            shopClose.setBackgroundColor(Color.BLACK);
                            shopClose.setTextColor(Color.WHITE);
                            // ?????? ????????? ????????? (shopClose)
                            // ?????? ?????? ?????? ??????????????? ????????? ?????? ??????
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

                            // ????????? ?????? ????????? ?????? (deleteFrameLayout)
                            ((Delete) Delete.ctx).deleteFrameLayout.setBackgroundColor(Color.BLACK);
                            // ???????????? ?????? (deleteMainLayout)
                            (((Delete) Delete.ctx).deleteMainLayout).setBackgroundColor(Color.BLACK);
                            // ?????? ??????????????? ?????? (deleteClose)
                            (((Delete) Delete.ctx).close).setBackgroundColor(Color.BLACK);
                            // ?????? ????????? ?????????
                            (((Delete) Delete.ctx).close).setTextColor(Color.WHITE);
                            // ?????????????????? ????????? ??????

                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }

                        saveBlackThemeInShared();


                    } else if (btnMent.equals("?????? ??????") || blackAndWhiteBuyBtn.getText().toString().equals("?????? ??????")) {

                        btnMent = "?????? ??????";
                        blackAndWhiteBuyBtn.setText(btnMent);
                        saveBlackThemeInShared();


                        if (whiteBtnMent.equals("?????? ??????") || whiteBtn.getText().toString().equals("?????? ??????")) {
                            whiteBtnMent = "??????";
                            whiteBtn.setText(whiteBtnMent);
                            saveWhiteThemeInShared();
                        } else if (btnMent.equals("??????") || whiteBtn.getText().toString().equals("??????")) {

                        } else if (btnMent.equals("??????") || whiteBtn.getText().toString().equals("??????")) {

                        }

                        if (originalBtnMent.equals("?????? ??????") || originalBtn.getText().toString().equals("?????? ??????")) {
                            originalBtnMent = "??????";
                            originalBtn.setText(originalBtnMent);
                            saveOriginalThemeInShared();
                        } else if (originalBtnMent.equals("??????") || originalBtn.getText().toString().equals("??????")) {

                        } else if (originalBtnMent.equals("??????") || originalBtn.getText().toString().equals("??????")) {

                        }

                        if (ableDBtnMent.equals("?????? ??????") || ableDBtn.getText().toString().equals("?????? ??????")) {
                            ableDBtnMent = "??????";
                            ableDBtn.setText(ableDBtnMent);
                            saveAbleDThemeInShared();
                        } else if (ableDBtnMent.equals("??????") || ableDBtn.getText().toString().equals("??????")) {

                        } else if (ableDBtnMent.equals("??????") || ableDBtn.getText().toString().equals("??????")) {

                        }

                        try {
                            ((MainActivity) MainActivity.ctx).title.setTextColor(Color.WHITE);
                            // ????????? ????????? ?????? ?????? -> (title) white
                            ((MainActivity) MainActivity.ctx).realMainLayout.setBackgroundColor(Color.BLACK);
                            // ????????? ???????????? ?????? -> (mainLayout) Black
                            ((MainActivity) MainActivity.ctx).recyclerView.setBackgroundColor(Color.BLACK);
                            // ????????? ?????????????????? ?????? -> (recyclerView) white
                            // ?????????????????? ????????? ?????? -> black
                            ((MainActivity) MainActivity.ctx).frameLayout.setBackgroundColor(Color.BLACK);
                            // ????????? ????????? ????????? ???????????? -> (frameLayout) black

                            // ????????? ?????? ????????? ?????? (frameLayout)
                            frameLayout.setBackgroundColor(Color.BLACK);
                            // ????????????
                            shopTitle.setTextColor(Color.WHITE);
                            // ??? ?????? : ?????? (shopGold)
                            shopGold.setTextColor(Color.WHITE);
                            // ???????????? ????????? (shopLayout)
                            shopLayout.setBackgroundColor(Color.BLACK);
                            // ?????? ??????????????? ?????? (shopClose)
                            shopClose.setBackgroundColor(Color.BLACK);
                            shopClose.setTextColor(Color.WHITE);
                            // ?????? ????????? ????????? (shopClose)
                            // ?????? ?????? ?????? ??????????????? ????????? ?????? ??????
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

                        // ???????????? LV.5???
                    }
                } else {
                    Log.i("black Theme lvUp??? 5 ????????? ???", "");
                    Toast.makeText(ctx, "- LV.5 ?????? ?????? ?????? -", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (btnMent.equals("?????? ??????")) {
            if (!originalBtnMent.equals("?????? ??????") || !whiteBtnMent.equals("?????? ??????") || !ableDBtnMent.equals("?????? ??????")) {
                // ????????? ?????? ????????? ?????? (frameLayout)
                originalBtnMent.equals("??????");
                originalBtn.setText(originalBtnMent);

                ((MainActivity) MainActivity.ctx).title.setTextColor(Color.WHITE);
                // ????????? ????????? ?????? ?????? -> (title) white
                ((MainActivity) MainActivity.ctx).realMainLayout.setBackgroundColor(Color.BLACK);
                // ????????? ???????????? ?????? -> (mainLayout) Black
                ((MainActivity) MainActivity.ctx).recyclerView.setBackgroundColor(Color.BLACK);
                // ????????? ?????????????????? ?????? -> (recyclerView) white
                // ?????????????????? ????????? ?????? -> black
                ((MainActivity) MainActivity.ctx).frameLayout.setBackgroundColor(Color.BLACK);
                // ????????? ????????? ????????? ???????????? -> (frameLayout) black

                frameLayout.setBackgroundColor(Color.BLACK);
                // ????????????
                shopTitle.setTextColor(Color.WHITE);
                // ??? ?????? : ?????? (shopGold)
                shopGold.setTextColor(Color.WHITE);
                // ???????????? ????????? (shopLayout)
                shopLayout.setBackgroundColor(Color.BLACK);
                // ?????? ??????????????? ?????? (shopClose)
                shopClose.setBackgroundColor(Color.BLACK);
                shopClose.setTextColor(Color.WHITE);
                ((MainActivity) MainActivity.ctx).ableDLogo.setVisibility(View.GONE);

                // ?????? ????????? ????????? (shopClose)
                // ?????? ?????? ?????? ??????????????? ????????? ?????? ??????
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

        if (whiteBtnMent.equals("?????? ??????") || whiteBtn.getText().toString().equals("?????? ??????")) {
            if (originalBtnMent.equals("?????? ??????") || originalBtn.getText().toString().equals("?????? ??????")) {
                // ????????? ?????? ????????? ?????? (frameLayout)
                originalBtnMent = "??????";
                originalBtn.setText(originalBtnMent);
                frameLayout.setBackgroundColor(Color.BLACK);
            }
            if (blackAndWhiteBuyBtn.equals("?????? ??????") || blackAndWhiteBuyBtn.getText().toString().equals("?????? ??????")) {
                btnMent = "??????";
                blackAndWhiteBuyBtn.setText(btnMent);

            }
            if (ableDBtnMent.equals("?????? ??????") || ableDBtn.getText().toString().equals("?????? ??????")) {
                ableDBtnMent = "??????";
                ableDBtn.setText(ableDBtnMent);
            }

            ((MainActivity) MainActivity.ctx).title.setTextColor(Color.BLACK);
            // ????????? ????????? ?????? ?????? -> (title) white
            ((MainActivity) MainActivity.ctx).realMainLayout.setBackgroundColor(Color.WHITE);
            // ????????? ???????????? ?????? -> (mainLayout) Black
            ((MainActivity) MainActivity.ctx).recyclerView.setBackgroundColor(Color.WHITE);
            // ????????? ?????????????????? ?????? -> (recyclerView) white
            // ?????????????????? ????????? ?????? -> black
            ((MainActivity) MainActivity.ctx).frameLayout.setBackgroundColor(Color.BLACK);
            // ????????? ????????? ????????? ???????????? -> (frameLayout) black
            ((MainActivity) MainActivity.ctx).ableDLogo.setVisibility(View.GONE);

            // ????????????
            shopTitle.setTextColor(Color.BLACK);
            // ??? ?????? : ?????? (shopGold)
            shopGold.setTextColor(Color.BLACK);
            // ???????????? ????????? (shopLayout)
            shopLayout.setBackgroundColor(Color.WHITE);
            // ?????? ??????????????? ?????? (shopClose)
            shopClose.setBackgroundColor(Color.WHITE);
            shopClose.setTextColor(Color.BLACK);
            // ?????? ????????? ????????? (shopClose)
            // ?????? ?????? ?????? ??????????????? ????????? ?????? ??????
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


        if (originalBtnMent.equals("?????? ??????")) {

            if (ableDBtnMent.equals("?????? ??????") || ableDBtn.getText().toString().equals("?????? ??????")) {
                // ????????? ?????? ????????? ?????? (frameLayout)
                ableDBtnMent = "??????";
                ableDBtn.setText(ableDBtnMent);
//                frameLayout.setBackgroundColor(Color.BLACK);
            }
            if (blackAndWhiteBuyBtn.equals("?????? ??????") || blackAndWhiteBuyBtn.getText().toString().equals("?????? ??????")) {
                btnMent = "??????";
                blackAndWhiteBuyBtn.setText(btnMent);

            }
            if (whiteBtnMent.equals("?????? ??????") || whiteBtn.getText().toString().equals("?????? ??????")) {
                whiteBtnMent = "??????";
                whiteBtn.setText(whiteBtnMent);
            }
        }

        if (ableDBtnMent.equals("?????? ??????")) {

            if (originalBtnMent.equals("?????? ??????") || originalBtn.getText().toString().equals("?????? ??????")) {
                // ????????? ?????? ????????? ?????? (frameLayout)
                originalBtnMent = "??????";
                originalBtn.setText(originalBtnMent);
//                frameLayout.setBackgroundColor(Color.BLACK);
            }
            if (blackAndWhiteBuyBtn.equals("?????? ??????") || blackAndWhiteBuyBtn.getText().toString().equals("?????? ??????")) {
                btnMent = "??????";
                blackAndWhiteBuyBtn.setText(btnMent);

            }
            if (whiteBtnMent.equals("?????? ??????") || whiteBtn.getText().toString().equals("?????? ??????")) {
                whiteBtnMent = "??????";
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

        btnMent = shared.getString("myBlackTheme", "??????");

    }

    public void setWhiteShared() {
        shared = getSharedPreferences("toDoList", Activity.MODE_PRIVATE);

        editor = shared.edit();

        whiteBtnMent = shared.getString("myWhiteTheme", "??????");
    }

    public void setOriginalShared() {

        shared = getSharedPreferences("toDoList", Activity.MODE_PRIVATE);

        editor = shared.edit();

        originalBtnMent = shared.getString("myOriginalTheme", "?????? ??????");
    }

    public void setAbleDShared() {

        shared = getSharedPreferences("toDoList", Activity.MODE_PRIVATE);

        editor = shared.edit();

        ableDBtnMent = shared.getString("myAbleDTheme", "??????");
    }

    public void saveBlackThemeInShared() {
        preferenceManager = new PreferenceManager();
        preferenceManager.setString(((Shop) Shop.ctx).getApplication()
                , "myBlackTheme", "" + btnMent);
        Log.i("myBlackTheme ????????? ??????", "" + btnMent);

        editor.commit();
    }

    public void saveWhiteThemeInShared() {
        preferenceManager = new PreferenceManager();
        preferenceManager.setString(((Shop) Shop.ctx).getApplication()
                , "myWhiteTheme", "" + whiteBtnMent);
        Log.i("myWhiteTheme ????????? ??????", "" + whiteBtnMent);

        editor.commit();
    }

    public void saveAbleDThemeInShared() {
        preferenceManager = new PreferenceManager();
        preferenceManager.setString(((Shop) Shop.ctx).getApplication()
                , "myAbleDTheme", "" + ableDBtnMent);
        Log.i("myAbleDTheme ????????? ??????", "" + ableDBtnMent);

        editor.commit();
    }

    public void saveOriginalThemeInShared() {
        preferenceManager = new PreferenceManager();
        preferenceManager.setString(((Shop) Shop.ctx).getApplication()
                , "myOriginalTheme", "" + originalBtnMent);
        Log.i("myOriginalTheme ????????? ??????", "" + originalBtnMent);

        editor.commit();
    }

    public void bringBlackThemeInShared() {
        Log.i("bringBlackThemeInShared", "??????");
        if (btnMent.equals("??????")) {
            Log.i("myBlackTheme ????????? (X)", "" + btnMent);
        } else {
            Log.i("myBlackTheme ????????? (O)", "" + btnMent);
//            blackAndWhiteBuyBtn.setText(btnMent);

        }
    }

    public void bringWhiteThemeInShared() {
        Log.i("bringWhiteThemeInShared", "??????");
        if (whiteBtnMent.equals("??????")) {
            Log.i("myWhiteTheme ????????? (X)", "" + whiteBtnMent);
        } else {
            Log.i("myWhiteTheme ????????? (O)", "" + whiteBtnMent);
//            blackAndWhiteBuyBtn.setText(btnMent);

        }
    }

    public void bringAbleDThemeInShared() {
        Log.i("bringAbleDThemeInShared", "??????");
        if (ableDBtnMent.equals("??????")) {
            Log.i("myAbleDTheme ????????? (X)", "" + ableDBtnMent);
        } else {
            Log.i("myAbleDTheme ????????? (O)", "" + ableDBtnMent);
//            blackAndWhiteBuyBtn.setText(btnMent);

        }
    }

    public void bringOriginalThemeInShared() {
        Log.i("bringOriginalThemeInShared", "?????? ??????");
        if (originalBtnMent.equals("??????")) {
            Log.i("myOriginalTheme ????????? (X)", "" + originalBtnMent);
        } else {
            Log.i("myOriginalTheme ????????? (O)", "" + originalBtnMent);
//            blackAndWhiteBuyBtn.setText(btnMent);

        }
    }

    public void bringGoldInShared() {
        Log.i("bringGoldInShared", "");
        myGold = shared.getString("myGold", "0");
        gold = Integer.parseInt(myGold);
        if (gold == 0) {
            Log.i("myGold ????????? (X)", "" + gold);
        } else {
            try {
                Log.i("myGold ????????? (O)", "" + gold);
//                myGold = String.valueOf(goldPlus);
                shopGold.setText("??? ?????? : " + gold);
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
        Log.i("myGold ????????? ??????", "" + gold);

        editor.commit();
    }
}