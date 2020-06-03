package com.example.foodger.ui.Nutrition;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NutritionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NutritionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}