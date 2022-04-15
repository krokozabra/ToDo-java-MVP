package com.kondaurov.todojavamvp.presenter;

import com.kondaurov.todojavamvp.common.ToDoData;
import com.kondaurov.todojavamvp.interfaces.EveryInterfase;
import com.kondaurov.todojavamvp.interfaces.ToDoInterface;
import com.kondaurov.todojavamvp.model.EveryDayModel;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class EveryDayPresenter {

    private EveryInterfase view;
    private final EveryDayModel model;
    private ArrayList<ToDoData> everyList = new ArrayList<>();

    public EveryDayPresenter(EveryDayModel model) {
        this.model = model;
    }

    public void attachView(EveryInterfase view) {
        this.view = view;
    }

    public void detachView() {
        view = null;
    }

    public void viewIsReady() {
        loadList();
    }

    public void loadList() {
        //RxJava
        model.loadEvery(observer);

    }

    public void startAddQuest(Class activity)
    {
        view.startOtherScreen(activity);
    }

    Observer<ToDoData> observer = new Observer<ToDoData>() {

        @Override
        public void onSubscribe(Disposable d) {
            System.out.println("onSubscribe: ");
            everyList.clear();
        }

        @Override
        public void onNext(ToDoData toDoData) {
            everyList.add(toDoData);

        }

        @Override
        public void onError(Throwable e) {
            System.out.println("onError: " + e);
        }

        @Override
        public void onComplete() {
            System.out.println("onComplete: All Done!");
            view.showList(everyList);
        }
    };
}
