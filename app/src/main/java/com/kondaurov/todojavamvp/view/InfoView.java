package com.kondaurov.todojavamvp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.kondaurov.todojavamvp.R;
import com.kondaurov.todojavamvp.common.ToDoData;
import com.kondaurov.todojavamvp.database.DBHelper;
import com.kondaurov.todojavamvp.interfaces.InfoQuestInterface;
import com.kondaurov.todojavamvp.model.QuestModel;
import com.kondaurov.todojavamvp.model.ToDoModel;
import com.kondaurov.todojavamvp.presenter.InfoQuestPresenter;
import com.kondaurov.todojavamvp.presenter.ToDoPresenter;

public class InfoView extends AppCompatActivity implements InfoQuestInterface {

    public static final String EVERYDAY = "everyday";
    public static final String IDTASK = "idtask";

    private InfoQuestPresenter presenter;
    private int everyday;
    private int id;
    private ToDoData currentQuest;

    TextView name, description, date;
    Button dellBtn, completeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_view);

        init();
    }

    private void init()
    {

        Bundle arguments = getIntent().getExtras();
        everyday = arguments.getInt(EVERYDAY);
        id = arguments.getInt(IDTASK);
        System.out.println("current id= "+id);
        name = findViewById(R.id.aiv_name_tack);
        description = findViewById(R.id.aiv_description_tack);
        date = findViewById(R.id.aiv_date);
        dellBtn = findViewById(R.id.aiv_btn_dell);
        completeBtn = findViewById(R.id.aiv_btn_complete);

        dellBtn.setOnClickListener(v ->
        {
            presenter.deleteQuest();
        });

        completeBtn.setOnClickListener(v ->
        {
            presenter.completeQuest();
        });

        DBHelper dbHelper = new DBHelper(this);
        QuestModel questModel = new QuestModel(dbHelper);
        presenter = new InfoQuestPresenter(questModel);
        presenter.attachView(this);
        presenter.viewIsReady();
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public int getEvery() {
        return everyday;
    }

    @Override
    public void showQuest(ToDoData quest) {

        currentQuest = quest;
        setTitle(currentQuest.getName());
        name.setText(quest.getName());
        description.setText(quest.getDescription());
        date.setText(quest.getDay()+"."+quest.getMonth()+"."+quest.getYear());

    }

    @Override
    public void startOtherScreen(Class activity) {
        if (activity == MainActivity.class)
            MainActivity.start(this);
        finish();
    }


    @Override
    public ToDoData getCurrentQuest() {
        return currentQuest;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}