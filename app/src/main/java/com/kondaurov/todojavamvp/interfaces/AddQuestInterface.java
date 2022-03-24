package com.kondaurov.todojavamvp.interfaces;

import android.view.View;

import com.kondaurov.todojavamvp.common.ToDoData;

import java.util.List;

public interface AddQuestInterface {


    ToDoData getNewQuest();
    void startOtherScreen(Class activity);
    void setDate();
    void showDate();
}
