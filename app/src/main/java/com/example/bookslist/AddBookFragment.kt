package com.example.bookslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton

class AddBookFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_books_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val nameEditText = view.findViewById<EditText>(R.id.nameEditText)
        val authorEditText = view.findViewById<EditText>(R.id.authorEditText)
        val saveButton = view.findViewById<MaterialButton>(R.id.saveButton)
        val dataBaseHelper = DataBaseHelper(this.context!!)

        saveButton.setOnClickListener {
            saveBook(nameEditText, authorEditText, dataBaseHelper)
        }
    }

    private fun saveBook(
        nameEditText: EditText,
        authorEditText: EditText,
        dataBaseHelper: DataBaseHelper
    ) {
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

            val status = dataBaseHelper.addBook(
                nameEditText.text.toString(),
                authorEditText.text.toString(),
            )
            if (status <= -1) {
                Toast.makeText(
                    this.context,
                    "book didn't save!",
                    Toast.LENGTH_LONG
                ).show()
            } else Toast.makeText(this.context, "book saved!", Toast.LENGTH_LONG).show()
            replaceListFragment()
        }
    }


    private fun replaceListFragment() {
        val fragmentManager = fragmentManager
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.mainActivity, BooksListFragment())

        transaction?.commit()
    }


}
