package com.example.bookslist

import android.content.Intent
import android.os.Bundle
import android.provider.BaseColumns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton


class UpdateBookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_books_detail)
        val KEY_ID = BaseColumns._ID
        val bookId: String? = intent.extras?.getString(KEY_ID)
        val dataBaseHelper = DataBaseHelper(this)
        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val authorEditText = findViewById<EditText>(R.id.authorEditText)
        val book = dataBaseHelper.fetchBook(bookId!!)
        nameEditText.setText(book?.name)
        authorEditText.setText(book?.author)

        val saveButton = findViewById<MaterialButton>(R.id.saveButton)
        val deleteButton = findViewById<MaterialButton>(R.id.deleteButton)

        deleteButton.setOnClickListener(View.OnClickListener {
//            dataBaseHelper.deleteBook()
        })
        saveButton.setOnClickListener {
            var isValid = true
            nameEditText.error = if (nameEditText.text.toString().isEmpty()) {
                isValid = false
                "Required Field"
            } else null
            authorEditText.error = if (authorEditText.text.toString().isEmpty()) {
                isValid = false
                "Required Field"
            } else null
            if (isValid) {
                val newName = nameEditText.text.toString()
                val newAuthor = authorEditText.text.toString()
                val newBook = BooksModel(newName, newAuthor, bookId)
                val status = dataBaseHelper.updateBook(newBook)
                if (status <= -1) {
                    Toast.makeText(
                        this,
                        "book didn't update!",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(this, "book successfully updated!", Toast.LENGTH_LONG).show()
                    setResult(RESULT_OK, Intent())
                    finish()
                }
            }
        }


    }
}