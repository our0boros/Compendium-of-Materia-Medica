package com.example.compendiumofmateriamedica;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import model.Datastructure.PlantTreeManager;
import model.Datastructure.PostTreeManager;
import model.Datastructure.RBTreeNode;
import model.Datastructure.User;
import model.Datastructure.UserTreeManager;
import model.FirebaseAuthManager;

/**
 * @author: Tianhao Shan
 * @datetime: 2024/4/24
 * @description: Login Page
 */

public class LoginActivity extends AppCompatActivity {
    // UI element
    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;

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

        // Set a click listener for the login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize tree managers here
                userTreeManager=UserTreeManager.getInstance();
                // Retrieve entered email and password
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                // Check that the username or password cannot be empty
                if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
                    // Failed login
                    Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    // TODO: (EMPTY INPUT) USER ID 5 FOR TESTING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    ArrayList<RBTreeNode<User>> users = userTreeManager.search(UserTreeManager.UserInfoType.ID, 5);
                    if(!users.isEmpty()){
                        user = users.get(0).getValue();
                    }
                    startMainActivity(user);
                    // TODO: FOR TESTING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                } else {
                    // Implement authentication logic here
                    // email:user1@test.com password:111111
                    FirebaseAuth firebaseAuth = FirebaseAuthManager.getInstance().getmAuth();
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Firebase Login successful", Toast.LENGTH_SHORT).show();
                            // TODO: 创建一个用户虚拟类class User, 将这个类的putExtra 到 Main 下面，后续会用到
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference usersRef = database.getReference("users");

                            String emailToSearch = email; // 你要查询的电子邮箱地址

                            usersRef.orderByChild("email").equalTo(emailToSearch)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                    Log.d("FirebaseTest", "DataSnapshot: " + snapshot.getValue());
                                                    User user = snapshot.getValue(User.class);
                                                    // 处理用户数据，例如打印信息
                                                    Log.d("UserData", "User ID: " + user.getUsername() + ", Username: " + user.getUser_id());
                                                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                                    startMainActivity(user);
                                                    finish();
                                                }
                                            } else {
                                                Log.d("UserData", "No user found with email " + emailToSearch);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Log.w("UserData", "loadUser:onCancelled", databaseError.toException());
                                        }
                                    });
                        } else {
                            // Failed login
                            Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
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
