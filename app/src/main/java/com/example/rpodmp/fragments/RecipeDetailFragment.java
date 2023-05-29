package com.example.rpodmp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rpodmp.R;
import com.example.rpodmp.adapters.BindingAdapter;
import com.example.rpodmp.entities.Recipe;
import com.example.rpodmp.extensions.Extension;

public class RecipeDetailFragment extends Fragment {

    public static final String ARG_RECIPE = "recipe-instance";

    private Recipe mValue;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    public static RecipeDetailFragment newInstance(Recipe recipe){
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECIPE, recipe);
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mValue = (Recipe)getArguments().getSerializable(ARG_RECIPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null && mValue != null) {
            TextView nameTextView = view.findViewById(R.id.recipeNameTextView);
            TextView ingredientTextView = view.findViewById(R.id.recipeIngredientsTextView);
            TextView timeTextView = view.findViewById(R.id.recipeTimeTextView);
            TextView descriptionTextView = view.findViewById(R.id.recipeDescriptionTextView);
            TextView categoryTextVIew = view.findViewById(R.id.recipeCategoryTextView);
            ImageView imageView = view.findViewById(R.id.recipeImageView);

            nameTextView.setText(mValue.getName());
            ingredientTextView.setText(mValue.getIngredients());
            timeTextView.setText(Extension.fromMinutesToHHmm(mValue.getTime()));
            descriptionTextView.setText(mValue.getDescription());
            BindingAdapter.recipeCategory(categoryTextVIew, mValue.getCategory());
            BindingAdapter.loadImageFromStorage(imageView, mValue.getImagePath());
        }
    }
}