package com.libraryecommerce.db

import com.libraryecommerce.model.Book

class BasketDB {
    companion object{
        var basket : MutableMap<String, Int> = mutableMapOf()
        var shared = BasketDB()
    }

    fun computeTotal(): Float {
        var total: Float = 0f
        BooksDB.books.filter { book -> basket.containsKey(book?.isbn) }.forEach{book -> total += (book?.price?:0f) * basket.get(book?.isbn)}
        return total
    }

    fun addToBasket(book: Book?){
        book?.isbn?:return
        if (!basket.containsKey(book.isbn)) basket.put(book.isbn, 0)
        basket.put(book.isbn, basket.get(book.isbn)!!+1)
    }

    fun removeOneFromBasket(book: Book?){
        book?.isbn?:return
        if (!basket.containsKey(book.isbn)) return
        if(basket.get(book.isbn)==0) return
        else basket.put(book.isbn, basket.get(book.isbn)!!-1)
    }
}