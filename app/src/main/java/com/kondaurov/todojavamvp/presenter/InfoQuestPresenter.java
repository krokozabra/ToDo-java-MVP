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

    }

    public void completeQuest()
    {

    }



}
