package com.kondaurov.todojavamvp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.kondaurov.todojavamvp.interfaces.EveryInterfase;
import com.kondaurov.todojavamvp.model.EveryDayModel;
import com.kondaurov.todojavamvp.presenter.EveryDayPresenter;

import java.util.ArrayList;
import java.util.List;

public class EverydayActivity extends AppCompatActivity implements EveryInterfase {

    private EveryDayPresenter presenter;

    ListView list;
    ArrayList<ToDoData> toDoList = new ArrayList<>();
    ToDoAdapter toDoAdapter;

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, EverydayActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dots_every_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_every_add: {
                presenter.startAddQuest(AddQuestView.class);
                break;
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_everyday);

        init();
    }

    private void init()
    {
        setTitle("Квесты на сегодня");

        list = findViewById(R.id.ae_list);
        toDoAdapter = new ToDoAdapter(this, toDoList);

        DBHelper dbHelper = new DBHelper(this);
        EveryDayModel everyDayModel = new EveryDayModel(dbHelper);
        presenter = new EveryDayPresenter(everyDayModel);
        presenter.attachView(this);
        presenter.viewIsReady();
    }

    @Override
    public void showList(List<ToDoData> listToDo) {
        toDoList.clear();
        toDoList.addAll(listToDo);
        //прочитать про runOnUiThread
        runOnUiThread(() -> list.setAdapter(toDoAdapter));
    }

    @Override
    public void startOtherScreen(Class activity) {
        if (activity == AddQuestView.class)
            AddQuestView.start(this);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}