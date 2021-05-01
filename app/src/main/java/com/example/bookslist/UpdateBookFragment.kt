package com.example.bookslist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso


class UpdateBookFragment(private val bookId: String) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_books_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataBaseHelper = DataBaseHelper(this.context!!)
        val nameEditText = view.findViewById<EditText>(R.id.nameEditText)
        val authorEditText = view.findViewById<EditText>(R.id.authorEditText)
        val desTextView = view.findViewById<TextView>(R.id.descriptionTextView)
        val imageView = view.findViewById<ImageView>(R.id.bookImageView)
        val book = dataBaseHelper.fetchBook(bookId)
        nameEditText.setText(book?.name)
        authorEditText.setText(book?.author)
        Picasso.get().load(book?.imageLink).into(imageView)
        desTextView.text = book?.description

        val saveButton = view.findViewById<MaterialButton>(R.id.saveButton)
        val deleteButton = view.findViewById<MaterialButton>(R.id.deleteButton)
        deleteButton.setOnClickListener {
            val status = dataBaseHelper.deleteBook(bookId)
            if (status <= -1) {
                Toast.makeText(
                    this.context,
                    "book didn't delete!",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(this.context, "book successfully deleted!", Toast.LENGTH_LONG).show()
                replaceListFragment()
            }

        }
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
                        this.context,
                        "book didn't update!",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(this.context, "book successfully updated!", Toast.LENGTH_LONG)
                        .show()
                    replaceListFragment()

                }
            }

        }

    }

    private fun replaceListFragment() {
        val fragmentManager = fragmentManager
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.mainActivity, BooksListFragment())
        transaction?.commit()
    }
}