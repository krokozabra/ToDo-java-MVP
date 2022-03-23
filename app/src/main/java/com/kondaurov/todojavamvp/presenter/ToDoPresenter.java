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
        loadUsers();
    }

    public void loadUsers() {
        //RxJava
        model.loadToDo(observer);

    }

    public void addQuest(Class activity){
        view.startOtherScreen(activity);
    }

    Observer<ArrayList<ToDoData>> observer = new Observer<ArrayList<ToDoData>>() {

        @Override
        public void onSubscribe(Disposable d) {
            System.out.println("onSubscribe: ");

        }

        @Override
        public void onNext(ArrayList<ToDoData> toDoData) {
            for (ToDoData x : toDoData) {
                System.out.println(x.getId() + " " + x.getName() + " " + x.getOK());
            }
            view.showUsers(toDoData);

        }

        @Override
        public void onError(Throwable e) {
            System.out.println("onError: ");
        }

        @Override
        public void onComplete() {
            System.out.println("onComplete: All Done!");
        }
    };
}
