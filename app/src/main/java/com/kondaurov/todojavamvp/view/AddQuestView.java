package com.kondaurov.todojavamvp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.kondaurov.todojavamvp.R;
import com.kondaurov.todojavamvp.common.ToDoData;
import com.kondaurov.todojavamvp.database.DBHelper;
import com.kondaurov.todojavamvp.interfaces.AddQuestInterface;
import com.kondaurov.todojavamvp.model.QuestModel;
import com.kondaurov.todojavamvp.model.ToDoModel;
import com.kondaurov.todojavamvp.presenter.AddQuestPresenter;
import com.kondaurov.todojavamvp.presenter.ToDoPresenter;

import java.util.Calendar;

public class AddQuestView extends AppCompatActivity implements AddQuestInterface {

    private AddQuestPresenter presenter;

    Button addQuestBTN;
    EditText nameET, descriptionET, dateET;
    CheckBox everyDayCB;

    Calendar dateExec= Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quest_view);

        init();
    }

    private void init() {
        addQuestBTN = findViewById(R.id.aaqv_btn_add);
        nameET = findViewById(R.id.aaqv_et_name);
        descriptionET = findViewById(R.id.aaqv_et_decription);
        dateET = findViewById(R.id.aaqv_et_date);
        everyDayCB = findViewById(R.id.aaqv_cb_every_day);

        addQuestBTN.setOnClickListener(v ->
        {
            //метод презентера
            presenter.addNewQuest();
        });

        everyDayCB.setOnClickListener(v ->
        {
            if (everyDayCB.isChecked())
                //скорее всего сюда надо отправить ещё один метод презентера и там отмечать флаг
                dateET.setVisibility(View.INVISIBLE);
            else
                //скорее всего сюда надо отправить ещё один метод презентера и там отмечать флаг
                dateET.setVisibility(View.VISIBLE);
        });

        dateET.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                presenter.clickDate();
            }
        });

        dateET.setOnClickListener(v ->
        {
            presenter.clickDate();
        });

        DBHelper dbHelper = new DBHelper(this);
        QuestModel questModel = new QuestModel(dbHelper);
        presenter = new AddQuestPresenter(questModel);
        presenter.attachView(this);
        presenter.viewIsReady();

    }

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, AddQuestView.class);
        activity.startActivity(intent);
    }

    @Override
    public ToDoData getNewQuest() {
        ToDoData newQuest = new ToDoData();
        newQuest.setName(nameET.getText().toString());
        newQuest.setDescription(descriptionET.getText().toString());
        newQuest.setEveryday( (everyDayCB.isChecked()) ? 1 : 0);
        newQuest.setDay(Integer.toString(dateExec.get(Calendar.DAY_OF_MONTH)));
        newQuest.setMonth(Integer.toString(dateExec.get(Calendar.MONTH)));
        newQuest.setYear(Integer.toString(dateExec.get(Calendar.YEAR)));

        return newQuest;
    }

    @Override
    public void startOtherScreen(Class activity) {
        if (activity == MainActivity.class)
            MainActivity.start(this);
        finish();
    }

    // отображаем диалоговое окно для выбора даты
    public void setDate() {
        new DatePickerDialog(this, d,
                dateExec.get(Calendar.YEAR),
                dateExec.get(Calendar.MONTH),
                dateExec.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // вывод на экран даты
    public void showDate() {
        dateET.setText(DateUtils.formatDateTime(this,
                dateExec.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }


    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d= (view, year, monthOfYear, dayOfMonth) -> {
        dateExec.set(Calendar.YEAR, year);
        dateExec.set(Calendar.MONTH, monthOfYear);
        dateExec.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        showDate();
    };
}