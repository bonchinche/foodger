package com.example.foodger;

public class calendarDate {
    private int _date;
    private int _month;
    private int _year;


    public calendarDate(int d, int m, int y){
        _date = d;
        _month = m;
        _year = y;
    }

    public int get_date() {
        return _date;
    }

    public int get_month() {
        return _month;
    }

    public int get_year() {
        return _year;
    }

}
