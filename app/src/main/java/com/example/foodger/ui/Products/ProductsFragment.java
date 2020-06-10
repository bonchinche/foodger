package com.example.foodger.ui.Products;

import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.foodger.R;
import com.example.foodger.ProductsTablesContracts.Product_Characteristic;
import com.example.foodger.ProductsTablesContracts.Products;
import com.example.foodger.DataBaseHelper;

import java.util.ArrayList;

public class ProductsFragment extends Fragment {

    public ListView listView;
    private DataBaseHelper dbHelper;
    ArrayList<String> ProductsList = new ArrayList<>();
    ArrayList<Integer> ProductsID=new ArrayList<>();
    ArrayList<String> TypeProductsList = new ArrayList<>();
    MyAdapter myAdapter;

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

        final View root = inflater.inflate(R.layout.fragment_products, container, false);

        listView = root.findViewById(R.id.products_list);

        dbHelper = new DataBaseHelper(getContext());

        if (!(ProductsList.size() > 0)) {
        //if (!(mainActivity.ProductsList.size()>0)){
            Toast.makeText(getContext(), "Пустой products_list", Toast.LENGTH_SHORT).show();
            SelectFromProducts();
        }

        Spinner spinner = (Spinner) root.findViewById(R.id.type_products);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, TypeProductsList);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);

        myAdapter=new MyAdapter(getActivity());

        listView.setAdapter(myAdapter);
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

            // Проходим через все ряды
            while (cursor.moveToNext()) {
                // Используем индекс для получения строки или числа
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(ProductNameIndex);

                // Выводим значения каждого столбца
                ProductsList.add(currentName);
                ProductsID.add(currentID);
                // mainActivity.ProductsList.add(currentName);
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

            final ViewHolder holder;

            if (convertView == null) {

                convertView=layoutInflater.inflate(R.layout.row,null);
                holder=new ViewHolder();
                holder.product_name=(TextView) convertView.findViewById(R.id.ProductName);
               // holder.product_name.setTag(position);
                holder.delete_button=(ImageButton)convertView.findViewById(R.id.deleteButton);
                if (holder.delete_button.getTag()==null){
                    //Toast.makeText(getContext(), "Tag: " + holder.delete_button.getTag(), Toast.LENGTH_SHORT).show();
                holder.delete_button.setTag(position);
                     } //тут присваивать тег равны   й йди в таблице продукт
                convertView.setTag(holder);
            }
            else
            {
               holder=(ViewHolder)convertView.getTag();
            }


            holder.product_name.setText(ProductsList.get(position).toString());


            holder.product_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 SQLiteDatabase ReadCharacteristics=dbHelper.getReadableDatabase();

                    String selectedFromList = holder.product_name.getText().toString()+", ID: "+ProductsID.get(position);

                    SQLiteDatabase TakeInfo= dbHelper.getReadableDatabase();

                    // Делаем запрос rawQuery("SELECT id, name FROM people WHERE name = ? AND id = ?", new String[] {"David", "2"});
                    String CurrentId=ProductsID.get(position).toString();
                    //Cursor cursor = TakeInfo.rawQuery("Select * from Product_Characteristic where _ID=?",new String[]{CurrentId});
                    Cursor cursor = TakeInfo.rawQuery("Select * from "+Product_Characteristic.TABLE_NAME.toString()+" where _ID="+CurrentId,null);

                    cursor.moveToFirst();
                        //int currentID = cursor.getInt(cursor.getColumnIndex(Product_Characteristic._ID));
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

                        // ProductCharacteristicList.add(currentID);
                    Log.d("SOSAT","rating: "+rating_column_s);

                    Bundle bundle = new Bundle();
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

                    Toast.makeText(getContext(), "Удаляем: ID: " + ProductsID.get(position)+", ИМЯ: "+ProductsList.get(position), Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setTitle("Вы уверены, что хотите удалить продукт: "+ProductsList.get(position)+"?");

                    builder.setMessage("Продукт будет удален с вашего устройства, восстановить его уже не получится!!!")
                            .setCancelable(false)
                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dbHelper.getWritableDatabase().delete(Products.TABLE_NAME,Products._ID+"="+ProductsID.get(position),null);
                                    dbHelper.getWritableDatabase().delete(Product_Characteristic.TABLE_NAME,Product_Characteristic._ID+"="+ProductsID.get(position),null);
                                    ProductsID.remove(position);
                                    ProductsList.remove(position);
                                    notifyDataSetChanged();
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
            return convertView;
        }
    }

    static class ViewHolder{
        TextView product_name;
        ImageButton delete_button;
    }

}
