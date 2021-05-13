package com.example.bookslist

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookDao {
    @Insert
    fun insert(book: Book)

    @get:Query("SELECT * FROM books")
    val booksList : LiveData<List<Book>>
}