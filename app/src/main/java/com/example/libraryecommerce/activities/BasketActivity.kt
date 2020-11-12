
package com.example.libraryecommerce.activities

import BasketAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryecommerce.R
import com.example.libraryecommerce.model.Book

class BasketActivity : AppCompatActivity() {
    lateinit var basketAdapter : BasketAdapter;

    companion object {
        var bookExtra = "BOOK_EXTRA"
        var REQUEST_CODE_BASKET = 1
        var PANIER_VIDE = 100
        var FINALISER = 101
    }

    lateinit var books: ArrayList<Book>
    lateinit var recyclerView: RecyclerView
    lateinit var finaliser: Button
    lateinit var vider: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.basket_activity)
        books = intent.getParcelableArrayListExtra<Book>(bookExtra)?: arrayListOf()
        basketAdapter = BasketAdapter(books);
        recyclerView = findViewById<RecyclerView>(R.id.basket)
        finaliser = findViewById(R.id.finaliser)
        vider = findViewById(R.id.vider)
        recyclerView.adapter = basketAdapter
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        finaliser.setOnClickListener {
            setResult(FINALISER)
            finish()
        }
        vider.setOnClickListener {
            setResult(PANIER_VIDE)
            finish()
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