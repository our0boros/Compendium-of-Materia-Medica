package com.example.compendiumofmateriamedica.ui.capture;

import static model.UtilsApp.loadImageFromURL;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.compendiumofmateriamedica.GeneralFunctions;
import com.example.compendiumofmateriamedica.PostShareActivity;
import com.example.compendiumofmateriamedica.R;
import com.example.compendiumofmateriamedica.SearchedResults;
import com.example.compendiumofmateriamedica.databinding.FragmentCaptureBinding;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.compendiumofmateriamedica.EmptySearchResult;

import model.Datastructure.DataType;
import model.Datastructure.Plant;
import model.Datastructure.PlantTreeManager;
import model.Datastructure.Post;
import model.Datastructure.PostTreeManager;
import model.Datastructure.RBTreeNode;
import model.Datastructure.UserTreeManager;
import model.Parser.ParserEventHandler;
import model.Parser.SearchGrammarParser;
import model.Parser.Token;
import model.Parser.Tokenizer;
import model.Datastructure.User;

/**
 * @author: Hongjun Xu, Xing Chen
 * @datetime: u7733037, u7725171
 * @description: Capture Page, user can search and take photo here
 */
public class CaptureFragment extends Fragment {
    // =========== UI PARAMETERS ===========
    private FragmentCaptureBinding binding;

    private TextView greeting;
    private EditText searchText;
    private ImageView userImage;
    private ImageButton captureButton;
    private ImageView searchHint;
    private User currentUser;
    // =========== SEARTCH METHOD SETTINGS ===========
    private Spinner spinner;
    private Switch plantPostSwitch;
    private boolean isPost = false;
    private ArrayAdapter<CharSequence> currentArrayAdapter;
    // ================ CAPTURE ================
    private String currentPhotoPath;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    dispatchTakePictureIntent();
                } else {
                    // Permission is denied, you can handle it here, for example, display a Toast to prompt the user
                    Toast.makeText(getContext(), "Camera permission is required to take photos", Toast.LENGTH_SHORT).show();
                }
            });

    private ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == getActivity().RESULT_OK) {
                    Intent intent = new Intent(getActivity(), PostShareActivity.class);
                    intent.putExtra("photoPath", currentPhotoPath);
                    intent.putExtra("User", getActivity().getIntent().getSerializableExtra("User"));
                    startActivity(intent);
                }
            });
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CaptureViewModel captureViewModel =
                new ViewModelProvider(this).get(CaptureViewModel.class);

        binding = FragmentCaptureBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        currentUser = (User) getActivity().getIntent().getSerializableExtra("User");
        // ======================== UI ========================
        greeting = binding.textDashboard;
        // Get the name of the current user
        captureViewModel.setGreetingText(getResources().getString(R.string.greeting_msg).replace("[]",
                currentUser.getUsername()));
        captureViewModel.getGreetingText().observe(getViewLifecycleOwner(), greeting::setText);
        userImage = binding.userHeader;
        String userURL = UserTreeManager.getInstance().search(UserTreeManager.UserInfoType.ID, currentUser.getUser_id()).get(0).getAvatar_url();
        loadImageFromURL(getContext(), userURL, userImage, "Avatar");
        searchHint = binding.searchHint;
        searchHint.setImageDrawable(getResources().getDrawable(R.drawable.ic_error_outline_24));
        searchHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.println(Log.ASSERT, "DEBUG", "Hint button click");
                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setTitle("Search Grammar")
                        .setMessage(getResources().getString(R.string.hint_message) + "\n\n" +
                                " * parser grammar:\n" +
                                " * <Exp>        := <TagColumn>, <TextColumn>, <METHOD> | <TextColumn>, <TagColumn>, <METHOD>\n" +
                                " * <TagColumn>  := #: { <Content> },\n" +
                                " * <TextColumn> := $: { <Content> },\n" +
                                " * <Method>     :=  *:{&} |  *:{|}\n" +
                                " * <Content>    := STR | STR, <Content>")
                        .setIcon(R.drawable.ic_error_outline_24)
                        .create();
                alertDialog.show();
            }
        });

        // ======================== Search logic ======================
        // Search content switching
        plantPostSwitch = binding.plantPostSwitch;
        //Search bar text monitoring
        searchText = binding.searchBarText;
        //Add a drop-down menu
        spinner = binding.plantsAttribute;
        //Set spinner list
        currentArrayAdapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.plants_attribute,
                android.R.layout.simple_spinner_item
        );
        currentArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // ======================= Take photos ======================= =
        // Photo button
        captureButton = binding.captureButton;

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Detect the status of Switch
        plantPostSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                // True: Post
                if (b) {
                    isPost = true;
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
                    isPost = false;
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


        // Add input box monitoring, search directly when the input is completed,
        // delete the original button search method
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                // Hide keyboard
                InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(textView.getWindowToken(), 0);

                Log.println(Log.ASSERT, "DEBUG", "[OnClick] Get input: " + textView.getText() + " Select [" + spinner.getSelectedItemId() + "]");
                // 如果是空的不进行处理
                if (textView.getText().toString().trim().equals("")) {
                    textView.setText("");
                    return false;
                }

                // =============================================================================
                // Search with grammar
                // =============================================================================
                // If the spinner selects something other than the first item [grammar search],
                // otherwise it will search according to the current selection.
                if (spinner.getSelectedItemId() == 0) {
                    // Otherwise, perform grammar judgment logic
                    ArrayList<Integer> IDList = ParserEventHandler.getIDListFromGrammarText(
                            String.valueOf(textView.getText()),
                            (isPost ? DataType.POST : DataType.PLANT),
                            0.5);
                    Log.println(Log.ASSERT, "DEBUG", "[OnClick] Search with grammar");
                    Toast.makeText(requireActivity().getApplicationContext(), "Search with grammar", Toast.LENGTH_LONG).show();
                    // Jump interface
                    textView.setText("");
                    if (IDList == null || IDList.size() == 0) {
                        Intent noResult = new Intent(getContext(), EmptySearchResult.class);
                        startActivity(noResult);
                        return false;
                    } else {
                        Intent postIntent = new Intent(getContext(), SearchedResults.class);
                        postIntent.putExtra("isPost", isPost);
                        postIntent.putExtra("idList", IDList);
                        startActivity(postIntent);
                        return true;
                    }
                } else {
                    // =============================================================================
                    // Search without grammar
                    // =============================================================================

                    Log.println(Log.ASSERT, "DEBUG", "[OnClick] Search without grammar");
                    Toast.makeText(requireActivity().getApplicationContext(), "Search without grammar", Toast.LENGTH_LONG).show();
                    Log.println(Log.ASSERT, "DEBUG", "[OnClick] Search " + PlantTreeManager.PlantInfoType.values()[(int) spinner.getSelectedItemId() - 1]
                            + " with: " + textView.getText().toString().trim());
                    //Search for nodes
                    ArrayList<Integer> IDList = new ArrayList<>();
                    if (!isPost) {
                        ArrayList<Plant> searchResult = PlantTreeManager.getInstance().search(
                                PlantTreeManager.PlantInfoType.values()[(int) spinner.getSelectedItemId() - 1],
                                textView.getText().toString().trim());
                        // try blur search
                        if (searchResult.size() == 0) {
                            String guessValue = ParserEventHandler.getSearchedResultsFromBlurParameter(
                                    PlantTreeManager.PlantInfoType.values()[(int) spinner.getSelectedItemId() - 1],
                                    textView.getText().toString().trim(),
                                    0.5);
                            if (!guessValue.equals("")) {
                                searchResult = PlantTreeManager.getInstance().search(
                                        PlantTreeManager.PlantInfoType.values()[(int) spinner.getSelectedItemId() - 1],
                                        guessValue);
                                GeneralFunctions.getInstance().makeToast("Can not find result, try guessed value: " + guessValue);
                            }
                        }
                        for (Plant node : searchResult) {
                            IDList.add(node.getId());
                        }
                    } else {
                        ArrayList<Post> searchResult = PostTreeManager.getInstance().search(
                                PostTreeManager.PostInfoType.values()[(int) spinner.getSelectedItemId() - 1],
                                textView.getText().toString().trim());
                        // try blur search
                        if (searchResult.size() == 0) {
                            String guessValue = ParserEventHandler.getSearchedResultsFromBlurParameter(
                                    PostTreeManager.PostInfoType.values()[(int) spinner.getSelectedItemId() - 1],
                                    textView.getText().toString().trim(),
                                    0.5);
                            if (!guessValue.equals("")) {
                                searchResult = PostTreeManager.getInstance().search(
                                        PostTreeManager.PostInfoType.values()[(int) spinner.getSelectedItemId() - 1],
                                        guessValue);
                                GeneralFunctions.getInstance().makeToast("Can not find result, try guessed value: " + guessValue);
                            }
                        }
                        for (Post node : searchResult) {
                            IDList.add(node.getPost_id());
                        }
                    }
                    // Jump interface
                    Log.println(Log.ASSERT, "DEBUG", "[OnClick] putExtra: " + IDList.size());
                    textView.setText("");
                    if (IDList.size() == 0) {
                        Intent noResult = new Intent(getContext(), EmptySearchResult.class);
                        startActivity(noResult);
                        return false;
                    } else {
                        // 跳转界面
                        Intent postIntent = new Intent(getContext(), SearchedResults.class);
                        postIntent.putExtra("isPost", isPost);
                        postIntent.putExtra("idList", IDList);
                        startActivity(postIntent);
                        return true;
                    }
                }
            }
        });

        // ===================== Xing Chen:
        // if capture button clicked, will launch camera action, after taking a photo, enter post share page
        captureButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.d("CaptureFragment", "Capture button clicked");
                // launch camera
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Log.d("CaptureFragment", "Request for camera permission");
                    // require for access
                    requestPermissionLauncher.launch(Manifest.permission.CAMERA);
                    Log.d("CaptureFragment", "Camera permission grated.");
                } else {
                    Log.d("CaptureFragment", "call dispatchTakePictureIntent()");
                    // launch camera
                    dispatchTakePictureIntent();
                }
            }
        });

        // ===================== Xing Chen: ends here
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        currentArrayAdapter = null;
    }
    // ===================== Xing Chen: launch camera
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            Log.d("CaptureFragment", "Camera app is available");
            // create file to save image
            File photoFile = null;
            try {
                Log.d("CaptureFragment", "call createImageFile");
                photoFile = createImageFile();
            } catch (IOException ex) {
                // handle error
                Log.d("CaptureFragment", "Error creating file: " + ex.getMessage());
            }
            // continue only if file created
            Log.d("CaptureFragment", "Camera intent is about to launch");
            if (photoFile != null) {
                Log.d("CaptureFragment", "File created: " + photoFile.getAbsolutePath());
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.compendiumofmateriamedica.fileprovider",
                        photoFile);
                Log.d("CaptureFragment", "URI created");
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                Log.d("CaptureFragment", "Launching camera");
                cameraLauncher.launch(takePictureIntent);
            } else {
                Log.d("CaptureFragment", "Failed to create file");
            }
        } else{
            Log.w("CaptureFragment", "takePictureIntent.resolveActivity(getActivity().getPackageManager()) is null");
        }
    }

    private File createImageFile() throws IOException {
        // create a file with unique name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* former */
                ".jpg",         /* latter */
                storageDir      /* storage direction */
        );

        // save file path
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    // ===================== Xing Chen: ends here
}