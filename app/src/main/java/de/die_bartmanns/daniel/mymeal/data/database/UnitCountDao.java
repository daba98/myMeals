package de.die_bartmanns.daniel.mymeal.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import de.die_bartmanns.daniel.mymeal.data.UnitCount;

@Dao
public interface UnitCountDao {

    @Insert
    long insertUnitCount(UnitCount unitCount);

    @Query("SELECT * FROM UnitCounts WHERE ingredient = :ingredient")
    UnitCount getUnitCount(String ingredient);

    @Update
    void updateUnitCount(UnitCount unitCount);
}
