package com.example.bookslist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso


class UpdateBookFragment : Fragment() {

    private lateinit var bookViewModel: BookViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_books_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nameEditText = view.findViewById<EditText>(R.id.nameEditText)
        val authorEditText = view.findViewById<EditText>(R.id.authorEditText)
        val desTextView = view.findViewById<TextView>(R.id.descriptionTextView)
        val imageView = view.findViewById<ImageView>(R.id.bookImageView)
        bookViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.requireActivity().application)
        ).get(BookViewModel::class.java)
        val book = arguments?.getParcelable<Book>("UpdateBook")
        nameEditText.setText(book?.name)
        authorEditText.setText(book?.author)
        Picasso.get().load(book?.imageLink).into(imageView)
        desTextView.text = book?.description

        val saveButton = view.findViewById<MaterialButton>(R.id.saveButton)
        val deleteButton = view.findViewById<MaterialButton>(R.id.deleteButton)
        deleteButton.setOnClickListener {
            if (book != null) {
                bookViewModel.delete(book)
            }
            Toast.makeText(this.context, "book successfully deleted!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateBookFragment_to_booksListFragment)


        }
        saveButton.setOnClickListener {
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
                val newName = nameEditText.text.toString()
                val newAuthor = authorEditText.text.toString()
                val newBook =
                    Book(newName, newAuthor, book!!.id, book.description, book.imageLink)
                bookViewModel.update(newBook)
                Toast.makeText(
                    this.context,
                    "book updated successfully!",
                    Toast.LENGTH_LONG
                ).show()
                findNavController().navigate(R.id.action_updateBookFragment_to_booksListFragment)
            }
        }
    }
}