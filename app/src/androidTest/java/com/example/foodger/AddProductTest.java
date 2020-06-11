package com.example.foodger;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;


public class AddProductTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);
    @Test //Добавление продукта без рейтинга(доработать)
    public void testFull() {
        onView(allOf(isDescendantOfA(withId(R.id.toolbar)), isAssignableFrom(AppCompatImageButton.class))).perform(click());
        onView(withText("Add Product")).perform(click());
        onView(withId(R.id.productNameEditText)).perform(typeText("MilkFull"));
        onView(withId(R.id.additionalnfoButton)).perform(click());
        onView(withId(R.id.temperatureEditText)).perform(click());
        onView(withId(R.id.temperatureEditText)).perform(typeText("12"));
        onView(withId(R.id.caloriesTextEdit)).perform(click());
        onView(withId(R.id.caloriesTextEdit)).perform(typeText("30"));
        onView(withId(R.id.proteinTextEdit)).perform(click());
        onView(withId(R.id.proteinTextEdit)).perform(typeText("40"));
        onView(withId(R.id.carbohydratesTextEdit)).perform(click());
        onView(withId(R.id.carbohydratesTextEdit)).perform(typeText("40"));
        onView(withId(R.id.fatnessTextEdit)).perform(click());
        onView(withId(R.id.fatnessTextEdit)).perform(typeText("50"));
        onView(withId(R.id.shelfLifeTextView)).perform(click());
        onView(withId(R.id.shelfLifeTextView)).perform(typeText("7"));
        Espresso.closeSoftKeyboard();
        onView(withText("ОК")).perform(click());
        onView(withId(R.id.applyButton)).perform(click());
    }
    @Test //Добавление продукта без рейтинга(доработать) и температуры хранения
    public void testWithoutTemperature() {
        onView(allOf(isDescendantOfA(withId(R.id.toolbar)), isAssignableFrom(AppCompatImageButton.class))).perform(click());
        onView(withText("Add Product")).perform(click());
        onView(withId(R.id.productNameEditText)).perform(typeText("MilkWithoutTemperature"));
        onView(withId(R.id.additionalnfoButton)).perform(click());
        onView(withId(R.id.caloriesTextEdit)).perform(click());
        onView(withId(R.id.caloriesTextEdit)).perform(typeText("30"));
        onView(withId(R.id.proteinTextEdit)).perform(click());
        onView(withId(R.id.proteinTextEdit)).perform(typeText("40"));
        onView(withId(R.id.carbohydratesTextEdit)).perform(click());
        onView(withId(R.id.carbohydratesTextEdit)).perform(typeText("40"));
        onView(withId(R.id.fatnessTextEdit)).perform(click());
        onView(withId(R.id.fatnessTextEdit)).perform(typeText("50"));
        onView(withId(R.id.shelfLifeTextView)).perform(click());
        onView(withId(R.id.shelfLifeTextView)).perform(typeText("7"));
        Espresso.closeSoftKeyboard();
        onView(withText("ОК")).perform(click());
        onView(withId(R.id.applyButton)).perform(click());
    }
    @Test //Добавление продукта без рейтинга(доработать) и калорий
    public void testWithoutCalories() {
        onView(allOf(isDescendantOfA(withId(R.id.toolbar)), isAssignableFrom(AppCompatImageButton.class))).perform(click());
        onView(withText("Add Product")).perform(click());
        onView(withId(R.id.productNameEditText)).perform(typeText("MilkWithoutCalories"));
        onView(withId(R.id.additionalnfoButton)).perform(click());
        onView(withId(R.id.temperatureEditText)).perform(click());
        onView(withId(R.id.temperatureEditText)).perform(typeText("12"));
        onView(withId(R.id.proteinTextEdit)).perform(click());
        onView(withId(R.id.proteinTextEdit)).perform(typeText("40"));
        onView(withId(R.id.carbohydratesTextEdit)).perform(click());
        onView(withId(R.id.carbohydratesTextEdit)).perform(typeText("40"));
        onView(withId(R.id.fatnessTextEdit)).perform(click());
        onView(withId(R.id.fatnessTextEdit)).perform(typeText("50"));
        onView(withId(R.id.shelfLifeTextView)).perform(click());
        onView(withId(R.id.shelfLifeTextView)).perform(typeText("7"));
        Espresso.closeSoftKeyboard();
        onView(withText("ОК")).perform(click());
        onView(withId(R.id.applyButton)).perform(click());
    }
    @Test //Добавление продукта без рейтинга(доработать) и белков
    public void testWithoutProtein() {
        onView(allOf(isDescendantOfA(withId(R.id.toolbar)), isAssignableFrom(AppCompatImageButton.class))).perform(click());
        onView(withText("Add Product")).perform(click());
        onView(withId(R.id.productNameEditText)).perform(typeText("MilkWithoutProtein"));
        onView(withId(R.id.additionalnfoButton)).perform(click());
        onView(withId(R.id.temperatureEditText)).perform(click());
        onView(withId(R.id.temperatureEditText)).perform(typeText("12"));
        onView(withId(R.id.caloriesTextEdit)).perform(click());
        onView(withId(R.id.caloriesTextEdit)).perform(typeText("30"));
        onView(withId(R.id.carbohydratesTextEdit)).perform(click());
        onView(withId(R.id.carbohydratesTextEdit)).perform(typeText("40"));
        onView(withId(R.id.fatnessTextEdit)).perform(click());
        onView(withId(R.id.fatnessTextEdit)).perform(typeText("50"));
        onView(withId(R.id.shelfLifeTextView)).perform(click());
        onView(withId(R.id.shelfLifeTextView)).perform(typeText("7"));
        Espresso.closeSoftKeyboard();
        onView(withText("ОК")).perform(click());
        onView(withId(R.id.applyButton)).perform(click());
    }
    @Test //Добавление продукта без рейтинга(доработать) и углеводов
    public void testWithoutCarbohydrates() {
        onView(allOf(isDescendantOfA(withId(R.id.toolbar)), isAssignableFrom(AppCompatImageButton.class))).perform(click());
        onView(withText("Add Product")).perform(click());
        onView(withId(R.id.productNameEditText)).perform(typeText("MilkWithoutCarbohydrates"));
        onView(withId(R.id.additionalnfoButton)).perform(click());
        onView(withId(R.id.temperatureEditText)).perform(click());
        onView(withId(R.id.temperatureEditText)).perform(typeText("12"));
        onView(withId(R.id.caloriesTextEdit)).perform(click());
        onView(withId(R.id.caloriesTextEdit)).perform(typeText("30"));
        onView(withId(R.id.proteinTextEdit)).perform(click());
        onView(withId(R.id.proteinTextEdit)).perform(typeText("40"));
        onView(withId(R.id.fatnessTextEdit)).perform(click());
        onView(withId(R.id.fatnessTextEdit)).perform(typeText("50"));
        onView(withId(R.id.shelfLifeTextView)).perform(click());
        onView(withId(R.id.shelfLifeTextView)).perform(typeText("7"));
        Espresso.closeSoftKeyboard();
        onView(withText("ОК")).perform(click());
        onView(withId(R.id.applyButton)).perform(click());
    }
    @Test //Добавление продукта без рейтинга(доработать) и жиров
    public void testWithoutFatness() {
        onView(allOf(isDescendantOfA(withId(R.id.toolbar)), isAssignableFrom(AppCompatImageButton.class))).perform(click());
        onView(withText("Add Product")).perform(click());
        onView(withId(R.id.productNameEditText)).perform(typeText("MilkWithoutFatness"));
        onView(withId(R.id.additionalnfoButton)).perform(click());
        onView(withId(R.id.temperatureEditText)).perform(click());
        onView(withId(R.id.temperatureEditText)).perform(typeText("12"));
        onView(withId(R.id.caloriesTextEdit)).perform(click());
        onView(withId(R.id.caloriesTextEdit)).perform(typeText("30"));
        onView(withId(R.id.proteinTextEdit)).perform(click());
        onView(withId(R.id.proteinTextEdit)).perform(typeText("40"));
        onView(withId(R.id.carbohydratesTextEdit)).perform(click());
        onView(withId(R.id.carbohydratesTextEdit)).perform(typeText("40"));
        onView(withId(R.id.shelfLifeTextView)).perform(click());
        onView(withId(R.id.shelfLifeTextView)).perform(typeText("7"));
        Espresso.closeSoftKeyboard();
        onView(withText("ОК")).perform(click());
        onView(withId(R.id.applyButton)).perform(click());
    }
    @Test //Добавление продукта без рейтинга(доработать) и срока годности
    public void testWithoutShelfLife() {
        onView(allOf(isDescendantOfA(withId(R.id.toolbar)), isAssignableFrom(AppCompatImageButton.class))).perform(click());
        onView(withText("Add Product")).perform(click());
        onView(withId(R.id.productNameEditText)).perform(typeText("MilkWithoutShelfLife"));
        onView(withId(R.id.additionalnfoButton)).perform(click());
        onView(withId(R.id.temperatureEditText)).perform(click());
        onView(withId(R.id.temperatureEditText)).perform(typeText("12"));
        onView(withId(R.id.caloriesTextEdit)).perform(click());
        onView(withId(R.id.caloriesTextEdit)).perform(typeText("30"));
        onView(withId(R.id.proteinTextEdit)).perform(click());
        onView(withId(R.id.proteinTextEdit)).perform(typeText("40"));
        onView(withId(R.id.carbohydratesTextEdit)).perform(click());
        onView(withId(R.id.carbohydratesTextEdit)).perform(typeText("40"));
        onView(withId(R.id.fatnessTextEdit)).perform(click());
        onView(withId(R.id.fatnessTextEdit)).perform(typeText("50"));
        Espresso.closeSoftKeyboard();
        onView(withText("ОК")).perform(click());
        onView(withId(R.id.applyButton)).perform(click());
    }
}