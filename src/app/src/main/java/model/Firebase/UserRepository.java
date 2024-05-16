package model.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


/**
 * @author: Yusi Zhong
 * @datetime: 2024/5
 * @description: Manages user authentication and login with Firebase.
 */
public class UserRepository {
    private FirebaseAuth firebaseAuth;

    /**
     * Constructor to initialize FirebaseAuth instance.
     */
    public UserRepository() {
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    /**
     * Method to log in a user using email and password.
     * @param email User's email address
     * @param password User's password
     * @return LiveData<FirebaseUser> LiveData object with the authenticated user
     */
    public LiveData<FirebaseUser> login(String email, String password) {
        MutableLiveData<FirebaseUser> userLiveData = new MutableLiveData<>();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Login successful, update LiveData with current user
                        userLiveData.setValue(firebaseAuth.getCurrentUser());
                    } else {
                        // Login failed, update LiveData with null
                        userLiveData.setValue(null);
                    }
                });
        return userLiveData;
    }
}
