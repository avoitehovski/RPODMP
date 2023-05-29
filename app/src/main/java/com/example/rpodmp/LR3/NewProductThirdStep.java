package com.example.rpodmp.LR3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.rpodmp.R;
import com.example.rpodmp.databinding.ActivityNewProductFirstStepBinding;
import com.example.rpodmp.databinding.ActivityNewProductThirdStepBinding;
import com.example.rpodmp.entities.Product;

public class NewProductThirdStep extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product_third_step);

        NewProductThirdStep instance = this;
        Bundle arguments = getIntent().getExtras();
        ActivityNewProductThirdStepBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_new_product_third_step);
        Product product = (Product)arguments.getSerializable(Product.class.getSimpleName());
        binding.setProduct(product);

        Button saveButton = findViewById(R.id.productSaveButton);
        View.OnClickListener onClickListener = v -> {
            Intent intent = new Intent(instance, ProductsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("Новый продукт", product);
            startActivity(intent);
        };
        saveButton.setOnClickListener(onClickListener);
    }
}