package com.example.rpodmp.entities;

import android.graphics.Bitmap;
import android.media.Image;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.rpodmp.BR;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Recipe extends BaseObservable implements Serializable {
    public int id;
    private String name;
    private String description;
    private String ingredients;
    private int time;
    private int category;
    private String imagePath;

    public Recipe() {

    }

    public Recipe(String name, String description, String ingredients, int time, int category) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.time = time;
        this.category = category;
    }

    public Recipe(Recipe anotherRecipe) {
        this.name = anotherRecipe.getName();
        this.description = anotherRecipe.getDescription();
        this.ingredients = anotherRecipe.getIngredients();
        this.time = anotherRecipe.getTime();
        this.category = anotherRecipe.getCategory();
        this.imagePath = anotherRecipe.getImagePath();
    }

    public Recipe(JSONObject object) throws JSONException {
        this();
        this.name = object.get("name").toString();
        this.description = object.get("description").toString();
        this.ingredients = object.get("ingredients").toString();
        this.time = Integer.parseInt(object.get("time").toString());
        this.category = Integer.parseInt(object.get("category").toString());
        this.imagePath = object.get("imagePath").toString();
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Bindable
    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
        notifyPropertyChanged(BR.category);
    }

    @Bindable
    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients){
        this.ingredients = ingredients;
        notifyPropertyChanged(BR.ingredients);
    }

    @Bindable
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void copy(Recipe anotherRecipe) {
        this.name = anotherRecipe.getName();
        this.description = anotherRecipe.getDescription();
        this.ingredients = anotherRecipe.getIngredients();
        this.time = anotherRecipe.getTime();
        this.category = anotherRecipe.getCategory();
        this.imagePath = anotherRecipe.getImagePath();
    }

    public JSONObject toJSON(){

        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("name", getName());
            jsonObject.put("description", getDescription());
            jsonObject.put("ingredients", getIngredients());
            jsonObject.put("time", getTime());
            jsonObject.put("category", getCategory());
            jsonObject.put("imagePath", getImagePath());

            return jsonObject;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
