package com.kingdom.menumate;

import static com.kingdom.menumate.Database.DatabaseHelper.CATEGORIES;
import static com.kingdom.menumate.Database.DatabaseHelper.ID;
import static com.kingdom.menumate.Database.DatabaseHelper.IMAGE_URL;
import static com.kingdom.menumate.Database.DatabaseHelper.NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import com.kingdom.menumate.Adapters.CategoriesAdapter;
import com.kingdom.menumate.Database.DatabaseHelper;
import com.kingdom.menumate.Dialogs.LicensesDialog;
import com.kingdom.menumate.Models.Category;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView categoriesRecView;
    private Context context;
    private CategoriesAdapter adapter;
    private Toolbar toolbar;

    private GetCategories getCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        initViews();

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_logo) {
                showInfoDialog();
            }

            return false;
        });

        adapter = new CategoriesAdapter(context);
        categoriesRecView.setAdapter(adapter);
        categoriesRecView.setLayoutManager(new LinearLayoutManager(context));

        getCategories = new GetCategories();
        getCategories.execute();
    }

    private class GetCategories extends AsyncTask<Void, Void, ArrayList<Category>> {
        @Override
        protected ArrayList<Category> doInBackground(Void... voids) {
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.query(CATEGORIES, null, null, null, null, null, null);
            try {
                if (null != cursor) {
                    if (cursor.moveToFirst()) {
                        ArrayList<Category> categories = new ArrayList<>();
                        for (int i = 0; i < cursor.getCount(); i++) {
                            Category category = new Category();
                            category.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                            category.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                            category.setImage_url(cursor.getString(cursor.getColumnIndex(IMAGE_URL)));

                            categories.add(category);
                            cursor.moveToNext();
                        }

                        return categories;
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
        protected void onPostExecute(ArrayList<Category> categories) {
            super.onPostExecute(categories);

            if (null != categories) {
                adapter.setCategories(categories);
            }else {
                adapter.setCategories(new ArrayList<>());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (null != getCategories) {
            if (!getCategories.isCancelled()) {
                getCategories.cancel(true);
            }
        }
    }

    private void showInfoDialog() {
        LicensesDialog dialog = new LicensesDialog();
        dialog.show(getSupportFragmentManager(), "licenses");
    }

    private void initViews() {
        categoriesRecView = findViewById(R.id.categoriesRecView);
        toolbar = findViewById(R.id.mainToolbar);
    }
}