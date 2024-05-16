package com.example.compendiumofmateriamedica;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author: Hongjun Xu
 * @datetime: 2024/05/16
 * @description: A method common to the entire app,
 * it will preload a single instance when the app
 * is loaded and use the singleton mode.
 */
public class GeneralFunctions {
    private static GeneralFunctions instance;
    private static Context context;
    private GeneralFunctions(Context context) {
        this.context = context;
    }

    public static GeneralFunctions getInstance() {
        assert instance != null;
        return instance;
    }
    public static GeneralFunctions getInstance(Context context) {
        if (instance == null) {
            instance = new GeneralFunctions(context);
        }
        return instance;
    }
    // Add and load sensitive word dictionary
    public static boolean isSensitiveWord(String word) {
        try {
            // Open the file input stream in the raw file
            InputStream inputStream = context.getResources().openRawResource(R.raw.sensitive_words);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            // Read characters line by line
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Match the current character
                if (line.contains(word)) {
                    return true;
                }
            }

            // close the stream
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Display Toast anywhere
    public void makeToast(String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
