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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
    // =========== UI变量 ===========
    private FragmentCaptureBinding binding;

    private TextView greeting;
    private EditText searchText;
    private ImageButton captureButton;
    // =========== 当前搜索方法设定 ===========
    private Boolean searchMethod;
    private Spinner spinner;
    private Switch plantPostSwitch;
    private ArrayAdapter<CharSequence> currentArrayAdapter;
    private ArrayList<String> plantAttributes = new ArrayList<>(Arrays.asList(new String[]{"ID", "COMMON_NAME", "SLUG", "SCIENTIFIC_NAME", "GENUS", "FAMILY"}));

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CaptureViewModel captureViewModel =
                new ViewModelProvider(this).get(CaptureViewModel.class);

        binding = FragmentCaptureBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // ======================== UI ========================
        greeting = binding.textDashboard;
        // 获取当前用户的名称
        captureViewModel.setText(getResources().getString(R.string.greeting_msg).replace("[]", FirebaseAuth.getInstance().getCurrentUser().getDisplayName()));
        captureViewModel.getText().observe(getViewLifecycleOwner(), greeting::setText);

        // ======================== 搜索逻辑 ========================
        // 搜索内容切换
        plantPostSwitch = binding.plantPostSwitch;
        // 设置Switch的Text
        captureViewModel.setText(getResources().getString(R.string.search_switch));
        captureViewModel.getText().observe(getViewLifecycleOwner(), plantPostSwitch::setText);
        // 搜索栏文字监听
        searchText = binding.searchBarText;
        // 添加一个下拉菜单
        spinner = binding.plantsAttribute;
        // 设置 spinner 列表
        currentArrayAdapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.posts_attribute,
                android.R.layout.simple_spinner_item
        );
        currentArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // ======================== 拍照 ========================
        // 拍照按钮
        captureButton = binding.captureButton;

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 检测Switch的状态
        plantPostSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                // True: Post
                if (b) {
                    // Create an ArrayAdapter using the string array and a default spinner layout.
                    currentArrayAdapter = ArrayAdapter.createFromResource(
                            getContext(),
                            R.array.posts_attribute,
                            android.R.layout.simple_spinner_item
                    );
                    // Specify the layout to use when the list of choices appears.
                    currentArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // Apply the adapter to the spinner.
                    spinner.setAdapter(currentArrayAdapter);
                    // False: Plant
                } else {
                    // Create an ArrayAdapter using the string array and a default spinner layout.
                    currentArrayAdapter = ArrayAdapter.createFromResource(
                            getContext(),
                            R.array.plants_attribute,
                            android.R.layout.simple_spinner_item
                    );
                    // Specify the layout to use when the list of choices appears.
                    currentArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // Apply the adapter to the spinner.
                    spinner.setAdapter(currentArrayAdapter);
                }
            }
        });
        // 初始化spinner
        spinner.setAdapter(currentArrayAdapter);


        /**
         * 添加输入框监听，当输入完成时直接进行搜索， 删除原有的按钮搜索方法
         */
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                // 隐藏键盘
                InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(textView.getWindowToken(), 0);

                Log.println(Log.ASSERT, "DEBUG", "[OnClick] Get input: " + textView.getText() + " Select [" + spinner.getSelectedItemId() + "]");
                // 如果是空的不进行处理
                if (textView.getText().toString().trim().equals("")) {
                    textView.setText("");
                    return false;
                }

                // 如果spinner选择的不是第一项【语法搜索】，反之按照当前选择搜索
                if (spinner.getSelectedItemId() == 0) {
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

                        // 遍历搜索attribute
                        Map<RBTreeNode<Plant>, Integer> searchResult = new HashMap<>();
                        for (Map.Entry<String, String> entry : searchParam.entrySet()) {
                            // 生成文件树
                            ArrayList<RBTreeNode<Plant>> temp;
                            PlantTreeManager plantTreeManager = new PlantTreeManager(((MainActivity) requireActivity()).getPlantTree());
                            // 如果是ID需要搜索Integer类型
                            if (entry.getKey().equals("ID")) {
                                temp = plantTreeManager.search(PlantTreeManager.PlantInfoType.ID, Integer.valueOf(entry.getValue()));
                            } else {
                                int index = plantAttributes.indexOf(entry.getKey());
                                // 如果没有匹配的则忽略
                                if (index == -1) continue;
                                temp = plantTreeManager.search(
                                        PlantTreeManager.PlantInfoType.values()[index], entry.getValue());
                            }
                            // 添加搜索结果
                            for (RBTreeNode<Plant> node : temp) {
                                if (searchResult.containsKey(node)) {
                                    searchResult.put(node, searchResult.get(node) + 1);
                                } else {
                                    searchResult.put(node, 1);
                                }
                            }

                        }
                        textView.setText("");
                        Log.println(Log.ASSERT, "DEBUG", "[OnClick] putExtra: " + searchResult.size());
                        // 准备跳转数据
                        // 既然Node无法序列化那就用Id list
                        ArrayList<Integer> plantIDList = new ArrayList<>();
                        for (Map.Entry<RBTreeNode<Plant>, Integer> entry : searchResult.entrySet()) {
                            // 如果是AND直接添加
                            if (!searchMethod) {
                                plantIDList.add(entry.getKey().getKey());
                                // 如果是OR 只添加出现次数与attribute size相同的plant
                            } else if (entry.getValue() == searchResult.size()) {
                                plantIDList.add(entry.getKey().getKey());
                            }

                        }

                        // 跳转界面
                        Intent postIntent = new Intent(getContext(), SearchedPostResults.class);
                        postIntent.putExtra("post", plantIDList);
                        startActivity(postIntent);
                        return true;
                    } catch (SearchGrammarParser.IllegalProductionException | Token.IllegalTokenException | IllegalAccessException e) {
                        Log.println(Log.ASSERT, "DEBUG", "[OnClick] catch error: " + e);
                    }
                }
                textView.setText("");
                // Search without grammar
                Log.println(Log.ASSERT, "DEBUG", "[OnClick] Search without grammar");
                Toast.makeText(requireActivity().getApplicationContext() ,"Search without grammar", Toast.LENGTH_LONG).show();

                switch ((int) spinner.getSelectedItemId()) {
                    case 1:

                }

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