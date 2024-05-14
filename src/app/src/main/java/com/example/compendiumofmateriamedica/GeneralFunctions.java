package com.example.compendiumofmateriamedica;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
    // 添加加载敏感词次词库
    public static boolean isSensitiveWord(String word) {
        try {
            // 打开 raw 文件中的文件输入流
            InputStream inputStream = context.getResources().openRawResource(R.raw.sensitive_words);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            // 逐行读取字符
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // 与当前字符进行匹配
                if (line.contains(word)) {
                    return true;
                }
            }

            // 关闭流
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void makeToast(String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
