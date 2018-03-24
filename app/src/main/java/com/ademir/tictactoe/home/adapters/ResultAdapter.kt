package com.ademir.tictactoe.home.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.ademir.tictactoe.R
import com.ademir.tictactoe.commons.inflate
import com.ademir.tictactoe.commons.load
import com.ademir.tictactoe.data.models.GameResult
import kotlinx.android.synthetic.main.row_result.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by ademir on 24/03/18.
 */
class ResultAdapter : RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {

    private val dataSet = ArrayList<GameResult>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(parent.inflate(R.layout.row_result))
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    fun setData(results: Collection<GameResult>) {
        this.dataSet.clear()
        this.dataSet.addAll(results)
        notifyDataSetChanged()
    }

    class ResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val dateFormatter by lazy {
            SimpleDateFormat(view.context.getString(R.string.date_formatter_pattern), Locale.getDefault())
        }

        fun bind(result: GameResult) = with(itemView) {
            val text = if (result.winner == GameResult.CROSS) {
                context.getString(R.string.text_cross_won)
            } else {
                context.getString(R.string.text_circle_won)
            }
            tv_winner.text = text
            tv_date.text = dateFormatter.format(Date(result.time))
            iv_picture.load(result.winnerImagePath)
        }
    }

}