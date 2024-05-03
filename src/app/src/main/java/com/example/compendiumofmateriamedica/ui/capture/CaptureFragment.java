package com.example.compendiumofmateriamedica.ui.capture;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.compendiumofmateriamedica.MainActivity;
import com.example.compendiumofmateriamedica.PostShare;
import com.example.compendiumofmateriamedica.R;
import com.example.compendiumofmateriamedica.SearchedPostResults;
import com.example.compendiumofmateriamedica.databinding.FragmentCaptureBinding;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import model.DataType;
import model.GeneratorFactory;
import model.Plant;
import model.PlantTreeManager;
import model.RBTree;
import model.RBTreeNode;
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
    private TextView greeting;
    private EditText searchText;
    private ImageButton captureButton;
    private Boolean searchMethod;
    private Spinner spinner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CaptureViewModel captureViewModel =
                new ViewModelProvider(this).get(CaptureViewModel.class);

        binding = FragmentCaptureBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        greeting = binding.textDashboard;
        // 获取当前用户的名称
        captureViewModel.setText(getResources().getString(R.string.greeting_msg).replace("[]", FirebaseAuth.getInstance().getCurrentUser().getDisplayName()));
        captureViewModel.getText().observe(getViewLifecycleOwner(), greeting::setText);

        searchText = binding.searchBarText;

        captureButton = binding.captureButton;
        searchText.getBackground();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 添加一个下拉菜单
        spinner = (Spinner) getView().findViewById(R.id.plants_attribute);
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.plants_attribute,
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        spinner.setAdapter(adapter);

        /**
         * 添加输入框监听，当输入完成时直接进行搜索， 删除原有的按钮搜索方法
         */
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                // 隐藏键盘
                InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                // 如果是空的不进行处理
                if (textView.getText().toString().trim().equals("")) {
                    textView.setText("");
                    return false;
                }
                Log.println(Log.ASSERT, "DEBUG", "[OnClick] Get input: " + textView.getText());
                // 反之进行语法判定逻辑
                try {
                    // Search with grammar
                    Tokenizer tokenizer = new Tokenizer(textView.getText().toString());
                    SearchGrammarParser searchGrammarParser = new SearchGrammarParser(tokenizer);
                    Map<String, String> searchParam = searchGrammarParser.parseExp();
                    searchMethod = searchGrammarParser.getSearchMethod(); // otherwise AND
                    Log.println(Log.ASSERT, "DEBUG", "[OnClick] Search with grammar");
                    Log.println(Log.ASSERT, "DEBUG", "[OnClick] Found searchParam has "
                            + searchParam.size() + " entities with " + (searchMethod ? "OR" : "AND"));
                    Toast.makeText(requireActivity().getApplicationContext() ,"Search with grammar", Toast.LENGTH_LONG).show();
                    textView.setText("");

                    // 生成文件树
                    PlantTreeManager plantTreeManager = new PlantTreeManager(((MainActivity) requireActivity()).getPlantTree());
                    ArrayList<RBTreeNode<Plant>> searchResult = plantTreeManager.search(PlantTreeManager.PlantInfoType.ID, Integer.valueOf(searchParam.get("ID")));
                    Log.println(Log.ASSERT, "DEBUG", "[OnClick] sample search result[0]: " + searchResult.get(0).getKey());

                    // 既然Node无法序列化那就用Id list
                    ArrayList<Integer> plantIDList = new ArrayList<>();
                    for (RBTreeNode<Plant> node : searchResult) {
                        plantIDList.add(searchResult.get(0).getKey());
                    }
                    // 跳转界面
                    Intent postIntent = new Intent(getContext(), SearchedPostResults.class);
                    postIntent.putExtra("post", plantIDList);
                    startActivity(postIntent);
                    return true;
                } catch (SearchGrammarParser.IllegalProductionException | Token.IllegalTokenException | IllegalAccessException e) {
                    // Search without grammar
                    Log.println(Log.ASSERT, "DEBUG", "[OnClick] catch error: " + e);
                    Log.println(Log.ASSERT, "DEBUG", "[OnClick] Search without grammar");
                    Toast.makeText(requireActivity().getApplicationContext() ,"Search without grammar", Toast.LENGTH_LONG).show();
                }

                textView.setText("");
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}