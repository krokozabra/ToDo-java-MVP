package com.kondaurov.todojavamvp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.kondaurov.todojavamvp.R;
import com.kondaurov.todojavamvp.common.ToDoAdapter;
import com.kondaurov.todojavamvp.common.ToDoData;
import com.kondaurov.todojavamvp.database.DBHelper;
import com.kondaurov.todojavamvp.interfaces.ToDoInterface;
import com.kondaurov.todojavamvp.model.ToDoModel;
import com.kondaurov.todojavamvp.presenter.ToDoPresenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ToDoInterface, SwipeRefreshLayout.OnRefreshListener {

    private ToDoPresenter presenter;

    ListView list;

    ArrayList<ToDoData> toDoList = new ArrayList<>();
    ToDoAdapter toDoAdapter;

    SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    public void onRefresh() {
        toDoList.clear();
        list.setAdapter(null);
        //засунуть метод опроса библии через презентера
        mSwipeRefreshLayout.setRefreshing(false); // останавливает анимацию загрузки

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dots_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add: {
                presenter.addQuest(AddQuestView.class);
                break;
            }
            case R.id.action_every: {
                //presenter
                break;
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init()
    {
        setTitle("Квесты на сегодня");

        list = findViewById(R.id.am_list);
        toDoAdapter = new ToDoAdapter(this, toDoList);



        DBHelper dbHelper = new DBHelper(this);
        ToDoModel toDoModel = new ToDoModel(dbHelper);
        presenter = new ToDoPresenter(toDoModel);
        presenter.attachView(this);
        presenter.viewIsReady();
    }

    @Override
    public void showUsers(List<ToDoData> listToDo) {
        toDoList.clear();
        toDoList.addAll(listToDo);
        //по идее нужно оставить только setAdapter
        //для этого нужно изменить ToDoAdapter
        list.setAdapter(toDoAdapter);
    }

    @Override
    public void startOtherScreen(Class activity) {
        if (activity == AddQuestView.class)
            AddQuestView.start(this);
        finish();
    }

    public void openEverydayScreen() {
        EverydayActivity.start(this);
        finish();
    }
}