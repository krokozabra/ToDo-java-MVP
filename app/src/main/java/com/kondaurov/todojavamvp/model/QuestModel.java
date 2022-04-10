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
                .subscribe(observerAdd);
    }

    Observer<ToDoData> observerAdd = new Observer<ToDoData>() {

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

    @SuppressLint("CheckResult")
    public void getQuest(int id, int every, Observer<ToDoData> observerGet) {
        Observable.just(getDBQuest(id, every))
                .subscribeOn(Schedulers.newThread())
                .subscribe(observerGet);
    }


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

    private ToDoData getDBQuest(int id, int everyday) {

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ToDoData currentQuest = new ToDoData();
        Cursor cursor;
        if (everyday == 0) {
            cursor = database.query(DBHelper.TABLE_TO_DO_LIST, null, DBHelper.ONE_KEY_ID + " = " + id, null, null, null, null);
            cursor.moveToFirst();

            int idIndex = cursor.getColumnIndex(DBHelper.ONE_KEY_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.ONE_NAME_TODO);
            int descIndex = cursor.getColumnIndex(DBHelper.ONE_DESCRIPTION_TODO);
            int dayIndex = cursor.getColumnIndex(DBHelper.ONE_DAY_TODO);
            int monthIndex = cursor.getColumnIndex(DBHelper.ONE_MONTH_TODO);
            int yearIndex = cursor.getColumnIndex(DBHelper.ONE_YEAR_TODO);
            int OKIndex = cursor.getColumnIndex(DBHelper.ONE_OK_TODO);

            System.out.println("cursor query = "+id);
            System.out.println("cursor size = "+cursor.getCount());

            System.out.println(cursor.getInt(idIndex));
            System.out.println(cursor.getString(nameIndex));
            System.out.println(cursor.getString(dayIndex));
            System.out.println(dayIndex);
            System.out.println(monthIndex);
            System.out.println(yearIndex);
            System.out.println(OKIndex);

            currentQuest = new ToDoData(cursor.getInt(idIndex), cursor.getString(nameIndex), cursor.getString(dayIndex), cursor.getString(monthIndex), cursor.getString(yearIndex), cursor.getString(descIndex), cursor.getInt(OKIndex), everyday);
        } else {
            //что то тут не чисто. подумать надо над логикой вывода из этого метода. может даже убрать вывод из ежедневной таблицы
            cursor = database.query(DBHelper.TABLE_EVERY_DAY_LIST, null, DBHelper.TWO_KEY_ID + " = " + id, null, null, null, null);

            cursor.moveToFirst();

            int idIndex = cursor.getColumnIndex(DBHelper.TWO_KEY_ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.TWO_NAME_TODO);
            int descIndex = cursor.getColumnIndex(DBHelper.TWO_DESCRIPTION_TODO);


            currentQuest = new ToDoData(cursor.getInt(idIndex), cursor.getString(nameIndex), "0", "0", "0", cursor.getString(descIndex), 0, everyday);
        }
        cursor.close();
        dbHelper.close();
        return currentQuest;
    }

    private void completeQuest(ToDoData quest) {

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

//в последствии добавить изменить квест, хорошо подойдёт для ежедневок, но сделать для всех