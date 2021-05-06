package com.example.bookslist

data class BooksModel(
    val name: String, val author: String, val id: Int, val description: String? = null,
    val imageLink: String? = null
)

