package com.example.fetchproductassignment.app;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fetchproductassignment.models.CartDataModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CartDataDAO {

    @Query("select * from CartDB")
    List<CartDataModel> getAllData();

    @Query("select count(CartDB.id) from CartDB where id=:queryID")
    int productExist(String queryID);

    @Query("update CartDB set quantity=:newQuantity where id=:queryID")
    void updateProductCount(String queryID, int newQuantity);

    @Query("delete from CartDB where id=:queryID")
    void deleteProduct(String queryID);

    @Insert
    void addProduct(CartDataModel cartDataModel);

    @Query("delete from CartDB")
    void deleteTable();

    @Query("select quantity from CartDB where id=:id")
    int productCount(String id);
}
