package com.example.abe.mobiletrabalho.Data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.media.Image;

@Entity(tableName = "imageclass")
public class ImageClass {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    public long id; //PK

    @ColumnInfo(name = "description")
    private String description; //Saber qual é a descrição dela (Leão, Macaco, feliz, etc)
    @ColumnInfo(name = "type")
    private String type; //Saber de qual classe ela vem
    @ColumnInfo(name = "name")
    private String name; //Saber de qual classe ela vem

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageClass(String name, String description, String type){
        this.description = description;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
