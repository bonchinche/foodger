package com.example.foodger.ui.AddProduct;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.example.foodger.DataBaseHelper;
import com.example.foodger.ProductsTablesContracts;
import com.example.foodger.R;
import com.example.foodger.calendarDate;


public class AddProductFragment extends Fragment{

    private AdditionalInfo additionalInfo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_addproduct, container, false);
        
        Button cancellButton = (Button)root.findViewById(R.id.cancellButton);
        Button applyButton = (Button)root.findViewById(R.id.applyButton);
        Button additionalInfoButton = (Button)root.findViewById(R.id.additionalnfoButton);
        final RatingBar ratingBar = (RatingBar)root.findViewById(R.id.ratingBar);
        final EditText productNameEditText = (EditText)root.findViewById(R.id.productNameEditText);
        final CalendarView calendarView = (CalendarView)root.findViewById(R.id.Layout);
        Spinner spinner = (Spinner)root.findViewById(R.id.productCategorySpinner);

        //DataBase
        _db = new DataBaseHelper(getContext());


        //адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, _productType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Выпадающий список
        spinner.setAdapter(adapter);
        spinner.setPrompt("Choose your category");
        spinner.setSelection(1);

        //Rating bar
        ratingBar.setStepSize(1);

        //Calendar
        _calendarDate = new calendarDate(0, 0, 0);


        additionalInfo = new AdditionalInfo();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                Toast.makeText(getContext(), "Position = " + position, Toast.LENGTH_SHORT).show();

                _chosenPosition = position;
                _chosenType = _productType[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                _chosenPosition = 0;
            }
        });

        cancellButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        additionalInfoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();

                additionalInfo.setTargetFragment(AddProductFragment.this, 0);
                additionalInfo.show(fragmentManager, "DIALOG");
            }
        });


        applyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                _productName = productNameEditText.getText().toString();
                _dateOfManufacture = _calendarDate.get_date() + "." + _calendarDate.get_month() + "." + _calendarDate.get_year();

                // Gets the database in write mode
                Toast.makeText(getContext(), "Дата: " + _dateOfManufacture, Toast.LENGTH_SHORT).show();
                SQLiteDatabase db = _db.getWritableDatabase();
                //SQLiteDatabase db = DataBaseHelper.mDBHelper.getWritableDatabase();
                // Создаем объект ContentValues, где имена столбцов ключи,

                SQLiteDatabase selectmaxid=_db.getReadableDatabase();

                Cursor cursor = selectmaxid.rawQuery("Select MAX(_ID) from Products;",null);

                int min_free_id;
                String str_min_free_id;

                    cursor.moveToFirst();
                    str_min_free_id=cursor.getString(0);
                    min_free_id = cursor.getInt(0);

                    if (str_min_free_id!=null){
                        min_free_id++;
                    }
                else{
                    min_free_id=0;
                }

                ContentValues valuesOfProduct = new ContentValues();
                valuesOfProduct.put(ProductsTablesContracts.Products._ID, min_free_id);
                valuesOfProduct.put(ProductsTablesContracts.Products.NAME, _productName); // Имя продукта
                valuesOfProduct.put(ProductsTablesContracts.Products.DOM, _dateOfManufacture); // Выбранный тип продукта
                //valuesOfProduct.put(ProductsTablesContracts.Products.PRODUCT_TYPE_ID, ); // Дата изготовления
                valuesOfProduct.put(ProductsTablesContracts.Products.SHELF_LIFE, 0); // Срок хранения
                //valuesOfProduct.put(ProductsTablesContracts.Products.PRODUCT_CHARACTERISTIC_ID, ); //

                ContentValues valuesOfProductCharacteristics = new ContentValues();
                valuesOfProductCharacteristics.put(ProductsTablesContracts.Product_Characteristic._ID, min_free_id);
                valuesOfProductCharacteristics.put(ProductsTablesContracts.Product_Characteristic.CALORIES, _calories);
                valuesOfProductCharacteristics.put(ProductsTablesContracts.Product_Characteristic.PROTEIN, _protein);
                valuesOfProductCharacteristics.put(ProductsTablesContracts.Product_Characteristic.FATNESS, _fatness);
                valuesOfProductCharacteristics.put(ProductsTablesContracts.Product_Characteristic.CARBOHYDRATES, _carbohydrates);
                valuesOfProductCharacteristics.put(ProductsTablesContracts.Product_Characteristic.RATING, _rating);

                long newRowIdProducts = db.insert(ProductsTablesContracts.Products.TABLE_NAME, null, valuesOfProduct);
                long newRowIdCharacteristics = db.insert(ProductsTablesContracts.Product_Characteristic.TABLE_NAME, null, valuesOfProductCharacteristics);

                if (newRowIdProducts == -1) {
                    // Если ID  -1, значит произошла ошибка
                    Toast.makeText(getContext(), "Ошибка при добавлении продукта", Toast.LENGTH_SHORT).show();
                } else {
                   Toast.makeText(getContext(), "Продукт добавлен с ID: " + min_free_id, Toast.LENGTH_SHORT).show();
                }
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView arg0, int year, int month,
                                            int date) {

                month = month + 1;
                Toast.makeText(getContext(), "Дата: " + date + "/" + month + "/" + year, Toast.LENGTH_SHORT).show();
                _calendarDate.setDate(date, month, year);

            }
        });




        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                _rating = rating;
                Toast.makeText(getContext(), "Рейтинг продукта: " + rating, Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data ) {
            String[] buf = data.getStringArrayExtra("DIALOG_RESULT");

            _calories = buf[0];
            _protein = buf[1];
            _fatness = buf[2];
            _carbohydrates = buf[3];
    }


    private String _calories = "0";
    private String _protein = "0";
    private String _fatness = "0";
    private String _carbohydrates = "0";
    private float _rating = 0;

    private DataBaseHelper _db;
    private calendarDate _calendarDate;
    private String _productName;
    private String _dateOfManufacture;
    private String _chosenType;
    private int _chosenPosition;
    private String[] _productType = {"milk", "meat", "eggs", "cheese", "vegetables", "other"};


}
