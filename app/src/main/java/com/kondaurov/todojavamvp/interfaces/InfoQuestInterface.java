package com.kondaurov.todojavamvp.interfaces;

import com.kondaurov.todojavamvp.common.ToDoData;

public interface InfoQuestInterface {

    int getID();
    int getEvery();

    void showQuest(ToDoData quest);
    void startOtherScreen(Class activity);
    void deleteQuest();
    void completeQuest();
}
