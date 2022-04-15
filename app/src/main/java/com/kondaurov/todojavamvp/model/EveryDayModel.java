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

public class EveryDayModel {
    final static String LOG_TAG = "work width EveryDayModel";

    private final DBHelper dbHelper;

    public EveryDayModel(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void loadEvery(Observer<ToDoData> observer) {
        Observable.fromIterable(getEveryList())
                .subscribeOn(Schedulers.newThread())
                .subscribe(observer);
    }

    //получение списка ежедневок
    private ArrayList<ToDoData> getEveryList() {

        ArrayList<ToDoData> everys = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        Cursor cursor = database.query(DBHelper.TABLE_EVERY_DAY_LIST, null, null, null, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(DBHelper.TWO_KEY_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.TWO_NAME_TODO);
                int descIndex = cursor.getColumnIndex(DBHelper.TWO_DESCRIPTION_TODO);
                do {
                    ToDoData rec = new ToDoData(cursor.getInt(idIndex), cursor.getString(nameIndex), "0", "0", "0", cursor.getString(descIndex), 0, 1);
                    everys.add(rec);
                } while (cursor.moveToNext());
            } else
                Log.d("mainLog", "0 rows");
        } catch (Exception e) {
            Log.d("mainLog", "exept: " + e);
        }
        cursor.close();
        dbHelper.close();

        return everys;
    }
}
