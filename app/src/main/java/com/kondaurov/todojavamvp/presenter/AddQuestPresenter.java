package com.kondaurov.todojavamvp.presenter;

import com.kondaurov.todojavamvp.common.ToDoData;
import com.kondaurov.todojavamvp.interfaces.AddQuestInterface;
import com.kondaurov.todojavamvp.interfaces.ToDoInterface;
import com.kondaurov.todojavamvp.model.QuestModel;
import com.kondaurov.todojavamvp.model.ToDoModel;
import com.kondaurov.todojavamvp.view.MainActivity;

public class AddQuestPresenter {

    private AddQuestInterface view;
    private final QuestModel model;

    public AddQuestPresenter(QuestModel model) {
        this.model = model;
    }

    public void attachView(AddQuestInterface view) {
        this.view = view;
    }

    public void detachView() {
        view = null;
    }

    public void viewIsReady() {
        //пока не знаю, какой метод запускать, если это вообще нужно
    }

    public void clickDate()
    {
        view.setDate();
    }

    public void addNewQuest()
    {
        //вызов метода view которые собирает данные и возвращает их сюда
        ToDoData newQuest = view.getNewQuest();
        //вызов метода модели с передачей в него нового квеста
        model.addQuest(newQuest);

        view.startOtherScreen(MainActivity.class);
    }
}
