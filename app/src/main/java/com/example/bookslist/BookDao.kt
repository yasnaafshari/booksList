package com.example.bookslist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert

@Dao
interface BookDao {
    @Insert
    fun insert(book : Book)
}