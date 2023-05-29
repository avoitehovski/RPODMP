package com.example.rpodmp.LR4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.rpodmp.LR3.ProductsActivity;
import com.example.rpodmp.R;
import com.example.rpodmp.databinding.ActivityProductInfoBinding;
import com.example.rpodmp.databinding.ActivityRecipeEditBinding;
import com.example.rpodmp.entities.Product;
import com.example.rpodmp.entities.Recipe;
import com.example.rpodmp.fragments.RecipeDetailFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

public class RecipeEditActivity extends AppCompatActivity {

    final int DIALOG_RECIPE_CONFIRM_ID = 1;

    private boolean isEdit;
    private Recipe newRecipe;
    private Recipe oldRecipe;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_edit);

        Bundle arguments = getIntent().getExtras();

        isEdit = arguments.getBoolean("IS_EDIT");

        ActivityRecipeEditBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_edit);
        oldRecipe = (Recipe)arguments.getSerializable(Recipe.class.getSimpleName());
        newRecipe = new Recipe(oldRecipe);
        binding.setRecipe(newRecipe);

        imageView = findViewById(R.id.recipeImageView);

        RecipeEditActivity instance = this;

        Button newImageButton = findViewById(R.id.recipeImageButton);
        newImageButton.setOnClickListener(v -> {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto, 1);
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(instance);
        builder.setMessage("Confirm saving data?")
                .setTitle("Save data");
        builder.setPositiveButton("Save", (dialog, id) -> {
            imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache();
            Bitmap bitmap = Bitmap.createBitmap(imageView.getDrawingCache());
            imageView.setDrawingCacheEnabled(false);
            String path = saveToInternalStorage(newRecipe, bitmap);
            newRecipe.setImagePath(path);

            Intent intent = new Intent(instance, RecepiesActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            if (isEdit) {
                intent.putExtra("IS_EDIT", true);
            }
            intent.putExtra("newRecipe", newRecipe);
            startActivity(intent);
        });
        builder.setNegativeButton("Cancel", (dialog, id) -> {
            // User cancelled the dialog
        });
        AlertDialog dialog = builder.create();

        Button saveButton = findViewById(R.id.recipeSaveButton);
        saveButton.setOnClickListener(v -> {
            dialog.show();
        });
    }

    private String saveToInternalStorage(Recipe recipe, Bitmap image){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(directory, recipe.getName() + " " + System.currentTimeMillis() + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath.getAbsolutePath();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Bundle extras = imageReturnedIntent.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    imageView.setImageBitmap(imageBitmap);
                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageView.setImageURI(selectedImage);
                }
                break;
        }
    }
}