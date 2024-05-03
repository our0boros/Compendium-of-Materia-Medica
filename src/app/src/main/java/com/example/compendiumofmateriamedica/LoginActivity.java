package com.example.compendiumofmateriamedica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;

import java.io.IOException;

import model.DataType;
import model.FirebaseAuthManager;
import model.GeneratorFactory;
import model.Plant;
import model.Post;
import model.RBTree;
import model.User;

/**
 * @author: Tianhao Shan
 * @datetime: 2024/4/24
 * @description: Login Page
 */

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;



    private RBTree<User> userTree;
    private RBTree<Plant> plantTree;
    private RBTree<Post> postTree;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 运行加载数据的函数
        try {
            DataInitial();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Initialize UI elements
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        // Set a click listener for the login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve entered username and password
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                // Implement authentication logic here
                FirebaseAuth firebaseAuth = FirebaseAuthManager.getInstance().getmAuth();
                firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(LoginActivity.this, task ->{
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        // TODO: 创建一个用户虚拟类class User, 将这个类的putExtra 到 Main 下面，后续会用到
                        startActivity(intent);
                    }
                    else {
                        // Failed login
                        Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /*
     * @author: Haochen Gong
     * 加载数据
     */
    private void DataInitial() throws JSONException, IOException {
        userTree = (RBTree<User>) GeneratorFactory.tree(this, DataType.USER, R.raw.users);
        plantTree = (RBTree<Plant>) GeneratorFactory.tree(this, DataType.PLANT, R.raw.plants);
        postTree = (RBTree<Post>) GeneratorFactory.tree(this, DataType.POST, R.raw.posts);
    }
}
