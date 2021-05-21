package com.example.bookslist

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ParseBookTest {

    @Test
    fun parse() {
        val parseBook = ParseBook()
        val url =
            "https://www.goodreads.com/book/show/49552.The_Stranger?from_search=true&from_srp=true&qid=Yv0ZiMIA7Z&rank=1"
        val bookLiveData = parseBook.parse(url)
        val author = bookLiveData.value?.author
        assertEquals(
            "Albert Camus Vedat Günyol (Translator) Jalal Al-e Ahmad (Translator) Matthew Ward (Translator) علی\u200Cاصغر خبره\u200Cزاده (Translator)",
            author
        )
        assertEquals("The Stranger", bookLiveData.value?.name)

    }
}