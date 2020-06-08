package com.example.foodger.ui.ShelfLife;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.example.foodger.DataBaseHelper;
import com.example.foodger.MainActivity;
import com.example.foodger.ProductsTablesContracts;
import com.example.foodger.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShelfLifeFragment extends Fragment {

    private int _currentMonth;
    private DataBaseHelper dbHelper;

    private String getCurrentMonth(String curMonth) {


        //Calendar calendar = Calendar.getInstance();
        //int month = calendar.get(Calendar.MONTH) + 1;
        int month = Integer.parseInt (curMonth);
        String monthStr = "";
        switch(month){
            case 1:{
                monthStr = "Январь";
                break;
            }
            case 2:{
                monthStr = "Февраль";
                break;
            }
            case 3:{
                monthStr = "Март";
                break;
            }
            case 4:{
                monthStr = "Апрель";
                break;
            }
            case 5:{
                monthStr = "Май";
                break;
            }
            case 6:{
                monthStr = "Июнь";
                break;
            }
            case 7:{
                monthStr = "Июль";
                break;
            }
            case 8:{
                monthStr = "Август";
                break;
            }
            case 9:{
                monthStr = "Сентябрь";
                break;
            }
            case 10:{
                monthStr = "Октябрь";
                break;
            }
            case 11:{
                monthStr = "Ноябрь";
                break;
            }
            case 12:{
                monthStr = "Декабрь";
                break;
            }
        }
       return monthStr;
    }

    private SimpleDateFormat date = new SimpleDateFormat("M");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_shelflife, container, false);
        //final CalendarView calendarView = root.findViewById(R.id.calendarView);
        final CompactCalendarView compactCalendarView;
        final ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        final TextView monthTextView = (TextView)root.findViewById(R.id.monthTextView);
        //actionBar.setDisplayHomeAsUpEnabled(false);
        //actionBar.setTitle(null);

        dbHelper = new DataBaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Calendar calendar = Calendar.getInstance();
        _currentMonth = calendar.get(Calendar.MONTH) + 1;
        String curMonth = String.valueOf(_currentMonth);

        monthTextView.setText(getCurrentMonth(curMonth));

        compactCalendarView = (CompactCalendarView)root.findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);

        String[] projection = {
                ProductsTablesContracts.Products._ID,
                ProductsTablesContracts.Products.PRODUCT_TYPE_ID,
                ProductsTablesContracts.Products.NAME,
                ProductsTablesContracts.Products.SHELF_LIFE,
                ProductsTablesContracts.Products.DOM};

        String[] selection = {
                ProductsTablesContracts.Products.SHELF_LIFE
        };

        Cursor cursor = db.query(
                ProductsTablesContracts.Products.TABLE_NAME,   // таблица
                projection,            // столбцы
                ProductsTablesContracts.Products.SHELF_LIFE,   // столбцы для условия WHERE
                null,                  // значения для условия WHERE
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);



        String myDate = "2020/06/09 00:00:00"; //1596978249000
                                                //1591707849000
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date2 = null;
        try {
            date2 = sdf.parse(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millis = date2.getTime();

        Event event = new Event(R.color.Red, millis, "EVENT");

        compactCalendarView.addEvent(event);

        Date month = compactCalendarView.getFirstDayOfCurrentMonth();


        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getContext();
                SimpleDateFormat dayFormat = new SimpleDateFormat("d", Locale.getDefault());
                SimpleDateFormat monthFormat = new SimpleDateFormat("M");
                SimpleDateFormat yearFormat = new SimpleDateFormat("YYYY");

                int day = Integer.parseInt(dayFormat.format(dateClicked));
                int month = Integer.parseInt(monthFormat.format(dateClicked));
                int year = Integer.parseInt(yearFormat.format(dateClicked));

                FragmentManager fragmentManager = getFragmentManager();
                ProductsDialog productsDialog = new ProductsDialog();
                productsDialog.setTargetFragment(ShelfLifeFragment.this, 0);
                productsDialog.show(fragmentManager, "DIALOG");

                Toast.makeText(context, day + "/" + month + "/" + year, Toast.LENGTH_SHORT).show();



                /*
                if(dateClicked.toString().compareTo("Fri Oct 21 00:00:00 AST 2020") == 0){
                    Toast.makeText(context, "TEACHERS DAY", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "NO EVENTS", Toast.LENGTH_SHORT).show();
                }

                 */
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                monthTextView.setText(getCurrentMonth(date.format(firstDayOfNewMonth)));

            }
        });

        return root;
    }
}
