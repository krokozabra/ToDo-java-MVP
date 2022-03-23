package com.kondaurov.todojavamvp.model;

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

public class ToDoModel {

    private final DBHelper dbHelper;

    public ToDoModel(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void loadToDo(Observer<ArrayList<ToDoData>> observer)
    {
        Observable.fromArray(getToDoList())
                .subscribeOn(Schedulers.newThread())
                .subscribe(observer);
    }

    //полусение списка заданий на сегодняшний день
    private ArrayList<ToDoData> getToDoList() {
        ArrayList<ToDoData> todos = new ArrayList<>();
        Calendar today = Calendar.getInstance();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Log.d("mainLog", "today = " + today.get(Calendar.DAY_OF_MONTH));

        Cursor cursor = database.query(DBHelper.TABLE_TO_DO_LIST, null, DBHelper.ONE_DAY_TODO + " = " + today.get(Calendar.DAY_OF_MONTH) + " AND " + DBHelper.ONE_MONTH_TODO + " = " + today.get(Calendar.MONTH) + " AND " + DBHelper.ONE_YEAR_TODO + " = " + today.get(Calendar.YEAR), null, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(DBHelper.ONE_KEY_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.ONE_NAME_TODO);
                int descIndex = cursor.getColumnIndex(DBHelper.ONE_DESCRIPTION_TODO);
                int dayIndex = cursor.getColumnIndex(DBHelper.ONE_DAY_TODO);
                int monthIndex = cursor.getColumnIndex(DBHelper.ONE_MONTH_TODO);
                int yearIndex = cursor.getColumnIndex(DBHelper.ONE_YEAR_TODO);
                int OKIndex = cursor.getColumnIndex(DBHelper.ONE_OK_TODO);
                do {
                    ToDoData rec = new ToDoData(cursor.getInt(idIndex), cursor.getString(nameIndex), cursor.getString(dayIndex), cursor.getString(monthIndex), cursor.getString(yearIndex), cursor.getString(descIndex), cursor.getInt(OKIndex), 0);
                    todos.add(rec);
                } while (cursor.moveToNext());
            } else
                Log.d("mainLog", "0 rows");
        } catch (Exception e) {
            Log.d("mainLog", "exept: " + e);
        }
        cursor.close();
        dbHelper.close();

        return todos;
    }
}
