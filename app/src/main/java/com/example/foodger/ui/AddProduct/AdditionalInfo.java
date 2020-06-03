package com.example.foodger.ui.AddProduct;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.foodger.R;

public class AdditionalInfo extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_addproduct, container, false);

        Button cancellButton = (Button)root.findViewById(R.id.cancellButton);
        Button applyButton = (Button)root.findViewById(R.id.applyButton);

        final TextView caloriesTextView = (TextView)root.findViewById(R.id.caloriesTextEdit);
        final TextView proteinTextView = (TextView)root.findViewById(R.id.proteinTextEdit);
        final TextView carbohydratesTextView = (TextView)root.findViewById(R.id.carbohudratesTextEdit);
        final TextView fatnessTextView = (TextView)root.findViewById(R.id.fatnessTextEdit);

        applyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                _calories = caloriesTextView.getText().toString();
                _protein = proteinTextView.getText().toString();
                _carbohydrates = carbohydratesTextView.getText().toString();
                _fatness = fatnessTextView.getText().toString();



            }
        });


        return root;
    }




    private String _calories;
    private String _protein;
    private String _carbohydrates;
    private String _fatness;
}
