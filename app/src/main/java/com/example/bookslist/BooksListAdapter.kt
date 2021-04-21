package com.example.bookslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BooksListAdapter(var booksList: ArrayList<BooksModel>) : RecyclerView.Adapter<BooksListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_books_list, parent, false)
        view.setOnClickListener(View.OnClickListener {
            TODO()
        })
        return BooksListViewHolder(view)
    }

    override fun onBindViewHolder(holder: BooksListViewHolder, position: Int) {
        holder.bookName.text = booksList[position].name
        holder.bookAuthor.text = booksList[position].author

    }

    override fun getItemCount(): Int {
        return booksList.size
    }
}