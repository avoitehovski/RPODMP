package com.example.rpodmp.LR3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.rpodmp.R;
import com.example.rpodmp.adapters.ProductAdapter;
import com.example.rpodmp.entities.Product;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    private ProductsActivity instance;
    private RecyclerView _recyclerView;
    private Button _addButton;
    private Button _clearButton;
    private Button _saveButton;
    private ProductAdapter _productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        instance = this;
        initializeRecyclerView();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null)
            setIntent(intent);
    }

    @Override
    protected void onResume() {
        Intent intent = getIntent();
        Bundle arguments = getIntent().getExtras();
        if (arguments != null){
            Product product = (Product)arguments.getSerializable("newProduct");
            _productAdapter.addItem(product);
        }
        super.onResume();
    }

    private void initializeRecyclerView() {

        _recyclerView = findViewById(R.id.productsRecyclerView);
        _recyclerView.setLayoutManager(new LinearLayoutManager(this));

        _productAdapter = new ProductAdapter();
        _recyclerView.setAdapter(_productAdapter);

        _productAdapter.itemOnClickListener = view -> {
            int itemPosition = _recyclerView.getChildLayoutPosition(view);
            Product product = _productAdapter.getItems().get(itemPosition);
            Intent intent = new Intent(instance, ProductInfoActivity.class);
            intent.putExtra(Product.class.getSimpleName(), product);
            startActivity(intent);
        };

        try {
            loadProducts();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        _addButton = findViewById(R.id.newProductButton);
        _clearButton = findViewById(R.id.clearProductsButton);
        _saveButton = findViewById(R.id.saveProductsButton);
        View.OnClickListener addClickListener = v -> {
            Intent intent = new Intent(instance, NewProductFirstStep.class);
            Product newProduct = new Product();
            intent.putExtra(Product.class.getSimpleName(), newProduct);
            startActivity(intent);
        };
        _addButton.setOnClickListener(addClickListener);

        View.OnClickListener clearClickListener = v -> _productAdapter.clearItems();
        _clearButton.setOnClickListener(clearClickListener);

        View.OnClickListener saveClickListener = v -> saveProducts();
        _saveButton.setOnClickListener(saveClickListener);
    }

    private void loadProducts() throws JSONException {
        File file = new File(getFilesDir(),"products.json");
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
                _productAdapter.addItem(new Product(array.getJSONObject(i)));
            }
        } catch (IOException exception) { }
    }

    private void saveProducts() {
        List<Product> products = _productAdapter.getItems();
        try {
            JSONArray array = new JSONArray();
            for (Product product : products) {
                array.put(product.toJSON());
            }
            File file = new File(getFilesDir(), "products.json");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(array.toString());
            bufferedWriter.close();
        }
        catch (IOException exception) {}
    }
}