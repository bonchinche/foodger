package com.example.foodger.ui.AddProduct;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddProductFragment extends Fragment{



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_addproduct, container, false);

        Button cancellButton = (Button)root.findViewById(R.id.cancellButton);
        Button applyButton = (Button)root.findViewById(R.id.applyButton);
        Button additionalInfoButton = (Button)root.findViewById(R.id.additionalnfoButton);
        final RatingBar ratingBar = (RatingBar)root.findViewById(R.id.ratingBar);
        final EditText productNameEditText = (EditText)root.findViewById(R.id.productNameEditText);
        final CalendarView calendarView = (CalendarView)root.findViewById(R.id.Layout);
        final Spinner spinner = (Spinner)root.findViewById(R.id.productCategorySpinner);
        final long date = calendarView.getDate();
        //DataBase
        _db = new DataBaseHelper(getContext());


        //адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, _productType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Выпадающий список
        spinner.setAdapter(adapter);
        spinner.setPrompt("Choose your category");
        spinner.setSelection(0);

        //Rating bar
        ratingBar.setStepSize(1);
        ratingBar.setRating(0);
        //Calendar

        Calendar calendar = Calendar.getInstance();
        _calendarDate = new calendarDate(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));

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
                productNameEditText.setText("");
                spinner.setSelection(0);
                ratingBar.setRating(0);
                calendarView.setDate(date);
            }
        });

        additionalInfoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                AdditionalInfo additionalInfo = new AdditionalInfo(_calories, _protein, _carbohydrates, _fatness, _shelfLife);
                additionalInfo.setTargetFragment(AddProductFragment.this, 0);
                additionalInfo.show(fragmentManager, "DIALOG");
            }
        });


        applyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                _productName = productNameEditText.getText().toString();
                _dateOfManufacture = _calendarDate.get_year() + "/" + _calendarDate.get_month() + "/" + _calendarDate.get_date() + " 00:00:00";

                //date2 = sdf.parse(myDate);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd 00:00:00");
                Date dt = new Date();
                try {
                    dt = sdf.parse(_dateOfManufacture); // присваиваем dt значение текущей даты
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar c = Calendar.getInstance();
                c.setTime(dt);
//                c.add(Calendar.DATE, Integer.parseInt(_shelfLife));
                dt = c.getTime();
                _dateOfSpoilage = sdf.format(dt);
                Log.d("TEST", "***************************************************************");
                Log.d("DATE OF MANIFACTURE: ", _dateOfManufacture);
                Log.d("DATE OF SPOILAGE: ", _dateOfSpoilage);
                Log.d("TEST", "***************************************************************");


                // Gets the database in write mode
                //Toast.makeText(getContext(), "Дата: " + _dateOfManufacture, Toast.LENGTH_SHORT).show()
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

                //SQLiteDatabase db = DataBaseHelper.mDBHelper.getWritableDatabase();
                // Создаем объект ContentValues, где имена столбцов ключи,


                ContentValues valuesOfProduct = new ContentValues();
                valuesOfProduct.put(ProductsTablesContracts.Products._ID, min_free_id);
                valuesOfProduct.put(ProductsTablesContracts.Products.NAME, _productName); // Имя продукта
                valuesOfProduct.put(ProductsTablesContracts.Products.DOM, _dateOfManufacture); // Дата изготовления
                valuesOfProduct.put(ProductsTablesContracts.Products.DOS, _dateOfSpoilage); // Дата порчи продукта
                //valuesOfProduct.put(ProductsTablesContracts.Products.PRODUCT_TYPE_ID, ); // Дата изготовления
                valuesOfProduct.put(ProductsTablesContracts.Products.SHELF_LIFE, 5); // Срок хранения
                //valuesOfProduct.put(ProductsTablesContracts.Products.PRODUCT_CHARACTERISTIC_ID, ); //

                ContentValues valuesOfProductCharacteristics = new ContentValues();
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


                productNameEditText.setText("");
                spinner.setSelection(0);
                ratingBar.setRating(0);
                calendarView.setDate(date);
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
        _carbohydrates = buf[2];
        _fatness = buf[3];
        _shelfLife = buf[4];
    }


    private String _calories = "";
    private String _protein = "";
    private String _carbohydrates = "";
    private String _fatness = "";
    private String _shelfLife = "";
    private float _rating = 0;

    private DataBaseHelper _db;
    private calendarDate _calendarDate;
    private String _productName;
    private String _dateOfManufacture;
    private String _chosenType;
    private String _dateOfSpoilage;
    private int _chosenPosition;
    private String[] _productType = {"milk", "meat", "eggs", "cheese", "vegetables", "other"};


}
