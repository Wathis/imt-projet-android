package com.libraryecommerce.model

data class Offers(var offers: ArrayList<Offer>?) {

    data class Offer(var type: String?, var value: Int, var sliceValue: Int) {
        
    }
}