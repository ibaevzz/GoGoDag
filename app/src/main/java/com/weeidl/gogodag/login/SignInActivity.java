package com.weeidl.gogodag.login;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toolbar;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.weeidl.gogodag.MainActivity;
import com.weeidl.gogodag.R;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private GoogleSignInClient client;
    private SignInButton signIn;
    private Button regButton;
    private Button signInWithEmail;
    public static final int SIGN_WITH_GOOGLE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        auth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.bar);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        client = GoogleSignIn.getClient(this, gso);

        signIn = findViewById(R.id.google_button);
        regButton = findViewById(R.id.reg);
        signInWithEmail = findViewById(R.id.sign_in);

        signIn.setOnClickListener(v -> {
            signIn();
        });

        regButton.setOnClickListener(v ->{
            startActivity(new Intent(this, LoginActivity.class));
        });

        signInWithEmail.setOnClickListener(v -> {
            startActivity(new Intent(this, SignInWithEmailActivity.class));
        });

        setActionBar(toolbar);

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser == null){
            signIn();
        }
        updateUI(currentUser);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_WITH_GOOGLE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account = null;
            try {
                account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } catch (ApiException ignored) {
                Log.i("zubai", ignored.getMessage());
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        updateUI(user);
                    } else {
                        updateUI(null);
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = client.getSignInIntent();
        startActivityForResult(signInIntent, SIGN_WITH_GOOGLE);
    }

    private void updateUI(FirebaseUser user) {

    }

}