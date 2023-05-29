package com.example.rpodmp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rpodmp.R;
import com.example.rpodmp.entities.Product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    public View.OnClickListener itemOnClickListener;

    private List<Product> productList = new ArrayList<>();

    public void setItems(Collection<Product> products){
        productList.addAll(products);
        notifyDataSetChanged();
    }

    public void addItem(Product product){
        productList.add(product);
        notifyDataSetChanged();
    }

    public void removeItem(Product product){
        productList.remove(product);
        notifyDataSetChanged();
    }

    public void clearItems() {
        productList.clear();
        notifyDataSetChanged();
    }

    public List<Product> getItems() {
        return  productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item_view, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.bind(productList.get(position));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView _nameTextView;
        private TextView _priceTextView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            _nameTextView = itemView.findViewById(R.id.productNameTextView);
            _priceTextView = itemView.findViewById(R.id.productPriceTextView);
            itemView.setOnClickListener(itemOnClickListener);
        }

        public void bind(Product product) {
            _nameTextView.setText(product.getName());
            _priceTextView.setText(product.getPrice() + "$");
        }
    }
}
