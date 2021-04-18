package com.example.bookslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BooksListAdapter : RecyclerView.Adapter<BooksListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_books_list, parent, false)
        return BooksListViewHolder(view)
    }

    override fun onBindViewHolder(holder: BooksListViewHolder, position: Int) {
       holder.bookName.text = DataManager.booksList[position].name
        holder.bookAuthor.text = DataManager.booksList[position].author


    }

    override fun getItemCount(): Int {
       return DataManager.booksList.size
    }
}