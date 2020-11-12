
import android.content.res.Resources
import android.icu.number.NumberFormatter.with
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryecommerce.R
import com.example.libraryecommerce.inflate
import com.example.libraryecommerce.model.Book
import com.squareup.picasso.Picasso


class BooksAdapter(private val books: ArrayList<Book>) :
    RecyclerView.Adapter<BooksAdapter.BookHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksAdapter.BookHolder {
        val inflatedView = parent.inflate(R.layout.books_item, false)
        return BookHolder(inflatedView)
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
            description = itemView.findViewById(R.id.bookDescription)
            price = itemView.findViewById(R.id.bookPrice)
            imageView = itemView.findViewById(R.id.bookImage)
            itemView.setOnClickListener(this)
            buttonAdd = itemView.findViewById(R.id.ajouter)
            buttonAdd?.setOnClickListener {
                Toast.makeText(itemView.context, "Livre ajouté", Toast.LENGTH_SHORT).show()
                book?.quantity = (book?.quantity?: 0) + 1
            }
        }

        override fun onClick(p0: View?) {

        }

        fun bindBook(book: Book) {
            this.book = book
            title?.text = book.name
            description?.text = book.description
            Picasso.get().load(book.imageUrl).into(imageView)
            price?.text = price?.resources?.getString(R.string.price, book.price)
        }
    }

}