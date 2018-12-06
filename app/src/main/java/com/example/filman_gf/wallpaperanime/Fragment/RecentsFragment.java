package com.example.filman_gf.wallpaperanime.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.filman_gf.wallpaperanime.Adapter.MyRecyclerAdapter;
import com.example.filman_gf.wallpaperanime.Database.DataSource.RecentRepository;
import com.example.filman_gf.wallpaperanime.Database.LocalDatabase.LocalDatabase;
import com.example.filman_gf.wallpaperanime.Database.LocalDatabase.RecentsDataSource;
import com.example.filman_gf.wallpaperanime.Database.Recents;
import com.example.filman_gf.wallpaperanime.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecentsFragment extends Fragment {

    private static RecentsFragment INSTANCE=null;

    RecyclerView recyclerView;

    Context context;

    List<Recents> recentsList;
    MyRecyclerAdapter adapter;

    //Room Database
    CompositeDisposable compositeDisposable;
    RecentRepository recentRepository;


    @SuppressLint("ValidFragment")
    public RecentsFragment(Context context) {
        // Required empty public constructor
        this.context = context;

        //Init RoomDatabase
        compositeDisposable = new CompositeDisposable();
        LocalDatabase database = LocalDatabase.getInstance(context);
        recentRepository = RecentRepository.getInstance(RecentsDataSource.getInstance(database.recentsDAO()));
    }

    public static RecentsFragment getInstance(Context context)
    {
        if (INSTANCE ==null)
            INSTANCE = new RecentsFragment(context);
        return INSTANCE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Mengembangkan tata letak untuk fragmen ini
        View view =  inflater.inflate(R.layout.fragment_recents, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_recent);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recentsList = new ArrayList<>();
        adapter = new MyRecyclerAdapter(context,recentsList);
        recyclerView.setAdapter(adapter);

        loadRecents();

        return view;
    }

    private void loadRecents() {
        Disposable disposable = recentRepository.getAllRecents()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Recents>>() {
                    @Override
                    public void accept(List<Recents> recents) throws Exception {
                        onGetAllRecentsSucces(recents);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("ERROR",throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void onGetAllRecentsSucces(List<Recents> recents) {
        recentsList.clear();
        recentsList.addAll(recents);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
