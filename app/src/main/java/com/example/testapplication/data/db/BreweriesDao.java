package com.example.testapplication.data.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface BreweriesDao {

    @Query("SELECT * FROM breweries")
    Single<List<Breweries>> getAll();

    @Query("SELECT * FROM breweries WHERE name LIKE '%' || :search || '%'")
    Flowable<List<Breweries>> getAllWithName(String search);

    @Query("SELECT * FROM breweries LIMIT 1")
    Breweries getAnyBreweries();

    @Insert
    void insert(List<Breweries> breweries);

    @Update
    void update(List<Breweries> breweries);

    @Delete
    void delete(Breweries breweries);

}