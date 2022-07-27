package de.die_bartmanns.daniel.mymeal.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.die_bartmanns.daniel.mymeal.data.OwnCategory;
import de.die_bartmanns.daniel.mymeal.data.UnitCount;

@Dao
public interface OwnCategoryDao {

    @Insert
    void insertCategory(OwnCategory category);

    @Query("SELECT * FROM OwnCategories WHERE name = :name")
    OwnCategory getCategory(String name);

    @Query("SELECT c.name FROM OwnCategories c ORDER BY name ASC")
    List<String> getAllCategoryNames();

    @Update
    void updateCategory(OwnCategory ownCategory);

    @Delete
    void deleteCategory(OwnCategory category);
}
