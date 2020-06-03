package com.example.foodger.ui.Products;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProductsViewModel  extends ViewModel{

        private MutableLiveData<String> pText;
        public ProductsViewModel() {
            pText = new MutableLiveData<>();
            pText.setValue("This is products fragment");
        }

        public LiveData<String> getText() {
            return pText;
        }

}
