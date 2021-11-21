package com.los3molineros.snooker.ui.navigation.results

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.los3molineros.snooker.R
import com.los3molineros.snooker.common.CommonFunctions.returnTournamentDates
import com.los3molineros.snooker.data.model.SeasonEventResponse
import com.los3molineros.snooker.databinding.ItemEventsBinding
import com.los3molineros.snooker.ui.navigation.tournament.TournamentActivity

class EventAdapter(private var seasonEventResponseList: List<SeasonEventResponse>) :
    RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    fun setEvent(seasonEventResponseList: List<SeasonEventResponse>) {
        this.seasonEventResponseList = seasonEventResponseList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventAdapter.ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_events, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventAdapter.ViewHolder, position: Int) =
        holder.bind(seasonEventResponseList[position])

    override fun getItemCount() = seasonEventResponseList.size


    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemEventsBinding.bind(view)

        fun bind(eventResponse: SeasonEventResponse) {
            binding.txtDate.text =
                returnTournamentDates(eventResponse.StartDate, eventResponse.EndDate)
            binding.txtTournament.text = "${eventResponse.Sponsor} ${eventResponse.Name}"
            binding.txtLocation.text = eventResponse.City
            binding.txtType.text = eventResponse.Type

            binding.clItem.setOnClickListener {
                val intent = Intent(view.context, TournamentActivity::class.java)
                intent.putExtra("ID", eventResponse.ID)
                binding.root.context.startActivity(intent)
            }
        }
    }

}