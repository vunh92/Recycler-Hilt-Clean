package com.vunh.recycler_hilt_clean.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

@Entity(tableName = "tb_Moive")
data class Movie (
    @PrimaryKey()
    @SerializedName("movieId")
    var movieId: String,
    @SerializedName("category")
    var category: String,
    @SerializedName("imageUrl")
    var imageUrl: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("desc")
    var desc: String,
//    @SerializedName("date_created")
//    var dateCreated: Date,
//    @SerializedName("date_updated")
//    var dateUpdated: Date
) : Serializable {
//    @PrimaryKey()
//    var id: Int = 0
}
