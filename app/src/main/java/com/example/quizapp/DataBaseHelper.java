package com.example.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {


    private static final String QUESTION_TABLE = "question";
    private static final String QUESTION_NAME = "QUESTION_NAME";
    private static final String ANSWER1 = "ANSWER1";
    private static final String ANSWER2 = "ANSWER2";
    private static final String ANSWER3 = "ANSWER3";
    private static final String CORRECT_ANSWER = "CORRECT_ANSWER";
    private static SQLiteDatabase db;
    public DataBaseHelper(@Nullable Context context) {
        super(context, "Quiz.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE  " + QUESTION_TABLE + "  ( ID INTEGER PRIMARY KEY AUTOINCREMENT, " + QUESTION_NAME + " TEXT, "
                + ANSWER1 + " TEXT, "+ ANSWER2 + " TEXT, "+ ANSWER3 + " TEXT, " + CORRECT_ANSWER + " TEXT ) ";
        db.execSQL(createTable);
        addQuestionToDB(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public boolean addQuestion(Question question,SQLiteDatabase db) {
        // SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUESTION_NAME, question.getQuestionName());
        contentValues.put(ANSWER1, question.getAnswer1());
        contentValues.put(ANSWER2, question.getAnswer2());
        contentValues.put(ANSWER3, question.getAnswer3());
        contentValues.put(CORRECT_ANSWER, question.getRightAnswer());
        long insert = db.insert(QUESTION_TABLE, null, contentValues);
        if (insert == -1) {
            return false;
        }
        return true;
    }
    public void addQuestionToDB(SQLiteDatabase db){
        String ans1 = "India";
        String ans2 = "South America";
        String ans3 = "Africa";
        Question question1 = new Question(0,"  Where is the Amazon rainforest?  ",ans1, ans2, ans3, ans2);
        addQuestion(question1,db);
        ans1 = "The Tropic Line";
        ans2  = ("The Worldline");
        ans3 = ("The Equator");
        Question question2 = new Question(1,"  What do you call the line that runs all the way round the world?  ",ans1, ans2,ans3 , ans3);
        addQuestion(question2,db);
        ans1 = "Pugua New GoobBoy";
        ans2 = "Turkmenistan";
        ans3 = "Djibouti";
        Question question3 = new Question(2,"  Which of these ISN'T a real country?  ",ans1, ans2, ans3 , ans1);
        addQuestion(question3,db);
        ans1 = "Australia";
        ans2 = "Canada";
        ans3  = "Sudan";
        Question question4 = new Question(3," Which of these countries is the biggest?  ",ans1,ans2,ans3 , ans2);
        addQuestion(question4,db);
        ans1   = "Africa";
        ans2 = "North America";
        ans3 = "Asia";
        Question question5 = new Question(4,"  Where is Kazakhstan?  ",ans1, ans2, ans3 , ans3);
        addQuestion(question5,db);
    }
    public ArrayList<Question> getQuestionFromDb(){
         SQLiteDatabase db = this.getReadableDatabase();
        ArrayList <Question> questions = new ArrayList<>();
        String getQuestion = "SELECT * FROM " +  QUESTION_TABLE;
        Cursor cursor = db.rawQuery(getQuestion,null);
        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String questionName = cursor.getString(1);
                String answer1 = cursor.getString(2);
                String answer2 = cursor.getString(3);
                String answer3 = cursor.getString(4);
                String rightAnswer = cursor.getString(5);
                Question question = new Question(id,id+" "+questionName, answer1,answer2, answer3,rightAnswer);
                questions.add(question);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return questions;
    }
}
