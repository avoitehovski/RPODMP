package com.example.rpodmp.extensions;

import com.example.rpodmp.entities.Recipe;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Extension {
    public static String fromMinutesToHHmm(int minutes) {
        long hours = TimeUnit.MINUTES.toHours(Long.valueOf(minutes));
        long remainMinutes = minutes - TimeUnit.HOURS.toMinutes(hours);
        return String.format("%02d:%02d", hours, remainMinutes);
    }

    public static List<Recipe> recipesTest = Arrays.asList(new Recipe[]{
            new Recipe("Test 1", "Description test", "Ingredient 1, 2, 3", 60, 1),
            new Recipe("Test 2", "Description test 2", "Ingredient 3, 2, 1", 90, 2),
            new Recipe("Test 3", "Description test 3", "Ingredient 3, 2, 1", 15, 3),
    });
}
