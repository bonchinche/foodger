package com.example.foodger.ui.ShelfLife;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.foodger.MainActivity;
import com.example.foodger.R;

import java.util.List;

public class ProductsDialog extends AppCompatDialogFragment {

    private ListView productsListView;
    private List<String> productsList;




    ProductsDialog(List<String> list){
        productsList = list;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_products, null);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, productsList);

        productsListView = (ListView)view.findViewById(R.id.productsListView);
        productsListView.setAdapter(adapter);

        builder.setView(view)
                .setTitle("Продукты с истекающим сроком хранения")
                .setPositiveButton("Ок", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){


                    }
                });



        return builder.create();
    }
}
