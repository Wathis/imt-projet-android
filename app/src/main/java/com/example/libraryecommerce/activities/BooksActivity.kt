
package com.example.libraryecommerce.activities

import BooksAdapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryecommerce.R
import com.example.libraryecommerce.model.Book
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BooksActivity : AppCompatActivity() {
    lateinit var booksAdapter : BooksAdapter;

    var books = arrayListOf(
        Book("Les recettes des films", " Chaque fois que nous créons une chose avec amour, espoir ou passion, nous donnons une âme à notre création. » Baron von Gikkingen, Le Royaume des chatsDes brioches aux haricots rouges du Voyage d","https://static.fnac-static.com/multimedia/Images/FR/NR/17/bb/bf/12565271/1540-1/tsp20200928070220/Les-Recettes-des-films-du-Studio-Ghibli.jpg", 17.85f, 0),
        Book("Fait Maison - numéro 3 par Cyril Lignac ", "Cyril Lignac cuisine 45 recettes salées et sucrées pour mettre encore et toujours un peu de peps dans ton quotidien. Un tian de légumes, un burger de bœuf, des endives au jambon au maroilles ou encore une superbe tarte au citron, un gâteau de Savoie ou un pop-corn caramélisé, sauce chocolat... \" Tu vas te régaler en toute simplicité ! \"","https://static.fnac-static.com/multimedia/Images/FR/NR/73/ac/c2/12758131/1540-1/tsp20201111082720/Fait-Maison-numero-3-par-Cyril-Lignac.jpg", 10f,0)
    )

    lateinit var recyclerView: RecyclerView
    lateinit var toolBar: Toolbar
    lateinit var floatingButton : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.books_activity)
        booksAdapter = BooksAdapter(books);
        floatingButton = findViewById(R.id.basketButton)
        toolBar = findViewById(R.id.toolbar)
        setSupportActionBar(toolBar)
        recyclerView = findViewById<RecyclerView>(R.id.books)
        recyclerView.adapter = booksAdapter
        recyclerView.setLayoutManager(LinearLayoutManager(this));
        floatingButton.setOnClickListener {
            var intent = Intent(this.baseContext, BasketActivity::class.java)
            intent.putParcelableArrayListExtra(BasketActivity.bookExtra, this.books)
            startActivity(intent)
        }

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