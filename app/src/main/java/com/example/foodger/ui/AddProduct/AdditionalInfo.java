package com.example.foodger.ui.AddProduct;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.foodger.R;

public class AdditionalInfo extends AppCompatDialogFragment {
    private TextView caloriesTextView;
    private TextView proteinTextView;
    private TextView fatnessTextView;
    private TextView carbohydratesTextView;

    private dialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstance){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());



        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout, null);

        caloriesTextView = view.findViewById(R.id.caloriesTextEdit);
        proteinTextView = view.findViewById(R.id.proteinTextEdit);
        fatnessTextView = view.findViewById(R.id.fatnessTextEdit);
        carbohydratesTextView = view.findViewById(R.id.carbohydratesTextEdit);

        builder.setView(view)
                    .setTitle("Введите данные")
                    .setNegativeButton("Отмена", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i){

                        }
                    })
                    .setPositiveButton("Ок", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i){

                            String calories = caloriesTextView.getText().toString();
                            String protein = proteinTextView.getText().toString();
                            String fatness = fatnessTextView.getText().toString();
                            String carbohydrates = carbohydratesTextView.getText().toString();

                            String buf[] = {calories, protein, fatness, carbohydrates};

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


    public interface dialogListener{
        void applyTexts(String calories, String protein, String fatness, String carbohydrates);

    }

    private String _calories;
    private String _protein;
    private String _carbohydrates;
    private String _fatness;
}
