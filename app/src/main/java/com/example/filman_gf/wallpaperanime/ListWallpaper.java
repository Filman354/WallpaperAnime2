package com.example.filman_gf.wallpaperanime;

import android.content.ClipData;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.filman_gf.wallpaperanime.Common.Common;
import com.example.filman_gf.wallpaperanime.Interface.ItemClickListener;
import com.example.filman_gf.wallpaperanime.Model.WallpaperItem;
import com.example.filman_gf.wallpaperanime.ViewHolder.ListWallpaperViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class ListWallpaper extends AppCompatActivity {

    Query query;
    FirebaseRecyclerOptions<WallpaperItem> options;
    FirebaseRecyclerAdapter<WallpaperItem,ListWallpaperViewHolder> adapter;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_wallpaper);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(Common.CATEGORY_SELECTED);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_list_wallpaper);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        loadBackgroundList();
    }

    private void loadBackgroundList() {
        query = FirebaseDatabase.getInstance().getReference(Common.STR_WALLPAPER)
                .orderByChild("categoryId").equalTo(Common.CATEGORY_ID_SELECTED);
        options = new FirebaseRecyclerOptions.Builder<WallpaperItem>()
                .setQuery(query,WallpaperItem.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<WallpaperItem, ListWallpaperViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ListWallpaperViewHolder holder, int position, @NonNull final WallpaperItem model) {
                Picasso.with(getBaseContext())
                                        .load(model.getImageLink())
                                        .networkPolicy(NetworkPolicy.OFFLINE)
                                        .into(holder.wallpaper, new Callback() {
                                            @Override
                                            public void onSuccess() {

                                            }

                                            @Override
                                                public void onError() {
                                                Picasso.with(getBaseContext())
                                                        .load(model.getImageLink())
                                                        .error(R.drawable.ic_terrain_black_24dp)
                                                        .into(holder.wallpaper, new Callback() {
                                                            @Override
                                                            public void onSuccess() {

                                                            }

                                                            @Override
                                                            public void onError() {
                                                                Log.e("ERROR_N3KODEV","Tidak bisa mengambil gambar");
                                                            }
                                                        });

                            }
                        });

                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        Intent intent = new Intent(ListWallpaper.this,ViewWallpaper.class);
                        Common.select_background = model;
                        Common.select_background_key = adapter.getRef(position).getKey();
                        startActivity(intent);
                    }
                });

            }

            @Override
            public ListWallpaperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_wallpaper_item, parent,false);
                int height = parent.getMeasuredHeight() / 2;
                itemView.setMinimumHeight(height);
                return new ListWallpaperViewHolder(itemView);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    //CTRL+O
    @Override
    protected void onStart() {
        super.onStart();
        if (adapter!=null)
            adapter.startListening();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (adapter!=null)
            adapter.startListening();
    }

    @Override
    protected void onStop() {
        if (adapter!=null)
            adapter.stopListening();
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish(); //menutup aplikasi ketika kembali
        return super.onOptionsItemSelected(item);
    }
}
