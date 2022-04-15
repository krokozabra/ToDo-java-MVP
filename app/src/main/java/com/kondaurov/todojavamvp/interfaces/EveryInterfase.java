package com.kondaurov.todojavamvp.interfaces;

import com.kondaurov.todojavamvp.common.ToDoData;

import java.util.List;

public interface EveryInterfase {
    void showList(List<ToDoData> listToDo);

    void startOtherScreen(Class activity);
}
