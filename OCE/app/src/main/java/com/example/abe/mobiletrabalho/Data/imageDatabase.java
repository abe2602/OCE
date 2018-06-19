package com.example.abe.mobiletrabalho.Data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/*Cria a base, linkando*/
@Database(entities = {ImageClass.class}, version = 2, exportSchema = false)
public abstract class imageDatabase extends RoomDatabase{
    private static imageDatabase INSTANCE;
    public abstract DAOImage imageDao();

    public static imageDatabase getDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE =
                    Room.databaseBuilder(context, imageDatabase.class, "imagedatabase")
                            .allowMainThreadQueries() //Permite querys da main
                            .fallbackToDestructiveMigration()
                            .build(); //Cria
        }
        return INSTANCE;
    }

    /*public static void destroyInstance(){
        INSTANCE = null;
    }*/
}
