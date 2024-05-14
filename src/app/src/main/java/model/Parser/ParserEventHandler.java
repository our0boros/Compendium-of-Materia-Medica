package model.Parser;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.compendiumofmateriamedica.EmptySearchResult;
import com.example.compendiumofmateriamedica.GeneralFunctions;
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
                    if (temp.size() == 0) {
                        // if can not find mapping results, try blur search
                        String guessValue = getSearchedResultsFromBlurParameter(PlantTreeManager.getInstance().getTypeByString(entry.getKey()), entry.getValue());
                        if (!guessValue.equals("")) {
                            temp = PlantTreeManager.getInstance().search(
                                    PlantTreeManager.getInstance().getTypeByString(entry.getKey()), guessValue);
                            GeneralFunctions.getInstance().makeToast("Can not find result, try guessed value: " + guessValue);
                        }

                    }
                    for (Object node : temp) {
                        Integer nodeValueIndex = null;
                        nodeValueIndex = ((Plant) node).getId();
                        searchResult.put(nodeValueIndex, searchResult.getOrDefault(nodeValueIndex, 1) + 1);
                    }
                }else if (dataType == DataType.POST) {
                    temp = PostTreeManager.getInstance().search(
                            PostTreeManager.getInstance().getTypeByString(entry.getKey()), entry.getValue());
                    if (temp.size() == 0) {
                        // if can not find mapping results, try blur search
                        String guessValue = getSearchedResultsFromBlurParameter(PostTreeManager.getInstance().getTypeByString(entry.getKey()), entry.getValue());
                        if (!guessValue.equals("")) {
                            temp = PostTreeManager.getInstance().search(
                                    PostTreeManager.getInstance().getTypeByString(entry.getKey()), guessValue);
                            GeneralFunctions.getInstance().makeToast("Can not find result, try guessed value: " + guessValue);
                        }

                    }
                    for (Object node : temp) {
                        Integer nodeValueIndex = null;
                        nodeValueIndex = ((Post) node).getPost_id();
                        searchResult.put(nodeValueIndex, searchResult.getOrDefault(nodeValueIndex, 1) + 1);
                    }
                } else {
                    temp = new ArrayList<>();
                }


            } catch (Exception e) {
                continue;
            }
        }
        return searchResult;
    }

    public static String getSearchedResultsFromBlurParameter(PlantTreeManager.PlantInfoType plantInfoType, String value) {
        System.out.println("=== [getSearchedResultsFromBlurParameter] ===");
        // get all plant list
        double bestSimilarity = 0.5;
        String guessValue = "";
        if (!(plantInfoType == PlantTreeManager.PlantInfoType.COMMON_NAME ||
        plantInfoType == PlantTreeManager.PlantInfoType.SCIENTIFIC_NAME ||
        plantInfoType == PlantTreeManager.PlantInfoType.FAMILY)) {
            return guessValue;
        }

        ArrayList<Plant> plantArrayList = PlantTreeManager.getInstance().search(PlantTreeManager.PlantInfoType.COMMON_NAME, "");
        System.out.println(plantArrayList.size());
        for (Plant plant : plantArrayList) {
            double similarity = calculateStringSimilarity((String) plant.getByType(plantInfoType), value);
            if (similarity > bestSimilarity) {
                System.out.println("[getSearchedResultsFromBlurParameter] find similar string");
                bestSimilarity = similarity;
                guessValue = (String) plant.getByType(plantInfoType);
            }
        }
        return guessValue;
    }
    public static String getSearchedResultsFromBlurParameter(PostTreeManager.PostInfoType postInfoType, String value) {
        System.out.println("=== [getSearchedResultsFromBlurParameter] ===");
        // get all plant list
        double bestSimilarity = 0.5;
        String guessValue = "";
        if (!(postInfoType == PostTreeManager.PostInfoType.CONTENT)) {
            return guessValue;
        }
        ArrayList<Post> postArrayList = PostTreeManager.getInstance().search(PostTreeManager.PostInfoType.CONTENT, "");
        System.out.println(postArrayList.size());
        for (Post post : postArrayList) {
            double similarity = calculateStringSimilarity((String) post.getByType(postInfoType), value);
            if (similarity > bestSimilarity) {
                System.out.println("[getSearchedResultsFromBlurParameter] find similar string");
                bestSimilarity = similarity;
                guessValue = (String) post.getByType(postInfoType);
            }
        }
        return guessValue;
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

    // 计算编辑距离
    private static int calculateEditDistance(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();

        // 初始化二维数组
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        // 动态规划计算编辑距离
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }

        return dp[m][n];
    }

    // 计算字符串相似度（基于编辑距离）
    private static double calculateStringSimilarity(String s1, String s2) {
        int editDistance = calculateEditDistance(s1, s2);
        int maxLength = Math.max(s1.length(), s2.length());
        return 1.0 - (double) editDistance / maxLength;
    }
}
