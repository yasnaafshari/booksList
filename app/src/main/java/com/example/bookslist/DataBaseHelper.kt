package com.example.bookslist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import java.util.ArrayList

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_BOOKS_TABLE = ("CREATE TABLE " + TABLE_BOOKS +
                "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_AUTHOR + " TEXT" + ")")
        db?.execSQL(CREATE_BOOKS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val DATABASE_NAME = "booksDataBase"
        private const val TABLE_BOOKS = "BooksTable"
        private const val KEY_ID = "_id"
        private const val KEY_NAME = "name"
        private const val KEY_AUTHOR = "author"

    }

    fun addBook(book: BooksModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, book.name)
        contentValues.put(KEY_AUTHOR, book.author)
        val success = db.insert(TABLE_BOOKS, null, contentValues)
        db.close()
        return success
    }

    fun showBooks(): ArrayList<BooksModel> {
        val booksList = ArrayList<BooksModel>()
        val selectQuery = "SELECT  * FROM $TABLE_BOOKS"
        val db = this.readableDatabase
        var cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var name: String
        var author: String
        var id: Int
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                author = cursor.getString(cursor.getColumnIndex(KEY_AUTHOR))
                name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                val book = BooksModel(name, author, id)
                booksList.add(book)

            } while (cursor.moveToNext())
        }
        return booksList
    }

    fun updateBook(book: BooksModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, book.name)
        contentValues.put(KEY_AUTHOR, book.author)
        val success = db.update(TABLE_BOOKS, contentValues, KEY_ID + "=" + book.id, null)
        db.close()
        return success
    }

    fun deleteBook(book: BooksModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID,book.id)
        val success = db.delete(TABLE_BOOKS, KEY_ID + "=" + book.id,null)
        db.close()
        return success
    }
}