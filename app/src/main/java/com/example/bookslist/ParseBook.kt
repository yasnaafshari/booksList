package com.example.bookslist

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class ParseBook {
    fun parse(url: String, onSuccess: (Book) -> Unit) {

        val thread = Thread {
            val doc: Document = Jsoup.connect(url).get()
            val imageElement = doc.getElementById("coverImage")
            val titleElement = doc.getElementsByTag("h1")
            val authorElement = doc.getElementsByClass("authorName")
            val desElement = doc.getElementById("description")
            val imageUrl: String = imageElement.absUrl("src")
            var book = Book(
                titleElement.text(),
                authorElement.text(),
                0,
                desElement.text(),
                imageUrl
            )
            onSuccess(book)
        }
        thread.start()

    }
}