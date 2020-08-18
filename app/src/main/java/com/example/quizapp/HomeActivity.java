package com.example.quizapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class HomeActivity extends Activity {

    public static final String PLAYER_NAME = "better_life";
    public static final String PLAYER_HIGH_SCORE = "playerHighScore";

    TextView startPlay;
    TextView highScore;
    TextView homePlayer;
    ImageView mainBackground;

    public static ArrayList images;
    public static int playerHighScore= 0;
    //private InterstitialAD interstitial;


    SharedPreferences storage;
    ConstraintLayout mainLayout;
    static int difficulty;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //getActionBar().hide();


        mainLayout = findViewById(R.id.home_main_layout);
        startPlay = findViewById(R.id.home_start_textview);
        highScore = findViewById(R.id.high_score_textview);
        homePlayer = findViewById(R.id.home_player_textview);
        mainBackground = findViewById(R.id.home_background_image);

        //Restore preferences
        storage = getSharedPreferences(PLAYER_NAME, MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = storage.edit();

        //set difficulty
        difficulty = Integer.parseInt(String.valueOf(R.string.difficulty_index));
        //initAdmobInterstitial();

        // Set the date of the first launch
        Long firstLaunchDate = storage.getLong("firstLaunchDate", 0);
        if (firstLaunchDate == 0) {
            firstLaunchDate = System.currentTimeMillis();
            editor.putLong("firstLaunchDate", firstLaunchDate);
            editor.commit();
        }
        ImageItem img;
        images = new ArrayList();
        //list images and them to the images array
        try {
            String[] files = getAssets().list("aimages");
            for (String file : files) {
                img = new ImageItem();
                //i3.setName("Love");
                String[] parts = file.split("\\.");
                img.setName(parts[0].replaceAll("-", " "));
                img.setPath("aimages/" + file);
                images.add(img);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Handler to update UI after the backend thread
        playerHighScore = storage.getInt(PLAYER_HIGH_SCORE, 0);
        highScore.setText(String.valueOf(playerHighScore * 1000) + "XP");
        startPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initNewGame();
            }
        });
        homePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuFunctions.openMore(context);
            }
        });

         /* try {
            //picks a random number for the home background
            int randomBackground = 0 + (int)(Math.random() * ((images.size() - 1) + 1));
            // get input stream
          InputStream ims = getAssets().open(images.get(randomBackground).toString());
            // load image as Drawable
            //Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            // mainBackground.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initAdmobInterstitial(){
        // Create the interstitial.
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(getString(R.string.admob_challenge_interstitial));
        // Create ad request.
        AdRequest adRequest = new AdRequest.Builder().build();
        // Begin loading your interstitial.
        interstitial.loadAd(adRequest);
        interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                finish();
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
            }
        });
    }
    // Invoke displayInterstitial() when you are ready to display an interstitial.
    /*public void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }*/
    }
    private void initNewGame(){
        Intent geographyIntent = new Intent(this , GeographyActivity.class);
        geographyIntent.putExtra("EXTRA_DIFFICULTY", difficulty);
        this.startActivity(geographyIntent);
    }
   /* *//**
     * Override the back button
     *//*
    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event)
    {
        if (keyCode== KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            if(!getString(R.string.geo_player).equals("")){
                //RevMob Full Screen Ad
               // displayInterstitial();
            }
            // return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        /*if (id == R.id.action_settings) {
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

        }