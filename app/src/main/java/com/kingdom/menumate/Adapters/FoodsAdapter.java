package com.kingdom.menumate.Adapters;

import static com.kingdom.menumate.FoodActivity.FOOD_ITEM_KEY;

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
import com.kingdom.menumate.FoodActivity;
import com.kingdom.menumate.Models.Food;
import com.kingdom.menumate.R;

import java.util.ArrayList;

public class FoodsAdapter extends RecyclerView.Adapter<FoodsAdapter.ViewHolder> {

    private ArrayList<Food> foods = new ArrayList<>();
    private final Context context;

    public FoodsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FoodsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_categories_foods, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(foods.get(position).getName());
        Glide.with(context)
                .asBitmap()
                .load(foods.get(position).getImage_url())
                .into(holder.img);
        holder.view.setVisibility(View.VISIBLE);
        holder.parent.setOnClickListener(view -> {
            Intent intent = new Intent(context, FoodActivity.class);
            intent.putExtra(FOOD_ITEM_KEY, foods.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
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
