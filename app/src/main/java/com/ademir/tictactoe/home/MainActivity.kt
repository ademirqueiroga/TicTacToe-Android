package com.ademir.tictactoe.home

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ademir.tictactoe.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_play.setOnClickListener {
            startActivity(Intent(this, BoardActivity::class.java))
        }
        btn_records.setOnClickListener {
            startActivity(Intent(this, ResultsActivity::class.java))
        }
    }
}
