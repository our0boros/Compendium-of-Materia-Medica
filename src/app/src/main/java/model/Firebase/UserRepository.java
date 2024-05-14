package model.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UserRepository {
    private FirebaseAuth firebaseAuth;

    public UserRepository() {
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    public LiveData<FirebaseUser> login(String email, String password) {
        MutableLiveData<FirebaseUser> userLiveData = new MutableLiveData<>();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        userLiveData.setValue(firebaseAuth.getCurrentUser());
                    } else {
                        userLiveData.setValue(null);
                    }
                });
        return userLiveData;
    }
}
