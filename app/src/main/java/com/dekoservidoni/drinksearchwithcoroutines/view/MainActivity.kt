package com.dekoservidoni.drinksearchwithcoroutines.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.dekoservidoni.drinksearchwithcoroutines.R
import com.dekoservidoni.drinksearchwithcoroutines.databinding.ActivityMainBinding
import com.dekoservidoni.drinksearchwithcoroutines.di.ViewModelFactory
import com.dekoservidoni.drinksearchwithcoroutines.models.status.SearchStatus
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, factory).get(MainViewModel::class.java) }
    private val mainAdapter by lazy { MainAdapter() }

    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupObservers()

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.viewModel
        binding.executePendingBindings()

        with(toolbar) {
            setSupportActionBar(this)
            supportActionBar?.setDisplayShowTitleEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }

        with(list) {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = mainAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        val myActionMenuItem = menu?.findItem(R.id.action_search)
        searchView = myActionMenuItem?.actionView as SearchView
        searchView.isIconified = false

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.search(it)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            when(it.itemId) {
                R.id.action_search -> searchView.requestFocus()
                else -> {}
            }
        }
        return true
    }

    /// Private methods

    private fun setupObservers() {
        viewModel.data.observe(this, Observer { status ->

            status?.let {
                when (it) {
                    is SearchStatus.Success -> { mainAdapter.updateContent(it.content) }
                    is SearchStatus.Error -> { Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show() }
                }
            }
        })
    }
}
