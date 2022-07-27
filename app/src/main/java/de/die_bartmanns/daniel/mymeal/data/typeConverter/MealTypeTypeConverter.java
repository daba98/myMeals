package de.die_bartmanns.daniel.mymeal.data.typeConverter;

import androidx.room.TypeConverter;

import de.die_bartmanns.daniel.mymeal.data.Difficulty;
import de.die_bartmanns.daniel.mymeal.data.MealType;

public class MealTypeTypeConverter {

    @TypeConverter
    public static MealType toMealType(Integer value) {
        return value == null ? null : MealType.values()[value];
    }

    @TypeConverter
    public static Integer toInteger(MealType type) {
        return type == null ? null : type.ordinal();
    }
}
