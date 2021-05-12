package com.example.bookslist

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class BookViewModel(application: Application) : AndroidViewModel(application) {
    private val bookDao: BookDao

    init {
        val bookDb = BookRoomDatabase.getDatabase(application)
        bookDao = bookDb!!.bookDao()
    }

    fun insert(book: Book, onSuccess: Unit) {

        val thread = Thread {
            bookDao.insert(book)
        }
        thread.start()
        onSuccess
    }

}