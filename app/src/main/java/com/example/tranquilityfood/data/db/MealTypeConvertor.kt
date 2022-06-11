package com.example.tranquilityfood.data.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConvertor {

    @TypeConverter
    fun fromAnyToString(item: Any?): String {
        if(item == null)
            return ""
        return item as String
    }

    @TypeConverter
    fun fromStringToAny(item: String?): Any {
        if(item == null)
            return ""
        return item
    }
}