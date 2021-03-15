package com.weeidl.gogodag.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.PagerAdapter;
import com.weeidl.gogodag.R;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private List places[];
    private Context mContext;
    private boolean isLoading = false;

    public ViewPagerAdapter(Context mContext, List[] places){
        this.mContext = mContext;
        this.places = places;
    }

    @Override
    public int getCount() {
        return places.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        RecyclerView recyclerView;
        GridLayoutManager manager;
        PlacesAdapter adapter;

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.recycler_view, container,
                false);

        recyclerView = itemView.findViewById(R.id.rv);
        manager = new GridLayoutManager(mContext, 2);
        adapter = new PlacesAdapter(mContext, places[position]);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = manager.getChildCount();
                int totalItemCount = manager.getItemCount();
                int firstVisibleItems = manager.findFirstVisibleItemPosition();

                if (!isLoading) {
                    if ( (visibleItemCount+firstVisibleItems) >= totalItemCount) {
                        adapter.addItems(places[position]);
                        //isLoading = true;
                    }
                }
            }
        });

        container.addView(itemView);

        return recyclerView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
