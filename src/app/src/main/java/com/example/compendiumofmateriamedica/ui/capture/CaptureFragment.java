package com.example.compendiumofmateriamedica.ui.capture;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.compendiumofmateriamedica.R;
import com.example.compendiumofmateriamedica.databinding.FragmentCaptureBinding;

import java.util.Objects;

/**
 * @author: Hongjun Xu
 * @datetime: 2024/4/27
 * @description: Capture Page
 */
public class CaptureFragment extends Fragment {

    private FragmentCaptureBinding binding;
    private EditText searchText;
    private ImageButton captureButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CaptureViewModel captureViewModel =
                new ViewModelProvider(this).get(CaptureViewModel.class);

        binding = FragmentCaptureBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        captureViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        searchText = requireView().findViewById(R.id.searchBarText);
        captureButton = requireView().findViewById(R.id.captureButton);

        captureButton.setOnClickListener(this::OnClick);
        return root;
    }

    public void OnClick(View v) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}