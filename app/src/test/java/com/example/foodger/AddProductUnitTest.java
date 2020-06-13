package com.example.foodger;

import com.example.foodger.ui.AddProduct.AddProductFragment;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

public class AddProductUnitTest {

    private DataBaseHelper _db;

    public int expireDate(final int shelfLife) {
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DAY_OF_MONTH, shelfLife);
        int trueDate = c.get(Calendar.DAY_OF_MONTH);
        return trueDate;
    }
    //Тестируем корректность вычесления дня истечения срока годности
    @Test
    public void testDayOfSpoilage() {
        AddProductFragment fragment = new AddProductFragment();
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd 00:00:00");
        String formattedDt = sdf.format(dt);
        try {
            dt = sdf.parse(fragment.findDayOfSpoilage(formattedDt, "7", sdf));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = new GregorianCalendar();
        c.setTime(dt);
        int dateOfSpoilage = c.get(Calendar.DAY_OF_MONTH);
        assertEquals(expireDate(7), dateOfSpoilage);
    }
}