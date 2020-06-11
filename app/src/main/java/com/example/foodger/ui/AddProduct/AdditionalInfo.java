package com.example.foodger.ui.AddProduct;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.foodger.R;

public class AdditionalInfo extends AppCompatDialogFragment {
    private TextView caloriesTextView;
    private TextView proteinTextView;
    private TextView fatnessTextView;
    private TextView carbohydratesTextView;
    private TextView shelfLifeTextView;
    private TextView temperatureTextView;
    private RatingBar productRatingBar;

    public AdditionalInfo(String calories, String protein, String carbohydrates, String fatness, String shelfLife, String temp, String rating){
                _calories = calories;
                _protein = protein;
                _carbohydrates = carbohydrates;
                _fatness = fatness;
                _shelfLife = shelfLife;
                _temperature = temp;
                _rating = rating;
    }





    @Override
    public Dialog onCreateDialog(Bundle savedInstance){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout, null);

        caloriesTextView = view.findViewById(R.id.caloriesTextEdit);
        proteinTextView = view.findViewById(R.id.proteinTextEdit);
        fatnessTextView = view.findViewById(R.id.fatnessTextEdit);
        carbohydratesTextView = view.findViewById(R.id.carbohydratesTextEdit);
        shelfLifeTextView = view.findViewById(R.id.shelfLifeTextView);
        temperatureTextView = view.findViewById(R.id.temperatureEditText);
        productRatingBar = view.findViewById(R.id.productRatingBar);

        productRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                _rating = String.valueOf(rating);
                Toast.makeText(getContext(), "Ваш рейтинг: " + rating, Toast.LENGTH_SHORT).show();
            }
        });

        if(_calories != ""){
            caloriesTextView.setText(_calories);
        }
        if(_protein != ""){
            proteinTextView.setText(_protein);
        }
        if(_carbohydrates != "") {
            carbohydratesTextView.setText(_carbohydrates);
        }
        if(_fatness != ""){
            fatnessTextView.setText(_fatness);
        }
        if(_shelfLife != ""){
            shelfLifeTextView.setText(_shelfLife);
        }
        if(_rating != ""){
            productRatingBar.setRating(Float.parseFloat(_rating));
        }
        if(_temperature != ""){
            temperatureTextView.setText(_temperature);
        }
        //int i = Integer.parseInt (myString);

        temperatureTextView.addTextChangedListener(new TextWatcher(){

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(temperatureTextView.getText().toString().isEmpty() != true) {
                    long i = Long.parseLong(temperatureTextView.getText().toString());
                    if (i > 100) {
                        temperatureTextView.setBackgroundColor(getResources().getColor(R.color.Red));
                    } else {
                        temperatureTextView.setBackgroundColor(getResources().getColor(R.color.White));
                    }
                }
            }
        });

        shelfLifeTextView.addTextChangedListener(new TextWatcher(){
            @SuppressLint("ResourceAsColor")
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(shelfLifeTextView.getText().toString().isEmpty() != true) {
                    long i = Long.parseLong(shelfLifeTextView.getText().toString());
                        if (i > 10000) {
                            Log.d("YOUR_SHELF_LIFE = ", shelfLifeTextView.getText().toString());
                            //shelfLifeTextView.setTextColor(R.color.Red);
                            shelfLifeTextView.setBackgroundColor(getResources().getColor(R.color.Red));
                        } else {
                            shelfLifeTextView.setBackgroundColor(getResources().getColor(R.color.White));

                        }
                }
            }
        });

        fatnessTextView.addTextChangedListener(new TextWatcher(){

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(fatnessTextView.getText().toString().isEmpty() != true) {
                    long i = Long.parseLong(fatnessTextView.getText().toString());
                    if (i > 1000) {
                        fatnessTextView.setBackgroundColor(getResources().getColor(R.color.Red));
                    } else {
                        fatnessTextView.setBackgroundColor(getResources().getColor(R.color.White));
                    }
                }
            }
        });

        carbohydratesTextView.addTextChangedListener(new TextWatcher(){

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(carbohydratesTextView.getText().toString().isEmpty() != true) {
                    long i = Long.parseLong(carbohydratesTextView.getText().toString());
                    if (i > 1000) {
                        carbohydratesTextView.setBackgroundColor(getResources().getColor(R.color.Red));
                    } else {
                        carbohydratesTextView.setBackgroundColor(getResources().getColor(R.color.White));
                    }
                }
            }
        });

        proteinTextView.addTextChangedListener(new TextWatcher(){

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(proteinTextView.getText().toString().isEmpty() != true) {
                    long i = Long.parseLong(proteinTextView.getText().toString());
                    if (i > 1000) {
                        proteinTextView.setBackgroundColor(getResources().getColor(R.color.Red));
                    } else {
                        proteinTextView.setBackgroundColor(getResources().getColor(R.color.White));
                    }
                }
            }
        });

        caloriesTextView.addTextChangedListener(new TextWatcher(){

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(caloriesTextView.getText().toString().isEmpty() != true) {
                    long i = Long.parseLong(caloriesTextView.getText().toString());
                    if (i > 1000) {
                        caloriesTextView.setBackgroundColor(getResources().getColor(R.color.Red));
                    } else {
                        caloriesTextView.setBackgroundColor(getResources().getColor(R.color.White));
                    }
                }
            }
        });

        shelfLifeTextView.setOnKeyListener(
                new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        return false;
                    }
                });

        builder.setView(view)
                    .setTitle("Введите данные")
                    .setNegativeButton("Отмена", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i){
                            _calories = "";
                            _protein = "";
                            _carbohydrates = "";
                            _fatness = "";
                            _shelfLife = "";
                            _rating = "";
                            _temperature = "";

                            String buf[] = {_calories, _protein, _carbohydrates, _fatness, _shelfLife, _temperature, _rating};
                            sendResult(Activity.RESULT_OK, buf);
                        }
                    })
                    .setPositiveButton("Ок", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i){

                            String calories = caloriesTextView.getText().toString();
                            String protein = proteinTextView.getText().toString();
                            String fatness = fatnessTextView.getText().toString();
                            String carbohydrates = carbohydratesTextView.getText().toString();
                            String shelfLife = shelfLifeTextView.getText().toString();
                            String temperature = temperatureTextView.getText().toString();

                            String buf[] = {calories, protein, carbohydrates, fatness, shelfLife, temperature, _rating};

                            sendResult(Activity.RESULT_OK, buf);
                        }
                    });

        return builder.create();
    }

    private void sendResult( int resultCode, String[] result ) {

        if ( getTargetFragment() == null )
            return;

        Intent i = new Intent();
        i.putExtra( "DIALOG_RESULT", result );
        getTargetFragment().onActivityResult( getTargetRequestCode(), resultCode, i );
    }
    public void reset(){
        _calories = null;
        _protein = null;
        _carbohydrates = null;
        _fatness = null;
        _shelfLife = null;
        _rating = null;
        _temperature = null;
    }

    private String _calories = null;
    private String _protein = null;
    private String _carbohydrates = null;
    private String _fatness = null;
    private String _shelfLife = null;
    private String _rating = null;
    private String _temperature = null;
}
