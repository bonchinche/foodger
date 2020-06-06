package com.example.foodger.ui.Products;

import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import com.example.foodger.R;
import com.example.foodger.ProductsTablesContracts.Product_Characteristic;
import com.example.foodger.ProductsTablesContracts.Product_Type;
import com.example.foodger.ProductsTablesContracts.Products;
import com.example.foodger.DataBaseHelper;


import java.util.ArrayList;

public class ProductsFragment extends Fragment {


    private ReplaceFragment replaceFragment;

    public interface ReplaceFragment {
        void onFragmentReplace(Bundle bundle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ReplaceFragment) {
            replaceFragment = (ReplaceFragment) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragment1DataListener");
        }
    }

    public ProductsFragment(){

    }

    private DataBaseHelper dbHelper;
    ArrayList<String> ProductsList = new ArrayList<>();
    ArrayList<String> TypeProductsList = new ArrayList<>();
    ArrayList<String> ProductCharacteristicList = new ArrayList<>();

    private void SelectFromProducts() {
        // Создадим и откроем для чтения базу данных
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Зададим условие для выборки - список столбцов
        String[] projection = {
                Products._ID,
                Products.PRODUCT_TYPE_ID,
                Products.PRODUCT_CHARACTERISTIC_ID,
                Products.NAME,
                Products.SHELF_LIFE ,
                Products.DOM};

        // Делаем запрос
        Cursor cursor = db.query(
                Products.TABLE_NAME,   // таблица
                projection,            // столбцы
                null,                  // столбцы для условия WHERE
                null,                  // значения для условия WHERE
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // порядок сортировки


        try {

            // Узнаем индекс каждого столбца
            int idColumnIndex = cursor.getColumnIndex(Products._ID);
            int ProductNameIndex = cursor.getColumnIndex(Products.NAME);

            // Проходим через все ряды
            while (cursor.moveToNext()) {
                // Используем индекс для получения строки или числа
                //int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(ProductNameIndex);

                // Выводим значения каждого столбца
                ProductsList.add(currentName);
            }
        } finally {
            // Всегда закрываем курсор после чтения
            cursor.close();
        }
    }

    private void SelectFromTypeProducts() {
        // Создадим и откроем для чтения базу данных
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Зададим условие для выборки - список столбцов
        String[] projection = {
                Product_Type._ID,
                Product_Type.TYPE_NAME,
                Product_Type.COLOR,
                Product_Type.TEMPERATURE,
                Product_Type.AVG_SHELF_LIFE,
                };

        // Делаем запрос
        Cursor cursor = db.query(
                Product_Type.TABLE_NAME,   // таблица
                projection,            // столбцы
                null,                  // столбцы для условия WHERE
                null,                  // значения для условия WHERE
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // порядок сортировки


        try {

            //roductsList.add(Products.NAME);

            // Узнаем индекс каждого столбца
            int idColumnIndex = cursor.getColumnIndex(Product_Type._ID);
            int ProductNameIndex = cursor.getColumnIndex(Product_Type.TYPE_NAME);

            // Проходим через все ряды
            while (cursor.moveToNext()) {
                // Используем индекс для получения строки или числа
                //int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(ProductNameIndex);

                // Выводим значения каждого столбца
                //TypeProductsList.add(currentName);
            }
        } finally {
            // Всегда закрываем курсор после чтения
            cursor.close();
        }
    }

    private void SelectFromProductCharacteristic() {
        // Создадим и откроем для чтения базу данных
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Зададим условие для выборки - список столбцов
        String[] projection = {
                Product_Characteristic._ID,
                Product_Characteristic.PROTEIN,
                Product_Characteristic.PHOTO,
                Product_Characteristic.RATING,
                Product_Characteristic.FATNESS,
                Product_Characteristic.CARBOHYDRATES,
                Product_Characteristic.CALORIES,
        };

        // Делаем запрос
        Cursor cursor = db.query(
                Product_Characteristic.TABLE_NAME,   // таблица
                projection,            // столбцы
                null,                  // столбцы для условия WHERE
                null,                  // значения для условия WHERE
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // порядок сортировки


        try {

            //roductsList.add(Products.NAME);

            // Узнаем индекс каждого столбца
            int idColumnIndex = cursor.getColumnIndex(Product_Characteristic._ID);

            // Проходим через все ряды
            while (cursor.moveToNext()) {
                // Используем индекс для получения строки или числа
                //int currentID = cursor.getInt(idColumnIndex);
                String currentID = cursor.getString(idColumnIndex);

                // Выводим значения каждого столбца
               // ProductCharacteristicList.add(currentID);
            }
        } finally {
            // Всегда закрываем курсор после чтения
            cursor.close();
        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_products, container, false);
        //final TextView textView = root.findViewById(R.id.text_products);

        ListView listView = root.findViewById(R.id.products_list);


        dbHelper = new DataBaseHelper(getContext());

        /*SQLiteDatabase wd = dbHelper.getWritableDatabase();
        // Создаем объект ContentValues, где имена столбцов ключи,
        // а информация о госте является значениями ключей
        ContentValues values = new ContentValues();
        values.put(Products.NAME, "diimmooonn");


        long newRowId = wd.insert(Products.TABLE_NAME, null, values);


        if (newRowId == -1) {
            // Если ID  -1, значит произошла ошибка
            Toast.makeText(getContext(), "Ошибка при заведении гостя", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Гость заведён под номером: " + newRowId, Toast.LENGTH_SHORT).show();
        }*/

        SelectFromProducts();

        Spinner spinner = (Spinner) root.findViewById(R.id.type_products);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity (), android.R.layout.simple_spinner_item,  TypeProductsList);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);

        ArrayAdapter<String> meat_list_adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, ProductsList );

        listView.setAdapter(meat_list_adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String selectedFromList= (String) parent.getItemAtPosition(position).toString();

                Bundle bundle = new Bundle();
                bundle.putString("arg1", selectedFromList);
                bundle.putInt("arg2", position);
                replaceFragment.onFragmentReplace(bundle);
            }
        });

        return root;
    }
}


