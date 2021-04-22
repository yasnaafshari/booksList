package com.example.bookslist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.provider.BaseColumns._ID
import java.util.ArrayList

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_BOOKS_TABLE = (
                "CREATE TABLE $TABLE_BOOKS ( $_ID INTEGER PRIMARY KEY AUTOINCREMENT, $KEY_NAME TEXT NOT NULL, $KEY_AUTHOR  TEXT NOT NULL)")
        db?.execSQL(CREATE_BOOKS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val SQL_DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_BOOKS"
        db?.execSQL(SQL_DROP_TABLE)
        onCreate(db)
    }

    companion object : BaseColumns {
        private const val DATABASE_NAME = "booksDataBase"
        private const val TABLE_BOOKS = "BooksTable"
        private const val KEY_NAME = "name"
        private const val KEY_AUTHOR = "author"
        private const val KEY_ID = _ID

    }

    fun addBook(name: String, author: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, name)
        contentValues.put(KEY_AUTHOR, author)
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
        val idPos = cursor.getColumnIndex(KEY_ID)
        val authorPos = cursor.getColumnIndex(KEY_AUTHOR)
        val namePos = cursor.getColumnIndex(KEY_NAME)
        var name: String
        var author: String
        var id: String

        while (cursor.moveToNext()) {
            author = cursor.getString(authorPos)
            name = cursor.getString(namePos)
            id = cursor.getString(idPos)
            val book = BooksModel(name, author, id)
            booksList.add(book)
        }
        cursor.close()
        return booksList
    }

    fun updateBook(book: BooksModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, book.name)
        contentValues.put(KEY_AUTHOR, book.author)
        val selection = "$KEY_ID LIKE ? "
        val selectionArgs = arrayOf(book.id)
        val success = db.update(TABLE_BOOKS, contentValues, selection,selectionArgs)
        db.close()
        return success
    }

    fun deleteBook(book: BooksModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(_ID, book.id)
        val success = db.delete(TABLE_BOOKS, _ID + "=" + book.id, null)
        db.close()
        return success
    }

    fun fetchBook(bookId: String): BooksModel? {
        val db = this.readableDatabase
        var book: BooksModel? = null
        val columns: Array<String> = arrayOf(
            KEY_NAME,
            KEY_AUTHOR
        )
        val selection = KEY_ID + " LIKE ? "
        val selectionArgs = arrayOf(bookId)
        val cursor = db.query(
            TABLE_BOOKS,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        val authorPos = cursor.getColumnIndex(KEY_AUTHOR)
        val namePos = cursor.getColumnIndex(KEY_NAME)
        var name: String
        var author: String
        var id: String

        while (cursor.moveToNext()) {
            author = cursor.getString(authorPos)
            name = cursor.getString(namePos)

            book = BooksModel(name, author, bookId)
        }
        cursor.close()
        return book
    }
}