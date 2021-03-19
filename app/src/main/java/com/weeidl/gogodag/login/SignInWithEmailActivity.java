package com.weeidl.gogodag.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.weeidl.gogodag.MainActivity;
import com.weeidl.gogodag.R;

public class SignInWithEmailActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText email;
    private EditText password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_with_email);

        Toolbar toolbar = findViewById(R.id.sigbar);
        setActionBar(toolbar);

        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.signInEmail);
        password = findViewById(R.id.signInPassword);
        login = findViewById(R.id.signInWithEmail);

        login.setOnClickListener(v -> {
            signInWithEmail(email.getText().toString(), password.getText().toString());
        });
    }

    private void signInWithEmail(String email, String password){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            updateUI(user);
                            startActivity(new Intent(this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                });
    }

    private void updateUI(FirebaseUser user) {

    }
}