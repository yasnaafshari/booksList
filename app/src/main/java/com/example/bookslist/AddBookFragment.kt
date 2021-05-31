package com.example.bookslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso

class AddBookFragment : Fragment() {

    private lateinit var bookViewModel: BookViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_books_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val nameEditText = view.findViewById<EditText>(R.id.nameEditText)
        val authorEditText = view.findViewById<EditText>(R.id.authorEditText)
        val desTextView = view.findViewById<TextView>(R.id.descriptionTextView)
        val imageView = view.findViewById<ImageView>(R.id.bookImageView)
        bookViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.requireActivity().application)
        ).get(BookViewModel::class.java)
        val book = arguments?.getParcelable<Book>("book")
        Picasso.get().load(book?.imageLink).into(imageView)
        nameEditText.setText(book?.name)
        authorEditText.setText(book?.author)
        desTextView.text = book?.description

        val saveButton = view.findViewById<MaterialButton>(R.id.saveButton)
        val deleteButton = view.findViewById<MaterialButton>(R.id.deleteButton)
        deleteButton.setOnClickListener {
            findNavController().navigate(R.id.action_addBookFragment_to_booksListFragment)
        }

        saveButton.setOnClickListener {
            saveBook(nameEditText, authorEditText, desTextView, book?.imageLink!!)
        }
    }

    private fun saveBook(
        nameEditText: EditText,
        authorEditText: EditText,
        descriptionTextView: TextView,
        imageUrl: String
    ) {
        var isValid = true
        nameEditText.error = if (nameEditText.text.toString().isEmpty()) {
            isValid = false
            "Required Field"
        } else null
        authorEditText.error = if (authorEditText.text.toString().isEmpty()) {
            isValid = false
            "Required Field"
        } else null
        if (isValid) {

            val book = Book(
                nameEditText.text.toString(),
                authorEditText.text.toString(),
                arguments?.getParcelable<Book>("book")!!.id,
                descriptionTextView.text.toString(),
                imageUrl
            )
            bookViewModel.insert(book)
            findNavController().navigate(R.id.action_addBookFragment_to_booksListFragment)
        }
    }
}
