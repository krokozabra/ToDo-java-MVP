package com.kondaurov.todojavamvp.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kondaurov.todojavamvp.common.ToDoData;
import com.kondaurov.todojavamvp.database.DBHelper;

import java.util.ArrayList;
import java.util.Calendar;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class QuestModel {

    final static String LOG_TAG = "work width QuestModel";
    private final DBHelper dbHelper;


    public QuestModel(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @SuppressLint("CheckResult")
    public void addQuest(ToDoData newQuest) {
        Observable.just(newQuest)
                .subscribeOn(Schedulers.newThread())
                .subscribe(observer);
    }

    Observer<ToDoData> observer = new Observer<ToDoData>() {

        @Override
        public void onSubscribe(Disposable d) {
            System.out.println("onSubscribe: ");

        }

        @Override
        public void onNext(ToDoData toDoData) {
            addNewQuest(toDoData);
        }

        @Override
        public void onError(Throwable e) {
            System.out.println("onError: ");
        }

        @Override
        public void onComplete() {
            System.out.println("onComplete: All Done!");
        }
    };

    //Добавление нового квеста
    //добавить в выполнение в observer
    private void addNewQuest(ToDoData quest) {

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        if (quest.getEveryday() == 1) {
            contentValues.put(DBHelper.TWO_NAME_TODO, quest.getName());
            contentValues.put(DBHelper.TWO_DESCRIPTION_TODO, quest.getDescription());
            database.insert(DBHelper.TABLE_EVERY_DAY_LIST, null, contentValues);
        } else {
            contentValues.put(DBHelper.ONE_NAME_TODO, quest.getName());
            contentValues.put(DBHelper.ONE_DESCRIPTION_TODO, quest.getDescription());
            contentValues.put(DBHelper.ONE_DAY_TODO, quest.getDay());
            contentValues.put(DBHelper.ONE_MONTH_TODO, quest.getMonth());
            contentValues.put(DBHelper.ONE_YEAR_TODO, quest.getYear());
            contentValues.put(DBHelper.ONE_OK_TODO, 0);
            database.insert(DBHelper.TABLE_TO_DO_LIST, null, contentValues);
        }
        dbHelper.close();

    }




}
