package de.die_bartmanns.daniel.mymeal.data.typeConverter;

import androidx.room.TypeConverter;

import de.die_bartmanns.daniel.mymeal.data.Difficulty;

public class DifficultyTypeConverter {

    @TypeConverter
    public static Difficulty toDifficulty(Integer value) {
        return value == null ? null : Difficulty.values()[value];
    }

    @TypeConverter
    public static Integer toInteger(Difficulty difficulty) {
        return difficulty == null ? null : difficulty.ordinal();
    }
}
