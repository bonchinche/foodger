package com.example.foodger.ui.AddProduct;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.foodger.R;

public class AddProductFragment extends Fragment {

    private AddProductViewModel addProductViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addProductViewModel =
                ViewModelProviders.of(this).get(AddProductViewModel.class);
        View root = inflater.inflate(R.layout.fragment_addproduct, container, false);
        final TextView textView = root.findViewById(R.id.text_addproduct);
        addProductViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
