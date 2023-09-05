package com.example.apptest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.apptest.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsDataSource {
    private SQLiteDatabase database;
    private  DatabaseHelper dbHelper;
    private  String [] allColumns = {"product_id","product_name", "product_price"};

    public ProductsDataSource(Context context){
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }

    public Product createProduct(String name, double price){
        ContentValues contentValues = new ContentValues();
        contentValues.put("product_name", name);
        contentValues.put("product_price", price);

        int product_id = (int) database.insert(DatabaseHelper.TABLE_PRODUCTS, null, contentValues);

        Cursor cursor = database.query(DatabaseHelper.TABLE_PRODUCTS, allColumns, "product_id = " + product_id, null, null,null,null);
        cursor.moveToFirst();
        Product newProduct = cursorToProduct(cursor);
        cursor.close();
        return newProduct;
    }

    public void deleteProduct(Product product){
        int id = product.getId();
        database.delete(DatabaseHelper.TABLE_PRODUCTS, "product_id =" + id, null);
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<Product>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_PRODUCTS, allColumns, null ,null ,null ,null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Product product = cursorToProduct(cursor);
            products.add(product);
            cursor.moveToNext();
        }
        cursor.close();
        return products;
    }

    private Product cursorToProduct(Cursor cursor){
        Product product = new Product();
        product.setId(cursor.getInt(0));
        product.setName(cursor.getString(1));
        product.setPrice(cursor.getDouble(2));
        return product;
    }
}
