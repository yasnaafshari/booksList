package com.example.bookslist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
class Book(val name: String, val author: String, @PrimaryKey val id: Int, val description: String? = null,
           val imageLink: String? = null) {
}