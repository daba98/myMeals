package de.die_bartmanns.daniel.mymeal.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "OwnCategories")
public class OwnCategory {

    @NonNull
    @PrimaryKey
    private String name;

    public OwnCategory(String name){
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
