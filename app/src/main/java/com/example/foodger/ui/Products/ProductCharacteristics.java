package com.example.foodger.ui.Products;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


import com.example.foodger.DataBaseHelper;
import com.example.foodger.ProductsTablesContracts;
import com.example.foodger.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ProductCharacteristics extends Fragment {


    EditText text_searcher;
    TextView text_searcher_textview;
    boolean updateCalories;
    boolean updateCarbohydrates;
    boolean updateRating;
    boolean updateFatness;
    boolean updateProtein;
    boolean updateTemperature;
    boolean updateShelf;
    private DataBaseHelper MyDB;


        public ProductCharacteristics() {

    }


        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {

            text_searcher=(EditText)getActivity().findViewById(R.id.SearchEdit);
            text_searcher.setVisibility(View.INVISIBLE);

            text_searcher_textview=(TextView)getActivity().findViewById(R.id.SearchText);
            text_searcher_textview.setVisibility(View.INVISIBLE);

            MyDB=new DataBaseHelper(getActivity());
            updateCalories=false;
            updateCarbohydrates=false;
            updateRating=false;
            updateFatness=false;
            updateProtein=false;
            updateTemperature=false;
            updateShelf=false;

            final View root = inflater.inflate(R.layout.fragment_product_characteristics, container, false);
            int Protein = getArguments().getInt("Protein");
            int Carbo = getArguments().getInt("Carbohydrates");

            Log.d("CARBGO: ","CARBO= "+Carbo);

            int Calories = getArguments().getInt("Calories");
            final String Type_Name=getArguments().getString("TYPE_NAME");
            final String ID=getArguments().getString("ID");
            int Fatness = getArguments().getInt("Fatness");
            float Rating=getArguments().getFloat("Rating");
            String Name=getArguments().getString("Name");
            String Dom=getArguments().getString("DOM");
            String Temperature=getArguments().getString("TEMPERATURE");
            final String Shelf=getArguments().getString("SHELF");

            TextView nametextview=(TextView)root.findViewById(R.id.nameproductview);
            nametextview.setText(Name);
            nametextview.setTextColor(Color.rgb(0,22,186));

            TextView TypeProduct=(TextView)root.findViewById(R.id.typeproductView);
            TypeProduct.setTextColor(Color.rgb(0,0,0));

            TextView RatingView=(TextView)root.findViewById(R.id.textView28);
            RatingView.setTextColor(Color.rgb(247,113,2));

            final Button updateButton=(Button)root.findViewById(R.id.updatebutton);
            updateButton.setVisibility(View.INVISIBLE);

            final EditText carbohydrates=(EditText)root.findViewById(R.id.editcarbohydrates);
            if (Carbo>0) {
                carbohydrates.setText(String.valueOf(Carbo));
            }else{
                carbohydrates.setText("");
            }

            final EditText calories=(EditText) root.findViewById(R.id.editcalories);
            if (Calories>0) {
                calories.setText(String.valueOf(Calories));
            }else{
                calories.setText("");
            }

            final EditText dom=(EditText) root.findViewById(R.id.editDom);
            if (Dom!=null) {
                dom.setText(String.valueOf(Dom));
            }else{
                dom.setText("");
            }
            dom.setEnabled(false);
            dom.setHintTextColor(Color.rgb(0,0,0));
            dom.setTextColor(Color.rgb(0,0,0));

            final EditText editType=(EditText)root.findViewById(R.id.editType);
            editType.setText(Type_Name);
            editType.setEnabled(false);
            editType.setHintTextColor(Color.rgb(0,0,0));
            editType.setTextColor(Color.rgb(0,0,0));

            final EditText shelf=(EditText) root.findViewById(R.id.editShelf);
            if (Shelf!=null) {
                shelf.setText(String.valueOf(Shelf));
            }else{
                shelf.setText("");
            }

            final EditText temp=(EditText) root.findViewById(R.id.editTemperature);
            if (Temperature!=null) {
                temp.setText(String.valueOf(Temperature));
            }else{
                temp.setText("");
            }

            final EditText fatness=(EditText) root.findViewById(R.id.editfatness);
            if (Fatness>0) {
                fatness.setText(String.valueOf(Fatness));
            }else{
                fatness.setText("");
            }

           final EditText protein=(EditText) root.findViewById(R.id.editprotein);
            if (Protein>0) {
                protein.setText(String.valueOf(Protein));
            }else{
                protein.setText("");
            }

            final RatingBar rating=(RatingBar)root.findViewById(R.id.Rating);
            rating.setRating(Rating);


            final String TextBeforeCarbo=carbohydrates.getText().toString();

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
                    if (TextBeforeCarbo.equalsIgnoreCase(s.toString())) {
                         carbohydrates.setBackgroundResource(R.drawable.edit_text_style);
                         updateCarbohydrates=false;
                         if(((updateCalories==false)&&(updateFatness==false)&&(updateProtein==false)&&(updateShelf==false)&&(updateTemperature==false)&&(updateRating==false))){
                             updateButton.setVisibility(View.INVISIBLE);
                         }else{
                             updateButton.setVisibility(View.VISIBLE);
                         }
                    }
                    else {
                        if(!(carbohydrates.getText().toString().isEmpty())) {
                            int CarboInt = Integer.parseInt(carbohydrates.getText().toString());
                            if (((CarboInt < 10000) && (CarboInt > 0))) {
                                carbohydrates.setBackgroundResource(R.drawable.edit_text_changed);
                                updateCarbohydrates = true;
                                updateButton.setVisibility(View.VISIBLE);
                            } else {
                                carbohydrates.setBackgroundResource(R.drawable.edit_text_false);
                                updateCarbohydrates = false;
                                if (((updateCalories == false) && (updateFatness == false) && (updateProtein == false) && (updateShelf == false) && (updateTemperature == false) && (updateRating == false))) {
                                    updateButton.setVisibility(View.INVISIBLE);
                                } else {
                                    updateButton.setVisibility(View.VISIBLE);
                                }
                            }
                        }else {
                            carbohydrates.setBackgroundResource(R.drawable.edit_text_false);
                            updateCarbohydrates = false;
                            if (((updateCalories == false) && (updateFatness == false) && (updateProtein == false) && (updateShelf == false) && (updateTemperature == false) && (updateRating == false))) {
                                updateButton.setVisibility(View.INVISIBLE);
                            } else {
                                updateButton.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    }
            });

            carbohydrates.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                Drawable background=carbohydrates.getBackground();
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        if (TextBeforeCarbo.equalsIgnoreCase(carbohydrates.getText().toString())) {
                            carbohydrates.setBackground(background);
                        } else {
                            if (updateCarbohydrates==false){
                                carbohydrates.setText(TextBeforeCarbo);
                                carbohydrates.setBackground(background);
                            }
                        }
                    }
                }
            });

            final String TextBeforeCalories=calories.getText().toString();

            calories.addTextChangedListener(new TextWatcher(){

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
                    if (TextBeforeCalories.equalsIgnoreCase(s.toString())) {
                        calories.setBackgroundResource(R.drawable.edit_text_style);
                        updateCalories=false;
                        if(((updateCarbohydrates==false)&&(updateFatness==false)&&(updateProtein==false)&&(updateShelf==false)&&(updateTemperature==false)&&(updateRating==false))){
                            updateButton.setVisibility(View.INVISIBLE);
                        }else{
                            updateButton.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        if(!(calories.getText().toString().isEmpty())) {
                            int CaloriesInt = Integer.parseInt(calories.getText().toString());
                            if (((CaloriesInt < 10000) && (CaloriesInt > 0))) {
                                calories.setBackgroundResource(R.drawable.edit_text_changed);
                                updateCalories = true;
                                updateButton.setVisibility(View.VISIBLE);
                            } else {
                                calories.setBackgroundResource(R.drawable.edit_text_false);
                                updateCalories = false;
                                if (((updateCarbohydrates == false) && (updateFatness == false) && (updateProtein == false) && (updateShelf == false) && (updateTemperature == false) && (updateRating == false))) {
                                    updateButton.setVisibility(View.INVISIBLE);
                                } else {
                                    updateButton.setVisibility(View.VISIBLE);
                                }
                            }
                        }else {
                            calories.setBackgroundResource(R.drawable.edit_text_false);
                            updateCalories = false;
                            if (((updateCarbohydrates == false) && (updateFatness == false) && (updateProtein == false) && (updateShelf == false) && (updateTemperature == false) && (updateRating == false))) {
                                updateButton.setVisibility(View.INVISIBLE);
                            } else {
                                updateButton.setVisibility(View.VISIBLE);
                            }

                        }
                    }
                }
            });

            calories.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                Drawable background=calories.getBackground();
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        if (TextBeforeCalories.equalsIgnoreCase(calories.getText().toString())) {
                            calories.setBackground(background);
                        } else {
                            if (updateCalories==false){
                                calories.setText(TextBeforeCalories);
                                calories.setBackground(background);
                            }
                        }
                    }
                }
            });


            final String TextBeforeDom=dom.getText().toString();

           dom.addTextChangedListener(new TextWatcher(){

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
                    if (TextBeforeDom.equalsIgnoreCase(s.toString())) {
                        dom.setBackgroundResource(R.drawable.edit_text_false);
                    }
                    else {
                        dom.setBackgroundResource(R.drawable.edit_text_changed);
                    }
                }
            });

            dom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                Drawable background=dom.getBackground();
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        if (TextBeforeDom.equalsIgnoreCase(dom.getText().toString())) {
                            dom.setBackground(background);
                        }
                    }
                }
            });


            final String TextBeforeShelf=shelf.getText().toString();

            shelf.addTextChangedListener(new TextWatcher(){

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
                    if (TextBeforeShelf.equalsIgnoreCase(s.toString())) {
                        shelf.setBackgroundResource(R.drawable.edit_text_style);
                        updateShelf=false;
                        if(((updateCarbohydrates==false)&&(updateFatness==false)&&(updateProtein==false)&&(updateCalories==false)&&(updateTemperature==false)&&(updateRating==false))){
                            updateButton.setVisibility(View.INVISIBLE);
                        }else{
                            updateButton.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        if(!(shelf.getText().toString().isEmpty())) {
                            int ShelfInt = Integer.parseInt(shelf.getText().toString());
                            if (((ShelfInt < 999999) && (ShelfInt > 0))) {
                                shelf.setBackgroundResource(R.drawable.edit_text_changed);
                                updateShelf = true;
                                updateButton.setVisibility(View.VISIBLE);
                            } else {
                                shelf.setBackgroundResource(R.drawable.edit_text_false);
                                updateShelf = false;
                                if (((updateCalories == false) && (updateFatness == false) && (updateProtein == false) && (updateCarbohydrates == false) && (updateTemperature == false) && (updateRating == false))) {
                                    updateButton.setVisibility(View.INVISIBLE);
                                } else {
                                    updateButton.setVisibility(View.VISIBLE);
                                }
                            }
                        }else {
                            shelf.setBackgroundResource(R.drawable.edit_text_false);
                            updateShelf = false;
                            if (((updateCalories == false) && (updateFatness == false) && (updateProtein == false) && (updateCarbohydrates == false) && (updateTemperature == false) && (updateRating == false))) {
                                updateButton.setVisibility(View.INVISIBLE);
                            } else {
                                updateButton.setVisibility(View.VISIBLE);
                            }

                        }
                    }
                }
            });

            shelf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                Drawable background=shelf.getBackground();
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        if (TextBeforeShelf.equalsIgnoreCase(shelf.getText().toString())) {
                            shelf.setBackground(background);
                        } else {
                            if (updateShelf==false){
                                shelf.setText(TextBeforeShelf);
                                shelf.setBackground(background);
                            }
                        }
                    }
                }
            });


            final String TextBeforeTemp=temp.getText().toString();

            temp.addTextChangedListener(new TextWatcher(){

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
                    if (TextBeforeTemp.equalsIgnoreCase(s.toString())) {
                        temp.setBackgroundResource(R.drawable.edit_text_style);
                        updateTemperature=false;
                        if(((updateCarbohydrates==false)&&(updateFatness==false)&&(updateProtein==false)&&(updateShelf==false)&&(updateCalories==false)&&(updateRating==false))){
                            updateButton.setVisibility(View.INVISIBLE);
                        }else{
                            updateButton.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        if(!(temp.getText().toString().isEmpty())) {
                            Integer TempInt = Integer.parseInt(temp.getText().toString());
                            if (((TempInt < 1000) && (TempInt > -1000))) {
                                temp.setBackgroundResource(R.drawable.edit_text_changed);
                                updateTemperature = true;
                                updateButton.setVisibility(View.VISIBLE);
                            } else {
                                temp.setBackgroundResource(R.drawable.edit_text_false);
                                updateTemperature = false;
                                if (((updateCalories == false) && (updateFatness == false) && (updateProtein == false) && (updateShelf == false) && (updateCarbohydrates == false) && (updateRating == false))) {
                                    updateButton.setVisibility(View.INVISIBLE);
                                } else {
                                    updateButton.setVisibility(View.VISIBLE);
                                }
                            }
                        }else {
                            temp.setBackgroundResource(R.drawable.edit_text_false);
                            updateTemperature = false;
                            if (((updateCalories == false) && (updateFatness == false) && (updateProtein == false) && (updateShelf == false) && (updateCarbohydrates == false) && (updateRating == false))) {
                                updateButton.setVisibility(View.INVISIBLE);
                            } else {
                                updateButton.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
            });

            temp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                Drawable background=temp.getBackground();
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        if (TextBeforeTemp.equalsIgnoreCase(temp.getText().toString())) {
                            temp.setBackground(background);
                        } else {
                            if (updateTemperature==false){
                                temp.setText(TextBeforeTemp);
                                temp.setBackground(background);
                            }
                        }
                    }
                }
            });

            final String TextBeforeFatness=fatness.getText().toString();

            fatness.addTextChangedListener(new TextWatcher(){

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
                    if (TextBeforeFatness.equalsIgnoreCase(s.toString())) {
                        fatness.setBackgroundResource(R.drawable.edit_text_style);
                        updateFatness=false;
                        if(((updateCarbohydrates==false)&&(updateCalories==false)&&(updateProtein==false)&&(updateShelf==false)&&(updateTemperature==false)&&(updateRating==false))){
                            updateButton.setVisibility(View.INVISIBLE);
                        }else{
                            updateButton.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        if(!(fatness.getText().toString().isEmpty())) {
                            int FatInt = Integer.parseInt(fatness.getText().toString());
                            if (((FatInt < 10000) && (FatInt > 0))) {
                                fatness.setBackgroundResource(R.drawable.edit_text_changed);
                                updateFatness = true;
                                updateButton.setVisibility(View.VISIBLE);
                            } else {
                                fatness.setBackgroundResource(R.drawable.edit_text_false);
                                updateFatness = false;
                                if (((updateCalories == false) && (updateCarbohydrates == false) && (updateProtein == false) && (updateShelf == false) && (updateTemperature == false) && (updateRating == false))) {
                                    updateButton.setVisibility(View.INVISIBLE);
                                } else {
                                    updateButton.setVisibility(View.VISIBLE);
                                }
                            }
                        }else {
                            fatness.setBackgroundResource(R.drawable.edit_text_false);
                            updateFatness = false;
                            if (((updateCalories == false) && (updateCarbohydrates == false) && (updateProtein == false) && (updateShelf == false) && (updateTemperature == false) && (updateRating == false))) {
                                updateButton.setVisibility(View.INVISIBLE);
                            } else {
                                updateButton.setVisibility(View.VISIBLE);
                            }

                        }
                    }
                }
            });

            fatness.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                Drawable background=fatness.getBackground();
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        if (TextBeforeFatness.equalsIgnoreCase(fatness.getText().toString())) {
                            fatness.setBackground(background);
                        } else {
                            if (updateFatness==false){
                                fatness.setText(TextBeforeFatness);
                                fatness.setBackground(background);
                            }
                        }
                    }
                }
            });

            final String TextBeforeProtein=protein.getText().toString();

            protein.addTextChangedListener(new TextWatcher(){

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
                    if (TextBeforeProtein.equalsIgnoreCase(s.toString())) {
                        protein.setBackgroundResource(R.drawable.edit_text_style);
                        updateProtein=false;
                        if(((updateCarbohydrates==false)&&(updateFatness==false)&&(updateCalories==false)&&(updateShelf==false)&&(updateTemperature==false)&&(updateRating==false))){
                            updateButton.setVisibility(View.INVISIBLE);
                        }else{
                            updateButton.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        if(!(protein.getText().toString().isEmpty())) {
                            int ProteinInt = Integer.parseInt(protein.getText().toString());
                            if (((ProteinInt < 10000) && (ProteinInt > 0))) {
                                protein.setBackgroundResource(R.drawable.edit_text_changed);
                                updateProtein = true;
                                updateButton.setVisibility(View.VISIBLE);
                            } else {
                                protein.setBackgroundResource(R.drawable.edit_text_false);
                                updateProtein = false;
                                if (((updateCalories == false) && (updateFatness == false) && (updateCarbohydrates == false) && (updateShelf == false) && (updateTemperature == false) && (updateRating == false))) {
                                    updateButton.setVisibility(View.INVISIBLE);
                                } else {
                                    updateButton.setVisibility(View.VISIBLE);
                                }
                            }
                        }else {
                            protein.setBackgroundResource(R.drawable.edit_text_false);
                            updateProtein = false;
                            if (((updateCalories == false) && (updateFatness == false) && (updateCarbohydrates == false) && (updateShelf == false) && (updateTemperature == false) && (updateRating == false))) {
                                updateButton.setVisibility(View.INVISIBLE);
                            } else {
                                updateButton.setVisibility(View.VISIBLE);
                            }

                        }
                    }
                }
            });

            protein.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                Drawable background=protein.getBackground();
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        if (TextBeforeProtein.equalsIgnoreCase(protein.getText().toString())) {
                            protein.setBackground(background);
                        } else {
                            if (updateProtein==false){
                                protein.setText(TextBeforeProtein);
                                protein.setBackground(background);
                            }
                        }
                    }
                }
            });


            final Float RatingBefore=rating.getRating();

            rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                Drawable background=rating.getBackground();
                @Override
                public void onRatingChanged(RatingBar ratingBar, float ratingg,
                                            boolean fromUser) {
                    if (RatingBefore==ratingg){
                        rating.setBackground(background);
                        updateRating=false;
                        if(((updateCarbohydrates==false)&&(updateFatness==false)&&(updateProtein==false)&&(updateShelf==false)&&(updateTemperature==false)&&(updateCalories==false))){
                            updateButton.setVisibility(View.INVISIBLE);
                        }else{
                            updateButton.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        rating.setBackgroundResource(R.drawable.edit_text_changed);
                        updateRating=true;
                        updateButton.setVisibility(View.VISIBLE);
                    }
                }
            });

            rating.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                Drawable background=rating.getBackground();
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        if (RatingBefore==rating.getRating()) {
                            rating.setBackground(background);
                        }
                    }
                }
            });

            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setTitle("Вы уверены, что хотите обновить данные о продукте?");

                    builder.setMessage("Информация о продукте будет обновлена на вашем устройстве.")
                            .setCancelable(false)
                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                    SQLiteDatabase db=MyDB.getWritableDatabase();

                    if(updateCalories==true){
                        ContentValues cv=new ContentValues();
                        cv.put("CALORIES",calories.getText().toString());
                        db.update(ProductsTablesContracts.Product_Characteristic.TABLE_NAME,cv,"_ID=?", new String[]{ID});
                    }

                    if(updateCarbohydrates==true){
                        ContentValues cv=new ContentValues();
                        cv.put("CARBOHYDRATES",carbohydrates.getText().toString());
                        db.update(ProductsTablesContracts.Product_Characteristic.TABLE_NAME,cv,"_ID=?", new String[]{ID});
                    }

                    if(updateProtein==true){
                        ContentValues cv=new ContentValues();
                        cv.put("PROTEIN",protein.getText().toString());
                        db.update(ProductsTablesContracts.Product_Characteristic.TABLE_NAME,cv,"_ID=?", new String[]{ID});
                    }

                    if(updateFatness==true){
                        ContentValues cv=new ContentValues();
                        cv.put("FATNESS",fatness.getText().toString());
                        db.update(ProductsTablesContracts.Product_Characteristic.TABLE_NAME,cv,"_ID=?", new String[]{ID});
                    }

                    if(updateShelf==true){

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd 00:00:00");

                        SQLiteDatabase sel_dom= MyDB.getReadableDatabase();

                        Cursor select_dom=sel_dom.rawQuery("Select DOM FROM "+ ProductsTablesContracts.Products.TABLE_NAME+ " WHERE _ID="+ID,null);
                        select_dom.moveToFirst();

                        String dom=select_dom.getString(select_dom.getColumnIndex(ProductsTablesContracts.Products.DOM));

                        String dos=findDayOfSpoilage(dom, shelf.getText().toString(), sdf);

                        ContentValues cv=new ContentValues();
                        cv.put("SHELF_LIFE",shelf.getText().toString());
                        cv.put("DOS",dos);
                        db.update(ProductsTablesContracts.Products.TABLE_NAME,cv,"_ID=?", new String[]{ID});
                    }

                    if(updateTemperature==true){
                        ContentValues cv=new ContentValues();
                        cv.put("TEMPERATURE",temp.getText().toString());
                        db.update(ProductsTablesContracts.Products.TABLE_NAME,cv,"_ID=?", new String[]{ID});
                    }

                    if(updateRating==true){
                        ContentValues cv=new ContentValues();
                        cv.put("RATING",rating.getRating());
                        db.update(ProductsTablesContracts.Product_Characteristic.TABLE_NAME,cv,"_ID=?", new String[]{ID});
                    }

                                    NavController navController= Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
                                    navController.navigate(R.id.nav_products);

                                }
                            })
                            .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });


            return root;
        }


    public String findDayOfSpoilage(String dateOfManufacture, String shelfLife, SimpleDateFormat sdf){
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd 00:00:00"); // Задаем формат даты
        Date dt = new Date();
        try {
            dt = sdf.parse(dateOfManufacture); // присваиваем dt значение даты изготовления продукта
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, Integer.parseInt(shelfLife)); //
        dt = c.getTime();
        String dateOfSpoilage = sdf.format(dt); // получаем обычный формат даты
        return dateOfSpoilage;
    }

}
