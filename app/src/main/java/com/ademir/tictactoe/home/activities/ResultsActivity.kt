package com.ademir.tictactoe.home.activities

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.VERTICAL
import android.view.View
import com.ademir.tictactoe.App
import com.ademir.tictactoe.R
import com.ademir.tictactoe.commons.prepare
import com.ademir.tictactoe.home.adapters.ResultAdapter
import kotlinx.android.synthetic.main.activity_results.*

class ResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = getString(R.string.text_results)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        val adapter = ResultAdapter()

        recyclerview.prepare(adapter, LinearLayoutManager(this, VERTICAL, false))

        App.database.gameDao().all().observe(this, Observer {
            it?.let {
                adapter.setData(it)
                progressbar.visibility = View.GONE
            }
        })

    }

}
