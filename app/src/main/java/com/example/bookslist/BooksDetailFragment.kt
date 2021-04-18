package com.example.bookslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
        saveButton.setOnClickListener(View.OnClickListener {
            DataManager.booksList.add(BooksModel(nameEditText.text.toString(),authorEditText.text.toString()))
            val fragmentManager = fragmentManager
            val transaction = fragmentManager?.beginTransaction()
            if (transaction != null) {
                transaction.replace(R.id.mainActivity, BooksListFragment())
            }

            if (transaction != null) {
                transaction.commit()
            }
        })
    }


}
