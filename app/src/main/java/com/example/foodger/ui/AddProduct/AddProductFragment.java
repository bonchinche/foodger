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
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class AddProductFragment extends Fragment{

    EditText text_searcher;
    TextView text_searcher_textview;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        text_searcher=(EditText)getActivity().findViewById(R.id.SearchEdit);
        text_searcher.setVisibility(View.INVISIBLE);
        text_searcher_textview=(TextView)getActivity().findViewById(R.id.SearchText);
        text_searcher_textview.setVisibility(View.INVISIBLE);

        final View root = inflater.inflate(R.layout.fragment_addproduct, container, false);

        Button cancellButton = (Button)root.findViewById(R.id.cancellButton);
        Button applyButton = (Button)root.findViewById(R.id.applyButton);
        Button additionalInfoButton = (Button)root.findViewById(R.id.additionalnfoButton);
        final EditText productNameEditText = (EditText)root.findViewById(R.id.productNameEditText);
        final CalendarView calendarView = (CalendarView)root.findViewById(R.id.Layout);
        final Spinner spinner = (Spinner)root.findViewById(R.id.productCategorySpinner);
        final long date = calendarView.getDate();
        //DataBase
        _db = new DataBaseHelper(getContext());


        SQLiteDatabase for_type_products_select=_db.getReadableDatabase();
        Cursor check_types=for_type_products_select.rawQuery("Select TYPE_NAME FROM "+ ProductsTablesContracts.Product_Type.TABLE_NAME,null);
        check_types.moveToFirst();
        String current_name=check_types.getString(check_types.getColumnIndex(ProductsTablesContracts.Product_Type.TYPE_NAME));
        _productType.add(current_name);
        while (check_types.moveToNext()){
            current_name=check_types.getString(check_types.getColumnIndex(ProductsTablesContracts.Product_Type.TYPE_NAME));
            _productType.add(current_name);
        }

        //адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, _productType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Выпадающий список
        spinner.setAdapter(adapter);
        spinner.setPrompt("Выберите категорию продукта");
        spinner.setSelection(0);

        //Calendar

        Calendar calendar = Calendar.getInstance();
        _calendarDate = new calendarDate(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                _chosenPosition = position;
              //  _chosenType = _productType[position];
                _chosenType = _productType.get(position);
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
                calendarView.setDate(date);

                reset();
            }
        });

        additionalInfoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                additionalInfo = new AdditionalInfo(_calories, _protein, _carbohydrates, _fatness, _shelfLife, _temperature, _rating);
                additionalInfo.setTargetFragment(AddProductFragment.this, 0);
                additionalInfo.show(fragmentManager, "DIALOG");
            }
        });


        applyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (productNameEditText.getText().toString().length() != 0) {
                    _productName = productNameEditText.getText().toString();
                    _dateOfManufacture = _calendarDate.get_year() + "/" + _calendarDate.get_month() + "/" + _calendarDate.get_date() + " 00:00:00";

                    //date2 = sdf.parse(myDate);



                    // Gets the database in write mode
                    //Toast.makeText(getContext(), "Дата: " + _dateOfManufacture, Toast.LENGTH_SHORT).show()
                    SQLiteDatabase db = _db.getWritableDatabase();
                    //SQLiteDatabase db = DataBaseHelper.mDBHelper.getWritableDatabase();
                    // Создаем объект ContentValues, где имена столбцов ключи,

                    SQLiteDatabase selectmaxid = _db.getReadableDatabase();

                    Cursor cursor = selectmaxid.rawQuery("Select MAX(_ID) from Products;", null);

                    int min_free_id;
                    String str_min_free_id;

                    cursor.moveToFirst();
                    str_min_free_id = cursor.getString(0);
                    min_free_id = cursor.getInt(0);

                    if (str_min_free_id != null) {
                        min_free_id++;
                    } else {
                        min_free_id = 0;
                    }

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd 00:00:00");

                    if(_shelfLife.length() == 0){
                        if(_chosenPosition != 6) {
                            Cursor shelfLifeCursor = db.rawQuery("Select AVG_SHELF_LIFE from Product_Type WHERE _ID = " + _chosenPosition, null);

                            if (shelfLifeCursor != null) {
                                shelfLifeCursor.moveToFirst();
                                //_shelfLife = shelfLifeCursor.getString(0);
                                _dateOfSpoilage = findDayOfSpoilage(_dateOfManufacture, shelfLifeCursor.getString(0), sdf);
                                Log.d("TEST", "***************************************************************");
                                Log.d("SHELF LIFE: ", _shelfLife);
                                Log.d("TEST", "***************************************************************");
                            }
                        }else{
                            Toast.makeText(getContext(), "Введите срок годности продукта!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }else{
                        _dateOfSpoilage = findDayOfSpoilage(_dateOfManufacture, _shelfLife, sdf);
                    }

                    //_dateOfSpoilage = findDayOfSpoilage(_dateOfManufacture, _shelfLife, sdf);

                    Log.d("TEST", "***************************************************************");
                    Log.d("DATE OF MANIFACTURE: ", _dateOfManufacture);
                    Log.d("SHELF LIFE: ", _shelfLife);
                    Log.d("DATE OF SPOILAGE: ", _dateOfSpoilage);
                    Log.d("TEST", "***************************************************************");

                    //SQLiteDatabase db = DataBaseHelper.mDBHelper.getWritableDatabase();
                    // Создаем объект ContentValues, где имена столбцов ключи,


                    ContentValues valuesOfProduct = new ContentValues();
                    valuesOfProduct.put(ProductsTablesContracts.Products._ID, min_free_id);
                    valuesOfProduct.put(ProductsTablesContracts.Products.PRODUCT_TYPE_ID, _chosenPosition);
                    valuesOfProduct.put(ProductsTablesContracts.Products.NAME, _productName); // Имя продукта
                    valuesOfProduct.put(ProductsTablesContracts.Products.DOM, _dateOfManufacture); // Дата изготовления
                    valuesOfProduct.put(ProductsTablesContracts.Products.DOS, _dateOfSpoilage); // Дата порчи продукта
                    valuesOfProduct.put(ProductsTablesContracts.Products.TEMPERATURE, _temperature); // Дата изготовления
                    valuesOfProduct.put(ProductsTablesContracts.Products.SHELF_LIFE, _shelfLife); // Срок хранения

                    ContentValues valuesOfProductCharacteristics = new ContentValues();
                    valuesOfProductCharacteristics.put(ProductsTablesContracts.Product_Characteristic._ID, min_free_id);
                    valuesOfProductCharacteristics.put(ProductsTablesContracts.Product_Characteristic.CALORIES, _calories);
                    valuesOfProductCharacteristics.put(ProductsTablesContracts.Product_Characteristic.PROTEIN, _protein);
                    valuesOfProductCharacteristics.put(ProductsTablesContracts.Product_Characteristic.FATNESS, _fatness);
                    valuesOfProductCharacteristics.put(ProductsTablesContracts.Product_Characteristic.CARBOHYDRATES, _carbohydrates);
                    valuesOfProductCharacteristics.put(ProductsTablesContracts.Product_Characteristic.RATING, _rating);
                    Log.d("TEST", "***************************************************************");
                    Log.d("ADDED CHAR", "CALORIES = " + _calories + " PROTEIN = " + _protein + " FATNESS = " + _fatness + " CARBO = " + _carbohydrates + " TEMPERATURE = " + _temperature + " RATING = " + _rating);
                    Log.d("TEST", "***************************************************************");
                    long newRowIdProducts = db.insert(ProductsTablesContracts.Products.TABLE_NAME, null, valuesOfProduct);
                    long newRowIdCharacteristics = db.insert(ProductsTablesContracts.Product_Characteristic.TABLE_NAME, null, valuesOfProductCharacteristics);
                    Log.d("TEST", "NEW ROW IN CHAR = " + newRowIdCharacteristics);

                    if (newRowIdProducts == -1) {
                        // Если ID  -1, значит произошла ошибка
                        Toast.makeText(getContext(), "Ошибка при добавлении продукта", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Продукт добавлен с ID: " + min_free_id, Toast.LENGTH_SHORT).show();
                    }


                    productNameEditText.setText("");
                    spinner.setSelection(0);
                    calendarView.setDate(date);
                    reset();
                }else{
                    Toast.makeText(getContext(), "Пустое название продукта! ", Toast.LENGTH_LONG).show();
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





        return root;
    }

    public void reset(){
        if(additionalInfo != null){
            additionalInfo.reset();
        }

        _calories = "";
        _protein = "";
        _carbohydrates = "";
        _fatness = "";
        _shelfLife = "";
        _temperature = "";
        _rating = "";
    }

    public String findDayOfSpoilage(String dateOfManufacture, String shelfLife, SimpleDateFormat sdf){
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd 00:00:00"); // Задаем формат даты
        Date dt = new Date();
        try {
            dt = sdf.parse(dateOfManufacture); // присваиваем dt значение даты изготовления продукта
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, Integer.parseInt(shelfLife)); //
        dt = c.getTime();
        String dateOfSpoilage = sdf.format(dt); // получаем обычный формат даты
        return dateOfSpoilage;
    }

    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data ) {
        String[] buf = data.getStringArrayExtra("DIALOG_RESULT");

        _calories = buf[0];
        _protein = buf[1];
        _carbohydrates = buf[2];
        _fatness = buf[3];
        _shelfLife = buf[4];
        _temperature = buf[5];
        _rating = buf[6];
    }

    private AdditionalInfo additionalInfo;

    private String _calories = "";
    private String _protein = "";
    private String _carbohydrates = "";
    private String _fatness = "";
    private String _shelfLife = "";
    private String _rating = "";
    private String _temperature = "";

    private DataBaseHelper _db;
    private calendarDate _calendarDate;
    private String _productName;
    private String _dateOfManufacture;
    private String _chosenType;
    private String _dateOfSpoilage;
    private int _chosenPosition;
    //private String[] _productType = {"\uD83E\uDD66 Овощи", "\uD83E\uDDC0 Сыр", "\uD83E\uDD5A Яйца ", "\uD83E\uDD69 Мясо", "\uD83E\uDD5B Молоко", "\uD83C\uDF4E Фрукты", "Другая"};
    ArrayList<String> _productType=new ArrayList<>();


}
