package com.kondaurov.todojavamvp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.kondaurov.todojavamvp.R;
import com.kondaurov.todojavamvp.database.DBHelper;
import com.kondaurov.todojavamvp.model.QuestModel;
import com.kondaurov.todojavamvp.model.ToDoModel;
import com.kondaurov.todojavamvp.presenter.AddQuestPresenter;
import com.kondaurov.todojavamvp.presenter.ToDoPresenter;

public class AddQuestView extends AppCompatActivity {

    private AddQuestPresenter presenter;

    Button addQuestBTN;
    EditText nameET, decriptionET, dateET;
    CheckBox everyDayCB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quest_view);

        init();
    }

    private void init()
    {
        addQuestBTN = findViewById(R.id.aaqv_btn_add);
        nameET = findViewById(R.id.aaqv_et_name);
        decriptionET = findViewById(R.id.aaqv_et_decription);
        dateET = findViewById(R.id.aaqv_et_date);
        everyDayCB = findViewById(R.id.aaqv_cb_every_day);

        addQuestBTN.setOnClickListener(v ->
        {
            //метод презентера
        });

        everyDayCB.setOnClickListener(v ->
        {
            if(everyDayCB.isChecked())
                //скорее всего сюда надо отправить ещё один метод презентера и там отмечать флаг
                dateET.setVisibility(View.INVISIBLE);
            else
                //скорее всего сюда надо отправить ещё один метод презентера и там отмечать флаг
                dateET.setVisibility(View.VISIBLE);
        });

        dateET.setOnClickListener(view ->
        {
            //метод презентера
        });

        DBHelper dbHelper = new DBHelper(this);
        QuestModel questModel = new QuestModel(dbHelper);
        presenter = new ToDoPresenter(questModel);
        presenter.attachView(this);
        presenter.viewIsReady();

    }

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, AddQuestView.class);
        activity.startActivity(intent);
    }
}