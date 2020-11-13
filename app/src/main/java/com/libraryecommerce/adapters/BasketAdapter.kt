
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.libraryecommerce.R
import com.libraryecommerce.inflate
import com.libraryecommerce.model.Book
import com.squareup.picasso.Picasso


class BasketAdapter(private val booksInBasket: ArrayList<Book?>) :
    RecyclerView.Adapter<BasketAdapter.BasketHolder>() {

    private var onModifyQuantityListener: OnModifyQuantityListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketAdapter.BasketHolder {
        val inflatedView = parent.inflate(R.layout.basket_item, false)
        return BasketHolder(inflatedView, onModifyQuantityListener)
    }

    override fun getItemCount(): Int {
        return booksInBasket.size
    }

    override fun onBindViewHolder(holder: BasketAdapter.BasketHolder, position: Int) {
        holder.bindBook(booksInBasket[position])
    }

    interface OnModifyQuantityListener {
        fun onDataChanged()
    }

    fun setModifyQuantityListener(modifyQuantityListener: OnModifyQuantityListener) {
        onModifyQuantityListener = modifyQuantityListener
    }

    class BasketHolder internal constructor(itemView: View, callbackUpdatePanier: OnModifyQuantityListener?) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var title: TextView? = null
        var price: TextView? = null
        var quantity: TextView? = null
        var imageView: ImageView? = null
        var plusButton: Button? = null
        var minusButton: Button? = null
        var book: Book? = null
        init {
            title = itemView.findViewById(R.id.bookTitle)
            price = itemView.findViewById(R.id.bookPrice)
            quantity = itemView.findViewById(R.id.quantity)
            imageView = itemView.findViewById(R.id.bookImage)
            plusButton = itemView.findViewById(R.id.moreQuantity)
            minusButton = itemView.findViewById(R.id.lessQuantity)
            itemView.setOnClickListener(this)
            plusButton?.setOnClickListener {
                book?.quantity = (book?.quantity?: 0) + 1
                this.bindBook(book)
                callbackUpdatePanier?.onDataChanged()
            }
            minusButton?.setOnClickListener {
                if ((book?.quantity ?: 0) > 0) {
                    book?.quantity = (book?.quantity?: 0) - 1
                    this.bindBook(book)
                    callbackUpdatePanier?.onDataChanged()
                }
            }
        }

        override fun onClick(p0: View?) {

        }

        fun bindBook(book: Book?) {
            this.book = book
            title?.text = book?.title
            Picasso.get().load(book?.cover).into(imageView)
            price?.text = price?.resources?.getString(R.string.price, book?.price)
            quantity?.text = this.book?.quantity.toString()
        }
    }

}