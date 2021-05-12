package com.example.bookslist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.provider.BaseColumns._ID
import java.util.*

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_BOOKS_TABLE = (
                "CREATE TABLE $TABLE_BOOKS ( $_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "$KEY_NAME TEXT NOT NULL, " +
                        "$KEY_AUTHOR  TEXT NOT NULL," +
                        "$KEY_DESCRIPTION TEXT NOT NULL," +
                        "$KEY_IMAGE TEXT NOT NULL)")
        db?.execSQL(CREATE_BOOKS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val SQL_DROP_TABLE = ("DROP TABLE IF EXISTS $TABLE_BOOKS")
        db?.execSQL(SQL_DROP_TABLE)
        onCreate(db)
    }


    fun showBooks(): ArrayList<BooksModel> {
        val booksList = ArrayList<BooksModel>()
        val db = this.readableDatabase
        val columns = arrayOf(
            KEY_ID,
            KEY_NAME,
            KEY_AUTHOR,
            KEY_DESCRIPTION,
            KEY_IMAGE
        )
        val cursor: Cursor = db.query(TABLE_BOOKS, columns, null, null, null, null, null)

        val idPos = cursor.getColumnIndex(KEY_ID)
        val authorPos = cursor.getColumnIndex(KEY_AUTHOR)
        val namePos = cursor.getColumnIndex(KEY_NAME)
        val desPos = cursor.getColumnIndex(KEY_DESCRIPTION)
        val imagePos = cursor.getColumnIndex(KEY_IMAGE)
        var name: String
        var author: String
        var id: Int
        var image: String
        var des: String

        while (cursor.moveToNext()) {
            author = cursor.getString(authorPos)
            name = cursor.getString(namePos)
            id = cursor.getInt(idPos)
            des = cursor.getString(desPos)
            image = cursor.getString(imagePos)

            val book = BooksModel(name, author, id, des, image)
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
        contentValues.put(KEY_DESCRIPTION, book.description)
        contentValues.put(KEY_IMAGE, book.imageLink)
        val selection = "$KEY_ID LIKE ? "
        val selectionArgs = arrayOf(book.id.toString())
        return db.update(TABLE_BOOKS, contentValues, selection, selectionArgs)
    }

    fun deleteBook(bookId: Int): Int {
        val db = this.writableDatabase
        val selection = "$KEY_ID LIKE ? "
        val selectionArgs = arrayOf(bookId.toString())
        return db.delete(TABLE_BOOKS, selection, selectionArgs)
    }

    fun fetchBook(bookId: Int): BooksModel? {
        val db = this.readableDatabase
        var book: BooksModel? = null
        val columns: Array<String> = arrayOf(
            KEY_NAME,
            KEY_AUTHOR,
            KEY_DESCRIPTION,
            KEY_IMAGE
        )
        val selection = "$KEY_ID LIKE ? "
        val selectionArgs = arrayOf(bookId.toString())
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
        val desPos = cursor.getColumnIndex(KEY_DESCRIPTION)
        val imagePos = cursor.getColumnIndex(KEY_IMAGE)
        var name: String
        var author: String
        var image: String
        var des: String

        while (cursor.moveToNext()) {
            author = cursor.getString(authorPos)
            name = cursor.getString(namePos)
            image = cursor.getString(imagePos)
            des = cursor.getString(desPos)
            book = BooksModel(name, author, bookId, des, image)
        }
        cursor.close()
        return book
    }

    companion object : BaseColumns {
        private const val DATABASE_NAME = "booksDataBase"
        private const val TABLE_BOOKS = "BooksTable"
        private const val KEY_NAME = "name"
        private const val KEY_AUTHOR = "author"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_IMAGE = "image"
        private const val KEY_ID = _ID

    }
}