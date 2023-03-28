package com.kingdom.menumate;

import static com.kingdom.menumate.Database.DatabaseHelper.CATEGORY;
import static com.kingdom.menumate.Database.DatabaseHelper.DESCRIPTION;
import static com.kingdom.menumate.Database.DatabaseHelper.FOODS;
import static com.kingdom.menumate.Database.DatabaseHelper.ID;
import static com.kingdom.menumate.Database.DatabaseHelper.IMAGE_URL;
import static com.kingdom.menumate.Database.DatabaseHelper.NAME;
import static com.kingdom.menumate.Database.DatabaseHelper.PRICE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.kingdom.menumate.Adapters.FoodsAdapter;
import com.kingdom.menumate.Database.DatabaseHelper;
import com.kingdom.menumate.Dialogs.LicensesDialog;
import com.kingdom.menumate.Models.Food;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private TextView categoryTitle;
    private RecyclerView foodsRecView;
    private Context context;
    private String category;
    private FoodsAdapter adapter;

    private GetFoods getFoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Intent intent = getIntent();

        initViews();

        if (null != intent) {
            context = this;
            category = intent.getStringExtra(CATEGORY);

            toolbar.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_logo) {
                    showInfoDialog();
                }

                return false;
            });

            categoryTitle.setText(category);

            toolbar.setNavigationOnClickListener(view -> finish());

            adapter = new FoodsAdapter(context);

            foodsRecView.setAdapter(adapter);
            foodsRecView.setLayoutManager(new LinearLayoutManager(context));

            getFoods = new GetFoods();
            getFoods.execute();
        }
    }

    private void showInfoDialog() {
        LicensesDialog dialog = new LicensesDialog();
        dialog.show(getSupportFragmentManager(), "licenses");
    }

    private class GetFoods extends AsyncTask<Void, Void, ArrayList<Food>> {
        @Override
        protected ArrayList<Food> doInBackground(Void... voids) {
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query(FOODS, null, CATEGORY + "='" + category + "'", null, null, null, null);
            try {
                if (null != cursor) {
                    if (cursor.moveToFirst()) {
                        ArrayList<Food> foods = new ArrayList<>();
                        for (int i = 0; i < cursor.getCount(); i++) {
                            Food food = new Food();
                            food.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                            food.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                            food.setImage_url(cursor.getString(cursor.getColumnIndex(IMAGE_URL)));
                            food.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
                            food.setPrice(cursor.getString(cursor.getColumnIndex(PRICE)));
                            food.setCategory(cursor.getString(cursor.getColumnIndex(CATEGORY)));

                            foods.add(food);
                            cursor.moveToNext();
                        }

                        return foods;
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Food> foods) {
            super.onPostExecute(foods);

            if (null != foods) {
                adapter.setFoods(foods);
            }else {
                adapter.setFoods(new ArrayList<>());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (getFoods != null) {
            if (!getFoods.isCancelled()) {
                getFoods.cancel(true);
            }
        }
    }

    private void initViews() {
        toolbar = findViewById(R.id.categoryToolbar);
        foodsRecView = findViewById(R.id.foodsRecView);
        categoryTitle = findViewById(R.id.categoryTitle);
    }
}