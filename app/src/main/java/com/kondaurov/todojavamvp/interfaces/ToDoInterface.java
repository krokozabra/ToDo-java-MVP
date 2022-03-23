package com.kondaurov.todojavamvp.interfaces;

import android.app.Activity;

import com.kondaurov.todojavamvp.common.ToDoData;

import java.util.List;

public interface ToDoInterface {

    void showUsers(List<ToDoData> listToDo);

    void startOtherScreen(Class activity);
}
