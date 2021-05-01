package com.example.bookslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import org.jsoup.Jsoup

class LinkFragment() : Fragment() {
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
            val fragmentManager = fragmentManager
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(
                R.id.mainActivity,
                AddBookFragment(retrieveInfoFromWeb(link.text.toString()))
            )
            transaction?.commit()
        }


    }

    private fun retrieveInfoFromWeb(link: String): BooksModel {

        val doc = Jsoup.connect(link).get()
        val imageElement = doc.getElementById("coverImage")
        val titleElement = doc.getElementsByTag("h1")
        val authorElement = doc.getElementsByClass("authorName")
        val desElement = doc.getElementById("description")
        val imageUrl = imageElement.absUrl("src")
        return BooksModel(titleElement.text(), authorElement.text(), desElement.text(), imageUrl)


    }
}