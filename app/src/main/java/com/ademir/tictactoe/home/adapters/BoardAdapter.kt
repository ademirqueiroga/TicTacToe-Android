package com.ademir.tictactoe.home.adapters

import android.content.Context
import android.preference.PreferenceManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.ademir.tictactoe.App
import com.ademir.tictactoe.R
import com.ademir.tictactoe.commons.inflate
import com.ademir.tictactoe.data.models.GameResult
import com.ademir.tictactoe.game.Board
import kotlinx.android.synthetic.main.row_cell.view.*
import kotlinx.android.synthetic.main.row_header.view.*

/**
 * Created by ademir on 23/03/18.
 */
class BoardAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Board.OnGameOverListener {

    private val TAG = BoardAdapter::class.java.simpleName

    private lateinit var board: Board
//    private val imagesPath = HashMap<Int, String>(2)
    private val imagesPath = HashMap<Int, Int>(2).apply {
        put(Board.Cell.CIRCLE, R.mipmap.ic_launcher_round)
        put(Board.Cell.CROSS, R.drawable.ic_launcher_background)
    }

//    init {
//        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
//        imagesPath[Board.Cell.CROSS] = prefs.getString(Board.PREF_CROSS_IMAGE, "")
//        imagesPath[Board.Cell.CIRCLE] = prefs.getString(Board.PREF_CIRCLE_IMAGE, "")
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.row_header -> HeaderViewHolder(parent.inflate(viewType))
            R.layout.row_cell -> CellViewHolder(parent.inflate(viewType))
            else -> throw Exception("Invalid viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (position) {
            0 -> (holder as HeaderViewHolder).bind(board.status)
            else -> (holder as CellViewHolder).bind(board.getCells().elementAt(position - 1))
        }
    }

    override fun getItemCount(): Int {
        if (this::board.isInitialized) {
            return (board.size * board.size) + 1
        } else {
            return 0
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> R.layout.row_header
            else -> R.layout.row_cell
        }
    }

    override fun onGameOver(status: Int) {
        notifyItemChanged(0)
        App.DISK_IO.execute {
            val imagePath: String
            val winner: String
            if (status == Board.CROSS_WON) {
                winner = GameResult.CROSS
//                imagePath = imagesPath[Board.Cell.CROSS]!!
            } else {
                winner = GameResult.Circle
//                imagePath = imagesPath[Board.Cell.CIRCLE]!!
            }
//            App.database.gameDao().insert(GameResult(winner, imagePath))
            App.database.gameDao().insert(GameResult(winner))
        }
    }

    fun setBoard(board: Board) {
        this.board = board
        notifyDataSetChanged()
    }


    inner class CellViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            with(itemView) {
                setOnClickListener {
                    val cell = board.getCells().elementAt(adapterPosition - 1)
                    board.addMove(cell)
                    Log.i(TAG, cell.position.toString())
                    Log.i(TAG, board.toString())
                    notifyItemChanged(0)
                    notifyItemChanged(adapterPosition)
                }
            }
        }

        fun bind(cell: Board.Cell) = with(itemView) {
            if (cell.value == Board.Cell.EMPTY) {
                iv_picture.setImageDrawable(null)
            } else {
//                iv_picture.load(imagesPath[cell.value]!!)
                iv_picture.setImageResource(imagesPath[cell.value]!!)
            }
        }

    }


    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(status: Int) = with(itemView) {
            val text = when (status) {
                Board.CROSS_TURN -> context.getString(R.string.text_cross_turn)
                Board.CIRCLE_TURN -> context.getString(R.string.text_circle_turn)
                Board.TIED -> context.getString(R.string.text_tied)
                Board.CROSS_WON -> context.getString(R.string.text_cross_won)
                Board.CIRCLE_WON -> context.getString(R.string.text_circle_won)
                else -> throw Exception("Invalid board status")
            }
            tv_header.text = text
        }

    }

}