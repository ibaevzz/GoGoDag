package com.weeidl.gogodag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {

    private ChipNavigationBar chipNavigationBar;
    private Fragment fragment=null;
    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;

        fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();

        chipNavigationBar = findViewById(R.id.chipNavigation);

        chipNavigationBar.setItemSelected(R.id.home, true);

        chipNavigationBar.setOnItemSelectedListener(i -> {
            switch (i){
                case R.id.home:
                    fragment=new HomeFragment();
                    break;
                case R.id.cart:
                    fragment=new HotelFragment();
                    break;
                case R.id.profile:
                    fragment=new ProfileFragment();
                    break;
                case R.id.gid:
                    fragment=new GuideFragment();
                    break;
            }
            if (fragment!=null){
                getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
            }
        });

    }



}