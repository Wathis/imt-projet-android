
package com.libraryecommerce.activities

import BasketAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.libraryecommerce.R
import com.libraryecommerce.db.BasketDB
import com.libraryecommerce.db.BooksDB
import com.libraryecommerce.model.Book

class BasketActivity : AppCompatActivity() {
    lateinit var basketAdapter : BasketAdapter;

    var finalPrice: Float = 0.0f

    lateinit var recyclerView: RecyclerView
    lateinit var finaliser: Button
    lateinit var vider: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.basket_activity)
        basketAdapter = BasketAdapter(ArrayList(BooksDB.books.filter { book -> BasketDB.basket.containsKey(book?.isbn) && BasketDB.basket[book?.isbn]!! > 0 }));

        basketAdapter.setModifyQuantityListener(object: BasketAdapter.OnModifyQuantityListener{
            override fun onDataChanged() {
                updateFinalPrice()
            }
        })

        recyclerView = findViewById<RecyclerView>(R.id.basket)
        finaliser = findViewById(R.id.finaliser)
        vider = findViewById(R.id.vider)

        updateFinalPrice()
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

    private fun viderPanier() {
        BasketDB.basket = mutableMapOf()
    }

    private fun updateFinalPrice() {
        findViewById<TextView>(R.id.finalPrice).text = getString(R.string.price, BasketDB.shared.computeTotal())
    }

    private fun updateFinalPriceWithPromo() {
        findViewById<TextView>(R.id.finalPrice).text = getString(R.string.price, BasketDB.shared.computeTotal())
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