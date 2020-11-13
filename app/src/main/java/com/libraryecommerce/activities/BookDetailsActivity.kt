package com.libraryecommerce.activities

//import BookDetailsAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.DialogTitle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.libraryecommerce.R
import com.libraryecommerce.model.Book
import com.libraryecommerce.service.HenriPotierXebiaService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.libraryecommerce.db.BasketDB
import com.libraryecommerce.db.BooksDB
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder
import java.util.*


class BookDetailsActivity : AppCompatActivity() {

    companion object {
        var bookExtra = "BOOK_EXTRA"
        var REQUEST_CODE_BOOK_DETAILS = 2
        var RETOUR = 100
    }

    var book : Book? = null

    lateinit var title: TextView
    lateinit var synopsis: TextView
    lateinit var price: TextView
    lateinit var imageView: ImageView

    lateinit var ajouter: Button

    lateinit var toolBar: Toolbar
    lateinit var floatingButton : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_details_activity)
        book = intent.getParcelableExtra<Book?>(BookDetailsActivity.bookExtra)?: null
        toolBar = findViewById(R.id.toolbar)
        setSupportActionBar(toolBar)
        title = findViewById(R.id.bookTitle)
        synopsis = findViewById(R.id.bookSynopsis)
        price = findViewById(R.id.bookPrice)
        imageView = findViewById(R.id.bookImage)

        initValues()

        ajouter = findViewById(R.id.ajouter)
        floatingButton = findViewById(R.id.basketButton)
        floatingButton.setOnClickListener {
            var intent = Intent(this.baseContext, BasketActivity::class.java)
            startActivity(intent)
        }
        ajouter?.setOnClickListener {
            if(book!=null){
                BasketDB.shared.addToBasket(book!!)
                Toast.makeText(it.context, "Livre ajouté", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(it.context, "Livre non trouvé", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun initValues(){
        title.text = book?.title
        synopsis.text = book?.synopsis?.joinToString("\n\n")
        price.text = price?.resources?.getString(R.string.price, book?.price)
        Picasso.get().load(book?.cover).into(imageView)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toolBar.title = "Nos livres"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.panier -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}