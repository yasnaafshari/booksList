package com.example.bookslist

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookDao {
    @Insert
    fun insert(book: Book)

    @get:Query("SELECT * FROM books")
    val booksList: LiveData<List<Book>>

    @Update
    fun update(book: Book)

    @Delete
    fun delete(book: Book)
}