package com.example.bookslist

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BooksListViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
    val bookName: TextView = itemView.findViewById<TextView>(R.id.bookName)
    val bookAuthor: TextView = itemView.findViewById<TextView>(R.id.bookAuthor)


}