package com.example.firebaseproject;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class JsonFileReaderAndDatabaseUpdater extends IntentService {

    private static final String TAG = JsonFileReaderAndDatabaseUpdater.class.getSimpleName();
    private final static Map<String, String> databaseContent = new HashMap<>();

    public JsonFileReaderAndDatabaseUpdater() {
        super("FileReader");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String fileContent;
        try {
            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
            InputStream inputStream = getApplicationContext().getAssets().open("anagram.json");
            int size = 0;
            size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            fileContent = new String(buffer, "UTF-8");

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            JSONArray wordsArray = new JSONArray(fileContent);
            for (int i = 0; i < wordsArray.length(); i++) {
                if (!wordsArray.getString(i).equals("null")) {
                    DatabaseReference myRef = database.getReference(Integer.toString(i));
                    myRef.setValue(wordsArray.getString(i));
                    databaseContent.put(Integer.toString(i), wordsArray.getString(i));

                    // setup database listener
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            databaseContent.put(dataSnapshot.getKey(), dataSnapshot.getValue().toString());
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            Log.w(TAG, "Failed to read value.", error.toException());
                        }
                    });
                }
            }
        }
        catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> getDatabaseContent() {
        return databaseContent;
    }
}
