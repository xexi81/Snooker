package com.los3molineros.snooker.ui.navigation.matchResult

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.los3molineros.snooker.R
import com.los3molineros.snooker.data.model.RankingUI
import com.los3molineros.snooker.data.model.Result
import com.los3molineros.snooker.databinding.ItemRankingBinding
import com.los3molineros.snooker.databinding.ItemResultBinding

class ResultAdapter(private var resultList: List<com.los3molineros.snooker.data.model.Result>): RecyclerView.Adapter<ResultAdapter.ViewHolder>() {

    fun setResult(resultList: List<com.los3molineros.snooker.data.model.Result>) {
        this.resultList = resultList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultAdapter.ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_result, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResultAdapter.ViewHolder, position: Int) = holder.bind(resultList[position])

    override fun getItemCount() = resultList.size


    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        private val binding = ItemResultBinding.bind(view)

        fun bind(result: Result) {
            binding.txtResult1.text = result.result1
            binding.txtResult2.text = result.result2
            binding.txtFrameNumber.text = (layoutPosition + 1).toString()
        }
    }

}