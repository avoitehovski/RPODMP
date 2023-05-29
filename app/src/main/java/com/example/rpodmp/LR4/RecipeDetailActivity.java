package com.example.rpodmp.LR4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.rpodmp.R;
import com.example.rpodmp.databinding.ActivityNewProductFirstStepBinding;
import com.example.rpodmp.databinding.ActivityRecipeDetailBinding;
import com.example.rpodmp.entities.Product;
import com.example.rpodmp.entities.Recipe;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Bundle arguments = getIntent().getExtras();
        ActivityRecipeDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_detail);
        Recipe recipe = (Recipe)arguments.getSerializable(Recipe.class.getSimpleName());
        binding.setRecipe(recipe);
    }
}