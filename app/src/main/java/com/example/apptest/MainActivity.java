package com.example.apptest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.apptest.database.ProductsDataSource;
import com.example.apptest.models.Product;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProductsDataSource productsDataSource;
    private Button add;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        productsDataSource = new ProductsDataSource(this);
        productsDataSource.open();

        listView = findViewById(R.id.list);
        loadData();
        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product product = null;
                product = productsDataSource.createProduct("ABCD", 100000);
                Toast.makeText(MainActivity.this, "Completed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        productsDataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        productsDataSource.close();
        super.onPause();
    }


    private void loadData() {
        List<Product> products = this.productsDataSource.getAllProducts();
        ArrayAdapter<Product> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, products);
        listView.setAdapter(adapter);
    }
}