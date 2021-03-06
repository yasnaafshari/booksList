package com.example.bookslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BooksListFragment : Fragment() {
    private lateinit var bookViewModel: BookViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_books_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.booksListRecycler)
        val adapter = BooksListAdapter()
        val layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        bookViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.requireActivity().application)
        ).get(BookViewModel::class.java)
        bookViewModel.getAllBooks().observe(viewLifecycleOwner, { booksList ->
            booksList?.let {
                adapter.setBookList(booksList)
            }
        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        val floatingActionButton = view.findViewById<FloatingActionButton>(R.id.fab)
        floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_booksListFragment_to_linkFragment)
        }
    }

}