package com.example.compendiumofmateriamedica.ui.capture;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.compendiumofmateriamedica.R;
import com.example.compendiumofmateriamedica.databinding.FragmentCaptureBinding;

import java.util.Objects;

import model.Tokenizer;

/**
 * @author: Hongjun Xu
 * @datetime: 2024/4/27
 * @description: Capture Page
 */
public class CaptureFragment extends Fragment {

    private FragmentCaptureBinding binding;
    private EditText searchText;
    private ImageButton searchButton;
    private ImageButton captureButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CaptureViewModel captureViewModel =
                new ViewModelProvider(this).get(CaptureViewModel.class);

        binding = FragmentCaptureBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        captureViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        searchText = requireView().findViewById(R.id.searchBarText);
        searchButton = requireView().findViewById(R.id.searchButton);
        captureButton = requireView().findViewById(R.id.captureButton);

        searchButton.setOnClickListener(this::OnClick);
    }

    public void OnClick(View v) {
        Tokenizer tokenizer = new Tokenizer(searchText.getText().toString());
        String tokenizerOutput = "Get tokenizer input: ";
        while (tokenizer.hasNext()) {

            tokenizerOutput = tokenizerOutput + " " + tokenizer.current();
            tokenizer.next();
        }
        Log.println(Log.ASSERT, "DEBUG", tokenizerOutput);
        searchText.setText("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}