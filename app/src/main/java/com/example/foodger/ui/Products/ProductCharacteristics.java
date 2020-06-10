package com.example.foodger.ui.Products;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.ColorInt;
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

            final EditText carbohydrates=(EditText)root.findViewById(R.id.editcarbohydrates);
            if (Carbo!=0) {
                carbohydrates.setText(String.valueOf(Carbo));
            }else{
                carbohydrates.setText("");
                //int RGB = android.graphics.Color.rgb(215, 115, 25);
               // carbohydrates.setBackgroundColor(RGB);
            }

            EditText calories=(EditText) root.findViewById(R.id.editcalories);
            if (Calories!=0) {
                calories.setText(String.valueOf(Calories));
            }else{
                calories.setText("");
               // int RGB = android.graphics.Color.rgb(215, 115, 25);
                //calories.setBackgroundColor(RGB);
            }

            EditText dom=(EditText) root.findViewById(R.id.editDom);
            if (Dom!=null) {
                dom.setText(String.valueOf(Dom));
            }else{
                dom.setText("");
               // int RGB = android.graphics.Color.rgb(215, 115, 25);
               // dom.setBackgroundColor(RGB);
            }

            EditText shelf=(EditText) root.findViewById(R.id.editShelf);
            if (Shelf!=null) {
                shelf.setText(String.valueOf(Shelf));
            }else{
                shelf.setText("");
                //int RGB = android.graphics.Color.rgb(215, 115, 25);
               // shelf.setBackgroundColor(RGB);
            }

            EditText temp=(EditText) root.findViewById(R.id.editTemperature);
            if (Temperature!=null) {
                temp.setText(String.valueOf(Temperature));
            }else{
                temp.setText("");
                //int RGB = android.graphics.Color.rgb(215, 115, 25);
                //temp.setBackgroundColor(RGB);
            }

            EditText fatness=(EditText) root.findViewById(R.id.editfatness);
            if (Fatness!=0) {
                fatness.setText(String.valueOf(Fatness));
            }else{
                fatness.setText("");
                //int RGB = android.graphics.Color.rgb(215, 115, 25);
                //fatness.setBackgroundColor(RGB);
            }

           EditText protein=(EditText) root.findViewById(R.id.editprotein);
            if (Protein!=0) {
                protein.setText(String.valueOf(Protein));
            }else{
                protein.setText("");
               // int RGB = android.graphics.Color.rgb(215, 115, 25);
               // protein.setBackgroundColor(RGB);
            }

            RatingBar rating=(RatingBar)root.findViewById(R.id.Rating);
            rating.setRating(Rating);


            final String TextBefore=carbohydrates.getText().toString();

            carbohydrates.addTextChangedListener(new TextWatcher(){

                @Override
                public void afterTextChanged(Editable s) {
                    //срабатывает сразу после изменения текста
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    //срабатывает сразу перед изменением текста
                }


                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //срабатывает во время изменения текста
                    if (TextBefore.equalsIgnoreCase(s.toString())) {
                         carbohydrates.setBackgroundResource(R.drawable.edit_text_false);
                    }
                    else {
                        carbohydrates.setBackgroundResource(R.drawable.edit_text_changed);
                    }
                    }
            });

            carbohydrates.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                Drawable background=carbohydrates.getBackground();
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        if (TextBefore.equalsIgnoreCase(carbohydrates.getText().toString())) {
                            carbohydrates.setBackground(background);
                        }
                    }
                }
            });


            return root;
        }
}
