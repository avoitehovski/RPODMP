package com.example.rpodmp.LR3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.rpodmp.R;
import com.example.rpodmp.databinding.ActivityNewProductFirstStepBinding;
import com.example.rpodmp.databinding.ActivityNewProductSecondStepBinding;
import com.example.rpodmp.entities.Product;

public class NewProductSecondStep extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product_second_step);

        NewProductSecondStep instance = this;
        Bundle arguments = getIntent().getExtras();
        ActivityNewProductSecondStepBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_new_product_second_step);
        Product product = (Product)arguments.getSerializable(Product.class.getSimpleName());
        binding.setProduct(product);

        Button nextButton = findViewById(R.id.productNextButton2);
        View.OnClickListener onClickListener = v -> {
            Intent intent = new Intent(instance, NewProductThirdStep.class);
            intent.putExtra(Product.class.getSimpleName(), product);
            startActivity(intent);
        };
        nextButton.setOnClickListener(onClickListener);
    }
}