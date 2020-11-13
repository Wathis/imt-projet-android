package com.libraryecommerce.service

import com.libraryecommerce.model.Book
import com.libraryecommerce.model.Offers
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface HenriPotierXebiaService {

    companion object {
        val instance: HenriPotierXebiaService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://henri-potier.xebia.fr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(HenriPotierXebiaService::class.java)
        }
    }

    @GET("books")
    fun listBooks(): Call<List<Book?>?>?

    @GET("books/{isbn},{isbn2}/commercialOffers")
    fun commercialOffers(@Path("isbn") isbn: String?, @Path("isbn2") isbn2: String?): Call<Offers?>?
}