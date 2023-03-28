package com.kingdom.menumate.Adapters;

import static com.kingdom.menumate.Database.DatabaseHelper.CATEGORY;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kingdom.menumate.CategoryActivity;
import com.kingdom.menumate.Models.Category;
import com.kingdom.menumate.R;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private ArrayList<Category> categories = new ArrayList<>();
    private final Context context;

    public CategoriesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_categories_foods, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(categories.get(position).getName());
        Glide.with(context)
                .asBitmap()
                .load(categories.get(position).getImage_url())
                .into(holder.img);
        holder.view.setVisibility(View.VISIBLE);
        holder.parent.setOnClickListener(view -> {
            Intent intent = new Intent(context, CategoryActivity.class);
            intent.putExtra(CATEGORY, categories.get(position).getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final ImageView img;
        private final View view;
        private final FrameLayout parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.categoriesParent);
            name = itemView.findViewById(R.id.categoriesTxtName);
            img = itemView.findViewById(R.id.categoriesImg);
            view = itemView.findViewById(R.id.categoriesBorderView);
        }
    }
}
