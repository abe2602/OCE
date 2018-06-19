package com.example.abe.mobiletrabalho.Data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DAOImage {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addImage(ImageClass image);

    @Query("select * from imageclass")
    public List<ImageClass> getAllImages();

    @Query("select * from imageclass where type = :type")
    public List<ImageClass> getAllImagesByType(String type);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateImage(ImageClass image);

    //Pega todas as imagens por tipo, então, a partir do tipo e descrição (que é única na nossa base), adquirir o ID
    @Query("delete from imageclass")
    void deleteAll();
}
