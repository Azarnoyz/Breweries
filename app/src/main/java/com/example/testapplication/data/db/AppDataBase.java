package com.example.testapplication.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Breweries.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    public abstract BreweriesDao breweriesDao();
}
