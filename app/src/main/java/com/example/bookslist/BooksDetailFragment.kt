package com.example.bookslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton

class BooksDetailFragment : Fragment() {

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
        var id = 0
        val dataBaseHelper = DataBaseHelper(this.context!!)

        saveButton.setOnClickListener {
            if (nameEditText.text != null && authorEditText.text != null) {
                saveToDataManager(nameEditText, authorEditText, id)

                val status = dataBaseHelper.addBook(
                    BooksModel(
                        nameEditText.text.toString(),
                        authorEditText.text.toString(),
                        0
                    )
                )
                if (status > -1) {
                    Toast.makeText(this.context, "book saved!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this.context, "book didn't save!", Toast.LENGTH_LONG).show()
                }
                id++
                replaceListFragment()
            }
        }
    }

    private fun saveToDataManager(
        nameEditText: EditText,
        authorEditText: EditText,
        id: Int
    ) {

        DataManager.booksList.add(
            BooksModel(
                nameEditText.text.toString(),
                authorEditText.text.toString(),
                id
            )
        )

    }

    private fun replaceListFragment() {
        val fragmentManager = fragmentManager
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.mainActivity, BooksListFragment())

        transaction?.commit()
    }


}
