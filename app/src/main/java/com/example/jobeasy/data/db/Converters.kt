package com.example.jobeasy.data.db


import androidx.room.TypeConverter
import com.example.jobeasy.data.model.ContactPreference
import com.example.jobeasy.data.model.ContentV3
import com.example.jobeasy.data.model.Creative
import com.example.jobeasy.data.model.JobTag
import com.example.jobeasy.data.model.Location
import com.example.jobeasy.data.model.PrimaryDetails
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    // Convert ContactPreference to a JSON string
    @TypeConverter
    fun fromContactPreference(contactPreference: ContactPreference?): String? {
        return Gson().toJson(contactPreference)
    }

    @TypeConverter
    fun toContactPreference(contactPreferenceString: String?): ContactPreference? {
        return Gson().fromJson(contactPreferenceString, ContactPreference::class.java)
    }


    @TypeConverter
    fun fromPrimaryDetails(details: PrimaryDetails): String = Gson().toJson(details)
    @TypeConverter
    fun toPrimaryDetails(json: String): PrimaryDetails = Gson().fromJson(json, PrimaryDetails::class.java)

    // JobTag List Converter
    @TypeConverter
    fun fromJobTagList(tags: List<JobTag>): String = Gson().toJson(tags)
    @TypeConverter
    fun toJobTagList(json: String): List<JobTag> =
        Gson().fromJson(json, object : TypeToken<List<JobTag>>() {}.type)

    // Creative List Converter
    @TypeConverter
    fun fromCreativeList(creatives: List<Creative>): String = Gson().toJson(creatives)
    @TypeConverter
    fun toCreativeList(json: String): List<Creative> =
        Gson().fromJson(json, object : TypeToken<List<Creative>>() {}.type)

    // Location List Converter
    @TypeConverter
    fun fromLocationList(locations: List<Location>): String = Gson().toJson(locations)
    @TypeConverter
    fun toLocationList(json: String): List<Location> =
        Gson().fromJson(json, object : TypeToken<List<Location>>() {}.type)

    // ContentV3 Converter
    @TypeConverter
    fun fromContentV3(content: ContentV3): String = Gson().toJson(content)
    @TypeConverter
    fun toContentV3(json: String): ContentV3 =
        Gson().fromJson(json, ContentV3::class.java)

    // TranslatedContent Map Converter
    @TypeConverter
    fun fromTranslatedContent(map: Map<String, Any>): String = Gson().toJson(map)
    @TypeConverter
    fun toTranslatedContent(json: String): Map<String, Any> =
        Gson().fromJson(json, object : TypeToken<Map<String, Any>>() {}.type)


    @TypeConverter
    fun fromHashMap(value: HashMap<String, String>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toHashMap(value: String?): HashMap<String, String>? {
        val type = object : TypeToken<HashMap<String, String>>() {}.type
        return Gson().fromJson(value, type)
    }

}