package com.kondaurov.todojavamvp.presenter;

import com.kondaurov.todojavamvp.common.ToDoData;
import com.kondaurov.todojavamvp.interfaces.ToDoInterface;
import com.kondaurov.todojavamvp.model.ToDoModel;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ToDoPresenter {

    private ToDoInterface view;
    private final ToDoModel model;
    private ArrayList<ToDoData> toDoList = new ArrayList<>();


    public ToDoPresenter(ToDoModel model) {
        this.model = model;
    }

    public void attachView(ToDoInterface view) {
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
        model.loadToDo(observer);

    }

    public void startAddQuest(Class activity)
    {
        view.startOtherScreen(activity);
    }

    Observer<ToDoData> observer = new Observer<ToDoData>() {

        @Override
        public void onSubscribe(Disposable d) {
            System.out.println("onSubscribe: ");
            toDoList.clear();
        }

        @Override
        public void onNext(ToDoData toDoData) {
            toDoList.add(toDoData);

        }

        @Override
        public void onError(Throwable e) {
            System.out.println("onError: " + e);
        }

        @Override
        public void onComplete() {
            System.out.println("onComplete: All Done!");
            view.showList(toDoList);
        }
    };
}
