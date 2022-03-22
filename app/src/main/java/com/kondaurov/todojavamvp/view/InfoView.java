package com.kondaurov.todojavamvp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kondaurov.todojavamvp.R;

public class InfoView extends AppCompatActivity {

    public static final String EVERYDAY = "everyday";
    public static final String IDTASK = "idtask";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_view);
    }
}