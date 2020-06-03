package com.example.foodger.ui.ShelfLife;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShelfLifeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ShelfLifeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}