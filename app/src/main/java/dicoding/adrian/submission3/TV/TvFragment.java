package dicoding.adrian.submission3.TV;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import dicoding.adrian.submission3.Movie.MainViewModelMovie;
import dicoding.adrian.submission3.Movie.MovieAdapter;
import dicoding.adrian.submission3.Movie.MovieItems;
import dicoding.adrian.submission3.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvFragment extends Fragment {

    // Variables
    RecyclerView rvTv;
    private TvAdapter adapter;
    private ProgressBar progressBar;
    private MainViewModelTv mainViewModelTv;

    // Empty Constructor
    public TvFragment() {
        // Required empty public constructor
    }

    // Observer
    private Observer<ArrayList<TvItems>> getTvs = new Observer<ArrayList<TvItems>>() {
        @Override
        public void onChanged(ArrayList<TvItems> tvItems) {
            if (tvItems != null) {
                adapter.setData(tvItems);
                showLoading(false);
            }
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Adapter Instance
        adapter = new TvAdapter();
        adapter.notifyDataSetChanged();

        // Cast Recyclerview
        rvTv = view.findViewById(R.id.rv_tv);

        // Layout Manager
        rvTv.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Divider between item list
        DividerItemDecoration itemDecorator = new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.divider)));
        rvTv.addItemDecoration(itemDecorator);
        rvTv.setHasFixedSize(true);

        //Set Adapter
        rvTv.setAdapter(adapter);

        // Progress Bar Declaration
        progressBar = view.findViewById(R.id.progressBar_tv);
        progressBar.bringToFront();

        //Intent to Detail Activity
        adapter.setOnItemClickListener(new TvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TvItems tvItems) {
                Toast.makeText(getActivity(), tvItems.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // MainViewModel Instance
        mainViewModelTv = ViewModelProviders.of(getActivity()).get(MainViewModelTv.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Observer
        mainViewModelTv.getTvs().observe(getActivity(), getTvs);

        // Display The Items
        mainViewModelTv.setTv();
        showLoading(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Unsubscribing The Observer
        mainViewModelTv.getTvs().removeObserver(getTvs);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv, container, false);
    }

    // Methods

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
