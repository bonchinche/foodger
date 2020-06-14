package com.example.foodger.ui.Products;

import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.foodger.ProductsTablesContracts;
import com.example.foodger.R;
import com.example.foodger.ProductsTablesContracts.Product_Characteristic;
import com.example.foodger.ProductsTablesContracts.Products;
import com.example.foodger.DataBaseHelper;

import java.util.ArrayList;

public class ProductsFragment extends Fragment {

    EditText text_searcher;
    TextView text_searcher_textview;

    public ListView listView;
    private DataBaseHelper dbHelper;
    ArrayList<String> ProductsList = new ArrayList<>();
    ArrayList<Integer> ProductsID=new ArrayList<>();
    ArrayList<String> TypeProductsList = new ArrayList<>();
    ArrayList<String> ProductsListMain = new ArrayList<>();
    ArrayList<Integer> ProductsIDMain = new ArrayList<>();
    ArrayList <Integer> CheckLikeText=new ArrayList<>();
    MyAdapter myAdapter;
    int current_type;

    private ReplaceFragment replaceFragment;


    public ProductsFragment() {

    }

    public interface ReplaceFragment {
        void onFragmentReplace(Bundle bundle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ReplaceFragment) {
            replaceFragment = (ReplaceFragment) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragment1DataListener");
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        current_type=0;


        final View root = inflater.inflate(R.layout.fragment_products, container, false);

        listView = root.findViewById(R.id.products_list);

        dbHelper = new DataBaseHelper(getContext());

        if (!(ProductsList.size() > 0)) {
            SelectFromProducts();
        }

       if (!(TypeProductsList.size()>0)) {

           TypeProductsList.add("All Products");

            SQLiteDatabase select_types = dbHelper.getReadableDatabase();
            Cursor cursor = select_types.rawQuery("Select TYPE_NAME from " + ProductsTablesContracts.Product_Type.TABLE_NAME.toString(), null);

            cursor.moveToFirst();
            String TypeName = cursor.getString(cursor.getColumnIndex(ProductsTablesContracts.Product_Type.TYPE_NAME));
            TypeProductsList.add(TypeName);

            while (cursor.moveToNext()) {
                TypeName = cursor.getString(cursor.getColumnIndex(ProductsTablesContracts.Product_Type.TYPE_NAME));
                TypeProductsList.add(TypeName);
            }

        }

        Spinner spinner = (Spinner) root.findViewById(R.id.type_products);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item ,TypeProductsList);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                       long id) {
              current_type=pos;
              boolean flag;
              text_searcher.clearFocus();
                ProductsID.clear();
                ProductsList.clear();

                CheckLikeText.clear();

                SQLiteDatabase check_like_type = dbHelper.getReadableDatabase();

                Cursor like_type = check_like_type.rawQuery("Select _ID from " + ProductsTablesContracts.Products.TABLE_NAME.toString() + " where NAME LIKE '%" + text_searcher.getText().toString() + "%'", null);

                while (like_type.moveToNext()) {
                    CheckLikeText.add(like_type.getInt(like_type.getColumnIndex(Products._ID)));
                }

                for (int i=0;i<ProductsIDMain.size();i++) {
                        flag=false;
                    if (CheckLikeText.size() > 0) {

                        for (int j = 0; j < CheckLikeText.size(); j++) {
                            if (ProductsIDMain.get(i) == CheckLikeText.get(j)) {
                            flag = true;
                            }
                        }
                    } else if (text_searcher.getText().toString().length()==0) {
                        flag = true;
                    }
                    if (flag) {
                        ProductsList.add(ProductsListMain.get(i));
                        ProductsID.add(ProductsIDMain.get(i));
                    }
                }

                  if (current_type != 0) {

                      SQLiteDatabase check_type = dbHelper.getReadableDatabase();

                      Cursor type = check_type.rawQuery("Select _ID,PRODUCT_TYPE_ID from " + ProductsTablesContracts.Products.TABLE_NAME.toString(), null);

                      while (type.moveToNext()) {

                          int type_from_select = type.getInt(type.getColumnIndex(Products.PRODUCT_TYPE_ID));
                          int id_from_select = type.getInt(type.getColumnIndex(Products._ID));

                          for (int i = 0; i < ProductsID.size(); i++) {
                              if (ProductsID.get(i) == id_from_select) {
                                  if (type_from_select != current_type - 1) {
                                      ProductsID.remove(i);
                                      ProductsList.remove(i);
                                  }
                              }
                          }
                      }
                  }

              myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                current_type=0;
            }
        });

        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);

        myAdapter=new MyAdapter(getActivity());

        listView.setAdapter(myAdapter);


        text_searcher=(EditText)getActivity().findViewById(R.id.SearchEdit);
        text_searcher_textview=(TextView)getActivity().findViewById(R.id.SearchText);
        text_searcher_textview.setVisibility(View.VISIBLE);
        text_searcher.setVisibility(View.VISIBLE);

        text_searcher.setText("");

        text_searcher.clearFocus();

        text_searcher.addTextChangedListener(new TextWatcher() {

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

                CheckLikeText.clear();

                ProductsList.clear();
                ProductsID.clear();

                if (text_searcher.getText().toString().length()>0) {

                    SQLiteDatabase check_type = dbHelper.getReadableDatabase();

                    Cursor type = check_type.rawQuery("Select _ID,NAME,PRODUCT_TYPE_ID from " + ProductsTablesContracts.Products.TABLE_NAME.toString() + " where NAME LIKE '%" + text_searcher.getText().toString() + "%'", null);

                    while (type.moveToNext()) {

                        if (current_type!=0) {

                            int type_from_select = type.getInt(type.getColumnIndex(Products.PRODUCT_TYPE_ID));

                            if (type_from_select == current_type - 1) {
                                CheckLikeText.add(type.getInt(type.getColumnIndex(Products._ID)));
                                ProductsList.add(type.getString(type.getColumnIndex(Products.NAME)));
                                ProductsID.add(type.getInt(type.getColumnIndex(Products._ID)));
                            }

                        } else {
                            CheckLikeText.add(type.getInt(type.getColumnIndex(Products._ID)));
                            ProductsList.add(type.getString(type.getColumnIndex(Products.NAME)));
                            ProductsID.add(type.getInt(type.getColumnIndex(Products._ID)));
                        }
                    }
                } else {

                    if (current_type==0){

                for (int i=0;i<ProductsIDMain.size();i++){
                    ProductsID.add(ProductsIDMain.get(i));
                    ProductsList.add(ProductsListMain.get(i));
                    }

                    } else {
                        SQLiteDatabase check_type2 = dbHelper.getReadableDatabase();

                        Cursor type2 = check_type2.rawQuery("Select _ID,PRODUCT_TYPE_ID from " + ProductsTablesContracts.Products.TABLE_NAME, null);

                        while (type2.moveToNext()) {
                            int type2_from_select = type2.getInt(type2.getColumnIndex(Products.PRODUCT_TYPE_ID));
                            int id2_from_select=type2.getInt(type2.getColumnIndex(Products._ID));

                            for (int i=0;i<ProductsIDMain.size();i++){
                                if (ProductsIDMain.get(i)==id2_from_select){
                                    if (type2_from_select==current_type-1){
                                        ProductsID.add(ProductsIDMain.get(i));
                                        ProductsList.add(ProductsListMain.get(i));
                                    }
                                }
                            }
                        }
                    }
                }
                myAdapter.notifyDataSetChanged();
            }
        });

        return root;
    }

    private void SelectFromProducts() {
        // Создадим и откроем для чтения базу данных
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Зададим условие для выборки - список столбцов
        String[] projection = {
                Products._ID,
                Products.PRODUCT_TYPE_ID,
                Products.NAME,
                Products.SHELF_LIFE,
                Products.DOM};

        // Делаем запрос
        Cursor cursor = db.query(
                Products.TABLE_NAME,   // таблица
                projection,            // столбцы
                null,                  // столбцы для условия WHERE
                null,                  // значения для условия WHERE
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // порядок сортировки


        try {

            // Узнаем индекс каждого столбца
            int idColumnIndex = cursor.getColumnIndex(Products._ID);
            int ProductNameIndex = cursor.getColumnIndex(Products.NAME);
            int i=-1;
            // Проходим через все ряды
            while (cursor.moveToNext()) {
                // Используем индекс для получения строки или числа
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(ProductNameIndex);
                i++;
                // Выводим значения каждого столбца
                ProductsList.add(currentName);
                ProductsID.add(i);
                ProductsListMain.add(currentName);
                ProductsIDMain.add(currentID);

            }
        } finally {
            // Всегда закрываем курсор после чтения
            cursor.close();
        }
    }


    class MyAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        Context context;

        public MyAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
            this.context = context;
        }

        @Override
        public int getCount() {
            return ProductsList.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position){
            return ProductsList.get(position);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {

            boolean flag = false;
            String CorrectType;
            String previous_text;

            final ViewHolder holder;

            if (convertView == null) {

                convertView = layoutInflater.inflate(R.layout.row, null);
                holder = new ViewHolder();
                holder.product_name = (TextView) convertView.findViewById(R.id.ProductName);
                holder.delete_button = (ImageButton) convertView.findViewById(R.id.deleteButton);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();

            }

                holder.product_name.setText(ProductsList.get(position).toString());


            holder.product_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    text_searcher.clearFocus();
                 SQLiteDatabase ReadCharacteristics=dbHelper.getReadableDatabase();

                    String selectedFromList = holder.product_name.getText().toString()+", ID: "+ProductsID.get(position);

                    SQLiteDatabase TakeInfo= dbHelper.getReadableDatabase();

                   String CurrentId=ProductsID.get(position).toString();

                    Cursor cursor = TakeInfo.rawQuery("Select * from "+Product_Characteristic.TABLE_NAME.toString()+" where _ID="+CurrentId,null);

                    cursor.moveToFirst();
                        int protein_column=cursor.getInt(cursor.getColumnIndex(Product_Characteristic.PROTEIN));
                        String rating_column_s=cursor.getString(cursor.getColumnIndex(Product_Characteristic.RATING));
                        int fatness_column=cursor.getInt(cursor.getColumnIndex(Product_Characteristic.FATNESS));
                        int carbo_column=cursor.getInt(cursor.getColumnIndex(Product_Characteristic.CARBOHYDRATES));
                        int calories_column=cursor.getInt(cursor.getColumnIndex(Product_Characteristic.CALORIES));
                       // String photo_column=cursor.getString(cursor.getColumnIndex(Product_Characteristic.PHOTO));
                    float rating_column;
                        if (rating_column_s.isEmpty()){
                            rating_column=0;
                        }else {
                            rating_column = Float.parseFloat(rating_column_s);
                        }
                        cursor=TakeInfo.rawQuery("Select DOM, TEMPERATURE, SHELF_LIFE FROM "+Products.TABLE_NAME.toString()+" where _ID="+CurrentId,null);
                        cursor.moveToFirst();
                        String dom=cursor.getString(cursor.getColumnIndex(Products.DOM));
                        String temperature=cursor.getString(cursor.getColumnIndex(Products.TEMPERATURE));
                        String shelf=cursor.getString(cursor.getColumnIndex(Products.SHELF_LIFE));

                        cursor=TakeInfo.rawQuery("Select pt.TYPE_NAME from Product_Type pt inner join Products p on p.PRODUCT_TYPE_ID=pt._ID where p._ID="+CurrentId,null);
                        cursor.moveToFirst();
                        String type_name=cursor.getString(cursor.getColumnIndex(ProductsTablesContracts.Product_Type.TYPE_NAME));

                    Bundle bundle = new Bundle();
                    bundle.putString("TYPE_NAME",type_name);
                    bundle.putString("ID",CurrentId);
                    bundle.putString("Name",holder.product_name.getText().toString());
                    bundle.putInt("Protein",protein_column);
                    bundle.putInt("Fatness",fatness_column);
                    bundle.putInt("Calories",calories_column);
                    bundle.putInt("Carbohydrates",carbo_column);
                    bundle.putFloat("Rating",rating_column);
                    bundle.putString("DOM",dom);
                    bundle.putString("TEMPERATURE",temperature);
                    bundle.putString("SHELF",shelf);

                    replaceFragment.onFragmentReplace(bundle);

                }
            });

            holder.delete_button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    text_searcher.clearFocus();
                    holder.delete_button.setEnabled(false);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setTitle("Вы уверены, что хотите удалить продукт: "+ProductsList.get(position)+"?");

                    builder.setMessage("Продукт будет удален с вашего устройства, восстановить его уже не получится!!!")
                            .setCancelable(false)
                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dbHelper.getWritableDatabase().delete(Products.TABLE_NAME,Products._ID+"="+ProductsID.get(position),null);
                                    dbHelper.getWritableDatabase().delete(Product_Characteristic.TABLE_NAME,Product_Characteristic._ID+"="+ProductsID.get(position),null);

                                    for (int i=0;i<ProductsIDMain.size();i++){
                                        if (ProductsID.get(position)==ProductsIDMain.get(i)){
                                            ProductsIDMain.remove(i);
                                            ProductsListMain.remove(i);
                                        }
                                    }

                                    ProductsList.remove(position);
                                    ProductsID.remove(position);
                                    notifyDataSetChanged();
                                    holder.delete_button.setEnabled(true);
                                }
                            })
                            .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    holder.delete_button.setEnabled(true);
                                }
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            });
            return convertView;
        }
    }

    static class ViewHolder{
        TextView product_name;
        ImageButton delete_button;
    }

}
