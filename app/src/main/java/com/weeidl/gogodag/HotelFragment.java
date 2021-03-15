package com.weeidl.gogodag;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.weeidl.gogodag.adapter.HotelAdapter;
import com.weeidl.gogodag.model.RecentsData;
import java.util.ArrayList;
import java.util.List;

public class HotelFragment extends Fragment {

    private List hotels;
    private boolean loading = true;
    private boolean isLoading = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hotels = new ArrayList<RecentsData>();

        hotels.add(new RecentsData("Hotel1", "", null, "здесь может быть ваша реклама", 5, R.drawable.hotel11, R.drawable.hotel11));
        hotels.add(new RecentsData("Hotel2", null, null, "здесь может быть ваша реклама", 4, R.drawable.hotel12, R.drawable.hotel11));
        hotels.add(new RecentsData("Hotel3", null, null, "здесь может быть ваша реклама", 3, R.drawable.hotel21, R.drawable.hotel11));
        hotels.add(new RecentsData("Hotel4", null, null, "здесь может быть ваша реклама", 2, R.drawable.hotel22, R.drawable.hotel11));
        hotels.add(new RecentsData("Hotel5", null, null, "здесь может быть ваша реклама", 5, R.drawable.hotel11, R.drawable.hotel11));
        hotels.add(new RecentsData("Hotel6", null, null, "здесь может быть ваша реклама", 4, R.drawable.hotel12, R.drawable.hotel11));
        hotels.add(new RecentsData("Hotel7", null, null, "здесь может быть ваша реклама", 3, R.drawable.hotel21, R.drawable.hotel11));
        hotels.add(new RecentsData("Hotel8", null, null, "здесь может быть ваша реклама", 2, R.drawable.hotel22, R.drawable.hotel11));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hotel, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle){
        RecyclerView hotels_rv;
        GridLayoutManager manager;
        HotelAdapter adapter;
        ProgressBar progressBar;

        manager = new GridLayoutManager(view.getContext(), 2);
        adapter = new HotelAdapter(hotels);
        progressBar = view.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);

        hotels_rv = view.findViewById(R.id.hotels);
        new Handler().post(() -> {
            hotels_rv.setLayoutManager(manager);
            hotels_rv.setAdapter(adapter);
            hotels_rv.animate();
            progressBar.setVisibility(View.INVISIBLE);
        });

        hotels_rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = manager.getChildCount();
                int totalItemCount = manager.getItemCount();
                int firstVisibleItems = manager.findFirstVisibleItemPosition();

                if (!isLoading) {
                    if ( (visibleItemCount+firstVisibleItems) >= totalItemCount) {
                        adapter.addItems(hotels);
                        isLoading = true;
                    }
                }
            }
        });

    }
}