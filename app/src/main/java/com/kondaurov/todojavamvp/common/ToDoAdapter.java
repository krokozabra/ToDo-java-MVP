package com.kondaurov.todojavamvp.common;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.kondaurov.todojavamvp.R;
import com.kondaurov.todojavamvp.database.DBHelper;
import com.kondaurov.todojavamvp.model.QuestModel;
import com.kondaurov.todojavamvp.view.InfoView;

import java.util.ArrayList;

public class ToDoAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<ToDoData> objects;
    TextView name;
    CheckBox flagTack;
    RelativeLayout spase;
    DBHelper dbHelper;


    public ToDoAdapter(Context context, ArrayList<ToDoData> todos) {
        ctx = context;
        objects = todos;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dbHelper = new DBHelper(context);

    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_task, parent, false);
        }

        ToDoData p = getToDoData(position);
        // заполняем View в пункте списка данными из истории
        name = view.findViewById(R.id.it_name);
        name.setText(p.getName());
        spase = view.findViewById(R.id.it_space);
        spase.setOnClickListener(v -> {
            System.out.println("название " + name.getText().toString());
            //переход на экран описание таска
            Intent intent = new Intent(ctx, InfoView.class);
            intent.putExtra(InfoView.EVERYDAY, p.getEveryday());
            intent.putExtra(InfoView.IDTASK, p.getId());
            ctx.startActivity(intent);
        });

        flagTack = view.findViewById(R.id.it_check);
        if(p.getEveryday()==0) {
            View finalView = view;
            QuestModel questModel = new QuestModel(dbHelper);
            flagTack.setOnClickListener(v ->
            {
//            System.out.println("значение текущего таска: "+p.getOK());
                //окрашивается при нажатии на чекбокс
                if (p.getOK() == 0) {
                    p.setOK(1);
                    questModel.completeQuest(p);
                    spase = finalView.findViewById(R.id.it_space);
                    spase.setBackgroundColor(ContextCompat.getColor(ctx, R.color.green));
                } else {
                    p.setOK(0);
                    questModel.completeQuest(p);
                    spase = finalView.findViewById(R.id.it_space);
                    spase.setBackgroundColor(ContextCompat.getColor(ctx, R.color.white));
                }
            });
        }
        else
        {
            flagTack.setVisibility(View.GONE);
        }
        //для изначального окрашивания
        if (p.getOK() == 1) {
            ((CheckBox) view.findViewById(R.id.it_check)).setChecked(true);
            spase.setBackgroundColor(ContextCompat.getColor(ctx, R.color.green));
        }
        else {
            ((CheckBox) view.findViewById(R.id.it_check)).setChecked(false);
            spase.setBackgroundColor(ContextCompat.getColor(ctx, R.color.white));
        }

        return view;
    }

    ToDoData getToDoData(int position) {
        return ((ToDoData) getItem(position));
    }

}
