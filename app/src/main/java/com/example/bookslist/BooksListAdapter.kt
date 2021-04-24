package com.example.bookslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView

class BooksListAdapter(
    var booksList: ArrayList<BooksModel>,
    private val fragmentManager: FragmentManager
) :
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
                val transaction = fragmentManager.beginTransaction()
                val updateBookFragment = UpdateBookFragment(booksList[position].id)
                transaction.replace(R.id.mainActivity, updateBookFragment)
                transaction.commit()
            }
    }


    override fun getItemCount(): Int {
        return booksList.size
    }
}