package com.example.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/*
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
*/

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GeographyActivity extends AppCompatActivity {


    ImageView mainIV;
    ArrayAdapter adapter;
    //private InterstitialAd interstitial;
    public static ArrayList imagesShuffled;
    private DataBaseHelper dataBaseHelper;
    private ArrayList<Question> questions;
    private ListView que_listView;
    TextView btn1;
    TextView btn2;
    TextView btn3;
    TextView btn4;
    private TextView timerShow;
    private TextView scoreShow;
    int currentQuestion;
    int correctAnswer;
    Drawable drawable;
    int playerScore = 0;
    CountDownTimer timer;
    int timerTime = 0;
    int timerCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geography);
        /*MobileAds.initialize(this, initializationStatus -> {});
                    interstitial = new InterstitialAd(this);
        //interstitial.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitial.loadAd(new AdRequest.Builder().build());
*/




        que_listView = findViewById(R.id.geography_listview);
        btn1 = findViewById(R.id.home_start_textview2);
        btn2 = findViewById(R.id.home_player_textview2);
        btn3 = findViewById(R.id.high_score_textview2);

        dataBaseHelper = new DataBaseHelper(this);
        questions = dataBaseHelper.getQuestionFromDb();

        registerForContextMenu(que_listView);

        updateViews();

        }

    private void updateViews() {
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, questions);
        que_listView.setAdapter(adapter);

    }


    
    /*private void initAdmobInterstitial(){
        // Create the interstitial.
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(getString(R.string.geo_challenge));
        // Create ad request.
        AdRequest adRequest = new AdRequest.Builder().build();
        // Begin loading your interstitial.
        interstitial.loadAd(adRequest);
    }*/

   /* // Invoke displayInterstitial() when you are ready to display an interstitial.
    public void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }*/
    @Override
    protected void onPause() {
        timer.cancel();
        super.onPause();
    }
   /* @Override
    protected void onResume() {
        super.onResume();
        timer.cancel();
        startTimer(timerTime - (timerCount*1000));
    }*/
    public void startQuiz(){
        //Get All the file names in an array
        currentQuestion = 0;
        createQuestion();
        startTimer(timerTime);
    }
    public void  startTimer(int time){
        timer = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                timerShow.setText(getString(R.string.timer_text) +" "+ millisUntilFinished / 1000);
                timerCount++;
            }
            public void onFinish() {
                timerShow.setText("done!");
                resultsPage();
            }
        }.start();
    }
    /**
     * Override the back button
     */
    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event)
    {
        if (keyCode== KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            if(!getString(R.string.geo_challenge).equals("")){
                //RevMob Full Screen Ad
                //displayInterstitial();
                timer.cancel();
                finish();
            }
            // return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void createQuestion(){
        int randomFileIndex;
        if(currentQuestion%10 == 0 ){ //&& interstitial.isLoaded()
            //displayInterstitial();
            //initAdmobInterstitial();
        }
        //picks a random number for the answer
        correctAnswer = 0 + (int)(Math.random() * ((3 - 0) + 1));
        //create an array of answers from file names
        ArrayList answers = new ArrayList();
        List categoryList = new ArrayList();
        for (int subArrayFlag = 0; subArrayFlag < imagesShuffled.size();subArrayFlag++){
            if(imagesShuffled.get(currentQuestion).toString().substring(0,1)
                    .equalsIgnoreCase(imagesShuffled.get(subArrayFlag).toString().substring(0, 1))){
                categoryList.add(subArrayFlag);
            }
        }
        //get 3 random answers and add it to the array
        for (int i = 0 ; i < 4 ;i++ ){
            if (i == correctAnswer){ answers.add(imagesShuffled.get(currentQuestion).toString());
            }else {
                do { randomFileIndex = (int) (Math.random() * categoryList.size()); }
                while ( categoryList.get(randomFileIndex).equals(currentQuestion) && categoryList.size() > 0 );
            answers.add(imagesShuffled.get((Integer) categoryList.get(randomFileIndex)).toString());
        }
        }
        try
        {
            // get input stream
            InputStream ims = getAssets().open(imagesShuffled.get(currentQuestion).toString());
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            mainIV.setImageDrawable(d);
        }
        catch(IOException ex)
        {
            return;
        }
        btn1.setText(answers.get(0).toString());
            /*btn2.setText(answers.get(1).substring(2));
            btn3.setText(answers.get(2).substring(2));
            btn4.setText(answers.get(3).substring(2));*/
        btn1.setBackgroundDrawable(drawable);
        btn2.setBackgroundDrawable(drawable);
        btn3.setBackgroundDrawable(drawable);
        //btn4.setBackgroundDrawable(drawable);
    }
    private void submitAnswer(int answer){
        if(answer == correctAnswer){
            currentQuestion++;
            playerScore++;
            if (currentQuestion == imagesShuffled.size()){
                //Show the dialog
                resultsPage();
            }else{
                switch (answer) {
                    case 0:
                        btn1.setBackgroundColor(Color.GREEN);
                        break;
                    case 1:
                        btn2.setBackgroundColor(Color.GREEN);
                        break;
                    case 2:
                        btn3.setBackgroundColor(Color.GREEN);
                        break;
                    case 3:
                        btn4.setBackgroundColor(Color.GREEN);
                        break;
                }
                final int finalAnswer = answer;
                // SLEEP 2 SECONDS HERE ...
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
//                        if (currentQuestion%2 == 0){
//                            displayInterstitial();
//
//                        }
                        createQuestion();
                    }
                }, 500);
            }
        }else{
            playerScore--;
            switch (answer) {
                case 0:
                    btn1.setBackgroundColor(Color.RED);
                    break;
                case 1:
                    btn2.setBackgroundColor(Color.RED);
                    break;
                case 2:
                    btn3.setBackgroundColor(Color.RED);
                    break;
                case 3:
                    btn4.setBackgroundColor(Color.RED);
                    break;
                default:
                    break;
            }
        }
        scoreShow.setText(getString(R.string.score_text)+playerScore);
    }
    private void resultsPage(){
        String msg = "";
        currentQuestion = 0;
        if(timerTime > timerCount){
            msg = "Congrats!";
        }
        timer.cancel();
        Intent resultIntent = new Intent(this , ResultsActivity.class);
        resultIntent.putExtra("EXTRA_PLAYER_SCORE", playerScore);
        resultIntent.putExtra("EXTRA_TIMER_COUNT", timerCount);
        resultIntent.putExtra("EXTRA_TIMER_TIME", timerTime);
        this.startActivity(resultIntent);
        finish();
    }
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
