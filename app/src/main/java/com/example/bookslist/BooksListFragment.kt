package com.example.bookslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BooksListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_books_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val dataBaseHelper = DataBaseHelper(this.context!!)
        val recyclerView = view.findViewById<RecyclerView>(R.id.booksListRecycler)
        val adapter = BooksListAdapter(dataBaseHelper.showBooks(), fragmentManager!!)
        val layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        val floatingActionButton = view.findViewById<FloatingActionButton>(R.id.fab)
        floatingActionButton.setOnClickListener {
            replaceDetailFragment()
        }

    }

    private fun replaceDetailFragment() {
        val fragmentManager = fragmentManager
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.mainActivity, AddBookFragment())
        transaction?.addToBackStack("add")
        transaction?.commit()
    }
}