package com.kondaurov.todojavamvp.model;

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
import io.reactivex.schedulers.Schedulers;

public class QuestModel {

    private final DBHelper dbHelper;

    public QuestModel(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void addQuest(Observer<ArrayList<ToDoData>> observer)
    {
       /* Observable.fromArray(addNewQuest())
                .subscribeOn(Schedulers.newThread())
                .subscribe(observer);*/
    }

    //Добавление нового квеста
    /*private Void addNewQuest(ToDoData quest) {
        String name = etName.getText().toString();
        String description = etDecription.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        if(cbEveryDay.isChecked()) {
            contentValues.put(DBHelper.TWO_NAME_TODO, name);
            contentValues.put(DBHelper.TWO_DESCRIPTION_TODO, description);
            database.insert(DBHelper.TABLE_EVERY_DAY_LIST, null, contentValues);
        }
        else {
            contentValues.put(DBHelper.ONE_NAME_TODO, name);
            contentValues.put(DBHelper.ONE_DESCRIPTION_TODO, description);
            contentValues.put(DBHelper.ONE_DAY_TODO, dateExec.get(Calendar.DAY_OF_MONTH));
            contentValues.put(DBHelper.ONE_MONTH_TODO, dateExec.get(Calendar.MONTH));
            contentValues.put(DBHelper.ONE_YEAR_TODO, dateExec.get(Calendar.YEAR));
            contentValues.put(DBHelper.ONE_OK_TODO, 0);
            database.insert(DBHelper.TABLE_TO_DO_LIST, null, contentValues);
        }
        dbHelper.close();

        //переход на новый экран

    }*/
}
