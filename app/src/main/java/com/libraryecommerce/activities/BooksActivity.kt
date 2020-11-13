
package com.libraryecommerce.activities

import BooksAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.libraryecommerce.R
import com.libraryecommerce.model.Book
import com.libraryecommerce.service.HenriPotierXebiaService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.libraryecommerce.db.BooksDB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BooksActivity : AppCompatActivity() {
    lateinit var booksAdapter : BooksAdapter;

    lateinit var recyclerView: RecyclerView
    lateinit var toolBar: Toolbar
    lateinit var floatingButton : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.books_activity)
        booksAdapter = BooksAdapter(BooksDB.books);
        floatingButton = findViewById(R.id.basketButton)
        toolBar = findViewById(R.id.toolbar)
        setSupportActionBar(toolBar)
        recyclerView = findViewById<RecyclerView>(R.id.books)
        recyclerView.adapter = booksAdapter
        recyclerView.setLayoutManager(GridLayoutManager(this, 2 ));
        floatingButton.setOnClickListener {
            var intent = Intent(this.baseContext, BasketActivity::class.java)
            startActivity(intent)
        }
        loadBooks()
    }

    fun loadBooks() {
        var booooks = HenriPotierXebiaService.instance.listBooks()
        booooks?.enqueue(object : Callback<List<Book?>?> {
            override fun onFailure(call: Call<List<Book?>?>?, t: Throwable?) {
                Log.v("retrofit", t.toString())
            }

            override fun onResponse(call: Call<List<Book?>?>?, response: Response<List<Book?>?>?) {
                if (response?.body() != null) {
                    BooksDB.books = ArrayList(response.body()!!)
                    booksAdapter.refreshBooks(BooksDB.books)
                }
            }
        });
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