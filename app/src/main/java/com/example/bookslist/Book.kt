package com.example.bookslist

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "books")
@Parcelize
class Book(
    val name: String,
    val author: String,
    @PrimaryKey(autoGenerate = true) val id: Int,
    val description: String? = null,
    val imageLink: String? = null
) : Parcelable