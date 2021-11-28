package com.los3molineros.snooker.ui.navigation.tournament

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.los3molineros.snooker.R
import com.los3molineros.snooker.common.CommonFunctions.returnShortDateWithoutYear
import com.los3molineros.snooker.data.model.MatchUI
import com.los3molineros.snooker.databinding.ItemMatchEvenBinding
import com.los3molineros.snooker.databinding.ItemMatchHeaderEvenBinding
import com.los3molineros.snooker.databinding.ItemMatchHeaderOddBinding
import com.los3molineros.snooker.databinding.ItemMatchOddBinding
import com.los3molineros.snooker.ui.navigation.matchResult.MatchDetailActivity

class MatchesAdapter(private var matchesUIList: List<MatchUI>) :
    RecyclerView.Adapter<MatchesAdapter.ViewHolder>() {

    // Define xml to show
    companion object {
        const val HEADER_ODD = 1
        const val HEADER_EVEN = 2
        const val ODD = 3
        const val EVEN = 4
    }

    fun setMatches(matchesUIList: List<MatchUI>) {
        this.matchesUIList = matchesUIList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesAdapter.ViewHolder {
        val view: View
        when (viewType) {
            2 -> view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_match_header_even, parent, false)
            3 -> view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_match_odd, parent, false)
            4 -> view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_match_even, parent, false)
            else -> view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_match_header_odd, parent, false)
        }

        return ViewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: MatchesAdapter.ViewHolder, position: Int) =
        holder.bind(matchesUIList[position])

    override fun getItemCount() = matchesUIList.size

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return HEADER_ODD
        } else {
            if (matchesUIList[position].Round == matchesUIList[position - 1].Round) {
                if (position % 2 == 0) {
                    return ODD
                } else {
                    return EVEN
                }
            } else {
                if (position % 2 == 0) {
                    return HEADER_ODD
                } else {
                    return HEADER_EVEN
                }
            }
        }
    }

    inner class ViewHolder(private val view: View, private val viewType: Int) :
        RecyclerView.ViewHolder(view) {

        private val binding = if (viewType == 1) ItemMatchHeaderOddBinding.bind(view) else null
        private val binding2 = if (viewType == 2) ItemMatchHeaderEvenBinding.bind(view) else null
        private val binding3 = if (viewType == 3) ItemMatchOddBinding.bind(view) else null
        private val binding4 = if (viewType == 4) ItemMatchEvenBinding.bind(view) else null

        fun bind(matchesUIList: MatchUI) {
            val dateToCut = if (matchesUIList.EndDate!="") matchesUIList.EndDate else matchesUIList.ScheduledDate

            when (viewType) {
                1 -> {
                    binding?.txtRound?.text = matchesUIList.RoundName
                    binding?.txtNumero?.text = matchesUIList.Number.toString()
                    binding?.txtPlayer1?.text = if (matchesUIList.Player1Name.isNotEmpty()) matchesUIList.Player1Name else "Unknown"
                    binding?.txtPlayer2?.text = if (matchesUIList.Player2Name.isNotEmpty()) matchesUIList.Player2Name else "Unknown"
                    binding?.txtResult?.text = "${matchesUIList.Score1} - ${matchesUIList.Score2}"
                    binding?.txtNote?.text = matchesUIList.Note
                    binding?.txtDate?.text = returnShortDateWithoutYear(dateToCut)
                    if (matchesUIList.FrameScores.isEmpty()) {
                        binding?.ivMatchDetail?.visibility = View.GONE
                    } else {
                        binding?.ivMatchDetail?.visibility = View.VISIBLE

                        binding?.item?.setOnClickListener {
                            val intent = Intent(binding.root.context, MatchDetailActivity::class.java)
                            intent.putExtra("MATCH", matchesUIList)
                            it.context.startActivity(intent)
                        }
                    }

                }
                2 -> {
                    binding2?.txtRound?.text = matchesUIList.RoundName
                    binding2?.txtNumero?.text = matchesUIList.Number.toString()
                    binding2?.txtDate?.text = returnShortDateWithoutYear(dateToCut)
                    binding2?.txtPlayer1?.text = if (matchesUIList.Player1Name.isNotEmpty()) matchesUIList.Player1Name else "Unknown"
                    binding2?.txtPlayer2?.text = if (matchesUIList.Player2Name.isNotEmpty()) matchesUIList.Player2Name else "Unknown"
                    binding2?.txtResult?.text = "${matchesUIList.Score1} - ${matchesUIList.Score2}"
                    binding2?.txtNote?.text = matchesUIList.Note
                    if (matchesUIList.FrameScores.isEmpty()) {
                        binding2?.ivMatchDetail?.visibility = View.GONE
                    } else {
                        binding2?.ivMatchDetail?.visibility = View.VISIBLE

                        binding2?.item?.setOnClickListener {
                            val intent = Intent(binding2.root.context, MatchDetailActivity::class.java)
                            intent.putExtra("MATCH", matchesUIList)
                            it.context.startActivity(intent)
                        }
                    }
                }
                3 -> {
                    binding3?.txtNumero?.text = matchesUIList.Number.toString()
                    binding3?.txtDate?.text = returnShortDateWithoutYear(dateToCut)
                    binding3?.txtPlayer1?.text = if (matchesUIList.Player1Name.isNotEmpty()) matchesUIList.Player1Name else "Unknown"
                    binding3?.txtPlayer2?.text = if (matchesUIList.Player2Name.isNotEmpty()) matchesUIList.Player2Name else "Unknown"
                    binding3?.txtResult?.text = "${matchesUIList.Score1} - ${matchesUIList.Score2}"
                    binding3?.txtNote?.text = matchesUIList.Note
                    if (matchesUIList.FrameScores.isEmpty()) {
                        binding3?.ivMatchDetail?.visibility = View.GONE
                    } else {
                        binding3?.ivMatchDetail?.visibility = View.VISIBLE

                        binding3?.item?.setOnClickListener {
                            val intent = Intent(binding3.root.context, MatchDetailActivity::class.java)
                            intent.putExtra("MATCH", matchesUIList)
                            it.context.startActivity(intent)
                        }
                    }

                }
                else -> {
                    binding4?.txtNumero?.text = matchesUIList.Number.toString()
                    binding4?.txtDate?.text = returnShortDateWithoutYear(dateToCut)
                    binding4?.txtPlayer1?.text = if (matchesUIList.Player1Name.isNotEmpty()) matchesUIList.Player1Name else "Unknown"
                    binding4?.txtPlayer2?.text = if (matchesUIList.Player2Name.isNotEmpty()) matchesUIList.Player2Name else "Unknown"
                    binding4?.txtResult?.text = "${matchesUIList.Score1} - ${matchesUIList.Score2}"
                    binding4?.txtNote?.text = matchesUIList.Note
                    if (matchesUIList.FrameScores.isEmpty()) {
                        binding4?.ivMatchDetail?.visibility = View.GONE
                    } else {
                        binding4?.ivMatchDetail?.visibility = View.VISIBLE

                        binding4?.item?.setOnClickListener {
                            val intent = Intent(binding4.root.context, MatchDetailActivity::class.java)
                            intent.putExtra("MATCH", matchesUIList)
                            it.context.startActivity(intent)
                        }
                    }
                }
            }

        }
    }


}