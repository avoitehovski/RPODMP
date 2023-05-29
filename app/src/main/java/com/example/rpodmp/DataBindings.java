package com.example.rpodmp;

import android.widget.Spinner;

import androidx.databinding.BindingAdapter;

import java.util.Arrays;

public class DataBindings {
    @BindingAdapter({"app:selectedValue", "app:spinnerEntries"})
    public static void selectValue(Spinner spinner, String selectedValue, String[] spinnerEntries) {
        if (selectedValue != null) {
            int indexSelection = Arrays.binarySearch(spinnerEntries, selectedValue);
            spinner.setSelection(indexSelection);
        }
    }
}
