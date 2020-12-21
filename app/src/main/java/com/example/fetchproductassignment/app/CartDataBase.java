package com.example.fetchproductassignment.app;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.fetchproductassignment.models.CartDataModel;

@Database(entities = CartDataModel.class, version = 1, exportSchema = false)
public abstract class CartDataBase extends RoomDatabase {
    private static final String DATABASE_NAME = "cart_db";
    private static CartDataBase cartDataBase;

    public static synchronized CartDataBase getInstance(Context context) {
        if (cartDataBase == null) {
            cartDataBase = Room.databaseBuilder(context.getApplicationContext(), CartDataBase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return cartDataBase;
    }

    public abstract CartDataDAO cartDataDAO();
}
