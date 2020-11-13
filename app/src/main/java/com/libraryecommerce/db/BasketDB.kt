package com.libraryecommerce.db

import android.util.Log
import com.libraryecommerce.model.Book
import com.libraryecommerce.model.Offers
import com.libraryecommerce.service.HenriPotierXebiaService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Math.floor

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

    fun computeTotalWithPromo(callback: (Float) -> Unit) {
        var price: Float = 0F
        var allISBN: String = ""
        for(isbn in basket.entries) {
            allISBN += isbn.key + ","
        }
        val offers: Call<Offers?>? = HenriPotierXebiaService.instance.commercialOffers(allISBN)

        offers?.enqueue(object : Callback<Offers?> {
            override fun onFailure(call: Call<Offers?>, t: Throwable) {
                Log.v("retrofit", t.toString())
            }

            override fun onResponse(call: Call<Offers?>, response: Response<Offers?>) {
                if (response.code() != 200) {
                    callback(0f)
                }
                else if (response?.body() != null) {
                    price = getBestOffer(computeTotal(), response.body()!!.offers)
                    callback(price)
                }
            }
        })
    }

    private fun getBestOffer(price: Float, offers: ArrayList<Offers.Offer>?): Float {
        var minValue = 0f
        for (offer in offers!!) {
            var currentValue = 0f
            when (offer.type) {
                "percentage" -> currentValue = price - price*offer.value/100
                "minus" -> currentValue = price - offer.value
                "slice" -> currentValue = price - (floor((price/offer.sliceValue).toDouble()) *offer.value).toFloat()
            }
            if(minValue.equals(0f) || currentValue < minValue) {
                minValue = currentValue
            }
        }
        return minValue
    }
}