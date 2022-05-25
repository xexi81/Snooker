package com.los3molineros.snooker.common

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.los3molineros.snooker.common.AppConstants.TAG_LOG
import com.los3molineros.snooker.data.model.Result
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import java.net.InetSocketAddress
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.*
import javax.net.ssl.*


object CommonFunctions {
    fun debugLog(tag: String = TAG_LOG, description: String) {
        Log.d(tag, description)
    }

    suspend fun isNetworkAvailable() = coroutineScope {
        return@coroutineScope try {
            val sock = Socket()
            val socketAddress = InetSocketAddress("8.8.8.8", 53)
            sock.connect(socketAddress, 2000)
            sock.close()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getCurrentSeason(): Int {
        val auth = Firebase.auth
        auth.signInAnonymously().await()

        val db = Firebase.firestore

        val result = db.collection("params").document("params").get().await()
        val resultYear = result.data?.values

        val year = resultYear?.elementAt(0) as Long
        return year.toInt()
    }

    @SuppressLint("ClickableViewAccessibility")
    fun buttonEffect(button: View) {
        button.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.background.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
                    v.invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    v.background.clearColorFilter()
                    v.invalidate()
                }
            }
            false
        }
    }

    fun returnTournamentDates(date1: String, date2: String): String {
        val month1 = date1.subSequence(5, 7)
        val month2 = date2.subSequence(5, 7)

        try {
            if (month1 == month2) {
                return "${date1.subSequence(8, 10)} - ${date2.subSequence(8, 10)} ${
                    returnMonthName(
                        month1.toString().toInt()
                    )
                }"
            } else {
                return "${date1.subSequence(8, 10)} ${
                    returnMonthName(
                        month1.toString().toInt()
                    )
                } - ${date2.subSequence(8, 10)} ${returnMonthName(month2.toString().toInt())}"
            }
        } catch (e: Exception) {
            return e.message.toString()
        }
    }

    private fun returnMonthName(number: Int): String {
        var month: String = ""
        when (number) {
            1 -> month = "Jan"
            2 -> month = "Feb"
            3 -> month = "Mar"
            4 -> month = "Apr"
            5 -> month = "May"
            6 -> month = "Jun"
            7 -> month = "Jul"
            8 -> month = "Aug"
            9 -> month = "Sep"
            10 -> month = "Oct"
            11 -> month = "Nov"
            12 -> month = "Dec"
        }
        return month
    }

    fun returnShortDate(date: String): String {
        try {
            return "${date.subSequence(8, 10)} ${
                returnMonthName(
                    date.subSequence(5, 7).toString().toInt()
                )
            } ${date.subSequence(0, 4)}"
        }catch (e: Exception) {
            return " "
        }
    }

    fun returnShortDateWithoutYear(date: String): String {
        try {
            return "${date.subSequence(8, 10)} ${
                returnMonthName(
                    date.subSequence(5, 7).toString().toInt()
                )
            }"
        } catch (e: Exception) {
            return " "
        }

    }

    fun obtainFramesList(string: String?): List<String>? {
        if (string.isNullOrEmpty()) {
            return null
        }

        val list: MutableList<String> = mutableListOf()
        var x = 0

        while(x < string.length) {
            if (string[x].toString().toIntOrNull()==null) {
                x++
            } else {
                var y = x
                while (x <= y ) {
                    if (string[y].toString().toIntOrNull()!=null ||
                        string[y].toString() == "-" ||
                        string[y].toString() == "(" ||
                        string[y].toString() == ")" ||
                        string[y].toString() == " " ||
                        string[y].toString() == "")
                    {
                        y++
                        if (y == string.length) {
                            list.add(string.subSequence(x,y).toString())
                            x = y + 1
                        }
                    } else {
                        if (string.subSequence(x, y).toString().length > 3) {
                            list.add(string.subSequence(x, y).toString())
                        }
                        x = y + 1
                    }
                }
            }
        }

        return list
    }

    fun getResultListFromFrameList(frameList: List<String>): List<Result> {
        val list: MutableList<Result> = mutableListOf()

        for (item in frameList) {
            val score1 = item.substringBefore("-")
            var score2 = item.substringAfter("-").substringBefore("(")
            if (score2.isEmpty()) {
                score2 = item.substringAfter("-")
            }

            list.add(Result(score1, score2))
        }

        return list
    }


}