package com.example.bookslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class ParseBook {
    var id = 0
    fun parse(url: String): LiveData<Book> {
        val bookLiveData = MutableLiveData<Book>()

        val thread = Thread {
            val doc: Document = Jsoup.connect(url).get()
            val imageElement = doc.getElementById("coverImage")
            val titleElement = doc.getElementsByTag("h1")
            val authorElement = doc.getElementsByClass("authorName")
            val desElement = doc.getElementById("description")
            val imageUrl: String = imageElement.absUrl("src")

            val book = Book(
                titleElement.text(),
                authorElement.text(),
                id,
                desElement.text(),
                imageUrl
            )
            bookLiveData.postValue(book)

        }
        thread.start()
        id++
        return bookLiveData

    }
}