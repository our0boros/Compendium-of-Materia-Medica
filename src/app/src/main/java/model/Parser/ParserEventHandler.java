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
/**
 * @author: Hongjun Xu
 * @datetime: 2024/05/16
 * @description: Mainly handles the format transformation of various
 * indexed contents, such as converting the
 * HashMap<index id, number of occurrences> table into a valid index
 * id list
 */
public class ParserEventHandler {
    /**
     * According to the input data type, a search table (including
     * various tags and corresponding content) is converted into
     * a table of searched object IDs and their occurrence times,
     * and fuzzy search is added.
     * @param searchParam
     * @param dataType
     * @param bestSimilarity
     * @return searchResult
     */
    private static Map<Integer, Integer> getSearchedResultsFromParameters(Map<String, String> searchParam, DataType dataType, double bestSimilarity) {
        // 遍历搜索attribute
        Map<Integer, Integer> searchResult = new HashMap<>();
        // Iterate each search entities
        for (Map.Entry<String, String> entry : searchParam.entrySet()) {
            System.out.println("[getSearchedResultsFromParameters] entry: " + entry.getKey() + " | " + entry.getValue());
            try {
                ArrayList<?> temp;
                // PLANT CASE
                if (dataType == DataType.PLANT) {
                    temp = PlantTreeManager.getInstance().search(
                            PlantTreeManager.getInstance().getTypeByString(entry.getKey()), entry.getValue());
                    // if can not find mapping results, try blur search
                    if (temp.size() == 0) {
                        String guessValue = getSearchedResultsFromBlurParameter(
                                PlantTreeManager.getInstance().getTypeByString(entry.getKey()),
                                entry.getValue(),
                                bestSimilarity);
                        if (!guessValue.equals("")) {
                            temp = PlantTreeManager.getInstance().search(
                                    PlantTreeManager.getInstance().getTypeByString(entry.getKey()), guessValue);
                            GeneralFunctions.getInstance().makeToast("Can not find result, try guessed value: " + guessValue);
                        }

                    }
                    // Parse data
                    for (Object node : temp) {
                        Integer nodeValueIndex = null;
                        nodeValueIndex = ((Plant) node).getId();
                        searchResult.put(nodeValueIndex, searchResult.getOrDefault(nodeValueIndex, 1) + 1);
                    }
                    // POST CASE
                }else if (dataType == DataType.POST) {
                    temp = PostTreeManager.getInstance().search(
                            PostTreeManager.getInstance().getTypeByString(entry.getKey()), entry.getValue());
                    // if can not find mapping results, try blur search
                    if (temp.size() == 0) {
                        String guessValue = getSearchedResultsFromBlurParameter(
                                PostTreeManager.getInstance().getTypeByString(entry.getKey()),
                                entry.getValue(),
                                bestSimilarity);
                        if (!guessValue.equals("")) {
                            temp = PostTreeManager.getInstance().search(
                                    PostTreeManager.getInstance().getTypeByString(entry.getKey()), guessValue);
                            GeneralFunctions.getInstance().makeToast("Can not find result, try guessed value: " + guessValue);
                        }

                    }
                    // Parse data
                    for (Object node : temp) {
                        Integer nodeValueIndex = null;
                        nodeValueIndex = ((Post) node).getPost_id();
                        searchResult.put(nodeValueIndex, searchResult.getOrDefault(nodeValueIndex, 1) + 1);
                    }
                } else {
                    temp = new ArrayList<>();
                }

            } catch (Exception e) {
                // Skip these data parser if can not parse search value
                continue;
            }
        }
        return searchResult;
    }

    /**
     * Compare the current parameters with all related instances.
     * If the similarity is greater than the preset threshold,
     * return the value closest to the original value.
     * @param plantInfoType
     * @param bestSimilarity
     * @param value
     * @return
     */
    public static String getSearchedResultsFromBlurParameter(PlantTreeManager.PlantInfoType plantInfoType, String value, double bestSimilarity) {
        System.out.println("=== [getSearchedResultsFromBlurParameter] ===");
        // get all plant list
        String guessValue = "";
        // Only solve following case, and when best similarity larger than 0
        if (!(bestSimilarity > 0 ||
            plantInfoType == PlantTreeManager.PlantInfoType.COMMON_NAME ||
            plantInfoType == PlantTreeManager.PlantInfoType.SCIENTIFIC_NAME ||
            plantInfoType == PlantTreeManager.PlantInfoType.FAMILY)) {
            return guessValue;
        }
        // get search list
        ArrayList<Plant> plantArrayList = PlantTreeManager.getInstance().search(PlantTreeManager.PlantInfoType.COMMON_NAME, "");
        System.out.println(plantArrayList.size());
        // Iterate each entities
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
    public static String getSearchedResultsFromBlurParameter(PostTreeManager.PostInfoType postInfoType, String value, double bestSimilarity) {
        System.out.println("=== [getSearchedResultsFromBlurParameter] ===");
        // get all plant list
        String guessValue = "";
        if (!(bestSimilarity > 0 ||
            postInfoType == PostTreeManager.PostInfoType.CONTENT)) {
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
     * Through the matching table of the obtained ID and the number of occurrences,
     * the correct ID list is returned according to the current search method.
     * If the search method is AND, it means that the returned list must be the
     * number of occurrences of the ID equal to the total number of parameters. If
     * it is OR, it means that the returned list must be the number of occurrences
     * of any ID.
     * @param searchResult
     * @param searchType
     * @param paramLength
     * @return IDList
     */
    private static ArrayList<Integer> getIDListFromSearchedResults(Map<Integer, Integer> searchResult, Token.Type searchType, int paramLength) {
        ArrayList<Integer> IDList = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : searchResult.entrySet()) {
            // If it is OR, add it directly
            if (searchType == Token.Type.OR) {
                IDList.add(entry.getKey());
            }
            // If it is AND, only add plants with the same number of occurrences as attribute size.
            if (searchType == Token.Type.AND && paramLength == entry.getValue()) {
                IDList.add(entry.getKey());
            }

        }
        return IDList;
    }

    /**
     * Read the string that conforms to the grammatical format
     * and convert it into a list of corresponding object IDs
     * required for actual search results through Tokenizer and
     * parser.
     * @param text
     * @param dataType
     * @param bestSimilarity
     * @return IDList
     */
    public static ArrayList<Integer> getIDListFromGrammarText(String text, DataType dataType, double bestSimilarity) {
        // 反之进行语法判定逻辑
        try {
            // Search with grammar
            Tokenizer tokenizer = new Tokenizer(text);
            SearchGrammarParser searchGrammarParser = new SearchGrammarParser(tokenizer);
            Map<String, String> searchParam = searchGrammarParser.parseExp();
            Token.Type searchMethod = searchGrammarParser.getSearchMethod(); // otherwise AND
            // get List IDs
            Map<Integer, Integer> searchResult = getSearchedResultsFromParameters(searchParam, dataType, bestSimilarity);

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
    // ===========================================================
    // Calculate edit distance
    // ===========================================================

    private static int calculateEditDistance(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        //Initialize the two-dimensional array
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }
        // Dynamic programming calculates edit distance
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

    /**
     * Calculate string similarity (based on edit distance)
     * Determining the similarity between two strings through the
     * method of minimal modification ensures that the expected
     * results can still be found when the user makes an input error.
     * @param s1
     * @param s2
     * @return
     */
    private static double calculateStringSimilarity(String s1, String s2) {
        int editDistance = calculateEditDistance(s1, s2);
        int maxLength = Math.max(s1.length(), s2.length());
        return 1.0 - (double) editDistance / maxLength;
    }
}
