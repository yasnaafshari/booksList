package com.example.bookslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class BooksListAdapter :
    RecyclerView.Adapter<BooksListViewHolder>() {
    private var booksList: List<Book> = mutableListOf()
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
                val args = Bundle()
                args.putParcelable("UpdateBook", booksList[position])
                val navController = findNavController(holder.itemView)
                navController.navigate(R.id.action_booksListFragment_to_updateBookFragment, args)
            }
    }


    override fun getItemCount(): Int {
        return booksList.size
    }

    fun setBookList(booksList: List<Book>) {
        this.booksList = booksList
        notifyDataSetChanged()
    }
}