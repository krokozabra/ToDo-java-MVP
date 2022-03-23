package com.kondaurov.todojavamvp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.kondaurov.todojavamvp.R;

public class EverydayActivity extends AppCompatActivity {


    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, EverydayActivity.class);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_everyday);
    }
}