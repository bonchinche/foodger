package com.example.foodger;

import android.provider.BaseColumns;

//TEST COMMENT

public class ProductsTablesContracts {

    private ProductsTablesContracts() {
    };

    public static final class Products implements BaseColumns {
        public final static String TABLE_NAME="Products";

        public final static String _ID= BaseColumns._ID;
        public final static String PRODUCT_TYPE_ID="PRODUCT_TYPE_ID";
        public final static String DOM = "DOM";
        public final static String DOS = "DOS";
        public final static String SHELF_LIFE = "SHELF_LIFE";
        public final static String TEMPERATURE = "TEMPERATURE";
        public final static String NAME = "NAME";

    }

    public static final class Product_Type implements BaseColumns {
        public final static String TABLE_NAME="Product_Type";

        public final static String _ID = BaseColumns._ID;
        public final static String AVG_SHELF_LIFE="AVG_SHELF_LIFE";
        public final static String AVG_TEMPERATURE = "AVG_TEMPERATURE";
        public final static String COLOR = "COLOR";
        public final static String TYPE_NAME = "TYPE_NAME";

    }

    public static final class Product_Characteristic implements BaseColumns {
        public final static String TABLE_NAME="Product_Characteristic";

        public final static String _ID = BaseColumns._ID;
        public final static String CALORIES="CALORIES";
        public final static String CARBOHYDRATES = "CARBOHYDRATES";
        public final static String FATNESS = "FATNESS";
        public final static String RATING = "RATING";
        public final static String PHOTO = "PHOTO";
        public final static String PROTEIN = "PROTEIN";
    }

}
