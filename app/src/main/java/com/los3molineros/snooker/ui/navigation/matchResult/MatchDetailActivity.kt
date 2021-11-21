package com.los3molineros.snooker.ui.navigation.matchResult

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.los3molineros.snooker.common.CommonFunctions.getResultListFromFrameList
import com.los3molineros.snooker.common.CommonFunctions.obtainFramesList
import com.los3molineros.snooker.data.model.MatchUI
import com.los3molineros.snooker.databinding.ActivityMatchDetailBinding
import com.squareup.picasso.Picasso

class MatchDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityMatchDetailBinding
    private lateinit var adapter: ResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMatchDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        val match = bundle?.getParcelable<MatchUI>("MATCH")

        val results = obtainFramesList(match?.FrameScores)?.let { getResultListFromFrameList(it) }

        if (results!=null) {
            adapter = ResultAdapter(results)
            binding.rvResult.adapter = adapter
        }

        binding.txtPlayer1.text = match?.Player1Name
        binding.txtPlayer2.text = match?.Player2Name

        if (!match?.Player1Image.isNullOrEmpty()) {
            Picasso.get().load(match?.Player1Image).into(binding.ivPlayer1)
        }

        if (!match?.Player2Image.isNullOrEmpty()) {
            Picasso.get().load(match?.Player2Image).into(binding.ivPlayer2)
        }

        binding.txtResult1.text = match?.Score1.toString()
        binding.txtResult2.text = match?.Score2.toString()
    }
}