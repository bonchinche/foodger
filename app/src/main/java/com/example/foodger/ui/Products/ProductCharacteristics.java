package com.example.foodger.ui.Products;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.foodger.R;

public class ProductCharacteristics extends Fragment {

        public ProductCharacteristics() {

    }


        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {

            View root = inflater.inflate(R.layout.fragment_product_characteristics, container, false);
            String arg1Value = getArguments().getString("arg1");
            Integer arg2Value=getArguments().getInt("arg2");

            TextView nametextview=(TextView)root.findViewById(R.id.nameproductview);
            nametextview.setText(arg1Value);
           // Button tButton=(Button)root.findViewById(R.id.button);
           // tButton.setText(arg1Value+",  ID: "+arg2Value.toString());
            return root;
        }
}
