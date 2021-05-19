package com.example.bookslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView

class BooksListAdapter(
    private val fragmentManager: FragmentManager
) :
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
                val transaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.mainActivity, UpdateBookFragment(booksList[position]))
                transaction.commit()
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