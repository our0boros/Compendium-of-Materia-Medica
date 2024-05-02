package com.example.compendiumofmateriamedica.ui.capture;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.WindowInsetsAnimation;
import android.widget.LinearLayout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.compendiumofmateriamedica.MainActivity;

public class CaptureViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<Drawable> searchBarIconBackground;

    public CaptureViewModel() {
        mText = new MutableLiveData<>();
        searchBarIconBackground = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
    public void setText(String text) {
        mText.setValue(text);
    }
    public LiveData<Drawable> getIconBackground() { return searchBarIconBackground; }

    public void setIconBackground(Drawable background) {

        searchBarIconBackground.setValue(background);
    }
}