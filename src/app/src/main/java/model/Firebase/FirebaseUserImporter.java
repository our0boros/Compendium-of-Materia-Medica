package model.Firebase;

import android.content.Context;
import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


/**
 * This class is responsible for importing user data from a JSON resource file
 * and creating users in Firebase Authentication.
 *
 * @author: Yusi Zhong
 * @datetime: 2024/5
 * @description: A utility class to create Firebase users from a JSON file.
 */
public class FirebaseUserImporter {
    private FirebaseAuth mAuth;
    private Context mContext; // Context to access application assets

    /**
     * Constructor to initialize FirebaseAuth and context.
     * @param context Context to access application assets.
     */
    public FirebaseUserImporter(Context context) {
        this.mAuth = FirebaseAuth.getInstance();
        this.mContext = context;
    }

    /**
     * Method to create users from a JSON resource file.
     * @param resourceId Resource ID of the JSON file containing user data.
     */
    public void createUsersFromJson(int resourceId) {
        // Run the process in a new thread to avoid blocking the main thread.
        new Thread(() -> {
            try {
                // Read JSON from resource file.
                InputStream is = mContext.getResources().openRawResource(resourceId);
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                is.close();

                // Parse JSON data.
                JSONArray usersArray = new JSONArray(builder.toString());
                for (int i = 0; i < usersArray.length(); i++) {
                    JSONObject userObject = usersArray.getJSONObject(i);
                    String email = userObject.getString("email");
                    String password = userObject.getString("password");
                    createUser(email, password); // Create user with parsed email and password.
                }
            } catch (IOException | JSONException e) {
                Log.e("UserImporter", "Error parsing JSON or reading file", e);
            }
        }).start();
    }

    /**
     * Method to create a single user with email and password.
     * @param email User's email address.
     * @param password User's password.
     */
    private void createUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Registration successful.
                        FirebaseUser user = mAuth.getCurrentUser();
                        System.out.println("User created successfully: " + user.getEmail());
                    } else {
                        // If registration fails, display a message to the user.
                        System.out.println("Failed to create user: " + task.getException().getMessage());
                    }
                });
    }
}
