package com.example.compendiumofmateriamedica.ui.capture;

import android.graphics.drawable.Drawable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CaptureViewModel extends ViewModel {

    private final MutableLiveData<String> greetingText;
    private final MutableLiveData<String> switchText;
    private final MutableLiveData<Drawable> searchBarIconBackground;

    public CaptureViewModel() {
        greetingText = new MutableLiveData<>();
        switchText = new MutableLiveData<>();
        searchBarIconBackground = new MutableLiveData<>();
    }

    public LiveData<String> getGreetingText() {
        return greetingText;
    }
    public LiveData<String> getSwitchText() {
        return switchText;
    }
    public void setSwitchTextText(String text) {
        switchText.setValue(text);
    }
    public void setGreetingText(String text) {
        greetingText.setValue(text);
    }
    public LiveData<Drawable> getIconBackground() { return searchBarIconBackground; }

    public void setIconBackground(Drawable background) {

        searchBarIconBackground.setValue(background);
    }
}