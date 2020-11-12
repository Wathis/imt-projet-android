
import android.content.res.Resources
import android.icu.number.NumberFormatter.with
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryecommerce.R
import com.example.libraryecommerce.inflate
import com.example.libraryecommerce.model.Book
import com.squareup.picasso.Picasso


class BasketAdapter(private val booksInBasket: ArrayList<Book>) :
    RecyclerView.Adapter<BasketAdapter.BasketHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketAdapter.BasketHolder {
        val inflatedView = parent.inflate(R.layout.basket_item, false)
        return BasketHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return booksInBasket.size
    }

    override fun onBindViewHolder(holder: BasketAdapter.BasketHolder, position: Int) {
        holder.bindBook(booksInBasket[position])
    }


    class BasketHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var title: TextView? = null
        var price: TextView? = null
        var imageView: ImageView? = null
        var book: Book? = null
        init {
            title = itemView.findViewById(R.id.bookTitle)
            price = itemView.findViewById(R.id.bookPrice)
            imageView = itemView.findViewById(R.id.bookImage)
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {

        }

        fun bindBook(book: Book) {
            this.book = book
            title?.text = book.name
            Picasso.get().load(book.imageUrl).into(imageView)
            price?.text = price?.resources?.getString(R.string.price, book.price)
        }
    }

}