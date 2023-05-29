package com.example.rpodmp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.rpodmp.R;
import com.example.rpodmp.entities.Product;
import com.example.rpodmp.entities.Recipe;
import com.example.rpodmp.extensions.Extension;

import java.util.List;

public class RecipeListFragment extends Fragment {

    public static interface RecipeListListener {
        void onItemClicked(Recipe recipe);
    }

    public int currentItemPosition;

    private RecipeRecyclerViewAdapter mAdapter;

    public RecipeListListener recipeListListener;

    public void setAdapterItems(List<Recipe> recipes){
        mAdapter.setItems(recipes);
    }

    public RecipeRecyclerViewAdapter getAdapter(){
        return mAdapter;
    }

    public RecipeListFragment() {
        mAdapter = new RecipeRecyclerViewAdapter();
        mAdapter.setOnLongItemClickListener((v, position) -> currentItemPosition = position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            recyclerView.setAdapter(mAdapter);
            registerForContextMenu(recyclerView);
            mAdapter.itemOnClickListener = v -> {
                int itemPosition = recyclerView.getChildLayoutPosition(v);
                Recipe recipe = mAdapter.getItems().get(itemPosition);
                recipeListListener.onItemClicked(recipe);
            };

            registerForContextMenu(recyclerView);
            mAdapter.setOnLongItemClickListener((v, position) -> {
                currentItemPosition = position;
                v.showContextMenu();
            });
        }
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.recipeListListener = (RecipeListListener)activity;
    }
}