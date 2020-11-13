package com.libraryecommerce.db

import com.libraryecommerce.model.Book

class BooksDB {
    companion object {
        var books: ArrayList<Book?> = arrayListOf()
    }
}