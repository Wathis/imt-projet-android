
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.libraryecommerce.R
import com.libraryecommerce.activities.BasketActivity
import com.libraryecommerce.activities.BookDetailsActivity
import com.libraryecommerce.db.BasketDB
import com.libraryecommerce.inflate
import com.libraryecommerce.model.Book
import com.squareup.picasso.Picasso


class BooksAdapter(private var books: ArrayList<Book?>) :
    RecyclerView.Adapter<BooksAdapter.BookHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksAdapter.BookHolder {
        val inflatedView = parent.inflate(R.layout.books_item_grid, false)
        return BookHolder(inflatedView)
    }

    fun refreshBooks(newBooks : ArrayList<Book?>) {
        books = newBooks;
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return books.size
    }

    override fun onBindViewHolder(holder: BooksAdapter.BookHolder, position: Int) {
        holder.bindBook(books[position])
    }


    class BookHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var title: TextView? = null
        var description: TextView? = null
        var price: TextView? = null
        var imageView: ImageView? = null
        var buttonAdd: Button? = null
        var book: Book? = null
        init {
            title = itemView.findViewById(R.id.bookTitle)
//            description = itemView.findViewById(R.id.bookDescription)
//            price = itemView.findViewById(R.id.bookPrice)
            imageView = itemView.findViewById(R.id.bookImage)
            itemView.setOnClickListener(this)
//            buttonAdd = itemView.findViewById(R.id.ajouter)
//            buttonAdd?.setOnClickListener {
//                if(book!=null){
//                    BasketDB.shared.addToBasket(book!!)
//                    Toast.makeText(itemView.context, "Livre ajouté", Toast.LENGTH_SHORT).show()
//                }else{
//                    Toast.makeText(itemView.context, "Livre non trouvé", Toast.LENGTH_SHORT).show()
//                }
//            }
        }

        override fun onClick(p0: View?) {
            var intent = Intent(p0?.context, BookDetailsActivity::class.java)
            intent.putExtra(BookDetailsActivity.bookExtra, book)
            p0?.context?.let { startActivity(it, intent, null) }
        }

        fun bindBook(book: Book?) {
            this.book = book
            title?.text = book?.title
//            description?.text = book?.synopsis?.first()
            Picasso.get().load(book?.cover).into(imageView)
//            price?.text = price?.resources?.getString(R.string.price, book?.price)
        }
    }

}