package com.example.bookslist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class BookViewModel(application: Application) : AndroidViewModel(application) {

    private val bookDao: BookDao
    private val bookList: LiveData<List<Book>>

    init {
        val bookDb = BookRoomDatabase.getDatabase(application)
        bookDao = bookDb!!.bookDao()
        bookList = bookDao.booksList
    }

    fun insert(book: Book) {
        val thread = Thread {
            bookDao.insert(book)
        }
        thread.start()
    }

    fun getAllBooks(): LiveData<List<Book>> {
        return bookList
    }
    fun update(book: Book){
        val thread = Thread {
            bookDao.update(book)
        }
        thread.start()
    }
    fun delete(book: Book){
        val thread = Thread {
            bookDao.delete(book)
        }
        thread.start()
    }

}