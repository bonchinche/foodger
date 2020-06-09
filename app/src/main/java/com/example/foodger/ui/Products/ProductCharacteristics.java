package com.example.foodger.ui.Products;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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

            Log.d("CARBGO: ","CARBO= "+Carbo);

            int Calories = getArguments().getInt("Calories");
            int Fatness = getArguments().getInt("Fatness");
            float Rating=getArguments().getFloat("Rating");
            String Name=getArguments().getString("Name");
            String Dom=getArguments().getString("DOM");
            String Temperature=getArguments().getString("TEMPERATURE");
            String Shelf=getArguments().getString("SHELF");

            TextView nametextview=(TextView)root.findViewById(R.id.nameproductview);
            nametextview.setText(Name);

            EditText carbohydrates=(EditText)root.findViewById(R.id.editcarbohydrates);
            carbohydrates.setText(String.valueOf(Carbo));

            EditText calories=(EditText) root.findViewById(R.id.editcalories);
            calories.setText(String.valueOf(Calories));

            EditText dom=(EditText) root.findViewById(R.id.editDom);
            dom.setText(String.valueOf(Dom));

            EditText shelf=(EditText) root.findViewById(R.id.editShelf);
            shelf.setText(String.valueOf(Shelf));

            EditText temp=(EditText) root.findViewById(R.id.editTemperature);
            temp.setText(String.valueOf(Temperature));

            EditText fatness=(EditText) root.findViewById(R.id.editfatness);
            fatness.setText(String.valueOf(Fatness));

           EditText protein=(EditText) root.findViewById(R.id.editprotein);
            protein.setText(String.valueOf(Protein));

            RatingBar rating=(RatingBar)root.findViewById(R.id.Rating);
            rating.setRating(Rating);

            return root;
        }
}
