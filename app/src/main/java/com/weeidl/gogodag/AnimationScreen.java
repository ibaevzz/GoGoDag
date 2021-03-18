package com.weeidl.gogodag;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.weeidl.gogodag.secrettextview.SecretTextView;
import java.util.Timer;
import java.util.TimerTask;

public class AnimationScreen extends AppCompatActivity {

    private  FirebaseAuth auth;

    LottieAnimationView lottieAnimationView;
    SecretTextView go;
    SecretTextView travel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_screen);

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() == null){
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        }

        go = findViewById(R.id.textView6);
        travel = findViewById(R.id.textView);
        lottieAnimationView = findViewById(R.id.lottie);

        go.setDuration(1500);
        travel.setDuration(1500);
        go.show();
        travel.show();

        Intent intent = new Intent(this, MainActivity.class);

        Timer mTimer = new Timer();
        mTimer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                startActivity(intent);
                finish();
            }
        }, 1500);


    }
}