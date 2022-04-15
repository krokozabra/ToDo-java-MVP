package com.kondaurov.todojavamvp.model;

import android.content.ContentValues;
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

    final static String LOG_TAG = "work width ToDoModel";

    private final DBHelper dbHelper;

    public ToDoModel(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void loadToDo(Observer<ToDoData> observer) {
        Observable.fromIterable(getToDoList())
                .subscribeOn(Schedulers.newThread())
                .subscribe(observer);
    }

    //получение списка заданий на сегодняшний день
    private ArrayList<ToDoData> getToDoList() {
        //проверка на ежедневные задания сегодня
        checkEverydayInTask();

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

    //проверка недостающих ежедневок
    public void checkEverydayInTask() {
        Calendar today = Calendar.getInstance();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor;

        //сделать вычитание таблиц

        Log.d(LOG_TAG, "---INNER JOIN with query---");
        String table = DBHelper.TABLE_TO_DO_LIST + " as t, " + DBHelper.TABLE_EVERY_DAY_LIST + " as e ";
        String[] columns = {"*"};
        String selection = "(" +
                "t." + DBHelper.ONE_DAY_TODO + " = " + today.get(Calendar.DAY_OF_MONTH) + " AND " +
                "t." + DBHelper.ONE_MONTH_TODO + " = " + today.get(Calendar.MONTH) + " AND " +
                "t." + DBHelper.ONE_YEAR_TODO + " = " + today.get(Calendar.YEAR) + " AND " +
                "t." + DBHelper.ONE_NAME_TODO + " = e." + DBHelper.TWO_NAME_TODO + " AND " +
                "t." + DBHelper.ONE_DESCRIPTION_TODO + " = e." + DBHelper.TWO_DESCRIPTION_TODO + ")";
        cursor = database.query(table, columns, selection, null, null, null, null);
        int countEveryInToDo = cursor.getCount();
        cursor.close();

        cursor = database.query(DBHelper.TABLE_EVERY_DAY_LIST, null, null, null, null, null, null);
        int countEveryDay = cursor.getCount();

        cursor.close();

        if (countEveryInToDo != countEveryDay) {
            insertInToDo();
        }

        dbHelper.close();

    }

    public void insertInToDo() {

        Calendar today = Calendar.getInstance();
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String intersectQuery = "SELECT name, description FROM everyDayList " +
                "EXCEPT " +
                "SELECT name, description FROM todoList WHERE" +
                "(" +
                DBHelper.ONE_DAY_TODO + " = " + today.get(Calendar.DAY_OF_MONTH) + " AND " +
                DBHelper.ONE_MONTH_TODO + " = " + today.get(Calendar.MONTH) + " AND " +
                DBHelper.ONE_YEAR_TODO + " = " + today.get(Calendar.YEAR) + ")";

        Cursor cursorEveryDay = database.rawQuery(intersectQuery, null);

        int nameIndexEvery = cursorEveryDay.getColumnIndex(DBHelper.TWO_NAME_TODO);
        int descIndexEvery = cursorEveryDay.getColumnIndex(DBHelper.TWO_DESCRIPTION_TODO);


        if (cursorEveryDay.moveToFirst())
            do {
                ContentValues cv = new ContentValues();
                try {

                    cv.put(DBHelper.ONE_NAME_TODO, cursorEveryDay.getString(nameIndexEvery));
                    cv.put(DBHelper.ONE_DESCRIPTION_TODO, cursorEveryDay.getString(descIndexEvery));
                    cv.put(DBHelper.ONE_DAY_TODO, today.get(Calendar.DAY_OF_MONTH));
                    cv.put(DBHelper.ONE_MONTH_TODO, today.get(Calendar.MONTH));
                    cv.put(DBHelper.ONE_YEAR_TODO, today.get(Calendar.YEAR));
                    cv.put(DBHelper.ONE_OK_TODO, 0);

                    database.insert(DBHelper.TABLE_TO_DO_LIST, null, cv);

                } catch (Exception e) {
                    Log.d("mainLog", "exept: " + e);
                }
            } while (cursorEveryDay.moveToNext());

        cursorEveryDay.close();
        dbHelper.close();
    }
}
