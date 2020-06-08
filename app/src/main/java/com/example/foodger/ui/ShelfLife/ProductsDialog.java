package com.example.foodger.ui.ShelfLife;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.foodger.R;

public class ProductsDialog extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_products, null);

        final ListView productsListView = (ListView)view.findViewById(R.id.productsListView);

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
