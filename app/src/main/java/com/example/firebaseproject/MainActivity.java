package com.example.firebaseproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.example.firebaseproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent fileReaderIntent  = new Intent(this, JsonFileReaderAndDatabaseUpdater.class);
        startService(fileReaderIntent);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setFindAnagramHandler(new FindAnagramHandler(this));
        binding.setResult("0");
    }
}