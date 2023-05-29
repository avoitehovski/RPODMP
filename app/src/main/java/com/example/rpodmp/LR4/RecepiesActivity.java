package com.example.rpodmp.LR4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.rpodmp.R;
import com.example.rpodmp.database.Server;
import com.example.rpodmp.entities.Product;
import com.example.rpodmp.entities.Recipe;
import com.example.rpodmp.extensions.Extension;
import com.example.rpodmp.fragments.RecipeDetailFragment;
import com.example.rpodmp.fragments.RecipeListFragment;
import com.example.rpodmp.fragments.RecipeRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecepiesActivity extends AppCompatActivity
        implements RecipeListFragment.RecipeListListener {

    private List<Recipe> recipeList;
    private List<Recipe> displayList;

    private RecipeListFragment listFragment;

    private RecipeRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepise);
        recipeList = new ArrayList<>();
        displayList = new ArrayList<>();
        if (findViewById(R.id.recipeListFragmentContainer) != null){
            listFragment = new RecipeListFragment();
            listFragment.setAdapterItems(displayList);
            adapter = listFragment.getAdapter();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.recipeListFragmentContainer, listFragment);
            ft.commit();
        }

        try {
            loadProducts();
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null)
            setIntent(intent);
    }

    @Override
    protected void onResume() {
        Bundle arguments = getIntent().getExtras();
        if (arguments != null){
            Recipe recipe = (Recipe)arguments.getSerializable("newRecipe");
            boolean isEdit = arguments.getBoolean("IS_EDIT");
            if (!isEdit) {
                recipeList.add(recipe);
                displayList.add(recipe);
                adapter.notifyDataSetChanged();
                saveProducts();
            } else {
                recipeList.remove(listFragment.currentItemPosition);
                displayList.remove(listFragment.currentItemPosition);
                recipeList.add(listFragment.currentItemPosition, recipe);
                displayList.add(listFragment.currentItemPosition, recipe);
                adapter.notifyDataSetChanged();
                saveProducts();
            }
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        saveProducts();
        super.onDestroy();
    }

    @Override
    public void onItemClicked(Recipe recipe) {
        if (findViewById(R.id.recipeFragmentContainer) != null) {
            RecipeDetailFragment details = RecipeDetailFragment.newInstance(recipe);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.recipeFragmentContainer, details);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        } else {
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtra(Recipe.class.getSimpleName(), recipe);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.recipes_menu_bar, menu);

        MenuItem searchItem = menu.findItem(R.id.recipeSearch);

        if (searchItem != null) {
            SearchView searchView = (SearchView) searchItem.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (!newText.isEmpty()) {
                        displayList.clear();
                        String search = newText.toLowerCase(Locale.getDefault());
                        for (Recipe recipe : recipeList) {
                            if (recipe.getName().toLowerCase(Locale.getDefault()).contains(search)) {
                                displayList.add(recipe);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        displayList.clear();
                        displayList.addAll(recipeList);
                        adapter.notifyDataSetChanged();
                    }
                    return true;
                }
            });
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.recipeNew) {
            Intent intent = new Intent(this, RecipeEditActivity.class);
            intent.putExtra(Recipe.class.getSimpleName(), new Recipe());
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete_context) {
            recipeList.remove(listFragment.currentItemPosition);
            displayList.remove(listFragment.currentItemPosition);
            adapter.notifyDataSetChanged();
        }

        if (id == R.id.edit_context) {
            Recipe recipe = displayList.get(listFragment.currentItemPosition);
            Intent intent = new Intent(this, RecipeEditActivity.class);
            intent.putExtra(Recipe.class.getSimpleName(), recipe);
            intent.putExtra("IS_EDIT", true);
            startActivity(intent);
        }

        return true;
    }

    private void loadProducts() throws JSONException {
        File file = new File(getFilesDir(),"recipes.json");
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null){
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            JSONArray array = new JSONArray(stringBuilder.toString());
            for (int i = 0; i < array.length(); i++) {
                Recipe recipe = new Recipe(array.getJSONObject(i));
                recipeList.add(recipe);
                displayList.add(recipe);
            }
        } catch (IOException exception) { }
    }

    private void saveProducts() {
        List<Recipe> recipes = recipeList;
        try {
            JSONArray array = new JSONArray();
            for (Recipe recipe : recipes) {
                array.put(recipe.toJSON());
            }
            File file = new File(getFilesDir(), "recipes.json");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(array.toString());
            bufferedWriter.close();
        }
        catch (IOException exception) {}
    }
}