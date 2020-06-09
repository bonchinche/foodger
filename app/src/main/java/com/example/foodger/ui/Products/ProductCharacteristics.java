package com.example.foodger.ui.Products;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
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
            int Protein = getArguments().getInt("Protein");
            int Carbo = getArguments().getInt("Carbohydrates");
            int Calories = getArguments().getInt("Calories");
            int Fatness = getArguments().getInt("Fatness");
           // float Rating=getArguments().getFloat("Rating");
            String Name=getArguments().getString("Name");

            TextView nametextview=(TextView)root.findViewById(R.id.nameproductview);
            nametextview.setText(Name);

            EditText carbohydrates=(EditText) root.findViewById(R.id.editcarbohydrates);
            carbohydrates.setText(Carbo);

            EditText calories=(EditText) root.findViewById(R.id.editcalories);
            calories.setText(Calories);

           // EditText dom=(EditText) root.findViewById(R.id.editDom);
           // carbohydrates.setText(dom);

            EditText fatness=(EditText) root.findViewById(R.id.editfatness);
            fatness.setText(Fatness);

            EditText protein=(EditText) root.findViewById(R.id.editprotein);
            protein.setText(Protein);

           // RatingBar rating=(RatingBar)root.findViewById(R.id.Rating);
           // rating.setRating(Rating);



            return root;
        }
}
