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


    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }


    @Override
    public void onRefresh() {
        toDoList.clear();
        list.setAdapter(null);
        presenter.loadList();
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
                presenter.startAddQuest(AddQuestView.class);
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

        mSwipeRefreshLayout = findViewById(R.id.am_swipe);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        list = findViewById(R.id.am_list);
        toDoAdapter = new ToDoAdapter(this, toDoList);

        DBHelper dbHelper = new DBHelper(this);
        ToDoModel toDoModel = new ToDoModel(dbHelper);
        presenter = new ToDoPresenter(toDoModel);
        presenter.attachView(this);
        presenter.viewIsReady();
    }

    @Override
    public void showList(List<ToDoData> listToDo) {
        toDoList.clear();
        toDoList.addAll(listToDo);
        //прочитать про runOnUiThread
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                list.setAdapter(toDoAdapter);
            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}