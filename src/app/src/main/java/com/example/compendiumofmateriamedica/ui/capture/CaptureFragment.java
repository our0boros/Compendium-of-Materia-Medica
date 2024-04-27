package com.example.compendiumofmateriamedica.ui.capture;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.compendiumofmateriamedica.MainActivity;
import com.example.compendiumofmateriamedica.R;
import com.example.compendiumofmateriamedica.databinding.FragmentCaptureBinding;

import java.util.Map;
import java.util.Objects;

import model.SearchGrammarParser;
import model.Token;
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

        searchButton.setOnClickListener(this::OnSearchButtonClick);
    }

    public void OnSearchButtonClick(View v) {
        try {
            // Search with grammar
            Tokenizer tokenizer = new Tokenizer(searchText.getText().toString());
            SearchGrammarParser searchGrammarParser = new SearchGrammarParser(tokenizer);
            Map<String, String> searchParam = searchGrammarParser.parseExp();
            Boolean isOR = searchGrammarParser.getSearchMethod(); // otherwise AND
            Log.println(Log.ASSERT, "DEBUG", "[OnClick] Search with grammar");
            Log.println(Log.ASSERT, "DEBUG", "[OnClick] Found searchParam has "
                    + searchParam.size() + " entities with " + (isOR ? "OR" : "AND"));
            Toast.makeText(requireActivity().getApplicationContext() ,"Search with grammar", Toast.LENGTH_LONG).show();
        } catch (SearchGrammarParser.IllegalProductionException | Token.IllegalTokenException e) {
            // Search without grammar

            Log.println(Log.ASSERT, "DEBUG", "[OnClick] Search without grammar");
            Toast.makeText(requireActivity().getApplicationContext() ,"Search without grammar", Toast.LENGTH_LONG).show();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        searchText.setText("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}