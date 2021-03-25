package com.weeidl.gogodag.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.weeidl.gogodag.AnimationScreen;
import com.weeidl.gogodag.R;
import com.weeidl.gogodag.adapter.ViewPagerAdapter;
import com.weeidl.gogodag.login.SignInActivity;
import com.weeidl.gogodag.model.RecentsData;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private FirebaseAuth auth;
    private List places, guides, hotels;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        places = new ArrayList();
        guides = new ArrayList();
        hotels = new ArrayList();
        places.add(new RecentsData("wfg", "gerg", "egre", "erger", 5, R.drawable.hotel22));
        guides.add(new RecentsData("wfg", "gerg", "egre", "erger", 5, R.drawable.hotel22));
        hotels.add(new RecentsData("wfg", "gerg", "egre", "erger", 5, R.drawable.hotel22));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;

        view = inflater.inflate(R.layout.fragment_profile, container, false);

        view.findViewById(R.id.logout).setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(getContext(), SignInActivity.class));
            getActivity().finish();
        });

        ((TextView)view.findViewById(R.id.profileName)).setText(auth.getCurrentUser().getDisplayName());
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle saved){

        TabLayout tabLayout = view.findViewById(R.id.tabs);

        ViewPager viewPager = view.findViewById(R.id.my);
        ProgressBar progressBar = view.findViewById(R.id.progress);

        progressBar.setVisibility(View.VISIBLE);

        new Handler().post(() -> {
            viewPager.setAdapter(new ViewPagerAdapter(getContext(), new List[]{places, hotels, guides}));
            progressBar.setVisibility(View.INVISIBLE);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.getTabAt(0).setText("My tours");
            tabLayout.getTabAt(1).setText("My hotels");
            tabLayout.getTabAt(2).setText("My guides");
        });

    }
}