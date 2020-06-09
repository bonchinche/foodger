package com.example.foodger.ui.ShelfLife;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.foodger.DataBaseHelper;
import com.example.foodger.ProductsTablesContracts;
import com.example.foodger.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class ShelfLifeFragment extends Fragment {

    private int _currentMonth;
    private DataBaseHelper dbHelper;

    private String getCurrentMonth(String curMonth) {
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

        final View root = inflater.inflate(R.layout.fragment_shelflife, container, false);
        final CompactCalendarView compactCalendarView;
        final TextView monthTextView = (TextView)root.findViewById(R.id.monthTextView);

        dbHelper = new DataBaseHelper(getContext());
        final SQLiteDatabase db = dbHelper.getReadableDatabase();

        Calendar calendar = Calendar.getInstance();
        _currentMonth = calendar.get(Calendar.MONTH) + 1;
        String curMonth = String.valueOf(_currentMonth);

        monthTextView.setText(getCurrentMonth(curMonth));

        compactCalendarView = (CompactCalendarView)root.findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);

        Cursor cursor = db.rawQuery("Select DOS from Products", null);
        final List<String> datesOfSpoilage = new ArrayList<>();
        if(cursor != null) {
            cursor.moveToFirst();
            Log.d("CURSOR#1", "***************************************************************************");
            if (cursor != null && cursor.moveToFirst()) {
                datesOfSpoilage.add(cursor.getString(0));
                while (cursor.moveToNext()) {
                    int columnIndex = cursor.getColumnIndex(ProductsTablesContracts.Products.DOS);
                    datesOfSpoilage.add(cursor.getString(columnIndex));
                }
            }
        }
        Log.d("SIZE OF LIST ", "size = " + datesOfSpoilage.size());

        for(int i=0; i < datesOfSpoilage.size(); i++) {
            Object element = datesOfSpoilage.get(i);
            Log.d("DOM LIST", "element = " + element.toString());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date2 = null;
            try {
                date2 = sdf.parse(datesOfSpoilage.get(i));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long millis = date2.getTime();
            Event event = new Event(R.color.Red, millis, "EVENT");
            compactCalendarView.addEvent(event);
        }

            Log.d("CURSOR#1", "***************************************************************************");


        Date month = compactCalendarView.getFirstDayOfCurrentMonth();


        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                Context context = getContext();
                SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
                SimpleDateFormat yearFormat = new SimpleDateFormat("YYYY");

                String day = dayFormat.format(dateClicked);
                String month = monthFormat.format(dateClicked);
                String year = yearFormat.format(dateClicked);

                Cursor cursor = db.rawQuery("Select Name from Products WHERE DOS like '%" + year + "/" + month + "/" + day + "%'", null);
                List<String> currentDOM = new ArrayList<>();
                Log.d(" CURSOR#2", "***************************************************************************");
                Log.d(" DOS#2", year + "/" + month + "/" + day);
                if(cursor != null && cursor.moveToFirst()){
                    currentDOM.add(cursor.getString(0));
                    while(cursor.moveToNext()){
                        int columnIndex = cursor.getColumnIndex(ProductsTablesContracts.Products.NAME);
                        currentDOM.add(cursor.getString(columnIndex));
                    }
                }

                for(int i=0; i < currentDOM.size(); i++) {
                    Object element = currentDOM.get(i);
                    Log.d("CURRENT DOM", "element = " + element.toString());
                }

                    Log.d("CURSOR#2", "***************************************************************************");
                FragmentManager fragmentManager = getFragmentManager();
                ProductsDialog productsDialog = new ProductsDialog(currentDOM);
                productsDialog.setTargetFragment(ShelfLifeFragment.this, 0);
                productsDialog.show(fragmentManager, "DIALOG");

                Toast.makeText(context, day + "/" + month + "/" + year, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                monthTextView.setText(getCurrentMonth(date.format(firstDayOfNewMonth)));

            }
        });
        return root;
    }
}
