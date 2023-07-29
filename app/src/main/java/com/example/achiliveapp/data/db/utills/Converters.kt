package com.example.achiliveapp.data.db.utills

import android.net.Uri
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.achiliveapp.data.models.entities.AwardType

@ProvidedTypeConverter
class AwardTypeConverter {

    @TypeConverter
    fun stringToEnum(value: String?): AwardType? {
        return value?.let { AwardType.valueOf(it) }
    }

    @TypeConverter
    fun enumToString(type: AwardType?): String? {
        return type?.let { type.name }
    }

}


@ProvidedTypeConverter
class ImageUriConverter {

    @TypeConverter
    fun uriToString(uri: Uri?): String? = uri?.let { it.toString() }

    @TypeConverter
    fun stringToUri(path: String?): Uri? = path.let { Uri.parse(it) }
}