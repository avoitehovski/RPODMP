package com.example.rpodmp.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.rpodmp.R;
import com.example.rpodmp.extensions.Extension;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BindingAdapter {
    @androidx.databinding.BindingAdapter({"app:categoryText"})
    public static void categoryIntToString(TextView view, int index){
        String[] categories = view.getResources().getStringArray(R.array.Categories);
        view.setText(categories[index]);
    }

    @androidx.databinding.BindingAdapter({"app:providersText"})
    public static void providerIntToString(TextView view, int index){
        String[] providers = view.getResources().getStringArray(R.array.Providers);
        view.setText(providers[index]);
    }

    @androidx.databinding.BindingAdapter({"app:recipeTime"})
    public static void timeFormat(TextView view, int time){
        view.setText(Extension.fromMinutesToHHmm(time));
    }

    @androidx.databinding.BindingAdapter({"app:recipeCategoryText"})
    public static void recipeCategory(TextView view, int index){
        if (view != null) {
            String[] categories = view.getResources().getStringArray(R.array.Recipes);
            view.setText(categories[index]);
        }
    }

    public static String getRecipeCategory(TextView view, int index){
        if (view != null) {
            String[] categories = view.getResources().getStringArray(R.array.Recipes);
            return categories[index];
        }
        return null;
    }

    @androidx.databinding.BindingAdapter({"app:recipeImage"})
    public static void loadImageFromStorage(ImageView imageView, String path)
    {
        if (imageView != null){
            if (path != null) {
                try {
                    File file = new File(path);
                    Bitmap b = BitmapFactory.decodeStream(new FileInputStream(file));
                    imageView.setImageBitmap(b);
                } catch (FileNotFoundException e) {
                    Drawable drawable = imageView.getResources().getDrawable(R.drawable.baseline_question_mark_24);
                    imageView.setImageDrawable(drawable);
                }
            } else {
                Drawable drawable = imageView.getResources().getDrawable(R.drawable.baseline_question_mark_24);
                imageView.setImageDrawable(drawable);
            }
        }
    }

    public static Bitmap imageFromStorage(ImageView imageView, String path) {
        if (imageView != null){
            if (path != null) {
                try {
                    File file = new File(path);
                    Bitmap b = BitmapFactory.decodeStream(new FileInputStream(file));
                    return b;
                } catch (FileNotFoundException e) {
                    return BitmapFactory.decodeResource(imageView.getResources(),
                            R.drawable.baseline_question_mark_24);
                }
            } else {
                return BitmapFactory.decodeResource(imageView.getResources(),
                        R.drawable.baseline_question_mark_24);
            }
        }
        return BitmapFactory.decodeResource(imageView.getResources(),
                R.drawable.baseline_question_mark_24);
    }
}
