package com.example.compendiumofmateriamedica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;


import java.util.ArrayList;

import model.Datastructure.PlantTreeManager;
import model.Datastructure.PostTreeManager;
import model.Datastructure.RBTreeNode;
import model.Datastructure.User;
import model.Datastructure.UserTreeManager;


/**
 * @author: Tianhao Shan
 * @uid: u7709429
 * @datetime: 2024/05/16
 * @description: Activity to login
 */

public class LoginActivity extends AppCompatActivity {
    // UI element
    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;

    private LoginViewModel loginViewModel;

    // TreeManager based on Singleton Pattern, global access throughout the app
    private UserTreeManager userTreeManager;
    private PostTreeManager postTreeManager;
    private PlantTreeManager plantTreeManager;

    // User logged in
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI elements
        editTextEmail = findViewById(R.id.editTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // Set a click listener for the login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize tree managers here
                userTreeManager = UserTreeManager.getInstance();
                // Retrieve entered email and password
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                // Check that the username or password cannot be empty
                if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
                    // Failed login
                    Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    // TODO: (EMPTY INPUT) USER ID 5 FOR TESTING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    ArrayList<User> users = userTreeManager.search(UserTreeManager.UserInfoType.ID, 5);
                    if (!users.isEmpty()) {
                        user = users.get(0);
                    }
                    startMainActivity(user);
                    // TODO: FOR TESTING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                } else {
                    // Implement authentication logic here
                    // email:user1@test.com password:111111
                    loginViewModel.login(email, password).observe(LoginActivity.this, new Observer<FirebaseUser>() {
                        @Override
                        public void onChanged(FirebaseUser firebaseUser) {
                            if (firebaseUser != null) {
                                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                ArrayList<User> users = userTreeManager.search(UserTreeManager.UserInfoType.EMAIL, firebaseUser.getEmail());
                                user = users.get(0);
                                startMainActivity(user);
                            } else {
                                Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void startMainActivity(User user) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        // Pass user object as extra to MainActivity
        intent.putExtra("User", user);
        // Set flags to clear task and create new task
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        // Finish LoginActivity
        finish();
    }
}
