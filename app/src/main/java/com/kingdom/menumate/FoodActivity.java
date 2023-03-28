package com.kingdom.menumate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.kingdom.menumate.Dialogs.LicensesDialog;
import com.kingdom.menumate.Models.Food;

public class FoodActivity extends AppCompatActivity {
    public static final String FOOD_ITEM_KEY = "incoming_food";
    private MaterialToolbar toolbar;
    private TextView foodTitle, foodDescription, foodPrice;
    private ImageView foodImg;
    private View borderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        Intent intent = getIntent();

        initViews();

        if (null != intent) {
            Context context = this;
            Food food = intent.getParcelableExtra(FOOD_ITEM_KEY);

            toolbar.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_logo) {
                    showInfoDialog();
                }

                return false;
            });

            foodTitle.setText(food.getName());

            foodDescription.setText(food.getDescription());

            foodPrice.setText(food.getPrice());

            Glide.with(context)
                    .asBitmap()
                    .load(food.getImage_url())
                    .into(foodImg);

            borderView.setVisibility(View.VISIBLE);

            toolbar.setNavigationOnClickListener(view -> finish());
        }
    }

    private void showInfoDialog() {
        LicensesDialog dialog = new LicensesDialog();
        dialog.show(getSupportFragmentManager(), "licenses");
    }

    private void initViews() {
        toolbar = findViewById(R.id.foodToolbar);
        foodTitle = findViewById(R.id.foodTitle);
        foodDescription = findViewById(R.id.foodTxtDescription);
        foodPrice = findViewById(R.id.foodTxtPrice);
        foodImg = findViewById(R.id.foodImg);
        borderView = findViewById(R.id.foodBorderView);
    }
}