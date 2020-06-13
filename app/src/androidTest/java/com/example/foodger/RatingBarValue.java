package com.example.foodger;

import android.view.View;
import android.widget.RatingBar;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public final class RatingBarValue implements ViewAction {

    private float rating;

    private RatingBarValue(float rating) {
        this.rating = rating;
    }

    public static ViewAction setValue(float rating) {
        return new RatingBarValue(rating);
    }
    public static Matcher<View> checkValue(final float rating) {
       return new TypeSafeMatcher<View> (RatingBar.class) {
           @Override
           protected boolean matchesSafely(View item) {
               RatingBar bar = (RatingBar) item;
               return rating == bar.getRating();
           }
           @Override
           public void describeTo(Description description) {

           }
       };
    }

    @Override
    public Matcher<View> getConstraints() {
        Matcher <View> isRatingBarConstraint = ViewMatchers.isAssignableFrom(RatingBar.class);
        return isRatingBarConstraint;
    }

    @Override
    public String getDescription() {
        return "Custom view action to set rating.";
    }

    @Override
    public void perform(UiController uiController, View view) {
        RatingBar ratingBar = (RatingBar) view;
        ratingBar.setRating(this.rating);
    }
}