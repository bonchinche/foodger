package com.example.foodger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.foodger.ProductsTablesContracts.Product_Type;
import com.example.foodger.ProductsTablesContracts.Product_Characteristic;



public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = DataBaseHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "Products.db";
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_TABLE = "DBTable";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Строка для создания таблицы
        String SQL_CREATE_PRODUCT_TYPE_TABLE = "CREATE TABLE IF NOT EXISTS " + ProductsTablesContracts.Product_Type.TABLE_NAME + " ("
                + ProductsTablesContracts.Product_Type._ID + " INTEGER PRIMARY KEY NOT NULL, "
                + ProductsTablesContracts.Product_Type.AVG_SHELF_LIFE + " INTEGER, "
                + ProductsTablesContracts.Product_Type.TYPE_NAME + " TEXT NOT NULL, "
                + ProductsTablesContracts.Product_Type.COLOR + " TEXT )";
        // Запускаем создание таблицы
        db.execSQL(SQL_CREATE_PRODUCT_TYPE_TABLE);

        // Строка для создания таблицы
        String SQL_CREATE_PRODUCT_CHARACTERISTIC_TABLE = "CREATE TABLE IF NOT EXISTS " + ProductsTablesContracts.Product_Characteristic.TABLE_NAME + " ("
                + ProductsTablesContracts.Product_Characteristic._ID + " INTEGER PRIMARY KEY NOT NULL, "
                + ProductsTablesContracts.Product_Characteristic.CALORIES + " INTEGER, "
                + ProductsTablesContracts.Product_Characteristic.CARBOHYDRATES + " INTEGER , "
                + ProductsTablesContracts.Product_Characteristic.PROTEIN + " INTEGER , "
                + ProductsTablesContracts.Product_Characteristic.FATNESS + " INTEGER, "
                + ProductsTablesContracts.Product_Characteristic.RATING + " NUMERIC(2,1) , "
                + ProductsTablesContracts.Product_Characteristic.PHOTO + " TEXT )";
        // Запускаем создание таблицы
        db.execSQL(SQL_CREATE_PRODUCT_CHARACTERISTIC_TABLE);


        String SQL_CREATE_PRODUCTS_TABLE = "CREATE TABLE IF NOT EXISTS " + ProductsTablesContracts.Products.TABLE_NAME + " ("
                + ProductsTablesContracts.Products._ID + " INTEGER PRIMARY KEY NOT NULL, "
                + ProductsTablesContracts.Products.PRODUCT_TYPE_ID + " INTEGER, "
                + ProductsTablesContracts.Products.TEMPERATURE + " INTEGER, "
                + ProductsTablesContracts.Products.DOM + " TEXT, "
                + ProductsTablesContracts.Products.DOS + " TEXT, "
                + ProductsTablesContracts.Products.SHELF_LIFE+ " INTEGER , "
                + ProductsTablesContracts.Products.NAME + " TEXT , FOREIGN KEY(\""
                + ProductsTablesContracts.Products.PRODUCT_TYPE_ID+"\") REFERENCES "
                + ProductsTablesContracts.Product_Type.TABLE_NAME+"("+ Product_Type._ID+"), FOREIGN KEY(\""
                + ProductsTablesContracts.Products._ID+"\") REFERENCES "
                + ProductsTablesContracts.Product_Characteristic.TABLE_NAME+"("+ Product_Characteristic._ID+"))";
        // Запускаем создание таблицы
        db.execSQL(SQL_CREATE_PRODUCTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}


