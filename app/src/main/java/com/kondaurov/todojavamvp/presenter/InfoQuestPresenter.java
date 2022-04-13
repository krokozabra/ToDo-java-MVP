package com.kondaurov.todojavamvp.presenter;

import com.kondaurov.todojavamvp.common.ToDoData;
import com.kondaurov.todojavamvp.interfaces.InfoQuestInterface;
import com.kondaurov.todojavamvp.model.QuestModel;
import com.kondaurov.todojavamvp.view.MainActivity;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class InfoQuestPresenter {
    private InfoQuestInterface view;
    private final QuestModel model;

    public InfoQuestPresenter(QuestModel model) {
        this.model = model;
    }

    public void attachView(InfoQuestInterface view) {
        this.view = view;
    }

    public void detachView() {
        view = null;
    }

    Observer<ToDoData> observer = new Observer<ToDoData>() {

        @Override
        public void onSubscribe(Disposable d) {
            System.out.println("onSubscribe: ");
        }

        @Override
        public void onNext(ToDoData toDoData) {
            view.showQuest(toDoData);

        }

        @Override
        public void onError(Throwable e) {
            System.out.println("onError: " + e);
        }

        @Override
        public void onComplete() {
            System.out.println("onComplete: All Done!");
        }
    };

    public void viewIsReady() {
        //загрузка текущего таска
        model.getQuest(view.getID(), view.getEvery(), observer);
    }

    public void deleteQuest()
    {
        //вызов метода view которые собирает данные и возвращает их сюда
        ToDoData delQuest = view.getCurrentQuest();
        //вызов метода модели с передачей в него нового квеста
        model.deleteQuest(delQuest);

        view.startOtherScreen(MainActivity.class);
    }

    public void completeQuest()
    {
        //вызов метода view которые собирает данные и возвращает их сюда
        ToDoData complQuest = view.getCurrentQuest();
        complQuest.setOK(1);
        model.completeQuest(complQuest);

        view.startOtherScreen(MainActivity.class);
    }



}
