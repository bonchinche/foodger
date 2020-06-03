package com.example.foodger.ui.AddProduct;

import android.content.ContentValues;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.foodger.DataBaseHelper;
import com.example.foodger.ProductsTablesContracts;
import com.example.foodger.R;
import com.example.foodger.calendarDate;
import com.example.foodger.ui.Products.ProductsFragment;

public class AddProductFragment extends Fragment {

    private AddProductViewModel addProductViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addProductViewModel = ViewModelProviders.of(this).get(AddProductViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_addproduct, container, false);

        Button cancellButton = (Button)root.findViewById(R.id.cancellButton);
        Button applyButton = (Button)root.findViewById(R.id.applyButton);
        Button additionalInfoButton = (Button)root.findViewById(R.id.additionalnfoButton);
        final RatingBar ratingBar = (RatingBar)root.findViewById(R.id.ratingBar);
        final EditText productNameEditText = (EditText)root.findViewById(R.id.productNameEditText);
        final CalendarView calendarView = (CalendarView)root.findViewById(R.id.calendarView);
        Spinner spinner = (Spinner)root.findViewById(R.id.productCategorySpinner);

        //адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, _productType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Выпадающий список
        spinner.setAdapter(adapter);
        spinner.setPrompt("Choose your category");
        spinner.setSelection(1);
        //Rating bar
        ratingBar.setStepSize(1);

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
                //AdditionalInfo additionalInfoFragment
                //DrawerLayout drawer = root.findViewById(R.layout.fragment_additionalinfo);

            }
        });


        applyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                _productName = productNameEditText.getText().toString();
                //intent.putExtra("productName", _productName);
                String dateOfManufacture = _calendarDate.get_date() + "." + _calendarDate.get_month() + "." + _calendarDate.get_year();
                //ProductsFragment.mDb.execSQL("INSERT INTO products (_id, name, category, day, month, year, rating)\n" +


                // Gets the database in write mode
                SQLiteDatabase db = _db.getReadableDatabase();
                //SQLiteDatabase db = DataBaseHelper.mDBHelper.getWritableDatabase();
                // Создаем объект ContentValues, где имена столбцов ключи,

                ContentValues values = new ContentValues();
                //values.put(ProductsTablesContracts.Products.PRODUCT_NAME, _productName); // Имя продукта
                //values.put(ProductsTablesContracts.Product.PRODUCT_TYPE_ID, _chosenPosition); // Выбранный тип продукта
                //values.put(ProductsTablesContracts.Product.PRODUCT_DOM, dateOfManufacture); // Дата изготовления
                //values.put(ProductsTablesContracts.Product.SHELF_LIFE, ); // Срок хранения
                //values.put(ProductsTablesContracts.Product.PRODUCT_CHARACTERISTIC_ID, ); //

                long newRowId = db.insert(ProductsTablesContracts.Products.TABLE_NAME, null, values);

                if (newRowId == -1) {
                    // Если ID  -1, значит произошла ошибка
                    Toast.makeText(getContext(), "Ошибка при добавлении продукта", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Продукт добавлен под номером: " + newRowId, Toast.LENGTH_SHORT).show();
                }


                //_prDate = _calendarView.getDate();
                //_productionDate.setDate(_calendarView.getDateTextAppearance());
                //Toast.makeText(getBaseContext(), "Pr = " + _productName, Toast.LENGTH_SHORT).show();
                //closeActivity();
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView arg0, int year, int month,
                                            int date) {

                month = month + 1;
                Toast.makeText(getContext(), "Дата: " + date + "/" + month + "/" + year, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(),date+ "/"+month+"/"+year,4000).show();
                _calendarDate = new calendarDate(date, month, year);

            }
        });


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(getContext(), "Рейтинг продукта: " + ratingBar.getRating(), Toast.LENGTH_SHORT).show();
            }
        });

        addProductViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s); //test
            }
        });
        return root;
    }




    private DataBaseHelper _db;
    private calendarDate _calendarDate;
    private String _productName;
    private String _chosenType;
    private int _chosenPosition;
    private String[] _productType = {"milk", "meat", "eggs", "cheese", "vegetables", "other"};
}
