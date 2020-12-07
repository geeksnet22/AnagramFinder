package com.example.firebaseproject;

import android.app.Activity;
import android.widget.TextView;

import java.util.Arrays;

public class FindAnagramHandler {

    private Activity activity;

    public FindAnagramHandler(Activity activity) {
        this.activity = activity;
    }

    public void getNumberOfAnagrams(final String word) {
        int num_anagrams = 0;
        for (String value: JsonFileReaderAndDatabaseUpdater.databaseContent.values()) {
            if (isAnagram(word, value)) {
                num_anagrams++;
            }
        }

        ((TextView) activity.findViewById(R.id.result)).setText(Integer.toString(num_anagrams));
    }

    private boolean isAnagram(String s1, String s2)
    {
        char[] ch1 = s1.toCharArray();
        char[] ch2 = s2.toCharArray();

        Arrays.sort(ch1);
        Arrays.sort(ch2);

        return Arrays.equals(ch1, ch2);
    }
}

