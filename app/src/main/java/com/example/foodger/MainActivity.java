package com.example.foodger;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.foodger.ui.Products.ProductsFragment;
import com.google.android.material.navigation.NavigationView;



public class MainActivity extends AppCompatActivity implements ProductsFragment.ReplaceFragment, NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataBaseHelper dbHelper=new DataBaseHelper(this);
        //this.deleteDatabase("Products.db");

        SQLiteDatabase checktypes= dbHelper.getReadableDatabase();

        Cursor cursor = checktypes.rawQuery("Select TYPE_NAME from "+ProductsTablesContracts.Product_Type.TABLE_NAME.toString(),null);

        if (!(cursor.moveToFirst())){

            SQLiteDatabase mydb = dbHelper.getWritableDatabase();

            Log.d("Check","Данные еще не существует, делаем insert в Product_Type");

            ContentValues insert_types_veg = new ContentValues();
            insert_types_veg.put(ProductsTablesContracts.Product_Type._ID, 0);
            insert_types_veg.put(ProductsTablesContracts.Product_Type.AVG_SHELF_LIFE, 21);
            insert_types_veg.put(ProductsTablesContracts.Product_Type.COLOR, "#068006");
            insert_types_veg.put(ProductsTablesContracts.Product_Type.TYPE_NAME, "Vegetables");
            mydb.insert(ProductsTablesContracts.Product_Type.TABLE_NAME, null, insert_types_veg);

            ContentValues insert_types_cheese = new ContentValues();
            insert_types_cheese.put(ProductsTablesContracts.Product_Type._ID, 1);
            insert_types_cheese.put(ProductsTablesContracts.Product_Type.AVG_SHELF_LIFE, 10);
            insert_types_cheese.put(ProductsTablesContracts.Product_Type.COLOR, "#f5f511");
            insert_types_cheese.put(ProductsTablesContracts.Product_Type.TYPE_NAME, "Cheese");
            mydb.insert(ProductsTablesContracts.Product_Type.TABLE_NAME, null, insert_types_cheese);

            ContentValues insert_types_eggs = new ContentValues();
            insert_types_eggs.put(ProductsTablesContracts.Product_Type._ID, 2);
            insert_types_eggs.put(ProductsTablesContracts.Product_Type.AVG_SHELF_LIFE, 25);
            insert_types_eggs.put(ProductsTablesContracts.Product_Type.COLOR, "#c2c2b8");
            insert_types_eggs.put(ProductsTablesContracts.Product_Type.TYPE_NAME, "Eggs");
            mydb.insert(ProductsTablesContracts.Product_Type.TABLE_NAME, null, insert_types_eggs);

            ContentValues insert_types_meat = new ContentValues();
            insert_types_meat.put(ProductsTablesContracts.Product_Type._ID, 3);
            insert_types_meat.put(ProductsTablesContracts.Product_Type.AVG_SHELF_LIFE, 7);
            insert_types_meat.put(ProductsTablesContracts.Product_Type.COLOR, "#ff0000");
            insert_types_meat.put(ProductsTablesContracts.Product_Type.TYPE_NAME, "Meat");
            mydb.insert(ProductsTablesContracts.Product_Type.TABLE_NAME, null, insert_types_meat);

            ContentValues insert_types_milk = new ContentValues();
            insert_types_milk.put(ProductsTablesContracts.Product_Type._ID, 4);
            insert_types_milk.put(ProductsTablesContracts.Product_Type.AVG_SHELF_LIFE, 5);
            insert_types_milk.put(ProductsTablesContracts.Product_Type.COLOR, "#f0f0e6");
            insert_types_milk.put(ProductsTablesContracts.Product_Type.TYPE_NAME, "Milk");
            mydb.insert(ProductsTablesContracts.Product_Type.TABLE_NAME, null, insert_types_milk);

            ContentValues insert_types_fruits = new ContentValues();
            insert_types_fruits.put(ProductsTablesContracts.Product_Type._ID, 5);
            insert_types_fruits.put(ProductsTablesContracts.Product_Type.AVG_SHELF_LIFE, 21);
            insert_types_fruits.put(ProductsTablesContracts.Product_Type.COLOR, "#ff8800");
            insert_types_fruits.put(ProductsTablesContracts.Product_Type.TYPE_NAME, "Fruits");
            mydb.insert(ProductsTablesContracts.Product_Type.TABLE_NAME, null, insert_types_fruits);

            ContentValues insert_types_other = new ContentValues();
            insert_types_other.put(ProductsTablesContracts.Product_Type._ID, 6);
            insert_types_other.put(ProductsTablesContracts.Product_Type.COLOR, "#00eeff");
            insert_types_other.put(ProductsTablesContracts.Product_Type.TYPE_NAME, "Other");
            mydb.insert(ProductsTablesContracts.Product_Type.TABLE_NAME, null, insert_types_other);

        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       /* FloatingActionButton add_product = findViewById(R.id.add_product); //нижний правый угол плюсик
        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
               // R.id.nav_products, R.id.nav_shelflife,R.id.nav_nutrition, R.id.nav_addproduct)
                R.id.nav_products, R.id.nav_shelflife,R.id.nav_nutrition, R.id.nav_addproduct)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onFragmentReplace(Bundle bundle) {
    NavController navController=Navigation.findNavController(this,R.id.nav_host_fragment);
    navController.navigate(R.id.nav_product_characteristics,bundle);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        return true;
    }

}
