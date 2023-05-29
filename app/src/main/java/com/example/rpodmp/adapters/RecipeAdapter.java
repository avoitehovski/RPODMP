//package com.example.rpodmp.adapters;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.rpodmp.R;
//import com.example.rpodmp.entities.Recipe;
//import com.example.rpodmp.extensions.Extension;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
//
//    public View.OnClickListener itemOnClickListener;
//
//    List<Recipe> recipeList = new ArrayList<>();
//
//    public void setItems(Collection<Recipe> recipes){
//        recipeList.addAll(recipes);
//        notifyDataSetChanged();
//    }
//
//    public void addItem(Recipe recipe){
//        recipeList.add(recipe);
//        notifyDataSetChanged();
//    }
//
//    public void removeItem(Recipe recipe){
//        recipeList.remove(recipe);
//        notifyDataSetChanged();
//    }
//
//    public void clearItems() {
//        recipeList.clear();
//        notifyDataSetChanged();
//    }
//
//    public List<Recipe> getItems() {
//        return  recipeList;
//    }
//
//    public RecipeAdapter.RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.recepie_item_view, parent, false);
//        return new RecipeAdapter.RecipeViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(RecipeAdapter.RecipeViewHolder holder, int position) {
//        holder.bind(recipeList.get(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        return recipeList.size();
//    }
//
//    class RecipeViewHolder extends RecyclerView.ViewHolder {
//        private TextView _nameTextView;
//        private TextView _ingredientsTextView;
//        private TextView _timeTextView;
//        private ImageView _imageView;
//
//        public RecipeViewHolder(View itemView) {
//            super(itemView);
//            _nameTextView = itemView.findViewById(R.id.recipeNameDetailTextView);
//            _ingredientsTextView = itemView.findViewById(R.id.recipeIngeidientsDetailTextView);
//            _timeTextView = itemView.findViewById(R.id.recipeTimeDetailTextView);
//            _imageView = itemView.findViewById(R.id.recipeDetailImageView);
//            itemView.setOnClickListener(itemOnClickListener);
//        }
//
//        public void bind(Recipe recipe) {
//            _nameTextView.setText(recipe.getName());
//            _ingredientsTextView.setText(recipe.getIngredients());
//            _timeTextView.setText(Extension.fromMinutesToHHmm(recipe.getTime()));
//            _imageView.setImageBitmap;
//        }
//    }
//}
