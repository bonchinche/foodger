package com.example.foodger;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.test.espresso.Espresso;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;


public class AddProductTest {

    private DataBaseHelper _db;
    private Thread BaristaSleepActions;

    public void openNavScreen(String screenName) {
        onView(allOf(isDescendantOfA(withId(R.id.toolbar)), isAssignableFrom(AppCompatImageButton.class))).perform(click());
        onView(withText(screenName)).perform(click());
    }

    public void submitForm() {
        onView(withText("ОК")).perform(click());
        onView(withId(R.id.applyButton)).perform(click());
    }

    public void fillText(int id, String text) {
        onView(withId(id)).perform(click());
        onView(withId(id)).perform(typeText(text));
    }

   /* @Before
    public void deleteDataBase() {
        InstrumentationRegistry.getInstrumentation().getTargetContext().deleteDatabase("Products.db");
    }
    */

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    //Все тесты производятся с удалением продукта

    @Test //Добавление продукта со всей информацией и проверка всех значений в вкладке Products
    public void testFull() {
        openNavScreen("Add Product");
        onView(withId(R.id.productNameEditText)).perform(typeText("MilkFull"));
        onView(withId(R.id.additionalnfoButton)).perform(click());
        fillText(R.id.temperatureEditText, "12");
        fillText(R.id.caloriesTextEdit, "30");
        fillText(R.id.proteinTextEdit, "40");
        fillText(R.id.carbohydratesTextEdit, "40");
        fillText(R.id.fatnessTextEdit, "50");
        fillText(R.id.shelfLifeTextView, "7");
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.productRatingBar)).perform(RatingBarValue.setValue(4));
        onView(withText("ОК")).perform(click());
        onView(withId(R.id.applyButton)).perform(click());
        openNavScreen("Products");
        onView(withText("MilkFull")).perform(click());
        onView(withId(R.id.editTemperature)).check(matches(withText("12")));
        onView(withId(R.id.editcalories)).check(matches(withText("30")));
        onView(withId(R.id.editprotein)).check(matches(withText("40")));
        onView(withId(R.id.editcarbohydrates)).check(matches(withText("40")));
        onView(withId(R.id.editfatness)).check(matches(withText("50")));
        onView(withId(R.id.editShelf)).check(matches(withText("7")));
        onView(withId(R.id.Rating)).check(matches(RatingBarValue.checkValue(4)));
        Espresso.pressBack();
        onView(withId(R.id.deleteButton)).perform(click());
        onView(withText("ДА")).perform(click());
    }

    @Test //Добавление продукта температуры хранения
    public void testWithoutTemperature() {
        openNavScreen("Add Product");
        onView(withId(R.id.productNameEditText)).perform(typeText("MilkWithoutTemperature"));
        onView(withId(R.id.additionalnfoButton)).perform(click());
        fillText(R.id.caloriesTextEdit, "30");
        fillText(R.id.proteinTextEdit, "40");
        fillText(R.id.carbohydratesTextEdit, "40");
        fillText(R.id.fatnessTextEdit, "50");
        fillText(R.id.shelfLifeTextView, "7");
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.productRatingBar)).perform(RatingBarValue.setValue(4));
        onView(withText("ОК")).perform(click());
        onView(withId(R.id.applyButton)).perform(click());
        openNavScreen("Products");
        onView(withId(R.id.deleteButton)).perform(click());
        onView(withText("ДА")).perform(click());
    }

    @Test //Добавление продукта без калорий
    public void testWithoutCalories() {
        openNavScreen("Add Product");
        onView(withId(R.id.productNameEditText)).perform(typeText("MilkWithoutCalories"));
        onView(withId(R.id.additionalnfoButton)).perform(click());
        fillText(R.id.temperatureEditText, "12");
        fillText(R.id.proteinTextEdit, "40");
        fillText(R.id.carbohydratesTextEdit, "40");
        fillText(R.id.fatnessTextEdit, "50");
        fillText(R.id.shelfLifeTextView, "7");
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.productRatingBar)).perform(RatingBarValue.setValue(4));
        onView(withText("ОК")).perform(click());
        onView(withId(R.id.applyButton)).perform(click());
        openNavScreen("Products");
        onView(withId(R.id.deleteButton)).perform(click());
        onView(withText("ДА")).perform(click());
    }

    @Test //Добавление продукта без белков
    public void testWithoutProtein() {
        openNavScreen("Add Product");
        onView(withId(R.id.productNameEditText)).perform(typeText("MilkWithoutProtein"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.additionalnfoButton)).perform(click());
        Espresso.closeSoftKeyboard();
        fillText(R.id.temperatureEditText, "12");
        Espresso.closeSoftKeyboard();
        fillText(R.id.caloriesTextEdit, "30");
        Espresso.closeSoftKeyboard();
        fillText(R.id.carbohydratesTextEdit, "40");
        Espresso.closeSoftKeyboard();
        fillText(R.id.fatnessTextEdit, "50");
        Espresso.closeSoftKeyboard();
        fillText(R.id.shelfLifeTextView, "7");
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.productRatingBar)).perform(RatingBarValue.setValue(4));
        Espresso.closeSoftKeyboard();
        onView(withText("ОК")).perform(click());
        onView(withId(R.id.applyButton)).perform(click());
        openNavScreen("Products");
        onView(withId(R.id.deleteButton)).perform(click());
        onView(withText("ДА")).perform(click());
    }

    @Test //Добавление продукта без углеводов
    public void testWithoutCarbohydrates() throws InterruptedException {
        openNavScreen("Add Product");
        onView(withId(R.id.productNameEditText)).perform(typeText("MilkWithoutCarbohydrates"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.additionalnfoButton)).perform(click());
        Espresso.closeSoftKeyboard();
        BaristaSleepActions.sleep(35000);
        onView(withId(R.id.caloriesTextEdit)).perform(click());
        onView(withId(R.id.caloriesTextEdit)).perform(typeText("30"));
        //fillText(R.id.caloriesTextEdit, "30");
        Espresso.closeSoftKeyboard();
        fillText(R.id.temperatureEditText, "12");
        Espresso.closeSoftKeyboard();
        fillText(R.id.proteinTextEdit, "40");
        Espresso.closeSoftKeyboard();
        fillText(R.id.fatnessTextEdit, "50");
        Espresso.closeSoftKeyboard();
        fillText(R.id.shelfLifeTextView, "7");
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.productRatingBar)).perform(RatingBarValue.setValue(4));
        Espresso.closeSoftKeyboard();
        onView(withText("ОК")).perform(click());
        onView(withId(R.id.applyButton)).perform(click());
        openNavScreen("Products");
        onView(withId(R.id.deleteButton)).perform(click());
        onView(withText("ДА")).perform(click());
    }

    @Test //Добавление продукта без жиров
    public void testWithoutFatness() {
        openNavScreen("Add Product");
        onView(withId(R.id.productNameEditText)).perform(typeText("MilkWithoutFatness"));
        onView(withId(R.id.additionalnfoButton)).perform(click());
        fillText(R.id.temperatureEditText, "12");
        fillText(R.id.caloriesTextEdit, "30");
        fillText(R.id.proteinTextEdit, "40");
        fillText(R.id.carbohydratesTextEdit, "40");
        fillText(R.id.shelfLifeTextView, "7");
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.productRatingBar)).perform(RatingBarValue.setValue(4));
        onView(withText("ОК")).perform(click());
        onView(withId(R.id.applyButton)).perform(click());
        openNavScreen("Products");
        onView(withId(R.id.deleteButton)).perform(click());
        onView(withText("ДА")).perform(click());
    }

    @Test //Добавление продукта без срока годности
    public void testWithoutShelfLife() {
        openNavScreen("Add Product");
        onView(withId(R.id.productNameEditText)).perform(typeText("MilkWithoutShelfLife"));
        onView(withId(R.id.additionalnfoButton)).perform(click());
        fillText(R.id.temperatureEditText, "12");
        fillText(R.id.caloriesTextEdit, "30");
        fillText(R.id.proteinTextEdit, "40");
        fillText(R.id.carbohydratesTextEdit, "40");
        fillText(R.id.fatnessTextEdit, "50");
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.productRatingBar)).perform(RatingBarValue.setValue(4));
        onView(withText("ОК")).perform(click());
        onView(withId(R.id.applyButton)).perform(click());
        openNavScreen("Products");
        onView(withId(R.id.deleteButton)).perform(click());
        onView(withText("ДА")).perform(click());
    }

    @Test //Добавление продукта без рейтинга
    public void testWithoutRating() {
        openNavScreen("Add Product");
        onView(withId(R.id.productNameEditText)).perform(typeText("MilkWithoutRating"));
        onView(withId(R.id.additionalnfoButton)).perform(click());
        fillText(R.id.temperatureEditText, "12");
        fillText(R.id.caloriesTextEdit, "30");
        fillText(R.id.proteinTextEdit, "40");
        fillText(R.id.carbohydratesTextEdit, "40");
        fillText(R.id.fatnessTextEdit, "50");
        fillText(R.id.shelfLifeTextView, "7");
        Espresso.closeSoftKeyboard();
        onView(withText("ОК")).perform(click());
        onView(withId(R.id.applyButton)).perform(click());
        openNavScreen("Products");
        onView(withId(R.id.deleteButton)).perform(click());
        onView(withText("ДА")).perform(click());
    }

    // TODO: 11.06.2020 Добавление продукта с большими значениями

    @Test //Тест кнопки отмена в Additional Information
    public void testAdditionalInfoCancel() {
        openNavScreen("Add Product");
        onView(withId(R.id.productNameEditText)).perform(typeText("MilkAdditionalInfoCancel"));
        onView(withId(R.id.additionalnfoButton)).perform(click());
        fillText(R.id.temperatureEditText, "12");
        fillText(R.id.caloriesTextEdit, "30");
        fillText(R.id.proteinTextEdit, "40");
        fillText(R.id.carbohydratesTextEdit, "40");
        fillText(R.id.fatnessTextEdit, "50");
        fillText(R.id.shelfLifeTextView, "7");
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.productRatingBar)).perform(RatingBarValue.setValue(4));
        onView(withText("ОТМЕНА")).perform(click());
        onView(withId(R.id.additionalnfoButton)).perform(click());
        onView(withId(R.id.temperatureEditText)).check(matches(withText("")));
        onView(withId(R.id.caloriesTextEdit)).check(matches(withText("")));
        onView(withId(R.id.proteinTextEdit)).check(matches(withText("")));
        onView(withId(R.id.carbohydratesTextEdit)).check(matches(withText("")));
        onView(withId(R.id.fatnessTextEdit)).check(matches(withText("")));
        onView(withId(R.id.shelfLifeTextView)).check(matches(withText("")));
        onView(withId(R.id.productRatingBar)).check(matches(RatingBarValue.checkValue(0)));
    }

    @Test //Тест кнопки отмена в Add Products
    public void testAddProductsCancel() {
        openNavScreen("Add Product");
        onView(withId(R.id.productNameEditText)).perform(typeText("MilkAddProductsCancel"));
        onView(withId(R.id.additionalnfoButton)).perform(click());
        fillText(R.id.temperatureEditText, "12");
        fillText(R.id.caloriesTextEdit, "30");
        fillText(R.id.proteinTextEdit, "40");
        fillText(R.id.carbohydratesTextEdit, "40");
        fillText(R.id.fatnessTextEdit, "50");
        fillText(R.id.shelfLifeTextView, "7");
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.productRatingBar)).perform(RatingBarValue.setValue(4));
        onView(withText("ОК")).perform(click());
        onView(withId(R.id.cancellButton)).perform(click());
        onView(withId(R.id.productNameEditText)).check(matches(withText("")));
        onView(withId(R.id.additionalnfoButton)).perform(click());
        onView(withId(R.id.temperatureEditText)).check(matches(withText("")));
        onView(withId(R.id.caloriesTextEdit)).check(matches(withText("")));
        onView(withId(R.id.proteinTextEdit)).check(matches(withText("")));
        onView(withId(R.id.carbohydratesTextEdit)).check(matches(withText("")));
        onView(withId(R.id.fatnessTextEdit)).check(matches(withText("")));
        onView(withId(R.id.shelfLifeTextView)).check(matches(withText("")));
        onView(withId(R.id.productRatingBar)).check(matches(RatingBarValue.checkValue(0)));
    }

    @Test //Тест на занесение в базу данных при нажатии Apply Button
    public void testSetInDataBase() {
      //  InstrumentationRegistry.getInstrumentation().getTargetContext().deleteDatabase("Products.db");
        _db = new DataBaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        openNavScreen("Add Product");
        onView(withId(R.id.productNameEditText)).perform(typeText("MilkSetInDataBase"));
        onView(withId(R.id.additionalnfoButton)).perform(click());
        fillText(R.id.temperatureEditText, "12");
        fillText(R.id.caloriesTextEdit, "30");
        fillText(R.id.proteinTextEdit, "40");
        fillText(R.id.carbohydratesTextEdit, "40");
        fillText(R.id.fatnessTextEdit, "50");
        fillText(R.id.shelfLifeTextView, "7");
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.productRatingBar)).perform(RatingBarValue.setValue(4));
        onView(withText("ОК")).perform(click());
        onView(withId(R.id.applyButton)).perform(click());
        SQLiteDatabase db = _db.getReadableDatabase();

        Cursor productsCursor = db.rawQuery("Select * from Products WHERE _id = " + 0, null);
        productsCursor.moveToFirst();

        assertEquals("12", productsCursor.getString(productsCursor.getColumnIndex(ProductsTablesContracts.Products.TEMPERATURE)));
        assertEquals("MilkSetInDataBase", productsCursor.getString(productsCursor.getColumnIndex(ProductsTablesContracts.Products.NAME)));
        assertEquals("7", productsCursor.getString(productsCursor.getColumnIndex(ProductsTablesContracts.Products.SHELF_LIFE)));



        Cursor productCharacteristicCursor = db.rawQuery("Select * from Product_Characteristic WHERE _id = " + 0, null);
        productCharacteristicCursor.moveToFirst();

        assertEquals("30", productCharacteristicCursor.getString(productCharacteristicCursor.getColumnIndex(ProductsTablesContracts.Product_Characteristic.CALORIES)));
        assertEquals("40", productCharacteristicCursor.getString(productCharacteristicCursor.getColumnIndex(ProductsTablesContracts.Product_Characteristic.PROTEIN)));
        assertEquals("40", productCharacteristicCursor.getString(productCharacteristicCursor.getColumnIndex(ProductsTablesContracts.Product_Characteristic.CARBOHYDRATES)));
        assertEquals("50", productCharacteristicCursor.getString(productCharacteristicCursor.getColumnIndex(ProductsTablesContracts.Product_Characteristic.FATNESS)));
        assertEquals("4", productCharacteristicCursor.getString(productCharacteristicCursor.getColumnIndex(ProductsTablesContracts.Product_Characteristic.RATING)));

        openNavScreen("Products");
        onView(withId(R.id.deleteButton)).perform(click());
        onView(withText("ДА")).perform(click());
    }
}