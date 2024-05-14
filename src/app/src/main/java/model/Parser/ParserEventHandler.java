package model.Parser;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.compendiumofmateriamedica.EmptySearchResult;
import com.example.compendiumofmateriamedica.SearchedResults;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import model.Datastructure.DataType;
import model.Datastructure.Plant;
import model.Datastructure.PlantTreeManager;
import model.Datastructure.Post;
import model.Datastructure.PostTreeManager;
import model.Datastructure.RBTreeNode;

public class ParserEventHandler {
    /**
     *
     * @param searchParam
     * @param dataType
     * @return
     */
    private static Map<Integer, Integer> getSearchedResultsFromParameters(Map<String, String> searchParam, DataType dataType) {
        // 遍历搜索attribute
        Map<Integer, Integer> searchResult = new HashMap<>();

        for (Map.Entry<String, String> entry : searchParam.entrySet()) {
            System.out.println("[getSearchedResultsFromParameters] entry: " + entry.getKey() + " | " + entry.getValue());
            try {
                ArrayList<?> temp;

                if (dataType == DataType.PLANT) {
                    temp = PlantTreeManager.getInstance().search(
                            PlantTreeManager.getInstance().getTypeByString(entry.getKey()), entry.getValue());
                    for (Object node : temp) {
                        Integer nodeValueIndex = null;
                        nodeValueIndex = ((Plant) node).getId();
                        searchResult.put(nodeValueIndex, searchResult.getOrDefault(nodeValueIndex, 1) + 1);
                    }
                }else if (dataType == DataType.POST) {
                    temp = PostTreeManager.getInstance().search(
                            PostTreeManager.getInstance().getTypeByString(entry.getKey()), entry.getValue());
                    for (Object node : temp) {
                        Integer nodeValueIndex = null;
                        nodeValueIndex = ((Post) node).getPost_id();
                        searchResult.put(nodeValueIndex, searchResult.getOrDefault(nodeValueIndex, 1) + 1);
                    }
                }

            } catch (Exception e) {
                continue;
            }
        }
        return searchResult;
    }

    /**
     *
     * @param searchResult
     * @param searchType
     * @param paramLength
     * @return
     */
    private static ArrayList<Integer> getIDListFromSearchedResults(Map<Integer, Integer> searchResult, Token.Type searchType, int paramLength) {
        ArrayList<Integer> IDList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : searchResult.entrySet()) {
            // 如果是OR直接添加
            if (searchType == Token.Type.OR) {
                IDList.add(entry.getKey());
            }
            // 如果是AND 只添加出现次数与attribute size相同的plant
            if (searchType == Token.Type.AND && paramLength == entry.getValue()) {
                IDList.add(entry.getKey());
            }

        }
        return IDList;
    }
    public static ArrayList<Integer> getIDListFromGrammarText(String text, DataType dataType) {
        // 反之进行语法判定逻辑
        try {
            // Search with grammar
            Tokenizer tokenizer = new Tokenizer(text);
            SearchGrammarParser searchGrammarParser = new SearchGrammarParser(tokenizer);
            Map<String, String> searchParam = searchGrammarParser.parseExp();
            Token.Type searchMethod = searchGrammarParser.getSearchMethod(); // otherwise AND
            // get List IDs
            Map<Integer, Integer> searchResult = getSearchedResultsFromParameters(searchParam, dataType);

            // 准备跳转数据
            // 既然Node无法序列化那就用Id list
            ArrayList<Integer> IDList = getIDListFromSearchedResults(searchResult, searchMethod, searchParam.size());
            return IDList;
            // =============================================================================
        } catch (SearchGrammarParser.IllegalProductionException | Token.IllegalTokenException | IllegalAccessException e) {
            System.out.println("[getIDListFromGrammarText] catch Exception: " + e);
            return null;
        }
    }

}
