package com.example.bookslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton

class LinkFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_link, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val link = view.findViewById<EditText>(R.id.linkEditText)
        val searchBookButton = view.findViewById<MaterialButton>(R.id.searchButton)
        searchBookButton.setOnClickListener {
            val parseBook = ParseBook()
            parseBook.parse(link.text.toString()).observe(this.viewLifecycleOwner, {
                showAddBookFragment(it)
            })
        }
    }

    private fun showAddBookFragment(book: Book) {
        val fragmentManager = fragmentManager
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(
            R.id.mainActivity,
            AddBookFragment.newInstance(book)
        )
        transaction?.commit()
    }
}

