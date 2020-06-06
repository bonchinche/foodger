package com.example.foodger.ui.ShelfLife;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;



import com.example.foodger.R;

public class ShelfLifeFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_shelflife, container, false);
        final TextView textView = root.findViewById(R.id.text_shelflife);

        return root;
    }
}
