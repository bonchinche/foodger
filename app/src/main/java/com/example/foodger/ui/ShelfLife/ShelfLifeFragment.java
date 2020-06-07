package com.example.foodger.ui.ShelfLife;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;



import com.example.foodger.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.text.SimpleDateFormat;

public class ShelfLifeFragment extends Fragment {

    private SimpleDateFormat date = new SimpleDateFormat();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_shelflife, container, false);
        //final CalendarView calendarView = root.findViewById(R.id.calendarView);
        final CompactCalendarView calendarView;


        return root;
    }
}
