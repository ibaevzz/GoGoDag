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

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText email;
    private EditText password;
    private EditText confirm;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.logbar);
        setActionBar(toolbar);

        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirm = findViewById(R.id.confirm);
        login = findViewById(R.id.login);

        login.setOnClickListener(v -> {
            if (password.getText().toString().equals(confirm.getText().toString())){
                regWithEmail(email.getText().toString(), password.getText().toString());
            } else {
                Toast.makeText(this, "Passwords mismatch", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void regWithEmail(String email, String password){
        auth.createUserWithEmailAndPassword(email, password)
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