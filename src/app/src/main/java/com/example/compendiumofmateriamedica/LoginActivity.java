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
import java.util.ArrayList;

import model.DataType;
import model.FirebaseAuthManager;
import model.GeneratorFactory;
import model.Plant;
import model.Post;
import model.RBTree;
import model.RBTreeNode;
import model.TreeManager;
import model.User;
import model.UserTreeManager;

/**
 * @author: Tianhao Shan
 * @datetime: 2024/4/24
 * @description: Login Page
 */

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;



    private RBTree<User> userTree;
    private RBTree<Plant> plantTree;
    private RBTree<Post> postTree;
    // 开发用的，这行可删
    private UserTreeManager userTreeManager;



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
        // 开发用的，这行可删
        userTreeManager = new UserTreeManager(userTree);

        // Initialize UI elements
        editTextEmail = findViewById(R.id.editTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        // Set a click listener for the login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve entered username and password
                String username = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                // 添加一个全空白时直接跳转
                if (username == null || password == null ||
                    username.isEmpty() || password.isEmpty()) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                    // 这段可删，XingChen:这里开发的时候默认是一个指定用户登录的吧，传入后面的界面，后面搞好了可以改
                    ArrayList<RBTreeNode<User>> users = userTreeManager.search(UserTreeManager.UserInfoType.ID, 5);
                    if(!users.isEmpty()){
                        User user = users.get(0).getValue();
                        intent.putExtra("User", user);
                    }

                    startActivity(intent);
                    // Failed login
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                } else {

                    // Implement authentication logic here
                    // username:user1@test.com password:111111
                    FirebaseAuth firebaseAuth = FirebaseAuthManager.getInstance().getmAuth();
                    firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(LoginActivity.this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            // TODO: 创建一个用户虚拟类class User, 将这个类的putExtra 到 Main 下面，后续会用到
                            ArrayList<RBTreeNode<User>> users = userTreeManager.search(UserTreeManager.UserInfoType.EMAIL, username);
                            User user = users.get(0).getValue();
                            intent.putExtra("User", user);
                            startActivity(intent);
                        } else {
                            // Failed login
                            Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
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
