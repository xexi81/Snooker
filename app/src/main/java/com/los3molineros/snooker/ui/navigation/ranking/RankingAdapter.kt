package com.los3molineros.snooker.ui.navigation.ranking

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.los3molineros.snooker.R
import com.los3molineros.snooker.data.model.RankingUI
import com.los3molineros.snooker.databinding.ItemRankingBinding
import com.los3molineros.snooker.ui.navigation.matchResult.MatchDetailActivity
import com.los3molineros.snooker.ui.player.PlayerActivity
import java.text.DecimalFormat
import java.text.NumberFormat

class RankingAdapter(private var rankingUIList: List<RankingUI>): RecyclerView.Adapter<RankingAdapter.ViewHolder>() {
    val formatter: NumberFormat = DecimalFormat("#,###")

    fun setRanking(rankingUIList: List<RankingUI>) {
        this.rankingUIList = rankingUIList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingAdapter.ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_ranking, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RankingAdapter.ViewHolder, position: Int) = holder.bind(rankingUIList[position])

    override fun getItemCount() = rankingUIList.size


    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        private val binding = ItemRankingBinding.bind(view)

        fun bind(rankingUI: RankingUI) {
            binding.txtPlayer.text = rankingUI.playerName
            binding.txtPosition.text = rankingUI.position.toString()
            binding.txtPounds.text = formatter.format(rankingUI.sum)

            binding.item.setOnClickListener {
                val intent = Intent(binding.root.context, PlayerActivity::class.java)
                intent.putExtra("ID", rankingUI.playerId)
                it.context.startActivity(intent)
            }
        }
    }

}