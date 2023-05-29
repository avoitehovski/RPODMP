package com.example.rpodmp.fragments;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rpodmp.R;
import com.example.rpodmp.adapters.BindingAdapter;
import com.example.rpodmp.entities.Product;
import com.example.rpodmp.entities.Recipe;
import com.example.rpodmp.extensions.Extension;
import com.example.rpodmp.fragments.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.rpodmp.databinding.FragmentRecipeBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.ViewHolder> {

    public View.OnClickListener itemOnClickListener;

    public OnLongItemClickListener mOnLongItemClickListener;

    private List<Recipe> mValues;

    private int position;
    public RecipeRecyclerViewAdapter() {
        mValues = new ArrayList<>();
    }


    public void setItems(List<Recipe> recipes){
        mValues = recipes;
        notifyDataSetChanged();
    }

    public void addItem(Recipe recipe){
        mValues.add(recipe);
        notifyDataSetChanged();
    }

    public void removeItem(Recipe recipe){
        mValues.remove(recipe);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mValues.clear();
        notifyDataSetChanged();
    }

    public List<Recipe> getItems() {
        return  mValues;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentRecipeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.setValues();
        holder.itemView.setOnLongClickListener(v -> {
            if (mOnLongItemClickListener != null) {
                mOnLongItemClickListener.itemLongClicked(v, position);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public void setOnLongItemClickListener(OnLongItemClickListener onLongItemClickListener) {
        mOnLongItemClickListener = onLongItemClickListener;
    }

    public interface OnLongItemClickListener {
        void itemLongClicked(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView _nameTextView;
        private TextView _ingredientsTextView;
        private TextView _timeTextView;
        private TextView _categoryTextView;
        private ImageView _imageView;
        public Recipe mItem;

        public View mView;

        public ViewHolder(FragmentRecipeBinding binding) {
            super(binding.getRoot());
            _nameTextView = itemView.findViewById(R.id.recipeNameDetailTextView);
            _ingredientsTextView = itemView.findViewById(R.id.recipeIngredientsDetailTextView);
            _timeTextView = itemView.findViewById(R.id.recipeTimeDetailTextView);
            _imageView = itemView.findViewById(R.id.recipeDetailImageView);
            _categoryTextView = itemView.findViewById(R.id.recipeCategoryDetailTextView);
            itemView.setOnClickListener(itemOnClickListener);
        }

        public void setValues() {
            if (mItem != null) {
                _nameTextView.setText(mItem.getName());
                _ingredientsTextView.setText(mItem.getIngredients());
                _timeTextView.setText(Extension.fromMinutesToHHmm(mItem.getTime()));

                _categoryTextView.setText(BindingAdapter.getRecipeCategory(_categoryTextView, mItem.getCategory()));
                Bitmap b = BindingAdapter.imageFromStorage(_imageView, mItem.getImagePath());
                if (b == null) {
                    Drawable drawable = _imageView.getResources().getDrawable(R.drawable.baseline_question_mark_24);
                    _imageView.setImageDrawable(drawable);
                } else {
                    _imageView.setImageBitmap(b);
                }
            }
        }
    }
}