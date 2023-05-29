package com.example.rpodmp.entities;


import android.media.Image;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.rpodmp.BR;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Product extends BaseObservable implements Serializable {

    private String name;
    private int category;
    private double weight;
    private double price;
    private int count;
    private int provider;

    private String email;

    private String phone;

    private String link;

    public Product(String name, double price){
        this();
        this.setName(name);
        this.setPrice(price);
    }

    public Product(){
        email = "loew.vity@gmail.com";
        phone="+375298185865";
        link="https://vk.com/baldel";
    }

    public Product(JSONObject object) throws JSONException {
        this();
        this.name = object.get("name").toString();
        this.category = Integer.parseInt(object.get("category").toString());
        this.provider = Integer.parseInt(object.get("provider").toString());
        this.count = Integer.parseInt(object.get("count").toString());
        this.weight = Double.parseDouble(object.get("weight").toString());
        this.price = Double.parseDouble(object.get("price").toString());
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
    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
        notifyPropertyChanged(BR.category);
    }

    @Bindable
    public double getWeight() {
        return weight;
    }

    @Bindable
    public void setWeight(double weight) {
        this.weight = weight;
        notifyPropertyChanged(BR.weight);
    }

    @Bindable
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        notifyPropertyChanged(BR.price);
    }

    @Bindable
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        notifyPropertyChanged(BR.count);
    }

    @Bindable
    public int getProvider() {
        return provider;
    }

    public void setProvider(int provider) {
        this.provider = provider;
        notifyPropertyChanged(BR.provider);
    }

    public JSONObject toJSON(){

        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("name", getName());
            jsonObject.put("category", getCategory());
            jsonObject.put("weight", getWeight());
            jsonObject.put("price", getPrice());
            jsonObject.put("count", getCount());
            jsonObject.put("provider", getProvider());

            return jsonObject;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
        notifyPropertyChanged(BR.link);
    }
}
