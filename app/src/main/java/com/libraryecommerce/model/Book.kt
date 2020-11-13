package com.libraryecommerce.model

import android.os.Parcel
import android.os.Parcelable

class Book(
    val isbn : String?,
    val title : String?,
    val price : Float,
    val cover : String?,
    var synopsis : List<String>) :
    Parcelable {

    var quantity = 0

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readFloat(),
        parcel.readString(),
        arrayListOf()
    ) {
        parcel.readStringList(synopsis)
        quantity = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(isbn)
        parcel.writeString(title)
        parcel.writeFloat(price)
        parcel.writeString(cover)
        parcel.writeStringList(synopsis)
        parcel.writeInt(quantity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }

}