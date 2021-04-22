package com.example.bookslist

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BooksListAdapter(var booksList: ArrayList<BooksModel>, var context: Context) :
    RecyclerView.Adapter<BooksListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_books_list, parent, false)

        return BooksListViewHolder(view)
    }

    override fun onBindViewHolder(holder: BooksListViewHolder, position: Int) {
        holder.bookName.text = booksList[position].name
        holder.bookAuthor.text = booksList[position].author
        holder.itemView
            .setOnClickListener {
                val intent = Intent(context, UpdateBookActivity::class.java)
                val KEY_ID = BaseColumns._ID
                intent.putExtra(KEY_ID, booksList[position].id)
                (context as Activity).startActivity(intent)
            }
    }



    override fun getItemCount(): Int {
        return booksList.size
    }
}