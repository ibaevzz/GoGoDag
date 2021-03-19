package com.weeidl.gogodag.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.weeidl.gogodag.R;
import com.weeidl.gogodag.adapter.GuideAdapter;
import com.weeidl.gogodag.adapter.HotelAdapter;
import com.weeidl.gogodag.model.RecentsData;
import java.util.ArrayList;
import java.util.List;

public class GuideFragment extends Fragment {

    private List guide;
    private boolean isLoading = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        guide = new ArrayList<RecentsData>();

        guide.add(new RecentsData("Татьяна", null, null, "здесь может быть ваша реклама", 5, R.drawable.gid1));
        guide.add(new RecentsData("Миша", null, null, "здесь может быть ваша реклама", 4, R.drawable.gid2));
        guide.add(new RecentsData("Зюма", null, null, "здесь может быть ваша реклама", 3, R.drawable.gid3));
        guide.add(new RecentsData("Ася", null, null, "здесь может быть ваша реклама", 2, R.drawable.gid4));
        guide.add(new RecentsData("Камилла", null, null, "здесь может быть ваша реклама", 5, R.drawable.gid5));
        guide.add(new RecentsData("Курбан", null, null, "здесь может быть ваша реклама", 4, R.drawable.gid6));
        guide.add(new RecentsData("Артур(я иду за тобой, Артур)", null, null, "здесь может быть ваша реклама", 3, R.drawable.gid8));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guide, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle){
        RecyclerView guide_rv;
        GridLayoutManager manager;
        GuideAdapter adapter;
        ProgressBar progressBar;

        manager = new GridLayoutManager(view.getContext(), 2);
        adapter = new GuideAdapter(guide);
        progressBar = view.findViewById(R.id.progressBarGuide);

        progressBar.setVisibility(View.VISIBLE);

        guide_rv = view.findViewById(R.id.guide);
        new Handler().post(() -> {
            guide_rv.setLayoutManager(manager);
            guide_rv.setAdapter(adapter);
            progressBar.setVisibility(View.INVISIBLE);
        });

        guide_rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = manager.getChildCount();
                int totalItemCount = manager.getItemCount();
                int firstVisibleItems = manager.findFirstVisibleItemPosition();

                if (!isLoading) {
                    if ( (visibleItemCount+firstVisibleItems) >= totalItemCount) {
                        adapter.addItems(guide);
                        isLoading = true;
                    }
                }
            }
        });

    }
}