
package com.libraryecommerce.activities

import BasketAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.libraryecommerce.R
import com.libraryecommerce.db.BooksDB
import com.libraryecommerce.model.Book
import java.sql.Array

class BasketActivity : AppCompatActivity() {
    lateinit var basketAdapter : BasketAdapter;

    lateinit var filteredBooks: ArrayList<Book?>

    lateinit var recyclerView: RecyclerView
    lateinit var finaliser: Button
    lateinit var vider: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.basket_activity)
        filteredBooks = ArrayList(BooksDB.books.filter { book -> (book?.quantity?:0) > 0 })
        basketAdapter = BasketAdapter(filteredBooks);
        recyclerView = findViewById<RecyclerView>(R.id.basket)
        finaliser = findViewById(R.id.finaliser)
        vider = findViewById(R.id.vider)
        recyclerView.adapter = basketAdapter
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        finaliser.setOnClickListener {
            viderPanier()
            Toast.makeText(this, "Commande réalisée", Toast.LENGTH_SHORT).show()
            finish()
        }
        vider.setOnClickListener {
            viderPanier()
            Toast.makeText(this, "Votre panier est maintenant vide", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun viderPanier() {
        BooksDB.books.forEach {
            it?.quantity = 0
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
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